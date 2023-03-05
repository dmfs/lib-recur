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

package org.dmfs.rfc5545.recur;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Instance;


/**
 * A {@link Limiter} that filters all instances after a certain all-day date (the one specified in the UNTIL part).
 */
final class UntilDateLimiter extends Limiter
{
    /**
     * The latest allowed instance start date.
     */
    private final long mUntil;


    /**
     * Create a new limiter for an all-day UNTIL part.
     */
    public UntilDateLimiter(RecurrenceRule rule, RuleIterator previous)
    {
        super(previous);
        DateTime until = rule.getUntil();
        if (!until.isAllDay())
        {
            throw new RuntimeException("Illegal use of UntilDateLimiter with non-allday date " + until);
        }
        mUntil = until.getInstance();
    }


    @Override
    boolean stop(long instance)
    {
        return mUntil < Instance.setSecond(Instance.setMinute(Instance.setHour(Instance.maskWeekday(instance), 0), 0), 0);
    }
}
