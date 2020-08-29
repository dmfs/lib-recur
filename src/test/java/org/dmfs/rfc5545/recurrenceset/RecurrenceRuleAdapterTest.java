/*
 * Copyright 2017 Marten Gajda <marten@dmfs.org>
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
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.junit.Test;

import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author marten
 */
public class RecurrenceRuleAdapterTest
{
    @Test
    public void testGetIteratorSyncedStartInfinite() throws Exception
    {
        AbstractRecurrenceAdapter.InstanceIterator iterator = new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY"))
                .getIterator(TimeZone.getTimeZone("Europe/Berlin"), DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp());

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170210T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170310T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
    }


    @Test
    public void testGetIteratorSyncedStartWithCount() throws Exception
    {
        AbstractRecurrenceAdapter.InstanceIterator iterator = new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY;COUNT=3"))
                .getIterator(TimeZone.getTimeZone("Europe/Berlin"), DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp());

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170210T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170310T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
    }


    @Test
    public void testGetIteratorSyncedStartWithUntil() throws Exception
    {
        AbstractRecurrenceAdapter.InstanceIterator iterator = new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY;UNTIL=20170312T113012Z"))
                .getIterator(TimeZone.getTimeZone("Europe/Berlin"), DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp());

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170210T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170310T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
    }


    @Test
    public void testGetIteratorUnsyncedStartInfinite() throws Exception
    {
        AbstractRecurrenceAdapter.InstanceIterator iterator = new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY;BYMONTHDAY=11"))
                .getIterator(TimeZone.getTimeZone("Europe/Berlin"), DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp());

        // note the unsynced start is not a result, it's added separately by `RecurrenceSet`
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170111T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170211T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170311T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
    }


    @Test
    public void testGetIteratorUnsyncedStartWithCount() throws Exception
    {
        AbstractRecurrenceAdapter.InstanceIterator iterator = new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY;COUNT=3;BYMONTHDAY=11"))
                .getIterator(TimeZone.getTimeZone("Europe/Berlin"), DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp());

        // note the unsynced start is not a result, it's added separately by `RecurrenceSet`
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170111T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170211T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
    }


    @Test
    public void testGetIteratorUnsyncedStartWithUntil() throws Exception
    {
        AbstractRecurrenceAdapter.InstanceIterator iterator = new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY;UNTIL=20170312T113012Z;BYMONTHDAY=11"))
                .getIterator(TimeZone.getTimeZone("Europe/Berlin"), DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp());

        // note the unsynced start is not a result, it's added separately by `RecurrenceSet`
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170111T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170211T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(DateTime.parse("Europe/Berlin", "20170311T113012").getTimestamp()));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
    }


    @Test
    public void testIsInfinite() throws Exception
    {
        assertThat(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY")).isInfinite(), is(true));
        assertThat(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY;COUNT=10")).isInfinite(), is(false));
        assertThat(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY;UNTIL=20171212")).isInfinite(), is(false));
    }


    @Test
    public void testGetLastInstance() throws Exception
    {
        assertThat(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY;COUNT=10"))
                        .getLastInstance(TimeZone.getTimeZone("Europe/Berlin"), DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp()),
                is(DateTime.parse("Europe/Berlin", "20171010T113012").getTimestamp()));
        assertThat(new RecurrenceRuleAdapter(new RecurrenceRule("FREQ=MONTHLY;UNTIL=20171212T101010Z"))
                        .getLastInstance(TimeZone.getTimeZone("Europe/Berlin"), DateTime.parse("Europe/Berlin", "20170110T113012").getTimestamp()),
                is(DateTime.parse("Europe/Berlin", "20171210T113012").getTimestamp()));
    }
}