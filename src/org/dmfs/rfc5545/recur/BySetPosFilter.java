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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	private final List<Integer> mSetPositions;

	/**
	 * An {@link Iterator} to iterate over the elements in the resulting set. This is used by {@link #next()}.
	 */
	private Iterator<Instance> mSetIterator;

	/**
	 * The set we work on.
	 */
	private final TreeSet<Instance> mWorkingSet = new TreeSet<Instance>();

	/**
	 * The set we return to subsequent filters.
	 */
	private final Set<Instance> mResultSet = Collections.unmodifiableSet(mWorkingSet);


	public BySetPosFilter(RecurrenceRule rule, RuleIterator previous)
	{
		super(previous);
		mSetPositions = rule.getByPart(Part.BYSETPOS);
	}


	@Override
	public Instance next()
	{
		if (mSetIterator == null || !mSetIterator.hasNext())
		{
			mSetIterator = nextSet().iterator();
		}
		return mSetIterator.next();
	}


	@Override
	Set<Instance> nextSet()
	{
		TreeSet<Instance> workingSet = mWorkingSet;
		workingSet.clear();

		int counter = -1;
		do
		{
			if (++counter == MAX_EMPTY_SETS)
			{
				throw new IllegalStateException("too many empty recurrence sets");
			}

			Set<Instance> nextSet = mPrevious.nextSet();
			int limit = nextSet.size() + 1;
			int pos = 1;

			// iterate over all instances and check if their position is in mSetPositions
			for (Instance d : nextSet)
			{
				if (mSetPositions.contains(pos) || pos < limit && mSetPositions.contains(pos - limit))
				{
					workingSet.add(d);
				}
				++pos;
			}
		} while (workingSet.size() == 0);
		return mResultSet;
	}

}
