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
import static org.dmfs.rfc5545.Weekday.TH;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.are;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.instances;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.results;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.startingWith;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.validRule;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.walking;
import static org.dmfs.rfc5545.hamcrest.datetime.BeforeMatcher.before;
import static org.dmfs.rfc5545.hamcrest.datetime.DayOfMonthMatcher.onDayOfMonth;
import static org.dmfs.rfc5545.hamcrest.datetime.DayOfYearMatcher.onDayOfYear;
import static org.dmfs.rfc5545.hamcrest.datetime.MonthMatcher.inMonth;
import static org.dmfs.rfc5545.hamcrest.datetime.WeekDayMatcher.onWeekDay;
import static org.dmfs.rfc5545.hamcrest.datetime.YearMatcher.inYear;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public final class RecurrenceRuleTest
{
    @Test
    public void test() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceRule("FREQ=WEEKLY;COUNT=1000"),
                is(validRule(DateTime.parse("20180101"),
                        walking(),
                        instances(are(onWeekDay(MO))),
                        results(1000))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;BYMONTH=1,3;BYYEARDAY=1,14,31,32,60,61"),
                is(validRule(DateTime.parse("20100101"),
                        walking(),
                        instances(are(inMonth(1, 3), onDayOfYear(1, 14, 31, 60, 61))),
                        startingWith("20100101", "20100114", "20100131", "20100301", "20100302", "20110101", "20110114", "20110131"))));

        assertThat(new RecurrenceRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3TH;UNTIL=20140101T045959Z;WKST=SU"),
                is(validRule(DateTime.parse("20130101T050000Z"),
                        walking(),
                        instances(are(onWeekDay(TH), onDayOfMonth(15, 16, 17, 18, 19, 20, 21), inYear(2013), before("20140101T050000Z"))),
                        startingWith("20130117T050000Z", "20130221T050000Z", "20130321T050000Z"),
                        results(12))));

    }
}
