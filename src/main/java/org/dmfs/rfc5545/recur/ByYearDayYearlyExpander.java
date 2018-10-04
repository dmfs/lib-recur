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
final class ByYearDayYearlyExpander extends ByExpander
{
    /**
     * The year days to let pass or to expand.
     */
    private final int[] mYearDays;


    public ByYearDayYearlyExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarMetrics, long start)
    {
        super(previous, calendarMetrics, start);

        mYearDays = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYYEARDAY));
    }


    @Override
    void expand(long instance, long start)
    {
        int year = Instance.year(instance);
        int yearDays = mCalendarMetrics.getDaysPerYear(year);
        int startDayOfYear = mCalendarMetrics.getDayOfYear(Instance.year(start), Instance.month(start), Instance.dayOfMonth(start));
        for (int day : mYearDays)
        {
            int actualDay = day;
            if (day < 0)
            {
                actualDay = day + yearDays + 1;
            }

            if (0 < actualDay && actualDay <= yearDays && !(actualDay < startDayOfYear && year == Instance.year(start)))
            {
                int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year, actualDay);
                addInstance(Instance.setMonthAndDayOfMonth(instance, CalendarMetrics.packedMonth(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay)));
            }
            else if (actualDay <= 0)
            {
                addInstance(Instance.setMonthAndDayOfMonth(instance, 0, 0));
            }
            else if (actualDay > yearDays)
            {
                int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year, yearDays);
                addInstance(Instance.setMonthAndDayOfMonth(instance, CalendarMetrics.packedMonth(monthAndDay), CalendarMetrics.dayOfMonth(monthAndDay) + 1));
            }
        }
    }
}
