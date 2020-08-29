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

import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.iterables.decorators.Sieved;
import org.dmfs.jems.function.FragileFunction;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.predicate.composite.Not;
import org.dmfs.jems.procedure.composite.ForEach;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recurrenceset.RecurrenceRuleAdapter;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSet;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSetIterator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

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


        @Setup
        public void setup()
        {
            recurrenceSet = new RecurrenceSet();
            new ForEach<>(new RuleAdapters(instances)).process(recurrenceSet::addInstances);
            new ForEach<>(new RuleAdapters(exceptions)).process(recurrenceSet::addExceptions);
        }
    }


    @Benchmark
    public long testExpansion(BenchmarkState state)
    {
        RecurrenceSetIterator r = state.recurrenceSet.iterator(TimeZone.getDefault(), DateTime.now().getTimestamp());
        long last = 0L;
        int count = state.iterations;
        while (r.hasNext() && count-- > 0)
        {
            last = r.next();
        }
        return last;
    }


    private static class RuleAdapters extends DelegatingIterable<RecurrenceRuleAdapter>
    {

        public RuleAdapters(String ruleString)
        {
            super(new Mapped<>(
                    RecurrenceRuleAdapter::new,
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
