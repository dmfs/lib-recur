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

import java.util.NoSuchElementException;


/**
 * An {@link AbstractRecurrenceAdapter.InstanceIterator} which inserts a start instance.
 *
 * @author Marten Gajda
 */
public final class CountLimitedRecurrenceRuleIterator implements AbstractRecurrenceAdapter.InstanceIterator
{
    private final AbstractRecurrenceAdapter.InstanceIterator mDelegate;
    private int mRemaining;


    public CountLimitedRecurrenceRuleIterator(AbstractRecurrenceAdapter.InstanceIterator delegate, int remaining)
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
        return mDelegate.next();
    }


    @Override
    public long peek()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("No further elements to iterate");
        }
        return mDelegate.peek();
    }


    @Override
    public void skip(int count)
    {
        mRemaining -= count;
        mDelegate.skip(count);
    }


    @Override
    public void fastForward(long until)
    {
        while (hasNext() && peek() < until)
        {
            next();
        }
    }
}
