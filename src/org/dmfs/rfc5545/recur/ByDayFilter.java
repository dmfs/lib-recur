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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
final class ByDayFilter extends ByFilter
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
	 * A flag indicating that at least one of the week days has a positional prefix.
	 */
	private final boolean mHasPositions;

	/**
	 * A set of week days without positional prefix.
	 */
	private Set<Integer> mSimpleDaySet = new HashSet<Integer>();

	/**
	 * An {@link Iterator} to iterate over the instances. Since we may have positional weekdays we have to cache an entire interval and later iterate over the
	 * instanced to return them.
	 */
	private Iterator<Instance> mSetIterator;

	/**
	 * A Helper for calendar calculations.
	 */
	private final Calendar mHelper = new Calendar(Calendar.UTC, 2000, 0, 1, 0, 0, 0);

	/**
	 * The list of months if a BYMONTH part is specified in the rule. We need this to filter by month if the rule has a monthly and weekly scope.
	 */
	private final List<Integer> mMonths;


	public ByDayFilter(RecurrenceRule rule, RuleIterator previous, Calendar start)
	{
		super(previous, start, ((rule.getFreq() == Freq.YEARLY || rule.getFreq() == Freq.MONTHLY) && !rule.hasPart(Part.BYYEARDAY) && !rule
			.hasPart(Part.BYMONTHDAY)) || rule.getFreq() == Freq.WEEKLY);
		mByDay = rule.getByDayPart();

		mScope = rule.hasPart(Part.BYWEEKNO) || rule.getFreq() == Freq.WEEKLY ? (rule.hasPart(Part.BYMONTH) || rule.getFreq() == Freq.MONTHLY ? Scope.WEEKLY_AND_MONTHLY
			: Scope.WEEKLY)
			: (rule.hasPart(Part.BYMONTH) || rule.getFreq() == Freq.MONTHLY ? Scope.MONTHLY : Scope.YEARLY);

		// Get a build a set that contains the weekdays only
		boolean hasPositions = false;
		for (WeekdayNum w : mByDay)
		{
			if (w.pos != 0)
			{
				hasPositions = true;
			}
			mSimpleDaySet.add(w.weekday.toCalendarDay());
		}
		mHasPositions = hasPositions;

		// set up helper
		mHelper.setMinimalDaysInFirstWeek(4);
		mHelper.setFirstDayOfWeek(rule.getWeekStart().toCalendarDay());

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
	public Instance next()
	{
		if (mHasPositions)
		{
			// we have positional weekdays, so get an entire set and return the instances one by one.
			if (mSetIterator == null || !mSetIterator.hasNext())
			{
				mSetIterator = nextSet().iterator();
			}
			return mSetIterator.next();
		}
		else
		{
			return super.next();
		}
	}


	@Override
	boolean filter(Instance instance)
	{
		// this is called if FREQ is <= DAILY or any of BYMONTHDAY or BYYEARDAY is present, so we don't have to filter by month here

		if (!mHasPositions)
		{
			mHelper.set(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
			// calculate WEEK_OF_YEAR to enforce calculation of DAY_OF_WEEK
			mHelper.get(Calendar.WEEK_OF_YEAR);
			return !mSimpleDaySet.contains(mHelper.get(Calendar.DAY_OF_WEEK));
		}
		else
		{
			switch (mScope)
			{
				case WEEKLY:
					/*
					 * Note: if we're in a weekly scope we shouldn't be here. So we just ignore any positions.
					 */
					return !mSimpleDaySet.contains(instance.dayOfWeek);

				case WEEKLY_AND_MONTHLY:
					return !mSimpleDaySet.contains(instance.dayOfWeek);

				case MONTHLY:
				case YEARLY:
					// FIXME: filter because BYMONTHDAY or BYYEARDAY are specified in the rule
					throw new UnsupportedOperationException("not implemented yet");

				default:
					return false;
			}
		}
	}


	@Override
	void expand(TreeSet<Instance> set, Instance instance, Instance start)
	{
		for (WeekdayNum day : mByDay)
		{
			switch (mScope)
			{
				case WEEKLY:
					if (day.pos == 0 || day.pos == 1) // ignore any positional days
					{
						mHelper.set(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);

						if (instance.weekOfYear == 1 && instance.month > 0)
						{
							// this day of calendar week 1 belongs to the previous year
							mHelper.set(Calendar.YEAR, instance.year + 1);
						}
						else if (instance.weekOfYear >= 10 && instance.month == 0)
						{
							// this day of the last calendar week belongs to the next year
							mHelper.set(Calendar.YEAR, instance.year - 1);
						}
						mHelper.set(Calendar.WEEK_OF_YEAR, instance.weekOfYear);
						mHelper.set(Calendar.DAY_OF_WEEK, day.weekday.toCalendarDay());
						set.add(new Instance(mHelper));
					}
					break;

				case WEEKLY_AND_MONTHLY: // the rule is either MONTHLY with BYWEEKNO expansion, WEEKLY with BYMONTH filter or YEARLY with BYMONTH and BYWEEKNO

					if (day.pos == 0 || day.pos == 1) // ignore any positional days
					{
						mHelper.set(instance.year, 1, 1, instance.hour, instance.minute, instance.second);
						mHelper.set(Calendar.WEEK_OF_YEAR, instance.weekOfYear);
						mHelper.set(Calendar.DAY_OF_WEEK, day.weekday.toCalendarDay());

						if (mMonths != null && mMonths.contains(mHelper.get(Calendar.MONTH) + 1))
						/*
						 * the rule is WEEKLY with BYMONTH filter or MONTHLY or YEARLY with BYMONTH and BYWEEKNO filter, so filter by month because we may have
						 * overlapping weeks
						 */
						{
							set.add(new Instance(mHelper));
						}
						else if (mMonths == null && mHelper.get(Calendar.MONTH) == instance.month)
						/*
						 * the rule is MONTHLY with BYWEEKNOfilter, so add only instances in the original month
						 */
						{
							set.add(new Instance(mHelper));
						}
					}
					break;

				case MONTHLY: // the rule is MONTHLY or there is a BYMONTH filter present

					// get the first week day and the number of days of this month
					mHelper.set(instance.year, instance.month, 1, 0, 0, 0);
					mHelper.get(Calendar.WEEK_OF_YEAR);

					int weekDayOfFirstInMonth = mHelper.get(Calendar.DAY_OF_WEEK);
					int monthDays = mHelper.getActualMaximum(Calendar.DAY_OF_MONTH);

					// get the first instance of the weekday in this month
					int firstDay = (day.weekday.toCalendarDay() - weekDayOfFirstInMonth + 7) % 7 + 1;

					if (day.pos == 0)
					{
						// add all instances of this weekday of this month
						for (int dayOfMonth = firstDay; dayOfMonth <= monthDays; dayOfMonth += 7)
						{
							set.add(new Instance(instance.year, instance.month, dayOfMonth, instance.hour, instance.minute, instance.second));
						}
					}
					else
					{
						int maxDays = 1 + (monthDays - firstDay) / 7;
						// add just one position
						if (day.pos > 0 && day.pos <= maxDays || day.pos < 0 && day.pos + maxDays + 1 > 0)
						{
							set.add(new Instance(instance.year, instance.month, firstDay + (day.pos > 0 ? day.pos - 1 : day.pos + maxDays) * 7, instance.hour,
								instance.minute, instance.second));
						}
					}
					break;

				case YEARLY: // no other BY* filters are present

					mHelper.set(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);

					// calculate the first occurrence of this weekday in this year
					int firstWeekdayOfYear = (mHelper.get(Calendar.DAY_OF_YEAR) + day.weekday.toCalendarDay() - mHelper.get(Calendar.DAY_OF_WEEK) + 6) % 7 + 1;

					int yearDays = mHelper.getActualMaximum(Calendar.DAY_OF_YEAR);

					if (day.pos == 0)
					{
						// add an instance for every occurrence if this week day.
						for (int dayOfYear = firstWeekdayOfYear; dayOfYear <= yearDays; dayOfYear += 7)
						{
							mHelper.set(Calendar.DAY_OF_YEAR, dayOfYear);
							set.add(new Instance(mHelper));
						}
					}
					else
					{
						if (day.pos > 0)
						{
							int dayOfYear = firstWeekdayOfYear + (day.pos - 1) * 7;
							if (dayOfYear <= yearDays)
							{
								mHelper.set(Calendar.DAY_OF_YEAR, dayOfYear);
								set.add(new Instance(mHelper));
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
								mHelper.set(Calendar.DAY_OF_YEAR, dayOfYear);
								set.add(new Instance(mHelper));
							}
						}
					}
					break;
			}
		}
	}
}
