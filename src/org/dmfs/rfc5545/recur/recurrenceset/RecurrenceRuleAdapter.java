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

package org.dmfs.rfc5545.recur.recurrenceset;

import org.dmfs.rfc5545.recur.Calendar;
import org.dmfs.rfc5545.recur.RecurrenceIterator;
import org.dmfs.rfc5545.recur.RecurrenceRule;


/**
 * Implements {@link AbstractRecurrenceAdapter} for a {@link RecurrenceRule}.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class RecurrenceRuleAdapter extends AbstractRecurrenceAdapter
{

	/**
	 * The {@link RecurrenceIterator}.
	 */
	private RecurrenceIterator mIterator;


	/**
	 * Create a new adapter for the given rule and start.
	 * 
	 * @param rule
	 *            The recurrence rule to adapt to.
	 * @param start
	 *            The first instance to iterate.
	 */
	public RecurrenceRuleAdapter(RecurrenceRule rule, Calendar start)
	{
		mIterator = rule.iterator(start);
	}


	@Override
	public boolean hasNext()
	{
		return mIterator.hasNext();
	}


	@Override
	public long next()
	{
		return mIterator.nextMillis();
	}


	@Override
	public long peek()
	{
		return mIterator.peekMillis();
	}


	@Override
	public void skip(int count)
	{
		mIterator.skip(count);
	}
}
