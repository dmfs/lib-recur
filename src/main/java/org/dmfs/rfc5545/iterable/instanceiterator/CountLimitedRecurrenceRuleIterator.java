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

package org.dmfs.rfc5545.iterable.instanceiterator;

import org.dmfs.rfc5545.iterable.InstanceIterator;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;

import java.util.NoSuchElementException;


/**
 * An {@link InstanceIterator} which limits the number of iterated instances.
 *
 * @deprecated in favour of {@link org.dmfs.rfc5545.instanceiterator.CountLimitedRecurrenceRuleIterator}
 */
@Deprecated
public final class CountLimitedRecurrenceRuleIterator implements InstanceIterator
{
    private final RecurrenceRuleIterator mDelegate;
    private int mRemaining;


    public CountLimitedRecurrenceRuleIterator(RecurrenceRuleIterator delegate, int remaining)
    {
        mDelegate = delegate;
        mRemaining = remaining;
    }


    @Override
    public boolean hasNext()
    {
        return mRemaining > 0 && mDelegate.hasNext();
    }


    @Override
    public long next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("No further elements to iterate");
        }
        mRemaining--;
        return mDelegate.nextMillis();
    }


    @Override
    public void fastForward(long until)
    {
        while (hasNext() && mDelegate.peekMillis() < until)
        {
            next();
        }
    }
}
