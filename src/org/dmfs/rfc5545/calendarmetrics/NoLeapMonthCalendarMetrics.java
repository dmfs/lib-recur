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

import org.dmfs.rfc5545.Instance;


/**
 * An abstract base class for calendar scales that have no leap month. Knowing that a year has no leap months makes a couple of calculations much easier and
 * allows to share code.
 * <p>
 * Knowing that a calendar scale has no leap months also allows to return the plain month number as packed month, which makes month handling much easier.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class NoLeapMonthCalendarMetrics extends CalendarMetrics
{

	/**
	 * Create calendar metrics for a calendar without leap months with the given week numbering.
	 * 
	 * @param weekStart
	 *            The first day of the week.
	 * @param minDaysInFirstWeek
	 *            The minimal number of days in the first week.
	 */
	public NoLeapMonthCalendarMetrics(int weekStart, int minDaysInFirstWeek)
	{
		super(weekStart, minDaysInFirstWeek);
	}


	@Override
	public int packedMonth(String month)
	{
		// just return the plain month number as packed month.
		try
		{
			int monthNum = Integer.parseInt(month) - 1;
			if (monthNum < 0 || monthNum >= getMonthsPerYear())
			{
				throw new IllegalArgumentException("month " + month + " is out of range 1.." + getMonthsPerYear());
			}
			return monthNum;
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("illegal month string " + month, e);
		}
	}


	@Override
	public String packedMonthToString(int packedMonth)
	{
		return String.valueOf(packedMonth + 1);
	}


	@Override
	public int packedMonth(int monthNum, boolean leapMonth)
	{
		return monthNum;
	}


	@Override
	public boolean isLeapMonth(int packedMonth)
	{
		return false;
	}


	@Override
	public int monthNum(int packedMonth)
	{
		return packedMonth;
	}


	@Override
	public int getMonthsPerYear(int year)
	{
		// all years have the same number of months.
		return getMonthsPerYear();
	}


	/**
	 * Returns the number of months in a year.
	 * 
	 * @return The number of months in a year.
	 */
	public abstract int getMonthsPerYear();


	@Override
	public long nextMonth(long instance)
	{
		int newMonth = Instance.month(instance) + 1;

		if (newMonth < getMonthsPerYear())
		{
			return Instance.setMonth(instance, newMonth);
		}
		else
		{
			return Instance.setYear(Instance.setMonth(instance, 0), Instance.year(instance) + 1);
		}
	}


	@Override
	public long nextMonth(long instance, int n)
	{
		if (n < 0)
		{
			throw new IllegalArgumentException("n must be >=0");
		}
		if (n == 0)
		{
			return instance;
		}

		int newMonth = Instance.month(instance) + n;
		int maxMonthsPerYear = getMonthsPerYear();

		if (newMonth < maxMonthsPerYear)
		{
			return Instance.setMonth(instance, newMonth);
		}
		else
		{
			return Instance.setYear(Instance.setMonth(instance, newMonth % maxMonthsPerYear), Instance.year(instance) + newMonth / maxMonthsPerYear);
		}
	}


	@Override
	public long prevMonth(long instance)
	{
		int newMonth = Instance.month(instance) - 1;

		if (newMonth >= 0)
		{
			return Instance.setMonth(instance, newMonth);
		}
		else
		{
			return Instance.setYear(Instance.setMonth(instance, getMonthsPerYear() - 1), Instance.year(instance) - 1);
		}
	}


	@Override
	public long prevMonth(long instance, int n)
	{
		if (n < 0)
		{
			throw new IllegalArgumentException("n must be >=0");
		}
		if (n == 0)
		{
			return instance;
		}

		int newMonth = Instance.month(instance) - n;
		int maxMonthsPerYear = getMonthsPerYear();

		if (newMonth >= 0)
		{
			return Instance.setMonth(instance, newMonth);
		}
		else
		{
			return Instance.setYear(Instance.setMonth(instance, (maxMonthsPerYear + newMonth % maxMonthsPerYear) % maxMonthsPerYear), Instance.year(instance)
				+ newMonth / maxMonthsPerYear);
		}
	}


	@Override
	public long nextDay(long instance)
	{
		int day = Instance.dayOfMonth(instance) + 1;
		int year = Instance.year(instance);
		int month = Instance.month(instance);

		if (day > getDaysPerPackedMonth(year, month))
		{
			day = 1;
			if (++month == getMonthsPerYear())
			{
				instance = Instance.setYear(instance, year + 1);
				month = 0;
			}
			instance = Instance.setMonth(instance, month);
		}
		return Instance.setDayOfMonth(instance, day);
	}


	@Override
	public long nextDay(long instance, int n)
	{
		if (n < 0)
		{
			throw new IllegalArgumentException("n must be >=0");
		}
		if (n == 0)
		{
			return instance;
		}

		int year = Instance.year(instance);
		int month = Instance.month(instance);
		int day = Math.min(Instance.dayOfMonth(instance), getDaysPerPackedMonth(year, month));

		int yearDay = getDayOfYear(year, month, day) + n;
		int yearDays;
		while (yearDay > (yearDays = getDaysPerYear(year)))
		{
			yearDay -= yearDays;
			year++;
		}

		int monthAndDay = getMonthAndDayOfYearDay(year, yearDay);
		return Instance.setYear(Instance.setMonthAndDayOfMonth(instance, packedMonth(monthAndDay), dayOfMonth(monthAndDay)), year);
	}


	@Override
	public long prevDay(long instance)
	{
		int day = Instance.dayOfMonth(instance) - 1;

		if (day <= 0)
		{
			int year = Instance.year(instance);
			int month = Instance.month(instance) - 1;
			if (month <= -1)
			{
				instance = Instance.setYear(instance, --year);
				month = getMonthsPerYear() - 1;
			}
			day = getDaysPerPackedMonth(year, month);
			instance = Instance.setMonth(instance, month);
		}
		return Instance.setDayOfMonth(instance, day);
	}


	@Override
	public long prevDay(long instance, int n)
	{
		if (n < 0)
		{
			throw new IllegalArgumentException("n must be >=0");
		}
		if (n == 0)
		{
			return instance;
		}

		int year = Instance.year(instance);
		int month = Instance.month(instance);
		int day = Math.min(Instance.dayOfMonth(instance), getDaysPerPackedMonth(year, month) + 1);

		int yearDay = getDayOfYear(year, month, day) - n;
		while (yearDay < 1)
		{
			year--;
			yearDay += getDaysPerYear(year);
		}

		int monthAndDay = getMonthAndDayOfYearDay(year, yearDay);
		return Instance.setYear(Instance.setMonthAndDayOfMonth(instance, packedMonth(monthAndDay), dayOfMonth(monthAndDay)), year);
	}

}
