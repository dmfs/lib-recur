package org.dmfs.rfc5545.calendarmetrics;

import static org.junit.Assert.assertEquals;

import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.calendarmetrics.GregorianCalendarMetrics;
import org.junit.Test;


public class CalendarMetricsTest
{
	@Test
	public void testMonthAndDay()
	{
		CalendarMetrics tools = new GregorianCalendarMetrics(1, 4);
		for (int month = 0; month < 12; ++month)
		{
			for (int monthday = 1; monthday <= tools.getDaysPerPackedMonth(2000, month); ++monthday)
			{
				int mad = CalendarMetrics.monthAndDay(month, monthday);

				int mo = CalendarMetrics.packedMonth(mad);
				int dom = CalendarMetrics.dayOfMonth(mad);

				assertEquals(month, mo);
				assertEquals(monthday, dom);
			}
		}
	}

}
