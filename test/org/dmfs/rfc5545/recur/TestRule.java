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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;


public class TestRule
{
	public final String rule;
	public int count = -1;
	public DateTime until = null;
	public Set<Integer> months = null;
	public Set<Integer> weekdays = null;
	public Set<Integer> monthdays = null;
	public Set<Integer> weeks = null;
	public Set<Integer> hours = null;
	public Set<Integer> minutes = null;
	public Set<Integer> seconds = null;
	public boolean floating = false;
	public boolean allday = false;
	public DateTime start = null;
	public DateTime iterationStart = null;
	public int instances = -1;
	public boolean printInstances = false;
	public static final RfcMode defaultMode = RfcMode.RFC5545_LAX;
	public RfcMode mode;
	public DateTime lastInstance = null;

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


	public TestRule setIterationStart(DateTime start)
	{
		iterationStart = start;
		return this;
	}


	public TestRule setStart(String start)
	{
		this.start = DateTime.parse(start);
		return this;
	}


	public TestRule setStart(String start, String tzId)
	{
		this.start = DateTime.parse(tzId, start);
		return this;
	}


	public TestRule setLastInstance(String lastInstance)
	{
		this.lastInstance = DateTime.parse(lastInstance);
		return this;
	}


	public TestRule setLastInstance(String lastInstance, String tzId)
	{
		this.lastInstance = DateTime.parse(tzId, lastInstance);
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
		until = DateTime.parse(lastInstance);
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


	public void assertUntil(DateTime instance)
	{
		if (count == -1 && until != null)
		{
			String errMsg = "";
			// errMsg = "instance " + instance + " after " + until + " in rule " + rule;
			assertTrue(errMsg, !instance.after(until));
		}
	}


	public void assertMonth(DateTime instance)
	{
		if (months != null)
		{
			String errMsg = "";
			// errMsg = "month of " + instance + " not in " + months + " rule: " + rule;
			assertTrue(errMsg, months.contains(instance.getMonth() + 1));
		}
	}


	public void assertWeekday(DateTime instance)
	{
		if (weekdays != null)
		{
			String errMsg = "";
			// errMsg = "weekday of " + instance + " not in " + weekdays + " rule: " + rule;
			assertTrue(errMsg, weekdays.contains(instance.getDayOfWeek() + 1));
		}
	}


	public void assertMonthday(DateTime instance)
	{
		if (monthdays != null)
		{
			String errMsg = "";
			// errMsg = "monthday of " + instance + " not in " + monthdays + " rule: " + rule;
			assertTrue(errMsg, monthdays.contains(instance.getDayOfMonth()));
		}
	}


	public void assertWeek(DateTime instance)
	{
		if (weeks != null)
		{
			String errMsg = "";
			// errMsg = "week of " + instance + " not in " + weeks + " rule: " + rule;
			assertTrue(errMsg, weeks.contains(instance.getWeekOfYear()));
		}
	}


	public void assertHours(DateTime instance)
	{
		if (hours != null)
		{
			String errMsg = "";
			// errMsg = "hour of " + instance + " not in " + hours + " rule: " + rule;
			assertTrue(errMsg, hours.contains(instance.getHours()));
		}
	}


	public void assertMinutes(DateTime instance)
	{
		if (minutes != null)
		{
			String errMsg = "";
			// errMsg = "minute of " + instance + " not in " + minutes + " rule: " + rule;
			assertTrue(errMsg, minutes.contains(instance.getMinutes()));
		}
	}


	public void assertSeconds(DateTime instance)
	{
		if (seconds != null)
		{
			String errMsg = "";
			// errMsg = "hour of " + instance + " not in " + seconds + " rule: " + rule;
			assertTrue(errMsg, seconds.contains(instance.getSeconds()));
		}
	}


	public void assertAllDay(DateTime instance)
	{
		assertEquals(instance.isAllDay(), iterationStart.isAllDay());
	}


	public void assertTimeZone(DateTime instance)
	{
		assertEquals(instance.getTimeZone(), iterationStart.getTimeZone());
	}


	public void testInstance(DateTime instance)
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
		assertAllDay(instance);
		assertTimeZone(instance);
	}


	public void assertInstances(int instances)
	{
		if (this.instances > 0)
		{
			String errMsg = "";
			errMsg = "invalid number of instances for " + rule + " with start " + start;
			assertEquals(errMsg, this.instances, instances);
		}
	}


	public void testStart(DateTime instance)
	{
		if (this.start != null)
		{
			assertEquals(this.start.getYear(), instance.getYear());
			assertEquals(this.start.getMonth(), instance.getMonth());
			assertEquals(this.start.getDayOfMonth(), instance.getDayOfMonth());
			assertEquals(this.start.getHours(), instance.getHours());
			assertEquals(this.start.getMinutes(), instance.getMinutes());
			assertEquals(this.start.getSeconds(), instance.getSeconds());
		}

	}

}
