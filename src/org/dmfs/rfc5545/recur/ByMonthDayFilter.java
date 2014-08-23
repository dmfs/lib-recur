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
 * A filter that limits recurrence rules by day of month.
 * <p>
 * Even though RFC 5545 doesn't explicitly say it, we filter YEARLY, MONTHLY or WEEKLY rules if BYYEARDAY has been specified. BYYEARDAY is evaluated prior to
 * BYMONTHDAY and there is no point in expanding these days since they already are expanded.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByMonthDayFilter extends ByFilter
{
	/**
	 * A list of days of month to let pass.
	 */
	private final int[] mMonthDays;


	public ByMonthDayFilter(RecurrenceRule rule, CalendarMetrics calendarMetrics)
	{
		super(calendarMetrics);

		// get a list of the month days
		mMonthDays = StaticUtils.ListToArray(rule.getByPart(Part.BYMONTHDAY));

	}


	@Override
	boolean filter(long instance)
	{
		int monthDays = mCalendarMetrics.getDaysPerPackedMonth(Instance.year(instance), Instance.month(instance));
		int dayOfMonth = Instance.dayOfMonth(instance);
		return (StaticUtils.linearSearch(mMonthDays, dayOfMonth) < 0 && StaticUtils.linearSearch(mMonthDays, dayOfMonth - 1 - monthDays) < 0)
			|| dayOfMonth > monthDays;
	}

}