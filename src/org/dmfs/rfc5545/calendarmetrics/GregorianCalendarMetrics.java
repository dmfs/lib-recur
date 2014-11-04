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
 * Provides a set of methods that provide information about the Gregorian Calendar.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class GregorianCalendarMetrics extends NoLeapMonthCalendarMetrics
{
	public final static CalendarMetricsFactory FACTORY = new CalendarMetricsFactory()
	{

		@Override
		public CalendarMetrics getCalendarMetrics(int weekStart)
		{
			return new GregorianCalendarMetrics(weekStart, 4);
		}


		public String toString()
		{
			return CALENDAR_SCALE_ALIAS;
		};
	};

	public final static String CALENDAR_SCALE_ALIAS = "GREGORIAN";
	public final static String CALENDAR_SCALE_NAME = "GREGORY";

	/**
	 * The number of days in a specific month. This is for non-leap years. For leap years add <code>1</code> to <code>DAYS_PER_MONTH[1]</code>.
	 */
	private final static int[] DAYS_PER_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * The number of days preceding a specific month in a year. This is for non-leap years. For leap years add <code>1</code> to
	 * <code>YEARDAYS_PER_MONTH[i]</code> for all <code>i > 1<code>.
	 */
	private final static int[] YEARDAYS_PER_MONTH = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 };

	/**
	 * An array of {@link Weekday}s. This is handy to get a {@link Weekday} instance for a given weekday number.
	 */
	public final static Weekday[] WEEKDAYS = Weekday.values();


	/**
	 * Create calendar metrics for a Gregorian calendar with the given week numbering.
	 * 
	 * @param weekStart
	 *            The first day of the week.
	 * @param minDaysInFirstWeek
	 *            The minimal number of days in the first week.
	 */
	public GregorianCalendarMetrics(int weekStart, int minDaysInFirstWeek)
	{
		super(weekStart, minDaysInFirstWeek);
	}


	@Override
	public int getMaxMonthDayNum()
	{
		return 31;
	}


	@Override
	public int getMaxYearDayNum()
	{
		return 366;
	}


	@Override
	public int getMaxWeekNoNum()
	{
		return 53;
	}


	@Override
	public int getDaysPerPackedMonth(int year, int packedMonth)
	{
		if (packedMonth == 1 && isLeapYear(year))
		{
			return DAYS_PER_MONTH[packedMonth] + 1;
		}
		else
		{
			return DAYS_PER_MONTH[packedMonth];
		}
	}


	@Override
	public int getYearDaysForPackedMonth(int year, int packedMonth)
	{
		if (packedMonth > 1 && isLeapYear(year))
		{
			return YEARDAYS_PER_MONTH[packedMonth] + 1;
		}
		else
		{
			return YEARDAYS_PER_MONTH[packedMonth];
		}
	}


	@Override
	public int getMonthsPerYear()
	{
		return 12;
	}


	@Override
	public int getDaysPerYear(int year)
	{
		return isLeapYear(year) ? 366 : 365;
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
		return (year & 0x3) == 0 && year % 100 != 0 || year % 400 == 0;
	}


	@Override
	public int getWeekDayOfFirstYearDay(int year)
	{
		/* using Gauss's algorithm, see http://en.wikipedia.org/wiki/Calculating_the_day_of_the_week#Gauss.27s_algorithm */
		int y = year - 1;
		return (1 + 5 * (y & 3) + 4 * (y % 100) + 6 * (y % 400)) % 7;
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

		int month = (yearDay >> 5) + 1; // get a good estimation for the first month to check
		if (month < 12 && getYearDaysForPackedMonth(year, month) < yearDay)
		{
			month++;
		}
		return month - 1;
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

		int month = (yearDay >> 5) + 1; // get a good estimation for the first month to check
		if (month < 12 && getYearDaysForPackedMonth(year, month) < yearDay)
		{
			++month;

		}
		--month;
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
		int timeInMillis = ((hours * 60 + minutes) * 60 + seconds) * 1000 + millis;
		int dayOfWeek = getDayOfWeek(year, packedMonth, dayOfMonth);

		int dstOffset = timeZone.getOffset(1 /* GregorianCalendar.AD */, year, packedMonth, dayOfMonth, dayOfWeek + 1/* Calendar uses 1-7 */, timeInMillis)
			- timeZone.getRawOffset();

		int yearDay = getDayOfYear(year, packedMonth, dayOfMonth);
		long localTime = getTimeStamp(year, yearDay, hours, minutes, seconds, millis);

		timeInMillis -= dstOffset;
		if (timeInMillis < 0)
		{
			timeInMillis += 24 * 60 * 60 * 1000;
			if (--dayOfMonth == 0)
			{
				if (--packedMonth < 0)
				{
					--year;
					packedMonth = getMonthsPerYear(year) - 1;
				}
				dayOfMonth = getDaysPerPackedMonth(year, packedMonth);
				dayOfWeek = (dayOfWeek + 6) % 7;
			}
		}
		else if (timeInMillis >= 24 * 60 * 60 * 1000)
		{
			timeInMillis -= 24 * 60 * 60 * 1000;
			if (++dayOfMonth > getDaysPerPackedMonth(year, packedMonth))
			{
				if (++packedMonth >= getMonthsPerYear(year))
				{
					++year;
					packedMonth = 0;
				}
				dayOfMonth = 1;
				dayOfWeek = (dayOfWeek + 1) % 7;
			}
		}

		int offset2 = timeZone.getOffset(1 /* GregorianCalendar.AD */, year, packedMonth, dayOfMonth, dayOfWeek + 1, timeInMillis);

		return localTime - offset2;
	}


	long getTimeStamp(int year, int yearDay, int hours, int minutes, int seconds, int millis)
	{
		long result = (year - 1970) * 365;
		result = (result + yearDay - 1 + numLeapDaysSince1970(year)) * 24;
		result = (result + hours) * 60;
		result = (result + minutes) * 60;
		result = (result + seconds) * 1000 + millis;

		return result;
	}


	int numLeapDaysSince1970(int year)
	{
		int prevYear = year - 1; // don't include year itself
		int leapYears = prevYear >> 2; // leap years since year 0
		int nonLeapYears = prevYear / 100; // non leap years that are divisible by 4 since year 0
		int yetLeapYears = nonLeapYears >> 2; // leap years that are divisible by 400 since year 0
		return (leapYears - 492) - (nonLeapYears - 19) + (yetLeapYears - 4); // the number of leap days is just the number of leap years
	}


	@Override
	public long toInstance(long timestamp, TimeZone timeZone)
	{
		long localTime = timestamp;
		if (timeZone != null)
		{
			localTime += timeZone.getOffset(timestamp);
		}

		// get the time of the day in milliseconds
		int time = (int) (localTime % (24L * 3600L * 1000L));

		// remove the time from the date
		localTime -= time;

		// adjust negative dates
		if (time < 0)
		{
			time += 24 * 3600 * 1000;
			localTime -= 24 * 3600 * 1000;
		}

		// the number of days that have passed since 0001-01-01
		final int daysSince1 = (int) (localTime / (24 * 3600 * 1000L) + 365 * 1969 + 477);

		// the number of full 400 year cycles and the remaining days
		final int c400 = daysSince1 / (400 * 365 + 97);
		final int c400Remainder = daysSince1 % (400 * 365 + 97);

		// the number of full 100 year cycles and the remainder
		final int c100 = Math.min((c400Remainder / (100 * 365 + 24)), 3 /* there are at most 3 full 100 year cycles in <400 years */);
		final int c100Remainder = c400Remainder - c100 * (100 * 365 + 24);

		// the number of 4 year cycles and the remaining days
		final int c4 = Math.min((int) (c100Remainder / (4 * 365 + 1)), 24 /* there are at most 24 full 4 year cycles in <100 years */);
		final int c4Remainder = (int) (c100Remainder - c4 * (4 * 365 + 1));

		// the number of full years and the remaining days of the last year
		final int c1 = Math.min(c4Remainder / 365, 3 /* there are at most 3 full year cycles in <4 years */);
		final int c1Remainder = c4Remainder - 365 * c1 + 1 /* the first yearday is 1 not 0 */;

		int year = ((c400 << 2) + c100) * 100 + (c4 << 2) + c1 + 1;

		final int monthAndDay = getMonthAndDayOfYearDay(year, c1Remainder);

		final int minutes = time / 60000;

		return Instance.make(year, packedMonth(monthAndDay), dayOfMonth(monthAndDay), minutes / 60, minutes % 60, time / 1000 % 60);
	}


	@Override
	public boolean isLeapDay(int packedMonth, int day)
	{
		return day == 29 && packedMonth == 1;
	}

}
