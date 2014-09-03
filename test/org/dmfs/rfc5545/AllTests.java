package org.dmfs.rfc5545;

import org.dmfs.rfc5545.calendarmetrics.CalendarMetricsTest;
import org.dmfs.rfc5545.calendarmetrics.GregorianCalendarMetricsTest;
import org.dmfs.rfc5545.calendarmetrics.JulianCalendarMetricsTest;
import org.dmfs.rfc5545.recur.InvalidRuleTest;
import org.dmfs.rfc5545.recur.RecurrenceEquivalenceTest;
import org.dmfs.rfc5545.recur.RecurrenceIteratorTest;
import org.dmfs.rfc5545.recur.RecurrenceParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ CalendarMetricsTest.class, GregorianCalendarMetricsTest.class, JulianCalendarMetricsTest.class, InstanceTest.class, InvalidRuleTest.class,
	RecurrenceEquivalenceTest.class, RecurrenceIteratorTest.class, RecurrenceParserTest.class })
public class AllTests
{

}
