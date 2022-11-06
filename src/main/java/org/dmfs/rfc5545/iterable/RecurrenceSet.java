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

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.iterable.instanceiterable.Composite;
import org.dmfs.rfc5545.iterable.instanceiterable.EmptyIterable;
import org.dmfs.rfc5545.iterable.instanceiterator.EffectiveInstancesIterator;

import java.util.Iterator;
import java.util.TimeZone;


/**
 * An {@link Iterable} of a recurrence set.
 * <p>
 * The recurrence set is determined from a number of {@link InstanceIterable}s providing instances and exceptions.
 */
public final class RecurrenceSet implements Iterable<DateTime>
{
    private final DateTime mFirst;
    private final InstanceIterable mInstances;
    private final InstanceIterable mExceptions;


    public RecurrenceSet(TimeZone timeZone, long firstInstanceTimeStamp, InstanceIterable instances)
    {
        this(new DateTime(timeZone, firstInstanceTimeStamp), instances, EmptyIterable.INSTANCE);
    }


    public RecurrenceSet(TimeZone timeZone, long firstInstanceTimeStamp, Iterable<InstanceIterable> instances)
    {
        this(new DateTime(timeZone, firstInstanceTimeStamp), new Composite(instances),
            EmptyIterable.INSTANCE);
    }


    public RecurrenceSet(TimeZone timeZone, long firstInstanceTimeStamp, Iterable<InstanceIterable> instances, Iterable<InstanceIterable> exceptions)
    {
        this(new DateTime(timeZone, firstInstanceTimeStamp), new Composite(instances), new Composite(exceptions));
    }


    public RecurrenceSet(TimeZone timeZone, long firstInstanceTimeStamp, InstanceIterable instances, InstanceIterable exceptions)
    {
        this(new DateTime(timeZone, firstInstanceTimeStamp), instances, exceptions);
    }


    public RecurrenceSet(DateTime first, InstanceIterable instances)
    {
        this(first, instances, EmptyIterable.INSTANCE);
    }


    public RecurrenceSet(DateTime first, Iterable<InstanceIterable> instances)
    {
        this(first, new Composite(instances), EmptyIterable.INSTANCE);
    }


    public RecurrenceSet(DateTime first, Iterable<InstanceIterable> instances, Iterable<InstanceIterable> exceptions)
    {
        this(first, new Composite(instances), new Composite(exceptions));
    }


    public RecurrenceSet(DateTime first, InstanceIterable instances, InstanceIterable exceptions)
    {
        this.mFirst = first;
        this.mInstances = instances;
        this.mExceptions = exceptions;
    }


    @Override
    public Iterator<DateTime> iterator()
    {
        EffectiveInstancesIterator rsi = new EffectiveInstancesIterator(mInstances.iterator(mFirst), mExceptions.iterator(mFirst));
        return new Iterator<DateTime>()
        {
            @Override
            public boolean hasNext()
            {
                return rsi.hasNext();
            }


            @Override
            public DateTime next()
            {
                DateTime result = new DateTime(mFirst.getTimeZone(), rsi.next());
                if (mFirst.isAllDay())
                {
                    result = result.toAllDay();
                }
                return result;
            }
        };
    }
}
