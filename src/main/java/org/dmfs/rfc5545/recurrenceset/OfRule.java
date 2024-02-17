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
import org.dmfs.rfc5545.recur.RecurrenceRule;

/**
 * The {@link RecurrenceSet} of a single {@link RecurrenceRule}.
 */
public final class OfRule implements RecurrenceSet
{
    private final RecurrenceRule mRecurrenceRule;
    private final DateTime mFirstInstance;

    public OfRule(RecurrenceRule recurrenceRule, DateTime firstInstance)
    {
        mRecurrenceRule = recurrenceRule;
        mFirstInstance = firstInstance;
    }

    @Override
    public InstanceIterator iterator()
    {
        return mRecurrenceRule.iterator(mFirstInstance);
    }

    @Override
    public boolean isInfinite()
    {
        return mRecurrenceRule.isInfinite();
    }
}
