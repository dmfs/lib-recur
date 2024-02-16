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

import java.util.Locale;


/**
 * An iterator for recurrence sets. It takes a number of {@link InstanceIterator}s for instances and exceptions and iterates all resulting instances
 * (i.e. only the instances, not the exceptions).
 *
 * @deprecated in favour of {@link org.dmfs.rfc5545.instanceiterator.EffectiveInstancesIterator}
 */
@Deprecated
public final class EffectiveInstancesIterator implements InstanceIterator
{
    /**
     * Throw if we skipped this many instances in a line, because they were exceptions.
     */
    private final static int MAX_SKIPPED_INSTANCES = 1000;

    private final InstanceIterator mInstances;

    private final InstanceIterator mExceptions;

    private long mNextInstance = Long.MIN_VALUE;

    private long mNextException = Long.MIN_VALUE;


    /**
     * Create a new recurrence iterator for specific lists of instances and exceptions.
     *
     * @param instances  The instances, must not be <code>null</code> or empty.
     * @param exceptions The exceptions, may be null.
     */
    public EffectiveInstancesIterator(InstanceIterator instances, InstanceIterator exceptions)
    {
        mInstances = instances;
        mExceptions = exceptions;
        pullNext();
    }


    /**
     * Check if there is at least one more instance to iterate.
     *
     * @return <code>true</code> if the next call to {@link #next()} will return another instance, <code>false</code> otherwise.
     */
    @Override
    public boolean hasNext()
    {
        return mNextInstance < Long.MAX_VALUE;
    }


    /**
     * Get the next instance of this set. Do not call this if {@link #hasNext()} returns <code>false</code>.
     *
     * @return The time in milliseconds since the epoch of the next instance.
     * @throws ArrayIndexOutOfBoundsException if there are no more instances.
     */
    @Override
    public long next()
    {
        if (!hasNext())
        {
            throw new ArrayIndexOutOfBoundsException("no more elements");
        }
        long result = mNextInstance;
        pullNext();
        return result;
    }


    /**
     * Fast-forward to the next instance at or after the given date.
     *
     * @param until The date to fast-forward to in milliseconds since the epoch.
     */
    @Override
    public void fastForward(long until)
    {
        if (mNextInstance < until)
        {
            mInstances.fastForward(until);
            mExceptions.fastForward(until);
            pullNext();
        }
    }


    private void pullNext()
    {
        long next = Long.MAX_VALUE;
        long nextException = mNextException;
        int skipableInstances = MAX_SKIPPED_INSTANCES;
        while (mInstances.hasNext())
        {
            next = mInstances.next();
            while (nextException < next)
            {
                nextException = mExceptions.hasNext() ? mExceptions.next() : Long.MAX_VALUE;
            }
            if (nextException > next)
            {
                break;
            }
            if (--skipableInstances <= 0)
            {
                throw new RuntimeException(String.format(Locale.ENGLISH, "Skipped too many (%d) instances", MAX_SKIPPED_INSTANCES));
            }
            // we've skipped the next instance, this might have been the last one
            next = Long.MAX_VALUE;
        }
        mNextInstance = next;
        mNextException = nextException;
    }
}
