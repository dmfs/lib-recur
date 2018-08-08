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
import static org.dmfs.rfc5545.hamcrest.datetime.DayOfYearMatcher.onDayOfYear;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link DayOfYearMatcher}.
 *
 * @author Marten Gajda
 */
public class DayOfYearMatcherTest
{

    @Test
    public void test() throws Exception
    {
        assertThat(onDayOfYear(10),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180110T010001Z")),
                        mismatches(DateTime.parse("20180111T005959Z"), "day of year was <11>"),
                        mismatches(DateTime.parse("20180109T010000Z"), "day of year was <9>"),
                        describesAs("day of year (<10>)")
                ));
        assertThat(onDayOfYear(1, 8, 365),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180101T010001Z")),
                        matches(DateTime.parse("20180108T010002Z")),
                        matches(DateTime.parse("20181231T010003Z")),
                        mismatches(DateTime.parse("20180102T005959Z"), "day of year was <2>"),
                        mismatches(DateTime.parse("20180107T005959Z"), "day of year was <7>"),
                        mismatches(DateTime.parse("20180109T005959Z"), "day of year was <9>"),
                        mismatches(DateTime.parse("20180228T005958Z"), "day of year was <59>"),
                        mismatches(DateTime.parse("20181230T005957Z"), "day of year was <364>"),
                        describesAs("day of year (<1> or <8> or <365>)")
                ));
    }
}