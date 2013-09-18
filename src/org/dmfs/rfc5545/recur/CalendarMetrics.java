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

/**
 * Provides a set of methods that return all kinds of information about a calendar and do some calendar calculations.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class CalendarMetrics
{

	/**
	 * The first day of the week.
	 */
	public final int weekStart;

	/**
	 * The minimal number of days in the first week.
	 */
	public final int minDaysInFirstWeek;


	public CalendarMetrics(int weekStart, int minDaysInFirstWeek)
	{
		this.weekStart = weekStart;
		this.minDaysInFirstWeek = minDaysInFirstWeek;
	}


	/**
	 * Get the number of days in a specific month.
	 * 
	 * @param year
	 *            The year.
	 * @param month
	 *            The month (0-based).
	 * @return The number of days in that month.
	 */
	public abstract int getDaysPerMonth(int year, int month);


	/**
	 * Determines month for a given day of year.
	 * 
	 * @param year
	 *            The year.
	 * @param yearDay
	 *            The year day.
	 * @return the month (0-based).
	 */
	public abstract int getMonthOfYearDay(int year, int yearDay);


	/**
	 * Determines day of month for a given day of year.
	 * 
	 * @param year
	 *            The year.
	 * @param yearDay
	 *            The year day.
	 * @return the day.
	 */
	public abstract int getDayOfMonthOfYearDay(int year, int yearDay);


	/**
	 * Determines month and day for a given day of year. The result contains both, month and day in a single integer. To split the values use
	 * {@link #month(int)} and {@link #dayOfMonth(int)} like in:
	 * 
	 * <pre>
	 * int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year, yearDay);
	 * int month = CalendarMetrics.month(monthAndDay);
	 * int dayOfMonth = CalendarMetrics.dayOfMonth(monthAndDay);
	 * </pre>
	 * 
	 * @param year
	 *            The year.
	 * @param yearDay
	 *            The day of the year. Must be valid value between 1 and the maximum number of days in that year.
	 * @return in integer with the day of month in the lowest significant byte and the month in the second lowest significant byte.
	 */
	public abstract int getMonthAndDayOfYearDay(int year, int yearDay);


	public static int monthAndDay(int month, int day)
	{
		return (month << 8) + day;
	}


	/**
	 * Get the month from a compound MonthAndDay value like {@link #getMonthAndDayOfYearDay(int, int)} returns it.
	 * 
	 * @param monthAndDay
	 *            An integer that contains a month and a day.
	 * @return The month.
	 */
	public static int month(int monthAndDay)
	{
		return monthAndDay >> 8;
	}


	/**
	 * Get the day of month from a compound MonthAndDay value like {@link #getMonthAndDayOfYearDay(int, int)} returns it.
	 * 
	 * @param monthAndDay
	 *            An integer that contains a month and a day.
	 * @return The day of month.
	 */
	public static int dayOfMonth(int monthAndDay)
	{
		return monthAndDay & 0xff;
	}


	public abstract int getYearDaysForMonth(int year, int month);


	public abstract int getMonthsPerYear(int year);


	public abstract int getDaysPerYear(int year);


	public abstract int getWeeksPerYear(int year);


	public abstract int getWeekOfYear(int year, int month, int dayOfMonth);


	public abstract int getWeekOfYear(int year, int yearDay);


	public abstract int getDayOfWeek(int year, int yearDay);


	public abstract int getDayOfWeek(int year, int month, int dayOfMonth);


	/**
	 * Get the day of the year for the specified date.
	 * 
	 * @param year
	 *            The year.
	 * @param month
	 *            The month.
	 * @param dayOfMonth
	 *            The day of month.
	 * @return The day of year.
	 */
	public abstract int getDayOfYear(int year, int month, int dayOfMonth);


	/**
	 * Get the weekday of the first day (which is January the 1st in a Gregorian Calendar) in the given year.
	 * 
	 * @param year
	 *            The year.
	 * @return The weekday number.
	 */
	public abstract int getWeekDayOfFirstYearDay(int year);


	/**
	 * Get the day of year of the start of the first week in a year. Note this method returns values below 1 if the start of the week is in the previous year.
	 * The result depends on the values of {@link #weekStart} and {@link #minDaysInFirstWeek}.
	 * 
	 * @param year
	 *            The year.
	 * @return The day of year.
	 */
	public abstract int getYearDayOfFirstWeekStart(int year);

}
