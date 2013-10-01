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
 * A {@link Limiter} that filters all instances after a certain date (the one specified in the UNTIL part).
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class UntilLimiter extends Limiter
{
	/**
	 * The latest allowed instance start date.
	 */
	private final long mUntil;


	/**
	 * Create a new limiter for the UNTIL part.
	 * 
	 * @param rule
	 *            The {@link RecurrenceRule} to filter.
	 * @param previous
	 *            The previous filter instance.
	 * @param start
	 *            The first instance. This is used to determine if the iterated instances are floating or not.
	 */
	public UntilLimiter(RecurrenceRule rule, RuleIterator previous, Calendar start)
	{
		super(previous);
		Calendar until = rule.getUntil().clone();
		if (!start.isFloating())
		{
			// switch until to the time zone of start
			until.setTimeZone(start.getTimeZone());
		}
		mUntil = Instance.make(until);
	}


	@Override
	boolean stop(long instance)
	{
		return mUntil < Instance.maskWeekday(instance);
	}
}
