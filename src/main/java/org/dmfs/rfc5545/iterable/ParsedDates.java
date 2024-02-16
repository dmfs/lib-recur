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

import org.dmfs.jems2.iterable.DelegatingIterable;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.predicate.Not;
import org.dmfs.rfc5545.DateTime;

import java.util.TimeZone;

/**
 * An {@link Iterable} of {@link DateTime}s parsed from a {@link String} of comma separated RFC 5545 date or
 * datetime values.
 */
public final class ParsedDates extends DelegatingIterable<DateTime>
{
    public ParsedDates(TimeZone timeZone, String datesList)
    {
        super(new Mapped<>(dateString -> DateTime.parse(timeZone, dateString),
            new Sieved<>(new Not<>(String::isEmpty),
                new Seq<>(datesList.split(",")))));
    }


    public ParsedDates(String datesList)
    {
        super(new Mapped<>(DateTime::parse,
            new Sieved<>(new Not<>(String::isEmpty),
                new Seq<>(datesList.split(",")))));
    }
}
