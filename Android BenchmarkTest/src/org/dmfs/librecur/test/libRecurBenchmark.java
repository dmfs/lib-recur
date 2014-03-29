package org.dmfs.librecur.test;

import junit.framework.TestCase;

import org.dmfs.rfc5545.recur.Calendar;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.RfcMode;
import org.dmfs.rfc5545.recur.recurrenceset.RecurrenceRuleAdapter;
import org.dmfs.rfc5545.recur.recurrenceset.RecurrenceSet;
import org.dmfs.rfc5545.recur.recurrenceset.RecurrenceSetIterator;


public class libRecurBenchmark extends TestCase
{

	/**
	 * This test benchmarks the recurrence rule parser.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 * @throws InterruptedException
	 */
	public void testParserBenchmark() throws InvalidRecurrenceRuleException, InterruptedException
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~ lib-recur microbenchmark ~~~~~~~~~~~~~~~~~~~");
		System.out.println("testing recurrence rule parser");

		for (String rule : BenchmarkConditions.BENCHMARK_RULES)
		{

			System.out.println("initializing rule " + rule);
			long initTime = benchmarkParserIterator(rule, BenchmarkConditions.INIT_ITERATIONS, BenchmarkConditions.START_DATE);
			System.out.println("time to init rule: " + initTime + "   iterations: " + BenchmarkConditions.INIT_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.INIT_ITERATIONS * 1000f) / (float) initTime) + "   avg. time per iteration: "
				+ ((initTime * 1000f) / BenchmarkConditions.INIT_ITERATIONS) + " µs");

			// request a gc run and hope it runs within the next 500 ms
			System.gc();
			Thread.sleep(500);

			System.out.println("testing rule " + rule);
			long testTime = benchmarkParserIterator(rule, BenchmarkConditions.TEST_ITERATIONS, BenchmarkConditions.START_DATE);
			System.out.println("time to test rule: " + testTime + "   iterations: " + BenchmarkConditions.TEST_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.TEST_ITERATIONS * 1000f) / (float) testTime) + "   avg. time per iteration: "
				+ ((testTime * 1000f) / BenchmarkConditions.TEST_ITERATIONS) + " µs");

		}
	}


	public static long benchmarkParserIterator(String ruleStr, int iterations, Calendar start) throws InvalidRecurrenceRuleException
	{
		long dummy = 0;
		long testStart = System.nanoTime();
		for (int i = 0; i < iterations; ++i)
		{
			dummy += initParser(ruleStr, start);
		}
		long duration = System.nanoTime() - testStart;
		System.out.println("dummy value: " + dummy);
		return duration / 1000000; // convert to milliseconds
	}


	public static long initParser(String ruleStr, Calendar start) throws InvalidRecurrenceRuleException
	{
		RecurrenceRule rule = new RecurrenceRule(ruleStr, RfcMode.RFC2445_LAX);
		return rule.hashCode();
	}


	/**
	 * This test benchmarks the initialization of the recurrence iterator. This includes the parsing and everything that needs to be done before the first
	 * instance can be iterated. To ensure that everything is ready this method iterates the first instance from every rule.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 * @throws InterruptedException
	 */
	public void testInitializationBenchmark() throws InvalidRecurrenceRuleException, InterruptedException
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~ lib-recur microbenchmark ~~~~~~~~~~~~~~~~~~~");
		System.out.println("testing recurrence iterator initialization");

		for (String rule : BenchmarkConditions.BENCHMARK_RULES)
		{

			System.out.println("initializing rule " + rule);
			long initTime = benchmarkInitIterator(rule, BenchmarkConditions.INIT_ITERATIONS, BenchmarkConditions.START_DATE);
			System.out.println("time to init rule: " + initTime + "   iterations: " + BenchmarkConditions.INIT_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.INIT_ITERATIONS * 1000f) / (float) initTime) + "   avg. time per iteration: "
				+ ((initTime * 1000f) / BenchmarkConditions.INIT_ITERATIONS) + " µs");

			// request a gc run and hope it runs within the next 500 ms
			System.gc();
			Thread.sleep(500);

			System.out.println("testing rule " + rule);
			long testTime = benchmarkInitIterator(rule, BenchmarkConditions.TEST_ITERATIONS, BenchmarkConditions.START_DATE);
			System.out.println("time to test rule: " + testTime + "   iterations: " + BenchmarkConditions.TEST_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.TEST_ITERATIONS * 1000f) / (float) testTime) + "   avg. time per iteration: "
				+ ((testTime * 1000f) / BenchmarkConditions.TEST_ITERATIONS) + " µs");

		}
	}


	public static long benchmarkInitIterator(String ruleStr, int iterations, Calendar start) throws InvalidRecurrenceRuleException
	{
		long dummy = 0;
		long testStart = System.nanoTime();
		for (int i = 0; i < iterations; ++i)
		{
			dummy += initIterator(ruleStr, start);
		}
		long duration = System.nanoTime() - testStart;
		System.out.println("dummy value: " + dummy);
		return duration / 1000000; // convert to milliseconds
	}


	public static long initIterator(String ruleStr, Calendar start) throws InvalidRecurrenceRuleException
	{
		RecurrenceRule rule = new RecurrenceRule(ruleStr, RfcMode.RFC2445_LAX);
		RecurrenceSet rset = new RecurrenceSet();
		rset.setStart(start);
		rset.addInstances(new RecurrenceRuleAdapter(rule, start));

		return rset.iterator().next();
	}


	/**
	 * This test benchmarks the initialization of the recurrence iterator. This includes the parsing and everything that needs to be done before the first
	 * instance can be iterated. To ensure that everything is ready this method iterates the first instance from every rule.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 * @throws InterruptedException
	 */
	public void testIterate10YearsBenchmark() throws InvalidRecurrenceRuleException, InterruptedException
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~ lib-recur microbenchmark ~~~~~~~~~~~~~~~~~~~");
		System.out.println("testing recurrence iterations over 10 years");

		for (String rule : BenchmarkConditions.BENCHMARK_RULES)
		{
			long startlong = BenchmarkConditions.START_DATE.getTimeInMillis();
			long endlong = startlong + 10L * 365L * 24L * 3600L * 1000L;

			System.out.println("initializing rule " + rule);
			long initTime = benchmarkIterate(rule, BenchmarkConditions.INIT_ITERATIONS, BenchmarkConditions.START_DATE, startlong, endlong);
			System.out.println("time to init rule: " + initTime + "   iterations: " + BenchmarkConditions.INIT_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.INIT_ITERATIONS * 1000f) / (float) initTime) + "   avg. time per iteration: "
				+ ((initTime * 1000f) / BenchmarkConditions.INIT_ITERATIONS) + " µs");

			// request a gc run and hope it runs within the next 500 ms
			System.gc();
			Thread.sleep(500);

			System.out.println("testing rule " + rule);
			long testTime = benchmarkIterate(rule, BenchmarkConditions.TEST_ITERATIONS, BenchmarkConditions.START_DATE, startlong, endlong);
			System.out.println("time to test rule: " + testTime + "   iterations: " + BenchmarkConditions.TEST_ITERATIONS + "   iterations per second: "
				+ ((BenchmarkConditions.TEST_ITERATIONS * 1000f) / (float) testTime) + "   avg. time per iteration: "
				+ ((testTime * 1000f) / BenchmarkConditions.TEST_ITERATIONS) + " µs");

		}
	}


	public static long benchmarkIterate(String ruleStr, int iterations, Calendar start, long rangeStart, long rangeEnd) throws InvalidRecurrenceRuleException
	{
		long dummy = 0;
		long testStart = System.nanoTime();
		for (int i = 0; i < iterations; ++i)
		{
			dummy += iterateRange(ruleStr, start, rangeStart, rangeEnd);
		}
		long duration = System.nanoTime() - testStart;
		System.out.println("dummy value: " + dummy);
		return duration / 1000000; // convert to milliseconds
	}


	public static long iterateRange(String ruleStr, Calendar start, long rangeStart, long rangeEnd) throws InvalidRecurrenceRuleException
	{
		RecurrenceRule rule = new RecurrenceRule(ruleStr, RfcMode.RFC2445_LAX);
		RecurrenceSet rset = new RecurrenceSet();
		rset.setStart(start);
		rset.addInstances(new RecurrenceRuleAdapter(rule, start));

		long result = 0;

		RecurrenceSetIterator iterator = rset.iterator();
		long lastdate = Long.MIN_VALUE;
		while (lastdate < rangeEnd && iterator.hasNext())
		{
			result += lastdate = iterator.next();
		}
		return result;
	}

}
