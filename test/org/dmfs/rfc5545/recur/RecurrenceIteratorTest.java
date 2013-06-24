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
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;
import org.junit.Before;
import org.junit.Test;


public class RecurrenceIteratorTest
{
	private final static int MAX_ITERATIONS = 10000;

	private final static Calendar FLOATING_TEST_START_DATE = Calendar.parse("19850501T133912");
	private final static Calendar ABSOLUTE_TEST_START_DATE = Calendar.parse("19850501T133912Z");
	private final static Calendar ALLDAY_TEST_START_DATE = Calendar.parse("19850501");

	private final static List<TestRule> mTestRules = new ArrayList<TestRule>();


	@Before
	public void setUp() throws Exception
	{
	}


	@Test
	public void testInstances() throws InvalidRecurrenceRuleException
	{
		for (TestRule rule : mTestRules)
		{
			try
			{
				RecurrenceRule r = new RecurrenceRule(rule.rule, rule.mode);

				if (rule.start != null)
				{
					r.setStart(rule.start);
				}
				else if (!rule.floating)
				{
					r.setStart(ABSOLUTE_TEST_START_DATE);
				}
				else if (!rule.allday)
				{
					r.setStart(FLOATING_TEST_START_DATE);
				}
				else
				{
					r.setStart(ALLDAY_TEST_START_DATE);
				}

				int count = 0;
				RecurrenceIterator it = r.iterator();
				while (it.hasNext())
				{
					Calendar instance = it.nextCalendar();
					count++;
					if (count > 1) // do not test first instance
					{
						// if (rule.printInstances) System.out.print(" "+ rule.rule+ " " + count + "  ");
						rule.testInstance(instance);
					}

					if (count == MAX_ITERATIONS)
					{
						break;
					}

				}
				// System.out.println("x " + count);
				// rule.assertInstances(count);

				if (rule.until == null && rule.count >= 0)
				{
					rule.assertCount(count);
				}
				else if (rule.until == null && rule.count < 0)
				{
					assertEquals(MAX_ITERATIONS, count);
				}
			}
			catch (IllegalArgumentException e)
			{
				throw new IllegalArgumentException("error in " + rule.rule, e);
			}
		}
	}


	@Test
	public void testMonotonicallyIncreasing() throws InvalidRecurrenceRuleException
	{
		for (TestRule rule : mTestRules)
		{
			RecurrenceRule r = new RecurrenceRule(rule.rule, rule.mode);
			if (rule.start != null)
			{
				r.setStart(rule.start);
			}
			else if (!rule.floating)
			{
				r.setStart(ABSOLUTE_TEST_START_DATE);
			}
			else if (!rule.allday)
			{
				r.setStart(FLOATING_TEST_START_DATE);
			}
			else
			{
				r.setStart(ALLDAY_TEST_START_DATE);
			}

			// no instance should be before this day
			Calendar lastInstance = new Calendar(Calendar.UTC, 1900, 0, 1, 0, 0, 0);

			int count = 0;
			RecurrenceIterator it = r.iterator();
			while (it.hasNext())
			{
				Calendar instance = it.nextCalendar();
				// check that the previous instance is always before the next instance
				assertTrue("instance no " + count + " " + lastInstance + " not before " + instance + " in rule " + rule.rule, lastInstance.before(instance));

				lastInstance = instance;
				count++;

				if (count == MAX_ITERATIONS)
				{
					break;
				}
			}
		}

	}


	/**
	 * FIXME: we need more test rules that have a fixed start date, otherwise almost no rule will be optimized
	 * 
	 * @throws InvalidRecurrenceRuleException
	 */
	@Test
	public void testOptimize() throws InvalidRecurrenceRuleException
	{
		for (TestRule rule : mTestRules)
		{
			RecurrenceRule r1 = new RecurrenceRule(rule.rule, rule.mode);
			RecurrenceRule r2 = new RecurrenceRule(rule.rule, rule.mode);
			if (rule.start != null)
			{
				r1.setStart(rule.start);
				r2.setStart(rule.start);
			}
			else if (!rule.floating)
			{
				r1.setStart(ABSOLUTE_TEST_START_DATE);
				r2.setStart(ABSOLUTE_TEST_START_DATE);
			}
			else if (!rule.allday)
			{
				r1.setStart(FLOATING_TEST_START_DATE);
				r2.setStart(FLOATING_TEST_START_DATE);
			}
			else
			{
				r1.setStart(ALLDAY_TEST_START_DATE);
				r2.setStart(ALLDAY_TEST_START_DATE);
			}
			r2.optimize();

			int count = 0;
			RecurrenceIterator i1 = r1.iterator();
			RecurrenceIterator i2 = r2.iterator();
			while (i1.hasNext() && i2.hasNext())
			{
				Calendar c1 = i1.nextCalendar();
				Calendar c2 = i2.nextCalendar();

				// check that the instances always equal
				assertEquals("instances " + c1 + " and " + c2 + " do not equal in rule " + rule.rule, c1, c2);

				count++;

				if (count == MAX_ITERATIONS)
				{
					break;
				}
			}

			assertEquals("rule not properly optimized: " + rule.rule, i1.hasNext(), i2.hasNext());
		}

	}


	/**
	 * This test ensures that the rule is correctly evaluated when you move the start to a later instance. To do so it iterates over one rule and starts a new
	 * iteration for every instance (using that instance as the start date). Then it compares the next instances of both iterations.
	 * <p>
	 * TODO: do not just check the first and the second instance, check later instances too
	 * </p>
	 * 
	 * @throws InvalidRecurrenceRuleException
	 */
	@Test
	public void testWalkingStart() throws InvalidRecurrenceRuleException
	{
		for (TestRule rule : mTestRules)
		{
			Calendar lastInstance = null;
			try
			{
				RecurrenceRule r1 = new RecurrenceRule(rule.rule, rule.mode);
				if (rule.start != null)
				{
					r1.setStart(rule.start);
				}
				else if (!rule.floating)
				{
					r1.setStart(ABSOLUTE_TEST_START_DATE);
				}
				else if (!rule.allday)
				{
					r1.setStart(FLOATING_TEST_START_DATE);
				}
				else
				{
					r1.setStart(ALLDAY_TEST_START_DATE);
				}

				RecurrenceIterator it = r1.iterator();

				if (!it.hasNext())
				{
					continue;
				}
				lastInstance = it.nextCalendar();

				RecurrenceRule r2 = new RecurrenceRule(rule.rule, rule.mode);

				int count = 1;
				while (it.hasNext() && count < MAX_ITERATIONS)
				{
					count++;

					RecurrenceIterator i2 = r2.iterator(lastInstance);

					// first instance of r2 should be lastInstance
					assertEquals("error on first instance of rule " + rule.rule + " after " + count + " iterations ", lastInstance, i2.nextCalendar());

					lastInstance = it.nextCalendar();

					Calendar upcoming2 = i2.nextCalendar();

					// check that the second instance of i2 equals the current instance of i
					assertEquals("error on rule " + rule.rule + " after " + count + " iterations ", lastInstance, upcoming2);
				}
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				fail("error during iteration of " + rule.rule + "   " + lastInstance + " : " + e.toString());
			}
		}

	}


	// @Test
	public void testSpecial() throws InvalidRecurrenceRuleException
	{
		TestRule rule = new TestRule("FREQ=MONTHLY;COUNT=3;BYDAY=TU,WE,TH;BYSETPOS=3").setCount(3);

		Calendar lastInstance = null;
		try
		{
			RecurrenceRule r1 = new RecurrenceRule(rule.rule, rule.mode);
			if (rule.start != null)
			{
				r1.setStart(rule.start);
			}
			else if (!rule.floating)
			{
				r1.setStart(ABSOLUTE_TEST_START_DATE);
			}
			else if (!rule.allday)
			{
				r1.setStart(FLOATING_TEST_START_DATE);
			}
			else
			{
				r1.setStart(ALLDAY_TEST_START_DATE);
			}

			RecurrenceIterator it = r1.iterator();

			if (!it.hasNext())
			{
				return;
			}
			lastInstance = it.nextCalendar();

			RecurrenceRule r2 = new RecurrenceRule(rule.rule, rule.mode);

			int count = 1;
			while (it.hasNext() && count < MAX_ITERATIONS)
			{
				count++;

				RecurrenceIterator i2 = r2.iterator(lastInstance);
				System.out.println("z " + lastInstance);

				// first instance of r2 should be lastInstance
				assertEquals("error on first instance of rule " + rule.rule + " after " + count + " iterations ", lastInstance, i2.nextCalendar());

				lastInstance = it.nextCalendar();
				System.out.println("x " + lastInstance);

				Calendar upcoming2 = i2.nextCalendar();

				System.out.println("n " + lastInstance + "   " + upcoming2);

				// check that the second instance of i2 equals the current instance of i
				assertEquals("error on rule " + rule.rule + " after " + count + " iterations ", lastInstance, upcoming2);
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			fail("error during iteration of " + rule.rule + "   " + lastInstance + " : " + e.toString());
		}
	}

	static
	{

		/*
		 * Add a number of recurrence rules takes from the internet.
		 */

		/* rules limited by COUNT */
		mTestRules.add(new TestRule("FREQ=DAILY;COUNT=1").setCount(1));
		mTestRules.add(new TestRule("FREQ=DAILY;COUNT=10").setCount(10));
		mTestRules.add(new TestRule("FREQ=DAILY;COUNT=5").setCount(5));
		mTestRules.add(new TestRule("FREQ=DAILY;COUNT=5;INTERVAL=10").setCount(5));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=10;COUNT=5").setCount(5));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=1;COUNT=4").setCount(4));
		mTestRules.add(new TestRule("FREQ=MINUTELY;COUNT=4;INTERVAL=90").setCount(4));
		mTestRules.add(new TestRule("FREQ=MINUTELY;COUNT=6;INTERVAL=15").setCount(6));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=2TH;COUNT=7").setCount(7).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=2TH;COUNT=8").setCount(8).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=3TH;COUNT=2").setCount(2).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;COUNT=10;BYDAY=1FR").setCount(10).setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;COUNT=10;BYMONTHDAY=1,-1").setCount(10).setMonthdays(1, 28, 29, 30, 31));
		mTestRules.add(new TestRule("FREQ=MONTHLY;COUNT=10;BYMONTHDAY=2,15").setCount(10).setMonthdays(1, 15));
		mTestRules.add(new TestRule("FREQ=MONTHLY;COUNT=10;INTERVAL=18;BYMONTHDAY=10,11,12,13,14,15").setCount(10).setMonthdays(10, 11, 12, 13, 14, 15));
		mTestRules.add(new TestRule("FREQ=MONTHLY;COUNT=10;INTERVAL=2;BYDAY=1SU,-1SU").setCount(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;COUNT=3;BYDAY=TU,WE,TH;BYSETPOS=3").setCount(3));
		mTestRules.add(new TestRule("FREQ=MONTHLY;COUNT=6;BYDAY=-2MO").setCount(6));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;COUNT=25;BYDAY=2WE,4WE").setCount(25));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3SA;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;COUNT=1000;WKST=SU").setCount(1000).setMonths(1, 2,
			3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3SA;COUNT=24;WKST=SU").setCount(24));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3SU;BYMONTH=1,2,3,4,5,10,11,12;COUNT=8;WKST=SU").setCount(8).setMonths(1, 2, 3, 4, 5, 10,
			11, 12));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3SU;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;COUNT=24;WKST=SU").setCount(24).setMonths(1, 2, 3,
			4, 5, 6, 7, 8, 9, 10, 11, 12));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3TU;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;COUNT=24;WKST=SU").setCount(24).setMonths(1, 2, 3,
			4, 5, 6, 7, 8, 9, 10, 11, 12));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+4FR;BYMONTH=1,2,3,4,5,7,8,9,10,11,12;COUNT=24;WKST=SU").setCount(24).setMonths(1, 2, 3, 4,
			5, 7, 8, 9, 10, 11, 12));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+4SA;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;COUNT=24;WKST=SU").setCount(24).setMonths(1, 2, 3,
			4, 5, 6, 7, 8, 9, 10, 11, 12));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;COUNT=24;BYDAY=2MO,4MO").setCount(24));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;COUNT=24;BYDAY=3TU").setCount(24));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=2;COUNT=10;BYDAY=1SU,-1SU").setCount(10));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=3;COUNT=10;BYMONTHDAY=10,11,12,13,14,15").setCount(10).setMonthdays(10, 11, 12, 13, 14, 15));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=10").setCount(10));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=10;BYDAY=FR").setCount(10).setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=10;BYDAY=TH").setCount(10).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=10;BYDAY=TU").setCount(10).setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=10;BYDAY=TU,TH;WKST=SU").setCount(10).setWeekdays(Calendar.TUESDAY, Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=10;BYDAY=WE").setCount(10).setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=19;BYDAY=TU").setCount(19).setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=2").setCount(2));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=30").setCount(30));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=4;INTERVAL=2;BYDAY=TU,SU").setCount(4).setWeekdays(Calendar.TUESDAY, Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=4;INTERVAL=2;BYDAY=TU,SU;WKST=SU").setCount(4).setWeekdays(Calendar.TUESDAY, Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=8;INTERVAL=2;BYDAY=TU,TH;WKST=SU").setCount(8).setWeekdays(Calendar.TUESDAY, Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;COUNT=9;BYDAY=MO").setCount(9));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;COUNT=104;WKST=SU").setCount(104).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;COUNT=300;WKST=MO").setCount(300).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;COUNT=40;WKST=SU").setCount(40).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SA;COUNT=300;WKST=MO").setCount(300).setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;COUNT=1000;WKST=SU").setCount(1000).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;COUNT=100;WKST=SU").setCount(100).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;COUNT=3;WKST=SU").setCount(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TH;COUNT=104;WKST=SU").setCount(104).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TU;COUNT=300;WKST=MO").setCount(300).setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=WE;COUNT=104;WKST=SU").setCount(104).setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=2;BYDAY=MO;COUNT=10;WKST=MO").setCount(10).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=2;BYDAY=SU;COUNT=52;WKST=SU").setCount(52).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=2;COUNT=8;WKST=SU;BYDAY=TU,TH").setCount(8).setWeekdays(Calendar.TUESDAY, Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;COUNT=1;BYDAY=WE;WKST=SU").setCount(1).setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;COUNT=1").setCount(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;COUNT=10;BYMONTH=6,7").setCount(10).setMonths(6, 7));
		mTestRules.add(new TestRule("FREQ=YEARLY;COUNT=10;INTERVAL=2;BYMONTH=1,2,3").setCount(10).setMonths(1, 2, 3));
		mTestRules.add(new TestRule("FREQ=YEARLY;COUNT=10;INTERVAL=3;BYYEARDAY=1,100,200").setCount(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;COUNT=3;INTERVAL=4").setCount(3));
		mTestRules.add(new TestRule("FREQ=YEARLY;COUNT=4;INTERVAL=4").setCount(4));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=1;BYMONTHDAY=1;COUNT=5;WKST=SU").setCount(5).setMonths(1).setMonthdays(1));

		/* rules limited by UNTIL */
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=14;UNTIL=20130620T035959Z;WKST=SU").setUntil("20130620T035959Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR;UNTIL=20130928T065959Z;WKST=SU").setUntil("20130928T065959Z").setWeekdays(
			Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=1;UNTIL=20120331T035959Z").setUntil("20120331T035959Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=1;UNTIL=20120514T220000Z").setUntil("20120514T220000Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=1;UNTIL=20130810T035959Z;WKST=SU").setUntil("20130810T035959Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=1;WKST=SU;UNTIL=20130927T065959Z").setUntil("20130927T065959Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=3;WKST=SU;UNTIL=20130607T065959Z").setUntil("20130607T065959Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=19971224T000000Z").setUntil("19971224T000000Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20000131T090000Z;BYMONTH=1").setUntil("20000131T090000Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20040903T000000Z").setUntil("20040903T000000Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20090709;WKST=SU").setUntil("20090709"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20091224T000000Z").setUntil("20091224T000000Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20110331;INTERVAL=1").setUntil("20110331"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20110428T200000Z").setUntil("20110428T200000Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20110606").setUntil("20110606"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20110715T230000Z;INTERVAL=1").setUntil("20110715T230000Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20111002T003000").setUntil("20111002T003000"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20111223T000000;BYDAY=MO,TH").setUntil("20111223T000000"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20120324T210000").setUntil("20120324T210000"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20120515").setUntil("20120515"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20130405T040000Z;INTERVAL=1").setUntil("20130405T040000Z"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20130421T041500").setUntil("20130421T041500"));
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20130807").setUntil("20130807"));
		mTestRules.add(new TestRule("FREQ=HOURLY;UNTIL=19970902T170000Z;INTERVAL=3").setUntil("19970902T170000Z"));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTHDAY=1;UNTIL=19980901T210000Z").setUntil("19980901T210000Z").setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=1MO;UNTIL=20121231T203000Z").setUntil("20121231T203000Z").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=1TU;UNTIL=20131231T203000Z").setUntil("20131231T203000Z").setWeekdays(Calendar.TUESDAY));
		mTestRules
			.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=2TH,4TH;UNTIL=20091210T200000Z").setUntil("20091210T200000Z").setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=2TU,4TU;UNTIL=20100323T210000Z").setUntil("20100323T210000Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=-1MO;BYMONTH=5;UNTIL=20140602T075959Z;WKST=SU").setUntil("20140602T075959Z").setWeekdays(
			Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+1MO;UNTIL=20161206T045959Z;WKST=SU").setUntil("20161206T045959Z").setWeekdays(
			Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+1SA,+3SA;UNTIL=20130530T035959Z;WKST=SU").setUntil("20130530T035959Z").setWeekdays(
			Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=-1SU;UNTIL=20150103T045959Z;WKST=SU").setUntil("20150103T045959Z").setWeekdays(
			Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+1TH,+3TH;UNTIL=20121217T045959Z;WKST=SU").setUntil("20121217T045959Z").setWeekdays(
			Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+1TH,+3TH;UNTIL=20140102T045959Z;WKST=SU").setUntil("20140102T045959Z").setWeekdays(
			Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+1WE;UNTIL=20140101T075959Z;WKST=SU").setUntil("20140101T075959Z").setWeekdays(
			Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+2MO;UNTIL=20150101T045959Z;WKST=SU").setUntil("20150101T045959Z").setWeekdays(
			Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+2SA;UNTIL=20150106T045959Z;WKST=SU").setUntil("20150106T045959Z").setWeekdays(
			Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+2SU;UNTIL=20140101T045959Z;WKST=SU").setUntil("20140101T045959Z").setWeekdays(
			Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3MO;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;UNTIL=20131231T235959Z;WKST=MO")
			.setUntil("20131231T235959Z").setMonths(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3MO;UNTIL=20121201T095959Z;WKST=SU").setUntil("20121201T095959Z").setWeekdays(
			Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3TH;UNTIL=20140101T045959Z;WKST=SU").setUntil("20140101T045959Z").setWeekdays(
			Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3TH;UNTIL=20140102T045959Z;WKST=SU").setUntil("20140102T045959Z").setWeekdays(
			Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3TU;BYMONTH=1,3,5,7,9,11;UNTIL=20131231T235959Z;WKST=MO").setUntil("20131231T235959Z")
			.setMonths(1, 3, 5, 7, 9, 11).setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3TU;UNTIL=20131231T235959Z;WKST=MO").setUntil("20131231T235959Z").setWeekdays(
			Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=3WE;UNTIL=20121231T183000Z").setUntil("20121231T183000Z").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+4TU;UNTIL=20140101T075959Z;WKST=SU").setUntil("20140101T075959Z").setWeekdays(
			Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+4WE;UNTIL=20131231T235959Z;WKST=MO").setUntil("20131231T235959Z").setWeekdays(
			Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1;UNTIL=20031101T233000Z").setUntil("20031101T233000Z").setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;UNTIL=20040701T065959Z;BYMONTHDAY=1").setUntil("20040701T065959Z").setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;UNTIL=20040702T065959Z;BYMONTHDAY=2").setUntil("20040702T065959Z").setMonthdays(2));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;UNTIL=20091120T045959Z;BYDAY=1FR").setUntil("20091120T045959Z").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;UNTIL=20131220T045959Z;WKST=SU").setUntil("20131220T045959Z"));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;UNTIL=20140518;BYDAY=1SU").setUntil("20140518").setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;UNTIL=20140518;BYDAY=3SU").setUntil("20140518").setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=2;BYDAY=+4TU;UNTIL=20131101T065959Z;WKST=SU").setUntil("20131101T065959Z").setWeekdays(
			Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=3;UNTIL=20210107T095959Z;WKST=SU").setUntil("20210107T095959Z"));
		mTestRules.add(new TestRule("FREQ=MONTHLY;UNTIL=19971224T000000Z;BYDAY=1FR").setUntil("19971224T000000Z").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;UNTIL=20091224T000000Z;BYDAY=1FR").setUntil("20091224T000000Z").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;UNTIL=20130827T235959Z;INTERVAL=1;BYDAY=4TU").setUntil("20130827T235959Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;UNTIL=21001230T110000Z;BYMONTHDAY=8").setUntil("21001230T110000Z").setMonthdays(8));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=FR;UNTIL=20100115T143000;WKST=MO").setUntil("20100115T143000").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=FR;UNTIL=20121214T235959Z;INTERVAL=1").setUntil("20121214T235959Z").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=FR;UNTIL=20130329T235959Z;INTERVAL=1").setUntil("20130329T235959Z").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=MO,TH;UNTIL=20050203T000000Z").setUntil("20050203T000000Z").setWeekdays(Calendar.MONDAY,
			Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR;UNTIL=20031010T000000Z").setUntil("20031010T000000Z").setWeekdays(Calendar.MONDAY,
			Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR;UNTIL=20040227T000000Z").setUntil("20040227T000000Z").setWeekdays(Calendar.MONDAY,
			Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR;UNTIL=20050408T000000Z").setUntil("20050408T000000Z").setWeekdays(Calendar.MONDAY,
			Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=MO;UNTIL=20080630T110000;WKST=MO").setUntil("20080630T110000").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=MO;UNTIL=20121210T235959Z;INTERVAL=1").setUntil("20121210T235959Z").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=MO;UNTIL=20130325T235959Z;INTERVAL=1").setUntil("20130325T235959Z").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TH;UNTIL=20050203T000000Z").setUntil("20050203T000000Z").setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TH;UNTIL=20050715T000000Z").setUntil("20050715T000000Z").setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU,FR;UNTIL=20050523T000000Z").setUntil("20050523T000000Z").setWeekdays(Calendar.TUESDAY,
			Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU,FR;UNTIL=20050715T000000Z").setUntil("20050715T000000Z").setWeekdays(Calendar.TUESDAY,
			Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU,TH,FR;UNTIL=20030718T000000Z").setUntil("20030718T000000Z").setWeekdays(Calendar.TUESDAY,
			Calendar.THURSDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20030715T000000Z").setUntil("20030715T000000Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20050201T000000Z").setUntil("20050201T000000Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20050713T000000Z").setUntil("20050713T000000Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20070731;WKST=SU").setUntil("20070731").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20070828T183000Z;WKST=SU").setUntil("20070828T183000Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU;UNTIL=20100115T123000;WKST=MO").setUntil("20100115T123000").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=WE;UNTIL=20050715T000000Z").setUntil("20050715T000000Z").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=WE;UNTIL=20081219T123000;WKST=MO").setUntil("20081219T123000").setWeekdays(Calendar.WEDNESDAY));
		mTestRules
			.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=FR;UNTIL=20140101T075959Z;WKST=SU").setUntil("20140101T075959Z").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=MO,TU,WE,TH;UNTIL=20130614T065959Z;WKST=SU").setUntil("20130614T065959Z").setWeekdays(
			Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;UNTIL=20130505T045959Z").setUntil("20130505T045959Z").setWeekdays(Calendar.MONDAY));
		mTestRules
			.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;UNTIL=20131231T235959Z;WKST=MO").setUntil("20131231T235959Z").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SA;UNTIL=20130609T065959Z;WKST=SU").setUntil("20130609T065959Z").setWeekdays(
			Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SA;UNTIL=20251004T000000Z").setUntil("20251004T000000Z").setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU,SA;UNTIL=20130625T065959Z;WKST=SU").setUntil("20130625T065959Z").setWeekdays(
			Calendar.SUNDAY, Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU,SA;UNTIL=20130930T065959Z;WKST=SU").setUntil("20130930T065959Z").setWeekdays(
			Calendar.SUNDAY, Calendar.SATURDAY));
		mTestRules
			.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;UNTIL=20130527T035959Z;WKST=SU").setUntil("20130527T035959Z").setWeekdays(Calendar.SUNDAY));
		mTestRules
			.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;UNTIL=20130528T035959Z;WKST=SU").setUntil("20130528T035959Z").setWeekdays(Calendar.SUNDAY));
		mTestRules
			.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;UNTIL=20130610T065959Z;WKST=SU").setUntil("20130610T065959Z").setWeekdays(Calendar.SUNDAY));
		mTestRules
			.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;UNTIL=20131101T035959Z;WKST=SU").setUntil("20131101T035959Z").setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;UNTIL=20171220T000000Z").setUntil("20171220T000000Z").setWeekdays(Calendar.SUNDAY));
		mTestRules
			.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;UNTIL=20210106T045959Z;WKST=SU").setUntil("20210106T045959Z").setWeekdays(Calendar.SUNDAY));
		mTestRules
			.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;UNTIL=20210109T045959Z;WKST=SU").setUntil("20210109T045959Z").setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;UNTIL=20251005T000000Z").setUntil("20251005T000000Z").setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TH;UNTIL=20120317T075959Z;WKST=SU").setUntil("20120317T075959Z").setWeekdays(
			Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TH;UNTIL=20130531T035959Z;WKST=SU").setUntil("20130531T035959Z").setWeekdays(
			Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TH;UNTIL=20130701T035959Z;WKST=SU").setUntil("20130701T035959Z").setWeekdays(
			Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TH;UNTIL=20251215T000000Z").setUntil("20251215T000000Z").setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TU;UNTIL=20100525T210000Z").setUntil("20100525T210000Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TU;UNTIL=20110508T045959Z").setUntil("20110508T045959Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TU;UNTIL=20140128T235959Z;WKST=MO").setUntil("20140128T235959Z")
			.setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TU;UNTIL=20251215T000000Z").setUntil("20251215T000000Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TU;UNTIL=20251230T000000Z").setUntil("20251230T000000Z").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=WE;UNTIL=20071231T191500").setUntil("20071231T191500").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=WE;UNTIL=20131231T235959Z;WKST=MO").setUntil("20131231T235959Z").setWeekdays(
			Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=WE;UNTIL=20250924T000000Z").setUntil("20250924T000000Z").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=WE;UNTIL=20250925T000000Z").setUntil("20250925T000000Z").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=WE;UNTIL=20251220T000000Z").setUntil("20251220T000000Z").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20091129T000000Z;BYDAY=SA").setUntil("20091129T000000Z").setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20100210T155959Z").setUntil("20100210T155959Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20110715").setUntil("20110715"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20110926").setUntil("20110926"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20120227").setUntil("20120227"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20120229").setUntil("20120229"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20120301").setUntil("20120301"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20120302").setUntil("20120302"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20120329").setUntil("20120329"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20120503T035959Z").setUntil("20120503T035959Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20140102T135959Z;WKST=SU").setUntil("20140102T135959Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=2;UNTIL=20091224T000000Z;WKST=SU;BYDAY=MO,WE,FR").setUntil("20091224T000000Z").setWeekdays(
			Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=2;UNTIL=20120321T035959Z;WKST=SU").setUntil("20120321T035959Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=2;UNTIL=21001230T173000Z;BYDAY=MO").setUntil("21001230T173000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=4;BYDAY=SA;UNTIL=20131207T220000Z").setUntil("20131207T220000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=19971007T000000Z;BYDAY=TU,TH;WKST=SU").setUntil("19971007T000000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=19971224T000000Z").setUntil("19971224T000000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=19971224T000000Z;INTERVAL=2;BYDAY=MO,WE,FR;WKST=SU").setUntil("19971224T000000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20000126T030000;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR").setUntil("20000126T030000"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20000126T030000Z;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR").setUntil("20000126T030000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20060928T233000Z;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR").setUntil("20060928T233000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20091007T000000Z;WKST=SU;BYDAY=TU,TH").setUntil("20091007T000000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20121029T100000Z").setUntil("20121029T100000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20121212T155000;WKST=SU;BYDAY=MO,WE,FR").setUntil("20121212T155000"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20130313T175959Z;BYDAY=TU").setUntil("20130313T175959Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20130420T050000Z;BYDAY=TH,SA").setUntil("20130420T050000Z"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20131231T141021;BYDAY=MO").setUntil("20131231T141021"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=21001230T173000Z;INTERVAL=2;BYDAY=MO").setUntil("21001230T173000Z"));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=-1SU;BYMONTH=10;UNTIL=20061029").setUntil("20061029").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=-1SU;BYMONTH=10;UNTIL=20061029T060000Z").setUntil("20061029T060000Z").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=-1SU;BYMONTH=10;UNTIL=20061029T070000Z").setUntil("20061029T070000Z").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=1SU;BYMONTH=4;UNTIL=20060402").setUntil("20060402"));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=1SU;BYMONTH=4;UNTIL=20060402T080000Z").setUntil("20060402T080000Z"));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=MO,WE,FR;UNTIL=20090531T000000Z").setUntil("20090531T000000Z"));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYDAY=1SU;UNTIL=19491002T010000Z").setUntil("19491002T010000Z").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU;UNTIL=20070311T020000").setUntil("20070311T020000").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYMONTHDAY=1;UNTIL=19161001T000000Z").setUntil("19161001T000000Z").setMonths(10).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYMONTHDAY=1;UNTIL=19781001T010000Z").setUntil("19781001T010000Z").setMonths(10).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYMONTHDAY=2;UNTIL=19441002T010000Z").setUntil("19441002T010000Z").setMonths(10).setMonthdays(2));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYMONTHDAY=4;UNTIL=19431004T010000Z").setUntil("19431004T010000Z").setMonths(10).setMonthdays(4));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYMONTHDAY=7;UNTIL=19461007T010000Z").setUntil("19461007T010000Z").setMonths(10).setMonthdays(7));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=11;BYMONTHDAY=18;UNTIL=19451118T010000Z").setUntil("19451118T010000Z").setMonths(11).setMonthdays(18));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=11;BYMONTHDAY=2;UNTIL=19421102T010000Z").setUntil("19421102T010000Z").setMonths(11).setMonthdays(2));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYMONTHDAY=29;UNTIL=19430329T000000Z").setUntil("19430329T000000Z").setMonths(3).setMonthdays(29));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYDAY=1MO;UNTIL=19450402T000000Z").setUntil("19450402T000000Z").setMonths(4));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYDAY=1SU;UNTIL=19800406T010000Z").setUntil("19800406T010000Z").setMonths(4));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYDAY=1SU;UNTIL=20061029T020000").setUntil("20061029T020000").setMonths(4));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYDAY=3MO;UNTIL=19180415T000000Z").setUntil("19180415T000000Z").setMonths(4));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYMONTHDAY=10;UNTIL=19490410T000000Z").setUntil("19490410T000000Z").setMonths(4).setMonthdays(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYMONTHDAY=14;UNTIL=19460414T000000Z").setUntil("19460414T000000Z").setMonths(4).setMonthdays(14));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYMONTHDAY=18;UNTIL=19480418T000000Z").setUntil("19480418T000000Z").setMonths(4).setMonthdays(18));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYMONTHDAY=1;UNTIL=19400401T000000Z").setUntil("19400401T000000Z").setMonths(4).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYMONTHDAY=30;UNTIL=19160430T210000Z").setUntil("19160430T210000Z").setMonths(4).setMonthdays(30));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYMONTHDAY=6;UNTIL=19470406T010000Z").setUntil("19470406T010000Z").setMonths(4).setMonthdays(6));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=5;BYMONTHDAY=11;UNTIL=19470510T230000Z").setUntil("19470510T230000Z").setMonths(5).setMonthdays(11));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=5;BYMONTHDAY=24;UNTIL=19450523T230000Z").setUntil("19450523T230000Z").setMonths(5).setMonthdays(24));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=6;BYMONTHDAY=29;UNTIL=19470629T010000Z").setUntil("19470629T010000Z").setMonths(6).setMonthdays(29));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=9;BYDAY=-1SU;UNTIL=19770925T010000Z").setUntil("19770925T010000Z").setMonths(9));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=9;BYDAY=-1SU;UNTIL=19950924T010000Z").setUntil("19950924T010000Z").setMonths(9));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=9;BYDAY=3MO;UNTIL=19180916T010000Z").setUntil("19180916T010000Z").setMonths(9));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=9;BYMONTHDAY=24;UNTIL=19450924T010000Z").setUntil("19450924T010000Z").setMonths(9).setMonthdays(24));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=-1SU;BYMONTH=10;UNTIL=20061029T090000Z").setUntil("20061029T090000Z").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=1SU;BYMONTH=4;UNTIL=20060402T090000Z").setUntil("20060402T090000Z").setMonths(4));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=10;BYMONTHDAY=31;UNTIL=20160102T095959Z;WKST=SU").setUntil("20160102T095959Z")
			.setMonths(10).setMonthdays(31));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=1,4,6,9;BYMONTHDAY=15;UNTIL=20220102T045959Z;WKST=SU").setUntil("20220102T045959Z")
			.setMonths(1, 4, 6, 9).setMonthdays(15));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;UNTIL=20030317;BYMONTH=3").setUntil("20030317").setMonths(3));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;UNTIL=20131225T215959Z;WKST=MO").setUntil("20131225T215959Z"));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;UNTIL=20210106T095959Z;WKST=SU").setUntil("20210106T095959Z"));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=19491002T010000Z;BYMONTH=10;BYDAY=1SU").setUntil("19491002T010000Z").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=19950924T010000Z;BYMONTH=9;BYDAY=-1SU").setUntil("19950924T010000Z").setMonths(9));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20000131T090000Z;BYDAY=SU,MO,TU,WE,TH,FR,SA").setUntil("20000131T090000Z"));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20060402T070000Z;BYDAY=1SU;BYMONTH=4").setUntil("20060402T070000Z").setMonths(4));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20061029T070000Z;BYDAY=-1SU;BYMONTH=10").setUntil("20061029T070000Z").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20110131T090000Z;BYMONTH=1;BYDAY=SU,MO,TU,WE,TH,FR,SA").setUntil("20110131T090000Z").setMonths(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20130114T235959Z").setUntil("20130114T235959Z"));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20130228;BYMONTHDAY=1;BYMONTH=3").setUntil("20130228").setMonths(3).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20150101;BYMONTHDAY=1;BYMONTH=1").setUntil("20150101").setMonths(1).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20150321;BYMONTHDAY=21;BYMONTH=3").setUntil("20150321").setMonths(3).setMonthdays(21));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20150325T000000;BYMONTH=3").setUntil("20150325T000000").setMonths(3));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20150427;BYMONTHDAY=27;BYMONTH=4").setUntil("20150427").setMonths(4).setMonthdays(27));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20150501;BYMONTHDAY=1;BYMONTH=5").setUntil("20150501").setMonths(5).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20150616;BYMONTHDAY=16;BYMONTH=6").setUntil("20150616").setMonths(6).setMonthdays(16));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20150809;BYMONTHDAY=9;BYMONTH=8").setUntil("20150809").setMonths(8).setMonthdays(9));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20150924;BYMONTHDAY=24;BYMONTH=9").setUntil("20150924").setMonths(9).setMonthdays(24));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20151216;BYMONTHDAY=16;BYMONTH=12").setUntil("20151216").setMonths(12).setMonthdays(16));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20750331T020000Z;BYMONTH=3;BYDAY=-1SU").setUntil("20750331T020000Z").setMonths(3));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=20751031T030000Z;BYMONTH=10;BYDAY=-1SU").setUntil("20751031T030000Z").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;UNTIL=21000613T000000Z;INTERVAL=1;BYMONTH=6").setUntil("21000613T000000Z").setMonths(6));
		mTestRules.add(new TestRule("UNTIL=20070721T000000Z;FREQ=DAILY").setUntil("20070721T000000Z"));

		/* unlimited test rules */
		mTestRules.add(new TestRule("FREQ=DAILY"));
		mTestRules.add(new TestRule("FREQ=DAILY;BYDAY=SU,MO,TU,WE,TH,FR,SA"));
		mTestRules.add(new TestRule("FREQ=DAILY;BYMINUTE=0,20,40;BYHOUR=9,10,11,12,13,14,15,16"));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=1"));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=1;WKST=SU"));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=2"));
		mTestRules.add(new TestRule("FREQ=DAILY;INTERVAL=31"));
		mTestRules.add(new TestRule("FREQ=DAILY;WKST=MO;"));
		// won't work with all start dates
		// mTestRules.add(new TestRule("FREQ=MINUTELY;INTERVAL=20;BYHOUR=9,10,11,12,13,14,15,16"));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=2SA;INTERVAL=1"));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=+2TH").setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=FR;BYMONTHDAY=13").setWeekdays(Calendar.FRIDAY).setMonthdays(13));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=FR;BYSETPOS=2").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-1;WKST=SU").setWeekdays(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
			Calendar.THURSDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-2").setWeekdays(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
			Calendar.THURSDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=SA;BYMONTHDAY=7,8,9,10,11,12,13").setWeekdays(Calendar.SATURDAY).setMonthdays(7, 8, 9, 10, 11, 12, 13));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=SU,MO,TU,WE,TH,FR,SA;BYSETPOS=-1;WKST=SU"));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=WE;BYSETPOS=1").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=10;BYDAY=-1SU").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=10;BYDAY=2MO").setMonths(10).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=1;BYDAY=3MO").setMonths(1).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=2;BYDAY=3MO").setMonths(2).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=4;BYDAY=1SU").setMonths(4).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=5;BYDAY=-1MO").setMonths(5).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=5;BYDAY=2SU").setMonths(5).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=5;BYDAY=3SA").setMonths(5).setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=6;BYDAY=3SU").setMonths(6).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=9;BYDAY=1MO").setMonths(9).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTHDAY=-3").setMonthdays(26, 27, 28, 29));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=0;BYDAY=1WE,3WE").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=12;BYDAY=2MO").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=12;BYDAY=3MO").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=1FR,3FR,5FR").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=1TU,2TU,4TU,5TU").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=2TU,4TU").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=SU;BYMONTHDAY=13,7,8,9,10,11,12;BYMONTH=9").setMonths(9).setWeekdays(Calendar.SUNDAY)
			.setMonthdays(13, 7, 8, 9, 10, 11, 12));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=TU;BYMONTHDAY=2,3,4,5,6,7,8;BYMONTH=11").setMonths(11).setWeekdays(Calendar.TUESDAY)
			.setMonthdays(2, 3, 4, 5, 6, 7, 8));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=WE;BYMONTHDAY=25,26,27,21,22,23,24;BYMONTH=4").setMonths(4).setWeekdays(Calendar.WEDNESDAY)
			.setMonthdays(25, 26, 27, 21, 22, 23, 24));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1").setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=1;BYSETPOS=1;BYDAY=WE").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=MONTHLY;INTERVAL=2;BYDAY=TU").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=FR").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=SU").setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=SU,MO,TU,WE,TH,FR,SA"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TH").setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TH,SA").setWeekdays(Calendar.THURSDAY, Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TH;WKST=SU").setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=TU;WKST=SU").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=WE").setWeekdays(Calendar.WEDNESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=0;BYDAY=MO,TU,WE,TH,FR").setWeekdays(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
			Calendar.THURSDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=FR;").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=FR;WKST=SU").setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=MO").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR;WKST=SU").setWeekdays(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
			Calendar.THURSDAY, Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;WKST=SU").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SA").setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=SU;WKST=SU").setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TU,WE,TH;WKST=SU").setWeekdays(Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=1;BYDAY=TU;WKST=SU").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=20;WKST=SU;BYDAY=TU").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;INTERVAL=2;WKST=SU"));
		mTestRules.add(new TestRule("FREQ=WEEKLY;WKST=SU;BYDAY=TH").setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;WKST=SU;BYDAY=TU").setWeekdays(Calendar.TUESDAY));
		mTestRules.add(new TestRule("FREQ=WEEKLY;WKST=SU;BYDAY=TU,TH").setWeekdays(Calendar.TUESDAY, Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY"));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=1SU;BYHOUR=2;BYMINUTE=0;BYMONTH=11").setMonths(11).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=1SU;BYHOUR=2;BYMINUTE=0;BYMONTH=4").setMonths(4).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=1SU;BYMONTH=10").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=-1SU;BYMONTH=10").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=1SU;BYMONTH=11").setMonths(11).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=-1SU;BYMONTH=3").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=1SU;BYMONTH=4").setMonths(4).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=20MO").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=2SU;BYHOUR=2;BYMINUTE=0;BYMONTH=3").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=2SU;BYMONTH=3").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=3SA;BYMONTH=10").setMonths(10).setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=3SA;BYMONTH=2").setMonths(2).setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=3SU;BYMONTH=8").setMonths(8).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=4SU;BYMONTH=10").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=5SU;BYHOUR=2;BYMINUTE=0;BYMONTH=10").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=MO;BYWEEKNO=20").setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=SU;BYHOUR=2;BYMINUTE=0;BYMONTH=10;BYMONTHDAY=25,26").setMonths(10).setWeekdays(Calendar.SUNDAY)
			.setMonthdays(25, 26));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=SU;BYHOUR=2;BYMINUTE=0;BYMONTH=11;BYMONTHDAY=1,2,3").setMonths(11).setWeekdays(Calendar.SUNDAY)
			.setMonthdays(1, 2, 3));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=SU;BYHOUR=2;BYMINUTE=0;BYMONTH=3;BYMONTHDAY=8,9,10").setMonths(3).setWeekdays(Calendar.SUNDAY)
			.setMonthdays(8, 9, 10));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=SU;BYHOUR=2;BYMINUTE=0;BYMONTH=4;BYMONTHDAY=1,2,3,4").setMonths(4).setWeekdays(Calendar.SUNDAY)
			.setMonthdays(1, 2, 3, 4));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=TH;BYMONTH=3").setMonths(3).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=TH;BYMONTH=6,7,8").setMonths(6, 7, 8).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMINUTE=0;BYHOUR=2;BYDAY=-1SU;BYMONTH=10").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMINUTE=0;BYHOUR=2;BYDAY=1SU;BYMONTH=11").setMonths(11).setWeekdays(Calendar.SUNDAY));
		// mTestRules.add(new TestRule("FREQ=YEARLY;BYMINUTE=0;BYHOUR=2;BYDAY=-1SU;BYMONTH=3").setMonths(3));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMINUTE=0;BYHOUR=2;BYDAY=2SU;BYMONTH=3").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=1").setMonths(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYDAY=-1FR").setMonths(10).setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYDAY=1SU").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU;BYHOUR=2;BYMINUTE=0").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYDAY=2SA").setMonths(10).setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYDAY=3FR").setMonths(10).setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYDAY=3SU").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYMONTHDAY=24,25,26,27,28,29,30;BYDAY=SA").setMonths(10).setWeekdays(Calendar.SATURDAY)
			.setMonthdays(24, 25, 26, 27, 28, 29, 30));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=10;BYMONTHDAY=9,10,11,12,13,14,15;BYDAY=SU").setMonths(10).setWeekdays(Calendar.SUNDAY)
			.setMonthdays(9, 10, 11, 12, 13, 14, 15));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=11;BYDAY=1SU").setMonths(11).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=12;BYDAY=-1SU").setMonths(12).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=12;BYDAY=FR;BYMONTHDAY=-1").setMonths(12).setWeekdays(Calendar.FRIDAY).setMonthdays(31));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=12;BYMONTHDAY=31").setMonths(12).setMonthdays(31));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=1;BYDAY=MO,TU,WE,TH,FR;BYMONTHDAY=1,2,3;BYSETPOS=1").setMonths(1)
			.setWeekdays(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY).setMonthdays(1, 2, 3));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1").setMonths(1).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=2").setMonths(1).setMonthdays(2));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=2;BYDAY=-1SU").setMonths(2).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYDAY=-1SA").setMonths(3).setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYDAY=-1TH").setMonths(3).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYDAY=2SA").setMonths(3).setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYDAY=2SU").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYDAY=3SU").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYDAY=TH").setMonths(3).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYMONTHDAY=24,25,26,27,28,29,30;BYDAY=SA").setMonths(3).setWeekdays(Calendar.SATURDAY)
			.setMonthdays(24, 25, 26, 27, 28, 29, 30));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=3;BYMONTHDAY=9,10,11,12,13,14,15;BYDAY=SU").setMonths(3).setWeekdays(Calendar.SUNDAY)
			.setMonthdays(9, 10, 11, 12, 13, 14, 15));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYDAY=-1FR").setMonths(4).setWeekdays(Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYDAY=1SU").setMonths(4).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=4;BYDAY=3SU").setMonths(4).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=5;BYDAY=-1MO;WKST=SU").setMonths(5).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=9;BYDAY=-1SA").setMonths(9).setWeekdays(Calendar.SATURDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=9;BYDAY=1SU").setMonths(9).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=9;BYDAY=-1TH").setMonths(9).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTHDAY=1;BYMONTH=1").setMonths(1).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTHDAY=1;BYMONTH=11").setMonths(11).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTHDAY=1;BYMONTH=12").setMonths(12).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTHDAY=1;BYMONTH=3").setMonths(3).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTHDAY=1;BYMONTH=6").setMonths(6).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTHDAY=1;BYMONTH=9").setMonths(9).setMonthdays(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1"));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=-1SU;BYMONTH=10").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=1SU;BYMONTH=11").setMonths(11).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=-1SU;BYMONTH=3").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=1SU;BYMONTH=4").setMonths(4).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=2SU;BYMONTH=3").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=SU;BYMONTH=1;BYSETPOS=1;WKST=SU").setMonths(1).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=SU;BYMONTH=1;BYSETPOS=-1;WKST=SU").setMonths(1).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=SU;BYMONTH=4;BYSETPOS=1;WKST=SU").setMonths(4).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYDAY=SU;BYMONTH=9;BYSETPOS=-1;WKST=SU").setMonths(9).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=1").setMonths(1));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=10").setMonths(10));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=10;BYDAY=-1SU").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=11").setMonths(11));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=11;BYDAY=1SU;WKST=MO").setMonths(11).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=11;BYDAY=4TH").setMonths(11).setWeekdays(Calendar.THURSDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=12").setMonths(12));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=1,2,3,4,5,6;BYDAY=1MO;WKST=SU").setMonths(1, 2, 3, 4, 5, 6).setWeekdays(Calendar.MONDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=1;BYMONTHDAY=18").setMonths(1).setMonthdays(18));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=1;BYMONTHDAY=29").setMonths(1).setMonthdays(29));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=2").setMonths(2));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=3").setMonths(3));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=3;BYDAY=-1SU").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=3;BYDAY=2SU;WKST=MO").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=3;BYMONTHDAY=2").setMonths(3).setMonthdays(2));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=3;BYMONTHDAY=21").setMonths(3).setMonthdays(21));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=4").setMonths(4));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=4;BYMONTHDAY=12").setMonths(4).setMonthdays(12));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=4;BYMONTHDAY=21").setMonths(4).setMonthdays(21));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=4;BYMONTHDAY=25").setMonths(4).setMonthdays(25));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=4;BYMONTHDAY=9").setMonths(4).setMonthdays(9));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=5").setMonths(5));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=6").setMonths(6));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=7").setMonths(7));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=8").setMonths(8));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=9").setMonths(9));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=SU;BYHOUR=8,9;").setMonths(1).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;INTERVAL=4;BYDAY=TU;BYMONTHDAY=2,3,4,5,6,7,8;BYMONTH=11").setMonths(11).setWeekdays(Calendar.TUESDAY)
			.setMonthdays(2, 3, 4, 5, 6, 7, 8));
		mTestRules.add(new TestRule("FREQ=YEARLY;WKST=MO;INTERVAL=1;BYMONTH=10;BYDAY=-1SU").setMonths(10).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;WKST=MO;INTERVAL=1;BYMONTH=11;BYDAY=1SU").setMonths(11).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;WKST=MO;INTERVAL=1;BYMONTH=3;BYDAY=-1SU").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;WKST=MO;INTERVAL=1;BYMONTH=3;BYDAY=2SU").setMonths(3).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;WKST=SU;BYDAY=1SU;BYMONTH=4").setMonths(4).setWeekdays(Calendar.SUNDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;WKST=SU;BYDAY=MO,WE,FR;BYMONTH=4,5").setMonths(4, 5).setWeekdays(Calendar.MONDAY, Calendar.WEDNESDAY,
			Calendar.FRIDAY));
		mTestRules.add(new TestRule("FREQ=YEARLY;WKST=SU;BYMONTH=5;BYMONTHDAY=-1 ").setMonths(5).setMonthdays(31));
		mTestRules.add(new TestRule("INTERVAL=1;FREQ=MONTHLY"));

		/*
		 * Rules with a specific number of instances
		 */

		// every day in 2012 -> 366 instances
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20121231").setStart("20120101").setUntil("20121231").setInstances(366));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=SU,MO,TH,WE,TU,FR,SA;UNTIL=20121231").setStart("20120101").setUntil("20121231").setInstances(366));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=SU,MO,TH,WE,TU,FR,SA;UNTIL=20121231").setStart("20120101").setUntil("20121231").setInstances(366));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=SU,MO,TH,WE,TU,FR,SA;UNTIL=20121231").setStart("20120101").setUntil("20121231").setInstances(366));
		mTestRules
			.add(new TestRule(
				"FREQ=MONTHLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYMONTHDAY=1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31;UNTIL=20121231")
				.setStart("20120101").setUntil("20121231").setInstances(366));
		mTestRules
			.add(new TestRule(
				"FREQ=YEARLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYMONTHDAY=1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31;UNTIL=20121231")
				.setStart("20120101").setUntil("20121231").setInstances(366));

		// every day in 2013 -> 365 instances
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20131231").setStart("20130101").setUntil("20131231").setInstances(365));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=SU,MO,TH,WE,TU,FR,SA;UNTIL=20131231").setStart("20130101").setUntil("20131231").setInstances(365));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=SU,MO,TH,WE,TU,FR,SA;UNTIL=20131231").setStart("20130101").setUntil("20131231").setInstances(365));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=SU,MO,TH,WE,TU,FR,SA;UNTIL=20131231").setStart("20130101").setUntil("20131231").setInstances(365));
		mTestRules
			.add(new TestRule(
				"FREQ=MONTHLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYMONTHDAY=1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31;UNTIL=20131231")
				.setStart("20130101").setUntil("20131231").setInstances(365));
		mTestRules
			.add(new TestRule(
				"FREQ=YEARLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYMONTHDAY=1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31;UNTIL=20131231")
				.setStart("20130101").setUntil("20131231").setInstances(365));

		// every day in 2012 and 2013 -> 731 instances
		mTestRules.add(new TestRule("FREQ=DAILY;UNTIL=20131231").setStart("20120101").setUntil("20131231").setInstances(731));
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYDAY=SU,MO,TH,WE,TU,FR,SA;UNTIL=20131231").setStart("20120101").setUntil("20131231").setInstances(731));
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=SU,MO,TH,WE,TU,FR,SA;UNTIL=20131231").setStart("20120101").setUntil("20131231").setInstances(731));
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=SU,MO,TH,WE,TU,FR,SA;UNTIL=20131231").setStart("20120101").setUntil("20131231").setInstances(731));
		mTestRules
			.add(new TestRule(
				"FREQ=MONTHLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYMONTHDAY=1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31;UNTIL=20131231")
				.setStart("20120101").setUntil("20131231").setInstances(731));
		mTestRules
			.add(new TestRule(
				"FREQ=YEARLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYMONTHDAY=1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31;UNTIL=20131231")
				.setStart("20120101").setUntil("20131231").setInstances(731));

		// almost every day in 2013 (12*10 days are missing)
		mTestRules.add(new TestRule(
			"FREQ=MONTHLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYMONTHDAY=1,2,3,4,5,6,7,18,19,20,21,22,23,24,25,26,27,28,29,30,31;UNTIL=20131231")
			.setStart("20130101").setUntil("20131231").setInstances(245)
			.setMonthdays(1, 2, 3, 4, 5, 6, 7, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31));
		mTestRules.add(new TestRule(
			"FREQ=YEARLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYMONTHDAY=1,2,3,4,5,6,7,18,19,20,21,22,23,24,25,26,27,28,29,30,31;UNTIL=20131231")
			.setStart("20130101").setUntil("20131231").setInstances(245)
			.setMonthdays(1, 2, 3, 4, 5, 6, 7, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31));

		// every week in 2012 -> 53 instances
		mTestRules.add(new TestRule("FREQ=WEEKLY;UNTIL=20121231").setStart("20120101").setUntil("20121231").setInstances(53));

		// every 2nd week in 2012 (except week 1) + start instance -> 26 instances
		mTestRules.add(new TestRule("FREQ=YEARLY;BYWEEKNO=3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49,51,53;UNTIL=20121231")
			.setStart("20120101").setUntil("20121231").setInstances(26));

		// 2 instances in each month + start instance
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYDAY=1SU,-2MO;UNTIL=20131231").setStart("20130101").setUntil("20131231").setInstances(25)
			.setWeekdays(Calendar.SUNDAY, Calendar.MONDAY));

		// 31th of Jan, March, May, July (no instances in September and November)
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=1,3,5,7,9,11;BYMONTHDAY=31;UNTIL=20131231").setStart("20130131").setUntil("20131231")
			.setMonths(1, 3, 5, 7).setMonthdays(31).setInstances(4).setMonthdays(31));

		// 2 instances in Jan + 1 in Dec.
		mTestRules.add(new TestRule("FREQ=YEARLY;BYDAY=1SU,-2MO;UNTIL=20131231").setStart("20130101").setUntil("20131231").setMonths(1, 12).setInstances(3)
			.setWeekdays(Calendar.SUNDAY, Calendar.MONDAY));

		// 3 * 31 instances
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=1,3,5;BYDAY=SU,MO,TU,WE,TH,FR,SA;UNTIL=20131231").setStart("20130101").setUntil("20131231")
			.setMonths(1, 3, 5).setInstances(3 * 31));
		// 3 * 31 instances
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=1,3,5;BYDAY=SU,MO,TU,WE,TH,FR,SA;UNTIL=20131231").setStart("20130101").setUntil("20131231")
			.setMonths(1, 3, 5).setInstances(3 * 31));

		// each month having a 31st day : 1 + 7 * 5
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTHDAY=31;UNTIL=20171231").setStart("20130101").setUntil("20171231").setMonths(1, 3, 5, 7, 8, 10, 12)
			.setMonthdays(31).setInstances(36).setMonthdays(31));

		// each month having a 29th day: 1 + 11 * 4 + 12
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTHDAY=29;UNTIL=20171231").setStart("20130101").setUntil("20171231").setMonthdays(29).setInstances(57));

		// each day between Jan 27th and Jan 31st that is in week 5: 5 + 5 + 5 + 0 + 2
		mTestRules.add(new TestRule("FREQ=YEARLY;BYMONTH=1;BYWEEKNO=5;BYMONTHDAY=27,28,29,30,31;UNTIL=20171231").setStart("20130101").setUntil("20171231")
			.setMonths(1).setWeeks(5).setMonthdays(27, 28, 29, 30, 31).setInstances(17));

		// each day between Jan 27th and Jan 31st that is in week 5: 5 + 5 + 5 + 0 + 2
		mTestRules.add(new TestRule("FREQ=MONTHLY;BYMONTH=1;BYWEEKNO=5;BYMONTHDAY=27,28,29,30,31;UNTIL=20171231", RfcMode.RFC2445_LAX).setStart("20130101")
			.setUntil("20171231").setMonths(1).setWeeks(5).setMonthdays(27, 28, 29, 30, 31).setInstances(17));

		// each day between Jan 27th and Jan 31st that is in week 5: 5 + 5 + 5 + 0 + 2
		mTestRules.add(new TestRule("FREQ=WEEKLY;BYMONTH=1;BYWEEKNO=5;BYMONTHDAY=27,28,29,30,31;UNTIL=20171231", RfcMode.RFC2445_LAX).setStart("20130101")
			.setUntil("20171231").setMonths(1).setWeeks(5).setMonthdays(27, 28, 29, 30, 31).setInstances(17));

	}
}
