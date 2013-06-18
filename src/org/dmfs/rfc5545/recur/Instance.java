package org.dmfs.rfc5545.recur;

import java.io.StringWriter;


/**
 * Represents a single instance of the series of instances. We use this class instead of a {@link Calendar} because it allows invalid dates like 2013-02-29. A
 * Calendar instance either wraps around to 2013-03-01 or limits to 2013-2-28. In either case that's not what we want when iterating instances. We want to
 * preserve instances like that because they keep valuable information. Invalid dates get filtered out by the {@link SanityFilter} after all by-rules have been
 * applied.
 * <p>
 * Also this class doesn't maintain a time zone.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class Instance implements Comparable<Instance>, Cloneable
{
	/**
	 * The year of this instance.
	 */
	public int year;

	/**
	 * Zero based month index.
	 */
	public int month;

	/**
	 * The month of the instance.
	 */
	public int dayOfMonth;

	/**
	 * The week of the year of the instance.
	 */
	public int dayOfWeek;

	/**
	 * The day of the year of the instance.
	 */
	public int dayOfYear;

	/**
	 * The week of year of the instance. The actual value depends on the current setting of the week start.
	 */
	public int weekOfYear;

	/**
	 * The hour of the instance.
	 */
	public int hour;

	/**
	 * The minute of the instance.
	 */
	public int minute;

	/**
	 * The seconds of the instance.
	 */
	public int second;

	/**
	 * A flag indicating whether this instance is an allday instance or not.
	 */
	public boolean allDay = false;


	/**
	 * Create a new instance from a {@link Calendar} instance.
	 * 
	 * @param defaults
	 */
	public Instance(Calendar defaults)
	{
		year = defaults.get(Calendar.YEAR);
		month = defaults.get(Calendar.MONTH);
		weekOfYear = defaults.get(Calendar.WEEK_OF_YEAR);
		dayOfMonth = defaults.get(Calendar.DAY_OF_MONTH);
		dayOfWeek = defaults.get(Calendar.DAY_OF_WEEK);
		dayOfYear = defaults.get(Calendar.DAY_OF_YEAR);
		hour = defaults.get(Calendar.HOUR_OF_DAY);
		minute = defaults.get(Calendar.MINUTE);
		second = defaults.get(Calendar.SECOND);
	}


	/**
	 * Creates a new instance providing all values explicitly. This also sets {@link #allDay} to false;
	 * 
	 * @param year
	 *            The year of the instance.
	 * @param month
	 *            The month of the instance.
	 * @param dayOfMonth
	 *            The day of month of the instance.
	 * @param hour
	 *            The hour of the instance.
	 * @param minute
	 *            The minute of the instance.
	 * @param second
	 *            The seconds of the instance.
	 */
	public Instance(int year, int month, int dayOfMonth, int hour, int minute, int second)
	{
		this.year = year;
		this.month = month;
		this.dayOfMonth = dayOfMonth;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.allDay = false;
	}


	/**
	 * Creates a new instance providing all values explicitly. This also sets {@link #allDay} to true.
	 * 
	 * @param year
	 *            The year of the instance.
	 * @param month
	 *            The month of the instance.
	 * @param dayOfMonth
	 *            The day of month of the instance.
	 */
	public Instance(int year, int month, int dayOfMonth)
	{
		this.year = year;
		this.month = month;
		this.dayOfMonth = dayOfMonth;
		this.hour = 0;
		this.minute = 0;
		this.second = 0;
		this.allDay = true;
	}


	@Override
	public Instance clone()
	{
		Instance result = new Instance(year, month, dayOfMonth, hour, minute, second);
		result.dayOfWeek = dayOfWeek;
		result.weekOfYear = weekOfYear;
		result.dayOfYear = dayOfYear;
		result.allDay = allDay;

		return result;
	}


	@Override
	public String toString()
	{
		StringWriter result = new StringWriter(15);
		writeInt(result, year / 100);
		writeInt(result, year % 100);
		writeInt(result, month + 1);
		writeInt(result, dayOfMonth);
		if (!allDay)
		{
			result.append('T');
			writeInt(result, hour);
			writeInt(result, minute);
			writeInt(result, second);
		}
		return result.toString();
	}


	/**
	 * A helper to write two digit leading zero integers. This method writes only the two least significant digits.
	 * 
	 * @param sw
	 *            The {@link StringWriter} to write to.
	 * @param num
	 *            The int to write.
	 */
	private void writeInt(StringWriter sw, int num)
	{
		sw.write((num / 10) % 10 + '0');
		sw.write((num % 10) + '0');
	}


	@Override
	public int compareTo(Instance other)
	{
		/*
		 * We compare each value separately instead of just calculating a time stamp and compare this one.
		 * 
		 * That guarantees two values will not equal just because one value wraps around.
		 */
		if (year == other.year)
		{
			if (month == other.month)
			{
				if (dayOfMonth == other.dayOfMonth)
				{
					if (hour == other.hour)
					{
						if (minute == other.minute)
						{
							return second - other.second;
						}
						else
						{
							return minute - other.minute;
						}
					}
					else
					{
						return hour - other.hour;
					}
				}
				else
				{
					return dayOfMonth - other.dayOfMonth;
				}
			}
			else
			{
				return month - other.month;
			}
		}
		else
		{
			return year - other.year;
		}
	}


	@Override
	public int hashCode()
	{
		/*
		 * This hash code doesn't take leap years or other calendar scales into account. That's ok because we consider two instances the same only if year,
		 * month, day of month, hour, minute and second are equal.
		 * 
		 * In particular that means the dates 2013-03-01 and 2013-02-29 are not equal and must not have the same hash code.
		 */
		return (((year * 366 + 31 * month + dayOfMonth) * 24 + hour) * 60 + minute) * 60 + second;
	}


	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Instance))
		{
			return false;
		}
		return compareTo((Instance) other) == 0;
	}
}
