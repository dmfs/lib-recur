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

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.iterable.InstanceIterable;
import org.dmfs.rfc5545.iterable.InstanceIterator;


/**
 * A composite {@link InstanceIterable} composed of other {@link InstanceIterable}s. This {@link InstanceIterator}
 * returned by this class returns the instances of all given {@link InstanceIterable}s in chronological order.
 *
 * @deprecated in favour of {@link org.dmfs.rfc5545.recurrenceset.Merged}
 */
@Deprecated
public final class Composite implements InstanceIterable
{
    private final InstanceIterable mDelegate;


    public Composite(InstanceIterable... delegate)
    {
        this(new Seq<>(delegate));
    }


    public Composite(Iterable<InstanceIterable> delegate)
    {
        this(firstInstance -> new org.dmfs.rfc5545.iterable.instanceiterator.Composite(new Mapped<>(d -> d.iterator(firstInstance), delegate)));
    }


    private Composite(InstanceIterable delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public InstanceIterator iterator(DateTime firstInstance)
    {
        return mDelegate.iterator(firstInstance);
    }
}
