package org.dmfs.rfc5545.recur;

import java.util.HashMap;
import java.util.Map;

import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics.CalendarMetricsFactory;
import org.dmfs.rfc5545.calendarmetrics.GregorianCalendarMetrics;
import org.dmfs.rfc5545.calendarmetrics.JulianCalendarMetrics;


public final class UnicodeCalendarScales
{

	private final static Map<String, CalendarMetricsFactory> CALENDAR_SCALES = new HashMap<String, CalendarMetricsFactory>(10);


	public static CalendarMetricsFactory getCalendarMetricsForName(String calendarScaleName)
	{
		return CALENDAR_SCALES.get(calendarScaleName);
	}

	static
	{
		CALENDAR_SCALES.put(GregorianCalendarMetrics.CALENDAR_SCALE_ALIAS, GregorianCalendarMetrics.FACTORY);
		CALENDAR_SCALES.put(GregorianCalendarMetrics.CALENDAR_SCALE_NAME, GregorianCalendarMetrics.FACTORY);
		CALENDAR_SCALES.put(JulianCalendarMetrics.CALENDAR_SCALE_ALIAS, JulianCalendarMetrics.FACTORY);
		CALENDAR_SCALES.put(JulianCalendarMetrics.CALENDAR_SCALE_NAME, JulianCalendarMetrics.FACTORY);
	}
}
