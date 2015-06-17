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

import java.util.List;

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.recur.ByExpander.Scope;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;
import org.dmfs.rfc5545.recur.RecurrenceRule.WeekdayNum;


/**
 * A filter that limits recurrence rules by day of week.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByDayFilter extends ByFilter
{
	/**
	 * The scope of this rule.
	 */
	private final Scope mScope;

	/**
	 * A flag indicating that at least one of the week days has a positional prefix.
	 */
	private final boolean mHasPositions;

	/**
	 * An array of the weekdays and their positions in a packed form that allows us to find them with a simple integer comparison
	 */
	private final int[] mPackedDays;


	/**
	 * Get a packed representation of a {@link WeekdayNum}.
	 * 
	 * @param pos
	 *            The position of the day or <code>0</code>.
	 * @param day
	 *            The number of the weekday.
	 * @return An int that contains the position and the weekday.
	 */
	private static int packWeekday(int pos, int day)
	{
		return (pos << 8) + day;
	}


	/**
	 * Get the weekday part of a packed day.
	 * 
	 * @param packedDay
	 *            The packed day int.
	 * @return The weekday.
	 */
	@SuppressWarnings("unused")
	private static int unpackWeekday(int packedDay)
	{
		return packedDay & 0xff;
	}


	/**
	 * Get the positional part of a packed day.
	 * 
	 * @param packedDay
	 *            The packed day int.
	 * @return The position.
	 */
	@SuppressWarnings("unused")
	private static int unpackPos(int packedDay)
	{
		return packedDay >>> 8;
	}


	public ByDayFilter(RecurrenceRule rule, CalendarMetrics calendarMetrics)
	{
		super(calendarMetrics);
		List<WeekdayNum> byDay = rule.getByDayPart();

		boolean hasByMonth = rule.hasPart(Part.BYMONTH);
		Freq freq = rule.getFreq();

		mScope = rule.hasPart(Part.BYWEEKNO) || freq == Freq.WEEKLY ? (hasByMonth || freq == Freq.MONTHLY ? Scope.WEEKLY_AND_MONTHLY : Scope.WEEKLY)
			: (hasByMonth || freq == Freq.MONTHLY ? Scope.MONTHLY : Scope.YEARLY);

		boolean hasPositions = false;

		mPackedDays = new int[byDay.size()];
		int count = 0;
		for (WeekdayNum w : byDay)
		{
			if (w.pos != 0)
			{
				hasPositions = true;
			}
			mPackedDays[count] = packWeekday(w.pos, w.weekday.ordinal());
			++count;

		}
		mHasPositions = hasPositions;
	}


	@Override
	boolean filter(long instance)
	{
		// this is called if FREQ is <= DAILY or any of BYMONTHDAY or BYYEARDAY is present, so we don't have to filter by month here
		int year = Instance.year(instance);
		int month = Instance.month(instance);
		int dayOfMonth = Instance.dayOfMonth(instance);
		CalendarMetrics calendarMetrics = mCalendarMetrics;
		int dayOfWeek = calendarMetrics.getDayOfWeek(year, month, dayOfMonth);
		int[] packedDays = mPackedDays;

		if (!mHasPositions)
		{
			return StaticUtils.linearSearch(packedDays, packWeekday(0, dayOfWeek)) < 0;
		}
		else
		{
			switch (mScope)
			{
				case WEEKLY:
					/*
					 * Note: if we're in a weekly scope we shouldn't be here. So we just ignore any days with positions.
					 */
					return StaticUtils.linearSearch(packedDays, packWeekday(0, dayOfWeek)) < 0;

				case WEEKLY_AND_MONTHLY:
				case MONTHLY:
				{
					int nthDay = (dayOfMonth - 1) / 7 + 1;
					int lastNthDay = (dayOfMonth - calendarMetrics.getDaysPerPackedMonth(year, month)) / 7 - 1;
					return (nthDay <= 0 || StaticUtils.linearSearch(packedDays, packWeekday(nthDay, dayOfWeek)) < 0)
						&& (lastNthDay >= 0 || StaticUtils.linearSearch(packedDays, packWeekday(lastNthDay, dayOfWeek)) < 0);
				}
				case YEARLY:
				{
					int yearDay = calendarMetrics.getDayOfYear(year, month, dayOfMonth);
					int nthDay = (yearDay - 1) / 7 + 1;
					int lastNthDay = (yearDay - calendarMetrics.getDaysPerYear(year)) / 7 - 1;
					return (nthDay <= 0 || StaticUtils.linearSearch(packedDays, packWeekday(nthDay, dayOfWeek)) < 0)
						&& (lastNthDay >= 0 || StaticUtils.linearSearch(packedDays, packWeekday(lastNthDay, dayOfWeek)) < 0);
				}
				default:
					return false;
			}
		}
	}

}
