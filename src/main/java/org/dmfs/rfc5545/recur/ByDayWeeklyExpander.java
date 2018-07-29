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

import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.recur.RecurrenceRule.WeekdayNum;

import java.util.List;


/**
 * An expander that expands recurrence rules by day of week in a weekly scope.
 *
 * @author Marten Gajda
 */
final class ByDayWeeklyExpander extends ByExpander
{
    /**
     * The bitmap of week days to expand.
     */
    private final int mDayBitMap;


    public ByDayWeeklyExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
    {
        super(previous, calendarTools, start);

        // get the list of WeekDayNums and convert it into an array
        List<WeekdayNum> byDay = rule.getByDayPart();

        int dayBitMap = 0;

        for (WeekdayNum weekdayNum : byDay)
        {
            if (weekdayNum.pos == 0) // ignore any positional days
            {
                dayBitMap |= 1 << weekdayNum.weekday.ordinal();
            }
        }
        mDayBitMap = dayBitMap;
    }


    @Override
    void expand(long instance, long start)
    {
        CalendarMetrics calendarMetrics = mCalendarMetrics;

        int dayBitMap = mDayBitMap;
        int day = 0;
        while (dayBitMap > 0)
        {
            while ((dayBitMap & 1) == 0)
            {
                // fast forward to the next bit
                dayBitMap = dayBitMap >> 1;
                day += 1;
            }
            addInstance(calendarMetrics.setDayOfWeek(instance, day));
            // go to next day
            dayBitMap = dayBitMap >> 1;
            day++;
        }
    }
}
