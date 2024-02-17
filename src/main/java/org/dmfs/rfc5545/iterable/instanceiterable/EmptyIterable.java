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
import org.dmfs.rfc5545.iterable.instanceiterator.EmptyIterator;


/**
 * An {@link InstanceIterable} that doesn't have any instances.
 *
 * @deprecated without replacement
 */
@Deprecated
public final class EmptyIterable implements InstanceIterable
{
    public static final InstanceIterable INSTANCE = new EmptyIterable();


    @Override
    public InstanceIterator iterator(DateTime firstInstance)
    {
        return EmptyIterator.INSTANCE;
    }
}
