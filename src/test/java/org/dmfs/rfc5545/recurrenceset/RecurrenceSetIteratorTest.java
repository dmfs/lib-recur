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

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Duration;
import org.junit.Test;

import java.util.TimeZone;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


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
}