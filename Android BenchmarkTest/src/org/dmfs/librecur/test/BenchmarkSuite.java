package org.dmfs.librecur.test;

import java.math.BigInteger;

import junit.framework.TestCase;
import android.util.Log;


public abstract class BenchmarkSuite extends TestCase
{
	private final String name;


	public BenchmarkSuite()
	{
		name = this.getClass().getSimpleName();
	}


	public void testRunBenchmarks() throws Exception
	{
		for (String rule : BenchmarkCommons.BENCHMARK_RULES)
		{
			Log.v(name, String.format("%50s, %10s, %10s, %12s, %12s, %10s, %15s, %10s, %18s", "rule", "iterations", "time/ms", "iterations/s", "µs/iteration",
				"instances", "µs/instance", "memory", "checksum"));
			for (BenchmarkParams params : getBenchmarkParams())
			{

				Log.v(".", "initializing " + rule);
				runBenchmark(params, rule, BenchmarkCommons.INIT_ITERATIONS);

				Log.v(".", "testing " + rule);
				runBenchmark(params, rule, BenchmarkCommons.TEST_ITERATIONS);
			}
		}
	}


	public void runBenchmark(BenchmarkParams benchmarkParams, String rule, int iterations) throws Exception
	{
		long time = benchmarkParams.benchmark.runBenchmark(rule, iterations, benchmarkParams.startCalendar, benchmarkParams.startTime,
			benchmarkParams.rangeStart, benchmarkParams.rangeEnd);

		Log.v(benchmarkParams.benchmark.name, String.format("%50s, %10d, %10.2f, %12.2f, %12.2f, %10d, %15.2f, %10d, %18s", rule, iterations,
			(double) time / 1000000d, (iterations * 1000000000d) / time, (double) time / (iterations * 1000d), benchmarkParams.benchmark.instances,
			benchmarkParams.benchmark.instances > 0 ? (double) time / (iterations * benchmarkParams.benchmark.instances * 1000d) : Double.NaN,
			benchmarkParams.benchmark.mem, BigInteger.valueOf(benchmarkParams.benchmark.result).toString(16)));
	}


	public abstract BenchmarkParams[] getBenchmarkParams();
}
