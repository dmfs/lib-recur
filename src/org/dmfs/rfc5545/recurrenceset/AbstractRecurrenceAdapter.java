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

package org.dmfs.rfc5545.recurrenceset;

import java.util.TimeZone;


/**
 * An abstract adapter for recurrence instance sets. This represents the instances of a specific instance set (e.g. an rrule, an exrule, a list of rdates or
 * exdates)
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class AbstractRecurrenceAdapter
{

	interface InstanceIterator
	{

		/**
		 * Check if there is at least one more instance to iterate.
		 * 
		 * @return <code>true</code> if the next call to {@link #next()} will return another instance, <code>false</code> otherwise.
		 */
		abstract boolean hasNext();


		/**
		 * Get the next instance of this set. Do not call this if {@link #hasNext()} returns <code>false</code>.
		 * 
		 * @return The time in milliseconds since the epoch of the next instance.
		 * @throws ArrayIndexOutOfBoundsException
		 *             if there are no more instances.
		 */
		abstract long next();


		/**
		 * Peek at the upcoming instance without actually iterating it. Use {@link #hasNext()} to check if there are any more instances before calling this
		 * method.
		 * 
		 * @return The next instance of this set of instances.
		 * @throws ArrayIndexOutOfBoundsException
		 *             if there are no more instances.
		 */
		abstract long peek();


		/**
		 * Skip the given number of instances.
		 * 
		 * @param count
		 *            The number of instances to skip.
		 */
		abstract void skip(int count);


		/**
		 * Skip all instances till <code>until</code>. If <code>until</code> is an instance itself it will be the next iterated instance. If the rule doesn't
		 * recur till that date the next call to {@link #hasNext()} will return <code>false</code>.
		 * 
		 * @param until
		 *            A time stamp of the date to fast forward to.
		 */
		abstract void fastForward(long until);

	}


	/**
	 * Get an iterator for this adapter.
	 * 
	 * @param timezone
	 *            The {@link TimeZone} of the first instance.
	 * @param start
	 *            The start date in milliseconds since the epoch.
	 */
	abstract InstanceIterator getIterator(TimeZone timezone, long start);


	/**
	 * Returns whether this adapter iterates an infinite number of instances.
	 * 
	 * @return <code>true</code> if the instances in this adapter are not limited, <code>false</code> otherwise.
	 */
	abstract boolean isInfinite();


	/**
	 * Returns the last instance this adapter will iterate or {@link Long#MAX_VALUE} if {@link #isInfinite()} returns <code>true</code>.
	 * 
	 * @param timezone
	 *            The {@link TimeZone} of the first instance.
	 * @param start
	 *            The start date in milliseconds since the epoch.
	 * @return The last instance in milliseconds since the epoch.
	 */
	abstract long getLastInstance(TimeZone timezone, long start);
}
