package org.dmfs.rfc5545.recur;

import java.util.HashMap;
import java.util.Map;

import org.dmfs.rfc5545.recur.CalendarMetrics.CalendarMetricsFactory;


public final class UnicodeCalendarScales
{

	private final static Map<String, CalendarMetricsFactory> CALENDAR_SCALES = new HashMap<>(10);


	public static CalendarMetricsFactory getCalendarMetricsForName(String calendarScaleName)
	{
		return CALENDAR_SCALES.get(calendarScaleName);
	}

	static
	{
		CALENDAR_SCALES.put(GregorianCalendarMetrics.CALENDAR_SCALE_ALIAS, GregorianCalendarMetrics.FACTORY);
		CALENDAR_SCALES.put(GregorianCalendarMetrics.CALENDAR_SCALE_NAME, GregorianCalendarMetrics.FACTORY);
	}
}
