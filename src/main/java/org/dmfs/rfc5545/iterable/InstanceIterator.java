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

package org.dmfs.rfc5545.iterable;

public interface InstanceIterator
{
    /**
     * Returns {@code true} if there is at least one more instance to iterate and {@code false} otherwise.
     */
    boolean hasNext();

    /**
     * Get the next instance (in milliseconds since the epoch) of this set. Do not call this if {@link #hasNext()} returns {@code false}.
     */
    long next();

    /**
     * Skip all instances till {@code until}. If  {@code until} is an instance itself it will be the next iterated instance. If the rule doesn't
     * recur till that date the next call to {@link #hasNext()} will return {@code false}.
     */
    void fastForward(long until);
}
