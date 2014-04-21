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

import java.util.TimeZone;


/**
 * An iterator for recurrence rules.
 * <p>
 * <strong>Note:</strong> Some rules may recur forever, so be sure to add some limitation to your code that stops iterating after a certain number of instances
 * or at a certain date.
 * </p>
 * <p>
 * TODO: optimize {@link #fastForward(long)} & {@link #fastForward(Calendar)}. Often we can skip entire intervals so we don't need to iterate all the instances
 * in between.
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

	/**
	 * The upcoming instance, if any.
	 */
	private long mNextInstance;

	/**
	 * A helper for date calculations.
	 */
	private final Calendar mHelper;

	/**
	 * Whether the start instance is an all day instance.
	 */
	private final boolean mAllDay;

	/**
	 * The time zone oft the start instance or <code>null</code> if it's a floating time
	 */
	private final TimeZone mTimeZone;

	/**
	 * Caches the upcoming millis after a call to {@link #peekMillis()}.
	 */
	private long mNextMillis = Long.MIN_VALUE;


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
		mAllDay = start.isAllDay();
		mTimeZone = start.isFloating() ? null : start.getTimeZone();
		mHelper = start.clone();
		fetchNextInstance();
	}


	private void fetchNextInstance()
	{
		long instance = mNextInstance = mRuleIterator.next();

		if (instance != Long.MIN_VALUE)
		{
			mHelper.set(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance), Instance.hour(instance), Instance.minute(instance),
				Instance.second(instance));

			mNextMillis = mHelper.getTimeInMillis();
		}
	}


	/**
	 * Get the next instance. The instances are guaranteed to be strictly increasing in time.
	 * 
	 * @return A time stamp of the next instance.
	 */
	public long nextMillis()
	{
		if (mNextInstance == Long.MIN_VALUE)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}
		long result = mNextMillis;
		fetchNextInstance();
		return result;
	}


	/**
	 * Get the next instance. The instances are guaranteed to be strictly increasing in time.
	 * 
	 * @return A {@link Calendar} object for the next instance.
	 */
	public Calendar nextCalendar()
	{
		if (mNextInstance == Long.MIN_VALUE)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}
		// mHelper always holds the last calculated instance
		Calendar result = mHelper.clone();
		fetchNextInstance();
		if (mAllDay)
		{
			result.toAllDay();
		}
		else
		{
			result.setTimeZone(mTimeZone);
		}

		return result;
	}


	public boolean hasNext()
	{
		return mNextInstance != Long.MIN_VALUE;
	}


	/**
	 * Peek at the next instance to be returned by {@link #next()} without actually iterating it. Calling this method (even multiple times) won't affect the
	 * instances returned by {@link #next()}.
	 * 
	 * @return the upcoming instance or <code>null</code> if there are no more instances.
	 */
	public long peekMillis()
	{
		if (mNextInstance == Long.MIN_VALUE)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}
		return mNextMillis;
	}


	/**
	 * Peek at the next instance to be returned by {@link #next()} without actually iterating it. Calling this method (even multiple times) won't affect the
	 * instances returned by {@link #next()}.
	 * 
	 * @return the upcoming instance or <code>null</code> if there are no more instances.
	 */
	public Calendar peekCalendar()
	{
		if (mNextInstance == Long.MIN_VALUE)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}
		// mHelper always holds the last calculated instance
		Calendar result = mHelper.clone();
		if (mAllDay)
		{
			result.toAllDay();
		}
		else
		{
			result.setTimeZone(mTimeZone);
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
		if (skip == 0)
		{
			return;
		}

		long instance;
		do
		{
			instance = mRuleIterator.next();
		} while (skip-- > 0);

		mNextInstance = instance;

		if (instance != Long.MIN_VALUE)
		{
			mHelper.set(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance), Instance.hour(instance), Instance.minute(instance),
				Instance.second(instance));

			if (mAllDay)
			{
				mHelper.toAllDay();
			}
			else
			{
				mHelper.setTimeZone(mTimeZone);
			}

			mNextMillis = mHelper.getTimeInMillis();
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
	public void fastForward(long until)
	{
		if (!hasNext())
		{
			return;
		}

		// convert until to an instance
		Calendar untilCalendar = mHelper.clone();
		untilCalendar.setTimeInMillis(until);
		long untilInstance = Instance.make(untilCalendar);

		long next = Instance.maskWeekday(mNextInstance);
		if (untilInstance <= next)
		{
			// nothing to do
			return;
		}

		mRuleIterator.fastForward(untilInstance);

		while (next != Long.MIN_VALUE && next < untilInstance)
		{
			next = mRuleIterator.next();
		}

		mNextInstance = next;
		if (next != Long.MIN_VALUE)
		{
			mHelper
				.set(Instance.year(next), Instance.month(next), Instance.dayOfMonth(next), Instance.hour(next), Instance.minute(next), Instance.second(next));

			mNextMillis = mHelper.getTimeInMillis();
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
	public void fastForward(Calendar until)
	{
		if (!hasNext())
		{
			return;
		}

		Calendar untilCalendar = until;
		TimeZone untilTz = until.getTimeZone();
		if (mTimeZone != null && !mTimeZone.equals(untilTz) || mTimeZone == null && untilTz != null)
		{
			untilCalendar = until.clone();
			untilCalendar.setTimeZone(mTimeZone);
		}

		// convert until to an instance
		long untilInstance = Instance.make(untilCalendar);

		long next = Instance.maskWeekday(mNextInstance);
		if (untilInstance <= next)
		{
			// nothing to do
			return;
		}

		mRuleIterator.fastForward(untilInstance);

		while (next != Long.MIN_VALUE && next < untilInstance)
		{
			next = mRuleIterator.next();
		}

		mNextInstance = next;
		if (next != Long.MIN_VALUE)
		{
			mHelper
				.set(Instance.year(next), Instance.month(next), Instance.dayOfMonth(next), Instance.hour(next), Instance.minute(next), Instance.second(next));

			mNextMillis = mHelper.getTimeInMillis();
		}
	}
}
