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

package org.dmfs.rfc5545.recur;

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;


/**
 * This filter ensures we don't return any instance that is ahead of start. It also ensures that the start date is always returned as first result and that the
 * result contains no invalid instances.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 */
final class SanityFilter extends RuleIterator
{
	/**
	 * Stop iterating (throwing an exception) if this number of empty sets passed in a line, i.e. sets that contain no elements because they have been filtered
	 * or nothing was expanded.
	 */
	private final static int MAX_EMPTY_SETS = 1000;

	/**
	 * The max number of filtered instances.
	 */
	private final static int MAX_FILTERED_INSTANCES = 1000;

	/**
	 * This indicates that the next instance to return is the first instance.
	 */
	private boolean mFirst = true;

	/**
	 * The first instance (i.e. DTSTART).
	 */
	private final long mStart;

	/**
	 * A {@link LongArray} that contains the instances to return.
	 */
	private final LongArray mResultSet = new LongArray();

	/**
	 * Helper for calendar calculations.
	 */
	private final CalendarMetrics mCalendarMetrics;

	/**
	 * Whether we have to filter the results by start date (i.e. remove all instances preceding the start date).
	 */
	private final boolean mFilterStart;

	/**
	 * The last result iterated by {@link #next()}.
	 */
	private long mLastResult;


	/**
	 * Creates a new {@link SanityFilter} that filters the results of the previous instance. This filter should be located between {@link FreqIterator} or any
	 * BYxxx filters and {@link CountLimiter} or {@link UntilLimiter}.
	 * 
	 * @param previous
	 *            The previous {@link RuleIterator}.
	 * @param start
	 *            The earliest date to let pass.
	 */
	SanityFilter(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
	{
		super(previous);
		mStart = start;
		mCalendarMetrics = calendarTools;
		mFilterStart = !rule.hasPart(Part.BYSETPOS);
		mLastResult = mFilterStart ? mStart : Long.MIN_VALUE;
	}


	@Override
	public long next()
	{
		CalendarMetrics calendarMetrics = mCalendarMetrics;

		if (mFirst && mFilterStart)
		{
			// mStart is always the first result
			mFirst = false;
			return mStart;
		}
		else
		{
			int counter = -1;
			long next;
			long simpleInstance;
			long last = mLastResult;
			do
			{
				if (++counter == MAX_FILTERED_INSTANCES)
				{
					throw new IllegalArgumentException("too many filtered recurrence instances");
				}

				next = mPrevious.next();
				simpleInstance = Instance.maskWeekday(next);

			} while (last >= simpleInstance || !calendarMetrics.validate(simpleInstance));

			mLastResult = simpleInstance;

			return next;
		}
	}


	@Override
	LongArray nextSet()
	{
		LongArray resultSet = mResultSet;
		long last = Long.MIN_VALUE;
		CalendarMetrics calendarMetrics = mCalendarMetrics;

		resultSet.clear();
		if (mFirst && mFilterStart)
		{
			// mStart is always the first result
			mFirst = false;
			last = mStart;
			resultSet.add(last);
		}

		int counter = 0;
		do
		{
			if (counter == MAX_EMPTY_SETS)
			{
				throw new IllegalArgumentException("too many empty recurrence sets");
			}
			counter++;

			LongArray prev = mPrevious.nextSet();
			long simpleInstance = 0;
			while (prev.hasNext())
			{
				long next = prev.next();

				simpleInstance = Instance.maskWeekday(next);

				if ((last < simpleInstance) && calendarMetrics.validate(simpleInstance))
				{
					resultSet.add(next);
					last = simpleInstance;
				}
			}
		} while (!resultSet.hasNext());
		return resultSet;
	}
}
