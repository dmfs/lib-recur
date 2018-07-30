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
 * An expander that expands recurrence rules by day of week in a monthly scope.
 *
 * @author Marten Gajda
 */
final class ByDayMonthlyExpander extends ByExpander
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


    public ByDayMonthlyExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
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
        int month = Instance.month(instance);

        for (int packedDay : mByDay)
        {
            int pos = unpackPos(packedDay);
            int day = unpackWeekday(packedDay);

            // get the first week day and the number of days of this month
            int weekDayOfFirstInMonth = calendarMetrics.getDayOfWeek(year, month, 1);
            int monthDays = calendarMetrics.getDaysPerPackedMonth(year, month);

            // get the first instance of the weekday in this month
            int firstDay = (day - weekDayOfFirstInMonth + 7) % 7 + 1;

            if (pos == 0)
            {
                // add all instances of this weekday of this month
                for (int dayOfMonth = firstDay; dayOfMonth <= monthDays; dayOfMonth += 7)
                {
                    addInstance(Instance.setDayOfMonth(instance, dayOfMonth));
                }
            }
            else
            {
                int maxPos = 1 + (monthDays - firstDay) / 7;

                // add just one position
                if (pos > 0 && pos <= maxPos || pos < 0 && pos + maxPos + 1 > 0)
                {
                    addInstance(Instance.setDayOfMonth(instance,
                            firstDay + (pos > 0 ? pos - 1 : pos + maxPos) * 7));
                }
            }
        }
    }
}
