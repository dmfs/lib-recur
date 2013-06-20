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
