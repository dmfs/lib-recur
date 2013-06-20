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
