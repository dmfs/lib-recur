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

package org.dmfs.rfc5545.hamcrest;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.Test;

import static org.dmfs.jems2.hamcrest.matchers.matcher.MatcherMatcher.*;
import static org.dmfs.rfc5545.hamcrest.ResultsMatcher.results;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link ResultsMatcher}.
 * <p>
 * FIXME: this is circular reasoning, fix when RecurrenceRule and RecurrenceRuleIterator are mockable
 *
 * @author Marten Gajda
 */
public class ResultsMatcherTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(results(DateTime.parse("20180101"), 10),
            AllOf.<Matcher<RecurrenceRule>>allOf(
                matches(new RecurrenceRule("FREQ=DAILY;COUNT=10")),
                mismatches(new RecurrenceRule("FREQ=DAILY;COUNT=9"), "number of instances was <9>"),
                mismatches(new RecurrenceRule("FREQ=DAILY;COUNT=12"), "number of instances was <12>"),
                describesAs("number of instances is <10>")
            ));

        assertThat(results(DateTime.parse("20180101"), lessThan(20)),
            AllOf.<Matcher<RecurrenceRule>>allOf(
                matches(new RecurrenceRule("FREQ=DAILY;COUNT=1")),
                matches(new RecurrenceRule("FREQ=DAILY;COUNT=19")),
                mismatches(new RecurrenceRule("FREQ=DAILY;COUNT=20"), "number of instances <20> was equal to <20>"),
                mismatches(new RecurrenceRule("FREQ=DAILY;COUNT=21"), "number of instances <21> was greater than <20>"),
                describesAs("number of instances is a value less than <20>")
            ));
    }
}