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

import org.dmfs.jems2.iterable.Seq;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.InstanceIterator;
import org.dmfs.rfc5545.RecurrenceSet;


/**
 * A {@link RecurrenceSet} that fast forwards the iteration to a given instant.
 * All instances prior to that instant will be skipped.
 */
public final class FastForwarded implements RecurrenceSet
{
    private final DateTime mTimeStamp;
    private final RecurrenceSet mDelegate;


    public FastForwarded(DateTime fastForwardTo, RecurrenceSet... delegate)
    {
        this(fastForwardTo, new Seq<>(delegate));
    }


    public FastForwarded(DateTime fastForwardTo, Iterable<RecurrenceSet> delegate)
    {
        this(fastForwardTo, new Merged(delegate));
    }


    public FastForwarded(DateTime timeStamp, RecurrenceSet delegate)
    {
        mTimeStamp = timeStamp;
        mDelegate = delegate;
    }


    @Override
    public InstanceIterator iterator()
    {
        InstanceIterator iterator = mDelegate.iterator();
        iterator.fastForward(mTimeStamp);
        return iterator;
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
