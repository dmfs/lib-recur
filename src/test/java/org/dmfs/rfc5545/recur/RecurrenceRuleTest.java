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
import org.dmfs.rfc5545.Weekday;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.SingleMatcher.hasValue;
import static org.dmfs.rfc5545.Weekday.MO;
import static org.dmfs.rfc5545.Weekday.TH;
import static org.dmfs.rfc5545.Weekday.TU;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.are;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.instances;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.results;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.startingWith;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.validRule;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.walking;
import static org.dmfs.rfc5545.hamcrest.datetime.BeforeMatcher.before;
import static org.dmfs.rfc5545.hamcrest.datetime.DayOfMonthMatcher.onDayOfMonth;
import static org.dmfs.rfc5545.hamcrest.datetime.MonthMatcher.inMonth;
import static org.dmfs.rfc5545.hamcrest.datetime.WeekDayMatcher.onWeekDay;
import static org.dmfs.rfc5545.hamcrest.datetime.YearMatcher.inYear;
import static org.hamcrest.Matchers.hasToString;
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

        assertThat(new RecurrenceRule("FREQ=MONTHLY;INTERVAL=1;BYDAY=+3TH;UNTIL=20140101T045959Z;WKST=SU"),
                is(validRule(DateTime.parse("20130101T050000Z"),
                        walking(),
                        instances(are(onWeekDay(TH), onDayOfMonth(15, 16, 17, 18, 19, 20, 21), inYear(2013), before("20140101T050000Z"))),
                        startingWith("20130117T050000Z", "20130221T050000Z", "20130321T050000Z"),
                        results(12))));

        // see https://github.com/dmfs/lib-recur/issues/73
        assertThat(new RecurrenceRule("FREQ=WEEKLY;INTERVAL=2;WKST=SU;BYDAY=TU;UNTIL=20200430T170000Z"),
                is(validRule(DateTime.parse("20200404T100000Z"),
                        walking(),
                        instances(are(onWeekDay(TU), onDayOfMonth(14, 28), inMonth(4), inYear(2020), before("20200430T170000Z"))),
                        startingWith("20200414T100000Z", "20200428T100000Z"),
                        results(2))));

        // see https://github.com/dmfs/lib-recur/issues/78
        assertThat(
                () -> {
                    RecurrenceRule recurrenceRule = new RecurrenceRule(Freq.MONTHLY);
                    recurrenceRule.setCount(5);
                    recurrenceRule.setInterval(1);
                    recurrenceRule.setSkip(RecurrenceRule.Skip.FORWARD);
                    recurrenceRule.setWeekStart(Weekday.MO);
                    return recurrenceRule;
                },
                hasValue(hasToString("FREQ=MONTHLY;RSCALE=GREGORIAN;SKIP=FORWARD;COUNT=5"))
        );
    }
}
