package org.dmfs.librecur.test;

import org.dmfs.rfc5545.recur.Calendar;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;
import org.dmfs.rfc5545.recur.recurrenceset.RecurrenceRuleAdapter;
import org.dmfs.rfc5545.recur.recurrenceset.RecurrenceSet;
import org.dmfs.rfc5545.recur.recurrenceset.RecurrenceSetIterator;

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
		new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20210303"), new BenchmarkParams(ITERATE_BENCHMARK, "20010303", "20010303", "20310303"),
		new BenchmarkParams(ITERATE_BENCHMARK, "19910303", "20010303", "20310303"), new BenchmarkParams(ITERATE_BENCHMARK, "19810303", "20010303", "20310303"), };

	public static Benchmark PARSER_BENCHMARK = new Benchmark("Parser Benchmark ")
	{

		@Override
		public long run(String rrule, Calendar startCalendar, Time startTime, long rangeStart, long rangeEnd) throws InvalidRecurrenceRuleException
		{
			RecurrenceRule rule = new RecurrenceRule(rrule, RfcMode.RFC2445_LAX);
			return rule.getInterval();
		}

	};

	public static Benchmark ITERATE_BENCHMARK = new Benchmark("Iterate Benchmark")
	{

		@Override
		public long run(String rrule, Calendar startCalendar, Time startTime, long rangeStart, long rangeEnd) throws InvalidRecurrenceRuleException
		{
			RecurrenceRule rule = new RecurrenceRule(rrule, RfcMode.RFC2445_LAX);
			RecurrenceSet rset = new RecurrenceSet();
			rset.setStart(startCalendar);
			rset.addInstances(new RecurrenceRuleAdapter(rule, startCalendar));

			long result = 0;
			int instances = 0;

			RecurrenceSetIterator iterator = rset.iterator();
			iterator.setIterateEnd(rangeEnd);
			long lastdate = Long.MIN_VALUE;
			while (lastdate < rangeEnd && iterator.hasNext())
			{
				result += lastdate = iterator.next();
				++instances;
			}
			this.instances = instances;
			return result;
		}

	};

}
