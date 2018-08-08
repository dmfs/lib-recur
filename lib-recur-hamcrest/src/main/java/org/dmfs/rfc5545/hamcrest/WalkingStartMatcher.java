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
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.LinkedList;
import java.util.List;


/**
 * A Matcher which checks that the recurrence iterator returns the same instances when starting from a specific instance.
 * <p>
 * Not to be used with rules with SKIP parameter other than OMIT.
 *
 * @author Marten Gajda
 */
public final class WalkingStartMatcher extends TypeSafeDiagnosingMatcher<RecurrenceRule>
{
    private final static int MAX_ITERATIONS = 10000;
    private final static int MAX_BUFFER = 100;
    private final DateTime mStart;


    public WalkingStartMatcher(DateTime start)
    {
        mStart = start;
    }


    public static Matcher<RecurrenceRule> walking(DateTime start)
    {
        return new WalkingStartMatcher(start);
    }


    @Override
    protected boolean matchesSafely(RecurrenceRule recurrenceRule, Description mismatchDescription)
    {
        DateTime lastInstance = new DateTime(0, 0, 0, 0, 0, 0);
        List<RecurrenceRuleIterator> instanceIterators = new LinkedList<RecurrenceRuleIterator>();

        if (recurrenceRule.getSkip() != RecurrenceRule.Skip.OMIT)
        {
            // walking start doesn't work with SKIP, since that might change the order
            throw new IllegalArgumentException("Can't test walking start with SKIP value other than OMIT");
        }

        RecurrenceRuleIterator mainIterator = recurrenceRule.iterator(mStart);

        if (!mainIterator.hasNext())
        {
            return true;
        }

        int count = 1;
        while (mainIterator.hasNext() && count < MAX_ITERATIONS && lastInstance.getYear() < 9000)
        {
            lastInstance = mainIterator.nextDateTime();
            count++;
            RecurrenceRuleIterator i2 = recurrenceRule.iterator(lastInstance);
            instanceIterators.add(i2);
            if (instanceIterators.size() > MAX_BUFFER)
            {
                instanceIterators.remove(0);
            }

            for (RecurrenceRuleIterator iter : instanceIterators)
            {
                if (!iter.hasNext())
                {
                    //("Expected another instance! rule=" + rule.rule + " lastInstance=" + lastInstance);
                    return false;
                }
                DateTime upcoming = iter.nextDateTime();
                if (!lastInstance.equals(upcoming))
                {
                    return false;
                }
            }

        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("starting at an instance returns the same results");
    }
}
