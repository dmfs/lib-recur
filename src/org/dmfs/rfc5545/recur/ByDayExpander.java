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

import org.dmfs.rfc5545.recur.RecurrenceRule.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;
import org.dmfs.rfc5545.recur.RecurrenceRule.WeekdayNum;


/**
 * A filter that limits or expands recurrence rules by day of week. This filter expands instances for YEARLY, MONTHLY and WEEKLY rules with respect to the
 * exceptions mentioned in <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a>.
 * <p>
 * In particular that means YEARLY and MONTHLY rules are filtered instead of expanded when a BYYEARDAY or BYMONTH day parts are present in the rule. RFC 5545
 * forbids BYYEARDAY to be used with MONTHLY rules, but RFC 2445 allows it, so we've expanded the definition to that case.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByDayExpander extends ByExpander
{
	/**
	 * The list of week days to let pass or to expand.
	 */
	private final List<WeekdayNum> mByDay;

	/**
	 * The scope of this rule.
	 */
	private final Scope mScope;

	/**
	 * The list of months if a BYMONTH part is specified in the rule. We need this to filter by month if the rule has a monthly and weekly scope.
	 * 
	 * TODO: replace by an array of ints
	 */
	private final List<Integer> mMonths;


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
		return packedDay >> 8;
	}


	public ByDayExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
	{
		super(previous, calendarTools, start);
		mByDay = rule.getByDayPart();

		mScope = rule.hasPart(Part.BYWEEKNO) || rule.getFreq() == Freq.WEEKLY ? (rule.hasPart(Part.BYMONTH) || rule.getFreq() == Freq.MONTHLY ? Scope.WEEKLY_AND_MONTHLY
			: Scope.WEEKLY)
			: (rule.hasPart(Part.BYMONTH) || rule.getFreq() == Freq.MONTHLY ? Scope.MONTHLY : Scope.YEARLY);

		if (mScope == Scope.WEEKLY_AND_MONTHLY && rule.hasPart(Part.BYMONTH))
		{
			// we have to filter by month
			mMonths = rule.getByPart(Part.BYMONTH);
		}
		else
		{
			mMonths = null;
		}

		// nor now always sort the result
		setNeedsSorting(true);
	}


	@Override
	void expand(long instance, long start)
	{
		CalendarMetrics calendarMetrics = mCalendarMetrics;
		int year = Instance.year(instance);
		int month = Instance.month(instance);
		int dayOfMonth = Instance.dayOfMonth(instance);
		int hour = Instance.hour(instance);
		int minute = Instance.minute(instance);
		int second = Instance.second(instance);
		int weekOfYear = calendarMetrics.getWeekOfYear(year, month, dayOfMonth);

		for (WeekdayNum day : mByDay)
		{
			switch (mScope)
			{
				case WEEKLY:
					if (day.pos == 0 || day.pos == 1) // ignore any positional days
					{
						int tempYear = year;
						if (weekOfYear > 10 && month < 1)
						{
							// week is in previous ISO year
							--tempYear;
						}
						else if (weekOfYear < 10 && month > 4)
						{
							// week is in next ISO year
							++tempYear;
						}

						int yearDay = calendarMetrics.getYearDayOfIsoYear(tempYear, weekOfYear, day.weekday.ordinal());

						int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(tempYear, yearDay);

						if (yearDay < 1)
						{
							--tempYear;
						}
						else if (yearDay > calendarMetrics.getDaysPerYear(tempYear))
						{
							++tempYear;
						}

						addInstance(Instance.make(tempYear, CalendarMetrics.month(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay), hour, minute, second));
					}
					break;

				case WEEKLY_AND_MONTHLY: // the rule is either MONTHLY with BYWEEKNO expansion, WEEKLY with BYMONTH filter or YEARLY with BYMONTH and BYWEEKNO

					if (day.pos == 0 || day.pos == 1) // ignore any positional days
					{
						int tempYear = year;
						if (weekOfYear > 10 && month < 1)
						{
							// week is in previous ISO year
							--tempYear;
						}
						else if (weekOfYear < 10 && month > 4)
						{
							// week is in next ISO year
							++tempYear;
						}

						int yearDay = calendarMetrics.getYearDayOfIsoYear(tempYear, weekOfYear, day.weekday.ordinal());

						int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(tempYear, yearDay);

						if (yearDay < 1)
						{
							--tempYear;
						}
						else if (yearDay > calendarMetrics.getDaysPerYear(tempYear))
						{
							++tempYear;
						}

						int newMonth = CalendarMetrics.month(monthAndDay);

						if (mMonths != null && mMonths.contains(newMonth + 1) || mMonths == null && newMonth == month)
						{
							addInstance(Instance.make(tempYear, newMonth, CalendarMetrics.dayOfMonth(monthAndDay), hour, minute, second));
						}
					}
					break;

				case MONTHLY: // the rule is MONTHLY or there is a BYMONTH filter present

					// get the first week day and the number of days of this month
					int weekDayOfFirstInMonth = calendarMetrics.getDayOfWeek(year, month, 1) + 1;
					int monthDays = calendarMetrics.getDaysPerMonth(year, month);

					// get the first instance of the weekday in this month
					int firstDay = (day.weekday.toCalendarDay() - weekDayOfFirstInMonth + 7) % 7 + 1;

					if (day.pos == 0)
					{
						// add all instances of this weekday of this month
						for (int dayOfMonthx = firstDay; dayOfMonthx <= monthDays; dayOfMonthx += 7)
						{
							addInstance(Instance.setDayOfMonth(instance, dayOfMonthx));
						}
					}
					else
					{
						int maxDays = 1 + (monthDays - firstDay) / 7;
						// add just one position

						if (day.pos > 0 && day.pos <= maxDays || day.pos < 0 && day.pos + maxDays + 1 > 0)
						{
							addInstance(Instance.setDayOfMonth(instance, firstDay + (day.pos > 0 ? day.pos - 1 : day.pos + maxDays) * 7));
						}
					}
					break;

				case YEARLY: // no other BY* filters are present

					// calculate the first occurrence of this weekday in this year
					int firstWeekdayOfYear = (day.weekday.ordinal() - calendarMetrics.getWeekDayOfFirstYearDay(year) + 7) % 7 + 1;

					int yearDays = calendarMetrics.getDaysPerYear(year);

					if (day.pos == 0)
					{
						// add an instance for every occurrence if this week day.
						for (int dayOfYear = firstWeekdayOfYear; dayOfYear <= yearDays; dayOfYear += 7)
						{
							int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, dayOfYear);
							addInstance(Instance.make(year, CalendarMetrics.month(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay), hour, minute, second));
						}
					}
					else
					{
						if (day.pos > 0)
						{
							int dayOfYear = firstWeekdayOfYear + (day.pos - 1) * 7;
							if (dayOfYear <= yearDays)
							{
								int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, dayOfYear);
								addInstance(Instance.make(year, CalendarMetrics.month(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay), hour, minute,
									second));
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
							int dayOfYear = lastWeekdayOfYear + (day.pos + 1) * 7;
							if (dayOfYear > 0)
							{
								int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, dayOfYear);
								addInstance(Instance.make(year, CalendarMetrics.month(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay), hour, minute,
									second));
							}
						}
					}
					break;
			}
		}
	}
}
