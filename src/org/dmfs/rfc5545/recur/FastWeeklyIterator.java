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

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;
import org.dmfs.rfc5545.recur.RecurrenceRule.Skip;
import org.dmfs.rfc5545.recur.RecurrenceRule.WeekdayNum;


/**
 * Fast path for once a week type rules (i.e. instances recur once a week on the same weekday). You don't need to add a {@link SanityFilter}, since this method
 * won't iterate invalid dates and takes care of adding unsynchronized start dates.
 * <p>
 * This class also stops iterating after the right number of instances if a <code>COUNT</code> part is present, but you still need to add an
 * {@link UntilLimiter} if an <code>UNTIL</code> part is present.
 * </p>
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

	/**
	 * The first instance to iterate.
	 */
	private final long mStart;

	/**
	 * The next instance to iterate.
	 */
	private long mNextInstance;

	/**
	 * The current year.
	 */
	private int mYear;

	/**
	 * The current yearday.
	 */
	private int mYearDay;

	/**
	 * The maximum number of instances to iterate.
	 */
	private final int mInstanceLimit;

	/**
	 * The number of instances already iterated.
	 */
	private int mCount;


	/**
	 * Create a new WeeklyTypeIterator for the given rule, start date and first instance.
	 * 
	 * @param rule
	 *            The rule to iterate.
	 * @param calendarMetrics
	 *            The {@link CalendarMetrics} to use.
	 * @param start
	 *            The first instance to iterate.
	 * @param firstInstance
	 *            The first instance of the rule. If start is not synchronized with the rule, this is the first instance after start. It equals start otherwise.
	 */
	private FastWeeklyIterator(RecurrenceRule rule, CalendarMetrics calendarMetrics, long start, long firstInstance)
	{
		super(null, calendarMetrics, firstInstance);

		mInterval = rule.getInterval();

		mStart = start;
		mNextInstance = firstInstance;

		mYear = Instance.year(firstInstance);
		mYearDay = calendarMetrics.getDayOfYear(mYear, Instance.month(firstInstance), Instance.dayOfMonth(firstInstance));

		Integer max = rule.getCount();
		mInstanceLimit = max == null ? -1 : max;
	}


	/**
	 * Get an instance of a {@link FastWeeklyIterator} for the given rule.
	 * 
	 * @param rule
	 *            The {@link RecurrenceRule} to iterate.
	 * @param calendarMetrics
	 *            The {@link CalendarMetrics} to use.
	 * @param start
	 *            The first instance.
	 * @return A {@link FastBirthdayIterator} instance or <code>null</code> if the rule is not suitable for this kind of optimization.
	 */
	public static FastWeeklyIterator getInstance(RecurrenceRule rule, CalendarMetrics calendarMetrics, long start)
	{
		Freq freq = rule.getFreq();
		if (freq != Freq.WEEKLY || rule.hasPart(Part.BYMONTH) || rule.hasPart(Part.BYYEARDAY) || rule.hasPart(Part.BYMONTHDAY) || rule.hasPart(Part.BYWEEKNO)
			|| rule.hasPart(Part.BYHOUR) || rule.hasPart(Part.BYMINUTE) || rule.hasPart(Part.BYSECOND) || rule.hasPart(Part.BYSETPOS)
			|| rule.getSkip() != Skip.OMIT)
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
				int year = Instance.year(instance);
				int yearDay = calendarMetrics.getDayOfYear(year, Instance.month(instance), Instance.dayOfMonth(instance));
				int currentWeekDay = calendarMetrics.getDayOfWeek(year, yearDay);

				if (currentWeekDay != weekday)
				{
					// start is not synchronized with the rule, calculate the first instance of the actual rule
					yearDay += (weekday - currentWeekDay + 7) % 7;

					int daysPerYear = calendarMetrics.getDaysPerYear(Instance.year(instance));

					if (yearDay > daysPerYear)
					{
						year++;
						yearDay -= daysPerYear;
					}

					int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, yearDay);

					instance = Instance.setMonthAndDayOfMonth(Instance.setYear(instance, year), CalendarMetrics.packedMonth(monthAndDay),
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
			// be sure we iterate start first
			return mStart;
		}

		if (mInstanceLimit > 0 && mCount > mInstanceLimit)
		{
			return mNextInstance = Long.MIN_VALUE;
		}

		long result = mNextInstance;

		int daysPerYear = mCalendarMetrics.getDaysPerYear(mYear);

		mYearDay += 7 * mInterval;

		while (mYearDay > daysPerYear)
		{
			// roll over to next year
			mYear++;
			mYearDay -= daysPerYear;
			daysPerYear = mCalendarMetrics.getDaysPerYear(mYear);
		}

		int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(mYear, mYearDay);

		mNextInstance = Instance.setMonthAndDayOfMonth(Instance.setYear(mNextInstance, mYear), CalendarMetrics.packedMonth(monthAndDay),
			CalendarMetrics.dayOfMonth(monthAndDay));

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
		int untilYear = Instance.year(untilInstance);
		int untilMonth = Instance.month(untilInstance);
		int monthsOfPrevYear = mCalendarMetrics.getMonthsPerYear(untilYear - 1);
		int nextMonth = Instance.month(mNextInstance);

		// FIXME: make sure that untilMonth = 0 works with leap months or change it accordingly.
		/* we have to ensure we iterate the correct week, so we just stop one month before */
		while ((mYear < untilYear - 1 || mYear == untilYear - 1 && untilMonth == 0 && nextMonth < monthsOfPrevYear - 1 || mYear == untilYear
			&& nextMonth < untilMonth)
			&& mNextInstance > Long.MIN_VALUE)
		{
			nextMonth = Instance.month(next());
		}
	}
}
