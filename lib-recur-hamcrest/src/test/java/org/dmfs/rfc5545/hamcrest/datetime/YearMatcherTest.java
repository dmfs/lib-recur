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

import static org.dmfs.jems2.hamcrest.matchers.matcher.MatcherMatcher.*;
import static org.dmfs.rfc5545.hamcrest.datetime.YearMatcher.inYear;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link YearMatcher}.
 *
 * @author Marten Gajda
 */
public class YearMatcherTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(inYear(2018),
            AllOf.<Matcher<DateTime>>allOf(
                matches(DateTime.parse("20181001T010001Z")),
                mismatches(DateTime.parse("20210901T005959Z"), "year was <2021>"),
                mismatches(DateTime.parse("20171101T010000Z"), "year was <2017>"),
                describesAs("year (<2018>)")
            ));
        assertThat(inYear(2018, 2019, 2020),
            AllOf.<Matcher<DateTime>>allOf(
                matches(DateTime.parse("20181001T010001Z")),
                matches(DateTime.parse("20190801T010002Z")),
                matches(DateTime.parse("20200601T010003Z")),
                mismatches(DateTime.parse("20110501T005959Z"), "year was <2011>"),
                mismatches(DateTime.parse("20170701T005958Z"), "year was <2017>"),
                mismatches(DateTime.parse("20210901T005957Z"), "year was <2021>"),
                mismatches(DateTime.parse("20301101T010000Z"), "year was <2030>"),
                describesAs("year (<2018> or <2019> or <2020>)")
            ));

    }
}