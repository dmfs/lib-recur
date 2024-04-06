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

package org.dmfs.rfc5545.confidence.quality;

import org.dmfs.jems2.iterator.Seq;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.RecurrenceSet;
import org.dmfs.rfc5545.instanceiterator.EmptyIterator;
import org.dmfs.rfc5545.instanceiterator.FastForwardable;
import org.junit.jupiter.api.Test;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.allOf;
import static org.saynotobugs.confidence.test.quality.Test.fails;

class EmptyRecurrenceSetTest
{
    @Test
    void test()
    {
        assertThat(new EmptyRecurrenceSet(),
            allOf(
                org.saynotobugs.confidence.test.quality.Test.<RecurrenceSet>passes(mock(RecurrenceSet.class,
                    with(RecurrenceSet::isInfinite, returning(false)),
                    with(RecurrenceSet::isFinite, returning(true)),
                    with(RecurrenceSet::iterator, returning(new EmptyIterator())))),

                fails(mock(RecurrenceSet.class,
                    with(RecurrenceSet::isInfinite, returning(true)),
                    with(RecurrenceSet::isFinite, returning(false)),
                    with(RecurrenceSet::iterator, returning(new EmptyIterator())))),

                fails(mock(RecurrenceSet.class,
                    with(RecurrenceSet::isInfinite, returning(false)),
                    with(RecurrenceSet::isFinite, returning(true)),
                    with(RecurrenceSet::iterator, returning(
                        new FastForwardable(
                            DateTime.parse("20240101"),
                            new Seq<>(DateTime.parse("20240102"), DateTime.parse("20240103"))))))),

                fails(mock(RecurrenceSet.class,
                    with(RecurrenceSet::isInfinite, returning(true)),
                    with(RecurrenceSet::isFinite, returning(false)),
                    with(RecurrenceSet::iterator, returning(
                        new FastForwardable(
                            DateTime.parse("20240101"),
                            new Seq<>(DateTime.parse("20240102"), DateTime.parse("20240103")))))))));
    }

}