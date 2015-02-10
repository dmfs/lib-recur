package org.dmfs.rfc5545.calendarmetrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.TimeZone;

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.calendarmetrics.IslamicCalendarMetrics.LeapYearPattern;
import org.dmfs.rfc5545.recur.RecurrenceRule.Weekday;
import org.junit.Test;


public class IslamicCalendarMetricsTest
{

	private final static CalendarMetrics GREGORIAN_CALENDAR = GregorianCalendarMetrics.FACTORY.getCalendarMetrics(0);


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
	public void testToMillisTimeZoneIntIntIntIntIntIntInt()
	{
		fail("Noch nicht implementiert");
	}


	/**
	 * Compares a number of instances created with <a href="http://www.staff.science.uu.nl/~gent0113/islam/islam_tabcal.htm">Islamic-Western Calendar
	 * Converter</a>
	 */
	@Test
	public void testToInstance()
	{
		TimeZone tz = TimeZone.getTimeZone("UTC");
		CalendarMetrics islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.I, true);
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1389, 9, 22, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1420, 8, 24, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 9, 5, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 10, 7, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1422, 0, 7, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1423, 9, 27, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1426, 0, 19, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1435, 1, 28, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1437, 2, 20, 0, 0, 0, 0));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.I, false);
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1389, 9, 23, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1420, 8, 25, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 9, 6, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 10, 8, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1422, 0, 8, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1423, 9, 28, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1426, 0, 20, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1435, 1, 29, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1437, 2, 21, 0, 0, 0, 0));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.II, true);
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1389, 9, 22, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1420, 8, 24, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 9, 5, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 10, 7, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1422, 0, 7, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1423, 9, 27, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1426, 0, 20, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1435, 1, 28, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1437, 2, 20, 0, 0, 0, 0));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.II, false);
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1389, 9, 23, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1420, 8, 25, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 9, 6, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 10, 8, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1422, 0, 8, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1423, 9, 28, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1426, 0, 21, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1435, 1, 29, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1437, 2, 21, 0, 0, 0, 0));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.III, true);
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1389, 9, 22, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1420, 8, 24, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 9, 5, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 10, 7, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1422, 0, 7, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1423, 9, 27, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1426, 0, 20, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1435, 1, 28, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1437, 2, 21, 0, 0, 0, 0));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.III, false);
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1389, 9, 23, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1420, 8, 25, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 9, 6, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 10, 8, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1422, 0, 8, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1423, 9, 28, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1426, 0, 21, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1435, 1, 29, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1437, 2, 22, 0, 0, 0, 0));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.IV, true);
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1389, 9, 22, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1420, 8, 24, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 9, 6, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 10, 8, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1422, 0, 7, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1423, 9, 27, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1426, 0, 20, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1435, 1, 28, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1437, 2, 21, 0, 0, 0, 0));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.IV, false);
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1389, 9, 23, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1420, 8, 25, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 9, 7, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1421, 10, 9, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1422, 0, 8, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1423, 9, 28, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1426, 0, 21, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1435, 1, 29, 0, 0, 0, 0));
		assertEquals(GREGORIAN_CALENDAR.toMillis(tz, 2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toMillis(tz, 1437, 2, 22, 0, 0, 0, 0));
	}


	@Test
	public void testIslamicToGregorian()
	{
		IslamicCalendarMetrics islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.I, true);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 22, 0, 0, 0, 0)));
		assertEquals(Instance.make(2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1420, 8, 24, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 9, 5, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 10, 7, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1422, 0, 7, 0, 0, 0, 0)));
		assertEquals(Instance.make(2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1423, 9, 27, 0, 0, 0, 0)));
		assertEquals(Instance.make(2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1426, 0, 19, 0, 0, 0, 0)));
		assertEquals(Instance.make(2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1435, 1, 28, 0, 0, 0, 0)));
		assertEquals(Instance.make(2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1437, 2, 20, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.I, false);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 23, 0, 0, 0, 0)));
		assertEquals(Instance.make(2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1420, 8, 25, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 9, 6, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 10, 8, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1422, 0, 8, 0, 0, 0, 0)));
		assertEquals(Instance.make(2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1423, 9, 28, 0, 0, 0, 0)));
		assertEquals(Instance.make(2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1426, 0, 20, 0, 0, 0, 0)));
		assertEquals(Instance.make(2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1435, 1, 29, 0, 0, 0, 0)));
		assertEquals(Instance.make(2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1437, 2, 21, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.II, true);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 22, 0, 0, 0, 0)));
		assertEquals(Instance.make(2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1420, 8, 24, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 9, 5, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 10, 7, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1422, 0, 7, 0, 0, 0, 0)));
		assertEquals(Instance.make(2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1423, 9, 27, 0, 0, 0, 0)));
		assertEquals(Instance.make(2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1426, 0, 20, 0, 0, 0, 0)));
		assertEquals(Instance.make(2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1435, 1, 28, 0, 0, 0, 0)));
		assertEquals(Instance.make(2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1437, 2, 20, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.II, false);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 23, 0, 0, 0, 0)));
		assertEquals(Instance.make(2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1420, 8, 25, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 9, 6, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 10, 8, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1422, 0, 8, 0, 0, 0, 0)));
		assertEquals(Instance.make(2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1423, 9, 28, 0, 0, 0, 0)));
		assertEquals(Instance.make(2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1426, 0, 21, 0, 0, 0, 0)));
		assertEquals(Instance.make(2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1435, 1, 29, 0, 0, 0, 0)));
		assertEquals(Instance.make(2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1437, 2, 21, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.III, true);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 22, 0, 0, 0, 0)));
		assertEquals(Instance.make(2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1420, 8, 24, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 9, 5, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 10, 7, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1422, 0, 7, 0, 0, 0, 0)));
		assertEquals(Instance.make(2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1423, 9, 27, 0, 0, 0, 0)));
		assertEquals(Instance.make(2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1426, 0, 20, 0, 0, 0, 0)));
		assertEquals(Instance.make(2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1435, 1, 28, 0, 0, 0, 0)));
		assertEquals(Instance.make(2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1437, 2, 21, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.III, false);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 23, 0, 0, 0, 0)));
		assertEquals(Instance.make(2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1420, 8, 25, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 9, 6, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 10, 8, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1422, 0, 8, 0, 0, 0, 0)));
		assertEquals(Instance.make(2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1423, 9, 28, 0, 0, 0, 0)));
		assertEquals(Instance.make(2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1426, 0, 21, 0, 0, 0, 0)));
		assertEquals(Instance.make(2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1435, 1, 29, 0, 0, 0, 0)));
		assertEquals(Instance.make(2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1437, 2, 22, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.IV, true);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 22, 0, 0, 0, 0)));
		assertEquals(Instance.make(2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1420, 8, 24, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 9, 6, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 10, 8, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1422, 0, 7, 0, 0, 0, 0)));
		assertEquals(Instance.make(2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1423, 9, 27, 0, 0, 0, 0)));
		assertEquals(Instance.make(2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1426, 0, 20, 0, 0, 0, 0)));
		assertEquals(Instance.make(2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1435, 1, 28, 0, 0, 0, 0)));
		assertEquals(Instance.make(2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1437, 2, 21, 0, 0, 0, 0)));

		islamicCalendar = new IslamicCalendarMetrics(0, 0, LeapYearPattern.IV, false);
		assertEquals(Instance.make(1970, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1389, 9, 23, 0, 0, 0, 0)));
		assertEquals(Instance.make(2000, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1420, 8, 25, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 9, 7, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 1, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1421, 10, 9, 0, 0, 0, 0)));
		assertEquals(Instance.make(2001, 3, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1422, 0, 8, 0, 0, 0, 0)));
		assertEquals(Instance.make(2003, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1423, 9, 28, 0, 0, 0, 0)));
		assertEquals(Instance.make(2005, 2, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1426, 0, 21, 0, 0, 0, 0)));
		assertEquals(Instance.make(2014, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1435, 1, 29, 0, 0, 0, 0)));
		assertEquals(Instance.make(2016, 0, 1, 0, 0, 0, 0), islamicCalendar.toGregorian(Instance.make(1437, 2, 22, 0, 0, 0, 0)));
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

}
