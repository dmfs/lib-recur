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

import org.dmfs.rfc5545.InstanceIterator;
import org.dmfs.rfc5545.RecurrenceSet;
import org.dmfs.rfc5545.instanceiterator.EffectiveInstancesIterator;

/**
 * A {@link RecurrenceSet} that contains all the instances of a given {@link RecurrenceSet} except the ones that are
 * in the exceptions {@link RecurrenceSet}.
 */
public final class Difference implements RecurrenceSet
{
    private final RecurrenceSet mMinuend;
    private final RecurrenceSet mSubtrahend;

    public Difference(RecurrenceSet minuend, RecurrenceSet subtrahend)
    {
        mMinuend = minuend;
        mSubtrahend = subtrahend;
    }

    @Override
    public InstanceIterator iterator()
    {
        return new EffectiveInstancesIterator(mMinuend.iterator(), mSubtrahend.iterator());
    }

    @Override
    public boolean isInfinite()
    {
        return mMinuend.isInfinite();
    }

    @Override
    public boolean isFinite()
    {
        return mMinuend.isFinite();
    }
}
