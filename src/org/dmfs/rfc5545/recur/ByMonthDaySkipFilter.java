/*
 * Copyright (C) 2015 Marten Gajda <marten@dmfs.org>
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
import org.dmfs.rfc5545.recur.RecurrenceRule.Skip;


/**
 * A filter that ensures invalid day of month numbers are skipped.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByMonthDaySkipFilter extends RuleIterator
{
	/**
	 * Stop iterating (throwing an exception) if this number of empty sets passed in a line, i.e. sets that contain no elements because they have been filtered
	 * or nothing was expanded.
	 */
	private final static int MAX_EMPTY_SETS = 1000;

	private final CalendarMetrics mCalendarMetrics;
	private final Skip mSkip;

	/**
	 * The set we work on. This comes from the previous instance.
	 */
	private LongArray mWorkingSet = null;

	/**
	 * The set we return.
	 */
	private final LongArray mResultSet = new LongArray();


	public ByMonthDaySkipFilter(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarMetrics, long start)
	{
		super(previous);
		mCalendarMetrics = calendarMetrics;
		mSkip = rule.getSkip();
	}


	@Override
	public long next()
	{
		LongArray workingSet = mWorkingSet;
		if (workingSet == null || !workingSet.hasNext())
		{
			mWorkingSet = workingSet = nextSet();
		}
		return workingSet.next();
	}


	@Override
	LongArray nextSet()
	{
		LongArray resultSet = mResultSet;
		CalendarMetrics calendarMetrics = mCalendarMetrics;

		int counter = 0;
		do
		{
			if (counter == MAX_EMPTY_SETS)
			{
				throw new IllegalArgumentException("too many empty recurrence sets");
			}
			counter++;

			LongArray prev = mPrevious.nextSet();
			while (prev.hasNext())
			{
				long next = Instance.maskWeekday(prev.next());

				if (!calendarMetrics.validate(next))
				{
					if (mSkip == Skip.BACKWARD)
					{
						next = calendarMetrics.prevDay(next);
					}
					else
					// mSkip == Skip.FORWARD
					{
						next = calendarMetrics.nextDay(next);
					}
				}
				resultSet.add(next);
			}
		} while (!resultSet.hasNext());

		return resultSet;
	}
}
