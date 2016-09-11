package org.dmfs.librecur.test;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;
import org.dmfs.rfc5545.recurrenceset.RecurrenceRuleAdapter;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSet;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSetIterator;

import android.text.format.Time;


public class libRecurBenchmark extends BenchmarkSuite
{

  @Override
  public BenchmarkParams[] getBenchmarkParams()
  {
    return params;
  }

  private BenchmarkParams[] params = new BenchmarkParams[] { new BenchmarkParams(PARSER_BENCHMARK, "20010303", "20010303", "20010303"),
    new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20010303"), new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20020303"),
    new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20060303"), new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20110303"),
    new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20160303"), new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20210303"),
    new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20260303"), new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20310303"),

  // new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20020303", "20020303"), new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20120303", "20120303"),
  // new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20220303", "20220303"), new BenchmarkParams(ITERATE_BENCHMARK, "19920303", "20320303", "20320303"),
  // new BenchmarkParams(ITERATE_BENCHMARK, "19820303", "20320303", "20320303"), new BenchmarkParams(ITERATE_BENCHMARK, "19720303", "20320303", "20320303"),

  };

  public static Benchmark PARSER_BENCHMARK = new Benchmark("Parser Benchmark ")
  {

    @Override
    public long run(String rrule, DateTime startCalendar, Time startTime, long rangeStart, long rangeEnd) throws InvalidRecurrenceRuleException
    {
      RecurrenceRule rule = new RecurrenceRule(rrule, RfcMode.RFC2445_LAX);
      return rule.getInterval();
    }

  };

  public static Benchmark ITERATE_BENCHMARK = new Benchmark("Iterate Benchmark")
  {

    @Override
    public long run(String rrule, DateTime startCalendar, Time startTime, long rangeStart, long rangeEnd) throws InvalidRecurrenceRuleException
    {
      RecurrenceRule rule = new RecurrenceRule(rrule, RfcMode.RFC2445_LAX);
      RecurrenceSet rset = new RecurrenceSet();
      rset.addInstances(new RecurrenceRuleAdapter(rule));

      long result = 0;
      int instances = 0;

      RecurrenceSetIterator iterator = rset.iterator(startCalendar.getTimeZone(), startCalendar.getTimestamp(), rangeEnd);
      if (startCalendar.getTimestamp() != rangeStart)
      {
        iterator.fastForward(rangeStart);
      }
      long lastdate = Long.MIN_VALUE;
      while (lastdate < rangeEnd && iterator.hasNext())
      {
        lastdate = iterator.next();
        result += lastdate;
        ++instances;
      }
      this.instances = instances;
      return result;
    }

  };

}
