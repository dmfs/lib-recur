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
import org.junit.jupiter.api.Test;

import static java.util.TimeZone.getTimeZone;
import static org.dmfs.rfc5545.confidence.Recur.emptyRecurrenceSet;
import static org.dmfs.rfc5545.confidence.Recur.infinite;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;

class OfListTest
{
    @Test
    void testEmptyList()
    {
        assertThat(new OfList(new DateTime[0]),
            is(emptyRecurrenceSet()));
    }

    @Test
    void testSingletonList()
    {
        assertThat(new OfList(DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T142501")),
            allOf(
                is(not(infinite())),
                iterates(DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T142501"))));
    }

    @Test
    void testOrderedList()
    {
        assertThat(new OfList(
                DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T142501"),
                DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T162501"),
                DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T182501")),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T142501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T162501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T182501"))));
    }

    @Test
    void testUnorderedList()
    {
        assertThat(new OfList(
                DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T162501"),
                DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T142501"),
                DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T182501")),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T142501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T162501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T182501"))));
    }


    @Test
    void testStringList()
    {
        assertThat(new OfList(
                getTimeZone("Europe/Berlin"), "20240216T162501,20240216T142501,20240216T182501"),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T142501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T162501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T182501"))));
    }


    @Test
    void testStringLists()
    {
        assertThat(new OfList(
                getTimeZone("Europe/Berlin"),
                "20240216T162501,20240216T142501,20240216T182501",
                "20240216T202501,20240216T232501,20240216T222501"),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T142501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T162501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T182501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T202501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T222501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T232501"))));
    }


    @Test
    void testStrings()
    {
        assertThat(new OfList(
                getTimeZone("Europe/Berlin"),
                "20240216T162501",
                "20240216T142501",
                "20240216T182501",
                "20240216T202501",
                "20240216T232501",
                "20240216T222501"),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T142501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T162501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T182501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T202501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T222501"),
                    DateTime.parse(getTimeZone("Europe/Berlin"), "20240216T232501"))));
    }


    @Test
    void testFloatingStringList()
    {
        assertThat(new OfList(
                "20240216T162501,20240216T142501,20240216T182501"),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240216T142501"),
                    DateTime.parse("20240216T162501"),
                    DateTime.parse("20240216T182501"))));
    }


    @Test
    void testFloatingStringLists()
    {
        assertThat(new OfList(
                "20240216T162501,20240216T142501,20240216T182501",
                "20240216T202501,20240216T232501,20240216T222501"),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240216T142501"),
                    DateTime.parse("20240216T162501"),
                    DateTime.parse("20240216T182501"),
                    DateTime.parse("20240216T202501"),
                    DateTime.parse("20240216T222501"),
                    DateTime.parse("20240216T232501"))));
    }


    @Test
    void testFloatingStrings()
    {
        assertThat(new OfList(
                "20240216T162501",
                "20240216T142501",
                "20240216T182501",
                "20240216T202501",
                "20240216T232501",
                "20240216T222501"),
            allOf(
                is(not(infinite())),
                iterates(
                    DateTime.parse("20240216T142501"),
                    DateTime.parse("20240216T162501"),
                    DateTime.parse("20240216T182501"),
                    DateTime.parse("20240216T202501"),
                    DateTime.parse("20240216T222501"),
                    DateTime.parse("20240216T232501"))));
    }
}