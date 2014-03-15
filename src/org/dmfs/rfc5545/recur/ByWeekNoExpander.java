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

import org.dmfs.rfc5545.recur.RecurrenceRule.Part;


/**
 * A filter expands recurrence rules by week of year. This is allowed for yearly rules only.
 * <p>
 * If a BYMONTH part is present and any BY*DAY rules follows this filter also expands weeks that overlap the expanded month. That means two subsequent interval
 * sets can include the same week. The BY*DAY filters will take care of filtering those.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByWeekNoExpander extends ByExpander
{
	/**
	 * The week number to let pass or the expand.
	 */
	private final int[] mByWeekNo;

	/**
	 * The scope of this part.
	 */
	private final Scope mScope;

	/**
	 * A helper for calendar calculations.
	 * 
	 * TODO: get rid of it.
	 */
	private final Calendar mHelper = new Calendar(Calendar.UTC, 2000, 0, 1, 0, 0, 0);

	/**
	 * A flag that indicates that we have to expand weeks that overlap a month.
	 */
	private final boolean mAllowOverlappingWeeks;


	public ByWeekNoExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
	{
		super(previous, calendarTools, start);

		mByWeekNo = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYWEEKNO));

		mScope = rule.hasPart(Part.BYMONTH) ? Scope.MONTHLY : Scope.YEARLY;

		// allow overlapping weeks in MONTHLY scope and if any BY*DAY rule is present
		mAllowOverlappingWeeks = mScope == Scope.MONTHLY && (rule.hasPart(Part.BYDAY) || rule.hasPart(Part.BYMONTHDAY) || rule.hasPart(Part.BYYEARDAY));

		// initialize helper
		mHelper.setFirstDayOfWeek(rule.getWeekStart().ordinal() + 1);
		mHelper.setMinimalDaysInFirstWeek(4);
	}


	@Override
	void expand(long instance, long notBefore)
	{
		int year = Instance.year(instance);
		int month = Instance.month(instance);
		int dayOfMonth = Instance.dayOfMonth(instance);
		int hour = Instance.hour(instance);
		int minute = Instance.minute(instance);
		int second = Instance.second(instance);
		int dayOfWeek = Instance.dayOfWeek(instance);

		// get the number of weeks in that year
		int yearWeeks = mCalendarMetrics.getWeeksPerYear(year);

		for (int weekOfYear : mByWeekNo)
		{
			int actualWeek = weekOfYear;
			if (weekOfYear < 0)
			{
				actualWeek = yearWeeks + weekOfYear + 1;
			}

			if (actualWeek <= 0 || actualWeek > yearWeeks)
			{
				continue;
			}

			if (mScope == Scope.MONTHLY && mAllowOverlappingWeeks)
			{
				/*
				 * Expand instances if the week intersects instance.month. The by-day expansion will filter any instances not in that month.
				 */
				mHelper.set(year, month, dayOfMonth, hour, minute, second);
				mHelper.set(Calendar.WEEK_OF_YEAR, actualWeek);
				// maintain original day of week
				mHelper.set(Calendar.DAY_OF_WEEK, dayOfWeek);
				if (mHelper.get(Calendar.MONTH) == month)
				{
					addInstance(Instance.make(mHelper));
				}
				else
				{
					int firstDayOfWeek = mHelper.getFirstDayOfWeek();

					// check if the first day of this week is still in this month
					mHelper.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
					if (mHelper.get(Calendar.MONTH) == month)
					{
						// create a new instance and adjust day values
						int offset = (dayOfWeek - firstDayOfWeek + 7) % 7;
						addInstance(Instance.make(mHelper.get(Calendar.YEAR), mHelper.get(Calendar.MONTH), mHelper.get(Calendar.DAY_OF_MONTH) + offset, hour,
							minute, second));
					}
					else
					{
						// check if the last day of this week is still in this month
						mHelper.add(Calendar.DAY_OF_YEAR, 6);
						if (mHelper.get(Calendar.MONTH) == month)
						{
							// create a new instance and adjust day values
							int offset = (dayOfWeek - firstDayOfWeek - 6) % 7;
							addInstance(Instance.make(mHelper.get(Calendar.YEAR), mHelper.get(Calendar.MONTH), mHelper.get(Calendar.DAY_OF_MONTH) + offset,
								hour, minute, second));
						}
					}
				}
			}
			else if (mScope == Scope.MONTHLY)
			{
				/*
				 * Expand instances that are in instance.month.
				 */
				mHelper.set(year, month, dayOfMonth, hour, minute, second);
				mHelper.set(Calendar.WEEK_OF_YEAR, actualWeek);
				mHelper.set(Calendar.DAY_OF_WEEK, dayOfWeek);
				if (mHelper.get(Calendar.MONTH) == month)
				{
					addInstance(Instance.make(mHelper));
				}
			}
			else
			{
				// mScope == Scope.YEARLY
				mHelper.set(year, month, dayOfMonth, hour, minute, second);
				mHelper.set(Calendar.WEEK_OF_YEAR, actualWeek);
				mHelper.set(Calendar.DAY_OF_WEEK, dayOfWeek);
				addInstance(Instance.make(mHelper));
			}
		}
	}
}
