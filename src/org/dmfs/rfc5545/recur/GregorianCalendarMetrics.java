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

import org.dmfs.rfc5545.recur.RecurrenceRule.Weekday;


/**
 * Provides a set of methods that provide information about the Gregorian Calendar.
 * <p>
 * TODO: Some of these methods can use some optimizations.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class GregorianCalendarMetrics extends CalendarMetrics
{
	/**
	 * The number of days in a specific month. This is for non-leap years. For leap years add <code>1</code> to <code>DAYS_PER_MONTH[1]</code>.
	 */
	private final static int[] DAYS_PER_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * The number of days preceding a specific month in a year. This is for non-leap years. For leap years add <code>1</code> to <code>DAYS_PER_MONTH[i]</code>
	 * for all <code>i > 1<code>.
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
	public int getDaysPerMonth(int year, int month)
	{
		if (month == 1 && isLeapYear(year))
		{
			return DAYS_PER_MONTH[month] + 1;
		}
		else
		{
			return DAYS_PER_MONTH[month];
		}
	}


	@Override
	public int getYearDaysForMonth(int year, int month)
	{
		if (month > 1 && isLeapYear(year))
		{
			return YEARDAYS_PER_MONTH[month] + 1;
		}
		else
		{
			return YEARDAYS_PER_MONTH[month];
		}
	}


	@Override
	public int getMonthsPerYear(int year)
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
	public int getWeekOfYear(int year, int month, int dayOfMonth)
	{
		return getWeekOfYear(year, getYearDaysForMonth(year, month) + dayOfMonth);
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
	public int getDayOfWeek(int year, int yearDay)
	{
		/* using Gauss's algorithm, see http://en.wikipedia.org/wiki/Calculating_the_day_of_the_week#Gauss.27s_algorithm */
		int y = year - 1;
		return (yearDay + 5 * (y & 3) + 4 * (y % 100) + 6 * (y % 400)) % 7;
	}


	@Override
	public int getDayOfWeek(int year, int month, int dayOfMonth)
	{
		return getDayOfWeek(year, getDayOfYear(year, month, dayOfMonth));
	}


	@Override
	public int getDayOfYear(int year, int month, int dayOfMonth)
	{
		return getYearDaysForMonth(year, month) + dayOfMonth;
	}


	/**
	 * Determine if the given year is a leap year.
	 * 
	 * @param year
	 *            The year.
	 * @return <code>true</code> if the year is a leap year, <code>false</code> otherwise.
	 */
	private boolean isLeapYear(int year)
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
	public int getMonthOfYearDay(int year, int yearDay)
	{
		int month = (yearDay >> 5) + 1; // get a good estimation for the first month to check
		if (month < 12 && getYearDaysForMonth(year, month) < yearDay)
		{
			month++;
		}
		return month - 1;
	}


	@Override
	public int getDayOfMonthOfYearDay(int year, int yearDay)
	{
		return yearDay - getYearDaysForMonth(year, getMonthOfYearDay(year, yearDay));
	}


	@Override
	public int getMonthAndDayOfYearDay(int year, int yearDay)
	{
		int month = (yearDay >> 5) + 1; // get a good estimation for the first month to check
		if (month < 12 && getYearDaysForMonth(year, month) < yearDay)
		{
			++month;

		}
		--month;
		return monthAndDay(month, yearDay - getYearDaysForMonth(year, month));
	}

}
