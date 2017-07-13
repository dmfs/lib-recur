/*
 * Copyright 2016 Marten Gajda <marten@dmfs.org>
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

package org.dmfs.rfc5545.recur;

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;


/**
 * A trivial "ByMonth" filter that doesn't support overlapping weeks.
 *
 * @author Marten Gajda
 */
final class TrivialByMonthFilter implements ByFilter
{
    /**
     * The list of months to let pass.
     */
    private final int[] mMonths;


    public TrivialByMonthFilter(RecurrenceRule rule)
    {
        mMonths = StaticUtils.ListToArray(rule.getByPart(Part.BYMONTH));
    }


    @Override
    public boolean filter(long instance)
    {
        return StaticUtils.linearSearch(mMonths, Instance.month(instance)) < 0;
    }
}