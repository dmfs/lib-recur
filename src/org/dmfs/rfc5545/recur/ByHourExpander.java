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
 * A filter that expands recurrence rules by hour.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByHourExpander extends ByExpander
{
	/**
	 * The hour list from the rule.
	 */
	private final int[] mHours;


	public ByHourExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
	{
		super(previous, calendarTools, start);
		mHours = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYHOUR));
	}


	@Override
	void expand(long instance, long start)
	{
		// add a new instance for every hour in mHours
		for (int hour : mHours)
		{
			addInstance(Instance.setHour(instance, hour));
		}
	}
}
