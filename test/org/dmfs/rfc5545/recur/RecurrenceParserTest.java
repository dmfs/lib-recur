package org.dmfs.rfc5545.recur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;
import org.junit.Test;


public class RecurrenceParserTest
{
  private static Set<TestRuleWithException> mRules = new HashSet<TestRuleWithException>();
  private static String defaultStart = "20120101";
  private static final String[] byRules = { "INTERVAL=2", "BYSECOND=2", "BYMINUTE=2", "BYHOUR=2", "BYDAY=MO", "BYMONTHDAY=2", "BYYEARDAY=2", "BYWEEKNO=2",
    "BYMONTH=2", "WKST=MO" };

  public class TestRuleWithException extends TestRule
  {

    public Exception exception;
    public Set<String> invalidRules = new HashSet<String>();
    public Set<String> mustContain = new HashSet<String>();


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


    public TestRuleWithException setInstances(int instances)
    {
      super.setInstances(instances);
      return this;
    }


    public TestRuleWithException setUntil(String until)
    {
      super.setUntil(until);
      return this;
    }


    /**
     * Sets a list of rules that should be dropped after parsing.
     * 
     * @param invalidRules
     *            the rules
     * @return
     */
    public TestRuleWithException setInvalidRules(String... invalidRules)
    {
      this.invalidRules = new HashSet<String>();
      this.invalidRules.addAll(Arrays.asList(invalidRules));
      return this;
    }


    /**
     * Asserts that the rules set in {@link setInvalidRules} are really dropped.
     * 
     * @param rule
     *            Rule that is returned by {@link RecurrenceRule.toString}
     */
    public void assertInvalidRules(String rule)
    {
      for (String droppedRule : invalidRules)
      {
        assertFalse("Invalid rule detected that should have been dropped", rule.contains(droppedRule));
      }
    }


    /**
     * A set of attributes the toString() output must contain.
     * 
     * @param mustContain
     *            the attributes
     */

    public TestRuleWithException setObligatoryRuleParts(String... mustContain)
    {
      this.mustContain = new HashSet<String>();
      this.mustContain.addAll(Arrays.asList(mustContain));
      return this;
    }


    /**
     * Asserts that the toString() ouput contains the rule parts in <code>mustContain</code>
     * 
     * @param rule
     *            Rule that is returned by RecurrenceRule.toString
     */
    public void assertObligatoryRuleParts(String rule)
    {
      for (String obligatoryRule : mustContain)
      {
        assertTrue("Missing rule in the output of toString", rule.contains(obligatoryRule));
      }
    }

  }


  @Test
  public void testXParts() throws InvalidRecurrenceRuleException
  {
    RecurrenceRule r1 = new RecurrenceRule("FREQ=YEARLY;X-PART=1", RfcMode.RFC2445_STRICT);

    assertEquals("1", r1.getXPart("X-PART"));
    assertEquals(null, r1.getXPart("X-PARTXY"));
    assertTrue(r1.hasXPart("X-PART"));
    assertFalse(r1.hasXPart("X-PARTXY"));

    // override x-part

    r1.setXPart("X-PART", "a");

    assertEquals("a", r1.getXPart("X-PART"));
    assertEquals(null, r1.getXPart("X-PARTXY"));
    assertTrue(r1.hasXPart("X-PART"));
    assertFalse(r1.hasXPart("X-PARTXY"));
    assertEquals("FREQ=YEARLY;X-PART=a", r1.toString());

    // add x-part

    r1.setXPart("X-PARTXY", "xy");

    assertEquals("a", r1.getXPart("X-PART"));
    assertEquals("xy", r1.getXPart("X-PARTXY"));
    assertTrue(r1.hasXPart("X-PART"));
    assertTrue(r1.hasXPart("X-PARTXY"));
    assertTrue(r1.toString().contains(";X-PART=a"));
    assertTrue(r1.toString().contains(";X-PARTXY=xy"));

    // remove x-part
    r1.setXPart("X-PART", null);

    assertEquals(null, r1.getXPart("X-PART"));
    assertEquals("xy", r1.getXPart("X-PARTXY"));
    assertFalse(r1.hasXPart("X-PART"));
    assertTrue(r1.hasXPart("X-PARTXY"));
    assertEquals("FREQ=YEARLY;X-PARTXY=xy", r1.toString());

    // remove x-part
    r1.setXPart("X-PARTXY", null);

    assertEquals(null, r1.getXPart("X-PART"));
    assertEquals(null, r1.getXPart("X-PARTXY"));
    assertFalse(r1.hasXPart("X-PART"));
    assertFalse(r1.hasXPart("X-PARTXY"));
    assertEquals("FREQ=YEARLY", r1.toString());
  }


  public void addInvalidWhiteSpaceTests()
  {
    final String s = "FREQ=YEARLY;BYMONTH=12";
    for (int i = 0; i < s.length(); ++i)
    {
      StringBuffer sb = new StringBuffer(s);
      sb.insert(i, " ");
      mRules.add(new TestRuleWithException(sb.toString(), RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("Invalid whitespace - "
        + sb.toString())));
    }
  }


  /**
   * Keywords must only occur once.
   */
  private void addUniqueKeyWordTests()
  {
    String byRuleTemplate = "FREQ=YEARLY;";
    for (int i = 0; i < byRules.length; ++i)
    {
      String rule = byRuleTemplate + byRules[i] + ";" + byRules[i];
      mRules.add(new TestRuleWithException(rule, RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    }
  }


  @Test
  public void testRule()
  {
    /**
     * In RFC2445 a RecurrenceRule has to start with "FREQ=", in RFC5545 the order is arbitrary but it must occur.
     */
    mRules.add(new TestRuleWithException("BYDAY=MO;FREQ=WEEKLY", RfcMode.RFC2445_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("BYDAY=MO;FREQ=WEEKLY", RfcMode.RFC2445_LAX).setInstances(0).setUntil("20130101"));
    mRules.add(new TestRuleWithException("BYDAY=MO;FREQ=WEEKLY", RfcMode.RFC5545_STRICT));
    mRules.add(new TestRuleWithException("BYDAY=MO;FREQ=WEEKLY", RfcMode.RFC5545_LAX));

    /**
     * Missing FREQ=
     */
    mRules.add(new TestRuleWithException("BYDAY=MO", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("Without 'FREQ='")));
    mRules.add(new TestRuleWithException("BYDAY=MO", RfcMode.RFC2445_STRICT).setException(new InvalidRecurrenceRuleException("without 'FREQ='")));
    mRules.add(new TestRuleWithException("BYDAY=MO", RfcMode.RFC5545_LAX).setException(new InvalidRecurrenceRuleException("Without 'FREQ='")));
    mRules.add(new TestRuleWithException("BYDAY=MO", RfcMode.RFC2445_LAX).setException(new InvalidRecurrenceRuleException("without 'FREQ='")));

    /**
     * Duplicate FREQ
     */
    mRules.add(new TestRuleWithException("FREQ=WEEKLY;BYDAY=MO;FREQ=DAILY", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    // mRules.add(new TestRuleWithException("FREQ=WEEKLY;BYDAY=MO;FREQ=DAILY", RfcMode.RFC5545_LAX).setException(new InvalidRecurrenceRuleException("")));

    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYMONHT=2", RfcMode.RFC5545_STRICT)
      .setException(new InvalidRecurrenceRuleException("unknown keyword")));

    /**
     * Missing value for BYxxx rule.
     */
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYMONTH=", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYMONTH=", RfcMode.RFC5545_LAX).setInstances(0));

    /**
     * Invalid delimiter.
     */
    mRules.add(new TestRuleWithException("FREQ=YEARLY,BYMONTH=27", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));

    /**
     * COUNT and UNTIL must not occur in the same rule.
     */
    mRules
      .add(new TestRuleWithException("FREQ=DAILY;UNTIL=20130101;COUNT=12", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));

    /**
     * Values are out of range.
     */
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYMONTH=13", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYHOUR=25", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYMINUTE=61", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYSECOND=61", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYMONTHDAY=32", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));

    /**
     * BYMONTHDAY must not occur if the FREQ is set to WEEKLY.
     */
    mRules.add(new TestRuleWithException("FREQ=WEEKLY;BYMONTHDAY=7", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));

    /**
     * BYYEARDAY must only occur together with FREQ=YEARLY.
     */
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYYEARDAY=7", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=WEEKLY;BYYEARDAY=7", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=DAILY;BYYEARDAY=7", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));

    /**
     * BYWEEKNO must only be used when FREQ=YEARLY.
     */
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYWEEKNO=26", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYWEEKNO=26", RfcMode.RFC2445_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYWEEKNO=26", RfcMode.RFC5545_LAX).setObligatoryRuleParts("FREQ=YEARLY"));
    mRules.add(new TestRuleWithException("FREQ=MONTHLY;BYWEEKNO=26", RfcMode.RFC2445_LAX).setObligatoryRuleParts("FREQ=YEARLY"));

    /**
     * BYSETPOS must only be used in conjunction with another BYxxx rule.
     */
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYSETPOS=1", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYSETPOS=1;COUNT=2", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYSETPOS=1", RfcMode.RFC2445_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYSETPOS=1;COUNT=2", RfcMode.RFC2445_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYSETPOS=1", RfcMode.RFC5545_LAX).setInvalidRules("BYSETPOS="));

    mRules.add(new TestRuleWithException("FREQ=DAILY;BYDAY=+2MO", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYDAY=-2SO", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYWEEKNO=2;BYDAY=+1MO", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=DAILY;BYDAY=+2MO", RfcMode.RFC5545_LAX).setInvalidRules("BYDAY="));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYWEEKNO=2;BYDAY=+1MO", RfcMode.RFC5545_LAX).setInvalidRules("BYDAY="));
    mRules.add(new TestRuleWithException("FREQ=DAILY;BYDAY=+2MO", RfcMode.RFC2445_STRICT).setInvalidRules("BYDAY="));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYWEEKNO=2;BYDAY=+1MO", RfcMode.RFC2445_STRICT).setInvalidRules("BYDAY="));
    mRules.add(new TestRuleWithException("FREQ=DAILY;BYDAY=+2MO", RfcMode.RFC2445_LAX).setInvalidRules("BYDAY="));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYWEEKNO=2;BYDAY=+1MO", RfcMode.RFC2445_LAX).setInvalidRules("BYDAY="));

    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYDAY=MO;COUNT=MO", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;BYDAY=MO;INTERVAL=MO", RfcMode.RFC5545_STRICT).setException(new InvalidRecurrenceRuleException("")));

    /**
     * X-Parts in RFC 5545 modes. Lax mode should drop the part, strict mode must throw.
     */
    mRules.add(new TestRuleWithException("FREQ=YEARLY;X-MILLISECONDS=11,2,3,4", RfcMode.RFC5545_LAX).setInvalidRules("X-MILLISECONDS="));
    mRules.add(new TestRuleWithException("FREQ=YEARLY;X-MILLISECONDS=11,2,3,4", RfcMode.RFC5545_STRICT)
      .setException(new InvalidRecurrenceRuleException("")));

    /**
     * UNTIL value is invalid, these are examples from the wild
     */
    mRules.add(new TestRuleWithException("FREQ=WEEKLY;UNTIL=20140801T150000ZZ;INTERVAL=1;BYDAY=MO", RfcMode.RFC5545_STRICT)
      .setException(new InvalidRecurrenceRuleException("")));
    mRules.add(new TestRuleWithException("FREQ=WEEKLY;UNTIL=20140801T150000ZZ;INTERVAL=1;BYDAY=MO", RfcMode.RFC2445_STRICT)
      .setException(new InvalidRecurrenceRuleException("")));

    /**
     * Test for other keywords.
     */
    addUniqueKeyWordTests();
    addInvalidWhiteSpaceTests();

    for (TestRuleWithException rule : mRules)
    {
      if (rule.start == null)
      {
        rule.setStart(defaultStart);
      }
      boolean caughtException = false;
      try
      {

        RecurrenceRule r = new RecurrenceRule(rule.rule, rule.mode);
        rule.assertInvalidRules(r.toString());
        rule.assertObligatoryRuleParts(r.toString());
        rule.setIterationStart(rule.start);
        RecurrenceRuleIterator it = r.iterator(rule.start);
        Set<DateTime> instances = new HashSet<DateTime>();
        int count = 0;
        while (it.hasNext())
        {
          DateTime instance = it.nextDateTime();
          rule.testInstance(instance);
          rule.assertInstances(count);
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
        caughtException = true;
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
      if (!caughtException && rule.exception != null)
      {
        fail("Expected exception: " + rule.exception + " for rule " + rule.rule);
      }
    }
  }
}
