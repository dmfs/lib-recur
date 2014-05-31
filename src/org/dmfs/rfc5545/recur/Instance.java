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

import java.util.TimeZone;


/**
 * This class provides a set of static methods to manipulate instances stored in a long value.
 * 
 * Storing the values of an instance in a single long allows to compare them quickly and it doesn't require any object instantiations.
 * 
 * <p>
 * TODO: add javadoc
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class Instance
{
	private final static TimeZone UTC = TimeZone.getTimeZone("UTC");

	private final static int YEAR_BITS = 18;
	private final static int MONTH_BITS = 5;
	private final static int DAY_BITS = 7;
	private final static int HOUR_BITS = 5;
	private final static int MINUTE_BITS = 6;
	private final static int SECOND_BITS = 6;
	private final static int WEEKDAY_BITS = 3;

	private final static int WEEKDAY_POS = 0;
	private final static int SECOND_POS = WEEKDAY_BITS + WEEKDAY_POS;
	private final static int MINUTE_POS = SECOND_BITS + SECOND_POS;
	private final static int HOUR_POS = MINUTE_BITS + MINUTE_POS;
	private final static int DAY_POS = HOUR_BITS + HOUR_POS;
	private final static int MONTH_POS = DAY_BITS + DAY_POS;
	private final static int YEAR_POS = MONTH_BITS + MONTH_POS;

	private final static long SECOND_MASK = ((1L << SECOND_BITS) - 1) << SECOND_POS;
	private final static long MINUTE_MASK = ((1L << MINUTE_BITS) - 1) << MINUTE_POS;
	private final static long HOUR_MASK = ((1L << HOUR_BITS) - 1) << HOUR_POS;
	private final static long WEEKDAY_MASK = ((1L << WEEKDAY_BITS) - 1) << WEEKDAY_POS;
	private final static long DAY_MASK = ((1L << DAY_BITS) - 1) << DAY_POS;
	private final static long MONTH_MASK = ((1L << MONTH_BITS) - 1) << MONTH_POS;
	private final static long YEAR_MASK = ((1L << YEAR_BITS) - 1) << YEAR_POS;

	private final static int YEAR_BIAS = 0;
	private final static int DAY_BIAS = 1 << (DAY_BITS - 1);


	/**
	 * You shall not instantiate this class
	 */
	private Instance()
	{
	}


	/**
	 * Create a new instance from a {@link Calendar} instance.
	 * 
	 * @param defaults
	 */
	public static long make(Calendar defaults)
	{
		int year = defaults.get(Calendar.YEAR);
		int month = defaults.get(Calendar.MONTH);
		int dayOfMonth = defaults.get(Calendar.DAY_OF_MONTH);
		int hour = defaults.get(Calendar.HOUR_OF_DAY);
		int minute = defaults.get(Calendar.MINUTE);
		int second = defaults.get(Calendar.SECOND);
		return make(year, month, dayOfMonth, hour, minute, second);
	}


	public static long make(int year, int month, int dayOfMonth, int hour, int minute, int second, int dayOfWeek)
	{
		long mValue = (((long) year + YEAR_BIAS) << YEAR_POS) | ((long) month << MONTH_POS) | (((long) dayOfMonth + DAY_BIAS) << DAY_POS)
			| (((long) dayOfWeek) << WEEKDAY_POS) | ((long) hour << HOUR_POS) | ((long) minute << MINUTE_POS) | ((long) second << SECOND_POS);
		return mValue;
	}


	public static long make(int year, int month, int dayOfMonth, int hour, int minute, int second)
	{
		long mValue = (((long) year + YEAR_BIAS) << YEAR_POS) | ((long) month << MONTH_POS) | (((long) dayOfMonth + DAY_BIAS) << DAY_POS)
			| ((long) hour << HOUR_POS) | ((long) minute << MINUTE_POS) | ((long) second << SECOND_POS);
		return mValue;
	}


	public static long setYear(long instance, int year)
	{
		return (instance & ~YEAR_MASK) | (((long) year + YEAR_BIAS) << YEAR_POS);
	}


	public static long setMonth(long instance, int month)
	{
		return (instance & ~MONTH_MASK) | ((long) month << MONTH_POS);
	}


	public static long setDayOfMonth(long instance, int dayOfMonth)
	{
		return (instance & ~DAY_MASK) | (((long) dayOfMonth + DAY_BIAS) << DAY_POS);
	}


	public static long setDayOfWeek(long instance, int dayOfWeek)
	{
		return (instance & ~WEEKDAY_MASK) | (dayOfWeek << WEEKDAY_POS);
	}


	public static long setMonthAndDayOfMonth(long instance, int month, int dayOfMonth)
	{
		return (instance & ~(MONTH_MASK | DAY_MASK)) | ((long) month << MONTH_POS) | (((long) dayOfMonth + DAY_BIAS) << DAY_POS);
	}


	public static long setHour(long instance, int hour)
	{
		return (instance & ~HOUR_MASK) | ((long) hour << HOUR_POS);
	}


	public static long setMinute(long instance, int minute)
	{
		return (instance & ~MINUTE_MASK) | ((long) minute << MINUTE_POS);
	}


	public static long setSecond(long instance, int second)
	{
		return (instance & ~SECOND_MASK) | ((long) second << SECOND_POS);
	}


	public static long maskWeekday(long instance)
	{
		return (instance & ~(WEEKDAY_MASK));
	}


	public static int year(long instance)
	{
		return (int) ((instance & YEAR_MASK) >> YEAR_POS) - YEAR_BIAS;
	}


	public static int month(long instance)
	{
		return (int) ((instance & MONTH_MASK) >> MONTH_POS);
	}


	public static int dayOfMonth(long instance)
	{
		return (int) ((instance & DAY_MASK) >> DAY_POS) - DAY_BIAS;
	}


	public static int hour(long instance)
	{
		return (int) ((instance & HOUR_MASK) >> HOUR_POS);
	}


	public static int minute(long instance)
	{
		return (int) ((instance & MINUTE_MASK) >> MINUTE_POS);
	}


	public static int second(long instance)
	{
		return (int) ((instance & SECOND_MASK) >> SECOND_POS);
	}


	/**
	 * Get the day of week stored in the instance. If the day of week has not been stored, this method returns an invalid value. You have to ensure yourself
	 * that this value is valid.
	 * 
	 * Values are from 0-6 where 0 means Sunday and 7 means Saturday.
	 * <p>
	 * Not that this is different from the values the {@link java.util.Calendar} class uses.
	 * </p>
	 * 
	 * @param instance
	 *            The instance.
	 * @return The day of the week, if present.
	 */
	public static int dayOfWeek(long instance)
	{
		return (int) ((instance & WEEKDAY_MASK) >> WEEKDAY_POS);
	}


	/**
	 * Convert an instance to milliseconds since the epoch (i.e. 1970-01-01 0:00:00 UTC).
	 * 
	 * @param instance
	 *            The instance to convert.
	 * @param timeZone
	 *            The time zone or <code>null</code> for all day and floating instances
	 * @param calendarMetrics
	 *            The calendar metrics to use,
	 * @return The time in milliseconds since the epoch of this instance.
	 */
	public static long toMillis(long instance, TimeZone timeZone, CalendarMetrics calendarMetrics)
	{
		return calendarMetrics.toMillis(timeZone == null ? UTC : timeZone, year(instance), month(instance), dayOfMonth(instance), hour(instance),
			minute(instance), second(instance), 0);
	}


	/**
	 * Validates the given instance using the provided {@link CalendarMetrics}.
	 * <p>
	 * At present this method doesn't check for leap seconds and it doesn't honor daylight saving switches. So it validates times between 2:00h and 3:00 to
	 * <code>true</code> even if the local time can't have these values due to daylight savings.
	 * </p>
	 * TODO: add check for daylight saving switches
	 * 
	 * @param instance
	 *            The instance to test.
	 * @param calendarMetrics
	 *            The {@link CalendarMetrics} to use.
	 * @return <code>true</code> if the date is valid, <code>false</code> otherwise.
	 */
	public static boolean validate(long instance, CalendarMetrics calendarMetrics)
	{
		int year = year(instance);
		int month = month(instance);

		if (month < 0 || month >= calendarMetrics.getMonthsPerYear(year))
		{
			return false;
		}

		int day = dayOfMonth(instance);
		if (day < 1 || day > calendarMetrics.getDaysPerMonth(year, month))
		{
			return false;
		}

		int hour = hour(instance);
		if (hour < 0 || hour > 23)
		{
			return false;
		}

		int minute = minute(instance);
		if (minute < 0 || minute > 59)
		{
			return false;
		}

		int second = second(instance);
		if (second < 0 || second > 59) // we don't support leap seconds yet
		{
			return false;
		}
		return true;
	}
}
