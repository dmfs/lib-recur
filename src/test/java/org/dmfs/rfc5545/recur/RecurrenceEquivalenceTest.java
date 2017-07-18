package org.dmfs.rfc5545.recur;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;


/**
 * Recurrence equivalence tests ensure that rules that describe the same set of instance also return the same results.
 */
public class RecurrenceEquivalenceTest
{

	private final static Set<Unit> mRules = new HashSet<Unit>();
	public static final RfcMode defaultMode = RfcMode.RFC5545_LAX;
	public static final String defaultStartDate = "20120101";

	private class Unit
	{
		private Set<TestRule> rules;


		/**
		 *
		 * @param rules
		 *            {@link TestRule}s that should be compared
		 */
		public Unit(TestRule... rules)
		{
			this.rules = new HashSet<TestRule>();
			this.rules.addAll(Arrays.asList(rules));
			for (TestRule recRule : this.rules)
			{
				if (recRule.start == null)
				{
					recRule.setStart(defaultStartDate);
				}
			}
		}


		/**
		 * Compares the instances of the given {@link TestRule}'s.
		 *
		 * @return <code>True</code> if the instances match, otherwise <code>false</code>.
		 */
		public boolean compareRules()
		{
			for (List<DateTime> instancesA : expandAll())
			{
				for (List<DateTime> instancesB : expandAll())
				{
					if (!instancesA.equals(instancesB))
					{
						return false;
					}
				}
			}
			return true;
		}


		private List<List<DateTime>> expandAll()
		{
			List<List<DateTime>> instanceCollection = new ArrayList<List<DateTime>>();
			for (TestRule recRule : rules)
			{
				instanceCollection.add(expandRule(recRule));
			}
			return instanceCollection;
		}


		private List<DateTime> expandRule(TestRule rule)
		{
			try
			{
				RecurrenceRule r = new RecurrenceRule(rule.rule, rule.mode);
				RecurrenceRuleIterator it = r.iterator(rule.start);
				List<DateTime> instances = new ArrayList<DateTime>(1000);
				int count = 0;
				while (it.hasNext())
				{
					DateTime instance = it.nextDateTime();
					instances.add(instance);
					count++;
					if (count == 10/* RecurrenceIteratorTest.MAX_ITERATIONS */)
					{
						break;
					}
				}
				return instances;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IllegalArgumentException("Invalid testrule: " + rule.rule);
			}
		}


		public Unit setStart(String start)
		{
			for (TestRule recRule : rules)
			{
				recRule.setStart(start);
			}
			return this;
		}
	}


	@Test
	public void testEquivalence()
	{
		addRules();
		for (Unit ruleUnit : mRules)
		{
			assertTrue(ruleUnit.compareRules());
		}
	}


	private void addRules()
	{
		// last work day in month
		mRules.add(new Unit(new TestRule("FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-1"), new TestRule(
			"FREQ=MONTHLY;BYDAY=-1MO,-1TU,-1WE,-1TH,-1FR;BYSETPOS=-1"), new TestRule(
			"FREQ=MONTHLY;BYMONTHDAY=26,27,28,29,30,31;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-1")));

		// first work day in month
		mRules.add(new Unit(new TestRule("FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=1"), new TestRule("FREQ=MONTHLY;BYDAY=1MO,1TU,1WE,1TH,1FR;BYSETPOS=1"),
			new TestRule("FREQ=MONTHLY;BYMONTHDAY=1,2,3;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=1")));

		// last month in year
		mRules.add(new Unit(new TestRule("FREQ=YEARLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYSETPOS=-1"), new TestRule("FREQ=MONTHLY;BYMONTH=12"), new TestRule(
			"FREQ=DAILY;BYMONTH=12;BYMONTHDAY=1")).setStart("20101201"));

		mRules.add(new Unit(new TestRule("FREQ=YEARLY;BYMONTH=1,2,3,4,5,6,7,8,9,10,11,12;BYSETPOS=-1"), new TestRule("FREQ=MONTHLY;BYMONTH=12"), new TestRule(
			"FREQ=YEARLY;BYMONTH=12;BYMONTHDAY=27"), new TestRule("FREQ=DAILY;BYMONTH=12;BYMONTHDAY=27")).setStart("20101227"));

		mRules.add(new Unit(new TestRule("FREQ=DAILY;BYMONTH=2;BYMONTHDAY=1"), new TestRule("FREQ=YEARLY;BYMONTH=2")));
		mRules.add(new Unit(new TestRule("FREQ=DAILY;BYMONTH=2;BYMONTHDAY=3"), new TestRule("FREQ=YEARLY;BYMONTH=2;BYMONTHDAY=3")).setStart("20100505"));
		mRules.add(new Unit(new TestRule("FREQ=DAILY;BYMONTH=5;BYMONTHDAY=15"), new TestRule("FREQ=MONTHLY;BYMONTH=5;BYMONTHDAY=15"), new TestRule(
			"FREQ=MONTHLY;BYMONTH=5"), new TestRule("FREQ=YEARLY;BYMONTH=5;BYMONTHDAY=15"), new TestRule("FREQ=YEARLY")).setStart("19890515"));

		mRules.add(new Unit(new TestRule("FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR,SA,SU;BYSETPOS=1,3,5,7,9"), new TestRule("FREQ=MONTHLY;BYMONTHDAY=1,3,5,7,9"))
			.setStart("20010101"));

		mRules.add(new Unit(new TestRule("FREQ=MONTHLY;INTERVAL=12;BYDAY=MO,TU,WE,TH,FR,SA,SU;BYSETPOS=1,3,-1,-3,-5"), new TestRule(
			"FREQ=YEARLY;BYYEARDAY=1,3,31,29,27")).setStart("20010101"));

		mRules.add(new Unit(new TestRule("FREQ=YEARLY;BYMONTH=1,3,5,7,8,10,12;BYMONTHDAY=25,26,27,28,29,30,31;BYDAY=TH"), new TestRule(
			"FREQ=MONTHLY;BYMONTH=1,3,5,7,8,10,12;BYDAY=TH;BYSETPOS=-1"), new TestRule(
			"FREQ=DAILY;BYMONTH=1,3,5,7,8,10,12;BYMONTHDAY=25,26,27,28,29,30,31;BYDAY=TH"), new TestRule("FREQ=MONTHLY;BYMONTH=1,3,5,7,8,10,12;BYDAY=-1TH")));

		mRules.add(new Unit(new TestRule("FREQ=HOURLY;BYSECOND=27;BYMINUTE=0,15,30,45"), new TestRule("FREQ=MINUTELY;BYMINUTE=0,15,30,45;BYSECOND=27"),
			new TestRule("FREQ=SECONDLY;BYMINUTE=0,15,30,45;BYSECOND=27")).setStart("20120101T053027"));

		mRules.add(new Unit(new TestRule("FREQ=WEEKLY;BYDAY=MO,WE,FR;BYHOUR=15"), new TestRule("FREQ=HOURLY;BYDAY=MO,WE,FR;BYHOUR=15")));
		mRules.add(new Unit(new TestRule("FREQ=WEEKLY;BYDAY=MO,WE,FR;BYHOUR=15"), new TestRule("FREQ=HOURLY;BYDAY=MO,WE,FR;BYHOUR=15")).setStart("20090713"));

	}
}
