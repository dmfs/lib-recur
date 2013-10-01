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
 * An iterator for recurrence rules.
 * <p>
 * <strong>Note:</strong> Some rules may recur forever, so be sure to add some limitation to your code that stops iterating after a certain number of instances
 * or at a certain date.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class RecurrenceIterator
{
	/**
	 * The previous iterator instance. This is <code>null</code> for the {@link FreqIterator}.
	 */
	private final RuleIterator mRuleIterator;

	/**
	 * The first event to iterate;
	 */
	private Calendar mStart;

	/**
	 * The upcoming instance, if any.
	 */
	private long mNextInstance = Long.MIN_VALUE;

	/**
	 * A helper for date calculations.
	 */
	private final Calendar mHelper;


	/**
	 * Creates a new {@link RecurrenceIterator} that gets its input from <code>ruleIterator</code>.
	 * 
	 * @param ruleIterator
	 *            The last {@link RuleIterator} in the chain of iterators.
	 * @param start
	 *            The first instance to iterate.
	 */
	RecurrenceIterator(RuleIterator ruleIterator, Calendar start)
	{
		mRuleIterator = ruleIterator;
		mStart = start.clone();
		mHelper = start.clone();
	}


	/**
	 * Get the next instance. The instances are guaranteed to be strictly increasing in time.
	 * 
	 * @return A time stamp of the next instance.
	 */
	public long nextMillis()
	{
		long instance = mNextInstance;
		if (instance == Long.MIN_VALUE)
		{
			instance = mRuleIterator.next();
			if (instance == Long.MIN_VALUE)
			{
				throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
			}
		}
		else
		{
			mNextInstance = Long.MIN_VALUE;
		}

		mHelper.set(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance), Instance.hour(instance), Instance.minute(instance),
			Instance.second(instance));

		return mHelper.getTimeInMillis();
	}


	/**
	 * Get the next instance. The instances are guaranteed to be strictly increasing in time.
	 * 
	 * @return A {@link Calendar} object for the next instance.
	 */
	public Calendar nextCalendar()
	{
		long instance = mNextInstance;
		if (instance == Long.MIN_VALUE)
		{
			instance = mRuleIterator.next();
			if (instance == Long.MIN_VALUE)
			{
				throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
			}
		}
		else
		{
			mNextInstance = Long.MIN_VALUE;
		}

		mHelper.set(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance), Instance.hour(instance), Instance.minute(instance),
			Instance.second(instance));

		Calendar result = mHelper.clone();

		if (mStart.isAllDay())
		{
			result.toAllDay();
		}
		else
		{
			result.setTimeZone(mStart.isFloating() ? null : mStart.getTimeZone());
		}

		return result;
	}


	public boolean hasNext()
	{
		return (mNextInstance != Long.MIN_VALUE || (mNextInstance = mRuleIterator.next()) != Long.MIN_VALUE);
	}


	/**
	 * Peek at the next instance to be returned by {@link #next()} without actually iterating it. Calling this method (even multiple times) won't affect the
	 * instances returned by {@link #next()}.
	 * 
	 * @return the upcoming instance or <code>null</code> if there are no more instances.
	 */
	public long peekMillis()
	{
		long instance = mNextInstance;
		if (instance == Long.MIN_VALUE)
		{
			instance = mRuleIterator.next();

			if (instance == Long.MIN_VALUE)
			{
				throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
			}
		}

		mHelper.set(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance), Instance.hour(instance), Instance.minute(instance),
			Instance.second(instance));

		return mHelper.getTimeInMillis();
	}


	/**
	 * Peek at the next instance to be returned by {@link #next()} without actually iterating it. Calling this method (even multiple times) won't affect the
	 * instances returned by {@link #next()}.
	 * 
	 * @return the upcoming instance or <code>null</code> if there are no more instances.
	 */
	public Calendar peekCalendar()
	{
		long instance = mNextInstance;
		if (instance == Long.MIN_VALUE)
		{
			instance = mRuleIterator.next();

			if (instance == Long.MIN_VALUE)
			{
				throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
			}
		}

		mHelper.set(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance), Instance.hour(instance), Instance.minute(instance),
			Instance.second(instance));

		Calendar result = mHelper.clone();

		if (mStart.isAllDay())
		{
			result.isAllDay();
		}
		else
		{
			result.setTimeZone(mStart.isFloating() ? null : mStart.getTimeZone());
		}

		return result;
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
		if (mNextInstance != Long.MIN_VALUE)
		{
			--skip;
			mNextInstance = Long.MIN_VALUE;
		}
		while (skip > 0 && mRuleIterator.next() != Long.MIN_VALUE)
		{
			--skip;
		}
	}


	/**
	 * Skip all instances up to a specific date.
	 * <p>
	 * <strong>Note:</strong> After calling this method you should call {@link #hasNext()} before you continue because there might no more instances left if
	 * there is an UNTIL or COUNT part in the rule.
	 * </p>
	 * 
	 * @param until
	 *            The time stamp of earliest date to be returned by the next call to {@link #next()}.
	 */
	public void skip(long until)
	{
		if (!hasNext())
		{
			return;
		}

		long next = peekMillis();
		while (hasNext() && next < until)
		{
			skip(1);
			next = peekMillis();
		}
	}


	/**
	 * Skip all instances up to a specific date.
	 * <p>
	 * <strong>Note:</strong> After calling this method you should call {@link #hasNext()} before you continue because there might no more instances left if
	 * there is an UNTIL or COUNT part in the rule.
	 * </p>
	 * 
	 * @param until
	 *            The earliest date to be returned by the next call to {@link #next()}.
	 */
	public void skip(Calendar until)
	{
		skip(until.getTimeInMillis());
	}
}
