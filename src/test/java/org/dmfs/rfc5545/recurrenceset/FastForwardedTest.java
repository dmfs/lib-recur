/*
 * Copyright 2024 Marten Gajda <marten@dmfs.org>
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.rfc5545.recurrenceset;

import org.dmfs.jems2.iterable.EmptyIterable;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Duration;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.junit.jupiter.api.Test;

import static org.dmfs.rfc5545.confidence.Recur.*;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;

class FastForwardedTest
{
    @Test
    void testFastForwardEmptySet()
    {
        assertThat(
            new FastForwarded(DateTime.parse("20240225"),
                new OfList(new EmptyIterable<>())),
            is(emptyRecurrenceSet()));
    }

    @Test
    void testFastForwardBeyondLastInstance() throws InvalidRecurrenceRuleException
    {
        assertThat(
            new FastForwarded(DateTime.parse("20240225"),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215"))),
            is(emptyRecurrenceSet()));
    }

    @Test
    void testFastForwardRule() throws InvalidRecurrenceRuleException
    {
        assertThat(
            new FastForwarded(DateTime.parse("20240218"),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))));
    }


    @Test
    void testFastForwardRuleWithUnsyncedStart() throws InvalidRecurrenceRuleException
    {
        assertThat(
            new FastForwarded(DateTime.parse("20240218"),
                new OfRuleAndFirst(new RecurrenceRule("FREQ=DAILY;BYDAY=FR;COUNT=3"), DateTime.parse("20240215"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240223"))));
    }


    @Test
    void testFastForwardRuleWithUnsyncedStartMultipleInstances() throws InvalidRecurrenceRuleException
    {
        assertThat(
            new FastForwarded(DateTime.parse("20240218"),
                new OfRuleAndFirst(new RecurrenceRule("FREQ=DAILY;BYDAY=FR;COUNT=5"), DateTime.parse("20240207"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240223"),
                    DateTime.parse("20240301"))));
    }

    @Test
    void testFastForwardList()
    {
        assertThat(
            new FastForwarded(DateTime.parse("20240218"),
                new OfList(
                    DateTime.parse("20240216"),
                    DateTime.parse("20240217"),
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))));
    }


    @Test
    void testFastForwardMultiple() throws InvalidRecurrenceRuleException
    {
        assertThat(
            new FastForwarded(DateTime.parse("20240218"),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215")),
                new OfList(
                    DateTime.parse("20240213"),
                    DateTime.parse("20240214"),
                    DateTime.parse("20240220"),
                    DateTime.parse("20240221"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"),
                    DateTime.parse("20240220"),
                    DateTime.parse("20240221"))));
    }


    @Test
    void testFastForwardWithExceptions() throws InvalidRecurrenceRuleException
    {
        assertThat(
            new FastForwarded(DateTime.parse("20240218"),
                new Difference(
                    new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=7"), DateTime.parse("20240215")),
                    new OfList(
                        DateTime.parse("20240217"),
                        DateTime.parse("20240220")))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"),
                    DateTime.parse("20240221"))));
    }

    /**
     * See  <a href="https://github.com/dmfs/lib-recur/issues/61">Issue 61</a>
     */
    @Test
    void testGithubIssue61() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);
        assertThat(
            new FastForwarded(
                new DateTime(DateTime.UTC, 2019, 1, 1, 22, 0, 0),
                new Merged(
                    new OfRule(new RecurrenceRule("FREQ=HOURLY;INTERVAL=5"), start),
                    new OfRule(new RecurrenceRule("FREQ=DAILY;INTERVAL=1"), start)
                )),
            allOf(
                is(infinite()),
                startsWith(
                    new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 1, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 6, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 11, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 16, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 21, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 3, 2, 0, 0))));
    }

    /**
     * See <a href="https://github.com/dmfs/lib-recur/issues/85">Issue 85</a>
     */
    @Test
    void testGithubIssue85() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);
        assertThat(
            new FastForwarded(start,
                new OfRule(new RecurrenceRule("FREQ=DAILY;INTERVAL=1"), start)),
            allOf(
                is(infinite()),
                startsWith(
                    new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 4, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 5, 0, 0, 0))));
    }


    @Test
    void testFastForwardIntoPast() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);
        assertThat(
            new FastForwarded(start.addDuration(new Duration(-1, 10)),
                new OfRule(new RecurrenceRule("FREQ=DAILY;INTERVAL=1"), start)),
            allOf(
                is(infinite()),
                startsWith(
                    new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 4, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 5, 0, 0, 0))));
    }


    @Test
    void testFastForwardSkipping1stInstance() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);
        assertThat(
            new FastForwarded(start.addDuration(new Duration(1, 0, 1)),
                new OfRule(new RecurrenceRule("FREQ=DAILY;INTERVAL=1"), start)),
            allOf(
                is(infinite()),
                startsWith(
                    new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 4, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 5, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 6, 0, 0, 0))));
    }
}