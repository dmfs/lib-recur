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

package org.dmfs.rfc5545;

import java.io.IOException;
import java.io.Writer;
import java.util.TimeZone;

import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.calendarmetrics.GregorianCalendarMetrics;
import org.dmfs.rfc5545.recur.RecurrenceRule.Weekday;
import org.dmfs.rfc5545.recur.UnicodeCalendarScales;


/**
 * Represents a DATE-TIME or DATE value as specified in RFC 5545.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class DateTime
{
	/**
	 * The default calendar scale to use. By default all {@link DateTime} and Date values use the Gregorian calendar scale.
	 */
	public final static CalendarMetrics DEFAULT_CALENDAR_SCALE = new GregorianCalendarMetrics(Weekday.MO.ordinal(), 4);

	/**
	 * Static instance of the time zone UTC.
	 */
	public final static TimeZone UTC = TimeZone.getTimeZone("UTC");

	/**
	 * The {@link CalendarMetrics} of this {@link DateTime} object.
	 */
	private final CalendarMetrics mCalendarMetrics;

	/**
	 * The {@link TimeZone} of this {@link DateTime} object. May be <code>null</code> if the event is floating.
	 */
	private TimeZone mTimezone;

	/**
	 * The milliseconds since the epoch of this {@link DateTime}. This will be {@link Long#MAX_VALUE} of it has not been calculated yet.
	 */
	private long mTimestamp = Long.MAX_VALUE;

	/**
	 * The packed instance of this {@link DateTime}. This will be {@link Long#MAX_VALUE} of it has not been calculated yet.
	 * 
	 * @see Instance.
	 */
	private long mInstance = Long.MAX_VALUE;

	/**
	 * The week of the year or <code>-1</code> if it hasn't been calculated yet.
	 */
	private int mWeekOfYear = -1;

	/**
	 * The day of the week or <code>-1</code> if it hasn't been calculated yet.
	 */
	private int mDayOfWeek = -1;

	/**
	 * All-day flag.
	 */
	private boolean mAllday;


	/**
	 * Clone constructor to create a DateTime from another DateTime.
	 * 
	 * @param dateTime
	 *            The {@link DateTime} to clone.
	 */
	public DateTime(DateTime dateTime)
	{
		mCalendarMetrics = dateTime.mCalendarMetrics;
		mTimestamp = dateTime.mTimestamp;
		mTimezone = dateTime.mTimezone;
		mAllday = dateTime.mAllday;
		mInstance = dateTime.mInstance;
		mWeekOfYear = dateTime.mWeekOfYear;
		mDayOfWeek = dateTime.mDayOfWeek;
	}


	/**
	 * Clone constructor that changes the {@link CalendarMetrics}. It will represent the same absolute time, but instances will be in another calendar scale.
	 * 
	 * @param calendarMetrics
	 *            The calendar scale to use.
	 * @param dateTime
	 *            The {@link DateTime} to clone from.
	 */
	public DateTime(CalendarMetrics calendarMetrics, DateTime dateTime)
	{
		mCalendarMetrics = calendarMetrics;
		mTimestamp = dateTime.getTimestamp();
		mTimezone = dateTime.mTimezone;
		if (dateTime.mAllday)
		{
			toAllDay();
		}
	}


	/**
	 * Create a new {@link DateTime} from the given time stamp using {@link #DEFAULT_CALENDAR_SCALE} and {@link #UTC}.
	 * 
	 * @param timestamp
	 *            The time in milliseconds since the epoch of this date-time value.
	 */
	public DateTime(long timestamp)
	{
		this(DEFAULT_CALENDAR_SCALE, null, timestamp);
	}


	/**
	 * Create a new {@link DateTime} from the given time stamp using {@link #DEFAULT_CALENDAR_SCALE} and the given time zone.
	 * 
	 * @param timezone
	 *            The {@link TimeZone} of the new instance.
	 * @param timestamp
	 *            The time in milliseconds since the epoch of this date-time value.
	 */
	public DateTime(TimeZone timezone, long timestamp)
	{
		this(DEFAULT_CALENDAR_SCALE, timezone, timestamp);
	}


	public DateTime(CalendarMetrics calendarMetrics, TimeZone timezone, long timestamp)
	{
		mCalendarMetrics = calendarMetrics;
		mTimestamp = timestamp;
		mTimezone = timezone;
		mAllday = false;
	}


	public DateTime(int year, int month, int dayOfMonth)
	{
		mCalendarMetrics = DEFAULT_CALENDAR_SCALE;
		mInstance = Instance.make(year, dayOfMonth, dayOfMonth, 0, 0, 0);
		mTimezone = null;
		mAllday = true;
	}


	/**
	 * Create a new floating DateTime using {@link #DEFAULT_CALENDAR_SCALE}.
	 * 
	 * @param year
	 *            The year.
	 * @param month
	 *            The month.
	 * @param dayOfMonth
	 *            The day of the month.
	 * @param hours
	 *            The hour.
	 * @param minutes
	 *            The minutes.
	 * @param seconds
	 *            The seconds.
	 */
	public DateTime(int year, int month, int dayOfMonth, int hours, int minutes, int seconds)
	{
		this((TimeZone) null, year, month, dayOfMonth, hours, minutes, seconds);
	}


	public DateTime(TimeZone timezone, int year, int month, int dayOfMonth, int hours, int minutes, int seconds)
	{
		mCalendarMetrics = DEFAULT_CALENDAR_SCALE;
		mInstance = Instance.make(year, month, dayOfMonth, hours, minutes, seconds);
		mTimezone = timezone;
		mAllday = false;
	}


	public DateTime(String calScale, int year, int month, int dayOfMonth)
	{
		mCalendarMetrics = UnicodeCalendarScales.getCalendarMetricsForName(calScale).getCalendarMetrics(1);
		mInstance = Instance.make(year, month, dayOfMonth, 0, 0, 0);
		mTimezone = null;
		mAllday = true;
	}


	public DateTime(String calScale, int year, int month, int dayOfMonth, int hours, int minutes, int seconds)
	{
		this(calScale, null, year, month, dayOfMonth, hours, minutes, seconds);
	}


	public DateTime(String calScale, TimeZone timezone, int year, int month, int dayOfMonth, int hours, int minutes, int seconds)
	{
		mCalendarMetrics = UnicodeCalendarScales.getCalendarMetricsForName(calScale).getCalendarMetrics(1);
		mInstance = Instance.make(year, month, dayOfMonth, hours, minutes, seconds);
		mTimezone = timezone;
		mAllday = false;
	}


	public DateTime(CalendarMetrics calendarMetrics, int year, int month, int dayOfMonth)
	{
		mCalendarMetrics = calendarMetrics;
		mInstance = Instance.make(year, month, dayOfMonth, 0, 0, 0);
		mTimezone = null;
		mAllday = true;
	}


	public DateTime(CalendarMetrics calendarMetrics, int year, int month, int dayOfMonth, int hours, int minutes, int seconds)
	{
		this(calendarMetrics, null, year, month, dayOfMonth, hours, minutes, seconds);
	}


	public DateTime(CalendarMetrics calendarMetrics, TimeZone timezone, int year, int month, int dayOfMonth, int hours, int minutes, int seconds)
	{
		mCalendarMetrics = calendarMetrics;
		mInstance = Instance.make(year, month, dayOfMonth, hours, minutes, seconds);
		mTimezone = timezone;
		mAllday = false;
	}


	public CalendarMetrics getCalendarMetrics()
	{
		return mCalendarMetrics;
	}


	public TimeZone getTimeZone()
	{
		return mTimezone == null ? UTC : mTimezone;
	}


	public long getTimestamp()
	{
		if (mTimestamp == Long.MAX_VALUE)
		{
			long instance = getInstance();
			return mTimestamp = mCalendarMetrics.toMillis(mTimezone == null ? UTC : mTimezone, Instance.year(instance), Instance.month(instance),
				Instance.dayOfMonth(instance), Instance.hour(instance), Instance.minute(instance), Instance.second(instance), 0);
		}
		return mTimestamp;
	}


	public void toAllDay()
	{
		mAllday = true;
		long instance = mInstance;

		if (instance == Long.MAX_VALUE)
		{
			instance = getInstance();
		}

		mInstance = Instance.make(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance), 0, 0, 0);

		mTimestamp = Long.MAX_VALUE;
		mTimezone = null;
	}


	/**
	 * Replace the current time zone by the given one, keeping the local time constant. In effect the absolute time will change by the difference of the offsets
	 * to UTC of both time zones. Use this to convert a floating time to an absolute time.
	 * 
	 * @param timezone
	 *            The new {@link TimeZone}.
	 * @throws IllegalStateException
	 *             if the date is all-day.
	 */
	public void swapTimeZone(TimeZone timezone)
	{
		if (mAllday)
		{
			throw new IllegalStateException("can not swap the time zone of an all-day date");
		}

		TimeZone oldTimeZone = mTimezone;

		if (oldTimeZone == null && timezone == null || oldTimeZone != null && oldTimeZone.hasSameRules(timezone))
		{
			// time zone didn't change
			return;
		}

		long timestamp = mTimestamp;
		if (timestamp == Long.MAX_VALUE || sameTimestamps(oldTimeZone, timezone))
		{
			// we don't have a timestamp or we don't need to change it
			mTimezone = timezone;
			return;
		}

		getInstance();
		// invalidate time stamp
		mTimestamp = Long.MAX_VALUE;
		mTimezone = timezone;
	}


	/**
	 * Replace the current time zone by the given one, keeping the absolute time constant. In effect the local time will change by the difference of the offsets
	 * to UTC of both time zones.
	 * 
	 * @param timezone
	 *            The new {@link TimeZone}.
	 * @throws IllegalStateException
	 *             if the date is all-day.
	 */
	public void shiftToTimeZone(TimeZone timezone)
	{
		if (mAllday)
		{
			throw new IllegalStateException("can not shift the time zone of an all-day date");
		}

		TimeZone oldTimeZone = mTimezone;

		if (oldTimeZone == null && timezone == null || oldTimeZone != null && oldTimeZone.hasSameRules(timezone))
		{
			// time zone didn't change
			return;
		}

		long instance = mInstance;
		if (instance == Long.MAX_VALUE || sameTimestamps(oldTimeZone, timezone))
		{
			// we don't have an instance or we don't need to change it
			mTimezone = timezone;
			return;
		}

		getTimestamp();
		// invalidate the instance value
		mInstance = Long.MAX_VALUE;
		mWeekOfYear = -1;
		mDayOfWeek = -1;
		mTimezone = timezone;
	}


	public boolean isAllDay()
	{
		return mAllday;
	}


	public boolean isFloating()
	{
		return mTimezone == null;
	}


	public int getYear()
	{
		return Instance.year(getInstance());
	}


	public int getMonth()
	{
		return Instance.month(getInstance());
	}


	public int getWeekOfYear()
	{
		int weekOfYear = mWeekOfYear;
		if (weekOfYear < 0)
		{
			long instance = getInstance();
			mWeekOfYear = weekOfYear = mCalendarMetrics.getWeekOfYear(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance));
		}
		return weekOfYear;
	}


	public int getDayOfWeek()
	{
		int dayOfweek = mDayOfWeek;
		if (dayOfweek < 0)
		{
			long instance = getInstance();
			mDayOfWeek = dayOfweek = mCalendarMetrics.getDayOfWeek(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance));
		}
		return dayOfweek;
	}


	public int getDayOfMonth()
	{
		return Instance.dayOfMonth(getInstance());
	}


	/**
	 * Returns the hours in this date-time object. For all-day dates this always returns <code>0</code>.
	 * 
	 * @return The hours in this date-time object.
	 */
	public int getHours()
	{
		return Instance.hour(getInstance());
	}


	/**
	 * Returns the minutes in this date-time object. For all-day dates this always returns <code>0</code>.
	 * 
	 * @return The minutes in this date-time object.
	 */
	public int getMinutes()
	{
		return Instance.minute(getInstance());
	}


	/**
	 * Returns the seconds in this date-time object. For all-day dates this always returns <code>0</code>.
	 * 
	 * @return The seconds in this date-time object.
	 */
	public int getSeconds()
	{
		return Instance.second(getInstance());
	}


	/**
	 * Get the packed instance value of this DateTime. This is mostly for internal use.
	 * 
	 * @return The packed instance value.
	 */
	public long getInstance()
	{
		long instance = mInstance;
		if (instance == Long.MAX_VALUE)
		{
			return mInstance = mCalendarMetrics.toInstance(mTimestamp, mTimezone);
		}
		return instance;
	}


	public boolean after(DateTime that)
	{
		return getTimestamp() > that.getTimestamp();
	}


	public boolean before(DateTime that)
	{
		return getTimestamp() < that.getTimestamp();
	}


	/**
	 * Parses a date-time string as specified in RFC 5545. This method uses the default calendar scale {@link #DEFAULT_CALENDAR_SCALE}. Unless the given String
	 * ends with "Z" the resulting {@link DateTime} will be floating.
	 * 
	 * @param string
	 *            A valid date-time string.
	 * @return A new {@link DateTime} instance.
	 * 
	 * @see {@link #parse(CalendarMetrics, TimeZone, String)} for details.
	 */
	public static DateTime parse(String string)
	{
		return parse(DEFAULT_CALENDAR_SCALE, null, string);
	}


	/**
	 * Parses a date-time string as specified in RFC 5545. This method uses the default calendar scale {@link #DEFAULT_CALENDAR_SCALE}.
	 * 
	 * @param timeZone
	 *            A time zone to apply to non-allday and non-UTC date-time values. If timeZone is <code>null</code> the event will be floating.
	 * @param string
	 *            A valid date-time string.
	 * @return A new {@link DateTime} instance.
	 * 
	 * @see {@link #parse(CalendarMetrics, TimeZone, String)} for details.
	 */
	public static DateTime parse(String timeZone, String string)
	{
		return parse(DEFAULT_CALENDAR_SCALE, timeZone == null ? null : TimeZone.getTimeZone(timeZone), string);
	}


	/**
	 * Parses a date-time string as specified in RFC 5545. This method uses the default calendar scale {@link #DEFAULT_CALENDAR_SCALE}.
	 * 
	 * @param timeZone
	 *            A time zone to apply to non-allday and non-UTC date-time values. If timeZone is <code>null</code> the event will be floating.
	 * @param string
	 *            A valid date-time string.
	 * @return A new {@link DateTime} instance.
	 * 
	 * @see {@link #parse(CalendarMetrics, TimeZone, String)} for details.
	 */
	public static DateTime parse(TimeZone timeZone, String string)
	{
		return parse(DEFAULT_CALENDAR_SCALE, timeZone, string);
	}


	/**
	 * Parses a date-time string as specified in RFC 5545. This method takes the name of the calendar scale to use.
	 * 
	 * 
	 * @param calScale
	 *            The name of the calendar scale to use.
	 * @param timeZone
	 *            A time zone to apply to non-allday and non-UTC date-time values. If timeZone is <code>null</code> the event will be floating.
	 * @param string
	 *            A valid date-time string.
	 * @return A new {@link DateTime} instance.
	 * 
	 * @see {@link #parse(CalendarMetrics, TimeZone, String)} for details.
	 */
	public static DateTime parse(String calScale, TimeZone timeZone, String string)
	{
		return parse(UnicodeCalendarScales.getCalendarMetricsForName(calScale).getCalendarMetrics(1), timeZone, string);
	}


	/**
	 * Parses a date-time string as specified in RFC 5545. There are three valid forms of such a String:
	 * <ul>
	 * <li><code>YYYYMMDD</code></li>
	 * <li><code>YYYYMMDD'T'HHMMSS</code></li>
	 * <li><code>YYYYMMDD'T'HHMMSS'Z'</code></li>
	 * </ul>
	 * where YYYYMMDD means a date (year, month, day of month) and HHMMSS means a time (hour, minute, second). <code>'T'</code> and <code>'Z'</code> stand for
	 * the literals <code>T</code> and <code>Z</code>.
	 * 
	 * If 'Z' is present the time zone is UTC. Otherwise the time zone is specified in an additional parameter or if no such parameter exists it's floating
	 * (i.e. always local time).
	 * <p>
	 * Use {@link #setTimeZone(TimeZone)} to shift the time zone.
	 * </p>
	 * 
	 * @param calendarMetrics
	 *            The {@link CalendarMetrics} to use.
	 * @param timeZone
	 *            A time zone to apply to non-allday and non-UTC date-time values. If timeZone is <code>null</code> the event will be floating.
	 * @param string
	 *            A valid date-time string.
	 * @return A new {@link DateTime} instance.
	 */
	public static DateTime parse(CalendarMetrics calendarMetrics, TimeZone timeZone, String string)
	{
		if (string == null)
		{
			throw new NullPointerException("a date-time string must not be null");
		}

		try
		{
			if (string.length() == 8)
			{
				return new DateTime(calendarMetrics, parseFourDigits(string, 0), parseTwoDigits(string, 4) - 1, parseTwoDigits(string, 6));
			}
			else if (string.length() == 15 && string.charAt(8) == 'T')
			{
				return new DateTime(calendarMetrics, timeZone, parseFourDigits(string, 0), parseTwoDigits(string, 4) - 1, parseTwoDigits(string, 6),
					parseTwoDigits(string, 9), parseTwoDigits(string, 11), parseTwoDigits(string, 13));
			}
			else if (string.length() == 16 && string.charAt(8) == 'T' && string.charAt(15) == 'Z')
			{
				return new DateTime(calendarMetrics, UTC, parseFourDigits(string, 0), parseTwoDigits(string, 4) - 1, parseTwoDigits(string, 6), parseTwoDigits(
					string, 9), parseTwoDigits(string, 11), parseTwoDigits(string, 13));
			}
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("illegal characters in date-time string: '" + string + "'", e);
		}
		throw new IllegalArgumentException("illegal date-time string: '" + string + "'");
	}


	@Override
	public int hashCode()
	{
		return (int) getTimestamp();
	}


	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof DateTime))
		{
			return false;
		}

		DateTime other = (DateTime) obj;

		if (mInstance != Long.MAX_VALUE && other.mInstance != Long.MAX_VALUE)
		{
			// compare by instance
			return mInstance == other.mInstance && mAllday == other.mAllday && mCalendarMetrics.scaleEquals(other.mCalendarMetrics)
				&& (mTimezone == other.mTimezone || mTimezone != null && other.mTimezone != null && sameTimestamps(mTimezone, other.mTimezone));
		}
		else
		{
			// compare by timestamp
			return mAllday == other.mAllday && mCalendarMetrics.scaleEquals(other.mCalendarMetrics) && getTimestamp() == other.getTimestamp()
				&& (mTimezone == other.mTimezone || mTimezone != null && other.mTimezone != null && sameTimestamps(mTimezone, other.mTimezone));

		}
	}


	@Override
	public String toString()
	{
		long instance = getInstance();

		// build a date string that complies with RFC5545
		StringBuilder result = new StringBuilder(16);
		int year = Instance.year(instance);
		writeInt(result, year / 100);
		writeInt(result, year % 100);
		writeInt(result, Instance.month(instance) + 1);
		writeInt(result, Instance.dayOfMonth(instance));
		if (!mAllday)
		{
			result.append('T');
			writeInt(result, Instance.hour(instance));
			writeInt(result, Instance.minute(instance));
			writeInt(result, Instance.second(instance));
			TimeZone tz = mTimezone;
			if (tz != null && "UTC".equals(tz.getID()))
			{
				result.append('Z');
			}
		}
		return result.toString();
	}


	/**
	 * Write the date-time string represented by this object to the given {@link Writer}.
	 * 
	 * @param out
	 *            The {@link Writer} to write to.
	 * @throws IOException
	 */
	public void toWriter(Writer out) throws IOException
	{
		long instance = getInstance();

		// write a date string that complies with RFC5545
		int year = Instance.year(instance);
		writeInt(out, year / 100);
		writeInt(out, year % 100);
		writeInt(out, Instance.month(instance) + 1);
		writeInt(out, Instance.dayOfMonth(instance));
		if (!mAllday)
		{
			out.write('T');
			writeInt(out, Instance.hour(instance));
			writeInt(out, Instance.minute(instance));
			writeInt(out, Instance.second(instance));
			TimeZone tz = mTimezone;
			if (tz != null && "UTC".equals(tz.getID()))
			{
				out.write('Z');
			}
		}
	}


	/**
	 * A helper to write two digit leading zero integers. This method writes only the two least significant digits.
	 * 
	 * @param sw
	 *            The {@link StringBuilder} to write to.
	 * @param num
	 *            The int to write.
	 */
	private void writeInt(StringBuilder sw, int num)
	{
		sw.append((char) ((num / 10) % 10 + '0'));
		sw.append((char) ((num % 10) + '0'));
	}


	/**
	 * A helper to write two digit leading zero integers. This method writes only the two least significant digits.
	 * 
	 * @param w
	 *            The {@link Writer} to write to.
	 * @param num
	 *            The int to write.
	 * @throws IOException
	 */
	private void writeInt(Writer w, int num) throws IOException
	{
		w.write((num / 10) % 10 + '0');
		w.write((num % 10) + '0');
	}


	private static boolean sameTimestamps(TimeZone first, TimeZone second)
	{
		if (first == second)
		{
			return true;
		}

		String firstId = first != null ? first.getID() : null;
		if (second == null && ("UTC".equals(firstId) || UTC.equals(first) || UTC.hasSameRules(first)))
		{
			// second time zone is "floating" and first one is UTC, we don't need to change the time stamp
			return true;
		}

		String secondId = second != null ? second.getID() : null;
		if (first == null && ("UTC".equals(secondId) || UTC.equals(second) || UTC.hasSameRules(second)))
		{
			// first time zone is "floating" and second one is UTC, we don't need to change the time stamp
			return true;
		}

		return first != null && second != null && (firstId.equals(secondId) || first.equals(second) || first.hasSameRules(second));
	}


	/**
	 * Parses the next four characters in the given {@link String} at the given offset as an integer.
	 * 
	 * @param string
	 *            The {@link String} to parse.
	 * @param offset
	 *            The offset of the number in the string.
	 * @return The integer value.
	 * @throws NumberFormatException
	 *             if the String doesn't contain digits at the given offset.
	 */
	private static int parseFourDigits(String string, int offset)
	{
		return parseTwoDigits(string, offset) * 100 + parseTwoDigits(string, offset + 2);
	}


	/**
	 * Parses the next two characters in the given {@link String} at the given offset as an integer.
	 * 
	 * @param string
	 *            The {@link String} to parse.
	 * @param offset
	 *            The offset of the number in the string.
	 * @return The integer value.
	 * @throws NumberFormatException
	 *             if the String doesn't contain digits at the given offset.
	 */
	private static int parseTwoDigits(String string, int offset)
	{
		int d1 = string.charAt(offset) - '0';
		int d2 = string.charAt(offset + 1) - '0';

		if (d1 < 0 || d2 < 0 || d1 > 9 || d2 > 9)
		{
			throw new NumberFormatException("illegal digit in number " + string.substring(offset, 2));
		}

		return d1 * 10 + d2;
	}

}
