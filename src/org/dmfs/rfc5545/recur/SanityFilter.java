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
import java.util.Set;
import java.util.TreeSet;


/**
 * This filter ensures we don't return any instance that is ahead of start. It also ensures that the start date is always returned as first result and that the
 * result contains no invalid instances.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 */
final class SanityFilter extends RuleIterator
{
	/**
	 * The max number of filtered instances.
	 */
	private final static int MAX_FILTERED_INSTANCES = 1000;

	/**
	 * This indicates that the next instance to return is the first instance.
	 */
	private boolean mFirst = true;

	/**
	 * The {@link Calendar} of the first instance (i.e. DTSTART).
	 */
	private final Instance mStart;

	/**
	 * A helper for date calculations.
	 */
	private final Calendar mHelper = new Calendar(2000, 0, 1);

	/**
	 * The set we work on.
	 */
	private final TreeSet<Instance> mWorkingSet = new TreeSet<Instance>();

	/**
	 * The set we return to subsequent filters.
	 */
	private final Set<Instance> mResultSet = Collections.unmodifiableSet(mWorkingSet);


	/**
	 * Creates a new {@link SanityFilter} that filters the results of the previous instance. This filter should be located between {@link FreqIterator} or any
	 * BYxxx filters and {@link CountLimiter} or {@link UntilLimiter}.
	 * 
	 * @param previous
	 *            The previous {@link RuleIterator}.
	 * @param start
	 *            The earliest date to let pass.
	 */
	SanityFilter(RuleIterator previous, Calendar start)
	{
		super(previous);
		mStart = new Instance(start);
	}


	@Override
	public Instance next()
	{
		if (mFirst)
		{
			// mStart is always the first result
			mFirst = false;
			return mStart;
		}
		else
		{
			int counter = -1;
			Instance next;
			// skip all instances that precede start
			do
			{
				if (++counter == MAX_FILTERED_INSTANCES)
				{
					throw new IllegalArgumentException("too many filtered recurrence instances");
				}

				next = mPrevious.next();
				mHelper.set(next.year, next.month, next.dayOfMonth, next.hour, next.minute, next.second);
			} while (next != null && (mStart.compareTo(next) >= 0) || (mHelper.get(Calendar.YEAR) != next.year) || (mHelper.get(Calendar.MONTH) != next.month)
				|| (mHelper.get(Calendar.DAY_OF_MONTH) != next.dayOfMonth) || (mHelper.get(Calendar.HOUR_OF_DAY) != next.hour)
				|| (mHelper.get(Calendar.MINUTE) != next.minute) || (mHelper.get(Calendar.SECOND) != next.second));
			return next;
		}
	}


	@Override
	Set<Instance> nextSet()
	{
		Set<Instance> nextSet = mPrevious.nextSet();

		if (mFirst) // filter the first set only
		{
			mWorkingSet.clear();
			// ensure start is contained in the set
			nextSet.add(mStart);

			for (Instance d : nextSet)
			{
				if (mStart.compareTo(d) < 0)
				{
					mWorkingSet.add(d);
				}
			}

			mFirst = false;

			nextSet = mResultSet;
		}
		return nextSet;
	}
}
