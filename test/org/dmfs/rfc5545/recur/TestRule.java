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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;


public class TestRule
{
	public final String rule;
	public int count = -1;
	public Calendar until = null;
	public Set<Integer> months = null;
	public Set<Integer> weekdays = null;
	public Set<Integer> monthdays = null;
	public Set<Integer> weeks = null;
	public Set<Integer> hours = null;
	public Set<Integer> minutes = null;
	public Set<Integer> seconds = null;
	public boolean floating = false;
	public boolean allday = false;
	public Calendar start = null;
	public int instances = -1;
	public boolean printInstances = false;
	public static final RfcMode defaultMode = RfcMode.RFC5545_LAX;
	public RfcMode mode;

	private static final String UNTIL_REGEX = "UNTIL=(\\d{8}(T\\d{6}Z?)?)";
	private static final Pattern untilPattern = Pattern.compile(UNTIL_REGEX);


	public TestRule(String rule)
	{
		this(rule, defaultMode);

	}


	public TestRule(String rule, RfcMode mode)
	{
		this.rule = rule;
		this.mode = mode;

		Matcher matcher = untilPattern.matcher(rule);
		if (matcher.find())
		{
			_setUntil(matcher.group(1));
		}
	}


	public TestRule print()
	{
		printInstances = true;
		return this;
	}


	public TestRule setStart(String start)
	{
		this.start = Calendar.parse(start);
		return this;
	}


	public TestRule setStart(String start, String tzId)
	{
		this.start = Calendar.parse(TimeZone.getTimeZone(tzId), start);
		return this;
	}


	public TestRule setInstances(int instances)
	{
		this.instances = instances;
		return this;
	}


	public TestRule setCount(int instances)
	{
		count = instances;
		return this;
	}


	public TestRule setUntil(String lastInstance)
	{
		return this;
	}


	private TestRule _setUntil(String lastInstance)
	{
		until = Calendar.parse(lastInstance);
		floating = !lastInstance.endsWith("Z");
		allday = !lastInstance.contains("T");
		return this;
	}


	public TestRule setMonths(Integer... months)
	{
		this.months = new HashSet<Integer>();
		this.months.addAll(Arrays.asList(months));
		return this;
	}


	public TestRule setWeekdays(Integer... days)
	{
		this.weekdays = new HashSet<Integer>();
		this.weekdays.addAll(Arrays.asList(days));
		return this;
	}


	public TestRule setMonthdays(Integer... days)
	{
		this.monthdays = new HashSet<Integer>();
		this.monthdays.addAll(Arrays.asList(days));
		return this;
	}


	public TestRule setWeeks(Integer... days)
	{
		this.weeks = new HashSet<Integer>();
		this.weeks.addAll(Arrays.asList(days));
		return this;
	}


	public TestRule setHours(Integer... hours)
	{
		this.hours = new HashSet<Integer>();
		this.hours.addAll(Arrays.asList(hours));
		return this;
	}


	public TestRule setMinutes(Integer... minutes)
	{
		this.minutes = new HashSet<Integer>();
		this.minutes.addAll(Arrays.asList(minutes));
		return this;
	}


	public TestRule setSeconds(Integer... seconds)
	{
		this.seconds = new HashSet<Integer>();
		this.seconds.addAll(Arrays.asList(seconds));
		return this;
	}


	public void assertCount(int count)
	{
		if (this.count > 0 && until == null)
		{
			assertEquals(this.count, count);
		}
	}


	public void assertUntil(Calendar instance)
	{
		if (count == -1 && until != null)
		{
			String errMsg = "";
			// errMsg = "instance " + instance + " after " + until + " in rule " + rule;
			assertTrue(errMsg, !instance.after(until));
		}
	}


	public void assertMonth(Calendar instance)
	{
		if (months != null)
		{
			String errMsg = "";
			// errMsg = "month of " + instance + " not in " + months + " rule: " + rule;
			assertTrue(errMsg, months.contains(instance.get(Calendar.MONTH) + 1));
		}
	}


	public void assertWeekday(Calendar instance)
	{
		if (weekdays != null)
		{
			String errMsg = "";
			// errMsg = "weekday of " + instance + " not in " + weekdays + " rule: " + rule;
			assertTrue(errMsg, weekdays.contains(instance.get(Calendar.DAY_OF_WEEK)));
		}
	}


	public void assertMonthday(Calendar instance)
	{
		if (monthdays != null)
		{
			String errMsg = "";
			// errMsg = "monthday of " + instance + " not in " + monthdays + " rule: " + rule;
			assertTrue(errMsg, monthdays.contains(instance.get(Calendar.DAY_OF_MONTH)));
		}
	}


	public void assertWeek(Calendar instance)
	{
		if (weeks != null)
		{
			String errMsg = "";
			// errMsg = "week of " + instance + " not in " + weeks + " rule: " + rule;
			assertTrue(errMsg, weeks.contains(instance.get(Calendar.WEEK_OF_YEAR)));
		}
	}


	public void assertHours(Calendar instance)
	{
		if (hours != null)
		{
			String errMsg = "";
			// errMsg = "hour of " + instance + " not in " + hours + " rule: " + rule;
			assertTrue(errMsg, hours.contains(instance.get(Calendar.HOUR)));
		}
	}


	public void assertMinutes(Calendar instance)
	{
		if (minutes != null)
		{
			String errMsg = "";
			// errMsg = "minute of " + instance + " not in " + minutes + " rule: " + rule;
			assertTrue(errMsg, minutes.contains(instance.get(Calendar.MINUTE)));
		}
	}


	public void assertSeconds(Calendar instance)
	{
		if (seconds != null)
		{
			String errMsg = "";
			// errMsg = "hour of " + instance + " not in " + seconds + " rule: " + rule;
			assertTrue(errMsg, seconds.contains(instance.get(Calendar.SECOND)));
		}
	}


	public void testInstance(Calendar instance)
	{
		if (printInstances)
		{
			System.out.println(instance.toString());
		}
		assertMonth(instance);
		assertWeekday(instance);
		assertUntil(instance);
		assertWeek(instance);
		assertMonthday(instance);
		assertHours(instance);
		assertMinutes(instance);
		assertSeconds(instance);
	}


	public void assertInstances(int instances)
	{
		if (this.instances > 0)
		{
			String errMsg = "";
			// errMsg = "invalid number of instances for " + rule + " with start " + start;
			assertEquals(errMsg, this.instances, instances);
		}
	}


	public void testStart(Calendar instance)
	{
		if (this.start != null)
		{
			final int[] fields = { Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH };
			for (int i = 0; i < fields.length; ++i)
			{
				assertEquals(this.start.get(fields[i]), instance.get(fields[i]));
			}
		}

	}

}
