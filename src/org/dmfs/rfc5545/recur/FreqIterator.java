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

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.dmfs.rfc5545.recur.RecurrenceRule.Freq;


/**
 * The base frequency iterator for recurrence rules. On every call to {@link #next()} or {@link #nextSet()} it returns a new date according to the frequency and
 * interval specified in a recurrence rule.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class FreqIterator extends RuleIterator
{
	/**
	 * The base frequency of the rule.
	 */
	private final Freq mFreq;

	/**
	 * The interval of the rule.
	 */
	private final int mInterval;

	/**
	 * The next instance to return.
	 */
	private final Calendar mNextInstance;

	/**
	 * Keeps the default values for values not modified by the iterator.
	 */
	private final Instance mDefaults;

	/**
	 * A {@link Set} to hold the instances of the current interval.
	 */
	private final TreeSet<Instance> mWorkingSet = new TreeSet<Instance>();

	/**
	 * The {@link Set} that is returned to the caller of {@link #nextSet()}. It's an unmodifiable copy of the {@link #mWorkingSet}, so the caller can not mess
	 * it up.
	 */
	private final Set<Instance> mResultSet = Collections.unmodifiableSet(mWorkingSet);


	/**
	 * Create a new FreqIterator for the given rule and start date.
	 * 
	 * @param rule
	 *            The rule to iterate.
	 * @param start
	 *            The first instance to iterate.
	 */
	public FreqIterator(RecurrenceRule rule, Calendar start)
	{
		super(null);
		mFreq = rule.getFreq();
		mInterval = rule.getInterval();
		mNextInstance = getIntervalStart(rule, start);
		mDefaults = new Instance(start);
	}


	@Override
	public Instance next()
	{
		Instance result = mDefaults.clone();
		switch (mFreq)
		{
			case YEARLY:
				result.year = mNextInstance.get(Calendar.YEAR);
				result.weekOfYear = mNextInstance.get(Calendar.WEEK_OF_YEAR);
				mNextInstance.add(Calendar.YEAR, mInterval);
				break;

			case MONTHLY:
				result.year = mNextInstance.get(Calendar.YEAR);
				result.month = mNextInstance.get(Calendar.MONTH);
				result.weekOfYear = mNextInstance.get(Calendar.WEEK_OF_YEAR);
				mNextInstance.add(Calendar.MONTH, mInterval);
				break;

			case WEEKLY:
				result.year = mNextInstance.get(Calendar.YEAR);
				result.month = mNextInstance.get(Calendar.MONTH);
				result.weekOfYear = mNextInstance.get(Calendar.WEEK_OF_YEAR);
				result.dayOfMonth = mNextInstance.get(Calendar.DAY_OF_MONTH);
				mNextInstance.add(Calendar.DAY_OF_YEAR, 7 * mInterval);
				break;

			case DAILY:
				result.year = mNextInstance.get(Calendar.YEAR);
				result.month = mNextInstance.get(Calendar.MONTH);
				result.weekOfYear = mNextInstance.get(Calendar.WEEK_OF_YEAR);
				result.dayOfMonth = mNextInstance.get(Calendar.DAY_OF_MONTH);
				mNextInstance.add(Calendar.DAY_OF_YEAR, mInterval);
				break;

			case HOURLY:
				result.year = mNextInstance.get(Calendar.YEAR);
				result.month = mNextInstance.get(Calendar.MONTH);
				result.weekOfYear = mNextInstance.get(Calendar.WEEK_OF_YEAR);
				result.dayOfMonth = mNextInstance.get(Calendar.DAY_OF_MONTH);
				result.hour = mNextInstance.get(Calendar.HOUR_OF_DAY);
				mNextInstance.add(Calendar.HOUR_OF_DAY, mInterval);
				break;

			case MINUTELY:
				result.year = mNextInstance.get(Calendar.YEAR);
				result.month = mNextInstance.get(Calendar.MONTH);
				result.weekOfYear = mNextInstance.get(Calendar.WEEK_OF_YEAR);
				result.dayOfMonth = mNextInstance.get(Calendar.DAY_OF_MONTH);
				result.hour = mNextInstance.get(Calendar.HOUR_OF_DAY);
				result.minute = mNextInstance.get(Calendar.MINUTE);
				mNextInstance.add(Calendar.MINUTE, mInterval);
				break;

			case SECONDLY:
				result.year = mNextInstance.get(Calendar.YEAR);
				result.month = mNextInstance.get(Calendar.MONTH);
				result.weekOfYear = mNextInstance.get(Calendar.WEEK_OF_YEAR);
				result.dayOfMonth = mNextInstance.get(Calendar.DAY_OF_MONTH);
				result.hour = mNextInstance.get(Calendar.HOUR_OF_DAY);
				result.minute = mNextInstance.get(Calendar.MINUTE);
				result.second = mNextInstance.get(Calendar.SECOND);
				mNextInstance.add(Calendar.SECOND, mInterval);
				break;

		}
		return result;
	}


	/**
	 * Return the start of the interval given by the frequency of the rule and the start date.
	 * <p>
	 * The returned {@link Calendar} instance is in UTC time zone to avoid any daylight saving effects.
	 * </p>
	 * 
	 * @param rule
	 *            A {@link RecurrenceRule}.
	 * @param start
	 *            A start date.
	 * @return A new {@link Calendar} that represents the start of the interval.
	 */
	private static Calendar getIntervalStart(RecurrenceRule rule, Calendar start)
	{
		Calendar result = null;
		switch (rule.getFreq())
		{
			case YEARLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), 0, 1, 0, 0, 0);
				break;

			case MONTHLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), 1, 0, 0, 0);
				break;

			case WEEKLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
				break;

			case DAILY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
				break;

			case HOURLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH),
					start.get(Calendar.HOUR), 0, 0);
				break;

			case MINUTELY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH),
					start.get(Calendar.HOUR), start.get(Calendar.MINUTE), 0);
				break;

			case SECONDLY:
				result = new Calendar(Calendar.UTC, start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH),
					start.get(Calendar.HOUR), start.get(Calendar.MINUTE), start.get(Calendar.SECOND));
				break;

			default:
				// this should be impossible
				throw new RuntimeException("illegal Freq value: " + rule.getFreq().name());
		}

		// ensure the start of the week is set properly
		result.setFirstDayOfWeek(rule.getWeekStart().toCalendarDay());
		result.setMinimalDaysInFirstWeek(4);
		return result;
	}


	@Override
	Set<Instance> nextSet()
	{
		mWorkingSet.clear();
		mWorkingSet.add(next());
		return mResultSet;
	}

}
