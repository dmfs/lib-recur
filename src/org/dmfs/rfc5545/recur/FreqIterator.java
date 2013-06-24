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
public class FreqIterator extends RuleIterator
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
	 * The next instance to return.
	 * 
	 * TODO: get rid of it and use {@link CalendarMetrics}.
	 */
	private final Calendar mNextInstance;

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
	private final int mDefaultDayOfMonth;
	private final int mDefaultHour;
	private final int mDefaultMinute;
	private final int mDefaultSecond;
	private final int mDefaultDayOfWeek;


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
		mNextInstance = getIntervalStart(rule, start);
		long defaults = Instance.make(start);
		mCalendarMetrics = calendarTools;

		mNextYear = Instance.year(defaults);
		mNextMonth = Instance.month(defaults);
		mDefaultDayOfMonth = Instance.dayOfMonth(defaults);
		mDefaultDayOfWeek = Instance.dayOfWeek(defaults);
		mDefaultHour = Instance.hour(defaults);
		mDefaultMinute = Instance.minute(defaults);
		mDefaultSecond = Instance.second(defaults);
	}


	@Override
	public long next()
	{
		long result = 0;
		switch (mFreq)
		{
			case YEARLY:
				result = Instance.make(mNextYear, mNextMonth, mDefaultDayOfMonth, mDefaultHour, mDefaultMinute, mDefaultSecond, mDefaultDayOfWeek);
				mNextYear += mInterval;
				break;

			case MONTHLY:
				result = Instance.make(mNextYear, mNextMonth, mDefaultDayOfMonth, mDefaultHour, mDefaultMinute, mDefaultSecond, mDefaultDayOfWeek);
				mNextMonth += mInterval;
				int maxMonths;
				while (mNextMonth >= (maxMonths = mCalendarMetrics.getMonthsPerYear(mNextYear)))
				{
					mNextMonth -= maxMonths;
					++mNextYear;
				}
				break;

			case WEEKLY:
				result = Instance.make(mNextInstance.get(Calendar.YEAR), mNextInstance.get(Calendar.MONTH), mNextInstance.get(Calendar.DAY_OF_MONTH),
					mDefaultHour, mDefaultMinute, mDefaultSecond, mDefaultDayOfWeek);
				mNextInstance.add(Calendar.DAY_OF_YEAR, 7 * mInterval);
				break;

			case DAILY:
				result = Instance.make(mNextInstance.get(Calendar.YEAR), mNextInstance.get(Calendar.MONTH), mNextInstance.get(Calendar.DAY_OF_MONTH),
					mDefaultHour, mDefaultMinute, mDefaultSecond, mDefaultDayOfWeek);
				mNextInstance.add(Calendar.DAY_OF_YEAR, mInterval);
				break;

			case HOURLY:
				result = Instance.make(mNextInstance.get(Calendar.YEAR), mNextInstance.get(Calendar.MONTH), mNextInstance.get(Calendar.DAY_OF_MONTH),
					mNextInstance.get(Calendar.HOUR_OF_DAY), mDefaultMinute, mDefaultSecond, mDefaultDayOfWeek);
				mNextInstance.add(Calendar.HOUR_OF_DAY, mInterval);
				break;

			case MINUTELY:
				result = Instance.make(mNextInstance.get(Calendar.YEAR), mNextInstance.get(Calendar.MONTH), mNextInstance.get(Calendar.DAY_OF_MONTH),
					mNextInstance.get(Calendar.HOUR_OF_DAY), mNextInstance.get(Calendar.MINUTE), mDefaultSecond, mDefaultDayOfWeek);
				mNextInstance.add(Calendar.MINUTE, mInterval);
				break;

			case SECONDLY:
				result = Instance.make(mNextInstance.get(Calendar.YEAR), mNextInstance.get(Calendar.MONTH), mNextInstance.get(Calendar.DAY_OF_MONTH),
					mNextInstance.get(Calendar.HOUR_OF_DAY), mNextInstance.get(Calendar.MINUTE), mNextInstance.get(Calendar.SECOND), mDefaultDayOfWeek);
				mNextInstance.add(Calendar.SECOND, mInterval);
				break;

		}
		return result;
	}


	/**
	 * Return the start of the interval given by the frequency of the rule and the start date.
	 * <p>
	 * The returned {@link Calendar} instance is in UTC time zone to avoid any daylight saving effects.
	 * </p>
	 * 
	 * @param rule
	 *            A {@link RecurrenceRule}.
	 * @param start
	 *            A start date.
	 * @return A new {@link Calendar} that represents the start of the interval.
	 */
	private static Calendar getIntervalStart(RecurrenceRule rule, Calendar start)
	{
		Calendar result = null;
		switch (rule.getFreq())
		{
			case YEARLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), 0, 1, 0, 0, 0);
				break;

			case MONTHLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), 1, 0, 0, 0);
				break;

			case WEEKLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
				break;

			case DAILY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
				break;

			case HOURLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH),
					start.get(Calendar.HOUR), 0, 0);
				break;

			case MINUTELY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH),
					start.get(Calendar.HOUR), start.get(Calendar.MINUTE), 0);
				break;

			case SECONDLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH),
					start.get(Calendar.HOUR), start.get(Calendar.MINUTE), start.get(Calendar.SECOND));
				break;

			default:
				// this should be impossible
				throw new RuntimeException("illegal Freq value: " + rule.getFreq().name());
		}

		// ensure the start of the week is set properly
		result.setFirstDayOfWeek(rule.getWeekStart().toCalendarDay());
		result.setMinimalDaysInFirstWeek(4);
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
