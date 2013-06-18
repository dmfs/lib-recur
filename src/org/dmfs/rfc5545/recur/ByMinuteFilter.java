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
 * A filter that limits or expands recurrence rules by minute.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByMinuteFilter extends ByFilter
{
	/**
	 * The list of minutes from the recurrence rule.
	 */
	private final List<Integer> mMinutes;


	public ByMinuteFilter(RecurrenceRule rule, RuleIterator previous, Calendar start)
	{
		super(previous, start, rule.getFreq() != Freq.SECONDLY && rule.getFreq() != Freq.MINUTELY);
		mMinutes = rule.getByPart(Part.BYMINUTE);
	}


	@Override
	boolean filter(Instance instance)
	{
		// filter all minutes not in the list
		return !mMinutes.contains(instance.minute);
	}


	@Override
	void expand(TreeSet<Instance> instances, Instance instance, Instance start)
	{
		// add a new instance for every minute value in the list
		for (int minute : mMinutes)
		{
			Instance newInstance = instance.clone();
			newInstance.minute = minute;
			instances.add(newInstance);
		}
	}
}
