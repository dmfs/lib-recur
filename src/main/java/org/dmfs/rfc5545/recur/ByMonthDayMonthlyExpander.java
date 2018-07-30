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
 * An expander that expands recurrence rules by day of month. This expander expands instances for YEARLY, MONTHLY rules.
 *
 * @author Marten Gajda
 */
final class ByMonthDayMonthlyExpander extends ByExpander
{
    /**
     * A list of days of month to expand.
     */
    private final int[] mMonthDays;


    public ByMonthDayMonthlyExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
    {
        super(previous, calendarTools, start);

        // get a sorted list of the month days
        mMonthDays = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYMONTHDAY));
    }


    @Override
    void expand(long instance, long start)
    {
        CalendarMetrics calendarMetrics = mCalendarMetrics;

        int year = Instance.year(instance);
        int month = Instance.month(instance);

        int monthDays = calendarMetrics.getDaysPerPackedMonth(year, month);
        for (int day : mMonthDays)
        {
            int actualDay = day;
            if (day < 0)
            {
                actualDay = day + monthDays + 1;
            }
            /*
             * Expand all days in the current month. The SanityFilter will remove all instances before start.
             */
            if (0 < actualDay)
            {
                addInstance(Instance.setDayOfMonth(instance, actualDay));
            }
        }
    }
}
