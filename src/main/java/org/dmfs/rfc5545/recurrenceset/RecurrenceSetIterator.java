/*
 * Copyright (C) 2013 Marten Gajda <marten@dmfs.org>
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
 *
 */

package org.dmfs.rfc5545.recurrenceset;

import org.dmfs.rfc5545.recurrenceset.AbstractRecurrenceAdapter.InstanceIterator;

import java.util.List;
import java.util.Locale;


/**
 * An iterator for recurrence sets. It takes a number of {@link AbstractRecurrenceAdapter}s for instances and exceptions and iterates all resulting instances
 * (i.e. only the instances, not the exceptions). <p> This class doesn't implement the {@link InstanceIterator} interface for one reasons: </p> <ul> <li>An
 * {@link InstanceIterator} always returns an {@link Object}, so instead of a primitive <code>long</code> we would have to return a {@link Long}. That is an
 * additional object which doesn't have any advantage.</li> </ul>
 *
 * @author Marten Gajda
 */
public class RecurrenceSetIterator
{
    /**
     * Throw if we skipped this many instances in a line, because they were exceptions.
     */
    private final static int MAX_SKIPPED_INSTANCES = 1000;

    private final InstanceIterator mInstances;

    private final InstanceIterator mExceptions;

    private long mIterateEnd = Long.MAX_VALUE;

    private long mNextInstance = Long.MIN_VALUE;

    private long mNextException = Long.MIN_VALUE;


    /**
     * Create a new recurrence iterator for specific lists of instances and exceptions.
     *
     * @param instances
     *         The instances, must not be <code>null</code> or empty.
     * @param exceptions
     *         The exceptions, may be null.
     */
    RecurrenceSetIterator(List<InstanceIterator> instances, List<InstanceIterator> exceptions)
    {
        mInstances = instances.size() == 1 ? instances.get(0) : new CompositeIterator(instances);
        mExceptions = exceptions == null || exceptions.isEmpty() ? new EmptyIterator() :
                exceptions.size() == 1 ? exceptions.get(0) : new CompositeIterator(exceptions);
        pullNext();
    }


    /**
     * Set the iteration end. The iterator will stop if the next instance is after the given date, no matter how many instances are still to come. This needs to
     * be set before you start iterating, otherwise you may get wrong results.
     *
     * @param end
     *         The date at which to stop the iteration in milliseconds since the epoch.
     */
    RecurrenceSetIterator setEnd(long end)
    {
        mIterateEnd = end;
        return this;
    }


    /**
     * Check if there is at least one more instance to iterate.
     *
     * @return <code>true</code> if the next call to {@link #next()} will return another instance, <code>false</code> otherwise.
     */
    public boolean hasNext()
    {
        return mNextInstance < mIterateEnd;
    }


    /**
     * Get the next instance of this set. Do not call this if {@link #hasNext()} returns <code>false</code>.
     *
     * @return The time in milliseconds since the epoch of the next instance.
     *
     * @throws ArrayIndexOutOfBoundsException
     *         if there are no more instances.
     */
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
     * Fast forward to the next instance at or after the given date.
     *
     * @param until
     *         The date to fast forward to in milliseconds since the epoch.
     */
    public void fastForward(long until)
    {
        mInstances.fastForward(until);
        mExceptions.fastForward(until);
        pullNext();
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
        }
        mNextInstance = next;
        mNextException = nextException;
    }
}
