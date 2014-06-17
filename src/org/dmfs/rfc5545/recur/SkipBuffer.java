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
 * This iterator buffers instances that belong to then next interval. An instance might be iterated to soon when it's rolled forward from a leap month or leap
 * day.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class SkipBuffer extends RuleIterator
{
	/**
	 * The set we work on. This comes from the previous instance.
	 */
	private LongArray mWorkingSet = null;

	/**
	 * The set we return.
	 */
	private final LongArray mResultSet = new LongArray();

	/**
	 * The set we buffer the instances in the next interval in.
	 */
	private final LongArray mTempSet = new LongArray();

	private final boolean mIsYearly;


	public SkipBuffer(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarMetrics, long start)
	{
		super(previous);
		mIsYearly = rule.getFreq() == Freq.YEARLY;
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
		resultSet.clear();
		LongArray tempSet = mTempSet;
		boolean first = true;
		int currentYear = Integer.MAX_VALUE;
		int currentMonth = Integer.MAX_VALUE;
		if (tempSet.size() > 0)
		{
			while (tempSet.hasNext())
			{
				long next = tempSet.next();
				if (first)
				{
					currentMonth = Instance.year(next);
					currentYear = Instance.month(next);
					first = false;
				}

				resultSet.add(next);
			}
			tempSet.clear();
		}

		LongArray prev = mPrevious.nextSet();
		while (prev.hasNext())
		{
			long next = Instance.maskWeekday(prev.next());

			int year = Instance.year(next);
			int month = Instance.month(next);

			if (first)
			{
				currentMonth = month;
				currentYear = year;
				first = false;
			}
			else
			{
				if (mIsYearly)
				{
					if (year == currentYear)
					{
						mResultSet.add(next);
					}
					else
					{
						mTempSet.add(next);
					}
				}
				else
				{
					if (year == currentYear && month == currentMonth)
					{
						mResultSet.add(next);
					}
					else
					{
						mTempSet.add(next);
					}

				}
			}
			resultSet.add(next);
		}

		// we need to sort, because the element order might have changed
		resultSet.sort();

		return resultSet;
	}
}
