package org.dmfs.librecur.test;

import org.dmfs.rfc5545.recur.Calendar;

import android.text.format.Time;


public class BenchmarkConditions
{

	public final static String[] BENCHMARK_RULES = { "FREQ=YEARLY", "FREQ=YEARLY;BYMONTH=4;BYMONTHDAY=25", "FREQ=MONTHLY", "FREQ=MONTHLY;BYMONTHDAY=1,15,31",
		"FREQ=MONTHLY;BYDAY=-1SU", "FREQ=DAILY", "FREQ=WEEKLY", "FREQ=WEEKLY;BYDAY=SA,SU" };
	public final static Calendar START_DATE = Calendar.parse("19040425");
	public final static Calendar END_DATE = Calendar.parse("20370425");
	public final static Time START_DATE2 = new Time(Time.TIMEZONE_UTC);
	public final static Time END_DATE2 = new Time(Time.TIMEZONE_UTC);

	static
	{
		START_DATE2.parse("19040425");
		END_DATE2.parse("20370425");
	}

	public final static int INIT_ITERATIONS = 10000;
	public final static int TEST_ITERATIONS = 100000;

}
