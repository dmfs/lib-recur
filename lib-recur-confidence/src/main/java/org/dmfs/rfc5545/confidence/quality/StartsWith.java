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

package org.dmfs.rfc5545.confidence.quality;

import org.dmfs.jems2.iterable.First;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.RecurrenceSet;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.iterable.Iterates;

@StaticFactories(value = "Recur", packageName = "org.dmfs.rfc5545.confidence")
public final class StartsWith extends QualityComposition<RecurrenceSet>
{
    public StartsWith(DateTime... instances)
    {
        super(new Has<>("first " + instances.length + " instances",
            candidate -> new First<>(instances.length, candidate), new Iterates<>(instances)));
    }
}
