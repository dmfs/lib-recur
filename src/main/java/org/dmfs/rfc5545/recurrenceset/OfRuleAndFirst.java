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
import org.dmfs.rfc5545.InstanceIterator;
import org.dmfs.rfc5545.RecurrenceSet;
import org.dmfs.rfc5545.instanceiterator.CountLimitedRecurrenceRuleIterator;
import org.dmfs.rfc5545.instanceiterator.Merged;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;


/**
 * The {@link RecurrenceSet} of a single {@link RecurrenceRule} that also returns any non-synchronized first instance.
 */
public final class OfRuleAndFirst implements RecurrenceSet
{
    private final RecurrenceRule mRecurrenceRule;
    private final DateTime mFirst;

    /**
     * Create a new adapter for the given rule and start.
     *
     * @param rule The recurrence rule to adapt to.
     */
    public OfRuleAndFirst(RecurrenceRule rule, DateTime first)
    {
        mRecurrenceRule = rule;
        mFirst = first;
    }

    @Override
    public InstanceIterator iterator()
    {
        RecurrenceRuleIterator ruleIterator = mRecurrenceRule.iterator(mFirst);
        if (!ruleIterator.peekDateTime().equals(mFirst))
        {
            return new Merged(
                new OfList(mFirst).iterator(),
                mRecurrenceRule.getCount() != null
                    // we have a count limited rule and an unsynced start date
                    // since the start date counts as the first element, the RRULE iterator should return one less element.
                    ? new CountLimitedRecurrenceRuleIterator(ruleIterator, mRecurrenceRule.getCount() - 1)
                    : ruleIterator);
        }
        return ruleIterator;
    }

    @Override
    public boolean isInfinite()
    {
        return mRecurrenceRule.isInfinite();
    }
}
