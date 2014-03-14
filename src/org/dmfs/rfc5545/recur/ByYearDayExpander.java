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


/**
 * A filter that limits or expands recurrence rules by day of year. This filter expands instances for YEARLY, MONTHLY and WEEKLY rules and filters instances for
 * DAILY, HOURLY, MINUTELY and SECONDLY rules.
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
	 * A helper for calendar calculations.
	 * 
	 * TODO: get rid of it.
	 */
	private final Calendar mHelper = new Calendar(Calendar.UTC, 2000, 0, 1, 0, 0, 0);

	/**
	 * The list of months if a BYMONTH part is specified in the rule. We need this to filter by month if the rule has a monthly and weekly scope.
	 */
	private final List<Integer> mMonths;


	public ByYearDayExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
	{
		super(previous, calendarTools, start);

		mYearDays = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYYEARDAY));

		mScope = rule.getFreq() == Freq.WEEKLY || rule.hasPart(Part.BYWEEKNO) ? rule.hasPart(Part.BYMONTH) ? Scope.WEEKLY_AND_MONTHLY : Scope.WEEKLY : rule
			.getFreq() == Freq.YEARLY && !rule.hasPart(Part.BYMONTH) ? Scope.YEARLY : Scope.MONTHLY;

		mHelper.setMinimalDaysInFirstWeek(4);
		mHelper.setFirstDayOfWeek(rule.getWeekStart().ordinal());

		if (mScope == Scope.WEEKLY_AND_MONTHLY && rule.hasPart(Part.BYMONTH))
		{
			// we have to filter by month
			mMonths = rule.getByPart(Part.BYMONTH);
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
					int prevYearDays = mCalendarMetrics.getDaysPerYear(year - 1);
					int nextYearDays = mCalendarMetrics.getDaysPerYear(year + 1);

					int prevYearDay = day;
					int nextYearDay = day;
					if (day < 0)
					{
						prevYearDay = day + prevYearDays + 1;
						nextYearDay = day + nextYearDays + 1;
					}

					mHelper.set(year, month, dayOfMonth, hour, minute, second);
					int oldWeek = mHelper.get(Calendar.WEEK_OF_YEAR);
					mHelper.set(Calendar.DAY_OF_YEAR, actualDay);
					/*
					 * Add instance only if the week didn't change.
					 */
					int newWeek = mHelper.get(Calendar.WEEK_OF_YEAR);
					if (0 < actualDay && actualDay <= yearDays && newWeek == oldWeek)
					{
						addInstance(Instance.make(mHelper));
					}
					else if (0 < nextYearDay && nextYearDay <= nextYearDays && nextYearDay < 7)
					{
						/*
						 * The day might belong to the next year.
						 */
						mHelper.set(year + 1, 0, 1, hour, minute, second);
						mHelper.set(Calendar.DAY_OF_YEAR, nextYearDay);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek = mHelper.get(Calendar.WEEK_OF_YEAR);
						if (newWeek == oldWeek)
						{
							addInstance(Instance.make(mHelper));
						}
					}
					else if (0 < prevYearDay && prevYearDay <= prevYearDays && prevYearDay > prevYearDays - 7)
					{
						/*
						 * The day might belong to the previous year.
						 */
						mHelper.set(year - 1, 0, 1, hour, minute, second);
						mHelper.set(Calendar.DAY_OF_YEAR, prevYearDay);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek = mHelper.get(Calendar.WEEK_OF_YEAR);
						if (newWeek == oldWeek)
						{
							addInstance(Instance.make(mHelper));
						}

					}
					break;

				case WEEKLY_AND_MONTHLY:
					/*
					 * This case is handled almost like WEEKLY scope, just with additional month check
					 */
					int prevYearDays2 = mCalendarMetrics.getDaysPerYear(year - 1);
					int nextYearDays2 = mCalendarMetrics.getDaysPerYear(year + 1);

					int prevYearDay2 = day;
					int nextYearDay2 = day;
					if (day < 0)
					{
						prevYearDay2 = day + prevYearDays2 + 1;
						nextYearDay2 = day + nextYearDays2 + 1;
					}

					mHelper.set(year, month, dayOfMonth, hour, minute, second);
					int oldWeek2 = mHelper.get(Calendar.WEEK_OF_YEAR);
					mHelper.set(Calendar.DAY_OF_YEAR, actualDay);
					/*
					 * Add instance only if the week didn't change.
					 */
					int newWeek2 = mHelper.get(Calendar.WEEK_OF_YEAR);
					if (0 < actualDay && actualDay <= yearDays && newWeek2 == oldWeek2)
					{
						long newInstance = Instance.make(mHelper);
						int newMonth = mHelper.get(Calendar.MONTH);
						if (mMonths != null && mMonths.contains(newMonth) || mMonths == null && newMonth == month)
						{
							addInstance(newInstance);
						}
					}
					else if (0 < nextYearDay2 && nextYearDay2 <= nextYearDays2 && nextYearDay2 < 7)
					{
						/*
						 * The day might belong to the next year.
						 */
						mHelper.set(year + 1, 0, 1, hour, minute, second);
						mHelper.set(Calendar.DAY_OF_YEAR, nextYearDay2);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek2 = mHelper.get(Calendar.WEEK_OF_YEAR);
						int newMonth = mHelper.get(Calendar.MONTH);
						if (newWeek2 == oldWeek2 && mMonths != null && mMonths.contains(newMonth) || mMonths == null && newMonth == month)
						{
							addInstance(Instance.make(mHelper));
						}
					}
					else if (0 < prevYearDay2 && prevYearDay2 <= prevYearDays2 && prevYearDay2 > prevYearDays2 - 7)
					{
						/*
						 * The day might belong to the previous year.
						 */
						mHelper.set(year - 1, 0, 1, hour, minute, second);
						mHelper.set(Calendar.DAY_OF_YEAR, prevYearDay2);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek2 = mHelper.get(Calendar.WEEK_OF_YEAR);
						int newMonth = mHelper.get(Calendar.MONTH);
						if (newWeek2 == oldWeek2 && mMonths != null && mMonths.contains(newMonth) || mMonths == null && newMonth == month)
						{
							addInstance(Instance.make(mHelper));
						}

					}
					break;

				case MONTHLY:
					if (0 < actualDay && actualDay <= yearDays && !(actualDay < startDayOfYear && year == Instance.year(start)))
					{
						mHelper.set(year, month, dayOfMonth, hour, minute, second);
						mHelper.set(Calendar.DAY_OF_YEAR, actualDay);
						if (mHelper.get(Calendar.MONTH) == month)
						{
							addInstance(Instance.make(mHelper));
						}
					}
					break;

				case YEARLY:
					if (0 < actualDay && actualDay <= yearDays && !(actualDay < startDayOfYear && year == Instance.year(start)))
					{
						mHelper.set(year, month, dayOfMonth, hour, minute, second);
						mHelper.set(Calendar.DAY_OF_YEAR, actualDay);
						addInstance(Instance.make(mHelper));
					}
					break;
			}
		}
	}
}
