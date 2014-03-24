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

package org.dmfs.rfc5545.recur.recurrenceset;

import java.util.Arrays;
import java.util.TimeZone;

import org.dmfs.rfc5545.recur.Calendar;


/**
 * A concrete {@link AbstractRecurrenceAdapter} for lists of instances. You can provide the instances in a String or in an array of longs.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class RecurrenceList extends AbstractRecurrenceAdapter
{

	/**
	 * The instances to iterate.
	 */
	private final long[] mInstances;

	/**
	 * The number of instances in {@link #mInstances}.
	 */
	private final int mCount;

	/**
	 * The next instance to iterate.
	 */
	private int mNext = 0;


	/**
	 * Create an adapter for the instances in <code>list</code>.
	 * 
	 * @param list
	 *            A comma separated list if instances using the date-time format is defined in RFC 5545.
	 * @param timeZone
	 *            The time zone to apply to the instances.
	 */
	public RecurrenceList(String list, TimeZone timeZone)
	{
		if (list == null || list.length() == 0)
		{
			mInstances = null;
			mCount = 0;
			return;
		}

		String[] instances = list.split(",");
		mInstances = new long[instances.length];
		int count = 0;

		for (String instanceString : instances)
		{
			Calendar instance = Calendar.parse(timeZone, instanceString);
			mInstances[count] = instance.getTimeInMillis();
			++count;
		}
		mCount = count;
		Arrays.sort(mInstances);
	}


	/**
	 * Create an adapter for the instances in <code>list</code>.
	 * 
	 * @param instances
	 *            An array of instance time stamps in milliseconds.
	 */
	public RecurrenceList(long[] instances)
	{
		mInstances = Arrays.copyOf(instances, instances.length);
		mCount = instances.length;
		Arrays.sort(mInstances);
	}


	@Override
	public boolean hasNext()
	{
		return mNext < mCount;
	}


	@Override
	public long next()
	{
		if (mNext >= mCount)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}
		return mInstances[mNext++];
	}


	@Override
	public long peek()
	{
		if (mNext >= mCount)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}
		return mInstances[mNext];
	}


	@Override
	public void skip(int count)
	{
		mNext += count;
	}

}
