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


/**
 * The base frequency iterator for recurrence rules. On every call to {@link #next()} or {@link #nextSet()} it returns a new date according to the frequency and
 * interval specified in a recurrence rule.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class FreqIterator extends ByExpander
{
	/**
	 * Stop iterating (throwing an exception) if this number of empty sets passed in a line, i.e. sets that contain no elements because they have been filtered.
	 */
	private final static int MAX_EMPTY_SETS = 1000;

	/**
	 * The base frequency of the rule.
	 */
	private final Freq mFreq;

	/**
	 * The interval of the rule.
	 */
	private final int mInterval;

	/**
	 * A {@link LongArray} to hold the instances of the current interval.
	 */
	private final LongArray mResultSet = new LongArray(1);

	/**
	 * A helper to perform calendar calculations.
	 */
	private final CalendarMetrics mCalendarMetrics;

	/**
	 * The next instance to iterate.
	 */
	private long mNextInstance;


	/**
	 * Create a new FreqIterator for the given rule and start date.
	 * 
	 * @param rule
	 *            The rule to iterate.
	 * @param start
	 *            The first instance to iterate.
	 */
	public FreqIterator(RecurrenceRule rule, CalendarMetrics calendarMetrics, long start)
	{
		super(null, calendarMetrics, start);
		mFreq = rule.getFreq();
		mInterval = rule.getInterval();
		mCalendarMetrics = calendarMetrics;

		int year = Instance.year(start);
		int month = Instance.month(start);
		int dayOfMonth = Instance.dayOfMonth(start);
		int dayOfYear = mCalendarMetrics.getDayOfYear(year, month, dayOfMonth);

		// don't rely on the day of week field being set properly
		mNextInstance = Instance.setDayOfWeek(start, mCalendarMetrics.getDayOfWeek(year, dayOfYear));
	}


	@Override
	public long next()
	{
		final CalendarMetrics calendarMetrics = mCalendarMetrics;

		long result;
		int errorCountdown = MAX_EMPTY_SETS;
		do
		{
			// ensure we're not trapped in an infinite loop
			if (--errorCountdown < 0)
			{
				throw new IllegalArgumentException("too many empty recurrence sets");
			}

			result = mNextInstance;
			mNextInstance = mFreq.next(calendarMetrics, result, mInterval);
		} while (mFilterCount > 0 && filter(result));

		return result;
	}


	@Override
	LongArray nextSet()
	{
		mResultSet.clear();
		mResultSet.add(next());
		return mResultSet;
	}


	@Override
	void expand(long instance, long start)
	{
		// we don't need that.
	}


	@Override
	void fastForward(long untilInstance)
	{
		mNextInstance = mFreq.next(mCalendarMetrics, mNextInstance, mInterval, untilInstance);
	}
}
