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
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;
import org.dmfs.rfc5545.recur.RecurrenceRule.WeekdayNum;


/**
 * An expander that expands recurrence rules by day of week.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByDayExpander extends ByExpander
{
	/**
	 * The list of week days to expand.
	 */
	private final int[] mByDay;

	/**
	 * The scope of this rule.
	 */
	private final Scope mScope;

	/**
	 * The list of months if a BYMONTH part is specified in the rule. We need this to filter by month if the rule has a monthly and weekly scope.
	 */
	private final int[] mMonths;


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
	private static int unpackPos(int packedDay)
	{
		return packedDay >> 8;
	}


	public ByDayExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
	{
		super(previous, calendarTools, start);

		// get the list of WeekDayNums and convert it into an array
		List<WeekdayNum> byDay = rule.getByDayPart();
		mByDay = new int[byDay.size()];

		for (int i = 0, l = byDay.size(); i < l; ++i)
		{
			WeekdayNum weekdayNum = byDay.get(i);
			mByDay[i] = packWeekday(weekdayNum.pos, weekdayNum.weekday.ordinal());
		}

		boolean hasByMonth = rule.hasPart(Part.BYMONTH);
		Freq freq = rule.getFreq();

		mScope = rule.hasPart(Part.BYWEEKNO) || freq == Freq.WEEKLY ? (hasByMonth || freq == Freq.MONTHLY ? Scope.WEEKLY_AND_MONTHLY : Scope.WEEKLY)
			: (hasByMonth || freq == Freq.MONTHLY ? Scope.MONTHLY : Scope.YEARLY);

		if (mScope == Scope.WEEKLY_AND_MONTHLY && hasByMonth)
		{
			// we have to filter by month
			mMonths = StaticUtils.ListToArray(rule.getByPart(Part.BYMONTH));
		}
		else
		{
			mMonths = null;
		}
	}


	@Override
	void expand(long instance, long start)
	{
		CalendarMetrics calendarMetrics = mCalendarMetrics;
		int year = Instance.year(instance);
		int month = Instance.month(instance);

		for (int packedDay : mByDay)
		{
			int pos = unpackPos(packedDay);
			int day = unpackWeekday(packedDay);

			switch (mScope)
			{
				case WEEKLY:
				{
					if (pos == 0 || pos == 1) // ignore any positional days
					{
						addInstance(calendarMetrics.setDayOfWeek(instance, day));
					}
					break;
				}
				case WEEKLY_AND_MONTHLY: // the rule is either MONTHLY with BYWEEKNO expansion, WEEKLY with BYMONTH filter or YEARLY with BYMONTH and BYWEEKNO
				{
					if (pos == 0 || pos == 1) // ignore any positional days
					{
						long newInstance = calendarMetrics.setDayOfWeek(instance, day);

						int newMonth = Instance.month(newInstance);

						if (mMonths != null && StaticUtils.linearSearch(mMonths, newMonth) > 0 || mMonths == null && newMonth == month)
						{
							addInstance(newInstance);
						}
					}
					break;
				}
				case MONTHLY: // the rule is MONTHLY or there is a BYMONTH filter present
				{
					// get the first week day and the number of days of this month
					int weekDayOfFirstInMonth = calendarMetrics.getDayOfWeek(year, month, 1);
					int monthDays = calendarMetrics.getDaysPerPackedMonth(year, month);

					// get the first instance of the weekday in this month
					int firstDay = (day - weekDayOfFirstInMonth + 7) % 7 + 1;

					if (pos == 0)
					{
						// add all instances of this weekday of this month
						for (int dayOfMonth = firstDay; dayOfMonth <= monthDays; dayOfMonth += 7)
						{
							addInstance(Instance.setDayOfMonth(instance, dayOfMonth));
						}
					}
					else
					{
						int maxPos = 1 + (monthDays - firstDay) / 7;

						// add just one position
						if (pos > 0 && pos <= maxPos || pos < 0 && pos + maxPos + 1 > 0)
						{
							addInstance(Instance.setDayOfMonth(instance, firstDay + (pos > 0 ? pos - 1 : pos + maxPos) * 7));
						}
					}
					break;
				}
				case YEARLY: // no other BY* filters are present
				{
					// calculate the first occurrence of this weekday in this year
					int firstWeekdayOfYear = (day - calendarMetrics.getWeekDayOfFirstYearDay(year) + 7) % 7 + 1;

					int yearDays = calendarMetrics.getDaysPerYear(year);

					if (pos == 0)
					{
						// add an instance for every occurrence of this week day.
						for (int dayOfYear = firstWeekdayOfYear; dayOfYear <= yearDays; dayOfYear += 7)
						{
							int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, dayOfYear);
							addInstance(Instance.setMonthAndDayOfMonth(instance, CalendarMetrics.packedMonth(monthAndDay),
								CalendarMetrics.dayOfMonth(monthAndDay)));
						}
					}
					else
					{
						if (pos > 0)
						{
							int dayOfYear = firstWeekdayOfYear + (pos - 1) * 7;
							if (dayOfYear <= yearDays)
							{
								int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, dayOfYear);
								addInstance(Instance.setMonthAndDayOfMonth(instance, CalendarMetrics.packedMonth(monthAndDay),
									CalendarMetrics.dayOfMonth(monthAndDay)));
							}
						}
						else
						{
							// calculate the last occurrence of this weekday in this year
							int lastWeekdayOfYear = firstWeekdayOfYear + yearDays - yearDays % 7;
							if (lastWeekdayOfYear > yearDays)
							{
								// we have ended up in the next year, go back one week
								lastWeekdayOfYear -= 7;
							}

							// calculate the actual instance
							int dayOfYear = lastWeekdayOfYear + (pos + 1) * 7;
							if (dayOfYear > 0)
							{
								int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, dayOfYear);
								addInstance(Instance.setMonthAndDayOfMonth(instance, CalendarMetrics.packedMonth(monthAndDay),
									CalendarMetrics.dayOfMonth(monthAndDay)));
							}
						}
					}
					break;
				}
			}
		}
	}
}
