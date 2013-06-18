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
	 *            The {@link TimeZone} of this instance.
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
		super(timezone);
		super.set(year, month, day, hour, minute, second);
		set(Calendar.MILLISECOND, 0);
		mAllDay = false;
		mIsFloating = false;
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


	public void toAllDay()
	{
		mAllDay = true;
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
			setTimeZone(timezone);
			set(year, month, dayOfMonth, hour, minute, second);
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
	 * If the Z is present the time zone is UTC. Otherwise the time zone is specified in an additional parameter or if no such parameter exists it's floating
	 * (i.e. always local time).
	 * <p>
	 * Use {@link #replaceTimeZone(TimeZone)} to make set a time zone for floating instances and {@link #setTimeZone(TimeZone)} to shift the time zone for UTC
	 * instances.
	 * </p>
	 * 
	 * @param string
	 *            A valid date-time string.
	 * @return A new {@link Calendar} instance.
	 */
	public static Calendar parse(String string)
	{
		if (string.length() == 8)
		{
			return new Calendar(Integer.parseInt(string.substring(0, 4)), Integer.parseInt(string.substring(4, 6)) - 1,
				Integer.parseInt(string.substring(6, 8)));
		}
		else if (string.length() == 15 && string.charAt(8) == 'T')
		{
			return new Calendar(Integer.parseInt(string.substring(0, 4)), Integer.parseInt(string.substring(4, 6)) - 1,
				Integer.parseInt(string.substring(6, 8)), Integer.parseInt(string.substring(9, 11)), Integer.parseInt(string.substring(11, 13)),
				Integer.parseInt(string.substring(13, 15)));
		}
		else if (string.length() == 16 && string.charAt(8) == 'T' && string.charAt(15) == 'Z')
		{
			Calendar result = new Calendar(Integer.parseInt(string.substring(0, 4)), Integer.parseInt(string.substring(4, 6)) - 1, Integer.parseInt(string
				.substring(6, 8)), Integer.parseInt(string.substring(9, 11)), Integer.parseInt(string.substring(11, 13)), Integer.parseInt(string.substring(13,
				15)));
			result.replaceTimeZone(UTC);
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
