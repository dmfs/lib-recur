package org.dmfs.librecur.test;

public class BenchmarkCommons
{

	public final static String[] BENCHMARK_RULES = { "FREQ=YEARLY", "FREQ=YEARLY;BYMONTH=4;BYMONTHDAY=25", "FREQ=MONTHLY", "FREQ=MONTHLY;BYMONTHDAY=1,15,31",
		"FREQ=MONTHLY;BYDAY=-1SU", "FREQ=DAILY", "FREQ=WEEKLY", "FREQ=WEEKLY;BYDAY=SA,SU", "FREQ=WEEKLY;BYDAY=MO;COUNT=20" };

	public final static int INIT_ITERATIONS = 10000;
	public final static int TEST_ITERATIONS = 100000;

}
