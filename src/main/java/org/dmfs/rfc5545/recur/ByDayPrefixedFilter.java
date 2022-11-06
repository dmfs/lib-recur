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

import org.dmfs.jems2.BiFunction;
import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.Weekday;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;

import java.util.Map;
import java.util.Set;


/**
 * A filter that limits recurrence rules by day of week.
 *
 * @author Marten Gajda
 */
final class ByDayPrefixedFilter implements ByFilter
{
    public enum Scope
    {
        MONTH(
            (instance, metrics) ->
                (Instance.dayOfMonth(instance) - 1) / 7 + 1,
            (instance, metrics) ->
                (Instance.dayOfMonth(instance) - metrics.getDaysPerPackedMonth(Instance.year(instance), Instance.month(instance))) / 7 - 1),
        YEAR(
            (instance, metrics) ->
                (metrics.getDayOfYear(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance)) - 1) / 7 + 1,
            (instance, metrics) ->
                (metrics.getDayOfYear(Instance.year(instance), Instance.month(instance), Instance.dayOfMonth(instance))
                    - metrics.getDaysPerYear(Instance.year(instance))) / 7 - 1);

        private final BiFunction<Long, CalendarMetrics, Integer> mNthDay;
        private final BiFunction<Long, CalendarMetrics, Integer> mNthLastDay;


        Scope(BiFunction<Long, CalendarMetrics, Integer> nthDay, BiFunction<Long, CalendarMetrics, Integer> nthLastDay)
        {
            mNthDay = nthDay;
            mNthLastDay = nthLastDay;
        }
    }


    /**
     * The {@link CalendarMetrics} to use.
     */
    private final CalendarMetrics mCalendarMetrics;
    private final Map<Weekday, Set<Integer>> mPrefixedWeekDays;
    private final Scope mScope;
    private final Weekday[] mWeekdayArray = Weekday.values();


    public ByDayPrefixedFilter(
        CalendarMetrics calendarMetrics,
        Map<Weekday, Set<Integer>> prefixedWeekDays,
        Scope scope)
    {
        mCalendarMetrics = calendarMetrics;
        mPrefixedWeekDays = prefixedWeekDays;
        mScope = scope;
    }


    @Override
    public boolean filter(long instance)
    {
        Set<Integer> prefixes = mPrefixedWeekDays.get(mWeekdayArray[mCalendarMetrics.getDayOfWeek(
            Instance.year(instance),
            Instance.month(instance),
            Instance.dayOfMonth(instance))]);
        return prefixes == null
            || (!prefixes.contains(mScope.mNthDay.value(instance, mCalendarMetrics))
            && !prefixes.contains(mScope.mNthLastDay.value(instance, mCalendarMetrics)));
    }
}
