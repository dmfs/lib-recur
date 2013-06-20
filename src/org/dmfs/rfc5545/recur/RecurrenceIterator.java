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


/**
 * An iterator for recurrence rules.
 * <p>
 * <strong>Note:</strong> Some rules may recur forever, so be sure to add some limitation to your code that stops iterating after a certain number of instances
 * or at a certain date.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class RecurrenceIterator implements Iterator<Calendar>
{
	/**
	 * The previous iterator instance. This is <code>null</code> for the {@link FreqIterator}.
	 */
	private final RuleIterator mRuleIterator;

	/**
	 * The first instance. This is used to adjust the time zone and the all-day flag of the instances.
	 */
	private final Calendar mStart;

	/**
	 * The upcoming instance, if any.
	 */
	private Instance mNextInstance;


	/**
	 * Creates a new iterator that gets its input from <code>previous</code>.
	 * 
	 * @param ruleIterator
	 *            A RuleIterator that precedes this one in the chain of iterators or <code>null</code> if this is the first iterator (i.e. {@link FreqIterator}
	 *            ).
	 */
	RecurrenceIterator(RuleIterator ruleIterator, Calendar start)
	{
		mRuleIterator = ruleIterator;
		mStart = start;
	}


	/**
	 * Get the next instance. The instances are guaranteed to be strictly increasing in time.
	 * 
	 * @return A {@link Calendar} object for the next instance.
	 */
	@Override
	public Calendar next()
	{
		Instance instance = mNextInstance;
		if (instance == null)
		{
			instance = mRuleIterator.next();
			if (instance == null)
			{
				throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
			}
		}
		else
		{
			mNextInstance = null;
		}

		if (mStart.isFloating())
		{
			if (mStart.isAllDay())
			{
				return new Calendar(instance.year, instance.month, instance.dayOfMonth);
			}
			else
			{
				return new Calendar(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
			}
		}
		else
		{
			return new Calendar(mStart.getTimeZone(), instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
		}
	}


	@Override
	public boolean hasNext()
	{
		return (mNextInstance != null || (mNextInstance = mRuleIterator.next()) != null);
	}


	/**
	 * Peek at the next instance to be returned by {@link #next()} without actually iterating it. Calling this method (even multiple times) won't affect the
	 * instances returned by {@link #next()}.
	 * 
	 * @return the upcoming instance or <code>null</code> if there are no more instances.
	 */
	public Calendar peek()
	{
		Instance instance = mNextInstance;
		if (instance == null)
		{
			instance = mRuleIterator.next();

			if (instance == null)
			{
				throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
			}
		}

		if (mStart.isFloating())
		{
			if (mStart.isAllDay())
			{
				return new Calendar(instance.year, instance.month, instance.dayOfMonth);
			}
			else
			{
				return new Calendar(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
			}
		}
		else
		{
			return new Calendar(mStart.getTimeZone(), instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
		}
	}


	/**
	 * Skip the given number of instances.
	 * <p>
	 * <strong>Note:</strong> After calling this method you should call {@link #hasNext()} before you continue because there might be less than
	 * <code>skip</code> instances left when you call this.
	 * </p>
	 * 
	 * @param skip
	 *            The number of instances to skip.
	 */
	public void skip(int skip)
	{
		if (mNextInstance != null)
		{
			--skip;
			mNextInstance = null;
		}
		while (skip > 0 && mRuleIterator.next() != null)
		{
			--skip;
		}
	}


	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("Remove is not supported by this iterator");
	}
}
