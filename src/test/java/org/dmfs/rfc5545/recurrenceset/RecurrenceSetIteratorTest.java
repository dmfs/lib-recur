/*
 * Copyright 2019 Marten Gajda <marten@dmfs.org>
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

import org.dmfs.jems2.iterator.BaseIterator;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Duration;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.dmfs.jems2.hamcrest.matchers.generatable.GeneratableMatcher.startsWith;
import static org.dmfs.jems2.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * Unit test for {@link RecurrenceSetIterator}.
 *
 * @author Marten Gajda
 */
public class RecurrenceSetIteratorTest
{
    /**
     * Test results if a single exception list has been provided.
     */
    @Test
    public void testExceptionsAllDay()
    {
        TimeZone testZone = TimeZone.getTimeZone("UTC");
        DateTime start = DateTime.parse("20180101");
        RecurrenceSetIterator recurrenceSetIterator = new RecurrenceSetIterator(
            asList(new RecurrenceList("20180101,20180102,20180103,20180104", testZone).getIterator(testZone, start.getTimestamp())),
            asList(new RecurrenceList("20180102,20180103", testZone).getIterator(testZone, start.getTimestamp())));

        // note we call hasNext twice to ensure it's idempotent
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.next(), is(start.getTimestamp()));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.next(), is(start.addDuration(new Duration(1, 3, 0)).getTimestamp()));
        assertThat(recurrenceSetIterator.hasNext(), is(false));
        assertThat(recurrenceSetIterator.hasNext(), is(false));
    }


    /**
     * Test results if multiple exception lists have been provided.
     */
    @Test
    public void testMultipleExceptionsAllDay()
    {
        TimeZone testZone = TimeZone.getTimeZone("UTC");
        DateTime start = DateTime.parse("20180101");
        RecurrenceSetIterator recurrenceSetIterator = new RecurrenceSetIterator(
            asList(new RecurrenceList("20180101,20180102,20180103,20180104", testZone).getIterator(testZone, start.getTimestamp())),
            asList(new RecurrenceList("20180103", testZone).getIterator(testZone, start.getTimestamp()),
                new RecurrenceList("20180102", testZone).getIterator(testZone, start.getTimestamp())));

        // note we call hasNext twice to ensure it's idempotent
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.next(), is(start.getTimestamp()));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.next(), is(start.addDuration(new Duration(1, 3, 0)).getTimestamp()));
        assertThat(recurrenceSetIterator.hasNext(), is(false));
        assertThat(recurrenceSetIterator.hasNext(), is(false));
    }


    /**
     * Test results if a single exception list has been provided.
     */
    @Test
    public void testExceptions()
    {
        TimeZone testZone = TimeZone.getTimeZone("UTC");
        DateTime start = DateTime.parse("20180101T120000");
        RecurrenceSetIterator recurrenceSetIterator = new RecurrenceSetIterator(
            asList(new RecurrenceList("20180101T120000,20180102T120000,20180103T120000,20180104T120000", testZone).getIterator(testZone,
                start.getTimestamp())),
            asList(new RecurrenceList("20180102T120000,20180103T120000", testZone).getIterator(testZone, start.getTimestamp())));

        // note we call hasNext twice to ensure it's idempotent
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.next(), is(start.getTimestamp()));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.next(), is(start.addDuration(new Duration(1, 3, 0)).getTimestamp()));
        assertThat(recurrenceSetIterator.hasNext(), is(false));
        assertThat(recurrenceSetIterator.hasNext(), is(false));
    }


    /**
     * Test results if multiple exception lists have been provided.
     */
    @Test
    public void testMultipleExceptions()
    {
        TimeZone testZone = TimeZone.getTimeZone("UTC");
        DateTime start = DateTime.parse("20180101T120000");
        RecurrenceSetIterator recurrenceSetIterator = new RecurrenceSetIterator(
            asList(new RecurrenceList("20180101T120000,20180102T120000,20180103T120000,20180104T120000", testZone).getIterator(testZone,
                start.getTimestamp())),
            asList(new RecurrenceList("20180103T120000", testZone).getIterator(testZone, start.getTimestamp()),
                new RecurrenceList("20180102T120000", testZone).getIterator(testZone, start.getTimestamp())));

        // note we call hasNext twice to ensure it's idempotent
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.next(), is(start.getTimestamp()));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.hasNext(), is(true));
        assertThat(recurrenceSetIterator.next(), is(start.addDuration(new Duration(1, 3, 0)).getTimestamp()));
        assertThat(recurrenceSetIterator.hasNext(), is(false));
        assertThat(recurrenceSetIterator.hasNext(), is(false));
    }


    /**
     * See https://github.com/dmfs/lib-recur/issues/61
     */
    @Test
    public void testMultipleRules() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);

        // Combine all Recurrence Rules into a RecurrenceSet
        RecurrenceSet ruleSet = new RecurrenceSet();
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=HOURLY;INTERVAL=5")));
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;INTERVAL=1")));

        // Create an iterator using the RecurrenceSet
        RecurrenceSetIterator it = ruleSet.iterator(start.getTimeZone(), start.getTimestamp());

        assertThat(() -> it::next, startsWith(
            new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 1, 5, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 1, 10, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 1, 15, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 1, 20, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 1, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 6, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 11, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 16, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 21, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 3, 2, 0, 0).getTimestamp()
        ));
    }


    @Test
    public void testMultipleRulesWithSameValues() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(2019, 1, 1);

        // Combine all Recurrence Rules into a RecurrenceSet
        RecurrenceSet ruleSet = new RecurrenceSet();
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=MO,TU,WE")));
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=WE,TH,FR")));
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=WE,FR,SA")));
        ruleSet.addExceptions(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=MO,TH")));
        ruleSet.addExceptions(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=MO")));
        ruleSet.addExceptions(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=TH,FR")));

        // Create an iterator using the RecurrenceSet
        RecurrenceSetIterator it = ruleSet.iterator(start.getTimeZone(), start.getTimestamp());

        assertThat(() -> it::next, startsWith(
            new DateTime(2019, 1, 2).getTimestamp(), // SA
            new DateTime(2019, 1, 5).getTimestamp(), // TU
            new DateTime(2019, 1, 6).getTimestamp(), // WE
            new DateTime(2019, 1, 9).getTimestamp(), // SA
            new DateTime(2019, 1, 12).getTimestamp(), // TU
            new DateTime(2019, 1, 13).getTimestamp(), // WE
            new DateTime(2019, 1, 16).getTimestamp(), // SA
            new DateTime(2019, 1, 19).getTimestamp(), // TU
            new DateTime(2019, 1, 20).getTimestamp(), // WE
            new DateTime(2019, 1, 23).getTimestamp(), // SA
            new DateTime(2019, 1, 26).getTimestamp(), // TU
            new DateTime(2019, 1, 27).getTimestamp(), // WE
            new DateTime(2019, 2, 2).getTimestamp(), // SA
            new DateTime(2019, 2, 5).getTimestamp() // TU
        ));
    }


    /**
     * See https://github.com/dmfs/lib-recur/issues/93
     */
    @Test
    public void testGithubIssue93() throws InvalidRecurrenceRuleException
    {
        DateTime start = DateTime.parse("20200414T160000Z");

        // Combine all Recurrence Rules into a RecurrenceSet
        RecurrenceSet ruleSet = new RecurrenceSet();
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=WEEKLY;UNTIL=20200511T000000Z;BYDAY=TU")));
        ruleSet.addExceptions(new RecurrenceList("20200421T160000Z,20200505T160000Z", DateTime.UTC));

        // Create an iterator using the RecurrenceSet
        assertThat(() -> new RecurrenceAdapter(ruleSet.iterator(start.getTimeZone(), start.getTimestamp())),
            iteratorOf(
                DateTime.parse("20200414T160000Z").getTimestamp(),
                DateTime.parse("20200428T160000Z").getTimestamp()));
    }


    @Test
    public void testMultipleRulesWithSameValuesAndCount() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(2019, 1, 1);

        // Combine all Recurrence Rules into a RecurrenceSet
        RecurrenceSet ruleSet = new RecurrenceSet();
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=MO,TU,WE")));
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=WE,TH,FR;COUNT=10")));
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=WE,FR,SA;COUNT=5")));
        ruleSet.addExceptions(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=MO,TH;UNTIL=20190212")));
        ruleSet.addExceptions(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=MO;COUNT=4")));
        ruleSet.addExceptions(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;BYDAY=TH,FR")));

        // Create an iterator using the RecurrenceSet
        RecurrenceSetIterator it = ruleSet.iterator(start.getTimeZone(), start.getTimestamp());

        assertThat(() -> it::next, startsWith(
            new DateTime(2019, 1, 2).getTimestamp(), // SA
            new DateTime(2019, 1, 5).getTimestamp(), // TU
            new DateTime(2019, 1, 6).getTimestamp(), // WE
            new DateTime(2019, 1, 9).getTimestamp(), // SA
            new DateTime(2019, 1, 12).getTimestamp(), // TU
            new DateTime(2019, 1, 13).getTimestamp(), // WE
            //new DateTime(2019, 1, 16).getTimestamp(), // SA
            new DateTime(2019, 1, 19).getTimestamp(), // TU
            new DateTime(2019, 1, 20).getTimestamp(), // WE
            //new DateTime(2019, 1, 23).getTimestamp(), // SA
            new DateTime(2019, 1, 25).getTimestamp(), // MO
            new DateTime(2019, 1, 26).getTimestamp(), // TU
            new DateTime(2019, 1, 27).getTimestamp(), // WE
            //new DateTime(2019, 2, 2).getTimestamp(), // SA
            new DateTime(2019, 2, 4).getTimestamp(), // MO
            new DateTime(2019, 2, 5).getTimestamp() // TU
        ));
    }


    /**
     * See https://github.com/dmfs/lib-recur/issues/61
     */
    @Test
    public void testMultipleRulesWithFastForward() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);

        // Combine all Recurrence Rules into a RecurrenceSet
        RecurrenceSet ruleSet = new RecurrenceSet();
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=HOURLY;INTERVAL=5")));
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;INTERVAL=1")));

        // Create an iterator using the RecurrenceSet
        RecurrenceSetIterator it = ruleSet.iterator(start.getTimeZone(), start.getTimestamp());

        // Fast forward to the time of calculation (1/1/2019 at 10pm).
        it.fastForward(new DateTime(DateTime.UTC, 2019, 1, 1, 22, 0, 0).getTimestamp());

        assertThat(() -> it::next, startsWith(
            new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 1, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 6, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 11, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 16, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 21, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 3, 2, 0, 0).getTimestamp()
        ));
    }


    /**
     * See https://github.com/dmfs/lib-recur/issues/85
     * <p>
     * Fast forward to the start date (i.e. not fast forwarding at all)
     */
    @Test
    public void testFastForwardToStart() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);

        RecurrenceSet ruleSet = new RecurrenceSet();
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;INTERVAL=1")));

        // Create an iterator using the RecurrenceSet
        RecurrenceSetIterator it = ruleSet.iterator(start.getTimeZone(), start.getTimestamp());

        // "Fast forward" to start.
        it.fastForward(start.getTimestamp());

        assertThat(() -> it::next, startsWith(
            new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 4, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 5, 0, 0, 0).getTimestamp()
        ));
    }


    @Test
    public void testFastForwardToPast() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);

        RecurrenceSet ruleSet = new RecurrenceSet();
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;INTERVAL=1")));

        // Create an iterator using the RecurrenceSet
        RecurrenceSetIterator it = ruleSet.iterator(start.getTimeZone(), start.getTimestamp());

        // "Fast forward" to 100 days in the past.
        it.fastForward(start.getTimestamp() - TimeUnit.DAYS.toMillis(100));

        assertThat(() -> it::next, startsWith(
            new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 4, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 5, 0, 0, 0).getTimestamp()
        ));
    }


    @Test
    public void testFastForwardToNext() throws InvalidRecurrenceRuleException
    {
        DateTime start = new DateTime(DateTime.UTC, 2019, 1, 1, 0, 0, 0);

        RecurrenceSet ruleSet = new RecurrenceSet();
        ruleSet.addInstances(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=DAILY;INTERVAL=1")));

        // Create an iterator using the RecurrenceSet
        RecurrenceSetIterator it = ruleSet.iterator(start.getTimeZone(), start.getTimestamp());

        // "Fast forward" to 1 millisecond after start (skipping the first instance only)
        it.fastForward(start.getTimestamp() + 1);

        assertThat(() -> it::next, startsWith(
            new DateTime(DateTime.UTC, 2019, 1, 2, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 3, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 4, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 5, 0, 0, 0).getTimestamp(),
            new DateTime(DateTime.UTC, 2019, 1, 6, 0, 0, 0).getTimestamp()
        ));
    }


    private final static class RecurrenceAdapter extends BaseIterator<Long>
    {

        private final RecurrenceSetIterator mDelegate;


        private RecurrenceAdapter(RecurrenceSetIterator delegate)
        {
            mDelegate = delegate;
        }


        @Override
        public boolean hasNext()
        {
            return mDelegate.hasNext();
        }


        @Override
        public Long next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            return mDelegate.next();
        }
    }
}