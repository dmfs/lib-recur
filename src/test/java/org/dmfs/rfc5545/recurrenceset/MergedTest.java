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

class MergedTest
{
    @Test
    void testMergeSingleEmpty()
    {
        assertThat(new Merged(
                new OfList(new EmptyIterable<>())),
            is(emptyRecurrenceSet()));
    }

    @Test
    void testMergeMultipleEmpty()
    {
        assertThat(new Merged(
                new OfList(new EmptyIterable<>()),
                new OfList(new EmptyIterable<>())),
            is(emptyRecurrenceSet()));
    }

    @Test
    void testMergeSingleFinite() throws InvalidRecurrenceRuleException
    {
        assertThat(new Merged(
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240215"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240217"),
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))));
    }


    @Test
    void testMergeSingleFiniteWithEmpty() throws InvalidRecurrenceRuleException
    {
        assertThat(new Merged(
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215")),
                new OfList(new EmptyIterable<>())),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240215"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240217"),
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))));
    }

    @Test
    void testMergeEmptyWithSingleFinite() throws InvalidRecurrenceRuleException
    {
        assertThat(new Merged(
                new OfList(new EmptyIterable<>()),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240215"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240217"),
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))));
    }

    @Test
    void testSingleInfinite() throws InvalidRecurrenceRuleException
    {
        assertThat(new Merged(
                new OfRule(new RecurrenceRule("FREQ=DAILY"), DateTime.parse("20240215"))),
            allOf(
                is(infinite()),
                startsWith(
                    DateTime.parse("20240215"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240217"),
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))));
    }


    @Test
    void testTwoFiniteDelegates() throws InvalidRecurrenceRuleException
    {
        assertThat(new Merged(
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215T180000")),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215T120000"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240215T120000"),
                    DateTime.parse("20240215T180000"),
                    DateTime.parse("20240216T120000"),
                    DateTime.parse("20240216T180000"),
                    DateTime.parse("20240217T120000"),
                    DateTime.parse("20240217T180000"),
                    DateTime.parse("20240218T120000"),
                    DateTime.parse("20240218T180000"),
                    DateTime.parse("20240219T120000"),
                    DateTime.parse("20240219T180000"))));
    }


    @Test
    void testOneFiniteAndOneInfiniteDelegates() throws InvalidRecurrenceRuleException
    {
        assertThat(new Merged(
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215T180000")),
                new OfRule(new RecurrenceRule("FREQ=DAILY"), DateTime.parse("20240215T120000"))),
            allOf(
                is(infinite()),
                startsWith(
                    DateTime.parse("20240215T120000"),
                    DateTime.parse("20240215T180000"),
                    DateTime.parse("20240216T120000"),
                    DateTime.parse("20240216T180000"),
                    DateTime.parse("20240217T120000"),
                    DateTime.parse("20240217T180000"),
                    DateTime.parse("20240218T120000"),
                    DateTime.parse("20240218T180000"),
                    DateTime.parse("20240219T120000"),
                    DateTime.parse("20240219T180000"))));
    }


    @Test
    void testOneInfiniteAndOneFiniteDelegates() throws InvalidRecurrenceRuleException
    {
        assertThat(new Merged(
                new OfRule(new RecurrenceRule("FREQ=DAILY"), DateTime.parse("20240215T180000")),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215T120000"))),
            allOf(
                is(infinite()),
                startsWith(
                    DateTime.parse("20240215T120000"),
                    DateTime.parse("20240215T180000"),
                    DateTime.parse("20240216T120000"),
                    DateTime.parse("20240216T180000"),
                    DateTime.parse("20240217T120000"),
                    DateTime.parse("20240217T180000"),
                    DateTime.parse("20240218T120000"),
                    DateTime.parse("20240218T180000"),
                    DateTime.parse("20240219T120000"),
                    DateTime.parse("20240219T180000"))));
    }


    @Test
    void testTwoInfiniteDelegates() throws InvalidRecurrenceRuleException
    {
        assertThat(new Merged(
                new OfRule(new RecurrenceRule("FREQ=DAILY"), DateTime.parse("20240215T180000")),
                new OfRule(new RecurrenceRule("FREQ=DAILY"), DateTime.parse("20240215T120000"))),
            allOf(
                is(infinite()),
                startsWith(
                    DateTime.parse("20240215T120000"),
                    DateTime.parse("20240215T180000"),
                    DateTime.parse("20240216T120000"),
                    DateTime.parse("20240216T180000"),
                    DateTime.parse("20240217T120000"),
                    DateTime.parse("20240217T180000"),
                    DateTime.parse("20240218T120000"),
                    DateTime.parse("20240218T180000"),
                    DateTime.parse("20240219T120000"),
                    DateTime.parse("20240219T180000"))));
    }

    @Test
    void testMultipleFinite() throws InvalidRecurrenceRuleException
    {
        assertThat(new Merged(
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240216")),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240214")),
                new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215"))),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240214"),
                    DateTime.parse("20240215"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240217"),
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"),
                    DateTime.parse("20240220"))));
    }

    /**
     * See <a href="https://github.com/dmfs/lib-recur/issues/61">Issue 61</a>
     */
    @Test
    void test_github_issue_61() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);
        assertThat(
            new Merged(
                new OfRule(new RecurrenceRule("FREQ=HOURLY;INTERVAL=5"), start),
                new OfRule(new RecurrenceRule("FREQ=DAILY;INTERVAL=1"), start)
            ),
            allOf(
                is(infinite()),
                startsWith(
                    new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 1, 5, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 1, 10, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 1, 15, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 1, 20, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 1, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 6, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 11, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 16, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 2, 21, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0),
                    new DateTime(DateTime.UTC, 2019, 1, 3, 2, 0, 0)
                )));
    }
}