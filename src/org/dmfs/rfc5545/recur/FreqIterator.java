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
 * <p>
 * TODO: Apply all filters up to the first expansion.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class FreqIterator extends RuleIterator
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
	public FreqIterator(RecurrenceRule rule, CalendarMetrics calendarTools, Calendar start)
	{
		super(null);
		mFreq = rule.getFreq();
		mInterval = rule.getInterval();
		mCalendarMetrics = calendarTools;

		mNextYear = start.get(Calendar.YEAR);
		mNextMonth = start.get(Calendar.MONTH);
		mNextDayOfMonth = start.get(Calendar.DAY_OF_MONTH);
		mNextDayOfYear = mCalendarMetrics.getDayOfYear(mNextYear, mNextMonth, mNextDayOfMonth);
		mNextDayOfWeek = mCalendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;
		mNextHour = start.get(Calendar.HOUR_OF_DAY);
		mNextMinute = start.get(Calendar.MINUTE);
		mNextSecond = start.get(Calendar.SECOND);
	}


	@Override
	public long next()
	{
		long result = Instance.make(mNextYear, mNextMonth, mNextDayOfMonth, mNextHour, mNextMinute, mNextSecond, mNextDayOfWeek);

		switch (mFreq)
		{
			case YEARLY:
				mNextYear += mInterval;
				break;

			case MONTHLY:
				mNextMonth += mInterval;
				int maxMonths;
				while (mNextMonth >= (maxMonths = mCalendarMetrics.getMonthsPerYear(mNextYear)))
				{
					mNextMonth -= maxMonths;
					++mNextYear;
				}
				break;

			case WEEKLY:
				mNextDayOfYear += 7 * mInterval;

				int maxDays;
				while (mNextDayOfYear > (maxDays = mCalendarMetrics.getDaysPerYear(mNextYear)))
				{
					mNextDayOfYear -= maxDays;
					++mNextYear;
				}
				int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
				mNextMonth = CalendarMetrics.month(monthAndDay);
				mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay);

				break;

			case DAILY:
				mNextDayOfYear += mInterval;

				int maxDays2;
				while (mNextDayOfYear > (maxDays2 = mCalendarMetrics.getDaysPerYear(mNextYear)))
				{
					mNextDayOfYear -= maxDays2;
					++mNextYear;
				}
				int monthAndDay2 = mCalendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
				mNextMonth = CalendarMetrics.month(monthAndDay2);
				mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay2);
				mNextDayOfWeek = mCalendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;
				break;

			case HOURLY:
				mNextHour += mInterval;

				if (mNextHour > 23)
				{
					mNextDayOfYear += mNextHour / 24;
					mNextHour %= 24;
					int maxDays3;
					while (mNextDayOfYear > (maxDays3 = mCalendarMetrics.getDaysPerYear(mNextYear)))
					{
						mNextDayOfYear -= maxDays3;
						++mNextYear;
					}
				}
				int monthAndDay3 = mCalendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
				mNextMonth = CalendarMetrics.month(monthAndDay3);
				mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay3);
				mNextDayOfWeek = mCalendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;

				break;

			case MINUTELY:
				mNextMinute += mInterval;

				if (mNextMinute > 59)
				{
					mNextDayOfYear += mNextMinute / (24 * 60);
					mNextHour += (mNextMinute / 60) % 24;
					mNextMinute %= 60;
					int maxDays3;
					while (mNextDayOfYear > (maxDays3 = mCalendarMetrics.getDaysPerYear(mNextYear)))
					{
						mNextDayOfYear -= maxDays3;
						++mNextYear;
					}
				}
				int monthAndDay4 = mCalendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
				mNextMonth = CalendarMetrics.month(monthAndDay4);
				mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay4);
				mNextDayOfWeek = mCalendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;
				break;

			case SECONDLY:
				mNextSecond += mInterval;

				if (mNextSecond > 59)
				{
					mNextDayOfYear += mNextSecond / (24 * 60 * 60);
					mNextHour += (mNextSecond / (60 * 60)) % 24;
					mNextMinute += (mNextSecond / 60) % 24;
					mNextSecond %= 60;
					int maxDays3;
					while (mNextDayOfYear > (maxDays3 = mCalendarMetrics.getDaysPerYear(mNextYear)))
					{
						mNextDayOfYear -= maxDays3;
						++mNextYear;
					}
				}
				int monthAndDay5 = mCalendarMetrics.getMonthAndDayOfYearDay(mNextYear, mNextDayOfYear);
				mNextMonth = CalendarMetrics.month(monthAndDay5);
				mNextDayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay5);
				mNextDayOfWeek = mCalendarMetrics.getDayOfWeek(mNextYear, mNextDayOfYear) + 1;
				break;

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

}
