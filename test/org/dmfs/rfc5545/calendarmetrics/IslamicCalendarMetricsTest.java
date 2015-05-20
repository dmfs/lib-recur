package org.dmfs.rfc5545.calendarmetrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.calendarmetrics.IslamicCalendarMetrics.LeapYearPattern;
import org.dmfs.rfc5545.recur.RecurrenceRule.Weekday;
import org.junit.Test;


public class IslamicCalendarMetricsTest
{

	private final static CalendarMetrics GREGORIAN_CALENDAR = GregorianCalendarMetrics.FACTORY.getCalendarMetrics(0);

	private final static String[] TEST_TIME_ZONES = { "UTC", "Europe/Berlin", "Amerika/New_York", "Asia/Tokyo" };

	@SuppressWarnings("unchecked")
	private final static Map<Long, Long>[][] DATE_MAPS = (Map<Long, Long>[][]) new HashMap<?, ?>[4][2];


	@Test
	public void testPackedMonthString()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testPackedMonthToString()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testPackedMonthIntBoolean()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testIsLeapMonth()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testMonthNum()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetMaxMonthDayNum()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetMaxYearDayNum()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetMaxWeekNoNum()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testIsLeapDay()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetDaysPerPackedMonth()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetPackedMonthOfYearDay()
	{
		CalendarMetrics testMetrics = new IslamicCalendarMetrics(1, 4, LeapYearPattern.II, true);
		for (int year = 1; year < 3000; ++year)
		{
			for (int month = 0; month < 12; ++month)
			{
				for (int day = 1; day <= testMetrics.getDaysPerPackedMonth(year, month); ++day)
				{
					assertEquals(month, testMetrics.getPackedMonthOfYearDay(year, testMetrics.getDayOfYear(year, month, day)));
				}
			}
		}
	}


	@Test
	public void testGetDayOfMonthOfYearDay()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetMonthAndDayOfYearDay()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetYearDaysForPackedMonth()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetMonthsPerYear()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetDaysPerYear()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetWeeksPerYear()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetWeekOfYearIntIntInt()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetWeekOfYearIntInt()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetDayOfWeekIntInt()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetDayOfWeekIntIntInt()
	{
		// TODO: add more tests, at least one for each year in the 30 year cycle
		CalendarMetrics testMetrics = new IslamicCalendarMetrics(1, 4, LeapYearPattern.II, true);
		assertEquals(Weekday.FR.ordinal(), testMetrics.getDayOfWeek(1, 0, 1));
		assertEquals(Weekday.FR.ordinal(), testMetrics.getDayOfWeek(999, 1, 2));
		assertEquals(Weekday.TU.ordinal(), testMetrics.getDayOfWeek(1100, 2, 12));
		assertEquals(Weekday.SA.ordinal(), testMetrics.getDayOfWeek(1237, 5, 29));
		assertEquals(Weekday.SA.ordinal(), testMetrics.getDayOfWeek(1420, 8, 24));
		assertEquals(Weekday.TU.ordinal(), testMetrics.getDayOfWeek(1435, 11, 12));
		assertEquals(Weekday.TH.ordinal(), testMetrics.getDayOfWeek(1499, 9, 25));

		// TODO: add more tests, at least one for each year in the 30 year cycle
		testMetrics = new IslamicCalendarMetrics(1, 4, LeapYearPattern.II, false);
		assertEquals(Weekday.FR.ordinal(), testMetrics.getDayOfWeek(1, 0, 1));
		assertEquals(Weekday.FR.ordinal(), testMetrics.getDayOfWeek(999, 1, 2));
		assertEquals(Weekday.TU.ordinal(), testMetrics.getDayOfWeek(1100, 2, 12));
		assertEquals(Weekday.SA.ordinal(), testMetrics.getDayOfWeek(1237, 5, 29));
		assertEquals(Weekday.SA.ordinal(), testMetrics.getDayOfWeek(1420, 8, 24));
		assertEquals(Weekday.TU.ordinal(), testMetrics.getDayOfWeek(1435, 11, 12));
		assertEquals(Weekday.TH.ordinal(), testMetrics.getDayOfWeek(1499, 9, 25));

	}


	@Test
	public void testGetDayOfYear()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetYearDayOfIsoYear()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetWeekDayOfFirstYearDay()
	{
		CalendarMetrics testMetrics = new IslamicCalendarMetrics(1, 4, LeapYearPattern.I, true);
		for (int year = 1; year < 3000; ++year)
		{
			assertEquals(testMetrics.getDayOfWeek(year, 0, 1), testMetrics.getWeekDayOfFirstYearDay(year));
		}

		testMetrics = new IslamicCalendarMetrics(1, 4, LeapYearPattern.II, true);
		for (int year = 1; year < 3000; ++year)
		{
			assertEquals(testMetrics.getDayOfWeek(year, 0, 1), testMetrics.getWeekDayOfFirstYearDay(year));
		}

		testMetrics = new IslamicCalendarMetrics(1, 4, LeapYearPattern.III, true);
		for (int year = 1; year < 3000; ++year)
		{
			assertEquals(testMetrics.getDayOfWeek(year, 0, 1), testMetrics.getWeekDayOfFirstYearDay(year));
		}

		testMetrics = new IslamicCalendarMetrics(1, 4, LeapYearPattern.IV, true);
		for (int year = 1; year < 3000; ++year)
		{
			assertEquals(testMetrics.getDayOfWeek(year, 0, 1), testMetrics.getWeekDayOfFirstYearDay(year));
		}
	}


	@Test
	public void testGetYearDayOfFirstWeekStart()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetYearDayOfWeekStart()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testToMillisLongTimeZone()
	{
		for (String timezone : TEST_TIME_ZONES)
		{
			TimeZone tz = TimeZone.getTimeZone(timezone);

			for (int pattern = 0; pattern < 4; ++pattern)
			{
				for (int civil = 0; civil < 2; ++civil)
				{
					IslamicCalendarMetrics calendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.values()[pattern], civil == 1);
					{
						for (long gregorian : DATE_MAPS[pattern][civil].keySet())
						{
							assertEquals(
								"wrong value for " + pattern + "  " + civil + "  " + Instance.toString(gregorian) + "  "
									+ Instance.toString(DATE_MAPS[pattern][civil].get(gregorian)) + "  "
									+ Instance.toString(calendar.toGregorian(DATE_MAPS[pattern][civil].get(gregorian))),
								GREGORIAN_CALENDAR.toMillis(gregorian, tz), calendar.toMillis(DATE_MAPS[pattern][civil].get(gregorian), tz));
						}
					}
				}
			}
		}
	}


	@Test
	public void testToMillisTimeZoneIntIntIntIntIntIntInt()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testToInstance()
	{
		for (String timezone : TEST_TIME_ZONES)
		{
			TimeZone tz = TimeZone.getTimeZone(timezone);

			for (int pattern = 0; pattern < 4; ++pattern)
			{
				for (int civil = 0; civil < 2; ++civil)
				{
					IslamicCalendarMetrics calendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.values()[pattern], civil == 1);
					{
						for (long gregorian : DATE_MAPS[pattern][civil].keySet())
						{
							assertEquals(
								"wrong value for " + pattern + "  " + civil + "  " + Instance.toString(gregorian) + "  "
									+ Instance.toString(DATE_MAPS[pattern][civil].get(gregorian)) + "  "
									+ Instance.toString(calendar.toInstance(GREGORIAN_CALENDAR.toMillis(gregorian, tz), tz)),
								(long) DATE_MAPS[pattern][civil].get(gregorian), calendar.toInstance(GREGORIAN_CALENDAR.toMillis(gregorian, tz), tz));
						}
					}
				}
			}
		}
	}


	@Test
	public void testIslamicToGregorian()
	{
		IslamicCalendarMetrics islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.I, true);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 22, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.I, false);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 23, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.II, true);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 22, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.II, false);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 23, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.III, true);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 22, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.III, false);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 23, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.IV, true);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 22, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.IV, false);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 23, 0, 0, 0, 0)));

		for (int pattern = 0; pattern < 4; ++pattern)
		{
			for (int civil = 0; civil < 2; ++civil)
			{
				IslamicCalendarMetrics calendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.values()[pattern], civil == 1);
				{
					for (long gregorian : DATE_MAPS[pattern][civil].keySet())
					{
						assertEquals(
							"wrong value for " + pattern + "  " + civil + "  " + Instance.toString(gregorian) + "  "
								+ Instance.toString(DATE_MAPS[pattern][civil].get(gregorian)) + "  "
								+ Instance.toString(calendar.toGregorian(DATE_MAPS[pattern][civil].get(gregorian))), gregorian,
							calendar.toGregorian(DATE_MAPS[pattern][civil].get(gregorian)));
					}
				}
			}
		}
	}


	@Test
	public void testNextMonthLong()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testNextMonthLongInt()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testPrevMonthLong()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testPrevMonthLongInt()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testNextDayLong()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testNextDayLongInt()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testPrevDayLong()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testPrevDayLongInt()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testStartOfWeek()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testSetDayOfWeek()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testIslamicCalendarMetrics()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testIsLeapYear()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testGetTimeStamp()
	{
		fail("Noch nicht implementiert");
	}


	@Test
	public void testNumLeapDaysSince1970()
	{
		fail("Noch nicht implementiert");
	}


	/**
	 * Add a number of instances created with <a href="http://www.staff.science.uu.nl/~gent0113/islam/islam_tabcal.htm">Islamic-Western Calendar Converter</a>
	 */
	private static void initMaps1()
	{
		/* Pattern Ia */
		// 1410
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1989, 8 - 1, 3, 0, 0, 0), Instance.make(1410, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1989, 9 - 1, 2, 0, 0, 0), Instance.make(1410, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1989, 10 - 1, 1, 0, 0, 0), Instance.make(1410, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1989, 10 - 1, 31, 0, 0, 0), Instance.make(1410, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1989, 11 - 1, 29, 0, 0, 0), Instance.make(1410, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1989, 12 - 1, 29, 0, 0, 0), Instance.make(1410, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 1 - 1, 27, 0, 0, 0), Instance.make(1410, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 2 - 1, 26, 0, 0, 0), Instance.make(1410, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 3 - 1, 27, 0, 0, 0), Instance.make(1410, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 4 - 1, 26, 0, 0, 0), Instance.make(1410, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 5 - 1, 25, 0, 0, 0), Instance.make(1410, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 6 - 1, 24, 0, 0, 0), Instance.make(1410, 12 - 1, 1, 0, 0, 0));

		// 1411
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 7 - 1, 23, 0, 0, 0), Instance.make(1411, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 8 - 1, 22, 0, 0, 0), Instance.make(1411, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 9 - 1, 20, 0, 0, 0), Instance.make(1411, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 10 - 1, 20, 0, 0, 0), Instance.make(1411, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 11 - 1, 18, 0, 0, 0), Instance.make(1411, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1990, 12 - 1, 18, 0, 0, 0), Instance.make(1411, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 1 - 1, 16, 0, 0, 0), Instance.make(1411, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 2 - 1, 15, 0, 0, 0), Instance.make(1411, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 3 - 1, 16, 0, 0, 0), Instance.make(1411, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 4 - 1, 15, 0, 0, 0), Instance.make(1411, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 5 - 1, 14, 0, 0, 0), Instance.make(1411, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 6 - 1, 13, 0, 0, 0), Instance.make(1411, 12 - 1, 1, 0, 0, 0));

		// 1412
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 7 - 1, 12, 0, 0, 0), Instance.make(1412, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 8 - 1, 11, 0, 0, 0), Instance.make(1412, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 9 - 1, 9, 0, 0, 0), Instance.make(1412, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 10 - 1, 9, 0, 0, 0), Instance.make(1412, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 11 - 1, 7, 0, 0, 0), Instance.make(1412, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1991, 12 - 1, 7, 0, 0, 0), Instance.make(1412, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 1 - 1, 5, 0, 0, 0), Instance.make(1412, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 2 - 1, 4, 0, 0, 0), Instance.make(1412, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 3 - 1, 4, 0, 0, 0), Instance.make(1412, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 4 - 1, 3, 0, 0, 0), Instance.make(1412, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 5 - 1, 2, 0, 0, 0), Instance.make(1412, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 6 - 1, 1, 0, 0, 0), Instance.make(1412, 12 - 1, 1, 0, 0, 0));

		// 1413
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 7 - 1, 1, 0, 0, 0), Instance.make(1413, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 7 - 1, 31, 0, 0, 0), Instance.make(1413, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 8 - 1, 29, 0, 0, 0), Instance.make(1413, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 9 - 1, 28, 0, 0, 0), Instance.make(1413, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 10 - 1, 27, 0, 0, 0), Instance.make(1413, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 11 - 1, 26, 0, 0, 0), Instance.make(1413, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1992, 12 - 1, 25, 0, 0, 0), Instance.make(1413, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 1 - 1, 24, 0, 0, 0), Instance.make(1413, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 2 - 1, 22, 0, 0, 0), Instance.make(1413, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 3 - 1, 24, 0, 0, 0), Instance.make(1413, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 4 - 1, 22, 0, 0, 0), Instance.make(1413, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 5 - 1, 22, 0, 0, 0), Instance.make(1413, 12 - 1, 1, 0, 0, 0));

		// 1414
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 6 - 1, 20, 0, 0, 0), Instance.make(1414, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 7 - 1, 20, 0, 0, 0), Instance.make(1414, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 8 - 1, 18, 0, 0, 0), Instance.make(1414, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 9 - 1, 17, 0, 0, 0), Instance.make(1414, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 10 - 1, 16, 0, 0, 0), Instance.make(1414, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 11 - 1, 15, 0, 0, 0), Instance.make(1414, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1993, 12 - 1, 14, 0, 0, 0), Instance.make(1414, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 1 - 1, 13, 0, 0, 0), Instance.make(1414, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 2 - 1, 11, 0, 0, 0), Instance.make(1414, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 3 - 1, 13, 0, 0, 0), Instance.make(1414, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 4 - 1, 11, 0, 0, 0), Instance.make(1414, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 5 - 1, 11, 0, 0, 0), Instance.make(1414, 12 - 1, 1, 0, 0, 0));

		// 1415
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 6 - 1, 9, 0, 0, 0), Instance.make(1415, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 7 - 1, 9, 0, 0, 0), Instance.make(1415, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 8 - 1, 7, 0, 0, 0), Instance.make(1415, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 9 - 1, 6, 0, 0, 0), Instance.make(1415, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 10 - 1, 5, 0, 0, 0), Instance.make(1415, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 11 - 1, 4, 0, 0, 0), Instance.make(1415, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1994, 12 - 1, 3, 0, 0, 0), Instance.make(1415, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 1 - 1, 2, 0, 0, 0), Instance.make(1415, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 1 - 1, 31, 0, 0, 0), Instance.make(1415, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 3 - 1, 2, 0, 0, 0), Instance.make(1415, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 3 - 1, 31, 0, 0, 0), Instance.make(1415, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 4 - 1, 30, 0, 0, 0), Instance.make(1415, 12 - 1, 1, 0, 0, 0));

		// 1416
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 5 - 1, 30, 0, 0, 0), Instance.make(1416, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 6 - 1, 29, 0, 0, 0), Instance.make(1416, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 7 - 1, 28, 0, 0, 0), Instance.make(1416, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 8 - 1, 27, 0, 0, 0), Instance.make(1416, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 9 - 1, 25, 0, 0, 0), Instance.make(1416, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 10 - 1, 25, 0, 0, 0), Instance.make(1416, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 11 - 1, 23, 0, 0, 0), Instance.make(1416, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1995, 12 - 1, 23, 0, 0, 0), Instance.make(1416, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 1 - 1, 21, 0, 0, 0), Instance.make(1416, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 2 - 1, 20, 0, 0, 0), Instance.make(1416, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 3 - 1, 20, 0, 0, 0), Instance.make(1416, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 4 - 1, 19, 0, 0, 0), Instance.make(1416, 12 - 1, 1, 0, 0, 0));

		// 1417
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 5 - 1, 18, 0, 0, 0), Instance.make(1417, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 6 - 1, 17, 0, 0, 0), Instance.make(1417, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 7 - 1, 16, 0, 0, 0), Instance.make(1417, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 8 - 1, 15, 0, 0, 0), Instance.make(1417, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 9 - 1, 13, 0, 0, 0), Instance.make(1417, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 10 - 1, 13, 0, 0, 0), Instance.make(1417, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 11 - 1, 11, 0, 0, 0), Instance.make(1417, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1996, 12 - 1, 11, 0, 0, 0), Instance.make(1417, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 1 - 1, 9, 0, 0, 0), Instance.make(1417, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 2 - 1, 8, 0, 0, 0), Instance.make(1417, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 3 - 1, 9, 0, 0, 0), Instance.make(1417, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 4 - 1, 8, 0, 0, 0), Instance.make(1417, 12 - 1, 1, 0, 0, 0));

		// 1418
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 5 - 1, 8, 0, 0, 0), Instance.make(1418, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 6 - 1, 7, 0, 0, 0), Instance.make(1418, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 7 - 1, 6, 0, 0, 0), Instance.make(1418, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 8 - 1, 5, 0, 0, 0), Instance.make(1418, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 9 - 1, 3, 0, 0, 0), Instance.make(1418, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 10 - 1, 3, 0, 0, 0), Instance.make(1418, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 11 - 1, 1, 0, 0, 0), Instance.make(1418, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 12 - 1, 1, 0, 0, 0), Instance.make(1418, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1997, 12 - 1, 30, 0, 0, 0), Instance.make(1418, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 1 - 1, 29, 0, 0, 0), Instance.make(1418, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 2 - 1, 27, 0, 0, 0), Instance.make(1418, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 3 - 1, 29, 0, 0, 0), Instance.make(1418, 12 - 1, 1, 0, 0, 0));

		// 1419
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 4 - 1, 27, 0, 0, 0), Instance.make(1419, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 5 - 1, 27, 0, 0, 0), Instance.make(1419, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 6 - 1, 25, 0, 0, 0), Instance.make(1419, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 7 - 1, 25, 0, 0, 0), Instance.make(1419, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 8 - 1, 23, 0, 0, 0), Instance.make(1419, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 9 - 1, 22, 0, 0, 0), Instance.make(1419, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 10 - 1, 21, 0, 0, 0), Instance.make(1419, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 11 - 1, 20, 0, 0, 0), Instance.make(1419, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1998, 12 - 1, 19, 0, 0, 0), Instance.make(1419, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 1 - 1, 18, 0, 0, 0), Instance.make(1419, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 2 - 1, 16, 0, 0, 0), Instance.make(1419, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 3 - 1, 18, 0, 0, 0), Instance.make(1419, 12 - 1, 1, 0, 0, 0));

		// 1420
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 4 - 1, 16, 0, 0, 0), Instance.make(1420, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 5 - 1, 16, 0, 0, 0), Instance.make(1420, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 6 - 1, 14, 0, 0, 0), Instance.make(1420, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 7 - 1, 14, 0, 0, 0), Instance.make(1420, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 8 - 1, 12, 0, 0, 0), Instance.make(1420, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 9 - 1, 11, 0, 0, 0), Instance.make(1420, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 10 - 1, 10, 0, 0, 0), Instance.make(1420, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 11 - 1, 9, 0, 0, 0), Instance.make(1420, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(1999, 12 - 1, 8, 0, 0, 0), Instance.make(1420, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 1 - 1, 7, 0, 0, 0), Instance.make(1420, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 2 - 1, 5, 0, 0, 0), Instance.make(1420, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 3 - 1, 6, 0, 0, 0), Instance.make(1420, 12 - 1, 1, 0, 0, 0));

		// 1421
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 4 - 1, 5, 0, 0, 0), Instance.make(1421, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 5 - 1, 5, 0, 0, 0), Instance.make(1421, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 6 - 1, 3, 0, 0, 0), Instance.make(1421, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 7 - 1, 3, 0, 0, 0), Instance.make(1421, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 8 - 1, 1, 0, 0, 0), Instance.make(1421, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 8 - 1, 31, 0, 0, 0), Instance.make(1421, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 9 - 1, 29, 0, 0, 0), Instance.make(1421, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 10 - 1, 29, 0, 0, 0), Instance.make(1421, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 11 - 1, 27, 0, 0, 0), Instance.make(1421, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2000, 12 - 1, 27, 0, 0, 0), Instance.make(1421, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 1 - 1, 25, 0, 0, 0), Instance.make(1421, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 2 - 1, 24, 0, 0, 0), Instance.make(1421, 12 - 1, 1, 0, 0, 0));

		// 1422
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 3 - 1, 25, 0, 0, 0), Instance.make(1422, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 4 - 1, 24, 0, 0, 0), Instance.make(1422, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 5 - 1, 23, 0, 0, 0), Instance.make(1422, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 6 - 1, 22, 0, 0, 0), Instance.make(1422, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 7 - 1, 21, 0, 0, 0), Instance.make(1422, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 8 - 1, 20, 0, 0, 0), Instance.make(1422, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 9 - 1, 18, 0, 0, 0), Instance.make(1422, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 10 - 1, 18, 0, 0, 0), Instance.make(1422, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 11 - 1, 16, 0, 0, 0), Instance.make(1422, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2001, 12 - 1, 16, 0, 0, 0), Instance.make(1422, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 1 - 1, 14, 0, 0, 0), Instance.make(1422, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 2 - 1, 13, 0, 0, 0), Instance.make(1422, 12 - 1, 1, 0, 0, 0));

		// 1423
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 3 - 1, 14, 0, 0, 0), Instance.make(1423, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 4 - 1, 13, 0, 0, 0), Instance.make(1423, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 5 - 1, 12, 0, 0, 0), Instance.make(1423, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 6 - 1, 11, 0, 0, 0), Instance.make(1423, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 7 - 1, 10, 0, 0, 0), Instance.make(1423, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 8 - 1, 9, 0, 0, 0), Instance.make(1423, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 9 - 1, 7, 0, 0, 0), Instance.make(1423, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 10 - 1, 7, 0, 0, 0), Instance.make(1423, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 11 - 1, 5, 0, 0, 0), Instance.make(1423, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2002, 12 - 1, 5, 0, 0, 0), Instance.make(1423, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 1 - 1, 3, 0, 0, 0), Instance.make(1423, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 2 - 1, 2, 0, 0, 0), Instance.make(1423, 12 - 1, 1, 0, 0, 0));

		// 1424
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 3 - 1, 4, 0, 0, 0), Instance.make(1424, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 4 - 1, 3, 0, 0, 0), Instance.make(1424, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 5 - 1, 2, 0, 0, 0), Instance.make(1424, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 6 - 1, 1, 0, 0, 0), Instance.make(1424, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 6 - 1, 30, 0, 0, 0), Instance.make(1424, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 7 - 1, 30, 0, 0, 0), Instance.make(1424, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 8 - 1, 28, 0, 0, 0), Instance.make(1424, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 9 - 1, 27, 0, 0, 0), Instance.make(1424, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 10 - 1, 26, 0, 0, 0), Instance.make(1424, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 11 - 1, 25, 0, 0, 0), Instance.make(1424, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2003, 12 - 1, 24, 0, 0, 0), Instance.make(1424, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 1 - 1, 23, 0, 0, 0), Instance.make(1424, 12 - 1, 1, 0, 0, 0));

		// 1425
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 2 - 1, 21, 0, 0, 0), Instance.make(1425, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 3 - 1, 22, 0, 0, 0), Instance.make(1425, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 4 - 1, 20, 0, 0, 0), Instance.make(1425, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 5 - 1, 20, 0, 0, 0), Instance.make(1425, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 6 - 1, 18, 0, 0, 0), Instance.make(1425, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 7 - 1, 18, 0, 0, 0), Instance.make(1425, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 8 - 1, 16, 0, 0, 0), Instance.make(1425, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 9 - 1, 15, 0, 0, 0), Instance.make(1425, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 10 - 1, 14, 0, 0, 0), Instance.make(1425, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 11 - 1, 13, 0, 0, 0), Instance.make(1425, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2004, 12 - 1, 12, 0, 0, 0), Instance.make(1425, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 1 - 1, 11, 0, 0, 0), Instance.make(1425, 12 - 1, 1, 0, 0, 0));

		// 1426
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 2 - 1, 10, 0, 0, 0), Instance.make(1426, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 3 - 1, 12, 0, 0, 0), Instance.make(1426, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 4 - 1, 10, 0, 0, 0), Instance.make(1426, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 5 - 1, 10, 0, 0, 0), Instance.make(1426, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 6 - 1, 8, 0, 0, 0), Instance.make(1426, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 7 - 1, 8, 0, 0, 0), Instance.make(1426, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 8 - 1, 6, 0, 0, 0), Instance.make(1426, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 9 - 1, 5, 0, 0, 0), Instance.make(1426, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 10 - 1, 4, 0, 0, 0), Instance.make(1426, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 11 - 1, 3, 0, 0, 0), Instance.make(1426, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2005, 12 - 1, 2, 0, 0, 0), Instance.make(1426, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 1 - 1, 1, 0, 0, 0), Instance.make(1426, 12 - 1, 1, 0, 0, 0));

		// 1427
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 1 - 1, 30, 0, 0, 0), Instance.make(1427, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 3 - 1, 1, 0, 0, 0), Instance.make(1427, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 3 - 1, 30, 0, 0, 0), Instance.make(1427, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 4 - 1, 29, 0, 0, 0), Instance.make(1427, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 5 - 1, 28, 0, 0, 0), Instance.make(1427, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 6 - 1, 27, 0, 0, 0), Instance.make(1427, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 7 - 1, 26, 0, 0, 0), Instance.make(1427, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 8 - 1, 25, 0, 0, 0), Instance.make(1427, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 9 - 1, 23, 0, 0, 0), Instance.make(1427, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 10 - 1, 23, 0, 0, 0), Instance.make(1427, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 11 - 1, 21, 0, 0, 0), Instance.make(1427, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2006, 12 - 1, 21, 0, 0, 0), Instance.make(1427, 12 - 1, 1, 0, 0, 0));

		// 1428
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 1 - 1, 19, 0, 0, 0), Instance.make(1428, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 2 - 1, 18, 0, 0, 0), Instance.make(1428, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 3 - 1, 19, 0, 0, 0), Instance.make(1428, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 4 - 1, 18, 0, 0, 0), Instance.make(1428, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 5 - 1, 17, 0, 0, 0), Instance.make(1428, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 6 - 1, 16, 0, 0, 0), Instance.make(1428, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 7 - 1, 15, 0, 0, 0), Instance.make(1428, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 8 - 1, 14, 0, 0, 0), Instance.make(1428, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 9 - 1, 12, 0, 0, 0), Instance.make(1428, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 10 - 1, 12, 0, 0, 0), Instance.make(1428, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 11 - 1, 10, 0, 0, 0), Instance.make(1428, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2007, 12 - 1, 10, 0, 0, 0), Instance.make(1428, 12 - 1, 1, 0, 0, 0));

		// 1429
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 1 - 1, 9, 0, 0, 0), Instance.make(1429, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 2 - 1, 8, 0, 0, 0), Instance.make(1429, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 3 - 1, 8, 0, 0, 0), Instance.make(1429, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 4 - 1, 7, 0, 0, 0), Instance.make(1429, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 5 - 1, 6, 0, 0, 0), Instance.make(1429, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 6 - 1, 5, 0, 0, 0), Instance.make(1429, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 7 - 1, 4, 0, 0, 0), Instance.make(1429, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 8 - 1, 3, 0, 0, 0), Instance.make(1429, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 9 - 1, 1, 0, 0, 0), Instance.make(1429, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 10 - 1, 1, 0, 0, 0), Instance.make(1429, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 10 - 1, 30, 0, 0, 0), Instance.make(1429, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 11 - 1, 29, 0, 0, 0), Instance.make(1429, 12 - 1, 1, 0, 0, 0));

		// 1430
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2008, 12 - 1, 28, 0, 0, 0), Instance.make(1430, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 1 - 1, 27, 0, 0, 0), Instance.make(1430, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 2 - 1, 25, 0, 0, 0), Instance.make(1430, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 3 - 1, 27, 0, 0, 0), Instance.make(1430, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 4 - 1, 25, 0, 0, 0), Instance.make(1430, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 5 - 1, 25, 0, 0, 0), Instance.make(1430, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 6 - 1, 23, 0, 0, 0), Instance.make(1430, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 7 - 1, 23, 0, 0, 0), Instance.make(1430, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 8 - 1, 21, 0, 0, 0), Instance.make(1430, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 9 - 1, 20, 0, 0, 0), Instance.make(1430, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 10 - 1, 19, 0, 0, 0), Instance.make(1430, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 11 - 1, 18, 0, 0, 0), Instance.make(1430, 12 - 1, 1, 0, 0, 0));

		// 1431
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2009, 12 - 1, 17, 0, 0, 0), Instance.make(1431, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 1 - 1, 16, 0, 0, 0), Instance.make(1431, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 2 - 1, 14, 0, 0, 0), Instance.make(1431, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 3 - 1, 16, 0, 0, 0), Instance.make(1431, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 4 - 1, 14, 0, 0, 0), Instance.make(1431, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 5 - 1, 14, 0, 0, 0), Instance.make(1431, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 6 - 1, 12, 0, 0, 0), Instance.make(1431, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 7 - 1, 12, 0, 0, 0), Instance.make(1431, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 8 - 1, 10, 0, 0, 0), Instance.make(1431, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 9 - 1, 9, 0, 0, 0), Instance.make(1431, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 10 - 1, 8, 0, 0, 0), Instance.make(1431, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 11 - 1, 7, 0, 0, 0), Instance.make(1431, 12 - 1, 1, 0, 0, 0));

		// 1432
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2010, 12 - 1, 7, 0, 0, 0), Instance.make(1432, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 1 - 1, 6, 0, 0, 0), Instance.make(1432, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 2 - 1, 4, 0, 0, 0), Instance.make(1432, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 3 - 1, 6, 0, 0, 0), Instance.make(1432, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 4 - 1, 4, 0, 0, 0), Instance.make(1432, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 5 - 1, 4, 0, 0, 0), Instance.make(1432, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 6 - 1, 2, 0, 0, 0), Instance.make(1432, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 7 - 1, 2, 0, 0, 0), Instance.make(1432, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 7 - 1, 31, 0, 0, 0), Instance.make(1432, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 8 - 1, 30, 0, 0, 0), Instance.make(1432, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 9 - 1, 28, 0, 0, 0), Instance.make(1432, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 10 - 1, 28, 0, 0, 0), Instance.make(1432, 12 - 1, 1, 0, 0, 0));

		// 1433
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 11 - 1, 26, 0, 0, 0), Instance.make(1433, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2011, 12 - 1, 26, 0, 0, 0), Instance.make(1433, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 1 - 1, 24, 0, 0, 0), Instance.make(1433, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 2 - 1, 23, 0, 0, 0), Instance.make(1433, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 3 - 1, 23, 0, 0, 0), Instance.make(1433, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 4 - 1, 22, 0, 0, 0), Instance.make(1433, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 5 - 1, 21, 0, 0, 0), Instance.make(1433, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 6 - 1, 20, 0, 0, 0), Instance.make(1433, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 7 - 1, 19, 0, 0, 0), Instance.make(1433, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 8 - 1, 18, 0, 0, 0), Instance.make(1433, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 9 - 1, 16, 0, 0, 0), Instance.make(1433, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 10 - 1, 16, 0, 0, 0), Instance.make(1433, 12 - 1, 1, 0, 0, 0));

		// 1434
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 11 - 1, 14, 0, 0, 0), Instance.make(1434, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2012, 12 - 1, 14, 0, 0, 0), Instance.make(1434, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 1 - 1, 12, 0, 0, 0), Instance.make(1434, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 2 - 1, 11, 0, 0, 0), Instance.make(1434, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 3 - 1, 12, 0, 0, 0), Instance.make(1434, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 4 - 1, 11, 0, 0, 0), Instance.make(1434, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 5 - 1, 10, 0, 0, 0), Instance.make(1434, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 6 - 1, 9, 0, 0, 0), Instance.make(1434, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 7 - 1, 8, 0, 0, 0), Instance.make(1434, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 8 - 1, 7, 0, 0, 0), Instance.make(1434, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 9 - 1, 5, 0, 0, 0), Instance.make(1434, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 10 - 1, 5, 0, 0, 0), Instance.make(1434, 12 - 1, 1, 0, 0, 0));

		// 1435
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 11 - 1, 4, 0, 0, 0), Instance.make(1435, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2013, 12 - 1, 4, 0, 0, 0), Instance.make(1435, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 1 - 1, 2, 0, 0, 0), Instance.make(1435, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 2 - 1, 1, 0, 0, 0), Instance.make(1435, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 3 - 1, 2, 0, 0, 0), Instance.make(1435, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 4 - 1, 1, 0, 0, 0), Instance.make(1435, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 4 - 1, 30, 0, 0, 0), Instance.make(1435, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 5 - 1, 30, 0, 0, 0), Instance.make(1435, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 6 - 1, 28, 0, 0, 0), Instance.make(1435, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 7 - 1, 28, 0, 0, 0), Instance.make(1435, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 8 - 1, 26, 0, 0, 0), Instance.make(1435, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 9 - 1, 25, 0, 0, 0), Instance.make(1435, 12 - 1, 1, 0, 0, 0));

		// 1436
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 10 - 1, 24, 0, 0, 0), Instance.make(1436, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 11 - 1, 23, 0, 0, 0), Instance.make(1436, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2014, 12 - 1, 22, 0, 0, 0), Instance.make(1436, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 1 - 1, 21, 0, 0, 0), Instance.make(1436, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 2 - 1, 19, 0, 0, 0), Instance.make(1436, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 3 - 1, 21, 0, 0, 0), Instance.make(1436, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 4 - 1, 19, 0, 0, 0), Instance.make(1436, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 5 - 1, 19, 0, 0, 0), Instance.make(1436, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 6 - 1, 17, 0, 0, 0), Instance.make(1436, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 7 - 1, 17, 0, 0, 0), Instance.make(1436, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 8 - 1, 15, 0, 0, 0), Instance.make(1436, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 9 - 1, 14, 0, 0, 0), Instance.make(1436, 12 - 1, 1, 0, 0, 0));

		// 1437
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 10 - 1, 14, 0, 0, 0), Instance.make(1437, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 11 - 1, 13, 0, 0, 0), Instance.make(1437, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2015, 12 - 1, 12, 0, 0, 0), Instance.make(1437, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 1 - 1, 11, 0, 0, 0), Instance.make(1437, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 2 - 1, 9, 0, 0, 0), Instance.make(1437, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 3 - 1, 10, 0, 0, 0), Instance.make(1437, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 4 - 1, 8, 0, 0, 0), Instance.make(1437, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 5 - 1, 8, 0, 0, 0), Instance.make(1437, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 6 - 1, 6, 0, 0, 0), Instance.make(1437, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 7 - 1, 6, 0, 0, 0), Instance.make(1437, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 8 - 1, 4, 0, 0, 0), Instance.make(1437, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 9 - 1, 3, 0, 0, 0), Instance.make(1437, 12 - 1, 1, 0, 0, 0));

		// 1438
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 10 - 1, 2, 0, 0, 0), Instance.make(1438, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 11 - 1, 1, 0, 0, 0), Instance.make(1438, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 11 - 1, 30, 0, 0, 0), Instance.make(1438, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2016, 12 - 1, 30, 0, 0, 0), Instance.make(1438, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 1 - 1, 28, 0, 0, 0), Instance.make(1438, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 2 - 1, 27, 0, 0, 0), Instance.make(1438, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 3 - 1, 28, 0, 0, 0), Instance.make(1438, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 4 - 1, 27, 0, 0, 0), Instance.make(1438, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 5 - 1, 26, 0, 0, 0), Instance.make(1438, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 6 - 1, 25, 0, 0, 0), Instance.make(1438, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 7 - 1, 24, 0, 0, 0), Instance.make(1438, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 8 - 1, 23, 0, 0, 0), Instance.make(1438, 12 - 1, 1, 0, 0, 0));

		// 1439
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 9 - 1, 21, 0, 0, 0), Instance.make(1439, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 10 - 1, 21, 0, 0, 0), Instance.make(1439, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 11 - 1, 19, 0, 0, 0), Instance.make(1439, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2017, 12 - 1, 19, 0, 0, 0), Instance.make(1439, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 1 - 1, 17, 0, 0, 0), Instance.make(1439, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 2 - 1, 16, 0, 0, 0), Instance.make(1439, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 3 - 1, 17, 0, 0, 0), Instance.make(1439, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 4 - 1, 16, 0, 0, 0), Instance.make(1439, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 5 - 1, 15, 0, 0, 0), Instance.make(1439, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 6 - 1, 14, 0, 0, 0), Instance.make(1439, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 7 - 1, 13, 0, 0, 0), Instance.make(1439, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 8 - 1, 12, 0, 0, 0), Instance.make(1439, 12 - 1, 1, 0, 0, 0));

		// 1440
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 9 - 1, 11, 0, 0, 0), Instance.make(1440, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 10 - 1, 11, 0, 0, 0), Instance.make(1440, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 11 - 1, 9, 0, 0, 0), Instance.make(1440, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2018, 12 - 1, 9, 0, 0, 0), Instance.make(1440, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2019, 1 - 1, 7, 0, 0, 0), Instance.make(1440, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2019, 2 - 1, 6, 0, 0, 0), Instance.make(1440, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2019, 3 - 1, 7, 0, 0, 0), Instance.make(1440, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2019, 4 - 1, 6, 0, 0, 0), Instance.make(1440, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2019, 5 - 1, 5, 0, 0, 0), Instance.make(1440, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2019, 6 - 1, 4, 0, 0, 0), Instance.make(1440, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2019, 7 - 1, 3, 0, 0, 0), Instance.make(1440, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][0].put(Instance.make(2019, 8 - 1, 2, 0, 0, 0), Instance.make(1440, 12 - 1, 1, 0, 0, 0));

		/* Pattern Ic */

		// 1410
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1989, 8 - 1, 4, 0, 0, 0), Instance.make(1410, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1989, 9 - 1, 3, 0, 0, 0), Instance.make(1410, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1989, 10 - 1, 2, 0, 0, 0), Instance.make(1410, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1989, 11 - 1, 1, 0, 0, 0), Instance.make(1410, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1989, 11 - 1, 30, 0, 0, 0), Instance.make(1410, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1989, 12 - 1, 30, 0, 0, 0), Instance.make(1410, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 1 - 1, 28, 0, 0, 0), Instance.make(1410, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 2 - 1, 27, 0, 0, 0), Instance.make(1410, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 3 - 1, 28, 0, 0, 0), Instance.make(1410, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 4 - 1, 27, 0, 0, 0), Instance.make(1410, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 5 - 1, 26, 0, 0, 0), Instance.make(1410, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 6 - 1, 25, 0, 0, 0), Instance.make(1410, 12 - 1, 1, 0, 0, 0));

		// 1411
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 7 - 1, 24, 0, 0, 0), Instance.make(1411, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 8 - 1, 23, 0, 0, 0), Instance.make(1411, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 9 - 1, 21, 0, 0, 0), Instance.make(1411, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 10 - 1, 21, 0, 0, 0), Instance.make(1411, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 11 - 1, 19, 0, 0, 0), Instance.make(1411, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1990, 12 - 1, 19, 0, 0, 0), Instance.make(1411, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 1 - 1, 17, 0, 0, 0), Instance.make(1411, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 2 - 1, 16, 0, 0, 0), Instance.make(1411, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 3 - 1, 17, 0, 0, 0), Instance.make(1411, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 4 - 1, 16, 0, 0, 0), Instance.make(1411, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 5 - 1, 15, 0, 0, 0), Instance.make(1411, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 6 - 1, 14, 0, 0, 0), Instance.make(1411, 12 - 1, 1, 0, 0, 0));

		// 1412
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 7 - 1, 13, 0, 0, 0), Instance.make(1412, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 8 - 1, 12, 0, 0, 0), Instance.make(1412, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 9 - 1, 10, 0, 0, 0), Instance.make(1412, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 10 - 1, 10, 0, 0, 0), Instance.make(1412, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 11 - 1, 8, 0, 0, 0), Instance.make(1412, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1991, 12 - 1, 8, 0, 0, 0), Instance.make(1412, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 1 - 1, 6, 0, 0, 0), Instance.make(1412, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 2 - 1, 5, 0, 0, 0), Instance.make(1412, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 3 - 1, 5, 0, 0, 0), Instance.make(1412, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 4 - 1, 4, 0, 0, 0), Instance.make(1412, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 5 - 1, 3, 0, 0, 0), Instance.make(1412, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 6 - 1, 2, 0, 0, 0), Instance.make(1412, 12 - 1, 1, 0, 0, 0));

		// 1413
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 7 - 1, 2, 0, 0, 0), Instance.make(1413, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 8 - 1, 1, 0, 0, 0), Instance.make(1413, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 8 - 1, 30, 0, 0, 0), Instance.make(1413, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 9 - 1, 29, 0, 0, 0), Instance.make(1413, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 10 - 1, 28, 0, 0, 0), Instance.make(1413, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 11 - 1, 27, 0, 0, 0), Instance.make(1413, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1992, 12 - 1, 26, 0, 0, 0), Instance.make(1413, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 1 - 1, 25, 0, 0, 0), Instance.make(1413, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 2 - 1, 23, 0, 0, 0), Instance.make(1413, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 3 - 1, 25, 0, 0, 0), Instance.make(1413, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 4 - 1, 23, 0, 0, 0), Instance.make(1413, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 5 - 1, 23, 0, 0, 0), Instance.make(1413, 12 - 1, 1, 0, 0, 0));

		// 1414
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 6 - 1, 21, 0, 0, 0), Instance.make(1414, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 7 - 1, 21, 0, 0, 0), Instance.make(1414, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 8 - 1, 19, 0, 0, 0), Instance.make(1414, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 9 - 1, 18, 0, 0, 0), Instance.make(1414, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 10 - 1, 17, 0, 0, 0), Instance.make(1414, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 11 - 1, 16, 0, 0, 0), Instance.make(1414, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1993, 12 - 1, 15, 0, 0, 0), Instance.make(1414, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 1 - 1, 14, 0, 0, 0), Instance.make(1414, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 2 - 1, 12, 0, 0, 0), Instance.make(1414, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 3 - 1, 14, 0, 0, 0), Instance.make(1414, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 4 - 1, 12, 0, 0, 0), Instance.make(1414, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 5 - 1, 12, 0, 0, 0), Instance.make(1414, 12 - 1, 1, 0, 0, 0));

		// 1415
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 6 - 1, 10, 0, 0, 0), Instance.make(1415, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 7 - 1, 10, 0, 0, 0), Instance.make(1415, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 8 - 1, 8, 0, 0, 0), Instance.make(1415, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 9 - 1, 7, 0, 0, 0), Instance.make(1415, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 10 - 1, 6, 0, 0, 0), Instance.make(1415, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 11 - 1, 5, 0, 0, 0), Instance.make(1415, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1994, 12 - 1, 4, 0, 0, 0), Instance.make(1415, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 1 - 1, 3, 0, 0, 0), Instance.make(1415, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 2 - 1, 1, 0, 0, 0), Instance.make(1415, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 3 - 1, 3, 0, 0, 0), Instance.make(1415, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 4 - 1, 1, 0, 0, 0), Instance.make(1415, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 5 - 1, 1, 0, 0, 0), Instance.make(1415, 12 - 1, 1, 0, 0, 0));

		// 1416
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 5 - 1, 31, 0, 0, 0), Instance.make(1416, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 6 - 1, 30, 0, 0, 0), Instance.make(1416, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 7 - 1, 29, 0, 0, 0), Instance.make(1416, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 8 - 1, 28, 0, 0, 0), Instance.make(1416, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 9 - 1, 26, 0, 0, 0), Instance.make(1416, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 10 - 1, 26, 0, 0, 0), Instance.make(1416, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 11 - 1, 24, 0, 0, 0), Instance.make(1416, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1995, 12 - 1, 24, 0, 0, 0), Instance.make(1416, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 1 - 1, 22, 0, 0, 0), Instance.make(1416, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 2 - 1, 21, 0, 0, 0), Instance.make(1416, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 3 - 1, 21, 0, 0, 0), Instance.make(1416, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 4 - 1, 20, 0, 0, 0), Instance.make(1416, 12 - 1, 1, 0, 0, 0));

		// 1417
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 5 - 1, 19, 0, 0, 0), Instance.make(1417, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 6 - 1, 18, 0, 0, 0), Instance.make(1417, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 7 - 1, 17, 0, 0, 0), Instance.make(1417, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 8 - 1, 16, 0, 0, 0), Instance.make(1417, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 9 - 1, 14, 0, 0, 0), Instance.make(1417, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 10 - 1, 14, 0, 0, 0), Instance.make(1417, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 11 - 1, 12, 0, 0, 0), Instance.make(1417, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1996, 12 - 1, 12, 0, 0, 0), Instance.make(1417, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 1 - 1, 10, 0, 0, 0), Instance.make(1417, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 2 - 1, 9, 0, 0, 0), Instance.make(1417, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 3 - 1, 10, 0, 0, 0), Instance.make(1417, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 4 - 1, 9, 0, 0, 0), Instance.make(1417, 12 - 1, 1, 0, 0, 0));

		// 1418
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 5 - 1, 9, 0, 0, 0), Instance.make(1418, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 6 - 1, 8, 0, 0, 0), Instance.make(1418, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 7 - 1, 7, 0, 0, 0), Instance.make(1418, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 8 - 1, 6, 0, 0, 0), Instance.make(1418, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 9 - 1, 4, 0, 0, 0), Instance.make(1418, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 10 - 1, 4, 0, 0, 0), Instance.make(1418, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 11 - 1, 2, 0, 0, 0), Instance.make(1418, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 12 - 1, 2, 0, 0, 0), Instance.make(1418, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1997, 12 - 1, 31, 0, 0, 0), Instance.make(1418, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 1 - 1, 30, 0, 0, 0), Instance.make(1418, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 2 - 1, 28, 0, 0, 0), Instance.make(1418, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 3 - 1, 30, 0, 0, 0), Instance.make(1418, 12 - 1, 1, 0, 0, 0));

		// 1419
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 4 - 1, 28, 0, 0, 0), Instance.make(1419, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 5 - 1, 28, 0, 0, 0), Instance.make(1419, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 6 - 1, 26, 0, 0, 0), Instance.make(1419, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 7 - 1, 26, 0, 0, 0), Instance.make(1419, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 8 - 1, 24, 0, 0, 0), Instance.make(1419, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 9 - 1, 23, 0, 0, 0), Instance.make(1419, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 10 - 1, 22, 0, 0, 0), Instance.make(1419, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 11 - 1, 21, 0, 0, 0), Instance.make(1419, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1998, 12 - 1, 20, 0, 0, 0), Instance.make(1419, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 1 - 1, 19, 0, 0, 0), Instance.make(1419, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 2 - 1, 17, 0, 0, 0), Instance.make(1419, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 3 - 1, 19, 0, 0, 0), Instance.make(1419, 12 - 1, 1, 0, 0, 0));

		// 1420
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 4 - 1, 17, 0, 0, 0), Instance.make(1420, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 5 - 1, 17, 0, 0, 0), Instance.make(1420, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 6 - 1, 15, 0, 0, 0), Instance.make(1420, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 7 - 1, 15, 0, 0, 0), Instance.make(1420, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 8 - 1, 13, 0, 0, 0), Instance.make(1420, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 9 - 1, 12, 0, 0, 0), Instance.make(1420, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 10 - 1, 11, 0, 0, 0), Instance.make(1420, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 11 - 1, 10, 0, 0, 0), Instance.make(1420, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(1999, 12 - 1, 9, 0, 0, 0), Instance.make(1420, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 1 - 1, 8, 0, 0, 0), Instance.make(1420, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 2 - 1, 6, 0, 0, 0), Instance.make(1420, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 3 - 1, 7, 0, 0, 0), Instance.make(1420, 12 - 1, 1, 0, 0, 0));

		// 1421
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 4 - 1, 6, 0, 0, 0), Instance.make(1421, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 5 - 1, 6, 0, 0, 0), Instance.make(1421, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 6 - 1, 4, 0, 0, 0), Instance.make(1421, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 7 - 1, 4, 0, 0, 0), Instance.make(1421, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 8 - 1, 2, 0, 0, 0), Instance.make(1421, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 9 - 1, 1, 0, 0, 0), Instance.make(1421, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 9 - 1, 30, 0, 0, 0), Instance.make(1421, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 10 - 1, 30, 0, 0, 0), Instance.make(1421, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 11 - 1, 28, 0, 0, 0), Instance.make(1421, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2000, 12 - 1, 28, 0, 0, 0), Instance.make(1421, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 1 - 1, 26, 0, 0, 0), Instance.make(1421, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 2 - 1, 25, 0, 0, 0), Instance.make(1421, 12 - 1, 1, 0, 0, 0));

		// 1422
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 3 - 1, 26, 0, 0, 0), Instance.make(1422, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 4 - 1, 25, 0, 0, 0), Instance.make(1422, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 5 - 1, 24, 0, 0, 0), Instance.make(1422, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 6 - 1, 23, 0, 0, 0), Instance.make(1422, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 7 - 1, 22, 0, 0, 0), Instance.make(1422, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 8 - 1, 21, 0, 0, 0), Instance.make(1422, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 9 - 1, 19, 0, 0, 0), Instance.make(1422, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 10 - 1, 19, 0, 0, 0), Instance.make(1422, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 11 - 1, 17, 0, 0, 0), Instance.make(1422, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2001, 12 - 1, 17, 0, 0, 0), Instance.make(1422, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 1 - 1, 15, 0, 0, 0), Instance.make(1422, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 2 - 1, 14, 0, 0, 0), Instance.make(1422, 12 - 1, 1, 0, 0, 0));

		// 1423
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 3 - 1, 15, 0, 0, 0), Instance.make(1423, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 4 - 1, 14, 0, 0, 0), Instance.make(1423, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 5 - 1, 13, 0, 0, 0), Instance.make(1423, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 6 - 1, 12, 0, 0, 0), Instance.make(1423, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 7 - 1, 11, 0, 0, 0), Instance.make(1423, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 8 - 1, 10, 0, 0, 0), Instance.make(1423, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 9 - 1, 8, 0, 0, 0), Instance.make(1423, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 10 - 1, 8, 0, 0, 0), Instance.make(1423, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 11 - 1, 6, 0, 0, 0), Instance.make(1423, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2002, 12 - 1, 6, 0, 0, 0), Instance.make(1423, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 1 - 1, 4, 0, 0, 0), Instance.make(1423, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 2 - 1, 3, 0, 0, 0), Instance.make(1423, 12 - 1, 1, 0, 0, 0));

		// 1424
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 3 - 1, 5, 0, 0, 0), Instance.make(1424, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 4 - 1, 4, 0, 0, 0), Instance.make(1424, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 5 - 1, 3, 0, 0, 0), Instance.make(1424, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 6 - 1, 2, 0, 0, 0), Instance.make(1424, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 7 - 1, 1, 0, 0, 0), Instance.make(1424, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 7 - 1, 31, 0, 0, 0), Instance.make(1424, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 8 - 1, 29, 0, 0, 0), Instance.make(1424, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 9 - 1, 28, 0, 0, 0), Instance.make(1424, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 10 - 1, 27, 0, 0, 0), Instance.make(1424, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 11 - 1, 26, 0, 0, 0), Instance.make(1424, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2003, 12 - 1, 25, 0, 0, 0), Instance.make(1424, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 1 - 1, 24, 0, 0, 0), Instance.make(1424, 12 - 1, 1, 0, 0, 0));

		// 1425
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 2 - 1, 22, 0, 0, 0), Instance.make(1425, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 3 - 1, 23, 0, 0, 0), Instance.make(1425, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 4 - 1, 21, 0, 0, 0), Instance.make(1425, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 5 - 1, 21, 0, 0, 0), Instance.make(1425, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 6 - 1, 19, 0, 0, 0), Instance.make(1425, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 7 - 1, 19, 0, 0, 0), Instance.make(1425, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 8 - 1, 17, 0, 0, 0), Instance.make(1425, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 9 - 1, 16, 0, 0, 0), Instance.make(1425, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 10 - 1, 15, 0, 0, 0), Instance.make(1425, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 11 - 1, 14, 0, 0, 0), Instance.make(1425, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2004, 12 - 1, 13, 0, 0, 0), Instance.make(1425, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 1 - 1, 12, 0, 0, 0), Instance.make(1425, 12 - 1, 1, 0, 0, 0));

		// 1426
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 2 - 1, 11, 0, 0, 0), Instance.make(1426, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 3 - 1, 13, 0, 0, 0), Instance.make(1426, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 4 - 1, 11, 0, 0, 0), Instance.make(1426, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 5 - 1, 11, 0, 0, 0), Instance.make(1426, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 6 - 1, 9, 0, 0, 0), Instance.make(1426, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 7 - 1, 9, 0, 0, 0), Instance.make(1426, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 8 - 1, 7, 0, 0, 0), Instance.make(1426, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 9 - 1, 6, 0, 0, 0), Instance.make(1426, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 10 - 1, 5, 0, 0, 0), Instance.make(1426, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 11 - 1, 4, 0, 0, 0), Instance.make(1426, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2005, 12 - 1, 3, 0, 0, 0), Instance.make(1426, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 1 - 1, 2, 0, 0, 0), Instance.make(1426, 12 - 1, 1, 0, 0, 0));

		// 1427
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 1 - 1, 31, 0, 0, 0), Instance.make(1427, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 3 - 1, 2, 0, 0, 0), Instance.make(1427, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 3 - 1, 31, 0, 0, 0), Instance.make(1427, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 4 - 1, 30, 0, 0, 0), Instance.make(1427, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 5 - 1, 29, 0, 0, 0), Instance.make(1427, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 6 - 1, 28, 0, 0, 0), Instance.make(1427, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 7 - 1, 27, 0, 0, 0), Instance.make(1427, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 8 - 1, 26, 0, 0, 0), Instance.make(1427, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 9 - 1, 24, 0, 0, 0), Instance.make(1427, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 10 - 1, 24, 0, 0, 0), Instance.make(1427, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 11 - 1, 22, 0, 0, 0), Instance.make(1427, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2006, 12 - 1, 22, 0, 0, 0), Instance.make(1427, 12 - 1, 1, 0, 0, 0));

		// 1428
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 1 - 1, 20, 0, 0, 0), Instance.make(1428, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 2 - 1, 19, 0, 0, 0), Instance.make(1428, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 3 - 1, 20, 0, 0, 0), Instance.make(1428, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 4 - 1, 19, 0, 0, 0), Instance.make(1428, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 5 - 1, 18, 0, 0, 0), Instance.make(1428, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 6 - 1, 17, 0, 0, 0), Instance.make(1428, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 7 - 1, 16, 0, 0, 0), Instance.make(1428, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 8 - 1, 15, 0, 0, 0), Instance.make(1428, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 9 - 1, 13, 0, 0, 0), Instance.make(1428, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 10 - 1, 13, 0, 0, 0), Instance.make(1428, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 11 - 1, 11, 0, 0, 0), Instance.make(1428, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2007, 12 - 1, 11, 0, 0, 0), Instance.make(1428, 12 - 1, 1, 0, 0, 0));

		// 1429
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 1 - 1, 10, 0, 0, 0), Instance.make(1429, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 2 - 1, 9, 0, 0, 0), Instance.make(1429, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 3 - 1, 9, 0, 0, 0), Instance.make(1429, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 4 - 1, 8, 0, 0, 0), Instance.make(1429, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 5 - 1, 7, 0, 0, 0), Instance.make(1429, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 6 - 1, 6, 0, 0, 0), Instance.make(1429, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 7 - 1, 5, 0, 0, 0), Instance.make(1429, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 8 - 1, 4, 0, 0, 0), Instance.make(1429, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 9 - 1, 2, 0, 0, 0), Instance.make(1429, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 10 - 1, 2, 0, 0, 0), Instance.make(1429, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 10 - 1, 31, 0, 0, 0), Instance.make(1429, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 11 - 1, 30, 0, 0, 0), Instance.make(1429, 12 - 1, 1, 0, 0, 0));

		// 1430
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2008, 12 - 1, 29, 0, 0, 0), Instance.make(1430, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 1 - 1, 28, 0, 0, 0), Instance.make(1430, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 2 - 1, 26, 0, 0, 0), Instance.make(1430, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 3 - 1, 28, 0, 0, 0), Instance.make(1430, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 4 - 1, 26, 0, 0, 0), Instance.make(1430, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 5 - 1, 26, 0, 0, 0), Instance.make(1430, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 6 - 1, 24, 0, 0, 0), Instance.make(1430, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 7 - 1, 24, 0, 0, 0), Instance.make(1430, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 8 - 1, 22, 0, 0, 0), Instance.make(1430, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 9 - 1, 21, 0, 0, 0), Instance.make(1430, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 10 - 1, 20, 0, 0, 0), Instance.make(1430, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 11 - 1, 19, 0, 0, 0), Instance.make(1430, 12 - 1, 1, 0, 0, 0));

		// 1431
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2009, 12 - 1, 18, 0, 0, 0), Instance.make(1431, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 1 - 1, 17, 0, 0, 0), Instance.make(1431, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 2 - 1, 15, 0, 0, 0), Instance.make(1431, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 3 - 1, 17, 0, 0, 0), Instance.make(1431, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 4 - 1, 15, 0, 0, 0), Instance.make(1431, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 5 - 1, 15, 0, 0, 0), Instance.make(1431, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 6 - 1, 13, 0, 0, 0), Instance.make(1431, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 7 - 1, 13, 0, 0, 0), Instance.make(1431, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 8 - 1, 11, 0, 0, 0), Instance.make(1431, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 9 - 1, 10, 0, 0, 0), Instance.make(1431, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 10 - 1, 9, 0, 0, 0), Instance.make(1431, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 11 - 1, 8, 0, 0, 0), Instance.make(1431, 12 - 1, 1, 0, 0, 0));

		// 1432
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2010, 12 - 1, 8, 0, 0, 0), Instance.make(1432, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 1 - 1, 7, 0, 0, 0), Instance.make(1432, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 2 - 1, 5, 0, 0, 0), Instance.make(1432, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 3 - 1, 7, 0, 0, 0), Instance.make(1432, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 4 - 1, 5, 0, 0, 0), Instance.make(1432, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 5 - 1, 5, 0, 0, 0), Instance.make(1432, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 6 - 1, 3, 0, 0, 0), Instance.make(1432, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 7 - 1, 3, 0, 0, 0), Instance.make(1432, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 8 - 1, 1, 0, 0, 0), Instance.make(1432, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 8 - 1, 31, 0, 0, 0), Instance.make(1432, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 9 - 1, 29, 0, 0, 0), Instance.make(1432, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 10 - 1, 29, 0, 0, 0), Instance.make(1432, 12 - 1, 1, 0, 0, 0));

		// 1433
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 11 - 1, 27, 0, 0, 0), Instance.make(1433, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2011, 12 - 1, 27, 0, 0, 0), Instance.make(1433, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 1 - 1, 25, 0, 0, 0), Instance.make(1433, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 2 - 1, 24, 0, 0, 0), Instance.make(1433, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 3 - 1, 24, 0, 0, 0), Instance.make(1433, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 4 - 1, 23, 0, 0, 0), Instance.make(1433, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 5 - 1, 22, 0, 0, 0), Instance.make(1433, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 6 - 1, 21, 0, 0, 0), Instance.make(1433, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 7 - 1, 20, 0, 0, 0), Instance.make(1433, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 8 - 1, 19, 0, 0, 0), Instance.make(1433, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 9 - 1, 17, 0, 0, 0), Instance.make(1433, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 10 - 1, 17, 0, 0, 0), Instance.make(1433, 12 - 1, 1, 0, 0, 0));

		// 1434
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 11 - 1, 15, 0, 0, 0), Instance.make(1434, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2012, 12 - 1, 15, 0, 0, 0), Instance.make(1434, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 1 - 1, 13, 0, 0, 0), Instance.make(1434, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 2 - 1, 12, 0, 0, 0), Instance.make(1434, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 3 - 1, 13, 0, 0, 0), Instance.make(1434, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 4 - 1, 12, 0, 0, 0), Instance.make(1434, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 5 - 1, 11, 0, 0, 0), Instance.make(1434, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 6 - 1, 10, 0, 0, 0), Instance.make(1434, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 7 - 1, 9, 0, 0, 0), Instance.make(1434, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 8 - 1, 8, 0, 0, 0), Instance.make(1434, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 9 - 1, 6, 0, 0, 0), Instance.make(1434, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 10 - 1, 6, 0, 0, 0), Instance.make(1434, 12 - 1, 1, 0, 0, 0));

		// 1435
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 11 - 1, 5, 0, 0, 0), Instance.make(1435, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2013, 12 - 1, 5, 0, 0, 0), Instance.make(1435, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 1 - 1, 3, 0, 0, 0), Instance.make(1435, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 2 - 1, 2, 0, 0, 0), Instance.make(1435, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 3 - 1, 3, 0, 0, 0), Instance.make(1435, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 4 - 1, 2, 0, 0, 0), Instance.make(1435, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 5 - 1, 1, 0, 0, 0), Instance.make(1435, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 5 - 1, 31, 0, 0, 0), Instance.make(1435, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 6 - 1, 29, 0, 0, 0), Instance.make(1435, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 7 - 1, 29, 0, 0, 0), Instance.make(1435, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 8 - 1, 27, 0, 0, 0), Instance.make(1435, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 9 - 1, 26, 0, 0, 0), Instance.make(1435, 12 - 1, 1, 0, 0, 0));

		// 1436
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 10 - 1, 25, 0, 0, 0), Instance.make(1436, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 11 - 1, 24, 0, 0, 0), Instance.make(1436, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2014, 12 - 1, 23, 0, 0, 0), Instance.make(1436, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 1 - 1, 22, 0, 0, 0), Instance.make(1436, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 2 - 1, 20, 0, 0, 0), Instance.make(1436, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 3 - 1, 22, 0, 0, 0), Instance.make(1436, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 4 - 1, 20, 0, 0, 0), Instance.make(1436, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 5 - 1, 20, 0, 0, 0), Instance.make(1436, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 6 - 1, 18, 0, 0, 0), Instance.make(1436, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 7 - 1, 18, 0, 0, 0), Instance.make(1436, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 8 - 1, 16, 0, 0, 0), Instance.make(1436, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 9 - 1, 15, 0, 0, 0), Instance.make(1436, 12 - 1, 1, 0, 0, 0));

		// 1437
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 10 - 1, 15, 0, 0, 0), Instance.make(1437, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 11 - 1, 14, 0, 0, 0), Instance.make(1437, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2015, 12 - 1, 13, 0, 0, 0), Instance.make(1437, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 1 - 1, 12, 0, 0, 0), Instance.make(1437, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 2 - 1, 10, 0, 0, 0), Instance.make(1437, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 3 - 1, 11, 0, 0, 0), Instance.make(1437, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 4 - 1, 9, 0, 0, 0), Instance.make(1437, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 5 - 1, 9, 0, 0, 0), Instance.make(1437, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 6 - 1, 7, 0, 0, 0), Instance.make(1437, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 7 - 1, 7, 0, 0, 0), Instance.make(1437, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 8 - 1, 5, 0, 0, 0), Instance.make(1437, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 9 - 1, 4, 0, 0, 0), Instance.make(1437, 12 - 1, 1, 0, 0, 0));

		// 1438
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 10 - 1, 3, 0, 0, 0), Instance.make(1438, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 11 - 1, 2, 0, 0, 0), Instance.make(1438, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 12 - 1, 1, 0, 0, 0), Instance.make(1438, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2016, 12 - 1, 31, 0, 0, 0), Instance.make(1438, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 1 - 1, 29, 0, 0, 0), Instance.make(1438, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 2 - 1, 28, 0, 0, 0), Instance.make(1438, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 3 - 1, 29, 0, 0, 0), Instance.make(1438, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 4 - 1, 28, 0, 0, 0), Instance.make(1438, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 5 - 1, 27, 0, 0, 0), Instance.make(1438, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 6 - 1, 26, 0, 0, 0), Instance.make(1438, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 7 - 1, 25, 0, 0, 0), Instance.make(1438, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 8 - 1, 24, 0, 0, 0), Instance.make(1438, 12 - 1, 1, 0, 0, 0));

		// 1439
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 9 - 1, 22, 0, 0, 0), Instance.make(1439, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 10 - 1, 22, 0, 0, 0), Instance.make(1439, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 11 - 1, 20, 0, 0, 0), Instance.make(1439, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2017, 12 - 1, 20, 0, 0, 0), Instance.make(1439, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 1 - 1, 18, 0, 0, 0), Instance.make(1439, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 2 - 1, 17, 0, 0, 0), Instance.make(1439, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 3 - 1, 18, 0, 0, 0), Instance.make(1439, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 4 - 1, 17, 0, 0, 0), Instance.make(1439, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 5 - 1, 16, 0, 0, 0), Instance.make(1439, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 6 - 1, 15, 0, 0, 0), Instance.make(1439, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 7 - 1, 14, 0, 0, 0), Instance.make(1439, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 8 - 1, 13, 0, 0, 0), Instance.make(1439, 12 - 1, 1, 0, 0, 0));

		// 1440
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 9 - 1, 12, 0, 0, 0), Instance.make(1440, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 10 - 1, 12, 0, 0, 0), Instance.make(1440, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 11 - 1, 10, 0, 0, 0), Instance.make(1440, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2018, 12 - 1, 10, 0, 0, 0), Instance.make(1440, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2019, 1 - 1, 8, 0, 0, 0), Instance.make(1440, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2019, 2 - 1, 7, 0, 0, 0), Instance.make(1440, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2019, 3 - 1, 8, 0, 0, 0), Instance.make(1440, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2019, 4 - 1, 7, 0, 0, 0), Instance.make(1440, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2019, 5 - 1, 6, 0, 0, 0), Instance.make(1440, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2019, 6 - 1, 5, 0, 0, 0), Instance.make(1440, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2019, 7 - 1, 4, 0, 0, 0), Instance.make(1440, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.I.ordinal()][1].put(Instance.make(2019, 8 - 1, 3, 0, 0, 0), Instance.make(1440, 12 - 1, 1, 0, 0, 0));
	}


	/**
	 * Add a number of instances created with <a href="http://www.staff.science.uu.nl/~gent0113/islam/islam_tabcal.htm">Islamic-Western Calendar Converter</a>
	 */
	private static void initMaps2()
	{

		/* Pattern IIa */

		// 1410
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1989, 8 - 1, 3, 0, 0, 0), Instance.make(1410, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1989, 9 - 1, 2, 0, 0, 0), Instance.make(1410, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1989, 10 - 1, 1, 0, 0, 0), Instance.make(1410, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1989, 10 - 1, 31, 0, 0, 0), Instance.make(1410, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1989, 11 - 1, 29, 0, 0, 0), Instance.make(1410, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1989, 12 - 1, 29, 0, 0, 0), Instance.make(1410, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 1 - 1, 27, 0, 0, 0), Instance.make(1410, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 2 - 1, 26, 0, 0, 0), Instance.make(1410, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 3 - 1, 27, 0, 0, 0), Instance.make(1410, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 4 - 1, 26, 0, 0, 0), Instance.make(1410, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 5 - 1, 25, 0, 0, 0), Instance.make(1410, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 6 - 1, 24, 0, 0, 0), Instance.make(1410, 12 - 1, 1, 0, 0, 0));

		// 1411
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 7 - 1, 23, 0, 0, 0), Instance.make(1411, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 8 - 1, 22, 0, 0, 0), Instance.make(1411, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 9 - 1, 20, 0, 0, 0), Instance.make(1411, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 10 - 1, 20, 0, 0, 0), Instance.make(1411, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 11 - 1, 18, 0, 0, 0), Instance.make(1411, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1990, 12 - 1, 18, 0, 0, 0), Instance.make(1411, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 1 - 1, 16, 0, 0, 0), Instance.make(1411, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 2 - 1, 15, 0, 0, 0), Instance.make(1411, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 3 - 1, 16, 0, 0, 0), Instance.make(1411, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 4 - 1, 15, 0, 0, 0), Instance.make(1411, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 5 - 1, 14, 0, 0, 0), Instance.make(1411, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 6 - 1, 13, 0, 0, 0), Instance.make(1411, 12 - 1, 1, 0, 0, 0));

		// 1412
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 7 - 1, 12, 0, 0, 0), Instance.make(1412, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 8 - 1, 11, 0, 0, 0), Instance.make(1412, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 9 - 1, 9, 0, 0, 0), Instance.make(1412, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 10 - 1, 9, 0, 0, 0), Instance.make(1412, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 11 - 1, 7, 0, 0, 0), Instance.make(1412, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1991, 12 - 1, 7, 0, 0, 0), Instance.make(1412, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 1 - 1, 5, 0, 0, 0), Instance.make(1412, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 2 - 1, 4, 0, 0, 0), Instance.make(1412, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 3 - 1, 4, 0, 0, 0), Instance.make(1412, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 4 - 1, 3, 0, 0, 0), Instance.make(1412, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 5 - 1, 2, 0, 0, 0), Instance.make(1412, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 6 - 1, 1, 0, 0, 0), Instance.make(1412, 12 - 1, 1, 0, 0, 0));

		// 1413
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 7 - 1, 1, 0, 0, 0), Instance.make(1413, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 7 - 1, 31, 0, 0, 0), Instance.make(1413, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 8 - 1, 29, 0, 0, 0), Instance.make(1413, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 9 - 1, 28, 0, 0, 0), Instance.make(1413, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 10 - 1, 27, 0, 0, 0), Instance.make(1413, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 11 - 1, 26, 0, 0, 0), Instance.make(1413, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1992, 12 - 1, 25, 0, 0, 0), Instance.make(1413, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 1 - 1, 24, 0, 0, 0), Instance.make(1413, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 2 - 1, 22, 0, 0, 0), Instance.make(1413, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 3 - 1, 24, 0, 0, 0), Instance.make(1413, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 4 - 1, 22, 0, 0, 0), Instance.make(1413, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 5 - 1, 22, 0, 0, 0), Instance.make(1413, 12 - 1, 1, 0, 0, 0));

		// 1414
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 6 - 1, 20, 0, 0, 0), Instance.make(1414, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 7 - 1, 20, 0, 0, 0), Instance.make(1414, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 8 - 1, 18, 0, 0, 0), Instance.make(1414, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 9 - 1, 17, 0, 0, 0), Instance.make(1414, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 10 - 1, 16, 0, 0, 0), Instance.make(1414, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 11 - 1, 15, 0, 0, 0), Instance.make(1414, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1993, 12 - 1, 14, 0, 0, 0), Instance.make(1414, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 1 - 1, 13, 0, 0, 0), Instance.make(1414, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 2 - 1, 11, 0, 0, 0), Instance.make(1414, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 3 - 1, 13, 0, 0, 0), Instance.make(1414, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 4 - 1, 11, 0, 0, 0), Instance.make(1414, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 5 - 1, 11, 0, 0, 0), Instance.make(1414, 12 - 1, 1, 0, 0, 0));

		// 1415
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 6 - 1, 9, 0, 0, 0), Instance.make(1415, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 7 - 1, 9, 0, 0, 0), Instance.make(1415, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 8 - 1, 7, 0, 0, 0), Instance.make(1415, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 9 - 1, 6, 0, 0, 0), Instance.make(1415, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 10 - 1, 5, 0, 0, 0), Instance.make(1415, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 11 - 1, 4, 0, 0, 0), Instance.make(1415, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1994, 12 - 1, 3, 0, 0, 0), Instance.make(1415, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 1 - 1, 2, 0, 0, 0), Instance.make(1415, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 1 - 1, 31, 0, 0, 0), Instance.make(1415, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 3 - 1, 2, 0, 0, 0), Instance.make(1415, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 3 - 1, 31, 0, 0, 0), Instance.make(1415, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 4 - 1, 30, 0, 0, 0), Instance.make(1415, 12 - 1, 1, 0, 0, 0));

		// 1416
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 5 - 1, 30, 0, 0, 0), Instance.make(1416, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 6 - 1, 29, 0, 0, 0), Instance.make(1416, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 7 - 1, 28, 0, 0, 0), Instance.make(1416, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 8 - 1, 27, 0, 0, 0), Instance.make(1416, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 9 - 1, 25, 0, 0, 0), Instance.make(1416, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 10 - 1, 25, 0, 0, 0), Instance.make(1416, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 11 - 1, 23, 0, 0, 0), Instance.make(1416, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1995, 12 - 1, 23, 0, 0, 0), Instance.make(1416, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 1 - 1, 21, 0, 0, 0), Instance.make(1416, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 2 - 1, 20, 0, 0, 0), Instance.make(1416, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 3 - 1, 20, 0, 0, 0), Instance.make(1416, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 4 - 1, 19, 0, 0, 0), Instance.make(1416, 12 - 1, 1, 0, 0, 0));

		// 1417
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 5 - 1, 18, 0, 0, 0), Instance.make(1417, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 6 - 1, 17, 0, 0, 0), Instance.make(1417, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 7 - 1, 16, 0, 0, 0), Instance.make(1417, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 8 - 1, 15, 0, 0, 0), Instance.make(1417, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 9 - 1, 13, 0, 0, 0), Instance.make(1417, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 10 - 1, 13, 0, 0, 0), Instance.make(1417, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 11 - 1, 11, 0, 0, 0), Instance.make(1417, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1996, 12 - 1, 11, 0, 0, 0), Instance.make(1417, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 1 - 1, 9, 0, 0, 0), Instance.make(1417, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 2 - 1, 8, 0, 0, 0), Instance.make(1417, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 3 - 1, 9, 0, 0, 0), Instance.make(1417, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 4 - 1, 8, 0, 0, 0), Instance.make(1417, 12 - 1, 1, 0, 0, 0));

		// 1418
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 5 - 1, 8, 0, 0, 0), Instance.make(1418, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 6 - 1, 7, 0, 0, 0), Instance.make(1418, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 7 - 1, 6, 0, 0, 0), Instance.make(1418, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 8 - 1, 5, 0, 0, 0), Instance.make(1418, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 9 - 1, 3, 0, 0, 0), Instance.make(1418, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 10 - 1, 3, 0, 0, 0), Instance.make(1418, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 11 - 1, 1, 0, 0, 0), Instance.make(1418, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 12 - 1, 1, 0, 0, 0), Instance.make(1418, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1997, 12 - 1, 30, 0, 0, 0), Instance.make(1418, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 1 - 1, 29, 0, 0, 0), Instance.make(1418, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 2 - 1, 27, 0, 0, 0), Instance.make(1418, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 3 - 1, 29, 0, 0, 0), Instance.make(1418, 12 - 1, 1, 0, 0, 0));

		// 1419
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 4 - 1, 27, 0, 0, 0), Instance.make(1419, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 5 - 1, 27, 0, 0, 0), Instance.make(1419, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 6 - 1, 25, 0, 0, 0), Instance.make(1419, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 7 - 1, 25, 0, 0, 0), Instance.make(1419, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 8 - 1, 23, 0, 0, 0), Instance.make(1419, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 9 - 1, 22, 0, 0, 0), Instance.make(1419, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 10 - 1, 21, 0, 0, 0), Instance.make(1419, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 11 - 1, 20, 0, 0, 0), Instance.make(1419, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1998, 12 - 1, 19, 0, 0, 0), Instance.make(1419, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 1 - 1, 18, 0, 0, 0), Instance.make(1419, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 2 - 1, 16, 0, 0, 0), Instance.make(1419, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 3 - 1, 18, 0, 0, 0), Instance.make(1419, 12 - 1, 1, 0, 0, 0));

		// 1420
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 4 - 1, 16, 0, 0, 0), Instance.make(1420, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 5 - 1, 16, 0, 0, 0), Instance.make(1420, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 6 - 1, 14, 0, 0, 0), Instance.make(1420, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 7 - 1, 14, 0, 0, 0), Instance.make(1420, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 8 - 1, 12, 0, 0, 0), Instance.make(1420, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 9 - 1, 11, 0, 0, 0), Instance.make(1420, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 10 - 1, 10, 0, 0, 0), Instance.make(1420, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 11 - 1, 9, 0, 0, 0), Instance.make(1420, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(1999, 12 - 1, 8, 0, 0, 0), Instance.make(1420, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 1 - 1, 7, 0, 0, 0), Instance.make(1420, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 2 - 1, 5, 0, 0, 0), Instance.make(1420, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 3 - 1, 6, 0, 0, 0), Instance.make(1420, 12 - 1, 1, 0, 0, 0));

		// 1421
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 4 - 1, 5, 0, 0, 0), Instance.make(1421, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 5 - 1, 5, 0, 0, 0), Instance.make(1421, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 6 - 1, 3, 0, 0, 0), Instance.make(1421, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 7 - 1, 3, 0, 0, 0), Instance.make(1421, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 8 - 1, 1, 0, 0, 0), Instance.make(1421, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 8 - 1, 31, 0, 0, 0), Instance.make(1421, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 9 - 1, 29, 0, 0, 0), Instance.make(1421, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 10 - 1, 29, 0, 0, 0), Instance.make(1421, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 11 - 1, 27, 0, 0, 0), Instance.make(1421, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2000, 12 - 1, 27, 0, 0, 0), Instance.make(1421, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 1 - 1, 25, 0, 0, 0), Instance.make(1421, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 2 - 1, 24, 0, 0, 0), Instance.make(1421, 12 - 1, 1, 0, 0, 0));

		// 1422
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 3 - 1, 25, 0, 0, 0), Instance.make(1422, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 4 - 1, 24, 0, 0, 0), Instance.make(1422, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 5 - 1, 23, 0, 0, 0), Instance.make(1422, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 6 - 1, 22, 0, 0, 0), Instance.make(1422, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 7 - 1, 21, 0, 0, 0), Instance.make(1422, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 8 - 1, 20, 0, 0, 0), Instance.make(1422, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 9 - 1, 18, 0, 0, 0), Instance.make(1422, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 10 - 1, 18, 0, 0, 0), Instance.make(1422, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 11 - 1, 16, 0, 0, 0), Instance.make(1422, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2001, 12 - 1, 16, 0, 0, 0), Instance.make(1422, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 1 - 1, 14, 0, 0, 0), Instance.make(1422, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 2 - 1, 13, 0, 0, 0), Instance.make(1422, 12 - 1, 1, 0, 0, 0));

		// 1423
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 3 - 1, 14, 0, 0, 0), Instance.make(1423, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 4 - 1, 13, 0, 0, 0), Instance.make(1423, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 5 - 1, 12, 0, 0, 0), Instance.make(1423, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 6 - 1, 11, 0, 0, 0), Instance.make(1423, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 7 - 1, 10, 0, 0, 0), Instance.make(1423, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 8 - 1, 9, 0, 0, 0), Instance.make(1423, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 9 - 1, 7, 0, 0, 0), Instance.make(1423, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 10 - 1, 7, 0, 0, 0), Instance.make(1423, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 11 - 1, 5, 0, 0, 0), Instance.make(1423, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2002, 12 - 1, 5, 0, 0, 0), Instance.make(1423, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 1 - 1, 3, 0, 0, 0), Instance.make(1423, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 2 - 1, 2, 0, 0, 0), Instance.make(1423, 12 - 1, 1, 0, 0, 0));

		// 1424
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 3 - 1, 4, 0, 0, 0), Instance.make(1424, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 4 - 1, 3, 0, 0, 0), Instance.make(1424, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 5 - 1, 2, 0, 0, 0), Instance.make(1424, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 6 - 1, 1, 0, 0, 0), Instance.make(1424, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 6 - 1, 30, 0, 0, 0), Instance.make(1424, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 7 - 1, 30, 0, 0, 0), Instance.make(1424, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 8 - 1, 28, 0, 0, 0), Instance.make(1424, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 9 - 1, 27, 0, 0, 0), Instance.make(1424, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 10 - 1, 26, 0, 0, 0), Instance.make(1424, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 11 - 1, 25, 0, 0, 0), Instance.make(1424, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2003, 12 - 1, 24, 0, 0, 0), Instance.make(1424, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 1 - 1, 23, 0, 0, 0), Instance.make(1424, 12 - 1, 1, 0, 0, 0));

		// 1425
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 2 - 1, 21, 0, 0, 0), Instance.make(1425, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 3 - 1, 22, 0, 0, 0), Instance.make(1425, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 4 - 1, 20, 0, 0, 0), Instance.make(1425, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 5 - 1, 20, 0, 0, 0), Instance.make(1425, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 6 - 1, 18, 0, 0, 0), Instance.make(1425, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 7 - 1, 18, 0, 0, 0), Instance.make(1425, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 8 - 1, 16, 0, 0, 0), Instance.make(1425, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 9 - 1, 15, 0, 0, 0), Instance.make(1425, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 10 - 1, 14, 0, 0, 0), Instance.make(1425, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 11 - 1, 13, 0, 0, 0), Instance.make(1425, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2004, 12 - 1, 12, 0, 0, 0), Instance.make(1425, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 1 - 1, 11, 0, 0, 0), Instance.make(1425, 12 - 1, 1, 0, 0, 0));

		// 1426
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 2 - 1, 9, 0, 0, 0), Instance.make(1426, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 3 - 1, 11, 0, 0, 0), Instance.make(1426, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 4 - 1, 9, 0, 0, 0), Instance.make(1426, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 5 - 1, 9, 0, 0, 0), Instance.make(1426, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 6 - 1, 7, 0, 0, 0), Instance.make(1426, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 7 - 1, 7, 0, 0, 0), Instance.make(1426, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 8 - 1, 5, 0, 0, 0), Instance.make(1426, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 9 - 1, 4, 0, 0, 0), Instance.make(1426, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 10 - 1, 3, 0, 0, 0), Instance.make(1426, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 11 - 1, 2, 0, 0, 0), Instance.make(1426, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 12 - 1, 1, 0, 0, 0), Instance.make(1426, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2005, 12 - 1, 31, 0, 0, 0), Instance.make(1426, 12 - 1, 1, 0, 0, 0));

		// 1427
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 1 - 1, 30, 0, 0, 0), Instance.make(1427, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 3 - 1, 1, 0, 0, 0), Instance.make(1427, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 3 - 1, 30, 0, 0, 0), Instance.make(1427, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 4 - 1, 29, 0, 0, 0), Instance.make(1427, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 5 - 1, 28, 0, 0, 0), Instance.make(1427, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 6 - 1, 27, 0, 0, 0), Instance.make(1427, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 7 - 1, 26, 0, 0, 0), Instance.make(1427, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 8 - 1, 25, 0, 0, 0), Instance.make(1427, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 9 - 1, 23, 0, 0, 0), Instance.make(1427, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 10 - 1, 23, 0, 0, 0), Instance.make(1427, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 11 - 1, 21, 0, 0, 0), Instance.make(1427, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2006, 12 - 1, 21, 0, 0, 0), Instance.make(1427, 12 - 1, 1, 0, 0, 0));

		// 1428
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 1 - 1, 19, 0, 0, 0), Instance.make(1428, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 2 - 1, 18, 0, 0, 0), Instance.make(1428, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 3 - 1, 19, 0, 0, 0), Instance.make(1428, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 4 - 1, 18, 0, 0, 0), Instance.make(1428, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 5 - 1, 17, 0, 0, 0), Instance.make(1428, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 6 - 1, 16, 0, 0, 0), Instance.make(1428, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 7 - 1, 15, 0, 0, 0), Instance.make(1428, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 8 - 1, 14, 0, 0, 0), Instance.make(1428, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 9 - 1, 12, 0, 0, 0), Instance.make(1428, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 10 - 1, 12, 0, 0, 0), Instance.make(1428, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 11 - 1, 10, 0, 0, 0), Instance.make(1428, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2007, 12 - 1, 10, 0, 0, 0), Instance.make(1428, 12 - 1, 1, 0, 0, 0));

		// 1429
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 1 - 1, 9, 0, 0, 0), Instance.make(1429, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 2 - 1, 8, 0, 0, 0), Instance.make(1429, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 3 - 1, 8, 0, 0, 0), Instance.make(1429, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 4 - 1, 7, 0, 0, 0), Instance.make(1429, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 5 - 1, 6, 0, 0, 0), Instance.make(1429, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 6 - 1, 5, 0, 0, 0), Instance.make(1429, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 7 - 1, 4, 0, 0, 0), Instance.make(1429, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 8 - 1, 3, 0, 0, 0), Instance.make(1429, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 9 - 1, 1, 0, 0, 0), Instance.make(1429, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 10 - 1, 1, 0, 0, 0), Instance.make(1429, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 10 - 1, 30, 0, 0, 0), Instance.make(1429, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 11 - 1, 29, 0, 0, 0), Instance.make(1429, 12 - 1, 1, 0, 0, 0));

		// 1430
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2008, 12 - 1, 28, 0, 0, 0), Instance.make(1430, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 1 - 1, 27, 0, 0, 0), Instance.make(1430, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 2 - 1, 25, 0, 0, 0), Instance.make(1430, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 3 - 1, 27, 0, 0, 0), Instance.make(1430, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 4 - 1, 25, 0, 0, 0), Instance.make(1430, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 5 - 1, 25, 0, 0, 0), Instance.make(1430, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 6 - 1, 23, 0, 0, 0), Instance.make(1430, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 7 - 1, 23, 0, 0, 0), Instance.make(1430, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 8 - 1, 21, 0, 0, 0), Instance.make(1430, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 9 - 1, 20, 0, 0, 0), Instance.make(1430, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 10 - 1, 19, 0, 0, 0), Instance.make(1430, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 11 - 1, 18, 0, 0, 0), Instance.make(1430, 12 - 1, 1, 0, 0, 0));

		// 1431
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2009, 12 - 1, 17, 0, 0, 0), Instance.make(1431, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 1 - 1, 16, 0, 0, 0), Instance.make(1431, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 2 - 1, 14, 0, 0, 0), Instance.make(1431, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 3 - 1, 16, 0, 0, 0), Instance.make(1431, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 4 - 1, 14, 0, 0, 0), Instance.make(1431, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 5 - 1, 14, 0, 0, 0), Instance.make(1431, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 6 - 1, 12, 0, 0, 0), Instance.make(1431, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 7 - 1, 12, 0, 0, 0), Instance.make(1431, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 8 - 1, 10, 0, 0, 0), Instance.make(1431, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 9 - 1, 9, 0, 0, 0), Instance.make(1431, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 10 - 1, 8, 0, 0, 0), Instance.make(1431, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 11 - 1, 7, 0, 0, 0), Instance.make(1431, 12 - 1, 1, 0, 0, 0));

		// 1432
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2010, 12 - 1, 7, 0, 0, 0), Instance.make(1432, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 1 - 1, 6, 0, 0, 0), Instance.make(1432, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 2 - 1, 4, 0, 0, 0), Instance.make(1432, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 3 - 1, 6, 0, 0, 0), Instance.make(1432, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 4 - 1, 4, 0, 0, 0), Instance.make(1432, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 5 - 1, 4, 0, 0, 0), Instance.make(1432, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 6 - 1, 2, 0, 0, 0), Instance.make(1432, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 7 - 1, 2, 0, 0, 0), Instance.make(1432, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 7 - 1, 31, 0, 0, 0), Instance.make(1432, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 8 - 1, 30, 0, 0, 0), Instance.make(1432, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 9 - 1, 28, 0, 0, 0), Instance.make(1432, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 10 - 1, 28, 0, 0, 0), Instance.make(1432, 12 - 1, 1, 0, 0, 0));

		// 1433
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 11 - 1, 26, 0, 0, 0), Instance.make(1433, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2011, 12 - 1, 26, 0, 0, 0), Instance.make(1433, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 1 - 1, 24, 0, 0, 0), Instance.make(1433, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 2 - 1, 23, 0, 0, 0), Instance.make(1433, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 3 - 1, 23, 0, 0, 0), Instance.make(1433, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 4 - 1, 22, 0, 0, 0), Instance.make(1433, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 5 - 1, 21, 0, 0, 0), Instance.make(1433, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 6 - 1, 20, 0, 0, 0), Instance.make(1433, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 7 - 1, 19, 0, 0, 0), Instance.make(1433, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 8 - 1, 18, 0, 0, 0), Instance.make(1433, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 9 - 1, 16, 0, 0, 0), Instance.make(1433, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 10 - 1, 16, 0, 0, 0), Instance.make(1433, 12 - 1, 1, 0, 0, 0));

		// 1434
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 11 - 1, 14, 0, 0, 0), Instance.make(1434, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2012, 12 - 1, 14, 0, 0, 0), Instance.make(1434, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 1 - 1, 12, 0, 0, 0), Instance.make(1434, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 2 - 1, 11, 0, 0, 0), Instance.make(1434, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 3 - 1, 12, 0, 0, 0), Instance.make(1434, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 4 - 1, 11, 0, 0, 0), Instance.make(1434, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 5 - 1, 10, 0, 0, 0), Instance.make(1434, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 6 - 1, 9, 0, 0, 0), Instance.make(1434, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 7 - 1, 8, 0, 0, 0), Instance.make(1434, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 8 - 1, 7, 0, 0, 0), Instance.make(1434, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 9 - 1, 5, 0, 0, 0), Instance.make(1434, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 10 - 1, 5, 0, 0, 0), Instance.make(1434, 12 - 1, 1, 0, 0, 0));

		// 1435
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 11 - 1, 4, 0, 0, 0), Instance.make(1435, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2013, 12 - 1, 4, 0, 0, 0), Instance.make(1435, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 1 - 1, 2, 0, 0, 0), Instance.make(1435, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 2 - 1, 1, 0, 0, 0), Instance.make(1435, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 3 - 1, 2, 0, 0, 0), Instance.make(1435, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 4 - 1, 1, 0, 0, 0), Instance.make(1435, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 4 - 1, 30, 0, 0, 0), Instance.make(1435, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 5 - 1, 30, 0, 0, 0), Instance.make(1435, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 6 - 1, 28, 0, 0, 0), Instance.make(1435, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 7 - 1, 28, 0, 0, 0), Instance.make(1435, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 8 - 1, 26, 0, 0, 0), Instance.make(1435, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 9 - 1, 25, 0, 0, 0), Instance.make(1435, 12 - 1, 1, 0, 0, 0));

		// 1436
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 10 - 1, 24, 0, 0, 0), Instance.make(1436, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 11 - 1, 23, 0, 0, 0), Instance.make(1436, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2014, 12 - 1, 22, 0, 0, 0), Instance.make(1436, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 1 - 1, 21, 0, 0, 0), Instance.make(1436, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 2 - 1, 19, 0, 0, 0), Instance.make(1436, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 3 - 1, 21, 0, 0, 0), Instance.make(1436, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 4 - 1, 19, 0, 0, 0), Instance.make(1436, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 5 - 1, 19, 0, 0, 0), Instance.make(1436, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 6 - 1, 17, 0, 0, 0), Instance.make(1436, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 7 - 1, 17, 0, 0, 0), Instance.make(1436, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 8 - 1, 15, 0, 0, 0), Instance.make(1436, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 9 - 1, 14, 0, 0, 0), Instance.make(1436, 12 - 1, 1, 0, 0, 0));

		// 1437
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 10 - 1, 14, 0, 0, 0), Instance.make(1437, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 11 - 1, 13, 0, 0, 0), Instance.make(1437, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2015, 12 - 1, 12, 0, 0, 0), Instance.make(1437, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 1 - 1, 11, 0, 0, 0), Instance.make(1437, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 2 - 1, 9, 0, 0, 0), Instance.make(1437, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 3 - 1, 10, 0, 0, 0), Instance.make(1437, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 4 - 1, 8, 0, 0, 0), Instance.make(1437, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 5 - 1, 8, 0, 0, 0), Instance.make(1437, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 6 - 1, 6, 0, 0, 0), Instance.make(1437, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 7 - 1, 6, 0, 0, 0), Instance.make(1437, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 8 - 1, 4, 0, 0, 0), Instance.make(1437, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 9 - 1, 3, 0, 0, 0), Instance.make(1437, 12 - 1, 1, 0, 0, 0));

		// 1438
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 10 - 1, 2, 0, 0, 0), Instance.make(1438, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 11 - 1, 1, 0, 0, 0), Instance.make(1438, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 11 - 1, 30, 0, 0, 0), Instance.make(1438, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2016, 12 - 1, 30, 0, 0, 0), Instance.make(1438, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 1 - 1, 28, 0, 0, 0), Instance.make(1438, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 2 - 1, 27, 0, 0, 0), Instance.make(1438, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 3 - 1, 28, 0, 0, 0), Instance.make(1438, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 4 - 1, 27, 0, 0, 0), Instance.make(1438, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 5 - 1, 26, 0, 0, 0), Instance.make(1438, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 6 - 1, 25, 0, 0, 0), Instance.make(1438, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 7 - 1, 24, 0, 0, 0), Instance.make(1438, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 8 - 1, 23, 0, 0, 0), Instance.make(1438, 12 - 1, 1, 0, 0, 0));

		// 1439
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 9 - 1, 21, 0, 0, 0), Instance.make(1439, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 10 - 1, 21, 0, 0, 0), Instance.make(1439, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 11 - 1, 19, 0, 0, 0), Instance.make(1439, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2017, 12 - 1, 19, 0, 0, 0), Instance.make(1439, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 1 - 1, 17, 0, 0, 0), Instance.make(1439, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 2 - 1, 16, 0, 0, 0), Instance.make(1439, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 3 - 1, 17, 0, 0, 0), Instance.make(1439, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 4 - 1, 16, 0, 0, 0), Instance.make(1439, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 5 - 1, 15, 0, 0, 0), Instance.make(1439, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 6 - 1, 14, 0, 0, 0), Instance.make(1439, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 7 - 1, 13, 0, 0, 0), Instance.make(1439, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 8 - 1, 12, 0, 0, 0), Instance.make(1439, 12 - 1, 1, 0, 0, 0));

		// 1440
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 9 - 1, 11, 0, 0, 0), Instance.make(1440, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 10 - 1, 11, 0, 0, 0), Instance.make(1440, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 11 - 1, 9, 0, 0, 0), Instance.make(1440, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2018, 12 - 1, 9, 0, 0, 0), Instance.make(1440, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2019, 1 - 1, 7, 0, 0, 0), Instance.make(1440, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2019, 2 - 1, 6, 0, 0, 0), Instance.make(1440, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2019, 3 - 1, 7, 0, 0, 0), Instance.make(1440, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2019, 4 - 1, 6, 0, 0, 0), Instance.make(1440, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2019, 5 - 1, 5, 0, 0, 0), Instance.make(1440, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2019, 6 - 1, 4, 0, 0, 0), Instance.make(1440, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2019, 7 - 1, 3, 0, 0, 0), Instance.make(1440, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][0].put(Instance.make(2019, 8 - 1, 2, 0, 0, 0), Instance.make(1440, 12 - 1, 1, 0, 0, 0));

		/* Pattern IIc */

		// 1410
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1989, 8 - 1, 4, 0, 0, 0), Instance.make(1410, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1989, 9 - 1, 3, 0, 0, 0), Instance.make(1410, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1989, 10 - 1, 2, 0, 0, 0), Instance.make(1410, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1989, 11 - 1, 1, 0, 0, 0), Instance.make(1410, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1989, 11 - 1, 30, 0, 0, 0), Instance.make(1410, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1989, 12 - 1, 30, 0, 0, 0), Instance.make(1410, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 1 - 1, 28, 0, 0, 0), Instance.make(1410, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 2 - 1, 27, 0, 0, 0), Instance.make(1410, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 3 - 1, 28, 0, 0, 0), Instance.make(1410, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 4 - 1, 27, 0, 0, 0), Instance.make(1410, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 5 - 1, 26, 0, 0, 0), Instance.make(1410, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 6 - 1, 25, 0, 0, 0), Instance.make(1410, 12 - 1, 1, 0, 0, 0));

		// 1411
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 7 - 1, 24, 0, 0, 0), Instance.make(1411, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 8 - 1, 23, 0, 0, 0), Instance.make(1411, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 9 - 1, 21, 0, 0, 0), Instance.make(1411, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 10 - 1, 21, 0, 0, 0), Instance.make(1411, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 11 - 1, 19, 0, 0, 0), Instance.make(1411, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1990, 12 - 1, 19, 0, 0, 0), Instance.make(1411, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 1 - 1, 17, 0, 0, 0), Instance.make(1411, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 2 - 1, 16, 0, 0, 0), Instance.make(1411, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 3 - 1, 17, 0, 0, 0), Instance.make(1411, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 4 - 1, 16, 0, 0, 0), Instance.make(1411, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 5 - 1, 15, 0, 0, 0), Instance.make(1411, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 6 - 1, 14, 0, 0, 0), Instance.make(1411, 12 - 1, 1, 0, 0, 0));

		// 1412
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 7 - 1, 13, 0, 0, 0), Instance.make(1412, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 8 - 1, 12, 0, 0, 0), Instance.make(1412, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 9 - 1, 10, 0, 0, 0), Instance.make(1412, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 10 - 1, 10, 0, 0, 0), Instance.make(1412, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 11 - 1, 8, 0, 0, 0), Instance.make(1412, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1991, 12 - 1, 8, 0, 0, 0), Instance.make(1412, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 1 - 1, 6, 0, 0, 0), Instance.make(1412, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 2 - 1, 5, 0, 0, 0), Instance.make(1412, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 3 - 1, 5, 0, 0, 0), Instance.make(1412, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 4 - 1, 4, 0, 0, 0), Instance.make(1412, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 5 - 1, 3, 0, 0, 0), Instance.make(1412, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 6 - 1, 2, 0, 0, 0), Instance.make(1412, 12 - 1, 1, 0, 0, 0));

		// 1413
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 7 - 1, 2, 0, 0, 0), Instance.make(1413, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 8 - 1, 1, 0, 0, 0), Instance.make(1413, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 8 - 1, 30, 0, 0, 0), Instance.make(1413, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 9 - 1, 29, 0, 0, 0), Instance.make(1413, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 10 - 1, 28, 0, 0, 0), Instance.make(1413, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 11 - 1, 27, 0, 0, 0), Instance.make(1413, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1992, 12 - 1, 26, 0, 0, 0), Instance.make(1413, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 1 - 1, 25, 0, 0, 0), Instance.make(1413, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 2 - 1, 23, 0, 0, 0), Instance.make(1413, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 3 - 1, 25, 0, 0, 0), Instance.make(1413, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 4 - 1, 23, 0, 0, 0), Instance.make(1413, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 5 - 1, 23, 0, 0, 0), Instance.make(1413, 12 - 1, 1, 0, 0, 0));

		// 1414
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 6 - 1, 21, 0, 0, 0), Instance.make(1414, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 7 - 1, 21, 0, 0, 0), Instance.make(1414, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 8 - 1, 19, 0, 0, 0), Instance.make(1414, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 9 - 1, 18, 0, 0, 0), Instance.make(1414, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 10 - 1, 17, 0, 0, 0), Instance.make(1414, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 11 - 1, 16, 0, 0, 0), Instance.make(1414, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1993, 12 - 1, 15, 0, 0, 0), Instance.make(1414, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 1 - 1, 14, 0, 0, 0), Instance.make(1414, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 2 - 1, 12, 0, 0, 0), Instance.make(1414, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 3 - 1, 14, 0, 0, 0), Instance.make(1414, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 4 - 1, 12, 0, 0, 0), Instance.make(1414, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 5 - 1, 12, 0, 0, 0), Instance.make(1414, 12 - 1, 1, 0, 0, 0));

		// 1415
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 6 - 1, 10, 0, 0, 0), Instance.make(1415, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 7 - 1, 10, 0, 0, 0), Instance.make(1415, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 8 - 1, 8, 0, 0, 0), Instance.make(1415, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 9 - 1, 7, 0, 0, 0), Instance.make(1415, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 10 - 1, 6, 0, 0, 0), Instance.make(1415, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 11 - 1, 5, 0, 0, 0), Instance.make(1415, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1994, 12 - 1, 4, 0, 0, 0), Instance.make(1415, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 1 - 1, 3, 0, 0, 0), Instance.make(1415, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 2 - 1, 1, 0, 0, 0), Instance.make(1415, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 3 - 1, 3, 0, 0, 0), Instance.make(1415, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 4 - 1, 1, 0, 0, 0), Instance.make(1415, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 5 - 1, 1, 0, 0, 0), Instance.make(1415, 12 - 1, 1, 0, 0, 0));

		// 1416
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 5 - 1, 31, 0, 0, 0), Instance.make(1416, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 6 - 1, 30, 0, 0, 0), Instance.make(1416, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 7 - 1, 29, 0, 0, 0), Instance.make(1416, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 8 - 1, 28, 0, 0, 0), Instance.make(1416, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 9 - 1, 26, 0, 0, 0), Instance.make(1416, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 10 - 1, 26, 0, 0, 0), Instance.make(1416, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 11 - 1, 24, 0, 0, 0), Instance.make(1416, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1995, 12 - 1, 24, 0, 0, 0), Instance.make(1416, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 1 - 1, 22, 0, 0, 0), Instance.make(1416, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 2 - 1, 21, 0, 0, 0), Instance.make(1416, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 3 - 1, 21, 0, 0, 0), Instance.make(1416, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 4 - 1, 20, 0, 0, 0), Instance.make(1416, 12 - 1, 1, 0, 0, 0));

		// 1417
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 5 - 1, 19, 0, 0, 0), Instance.make(1417, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 6 - 1, 18, 0, 0, 0), Instance.make(1417, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 7 - 1, 17, 0, 0, 0), Instance.make(1417, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 8 - 1, 16, 0, 0, 0), Instance.make(1417, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 9 - 1, 14, 0, 0, 0), Instance.make(1417, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 10 - 1, 14, 0, 0, 0), Instance.make(1417, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 11 - 1, 12, 0, 0, 0), Instance.make(1417, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1996, 12 - 1, 12, 0, 0, 0), Instance.make(1417, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 1 - 1, 10, 0, 0, 0), Instance.make(1417, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 2 - 1, 9, 0, 0, 0), Instance.make(1417, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 3 - 1, 10, 0, 0, 0), Instance.make(1417, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 4 - 1, 9, 0, 0, 0), Instance.make(1417, 12 - 1, 1, 0, 0, 0));

		// 1418
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 5 - 1, 9, 0, 0, 0), Instance.make(1418, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 6 - 1, 8, 0, 0, 0), Instance.make(1418, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 7 - 1, 7, 0, 0, 0), Instance.make(1418, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 8 - 1, 6, 0, 0, 0), Instance.make(1418, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 9 - 1, 4, 0, 0, 0), Instance.make(1418, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 10 - 1, 4, 0, 0, 0), Instance.make(1418, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 11 - 1, 2, 0, 0, 0), Instance.make(1418, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 12 - 1, 2, 0, 0, 0), Instance.make(1418, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1997, 12 - 1, 31, 0, 0, 0), Instance.make(1418, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 1 - 1, 30, 0, 0, 0), Instance.make(1418, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 2 - 1, 28, 0, 0, 0), Instance.make(1418, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 3 - 1, 30, 0, 0, 0), Instance.make(1418, 12 - 1, 1, 0, 0, 0));

		// 1419
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 4 - 1, 28, 0, 0, 0), Instance.make(1419, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 5 - 1, 28, 0, 0, 0), Instance.make(1419, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 6 - 1, 26, 0, 0, 0), Instance.make(1419, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 7 - 1, 26, 0, 0, 0), Instance.make(1419, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 8 - 1, 24, 0, 0, 0), Instance.make(1419, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 9 - 1, 23, 0, 0, 0), Instance.make(1419, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 10 - 1, 22, 0, 0, 0), Instance.make(1419, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 11 - 1, 21, 0, 0, 0), Instance.make(1419, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1998, 12 - 1, 20, 0, 0, 0), Instance.make(1419, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 1 - 1, 19, 0, 0, 0), Instance.make(1419, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 2 - 1, 17, 0, 0, 0), Instance.make(1419, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 3 - 1, 19, 0, 0, 0), Instance.make(1419, 12 - 1, 1, 0, 0, 0));

		// 1420
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 4 - 1, 17, 0, 0, 0), Instance.make(1420, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 5 - 1, 17, 0, 0, 0), Instance.make(1420, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 6 - 1, 15, 0, 0, 0), Instance.make(1420, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 7 - 1, 15, 0, 0, 0), Instance.make(1420, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 8 - 1, 13, 0, 0, 0), Instance.make(1420, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 9 - 1, 12, 0, 0, 0), Instance.make(1420, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 10 - 1, 11, 0, 0, 0), Instance.make(1420, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 11 - 1, 10, 0, 0, 0), Instance.make(1420, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(1999, 12 - 1, 9, 0, 0, 0), Instance.make(1420, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 1 - 1, 8, 0, 0, 0), Instance.make(1420, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 2 - 1, 6, 0, 0, 0), Instance.make(1420, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 3 - 1, 7, 0, 0, 0), Instance.make(1420, 12 - 1, 1, 0, 0, 0));

		// 1421
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 4 - 1, 6, 0, 0, 0), Instance.make(1421, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 5 - 1, 6, 0, 0, 0), Instance.make(1421, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 6 - 1, 4, 0, 0, 0), Instance.make(1421, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 7 - 1, 4, 0, 0, 0), Instance.make(1421, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 8 - 1, 2, 0, 0, 0), Instance.make(1421, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 9 - 1, 1, 0, 0, 0), Instance.make(1421, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 9 - 1, 30, 0, 0, 0), Instance.make(1421, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 10 - 1, 30, 0, 0, 0), Instance.make(1421, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 11 - 1, 28, 0, 0, 0), Instance.make(1421, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2000, 12 - 1, 28, 0, 0, 0), Instance.make(1421, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 1 - 1, 26, 0, 0, 0), Instance.make(1421, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 2 - 1, 25, 0, 0, 0), Instance.make(1421, 12 - 1, 1, 0, 0, 0));

		// 1422
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 3 - 1, 26, 0, 0, 0), Instance.make(1422, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 4 - 1, 25, 0, 0, 0), Instance.make(1422, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 5 - 1, 24, 0, 0, 0), Instance.make(1422, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 6 - 1, 23, 0, 0, 0), Instance.make(1422, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 7 - 1, 22, 0, 0, 0), Instance.make(1422, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 8 - 1, 21, 0, 0, 0), Instance.make(1422, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 9 - 1, 19, 0, 0, 0), Instance.make(1422, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 10 - 1, 19, 0, 0, 0), Instance.make(1422, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 11 - 1, 17, 0, 0, 0), Instance.make(1422, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2001, 12 - 1, 17, 0, 0, 0), Instance.make(1422, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 1 - 1, 15, 0, 0, 0), Instance.make(1422, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 2 - 1, 14, 0, 0, 0), Instance.make(1422, 12 - 1, 1, 0, 0, 0));

		// 1423
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 3 - 1, 15, 0, 0, 0), Instance.make(1423, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 4 - 1, 14, 0, 0, 0), Instance.make(1423, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 5 - 1, 13, 0, 0, 0), Instance.make(1423, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 6 - 1, 12, 0, 0, 0), Instance.make(1423, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 7 - 1, 11, 0, 0, 0), Instance.make(1423, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 8 - 1, 10, 0, 0, 0), Instance.make(1423, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 9 - 1, 8, 0, 0, 0), Instance.make(1423, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 10 - 1, 8, 0, 0, 0), Instance.make(1423, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 11 - 1, 6, 0, 0, 0), Instance.make(1423, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2002, 12 - 1, 6, 0, 0, 0), Instance.make(1423, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 1 - 1, 4, 0, 0, 0), Instance.make(1423, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 2 - 1, 3, 0, 0, 0), Instance.make(1423, 12 - 1, 1, 0, 0, 0));

		// 1424
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 3 - 1, 5, 0, 0, 0), Instance.make(1424, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 4 - 1, 4, 0, 0, 0), Instance.make(1424, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 5 - 1, 3, 0, 0, 0), Instance.make(1424, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 6 - 1, 2, 0, 0, 0), Instance.make(1424, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 7 - 1, 1, 0, 0, 0), Instance.make(1424, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 7 - 1, 31, 0, 0, 0), Instance.make(1424, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 8 - 1, 29, 0, 0, 0), Instance.make(1424, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 9 - 1, 28, 0, 0, 0), Instance.make(1424, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 10 - 1, 27, 0, 0, 0), Instance.make(1424, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 11 - 1, 26, 0, 0, 0), Instance.make(1424, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2003, 12 - 1, 25, 0, 0, 0), Instance.make(1424, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 1 - 1, 24, 0, 0, 0), Instance.make(1424, 12 - 1, 1, 0, 0, 0));

		// 1425
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 2 - 1, 22, 0, 0, 0), Instance.make(1425, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 3 - 1, 23, 0, 0, 0), Instance.make(1425, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 4 - 1, 21, 0, 0, 0), Instance.make(1425, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 5 - 1, 21, 0, 0, 0), Instance.make(1425, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 6 - 1, 19, 0, 0, 0), Instance.make(1425, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 7 - 1, 19, 0, 0, 0), Instance.make(1425, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 8 - 1, 17, 0, 0, 0), Instance.make(1425, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 9 - 1, 16, 0, 0, 0), Instance.make(1425, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 10 - 1, 15, 0, 0, 0), Instance.make(1425, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 11 - 1, 14, 0, 0, 0), Instance.make(1425, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2004, 12 - 1, 13, 0, 0, 0), Instance.make(1425, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 1 - 1, 12, 0, 0, 0), Instance.make(1425, 12 - 1, 1, 0, 0, 0));

		// 1426
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 2 - 1, 10, 0, 0, 0), Instance.make(1426, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 3 - 1, 12, 0, 0, 0), Instance.make(1426, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 4 - 1, 10, 0, 0, 0), Instance.make(1426, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 5 - 1, 10, 0, 0, 0), Instance.make(1426, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 6 - 1, 8, 0, 0, 0), Instance.make(1426, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 7 - 1, 8, 0, 0, 0), Instance.make(1426, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 8 - 1, 6, 0, 0, 0), Instance.make(1426, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 9 - 1, 5, 0, 0, 0), Instance.make(1426, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 10 - 1, 4, 0, 0, 0), Instance.make(1426, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 11 - 1, 3, 0, 0, 0), Instance.make(1426, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2005, 12 - 1, 2, 0, 0, 0), Instance.make(1426, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 1 - 1, 1, 0, 0, 0), Instance.make(1426, 12 - 1, 1, 0, 0, 0));

		// 1427
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 1 - 1, 31, 0, 0, 0), Instance.make(1427, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 3 - 1, 2, 0, 0, 0), Instance.make(1427, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 3 - 1, 31, 0, 0, 0), Instance.make(1427, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 4 - 1, 30, 0, 0, 0), Instance.make(1427, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 5 - 1, 29, 0, 0, 0), Instance.make(1427, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 6 - 1, 28, 0, 0, 0), Instance.make(1427, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 7 - 1, 27, 0, 0, 0), Instance.make(1427, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 8 - 1, 26, 0, 0, 0), Instance.make(1427, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 9 - 1, 24, 0, 0, 0), Instance.make(1427, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 10 - 1, 24, 0, 0, 0), Instance.make(1427, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 11 - 1, 22, 0, 0, 0), Instance.make(1427, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2006, 12 - 1, 22, 0, 0, 0), Instance.make(1427, 12 - 1, 1, 0, 0, 0));

		// 1428
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 1 - 1, 20, 0, 0, 0), Instance.make(1428, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 2 - 1, 19, 0, 0, 0), Instance.make(1428, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 3 - 1, 20, 0, 0, 0), Instance.make(1428, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 4 - 1, 19, 0, 0, 0), Instance.make(1428, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 5 - 1, 18, 0, 0, 0), Instance.make(1428, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 6 - 1, 17, 0, 0, 0), Instance.make(1428, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 7 - 1, 16, 0, 0, 0), Instance.make(1428, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 8 - 1, 15, 0, 0, 0), Instance.make(1428, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 9 - 1, 13, 0, 0, 0), Instance.make(1428, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 10 - 1, 13, 0, 0, 0), Instance.make(1428, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 11 - 1, 11, 0, 0, 0), Instance.make(1428, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2007, 12 - 1, 11, 0, 0, 0), Instance.make(1428, 12 - 1, 1, 0, 0, 0));

		// 1429
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 1 - 1, 10, 0, 0, 0), Instance.make(1429, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 2 - 1, 9, 0, 0, 0), Instance.make(1429, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 3 - 1, 9, 0, 0, 0), Instance.make(1429, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 4 - 1, 8, 0, 0, 0), Instance.make(1429, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 5 - 1, 7, 0, 0, 0), Instance.make(1429, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 6 - 1, 6, 0, 0, 0), Instance.make(1429, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 7 - 1, 5, 0, 0, 0), Instance.make(1429, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 8 - 1, 4, 0, 0, 0), Instance.make(1429, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 9 - 1, 2, 0, 0, 0), Instance.make(1429, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 10 - 1, 2, 0, 0, 0), Instance.make(1429, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 10 - 1, 31, 0, 0, 0), Instance.make(1429, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 11 - 1, 30, 0, 0, 0), Instance.make(1429, 12 - 1, 1, 0, 0, 0));

		// 1430
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2008, 12 - 1, 29, 0, 0, 0), Instance.make(1430, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 1 - 1, 28, 0, 0, 0), Instance.make(1430, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 2 - 1, 26, 0, 0, 0), Instance.make(1430, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 3 - 1, 28, 0, 0, 0), Instance.make(1430, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 4 - 1, 26, 0, 0, 0), Instance.make(1430, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 5 - 1, 26, 0, 0, 0), Instance.make(1430, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 6 - 1, 24, 0, 0, 0), Instance.make(1430, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 7 - 1, 24, 0, 0, 0), Instance.make(1430, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 8 - 1, 22, 0, 0, 0), Instance.make(1430, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 9 - 1, 21, 0, 0, 0), Instance.make(1430, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 10 - 1, 20, 0, 0, 0), Instance.make(1430, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 11 - 1, 19, 0, 0, 0), Instance.make(1430, 12 - 1, 1, 0, 0, 0));

		// 1431
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2009, 12 - 1, 18, 0, 0, 0), Instance.make(1431, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 1 - 1, 17, 0, 0, 0), Instance.make(1431, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 2 - 1, 15, 0, 0, 0), Instance.make(1431, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 3 - 1, 17, 0, 0, 0), Instance.make(1431, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 4 - 1, 15, 0, 0, 0), Instance.make(1431, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 5 - 1, 15, 0, 0, 0), Instance.make(1431, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 6 - 1, 13, 0, 0, 0), Instance.make(1431, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 7 - 1, 13, 0, 0, 0), Instance.make(1431, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 8 - 1, 11, 0, 0, 0), Instance.make(1431, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 9 - 1, 10, 0, 0, 0), Instance.make(1431, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 10 - 1, 9, 0, 0, 0), Instance.make(1431, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 11 - 1, 8, 0, 0, 0), Instance.make(1431, 12 - 1, 1, 0, 0, 0));

		// 1432
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2010, 12 - 1, 8, 0, 0, 0), Instance.make(1432, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 1 - 1, 7, 0, 0, 0), Instance.make(1432, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 2 - 1, 5, 0, 0, 0), Instance.make(1432, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 3 - 1, 7, 0, 0, 0), Instance.make(1432, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 4 - 1, 5, 0, 0, 0), Instance.make(1432, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 5 - 1, 5, 0, 0, 0), Instance.make(1432, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 6 - 1, 3, 0, 0, 0), Instance.make(1432, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 7 - 1, 3, 0, 0, 0), Instance.make(1432, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 8 - 1, 1, 0, 0, 0), Instance.make(1432, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 8 - 1, 31, 0, 0, 0), Instance.make(1432, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 9 - 1, 29, 0, 0, 0), Instance.make(1432, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 10 - 1, 29, 0, 0, 0), Instance.make(1432, 12 - 1, 1, 0, 0, 0));

		// 1433
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 11 - 1, 27, 0, 0, 0), Instance.make(1433, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2011, 12 - 1, 27, 0, 0, 0), Instance.make(1433, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 1 - 1, 25, 0, 0, 0), Instance.make(1433, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 2 - 1, 24, 0, 0, 0), Instance.make(1433, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 3 - 1, 24, 0, 0, 0), Instance.make(1433, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 4 - 1, 23, 0, 0, 0), Instance.make(1433, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 5 - 1, 22, 0, 0, 0), Instance.make(1433, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 6 - 1, 21, 0, 0, 0), Instance.make(1433, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 7 - 1, 20, 0, 0, 0), Instance.make(1433, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 8 - 1, 19, 0, 0, 0), Instance.make(1433, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 9 - 1, 17, 0, 0, 0), Instance.make(1433, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 10 - 1, 17, 0, 0, 0), Instance.make(1433, 12 - 1, 1, 0, 0, 0));

		// 1434
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 11 - 1, 15, 0, 0, 0), Instance.make(1434, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2012, 12 - 1, 15, 0, 0, 0), Instance.make(1434, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 1 - 1, 13, 0, 0, 0), Instance.make(1434, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 2 - 1, 12, 0, 0, 0), Instance.make(1434, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 3 - 1, 13, 0, 0, 0), Instance.make(1434, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 4 - 1, 12, 0, 0, 0), Instance.make(1434, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 5 - 1, 11, 0, 0, 0), Instance.make(1434, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 6 - 1, 10, 0, 0, 0), Instance.make(1434, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 7 - 1, 9, 0, 0, 0), Instance.make(1434, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 8 - 1, 8, 0, 0, 0), Instance.make(1434, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 9 - 1, 6, 0, 0, 0), Instance.make(1434, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 10 - 1, 6, 0, 0, 0), Instance.make(1434, 12 - 1, 1, 0, 0, 0));

		// 1435
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 11 - 1, 5, 0, 0, 0), Instance.make(1435, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2013, 12 - 1, 5, 0, 0, 0), Instance.make(1435, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 1 - 1, 3, 0, 0, 0), Instance.make(1435, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 2 - 1, 2, 0, 0, 0), Instance.make(1435, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 3 - 1, 3, 0, 0, 0), Instance.make(1435, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 4 - 1, 2, 0, 0, 0), Instance.make(1435, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 5 - 1, 1, 0, 0, 0), Instance.make(1435, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 5 - 1, 31, 0, 0, 0), Instance.make(1435, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 6 - 1, 29, 0, 0, 0), Instance.make(1435, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 7 - 1, 29, 0, 0, 0), Instance.make(1435, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 8 - 1, 27, 0, 0, 0), Instance.make(1435, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 9 - 1, 26, 0, 0, 0), Instance.make(1435, 12 - 1, 1, 0, 0, 0));

		// 1436
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 10 - 1, 25, 0, 0, 0), Instance.make(1436, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 11 - 1, 24, 0, 0, 0), Instance.make(1436, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2014, 12 - 1, 23, 0, 0, 0), Instance.make(1436, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 1 - 1, 22, 0, 0, 0), Instance.make(1436, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 2 - 1, 20, 0, 0, 0), Instance.make(1436, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 3 - 1, 22, 0, 0, 0), Instance.make(1436, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 4 - 1, 20, 0, 0, 0), Instance.make(1436, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 5 - 1, 20, 0, 0, 0), Instance.make(1436, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 6 - 1, 18, 0, 0, 0), Instance.make(1436, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 7 - 1, 18, 0, 0, 0), Instance.make(1436, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 8 - 1, 16, 0, 0, 0), Instance.make(1436, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 9 - 1, 15, 0, 0, 0), Instance.make(1436, 12 - 1, 1, 0, 0, 0));

		// 1437
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 10 - 1, 15, 0, 0, 0), Instance.make(1437, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 11 - 1, 14, 0, 0, 0), Instance.make(1437, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2015, 12 - 1, 13, 0, 0, 0), Instance.make(1437, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 1 - 1, 12, 0, 0, 0), Instance.make(1437, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 2 - 1, 10, 0, 0, 0), Instance.make(1437, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 3 - 1, 11, 0, 0, 0), Instance.make(1437, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 4 - 1, 9, 0, 0, 0), Instance.make(1437, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 5 - 1, 9, 0, 0, 0), Instance.make(1437, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 6 - 1, 7, 0, 0, 0), Instance.make(1437, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 7 - 1, 7, 0, 0, 0), Instance.make(1437, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 8 - 1, 5, 0, 0, 0), Instance.make(1437, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 9 - 1, 4, 0, 0, 0), Instance.make(1437, 12 - 1, 1, 0, 0, 0));

		// 1438
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 10 - 1, 3, 0, 0, 0), Instance.make(1438, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 11 - 1, 2, 0, 0, 0), Instance.make(1438, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 12 - 1, 1, 0, 0, 0), Instance.make(1438, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2016, 12 - 1, 31, 0, 0, 0), Instance.make(1438, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 1 - 1, 29, 0, 0, 0), Instance.make(1438, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 2 - 1, 28, 0, 0, 0), Instance.make(1438, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 3 - 1, 29, 0, 0, 0), Instance.make(1438, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 4 - 1, 28, 0, 0, 0), Instance.make(1438, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 5 - 1, 27, 0, 0, 0), Instance.make(1438, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 6 - 1, 26, 0, 0, 0), Instance.make(1438, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 7 - 1, 25, 0, 0, 0), Instance.make(1438, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 8 - 1, 24, 0, 0, 0), Instance.make(1438, 12 - 1, 1, 0, 0, 0));

		// 1439
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 9 - 1, 22, 0, 0, 0), Instance.make(1439, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 10 - 1, 22, 0, 0, 0), Instance.make(1439, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 11 - 1, 20, 0, 0, 0), Instance.make(1439, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2017, 12 - 1, 20, 0, 0, 0), Instance.make(1439, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 1 - 1, 18, 0, 0, 0), Instance.make(1439, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 2 - 1, 17, 0, 0, 0), Instance.make(1439, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 3 - 1, 18, 0, 0, 0), Instance.make(1439, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 4 - 1, 17, 0, 0, 0), Instance.make(1439, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 5 - 1, 16, 0, 0, 0), Instance.make(1439, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 6 - 1, 15, 0, 0, 0), Instance.make(1439, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 7 - 1, 14, 0, 0, 0), Instance.make(1439, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 8 - 1, 13, 0, 0, 0), Instance.make(1439, 12 - 1, 1, 0, 0, 0));

		// 1440
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 9 - 1, 12, 0, 0, 0), Instance.make(1440, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 10 - 1, 12, 0, 0, 0), Instance.make(1440, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 11 - 1, 10, 0, 0, 0), Instance.make(1440, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2018, 12 - 1, 10, 0, 0, 0), Instance.make(1440, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2019, 1 - 1, 8, 0, 0, 0), Instance.make(1440, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2019, 2 - 1, 7, 0, 0, 0), Instance.make(1440, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2019, 3 - 1, 8, 0, 0, 0), Instance.make(1440, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2019, 4 - 1, 7, 0, 0, 0), Instance.make(1440, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2019, 5 - 1, 6, 0, 0, 0), Instance.make(1440, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2019, 6 - 1, 5, 0, 0, 0), Instance.make(1440, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2019, 7 - 1, 4, 0, 0, 0), Instance.make(1440, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.II.ordinal()][1].put(Instance.make(2019, 8 - 1, 3, 0, 0, 0), Instance.make(1440, 12 - 1, 1, 0, 0, 0));

	}


	/**
	 * Add a number of instances created with <a href="http://www.staff.science.uu.nl/~gent0113/islam/islam_tabcal.htm">Islamic-Western Calendar Converter</a>
	 */
	private static void initMaps3()
	{

		/* Pattern IIIa */

		// 1410
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1989, 8 - 1, 3, 0, 0, 0), Instance.make(1410, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1989, 9 - 1, 2, 0, 0, 0), Instance.make(1410, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1989, 10 - 1, 1, 0, 0, 0), Instance.make(1410, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1989, 10 - 1, 31, 0, 0, 0), Instance.make(1410, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1989, 11 - 1, 29, 0, 0, 0), Instance.make(1410, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1989, 12 - 1, 29, 0, 0, 0), Instance.make(1410, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 1 - 1, 27, 0, 0, 0), Instance.make(1410, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 2 - 1, 26, 0, 0, 0), Instance.make(1410, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 3 - 1, 27, 0, 0, 0), Instance.make(1410, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 4 - 1, 26, 0, 0, 0), Instance.make(1410, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 5 - 1, 25, 0, 0, 0), Instance.make(1410, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 6 - 1, 24, 0, 0, 0), Instance.make(1410, 12 - 1, 1, 0, 0, 0));

		// 1411
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 7 - 1, 23, 0, 0, 0), Instance.make(1411, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 8 - 1, 22, 0, 0, 0), Instance.make(1411, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 9 - 1, 20, 0, 0, 0), Instance.make(1411, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 10 - 1, 20, 0, 0, 0), Instance.make(1411, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 11 - 1, 18, 0, 0, 0), Instance.make(1411, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1990, 12 - 1, 18, 0, 0, 0), Instance.make(1411, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 1 - 1, 16, 0, 0, 0), Instance.make(1411, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 2 - 1, 15, 0, 0, 0), Instance.make(1411, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 3 - 1, 16, 0, 0, 0), Instance.make(1411, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 4 - 1, 15, 0, 0, 0), Instance.make(1411, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 5 - 1, 14, 0, 0, 0), Instance.make(1411, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 6 - 1, 13, 0, 0, 0), Instance.make(1411, 12 - 1, 1, 0, 0, 0));

		// 1412
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 7 - 1, 12, 0, 0, 0), Instance.make(1412, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 8 - 1, 11, 0, 0, 0), Instance.make(1412, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 9 - 1, 9, 0, 0, 0), Instance.make(1412, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 10 - 1, 9, 0, 0, 0), Instance.make(1412, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 11 - 1, 7, 0, 0, 0), Instance.make(1412, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1991, 12 - 1, 7, 0, 0, 0), Instance.make(1412, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 1 - 1, 5, 0, 0, 0), Instance.make(1412, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 2 - 1, 4, 0, 0, 0), Instance.make(1412, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 3 - 1, 4, 0, 0, 0), Instance.make(1412, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 4 - 1, 3, 0, 0, 0), Instance.make(1412, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 5 - 1, 2, 0, 0, 0), Instance.make(1412, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 6 - 1, 1, 0, 0, 0), Instance.make(1412, 12 - 1, 1, 0, 0, 0));

		// 1413
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 7 - 1, 1, 0, 0, 0), Instance.make(1413, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 7 - 1, 31, 0, 0, 0), Instance.make(1413, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 8 - 1, 29, 0, 0, 0), Instance.make(1413, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 9 - 1, 28, 0, 0, 0), Instance.make(1413, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 10 - 1, 27, 0, 0, 0), Instance.make(1413, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 11 - 1, 26, 0, 0, 0), Instance.make(1413, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1992, 12 - 1, 25, 0, 0, 0), Instance.make(1413, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 1 - 1, 24, 0, 0, 0), Instance.make(1413, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 2 - 1, 22, 0, 0, 0), Instance.make(1413, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 3 - 1, 24, 0, 0, 0), Instance.make(1413, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 4 - 1, 22, 0, 0, 0), Instance.make(1413, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 5 - 1, 22, 0, 0, 0), Instance.make(1413, 12 - 1, 1, 0, 0, 0));

		// 1414
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 6 - 1, 20, 0, 0, 0), Instance.make(1414, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 7 - 1, 20, 0, 0, 0), Instance.make(1414, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 8 - 1, 18, 0, 0, 0), Instance.make(1414, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 9 - 1, 17, 0, 0, 0), Instance.make(1414, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 10 - 1, 16, 0, 0, 0), Instance.make(1414, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 11 - 1, 15, 0, 0, 0), Instance.make(1414, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1993, 12 - 1, 14, 0, 0, 0), Instance.make(1414, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 1 - 1, 13, 0, 0, 0), Instance.make(1414, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 2 - 1, 11, 0, 0, 0), Instance.make(1414, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 3 - 1, 13, 0, 0, 0), Instance.make(1414, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 4 - 1, 11, 0, 0, 0), Instance.make(1414, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 5 - 1, 11, 0, 0, 0), Instance.make(1414, 12 - 1, 1, 0, 0, 0));

		// 1415
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 6 - 1, 9, 0, 0, 0), Instance.make(1415, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 7 - 1, 9, 0, 0, 0), Instance.make(1415, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 8 - 1, 7, 0, 0, 0), Instance.make(1415, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 9 - 1, 6, 0, 0, 0), Instance.make(1415, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 10 - 1, 5, 0, 0, 0), Instance.make(1415, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 11 - 1, 4, 0, 0, 0), Instance.make(1415, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1994, 12 - 1, 3, 0, 0, 0), Instance.make(1415, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 1 - 1, 2, 0, 0, 0), Instance.make(1415, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 1 - 1, 31, 0, 0, 0), Instance.make(1415, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 3 - 1, 2, 0, 0, 0), Instance.make(1415, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 3 - 1, 31, 0, 0, 0), Instance.make(1415, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 4 - 1, 30, 0, 0, 0), Instance.make(1415, 12 - 1, 1, 0, 0, 0));

		// 1416
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 5 - 1, 30, 0, 0, 0), Instance.make(1416, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 6 - 1, 29, 0, 0, 0), Instance.make(1416, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 7 - 1, 28, 0, 0, 0), Instance.make(1416, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 8 - 1, 27, 0, 0, 0), Instance.make(1416, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 9 - 1, 25, 0, 0, 0), Instance.make(1416, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 10 - 1, 25, 0, 0, 0), Instance.make(1416, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 11 - 1, 23, 0, 0, 0), Instance.make(1416, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1995, 12 - 1, 23, 0, 0, 0), Instance.make(1416, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 1 - 1, 21, 0, 0, 0), Instance.make(1416, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 2 - 1, 20, 0, 0, 0), Instance.make(1416, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 3 - 1, 20, 0, 0, 0), Instance.make(1416, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 4 - 1, 19, 0, 0, 0), Instance.make(1416, 12 - 1, 1, 0, 0, 0));

		// 1417
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 5 - 1, 18, 0, 0, 0), Instance.make(1417, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 6 - 1, 17, 0, 0, 0), Instance.make(1417, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 7 - 1, 16, 0, 0, 0), Instance.make(1417, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 8 - 1, 15, 0, 0, 0), Instance.make(1417, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 9 - 1, 13, 0, 0, 0), Instance.make(1417, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 10 - 1, 13, 0, 0, 0), Instance.make(1417, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 11 - 1, 11, 0, 0, 0), Instance.make(1417, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1996, 12 - 1, 11, 0, 0, 0), Instance.make(1417, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 1 - 1, 9, 0, 0, 0), Instance.make(1417, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 2 - 1, 8, 0, 0, 0), Instance.make(1417, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 3 - 1, 9, 0, 0, 0), Instance.make(1417, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 4 - 1, 8, 0, 0, 0), Instance.make(1417, 12 - 1, 1, 0, 0, 0));

		// 1418
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 5 - 1, 7, 0, 0, 0), Instance.make(1418, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 6 - 1, 6, 0, 0, 0), Instance.make(1418, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 7 - 1, 5, 0, 0, 0), Instance.make(1418, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 8 - 1, 4, 0, 0, 0), Instance.make(1418, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 9 - 1, 2, 0, 0, 0), Instance.make(1418, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 10 - 1, 2, 0, 0, 0), Instance.make(1418, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 10 - 1, 31, 0, 0, 0), Instance.make(1418, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 11 - 1, 30, 0, 0, 0), Instance.make(1418, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1997, 12 - 1, 29, 0, 0, 0), Instance.make(1418, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 1 - 1, 28, 0, 0, 0), Instance.make(1418, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 2 - 1, 26, 0, 0, 0), Instance.make(1418, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 3 - 1, 28, 0, 0, 0), Instance.make(1418, 12 - 1, 1, 0, 0, 0));

		// 1419
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 4 - 1, 27, 0, 0, 0), Instance.make(1419, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 5 - 1, 27, 0, 0, 0), Instance.make(1419, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 6 - 1, 25, 0, 0, 0), Instance.make(1419, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 7 - 1, 25, 0, 0, 0), Instance.make(1419, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 8 - 1, 23, 0, 0, 0), Instance.make(1419, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 9 - 1, 22, 0, 0, 0), Instance.make(1419, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 10 - 1, 21, 0, 0, 0), Instance.make(1419, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 11 - 1, 20, 0, 0, 0), Instance.make(1419, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1998, 12 - 1, 19, 0, 0, 0), Instance.make(1419, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 1 - 1, 18, 0, 0, 0), Instance.make(1419, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 2 - 1, 16, 0, 0, 0), Instance.make(1419, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 3 - 1, 18, 0, 0, 0), Instance.make(1419, 12 - 1, 1, 0, 0, 0));

		// 1420
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 4 - 1, 16, 0, 0, 0), Instance.make(1420, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 5 - 1, 16, 0, 0, 0), Instance.make(1420, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 6 - 1, 14, 0, 0, 0), Instance.make(1420, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 7 - 1, 14, 0, 0, 0), Instance.make(1420, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 8 - 1, 12, 0, 0, 0), Instance.make(1420, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 9 - 1, 11, 0, 0, 0), Instance.make(1420, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 10 - 1, 10, 0, 0, 0), Instance.make(1420, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 11 - 1, 9, 0, 0, 0), Instance.make(1420, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(1999, 12 - 1, 8, 0, 0, 0), Instance.make(1420, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 1 - 1, 7, 0, 0, 0), Instance.make(1420, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 2 - 1, 5, 0, 0, 0), Instance.make(1420, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 3 - 1, 6, 0, 0, 0), Instance.make(1420, 12 - 1, 1, 0, 0, 0));

		// 1421
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 4 - 1, 5, 0, 0, 0), Instance.make(1421, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 5 - 1, 5, 0, 0, 0), Instance.make(1421, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 6 - 1, 3, 0, 0, 0), Instance.make(1421, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 7 - 1, 3, 0, 0, 0), Instance.make(1421, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 8 - 1, 1, 0, 0, 0), Instance.make(1421, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 8 - 1, 31, 0, 0, 0), Instance.make(1421, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 9 - 1, 29, 0, 0, 0), Instance.make(1421, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 10 - 1, 29, 0, 0, 0), Instance.make(1421, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 11 - 1, 27, 0, 0, 0), Instance.make(1421, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2000, 12 - 1, 27, 0, 0, 0), Instance.make(1421, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 1 - 1, 25, 0, 0, 0), Instance.make(1421, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 2 - 1, 24, 0, 0, 0), Instance.make(1421, 12 - 1, 1, 0, 0, 0));

		// 1422
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 3 - 1, 25, 0, 0, 0), Instance.make(1422, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 4 - 1, 24, 0, 0, 0), Instance.make(1422, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 5 - 1, 23, 0, 0, 0), Instance.make(1422, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 6 - 1, 22, 0, 0, 0), Instance.make(1422, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 7 - 1, 21, 0, 0, 0), Instance.make(1422, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 8 - 1, 20, 0, 0, 0), Instance.make(1422, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 9 - 1, 18, 0, 0, 0), Instance.make(1422, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 10 - 1, 18, 0, 0, 0), Instance.make(1422, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 11 - 1, 16, 0, 0, 0), Instance.make(1422, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2001, 12 - 1, 16, 0, 0, 0), Instance.make(1422, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 1 - 1, 14, 0, 0, 0), Instance.make(1422, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 2 - 1, 13, 0, 0, 0), Instance.make(1422, 12 - 1, 1, 0, 0, 0));

		// 1423
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 3 - 1, 14, 0, 0, 0), Instance.make(1423, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 4 - 1, 13, 0, 0, 0), Instance.make(1423, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 5 - 1, 12, 0, 0, 0), Instance.make(1423, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 6 - 1, 11, 0, 0, 0), Instance.make(1423, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 7 - 1, 10, 0, 0, 0), Instance.make(1423, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 8 - 1, 9, 0, 0, 0), Instance.make(1423, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 9 - 1, 7, 0, 0, 0), Instance.make(1423, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 10 - 1, 7, 0, 0, 0), Instance.make(1423, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 11 - 1, 5, 0, 0, 0), Instance.make(1423, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2002, 12 - 1, 5, 0, 0, 0), Instance.make(1423, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 1 - 1, 3, 0, 0, 0), Instance.make(1423, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 2 - 1, 2, 0, 0, 0), Instance.make(1423, 12 - 1, 1, 0, 0, 0));

		// 1424
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 3 - 1, 4, 0, 0, 0), Instance.make(1424, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 4 - 1, 3, 0, 0, 0), Instance.make(1424, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 5 - 1, 2, 0, 0, 0), Instance.make(1424, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 6 - 1, 1, 0, 0, 0), Instance.make(1424, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 6 - 1, 30, 0, 0, 0), Instance.make(1424, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 7 - 1, 30, 0, 0, 0), Instance.make(1424, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 8 - 1, 28, 0, 0, 0), Instance.make(1424, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 9 - 1, 27, 0, 0, 0), Instance.make(1424, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 10 - 1, 26, 0, 0, 0), Instance.make(1424, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 11 - 1, 25, 0, 0, 0), Instance.make(1424, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2003, 12 - 1, 24, 0, 0, 0), Instance.make(1424, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 1 - 1, 23, 0, 0, 0), Instance.make(1424, 12 - 1, 1, 0, 0, 0));

		// 1425
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 2 - 1, 21, 0, 0, 0), Instance.make(1425, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 3 - 1, 22, 0, 0, 0), Instance.make(1425, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 4 - 1, 20, 0, 0, 0), Instance.make(1425, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 5 - 1, 20, 0, 0, 0), Instance.make(1425, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 6 - 1, 18, 0, 0, 0), Instance.make(1425, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 7 - 1, 18, 0, 0, 0), Instance.make(1425, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 8 - 1, 16, 0, 0, 0), Instance.make(1425, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 9 - 1, 15, 0, 0, 0), Instance.make(1425, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 10 - 1, 14, 0, 0, 0), Instance.make(1425, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 11 - 1, 13, 0, 0, 0), Instance.make(1425, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2004, 12 - 1, 12, 0, 0, 0), Instance.make(1425, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 1 - 1, 11, 0, 0, 0), Instance.make(1425, 12 - 1, 1, 0, 0, 0));

		// 1426
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 2 - 1, 9, 0, 0, 0), Instance.make(1426, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 3 - 1, 11, 0, 0, 0), Instance.make(1426, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 4 - 1, 9, 0, 0, 0), Instance.make(1426, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 5 - 1, 9, 0, 0, 0), Instance.make(1426, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 6 - 1, 7, 0, 0, 0), Instance.make(1426, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 7 - 1, 7, 0, 0, 0), Instance.make(1426, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 8 - 1, 5, 0, 0, 0), Instance.make(1426, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 9 - 1, 4, 0, 0, 0), Instance.make(1426, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 10 - 1, 3, 0, 0, 0), Instance.make(1426, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 11 - 1, 2, 0, 0, 0), Instance.make(1426, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 12 - 1, 1, 0, 0, 0), Instance.make(1426, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2005, 12 - 1, 31, 0, 0, 0), Instance.make(1426, 12 - 1, 1, 0, 0, 0));

		// 1427
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 1 - 1, 30, 0, 0, 0), Instance.make(1427, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 3 - 1, 1, 0, 0, 0), Instance.make(1427, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 3 - 1, 30, 0, 0, 0), Instance.make(1427, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 4 - 1, 29, 0, 0, 0), Instance.make(1427, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 5 - 1, 28, 0, 0, 0), Instance.make(1427, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 6 - 1, 27, 0, 0, 0), Instance.make(1427, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 7 - 1, 26, 0, 0, 0), Instance.make(1427, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 8 - 1, 25, 0, 0, 0), Instance.make(1427, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 9 - 1, 23, 0, 0, 0), Instance.make(1427, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 10 - 1, 23, 0, 0, 0), Instance.make(1427, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 11 - 1, 21, 0, 0, 0), Instance.make(1427, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2006, 12 - 1, 21, 0, 0, 0), Instance.make(1427, 12 - 1, 1, 0, 0, 0));

		// 1428
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 1 - 1, 19, 0, 0, 0), Instance.make(1428, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 2 - 1, 18, 0, 0, 0), Instance.make(1428, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 3 - 1, 19, 0, 0, 0), Instance.make(1428, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 4 - 1, 18, 0, 0, 0), Instance.make(1428, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 5 - 1, 17, 0, 0, 0), Instance.make(1428, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 6 - 1, 16, 0, 0, 0), Instance.make(1428, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 7 - 1, 15, 0, 0, 0), Instance.make(1428, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 8 - 1, 14, 0, 0, 0), Instance.make(1428, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 9 - 1, 12, 0, 0, 0), Instance.make(1428, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 10 - 1, 12, 0, 0, 0), Instance.make(1428, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 11 - 1, 10, 0, 0, 0), Instance.make(1428, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2007, 12 - 1, 10, 0, 0, 0), Instance.make(1428, 12 - 1, 1, 0, 0, 0));

		// 1429
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 1 - 1, 8, 0, 0, 0), Instance.make(1429, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 2 - 1, 7, 0, 0, 0), Instance.make(1429, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 3 - 1, 7, 0, 0, 0), Instance.make(1429, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 4 - 1, 6, 0, 0, 0), Instance.make(1429, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 5 - 1, 5, 0, 0, 0), Instance.make(1429, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 6 - 1, 4, 0, 0, 0), Instance.make(1429, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 7 - 1, 3, 0, 0, 0), Instance.make(1429, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 8 - 1, 2, 0, 0, 0), Instance.make(1429, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 8 - 1, 31, 0, 0, 0), Instance.make(1429, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 9 - 1, 30, 0, 0, 0), Instance.make(1429, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 10 - 1, 29, 0, 0, 0), Instance.make(1429, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 11 - 1, 28, 0, 0, 0), Instance.make(1429, 12 - 1, 1, 0, 0, 0));

		// 1430
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2008, 12 - 1, 28, 0, 0, 0), Instance.make(1430, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 1 - 1, 27, 0, 0, 0), Instance.make(1430, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 2 - 1, 25, 0, 0, 0), Instance.make(1430, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 3 - 1, 27, 0, 0, 0), Instance.make(1430, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 4 - 1, 25, 0, 0, 0), Instance.make(1430, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 5 - 1, 25, 0, 0, 0), Instance.make(1430, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 6 - 1, 23, 0, 0, 0), Instance.make(1430, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 7 - 1, 23, 0, 0, 0), Instance.make(1430, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 8 - 1, 21, 0, 0, 0), Instance.make(1430, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 9 - 1, 20, 0, 0, 0), Instance.make(1430, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 10 - 1, 19, 0, 0, 0), Instance.make(1430, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 11 - 1, 18, 0, 0, 0), Instance.make(1430, 12 - 1, 1, 0, 0, 0));

		// 1431
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2009, 12 - 1, 17, 0, 0, 0), Instance.make(1431, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 1 - 1, 16, 0, 0, 0), Instance.make(1431, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 2 - 1, 14, 0, 0, 0), Instance.make(1431, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 3 - 1, 16, 0, 0, 0), Instance.make(1431, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 4 - 1, 14, 0, 0, 0), Instance.make(1431, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 5 - 1, 14, 0, 0, 0), Instance.make(1431, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 6 - 1, 12, 0, 0, 0), Instance.make(1431, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 7 - 1, 12, 0, 0, 0), Instance.make(1431, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 8 - 1, 10, 0, 0, 0), Instance.make(1431, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 9 - 1, 9, 0, 0, 0), Instance.make(1431, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 10 - 1, 8, 0, 0, 0), Instance.make(1431, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 11 - 1, 7, 0, 0, 0), Instance.make(1431, 12 - 1, 1, 0, 0, 0));

		// 1432
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2010, 12 - 1, 7, 0, 0, 0), Instance.make(1432, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 1 - 1, 6, 0, 0, 0), Instance.make(1432, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 2 - 1, 4, 0, 0, 0), Instance.make(1432, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 3 - 1, 6, 0, 0, 0), Instance.make(1432, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 4 - 1, 4, 0, 0, 0), Instance.make(1432, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 5 - 1, 4, 0, 0, 0), Instance.make(1432, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 6 - 1, 2, 0, 0, 0), Instance.make(1432, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 7 - 1, 2, 0, 0, 0), Instance.make(1432, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 7 - 1, 31, 0, 0, 0), Instance.make(1432, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 8 - 1, 30, 0, 0, 0), Instance.make(1432, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 9 - 1, 28, 0, 0, 0), Instance.make(1432, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 10 - 1, 28, 0, 0, 0), Instance.make(1432, 12 - 1, 1, 0, 0, 0));

		// 1433
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 11 - 1, 26, 0, 0, 0), Instance.make(1433, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2011, 12 - 1, 26, 0, 0, 0), Instance.make(1433, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 1 - 1, 24, 0, 0, 0), Instance.make(1433, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 2 - 1, 23, 0, 0, 0), Instance.make(1433, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 3 - 1, 23, 0, 0, 0), Instance.make(1433, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 4 - 1, 22, 0, 0, 0), Instance.make(1433, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 5 - 1, 21, 0, 0, 0), Instance.make(1433, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 6 - 1, 20, 0, 0, 0), Instance.make(1433, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 7 - 1, 19, 0, 0, 0), Instance.make(1433, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 8 - 1, 18, 0, 0, 0), Instance.make(1433, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 9 - 1, 16, 0, 0, 0), Instance.make(1433, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 10 - 1, 16, 0, 0, 0), Instance.make(1433, 12 - 1, 1, 0, 0, 0));

		// 1434
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 11 - 1, 14, 0, 0, 0), Instance.make(1434, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2012, 12 - 1, 14, 0, 0, 0), Instance.make(1434, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 1 - 1, 12, 0, 0, 0), Instance.make(1434, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 2 - 1, 11, 0, 0, 0), Instance.make(1434, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 3 - 1, 12, 0, 0, 0), Instance.make(1434, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 4 - 1, 11, 0, 0, 0), Instance.make(1434, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 5 - 1, 10, 0, 0, 0), Instance.make(1434, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 6 - 1, 9, 0, 0, 0), Instance.make(1434, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 7 - 1, 8, 0, 0, 0), Instance.make(1434, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 8 - 1, 7, 0, 0, 0), Instance.make(1434, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 9 - 1, 5, 0, 0, 0), Instance.make(1434, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 10 - 1, 5, 0, 0, 0), Instance.make(1434, 12 - 1, 1, 0, 0, 0));

		// 1435
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 11 - 1, 4, 0, 0, 0), Instance.make(1435, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2013, 12 - 1, 4, 0, 0, 0), Instance.make(1435, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 1 - 1, 2, 0, 0, 0), Instance.make(1435, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 2 - 1, 1, 0, 0, 0), Instance.make(1435, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 3 - 1, 2, 0, 0, 0), Instance.make(1435, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 4 - 1, 1, 0, 0, 0), Instance.make(1435, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 4 - 1, 30, 0, 0, 0), Instance.make(1435, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 5 - 1, 30, 0, 0, 0), Instance.make(1435, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 6 - 1, 28, 0, 0, 0), Instance.make(1435, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 7 - 1, 28, 0, 0, 0), Instance.make(1435, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 8 - 1, 26, 0, 0, 0), Instance.make(1435, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 9 - 1, 25, 0, 0, 0), Instance.make(1435, 12 - 1, 1, 0, 0, 0));

		// 1436
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 10 - 1, 24, 0, 0, 0), Instance.make(1436, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 11 - 1, 23, 0, 0, 0), Instance.make(1436, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2014, 12 - 1, 22, 0, 0, 0), Instance.make(1436, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 1 - 1, 21, 0, 0, 0), Instance.make(1436, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 2 - 1, 19, 0, 0, 0), Instance.make(1436, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 3 - 1, 21, 0, 0, 0), Instance.make(1436, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 4 - 1, 19, 0, 0, 0), Instance.make(1436, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 5 - 1, 19, 0, 0, 0), Instance.make(1436, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 6 - 1, 17, 0, 0, 0), Instance.make(1436, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 7 - 1, 17, 0, 0, 0), Instance.make(1436, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 8 - 1, 15, 0, 0, 0), Instance.make(1436, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 9 - 1, 14, 0, 0, 0), Instance.make(1436, 12 - 1, 1, 0, 0, 0));

		// 1437
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 10 - 1, 13, 0, 0, 0), Instance.make(1437, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 11 - 1, 12, 0, 0, 0), Instance.make(1437, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2015, 12 - 1, 11, 0, 0, 0), Instance.make(1437, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 1 - 1, 10, 0, 0, 0), Instance.make(1437, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 2 - 1, 8, 0, 0, 0), Instance.make(1437, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 3 - 1, 9, 0, 0, 0), Instance.make(1437, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 4 - 1, 7, 0, 0, 0), Instance.make(1437, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 5 - 1, 7, 0, 0, 0), Instance.make(1437, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 6 - 1, 5, 0, 0, 0), Instance.make(1437, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 7 - 1, 5, 0, 0, 0), Instance.make(1437, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 8 - 1, 3, 0, 0, 0), Instance.make(1437, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 9 - 1, 2, 0, 0, 0), Instance.make(1437, 12 - 1, 1, 0, 0, 0));

		// 1438
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 10 - 1, 2, 0, 0, 0), Instance.make(1438, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 11 - 1, 1, 0, 0, 0), Instance.make(1438, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 11 - 1, 30, 0, 0, 0), Instance.make(1438, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2016, 12 - 1, 30, 0, 0, 0), Instance.make(1438, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 1 - 1, 28, 0, 0, 0), Instance.make(1438, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 2 - 1, 27, 0, 0, 0), Instance.make(1438, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 3 - 1, 28, 0, 0, 0), Instance.make(1438, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 4 - 1, 27, 0, 0, 0), Instance.make(1438, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 5 - 1, 26, 0, 0, 0), Instance.make(1438, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 6 - 1, 25, 0, 0, 0), Instance.make(1438, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 7 - 1, 24, 0, 0, 0), Instance.make(1438, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 8 - 1, 23, 0, 0, 0), Instance.make(1438, 12 - 1, 1, 0, 0, 0));

		// 1439
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 9 - 1, 21, 0, 0, 0), Instance.make(1439, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 10 - 1, 21, 0, 0, 0), Instance.make(1439, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 11 - 1, 19, 0, 0, 0), Instance.make(1439, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2017, 12 - 1, 19, 0, 0, 0), Instance.make(1439, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 1 - 1, 17, 0, 0, 0), Instance.make(1439, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 2 - 1, 16, 0, 0, 0), Instance.make(1439, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 3 - 1, 17, 0, 0, 0), Instance.make(1439, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 4 - 1, 16, 0, 0, 0), Instance.make(1439, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 5 - 1, 15, 0, 0, 0), Instance.make(1439, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 6 - 1, 14, 0, 0, 0), Instance.make(1439, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 7 - 1, 13, 0, 0, 0), Instance.make(1439, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 8 - 1, 12, 0, 0, 0), Instance.make(1439, 12 - 1, 1, 0, 0, 0));

		// 1440
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 9 - 1, 11, 0, 0, 0), Instance.make(1440, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 10 - 1, 11, 0, 0, 0), Instance.make(1440, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 11 - 1, 9, 0, 0, 0), Instance.make(1440, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2018, 12 - 1, 9, 0, 0, 0), Instance.make(1440, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2019, 1 - 1, 7, 0, 0, 0), Instance.make(1440, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2019, 2 - 1, 6, 0, 0, 0), Instance.make(1440, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2019, 3 - 1, 7, 0, 0, 0), Instance.make(1440, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2019, 4 - 1, 6, 0, 0, 0), Instance.make(1440, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2019, 5 - 1, 5, 0, 0, 0), Instance.make(1440, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2019, 6 - 1, 4, 0, 0, 0), Instance.make(1440, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2019, 7 - 1, 3, 0, 0, 0), Instance.make(1440, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][0].put(Instance.make(2019, 8 - 1, 2, 0, 0, 0), Instance.make(1440, 12 - 1, 1, 0, 0, 0));

		/* Pattern IIIc */

		// 1410
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1989, 8 - 1, 4, 0, 0, 0), Instance.make(1410, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1989, 9 - 1, 3, 0, 0, 0), Instance.make(1410, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1989, 10 - 1, 2, 0, 0, 0), Instance.make(1410, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1989, 11 - 1, 1, 0, 0, 0), Instance.make(1410, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1989, 11 - 1, 30, 0, 0, 0), Instance.make(1410, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1989, 12 - 1, 30, 0, 0, 0), Instance.make(1410, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 1 - 1, 28, 0, 0, 0), Instance.make(1410, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 2 - 1, 27, 0, 0, 0), Instance.make(1410, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 3 - 1, 28, 0, 0, 0), Instance.make(1410, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 4 - 1, 27, 0, 0, 0), Instance.make(1410, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 5 - 1, 26, 0, 0, 0), Instance.make(1410, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 6 - 1, 25, 0, 0, 0), Instance.make(1410, 12 - 1, 1, 0, 0, 0));

		// 1411
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 7 - 1, 24, 0, 0, 0), Instance.make(1411, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 8 - 1, 23, 0, 0, 0), Instance.make(1411, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 9 - 1, 21, 0, 0, 0), Instance.make(1411, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 10 - 1, 21, 0, 0, 0), Instance.make(1411, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 11 - 1, 19, 0, 0, 0), Instance.make(1411, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1990, 12 - 1, 19, 0, 0, 0), Instance.make(1411, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 1 - 1, 17, 0, 0, 0), Instance.make(1411, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 2 - 1, 16, 0, 0, 0), Instance.make(1411, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 3 - 1, 17, 0, 0, 0), Instance.make(1411, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 4 - 1, 16, 0, 0, 0), Instance.make(1411, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 5 - 1, 15, 0, 0, 0), Instance.make(1411, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 6 - 1, 14, 0, 0, 0), Instance.make(1411, 12 - 1, 1, 0, 0, 0));

		// 1412
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 7 - 1, 13, 0, 0, 0), Instance.make(1412, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 8 - 1, 12, 0, 0, 0), Instance.make(1412, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 9 - 1, 10, 0, 0, 0), Instance.make(1412, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 10 - 1, 10, 0, 0, 0), Instance.make(1412, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 11 - 1, 8, 0, 0, 0), Instance.make(1412, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1991, 12 - 1, 8, 0, 0, 0), Instance.make(1412, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 1 - 1, 6, 0, 0, 0), Instance.make(1412, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 2 - 1, 5, 0, 0, 0), Instance.make(1412, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 3 - 1, 5, 0, 0, 0), Instance.make(1412, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 4 - 1, 4, 0, 0, 0), Instance.make(1412, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 5 - 1, 3, 0, 0, 0), Instance.make(1412, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 6 - 1, 2, 0, 0, 0), Instance.make(1412, 12 - 1, 1, 0, 0, 0));

		// 1413
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 7 - 1, 2, 0, 0, 0), Instance.make(1413, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 8 - 1, 1, 0, 0, 0), Instance.make(1413, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 8 - 1, 30, 0, 0, 0), Instance.make(1413, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 9 - 1, 29, 0, 0, 0), Instance.make(1413, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 10 - 1, 28, 0, 0, 0), Instance.make(1413, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 11 - 1, 27, 0, 0, 0), Instance.make(1413, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1992, 12 - 1, 26, 0, 0, 0), Instance.make(1413, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 1 - 1, 25, 0, 0, 0), Instance.make(1413, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 2 - 1, 23, 0, 0, 0), Instance.make(1413, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 3 - 1, 25, 0, 0, 0), Instance.make(1413, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 4 - 1, 23, 0, 0, 0), Instance.make(1413, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 5 - 1, 23, 0, 0, 0), Instance.make(1413, 12 - 1, 1, 0, 0, 0));

		// 1414
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 6 - 1, 21, 0, 0, 0), Instance.make(1414, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 7 - 1, 21, 0, 0, 0), Instance.make(1414, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 8 - 1, 19, 0, 0, 0), Instance.make(1414, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 9 - 1, 18, 0, 0, 0), Instance.make(1414, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 10 - 1, 17, 0, 0, 0), Instance.make(1414, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 11 - 1, 16, 0, 0, 0), Instance.make(1414, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1993, 12 - 1, 15, 0, 0, 0), Instance.make(1414, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 1 - 1, 14, 0, 0, 0), Instance.make(1414, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 2 - 1, 12, 0, 0, 0), Instance.make(1414, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 3 - 1, 14, 0, 0, 0), Instance.make(1414, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 4 - 1, 12, 0, 0, 0), Instance.make(1414, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 5 - 1, 12, 0, 0, 0), Instance.make(1414, 12 - 1, 1, 0, 0, 0));

		// 1415
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 6 - 1, 10, 0, 0, 0), Instance.make(1415, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 7 - 1, 10, 0, 0, 0), Instance.make(1415, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 8 - 1, 8, 0, 0, 0), Instance.make(1415, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 9 - 1, 7, 0, 0, 0), Instance.make(1415, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 10 - 1, 6, 0, 0, 0), Instance.make(1415, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 11 - 1, 5, 0, 0, 0), Instance.make(1415, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1994, 12 - 1, 4, 0, 0, 0), Instance.make(1415, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 1 - 1, 3, 0, 0, 0), Instance.make(1415, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 2 - 1, 1, 0, 0, 0), Instance.make(1415, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 3 - 1, 3, 0, 0, 0), Instance.make(1415, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 4 - 1, 1, 0, 0, 0), Instance.make(1415, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 5 - 1, 1, 0, 0, 0), Instance.make(1415, 12 - 1, 1, 0, 0, 0));

		// 1416
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 5 - 1, 31, 0, 0, 0), Instance.make(1416, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 6 - 1, 30, 0, 0, 0), Instance.make(1416, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 7 - 1, 29, 0, 0, 0), Instance.make(1416, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 8 - 1, 28, 0, 0, 0), Instance.make(1416, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 9 - 1, 26, 0, 0, 0), Instance.make(1416, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 10 - 1, 26, 0, 0, 0), Instance.make(1416, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 11 - 1, 24, 0, 0, 0), Instance.make(1416, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1995, 12 - 1, 24, 0, 0, 0), Instance.make(1416, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 1 - 1, 22, 0, 0, 0), Instance.make(1416, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 2 - 1, 21, 0, 0, 0), Instance.make(1416, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 3 - 1, 21, 0, 0, 0), Instance.make(1416, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 4 - 1, 20, 0, 0, 0), Instance.make(1416, 12 - 1, 1, 0, 0, 0));

		// 1417
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 5 - 1, 19, 0, 0, 0), Instance.make(1417, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 6 - 1, 18, 0, 0, 0), Instance.make(1417, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 7 - 1, 17, 0, 0, 0), Instance.make(1417, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 8 - 1, 16, 0, 0, 0), Instance.make(1417, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 9 - 1, 14, 0, 0, 0), Instance.make(1417, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 10 - 1, 14, 0, 0, 0), Instance.make(1417, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 11 - 1, 12, 0, 0, 0), Instance.make(1417, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1996, 12 - 1, 12, 0, 0, 0), Instance.make(1417, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 1 - 1, 10, 0, 0, 0), Instance.make(1417, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 2 - 1, 9, 0, 0, 0), Instance.make(1417, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 3 - 1, 10, 0, 0, 0), Instance.make(1417, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 4 - 1, 9, 0, 0, 0), Instance.make(1417, 12 - 1, 1, 0, 0, 0));

		// 1418
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 5 - 1, 8, 0, 0, 0), Instance.make(1418, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 6 - 1, 7, 0, 0, 0), Instance.make(1418, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 7 - 1, 6, 0, 0, 0), Instance.make(1418, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 8 - 1, 5, 0, 0, 0), Instance.make(1418, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 9 - 1, 3, 0, 0, 0), Instance.make(1418, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 10 - 1, 3, 0, 0, 0), Instance.make(1418, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 11 - 1, 1, 0, 0, 0), Instance.make(1418, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 12 - 1, 1, 0, 0, 0), Instance.make(1418, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1997, 12 - 1, 30, 0, 0, 0), Instance.make(1418, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 1 - 1, 29, 0, 0, 0), Instance.make(1418, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 2 - 1, 27, 0, 0, 0), Instance.make(1418, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 3 - 1, 29, 0, 0, 0), Instance.make(1418, 12 - 1, 1, 0, 0, 0));

		// 1419
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 4 - 1, 28, 0, 0, 0), Instance.make(1419, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 5 - 1, 28, 0, 0, 0), Instance.make(1419, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 6 - 1, 26, 0, 0, 0), Instance.make(1419, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 7 - 1, 26, 0, 0, 0), Instance.make(1419, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 8 - 1, 24, 0, 0, 0), Instance.make(1419, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 9 - 1, 23, 0, 0, 0), Instance.make(1419, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 10 - 1, 22, 0, 0, 0), Instance.make(1419, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 11 - 1, 21, 0, 0, 0), Instance.make(1419, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1998, 12 - 1, 20, 0, 0, 0), Instance.make(1419, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 1 - 1, 19, 0, 0, 0), Instance.make(1419, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 2 - 1, 17, 0, 0, 0), Instance.make(1419, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 3 - 1, 19, 0, 0, 0), Instance.make(1419, 12 - 1, 1, 0, 0, 0));

		// 1420
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 4 - 1, 17, 0, 0, 0), Instance.make(1420, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 5 - 1, 17, 0, 0, 0), Instance.make(1420, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 6 - 1, 15, 0, 0, 0), Instance.make(1420, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 7 - 1, 15, 0, 0, 0), Instance.make(1420, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 8 - 1, 13, 0, 0, 0), Instance.make(1420, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 9 - 1, 12, 0, 0, 0), Instance.make(1420, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 10 - 1, 11, 0, 0, 0), Instance.make(1420, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 11 - 1, 10, 0, 0, 0), Instance.make(1420, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(1999, 12 - 1, 9, 0, 0, 0), Instance.make(1420, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 1 - 1, 8, 0, 0, 0), Instance.make(1420, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 2 - 1, 6, 0, 0, 0), Instance.make(1420, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 3 - 1, 7, 0, 0, 0), Instance.make(1420, 12 - 1, 1, 0, 0, 0));

		// 1421
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 4 - 1, 6, 0, 0, 0), Instance.make(1421, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 5 - 1, 6, 0, 0, 0), Instance.make(1421, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 6 - 1, 4, 0, 0, 0), Instance.make(1421, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 7 - 1, 4, 0, 0, 0), Instance.make(1421, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 8 - 1, 2, 0, 0, 0), Instance.make(1421, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 9 - 1, 1, 0, 0, 0), Instance.make(1421, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 9 - 1, 30, 0, 0, 0), Instance.make(1421, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 10 - 1, 30, 0, 0, 0), Instance.make(1421, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 11 - 1, 28, 0, 0, 0), Instance.make(1421, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2000, 12 - 1, 28, 0, 0, 0), Instance.make(1421, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 1 - 1, 26, 0, 0, 0), Instance.make(1421, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 2 - 1, 25, 0, 0, 0), Instance.make(1421, 12 - 1, 1, 0, 0, 0));

		// 1422
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 3 - 1, 26, 0, 0, 0), Instance.make(1422, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 4 - 1, 25, 0, 0, 0), Instance.make(1422, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 5 - 1, 24, 0, 0, 0), Instance.make(1422, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 6 - 1, 23, 0, 0, 0), Instance.make(1422, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 7 - 1, 22, 0, 0, 0), Instance.make(1422, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 8 - 1, 21, 0, 0, 0), Instance.make(1422, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 9 - 1, 19, 0, 0, 0), Instance.make(1422, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 10 - 1, 19, 0, 0, 0), Instance.make(1422, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 11 - 1, 17, 0, 0, 0), Instance.make(1422, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2001, 12 - 1, 17, 0, 0, 0), Instance.make(1422, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 1 - 1, 15, 0, 0, 0), Instance.make(1422, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 2 - 1, 14, 0, 0, 0), Instance.make(1422, 12 - 1, 1, 0, 0, 0));

		// 1423
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 3 - 1, 15, 0, 0, 0), Instance.make(1423, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 4 - 1, 14, 0, 0, 0), Instance.make(1423, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 5 - 1, 13, 0, 0, 0), Instance.make(1423, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 6 - 1, 12, 0, 0, 0), Instance.make(1423, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 7 - 1, 11, 0, 0, 0), Instance.make(1423, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 8 - 1, 10, 0, 0, 0), Instance.make(1423, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 9 - 1, 8, 0, 0, 0), Instance.make(1423, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 10 - 1, 8, 0, 0, 0), Instance.make(1423, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 11 - 1, 6, 0, 0, 0), Instance.make(1423, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2002, 12 - 1, 6, 0, 0, 0), Instance.make(1423, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 1 - 1, 4, 0, 0, 0), Instance.make(1423, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 2 - 1, 3, 0, 0, 0), Instance.make(1423, 12 - 1, 1, 0, 0, 0));

		// 1424
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 3 - 1, 5, 0, 0, 0), Instance.make(1424, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 4 - 1, 4, 0, 0, 0), Instance.make(1424, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 5 - 1, 3, 0, 0, 0), Instance.make(1424, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 6 - 1, 2, 0, 0, 0), Instance.make(1424, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 7 - 1, 1, 0, 0, 0), Instance.make(1424, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 7 - 1, 31, 0, 0, 0), Instance.make(1424, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 8 - 1, 29, 0, 0, 0), Instance.make(1424, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 9 - 1, 28, 0, 0, 0), Instance.make(1424, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 10 - 1, 27, 0, 0, 0), Instance.make(1424, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 11 - 1, 26, 0, 0, 0), Instance.make(1424, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2003, 12 - 1, 25, 0, 0, 0), Instance.make(1424, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 1 - 1, 24, 0, 0, 0), Instance.make(1424, 12 - 1, 1, 0, 0, 0));

		// 1425
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 2 - 1, 22, 0, 0, 0), Instance.make(1425, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 3 - 1, 23, 0, 0, 0), Instance.make(1425, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 4 - 1, 21, 0, 0, 0), Instance.make(1425, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 5 - 1, 21, 0, 0, 0), Instance.make(1425, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 6 - 1, 19, 0, 0, 0), Instance.make(1425, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 7 - 1, 19, 0, 0, 0), Instance.make(1425, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 8 - 1, 17, 0, 0, 0), Instance.make(1425, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 9 - 1, 16, 0, 0, 0), Instance.make(1425, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 10 - 1, 15, 0, 0, 0), Instance.make(1425, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 11 - 1, 14, 0, 0, 0), Instance.make(1425, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2004, 12 - 1, 13, 0, 0, 0), Instance.make(1425, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 1 - 1, 12, 0, 0, 0), Instance.make(1425, 12 - 1, 1, 0, 0, 0));

		// 1426
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 2 - 1, 10, 0, 0, 0), Instance.make(1426, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 3 - 1, 12, 0, 0, 0), Instance.make(1426, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 4 - 1, 10, 0, 0, 0), Instance.make(1426, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 5 - 1, 10, 0, 0, 0), Instance.make(1426, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 6 - 1, 8, 0, 0, 0), Instance.make(1426, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 7 - 1, 8, 0, 0, 0), Instance.make(1426, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 8 - 1, 6, 0, 0, 0), Instance.make(1426, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 9 - 1, 5, 0, 0, 0), Instance.make(1426, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 10 - 1, 4, 0, 0, 0), Instance.make(1426, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 11 - 1, 3, 0, 0, 0), Instance.make(1426, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2005, 12 - 1, 2, 0, 0, 0), Instance.make(1426, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 1 - 1, 1, 0, 0, 0), Instance.make(1426, 12 - 1, 1, 0, 0, 0));

		// 1427
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 1 - 1, 31, 0, 0, 0), Instance.make(1427, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 3 - 1, 2, 0, 0, 0), Instance.make(1427, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 3 - 1, 31, 0, 0, 0), Instance.make(1427, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 4 - 1, 30, 0, 0, 0), Instance.make(1427, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 5 - 1, 29, 0, 0, 0), Instance.make(1427, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 6 - 1, 28, 0, 0, 0), Instance.make(1427, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 7 - 1, 27, 0, 0, 0), Instance.make(1427, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 8 - 1, 26, 0, 0, 0), Instance.make(1427, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 9 - 1, 24, 0, 0, 0), Instance.make(1427, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 10 - 1, 24, 0, 0, 0), Instance.make(1427, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 11 - 1, 22, 0, 0, 0), Instance.make(1427, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2006, 12 - 1, 22, 0, 0, 0), Instance.make(1427, 12 - 1, 1, 0, 0, 0));

		// 1428
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 1 - 1, 20, 0, 0, 0), Instance.make(1428, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 2 - 1, 19, 0, 0, 0), Instance.make(1428, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 3 - 1, 20, 0, 0, 0), Instance.make(1428, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 4 - 1, 19, 0, 0, 0), Instance.make(1428, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 5 - 1, 18, 0, 0, 0), Instance.make(1428, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 6 - 1, 17, 0, 0, 0), Instance.make(1428, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 7 - 1, 16, 0, 0, 0), Instance.make(1428, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 8 - 1, 15, 0, 0, 0), Instance.make(1428, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 9 - 1, 13, 0, 0, 0), Instance.make(1428, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 10 - 1, 13, 0, 0, 0), Instance.make(1428, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 11 - 1, 11, 0, 0, 0), Instance.make(1428, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2007, 12 - 1, 11, 0, 0, 0), Instance.make(1428, 12 - 1, 1, 0, 0, 0));

		// 1429
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 1 - 1, 9, 0, 0, 0), Instance.make(1429, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 2 - 1, 8, 0, 0, 0), Instance.make(1429, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 3 - 1, 8, 0, 0, 0), Instance.make(1429, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 4 - 1, 7, 0, 0, 0), Instance.make(1429, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 5 - 1, 6, 0, 0, 0), Instance.make(1429, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 6 - 1, 5, 0, 0, 0), Instance.make(1429, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 7 - 1, 4, 0, 0, 0), Instance.make(1429, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 8 - 1, 3, 0, 0, 0), Instance.make(1429, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 9 - 1, 1, 0, 0, 0), Instance.make(1429, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 10 - 1, 1, 0, 0, 0), Instance.make(1429, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 10 - 1, 30, 0, 0, 0), Instance.make(1429, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 11 - 1, 29, 0, 0, 0), Instance.make(1429, 12 - 1, 1, 0, 0, 0));

		// 1430
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2008, 12 - 1, 29, 0, 0, 0), Instance.make(1430, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 1 - 1, 28, 0, 0, 0), Instance.make(1430, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 2 - 1, 26, 0, 0, 0), Instance.make(1430, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 3 - 1, 28, 0, 0, 0), Instance.make(1430, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 4 - 1, 26, 0, 0, 0), Instance.make(1430, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 5 - 1, 26, 0, 0, 0), Instance.make(1430, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 6 - 1, 24, 0, 0, 0), Instance.make(1430, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 7 - 1, 24, 0, 0, 0), Instance.make(1430, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 8 - 1, 22, 0, 0, 0), Instance.make(1430, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 9 - 1, 21, 0, 0, 0), Instance.make(1430, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 10 - 1, 20, 0, 0, 0), Instance.make(1430, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 11 - 1, 19, 0, 0, 0), Instance.make(1430, 12 - 1, 1, 0, 0, 0));

		// 1431
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2009, 12 - 1, 18, 0, 0, 0), Instance.make(1431, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 1 - 1, 17, 0, 0, 0), Instance.make(1431, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 2 - 1, 15, 0, 0, 0), Instance.make(1431, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 3 - 1, 17, 0, 0, 0), Instance.make(1431, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 4 - 1, 15, 0, 0, 0), Instance.make(1431, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 5 - 1, 15, 0, 0, 0), Instance.make(1431, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 6 - 1, 13, 0, 0, 0), Instance.make(1431, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 7 - 1, 13, 0, 0, 0), Instance.make(1431, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 8 - 1, 11, 0, 0, 0), Instance.make(1431, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 9 - 1, 10, 0, 0, 0), Instance.make(1431, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 10 - 1, 9, 0, 0, 0), Instance.make(1431, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 11 - 1, 8, 0, 0, 0), Instance.make(1431, 12 - 1, 1, 0, 0, 0));

		// 1432
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2010, 12 - 1, 8, 0, 0, 0), Instance.make(1432, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 1 - 1, 7, 0, 0, 0), Instance.make(1432, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 2 - 1, 5, 0, 0, 0), Instance.make(1432, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 3 - 1, 7, 0, 0, 0), Instance.make(1432, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 4 - 1, 5, 0, 0, 0), Instance.make(1432, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 5 - 1, 5, 0, 0, 0), Instance.make(1432, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 6 - 1, 3, 0, 0, 0), Instance.make(1432, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 7 - 1, 3, 0, 0, 0), Instance.make(1432, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 8 - 1, 1, 0, 0, 0), Instance.make(1432, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 8 - 1, 31, 0, 0, 0), Instance.make(1432, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 9 - 1, 29, 0, 0, 0), Instance.make(1432, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 10 - 1, 29, 0, 0, 0), Instance.make(1432, 12 - 1, 1, 0, 0, 0));

		// 1433
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 11 - 1, 27, 0, 0, 0), Instance.make(1433, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2011, 12 - 1, 27, 0, 0, 0), Instance.make(1433, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 1 - 1, 25, 0, 0, 0), Instance.make(1433, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 2 - 1, 24, 0, 0, 0), Instance.make(1433, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 3 - 1, 24, 0, 0, 0), Instance.make(1433, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 4 - 1, 23, 0, 0, 0), Instance.make(1433, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 5 - 1, 22, 0, 0, 0), Instance.make(1433, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 6 - 1, 21, 0, 0, 0), Instance.make(1433, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 7 - 1, 20, 0, 0, 0), Instance.make(1433, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 8 - 1, 19, 0, 0, 0), Instance.make(1433, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 9 - 1, 17, 0, 0, 0), Instance.make(1433, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 10 - 1, 17, 0, 0, 0), Instance.make(1433, 12 - 1, 1, 0, 0, 0));

		// 1434
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 11 - 1, 15, 0, 0, 0), Instance.make(1434, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2012, 12 - 1, 15, 0, 0, 0), Instance.make(1434, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 1 - 1, 13, 0, 0, 0), Instance.make(1434, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 2 - 1, 12, 0, 0, 0), Instance.make(1434, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 3 - 1, 13, 0, 0, 0), Instance.make(1434, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 4 - 1, 12, 0, 0, 0), Instance.make(1434, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 5 - 1, 11, 0, 0, 0), Instance.make(1434, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 6 - 1, 10, 0, 0, 0), Instance.make(1434, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 7 - 1, 9, 0, 0, 0), Instance.make(1434, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 8 - 1, 8, 0, 0, 0), Instance.make(1434, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 9 - 1, 6, 0, 0, 0), Instance.make(1434, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 10 - 1, 6, 0, 0, 0), Instance.make(1434, 12 - 1, 1, 0, 0, 0));

		// 1435
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 11 - 1, 5, 0, 0, 0), Instance.make(1435, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2013, 12 - 1, 5, 0, 0, 0), Instance.make(1435, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 1 - 1, 3, 0, 0, 0), Instance.make(1435, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 2 - 1, 2, 0, 0, 0), Instance.make(1435, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 3 - 1, 3, 0, 0, 0), Instance.make(1435, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 4 - 1, 2, 0, 0, 0), Instance.make(1435, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 5 - 1, 1, 0, 0, 0), Instance.make(1435, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 5 - 1, 31, 0, 0, 0), Instance.make(1435, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 6 - 1, 29, 0, 0, 0), Instance.make(1435, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 7 - 1, 29, 0, 0, 0), Instance.make(1435, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 8 - 1, 27, 0, 0, 0), Instance.make(1435, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 9 - 1, 26, 0, 0, 0), Instance.make(1435, 12 - 1, 1, 0, 0, 0));

		// 1436
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 10 - 1, 25, 0, 0, 0), Instance.make(1436, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 11 - 1, 24, 0, 0, 0), Instance.make(1436, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2014, 12 - 1, 23, 0, 0, 0), Instance.make(1436, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 1 - 1, 22, 0, 0, 0), Instance.make(1436, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 2 - 1, 20, 0, 0, 0), Instance.make(1436, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 3 - 1, 22, 0, 0, 0), Instance.make(1436, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 4 - 1, 20, 0, 0, 0), Instance.make(1436, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 5 - 1, 20, 0, 0, 0), Instance.make(1436, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 6 - 1, 18, 0, 0, 0), Instance.make(1436, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 7 - 1, 18, 0, 0, 0), Instance.make(1436, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 8 - 1, 16, 0, 0, 0), Instance.make(1436, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 9 - 1, 15, 0, 0, 0), Instance.make(1436, 12 - 1, 1, 0, 0, 0));

		// 1437
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 10 - 1, 14, 0, 0, 0), Instance.make(1437, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 11 - 1, 13, 0, 0, 0), Instance.make(1437, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2015, 12 - 1, 12, 0, 0, 0), Instance.make(1437, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 1 - 1, 11, 0, 0, 0), Instance.make(1437, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 2 - 1, 9, 0, 0, 0), Instance.make(1437, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 3 - 1, 10, 0, 0, 0), Instance.make(1437, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 4 - 1, 8, 0, 0, 0), Instance.make(1437, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 5 - 1, 8, 0, 0, 0), Instance.make(1437, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 6 - 1, 6, 0, 0, 0), Instance.make(1437, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 7 - 1, 6, 0, 0, 0), Instance.make(1437, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 8 - 1, 4, 0, 0, 0), Instance.make(1437, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 9 - 1, 3, 0, 0, 0), Instance.make(1437, 12 - 1, 1, 0, 0, 0));

		// 1438
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 10 - 1, 3, 0, 0, 0), Instance.make(1438, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 11 - 1, 2, 0, 0, 0), Instance.make(1438, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 12 - 1, 1, 0, 0, 0), Instance.make(1438, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2016, 12 - 1, 31, 0, 0, 0), Instance.make(1438, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 1 - 1, 29, 0, 0, 0), Instance.make(1438, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 2 - 1, 28, 0, 0, 0), Instance.make(1438, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 3 - 1, 29, 0, 0, 0), Instance.make(1438, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 4 - 1, 28, 0, 0, 0), Instance.make(1438, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 5 - 1, 27, 0, 0, 0), Instance.make(1438, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 6 - 1, 26, 0, 0, 0), Instance.make(1438, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 7 - 1, 25, 0, 0, 0), Instance.make(1438, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 8 - 1, 24, 0, 0, 0), Instance.make(1438, 12 - 1, 1, 0, 0, 0));

		// 1439
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 9 - 1, 22, 0, 0, 0), Instance.make(1439, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 10 - 1, 22, 0, 0, 0), Instance.make(1439, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 11 - 1, 20, 0, 0, 0), Instance.make(1439, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2017, 12 - 1, 20, 0, 0, 0), Instance.make(1439, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 1 - 1, 18, 0, 0, 0), Instance.make(1439, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 2 - 1, 17, 0, 0, 0), Instance.make(1439, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 3 - 1, 18, 0, 0, 0), Instance.make(1439, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 4 - 1, 17, 0, 0, 0), Instance.make(1439, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 5 - 1, 16, 0, 0, 0), Instance.make(1439, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 6 - 1, 15, 0, 0, 0), Instance.make(1439, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 7 - 1, 14, 0, 0, 0), Instance.make(1439, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 8 - 1, 13, 0, 0, 0), Instance.make(1439, 12 - 1, 1, 0, 0, 0));

		// 1440
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 9 - 1, 12, 0, 0, 0), Instance.make(1440, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 10 - 1, 12, 0, 0, 0), Instance.make(1440, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 11 - 1, 10, 0, 0, 0), Instance.make(1440, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2018, 12 - 1, 10, 0, 0, 0), Instance.make(1440, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2019, 1 - 1, 8, 0, 0, 0), Instance.make(1440, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2019, 2 - 1, 7, 0, 0, 0), Instance.make(1440, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2019, 3 - 1, 8, 0, 0, 0), Instance.make(1440, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2019, 4 - 1, 7, 0, 0, 0), Instance.make(1440, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2019, 5 - 1, 6, 0, 0, 0), Instance.make(1440, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2019, 6 - 1, 5, 0, 0, 0), Instance.make(1440, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2019, 7 - 1, 4, 0, 0, 0), Instance.make(1440, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.III.ordinal()][1].put(Instance.make(2019, 8 - 1, 3, 0, 0, 0), Instance.make(1440, 12 - 1, 1, 0, 0, 0));

	}


	/**
	 * Add a number of instances created with <a href="http://www.staff.science.uu.nl/~gent0113/islam/islam_tabcal.htm">Islamic-Western Calendar Converter</a>
	 */
	private static void initMaps4()
	{

		/* Pattern IVa */

		// 1410
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1989, 8 - 1, 2, 0, 0, 0), Instance.make(1410, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1989, 9 - 1, 1, 0, 0, 0), Instance.make(1410, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1989, 9 - 1, 30, 0, 0, 0), Instance.make(1410, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1989, 10 - 1, 30, 0, 0, 0), Instance.make(1410, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1989, 11 - 1, 28, 0, 0, 0), Instance.make(1410, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1989, 12 - 1, 28, 0, 0, 0), Instance.make(1410, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 1 - 1, 26, 0, 0, 0), Instance.make(1410, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 2 - 1, 25, 0, 0, 0), Instance.make(1410, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 3 - 1, 26, 0, 0, 0), Instance.make(1410, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 4 - 1, 25, 0, 0, 0), Instance.make(1410, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 5 - 1, 24, 0, 0, 0), Instance.make(1410, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 6 - 1, 23, 0, 0, 0), Instance.make(1410, 12 - 1, 1, 0, 0, 0));

		// 1411
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 7 - 1, 23, 0, 0, 0), Instance.make(1411, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 8 - 1, 22, 0, 0, 0), Instance.make(1411, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 9 - 1, 20, 0, 0, 0), Instance.make(1411, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 10 - 1, 20, 0, 0, 0), Instance.make(1411, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 11 - 1, 18, 0, 0, 0), Instance.make(1411, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1990, 12 - 1, 18, 0, 0, 0), Instance.make(1411, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 1 - 1, 16, 0, 0, 0), Instance.make(1411, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 2 - 1, 15, 0, 0, 0), Instance.make(1411, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 3 - 1, 16, 0, 0, 0), Instance.make(1411, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 4 - 1, 15, 0, 0, 0), Instance.make(1411, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 5 - 1, 14, 0, 0, 0), Instance.make(1411, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 6 - 1, 13, 0, 0, 0), Instance.make(1411, 12 - 1, 1, 0, 0, 0));

		// 1412
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 7 - 1, 12, 0, 0, 0), Instance.make(1412, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 8 - 1, 11, 0, 0, 0), Instance.make(1412, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 9 - 1, 9, 0, 0, 0), Instance.make(1412, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 10 - 1, 9, 0, 0, 0), Instance.make(1412, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 11 - 1, 7, 0, 0, 0), Instance.make(1412, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1991, 12 - 1, 7, 0, 0, 0), Instance.make(1412, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 1 - 1, 5, 0, 0, 0), Instance.make(1412, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 2 - 1, 4, 0, 0, 0), Instance.make(1412, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 3 - 1, 4, 0, 0, 0), Instance.make(1412, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 4 - 1, 3, 0, 0, 0), Instance.make(1412, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 5 - 1, 2, 0, 0, 0), Instance.make(1412, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 6 - 1, 1, 0, 0, 0), Instance.make(1412, 12 - 1, 1, 0, 0, 0));

		// 1413
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 7 - 1, 1, 0, 0, 0), Instance.make(1413, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 7 - 1, 31, 0, 0, 0), Instance.make(1413, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 8 - 1, 29, 0, 0, 0), Instance.make(1413, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 9 - 1, 28, 0, 0, 0), Instance.make(1413, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 10 - 1, 27, 0, 0, 0), Instance.make(1413, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 11 - 1, 26, 0, 0, 0), Instance.make(1413, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1992, 12 - 1, 25, 0, 0, 0), Instance.make(1413, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 1 - 1, 24, 0, 0, 0), Instance.make(1413, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 2 - 1, 22, 0, 0, 0), Instance.make(1413, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 3 - 1, 24, 0, 0, 0), Instance.make(1413, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 4 - 1, 22, 0, 0, 0), Instance.make(1413, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 5 - 1, 22, 0, 0, 0), Instance.make(1413, 12 - 1, 1, 0, 0, 0));

		// 1414
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 6 - 1, 20, 0, 0, 0), Instance.make(1414, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 7 - 1, 20, 0, 0, 0), Instance.make(1414, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 8 - 1, 18, 0, 0, 0), Instance.make(1414, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 9 - 1, 17, 0, 0, 0), Instance.make(1414, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 10 - 1, 16, 0, 0, 0), Instance.make(1414, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 11 - 1, 15, 0, 0, 0), Instance.make(1414, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1993, 12 - 1, 14, 0, 0, 0), Instance.make(1414, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 1 - 1, 13, 0, 0, 0), Instance.make(1414, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 2 - 1, 11, 0, 0, 0), Instance.make(1414, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 3 - 1, 13, 0, 0, 0), Instance.make(1414, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 4 - 1, 11, 0, 0, 0), Instance.make(1414, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 5 - 1, 11, 0, 0, 0), Instance.make(1414, 12 - 1, 1, 0, 0, 0));

		// 1415
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 6 - 1, 9, 0, 0, 0), Instance.make(1415, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 7 - 1, 9, 0, 0, 0), Instance.make(1415, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 8 - 1, 7, 0, 0, 0), Instance.make(1415, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 9 - 1, 6, 0, 0, 0), Instance.make(1415, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 10 - 1, 5, 0, 0, 0), Instance.make(1415, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 11 - 1, 4, 0, 0, 0), Instance.make(1415, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1994, 12 - 1, 3, 0, 0, 0), Instance.make(1415, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 1 - 1, 2, 0, 0, 0), Instance.make(1415, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 1 - 1, 31, 0, 0, 0), Instance.make(1415, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 3 - 1, 2, 0, 0, 0), Instance.make(1415, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 3 - 1, 31, 0, 0, 0), Instance.make(1415, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 4 - 1, 30, 0, 0, 0), Instance.make(1415, 12 - 1, 1, 0, 0, 0));

		// 1416
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 5 - 1, 30, 0, 0, 0), Instance.make(1416, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 6 - 1, 29, 0, 0, 0), Instance.make(1416, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 7 - 1, 28, 0, 0, 0), Instance.make(1416, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 8 - 1, 27, 0, 0, 0), Instance.make(1416, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 9 - 1, 25, 0, 0, 0), Instance.make(1416, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 10 - 1, 25, 0, 0, 0), Instance.make(1416, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 11 - 1, 23, 0, 0, 0), Instance.make(1416, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1995, 12 - 1, 23, 0, 0, 0), Instance.make(1416, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 1 - 1, 21, 0, 0, 0), Instance.make(1416, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 2 - 1, 20, 0, 0, 0), Instance.make(1416, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 3 - 1, 20, 0, 0, 0), Instance.make(1416, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 4 - 1, 19, 0, 0, 0), Instance.make(1416, 12 - 1, 1, 0, 0, 0));

		// 1417
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 5 - 1, 18, 0, 0, 0), Instance.make(1417, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 6 - 1, 17, 0, 0, 0), Instance.make(1417, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 7 - 1, 16, 0, 0, 0), Instance.make(1417, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 8 - 1, 15, 0, 0, 0), Instance.make(1417, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 9 - 1, 13, 0, 0, 0), Instance.make(1417, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 10 - 1, 13, 0, 0, 0), Instance.make(1417, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 11 - 1, 11, 0, 0, 0), Instance.make(1417, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1996, 12 - 1, 11, 0, 0, 0), Instance.make(1417, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 1 - 1, 9, 0, 0, 0), Instance.make(1417, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 2 - 1, 8, 0, 0, 0), Instance.make(1417, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 3 - 1, 9, 0, 0, 0), Instance.make(1417, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 4 - 1, 8, 0, 0, 0), Instance.make(1417, 12 - 1, 1, 0, 0, 0));

		// 1418
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 5 - 1, 7, 0, 0, 0), Instance.make(1418, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 6 - 1, 6, 0, 0, 0), Instance.make(1418, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 7 - 1, 5, 0, 0, 0), Instance.make(1418, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 8 - 1, 4, 0, 0, 0), Instance.make(1418, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 9 - 1, 2, 0, 0, 0), Instance.make(1418, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 10 - 1, 2, 0, 0, 0), Instance.make(1418, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 10 - 1, 31, 0, 0, 0), Instance.make(1418, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 11 - 1, 30, 0, 0, 0), Instance.make(1418, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1997, 12 - 1, 29, 0, 0, 0), Instance.make(1418, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 1 - 1, 28, 0, 0, 0), Instance.make(1418, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 2 - 1, 26, 0, 0, 0), Instance.make(1418, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 3 - 1, 28, 0, 0, 0), Instance.make(1418, 12 - 1, 1, 0, 0, 0));

		// 1419
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 4 - 1, 27, 0, 0, 0), Instance.make(1419, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 5 - 1, 27, 0, 0, 0), Instance.make(1419, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 6 - 1, 25, 0, 0, 0), Instance.make(1419, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 7 - 1, 25, 0, 0, 0), Instance.make(1419, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 8 - 1, 23, 0, 0, 0), Instance.make(1419, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 9 - 1, 22, 0, 0, 0), Instance.make(1419, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 10 - 1, 21, 0, 0, 0), Instance.make(1419, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 11 - 1, 20, 0, 0, 0), Instance.make(1419, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1998, 12 - 1, 19, 0, 0, 0), Instance.make(1419, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 1 - 1, 18, 0, 0, 0), Instance.make(1419, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 2 - 1, 16, 0, 0, 0), Instance.make(1419, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 3 - 1, 18, 0, 0, 0), Instance.make(1419, 12 - 1, 1, 0, 0, 0));

		// 1420
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 4 - 1, 16, 0, 0, 0), Instance.make(1420, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 5 - 1, 16, 0, 0, 0), Instance.make(1420, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 6 - 1, 14, 0, 0, 0), Instance.make(1420, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 7 - 1, 14, 0, 0, 0), Instance.make(1420, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 8 - 1, 12, 0, 0, 0), Instance.make(1420, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 9 - 1, 11, 0, 0, 0), Instance.make(1420, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 10 - 1, 10, 0, 0, 0), Instance.make(1420, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 11 - 1, 9, 0, 0, 0), Instance.make(1420, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(1999, 12 - 1, 8, 0, 0, 0), Instance.make(1420, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 1 - 1, 7, 0, 0, 0), Instance.make(1420, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 2 - 1, 5, 0, 0, 0), Instance.make(1420, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 3 - 1, 6, 0, 0, 0), Instance.make(1420, 12 - 1, 1, 0, 0, 0));

		// 1421
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 4 - 1, 4, 0, 0, 0), Instance.make(1421, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 5 - 1, 4, 0, 0, 0), Instance.make(1421, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 6 - 1, 2, 0, 0, 0), Instance.make(1421, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 7 - 1, 2, 0, 0, 0), Instance.make(1421, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 7 - 1, 31, 0, 0, 0), Instance.make(1421, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 8 - 1, 30, 0, 0, 0), Instance.make(1421, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 9 - 1, 28, 0, 0, 0), Instance.make(1421, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 10 - 1, 28, 0, 0, 0), Instance.make(1421, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 11 - 1, 26, 0, 0, 0), Instance.make(1421, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2000, 12 - 1, 26, 0, 0, 0), Instance.make(1421, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 1 - 1, 24, 0, 0, 0), Instance.make(1421, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 2 - 1, 23, 0, 0, 0), Instance.make(1421, 12 - 1, 1, 0, 0, 0));

		// 1422
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 3 - 1, 25, 0, 0, 0), Instance.make(1422, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 4 - 1, 24, 0, 0, 0), Instance.make(1422, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 5 - 1, 23, 0, 0, 0), Instance.make(1422, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 6 - 1, 22, 0, 0, 0), Instance.make(1422, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 7 - 1, 21, 0, 0, 0), Instance.make(1422, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 8 - 1, 20, 0, 0, 0), Instance.make(1422, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 9 - 1, 18, 0, 0, 0), Instance.make(1422, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 10 - 1, 18, 0, 0, 0), Instance.make(1422, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 11 - 1, 16, 0, 0, 0), Instance.make(1422, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2001, 12 - 1, 16, 0, 0, 0), Instance.make(1422, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 1 - 1, 14, 0, 0, 0), Instance.make(1422, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 2 - 1, 13, 0, 0, 0), Instance.make(1422, 12 - 1, 1, 0, 0, 0));

		// 1423
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 3 - 1, 14, 0, 0, 0), Instance.make(1423, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 4 - 1, 13, 0, 0, 0), Instance.make(1423, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 5 - 1, 12, 0, 0, 0), Instance.make(1423, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 6 - 1, 11, 0, 0, 0), Instance.make(1423, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 7 - 1, 10, 0, 0, 0), Instance.make(1423, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 8 - 1, 9, 0, 0, 0), Instance.make(1423, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 9 - 1, 7, 0, 0, 0), Instance.make(1423, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 10 - 1, 7, 0, 0, 0), Instance.make(1423, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 11 - 1, 5, 0, 0, 0), Instance.make(1423, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2002, 12 - 1, 5, 0, 0, 0), Instance.make(1423, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 1 - 1, 3, 0, 0, 0), Instance.make(1423, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 2 - 1, 2, 0, 0, 0), Instance.make(1423, 12 - 1, 1, 0, 0, 0));

		// 1424
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 3 - 1, 4, 0, 0, 0), Instance.make(1424, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 4 - 1, 3, 0, 0, 0), Instance.make(1424, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 5 - 1, 2, 0, 0, 0), Instance.make(1424, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 6 - 1, 1, 0, 0, 0), Instance.make(1424, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 6 - 1, 30, 0, 0, 0), Instance.make(1424, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 7 - 1, 30, 0, 0, 0), Instance.make(1424, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 8 - 1, 28, 0, 0, 0), Instance.make(1424, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 9 - 1, 27, 0, 0, 0), Instance.make(1424, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 10 - 1, 26, 0, 0, 0), Instance.make(1424, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 11 - 1, 25, 0, 0, 0), Instance.make(1424, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2003, 12 - 1, 24, 0, 0, 0), Instance.make(1424, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 1 - 1, 23, 0, 0, 0), Instance.make(1424, 12 - 1, 1, 0, 0, 0));

		// 1425
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 2 - 1, 21, 0, 0, 0), Instance.make(1425, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 3 - 1, 22, 0, 0, 0), Instance.make(1425, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 4 - 1, 20, 0, 0, 0), Instance.make(1425, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 5 - 1, 20, 0, 0, 0), Instance.make(1425, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 6 - 1, 18, 0, 0, 0), Instance.make(1425, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 7 - 1, 18, 0, 0, 0), Instance.make(1425, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 8 - 1, 16, 0, 0, 0), Instance.make(1425, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 9 - 1, 15, 0, 0, 0), Instance.make(1425, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 10 - 1, 14, 0, 0, 0), Instance.make(1425, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 11 - 1, 13, 0, 0, 0), Instance.make(1425, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2004, 12 - 1, 12, 0, 0, 0), Instance.make(1425, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 1 - 1, 11, 0, 0, 0), Instance.make(1425, 12 - 1, 1, 0, 0, 0));

		// 1426
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 2 - 1, 9, 0, 0, 0), Instance.make(1426, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 3 - 1, 11, 0, 0, 0), Instance.make(1426, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 4 - 1, 9, 0, 0, 0), Instance.make(1426, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 5 - 1, 9, 0, 0, 0), Instance.make(1426, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 6 - 1, 7, 0, 0, 0), Instance.make(1426, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 7 - 1, 7, 0, 0, 0), Instance.make(1426, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 8 - 1, 5, 0, 0, 0), Instance.make(1426, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 9 - 1, 4, 0, 0, 0), Instance.make(1426, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 10 - 1, 3, 0, 0, 0), Instance.make(1426, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 11 - 1, 2, 0, 0, 0), Instance.make(1426, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 12 - 1, 1, 0, 0, 0), Instance.make(1426, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2005, 12 - 1, 31, 0, 0, 0), Instance.make(1426, 12 - 1, 1, 0, 0, 0));

		// 1427
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 1 - 1, 30, 0, 0, 0), Instance.make(1427, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 3 - 1, 1, 0, 0, 0), Instance.make(1427, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 3 - 1, 30, 0, 0, 0), Instance.make(1427, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 4 - 1, 29, 0, 0, 0), Instance.make(1427, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 5 - 1, 28, 0, 0, 0), Instance.make(1427, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 6 - 1, 27, 0, 0, 0), Instance.make(1427, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 7 - 1, 26, 0, 0, 0), Instance.make(1427, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 8 - 1, 25, 0, 0, 0), Instance.make(1427, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 9 - 1, 23, 0, 0, 0), Instance.make(1427, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 10 - 1, 23, 0, 0, 0), Instance.make(1427, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 11 - 1, 21, 0, 0, 0), Instance.make(1427, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2006, 12 - 1, 21, 0, 0, 0), Instance.make(1427, 12 - 1, 1, 0, 0, 0));

		// 1428
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 1 - 1, 19, 0, 0, 0), Instance.make(1428, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 2 - 1, 18, 0, 0, 0), Instance.make(1428, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 3 - 1, 19, 0, 0, 0), Instance.make(1428, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 4 - 1, 18, 0, 0, 0), Instance.make(1428, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 5 - 1, 17, 0, 0, 0), Instance.make(1428, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 6 - 1, 16, 0, 0, 0), Instance.make(1428, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 7 - 1, 15, 0, 0, 0), Instance.make(1428, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 8 - 1, 14, 0, 0, 0), Instance.make(1428, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 9 - 1, 12, 0, 0, 0), Instance.make(1428, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 10 - 1, 12, 0, 0, 0), Instance.make(1428, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 11 - 1, 10, 0, 0, 0), Instance.make(1428, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2007, 12 - 1, 10, 0, 0, 0), Instance.make(1428, 12 - 1, 1, 0, 0, 0));

		// 1429
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 1 - 1, 8, 0, 0, 0), Instance.make(1429, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 2 - 1, 7, 0, 0, 0), Instance.make(1429, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 3 - 1, 7, 0, 0, 0), Instance.make(1429, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 4 - 1, 6, 0, 0, 0), Instance.make(1429, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 5 - 1, 5, 0, 0, 0), Instance.make(1429, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 6 - 1, 4, 0, 0, 0), Instance.make(1429, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 7 - 1, 3, 0, 0, 0), Instance.make(1429, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 8 - 1, 2, 0, 0, 0), Instance.make(1429, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 8 - 1, 31, 0, 0, 0), Instance.make(1429, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 9 - 1, 30, 0, 0, 0), Instance.make(1429, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 10 - 1, 29, 0, 0, 0), Instance.make(1429, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 11 - 1, 28, 0, 0, 0), Instance.make(1429, 12 - 1, 1, 0, 0, 0));

		// 1430
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2008, 12 - 1, 28, 0, 0, 0), Instance.make(1430, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 1 - 1, 27, 0, 0, 0), Instance.make(1430, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 2 - 1, 25, 0, 0, 0), Instance.make(1430, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 3 - 1, 27, 0, 0, 0), Instance.make(1430, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 4 - 1, 25, 0, 0, 0), Instance.make(1430, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 5 - 1, 25, 0, 0, 0), Instance.make(1430, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 6 - 1, 23, 0, 0, 0), Instance.make(1430, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 7 - 1, 23, 0, 0, 0), Instance.make(1430, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 8 - 1, 21, 0, 0, 0), Instance.make(1430, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 9 - 1, 20, 0, 0, 0), Instance.make(1430, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 10 - 1, 19, 0, 0, 0), Instance.make(1430, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 11 - 1, 18, 0, 0, 0), Instance.make(1430, 12 - 1, 1, 0, 0, 0));

		// 1431
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2009, 12 - 1, 17, 0, 0, 0), Instance.make(1431, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 1 - 1, 16, 0, 0, 0), Instance.make(1431, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 2 - 1, 14, 0, 0, 0), Instance.make(1431, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 3 - 1, 16, 0, 0, 0), Instance.make(1431, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 4 - 1, 14, 0, 0, 0), Instance.make(1431, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 5 - 1, 14, 0, 0, 0), Instance.make(1431, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 6 - 1, 12, 0, 0, 0), Instance.make(1431, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 7 - 1, 12, 0, 0, 0), Instance.make(1431, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 8 - 1, 10, 0, 0, 0), Instance.make(1431, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 9 - 1, 9, 0, 0, 0), Instance.make(1431, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 10 - 1, 8, 0, 0, 0), Instance.make(1431, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 11 - 1, 7, 0, 0, 0), Instance.make(1431, 12 - 1, 1, 0, 0, 0));

		// 1432
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2010, 12 - 1, 7, 0, 0, 0), Instance.make(1432, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 1 - 1, 6, 0, 0, 0), Instance.make(1432, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 2 - 1, 4, 0, 0, 0), Instance.make(1432, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 3 - 1, 6, 0, 0, 0), Instance.make(1432, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 4 - 1, 4, 0, 0, 0), Instance.make(1432, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 5 - 1, 4, 0, 0, 0), Instance.make(1432, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 6 - 1, 2, 0, 0, 0), Instance.make(1432, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 7 - 1, 2, 0, 0, 0), Instance.make(1432, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 7 - 1, 31, 0, 0, 0), Instance.make(1432, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 8 - 1, 30, 0, 0, 0), Instance.make(1432, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 9 - 1, 28, 0, 0, 0), Instance.make(1432, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 10 - 1, 28, 0, 0, 0), Instance.make(1432, 12 - 1, 1, 0, 0, 0));

		// 1433
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 11 - 1, 26, 0, 0, 0), Instance.make(1433, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2011, 12 - 1, 26, 0, 0, 0), Instance.make(1433, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 1 - 1, 24, 0, 0, 0), Instance.make(1433, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 2 - 1, 23, 0, 0, 0), Instance.make(1433, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 3 - 1, 23, 0, 0, 0), Instance.make(1433, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 4 - 1, 22, 0, 0, 0), Instance.make(1433, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 5 - 1, 21, 0, 0, 0), Instance.make(1433, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 6 - 1, 20, 0, 0, 0), Instance.make(1433, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 7 - 1, 19, 0, 0, 0), Instance.make(1433, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 8 - 1, 18, 0, 0, 0), Instance.make(1433, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 9 - 1, 16, 0, 0, 0), Instance.make(1433, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 10 - 1, 16, 0, 0, 0), Instance.make(1433, 12 - 1, 1, 0, 0, 0));

		// 1434
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 11 - 1, 14, 0, 0, 0), Instance.make(1434, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2012, 12 - 1, 14, 0, 0, 0), Instance.make(1434, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 1 - 1, 12, 0, 0, 0), Instance.make(1434, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 2 - 1, 11, 0, 0, 0), Instance.make(1434, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 3 - 1, 12, 0, 0, 0), Instance.make(1434, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 4 - 1, 11, 0, 0, 0), Instance.make(1434, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 5 - 1, 10, 0, 0, 0), Instance.make(1434, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 6 - 1, 9, 0, 0, 0), Instance.make(1434, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 7 - 1, 8, 0, 0, 0), Instance.make(1434, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 8 - 1, 7, 0, 0, 0), Instance.make(1434, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 9 - 1, 5, 0, 0, 0), Instance.make(1434, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 10 - 1, 5, 0, 0, 0), Instance.make(1434, 12 - 1, 1, 0, 0, 0));

		// 1435
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 11 - 1, 4, 0, 0, 0), Instance.make(1435, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2013, 12 - 1, 4, 0, 0, 0), Instance.make(1435, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 1 - 1, 2, 0, 0, 0), Instance.make(1435, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 2 - 1, 1, 0, 0, 0), Instance.make(1435, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 3 - 1, 2, 0, 0, 0), Instance.make(1435, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 4 - 1, 1, 0, 0, 0), Instance.make(1435, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 4 - 1, 30, 0, 0, 0), Instance.make(1435, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 5 - 1, 30, 0, 0, 0), Instance.make(1435, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 6 - 1, 28, 0, 0, 0), Instance.make(1435, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 7 - 1, 28, 0, 0, 0), Instance.make(1435, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 8 - 1, 26, 0, 0, 0), Instance.make(1435, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 9 - 1, 25, 0, 0, 0), Instance.make(1435, 12 - 1, 1, 0, 0, 0));

		// 1436
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 10 - 1, 24, 0, 0, 0), Instance.make(1436, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 11 - 1, 23, 0, 0, 0), Instance.make(1436, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2014, 12 - 1, 22, 0, 0, 0), Instance.make(1436, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 1 - 1, 21, 0, 0, 0), Instance.make(1436, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 2 - 1, 19, 0, 0, 0), Instance.make(1436, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 3 - 1, 21, 0, 0, 0), Instance.make(1436, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 4 - 1, 19, 0, 0, 0), Instance.make(1436, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 5 - 1, 19, 0, 0, 0), Instance.make(1436, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 6 - 1, 17, 0, 0, 0), Instance.make(1436, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 7 - 1, 17, 0, 0, 0), Instance.make(1436, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 8 - 1, 15, 0, 0, 0), Instance.make(1436, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 9 - 1, 14, 0, 0, 0), Instance.make(1436, 12 - 1, 1, 0, 0, 0));

		// 1437
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 10 - 1, 13, 0, 0, 0), Instance.make(1437, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 11 - 1, 12, 0, 0, 0), Instance.make(1437, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2015, 12 - 1, 11, 0, 0, 0), Instance.make(1437, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 1 - 1, 10, 0, 0, 0), Instance.make(1437, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 2 - 1, 8, 0, 0, 0), Instance.make(1437, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 3 - 1, 9, 0, 0, 0), Instance.make(1437, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 4 - 1, 7, 0, 0, 0), Instance.make(1437, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 5 - 1, 7, 0, 0, 0), Instance.make(1437, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 6 - 1, 5, 0, 0, 0), Instance.make(1437, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 7 - 1, 5, 0, 0, 0), Instance.make(1437, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 8 - 1, 3, 0, 0, 0), Instance.make(1437, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 9 - 1, 2, 0, 0, 0), Instance.make(1437, 12 - 1, 1, 0, 0, 0));

		// 1438
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 10 - 1, 2, 0, 0, 0), Instance.make(1438, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 11 - 1, 1, 0, 0, 0), Instance.make(1438, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 11 - 1, 30, 0, 0, 0), Instance.make(1438, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2016, 12 - 1, 30, 0, 0, 0), Instance.make(1438, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 1 - 1, 28, 0, 0, 0), Instance.make(1438, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 2 - 1, 27, 0, 0, 0), Instance.make(1438, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 3 - 1, 28, 0, 0, 0), Instance.make(1438, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 4 - 1, 27, 0, 0, 0), Instance.make(1438, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 5 - 1, 26, 0, 0, 0), Instance.make(1438, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 6 - 1, 25, 0, 0, 0), Instance.make(1438, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 7 - 1, 24, 0, 0, 0), Instance.make(1438, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 8 - 1, 23, 0, 0, 0), Instance.make(1438, 12 - 1, 1, 0, 0, 0));

		// 1439
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 9 - 1, 21, 0, 0, 0), Instance.make(1439, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 10 - 1, 21, 0, 0, 0), Instance.make(1439, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 11 - 1, 19, 0, 0, 0), Instance.make(1439, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2017, 12 - 1, 19, 0, 0, 0), Instance.make(1439, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 1 - 1, 17, 0, 0, 0), Instance.make(1439, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 2 - 1, 16, 0, 0, 0), Instance.make(1439, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 3 - 1, 17, 0, 0, 0), Instance.make(1439, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 4 - 1, 16, 0, 0, 0), Instance.make(1439, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 5 - 1, 15, 0, 0, 0), Instance.make(1439, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 6 - 1, 14, 0, 0, 0), Instance.make(1439, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 7 - 1, 13, 0, 0, 0), Instance.make(1439, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 8 - 1, 12, 0, 0, 0), Instance.make(1439, 12 - 1, 1, 0, 0, 0));

		// 1440
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 9 - 1, 10, 0, 0, 0), Instance.make(1440, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 10 - 1, 10, 0, 0, 0), Instance.make(1440, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 11 - 1, 8, 0, 0, 0), Instance.make(1440, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2018, 12 - 1, 8, 0, 0, 0), Instance.make(1440, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2019, 1 - 1, 6, 0, 0, 0), Instance.make(1440, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2019, 2 - 1, 5, 0, 0, 0), Instance.make(1440, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2019, 3 - 1, 6, 0, 0, 0), Instance.make(1440, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2019, 4 - 1, 5, 0, 0, 0), Instance.make(1440, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2019, 5 - 1, 4, 0, 0, 0), Instance.make(1440, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2019, 6 - 1, 3, 0, 0, 0), Instance.make(1440, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2019, 7 - 1, 2, 0, 0, 0), Instance.make(1440, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][0].put(Instance.make(2019, 8 - 1, 1, 0, 0, 0), Instance.make(1440, 12 - 1, 1, 0, 0, 0));

		/* Pattern IVc */
		// 1410
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1989, 8 - 1, 3, 0, 0, 0), Instance.make(1410, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1989, 9 - 1, 2, 0, 0, 0), Instance.make(1410, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1989, 10 - 1, 1, 0, 0, 0), Instance.make(1410, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1989, 10 - 1, 31, 0, 0, 0), Instance.make(1410, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1989, 11 - 1, 29, 0, 0, 0), Instance.make(1410, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1989, 12 - 1, 29, 0, 0, 0), Instance.make(1410, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 1 - 1, 27, 0, 0, 0), Instance.make(1410, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 2 - 1, 26, 0, 0, 0), Instance.make(1410, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 3 - 1, 27, 0, 0, 0), Instance.make(1410, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 4 - 1, 26, 0, 0, 0), Instance.make(1410, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 5 - 1, 25, 0, 0, 0), Instance.make(1410, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 6 - 1, 24, 0, 0, 0), Instance.make(1410, 12 - 1, 1, 0, 0, 0));

		// 1411
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 7 - 1, 24, 0, 0, 0), Instance.make(1411, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 8 - 1, 23, 0, 0, 0), Instance.make(1411, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 9 - 1, 21, 0, 0, 0), Instance.make(1411, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 10 - 1, 21, 0, 0, 0), Instance.make(1411, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 11 - 1, 19, 0, 0, 0), Instance.make(1411, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1990, 12 - 1, 19, 0, 0, 0), Instance.make(1411, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 1 - 1, 17, 0, 0, 0), Instance.make(1411, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 2 - 1, 16, 0, 0, 0), Instance.make(1411, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 3 - 1, 17, 0, 0, 0), Instance.make(1411, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 4 - 1, 16, 0, 0, 0), Instance.make(1411, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 5 - 1, 15, 0, 0, 0), Instance.make(1411, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 6 - 1, 14, 0, 0, 0), Instance.make(1411, 12 - 1, 1, 0, 0, 0));

		// 1412
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 7 - 1, 13, 0, 0, 0), Instance.make(1412, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 8 - 1, 12, 0, 0, 0), Instance.make(1412, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 9 - 1, 10, 0, 0, 0), Instance.make(1412, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 10 - 1, 10, 0, 0, 0), Instance.make(1412, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 11 - 1, 8, 0, 0, 0), Instance.make(1412, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1991, 12 - 1, 8, 0, 0, 0), Instance.make(1412, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 1 - 1, 6, 0, 0, 0), Instance.make(1412, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 2 - 1, 5, 0, 0, 0), Instance.make(1412, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 3 - 1, 5, 0, 0, 0), Instance.make(1412, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 4 - 1, 4, 0, 0, 0), Instance.make(1412, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 5 - 1, 3, 0, 0, 0), Instance.make(1412, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 6 - 1, 2, 0, 0, 0), Instance.make(1412, 12 - 1, 1, 0, 0, 0));

		// 1413
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 7 - 1, 2, 0, 0, 0), Instance.make(1413, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 8 - 1, 1, 0, 0, 0), Instance.make(1413, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 8 - 1, 30, 0, 0, 0), Instance.make(1413, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 9 - 1, 29, 0, 0, 0), Instance.make(1413, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 10 - 1, 28, 0, 0, 0), Instance.make(1413, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 11 - 1, 27, 0, 0, 0), Instance.make(1413, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1992, 12 - 1, 26, 0, 0, 0), Instance.make(1413, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 1 - 1, 25, 0, 0, 0), Instance.make(1413, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 2 - 1, 23, 0, 0, 0), Instance.make(1413, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 3 - 1, 25, 0, 0, 0), Instance.make(1413, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 4 - 1, 23, 0, 0, 0), Instance.make(1413, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 5 - 1, 23, 0, 0, 0), Instance.make(1413, 12 - 1, 1, 0, 0, 0));

		// 1414
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 6 - 1, 21, 0, 0, 0), Instance.make(1414, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 7 - 1, 21, 0, 0, 0), Instance.make(1414, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 8 - 1, 19, 0, 0, 0), Instance.make(1414, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 9 - 1, 18, 0, 0, 0), Instance.make(1414, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 10 - 1, 17, 0, 0, 0), Instance.make(1414, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 11 - 1, 16, 0, 0, 0), Instance.make(1414, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1993, 12 - 1, 15, 0, 0, 0), Instance.make(1414, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 1 - 1, 14, 0, 0, 0), Instance.make(1414, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 2 - 1, 12, 0, 0, 0), Instance.make(1414, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 3 - 1, 14, 0, 0, 0), Instance.make(1414, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 4 - 1, 12, 0, 0, 0), Instance.make(1414, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 5 - 1, 12, 0, 0, 0), Instance.make(1414, 12 - 1, 1, 0, 0, 0));

		// 1415
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 6 - 1, 10, 0, 0, 0), Instance.make(1415, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 7 - 1, 10, 0, 0, 0), Instance.make(1415, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 8 - 1, 8, 0, 0, 0), Instance.make(1415, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 9 - 1, 7, 0, 0, 0), Instance.make(1415, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 10 - 1, 6, 0, 0, 0), Instance.make(1415, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 11 - 1, 5, 0, 0, 0), Instance.make(1415, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1994, 12 - 1, 4, 0, 0, 0), Instance.make(1415, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 1 - 1, 3, 0, 0, 0), Instance.make(1415, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 2 - 1, 1, 0, 0, 0), Instance.make(1415, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 3 - 1, 3, 0, 0, 0), Instance.make(1415, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 4 - 1, 1, 0, 0, 0), Instance.make(1415, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 5 - 1, 1, 0, 0, 0), Instance.make(1415, 12 - 1, 1, 0, 0, 0));

		// 1416
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 5 - 1, 31, 0, 0, 0), Instance.make(1416, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 6 - 1, 30, 0, 0, 0), Instance.make(1416, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 7 - 1, 29, 0, 0, 0), Instance.make(1416, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 8 - 1, 28, 0, 0, 0), Instance.make(1416, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 9 - 1, 26, 0, 0, 0), Instance.make(1416, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 10 - 1, 26, 0, 0, 0), Instance.make(1416, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 11 - 1, 24, 0, 0, 0), Instance.make(1416, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1995, 12 - 1, 24, 0, 0, 0), Instance.make(1416, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 1 - 1, 22, 0, 0, 0), Instance.make(1416, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 2 - 1, 21, 0, 0, 0), Instance.make(1416, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 3 - 1, 21, 0, 0, 0), Instance.make(1416, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 4 - 1, 20, 0, 0, 0), Instance.make(1416, 12 - 1, 1, 0, 0, 0));

		// 1417
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 5 - 1, 19, 0, 0, 0), Instance.make(1417, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 6 - 1, 18, 0, 0, 0), Instance.make(1417, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 7 - 1, 17, 0, 0, 0), Instance.make(1417, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 8 - 1, 16, 0, 0, 0), Instance.make(1417, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 9 - 1, 14, 0, 0, 0), Instance.make(1417, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 10 - 1, 14, 0, 0, 0), Instance.make(1417, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 11 - 1, 12, 0, 0, 0), Instance.make(1417, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1996, 12 - 1, 12, 0, 0, 0), Instance.make(1417, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 1 - 1, 10, 0, 0, 0), Instance.make(1417, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 2 - 1, 9, 0, 0, 0), Instance.make(1417, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 3 - 1, 10, 0, 0, 0), Instance.make(1417, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 4 - 1, 9, 0, 0, 0), Instance.make(1417, 12 - 1, 1, 0, 0, 0));

		// 1418
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 5 - 1, 8, 0, 0, 0), Instance.make(1418, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 6 - 1, 7, 0, 0, 0), Instance.make(1418, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 7 - 1, 6, 0, 0, 0), Instance.make(1418, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 8 - 1, 5, 0, 0, 0), Instance.make(1418, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 9 - 1, 3, 0, 0, 0), Instance.make(1418, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 10 - 1, 3, 0, 0, 0), Instance.make(1418, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 11 - 1, 1, 0, 0, 0), Instance.make(1418, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 12 - 1, 1, 0, 0, 0), Instance.make(1418, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1997, 12 - 1, 30, 0, 0, 0), Instance.make(1418, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 1 - 1, 29, 0, 0, 0), Instance.make(1418, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 2 - 1, 27, 0, 0, 0), Instance.make(1418, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 3 - 1, 29, 0, 0, 0), Instance.make(1418, 12 - 1, 1, 0, 0, 0));

		// 1419
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 4 - 1, 28, 0, 0, 0), Instance.make(1419, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 5 - 1, 28, 0, 0, 0), Instance.make(1419, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 6 - 1, 26, 0, 0, 0), Instance.make(1419, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 7 - 1, 26, 0, 0, 0), Instance.make(1419, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 8 - 1, 24, 0, 0, 0), Instance.make(1419, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 9 - 1, 23, 0, 0, 0), Instance.make(1419, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 10 - 1, 22, 0, 0, 0), Instance.make(1419, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 11 - 1, 21, 0, 0, 0), Instance.make(1419, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1998, 12 - 1, 20, 0, 0, 0), Instance.make(1419, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 1 - 1, 19, 0, 0, 0), Instance.make(1419, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 2 - 1, 17, 0, 0, 0), Instance.make(1419, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 3 - 1, 19, 0, 0, 0), Instance.make(1419, 12 - 1, 1, 0, 0, 0));

		// 1420
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 4 - 1, 17, 0, 0, 0), Instance.make(1420, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 5 - 1, 17, 0, 0, 0), Instance.make(1420, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 6 - 1, 15, 0, 0, 0), Instance.make(1420, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 7 - 1, 15, 0, 0, 0), Instance.make(1420, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 8 - 1, 13, 0, 0, 0), Instance.make(1420, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 9 - 1, 12, 0, 0, 0), Instance.make(1420, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 10 - 1, 11, 0, 0, 0), Instance.make(1420, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 11 - 1, 10, 0, 0, 0), Instance.make(1420, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(1999, 12 - 1, 9, 0, 0, 0), Instance.make(1420, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 1 - 1, 8, 0, 0, 0), Instance.make(1420, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 2 - 1, 6, 0, 0, 0), Instance.make(1420, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 3 - 1, 7, 0, 0, 0), Instance.make(1420, 12 - 1, 1, 0, 0, 0));

		// 1421
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 4 - 1, 5, 0, 0, 0), Instance.make(1421, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 5 - 1, 5, 0, 0, 0), Instance.make(1421, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 6 - 1, 3, 0, 0, 0), Instance.make(1421, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 7 - 1, 3, 0, 0, 0), Instance.make(1421, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 8 - 1, 1, 0, 0, 0), Instance.make(1421, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 8 - 1, 31, 0, 0, 0), Instance.make(1421, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 9 - 1, 29, 0, 0, 0), Instance.make(1421, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 10 - 1, 29, 0, 0, 0), Instance.make(1421, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 11 - 1, 27, 0, 0, 0), Instance.make(1421, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2000, 12 - 1, 27, 0, 0, 0), Instance.make(1421, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 1 - 1, 25, 0, 0, 0), Instance.make(1421, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 2 - 1, 24, 0, 0, 0), Instance.make(1421, 12 - 1, 1, 0, 0, 0));

		// 1422
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 3 - 1, 26, 0, 0, 0), Instance.make(1422, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 4 - 1, 25, 0, 0, 0), Instance.make(1422, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 5 - 1, 24, 0, 0, 0), Instance.make(1422, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 6 - 1, 23, 0, 0, 0), Instance.make(1422, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 7 - 1, 22, 0, 0, 0), Instance.make(1422, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 8 - 1, 21, 0, 0, 0), Instance.make(1422, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 9 - 1, 19, 0, 0, 0), Instance.make(1422, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 10 - 1, 19, 0, 0, 0), Instance.make(1422, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 11 - 1, 17, 0, 0, 0), Instance.make(1422, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2001, 12 - 1, 17, 0, 0, 0), Instance.make(1422, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 1 - 1, 15, 0, 0, 0), Instance.make(1422, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 2 - 1, 14, 0, 0, 0), Instance.make(1422, 12 - 1, 1, 0, 0, 0));

		// 1423
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 3 - 1, 15, 0, 0, 0), Instance.make(1423, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 4 - 1, 14, 0, 0, 0), Instance.make(1423, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 5 - 1, 13, 0, 0, 0), Instance.make(1423, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 6 - 1, 12, 0, 0, 0), Instance.make(1423, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 7 - 1, 11, 0, 0, 0), Instance.make(1423, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 8 - 1, 10, 0, 0, 0), Instance.make(1423, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 9 - 1, 8, 0, 0, 0), Instance.make(1423, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 10 - 1, 8, 0, 0, 0), Instance.make(1423, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 11 - 1, 6, 0, 0, 0), Instance.make(1423, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2002, 12 - 1, 6, 0, 0, 0), Instance.make(1423, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 1 - 1, 4, 0, 0, 0), Instance.make(1423, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 2 - 1, 3, 0, 0, 0), Instance.make(1423, 12 - 1, 1, 0, 0, 0));

		// 1424
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 3 - 1, 5, 0, 0, 0), Instance.make(1424, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 4 - 1, 4, 0, 0, 0), Instance.make(1424, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 5 - 1, 3, 0, 0, 0), Instance.make(1424, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 6 - 1, 2, 0, 0, 0), Instance.make(1424, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 7 - 1, 1, 0, 0, 0), Instance.make(1424, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 7 - 1, 31, 0, 0, 0), Instance.make(1424, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 8 - 1, 29, 0, 0, 0), Instance.make(1424, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 9 - 1, 28, 0, 0, 0), Instance.make(1424, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 10 - 1, 27, 0, 0, 0), Instance.make(1424, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 11 - 1, 26, 0, 0, 0), Instance.make(1424, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2003, 12 - 1, 25, 0, 0, 0), Instance.make(1424, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 1 - 1, 24, 0, 0, 0), Instance.make(1424, 12 - 1, 1, 0, 0, 0));

		// 1425
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 2 - 1, 22, 0, 0, 0), Instance.make(1425, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 3 - 1, 23, 0, 0, 0), Instance.make(1425, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 4 - 1, 21, 0, 0, 0), Instance.make(1425, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 5 - 1, 21, 0, 0, 0), Instance.make(1425, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 6 - 1, 19, 0, 0, 0), Instance.make(1425, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 7 - 1, 19, 0, 0, 0), Instance.make(1425, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 8 - 1, 17, 0, 0, 0), Instance.make(1425, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 9 - 1, 16, 0, 0, 0), Instance.make(1425, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 10 - 1, 15, 0, 0, 0), Instance.make(1425, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 11 - 1, 14, 0, 0, 0), Instance.make(1425, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2004, 12 - 1, 13, 0, 0, 0), Instance.make(1425, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 1 - 1, 12, 0, 0, 0), Instance.make(1425, 12 - 1, 1, 0, 0, 0));

		// 1426
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 2 - 1, 10, 0, 0, 0), Instance.make(1426, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 3 - 1, 12, 0, 0, 0), Instance.make(1426, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 4 - 1, 10, 0, 0, 0), Instance.make(1426, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 5 - 1, 10, 0, 0, 0), Instance.make(1426, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 6 - 1, 8, 0, 0, 0), Instance.make(1426, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 7 - 1, 8, 0, 0, 0), Instance.make(1426, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 8 - 1, 6, 0, 0, 0), Instance.make(1426, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 9 - 1, 5, 0, 0, 0), Instance.make(1426, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 10 - 1, 4, 0, 0, 0), Instance.make(1426, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 11 - 1, 3, 0, 0, 0), Instance.make(1426, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2005, 12 - 1, 2, 0, 0, 0), Instance.make(1426, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 1 - 1, 1, 0, 0, 0), Instance.make(1426, 12 - 1, 1, 0, 0, 0));

		// 1427
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 1 - 1, 31, 0, 0, 0), Instance.make(1427, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 3 - 1, 2, 0, 0, 0), Instance.make(1427, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 3 - 1, 31, 0, 0, 0), Instance.make(1427, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 4 - 1, 30, 0, 0, 0), Instance.make(1427, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 5 - 1, 29, 0, 0, 0), Instance.make(1427, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 6 - 1, 28, 0, 0, 0), Instance.make(1427, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 7 - 1, 27, 0, 0, 0), Instance.make(1427, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 8 - 1, 26, 0, 0, 0), Instance.make(1427, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 9 - 1, 24, 0, 0, 0), Instance.make(1427, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 10 - 1, 24, 0, 0, 0), Instance.make(1427, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 11 - 1, 22, 0, 0, 0), Instance.make(1427, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2006, 12 - 1, 22, 0, 0, 0), Instance.make(1427, 12 - 1, 1, 0, 0, 0));

		// 1428
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 1 - 1, 20, 0, 0, 0), Instance.make(1428, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 2 - 1, 19, 0, 0, 0), Instance.make(1428, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 3 - 1, 20, 0, 0, 0), Instance.make(1428, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 4 - 1, 19, 0, 0, 0), Instance.make(1428, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 5 - 1, 18, 0, 0, 0), Instance.make(1428, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 6 - 1, 17, 0, 0, 0), Instance.make(1428, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 7 - 1, 16, 0, 0, 0), Instance.make(1428, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 8 - 1, 15, 0, 0, 0), Instance.make(1428, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 9 - 1, 13, 0, 0, 0), Instance.make(1428, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 10 - 1, 13, 0, 0, 0), Instance.make(1428, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 11 - 1, 11, 0, 0, 0), Instance.make(1428, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2007, 12 - 1, 11, 0, 0, 0), Instance.make(1428, 12 - 1, 1, 0, 0, 0));

		// 1429
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 1 - 1, 9, 0, 0, 0), Instance.make(1429, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 2 - 1, 8, 0, 0, 0), Instance.make(1429, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 3 - 1, 8, 0, 0, 0), Instance.make(1429, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 4 - 1, 7, 0, 0, 0), Instance.make(1429, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 5 - 1, 6, 0, 0, 0), Instance.make(1429, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 6 - 1, 5, 0, 0, 0), Instance.make(1429, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 7 - 1, 4, 0, 0, 0), Instance.make(1429, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 8 - 1, 3, 0, 0, 0), Instance.make(1429, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 9 - 1, 1, 0, 0, 0), Instance.make(1429, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 10 - 1, 1, 0, 0, 0), Instance.make(1429, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 10 - 1, 30, 0, 0, 0), Instance.make(1429, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 11 - 1, 29, 0, 0, 0), Instance.make(1429, 12 - 1, 1, 0, 0, 0));

		// 1430
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2008, 12 - 1, 29, 0, 0, 0), Instance.make(1430, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 1 - 1, 28, 0, 0, 0), Instance.make(1430, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 2 - 1, 26, 0, 0, 0), Instance.make(1430, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 3 - 1, 28, 0, 0, 0), Instance.make(1430, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 4 - 1, 26, 0, 0, 0), Instance.make(1430, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 5 - 1, 26, 0, 0, 0), Instance.make(1430, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 6 - 1, 24, 0, 0, 0), Instance.make(1430, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 7 - 1, 24, 0, 0, 0), Instance.make(1430, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 8 - 1, 22, 0, 0, 0), Instance.make(1430, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 9 - 1, 21, 0, 0, 0), Instance.make(1430, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 10 - 1, 20, 0, 0, 0), Instance.make(1430, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 11 - 1, 19, 0, 0, 0), Instance.make(1430, 12 - 1, 1, 0, 0, 0));

		// 1431
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2009, 12 - 1, 18, 0, 0, 0), Instance.make(1431, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 1 - 1, 17, 0, 0, 0), Instance.make(1431, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 2 - 1, 15, 0, 0, 0), Instance.make(1431, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 3 - 1, 17, 0, 0, 0), Instance.make(1431, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 4 - 1, 15, 0, 0, 0), Instance.make(1431, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 5 - 1, 15, 0, 0, 0), Instance.make(1431, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 6 - 1, 13, 0, 0, 0), Instance.make(1431, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 7 - 1, 13, 0, 0, 0), Instance.make(1431, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 8 - 1, 11, 0, 0, 0), Instance.make(1431, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 9 - 1, 10, 0, 0, 0), Instance.make(1431, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 10 - 1, 9, 0, 0, 0), Instance.make(1431, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 11 - 1, 8, 0, 0, 0), Instance.make(1431, 12 - 1, 1, 0, 0, 0));

		// 1432
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2010, 12 - 1, 8, 0, 0, 0), Instance.make(1432, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 1 - 1, 7, 0, 0, 0), Instance.make(1432, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 2 - 1, 5, 0, 0, 0), Instance.make(1432, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 3 - 1, 7, 0, 0, 0), Instance.make(1432, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 4 - 1, 5, 0, 0, 0), Instance.make(1432, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 5 - 1, 5, 0, 0, 0), Instance.make(1432, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 6 - 1, 3, 0, 0, 0), Instance.make(1432, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 7 - 1, 3, 0, 0, 0), Instance.make(1432, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 8 - 1, 1, 0, 0, 0), Instance.make(1432, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 8 - 1, 31, 0, 0, 0), Instance.make(1432, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 9 - 1, 29, 0, 0, 0), Instance.make(1432, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 10 - 1, 29, 0, 0, 0), Instance.make(1432, 12 - 1, 1, 0, 0, 0));

		// 1433
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 11 - 1, 27, 0, 0, 0), Instance.make(1433, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2011, 12 - 1, 27, 0, 0, 0), Instance.make(1433, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 1 - 1, 25, 0, 0, 0), Instance.make(1433, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 2 - 1, 24, 0, 0, 0), Instance.make(1433, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 3 - 1, 24, 0, 0, 0), Instance.make(1433, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 4 - 1, 23, 0, 0, 0), Instance.make(1433, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 5 - 1, 22, 0, 0, 0), Instance.make(1433, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 6 - 1, 21, 0, 0, 0), Instance.make(1433, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 7 - 1, 20, 0, 0, 0), Instance.make(1433, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 8 - 1, 19, 0, 0, 0), Instance.make(1433, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 9 - 1, 17, 0, 0, 0), Instance.make(1433, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 10 - 1, 17, 0, 0, 0), Instance.make(1433, 12 - 1, 1, 0, 0, 0));

		// 1434
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 11 - 1, 15, 0, 0, 0), Instance.make(1434, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2012, 12 - 1, 15, 0, 0, 0), Instance.make(1434, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 1 - 1, 13, 0, 0, 0), Instance.make(1434, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 2 - 1, 12, 0, 0, 0), Instance.make(1434, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 3 - 1, 13, 0, 0, 0), Instance.make(1434, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 4 - 1, 12, 0, 0, 0), Instance.make(1434, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 5 - 1, 11, 0, 0, 0), Instance.make(1434, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 6 - 1, 10, 0, 0, 0), Instance.make(1434, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 7 - 1, 9, 0, 0, 0), Instance.make(1434, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 8 - 1, 8, 0, 0, 0), Instance.make(1434, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 9 - 1, 6, 0, 0, 0), Instance.make(1434, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 10 - 1, 6, 0, 0, 0), Instance.make(1434, 12 - 1, 1, 0, 0, 0));

		// 1435
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 11 - 1, 5, 0, 0, 0), Instance.make(1435, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2013, 12 - 1, 5, 0, 0, 0), Instance.make(1435, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 1 - 1, 3, 0, 0, 0), Instance.make(1435, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 2 - 1, 2, 0, 0, 0), Instance.make(1435, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 3 - 1, 3, 0, 0, 0), Instance.make(1435, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 4 - 1, 2, 0, 0, 0), Instance.make(1435, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 5 - 1, 1, 0, 0, 0), Instance.make(1435, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 5 - 1, 31, 0, 0, 0), Instance.make(1435, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 6 - 1, 29, 0, 0, 0), Instance.make(1435, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 7 - 1, 29, 0, 0, 0), Instance.make(1435, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 8 - 1, 27, 0, 0, 0), Instance.make(1435, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 9 - 1, 26, 0, 0, 0), Instance.make(1435, 12 - 1, 1, 0, 0, 0));

		// 1436
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 10 - 1, 25, 0, 0, 0), Instance.make(1436, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 11 - 1, 24, 0, 0, 0), Instance.make(1436, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2014, 12 - 1, 23, 0, 0, 0), Instance.make(1436, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 1 - 1, 22, 0, 0, 0), Instance.make(1436, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 2 - 1, 20, 0, 0, 0), Instance.make(1436, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 3 - 1, 22, 0, 0, 0), Instance.make(1436, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 4 - 1, 20, 0, 0, 0), Instance.make(1436, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 5 - 1, 20, 0, 0, 0), Instance.make(1436, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 6 - 1, 18, 0, 0, 0), Instance.make(1436, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 7 - 1, 18, 0, 0, 0), Instance.make(1436, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 8 - 1, 16, 0, 0, 0), Instance.make(1436, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 9 - 1, 15, 0, 0, 0), Instance.make(1436, 12 - 1, 1, 0, 0, 0));

		// 1437
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 10 - 1, 14, 0, 0, 0), Instance.make(1437, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 11 - 1, 13, 0, 0, 0), Instance.make(1437, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2015, 12 - 1, 12, 0, 0, 0), Instance.make(1437, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 1 - 1, 11, 0, 0, 0), Instance.make(1437, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 2 - 1, 9, 0, 0, 0), Instance.make(1437, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 3 - 1, 10, 0, 0, 0), Instance.make(1437, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 4 - 1, 8, 0, 0, 0), Instance.make(1437, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 5 - 1, 8, 0, 0, 0), Instance.make(1437, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 6 - 1, 6, 0, 0, 0), Instance.make(1437, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 7 - 1, 6, 0, 0, 0), Instance.make(1437, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 8 - 1, 4, 0, 0, 0), Instance.make(1437, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 9 - 1, 3, 0, 0, 0), Instance.make(1437, 12 - 1, 1, 0, 0, 0));

		// 1438
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 10 - 1, 3, 0, 0, 0), Instance.make(1438, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 11 - 1, 2, 0, 0, 0), Instance.make(1438, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 12 - 1, 1, 0, 0, 0), Instance.make(1438, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2016, 12 - 1, 31, 0, 0, 0), Instance.make(1438, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 1 - 1, 29, 0, 0, 0), Instance.make(1438, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 2 - 1, 28, 0, 0, 0), Instance.make(1438, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 3 - 1, 29, 0, 0, 0), Instance.make(1438, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 4 - 1, 28, 0, 0, 0), Instance.make(1438, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 5 - 1, 27, 0, 0, 0), Instance.make(1438, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 6 - 1, 26, 0, 0, 0), Instance.make(1438, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 7 - 1, 25, 0, 0, 0), Instance.make(1438, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 8 - 1, 24, 0, 0, 0), Instance.make(1438, 12 - 1, 1, 0, 0, 0));

		// 1439
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 9 - 1, 22, 0, 0, 0), Instance.make(1439, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 10 - 1, 22, 0, 0, 0), Instance.make(1439, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 11 - 1, 20, 0, 0, 0), Instance.make(1439, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2017, 12 - 1, 20, 0, 0, 0), Instance.make(1439, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 1 - 1, 18, 0, 0, 0), Instance.make(1439, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 2 - 1, 17, 0, 0, 0), Instance.make(1439, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 3 - 1, 18, 0, 0, 0), Instance.make(1439, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 4 - 1, 17, 0, 0, 0), Instance.make(1439, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 5 - 1, 16, 0, 0, 0), Instance.make(1439, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 6 - 1, 15, 0, 0, 0), Instance.make(1439, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 7 - 1, 14, 0, 0, 0), Instance.make(1439, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 8 - 1, 13, 0, 0, 0), Instance.make(1439, 12 - 1, 1, 0, 0, 0));

		// 1440
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 9 - 1, 11, 0, 0, 0), Instance.make(1440, 1 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 10 - 1, 11, 0, 0, 0), Instance.make(1440, 2 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 11 - 1, 9, 0, 0, 0), Instance.make(1440, 3 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2018, 12 - 1, 9, 0, 0, 0), Instance.make(1440, 4 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2019, 1 - 1, 7, 0, 0, 0), Instance.make(1440, 5 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2019, 2 - 1, 6, 0, 0, 0), Instance.make(1440, 6 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2019, 3 - 1, 7, 0, 0, 0), Instance.make(1440, 7 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2019, 4 - 1, 6, 0, 0, 0), Instance.make(1440, 8 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2019, 5 - 1, 5, 0, 0, 0), Instance.make(1440, 9 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2019, 6 - 1, 4, 0, 0, 0), Instance.make(1440, 10 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2019, 7 - 1, 3, 0, 0, 0), Instance.make(1440, 11 - 1, 1, 0, 0, 0));
		DATE_MAPS[LeapYearPattern.IV.ordinal()][1].put(Instance.make(2019, 8 - 1, 2, 0, 0, 0), Instance.make(1440, 12 - 1, 1, 0, 0, 0));
	}

	static
	{
		DATE_MAPS[0][0] = new HashMap<Long, Long>(100);
		DATE_MAPS[0][1] = new HashMap<Long, Long>(100);
		DATE_MAPS[1][0] = new HashMap<Long, Long>(100);
		DATE_MAPS[1][1] = new HashMap<Long, Long>(100);
		DATE_MAPS[2][0] = new HashMap<Long, Long>(100);
		DATE_MAPS[2][1] = new HashMap<Long, Long>(100);
		DATE_MAPS[3][0] = new HashMap<Long, Long>(100);
		DATE_MAPS[3][1] = new HashMap<Long, Long>(100);
		initMaps1();
		initMaps2();
		initMaps3();
		initMaps4();
	}
}
