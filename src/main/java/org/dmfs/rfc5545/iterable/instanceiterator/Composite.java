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

import org.dmfs.jems2.comparator.By;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.single.Collected;
import org.dmfs.rfc5545.iterable.InstanceIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * An {@link InstanceIterator} which iterates the elements of other {@link InstanceIterator} in chronological order.
 *
 * @deprecated in favour of {@link org.dmfs.rfc5545.instanceiterator.Merged}
 */
@Deprecated
public final class Composite implements InstanceIterator
{
    private List<Helper> mHelpers;


    public Composite(InstanceIterator... delegates)
    {
        this(new Seq<>(delegates));
    }


    public Composite(Iterable<InstanceIterator> delegates)
    {
        mHelpers = new Collected<>(
            ArrayList::new,
            new Mapped<>(
                Helper::new,
                new Sieved<>(
                    InstanceIterator::hasNext,
                    delegates))).value();
        Collections.sort(mHelpers, new By<>(helper -> helper.nextValue));

        // resolve duplicates
        for (int i = mHelpers.size(); i > 1; --i)
        {
            if (mHelpers.get(i - 1).nextValue == mHelpers.get(i - 2).nextValue)
            {
                if (mHelpers.get(i - 1).iterator.hasNext())
                {
                    bubbleUp(i - 1);
                }
                else
                {
                    mHelpers.remove(i - 1);
                }
            }
        }
    }


    @Override
    public boolean hasNext()
    {
        return mHelpers.size() > 0;
    }


    @Override
    public long next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("No more elements to iterate");
        }
        long result = mHelpers.get(0).nextValue;
        advance();
        return result;
    }


    @Override
    public void fastForward(long until)
    {
        for (int i = mHelpers.size() - 1; i >= 0; --i)
        {
            Helper it = mHelpers.get(i);
            if (it.nextValue < until)
            {
                it.iterator.fastForward(until);
                if (it.iterator.hasNext())
                {
                    it.nextValue = it.iterator.next();
                }
                else
                {
                    mHelpers.remove(i);
                }
            }
        }
        Collections.sort(mHelpers, new By<>(helper -> helper.nextValue));
    }


    private void advance()
    {
        if (mHelpers.size() == 1)
        {
            Helper helper = mHelpers.get(0);
            if (helper.iterator.hasNext())
            {
                helper.nextValue = helper.iterator.next();
            }
            else
            {
                mHelpers.clear();
            }
        }
        else
        {
            Helper helper = mHelpers.get(0);
            if (helper.iterator.hasNext())
            {
                bubbleUp(0);
            }
            else
            {
                mHelpers.remove(0);
            }
        }
    }


    private void bubbleUp(int pos)
    {
        // pull the next element and let it bubble up to its position
        final List<Helper> helpers = mHelpers;
        Helper first = helpers.get(pos);
        long next = first.iterator.next();
        while (pos < helpers.size() - 1 && next >= helpers.get(pos + 1).nextValue)
        {
            if (next == helpers.get(pos + 1).nextValue)
            {
                // value already present, skip this one
                if (first.iterator.hasNext())
                {
                    next = first.iterator.next();
                }
                else
                {
                    // this one has no more elements
                    helpers.remove(pos);
                    return;
                }
            }
            helpers.set(pos, helpers.get(pos + 1));
            pos++;
        }
        first.nextValue = next;
        helpers.set(pos, first);
    }


    private final static class Helper
    {
        private long nextValue;
        private final InstanceIterator iterator;


        private Helper(InstanceIterator iterator)
        {
            this(iterator.next(), iterator);
        }


        private Helper(long nextValue, InstanceIterator iterator)
        {
            this.nextValue = nextValue;
            this.iterator = iterator;
        }
    }
}
