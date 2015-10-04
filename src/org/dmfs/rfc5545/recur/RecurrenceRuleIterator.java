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

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;


/**
 * An iterator for recurrence rules.
 * <p>
 * <strong>Note:</strong> Some rules may recur forever, so be sure to add some limitation to your code that stops iterating after a certain number of instances
 * or at a certain date.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class RecurrenceRuleIterator
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
	 * Caches the upcoming millis after a call to {@link #peekMillis()}. This may be {@link Long#MIN_VALUE} if the millis have not been calculated yet.
	 */
	private long mNextMillis = Long.MIN_VALUE;

	/**
	 * Caches the upcoming {@link DateTime} value after a call to {@link #peekDateTime()}, so we don't have to build it twice.
	 */
	private DateTime mNextDateTime = null;

	/**
	 * The {@link CalendarMetrics} of the calendar scale to use.
	 */
	private final CalendarMetrics mCalendarMetrics;


	/**
	 * Creates a new {@link RecurrenceRuleIterator} that gets its input from <code>ruleIterator</code>.
	 * 
	 * @param ruleIterator
	 *            The last {@link RuleIterator} in the chain of iterators.
	 * @param start
	 *            The first instance to iterate.
	 */
	RecurrenceRuleIterator(RuleIterator ruleIterator, DateTime start, CalendarMetrics calendarMetrics)
	{
		mRuleIterator = ruleIterator;
		mAllDay = start.isAllDay();
		mCalendarMetrics = calendarMetrics;
		mTimeZone = start.isFloating() ? null : start.getTimeZone();
		fetchNextInstance();
	}


	private void fetchNextInstance()
	{
		mNextInstance = mRuleIterator.next();
		// invalidate mNextMillis
		mNextMillis = Long.MIN_VALUE;
		mNextDateTime = null;
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

		if (result == Long.MIN_VALUE)
		{
			result = mCalendarMetrics.toMillis(mNextInstance, mTimeZone);
		}

		fetchNextInstance();
		return result;
	}


	/**
	 * Get the next instance. The instances are guaranteed to be strictly increasing in time.
	 * 
	 * @return A {@link DateTime} object for the next instance.
	 */
	public DateTime nextDateTime()
	{
		if (mNextInstance == Long.MIN_VALUE)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}

		long nextInstance = mNextInstance;
		DateTime nextDateTime = mNextDateTime;

		fetchNextInstance();

		if (nextDateTime != null)
		{
			return nextDateTime;
		}

		if (mAllDay)
		{
			return new DateTime(mCalendarMetrics, Instance.year(nextInstance), Instance.month(nextInstance), Instance.dayOfMonth(nextInstance));
		}
		else
		{
			return new DateTime(mCalendarMetrics, mTimeZone, Instance.year(nextInstance), Instance.month(nextInstance), Instance.dayOfMonth(nextInstance),
				Instance.hour(nextInstance), Instance.minute(nextInstance), Instance.second(nextInstance));
		}
	}


	public boolean hasNext()
	{
		return mNextInstance != Long.MIN_VALUE;
	}


	/**
	 * Peek at the next instance to be returned by {@link #nextMillis()} without actually iterating it. Calling this method (even multiple times) won't affect
	 * the instances returned by {@link #nextMillis()}.
	 * 
	 * @return the upcoming instance or <code>null</code> if there are no more instances.
	 */
	public long peekMillis()
	{
		if (mNextInstance == Long.MIN_VALUE)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}

		long result = mNextMillis;

		if (result == Long.MIN_VALUE)
		{
			// next millis not calculated yet
			result = mNextMillis = mCalendarMetrics.toMillis(mNextInstance, mTimeZone);
		}

		return result;
	}


	/**
	 * Peek at the next instance to be returned by {@link #nextDateTime()} without actually iterating it. Calling this method (even multiple times) won't affect
	 * the instances returned by {@link #nextDateTime()}.
	 * 
	 * @return the upcoming instance or <code>null</code> if there are no more instances.
	 */
	public DateTime peekDateTime()
	{
		if (mNextInstance == Long.MIN_VALUE)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}

		long nextInstance = mNextInstance;
		if (mAllDay)
		{
			return mNextDateTime = new DateTime(mCalendarMetrics, Instance.year(nextInstance), Instance.month(nextInstance), Instance.dayOfMonth(nextInstance));
		}
		else
		{
			return mNextDateTime = new DateTime(mCalendarMetrics, mTimeZone, Instance.year(nextInstance), Instance.month(nextInstance),
				Instance.dayOfMonth(nextInstance), Instance.hour(nextInstance), Instance.minute(nextInstance), Instance.second(nextInstance));
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
		if (skip == 0)
		{
			return;
		}

		if (skip < 0)
		{
			throw new IllegalArgumentException("Can not skip backbards");
		}

		RuleIterator iterator = mRuleIterator;

		long instance;
		do
		{
			instance = iterator.next();
		} while (--skip > 0);

		mNextInstance = instance;

		// invalidate mNextMillis
		mNextMillis = Long.MIN_VALUE;
		mNextDateTime = null;
	}


	/**
	 * Skip all instances up to a specific date.
	 * <p>
	 * <strong>Note:</strong> After calling this method you should call {@link #hasNext()} before you continue because there might no more instances left if
	 * there is an UNTIL or COUNT part in the rule.
	 * </p>
	 * 
	 * @param until
	 *            The time stamp of earliest date to be returned by the next call to {@link #nextMillis()} or {@link #nextDateTime()}.
	 */
	public void fastForward(long until)
	{
		if (!hasNext())
		{
			return;
		}

		// convert until to an instance
		long untilInstance = mCalendarMetrics.toInstance(until, mTimeZone);

		long next = Instance.maskWeekday(mNextInstance);
		if (untilInstance <= next)
		{
			// nothing to do
			return;
		}

		RuleIterator iterator = mRuleIterator;
		iterator.fastForward(untilInstance);

		while (next != Long.MIN_VALUE && next < untilInstance)
		{
			next = iterator.next();
		}

		mNextInstance = next;

		// invalidate mNextMillis
		mNextMillis = Long.MIN_VALUE;
		mNextDateTime = null;
	}


	/**
	 * Skip all instances up to a specific date.
	 * <p>
	 * <strong>Note:</strong> After calling this method you should call {@link #hasNext()} before you continue because there might no more instances left if
	 * there is an UNTIL or COUNT part in the rule.
	 * </p>
	 * 
	 * @param until
	 *            The earliest date to be returned by the next call to {@link #nextMillis()} or {@link #nextDateTime()}.
	 */
	public void fastForward(DateTime until)
	{
		if (!hasNext())
		{
			return;
		}

		DateTime untilDate = until.shiftTimeZone(mTimeZone);

		// convert until to an instance
		long untilInstance = untilDate.getInstance();

		long next = Instance.maskWeekday(mNextInstance);
		if (untilInstance <= next)
		{
			// nothing to do
			return;
		}

		RuleIterator iterator = mRuleIterator;
		iterator.fastForward(untilInstance);

		while (next != Long.MIN_VALUE && next < untilInstance)
		{
			next = iterator.next();
		}

		mNextInstance = next;

		// invalidate mNextMillis
		mNextMillis = Long.MIN_VALUE;
		mNextDateTime = null;
	}


	/**
	 * Skips all instances except for the last one. Ensure to call {@link #hasNext()} before calling {@link #nextMillis()} or {@link #nextDateTime()} after you
	 * called this.
	 * 
	 * <p>
	 * <strong>Note:</strong> At present this will loop infinitely when called on an infinite rule. So better check {@link RecurrenceRule#isInfinite()} first.
	 * </p>
	 */
	public void skipAllButLast()
	{
		long prevInstance;
		long instance = Long.MIN_VALUE;
		RuleIterator iterator = mRuleIterator;

		do
		{
			prevInstance = instance;
			instance = iterator.next();
		} while (instance != Long.MIN_VALUE);

		mNextInstance = prevInstance;

		// invalidate mNextMillis
		mNextMillis = Long.MIN_VALUE;
		mNextDateTime = null;
	}
}
