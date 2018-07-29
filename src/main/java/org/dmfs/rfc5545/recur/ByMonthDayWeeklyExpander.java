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
 * An expander that expands recurrence rules by day of month. This expander expands instances for YEARLY, MONTHLY and WEEKLY rules. <p> <strong>Note:
 * </strong><a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a> doesn't allow BYMONTHDAY to be used with WEEKLY rules, but RFC 2445 does.
 * A reasonable solution seems to be to expand if BYYEARDAY is not specified, but expand only days that are in the same week. The same approach is taken for
 * YEARLY rules if BYWEEKNO is present. </p>
 *
 * @author Marten Gajda
 */
final class ByMonthDayWeeklyExpander extends ByExpander
{
    /**
     * A list of days of month to expand.
     */
    private final int[] mMonthDays;


    public ByMonthDayWeeklyExpander(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, long start)
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

        int dayOfMonth = Instance.dayOfMonth(instance);
        int weekOfYear = 0;
        int hour = Instance.hour(instance);
        int minute = Instance.minute(instance);
        int second = Instance.second(instance);

        int prevMonthDays = 0;
        int nextMonthDays = 0;
        weekOfYear = calendarMetrics.getWeekOfYear(year, month, dayOfMonth);

        // FIXME: the following won't work with calendar scales that have leap months
        if (month == 0)
        {
            prevMonthDays = calendarMetrics.getDaysPerPackedMonth(year - 1,
                    calendarMetrics.getMonthsPerYear(year - 1) - 1);
        }
        else
        {
            prevMonthDays = calendarMetrics.getDaysPerPackedMonth(year, month - 1);
        }

        if (month == calendarMetrics.getMonthsPerYear(year) - 1)
        {
            nextMonthDays = calendarMetrics.getDaysPerPackedMonth(year + 1, 0);
        }
        else
        {
            nextMonthDays = calendarMetrics.getDaysPerPackedMonth(year, month + 1);
        }

        int monthDays = calendarMetrics.getDaysPerPackedMonth(year, month);
        for (int day : mMonthDays)
        {
            int actualDay = day;
            if (day < 0)
            {
                actualDay = day + monthDays + 1;
            }

            /*
             * Expand a WEEKLY rule by day of month. This is not supported by RFC 5545, but it's valid in RFC 2445.
             *
             * We handle this case just like expanding a MONTHLY rule. The difficult part is that a week can overlap two months.
             */

            int prevMonthDay = day;
            int nextMonthDay = day;
            if (day < 0)
            {
                prevMonthDay = day + prevMonthDays + 1;
                nextMonthDay = day + nextMonthDays + 1;
            }

            /*
             * Add instance only if the week didn't change.
             */
            if (0 < actualDay && actualDay <= monthDays && calendarMetrics.getWeekOfYear(year, month,
                    actualDay) == weekOfYear)
            {
                // the instance is in the same week
                addInstance(Instance.make(year, month, actualDay, hour, minute, second));
            }
            else if (0 < nextMonthDay && nextMonthDay <= nextMonthDays && nextMonthDay < 7)
            {
                /*
                 * The day might belong to the next month.
                 *
                 * FIXME: this won't work if the next month is shorter than 7 days (are there any calendars with months shorter than 7 days?).
                 */

                if (month < calendarMetrics.getMonthsPerYear(year) - 1)
                {
                    /*
                     * Add instance only if the week didn't change.
                     */
                    if (calendarMetrics.getWeekOfYear(year, month + 1, nextMonthDay) == weekOfYear)
                    {
                        addInstance(Instance.make(year, month + 1, nextMonthDay, hour, minute, second));
                    }
                }
                else
                {
                    /*
                     * Add instance only if the week didn't change.
                     */
                    if (calendarMetrics.getWeekOfYear(year + 1, 0, nextMonthDay) == weekOfYear)
                    {
                        addInstance(Instance.make(year + 1, 0, nextMonthDay, hour, minute, second));
                    }
                }
            }
            else if (0 < prevMonthDay && prevMonthDay <= prevMonthDays && prevMonthDay > prevMonthDays - 7)
            {
                /*
                 * The day might belong to the previous month.
                 *
                 * FIXME: this won't work if the previous month is shorter than 7 days (are there any calendars with months shorter than 7 days?).
                 */
                if (month > 0)
                {
                    /*
                     * Add instance only if the week didn't change.
                     */
                    if (calendarMetrics.getWeekOfYear(year, month - 1, prevMonthDay) == weekOfYear)
                    {
                        addInstance(Instance.make(year, month - 1, prevMonthDay, hour, minute, second));
                    }
                }
                else
                {
                    /*
                     * Add instance only if the week didn't change.
                     */
                    if (calendarMetrics.getWeekOfYear(year - 1, calendarMetrics.getMonthsPerYear(year - 1) - 1,
                            prevMonthDay) == weekOfYear)
                    {
                        addInstance(Instance.make(year - 1, calendarMetrics.getMonthsPerYear(year - 1) - 1,
                                prevMonthDay, hour, minute, second));
                    }
                }
            }
        }
    }
}
