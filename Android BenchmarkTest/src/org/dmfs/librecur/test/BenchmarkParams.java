package org.dmfs.librecur.test;

import org.dmfs.rfc5545.recur.Calendar;

import android.text.format.Time;


public class BenchmarkParams
{
	public final Benchmark benchmark;
	public final Calendar startCalendar;
	public final Time startTime;
	public final long rangeStart;
	public final long rangeEnd;


	public BenchmarkParams(Benchmark benchmark, String start, String rangeStart, String rangeEnd)
	{
		this.benchmark = benchmark;
		this.startCalendar = Calendar.parse(start);
		this.startTime = new Time(Time.TIMEZONE_UTC);
		this.startTime.set(startCalendar.getTimeInMillis());
		this.rangeStart = Calendar.parse(rangeStart).getTimeInMillis();
		this.rangeEnd = Calendar.parse(rangeEnd).getTimeInMillis();
	}
}
