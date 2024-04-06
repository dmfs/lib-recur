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

import org.dmfs.jems2.comparator.By;
import org.dmfs.jems2.iterable.Expanded;
import org.dmfs.jems2.iterable.Frozen;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sorted;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.InstanceIterator;
import org.dmfs.rfc5545.RecurrenceSet;
import org.dmfs.rfc5545.instanceiterator.EmptyIterator;
import org.dmfs.rfc5545.instanceiterator.FastForwardable;
import org.dmfs.rfc5545.iterable.ParsedDates;

import java.util.Iterator;
import java.util.TimeZone;


/**
 * A {@link RecurrenceSet} of a given list of instances, typically provided in the form of {@code RDATE}s or {@code EXDATE}s.
 * <p>
 * Note, that this class does <strong>not</strong> support parsing the {@code PERIOD} type specified in
 * <a href="https://datatracker.ietf.org/doc/html/rfc5545#section-3.3.9">RFC 5545, section 3.3.9</a>.
 */
public final class OfList implements RecurrenceSet
{
    private final Iterable<DateTime> mInstances;


    public OfList(TimeZone timeZone, String... instances)
    {
        this(timeZone, new Seq<>(instances));
    }

    public OfList(TimeZone timeZone, Iterable<String> instances)
    {
        this(new Expanded<>(list -> new ParsedDates(timeZone, list), instances));
    }

    public OfList(TimeZone timeZone, String instances)
    {
        this(new ParsedDates(timeZone, instances));
    }

    public OfList(String... instances)
    {
        this(new Expanded<>(ParsedDates::new, new Seq<>(instances)));
    }

    public OfList(String instances)
    {
        this(new ParsedDates(instances));
    }

    public OfList(DateTime... instances)
    {
        this(new Seq<>(instances));
    }

    public OfList(Iterable<DateTime> instances)
    {
        mInstances = new Frozen<>(new Sorted<>(new By<>(DateTime::getTimestamp), instances));
    }

    @Override
    public InstanceIterator iterator()
    {
        Iterator<DateTime> delegate = mInstances.iterator();
        return delegate.hasNext()
            ? new FastForwardable(delegate.next(), delegate.hasNext() ? delegate : EmptyIterator.INSTANCE)
            : EmptyIterator.INSTANCE;
    }

    @Override
    public boolean isInfinite()
    {
        return false;
    }

    @Override
    public boolean isFinite()
    {
        return true;
    }
}
