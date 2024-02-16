/*
 * Copyright 2024 Marten Gajda <marten@dmfs.org>
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

package org.dmfs.rfc5545.recurrenceset;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.junit.jupiter.api.Test;

import static org.dmfs.rfc5545.confidence.Recur.infinite;
import static org.dmfs.rfc5545.confidence.Recur.startsWith;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;

class OfRuleTest
{
    @Test
    void testRuleWithCount() throws InvalidRecurrenceRuleException
    {
        assertThat(new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215")),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240215"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240217"),
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))));
    }

    @Test
    void testRuleWithUntil() throws InvalidRecurrenceRuleException
    {
        assertThat(new OfRule(new RecurrenceRule("FREQ=DAILY;UNTIL=20240219"), DateTime.parse("20240215")),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240215"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240217"),
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))));
    }


    @Test
    void testInfiniteRule() throws InvalidRecurrenceRuleException
    {
        assertThat(new OfRule(new RecurrenceRule("FREQ=DAILY"), DateTime.parse("20240215")),
            allOf(
                is(infinite()),
                startsWith(
                    DateTime.parse("20240215"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240217"),
                    DateTime.parse("20240218"),
                    DateTime.parse("20240219"))));
    }
}