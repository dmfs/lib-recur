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
 * A filter that limits recurrence rules by seconds.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
class BySecondFilter extends ByFilter
{
	/**
	 * The list of minutes from the recurrence rule.
	 */
	private final int[] mSeconds;


	public BySecondFilter(RecurrenceRule rule, CalendarMetrics calendarTools)
	{
		super(calendarTools);
		mSeconds = StaticUtils.ListToArray(rule.getByPart(Part.BYSECOND));
	}


	@Override
	boolean filter(long instance)
	{
		// filter all minutes not in the list
		return StaticUtils.linearSearch(mSeconds, Instance.second(instance)) < 0;
	}

}
