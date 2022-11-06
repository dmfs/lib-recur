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
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;


/**
 * Implements {@link InstanceIterable} for a {@link RecurrenceRule}. That only iterates instances that match the {@link RecurrenceRule}.
 * Any non-synchronized first instance is not returned.
 */
public final class RuleInstances implements InstanceIterable
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
    public RuleInstances(RecurrenceRule rule)
    {
        mRrule = rule;
    }


    @Override
    public InstanceIterator iterator(DateTime firstInstance)
    {
        RecurrenceRuleIterator iterator = mRrule.iterator(firstInstance);

        return new InstanceIterator()
        {
            @Override
            public boolean hasNext()
            {
                return iterator.hasNext();
            }


            @Override
            public long next()
            {
                return iterator.nextMillis();
            }


            @Override
            public void fastForward(long until)
            {
                iterator.fastForward(until);
            }
        };
    }
}
