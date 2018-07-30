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
import org.dmfs.rfc5545.recur.RecurrenceRule.WeekdayNum;

import java.util.List;


/**
 * An expander that expands recurrence rules by day of week in a yearly scope.
 *
 * @author Marten Gajda
 */
final class ByDayYearlyExpander extends ByExpander
{
    /**
     * The list of week days to expand.
     */
    private final int[] mByDay;


    /**
     * Get a packed representation of a {@link WeekdayNum}.
     *
     * @param pos
     *         The position of the day or <code>0</code>.
     * @param day
     *         The number of the weekday.
     *
     * @return An int that contains the position and the weekday.
     */
    private static int packWeekday(int pos, int day)
    {
        return (pos << 8) + day;
    }


    /**
     * Get the weekday part of a packed day.
     *
     * @param packedDay
     *         The packed day int.
     *
     * @return The weekday.
     */
    private static int unpackWeekday(int packedDay)
    {
        return packedDay & 0xff;
    }


    /**
     * Get the positional part of a packed day.
     *
     * @param packedDay
     *         The packed day int.
     *
     * @return The position.
     */
    private static int unpackPos(int packedDay)
    {
        return packedDay >> 8;
    }


    public ByDayYearlyExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
    {
        super(previous, calendarTools, start);

        // get the list of WeekDayNums and convert it into an array
        List<WeekdayNum> byDay = rule.getByDayPart();
        mByDay = new int[byDay.size()];

        for (int i = 0, l = byDay.size(); i < l; ++i)
        {
            WeekdayNum weekdayNum = byDay.get(i);
            mByDay[i] = packWeekday(weekdayNum.pos, weekdayNum.weekday.ordinal());
        }
    }


    @Override
    void expand(long instance, long start)
    {
        CalendarMetrics calendarMetrics = mCalendarMetrics;
        int year = Instance.year(instance);

        for (int packedDay : mByDay)
        {
            int pos = unpackPos(packedDay);
            int day = unpackWeekday(packedDay);

            // calculate the first occurrence of this weekday in this year
            int firstWeekdayOfYear = (day - calendarMetrics.getWeekDayOfFirstYearDay(year) + 7) % 7 + 1;

            int yearDays = calendarMetrics.getDaysPerYear(year);

            if (pos == 0)
            {
                // add an instance for every occurrence of this week day.
                for (int dayOfYear = firstWeekdayOfYear; dayOfYear <= yearDays; dayOfYear += 7)
                {
                    int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, dayOfYear);
                    addInstance(
                            Instance.setMonthAndDayOfMonth(instance, CalendarMetrics.packedMonth(monthAndDay),
                                    CalendarMetrics.dayOfMonth(monthAndDay)));
                }
            }
            else
            {
                if (pos > 0)
                {
                    int dayOfYear = firstWeekdayOfYear + (pos - 1) * 7;
                    if (dayOfYear <= yearDays)
                    {
                        int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, dayOfYear);
                        addInstance(Instance.setMonthAndDayOfMonth(instance,
                                CalendarMetrics.packedMonth(monthAndDay),
                                CalendarMetrics.dayOfMonth(monthAndDay)));
                    }
                }
                else
                {
                    // calculate the last occurrence of this weekday in this year
                    int lastWeekdayOfYear = firstWeekdayOfYear + yearDays - yearDays % 7;
                    if (lastWeekdayOfYear > yearDays)
                    {
                        // we have ended up in the next year, go back one week
                        lastWeekdayOfYear -= 7;
                    }

                    // calculate the actual instance
                    int dayOfYear = lastWeekdayOfYear + (pos + 1) * 7;
                    if (dayOfYear > 0)
                    {
                        int monthAndDay = calendarMetrics.getMonthAndDayOfYearDay(year, dayOfYear);
                        addInstance(Instance.setMonthAndDayOfMonth(instance,
                                CalendarMetrics.packedMonth(monthAndDay),
                                CalendarMetrics.dayOfMonth(monthAndDay)));
                    }
                }
            }
        }
    }
}
