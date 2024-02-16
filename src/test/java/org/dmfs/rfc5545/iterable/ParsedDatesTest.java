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

package org.dmfs.rfc5545.iterable;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.iterable.ParsedDates;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.Core;

import static java.util.TimeZone.getTimeZone;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;

class ParsedDatesTest
{
    @Test
    void testEmpty()
    {
        assertThat(new ParsedDates(""), is(emptyIterable()));
    }

    @Test
    void testSingletonDate()
    {
        assertThat(new ParsedDates("20240216"), Core.iterates(DateTime.parse("20240216")));
    }

    @Test
    void testMultipleDates()
    {
        assertThat(new ParsedDates("20240216,20240217,20240218"),
            iterates(
                DateTime.parse("20240216"),
                DateTime.parse("20240217"),
                DateTime.parse("20240218")));
    }

    @Test
    void testSingletonDateTime()
    {
        assertThat(new ParsedDates("20240216T123456"), iterates(DateTime.parse("20240216T123456")));
    }

    @Test
    void testMultipleDateTimes()
    {
        assertThat(new ParsedDates("20240216T123456,20240217T123456,20240218T123456"),
            iterates(
                DateTime.parse("20240216T123456"),
                DateTime.parse("20240217T123456"),
                DateTime.parse("20240218T123456")));
    }

    @Test
    void testAbsoluteSingletonDateTime()
    {
        assertThat(new ParsedDates(getTimeZone("Europe/Berlin"), "20240216T123456"),
            iterates(DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T123456")));
    }

    @Test
    void testAbsoluteMultipleDateTimes()
    {
        assertThat(new ParsedDates(getTimeZone("Europe/Berlin"), "20240216T123456,20240217T123456,20240218T123456"),
            iterates(
                DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T123456"),
                DateTime.parse(getTimeZone("Europe/Berlin"), "20240217T123456"),
                DateTime.parse(getTimeZone("Europe/Berlin"), "20240218T123456")));
    }
}