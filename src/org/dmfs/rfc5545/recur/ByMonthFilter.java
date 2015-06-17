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
 * A filter that limits recurrence rules by month.
 * <p>
 * If the rule has a weekly scope (i.e. when FREQ=WEEKLY and any by-day filter is present), this filter allows weeks that overlap the month to pass. This
 * ensures the by day filters can expand all relevant instances. The expanding by-day filter will take care of filtering days not belonging to this month.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByMonthFilter extends ByFilter
{
	/**
	 * The list of months to let pass.
	 */
	private final int[] mMonths;

	/**
	 * Whether to allow weeks that overlap one of the months in {@link #mMonths} to pass. This is important if the rule is weekly and a BY*DAY filter is
	 * present.
	 */
	private final boolean mAllowOverlappingWeeks;


	public ByMonthFilter(RecurrenceRule rule, CalendarMetrics calendarMetrics)
	{
		super(calendarMetrics);
		mMonths = StaticUtils.ListToArray(rule.getByPart(Part.BYMONTH));

		/*
		 * If we expand day-wise in a weekly interval we'll have to keep overlapping weeks, otherwise we may loose instances.
		 * 
		 * The day filters will remove the invalid instances later.
		 */
		mAllowOverlappingWeeks = rule.getFreq() == Freq.WEEKLY && (rule.hasPart(Part.BYDAY) || rule.hasPart(Part.BYMONTHDAY) || rule.hasPart(Part.BYYEARDAY));
	}


	@Override
	boolean filter(long instance)
	{
		final int month = Instance.month(instance);
		if (!mAllowOverlappingWeeks)
		{
			return StaticUtils.linearSearch(mMonths, month) < 0;
		}
		else
		{
			int[] months = mMonths;
			CalendarMetrics calendarMetrics = mCalendarMetrics;

			if (StaticUtils.linearSearch(months, month) >= 0)
			{
				return false;
			}

			long startOfWeek = calendarMetrics.startOfWeek(instance);

			// check if the month of the week start is in mMonths
			if (StaticUtils.linearSearch(months, Instance.month(startOfWeek)) >= 0)
			{
				return false;
			}

			long endOfWeek = calendarMetrics.nextDay(startOfWeek, 6);

			// check if the month of the week end is in mMonths
			return StaticUtils.linearSearch(months, Instance.month(endOfWeek)) < 0;
		}
	}
}
