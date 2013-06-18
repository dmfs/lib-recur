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
 * A filter that limits or expands recurrence rules by month. Months are expanded for yearly rules only.
 * <p>
 * If the rule has a weekly scope (i.e. when FREQ=WEEKLY and any by-day filter is present), this filter allows weeks that overlap the month to pass. This
 * ensures the by day filters can expand all relevant instances. The expanding by-day filter will take care of filtering days not belonging to this month.
 * </p>
 * 
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByMonthFilter extends ByFilter
{
	/**
	 * The list of months to let pass or to expand.
	 */
	private final List<Integer> mMonths;

	/**
	 * Whether to allow weeks that overlap one of the months in {@link #mMonths} to pass. This is important if the rule is weekly and a BY*DAY filter is
	 * present.
	 */
	private final boolean mAllowOverlappingWeeks;

	/**
	 * The start of the week as defined by the rule.
	 */
	private final int mWeekStart;

	/**
	 * A helper for calendar calculations.
	 */
	private final Calendar mHelper;


	public ByMonthFilter(RecurrenceRule rule, RuleIterator previous, Calendar start)
	{
		super(previous, start, rule.getFreq() == Freq.YEARLY);
		mMonths = rule.getByPart(Part.BYMONTH);

		/*
		 * If we expand day-wise in a weekly interval we'll have to keep overlapping weeks, otherwise we may loose instances.
		 * 
		 * The day filters will remove the invalid instances later.
		 */
		mAllowOverlappingWeeks = rule.getFreq() == Freq.WEEKLY && (rule.hasPart(Part.BYDAY) || rule.hasPart(Part.BYMONTHDAY) || rule.hasPart(Part.BYYEARDAY));

		if (mAllowOverlappingWeeks)
		{
			mWeekStart = rule.getWeekStart().toCalendarDay();

			mHelper = new Calendar(Calendar.UTC, 2000, 0, 1, 0, 0, 0);
			mHelper.setMinimalDaysInFirstWeek(4);
			mHelper.setFirstDayOfWeek(mWeekStart);
		}
		else
		{
			mWeekStart = 0;
			mHelper = null;
		}
	}


	@Override
	boolean filter(Instance instance)
	{
		if (!mAllowOverlappingWeeks)
		{
			return !mMonths.contains(instance.month + 1);
		}
		else
		{
			if (mMonths.contains(instance.month + 1))
			{
				return false;
			}

			mHelper.set(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
			// force calculation of the WEEK_OF_YEAR and DAY_OF_WEEK fields.
			mHelper.get(Calendar.WEEK_OF_YEAR);

			/*
			 * Check if the current week overlaps any of the months in mMonths, i.e. if the month of the end or the start of the week is in mMonths.
			 */
			mHelper.set(Calendar.DAY_OF_WEEK, mWeekStart); // set to first day of the week
			if (mMonths.contains(mHelper.get(Calendar.MONTH) + 1))
			{
				return false;
			}

			mHelper.add(Calendar.DAY_OF_MONTH, 6); // set to last day of the week
			return !mMonths.contains(mHelper.get(Calendar.MONTH) + 1);
		}
	}


	@Override
	void expand(TreeSet<Instance> set, Instance instance, Instance start)
	{
		if (instance.year < start.year)
		{
			// nothing to do (actually we should never get here)
			return;
		}

		for (int month : mMonths)
		{
			if (instance.year == start.year && month /* 1-based */<= start.month /* 0-based */)
			{
				// instance is before start, nothing to do here
				continue;
			}

			Instance newInstance = (Instance) instance.clone();
			newInstance.month = month - 1;
			set.add(newInstance);
		}
	}
}
