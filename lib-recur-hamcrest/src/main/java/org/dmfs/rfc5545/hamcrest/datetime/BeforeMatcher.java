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
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * Matches if a {@link DateTime} is before a given date.
 *
 * @author Marten Gajda
 */
public final class BeforeMatcher extends TypeSafeDiagnosingMatcher<DateTime>
{
    private final DateTime mDateTime;


    public BeforeMatcher(DateTime referenceDate)
    {
        mDateTime = referenceDate;
    }


    public static Matcher<DateTime> before(String dateTime)
    {
        return before(DateTime.parse(dateTime));
    }


    public static Matcher<DateTime> before(DateTime dateTime)
    {
        return new BeforeMatcher(dateTime);
    }


    @Override
    protected boolean matchesSafely(DateTime item, Description mismatchDescription)
    {
        if (!item.before(mDateTime))
        {
            mismatchDescription.appendText(String.format("not before %s", mDateTime));
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText(String.format("before %s", mDateTime));
    }
}
