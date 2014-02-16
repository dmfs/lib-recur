package org.dmfs.rfc5545.recur;

import static org.junit.Assert.*;

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
	public void testMakeFastIntIntIntIntIntInt()
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
	public void testSetMonth()
	{
		long i = Instance.setMonth(Instance.make(2000, 2, 20, 3, 45, 36), 11);
		assertEquals(2000, Instance.year(i));
		assertEquals(11, Instance.month(i));
		assertEquals(20, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(36, Instance.second(i));
		assertEquals(0, Instance.dayOfWeek(i));
	}


	@Test
	public void testSetDayOfMonth()
	{
		long i = Instance.setDayOfMonth(Instance.make(2000, 2, 20, 3, 45, 36), 11);
		assertEquals(2000, Instance.year(i));
		assertEquals(2, Instance.month(i));
		assertEquals(11, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(36, Instance.second(i));
		assertEquals(0, Instance.dayOfWeek(i));
	}


	@Test
	public void testSetDayOfWeek()
	{
		long i = Instance.setDayOfWeek(Instance.make(2000, 2, 20, 3, 45, 36, 5), 4);
		assertEquals(2000, Instance.year(i));
		assertEquals(2, Instance.month(i));
		assertEquals(20, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(36, Instance.second(i));
		assertEquals(4, Instance.dayOfWeek(i));
	}


	@Test
	public void testSetMonthAndDayOfMonth()
	{
		long i = Instance.setMonthAndDayOfMonth(Instance.make(2000, 2, 20, 3, 45, 36), 11, 13);
		assertEquals(2000, Instance.year(i));
		assertEquals(11, Instance.month(i));
		assertEquals(13, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(36, Instance.second(i));
		assertEquals(0, Instance.dayOfWeek(i));
	}


	@Test
	public void testSetHour()
	{
		long i = Instance.setHour(Instance.make(2000, 2, 20, 3, 45, 36), 11);
		assertEquals(2000, Instance.year(i));
		assertEquals(2, Instance.month(i));
		assertEquals(20, Instance.dayOfMonth(i));
		assertEquals(11, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(36, Instance.second(i));
		assertEquals(0, Instance.dayOfWeek(i));
	}


	@Test
	public void testSetMinute()
	{
		long i = Instance.setMinute(Instance.make(2000, 2, 20, 3, 45, 36), 11);
		assertEquals(2000, Instance.year(i));
		assertEquals(2, Instance.month(i));
		assertEquals(20, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(11, Instance.minute(i));
		assertEquals(36, Instance.second(i));
		assertEquals(0, Instance.dayOfWeek(i));
	}


	@Test
	public void testSetSecond()
	{
		long i = Instance.setSecond(Instance.make(2000, 2, 20, 3, 45, 36), 11);
		assertEquals(2000, Instance.year(i));
		assertEquals(2, Instance.month(i));
		assertEquals(20, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(11, Instance.second(i));
		assertEquals(0, Instance.dayOfWeek(i));
	}


	@Test
	public void testMaskNonrelevant()
	{
		long i = Instance.maskWeekday(Instance.make(2000, 2, 20, 3, 45, 36, Calendar.MONDAY));
		assertEquals(2000, Instance.year(i));
		assertEquals(2, Instance.month(i));
		assertEquals(20, Instance.dayOfMonth(i));
		assertEquals(3, Instance.hour(i));
		assertEquals(45, Instance.minute(i));
		assertEquals(36, Instance.second(i));
		assertEquals(0, Instance.dayOfWeek(i));
	}
}
