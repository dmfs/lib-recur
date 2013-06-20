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
 * A filter that limits or expands recurrence rules by hour.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class ByHourFilter extends ByFilter
{
	/**
	 * The hour list from the rule.
	 */
	private final List<Integer> mHours;


	public ByHourFilter(RecurrenceRule rule, RuleIterator previous, Calendar start)
	{
		super(previous, start, rule.getFreq() == Freq.YEARLY || rule.getFreq() == Freq.MONTHLY || rule.getFreq() == Freq.WEEKLY || rule.getFreq() == Freq.DAILY);
		mHours = rule.getByPart(Part.BYHOUR);
	}


	@Override
	boolean filter(Instance instance)
	{
		// check that the hour of the instance is in mHours
		return !mHours.contains(instance.hour);
	}


	@Override
	void expand(TreeSet<Instance> instances, Instance instance, Instance start)
	{
		// add a new instance for every hour in mHours
		for (int hour : mHours)
		{
			Instance newInstance = instance.clone();
			newInstance.hour = hour;
			instances.add(newInstance);
		}
	}
}
