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

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.predicate.Not;
import org.dmfs.rfc5545.InstanceIterator;
import org.dmfs.rfc5545.RecurrenceSet;


/**
 * A composite {@link RecurrenceSet} composed of other {@link RecurrenceSet}s. This {@link RecurrenceSet}
 * iterates the instances of all given {@link RecurrenceSet}s in chronological order.
 */
public final class Merged implements RecurrenceSet
{
    private final RecurrenceSet mDelegate;


    public Merged(RecurrenceSet... delegates)
    {
        this(new Seq<>(delegates));
    }


    public Merged(Iterable<RecurrenceSet> delegates)
    {
        this(
            new RecurrenceSet()
            {
                @Override
                public InstanceIterator iterator()
                {
                    return new org.dmfs.rfc5545.instanceiterator.Merged(new Mapped<>(RecurrenceSet::iterator, delegates));
                }

                @Override
                public boolean isInfinite()
                {
                    return new Sieved<>(RecurrenceSet::isInfinite, delegates).iterator().hasNext();
                }

                @Override
                public boolean isFinite()
                {
                    return !new Sieved<>(new Not<>(RecurrenceSet::isFinite), delegates).iterator().hasNext();
                }
            });
    }


    private Merged(RecurrenceSet delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public InstanceIterator iterator()
    {
        return mDelegate.iterator();
    }

    @Override
    public boolean isInfinite()
    {
        return mDelegate.isInfinite();
    }

    @Override
    public boolean isFinite()
    {
        return mDelegate.isFinite();
    }
}
