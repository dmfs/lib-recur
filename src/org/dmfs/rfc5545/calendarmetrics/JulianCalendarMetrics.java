/*
 * Copyright (C) 2014 Marten Gajda <marten@dmfs.org>
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


/**
 * Provides a set of methods that provide information about the Julian Calendar.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
/*
 * Even though Gregorian is an improved version of the Julian calendar, we handle it the other way round here.
 */
public class JulianCalendarMetrics extends GregorianCalendarMetrics
{
	public final static CalendarMetricsFactory FACTORY = new CalendarMetricsFactory()
	{

		@Override
		public CalendarMetrics getCalendarMetrics(int weekStart)
		{
			return new JulianCalendarMetrics(weekStart, 4);
		}


		public String toString()
		{
			return CALENDAR_SCALE_ALIAS;
		};
	};

	public final static String CALENDAR_SCALE_ALIAS = "JULIAN";
	public final static String CALENDAR_SCALE_NAME = "JULIUS";

	private GregorianCalendarMetrics mGregorianCalendarMetrics;


	/**
	 * Create calendar metrics for a Julian calendar with the given week numbering.
	 * 
	 * @param weekStart
	 *            The first day of the week.
	 * @param minDaysInFirstWeek
	 *            The minimal number of days in the first week.
	 */
	public JulianCalendarMetrics(int weekStart, int minDaysInFirstWeek)
	{
		super(weekStart, minDaysInFirstWeek);
	}


	@Override
	public int getDayOfWeek(int year, int yearDay)
	{
		// 0001-01-01 was a Saturday and the first of each year goes one weekday forward, two after a leap year
		int y = year - 1;
		return (yearDay + 5 + y + (y >> 2)) % 7;
	}


	/**
	 * Determine if the given year is a leap year.
	 * 
	 * @param year
	 *            The year.
	 * @return <code>true</code> if the year is a leap year, <code>false</code> otherwise.
	 */
	@Override
	boolean isLeapYear(int year)
	{
		return (year & 0x3) == 0;
	}


	@Override
	public int getWeekDayOfFirstYearDay(int year)
	{
		// 0001-01-01 was a Saturday and the first of each year goes one weekday forward, two after a leap year
		int y = year - 1;
		return (6 + y + (y >> 2)) % 7;
	}


	@Override
	public long toMillis(TimeZone timeZone, int year, int packedMonth, int dayOfMonth, int hours, int minutes, int seconds, int millis)
	{
		// convert to Gregorian calendar and use that to convert the date

		if (packedMonth > 1)
		{
			int yh = year / 100;
			dayOfMonth += yh - (yh >> 2) - 2;
		}
		else
		{
			int yh = (year - 1) / 100;
			dayOfMonth += yh - (yh >> 2) - 2;
			if (packedMonth == 1 && dayOfMonth > 28 && year % 100 == 0 && year % 400 != 0)
			{
				dayOfMonth++;
			}
		}

		GregorianCalendarMetrics gregorianMetrics = mGregorianCalendarMetrics;
		if (gregorianMetrics == null)
		{
			gregorianMetrics = mGregorianCalendarMetrics = new GregorianCalendarMetrics(weekStart, minDaysInFirstWeek);
		}

		// adjust day of month over- or under-run

		int daysPerMonth = getDaysPerPackedMonth(year, packedMonth);
		if (dayOfMonth > daysPerMonth)
		{
			dayOfMonth -= daysPerMonth;
			if (++packedMonth > 11)
			{
				packedMonth = 0;
				++year;
			}
		}
		else if (dayOfMonth < 1)
		{
			--packedMonth;
			if (packedMonth < 0)
			{
				--year;
				packedMonth = 11;
			}
			dayOfMonth += gregorianMetrics.getDaysPerPackedMonth(year, packedMonth);
		}

		return gregorianMetrics.toMillis(timeZone, year, packedMonth, dayOfMonth, hours, minutes, seconds, millis);
	}


	long getTimeStamp(int year, int yearDay, int hours, int minutes, int seconds, int millis)
	{
		long result = (year - 1970) * 365;
		result = (result + yearDay - 1 + numLeapDaysSince1970(year) + 13 /* 1970-01-01 refers to the Gregorian calendar, which is 13 days ahead on that date */) * 24;
		result = (result + hours) * 60;
		result = (result + minutes) * 60;
		result = (result + seconds) * 1000 + millis;

		return result;
	}


	int numLeapDaysSince1970(int year)
	{
		int prevYear = year - 1; // don't include year itself
		int leapYears = prevYear >> 2; // leap years since year 0
		return leapYears - 492; // the number of leap days is just the number of leap years
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
		final int daysSince1 = (int) (localTime / (24 * 3600000L) + 365 * 1969 + 492 - 13 /*
																						 * account for the difference between Julian and Gregorian calendar on
																						 * 1970-01-01
																						 */);

		// the number of 4 year cycles and the remaining days
		final int c4 = daysSince1 / (4 * 365 + 1);
		final int c4Remainder = (int) (daysSince1 - c4 * (4 * 365 + 1));

		// the number of full years and the remaining days of the last year
		final int c1 = Math.min(c4Remainder / 365, 3 /* there are at most 3 full year cycles in <4 years */);
		final int c1Remainder = c4Remainder - 365 * c1 + 1 /* the first yearday is 1 not 0 */;

		int year = (c4 << 2) + c1 + 1;

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
