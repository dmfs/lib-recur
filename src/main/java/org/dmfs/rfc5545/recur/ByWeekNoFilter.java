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
 * A filter that limits recurrence rules by week number. Note, neither RFC 5545 nor RFC 2445 specify filtering by week number. This is meant for internal use
 * only.
 *
 * @author Marten Gajda
 */
final class ByWeekNoFilter implements ByFilter
{
    /**
     * An array of the week numbers.
     */
    private final int[] mWeekNumbers;

    /**
     * The {@link CalendarMetrics} to use.
     */
    final CalendarMetrics mCalendarMetrics;


    public ByWeekNoFilter(RecurrenceRule rule, CalendarMetrics calendarMetrics)
    {
        mCalendarMetrics = calendarMetrics;
        mWeekNumbers = StaticUtils.ListToSortedArray(rule.getByPart(Part.BYWEEKNO));
    }


    @Override
    public boolean filter(long instance)
    {
        int year = Instance.year(instance);
        int week = mCalendarMetrics.getWeekOfYear(year, Instance.month(instance), Instance.dayOfMonth(instance));
        int weeks;
        if (week > 10 && Instance.month(instance) == 1)
        {
            // week belongs to the previous iso year
            weeks = mCalendarMetrics.getWeeksPerYear(year - 1);
        }
        else if (week == 1 && Instance.month(instance) > 1)
        {
            // week belongs to the next iso year
            weeks = mCalendarMetrics.getWeeksPerYear(year + 1);
        }
        else
        {
            weeks = mCalendarMetrics.getWeeksPerYear(year);
        }

        return (StaticUtils.linearSearch(mWeekNumbers, week) < 0 && StaticUtils.linearSearch(mWeekNumbers, week - 1 - weeks) < 0) || week > weeks;
    }
}
