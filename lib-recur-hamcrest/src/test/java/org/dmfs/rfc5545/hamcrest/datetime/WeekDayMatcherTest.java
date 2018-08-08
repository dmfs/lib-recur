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

package org.dmfs.rfc5545.hamcrest.datetime;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Weekday;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.dmfs.rfc5545.hamcrest.datetime.WeekDayMatcher.onWeekDay;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link WeekDayMatcher}.
 *
 * @author Marten Gajda
 */
public class WeekDayMatcherTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(onWeekDay(Weekday.WE),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180808T010001Z")),
                        mismatches(DateTime.parse("20180809T005959Z"), "weekday was <TH>"),
                        mismatches(DateTime.parse("20180807T010000Z"), "weekday was <TU>"),
                        describesAs("weekday (<WE>)")
                ));
        assertThat(onWeekDay(Weekday.MO, Weekday.WE, Weekday.FR),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180808T010001Z")),
                        matches(DateTime.parse("20180806T010002Z")),
                        matches(DateTime.parse("20180810T010003Z")),
                        mismatches(DateTime.parse("20180805T005959Z"), "weekday was <SU>"),
                        mismatches(DateTime.parse("20180804T005958Z"), "weekday was <SA>"),
                        mismatches(DateTime.parse("20180814T005957Z"), "weekday was <TU>"),
                        mismatches(DateTime.parse("20180816T010000Z"), "weekday was <TH>"),
                        describesAs("weekday (<MO> or <WE> or <FR>)")
                ));

    }
}