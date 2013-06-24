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
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;


/**
 * A filter that limits or expands recurrence rules by month. Months are expanded for yearly rules only.
 * <p>
 * If the rule has a weekly scope (i.e. when FREQ=WEEKLY and any by-day filter is present), this filter allows weeks that overlap the month to pass. This
 * ensures the by day filters can expand all relevant instances. The expanding by-day filter will take care of filtering days not belonging to this month.
 * </p>
 * 
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByMonthFilter extends ByFilter
{
	/**
	 * The list of months to let pass or to expand.
	 */
	private final int[] mMonths;

	/**
	 * Whether to allow weeks that overlap one of the months in {@link #mMonths} to pass. This is important if the rule is weekly and a BY*DAY filter is
	 * present.
	 */
	private final boolean mAllowOverlappingWeeks;

	/**
	 * The start of the week as defined by the rule.
	 */
	private final int mWeekStart;

	/**
	 * A helper for calendar calculations.
	 */
	private final Calendar mHelper;


	public ByMonthFilter(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
	{
		super(previous, calendarTools, start, rule.getFreq() == Freq.YEARLY);
		mMonths = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYMONTH));

		/*
		 * If we expand day-wise in a weekly interval we'll have to keep overlapping weeks, otherwise we may loose instances.
		 * 
		 * The day filters will remove the invalid instances later.
		 */
		mAllowOverlappingWeeks = rule.getFreq() == Freq.WEEKLY && (rule.hasPart(Part.BYDAY) || rule.hasPart(Part.BYMONTHDAY) || rule.hasPart(Part.BYYEARDAY));

		if (mAllowOverlappingWeeks)
		{
			mWeekStart = rule.getWeekStart().toCalendarDay();

			mHelper = new Calendar(Calendar.UTC, 2000, 0, 1, 0, 0, 0);
			mHelper.setMinimalDaysInFirstWeek(4);
			mHelper.setFirstDayOfWeek(mWeekStart);
		}
		else
		{
			mWeekStart = 0;
			mHelper = null;
		}
	}


	@Override
	boolean filter(long instance)
	{
		final int month = Instance.month(instance);
		if (!mAllowOverlappingWeeks)
		{
			return StaticUtils.linearSearch(mMonths, month + 1) < 0;
		}
		else
		{
			if (StaticUtils.linearSearch(mMonths, month + 1) >= 0)
			{
				return false;
			}
			int year = Instance.year(instance);
			int dayOfMonth = Instance.dayOfMonth(instance);
			int hour = Instance.hour(instance);
			int minute = Instance.minute(instance);
			int second = Instance.second(instance);

			mHelper.set(year, month, dayOfMonth, hour, minute, second);
			// force calculation of the WEEK_OF_YEAR and DAY_OF_WEEK fields.
			mHelper.get(Calendar.WEEK_OF_YEAR);

			/*
			 * Check if the current week overlaps any of the months in mMonths, i.e. if the month of the end or the start of the week is in mMonths.
			 */
			mHelper.set(Calendar.DAY_OF_WEEK, mWeekStart); // set to first day of the week
			if (StaticUtils.linearSearch(mMonths, mHelper.get(Calendar.MONTH) + 1) >= 0)
			{
				return false;
			}

			mHelper.add(Calendar.DAY_OF_MONTH, 6); // set to last day of the week
			return StaticUtils.linearSearch(mMonths, mHelper.get(Calendar.MONTH) + 1) < 0;
		}
	}


	@Override
	void expand(LongArray set, long instance, long start)
	{
		for (int month : mMonths)
		{
			long newInstance = Instance.setMonth(instance, month - 1);
			if (newInstance < start)
			{
				// instance is before start, nothing to do here
				continue;
			}

			set.add(newInstance);
		}
	}
}
