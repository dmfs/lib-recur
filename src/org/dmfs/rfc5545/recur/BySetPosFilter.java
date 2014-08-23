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

import java.util.Iterator;

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;


/**
 * A filter that limits instances of recurrence rules by their position in the interval.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class BySetPosFilter extends RuleIterator
{
	/**
	 * Max. number of empty sets to pass before throwing an exception.
	 */
	private final static int MAX_EMPTY_SETS = 1000;

	/**
	 * The positions in the set to filter by.
	 */
	private final int[] mSetPositions;

	/**
	 * An {@link Iterator} to iterate over the elements in the resulting set. This is used by {@link #next()}.
	 */
	private LongArray mSetIterator;

	/**
	 * This indicates that the next instance to return is the first instance.
	 */
	private boolean mFirst = true;

	/**
	 * The set we return to subsequent filters.
	 */
	private final LongArray mResultSet = new LongArray();

	private final long mStart;


	public BySetPosFilter(RecurrenceRule rule, RuleIterator previous, long start)
	{
		super(previous);
		mSetPositions = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYSETPOS));
		mStart = start;
	}


	@Override
	public long next()
	{
		if (mSetIterator == null || !mSetIterator.hasNext())
		{
			mSetIterator = nextSet();
		}
		return mSetIterator.next();
	}


	@Override
	LongArray nextSet()
	{
		final LongArray resultSet = mResultSet;
		final int[] setPositions = mSetPositions;
		resultSet.clear();

		if (mFirst)
		{
			resultSet.add(mStart);
			mFirst = false;
		}

		boolean done = false;
		int counter = -1;
		do
		{
			if (++counter == MAX_EMPTY_SETS)
			{
				throw new IllegalStateException("too many empty recurrence sets");
			}

			LongArray nextSet = mPrevious.nextSet();

			int limit = nextSet.size() + 1;
			int pos = 1;

			// iterate over all instances and check if their position is in setPositions
			while (nextSet.hasNext())
			{
				long d = nextSet.next();

				if ((StaticUtils.linearSearch(setPositions, pos) >= 0 || pos < limit && StaticUtils.linearSearch(setPositions, pos - limit) >= 0)
					&& mStart < Instance.maskWeekday(d))
				{
					resultSet.add(d);
					done = true;
				}
				++pos;
			}
		} while (!done);
		return resultSet;
	}
}
