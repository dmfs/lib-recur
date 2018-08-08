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
import static org.dmfs.rfc5545.hamcrest.datetime.WeekOfYearMatcher.inWeekOfYear;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link WeekOfYearMatcher}.
 *
 * @author Marten Gajda
 */
public class WeekOfYearMatcherTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(inWeekOfYear(16),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180418T010001Z")),
                        mismatches(DateTime.parse("20180415T005959Z"), "week of year was <15>"),
                        mismatches(DateTime.parse("20180423T010000Z"), "week of year was <17>"),
                        describesAs("week of year (<16>)")
                ));
        assertThat(inWeekOfYear(1, 16, 52),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180101T010001Z")),
                        matches(DateTime.parse("20180418T010001Z")),
                        matches(DateTime.parse("20181230T010001Z")),
                        matches(DateTime.parse("20181231T010001Z")),
                        matches(DateTime.parse("20190101T010001Z")),
                        mismatches(DateTime.parse("20180108T010001Z"), "week of year was <2>"),
                        mismatches(DateTime.parse("20180415T005959Z"), "week of year was <15>"),
                        mismatches(DateTime.parse("20180423T010000Z"), "week of year was <17>"),
                        mismatches(DateTime.parse("20181223T010001Z"), "week of year was <51>"),
                        mismatches(DateTime.parse("20181223T010001Z"), "week of year was <51>"),
                        mismatches(DateTime.parse("20190107T010001Z"), "week of year was <2>"),
                        describesAs("week of year (<1> or <16> or <52>)")
                ));

    }
}