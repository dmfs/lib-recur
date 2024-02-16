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

package org.dmfs.rfc5545.instanceiterator;


import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.InstanceIterator;

import java.util.NoSuchElementException;


/**
 * An {@link InstanceIterator} without any instances.
 */
public final class EmptyIterator implements InstanceIterator
{
    public static final InstanceIterator INSTANCE = new EmptyIterator();


    @Override
    public boolean hasNext()
    {
        return false;
    }


    @Override
    public DateTime next()
    {
        throw new NoSuchElementException("No elements to iterate");
    }


    @Override
    public void fastForward(DateTime until)
    {
        /* nothing to do */
    }
}
