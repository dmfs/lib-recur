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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dmfs.rfc5545.recur.Calendar;


/**
 * A recurrence set iterator. This class iterates all instances of a recurrence set. A recurrence set consists of all instances defined by a recurrence rule or
 * a list if instances except for exception instances. Exception instances are defined by exceptions rules or lists of exception instances.
 * <p>
 * This class allows you to add any number of recurrence rules, recurrence instances, exception rules and exception instance. It returns an {@link Iterator}
 * that iterates all resulting instances.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class RecurrenceSet
{

	/**
	 * All the instances in the set. Not all of them may be iterated, since instances that are exceptions will be skipped.
	 */
	private final List<AbstractRecurrenceAdapter> mInstances = new ArrayList<AbstractRecurrenceAdapter>();

	/**
	 * All exceptions in the set.
	 */
	private List<AbstractRecurrenceAdapter> mExceptions = null;

	/**
	 * The first instance.
	 */
	private Calendar mStart;


	/**
	 * Add instances to the set of instances.
	 * 
	 * @param adapter
	 *            An {@link AbstractRecurrenceAdapter} that defines instances.
	 */
	public void addInstances(AbstractRecurrenceAdapter adapter)
	{
		mInstances.add(adapter);
	}


	/**
	 * Add exceptions to the set of instances (i.e. effectively remove instances from the instance set).
	 * 
	 * @param adapter
	 *            An {@link AbstractRecurrenceAdapter} that defines instances.
	 */
	public void addExceptions(AbstractRecurrenceAdapter adapter)
	{
		if (mExceptions == null)
		{
			mExceptions = new ArrayList<AbstractRecurrenceAdapter>();
		}
		mExceptions.add(adapter);
	}


	/**
	 * Set a start date for the iteration.
	 * 
	 * @param start
	 *            The first instance to iterate.
	 */
	public void setStart(Calendar start)
	{
		mStart = start;
	}


	public RecurrenceSetIterator iterator()
	{
		if (mStart == null)
		{
			throw new IllegalStateException("Start date not set! Use setStart(Calendar) to set a start before iterating instances.");
		}
		return new RecurrenceSetIterator(mInstances, mExceptions, mStart);
	}
}
