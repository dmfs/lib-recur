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

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;


/**
 * A filter that expands recurrence rules by day of year. This filter expands instances for YEARLY, MONTHLY and WEEKLY rules.
 * <p>
 * <strong>Note: </strong> <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a> Doesn't allow BYYEARDAY to be used with DAILY, WEEKLY and
 * MONTHLY rules, but RFC 2445 does. This filter tries to return a reasonable result for these cases. In particular that means we expand MONTHLY and WEEKLY
 * rules and filter DAILY rules.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByYearDayExpander extends ByExpander
{
	/**
	 * The year days to let pass or to expand.
	 */
	private final int[] mYearDays;

	/**
	 * The scope of this rule.
	 */
	private final Scope mScope;

	/**
	 * The list of months if a BYMONTH part is specified in the rule. We need this to filter by month if the rule has a monthly and weekly scope.
	 */
	private final int[] mMonths;


	public ByYearDayExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarMetrics, long start)
	{
		super(previous, calendarMetrics, start);

		mYearDays = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYYEARDAY));

		mScope = rule.getFreq() == Freq.WEEKLY || rule.hasPart(Part.BYWEEKNO) ? rule.hasPart(Part.BYMONTH) ? Scope.WEEKLY_AND_MONTHLY : Scope.WEEKLY : rule
			.getFreq() == Freq.YEARLY && !rule.hasPart(Part.BYMONTH) ? Scope.YEARLY : Scope.MONTHLY;

		if (mScope == Scope.WEEKLY_AND_MONTHLY && rule.hasPart(Part.BYMONTH))
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
		int year = Instance.year(instance);
		int month = Instance.month(instance);
		int dayOfMonth = Instance.dayOfMonth(instance);
		int hour = Instance.hour(instance);
		int minute = Instance.minute(instance);
		int second = Instance.second(instance);
		int yearDays = mCalendarMetrics.getDaysPerYear(year);
		int startDayOfYear = mCalendarMetrics.getDayOfYear(Instance.year(start), Instance.month(start), Instance.dayOfMonth(start));
		for (int day : mYearDays)
		{
			int actualDay = day;
			if (day < 0)
			{
				actualDay = day + yearDays + 1;
			}

			switch (mScope)
			{
				case WEEKLY:
				{
					int prevYearDays = mCalendarMetrics.getDaysPerYear(year - 1);
					int nextYearDays = mCalendarMetrics.getDaysPerYear(year + 1);

					int prevYearDay = day;
					int nextYearDay = day;
					if (day < 0)
					{
						prevYearDay = day + prevYearDays + 1;
						nextYearDay = day + nextYearDays + 1;
					}

					int oldWeek = mCalendarMetrics.getWeekOfYear(year, month, dayOfMonth);
					/*
					 * Add instance only if the week didn't change.
					 */
					int newWeek = mCalendarMetrics.getWeekOfYear(year, actualDay);
					if (0 < actualDay && actualDay <= yearDays && newWeek == oldWeek)
					{
						int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year, actualDay);
						addInstance(Instance.setMonthAndDayOfMonth(year, CalendarMetrics.packedMonth(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay)));
					}
					else if (0 < nextYearDay && nextYearDay <= nextYearDays && nextYearDay < 7)
					{
						/*
						 * The day might belong to the next year. Add instance only if the week didn't change.
						 */
						newWeek = mCalendarMetrics.getWeekOfYear(year + 1, nextYearDay);
						if (newWeek == oldWeek)
						{
							int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year + 1, nextYearDay);
							addInstance(Instance.make(year + 1, CalendarMetrics.packedMonth(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay), hour,
								minute, second));
						}
					}
					else if (0 < prevYearDay && prevYearDay <= prevYearDays && prevYearDay > prevYearDays - 7)
					{
						/*
						 * The day might belong to the previous year. Add instance only if the week didn't change.
						 */
						newWeek = mCalendarMetrics.getWeekOfYear(year - 1, prevYearDay);
						if (newWeek == oldWeek)
						{
							int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year - 1, prevYearDay);
							addInstance(Instance.make(year - 1, CalendarMetrics.packedMonth(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay), hour,
								minute, second));
						}
					}
					break;
				}
				case WEEKLY_AND_MONTHLY:
				{
					/*
					 * This case is handled almost like WEEKLY scope, just with additional month check
					 */
					int prevYearDays = mCalendarMetrics.getDaysPerYear(year - 1);
					int nextYearDays = mCalendarMetrics.getDaysPerYear(year + 1);

					int prevYearDay = day;
					int nextYearDay = day;
					if (day < 0)
					{
						prevYearDay = day + prevYearDays + 1;
						nextYearDay = day + nextYearDays + 1;
					}

					int oldWeek = mCalendarMetrics.getWeekOfYear(year, month, dayOfMonth);
					/*
					 * Add instance only if the week didn't change.
					 */
					int newWeek = mCalendarMetrics.getWeekOfYear(year, actualDay);
					if (0 < actualDay && actualDay <= yearDays && newWeek == oldWeek)
					{
						int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year, actualDay);
						int newMonth = CalendarMetrics.packedMonth(monthAndDay);
						if (mMonths != null && StaticUtils.linearSearch(mMonths, newMonth) >= 0 || mMonths == null && newMonth == month)
						{
							addInstance(Instance.setMonthAndDayOfMonth(year, newMonth, CalendarMetrics.dayOfMonth(monthAndDay)));
						}
					}
					else if (0 < nextYearDay && nextYearDay <= nextYearDays && nextYearDay < 7)
					{
						/*
						 * The day might belong to the next year.
						 */
						newWeek = mCalendarMetrics.getWeekOfYear(year + 1, nextYearDay);
						int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year + 1, nextYearDay);
						int newMonth = CalendarMetrics.packedMonth(monthAndDay);
						if (newWeek == oldWeek && mMonths != null && StaticUtils.linearSearch(mMonths, newMonth) >= 0 || mMonths == null && newMonth == month)
						{
							addInstance(Instance.make(year + 1, newMonth, CalendarMetrics.dayOfMonth(monthAndDay), hour, minute, second));
						}
					}
					else if (0 < prevYearDay && prevYearDay <= prevYearDays && prevYearDay > prevYearDays - 7)
					{
						/*
						 * The day might belong to the previous year.
						 */
						newWeek = mCalendarMetrics.getWeekOfYear(year - 1, prevYearDay);
						int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year - 1, prevYearDay);
						int newMonth = CalendarMetrics.packedMonth(monthAndDay);
						if (newWeek == oldWeek && mMonths != null && StaticUtils.linearSearch(mMonths, newMonth) >= 0 || mMonths == null && newMonth == month)
						{
							addInstance(Instance.make(year - 1, newMonth, CalendarMetrics.dayOfMonth(monthAndDay), hour, minute, second));
						}
					}
					break;
				}
				case MONTHLY:
				{
					if (0 < actualDay && actualDay <= yearDays && !(actualDay < startDayOfYear && year == Instance.year(start)))
					{
						int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year, actualDay);
						int newMonth = CalendarMetrics.packedMonth(monthAndDay);
						if (newMonth == month)
						{
							addInstance(Instance.setMonthAndDayOfMonth(instance, newMonth, CalendarMetrics.dayOfMonth(monthAndDay)));
						}
					}
					break;
				}
				case YEARLY:
				{
					if (0 < actualDay && actualDay <= yearDays && !(actualDay < startDayOfYear && year == Instance.year(start)))
					{
						int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year, actualDay);
						addInstance(Instance.setMonthAndDayOfMonth(instance, CalendarMetrics.packedMonth(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay)));
					}
					break;
				}
			}
		}
	}
}
