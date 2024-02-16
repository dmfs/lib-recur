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

import org.dmfs.jems2.FragileFunction;
import org.dmfs.jems2.Function;
import org.dmfs.jems2.iterable.DelegatingIterable;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.predicate.Not;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.RecurrenceSet;
import org.dmfs.rfc5545.recurrenceset.Difference;
import org.dmfs.rfc5545.recurrenceset.Merged;
import org.dmfs.rfc5545.recurrenceset.OfRule;
import org.openjdk.jmh.annotations.*;

import java.util.TimeZone;


/**
 * @author marten
 */
@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@Fork(5)
@BenchmarkMode(Mode.Throughput)
public class RecurrenceSetExpansion
{
    @State(Scope.Benchmark)
    public static class BenchmarkState
    {

        @Param({ "1", "1000" })
        int iterations;

        @Param({
            "FREQ=DAILY;BYDAY=MO,TU,WE",
            "FREQ=DAILY;BYDAY=MO,TU,WE" + ":" + "FREQ=DAILY;BYDAY=WE,TH,FR" + ":" + "FREQ=DAILY;BYDAY=SU,SO" })
        String instances;

        @Param({
            "",
            "FREQ=DAILY;BYDAY=MO",
            "FREQ=DAILY;BYDAY=MO" + ":" + "FREQ=DAILY;BYDAY=WE" + ":" + "FREQ=DAILY;BYDAY=WE,FR" })
        String exceptions;

        RecurrenceSet recurrenceSet;

        DateTime start = new DateTime(TimeZone.getTimeZone("Europe/Berlin"), 2024, 1, 16, 14, 1, 0);

        @Setup
        public void setup()
        {
            if (!exceptions.isEmpty())
            {
                recurrenceSet = new Difference(
                    new Merged(new RuleSets(instances, start)),
                    new Merged(new RuleSets(exceptions, start))
                );
            }
            else
            {
                recurrenceSet = new Merged(new RuleSets(instances, start));
            }
        }
    }


    @Benchmark
    public DateTime testExpansion(BenchmarkState state)
    {
        DateTime last = null;
        int count = state.iterations;
        for (DateTime dt : state.recurrenceSet)
        {
            last = dt;
            if (--count == 0)
            {
                break;
            }
        }
        return last;
    }


    private static class RuleSets extends DelegatingIterable<RecurrenceSet>
    {

        public RuleSets(String ruleString, DateTime start)
        {
            super(new Mapped<>(
                rule -> new OfRule(rule, start),
                new Mapped<>(
                    new Unchecked<>(RecurrenceRule::new),
                    new Sieved<>(new Not<>(String::isEmpty),
                        new Seq<>(ruleString.split(":"))))));
        }
    }


    /**
     * TODO: this should be provided by jems
     */
    private static class Unchecked<Arg, Res> implements Function<Arg, Res>
    {

        private final FragileFunction<? super Arg, ? extends Res, ?> mDelegate;


        private Unchecked(FragileFunction<? super Arg, ? extends Res, ?> mDelegate)
        {
            this.mDelegate = mDelegate;
        }


        @Override
        public Res value(Arg arg)
        {
            try
            {
                return mDelegate.value(arg);
            }
            catch (Throwable throwable)
            {
                throw new RuntimeException("Error", throwable);
            }
        }
    }
}
