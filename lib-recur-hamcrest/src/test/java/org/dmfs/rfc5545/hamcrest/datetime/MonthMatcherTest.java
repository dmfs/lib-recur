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
import static org.dmfs.rfc5545.hamcrest.datetime.MonthMatcher.inMonth;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link MonthMatcher}.
 *
 * @author Marten Gajda
 */
public class MonthMatcherTest
{

    @Test
    public void test() throws Exception
    {
        assertThat(inMonth(10),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20181001T010001Z")),
                        mismatches(DateTime.parse("20180901T005959Z"), "month was <9>"),
                        mismatches(DateTime.parse("20181101T010000Z"), "month was <11>"),
                        describesAs("month (<10>)")
                ));
        assertThat(inMonth(6, 8, 10),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20181001T010001Z")),
                        matches(DateTime.parse("20180801T010002Z")),
                        matches(DateTime.parse("20180601T010003Z")),
                        mismatches(DateTime.parse("20180501T005959Z"), "month was <5>"),
                        mismatches(DateTime.parse("20180701T005958Z"), "month was <7>"),
                        mismatches(DateTime.parse("20180901T005957Z"), "month was <9>"),
                        mismatches(DateTime.parse("20181101T010000Z"), "month was <11>"),
                        describesAs("month (<6> or <8> or <10>)")
                ));
    }
}