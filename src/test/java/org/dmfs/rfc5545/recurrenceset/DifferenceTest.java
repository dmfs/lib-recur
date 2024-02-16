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
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.junit.jupiter.api.Test;

import static org.dmfs.rfc5545.confidence.Recur.*;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;

class DifferenceTest
{
    @Test
    void testEmptyInstancesAndExceptions()
    {
        assertThat(new Difference(
                new OfList(new EmptyIterable<>()),
                new OfList(new EmptyIterable<>())
            ),
            is(emptyRecurrenceSet()));
    }

    @Test
    void testEmptyInstancesAndNonEmptyExceptions() throws InvalidRecurrenceRuleException
    {
        assertThat(new Difference(
                new OfList(new EmptyIterable<>()),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240224T120000"))),
            is(emptyRecurrenceSet()));
    }

    @Test
    void testInstancesEqualExceptions() throws InvalidRecurrenceRuleException
    {
        assertThat(new Difference(
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240224T120000")),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240224T120000"))),
            is(emptyRecurrenceSet()));
    }


    @Test
    void testInstancesAndExceptions() throws InvalidRecurrenceRuleException
    {
        assertThat(new Difference(
                new OfRule(new RecurrenceRule("FREQ=HOURLY;INTERVAL=12;COUNT=10"), DateTime.parse("20240224T120000")),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240224T000000"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240224T120000"),
                    DateTime.parse("20240225T120000"),
                    DateTime.parse("20240226T120000"),
                    DateTime.parse("20240227T120000"),
                    DateTime.parse("20240228T120000"),
                    DateTime.parse("20240229T000000"))));
    }


    /**
     * See <a href="https://github.com/dmfs/lib-recur/issues/93">Issue 93</a>
     */
    @Test
    void test_github_issue_93() throws InvalidRecurrenceRuleException
    {
        assertThat(
            new Difference(
                new OfRule(new RecurrenceRule("FREQ=WEEKLY;UNTIL=20200511T000000Z;BYDAY=TU"), DateTime.parse("20200414T160000Z")),
                new OfList(DateTime.parse("20200421T160000Z"), DateTime.parse("20200505T160000Z"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20200414T160000Z"),
                    DateTime.parse("20200428T160000Z"))));
    }


    @Test
    void test_multiple_rules_with_same_values_and_count() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(2019, 1, 1);

        assertThat(
            new Difference(
                new Merged(
                    new OfRule(new RecurrenceRule("FREQ=DAILY;BYDAY=MO,TU,WE"), start),
                    new OfRule(new RecurrenceRule("FREQ=DAILY;BYDAY=WE,TH,FR;COUNT=10"), start),
                    new OfRule(new RecurrenceRule("FREQ=DAILY;BYDAY=WE,FR,SA;COUNT=5"), start)
                ),
                new Merged(
                    new OfRule(new RecurrenceRule("FREQ=DAILY;BYDAY=MO,TH;UNTIL=20190212"), start),
                    new OfRule(new RecurrenceRule("FREQ=DAILY;BYDAY=MO;COUNT=4"), start),
                    new OfRule(new RecurrenceRule("FREQ=DAILY;BYDAY=TH,FR"), start)
                )
            ),
            allOf(
                is(infinite()),
                startsWith(
                    new DateTime(2019, 1, 2), // SA
                    new DateTime(2019, 1, 5), // TU
                    new DateTime(2019, 1, 6), // WE
                    new DateTime(2019, 1, 9), // SA
                    new DateTime(2019, 1, 12), // TU
                    new DateTime(2019, 1, 13), // WE
                    new DateTime(2019, 1, 19), // TU
                    new DateTime(2019, 1, 20), // WE
                    new DateTime(2019, 1, 26), // TU
                    new DateTime(2019, 1, 27), // WE
                    new DateTime(2019, 2, 4), // MO
                    new DateTime(2019, 2, 5), // TU
                    new DateTime(2019, 2, 6)  // WE
                )));
    }
}