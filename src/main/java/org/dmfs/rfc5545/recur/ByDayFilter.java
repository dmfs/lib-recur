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

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.Weekday;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;

import java.util.Set;


/**
 * A filter that limits recurrence rules by day of week.
 *
 * @author Marten Gajda
 */
final class ByDayFilter implements ByFilter
{

    /**
     * The {@link CalendarMetrics} to use.
     */
    private final CalendarMetrics mCalendarMetrics;

    private final Set<Weekday> mWeekdays;

    private final Weekday[] mWeekdayArray = Weekday.values();


    public ByDayFilter(CalendarMetrics calendarMetrics, Set<Weekday> weekdays)
    {
        mCalendarMetrics = calendarMetrics;
        mWeekdays = weekdays;
    }


    @Override
    public boolean filter(long instance)
    {
        return !mWeekdays.contains(mWeekdayArray[mCalendarMetrics.getDayOfWeek(
                Instance.year(instance),
                Instance.month(instance),
                Instance.dayOfMonth(instance))]);
    }
}
