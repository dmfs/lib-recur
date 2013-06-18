/*
 * Copyright (C) 2013 Marten Gajda <marten@dmfs.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
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
