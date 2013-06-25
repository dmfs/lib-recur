package org.dmfs.rfc5545.recur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.TimeZone;


public class TestDate
{

	public final String dateTime;
	public Calendar cal;
	public int day, month, year;
	public int hour = -1, minute = -1, second = -1;
	public String timeZone;
	public boolean isFloating = true;
	public boolean isAllDay = true;


	public TestDate(String rule)
	{
		this.dateTime = rule;

	}


	public TestDate setDate(int year, int month, int day)
	{
		this.year = year;
		this.month = month;
		this.day = day;
		return this;
	}


	public TestDate setTime(int hour, int minute, int second)
	{
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		isAllDay = false;
		return this;

	}


	public TestDate setTimeZone(String tz)
	{
		timeZone = tz;
		isFloating = false;
		return this;
	}


	public void testEvent()
	{
		cal = Calendar.parse(dateTime);
		testClone();
		testFlags();
		testDate();
		testTime();
		testToString();
		testTimeZone();
		testToAllDay();
		testDate();

	}


	public void testDate()
	{
		assertEquals(day, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(month, cal.get(Calendar.MONTH));
		assertEquals(year, cal.get(Calendar.YEAR));
	}


	public void testTime()
	{
		if (!isAllDay)
		{
			assertEquals(hour, cal.get(Calendar.HOUR_OF_DAY));
			assertEquals(minute, cal.get(Calendar.MINUTE));
			assertEquals(second, cal.get(Calendar.SECOND));
		}
	}


	public void testToString()
	{
		assertEquals(dateTime, cal.toString());
	}


	private void compareTwoCalendars(Calendar c1, Calendar c2)
	{

		assertEquals(c1.get(Calendar.YEAR), c2.get(Calendar.YEAR));
		assertEquals(c1.get(Calendar.MONTH), c2.get(Calendar.MONTH));
		assertEquals(c1.get(Calendar.DAY_OF_MONTH), c2.get(Calendar.DAY_OF_MONTH));
		assertEquals(c1.get(Calendar.HOUR_OF_DAY), c2.get(Calendar.HOUR_OF_DAY));
		assertEquals(c1.get(Calendar.MINUTE), c2.get(Calendar.MINUTE));
		assertEquals(c1.get(Calendar.SECOND), c2.get(Calendar.SECOND));
		assertEquals(c1.isAllDay(), c2.isAllDay());
		assertEquals(c1.isFloating(), c2.isFloating());

	}


	/**
	 * Tests if isAllDay and isFloating are set correctly.
	 */
	public void testFlags()
	{
		assertEquals(isAllDay, cal.isAllDay());
		assertEquals(isFloating, cal.isFloating());
		if (isAllDay)
		{
			assertTrue(isFloating);
		}
	}


	public void testTimeZone()
	{
		if (!isFloating)
		{
			assertEquals(timeZone, cal.getTimeZone().getID());
			Calendar calClone = cal.clone();
			calClone.replaceTimeZone(TimeZone.getTimeZone(timeZone));
			compareTwoCalendars(cal, calClone);
		}
		else
		{
			assertNull(cal.getTimeZone());
		}
	}


	public void testToAllDay()
	{
		Calendar calClone = cal.clone();
		calClone.toAllDay();
		assertEquals(0, calClone.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, calClone.get(Calendar.MINUTE));
		assertEquals(0, calClone.get(Calendar.SECOND));

		// date must stay the same
		assertEquals(cal.get(Calendar.YEAR), calClone.get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), calClone.get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), calClone.get(Calendar.DAY_OF_MONTH));

		assertTrue(calClone.isFloating());
		assertTrue(calClone.isAllDay());

		assertNull(calClone.getTimeZone());

	}


	public void testClone()
	{
		Calendar calClone = cal.clone();
		compareTwoCalendars(cal, calClone);

	}
}
