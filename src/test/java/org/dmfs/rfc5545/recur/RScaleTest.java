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

import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.are;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.instances;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.startingWith;
import static org.dmfs.rfc5545.hamcrest.RecurrenceRuleMatcher.validRule;
import static org.dmfs.rfc5545.hamcrest.datetime.DayOfMonthMatcher.onDayOfMonth;
import static org.dmfs.rfc5545.hamcrest.datetime.DayOfYearMatcher.onDayOfYear;
import static org.dmfs.rfc5545.hamcrest.datetime.MonthMatcher.inMonth;
import static org.dmfs.rfc5545.hamcrest.datetime.WeekOfYearMatcher.inWeekOfYear;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public final class RScaleTest
{
    @Test
    public void testByYearDaySkip() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYYEARDAY=366"),
                is(validRule(DateTime.parse("20121231"),
                        instances(are(onDayOfMonth(31), inMonth(12))),
                        startingWith("20121231", "20161231", "20201231"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYYEARDAY=366;SKIP=OMIT"),
                is(validRule(DateTime.parse("20121231"),
                        instances(are(onDayOfMonth(31), inMonth(12))),
                        startingWith("20121231", "20161231", "20201231"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYYEARDAY=366;SKIP=FORWARD"),
                is(validRule(DateTime.parse("20121231"),
                        instances(are(onDayOfMonth(1, 31), inMonth(1, 12), onDayOfYear(1, 366))),
                        startingWith("20121231", "20140101", "20150101", "20160101", "20161231", "20180101", "20190101", "20200101", "20201231"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYYEARDAY=366;SKIP=BACKWARD"),
                is(validRule(DateTime.parse("20121231"),
                        instances(are(onDayOfMonth(31), inMonth(12))),
                        startingWith("20121231", "20131231", "20141231", "20151231", "20161231", "20171231", "20181231", "20191231", "20201231"))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYYEARDAY=-366"),
                is(validRule(DateTime.parse("20120101"),
                        instances(are(onDayOfMonth(1), inMonth(1))),
                        startingWith("20120101", "20160101", "20200101"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYYEARDAY=-366;SKIP=OMIT"),
                is(validRule(DateTime.parse("20120101"),
                        instances(are(onDayOfMonth(1), inMonth(1))),
                        startingWith("20120101", "20160101", "20200101"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYYEARDAY=-366;SKIP=FORWARD"),
                is(validRule(DateTime.parse("20120101"),
                        instances(are(onDayOfMonth(1), inMonth(1), onDayOfYear(1))),
                        startingWith("20120101", "20130101", "20140101", "20150101", "20160101", "20170101", "20180101", "20190101", "20200101"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYYEARDAY=-366;SKIP=BACKWARD"),
                is(validRule(DateTime.parse("20120101"),
                        instances(are(onDayOfMonth(1, 31), inMonth(1, 12))),
                        startingWith("20120101", "20121231", "20131231", "20141231", "20160101", "20161231", "20171231", "20181231", "20200101"))));
    }


    @Test
    public void testByWeekNoSkip() throws InvalidRecurrenceRuleException
    {
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYWEEKNO=53"),
                is(validRule(DateTime.parse("20151229"),
                        instances(are(onDayOfMonth(28, 29), inMonth(12), inWeekOfYear(53))),
                        startingWith("20151229", "20201229", "20261229"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYWEEKNO=53;SKIP=OMIT"),
                is(validRule(DateTime.parse("20151229"),
                        instances(are(onDayOfMonth(28, 29), inMonth(12), inWeekOfYear(53))),
                        startingWith("20151229", "20201229", "20261229"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYWEEKNO=53;SKIP=FORWARD"),
                is(validRule(DateTime.parse("20151229"),
                        instances(are(onDayOfMonth(1, 28, 29), inMonth(1, 12), inWeekOfYear(1, 52, 53))),
                        startingWith("20151229", "20170101", "20180101", "20190101", "20200101", "20201229", "20220101", "20230101", "20240101", "20250101",
                                "20260101", "20261229"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYWEEKNO=53;SKIP=BACKWARD"),
                is(validRule(DateTime.parse("20151229"),
                        instances(are(onDayOfMonth(28, 29, 31), inMonth(1, 12), inWeekOfYear(1, 52, 53))),
                        startingWith("20151229", "20161231", "20171231", "20181231", "20191231", "20201229", "20211231", "20221231", "20231231", "20241231",
                                "20251231", "20261229"))));

        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYWEEKNO=-53"),
                is(validRule(DateTime.parse("20150103"),
                        instances(are(onDayOfMonth(3, 4), inMonth(1), inWeekOfYear(1))),
                        startingWith("20150103", "20200104", "20260103"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYWEEKNO=-53;SKIP=OMIT"),
                is(validRule(DateTime.parse("20150103"),
                        instances(are(onDayOfMonth(3, 4), inMonth(1), inWeekOfYear(1))),
                        startingWith("20150103", "20200104", "20260103"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYWEEKNO=-53;SKIP=FORWARD"),
                is(validRule(DateTime.parse("20150103"),
                        instances(are(onDayOfMonth(1, 3, 4), inMonth(1), inWeekOfYear(1, 52, 53))),
                        startingWith("20150103", "20160101", "20170101", "20180101", "20190101", "20200104", "20210101", "20220101", "20230101", "20240101",
                                "20250101", "20260103"))));
        assertThat(new RecurrenceRule("FREQ=YEARLY;RSCALE=GREGORIAN;BYWEEKNO=-53;SKIP=BACKWARD"),
                is(validRule(DateTime.parse("20150103"),
                        instances(are(onDayOfMonth(3, 4, 31), inMonth(1, 12), inWeekOfYear(1, 52, 53))),
                        startingWith("20150103", "20151231", "20161231", "20171231", "20181231", "20200104", "20201231", "20211231", "20221231", "20231231",
                                "20241231", "20260103"))));
    }
}
