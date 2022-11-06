/*
 * Copyright 2022 Marten Gajda <marten@dmfs.org>
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

package org.dmfs.rfc5545.iterable.instanceiterable;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.iterable.InstanceIterable;
import org.dmfs.rfc5545.iterable.InstanceIterator;
import org.dmfs.rfc5545.iterable.instanceiterator.Composite;
import org.dmfs.rfc5545.iterable.instanceiterator.CountLimitedRecurrenceRuleIterator;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;


/**
 * Implements {@link InstanceIterable} for a {@link RecurrenceRule} that also returns any non-synchronized first instance.
 */
public final class FirstAndRuleInstances implements InstanceIterable
{
    /**
     * The recurrence rule.
     */
    private final RecurrenceRule mRrule;


    /**
     * Create a new adapter for the given rule and start.
     *
     * @param rule
     *     The recurrence rule to adapt to.
     */
    public FirstAndRuleInstances(RecurrenceRule rule)
    {
        mRrule = rule;
    }


    @Override
    public InstanceIterator iterator(DateTime firstInstance)
    {
        RecurrenceRuleIterator ruleIterator = mRrule.iterator(firstInstance);
        if (mRrule.getCount() != null && ruleIterator.peekMillis() != firstInstance.getTimestamp())
        {
            // we have a count limited rule and an unsynched start date
            // since the start date counts as the first element, the RRULE iterator should return one less element.
            return new Composite(
                new InstanceList(new long[] { firstInstance.getTimestamp() }).iterator(firstInstance),
                new CountLimitedRecurrenceRuleIterator(ruleIterator, mRrule.getCount() - 1));
        }
        return new InstanceIterator()
        {
            @Override
            public boolean hasNext()
            {
                return ruleIterator.hasNext();
            }


            @Override
            public long next()
            {
                return ruleIterator.nextMillis();
            }


            @Override
            public void fastForward(long until)
            {
                ruleIterator.fastForward(until);
            }
        };
    }
}
