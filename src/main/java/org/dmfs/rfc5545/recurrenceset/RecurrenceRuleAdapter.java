/*
 * Copyright (C) 2013 Marten Gajda <marten@dmfs.org>
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
 *
 */

package org.dmfs.rfc5545.recurrenceset;

import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;

import java.util.TimeZone;


/**
 * Implements {@link AbstractRecurrenceAdapter} for a {@link RecurrenceRule}.
 *
 * @author Marten Gajda
 */
@Deprecated
public final class RecurrenceRuleAdapter extends AbstractRecurrenceAdapter
{

    class InstanceIterator implements AbstractRecurrenceAdapter.InstanceIterator
    {
        private final RecurrenceRuleIterator mIterator;


        public InstanceIterator(RecurrenceRuleIterator iterator)
        {
            mIterator = iterator;
        }


        @Override
        public boolean hasNext()
        {
            return mIterator.hasNext();
        }


        @Override
        public long next()
        {
            return mIterator.nextMillis();
        }


        @Override
        public void fastForward(long until)
        {
            mIterator.fastForward(until);
        }

    }


    /**
     * The recurrence rule.
     */
    private final RecurrenceRule mRrule;


    /**
     * Create a new adapter for the given rule and start.
     *
     * @param rule
     *         The recurrence rule to adapt to.
     */
    public RecurrenceRuleAdapter(RecurrenceRule rule)
    {
        mRrule = rule;
    }


    @Override
    AbstractRecurrenceAdapter.InstanceIterator getIterator(TimeZone timezone, long start)
    {
        RecurrenceRuleIterator ruleIterator = mRrule.iterator(start, timezone);
        AbstractRecurrenceAdapter.InstanceIterator iterator = new InstanceIterator(ruleIterator);
        if (mRrule.getCount() != null && ruleIterator.peekMillis() != start)
        {
            // we have a count limited rule and an unsynched start date
            // since the start date counts as the first element, the RRULE iterator should return one less element.
            iterator = new CountLimitedRecurrenceRuleIterator(ruleIterator, mRrule.getCount() - 1);
        }
        return iterator;
    }


    @Override
    boolean isInfinite()
    {
        return mRrule.isInfinite();
    }


    @Override
    long getLastInstance(TimeZone timezone, long start)
    {
        if (isInfinite())
        {
            return Long.MAX_VALUE;
        }

        RecurrenceRuleIterator iterator = mRrule.iterator(start, timezone);
        iterator.skipAllButLast();

        long lastInstance = Long.MIN_VALUE;
        if (iterator.hasNext())
        {
            lastInstance = iterator.nextMillis();
        }
        return lastInstance;
    }
}
