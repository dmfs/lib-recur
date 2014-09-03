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

import java.util.Calendar;
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
 * <p>
 * TODO: optimize {@link #fastForward(long)} & {@link #fastForward(Calendar)}. Using {@link Calendar} seems to slow down making it even slower than iterating
 * all instances.
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
	 * Caches the upcoming millis after a call to {@link #peekMillis()}.
	 */
	private long mNextMillis = Long.MIN_VALUE;

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
		long instance = mNextInstance = mRuleIterator.next();

		if (instance != Long.MIN_VALUE)
		{
			mNextMillis = mCalendarMetrics.toMillis(instance, mTimeZone);
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
	 * @return A {@link DateTime} object for the next instance.
	 */
	public DateTime nextDateTime()
	{
		if (mNextInstance == Long.MIN_VALUE)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}

		DateTime result = new DateTime(mTimeZone, mNextMillis);
		fetchNextInstance();
		if (mAllDay)
		{
			result.toAllDay();
		}
		else
		{
			result.shiftToTimeZone(mTimeZone);
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
	public DateTime peekDateTime()
	{
		if (mNextInstance == Long.MIN_VALUE)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}

		DateTime result = new DateTime(mTimeZone, mNextMillis);
		if (mAllDay)
		{
			result.toAllDay();
		}
		else
		{
			result.shiftToTimeZone(mTimeZone);
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

		RuleIterator iterator = mRuleIterator;

		long instance;
		do
		{
			instance = iterator.next();
		} while (--skip > 0);

		mNextInstance = instance;

		if (instance != Long.MIN_VALUE)
		{
			mNextMillis = mCalendarMetrics.toMillis(instance, mTimeZone);
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
		if (next != Long.MIN_VALUE)
		{
			mNextMillis = mCalendarMetrics.toMillis(next, mTimeZone);
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
	public void fastForward(DateTime until)
	{
		if (!hasNext())
		{
			return;
		}

		DateTime untilDate = new DateTime(until);
		untilDate.shiftToTimeZone(mTimeZone);

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
		if (next != Long.MIN_VALUE)
		{
			mNextMillis = mCalendarMetrics.toMillis(next, mTimeZone);
		}
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

		if (prevInstance != Long.MIN_VALUE)
		{
			mNextMillis = mCalendarMetrics.toMillis(prevInstance, mTimeZone);
		}

	}
}
