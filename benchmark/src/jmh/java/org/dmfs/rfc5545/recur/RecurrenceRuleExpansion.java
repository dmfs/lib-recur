/*
 * Copyright 2020 Marten Gajda <marten@dmfs.org>
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.rfc5545.recur;

import org.dmfs.rfc5545.DateTime;
import org.openjdk.jmh.annotations.*;

import java.util.TimeZone;


/**
 * @author marten
 */
@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@Fork(5)
@BenchmarkMode(Mode.Throughput)
public class RecurrenceRuleExpansion
{
    @State(Scope.Benchmark)
    public static class BenchmarkState
    {

        @Param({ "1", "1000" })
        int iterations;

        @Param({
            "FREQ=YEARLY;BYMONTH=12;BYMONTHDAY=24",
            "FREQ=MONTHLY;BYMONTH=12;BYMONTHDAY=24",
            "FREQ=YEARLY;BYDAY=-2SU,-3SU,-4SU,-5SU",
            "FREQ=MONTHLY;INTERVAL=3;BYDAY=2WE",
            "FREQ=YEARLY;INTERVAL=1;BYDAY=WE;BYMONTHDAY=25,26,27,21,22,23,24;BYMONTH=4",
            "FREQ=MONTHLY;INTERVAL=1;BYDAY=WE;BYMONTHDAY=25,26,27,21,22,23,24;BYMONTH=4",
            "FREQ=YEARLY;BYDAY=MO",
            "FREQ=MONTHLY;BYDAY=MO",
            "FREQ=WEEKLY;BYDAY=MO",
            "FREQ=DAILY;BYDAY=MO",
            "FREQ=YEARLY;BYDAY=MO,TU,WE,TH,FR,SA,SU",
            "FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR,SA,SU",
            "FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR,SA,SU",
            "FREQ=DAILY;BYDAY=MO,TU,WE,TH,FR,SA,SU",
        })
        String rule;

        TimeZone tz = TimeZone.getTimeZone("Europe/Berlin");

        DateTime start = new DateTime(tz, 2020, 3, 1, 10, 30, 0);

        RecurrenceRule recurrenceRule;


        @Setup
        public void setup() throws InvalidRecurrenceRuleException
        {
            recurrenceRule = new RecurrenceRule(rule);
        }
    }


    @Benchmark
    public long benchmarkExpansion(BenchmarkState state)
    {
        RecurrenceRuleIterator iterator = state.recurrenceRule.iterator(state.start);
        long last = 0L;
        int count = state.iterations;
        while (iterator.hasNext() && count-- > 0)
        {
            last = iterator.nextMillis();
        }
        return last;
    }
}
