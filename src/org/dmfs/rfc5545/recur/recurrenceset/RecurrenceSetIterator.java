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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.dmfs.rfc5545.recur.Calendar;


/**
 * An iterator for recurrence sets. It takes a number of {@link AbstractRecurrenceAdapter}s for instances and exceptions and iterates all resulting instances
 * (i.e. only the instances, not the exceptions).
 * <p>
 * This class doesn't implement the {@link Iterator} interface for one reasons:
 * </p>
 * <ul>
 * <li>An {@link Iterator} always returns an {@link Object}, so instead of a primitive <code>long</code> we would have to return a {@link Long}. That is an
 * additional object which doesn't have any advantage.</li>
 * </ul>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 */
public class RecurrenceSetIterator
{
	/**
	 * Calculate this many instances in advance.
	 */
	private final static int INSTANCE_CACHE_SIZE = 32;

	/**
	 * Calculate this many exceptions in advance.
	 */
	private final static int EXCEPTION_CACHE_SIZE = 32;

	/**
	 * Throw if we skipped this many instances in a line, because they were exceptions.
	 */
	private final static int MAX_SKIPPED_INSTANCES = 1000;

	/**
	 * An array of {@link AbstractRecurrenceAdapter} with instances. Call
	 * 
	 * <pre>
	 * Arrays.sort(mInstances, mAdapterComparator)
	 * </pre>
	 * 
	 * every time you call {@link AbstractRecurrenceAdapter#next()} of the the first element to ensure the first element always has the next instance.
	 */
	private AbstractRecurrenceAdapter[] mInstances;

	/**
	 * An array of {@link AbstractRecurrenceAdapter} with exceptions. Call
	 * 
	 * <pre>
	 * Arrays.sort(mExceptions, mAdapterComparator)
	 * </pre>
	 * 
	 * every time you call {@link AbstractRecurrenceAdapter#next()} of the the first element to ensure the first element always has the next exception.
	 */
	private AbstractRecurrenceAdapter[] mExceptions;

	/**
	 * A number of cached instances. The instances are guaranteed to be monotonically increasing and to not contain any exceptions.
	 */
	private long[] mInstanceCache;

	/**
	 * The number of instances in {@link #mInstanceCache}.
	 */
	private int mInstancesInCache;

	/**
	 * The next instance to iterate.
	 */
	private int mNextInstance = 0;

	/**
	 * A number of cached exceptions. The exceptions are guaranteed to be monotonically increasing.
	 */
	private long[] mExceptionCache;

	/**
	 * The number of exceptions in {@link #mExceptionCache}.
	 */
	private int mExceptionsInCache;

	/**
	 * The index of the last exception we've found. This is an optimization to reduce the number of exceptions to scan.
	 */
	private int mLastExceptionIndex;

	private long mIterateEnd = Long.MAX_VALUE;

	/**
	 * A comparator that is used to sort the {@link AbstractRecurrenceAdapter}s in {@link #mInstances} and {@link #mExceptions}.
	 */
	private Comparator<AbstractRecurrenceAdapter> mAdapterComparator = new Comparator<AbstractRecurrenceAdapter>()
	{

		@Override
		public int compare(AbstractRecurrenceAdapter o1, AbstractRecurrenceAdapter o2)
		{
			boolean hasNext1 = o1.hasNext();
			boolean hasNext2 = o2.hasNext();

			if (hasNext1 && hasNext2)
			{
				long diff = o1.peek() - o2.peek();
				return diff < 0 ? -1 : diff > 0 ? 1 : 0;
			}
			else
			{
				return hasNext1 ? -1 : 1;
			}
		}
	};


	/**
	 * Create a new recurrence iterator for specific lists of instances and exceptions.
	 * 
	 * @param instances
	 *            The instances, must not be <code>null</code> or empty.
	 * @param exceptions
	 *            The exceptions, may be null.
	 * @param start
	 *            The first instance.
	 */
	RecurrenceSetIterator(List<AbstractRecurrenceAdapter> instances, List<AbstractRecurrenceAdapter> exceptions, Calendar start)
	{
		mInstances = instances.toArray(new AbstractRecurrenceAdapter[instances.size()]);
		Arrays.sort(mInstances, mAdapterComparator);

		if (exceptions != null && exceptions.size() > 0)
		{
			mExceptions = exceptions.toArray(new AbstractRecurrenceAdapter[exceptions.size()]);
			Arrays.sort(mExceptions, mAdapterComparator);
		}
		else
		{
			mExceptions = null;
		}
	}


	public void setIterateEnd(long end)
	{
		mIterateEnd = end;
	}


	/**
	 * Check if there is at least one more instance to iterate.
	 * 
	 * @return <code>true</code> if the next call to {@link #next()} will return another instance, <code>false</code> othwerwise.
	 */
	public boolean hasNext()
	{
		if (mInstanceCache == null || mNextInstance == INSTANCE_CACHE_SIZE)
		{
			// get the next set of instances
			fillInstanceCache();
		}

		return mNextInstance < mInstancesInCache;
	}


	/**
	 * Get the next instance of this set. Do not call this if {@link #hasNext()} returns <code>false</code>.
	 * 
	 * @return The time in milliseconds since the epoch of the next instance.
	 * @throws ArrayIndexOutOfBoundsException
	 *             if there are no more instances.
	 */
	public long next()
	{
		if (mInstanceCache == null || mNextInstance == INSTANCE_CACHE_SIZE)
		{
			// get the next set of instances
			fillInstanceCache();
		}

		if (mNextInstance >= mInstancesInCache)
		{
			throw new ArrayIndexOutOfBoundsException("no more instances to iterate");
		}

		return mInstanceCache[mNextInstance++];
	}


	public void fastForward(long until)
	{
		if (mInstanceCache != null)
		{
			long[] instanceCache = mInstanceCache;
			int next = mNextInstance;
			int instanceCount = mInstancesInCache;

			while (next < instanceCount && instanceCache[next] < until)
			{
				next++;
			}

			if (next < mInstancesInCache)
			{
				mNextInstance = next;
				return;
			}
		}

		// no upcoming instances in cache, fast forward all adapters
		for (AbstractRecurrenceAdapter instances : mInstances)
		{
			instances.fastForward(until);
		}

		if (mExceptions != null)
		{
			for (AbstractRecurrenceAdapter exceptions : mExceptions)
			{
				exceptions.fastForward(until);
			}
		}
	}


	/**
	 * Populate the instance cache. It reads up to {@link #INSTANCE_CACHE_SIZE} instances into {@link #mInstanceCache} and sets {@link #mInstancesInCache} to
	 * the actual number of instances in the cache. If {@link #mInstancesInCache} is less than {@link #INSTANCE_CACHE_SIZE} then you know that there are no more
	 * instances.
	 * <p>
	 * This method already filters all exceptions, so {@link #mInstanceCache} contains only actual instances.
	 * </p>
	 */
	private void fillInstanceCache()
	{
		// recycle instance cache
		long[] instanceCache = mInstanceCache;
		if (instanceCache == null)
		{
			instanceCache = new long[INSTANCE_CACHE_SIZE];
			mInstanceCache = instanceCache;
		}
		long iterateEnd = mIterateEnd;

		AbstractRecurrenceAdapter[] instances = mInstances;
		int count = 0;
		int skipped = 0;
		long last = Long.MIN_VALUE;

		if (instances != null && instances.length == 1)
		{
			AbstractRecurrenceAdapter ra = instances[0];
			while (ra.hasNext() && count < INSTANCE_CACHE_SIZE)
			{
				try
				{
					long next = ra.next();
					if (next > iterateEnd)
					{
						break;
					}

					if (!isException(next) && last != next)
					{
						instanceCache[count] = next;
						++count;
						skipped = 0;
						last = next;
					}
					else if (last != next)
					{
						last = next;
						if (++skipped >= MAX_SKIPPED_INSTANCES)
						{
							break;
						}
					}
				}
				catch (IllegalArgumentException e)
				{
					instances = mInstances = null;
				}
			}
		}
		else if (instances != null)
		{
			while (instances.length > 0 && count < INSTANCE_CACHE_SIZE)
			{
				AbstractRecurrenceAdapter ra = instances[0];

				try
				{
					if (ra.hasNext())
					{
						long next = ra.next();

						if (next > iterateEnd)
						{
							break;
						}

						if (!isException(next) && last != next)
						{
							instanceCache[count] = next;
							++count;
							skipped = 0;
							last = next;
						}
						else if (last != next)
						{
							last = next;
							if (++skipped >= MAX_SKIPPED_INSTANCES)
							{
								break;
							}
						}

						Arrays.sort(instances, mAdapterComparator);
					}
					else
					{
						/*
						 * The first adapter has no more instances, remove it.
						 * 
						 * Since the rest of the list already sorted we don't have to sort again.
						 */
						AbstractRecurrenceAdapter[] tempInstances = new AbstractRecurrenceAdapter[instances.length - 1];
						System.arraycopy(instances, 1, tempInstances, 0, tempInstances.length);
						instances = mInstances = tempInstances;

					}

				}
				catch (IllegalArgumentException e)
				{
					// remove ra from the array
					AbstractRecurrenceAdapter[] tempInstances = new AbstractRecurrenceAdapter[instances.length - 1];
					System.arraycopy(instances, 1, tempInstances, 0, tempInstances.length);
					instances = mInstances = tempInstances;
				}
			}
		}
		mInstancesInCache = count;
		mNextInstance = 0;
	}


	/**
	 * Checks if the given instance matches any exception.
	 * 
	 * @param instance
	 *            The timestamp of the instance.
	 * @return true if instance is an Exception
	 */
	private boolean isException(long instance)
	{
		if (mExceptions == null)
		{
			// no exceptions: this will handle most of the cases
			return false;
		}

		if (mExceptionCache == null)
		{
			fillExceptionCache();
		}

		while (mExceptionsInCache > 0)
		{
			if (instance < mExceptionCache[0])
			{
				return false;
			}
			else if (instance <= mExceptionCache[mExceptionsInCache - 1])
			{
				// if instance is an exception it must be in the cache, don't scan exceptions preceding the last one
				int pos = Arrays.binarySearch(mExceptionCache, mLastExceptionIndex, mExceptionsInCache, instance);

				if (pos >= 0)
				{
					mLastExceptionIndex = pos;
					return true;
				}
				return false;
			}
			else
			{
				// instance can not be in the cache, get next exceptions
				fillExceptionCache();
			}
		}

		// no more exceptions
		return false;
	}


	/**
	 * Populate the exception cache. It reads up to {@link #EXCEPTION_CACHE_SIZE} exceptions into {@link #mExceptionCache} and sets {@link #mExceptionsInCache}
	 * to the actual number of exceptions in the cache. If {@link #mExceptionsInCache} is less than {@link #EXCEPTION_CACHE_SIZE} then you know that there are
	 * no more exceptions.
	 */
	private void fillExceptionCache()
	{
		mLastExceptionIndex = 0;

		// recycle exception cache, if any
		long[] exceptionCache = mExceptionCache;
		if (exceptionCache == null)
		{
			exceptionCache = new long[EXCEPTION_CACHE_SIZE];
			mExceptionCache = exceptionCache;
		}
		long iterateEnd = mIterateEnd;

		AbstractRecurrenceAdapter[] exceptions = mExceptions;
		int count = 0;

		if (exceptions != null && exceptions.length == 1)
		{
			// handle the average case
			AbstractRecurrenceAdapter ra = exceptions[0];
			if (!ra.hasNext())
			{
				// no exceptions
				mExceptions = null;
				mExceptionsInCache = count;
				return;
			}

			while (ra.hasNext() && count < EXCEPTION_CACHE_SIZE)
			{
				try
				{
					long next = exceptionCache[count] = ra.next();
					if (next > iterateEnd)
					{
						break;
					}
					++count;
				}
				catch (IllegalArgumentException e)
				{
					exceptions = mExceptions = null;
				}
			}
		}
		else
		{
			while (exceptions != null && exceptions.length > 0 && count < EXCEPTION_CACHE_SIZE)
			{
				AbstractRecurrenceAdapter ra = exceptions[0];
				try
				{
					if (ra.hasNext())
					{
						long next = exceptionCache[count] = ra.next();
						if (next > iterateEnd)
						{
							break;
						}

						++count;

						Arrays.sort(exceptions, mAdapterComparator);
					}
					else
					{
						/*
						 * The first adapter has no more exceptions, remove it.
						 * 
						 * Since the rest of the list is already sorted we don't have to sort again.
						 */
						if (exceptions.length > 1)
						{
							AbstractRecurrenceAdapter[] tempExceptions = new AbstractRecurrenceAdapter[exceptions.length - 1];
							System.arraycopy(exceptions, 1, tempExceptions, 0, tempExceptions.length);
							exceptions = mExceptions = tempExceptions;
						}
						else
						{
							exceptions = mExceptions = null;
						}
					}
				}
				catch (IllegalArgumentException e)
				{
					// remove ra from the array
					if (exceptions.length > 1)
					{
						AbstractRecurrenceAdapter[] tempExceptions = new AbstractRecurrenceAdapter[exceptions.length - 1];
						System.arraycopy(exceptions, 1, tempExceptions, 0, tempExceptions.length);
						exceptions = mExceptions = tempExceptions;
					}
					else
					{
						exceptions = mExceptions = null;
					}
				}
			}
		}
		mExceptionsInCache = count;
	}
}
