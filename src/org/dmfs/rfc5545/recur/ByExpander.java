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
 * An abstract by-part expander.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
abstract class ByExpander extends RuleIterator
{

	/**
	 * Stop iterating (throwing an exception) if this number of empty sets passed in a line, i.e. sets that contain no elements because they have been filtered
	 * or nothing was expanded.
	 */
	private final static int MAX_EMPTY_SETS = 1000;

	/**
	 * The scope of a part. Depending on the frequency and preceding filters some filters operate within a specific scope.
	 */
	protected enum Scope
	{
		WEEKLY, MONTHLY, WEEKLY_AND_MONTHLY, YEARLY;
	}

	/**
	 * The first instance to iterate.
	 */
	private final long mStart;

	/**
	 * The set we work on. This comes from the previous instance.
	 */
	private LongArray mWorkingSet = null;

	/**
	 * The set we return.
	 */
	private final LongArray mResultSet = new LongArray();

	/**
	 * The {@link CalendarMetrics} to use.
	 */
	final CalendarMetrics mCalendarMetrics;

	/**
	 * The filters to apply after an expansion. We never have more than 8 filters.
	 */
	private final ByFilter[] mFilters = new ByFilter[8];

	/**
	 * The number of {@link ByFilter}s in {@link #mFilters}.
	 */
	int mFilterCount = 0;


	/**
	 * Create a new expander that expands the instances returned by the previous {@link RuleIterator}.
	 * 
	 * @param previous
	 *            The preceding {@link RuleIterator}.
	 * @param calendarTools
	 *            The {@link CalendarMetrics} to use.
	 * @param start
	 *            The first instance.
	 */
	public ByExpander(RuleIterator previous, CalendarMetrics calendarTools, long start)
	{
		super(previous);
		mStart = start;
		mCalendarMetrics = calendarTools;
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
		final LongArray resultSet = mResultSet;
		final RuleIterator previous = mPrevious;
		final long start = mStart;
		resultSet.clear();

		int counter = 0;
		do
		{
			if (counter == MAX_EMPTY_SETS)
			{
				throw new IllegalArgumentException("too many empty recurrence sets " + this);
			}
			counter++;

			LongArray prev = previous.nextSet();
			while (prev.hasNext())
			{
				expand(prev.next(), start);
			}
		} while (!resultSet.hasNext());

		resultSet.sort();

		return resultSet;
	}


	/**
	 * Add a filter to this expander. A filter is applied before an instance is added to the resulting set.
	 * 
	 * @param filter
	 *            The {@link ByFilter} to add.
	 */
	final void addFilter(ByFilter filter)
	{
		mFilters[mFilterCount++] = filter;
	}


	/**
	 * Add an instance to the result set. The instance is not added if it's filtered.
	 * 
	 * @param instance
	 *            The instance to add.
	 */
	final void addInstance(long instance)
	{
		if (mFilterCount == 0 || !filter(instance))
		{
			mResultSet.add(instance);
		}
	}


	/**
	 * Filter an instance. This method determines if a given {@link Instance} should be removed from the result set or not.
	 * 
	 * @param instance
	 *            The instance to filter.
	 * @return <code>true</code> to remove the instance from the result set, <code>false</code> to include it.
	 */
	final boolean filter(long instance)
	{
		ByFilter[] filters = mFilters;
		for (int i = 0, count = mFilterCount; i < count; ++i)
		{
			if (filters[i].filter(instance))
			{
				return true;
			}
		}
		return false;
	}


	/**
	 * Expand an instance. This method expands an {@link Instance}.
	 * 
	 * @param instance
	 *            The instance to expand.
	 * @param start
	 *            The first instance of the rule. An implementing filter can use this to avoid iterating instances that precede the first instance if it's save
	 *            to do so.
	 */
	abstract void expand(long instance, long start);


	@Override
	void fastForward(long untilInstance)
	{
		untilInstance = Instance.maskWeekday(untilInstance);
		LongArray workingSet = mWorkingSet;
		if (workingSet != null)
		{
			// we have a working set, fast forward this one first
			while (workingSet.hasNext() && Instance.maskWeekday(workingSet.peek()) < untilInstance)
			{
				workingSet.next();
			}
		}

		if (workingSet == null || !workingSet.hasNext())
		{
			// no working set or empty working set, fast forward previous instance
			mPrevious.fastForward(untilInstance);
		}
	}
}
