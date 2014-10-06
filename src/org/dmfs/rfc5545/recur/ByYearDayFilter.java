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
 * A filter that limits recurrence rules by day of year.
 * <p>
 * <strong>Note: </strong> <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a> Doesn't allow BYYEARDAY to be used with DAILY, WEEKLY and
 * MONTHLY rules, but RFC 2445 does. This filter tries to return a reasonable result for these cases. In particular that means we expand MONTHLY and WEEKLY
 * rules and filter DAILY rules.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByYearDayFilter extends ByFilter
{
	/**
	 * The year days to let pass.
	 */
	private final int[] mYearDays;


	public ByYearDayFilter(RecurrenceRule rule, CalendarMetrics calendarMetrics)
	{
		super(calendarMetrics);

		mYearDays = StaticUtils.ListToArray(rule.getByPart(Part.BYYEARDAY));
	}


	@Override
	boolean filter(long instance)
	{
		int year = Instance.year(instance);
		int yearDays = mCalendarMetrics.getDaysPerYear(year);
		int dayOfYear = mCalendarMetrics.getDayOfYear(year, Instance.month(instance), Instance.dayOfMonth(instance));
		return StaticUtils.linearSearch(mYearDays, dayOfYear) < 0 && StaticUtils.linearSearch(mYearDays, dayOfYear - yearDays) < 0 || dayOfYear > yearDays;
	}
}
