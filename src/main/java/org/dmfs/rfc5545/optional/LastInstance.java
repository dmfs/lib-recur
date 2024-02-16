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
import org.dmfs.jems2.optional.DelegatingOptional;
import org.dmfs.jems2.optional.Restrained;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.RecurrenceSet;

/**
 * The {@link Optional} last {@link DateTime} of a {@link RecurrenceSet}.
 * The value is absent when the {@link RecurrenceSet} is empty or infinite.
 */
public final class LastInstance extends DelegatingOptional<DateTime>
{
    public LastInstance(RecurrenceSet recurrenceSet)
    {
        super(new Restrained<>(() -> !recurrenceSet.isInfinite(), new Last<>(recurrenceSet)));
    }
}
