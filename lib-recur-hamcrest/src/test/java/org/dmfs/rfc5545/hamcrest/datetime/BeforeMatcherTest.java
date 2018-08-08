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
import static org.dmfs.rfc5545.hamcrest.datetime.BeforeMatcher.before;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link BeforeMatcher}.
 *
 * @author Marten Gajda
 */
public class BeforeMatcherTest
{

    @Test
    public void test() throws Exception
    {
        assertThat(before(DateTime.parse("20180101T010000Z")),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180101T000059Z")),
                        mismatches(DateTime.parse("20180101T010001Z"), "not before 20180101T010000Z"),
                        mismatches(DateTime.parse("20180101T010000Z"), "not before 20180101T010000Z"),
                        describesAs("before 20180101T010000Z")
                ));

        assertThat(before("20180101T010000Z"),
                AllOf.<Matcher<DateTime>>allOf(
                        matches(DateTime.parse("20180101T000059Z")),
                        mismatches(DateTime.parse("20180101T010001Z"), "not before 20180101T010000Z"),
                        mismatches(DateTime.parse("20180101T010000Z"), "not before 20180101T010000Z"),
                        describesAs("before 20180101T010000Z")
                ));
    }
}