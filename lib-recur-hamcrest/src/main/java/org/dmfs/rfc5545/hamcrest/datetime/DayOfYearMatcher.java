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

import org.dmfs.iterables.elementary.Seq;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.rfc5545.DateTime;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.AnyOf;


/**
 * @author Marten Gajda
 */
public final class DayOfYearMatcher extends FeatureMatcher<DateTime, Integer>
{
    /**
     * Constructor
     *
     * @param subMatcher
     *         The matcher to apply to the feature
     */
    public DayOfYearMatcher(Matcher<Integer> subMatcher)
    {
        super(subMatcher, "day of year", "day of year");
    }


    public static Matcher<DateTime> onDayOfYear(Matcher<Integer> dayMatcher)
    {
        return new DayOfYearMatcher(dayMatcher);
    }


    public static Matcher<DateTime> onDayOfYear(Integer... days)
    {
        return new DayOfYearMatcher(new AnyOf<>(new Mapped<>(Matchers::equalTo, new Seq<>(days))));
    }


    @Override
    protected Integer featureValueOf(DateTime actual)
    {
        return actual.getCalendarMetrics().getDayOfYear(actual.getYear(), actual.getMonth(), actual.getDayOfMonth());
    }
}
