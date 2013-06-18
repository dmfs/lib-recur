/*
 * Copyright (C) 2012 Marten Gajda <marten@dmfs.org>
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
 * Limits the instance set of a recurrence rules. The only subclasses are {@link CountLimiter} and {@link UntilLimiter} that limit by instance count or by last
 * recurrence date respectively.
 * <p>
 * A limiter must always be the last iterator in the chain of iterators.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
abstract class Limiter extends RuleIterator
{
	/**
	 * The set we work on.
	 */
	private final TreeSet<Instance> mWorkingSet = new TreeSet<Instance>();

	/**
	 * The set we return to subsequent filters.
	 */
	private final Set<Instance> mResultSet = Collections.unmodifiableSet(mWorkingSet);


	/**
	 * Constructor for Limiter that just passes through the <code>previous</code> parameter.
	 * 
	 * @param previous
	 *            The previous iterator instance.
	 */
	Limiter(RuleIterator previous)
	{
		super(previous);
	}


	@Override
	public Instance next()
	{
		Instance instance = mPrevious.next();
		return stop(instance) ? null : instance;
	}


	@Override
	Set<Instance> nextSet()
	{
		TreeSet<Instance> workingSet = mWorkingSet;
		workingSet.clear();
		for (Instance d : mPrevious.nextSet())
		{
			if (!stop(d))
			{
				workingSet.add(d);
			}
		}
		return workingSet.size() == 0 ? null : mResultSet;
	}


	/**
	 * Returns true if the last instance has been iterated.
	 * 
	 * @param instance
	 *            The {@link Calendar} instance to check.
	 * @return <code>true</code> if the limit of the recurrence set has been reached and no more instances should be iterated.
	 */
	abstract boolean stop(Instance instance);

}
