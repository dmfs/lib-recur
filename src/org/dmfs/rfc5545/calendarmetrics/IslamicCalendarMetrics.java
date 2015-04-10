/*
 * Copyright (C) 2013, 2014 Marten Gajda <marten@dmfs.org>
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

package org.dmfs.rfc5545.calendarmetrics;

import java.util.TimeZone;

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.recur.RecurrenceRule.Weekday;


/**
 * Provides a set of methods that provide information about the Islamic Calendar.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class IslamicCalendarMetrics extends NoLeapMonthCalendarMetrics
{
	public enum LeapYearPattern
	{
		I, II, III, IV;
	}

	public final static class IslamicCalendarMetricsFactory extends CalendarMetricsFactory
	{
		private final String mName;
		private final LeapYearPattern mPattern;
		private final boolean mCivil;


		public IslamicCalendarMetricsFactory(String name, LeapYearPattern pattern, boolean civil)
		{
			mName = name;
			mPattern = pattern;
			mCivil = civil;
		}


		@Override
		public CalendarMetrics getCalendarMetrics(int weekStart)
		{
			return new IslamicCalendarMetrics(weekStart, 4, mPattern, mCivil);
		}


		public String toString()
		{
			return mName;
		};
	};

	public final static long DAYS_PER_CYCLE = 30 * 354 + 11;

	public final static long MILLIS_PER_DAY = 24 * 3600 * 1000;

	public final static long MILLIS_PER_CYCLE = DAYS_PER_CYCLE * MILLIS_PER_DAY;

	/**
	 * This is the number of milliseconds between {I}0001-01-01 (civil scale) and {I}1389-10-22 (civil scale) (i.e. {G}1970-01-01). Since this is the 9th year
	 * of the 30 year cycle, the number of leap years in the cycle is the same for all patterns.
	 */
	public final static long MILLIS_TO_1389_10_22C = ((1389 - 1) / 30 * DAYS_PER_CYCLE + (1389 - 1) % 30 * 354 + 3 /* leap days */+ 5 /* months à 30 days */
		* 30 + 4 /* months à 29 days */* 29 + 21 /* days */) * MILLIS_PER_DAY;

	public final static String CALENDAR_SCALE_TLBA = "ISLAMIC-TLBA";
	public final static String CALENDAR_SCALE_CIVIL = "ISLAMIC_CIVIL";

	/**
	 * An array of (packed) patterns based on different leap year rules.
	 */
	public final static int[] LEAP_YEAR_PATTERNS = { 1 << 2 | 1 << 5 | 1 << 7 | 1 << 10 | 1 << 13 | 1 << 15 | 1 << 18 | 1 << 21 | 1 << 24 | 1 << 26 | 1 << 29,
		1 << 2 | 1 << 5 | 1 << 7 | 1 << 10 | 1 << 13 | 1 << 16 | 1 << 18 | 1 << 21 | 1 << 24 | 1 << 26 | 1 << 29,
		1 << 2 | 1 << 5 | 1 << 8 | 1 << 10 | 1 << 13 | 1 << 16 | 1 << 19 | 1 << 21 | 1 << 24 | 1 << 27 | 1 << 29,
		1 << 2 | 1 << 5 | 1 << 8 | 1 << 11 | 1 << 13 | 1 << 16 | 1 << 19 | 1 << 21 | 1 << 24 | 1 << 27 | 1 << 30 };

	/**
	 * One array per {@link LeapYearPattern} that contains the number of leap days that occurred up to a particular year in the 30 year cycle.
	 */
	public final static byte[][] LEAP_YEAR_COUNT = { { 0, 0, 1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 11, 11 },
		{ 0, 0, 1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 11, 11 },
		{ 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 9, 10, 10, 11, 11 },
		{ 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 9, 10, 10, 10, 11 } };

	/**
	 * An array of {@link Weekday}s. This is handy to get a {@link Weekday} instance for a given weekday number.
	 */
	public final static Weekday[] WEEKDAYS = Weekday.values();

	private final int mLeapYearPatternIndex;

	private final static CalendarMetrics GREGORIAN_METRICS = GregorianCalendarMetrics.FACTORY.getCalendarMetrics(0);

	private final boolean mCivil;


	/**
	 * Create calendar metrics for an Islamic calendar with the given week numbering.
	 * 
	 * @param weekStart
	 *            The first day of the week.
	 * @param minDaysInFirstWeek
	 *            The minimal number of days in the first week.
	 */
	public IslamicCalendarMetrics(int weekStart, int minDaysInFirstWeek, LeapYearPattern leapYearPatternIndex, boolean civil)
	{
		super(weekStart, minDaysInFirstWeek);
		mLeapYearPatternIndex = leapYearPatternIndex.ordinal();
		mCivil = civil;
	}


	@Override
	public int getMaxMonthDayNum()
	{
		return 30;
	}


	@Override
	public int getMaxYearDayNum()
	{
		return 355;
	}


	@Override
	public int getMaxWeekNoNum()
	{
		return 52;
	}


	@Override
	public int getDaysPerPackedMonth(int year, int packedMonth)
	{
		if (packedMonth == 11 && isLeapYear(year))
		{
			// in leap years the 12th month has 30 days
			return 30;
		}
		else
		{
			// even months have 30 days, odd months have 29 days
			return 30 - (packedMonth & 1);
		}
	}


	@Override
	public int getYearDaysForPackedMonth(int year, int packedMonth)
	{
		return packedMonth * 29 + ((packedMonth + 1) >>> 1) /* add one day per two months */;
	}


	@Override
	public int getMonthsPerYear()
	{
		return 12;
	}


	@Override
	public int getDaysPerYear(int year)
	{
		return isLeapYear(year) ? 355 : 354;
	}


	@Override
	public int getWeeksPerYear(int year)
	{
		int yd1st = getYearDayOfFirstWeekStart(year);
		int yearDays = getDaysPerYear(year) - yd1st + 1;
		int fullweeks = yearDays / 7;
		int remainingDays = yearDays % 7;

		return 7 - remainingDays >= minDaysInFirstWeek ? fullweeks : fullweeks + 1;
	}


	@Override
	public int getWeekOfYear(int year, int packedMonth, int dayOfMonth)
	{
		return getWeekOfYear(year, getYearDaysForPackedMonth(year, packedMonth) + dayOfMonth);
	}


	@Override
	public int getWeekOfYear(int year, int yearDay)
	{
		int yd1st = getYearDayOfFirstWeekStart(year);

		if (yearDay < yd1st)
		{
			// day must be in the last week of the previous year
			int weeksInYear = getWeeksPerYear(year - 1);
			return weeksInYear;
		}
		else
		{
			int week = (yearDay - yd1st) / 7 + 1;
			int weeksInYear = getWeeksPerYear(year);

			return week > weeksInYear ? week - weeksInYear : week;
		}
	}


	@Override
	public int getDayOfYear(int year, int packedMonth, int dayOfMonth)
	{
		return getYearDaysForPackedMonth(year, packedMonth) + dayOfMonth;
	}


	/**
	 * Determine if the given year is a leap year.
	 * 
	 * @param year
	 *            The year.
	 * @return <code>true</code> if the year is a leap year, <code>false</code> otherwise.
	 */
	boolean isLeapYear(int year)
	{
		return (LEAP_YEAR_PATTERNS[mLeapYearPatternIndex] & (1 << ((year - 1) % 30 + 1))) != 0;
	}


	@Override
	public int getWeekDayOfFirstYearDay(int year)
	{
		int y = year - 1;
		int yc = y % 30;
		// 0001-01-01 was a Friday
		return (5 /* for Friday */+ 5 * (y / 30) /* 5 days per 30 year cycle */+ 4 * yc /* 4 days per year in the 30 year cycle */+ LEAP_YEAR_COUNT[mLeapYearPatternIndex][yc]) % 7;
	}


	@Override
	public int getYearDayOfFirstWeekStart(int year)
	{
		int jan1stWeekDay = getWeekDayOfFirstYearDay(year);

		int diff = weekStart - jan1stWeekDay;

		int yd = 1 + diff;

		return yd > minDaysInFirstWeek ? yd - 7 : yd < minDaysInFirstWeek - 6 ? yd + 7 : yd;
	}


	@Override
	public int getPackedMonthOfYearDay(int year, int yearDay)
	{
		int yearDays;

		while (yearDay < 1)
		{
			--year;
			yearDay += getDaysPerYear(year);
		}

		while (yearDay > (yearDays = getDaysPerYear(year)))
		{
			++year;
			yearDay -= yearDays;
		}

		return yearDay == 355 ? 11 : ((yearDay - 1) * 2) / 59;
	}


	@Override
	public int getDayOfMonthOfYearDay(int year, int yearDay)
	{
		return yearDay - getYearDaysForPackedMonth(year, getPackedMonthOfYearDay(year, yearDay));
	}


	@Override
	public int getMonthAndDayOfYearDay(int year, int yearDay)
	{
		int yearDays;

		while (yearDay < 1)
		{
			--year;
			yearDay += getDaysPerYear(year);
		}

		while (yearDay > (yearDays = getDaysPerYear(year)))
		{
			++year;
			yearDay -= yearDays;
		}

		int month = yearDay == 355 ? 11 : ((yearDay - 1) * 2) / 59;
		return monthAndDay(month, yearDay - getYearDaysForPackedMonth(year, month));
	}


	@Override
	public int getYearDayOfIsoYear(int year, int weekOfYear, int dayOfWeek)
	{
		return weekOfYear * 7 - 7 + (dayOfWeek - weekStart + 7) % 7 + getYearDayOfFirstWeekStart(year);
	}


	@Override
	public int getYearDayOfWeekStart(int year, int week)
	{
		return getYearDayOfFirstWeekStart(year) + (week - 1) * 7;
	}


	@Override
	public long toMillis(TimeZone timeZone, int year, int packedMonth, int dayOfMonth, int hours, int minutes, int seconds, int millis)
	{
		/*
		 * We can't to this conversion in Islamic scale, because the TimeZone class works with Gregorian calendars only. So convert the Islamic date to
		 * Gregorian first.
		 */

		long gregorianDate = toGregorian(Instance.make(year, packedMonth, dayOfMonth, 0, 0, 0));

		year = Instance.year(gregorianDate);
		packedMonth = Instance.month(gregorianDate);
		dayOfMonth = Instance.dayOfMonth(gregorianDate);

		return GREGORIAN_METRICS.toMillis(timeZone, year, packedMonth, dayOfMonth, hours, minutes, seconds, millis);
	}


	@Override
	public long toInstance(long timestamp, TimeZone timeZone)
	{
		long localTime = timestamp;
		if (timeZone != null)
		{
			localTime += timeZone.getOffset(timestamp);
		}

		// rebase to 0001-01-01
		localTime += mCivil ? MILLIS_TO_1389_10_22C : MILLIS_TO_1389_10_22C + MILLIS_PER_DAY;

		// split the time of the day in milliseconds
		int time = (int) (localTime % (24L * 3600L * 1000L));

		// convert to days
		localTime /= 24 * 3600L * 1000L;

		// adjust negative dates
		if (time < 0)
		{
			time += 24 * 3600 * 1000;
			--localTime;
		}

		// the number of full 30 year cycles since 0001-01-01
		final int cycles = (int) (localTime / DAYS_PER_CYCLE);

		// the days in the current cycle
		final long daysInCycle = localTime % DAYS_PER_CYCLE;

		// get an estimate of the year, this is 0-based and might be off by 1
		int year = (int) (daysInCycle / 355);

		int yearDay = (int) (daysInCycle - (year * 354 + LEAP_YEAR_COUNT[mLeapYearPatternIndex][year])) + 1;

		// switch year to 1-based
		++year;

		// adjust the year
		if (yearDay > 355 || yearDay == 355 && !isLeapYear(year))
		{
			// day is in next year
			yearDay -= getDaysPerYear(year);
			++year;
		}

		final int minutes = time / 60000;

		int monthAndDay = getMonthAndDayOfYearDay(year, yearDay);

		return Instance.make(30 * cycles + year, packedMonth(monthAndDay), dayOfMonth(monthAndDay), minutes / 60, minutes % 60, time / 1000 % 60);
	}


	@Override
	public boolean isLeapDay(int packedMonth, int day)
	{
		return day == 30 && packedMonth == 11;
	}


	/**
	 * Convert the given Islamic instance to Gregorian calendar.
	 * 
	 * @param islamicInstance
	 *            An {@link Instance} value in islamic calendar scale.
	 * @return The instance in Gregorian calendar scale.
	 */
	public long toGregorian(long islamicInstance)
	{
		int iYear = Instance.year(islamicInstance);
		int iPackedMonth = Instance.month(islamicInstance);
		int iDayOfMonth = Instance.dayOfMonth(islamicInstance);

		int yearInCycle = (iYear - 1) % 30;

		long iTimeStamp = ((((iYear - 1) / 30) * DAYS_PER_CYCLE + (354 * yearInCycle + LEAP_YEAR_COUNT[mLeapYearPatternIndex][yearInCycle]))
			+ getDayOfYear(iYear, iPackedMonth, iDayOfMonth) - 1) * 24 * 3600 * 1000L;

		long localTime = mCivil ? iTimeStamp - MILLIS_TO_1389_10_22C : iTimeStamp - MILLIS_TO_1389_10_22C - MILLIS_PER_DAY;

		// convert the "floating" timestamp to a Gregorian calendar instance
		return GREGORIAN_METRICS.toInstance(localTime, null);
	}

}
