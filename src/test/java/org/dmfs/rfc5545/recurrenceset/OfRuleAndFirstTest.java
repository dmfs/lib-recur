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

import static org.dmfs.rfc5545.confidence.Recur.*;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;

class OfRuleAndFirstTest
{
    @Test
    void testRuleWithCountSyncStart() throws InvalidRecurrenceRuleException
    {
        assertThat(new OfRuleAndFirst(new RecurrenceRule("FREQ=DAILY;BYDAY=FR;COUNT=3"), DateTime.parse("20240216")),
            allOf(
                is(finite()),
                iterates(
                    DateTime.parse("20240216"),
                    DateTime.parse("20240223"),
                    DateTime.parse("20240301"))));
    }

    @Test
    void testRuleWithCountUnsyncedStart() throws InvalidRecurrenceRuleException
    {
        assertThat(new OfRuleAndFirst(new RecurrenceRule("FREQ=DAILY;BYDAY=FR;COUNT=3"), DateTime.parse("20240214")),
            allOf(
                is(finite()),
                iterates(
                    DateTime.parse("20240214"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240223"))));
    }

    @Test
    void testRuleWithUntilSyncStart() throws InvalidRecurrenceRuleException
    {
        assertThat(new OfRuleAndFirst(new RecurrenceRule("FREQ=DAILY;BYDAY=FR;UNTIL=20240301"), DateTime.parse("20240216")),
            allOf(
                is(finite()),
                iterates(
                    DateTime.parse("20240216"),
                    DateTime.parse("20240223"),
                    DateTime.parse("20240301"))));
    }


    @Test
    void testRuleWithUntilUnsyncedStart() throws InvalidRecurrenceRuleException
    {
        assertThat(new OfRuleAndFirst(new RecurrenceRule("FREQ=DAILY;BYDAY=FR;UNTIL=20240301"), DateTime.parse("20240214")),
            allOf(
                is(finite()),
                iterates(
                    DateTime.parse("20240214"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240223"),
                    DateTime.parse("20240301"))));
    }

    @Test
    void testInfiniteRuleSyncStart() throws InvalidRecurrenceRuleException
    {
        assertThat(new OfRuleAndFirst(new RecurrenceRule("FREQ=DAILY;BYDAY=FR"), DateTime.parse("20240216")),
            allOf(
                is(infinite()),
                startsWith(
                    DateTime.parse("20240216"),
                    DateTime.parse("20240223"),
                    DateTime.parse("20240301"))));
    }


    @Test
    void testInfiniteRuleUnsyncedStart() throws InvalidRecurrenceRuleException
    {
        assertThat(new OfRuleAndFirst(new RecurrenceRule("FREQ=DAILY;BYDAY=FR"), DateTime.parse("20240214")),
            allOf(
                is(infinite()),
                startsWith(
                    DateTime.parse("20240214"),
                    DateTime.parse("20240216"),
                    DateTime.parse("20240223"),
                    DateTime.parse("20240301"))));
    }
}