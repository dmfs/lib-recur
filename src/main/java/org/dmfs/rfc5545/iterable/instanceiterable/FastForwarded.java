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

package org.dmfs.rfc5545.iterable.instanceiterable;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.iterable.InstanceIterable;
import org.dmfs.rfc5545.iterable.InstanceIterator;


/**
 * An {@link InstanceIterable} that fast forwards the iteration to a given instant. All instances prior to that instant will be skipped.
 */
public final class FastForwarded implements InstanceIterable
{
    private final long mTimeStamp;
    private final InstanceIterable mDelegate;


    public FastForwarded(DateTime fastForwardTo, InstanceIterable delegate)
    {
        this(fastForwardTo.getTimestamp(), delegate);
    }


    public FastForwarded(DateTime fastForwardTo, InstanceIterable... delegate)
    {
        this(fastForwardTo.getTimestamp(), new Composite(delegate));
    }


    public FastForwarded(DateTime fastForwardTo, Iterable<InstanceIterable> delegate)
    {
        this(fastForwardTo.getTimestamp(), new Composite(delegate));
    }


    public FastForwarded(long timeStamp, InstanceIterable delegate)
    {
        mTimeStamp = timeStamp;
        mDelegate = delegate;
    }


    @Override
    public InstanceIterator iterator(DateTime firstInstance)
    {
        InstanceIterator iterator = mDelegate.iterator(firstInstance);
        if (firstInstance.getTimestamp() < mTimeStamp)
        {
            iterator.fastForward(mTimeStamp);
        }
        return iterator;
    }
}
