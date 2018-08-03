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

package org.dmfs.rfc5545.recur;

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;


/**
 * A filter that expands recurrence rules by day of year. This filter expands instances for YEARLY, MONTHLY and WEEKLY rules. <p> <strong>Note: </strong> <a
 * href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a> Doesn't allow BYYEARDAY to be used with DAILY, WEEKLY and MONTHLY rules, but RFC 2445
 * does. This filter tries to return a reasonable result for these cases. In particular that means we expand MONTHLY and WEEKLY rules and filter DAILY rules.
 * </p>
 *
 * @author Marten Gajda
 */
final class ByYearDayWeeklyExpander extends ByExpander
{
    /**
     * The year days to let pass or to expand.
     */
    private final int[] mYearDays;


    public ByYearDayWeeklyExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarMetrics, long start)
    {
        super(previous, calendarMetrics, start);

        mYearDays = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYYEARDAY));
    }


    @Override
    void expand(long instance, long start)
    {
        int year = Instance.year(instance);
        int month = Instance.month(instance);
        int dayOfMonth = Instance.dayOfMonth(instance);
        int hour = Instance.hour(instance);
        int minute = Instance.minute(instance);
        int second = Instance.second(instance);
        int yearDays = mCalendarMetrics.getDaysPerYear(year);
        for (int day : mYearDays)
        {
            int actualDay = day;
            if (day < 0)
            {
                actualDay = day + yearDays + 1;
            }

            int prevYearDays = mCalendarMetrics.getDaysPerYear(year - 1);
            int nextYearDays = mCalendarMetrics.getDaysPerYear(year + 1);

            int prevYearDay = day;
            int nextYearDay = day;
            if (day < 0)
            {
                prevYearDay = day + prevYearDays + 1;
                nextYearDay = day + nextYearDays + 1;
            }

            int oldWeek = mCalendarMetrics.getWeekOfYear(year, month, dayOfMonth);
            /*
             * Add instance only if the week didn't change.
             */
            int newWeek = mCalendarMetrics.getWeekOfYear(year, actualDay);
            if (0 < actualDay && actualDay <= yearDays && newWeek == oldWeek)
            {
                int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year, actualDay);
                addInstance(Instance.setMonthAndDayOfMonth(instance, CalendarMetrics.packedMonth(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay)));
            }
            else if (0 < nextYearDay && nextYearDay <= nextYearDays && nextYearDay < 7)
            {
                /*
                 * The day might belong to the next year. Add instance only if the week didn't change.
                 */
                newWeek = mCalendarMetrics.getWeekOfYear(year + 1, nextYearDay);
                if (newWeek == oldWeek)
                {
                    int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year + 1, nextYearDay);
                    addInstance(
                            Instance.make(year + 1, CalendarMetrics.packedMonth(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay), hour, minute, second));
                }
            }
            else if (0 < prevYearDay && prevYearDay <= prevYearDays && prevYearDay > prevYearDays - 7)
            {
                /*
                 * The day might belong to the previous year. Add instance only if the week didn't change.
                 */
                newWeek = mCalendarMetrics.getWeekOfYear(year - 1, prevYearDay);
                if (newWeek == oldWeek)
                {
                    int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year - 1, prevYearDay);
                    addInstance(
                            Instance.make(year - 1, CalendarMetrics.packedMonth(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay), hour, minute, second));
                }
            }
        }
    }
}
