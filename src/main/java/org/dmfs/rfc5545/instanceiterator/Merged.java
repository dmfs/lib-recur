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

package org.dmfs.rfc5545.instanceiterator;

import org.dmfs.jems2.iterable.Ascending;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.single.Collected;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.InstanceIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * An {@link InstanceIterator} which iterates the elements of other {@link InstanceIterator} in chronological order.
 */
public final class Merged implements InstanceIterator
{
    private final List<IteratorHolder> mIteratorHolders;


    public Merged(InstanceIterator... delegates)
    {
        this(new Seq<>(delegates));
    }


    public Merged(Iterable<InstanceIterator> delegates)
    {
        mIteratorHolders = new Collected<>(
            ArrayList::new,
            new Ascending<IteratorHolder>(
                new Mapped<>(
                    IteratorHolder::new,
                    new Sieved<>(
                        InstanceIterator::hasNext,
                        delegates)))).value();

        // resolve duplicates
        for (int i = mIteratorHolders.size(); i > 1; --i)
        {
            if (mIteratorHolders.get(i - 1).mNextValue.equals(mIteratorHolders.get(i - 2).mNextValue))
            {
                if (mIteratorHolders.get(i - 1).mIterator.hasNext())
                {
                    bubbleUp(i - 1);
                }
                else
                {
                    mIteratorHolders.remove(i - 1);
                }
            }
        }
    }


    @Override
    public boolean hasNext()
    {
        return mIteratorHolders.size() > 0;
    }


    @Override
    public DateTime next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("No more elements to iterate");
        }
        DateTime result = mIteratorHolders.get(0).mNextValue;
        advance();
        return result;
    }


    @Override
    public void fastForward(DateTime until)
    {
        for (int i = mIteratorHolders.size() - 1; i >= 0; --i)
        {
            IteratorHolder it = mIteratorHolders.get(i);
            if (it.mNextValue.getTimestamp() < until.getTimestamp())
            {
                it.mIterator.fastForward(until);
                if (it.mIterator.hasNext())
                {
                    it.mNextValue = it.mIterator.next();
                }
                else
                {
                    mIteratorHolders.remove(i);
                }
            }
        }
        Collections.sort(mIteratorHolders);
    }


    private void advance()
    {
        if (mIteratorHolders.size() == 1)
        {
            IteratorHolder iteratorHolder = mIteratorHolders.get(0);
            if (iteratorHolder.mIterator.hasNext())
            {
                iteratorHolder.mNextValue = iteratorHolder.mIterator.next();
            }
            else
            {
                mIteratorHolders.clear();
            }
        }
        else
        {
            IteratorHolder iteratorHolder = mIteratorHolders.get(0);
            if (iteratorHolder.mIterator.hasNext())
            {
                bubbleUp(0);
            }
            else
            {
                mIteratorHolders.remove(0);
            }
        }
    }


    private void bubbleUp(int pos)
    {
        // pull the next element and let it bubble up to its position
        final List<IteratorHolder> iteratorHolders = mIteratorHolders;
        IteratorHolder first = iteratorHolders.get(pos);
        DateTime next = first.mIterator.next();
        while (pos < iteratorHolders.size() - 1 && !next.before(iteratorHolders.get(pos + 1).mNextValue))
        {
            if (next.equals(iteratorHolders.get(pos + 1).mNextValue))
            {
                // value already present, skip this one
                if (first.mIterator.hasNext())
                {
                    next = first.mIterator.next();
                }
                else
                {
                    // this one has no more elements
                    iteratorHolders.remove(pos);
                    return;
                }
            }
            iteratorHolders.set(pos, iteratorHolders.get(pos + 1));
            pos++;
        }
        first.mNextValue = next;
        iteratorHolders.set(pos, first);
    }


    private final static class IteratorHolder implements Comparable<IteratorHolder>
    {
        private DateTime mNextValue;
        private final InstanceIterator mIterator;


        private IteratorHolder(InstanceIterator iterator)
        {
            this(iterator.next(), iterator);
        }


        private IteratorHolder(DateTime nextValue, InstanceIterator iterator)
        {
            mNextValue = nextValue;
            mIterator = iterator;
        }

        @Override
        public int compareTo(IteratorHolder o)
        {
            long diff = mNextValue.getTimestamp() - o.mNextValue.getTimestamp();
            return diff == 0 ? 0 : diff < 0 ? -1 : 1;
        }
    }
}
