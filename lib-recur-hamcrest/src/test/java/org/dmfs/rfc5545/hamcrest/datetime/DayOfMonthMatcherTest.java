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
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.dmfs.rfc5545.hamcrest.datetime.DayOfMonthMatcher.onDayOfMonth;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link DayOfMonthMatcher}.
 *
 * @author Marten Gajda
 */
public class DayOfMonthMatcherTest
{

    @Test
    public void test() throws Exception
    {
        assertThat(onDayOfMonth(10),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180710T010001Z")),
                        mismatches(DateTime.parse("20181001T005959Z"), "day of month was <1>"),
                        mismatches(DateTime.parse("20181011T010000Z"), "day of month was <11>"),
                        describesAs("day of month (<10>)")
                ));
        assertThat(onDayOfMonth(6, 8, 10),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180706T010001Z")),
                        matches(DateTime.parse("20180708T010002Z")),
                        matches(DateTime.parse("20180710T010003Z")),
                        mismatches(DateTime.parse("20180605T005959Z"), "day of month was <5>"),
                        mismatches(DateTime.parse("20180811T005958Z"), "day of month was <11>"),
                        mismatches(DateTime.parse("20181009T005957Z"), "day of month was <9>"),
                        mismatches(DateTime.parse("20180907T010000Z"), "day of month was <7>"),
                        describesAs("day of month (<6> or <8> or <10>)")
                ));
    }
}