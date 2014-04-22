package org.dmfs.librecur.test;

public class BenchmarkCommons
{

	public final static String[] BENCHMARK_RULES = { "FREQ=YEARLY", "FREQ=YEARLY;BYMONTH=3;BYMONTHDAY=3", "FREQ=MONTHLY", "FREQ=MONTHLY;BYMONTHDAY=3,15,31",
		"FREQ=MONTHLY;BYDAY=-1SU", "FREQ=DAILY", "FREQ=WEEKLY", "FREQ=WEEKLY;INTERVAL=2;BYDAY=SA,SU", "FREQ=WEEKLY;BYDAY=MO" };

	public final static int INIT_ITERATIONS = 10000;
	public final static int TEST_ITERATIONS = 100000;

}
