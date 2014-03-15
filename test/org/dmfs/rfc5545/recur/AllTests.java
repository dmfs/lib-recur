package org.dmfs.rfc5545.recur;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ CalendarMetricsTest.class, CalendarTest.class, GregorianCalendarMetricsTest.class, InstanceTest.class, InvalidRuleTest.class,
	RecurrenceEquivalenceTest.class, RecurrenceIteratorTest.class, RecurrenceParserTest.class })
public class AllTests
{

}
