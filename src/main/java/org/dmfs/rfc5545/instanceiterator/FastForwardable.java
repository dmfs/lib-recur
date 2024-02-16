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

package org.dmfs.rfc5545.instanceiterator;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.InstanceIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class FastForwardable implements InstanceIterator
{
    private DateTime mNextInstance;
    private final Iterator<DateTime> mDelegate;
    private boolean mHasNext = true;

    public FastForwardable(
        DateTime firstInstance,
        Iterator<DateTime> delegate)
    {
        mNextInstance = firstInstance;
        mDelegate = delegate;
    }

    @Override
    public void fastForward(DateTime until)
    {
        while (mHasNext && until.after(mNextInstance))
        {
            moveToNext();
        }
    }

    @Override
    public boolean hasNext()
    {
        return mHasNext;
    }

    @Override
    public DateTime next()
    {
        if (!mHasNext)
        {
            throw new NoSuchElementException("No more elements to iterate");
        }
        DateTime next = mNextInstance;
        moveToNext();
        return next;
    }

    private void moveToNext()
    {
        if (mDelegate.hasNext())
        {
            mNextInstance = mDelegate.next();
        }
        else
        {
            mHasNext = false;
        }
    }
}
