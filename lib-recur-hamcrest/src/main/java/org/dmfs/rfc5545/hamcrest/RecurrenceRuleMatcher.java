/*
 * Copyright 2018 Marten Gajda <marten@dmfs.org>
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

package org.dmfs.rfc5545.hamcrest;

import org.dmfs.jems2.Function;
import org.dmfs.jems2.iterable.Joined;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.core.AllOf;

import static org.dmfs.rfc5545.hamcrest.GeneratorMatcher.generates;
import static org.dmfs.rfc5545.hamcrest.IncreasingMatcher.increasing;
import static org.hamcrest.Matchers.*;


/**
 * @author Marten Gajda
 */
public final class RecurrenceRuleMatcher
{
    public static Function<DateTime, Matcher<RecurrenceRule>> startingWith(String... dates)
    {
        return startingWith(new Seq<>(dates));
    }


    public static Function<DateTime, Matcher<RecurrenceRule>> startingWith(Iterable<String> dates)
    {
        return dateTime -> generates(dateTime, new Mapped<>(DateTime::parse, dates));
    }


    public static Function<DateTime, Matcher<RecurrenceRule>> results(int n)
    {
        return dateTime -> ResultsMatcher.results(dateTime, n);
    }


    public static Function<DateTime, Matcher<RecurrenceRule>> results(Matcher<Integer> matcher)
    {
        return dateTime -> ResultsMatcher.results(dateTime, matcher);
    }


    public static Function<DateTime, Matcher<RecurrenceRule>> instances(Matcher<DateTime> dtMatchers)
    {
        return dateTime -> InstancesMatcher.instances(dateTime, dtMatchers);
    }


    public static Function<DateTime, Matcher<RecurrenceRule>> walking()
    {
        return WalkingStartMatcher::walking;
    }


    public static Matcher<RecurrenceRule> validRule(DateTime start, Iterable<Function<DateTime, Matcher<RecurrenceRule>>> matcherFunctions)
    {
        return new AllOf<>(new Joined<>(new Seq<>(increasing(start)), new Mapped<>(f -> f.value(start), matcherFunctions)));
    }


    @SafeVarargs
    public static Matcher<RecurrenceRule> validRule(DateTime start, Function<DateTime, Matcher<RecurrenceRule>>... matcherFunctions)
    {
        return validRule(start, new Seq<>(matcherFunctions));
    }


    /**
     * This is just an alias to {@link CoreMatchers#is(Matcher)} which works better when being uses in a plural context.
     */
    public static <T> Matcher<T> are(Matcher<T> matcher)
    {
        Description description = new StringDescription();
        matcher.describeTo(description);
        return describedAs(String.format("are %s", description.toString()), is(matcher));
    }


    /**
     * This is just an alias to {@link CoreMatchers#is(Matcher)} which works better when being uses in a plural context.
     */
    @SafeVarargs
    public static <T> Matcher<T> are(Matcher<T>... matcher)
    {
        return are(allOf(matcher));
    }
}
