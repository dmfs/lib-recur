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
 * TODO: optimize {@link #fastForward(long)} & {@link #fastForward(Calendar)}. Using {@link Calendar} seems to slow down making it even slower than iterating
 * all instances.
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
	 * The {@link CalendarMetrics} of the calendar scale to use.
	 */
	private final CalendarMetrics mCalendarMetrics;


	/**
	 * Creates a new {@link RecurrenceIterator} that gets its input from <code>ruleIterator</code>.
	 * 
	 * @param ruleIterator
	 *            The last {@link RuleIterator} in the chain of iterators.
	 * @param start
	 *            The first instance to iterate.
	 */
	RecurrenceIterator(RuleIterator ruleIterator, Calendar start, CalendarMetrics calendarMetrics)
	{
		mRuleIterator = ruleIterator;
		mAllDay = start.isAllDay();
		mCalendarMetrics = calendarMetrics;
		mTimeZone = start.isFloating() ? null : start.getTimeZone();
		fetchNextInstance();
	}


	private void fetchNextInstance()
	{
		long instance = mNextInstance = mRuleIterator.next();

		if (instance != Long.MIN_VALUE)
		{
			mNextMillis = Instance.toMillis(instance, mTimeZone, mCalendarMetrics);
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

		Calendar result = new Calendar(mTimeZone, mNextMillis);
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

		Calendar result = new Calendar(mTimeZone, mNextMillis);
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

		if (skip < 0)
		{
			throw new IllegalArgumentException("Can not skip backbards");
		}

		long instance;
		do
		{
			instance = mRuleIterator.next();
		} while (--skip > 0);

		mNextInstance = instance;

		if (instance != Long.MIN_VALUE)
		{
			mNextMillis = Instance.toMillis(instance, mTimeZone, mCalendarMetrics);
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
		Calendar untilCalendar = new Calendar(mTimeZone, until);
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
			mNextMillis = Instance.toMillis(next, mTimeZone, mCalendarMetrics);
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
			mNextMillis = Instance.toMillis(next, mTimeZone, mCalendarMetrics);
		}
	}
}
