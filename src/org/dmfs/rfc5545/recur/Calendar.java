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

import java.io.StringWriter;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * Extends {@link GregorianCalendar} by a couple of routines to parse and serialize calendar values.
 * <p>
 * This class also adds support for all-day and floating instances.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class Calendar extends GregorianCalendar
{
	/**
	 * Generated serial ID.
	 */
	private static final long serialVersionUID = 90776257925589787L;

	/**
	 * Static instance of the time zone UTC.
	 */
	public final static TimeZone UTC = TimeZone.getTimeZone("UTC");

	/**
	 * Indicates that this calendar instance is and all-day instance.
	 */
	private boolean mAllDay;

	/**
	 * Indicates this calendar instance is floating, i.e. has no time zone and always refers to local time (in every time zone).
	 */
	private boolean mIsFloating;


	/**
	 * Create a new calendar instance from a time stamp. The instance will be initialized with UTC. Use {@link #toAllDay()} to make the instance all-day
	 * instance.
	 * 
	 * @param millis
	 *            The millisecond time stamp of the instance.
	 */
	public Calendar(long millis)
	{
		this(UTC, millis);
	}


	/**
	 * Create a new calendar instance from a time stamp with a specific time zone. The event will be floating if time zone is <code>null</code>.
	 * 
	 * @param timezone
	 *            The time zone of the new event.
	 * @param millis
	 *            The millisecond time stamp of the instance.
	 */
	public Calendar(TimeZone timezone, long millis)
	{
		super(timezone == null ? UTC : timezone);
		setTimeInMillis(millis);
		mIsFloating = timezone == null;
	}


	/**
	 * Create a new all-day calendar instance with some initial values.
	 * 
	 * @param year
	 *            The year of the instance.
	 * @param month
	 *            The month of the instance.
	 * @param day
	 *            The day of the month of the instance.
	 */
	public Calendar(int year, int month, int day)
	{
		super(UTC);
		set(year, month, day, 0, 0, 0);
		set(Calendar.MILLISECOND, 0);
		mAllDay = true;
		mIsFloating = true;
	}


	/**
	 * Create a new floating calendar instance with some initial values.
	 * 
	 * @param year
	 *            The year of the instance.
	 * @param month
	 *            The month of the instance.
	 * @param day
	 *            The day of the month of the instance.
	 * @param hour
	 *            The hour of the instance.
	 * @param minute
	 *            The minute of the instance.
	 * @param second
	 *            The seconds of the instance.
	 */
	public Calendar(int year, int month, int day, int hour, int minute, int second)
	{
		super(UTC);
		set(year, month, day, hour, minute, second);
		set(Calendar.MILLISECOND, 0);
		mAllDay = false;
		mIsFloating = true;
	}


	/**
	 * Create a new calendar instance with time zone and with some initial values.
	 * 
	 * @param timezone
	 *            The {@link TimeZone} of this instance. If timezone is <code>null</code> the instance will be floating.
	 * @param year
	 *            The year of the instance.
	 * @param month
	 *            The month of the instance.
	 * @param day
	 *            The day of the month of the instance.
	 * @param hour
	 *            The hour of the instance.
	 * @param minute
	 *            The minute of the instance.
	 * @param second
	 *            The seconds of the instance.
	 */
	public Calendar(TimeZone timezone, int year, int month, int day, int hour, int minute, int second)
	{
		super(timezone == null ? UTC : timezone);
		super.set(year, month, day, hour, minute, second);
		set(Calendar.MILLISECOND, 0);
		mAllDay = false;
		mIsFloating = timezone == null;
	}


	@Override
	public Calendar clone()
	{
		Calendar clone = (Calendar) super.clone();

		clone.mAllDay = mAllDay;
		clone.mIsFloating = mIsFloating;

		return clone;
	}


	@Override
	public void set(int field, int value)
	{
		if (mAllDay)
		{
			switch (field)
			{
				case HOUR_OF_DAY:
				case HOUR:
				case AM_PM:
				case MINUTE:
				case SECOND:
				case MILLISECOND:
					// this is no longer an all day instance if we set the value to anything but 0
					mAllDay &= value == 0;
			}
		}
		super.set(field, value);
	}


	@Override
	public void setTimeZone(TimeZone timezone)
	{
		if (timezone == null)
		{
			mIsFloating = true;
		}
		else
		{
			mIsFloating = false;
			super.setTimeZone(timezone);
		}
	}


	@Override
	public TimeZone getTimeZone()
	{
		if (mIsFloating)
		{
			return null;
		}
		else
		{
			return super.getTimeZone();
		}
	}


	/**
	 * Convert this instance to an all-day instance. This will drop all time zone and time information.
	 */
	public void toAllDay()
	{
		mAllDay = true;
		mIsFloating = true;
		int year = get(Calendar.YEAR);
		int month = get(Calendar.MONTH);
		int dayOfMonth = get(Calendar.DAY_OF_MONTH);
		super.setTimeZone(UTC);
		set(year, month, dayOfMonth, 0, 0, 0);
		set(MILLISECOND, 0);
	}


	/**
	 * Replace the current {@link TimeZone} without actually shifting the time.
	 * <p>
	 * In contrast to {@link #setTimeZone(TimeZone)} this will not change the local time. After calling this method the instance will have the same local time
	 * as before, in particular that means the absolute time will change.
	 * </p>
	 * <p>
	 * This method is useful to set a floating instance to a specific time zone.
	 * </p>
	 * 
	 * @param timezone
	 *            The new {@link TimeZone}.
	 */
	public void replaceTimeZone(TimeZone timezone)
	{
		if (timezone == null)
		{
			mIsFloating = true;
		}
		else if (!timezone.equals(getTimeZone()))
		{
			mIsFloating = false;
			int year = get(Calendar.YEAR);
			int month = get(Calendar.MONTH);
			int dayOfMonth = get(Calendar.DAY_OF_MONTH);
			int hour = get(Calendar.HOUR_OF_DAY);
			int minute = get(Calendar.MINUTE);
			int second = get(Calendar.SECOND);
			int millisecond = get(Calendar.MILLISECOND);
			setTimeZone(timezone);
			set(year, month, dayOfMonth, hour, minute, second);
			set(MILLISECOND, millisecond);
		}
	}


	/**
	 * Return whether this instance is a floating instance, i.e. it has not time zone information.
	 * 
	 * @return <code>true</code> if the instance is floating, <code>false</code> otherwise.
	 */
	public boolean isFloating()
	{
		return mIsFloating;
	}


	/**
	 * Return whether this instance is an all-day instance, i.e. it has no time information.
	 * 
	 * @return <code>true</code> if the instance is an all-day instance, <code>false</code> otherwise.
	 */
	public boolean isAllDay()
	{
		return mAllDay;
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
	 * Use {@link #parse(TimeZone, String)} to apply a time zone to floating events or {@link #replaceTimeZone(TimeZone)} to set the time zone after paring the
	 * string. Use {@link #setTimeZone(TimeZone)} to shift the time zone.
	 * </p>
	 * 
	 * @param string
	 *            A valid date-time string.
	 * @return A new {@link Calendar} instance.
	 */
	public static Calendar parse(String string)
	{
		return parse(null, string);
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
	 * @param timeZone
	 *            A time zone to apply to non-allday and non-UTC date-time values. If timeZone is <code>null</code> the event will be floating.
	 * @param string
	 *            A valid date-time string.
	 * @return A new {@link Calendar} instance.
	 */
	public static Calendar parse(TimeZone timeZone, String string)
	{
		if (string == null)
		{
			throw new NullPointerException("a date-time string must not be null");
		}

		if (string.length() == 8)
		{
			return new Calendar(Integer.parseInt(string.substring(0, 4)), Integer.parseInt(string.substring(4, 6)) - 1,
				Integer.parseInt(string.substring(6, 8)));
		}
		else if (string.length() == 15 && string.charAt(8) == 'T')
		{
			return new Calendar(timeZone, Integer.parseInt(string.substring(0, 4)), Integer.parseInt(string.substring(4, 6)) - 1, Integer.parseInt(string
				.substring(6, 8)), Integer.parseInt(string.substring(9, 11)), Integer.parseInt(string.substring(11, 13)), Integer.parseInt(string.substring(13,
				15)));
		}
		else if (string.length() == 16 && string.charAt(8) == 'T' && string.charAt(15) == 'Z')
		{
			Calendar result = new Calendar(UTC, Integer.parseInt(string.substring(0, 4)), Integer.parseInt(string.substring(4, 6)) - 1, Integer.parseInt(string
				.substring(6, 8)), Integer.parseInt(string.substring(9, 11)), Integer.parseInt(string.substring(11, 13)), Integer.parseInt(string.substring(13,
				15)));
			return result;
		}
		throw new IllegalArgumentException("illegal date-time string: " + string);
	}


	@Override
	public String toString()
	{
		// write a date string that complies with RFC5545
		StringWriter result = new StringWriter(16);
		writeInt(result, get(YEAR) / 100);
		writeInt(result, get(YEAR) % 100);
		writeInt(result, get(MONTH) + 1);
		writeInt(result, get(DAY_OF_MONTH));
		if (!mAllDay)
		{
			result.append('T');
			writeInt(result, get(HOUR_OF_DAY));
			writeInt(result, get(MINUTE));
			writeInt(result, get(SECOND));
			TimeZone tz = getTimeZone();
			if (tz != null && "UTC".equals(tz.getID()))
			{
				result.write('Z');
			}
		}
		return result.toString();
	}


	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof Calendar))
		{
			return false;
		}

		Calendar other = (Calendar) object;
		return other.mIsFloating == mIsFloating && other.mAllDay == mAllDay && other.getTimeInMillis() == getTimeInMillis();
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
}
