package org.dmfs.rfc5545.recur;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class InstanceTest
{

	@Test
	public void testMakeCalendar()
	{
		long i = Instance.make(new Calendar(2000, 2, 20, 3, 45, 36));
		assertEquals(2000, Instance.year(i));
		assertEquals(2, Instance.month(i));
		assertEquals(20, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(36, Instance.second(i));
	}


	@Test
	public void testMakeIntIntIntIntIntIntInt()
	{
		long i = Instance.make(2000, 2, 20, 3, 45, 36, Calendar.MONDAY);
		assertEquals(2000, Instance.year(i));
		assertEquals(2, Instance.month(i));
		assertEquals(20, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(36, Instance.second(i));
		assertEquals(Calendar.MONDAY, Instance.dayOfWeek(i));
	}


	@Test
	public void testMakeIntIntIntIntIntInt()
	{
		long i = Instance.make(2000, 2, 20, 3, 45, 36);
		assertEquals(2000, Instance.year(i));
		assertEquals(2, Instance.month(i));
		assertEquals(20, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(36, Instance.second(i));
		assertEquals(0, Instance.dayOfWeek(i));
	}


	@Test
	public void testSetYear()
	{
		for (int year = 1700; year < 4000; ++year)
		{
			long i = Instance.setYear(Instance.make(0, 2, 20, 3, 45, 36), year);
			assertEquals(year, Instance.year(i));
			assertEquals(2, Instance.month(i));
			assertEquals(20, Instance.dayOfMonth(i));
			assertEquals(3, Instance.hour(i));
			assertEquals(45, Instance.minute(i));
			assertEquals(36, Instance.second(i));
			assertEquals(0, Instance.dayOfWeek(i));
		}
	}


	@Test
	public void testSetMonth()
	{
		for (int month = 0; month < 12; ++month)
		{
			long i = Instance.setMonth(Instance.make(2000, 0, 20, 3, 45, 36), month);
			assertEquals(2000, Instance.year(i));
			assertEquals(month, Instance.month(i));
			assertEquals(20, Instance.dayOfMonth(i));
			assertEquals(3, Instance.hour(i));
			assertEquals(45, Instance.minute(i));
			assertEquals(36, Instance.second(i));
			assertEquals(0, Instance.dayOfWeek(i));
		}
	}


	@Test
	public void testSetDayOfMonth()
	{
		for (int dayOfMonth = 1; dayOfMonth < 40; ++dayOfMonth)
		{
			long i = Instance.setDayOfMonth(Instance.make(2000, 2, 20, 3, 45, 36), dayOfMonth);
			assertEquals(2000, Instance.year(i));
			assertEquals(2, Instance.month(i));
			assertEquals(dayOfMonth, Instance.dayOfMonth(i));
			assertEquals(3, Instance.hour(i));
			assertEquals(45, Instance.minute(i));
			assertEquals(36, Instance.second(i));
			assertEquals(0, Instance.dayOfWeek(i));
		}
	}


	@Test
	public void testSetDayOfWeek()
	{
		for (int dayOfWeek = 0; dayOfWeek < 7; ++dayOfWeek)
		{
			long i = Instance.setDayOfWeek(Instance.make(2000, 2, 20, 3, 45, 36, 5), dayOfWeek);
			assertEquals(2000, Instance.year(i));
			assertEquals(2, Instance.month(i));
			assertEquals(20, Instance.dayOfMonth(i));
			assertEquals(3, Instance.hour(i));
			assertEquals(45, Instance.minute(i));
			assertEquals(36, Instance.second(i));
			assertEquals(dayOfWeek, Instance.dayOfWeek(i));
		}
	}


	@Test
	public void testSetMonthAndDayOfMonth()
	{
		for (int month = 0; month < 12; ++month)
		{
			for (int dayOfMonth = 1; dayOfMonth < 40; ++dayOfMonth)
			{
				long i = Instance.setMonthAndDayOfMonth(Instance.make(2000, 2, 20, 3, 45, 36), month, dayOfMonth);
				assertEquals(2000, Instance.year(i));
				assertEquals(month, Instance.month(i));
				assertEquals(dayOfMonth, Instance.dayOfMonth(i));
				assertEquals(3, Instance.hour(i));
				assertEquals(45, Instance.minute(i));
				assertEquals(36, Instance.second(i));
				assertEquals(0, Instance.dayOfWeek(i));
			}
		}
	}


	@Test
	public void testSetHour()
	{
		for (int hour = 0; hour < 24; ++hour)
		{
			long i = Instance.setHour(Instance.make(2000, 2, 20, 3, 45, 36), hour);
			assertEquals(2000, Instance.year(i));
			assertEquals(2, Instance.month(i));
			assertEquals(20, Instance.dayOfMonth(i));
			assertEquals(hour, Instance.hour(i));
			assertEquals(45, Instance.minute(i));
			assertEquals(36, Instance.second(i));
			assertEquals(0, Instance.dayOfWeek(i));
		}
	}


	@Test
	public void testSetMinute()
	{
		for (int minute = 0; minute < 24; ++minute)
		{
			long i = Instance.setMinute(Instance.make(2000, 2, 20, 3, 45, 36), minute);
			assertEquals(2000, Instance.year(i));
			assertEquals(2, Instance.month(i));
			assertEquals(20, Instance.dayOfMonth(i));
			assertEquals(3, Instance.hour(i));
			assertEquals(minute, Instance.minute(i));
			assertEquals(36, Instance.second(i));
			assertEquals(0, Instance.dayOfWeek(i));
		}
	}


	@Test
	public void testSetSecond()
	{
		for (int second = 0; second < 24; ++second)
		{
			long i = Instance.setSecond(Instance.make(2000, 2, 20, 3, 45, 36), second);
			assertEquals(2000, Instance.year(i));
			assertEquals(2, Instance.month(i));
			assertEquals(20, Instance.dayOfMonth(i));
			assertEquals(3, Instance.hour(i));
			assertEquals(45, Instance.minute(i));
			assertEquals(second, Instance.second(i));
			assertEquals(0, Instance.dayOfWeek(i));
		}
	}


	@Test
	public void testMaskNonrelevant()
	{
		for (int dayOfWeek = 0; dayOfWeek < 7; ++dayOfWeek)
		{
			long i = Instance.maskWeekday(Instance.make(2000, 2, 20, 3, 45, 36, dayOfWeek));
			assertEquals(2000, Instance.year(i));
			assertEquals(2, Instance.month(i));
			assertEquals(20, Instance.dayOfMonth(i));
			assertEquals(3, Instance.hour(i));
			assertEquals(45, Instance.minute(i));
			assertEquals(36, Instance.second(i));
			assertEquals(0, Instance.dayOfWeek(i));
		}
	}
}
