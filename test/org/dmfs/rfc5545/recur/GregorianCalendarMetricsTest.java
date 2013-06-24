/*
 * Copyright (C) 2013 Marten Gajda <marten@dmfs.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.dmfs.rfc5545.recur;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;


/**
 * Test {@link GregorianCalendarMetrics}.
 * 
 * TODO: complete tests
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class GregorianCalendarMetricsTest
{

	@Before
	public void setUp() throws Exception
	{
	}


	@Test
	public void testGetDaysPerMonth()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					for (int month = 0; month < 12; ++month)
					{
						testCal.set(year, month, 1);

						assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
							testCal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH), tools.getDaysPerMonth(year, month));
					}
				}
			}
		}
	}


	@Test
	public void testGetMonthsPerYear()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					// set testCal to Feb 1st
					testCal.set(year, 1, 1);

					assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
						testCal.getActualMaximum(java.util.Calendar.MONTH), tools.getMonthsPerYear(year) - 1);
				}
			}
		}
	}


	@Test
	public void testGetDaysPerYear()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					// set testCal to Feb 1st
					testCal.set(year, 1, 1);

					assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
						testCal.getActualMaximum(java.util.Calendar.DAY_OF_YEAR), tools.getDaysPerYear(year));
				}
			}
		}
	}


	@Test
	public void testGetWeeksPerYear()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					// set testCal to Feb 1st
					testCal.set(year, 1, 1);

					assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
						testCal.getActualMaximum(java.util.Calendar.WEEK_OF_YEAR), tools.getWeeksPerYear(year));
				}
			}
		}
	}


	/**
	 * Test getWeekOfYear for all days between 1700 and 3000.
	 */
	@Test
	public void testGetWeekOfYear()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					testCal.set(year, 0, 1);
					for (int yearday = 1; yearday <= tools.getDaysPerYear(year); ++yearday)
					{
						testCal.set(java.util.Calendar.DAY_OF_YEAR, yearday);

						assertEquals("failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
							testCal.get(java.util.Calendar.WEEK_OF_YEAR), tools.getWeekOfYear(year, yearday));
					}
				}
			}
		}
	}


	@Test
	public void testGetDayOfYear()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					for (int month = 0; month < 12; ++month)
					{
						for (int monthday = 1; monthday <= tools.getDaysPerMonth(year, month); ++monthday)
						{
							testCal.set(year, month, monthday);

							assertEquals("failed for year " + year + " yearday " + monthday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
								testCal.get(java.util.Calendar.DAY_OF_YEAR), tools.getDayOfYear(year, month, monthday));
						}
					}
				}
			}
		}
	}


	@Test
	public void testGetDayOfJan1st()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					// set testCal to Jan 1st
					testCal.set(year, 0, 1);
					testCal.get(java.util.Calendar.WEEK_OF_YEAR);

					assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
						testCal.get(java.util.Calendar.DAY_OF_WEEK) - 1, tools.getWeekDayOfFirstYearDay(year));
				}
			}
		}
	}


	@Test
	public void testGetYearDayOf1stWeekStart()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					// set testCal to start of the first week in that year
					testCal.set(year, 0, 1);
					testCal.set(java.util.Calendar.WEEK_OF_YEAR, 1);
					testCal.set(java.util.Calendar.DAY_OF_WEEK, weekStart + 1);

					int yd = tools.getYearDayOfFirstWeekStart(year);
					if (yd < 1)
					{
						yd += tools.getDaysPerYear(year - 1);
					}

					assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
						testCal.get(java.util.Calendar.DAY_OF_YEAR), yd);
				}
			}
		}
	}


	@Test
	public void testGetYearDaysForMonth()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					for (int month = 0; month < 12; ++month)
					{
						testCal.set(year, month, 1);

						assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
							testCal.get(java.util.Calendar.DAY_OF_YEAR) - 1, tools.getYearDaysForMonth(year, month));
					}
				}
			}
		}
	}


	@Test
	public void testGetMonthAndDayOfYearDay()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					testCal.set(year, 0, 1);
					for (int yearday = 1; yearday <= tools.getDaysPerYear(year); ++yearday)
					{
						testCal.set(java.util.Calendar.DAY_OF_YEAR, yearday);
						// System.out.println("" + testCal);

						int monthAndDay = tools.getMonthAndDayOfYearDay(year, yearday);
						int month = CalendarMetrics.month(monthAndDay);
						int day = CalendarMetrics.dayOfMonth(monthAndDay);
						assertEquals("month failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
							testCal.get(java.util.Calendar.MONTH), month);
						assertEquals("day failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
							testCal.get(java.util.Calendar.DAY_OF_MONTH), day);
					}
				}
			}
		}
	}


	@Test
	public void testGetDayOfWeekIntInt()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					testCal.set(year, 0, 1);
					for (int yearday = 1; yearday <= tools.getDaysPerYear(year); ++yearday)
					{
						testCal.set(java.util.Calendar.DAY_OF_YEAR, yearday);
						// System.out.println("" + testCal);

						assertEquals("day of week failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays "
							+ minDaysInFirstWeek, testCal.get(java.util.Calendar.DAY_OF_WEEK) - 1, tools.getDayOfWeek(year, yearday));
					}
				}
			}
		}
	}


	@Test
	public void testGetDayOfWeekIntIntInt()
	{
		java.util.Calendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1700; year < 3000; ++year)
				{
					for (int month = 0; month < 12; ++month)
					{
						for (int monthday = 1; monthday <= tools.getDaysPerMonth(year, month); ++monthday)
						{
							testCal.set(year, month, monthday);

							assertEquals("day of week failed for year " + year + " yearday " + monthday + " weekstart " + weekStart + " minDays "
								+ minDaysInFirstWeek, testCal.get(java.util.Calendar.DAY_OF_WEEK) - 1, tools.getDayOfWeek(year, month, monthday));
						}
					}
				}
			}
		}
	}


	@Test
	public void testMonthAndDay()
	{
		CalendarMetrics tools = new GregorianCalendarMetrics(1, 4);
		for (int month = 0; month < 12; ++month)
		{
			for (int monthday = 1; monthday <= tools.getDaysPerMonth(2000, month); ++monthday)
			{
				int mad = CalendarMetrics.monthAndDay(month, monthday);

				int mo = CalendarMetrics.month(mad);
				int dom = CalendarMetrics.dayOfMonth(mad);

				assertEquals(month, mo);
				assertEquals(monthday, dom);
			}
		}
	}

}
