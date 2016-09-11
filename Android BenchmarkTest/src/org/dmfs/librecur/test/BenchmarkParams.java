package org.dmfs.librecur.test;

import org.dmfs.rfc5545.DateTime;

import android.text.format.Time;


public class BenchmarkParams
{
  public final Benchmark benchmark;
  public final DateTime startCalendar;
  public final Time startTime;
  public final long rangeStart;
  public final long rangeEnd;


  public BenchmarkParams(Benchmark benchmark, String start, String rangeStart, String rangeEnd)
  {
    this.benchmark = benchmark;
    this.startCalendar = DateTime.parse(start);
    this.startTime = new Time(Time.TIMEZONE_UTC);
    this.startTime.set(startCalendar.getTimestamp());
    this.rangeStart = DateTime.parse(rangeStart).getTimestamp();
    this.rangeEnd = DateTime.parse(rangeEnd).getTimestamp();
  }
}
