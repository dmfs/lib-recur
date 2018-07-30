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
 * A filter expands recurrence rules by week of year. This is allowed for yearly rules only. <p> If a BYMONTH part is present and any BY*DAY rules follows this
 * filter also expands weeks that overlap the expanded month. That means two subsequent interval sets can include the same week. The BY*DAY filters will take
 * care of filtering those. </p>
 *
 * @author Marten Gajda
 */
final class ByWeekNoMonthlyExpander extends ByExpander
{
    /**
     * The week numbers to expand.
     */
    private final int[] mByWeekNo;

    private final int mOriginalWeekDay;


    public ByWeekNoMonthlyExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
    {
        super(previous, calendarTools, start);

        mByWeekNo = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYWEEKNO));

        // BYWEEKNO expansion preserves the original day of week
        mOriginalWeekDay = calendarTools.getDayOfWeek(Instance.year(start), Instance.month(start), Instance.dayOfMonth(start));
    }


    @Override
    void expand(long instance, long notBefore)
    {
        int year = Instance.year(instance);
        int month = Instance.month(instance);

        // get the number of weeks in that year
        int yearWeeks = mCalendarMetrics.getWeeksPerYear(year);

        for (int weekOfYear : mByWeekNo)
        {
            int actualWeek = weekOfYear;
            if (weekOfYear < 0)
            {
                actualWeek = yearWeeks + weekOfYear + 1;
            }

            if (actualWeek <= 0 || actualWeek > yearWeeks)
            {
                continue;
            }

            /*
             * Expand instances in this week and month.
             */
            int yearDay = mCalendarMetrics.getYearDayOfIsoYear(year, actualWeek, mOriginalWeekDay);

            if (yearDay < 1 || yearDay > mCalendarMetrics.getDaysPerYear(year))
            {
                // definitely a different month
                continue;
            }

            final int monthAndDay = mCalendarMetrics.getMonthAndDayOfYearDay(year, yearDay);
            final int newMonth = CalendarMetrics.packedMonth(monthAndDay);
            if (newMonth == month)
            {
                addInstance(Instance.setMonthAndDayOfMonth(instance, newMonth,
                        CalendarMetrics.dayOfMonth(monthAndDay)));
            }

        }
    }
}
