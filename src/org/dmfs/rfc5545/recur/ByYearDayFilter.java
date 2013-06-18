/*
 * Copyright (C) 2013 Marten Gajda <marten@dmfs.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package org.dmfs.rfc5545.recur;

import java.util.List;
import java.util.TreeSet;

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
final class ByYearDayFilter extends ByFilter
{
	/**
	 * The year days to let pass or to expand.
	 */
	private final List<Integer> mYearDays;

	/**
	 * The scope of this rule.
	 */
	private final Scope mScope;

	/**
	 * A helper for calendar calculations.
	 */
	private final Calendar mHelper = new Calendar(Calendar.UTC, 2000, 0, 1, 0, 0, 0);

	/**
	 * The list of months if a BYMONTH part is specified in the rule. We need this to filter by month if the rule has a monthly and weekly scope.
	 */
	private final List<Integer> mMonths;


	public ByYearDayFilter(RecurrenceRule rule, RuleIterator previous, Calendar start)
	{
		super(previous, start, rule.getFreq() == Freq.YEARLY || rule.getFreq() == Freq.MONTHLY || rule.getFreq() == Freq.WEEKLY);

		mYearDays = rule.getByPart(Part.BYYEARDAY);

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
	boolean filter(Instance instance)
	{
		mHelper.set(instance.year, 0, 1);
		int yearDays = mHelper.getActualMaximum(Calendar.DAY_OF_YEAR);
		return !mYearDays.contains(instance.dayOfYear) && !mYearDays.contains(instance.dayOfYear - yearDays) || instance.dayOfYear > yearDays;
	}


	@Override
	void expand(TreeSet<Instance> set, Instance instance, Instance start)
	{
		if (instance.year < start.year)
		{
			// nothing to do
			return;
		}

		mHelper.set(instance.year, 1, 1);
		int yearDays = mHelper.getActualMaximum(Calendar.DAY_OF_YEAR);
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
					mHelper.set(instance.year - 1, 0, 1);
					int prevYearDays = mHelper.getActualMaximum(Calendar.DAY_OF_YEAR);
					mHelper.set(instance.year + 1, 0, 1);
					int nextYearDays = mHelper.getActualMaximum(Calendar.DAY_OF_YEAR);

					int prevYearDay = day;
					int nextYearDay = day;
					if (day < 0)
					{
						prevYearDay = day + prevYearDays + 1;
						nextYearDay = day + nextYearDays + 1;
					}

					mHelper.set(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
					int oldWeek = mHelper.get(Calendar.WEEK_OF_YEAR);
					mHelper.set(Calendar.DAY_OF_YEAR, actualDay);
					/*
					 * Add instance only if the week didn't change.
					 */
					int newWeek = mHelper.get(Calendar.WEEK_OF_YEAR);
					if (0 < actualDay && actualDay <= yearDays && newWeek == oldWeek)
					{
						Instance newInstance = new Instance(mHelper);
						set.add(newInstance);
					}
					else if (0 < nextYearDay && nextYearDay <= nextYearDays && nextYearDay < 7)
					{
						/*
						 * The day might belong to the next year.
						 */
						mHelper.set(instance.year + 1, 0, 1, instance.hour, instance.minute, instance.second);
						mHelper.set(Calendar.DAY_OF_YEAR, nextYearDay);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek = mHelper.get(Calendar.WEEK_OF_YEAR);
						if (newWeek == oldWeek)
						{
							Instance newInstance = new Instance(mHelper);
							set.add(newInstance);
						}
					}
					else if (0 < prevYearDay && prevYearDay <= prevYearDays && prevYearDay > prevYearDays - 7)
					{
						/*
						 * The day might belong to the previous year.
						 */
						mHelper.set(instance.year - 1, 0, 1, instance.hour, instance.minute, instance.second);
						mHelper.set(Calendar.DAY_OF_YEAR, prevYearDay);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek = mHelper.get(Calendar.WEEK_OF_YEAR);
						if (newWeek == oldWeek)
						{
							Instance newInstance = new Instance(mHelper);
							set.add(newInstance);
						}

					}
					break;

				case WEEKLY_AND_MONTHLY:
					/*
					 * This case is handled almost like WEEKLY scope, just with additional month check
					 */
					mHelper.set(instance.year - 1, 0, 1);
					int prevYearDays2 = mHelper.getActualMaximum(Calendar.DAY_OF_YEAR);
					mHelper.set(instance.year + 1, 0, 1);
					int nextYearDays2 = mHelper.getActualMaximum(Calendar.DAY_OF_YEAR);

					int prevYearDay2 = day;
					int nextYearDay2 = day;
					if (day < 0)
					{
						prevYearDay2 = day + prevYearDays2 + 1;
						nextYearDay2 = day + nextYearDays2 + 1;
					}

					mHelper.set(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
					int oldWeek2 = mHelper.get(Calendar.WEEK_OF_YEAR);
					mHelper.set(Calendar.DAY_OF_YEAR, actualDay);
					/*
					 * Add instance only if the week didn't change.
					 */
					int newWeek2 = mHelper.get(Calendar.WEEK_OF_YEAR);
					if (0 < actualDay && actualDay <= yearDays && newWeek2 == oldWeek2)
					{
						Instance newInstance = new Instance(mHelper);
						int newMonth = mHelper.get(Calendar.MONTH);
						if (mMonths != null && mMonths.contains(newMonth) || mMonths == null && newMonth == instance.month)
						{
							set.add(newInstance);
						}
					}
					else if (0 < nextYearDay2 && nextYearDay2 <= nextYearDays2 && nextYearDay2 < 7)
					{
						/*
						 * The day might belong to the next year.
						 */
						mHelper.set(instance.year + 1, 0, 1, instance.hour, instance.minute, instance.second);
						mHelper.set(Calendar.DAY_OF_YEAR, nextYearDay2);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek2 = mHelper.get(Calendar.WEEK_OF_YEAR);
						int newMonth = mHelper.get(Calendar.MONTH);
						if (newWeek2 == oldWeek2 && mMonths != null && mMonths.contains(newMonth) || mMonths == null && newMonth == instance.month)
						{
							Instance newInstance = new Instance(mHelper);
							set.add(newInstance);
						}
					}
					else if (0 < prevYearDay2 && prevYearDay2 <= prevYearDays2 && prevYearDay2 > prevYearDays2 - 7)
					{
						/*
						 * The day might belong to the previous year.
						 */
						mHelper.set(instance.year - 1, 0, 1, instance.hour, instance.minute, instance.second);
						mHelper.set(Calendar.DAY_OF_YEAR, prevYearDay2);
						/*
						 * Add instance only if the week didn't change.
						 */
						newWeek2 = mHelper.get(Calendar.WEEK_OF_YEAR);
						int newMonth = mHelper.get(Calendar.MONTH);
						if (newWeek2 == oldWeek2 && mMonths != null && mMonths.contains(newMonth) || mMonths == null && newMonth == instance.month)
						{
							Instance newInstance = new Instance(mHelper);
							set.add(newInstance);
						}

					}
					break;

				case MONTHLY:
					if (0 < actualDay && actualDay <= yearDays && !(actualDay < start.dayOfYear && instance.year == start.year))
					{
						mHelper.set(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
						mHelper.set(Calendar.DAY_OF_YEAR, actualDay);
						if (mHelper.get(Calendar.MONTH) == instance.month)
						{
							set.add(new Instance(mHelper));
						}
					}
					break;

				case YEARLY:
					if (0 < actualDay && actualDay <= yearDays && !(actualDay < start.dayOfYear && instance.year == start.year))
					{
						mHelper.set(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
						mHelper.set(Calendar.DAY_OF_YEAR, actualDay);
						set.add(new Instance(mHelper));
					}
					break;
			}
		}
	}
}
