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

package org.dmfs.rfc5545;


import org.dmfs.srcless.annotations.composable.Composable;

/**
 * A set of instances.
 */
@Composable
public interface RecurrenceSet extends Iterable<DateTime>
{
    /**
     * Returns an {@link InstanceIterator} for this {@link RecurrenceSet}.
     */
    InstanceIterator iterator();

    /**
     * Returns whether this {@link RecurrenceSet} is infinite or not.
     *
     * @deprecated in favour of {@link #isFinite()}
     */
    @Deprecated(forRemoval = true)
    boolean isInfinite();

    /**
     * Returns whether this {@link RecurrenceSet} is finite.
     */
    default boolean isFinite()
    {
        return !isInfinite();
    }
}