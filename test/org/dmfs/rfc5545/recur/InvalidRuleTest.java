package org.dmfs.rfc5545.recur;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;
import org.junit.Test;


/**
 * Test handling of invalid rules. Parsing in strict modes should always throw an exception. Parsing in lax mode should return a best effort.
 * <p>
 * TODO: we test rule by equality of the String output, we should test the rules by their equals(...) method, but that's not support yet.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class InvalidRuleTest
{

	private final static List<InvalidTestRule> TEST_RULES = new ArrayList<InvalidTestRule>();


	/**
	 * Tests that invalid rules are parsed in lax modes (if possible) and a reasonable alternative result is returned.
	 */
	@Test
	public void testLaxMode()
	{
		for (InvalidTestRule rule : TEST_RULES)
		{
			rule.testLaxMode();
		}
	}


	/**
	 * Tests that all rules in this test throw when parsed in a strict mode.
	 */
	@Test
	public void testStrictMode()
	{
		for (InvalidTestRule rule : TEST_RULES)
		{
			try
			{
				rule.testStrictMode();
			}
			catch (InvalidRecurrenceRuleException e)
			{
				continue;
			}
			fail("rule didn't throw in strict mode " + rule.mInvalidRule);
		}
	}

	static
	{
		/*
		 * Add couple of invalid rules found in the wild and their expected string representation after parsing in a lax mode.
		 * 
		 * An expected value of null means the parser must throw an InvalidRecurrenceRuleException since the rule is too broken.
		 */
		TEST_RULES.add(new InvalidTestRule(";COUNT=0", null)); // can't parse rules without FREQ part
		TEST_RULES.add(new InvalidTestRule("FREQ=MONTHLY;BYDAY=0TU;INTERVAL=1", "FREQ=MONTHLY;BYDAY=TU"));
		TEST_RULES.add(new InvalidTestRule("FREQ=MONTHLY;BYDAY=2;INTERVAL=1", "FREQ=MONTHLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=0", "FREQ=MONTHLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=MONTHLY;;INTERVAL=3;BYMONTHDAY=1", "FREQ=MONTHLY;INTERVAL=3;BYMONTHDAY=1"));
		TEST_RULES.add(new InvalidTestRule("FREQ=MONTHLY;UNTIL=20120215T115959Z;;BYDAY=3WE;WKST=SU", "FREQ=MONTHLY;WKST=SU;BYDAY=3WE;UNTIL=20120215T115959Z"));
		TEST_RULES.add(new InvalidTestRule("FREQ=MONTHLY;UNTIL=20120924T215959Z;BYDAY=4", "FREQ=MONTHLY;UNTIL=20120924T215959Z"));
		TEST_RULES.add(new InvalidTestRule("FREQ=WEEKLY;FREQ=WEEKLY;INTERVAL=2", "FREQ=WEEKLY;INTERVAL=2"));
		TEST_RULES.add(new InvalidTestRule("FREQ=WEEKLY;INTERVAL=1;UNTIL=20120830T220000Z;WKST=MO;BYDAY=", "FREQ=WEEKLY;WKST=MO;UNTIL=20120830T220000Z"));
		TEST_RULES.add(new InvalidTestRule("FREQ=WEEKLY;INTERVAL=2;BYDAY=", "FREQ=WEEKLY;INTERVAL=2"));
		TEST_RULES.add(new InvalidTestRule("FREQ=WEEKLY;INTERVAL=2;UNTIL=20130221T230000Z;WKST=MO;BYDAY=",
			"FREQ=WEEKLY;INTERVAL=2;WKST=MO;UNTIL=20130221T230000Z"));
		TEST_RULES.add(new InvalidTestRule("FREQ=WEEKLY;;INTERVAL=3", "FREQ=WEEKLY;INTERVAL=3"));
		TEST_RULES.add(new InvalidTestRule("FREQ=WEEKLY;UNTIL=20120626T215959Z;BYMONTHDAY=0", "FREQ=WEEKLY;UNTIL=20120626T215959Z"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYMONTH=0;BYMONTHDAY=0", "FREQ=YEARLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYMONTH=0;BYMONTHDAY=0", "FREQ=YEARLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=0", "FREQ=YEARLY;BYMONTH=1"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYMONTH=255;BYMONTHDAY=0", "FREQ=YEARLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYMONTH=255;BYMONTHDAY=29", "FREQ=YEARLY;BYMONTHDAY=29"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYMONTHDAY=0;BYMONTH=0", "FREQ=YEARLY"));
		TEST_RULES.add(new InvalidTestRule("YEARLY;BYMONTH=11;BYDAY=1SU", null)); // FREQ keyword is missing

		/* Add more invalid rules */
		TEST_RULES.add(new InvalidTestRule("FREQU=YEARLY;BYMONTH=11;BYDAY=1SU", null)); // invalid keyword
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYMONT=11;BYDAY=1SU", "FREQ=YEARLY;BYDAY=1SU"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYMONTH=13", "FREQ=YEARLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYDAY=64WE", "FREQ=YEARLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYDAY=1UZ", "FREQ=YEARLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;BYDAY=1WE,2WE,-1WE,55WE", "FREQ=YEARLY;BYDAY=1WE,2WE,-1WE"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;INTERVAL=0", "FREQ=YEARLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=YEARLY;INTERVAL=-1", "FREQ=YEARLY"));
		TEST_RULES.add(new InvalidTestRule("FREQ=DAILY;BYWEEKNO=1,5,8", "FREQ=YEARLY;BYWEEKNO=1,5,8"));
		TEST_RULES.add(new InvalidTestRule("FREQ=MONTHLY;UNTIL=20120924T215959ZZ", "FREQ=MONTHLY;UNTIL=20120924T215959Z"));
	}

	private static class InvalidTestRule
	{
		public final String mInvalidRule;
		public final String mExpectedResult;


		public InvalidTestRule(String invalidRule, String expectedResult)
		{
			mInvalidRule = invalidRule;
			mExpectedResult = expectedResult;
		}


		public void testLaxMode()
		{
			if (mExpectedResult != null)
			{
				try
				{
					assertEquals(mExpectedResult, new RecurrenceRule(mInvalidRule, RfcMode.RFC5545_LAX).toString());
				}
				catch (InvalidRecurrenceRuleException e)
				{
					fail("did not parse " + mInvalidRule);
				}
			}
			else
			{
				try
				{
					new RecurrenceRule(mInvalidRule, RfcMode.RFC5545_LAX);
					fail("parser did not throw when parsing " + mInvalidRule);
				}
				catch (InvalidRecurrenceRuleException e)
				{
					return;
				}
				fail("wrong exception");
			}
		}


		public void testStrictMode() throws InvalidRecurrenceRuleException
		{
			new RecurrenceRule(mInvalidRule, RfcMode.RFC5545_STRICT);
		}
	}

}
