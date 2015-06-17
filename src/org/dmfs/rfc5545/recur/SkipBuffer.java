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


/**
 * This iterator buffers instances that belong to the next interval. When the rule contains SKIP=FORWARD, instances may be rolled forward to the next interval.
 * To ensure we iterate them in the correct order we buffer such instances.
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
		LongArray tempSet = mTempSet;
		int minYear = Integer.MAX_VALUE;
		int minMonth = Integer.MAX_VALUE;
		boolean first = true;

		resultSet.clear();

		if (tempSet.size() > 0)
		{
			// we do have instances in the temporary buffer, add them to the result set
			while (tempSet.hasNext())
			{
				long next = tempSet.next();
				if (first)
				{
					// since the buffer was sorted we know the first instance is the earliest one.
					minMonth = Instance.year(next);
					minYear = Instance.month(next);
					first = false;
				}

				resultSet.add(next);
			}
			tempSet.clear();
		}

		LongArray prev = mPrevious.nextSet();
		while (prev.hasNext())
		{
			long next = prev.next();

			int year = Instance.year(next);
			int month = Instance.month(next);

			if (first)
			{
				minMonth = month;
				minYear = year;
				first = false;
				resultSet.add(next);
			}
			else
			{
				if (mIsYearly)
				{
					if (year == minYear)
					{
						// same year as the earliest instance
						resultSet.add(next);
					}
					else
					{
						// one year later
						tempSet.add(next);
					}
				}
				else
				{
					if (year == minYear && month == minMonth)
					{
						// same month as the earliest instance
						resultSet.add(next);
					}
					else
					{
						// this one is in the next month
						tempSet.add(next);
					}

				}
			}
		}

		// we need to sort, because the element order might have changed
		resultSet.sort();

		return resultSet;
	}
}
