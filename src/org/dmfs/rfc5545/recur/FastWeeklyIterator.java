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

import java.util.List;

import org.dmfs.rfc5545.recur.RecurrenceRule.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;
import org.dmfs.rfc5545.recur.RecurrenceRule.WeekdayNum;


/**
 * Fast path for once a week type rules (i.e. instances recur once a week on the same weekday).
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class FastWeeklyIterator extends ByExpander
{
	/**
	 * The interval of the rule.
	 */
	private final int mInterval;

	/**
	 * A {@link LongArray} to hold the instances of the current interval.
	 */
	private final LongArray mResultSet = new LongArray(1);

	private final long mStart;

	/**
	 * The next instance to iterate.
	 */
	private long mNextInstance;

	private int mYear;
	private int mYearDay;
	private final int mMax;
	private int mCount;


	/**
	 * Create a new WeeklyTypeIterator for the given rule and start date.
	 * 
	 * @param rule
	 *            The rule to iterate.
	 * @param start
	 *            The first instance to iterate.
	 */
	public FastWeeklyIterator(RecurrenceRule rule, CalendarMetrics calendarMetrics, long start, long firstInstance)
	{
		super(null, calendarMetrics, firstInstance);

		mInterval = rule.getInterval();

		mStart = start;
		mNextInstance = firstInstance;

		mYear = Instance.year(firstInstance);
		mYearDay = calendarMetrics.getDayOfYear(mYear, Instance.month(firstInstance), Instance.dayOfMonth(firstInstance));

		Integer max = rule.getCount();
		mMax = max == null ? -1 : max;
	}


	public static FastWeeklyIterator getInstance(RecurrenceRule rule, CalendarMetrics calendarMetrics, long start)
	{
		Freq freq = rule.getFreq();
		if (freq != Freq.WEEKLY || rule.hasPart(Part.BYMONTH) || rule.hasPart(Part.BYYEARDAY) || rule.hasPart(Part.BYMONTHDAY) || rule.hasPart(Part.BYWEEKNO)
			|| rule.hasPart(Part.BYHOUR) || rule.hasPart(Part.BYMINUTE) || rule.hasPart(Part.BYSECOND) || rule.hasPart(Part.BYSETPOS))
		{
			return null;
		}

		List<WeekdayNum> weekdays = rule.getByDayPart();

		if (weekdays == null || weekdays.size() == 1)
		{

			long instance = start;
			if (weekdays != null)
			{
				int weekday = weekdays.get(0).weekday.ordinal();

				// calculate the next instance of weekday after start

				int year = Instance.year(instance);
				int yearDay = calendarMetrics.getDayOfYear(year, Instance.month(instance), Instance.dayOfMonth(instance));
				int currentWeekDay = calendarMetrics.getDayOfWeek(year, yearDay);

				if (currentWeekDay != weekday)
				{
					yearDay += (weekday - currentWeekDay + 7) % 7;

					int daysPerYear = calendarMetrics.getDaysPerYear(Instance.year(instance));

					if (yearDay > daysPerYear)
					{
						year++;
						yearDay -= daysPerYear;
					}

					int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, yearDay);

					instance = Instance.setMonthAndDayOfMonth(Instance.setYear(instance, year), CalendarMetrics.month(monthAndDay),
						CalendarMetrics.dayOfMonth(monthAndDay));
				}
			}

			return new FastWeeklyIterator(rule, calendarMetrics, start, instance);
		}
		return null;
	}


	@Override
	public long next()
	{
		if (mCount++ == 0 && mStart != mNextInstance)
		{
			return mStart;
		}

		long result = mNextInstance;

		int daysPerYear = mCalendarMetrics.getDaysPerYear(mYear);

		if ((mYearDay = (mYearDay + 7 * mInterval)) > daysPerYear)
		{
			mYear++;
			mYearDay -= daysPerYear;
		}

		int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(mYear, mYearDay);

		mNextInstance = Instance.setMonthAndDayOfMonth(Instance.setYear(mNextInstance, mYear), CalendarMetrics.month(monthAndDay),
			CalendarMetrics.dayOfMonth(monthAndDay));

		if (mMax > 0 && mCount > mMax)
		{
			return Long.MIN_VALUE;
		}
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

}
