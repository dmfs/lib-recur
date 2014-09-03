package org.dmfs.rfc5545.recur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.TimeZone;

import org.dmfs.rfc5545.DateTime;


public class TestDate
{

	public final String dateTime;
	public DateTime cal;
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
		cal = DateTime.parse(dateTime);
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
		assertEquals(day, cal.getDayOfMonth());
		assertEquals(month, cal.getMonth());
		assertEquals(year, cal.getYear());
	}


	public void testTime()
	{
		if (!isAllDay)
		{
			assertEquals(hour, cal.getHours());
			assertEquals(minute, cal.getMinutes());
			assertEquals(second, cal.getSeconds());
		}
		else
		{
			assertTrue(cal.isAllDay());
		}
	}


	public void testToString()
	{
		assertEquals(dateTime, cal.toString());
	}


	private void compareTwoCalendars(DateTime c1, DateTime c2)
	{

		assertEquals(c1.getYear(), c2.getYear());
		assertEquals(c1.getMonth(), c2.getMonth());
		assertEquals(c1.getDayOfMonth(), c2.getDayOfMonth());
		assertEquals(c1.getHours(), c2.getHours());
		assertEquals(c1.getMinutes(), c2.getMinutes());
		assertEquals(c1.getSeconds(), c2.getSeconds());
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
			DateTime calClone = new DateTime(cal);
			calClone.shiftToTimeZone(TimeZone.getTimeZone(timeZone));
			compareTwoCalendars(cal, calClone);
		}
		else
		{
			assertEquals(TimeZone.getTimeZone("UTC"), cal.getTimeZone());
		}
	}


	public void testToAllDay()
	{
		DateTime calClone = new DateTime(cal);
		calClone.toAllDay();
		assertEquals(0, calClone.getHours());
		assertEquals(0, calClone.getMinutes());
		assertEquals(0, calClone.getSeconds());

		// date must stay the same
		assertEquals(cal.getYear(), calClone.getYear());
		assertEquals(cal.getMonth(), calClone.getMonth());
		assertEquals(cal.getMonth(), calClone.getMonth());

		assertTrue(calClone.isFloating());
		assertTrue(calClone.isAllDay());

		assertEquals(TimeZone.getTimeZone("UTC"), calClone.getTimeZone());

	}


	public void testClone()
	{
		DateTime calClone = new DateTime(cal);
		compareTwoCalendars(cal, calClone);

	}
}
