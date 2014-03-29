package org.dmfs.librecur.test;

import junit.framework.TestCase;

import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;

import android.text.format.Time;

import com.android.calendarcommon2.DateException;
import com.android.calendarcommon2.RecurrenceProcessor;
import com.android.calendarcommon2.RecurrenceSet;


public class calendarcommonBenchmark extends TestCase
{

	/**
	 * This test benchmarks the recurrence rule parser.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 * @throws InterruptedException
	 * @throws DateException
	 */
	public void testParserBenchmark() throws InterruptedException, DateException, InvalidRecurrenceRuleException
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~ calendarcommon microbenchmark ~~~~~~~~~~~~~~~~~~~");
		System.out.println("testing recurrence rule parser");

		for (String rule : BenchmarkConditions.BENCHMARK_RULES)
		{
			long intervalStart = BenchmarkConditions.START_DATE2.toMillis(false);

			System.out.println("initializing rule " + rule);
			long initTime = benchmarkParser(rule, BenchmarkConditions.INIT_ITERATIONS, BenchmarkConditions.START_DATE2, intervalStart, intervalStart + 1);
			System.out.println("time to init rule: " + initTime + "   iterations: " + BenchmarkConditions.INIT_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.INIT_ITERATIONS * 1000f) / (float) initTime) + "   avg. time per iteration: "
				+ ((initTime * 1000f) / BenchmarkConditions.INIT_ITERATIONS) + " µs");

			// request a gc run and hope it runs within the next 500 ms
			System.gc();
			Thread.sleep(500);

			System.out.println("testing rule " + rule);
			long testTime = benchmarkParser(rule, BenchmarkConditions.TEST_ITERATIONS, BenchmarkConditions.START_DATE2, intervalStart, intervalStart + 1);
			System.out.println("time to test rule: " + testTime + "   iterations: " + BenchmarkConditions.TEST_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.TEST_ITERATIONS * 1000f) / (float) testTime) + "   avg. time per iteration: "
				+ ((testTime * 1000f) / BenchmarkConditions.TEST_ITERATIONS) + " µs");

		}
	}


	public static long benchmarkParser(String ruleStr, int iterations, Time start, long intervalStart, long intervalEnd) throws DateException
	{
		long dummy = 0;
		long testStart = System.nanoTime();
		for (int i = 0; i < iterations; ++i)
		{
			dummy += initParser(ruleStr, start, intervalStart, intervalEnd);
		}
		long duration = System.nanoTime() - testStart;
		System.out.println("dummy value: " + dummy);
		return duration / 1000000; // convert to milliseconds
	}


	public static long initParser(String ruleStr, Time start, long intervalStart, long intervalEnd) throws DateException
	{
		RecurrenceSet rset = new RecurrenceSet(ruleStr, null, null, null);
		return rset.hashCode();
	}


	/**
	 * This test benchmarks the initialization of the recurrence iterator. This includes the parsing and everything that needs to be done before the first
	 * instance can be iterated. To ensure that everything is ready this method iterates the first instance from every rule.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 * @throws InterruptedException
	 * @throws DateException
	 */
	public void testInitializationBenchmark() throws InterruptedException, DateException, InvalidRecurrenceRuleException
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~ calendarcommon microbenchmark ~~~~~~~~~~~~~~~~~~~");
		System.out.println("testing recurrence iterator initialization");

		for (String rule : BenchmarkConditions.BENCHMARK_RULES)
		{
			long intervalStart = BenchmarkConditions.START_DATE2.toMillis(false);

			System.out.println("initializing rule " + rule);
			long initTime = benchmarkInitIterator(rule, BenchmarkConditions.INIT_ITERATIONS, BenchmarkConditions.START_DATE2, intervalStart, intervalStart + 1);
			System.out.println("time to init rule: " + initTime + "   iterations: " + BenchmarkConditions.INIT_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.INIT_ITERATIONS * 1000f) / (float) initTime) + "   avg. time per iteration: "
				+ ((initTime * 1000f) / BenchmarkConditions.INIT_ITERATIONS) + " µs");

			// request a gc run and hope it runs within the next 500 ms
			System.gc();
			Thread.sleep(500);

			System.out.println("testing rule " + rule);
			long testTime = benchmarkInitIterator(rule, BenchmarkConditions.TEST_ITERATIONS, BenchmarkConditions.START_DATE2, intervalStart, intervalStart + 1);
			System.out.println("time to test rule: " + testTime + "   iterations: " + BenchmarkConditions.TEST_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.TEST_ITERATIONS * 1000f) / (float) testTime) + "   avg. time per iteration: "
				+ ((testTime * 1000f) / BenchmarkConditions.TEST_ITERATIONS) + " µs");

		}
	}


	public static long benchmarkInitIterator(String ruleStr, int iterations, Time start, long intervalStart, long intervalEnd) throws DateException
	{
		long dummy = 0;
		long testStart = System.nanoTime();
		for (int i = 0; i < iterations; ++i)
		{
			dummy += initIterator(ruleStr, start, intervalStart, intervalEnd);
		}
		long duration = System.nanoTime() - testStart;
		System.out.println("dummy value: " + dummy);
		return duration / 1000000; // convert to milliseconds
	}


	public static long initIterator(String ruleStr, Time start, long intervalStart, long intervalEnd) throws DateException
	{
		RecurrenceSet rset = new RecurrenceSet(ruleStr, null, null, null);
		RecurrenceProcessor rp = new RecurrenceProcessor();
		return rp.expand(start, rset, intervalStart, intervalEnd)[0];
	}


	/**
	 * This test benchmarks the initialization of the recurrence iterator. This includes the parsing and everything that needs to be done before the first
	 * instance can be iterated. To ensure that everything is ready this method iterates the first instance from every rule.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 * @throws InterruptedException
	 * @throws DateException
	 */
	public void testIterate10YearsBenchmark() throws InterruptedException, DateException, InvalidRecurrenceRuleException
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~ calendarcommon microbenchmark ~~~~~~~~~~~~~~~~~~~");
		System.out.println("testing recurrence iterations over 10 years");

		for (String rule : BenchmarkConditions.BENCHMARK_RULES)
		{
			long intervalStart = BenchmarkConditions.START_DATE2.toMillis(false);
			long intervalEnd = intervalStart + 10L * 365L * 24L * 3600L * 1000L;

			System.out.println("initializing rule " + rule);
			long initTime = benchmarkInitIterator(rule, BenchmarkConditions.INIT_ITERATIONS, BenchmarkConditions.START_DATE2, intervalStart, intervalEnd);
			System.out.println("time to init rule: " + initTime + "   iterations: " + BenchmarkConditions.INIT_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.INIT_ITERATIONS * 1000f) / (float) initTime) + "   avg. time per iteration: "
				+ ((initTime * 1000f) / BenchmarkConditions.INIT_ITERATIONS) + " µs");

			// request a gc run and hope it runs within the next 500 ms
			System.gc();
			Thread.sleep(500);

			System.out.println("testing rule " + rule);
			long testTime = benchmarkInitIterator(rule, BenchmarkConditions.TEST_ITERATIONS, BenchmarkConditions.START_DATE2, intervalStart, intervalEnd);
			System.out.println("time to test rule: " + testTime + "   iterations: " + BenchmarkConditions.TEST_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.TEST_ITERATIONS * 1000f) / (float) testTime) + "   avg. time per iteration: "
				+ ((testTime * 1000f) / BenchmarkConditions.TEST_ITERATIONS) + " µs");

		}
	}

}
