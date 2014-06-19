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

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;


/**
 * Test {@link JulianCalendarMetrics}.
 * 
 * TODO: complete tests
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class JulianCalendarMetricsTest
{

	@Before
	public void setUp() throws Exception
	{
	}


	@Test
	public void testGetMaxMonthDays()
	{
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				assertEquals(31, tools.getMaxMonthDayNum());
			}
		}
	}


	@Test
	public void testGetMaxYearDays()
	{
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				assertEquals(366, tools.getMaxYearDayNum());
			}
		}
	}


	@Test
	public void testGetMaxWeeks()
	{
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				assertEquals(53, tools.getMaxWeeksNoNum());
			}
		}
	}


	@Test
	public void testGetDaysPerMonth()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
				{
					for (int month = 0; month < 12; ++month)
					{
						testCal.set(year, month, 1);

						String errMsg = "";
						// errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
						assertEquals(errMsg, testCal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH), tools.getDaysPerPackedMonth(year, month));
					}
				}
			}
		}
	}


	@Test
	public void testGetMonthsPerYear()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
				{
					// set testCal to Feb 1st
					testCal.set(year, 1, 1);

					String errMsg = "";
					// errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;

					assertEquals(errMsg, testCal.getActualMaximum(java.util.Calendar.MONTH), tools.getMonthsPerYear(year) - 1);
				}
			}
		}
	}


	@Test
	public void testGetDaysPerYear()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
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
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
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
	 * Test getWeekOfYear for all days between 1 and 1582.
	 */
	@Test
	public void testGetWeekOfYear()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 2; year < 2038; ++year)
				{
					testCal.set(year, 0, 1);
					for (int yearday = 1; yearday <= tools.getDaysPerYear(year); ++yearday)
					{
						testCal.set(java.util.Calendar.DAY_OF_YEAR, yearday);

						String errMsg = "";
						// errMsg = "failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
						assertEquals(errMsg, testCal.get(java.util.Calendar.WEEK_OF_YEAR), tools.getWeekOfYear(year, yearday));
					}
				}
			}
		}
	}


	/**
	 * Test getWeekOfYear for all days between 1 and 1582.
	 */
	@Test
	public void testGetWeekOfYearIntInt()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 2; year < 2038; ++year)
				{
					for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
					{
						for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
						{
							testCal.set(year, month, day);

							String errMsg = "";
							// errMsg = "failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
							assertEquals(errMsg, testCal.get(java.util.Calendar.WEEK_OF_YEAR), tools.getWeekOfYear(year, month, day));
						}
					}
				}
			}
		}
	}


	@Test
	public void testGetDayOfYear()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
				{
					for (int month = 0; month < 12; ++month)
					{
						for (int monthday = 1; monthday <= tools.getDaysPerPackedMonth(year, month); ++monthday)
						{
							testCal.set(year, month, monthday);

							String errMsg = "";
							// errMsg = "failed for year " + year + " yearday " + monthday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek"
							assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_YEAR), tools.getDayOfYear(year, month, monthday));
						}
					}
				}
			}
		}
	}


	@Test
	public void testGetDayOfJan1st()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
				{
					// set testCal to Jan 1st
					testCal.set(year, 0, 1);
					testCal.get(java.util.Calendar.WEEK_OF_YEAR);

					String errMsg = "";
					// errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
					assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_WEEK) - 1, tools.getWeekDayOfFirstYearDay(year));
				}
			}
		}
	}


	@Test
	public void testGetYearDayOf1stWeekStart()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 2; year < 2038; ++year)
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
					String errMsg = "";
					// errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;

					assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_YEAR), yd);
				}
			}
		}
	}


	@Test
	public void testGetYearDaysForMonth()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
				{
					for (int month = 0; month < 12; ++month)
					{
						testCal.set(year, month, 1);

						assertEquals("failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek,
							testCal.get(java.util.Calendar.DAY_OF_YEAR) - 1, tools.getYearDaysForPackedMonth(year, month));
					}
				}
			}
		}
	}


	@Test
	public void testGetMonthAndDayOfYearDay()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 3; year < 2035; ++year)
				{
					for (int yearday = -600; yearday <= tools.getDaysPerYear(year) + 600; ++yearday)
					{
						testCal.set(year, 0, 1);
						testCal.set(java.util.Calendar.DAY_OF_YEAR, yearday);
						// System.out.println("" + testCal);

						int monthAndDay = tools.getMonthAndDayOfYearDay(year, yearday);
						int month = CalendarMetrics.month(monthAndDay);
						int day = CalendarMetrics.dayOfMonth(monthAndDay);
						String errMsgMonth = "", errMsgDay = "";
						// errMsgMonth = "month failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
						// errMsgDay = "day failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;
						assertEquals(errMsgMonth, testCal.get(java.util.Calendar.MONTH), month);

						assertEquals(errMsgDay, testCal.get(java.util.Calendar.DAY_OF_MONTH), day);
					}
				}
			}
		}
	}


	@Test
	public void testGetDayOfWeekIntInt()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
				{
					testCal.set(year, 0, 1);
					for (int yearday = 1; yearday <= tools.getDaysPerYear(year); ++yearday)
					{
						testCal.set(java.util.Calendar.DAY_OF_YEAR, yearday);
						// System.out.println("" + testCal);

						String errMsg = "";
						// errMsg = "day of week failed for year " + year + " yearday " + yearday + " weekstart " + weekStart + " minDays "+minDaysInFirstWeek;
						assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_WEEK) - 1, tools.getDayOfWeek(year, yearday));
					}
				}
			}
		}
	}


	@Test
	public void testGetDayOfWeekIntIntInt()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
				{
					for (int month = 0; month < 12; ++month)
					{
						for (int monthday = 1; monthday <= tools.getDaysPerPackedMonth(year, month); ++monthday)
						{
							testCal.set(year, month, monthday);

							String errMsg = "";
							// errMsg = "day of week failed for year " + year + " yearday " + monthday + " weekstart " + weekStart + " minDays "
							// + minDaysInFirstWeek;
							assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_WEEK) - 1, tools.getDayOfWeek(year, month, monthday));
						}
					}
				}
			}
		}
	}


	@Test
	public void testGetMonthOfYearDay()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 3; year < 2035; ++year)
				{
					for (int yearDay = -600; yearDay <= tools.getDaysPerYear(year) + 600; ++yearDay)
					{
						testCal.set(year, 0, 1);
						testCal.set(java.util.Calendar.DAY_OF_YEAR, yearDay);
						String errMsg = "";
						// errMsg = "getMonthOfYearDay failed for year=" + year + " yearDay=" + yearDay + " weekStart=" + weekStart + " minDays=" +
						// minDaysInFirstWeek;

						assertEquals(errMsg, testCal.get(Calendar.MONTH), tools.getPackedMonthOfYearDay(year, yearDay));

					}

				}
			}
		}
	}


	@Test
	public void testGetDayOfMonthOfYearDay()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
				{
					testCal.set(year, 0, 1);
					for (int yearDay = 1; yearDay <= tools.getDaysPerYear(year); ++yearDay)
					{
						testCal.set(java.util.Calendar.DAY_OF_YEAR, yearDay);
						String errMsg = "";
						// errMsg = "getDayOfMonthOfYearDay failed for year=" + year + " yearDay=" + yearDay + " weekStart=" + " minDays=" + minDaysInFirstWeek;

						assertEquals(errMsg, testCal.get(Calendar.DAY_OF_MONTH), tools.getDayOfMonthOfYearDay(year, yearDay));
					}
				}
			}

		}
	}


	@Test
	public void testGetYearDayOfIsoYear()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);

				for (int year = 2; year < 2038; ++year)
				{
					for (int week = 1; week <= tools.getWeeksPerYear(year); ++week)
					{
						for (int dayOfWeek = 0; dayOfWeek < 7; ++dayOfWeek)
						{
							testCal.set(year, 0, 8);
							testCal.set(java.util.Calendar.WEEK_OF_YEAR, week);
							testCal.set(java.util.Calendar.DAY_OF_WEEK, dayOfWeek + 1);

							String errMsg = "";
							// errMsg = "getYearDayOfIsoYear failed for year=" + year + " week=" + week + " dayOfWeek=" + dayOfWeek + " weekStart=" + weekStart
							// + " minDays=" + minDaysInFirstWeek;

							int yd = tools.getYearDayOfIsoYear(year, week, dayOfWeek);
							if (yd < 1)
							{
								yd += tools.getDaysPerYear(year - 1);
							}
							else if (yd > tools.getDaysPerYear(year))
							{
								yd -= tools.getDaysPerYear(year);
							}

							assertEquals(errMsg, testCal.get(Calendar.DAY_OF_YEAR), yd);
						}
					}
				}
			}

		}
	}


	@Test
	public void testGetYearDayOfWeekStart()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				CalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 2; year < 2038; ++year)
				{
					for (int week = 1; week <= tools.getWeeksPerYear(year); ++week)
					{
						// set testCal to start of the first week in that year
						testCal.set(year, 0, 8);
						testCal.set(java.util.Calendar.WEEK_OF_YEAR, week);
						testCal.set(java.util.Calendar.DAY_OF_WEEK, weekStart + 1);

						int yd = tools.getYearDayOfWeekStart(year, week);
						if (yd < 1)
						{
							yd += tools.getDaysPerYear(year - 1);
						}
						else if (yd > tools.getDaysPerYear(year))
						{
							yd -= tools.getDaysPerYear(year);
						}

						String errMsg = "";
						// errMsg = "failed for year " + year + " weekstart " + weekStart + " minDays " + minDaysInFirstWeek;

						assertEquals(errMsg, testCal.get(java.util.Calendar.DAY_OF_YEAR), yd);
					}
				}
			}
		}
	}


	@Test
	public void testGetUtcTimeStamp()
	{
		java.util.GregorianCalendar testCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
		testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
		testCal.setTimeInMillis(0);
		for (int minDaysInFirstWeek = 1; minDaysInFirstWeek < 8; ++minDaysInFirstWeek)
		{
			testCal.setMinimalDaysInFirstWeek(minDaysInFirstWeek);
			for (int weekStart = 0; weekStart < 7; ++weekStart)
			{
				JulianCalendarMetrics tools = new JulianCalendarMetrics(weekStart, minDaysInFirstWeek);
				testCal.setFirstDayOfWeek(weekStart + 1);
				for (int year = 1; year < 2038; ++year)
				{
					testCal.set(year, 0, 1);
					for (int yearDay = 1; yearDay <= tools.getDaysPerYear(year); ++yearDay)
					{
						testCal.set(java.util.Calendar.DAY_OF_YEAR, yearDay);
						String errMsg = "";
						// errMsg = "getUtcTimeStamp failed for year=" + year + " yearDay=" + yearDay + " weekStart=" + " minDays=" + minDaysInFirstWeek;

						assertEquals(errMsg, testCal.getTimeInMillis(), tools.getTimeStamp(year, yearDay, 0, 0, 0, 0));
					}
				}
			}

		}
	}


	@Test
	public void testGetTimeStamp()
	{
		for (String z : new String[] { "America/Bogota", "America/Los_Angeles", "America/New_York", "UTC", "GMT", "Europe/Berlin", "Asia/Peking", "Asia/Tokyo",
			"Australia/Sidney" })
		{
			TimeZone zone = TimeZone.getTimeZone(z);
			java.util.GregorianCalendar testCal = new GregorianCalendar(zone, Locale.US);
			testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
			testCal.setMinimalDaysInFirstWeek(4);
			testCal.setFirstDayOfWeek(Calendar.MONDAY);
			testCal.setTimeInMillis(0);

			CalendarMetrics tools = new JulianCalendarMetrics(1 /* Monday */, 4);

			for (int year = 1; year < 2038; ++year) /* there seems to be some issue when we test dates after 2038, maybe a year 2038 bug somewhere? */
			{
				String errMsg = "";
				for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
				{
					for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
					{
						for (int hour = 0; hour < 24; hour += 2)
						{
							for (int minute = 0; minute < 1; ++minute)
							{
								testCal.set(year, month, day, hour, minute, 0);
								// errMsg = "getUtcTimeStamp failed for year=" + year + " month=" + month + " day=" + day + " weekStart=" + " minDays="
								// + minDaysInFirstWeek;

								assertEquals(errMsg, testCal.getTimeInMillis(), tools.toMillis(zone, year, month, day, hour, minute, 0, 0));
							}
						}
					}
				}
			}
		}
	}


	// @Test
	public void testToInstance()
	{
		for (String z : new String[] { "America/Bogota", "America/Los_Angeles", "America/New_York", "UTC", "GMT", "Europe/Berlin", "Asia/Peking", "Asia/Tokyo",
			"Australia/Sidney" })
		{
			TimeZone zone = TimeZone.getTimeZone(z);
			java.util.GregorianCalendar testCal = new GregorianCalendar(zone, Locale.US);
			testCal.setGregorianChange(new Date(1000L * 3600L * 24L * 365L * 200L));
			testCal.setMinimalDaysInFirstWeek(4);
			testCal.setFirstDayOfWeek(Calendar.MONDAY);
			testCal.setTimeInMillis(0);

			CalendarMetrics tools = new JulianCalendarMetrics(1 /* Monday */, 4);

			for (int year = 2; year < 2038; ++year)
			{
				String errMsg = "";
				for (int month = 0; month < tools.getMonthsPerYear(year); ++month)
				{
					for (int day = 1; day <= tools.getDaysPerPackedMonth(year, month); ++day)
					{
						for (int hour = 0; hour < 24; ++hour)
						{
							for (int minute = 0; minute < 60; ++minute)
							{
								testCal.set(year, month, day, hour, minute, 0);
								// errMsg = "toInstance failed for year=" + year + " month=" + month + " day=" + day + " hour=" + hour + " minute=" + minute
								// + "  tz=" + zone.getID();

								long instance = tools.toInstance(testCal.getTimeInMillis(), zone);

								assertEquals(errMsg, testCal.get(Calendar.HOUR_OF_DAY), Instance.hour(instance));
								assertEquals(errMsg, testCal.get(Calendar.MINUTE), Instance.minute(instance));
								assertEquals(errMsg, testCal.get(Calendar.SECOND), Instance.second(instance));
								assertEquals(errMsg, testCal.get(Calendar.DAY_OF_MONTH), Instance.dayOfMonth(instance));
								assertEquals(errMsg, testCal.get(Calendar.MONTH), Instance.month(instance));
								assertEquals(errMsg, testCal.get(Calendar.YEAR), Instance.year(instance));
							}
						}
					}
				}
			}
		}
	}

}
