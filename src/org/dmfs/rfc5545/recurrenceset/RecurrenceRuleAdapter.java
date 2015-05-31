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

package org.dmfs.rfc5545.recurrenceset;

import java.util.TimeZone;

import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;


/**
 * Implements {@link AbstractRecurrenceAdapter} for a {@link RecurrenceRule}.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class RecurrenceRuleAdapter extends AbstractRecurrenceAdapter
{

	class InstanceIterator implements AbstractRecurrenceAdapter.InstanceIterator
	{
		private final RecurrenceRuleIterator mIterator;


		public InstanceIterator(RecurrenceRuleIterator iterator)
		{
			mIterator = iterator;
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


		@Override
		public void fastForward(long until)
		{
			mIterator.fastForward(until);
		}

	}

	/**
	 * The recurrence rule.
	 */
	private final RecurrenceRule mRrule;


	/**
	 * Create a new adapter for the given rule and start.
	 * 
	 * @param rule
	 *            The recurrence rule to adapt to.
	 */
	public RecurrenceRuleAdapter(RecurrenceRule rule)
	{
		mRrule = rule;
	}


	@Override
	AbstractRecurrenceAdapter.InstanceIterator getIterator(TimeZone timezone, long start)
	{
		return new InstanceIterator(mRrule.iterator(start, timezone));
	}


	@Override
	boolean isInfinite()
	{
		return mRrule.isInfinite();
	}


	@Override
	long getLastInstance(TimeZone timezone, long start)
	{
		if (isInfinite())
		{
			return Long.MAX_VALUE;
		}

		RecurrenceRuleIterator iterator = mRrule.iterator(start, timezone);
		iterator.skipAllButLast();

		long lastInstance = Long.MIN_VALUE;
		if (iterator.hasNext())
		{
			lastInstance = iterator.nextMillis();
		}
		return lastInstance;
	}
}
