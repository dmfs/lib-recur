package org.dmfs.rfc5545;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.TimeZone;

import org.junit.Test;


public class DateTimeTest
{

	private final static String[] TESTDATES = { "19000101T000000", "19700101T000000", "20140330T010000", "20140414T132231" };


	@Test
	public void testSwapTimeZone()
	{
		String[] timeZones = TimeZone.getAvailableIDs();
		// append null to the zones
		timeZones = Arrays.copyOf(timeZones, timeZones.length + 1);

		// test the conversion between all timezones
		for (String testdate : TESTDATES)
		{
			for (String fromTimezone : timeZones)
			{
				for (String toTimezone : timeZones)
				{
					TimeZone fromZone = fromTimezone == null ? null : TimeZone.getTimeZone(fromTimezone);
					TimeZone toZone = toTimezone == null ? null : TimeZone.getTimeZone(toTimezone);

					DateTime originalDate = DateTime.parse(fromZone, testdate);

					DateTime modifiedDate = new DateTime(originalDate);
					modifiedDate.getTimestamp();
					modifiedDate.swapTimeZone(toZone);

					DateTime expectedDate = DateTime.parse(toZone, testdate);

					assertEquals(expectedDate.getTimestamp(), modifiedDate.getTimestamp());
					assertEquals(originalDate.getInstance(), modifiedDate.getInstance());
				}
			}
		}
	}
}
