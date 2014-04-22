package org.dmfs.librecur.test;

import org.dmfs.rfc5545.recur.Calendar;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;

import android.text.format.Time;

import com.android.calendarcommon2.DateException;
import com.android.calendarcommon2.RecurrenceProcessor;
import com.android.calendarcommon2.RecurrenceSet;


public class calendarcommonBench extends BenchmarkSuite
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

	public static Benchmark PARSER_BENCHMARK = new Benchmark("Parser Benchmark   ")
	{

		@Override
		public long run(String rrule, Calendar startCalendar, Time startTime, long rangeStart, long rangeEnd) throws InvalidRecurrenceRuleException
		{
			RecurrenceSet rset = new RecurrenceSet(rrule, null, null, null);
			return rset.hashCode();
		}

	};

	public static Benchmark ITERATE_BENCHMARK = new Benchmark("Iteration Benchmark")
	{

		@Override
		public long run(String rrule, Calendar startCalendar, Time startTime, long rangeStart, long rangeEnd) throws InvalidRecurrenceRuleException,
			DateException
		{
			RecurrenceSet rset = new RecurrenceSet(rrule, null, null, null);
			RecurrenceProcessor rp = new RecurrenceProcessor();
			long[] instances = rp.expand(startTime, rset, rangeStart, rangeEnd + 1000);
			long result = 0;
			for (long i : instances)
			{
				result += i;
			}
			this.instances = instances.length;
			return result;
		}

	};
}
