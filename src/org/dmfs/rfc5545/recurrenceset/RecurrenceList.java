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

import java.util.Arrays;
import java.util.TimeZone;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;


/**
 * A concrete {@link AbstractRecurrenceAdapter} for lists of instances. You can provide the instances in a String or in an array of longs.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class RecurrenceList extends AbstractRecurrenceAdapter
{
	class InstanceIterator implements AbstractRecurrenceAdapter.InstanceIterator
	{
		private int mNext;


		public InstanceIterator(long start)
		{
			fastForward(start);
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


		@Override
		public void fastForward(long until)
		{
			int count = mCount;
			int next = mNext;
			long[] instances = mInstances;
			while (next < count && instances[next] < until)
			{
				++next;
			}
			mNext = next;
		}
	}

	/**
	 * The instances to iterate.
	 */
	private final long[] mInstances;

	/**
	 * The number of instances in {@link #mInstances}.
	 */
	private final int mCount;


	/**
	 * Create an adapter for the instances in <code>list</code>.
	 * 
	 * @param list
	 *            A comma separated list of instances using the date-time format as defined in RFC 5545.
	 * @param timeZone
	 *            The time zone to apply to the instances.
	 */
	public RecurrenceList(String list, TimeZone timeZone)
	{
		this(DateTime.GREGORIAN_CALENDAR_SCALE, list, timeZone);
	}


	/**
	 * Create an adapter for the instances in <code>list</code>.
	 * 
	 * @param calendarMetrics
	 *            The calendar scale to use.
	 * @param list
	 *            A comma separated list of instances using the date-time format as defined in RFC 5545.
	 * @param timeZone
	 *            The time zone to apply to the instances.
	 */
	public RecurrenceList(CalendarMetrics calendarMetrics, String list, TimeZone timeZone)
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
			DateTime instance = DateTime.parse(calendarMetrics, timeZone, instanceString);
			mInstances[count] = instance.getTimestamp();
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
		mInstances = new long[instances.length];
		System.arraycopy(instances, 0, mInstances, 0, instances.length);
		mCount = instances.length;
		Arrays.sort(mInstances);
	}


	@Override
	InstanceIterator getIterator(TimeZone timezone, long start)
	{
		return new InstanceIterator(start);
	}


	@Override
	boolean isInfinite()
	{
		// the given lists are always finite
		return false;
	}


	@Override
	long getLastInstance(TimeZone timezone, long start)
	{
		long[] instances = mInstances;
		return instances[instances.length - 1];
	}
}
