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
import static org.dmfs.rfc5545.hamcrest.datetime.AfterMatcher.after;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link org.dmfs.rfc5545.hamcrest.datetime.AfterMatcher}.
 *
 * @author Marten Gajda
 */
public class AfterMatcherTest
{

    @Test
    public void test() throws Exception
    {
        assertThat(after(DateTime.parse("20180101T010000Z")),
            AllOf.<Matcher<DateTime>>allOf(
                matches(DateTime.parse("20180101T010001Z")),
                mismatches(DateTime.parse("20180101T005959Z"), "not after 20180101T010000Z"),
                mismatches(DateTime.parse("20180101T010000Z"), "not after 20180101T010000Z"),
                describesAs("after 20180101T010000Z")
            ));

        assertThat(after("20180101T010000Z"),
            AllOf.<Matcher<DateTime>>allOf(
                matches(DateTime.parse("20180101T010001Z")),
                mismatches(DateTime.parse("20180101T005959Z"), "not after 20180101T010000Z"),
                mismatches(DateTime.parse("20180101T010000Z"), "not after 20180101T010000Z"),
                describesAs("after 20180101T010000Z")
            ));
    }
}