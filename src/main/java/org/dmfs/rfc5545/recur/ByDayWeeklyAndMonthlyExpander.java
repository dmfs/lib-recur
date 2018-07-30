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
import org.dmfs.rfc5545.recur.RecurrenceRule.WeekdayNum;

import java.util.List;


/**
 * An expander that expands recurrence rules by day of week in a monthly and weekly scope.
 *
 * @author Marten Gajda
 */
final class ByDayWeeklyAndMonthlyExpander extends ByExpander
{
    /**
     * The bitmap of week days to expand.
     */
    private final int mDayBitMap;

    /**
     * The list of months if a BYMONTH part is specified in the rule. We need this to filter by month if the rule has a monthly and weekly scope.
     */
    private final int[] mMonths;


    public ByDayWeeklyAndMonthlyExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
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

        if (rule.hasPart(Part.BYMONTH))
        {
            // we have to filter by month
            mMonths = StaticUtils.ListToArray(rule.getByPart(Part.BYMONTH));
        }
        else
        {
            mMonths = null;
        }
    }


    @Override
    void expand(long instance, long start)
    {
        CalendarMetrics calendarMetrics = mCalendarMetrics;
        int month = Instance.month(instance);

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
            long newInstance = calendarMetrics.setDayOfWeek(instance, day);

            int newMonth = Instance.month(newInstance);

            if (mMonths != null && StaticUtils.linearSearch(mMonths,
                    newMonth) >= 0 || mMonths == null && newMonth == month)
            {
                addInstance(newInstance);
            }
            // goo
            dayBitMap = dayBitMap >> 1;
            day++;
        }
    }
}
