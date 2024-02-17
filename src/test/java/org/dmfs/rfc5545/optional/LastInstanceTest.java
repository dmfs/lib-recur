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

package org.dmfs.rfc5545.optional;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recurrenceset.OfList;
import org.dmfs.rfc5545.recurrenceset.OfRule;
import org.junit.jupiter.api.Test;

import static org.dmfs.jems2.confidence.Jems2.absent;
import static org.dmfs.jems2.confidence.Jems2.present;
import static org.dmfs.jems2.iterable.EmptyIterable.emptyIterable;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.is;

class LastInstanceTest
{
    @Test
    void testEmptyList()
    {
        assertThat(new LastInstance(new OfList(emptyIterable())),
            is(absent()));
    }


    @Test
    void testSingleton()
    {
        assertThat(new LastInstance(new OfList(DateTime.parse("20240215"))),
            is(present(DateTime.parse("20240215"))));
    }

    @Test
    void testFiniteRule() throws InvalidRecurrenceRuleException
    {
        assertThat(new LastInstance(
                new OfRule(
                    new RecurrenceRule("FREQ=DAILY;COUNT=5"), DateTime.parse("20240215"))),
            is(present(DateTime.parse("20240219"))));
    }


    @Test
    void testInfiniteRule() throws InvalidRecurrenceRuleException
    {
        assertThat(new LastInstance(
                new OfRule(
                    new RecurrenceRule("FREQ=DAILY"), DateTime.parse("20240215"))),
            is(absent()));
    }

}