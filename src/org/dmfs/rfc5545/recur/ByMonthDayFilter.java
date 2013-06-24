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

import org.dmfs.rfc5545.recur.RecurrenceRule.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;


/**
 * A filter that limits or expands recurrence rules by day of month. This filter expands instances for YEARLY, MONTHLY and WEEKLY rules.
 * <p>
 * <strong>Note: </strong><a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a> doesn't allow BYMONTHDAY to be used with WEEKLY rules, but
 * RFC 2445 does. A reasonable solution seems to be to expand if BYYEARDAY is not specified, but expand only days that are in the same week.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByMonthDayFilter extends ByFilter
{
	/**
	 * A list of days of month to expand or let pass.
	 */
	private final int[] mMonthDays;

	/**
	 * The scope of this filter.
	 */
	private final Scope mScope;

	/**
	 * A helper to do some calendar calculations.
	 */
	private final Calendar mHelper = new Calendar(Calendar.UTC, 2000, 0, 1, 0, 0, 0);

	/**
	 * The list of months if a BYMONTH part is specified in the rule. We need this to filter by month if the rule has a monthly and weekly scope.
	 */
	private final int[] mMonths;

	/**
	 * Indicates that the expanded set needs to be sorted.
	 */
	private final boolean mNeedsSorting;


	public ByMonthDayFilter(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
	{
		/*
		 * Even though RFC 5545 doesn't explicitly say it, we filter YEARLY, MONTHLY or WEEKLY rules if BYYEARDAY has been specified. BYYEARDAY is evaluated
		 * prior to BYMONTHDAY and there is no point in expanding these days since they already are expanded.
		 */
		super(previous, calendarTools, start,
			(rule.getFreq() == Freq.YEARLY || rule.getFreq() == Freq.MONTHLY || rule.getFreq() == Freq.WEEKLY /* for RFC 2445 */)
				&& !(rule.hasPart(Part.BYYEARDAY)));

		mMonthDays = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYMONTHDAY));

		mScope = rule.hasPart(Part.BYWEEKNO) || rule.getFreq() == Freq.WEEKLY ? (rule.hasPart(Part.BYMONTH) || rule.getFreq() == Freq.MONTHLY ? Scope.WEEKLY_AND_MONTHLY
			: Scope.WEEKLY)
			: (rule.hasPart(Part.BYMONTH) || rule.getFreq() == Freq.MONTHLY ? Scope.MONTHLY : Scope.YEARLY);

		mHelper.setMinimalDaysInFirstWeek(4);
		mHelper.setFirstDayOfWeek(rule.getWeekStart().toCalendarDay());

		mNeedsSorting = mMonthDays[0] < 0;

		if (mScope == Scope.WEEKLY_AND_MONTHLY && rule.hasPart(Part.BYMONTH))
		{
			// we have to filter by month
			mMonths = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYMONTH));
		}
		else
		{
			mMonths = null;
		}

	}


	@Override
	boolean filter(long instance)
	{
		int monthDays = mCalendarMetrics.getDaysPerMonth(Instance.year(instance), Instance.month(instance));
		int dayOfMonth = Instance.dayOfMonth(instance);
		return (StaticUtils.linearSearch(mMonthDays, dayOfMonth) < 0 && StaticUtils.linearSearch(mMonthDays, dayOfMonth - 1 - monthDays) < 0)
			|| dayOfMonth > monthDays;
	}


	@Override
	void expand(LongArray set, long instance, long start)
	{
		Calendar helper = mHelper;
		CalendarMetrics calendarMetrics = mCalendarMetrics;

		int year = Instance.year(instance);
		int month = Instance.month(instance);
		int startYear = Instance.year(start);
		int startMonth = Instance.month(start);

		if (year < startYear || year == startYear && month < startMonth)
		{
			// nothing to do
			return;
		}

		int dayOfMonth = Instance.dayOfMonth(instance);
		int hour = Instance.hour(instance);
		int minute = Instance.minute(instance);
		int second = Instance.second(instance);

		int prevMonthDays = 0;
		int nextMonthDays = 0;
		if (mScope == Scope.WEEKLY || mScope == Scope.WEEKLY_AND_MONTHLY)
		{
			if (month == 0)
			{
				prevMonthDays = calendarMetrics.getDaysPerMonth(year - 1, calendarMetrics.getMonthsPerYear(year - 1) - 1);
			}
			else
			{
				prevMonthDays = calendarMetrics.getDaysPerMonth(year, month - 1);
			}

			if (month == calendarMetrics.getMonthsPerYear(year) - 1)
			{
				nextMonthDays = calendarMetrics.getDaysPerMonth(year + 1, 0);
			}
			else
			{
				nextMonthDays = calendarMetrics.getDaysPerMonth(year, month + 1);
			}
		}

		int monthDays = calendarMetrics.getDaysPerMonth(year, month);
		for (int day : mMonthDays)
		{
			int newDay = day;
			if (day < 0)
			{
				newDay = day + monthDays + 1;
			}

			switch (mScope)
			{
				case WEEKLY:
					/*
					 * Expand a WEEKLY rule by day of month. This is not supported by RFC 5545, but it's valid in RFC 2445.
					 * 
					 * We handle this case just like expanding a MONTHLY rule. The difficult part is that a week can overlap two months.
					 */

					int prevMonthDay = day;
					int nextMonthDay = day;
					if (day < 0)
					{
						prevMonthDay = day + prevMonthDays + 1;
						nextMonthDay = day + nextMonthDays + 1;
					}

					int oldWeek = calendarMetrics.getWeekOfYear(year, month, dayOfMonth);// helper.get(Calendar.WEEK_OF_YEAR);
					/*
					 * Add instance only if the week didn't change.
					 */
					int newWeek = calendarMetrics.getWeekOfYear(year, month, newDay);
					if (0 < newDay && newDay <= monthDays && newWeek == oldWeek)
					{
						set.add(Instance.make(helper));
					}
					else if (0 < nextMonthDay && nextMonthDay <= nextMonthDays && nextMonthDay < 7)
					{
						/*
						 * The day might belong to the next month.
						 * 
						 * FIXME: this won't work if the next month is shorter than 7 days (are there any calendars with months shorter than 7 days?).
						 */
						helper.set(year, month + 1, nextMonthDay, hour, minute, second);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek = helper.get(Calendar.WEEK_OF_YEAR);
						if (newWeek == oldWeek)
						{
							set.add(Instance.make(helper));
						}
					}
					else if (0 < prevMonthDay && prevMonthDay <= prevMonthDays && prevMonthDay > prevMonthDays - 7)
					{
						/*
						 * The day might belong to the previous month.
						 * 
						 * FIXME: this won't work if the previous month is shorter than 7 days (are there any calendars with months shorter than 7 days?).
						 */
						helper.set(year, month - 1, prevMonthDay, hour, minute, second);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek = helper.get(Calendar.WEEK_OF_YEAR);
						if (newWeek == oldWeek)
						{
							set.add(Instance.make(helper));
						}

					}
					break;

				case WEEKLY_AND_MONTHLY:
					/*
					 * Expand a MONTHLY rule by day of month with respect to the current week or expand a WEEKLY rule with respect to the current month.
					 * 
					 * This case is handled like the WEEKLY case, just with an additional check for the correct month.
					 */

					int prevMonthDay2 = day;
					int nextMonthDay2 = day;
					if (day < 0)
					{
						prevMonthDay2 = day + prevMonthDays + 1;
						nextMonthDay2 = day + nextMonthDays + 1;
					}

					int oldWeek2 = calendarMetrics.getWeekOfYear(year, month, dayOfMonth);// helper.get(Calendar.WEEK_OF_YEAR);
					helper.set(year, month, newDay);
					/*
					 * Add instance only if the week didn't change.
					 */
					int newWeek2 = calendarMetrics.getWeekOfYear(year, month, newDay);
					if (0 < newDay && newDay <= monthDays && newWeek2 == oldWeek2)
					{
						if (mMonths != null && StaticUtils.linearSearch(mMonths, month + 1) >= 0)
						/*
						 * the rule is WEEKLY with BYMONTH filter or MONTHLY or YEARLY with BYMONTH and BYWEEKNO filter, so filter by month because we may have
						 * overlapping weeks
						 */
						{
							set.add(Instance.make(year, month, newDay, hour, minute, second, calendarMetrics.getDayOfWeek(year, month, newDay)));
						}
						else if (mMonths == null && helper.get(Calendar.MONTH) == month)
						/*
						 * the rule is MONTHLY with BYWEEKNOfilter, so add only instances in the original month
						 */
						{
							set.add(Instance.make(year, month, newDay, hour, minute, second, calendarMetrics.getDayOfWeek(year, month, newDay)));
						}
					}
					else if (0 < nextMonthDay2 && nextMonthDay2 <= nextMonthDays && nextMonthDay2 < 7)
					{
						/*
						 * The day might belong to the next month.
						 * 
						 * FIXME: this won't work if the next month is shorter than 7 days (are there any calendars with months shorter than 7 days?).
						 */
						helper.set(year, month + 1, nextMonthDay2, hour, minute, second);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek2 = helper.get(Calendar.WEEK_OF_YEAR);
						if (newWeek2 == oldWeek2)
						{
							if (mMonths != null && StaticUtils.linearSearch(mMonths, mHelper.get(Calendar.MONTH) + 1) >= 0)
							/*
							 * the rule is WEEKLY with BYMONTH filter or MONTHLY or YEARLY with BYMONTH and BYWEEKNO filter, so filter by month because we may
							 * have overlapping weeks
							 */
							{
								set.add(Instance.make(helper));
							}
							else if (mMonths == null && helper.get(Calendar.MONTH) == month)
							/*
							 * the rule is MONTHLY with BYWEEKNOfilter, so add only instances in the original month
							 */
							{
								set.add(Instance.make(helper));
							}
						}
					}
					else if (0 < prevMonthDay2 && prevMonthDay2 <= prevMonthDays && prevMonthDay2 > prevMonthDays - 7)
					{
						/*
						 * The day might belong to the previous month.
						 * 
						 * FIXME: this won't work if the previous month is shorter than 7 days (are there any calendars with months shorter than 7 days?).
						 */
						helper.set(year, month - 1, prevMonthDay2, hour, minute, second);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek2 = helper.get(Calendar.WEEK_OF_YEAR);
						if (newWeek2 == oldWeek2)
						{
							if (mMonths != null && StaticUtils.linearSearch(mMonths, mHelper.get(Calendar.MONTH) + 1) >= 0)
							/*
							 * the rule is WEEKLY with BYMONTH filter or MONTHLY or YEARLY with BYMONTH and BYWEEKNO filter, so filter by month because we may
							 * have overlapping weeks
							 */
							{
								set.add(Instance.make(helper));
							}
							else if (mMonths == null && helper.get(Calendar.MONTH) == month)
							/*
							 * the rule is MONTHLY with BYWEEKNOfilter, so add only instances in the original month
							 */
							{
								set.add(Instance.make(helper));
							}
						}
					}
					break;

				case MONTHLY:
					/*
					 * Expand all days in the current month.
					 */
					if (0 < newDay && newDay <= monthDays)// && !(newDay < Instance.dayOfMonth(start) && month == startMonth && year == startYear))
					{
						set.add(Instance.setDayOfMonth(instance, newDay));
					}
					break;

				case YEARLY:
					/*
					 * YEARLY expansion of BYMONTHDAY is not exactly specified in RFC 5545. There are two possible ways:
					 * 
					 * 1) Handle it like MONHTLY expansion and expand only days in the current month.
					 * 
					 * 2) Expand it like BYDAY, i.e. expand the day to every occurrence of that specific monthday in the year.
					 * 
					 * Since 2) sounds more reasonable, so that's what we do here. At least there is some consistency in the expansion of BYDAY and BYMONTHDAY
					 * in that case.
					 */

					for (int i = 0; i < 12; ++i)
					{
						helper.set(year, i, 1);
						int monthDays2 = calendarMetrics.getDaysPerMonth(year, i);

						if (day < 0)
						{
							newDay = day + monthDays2 + 1;
						}

						if (0 < newDay && newDay <= monthDays2 && !(i < startMonth && year == startYear))
						{
							set.add(Instance.setMonthAndDayOfMonth(instance, i, newDay));
						}
					}
					break;
			}
		}
		if (mNeedsSorting)
		{
			set.sort();
		}
	}
}
