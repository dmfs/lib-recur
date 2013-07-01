package org.dmfs.rfc5545.recur;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;
import org.junit.Test;


public class RecurrenceParserTest
{
	private static Set<TestRuleWithException> mRules = new HashSet<TestRuleWithException>();
	private static String defaultStart = "20120101";

	public class TestRuleWithException extends TestRule
	{

		public Exception exception;


		public TestRuleWithException(String rule)
		{
			super(rule);
		}


		public TestRuleWithException(String rule, RfcMode mode)
		{
			super(rule, mode);
		}


		public TestRuleWithException setException(Exception excp)
		{
			exception = excp;
			return this;
		}

	}


	@Test
	public void testRule()
	{
		mRules.add(new TestRuleWithException("BYDAY=MO;FREQ=WEEKLY", RfcMode.RFC2445_STRICT).setException(new InvalidRecurrenceRuleException("")));
		mRules.add(new TestRuleWithException("BYDAY=MO;FREQ=WEEKLY", RfcMode.RFC2445_LAX));
		mRules.add(new TestRuleWithException("BYDAY=MO;FREQ=WEEKLY", RfcMode.RFC5545_STRICT));
		mRules.add(new TestRuleWithException("BYDAY=MO;FREQ=WEEKLY", RfcMode.RFC5545_LAX));
		for (TestRuleWithException rule : mRules)
		{
			if (rule.start == null)
			{
				rule.setStart(defaultStart);
			}
			try
			{
				RecurrenceRule r = new RecurrenceRule(rule.rule, rule.mode);
				r.setStart(rule.start);
				RecurrenceIterator it = r.iterator();
				Set<Calendar> instances = new HashSet<Calendar>();
				int count = 0;
				while (it.hasNext())
				{
					Calendar instance = it.nextCalendar();
					instances.add(instance);
					count++;
					if (count == RecurrenceIteratorTest.MAX_ITERATIONS)
					{
						break;
					}
				}

			}
			catch (Exception e)
			{
				if (rule.exception != null)
				{

					if (!(e.getClass() == rule.exception.getClass()))
					{
						fail("Expected " + rule.exception + ", got " + e);
					}
				}
				else
				{
					e.printStackTrace();
					fail("Exception occured.");

				}

			}
		}
	}
}
