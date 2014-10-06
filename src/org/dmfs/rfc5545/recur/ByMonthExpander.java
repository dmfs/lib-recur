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
 * A filter that expands recurrence rules by month. Months are expanded for yearly rules only.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByMonthExpander extends ByExpander
{
	/**
	 * The list of months to expand.
	 */
	private final int[] mMonths;


	public ByMonthExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
	{
		super(previous, calendarTools, start);
		// sort array to iterate the months in the right order, that saves an additional sorting of each interval
		mMonths = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYMONTH));
	}


	@Override
	void expand(long instance, long start)
	{
		for (int month : mMonths)
		{
			long newInstance = Instance.setMonth(instance, month);
			if (newInstance < start)
			{
				// instance is before start, nothing to do here
				continue;
			}

			addInstance(newInstance);
		}
	}
}
