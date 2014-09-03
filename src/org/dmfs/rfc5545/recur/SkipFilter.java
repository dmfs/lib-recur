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
import org.dmfs.rfc5545.recur.RecurrenceRule.Skip;


/**
 * A filter that applies the skip part of a rule.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class SkipFilter extends RuleIterator
{
	/**
	 * Stop iterating (throwing an exception) if this number of empty sets passed in a line, i.e. sets that contain no elements because they have been filtered
	 * or nothing was expanded.
	 */
	private final static int MAX_EMPTY_SETS = 1000;

	/**
	 * Whether we have to filter the results by start date (i.e. remove all instances preceding the start date).
	 */
	private final boolean mFilterStart;

	/**
	 * This indicates that the next instance to return is the first instance.
	 */
	private boolean mFirst = true;

	/**
	 * The first instance (i.e. DTSTART).
	 */
	private final long mStart;

	/**
	 * The last result iterated by {@link #next()}.
	 */
	private long mLastResult;

	private final CalendarMetrics mCalendarMetrics;
	private final Skip mSkip;

	/**
	 * The set we work on. This comes from the previous instance.
	 */
	private LongArray mWorkingSet = null;

	/**
	 * The set we return.
	 */
	private final LongArray mResultSet = new LongArray();


	public SkipFilter(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarMetrics, long start)
	{
		super(previous);
		mCalendarMetrics = calendarMetrics;
		mSkip = rule.getSkip();
		mStart = start;
		mFilterStart = !rule.hasPart(Part.BYSETPOS);
		mLastResult = mFilterStart ? mStart : Long.MIN_VALUE;
	}


	@Override
	public long next()
	{
		LongArray workingSet = mWorkingSet;
		if (workingSet == null || !workingSet.hasNext())
		{
			mWorkingSet = workingSet = nextSet();
		}
		return workingSet.next();
	}


	@Override
	LongArray nextSet()
	{
		LongArray resultSet = mResultSet;
		long last = mLastResult;
		CalendarMetrics calendarMetrics = mCalendarMetrics;

		resultSet.clear();
		if (mFirst && mFilterStart)
		{
			// mStart is always the first result, start is guaranteed to be a valid date that doesn't need to be rolled backwards or forward.
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
			while (prev.hasNext())
			{
				long next = Instance.maskWeekday(prev.next());

				int year = Instance.year(next);
				int month = Instance.month(next);
				int monthDay = Instance.dayOfMonth(next);

				if (!calendarMetrics.validate(next))
				{
					if (calendarMetrics.isLeapDay(month, monthDay))
					{
						if (mSkip == Skip.BACKWARD)
						{
							next = Instance.setDayOfMonth(next, monthDay - 1);
						}
						else
						// mSkip == Skip.FORWARD
						{
							int yearDay = calendarMetrics.getDayOfYear(year, month, monthDay);
							if (yearDay > calendarMetrics.getDaysPerYear(year))
							{
								yearDay -= calendarMetrics.getDaysPerYear(year);
								++year;
							}
							int dayAndMonth = calendarMetrics.getMonthAndDayOfYearDay(year, yearDay);
							next = Instance.setMonthAndDayOfMonth(next, CalendarMetrics.packedMonth(dayAndMonth), CalendarMetrics.dayOfMonth(dayAndMonth));
						}
					}
					else if (calendarMetrics.isLeapMonth(month))
					{
						int yearDay = calendarMetrics.getDayOfYear(year, mSkip == Skip.BACKWARD ? month - 1 : month + 1, monthDay);
						if (yearDay > calendarMetrics.getDaysPerYear(year))
						{
							yearDay -= calendarMetrics.getDaysPerYear(year);
							++year;
						}
						int dayAndMonth = calendarMetrics.getMonthAndDayOfYearDay(year, yearDay);
						next = Instance.setMonthAndDayOfMonth(next, CalendarMetrics.packedMonth(dayAndMonth), CalendarMetrics.dayOfMonth(dayAndMonth));
					}
				}
				resultSet.add(next);
			}
		} while (!resultSet.hasNext());

		// we need to sort, because the element order might have changed
		resultSet.sort();

		mLastResult = last;
		return resultSet;
	}
}
