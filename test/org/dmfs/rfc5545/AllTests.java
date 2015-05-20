package org.dmfs.rfc5545;

import org.dmfs.rfc5545.recur.InvalidRuleTest;
import org.dmfs.rfc5545.recur.LongArrayTest;
import org.dmfs.rfc5545.recur.RecurrenceEquivalenceTest;
import org.dmfs.rfc5545.recur.RecurrenceIteratorTest;
import org.dmfs.rfc5545.recur.RecurrenceParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ InvalidRuleTest.class, RecurrenceEquivalenceTest.class, RecurrenceIteratorTest.class, RecurrenceParserTest.class, LongArrayTest.class })
public class AllTests
{

}
