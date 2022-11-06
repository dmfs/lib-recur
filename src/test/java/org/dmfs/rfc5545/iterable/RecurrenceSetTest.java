/*
 * Copyright 2022 Marten Gajda <marten@dmfs.org>
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

package org.dmfs.rfc5545.iterable;

import org.dmfs.jems2.iterable.Seq;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Duration;
import org.dmfs.rfc5545.iterable.instanceiterable.FastForwarded;
import org.dmfs.rfc5545.iterable.instanceiterable.FirstAndRuleInstances;
import org.dmfs.rfc5545.iterable.instanceiterable.InstanceList;
import org.dmfs.rfc5545.iterable.instanceiterable.RuleInstances;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.junit.jupiter.api.Test;

import java.util.TimeZone;

import static org.dmfs.jems2.hamcrest.matchers.iterable.IterableMatcher.iteratesTo;
import static org.hamcrest.MatcherAssert.assertThat;


class RecurrenceSetTest
{
    @Test
    void testWithFullSet() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceSet(DateTime.parse("20220101"),
                new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=MO;COUNT=5"))),
            iteratesTo(
                DateTime.parse("20220101"),
                DateTime.parse("20220103"),
                DateTime.parse("20220110"),
                DateTime.parse("20220117"),
                DateTime.parse("20220124")
            ));
    }


    @Test
    void testWithStrictSet() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceSet(DateTime.parse("20220101"),
                new RuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=MO;COUNT=5"))),
            iteratesTo(
                DateTime.parse("20220103"),
                DateTime.parse("20220110"),
                DateTime.parse("20220117"),
                DateTime.parse("20220124"),
                DateTime.parse("20220131")
            ));
    }


    @Test
    void testWithMultipleSets() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceSet(DateTime.parse("20220101"),
                new Seq<>(
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=MO;COUNT=5")),
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20220125")))),
            iteratesTo(
                DateTime.parse("20220101"),
                DateTime.parse("20220103"),
                DateTime.parse("20220104"),
                DateTime.parse("20220110"),
                DateTime.parse("20220111"),
                DateTime.parse("20220117"),
                DateTime.parse("20220118"),
                DateTime.parse("20220124"),
                DateTime.parse("20220125")
            ));
    }


    @Test
    void testWithExceptions() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceSet(DateTime.parse("20220101"),
                new Seq<>(
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=MO;COUNT=5")),
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20220125"))
                ),
                new Seq<>(new InstanceList("20220110,20220118", DateTime.UTC))),
            iteratesTo(
                DateTime.parse("20220101"),
                DateTime.parse("20220103"),
                DateTime.parse("20220104"),
                DateTime.parse("20220111"),
                DateTime.parse("20220117"),
                DateTime.parse("20220124"),
                DateTime.parse("20220125")
            ));
    }


    @Test
    void testWithFastForward() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceSet(DateTime.parse("20220101"),
                new FastForwarded(DateTime.parse("20220111"),
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=MO;COUNT=5")),
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20220125"))
                ),
                new InstanceList("20220110,20220118", DateTime.UTC)),
            iteratesTo(
                DateTime.parse("20220111"),
                DateTime.parse("20220117"),
                DateTime.parse("20220124"),
                DateTime.parse("20220125")
            ));
    }


    @Test
    void testWithTimeStamp() throws InvalidRecurrenceRuleException
    {
        DateTime start = DateTime.parse(TimeZone.getTimeZone("Europe/Berlin"), "20220101T120000");
        assertThat(new RecurrenceSet(start.getTimeZone(), start.getTimestamp(),
                new FastForwarded(start.addDuration(Duration.parse("P10D")),
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=MO;COUNT=5")),
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20220125T110000Z"))
                ),
                new InstanceList("20220110T120000,20220118T120000", start.getTimeZone())),
            iteratesTo(
                DateTime.parse(start.getTimeZone(), "20220111T120000"),
                DateTime.parse(start.getTimeZone(), "20220117T120000"),
                DateTime.parse(start.getTimeZone(), "20220124T120000"),
                DateTime.parse(start.getTimeZone(), "20220125T120000")
            ));
    }


    @Test
    void testWithDuplicates() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceSet(DateTime.parse("20220101"),
                new FastForwarded(DateTime.parse("20220111"),
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=MO;COUNT=5")),
                    new FirstAndRuleInstances(new RecurrenceRule("FREQ=WEEKLY;BYDAY=MO,TU;UNTIL=20220125"))
                ),
                new InstanceList("20220110,20220118", DateTime.UTC)),
            iteratesTo(
                DateTime.parse("20220111"),
                DateTime.parse("20220117"),
                DateTime.parse("20220124"),
                DateTime.parse("20220125")
            ));
    }
}
