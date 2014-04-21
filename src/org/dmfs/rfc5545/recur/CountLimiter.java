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
 * A {@link Limiter} that limits by the number of iterated instances.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
class CountLimiter extends Limiter
{
	/**
	 * The number of instances to let pass.
	 */
	private final int mLimit;

	/**
	 * The number of instances already passed.
	 */
	private int mCounter = 0;


	/**
	 * Creates a limiter that limits by instance number.
	 * 
	 * @param rule
	 *            The {@link RecurrenceRule} to limit.
	 * @param previous
	 *            The previous iterator instance.
	 */
	CountLimiter(RecurrenceRule rule, RuleIterator previous)
	{
		super(previous);
		mLimit = rule.getCount();
	}


	@Override
	boolean stop(long instance)
	{
		// Stop if more than mLimit instances have been iterated.
		return ++mCounter > mLimit;
	}


	@Override
	void fastForward(long untilInstance)
	{
		// we can not fast forward, because we have to count the instances
	}
}
