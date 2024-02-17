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

package org.dmfs.rfc5545.optional;

import org.dmfs.jems2.Optional;

import java.util.Iterator;

/**
 * TODO: move to jems2
 */
final class Last<T> implements Optional<T>
{
    private final Iterable<? extends T> mIterable;

    public Last(Iterable<? extends T> iterable)
    {
        this.mIterable = iterable;
    }

    @Override
    public boolean isPresent()
    {
        return mIterable.iterator().hasNext();
    }

    @Override
    public T value()
    {
        Iterator<? extends T> iterator = mIterable.iterator();
        T result = iterator.next();
        while (iterator.hasNext())
        {
            result = iterator.next();
        }
        return result;
    }
}
