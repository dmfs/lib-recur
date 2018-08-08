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
import org.dmfs.rfc5545.Duration;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Locale;


/**
 * A {@link Matcher} which verifies the iterated instances of a {@link RecurrenceRule} are strictly increasing.
 *
 * @author Marten Gajda
 */
public final class IncreasingMatcher extends TypeSafeDiagnosingMatcher<RecurrenceRule>
{
    private final static int MAX_ITERATIONS = 10000;
    private final DateTime mStart;


    public IncreasingMatcher(DateTime start)
    {
        mStart = start;
    }


    public static Matcher<RecurrenceRule> increasing(DateTime start)
    {
        return new IncreasingMatcher(start);
    }


    @Override
    protected boolean matchesSafely(RecurrenceRule recurrenceRule, Description mismatchDescription)
    {
        // no instance should be before start
        DateTime lastInstance = mStart.addDuration(mStart.isAllDay() ? new Duration(-1, 1, 0) : new Duration(-1, 0, 1));

        int count = 0;
        RecurrenceRuleIterator it = recurrenceRule.iterator(mStart);
        while (it.hasNext())
        {
            count++;
            DateTime instance = it.nextDateTime();
            if (!lastInstance.before(instance))
            {
                mismatchDescription.appendText(
                        String.format(Locale.ENGLISH, "instance number %d (%s) of rule %s was before the previous instance (%s)",
                                count,
                                instance.toString(),
                                recurrenceRule.toString(),
                                lastInstance.toString()));
                return false;
            }
            lastInstance = instance;

            if (count == MAX_ITERATIONS || instance.getYear() > 9000)
            {
                break;
            }
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("recurrence iteration is strictly increasing");
    }
}
