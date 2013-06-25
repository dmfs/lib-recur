package org.dmfs.rfc5545.recur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;


public class CalendarTest
{

	private static final List<TestDate> mTestDates = new ArrayList<TestDate>();

	private static final String stampWithTime = "20120101T231545";
	private static final String tzID = "Europe/Berlin";
	private static final TimeZone timeZone = TimeZone.getTimeZone(tzID);


	@Test
	public void testCalendarRules()
	{
		for (TestDate tcr : mTestDates)
		{
			tcr.testEvent();
		}
	}

	static
	{
		mTestDates.add(new TestDate("20130619").setDate(2013, Calendar.JUNE, 19));
		mTestDates.add(new TestDate(stampWithTime).setDate(2012, Calendar.JANUARY, 1).setTime(23, 15, 45));
		mTestDates.add(new TestDate("20110202T224713Z").setDate(2011, Calendar.FEBRUARY, 2).setTime(22, 47, 13).setTimeZone("UTC"));

	}


	@Test(expected = NullPointerException.class)
	public void testNullAsTimeString()
	{
		new TestDate(null).setDate(2000, Calendar.JANUARY, 1).testEvent();

	}


	@Test(expected = IllegalArgumentException.class)
	public void testMissingMonthAndDay()
	{
		new TestDate("2013").setDate(2013, 0, 0).testEvent();
	}


	@Test(expected = IllegalArgumentException.class)
	public void testMissingDay()
	{
		new TestDate("201311").setDate(2013, Calendar.NOVEMBER, 0).testEvent();
	}


	@Test(expected = IllegalArgumentException.class)
	public void testMissingTime()
	{
		new TestDate("20131111T").setDate(2013, Calendar.NOVEMBER, 11).testEvent();
	}


	@Test(expected = IllegalArgumentException.class)
	public void testMissingLiteralT()
	{
		new TestDate("20131111223344").setDate(2013, Calendar.NOVEMBER, 11).testEvent();
	}


	@Test(expected = IllegalArgumentException.class)
	public void testLowerCaseT()
	{
		new TestDate("20131111t101010").setDate(2013, Calendar.NOVEMBER, 11).setTime(10, 10, 10).testEvent();
	}


	@Test(expected = IllegalArgumentException.class)
	public void testLowerCaseZ()
	{
		new TestDate("20131111T101010z").testEvent();

	}


	@Test(expected = IllegalArgumentException.class)
	public void testInvalidWhiteSpace()
	{
		final String s = "20131111T101010Z";
		for (int i = 0; i < s.length(); i++)
		{
			testSingleWhiteSpace(s, i);
		}

		new TestDate(s + " ").setDate(2013, Calendar.NOVEMBER, 11).setTime(10, 10, 10).testEvent();
	}


	private void testSingleWhiteSpace(String s, int pos)
	{

		StringBuffer sb = new StringBuffer(s);
		sb.insert(pos, " ");
		try
		{
			new TestDate(sb.toString()).setDate(2013, Calendar.NOVEMBER, 11).setTime(10, 10, 10).testEvent();
		}
		catch (IllegalArgumentException e)
		{
			return;
		}
		fail("Invalid whitespace character didn't trigger exception.");
	}


	/**
	 * Integer.parseInt allows '-' and '+'. We don't.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidPlusSign()
	{
		new TestDate("+2011122T223344Z").setDate(201, Calendar.NOVEMBER, 22).testEvent();
	}


	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMinusSign()
	{
		new TestDate("-2011122T223344Z").setDate(201, Calendar.NOVEMBER, 22).testEvent();

	}


	/**
	 * Setting certain fields should set 'isAllDay' to false.
	 */
	@Test
	public void testSetWithChangingIsAllDay()
	{
		int[] fields = { Calendar.HOUR_OF_DAY, Calendar.HOUR, Calendar.AM_PM, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND };
		Calendar cal = Calendar.parse("20131010");
		int value = 20; // arbitary
		int size = fields.length;
		for (int i = 0; i < size; i++)
		{
			cal.set(fields[i], value);
			assertFalse(cal.isAllDay());
			cal.toAllDay();

		}
	}


	@Test
	public void testSetWithoutChangingIsAllDay()
	{
		Calendar cal = Calendar.parse("20131010");
		cal.set(Calendar.YEAR, 2014);
		assertTrue(cal.isAllDay());
		cal.toAllDay();
		// setting a time field to 0 must not change isAllDay
		cal.set(Calendar.MINUTE, 0);
		assertTrue(cal.isAllDay());

	}


	@Test
	public void testConstructorWithTimeZone()
	{

		final long timeStamp = 1371752544000l; // 20130620T182224Z

		Calendar cal = new Calendar(timeZone, timeStamp);
		assertFalse(cal.isAllDay());
		assertFalse(cal.isFloating());

		assertEquals(2013, cal.get(Calendar.YEAR));
		assertEquals(Calendar.JUNE, cal.get(Calendar.MONTH));
		assertEquals(20, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(20, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(22, cal.get(Calendar.MINUTE));
		assertEquals(24, cal.get(Calendar.SECOND));
	}


	@Test
	public void testSetTimeZone()
	{
		Calendar cal = Calendar.parse(stampWithTime);
		assertTrue(cal.isFloating());
		assertNull(cal.getTimeZone());
		cal.setTimeZone(timeZone);
		assertFalse(cal.isFloating());
		assertEquals(timeZone, cal.getTimeZone());

	}
}
