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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.dmfs.rfc5545.recurrenceset.AbstractRecurrenceAdapter.InstanceIterator;


/**
 * A recurrence set. A recurrence set consists of all instances defined by a recurrence rule or a list if instances except for exception instances. Exception
 * instances are defined by exceptions rules or lists of exception instances.
 * <p>
 * This class allows you to add any number of recurrence rules, recurrence instances, exception rules and exception instance. It returns an {@link Iterator}
 * that iterates all resulting instances.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class RecurrenceSet
{

	/**
	 * All the instances in the set. Not all of them may be iterated, since instances that are exceptions will be skipped.
	 */
	private final List<AbstractRecurrenceAdapter> mInstances = new ArrayList<AbstractRecurrenceAdapter>();

	/**
	 * All exceptions in the set.
	 */
	private List<AbstractRecurrenceAdapter> mExceptions = null;

	/**
	 * Indicates if the recurrence set is infinite.
	 */
	private boolean mIsInfinite = false;


	/**
	 * Add instances to the set of instances.
	 * 
	 * @param adapter
	 *            An {@link AbstractRecurrenceAdapter} that defines instances.
	 */
	public void addInstances(AbstractRecurrenceAdapter adapter)
	{
		mInstances.add(adapter);

		// the entire set is infinite if there is at least one infinite instance set
		mIsInfinite |= adapter.isInfinite();
	}


	/**
	 * Add exceptions to the set of instances (i.e. effectively remove instances from the instance set).
	 * 
	 * @param adapter
	 *            An {@link AbstractRecurrenceAdapter} that defines instances.
	 */
	public void addExceptions(AbstractRecurrenceAdapter adapter)
	{
		if (mExceptions == null)
		{
			mExceptions = new ArrayList<AbstractRecurrenceAdapter>();
		}
		mExceptions.add(adapter);
	}


	/**
	 * Get an iterator for the specified start time.
	 * 
	 * @param timezone
	 *            The {@link TimeZone} of the first instance.
	 * @param start
	 *            The start time in milliseconds since the epoch.
	 * @return A {@link RecurrenceSetIterator} that iterates all instances.
	 */
	public RecurrenceSetIterator iterator(TimeZone timezone, long start)
	{
		return iterator(timezone, start, Long.MAX_VALUE);
	}


	/**
	 * Return a new {@link RecurrenceSetIterator} for this recurrence set.
	 * 
	 * @param timezone
	 *            The {@link TimeZone} of the first instance.
	 * @param start
	 *            The start time in milliseconds since the epoch.
	 * @param end
	 *            The end of the time range to iterate in milliseconds since the epoch.
	 * @return A {@link RecurrenceSetIterator} that iterates all instances.
	 */
	public RecurrenceSetIterator iterator(TimeZone timezone, long start, long end)
	{
		List<InstanceIterator> instances = new ArrayList<InstanceIterator>(mInstances.size());
		for (AbstractRecurrenceAdapter adapter : mInstances)
		{
			instances.add(adapter.getIterator(timezone, start));
		}

		List<InstanceIterator> exceptions = null;
		if (mExceptions != null)
		{
			exceptions = new ArrayList<InstanceIterator>(mExceptions.size());
			for (AbstractRecurrenceAdapter adapter : mExceptions)
			{
				exceptions.add(adapter.getIterator(timezone, start));
			}
		}
		return new RecurrenceSetIterator(instances, exceptions).setEnd(end);
	}


	/**
	 * Returns whether this {@link RecurrenceSet} contains an infinite number of instances.
	 * 
	 * @return <code>true</code> if the instances in this {@link RecurrenceSet} is infinite, <code>false</code> otherwise.
	 */
	public boolean isInfinite()
	{
		return mIsInfinite;
	}


	public long getLastInstance(TimeZone timezone, long start)
	{
		if (isInfinite())
		{
			throw new IllegalStateException("can not calculate the last instance of an infinite recurrence set");
		}

		if (mExceptions != null && mExceptions.size() > 0)
		{
			/*
			 * This is the difficult case.
			 * 
			 * The last instance of the given rules might be an exception. Unfortunately there doesn't seem to be an easier way to get the very last instance
			 * than by iterating all instances.
			 */
			long last = Long.MIN_VALUE;

			RecurrenceSetIterator iterator = iterator(timezone, start);
			while (iterator.hasNext())
			{
				last = iterator.next();
			}
			return last;
		}

		if (mInstances.size() == 1)
		{
			// simple case, only one set of instances
			return mInstances.get(0).getLastInstance(timezone, start);
		}

		// We have multiple instance sets, but no exceptions. That means we just have to determine the maximum instance over all sets.
		long last = Long.MIN_VALUE;
		for (AbstractRecurrenceAdapter adapter : mInstances)
		{
			long lastOfAdapter = adapter.getLastInstance(timezone, start);
			if (lastOfAdapter > last)
			{
				last = lastOfAdapter;
			}
		}

		return last;
	}
}
