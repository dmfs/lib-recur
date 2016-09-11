package org.dmfs.librecur.test;

import java.util.Calendar;

import org.dmfs.rfc5545.DateTime;

import android.text.format.Time;


public abstract class Benchmark
{
  public final static String TAG = "Benchmark";
  public final String name;
  public long result = 0;
  public int instances = 0;
  public long mem;


  public Benchmark(String name)
  {
    this.name = name;
  }


  public long runBenchmark(String rrule, int iterations, DateTime startCalendar, Time startTime, long rangeStart, long rangeEnd) throws Exception
  {
    long result = 0;

    for (int i = 0; i < 10; ++i)
    {
      result ^= run(rrule, startCalendar, startTime, rangeStart, rangeEnd);
      System.gc();
    }
    Runtime rt = Runtime.getRuntime();

    long free = rt.freeMemory();
    result ^= run(rrule, startCalendar, startTime, rangeStart, rangeEnd);
    mem = free - rt.freeMemory();
    System.gc();

    result = 0;
    long start = System.nanoTime();
    for (int i = 0; i < iterations; ++i)
    {
      result += run(rrule, startCalendar, startTime, rangeStart, rangeEnd);
    }
    this.result = result;
    return System.nanoTime() - start;
  }


  protected abstract long run(String rrule, DateTime startCalendar, Time startTime, long rangeStart, long rangeEnd) throws Exception;
}
