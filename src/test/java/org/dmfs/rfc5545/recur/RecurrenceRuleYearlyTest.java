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

import org.dmfs.rfc5545.DateTime;
import org.junit.Test;

import static org.dmfs.rfc5545.Weekday.MO;
import static org.dmfs.rfc5545.Weekday.TU;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.are;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.instances;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.startingWith;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.validRule;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.walking;
import static org.dmfs.rfc5545.hamcrest.datetime.DayOfMonthMatcher.onDayOfMonth;
import static org.dmfs.rfc5545.hamcrest.datetime.DayOfYearMatcher.onDayOfYear;
import static org.dmfs.rfc5545.hamcrest.datetime.MonthMatcher.inMonth;
import static org.dmfs.rfc5545.hamcrest.datetime.WeekDayMatcher.onWeekDay;
import static org.dmfs.rfc5545.hamcrest.datetime.WeekOfYearMatcher.inWeekOfYear;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public final class RecurrenceRuleYearlyTest
{
    @Test
    public void test() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceRule("FREQ=YEARLY;BYWEEKNO=1,10"),
                is(validRule(DateTime.parse("20180101"),
                        walking(),
                        instances(are(inMonth(12, 1, 3), inWeekOfYear(1, 10), onWeekDay(MO) /* inherited weekday */)),
                        startingWith("20180101", "20180305", "20181231", "20190304", "20191230", "20200302", "20210104", "20210308", "20220103", "20220307",
                                "20230102", "20230306", "20240101", "20240304"))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;BYWEEKNO=1,10;BYDAY=TU"),
                is(validRule(DateTime.parse("20180101"),
                        walking(),
                        instances(are(inMonth(12, 1, 3), inWeekOfYear(1, 10), onWeekDay(TU))),
                        startingWith("20180102", "20180306", "20190101", "20190305", "20191231", "20200303", "20210105", "20210309", "20220104", "20220308",
                                "20230103", "20230307", "20240102", "20240305"))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;BYWEEKNO=1,10;BYDAY=TU;BYMONTHDAY=3"),
                is(validRule(DateTime.parse("20180101"),
                        walking(),
                        instances(are(inMonth(12, 1, 3), inWeekOfYear(1, 10), onWeekDay(TU), onDayOfMonth(3))),
                        startingWith("20200303", "20230103"))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;BYYEARDAY=1,100"),
                is(validRule(DateTime.parse("20180101"),
                        walking(),
                        instances(are(inMonth(1, 4), onDayOfYear(1, 100))),
                        startingWith("20180101", "20180410", "20190101", "20190410", "20200101", "20200409", "20210101", "20210410", "20220101", "20220410"))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;BYMONTH=1,2"),
                is(validRule(DateTime.parse("20180101"),
                        walking(),
                        instances(are(inMonth(1, 2), onDayOfMonth(1))),
                        startingWith("20180101", "20180201", "20190101", "20190201", "20200101", "20200201", "20210101", "20210201", "20220101", "20220201"))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;BYMONTH=1,2;BYWEEKNO=1,7,20"),
                is(validRule(DateTime.parse("20180101"),
                        walking(),
                        instances(are(inMonth(1, 2), inWeekOfYear(1, 7), onWeekDay(MO) /* inherited weekday */)),
                        startingWith("20180101", "20180212", "20190211", "20200210", "20210104", "20210215", "20220103", "20220214"))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;BYWEEKNO=1,3,4,5,8,9;BYYEARDAY=1,14,31,60"),
                is(validRule(DateTime.parse("20180101"),
                        walking(),
                        instances(are(inMonth(12, 1, 2, 3), inWeekOfYear(1, 3, 4, 5, 8, 9), onDayOfYear(1, 14, 31, 60))),
                        startingWith("20180101", "20180131", "20180301", "20190101", "20190114", "20190131", "20190301", "20200101", "20200114", "20200131"))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;BYMONTH=1,3;BYYEARDAY=1,14,31,32,60,61"),
                is(validRule(DateTime.parse("20100101"),
                        walking(),
                        instances(are(inMonth(1, 3), onDayOfYear(1, 14, 31, 60, 61))),
                        startingWith("20100101", "20100114", "20100131", "20100301", "20100302", "20110101", "20110114", "20110131"))));
    }
}
