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

import org.dmfs.rfc5545.recur.RecurrenceRule.Freq;


/**
 * The base frequency iterator for recurrence rules. On every call to {@link #next()} or {@link #nextSet()} it returns a new date according to the frequency and
 * interval specified in a recurrence rule.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class FreqIterator extends ByExpander
{
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

	private int mNextYear;
	private int mNextMonth;
	private int mNextDayOfYear;
	private int mNextDayOfMonth;
	private int mNextDayOfWeek;
	private int mNextHour;
	private int mNextMinute;
	private int mNextSecond;


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

		mNextYear = Instance.year(start);
		mNextMonth = Instance.month(start);
		mNextDayOfMonth = Instance.dayOfMonth(start);
		mNextDayOfYear = mCalendarMetrics.getDayOfYear(mNextYear, mNextMonth, mNextDayOfMonth);
		mNextDayOfWeek = mCalendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;
		mNextHour = Instance.hour(start);
		mNextMinute = Instance.minute(start);
		mNextSecond = Instance.second(start);
	}


	@Override
	public long next()
	{
		CalendarMetrics calendarMetrics = mCalendarMetrics;
		long result;
		do
		{
			result = Instance.make(mNextYear, mNextMonth, mNextDayOfMonth, mNextHour, mNextMinute, mNextSecond, mNextDayOfWeek);

			switch (mFreq)
			{
				case YEARLY:
				{
					mNextYear += mInterval;
					break;
				}
				case MONTHLY:
				{
					mNextMonth += mInterval;
					int maxMonths;
					while (mNextMonth >= (maxMonths = calendarMetrics.getMonthsPerYear(mNextYear)))
					{
						mNextMonth -= maxMonths;
						++mNextYear;
					}
					break;
				}
				case WEEKLY:
				{
					mNextDayOfYear += 7 * mInterval;

					int maxDays;
					while (mNextDayOfYear > (maxDays = calendarMetrics.getDaysPerYear(mNextYear)))
					{
						mNextDayOfYear -= maxDays;
						++mNextYear;
					}
					int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
					mNextMonth = CalendarMetrics.month(monthAndDay);
					mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay);

					break;
				}
				case DAILY:
				{
					mNextDayOfYear += mInterval;

					int maxDays;
					while (mNextDayOfYear > (maxDays = calendarMetrics.getDaysPerYear(mNextYear)))
					{
						mNextDayOfYear -= maxDays;
						++mNextYear;
					}
					int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
					mNextMonth = CalendarMetrics.month(monthAndDay);
					mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay);
					mNextDayOfWeek = calendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;
					break;
				}
				case HOURLY:
				{
					mNextHour += mInterval;

					if (mNextHour > 23)
					{
						mNextDayOfYear += mNextHour / 24;
						mNextHour %= 24;
						int maxDays;
						while (mNextDayOfYear > (maxDays = calendarMetrics.getDaysPerYear(mNextYear)))
						{
							mNextDayOfYear -= maxDays;
							++mNextYear;
						}
					}
					int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
					mNextMonth = CalendarMetrics.month(monthAndDay);
					mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay);
					mNextDayOfWeek = calendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;
					break;
				}
				case MINUTELY:
				{
					mNextMinute += mInterval;

					if (mNextMinute > 59)
					{
						mNextDayOfYear += (mNextHour + mNextMinute / 60) / 24 + mNextMinute / (24 * 60);
						mNextHour = (mNextHour + mNextMinute / 60) % 24;
						mNextMinute %= 60;
						int maxDays;
						while (mNextDayOfYear > (maxDays = calendarMetrics.getDaysPerYear(mNextYear)))
						{
							mNextDayOfYear -= maxDays;
							++mNextYear;
						}
					}
					int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
					mNextMonth = CalendarMetrics.month(monthAndDay);
					mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay);
					mNextDayOfWeek = calendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;
					break;
				}
				case SECONDLY:
				{
					mNextSecond += mInterval;

					if (mNextSecond > 59)
					{
						mNextDayOfYear += (mNextHour + (mNextMinute + mNextSecond / 60) / 60) / 24 + mNextSecond / (24 * 60 * 60);
						mNextHour = (mNextHour + (mNextMinute + mNextSecond / 60) / 60) % 24;
						mNextMinute = (mNextMinute + mNextSecond / 60) % 60;
						mNextSecond %= 60;
						int maxDays;
						while (mNextDayOfYear > (maxDays = calendarMetrics.getDaysPerYear(mNextYear)))
						{
							mNextDayOfYear -= maxDays;
							++mNextYear;
						}
					}
					int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
					mNextMonth = CalendarMetrics.month(monthAndDay);
					mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay);
					mNextDayOfWeek = calendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;
					break;
				}

			}
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

}
