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

/**
 * Limits the instance set of a recurrence rule. The subclasses are {@link CountLimiter} and {@link UntilLimiter} which limit by instance count or by last
 * recurrence date respectively.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
abstract class Limiter extends RuleIterator
{
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
	public long next()
	{
		long instance = mPrevious.next();
		return stop(instance) ? Long.MIN_VALUE : instance;
	}


	@Override
	LongArray nextSet()
	{
		throw new UnsupportedOperationException("nextSet is not implemented for Limiters, since it should never be called");
	}


	/**
	 * Returns true if the last instance has been iterated.
	 * 
	 * @param instance
	 *            The instance to check.
	 * @return <code>true</code> if the limit of the recurrence set has been reached and no more instances should be iterated.
	 */
	abstract boolean stop(long instance);

}
