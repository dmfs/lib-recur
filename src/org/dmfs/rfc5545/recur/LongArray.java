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

package org.dmfs.rfc5545.recur;

import java.util.Arrays;


/**
 * Maintains a list of <code>long</code> values.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
final class LongArray
{
	/**
	 * The default number of entries. 48 should hold most expansion interval sets.
	 */
	private final static int DEFAULT_SIZE = 48;

	/**
	 * The long values.
	 */
	private long[] mLongs;

	/**
	 * The number of valid longs in {@link #mLongs}.
	 */
	private int mCount = 0;

	/**
	 * The current iterator position.
	 */
	private int mPos = 0;

	/**
	 * Indicates whether this field is sorted or not. If this is <code>true</code> the field is strictly monotonically ordered.
	 */
	private boolean mSorted = true;


	/**
	 * Create a new LongArray with the default size of {@value #DEFAULT_SIZE} entries.
	 */
	public LongArray()
	{
		this(DEFAULT_SIZE);
	}


	/**
	 * Create a new LongArray with the given initial size (number of elements).
	 * 
	 * @param size
	 *            The initial size of the array.
	 */
	public LongArray(int size)
	{
		mLongs = new long[size];
	}


	/**
	 * Append a new long value to the array. The backing field is resized as needed.
	 * 
	 * @param data
	 *            The value to append.
	 */
	public void add(long data)
	{
		long[] longs = mLongs;
		int len = longs.length;
		int count = mCount;
		if (count == len)
		{
			longs = resizeBuffer(len + (len >> 1));
		}
		/*
		 * The condition below is too strict. The field would still be sorted if data = longs[count -1], but that can only happen when SKIP != YES. In this case
		 * we have to deduplicate anyway. If we keep the condition strict like that we can use it as an indicator for if we need to deduplicate.
		 */
		mSorted &= count == 0 || data > longs[count - 1];
		longs[count++] = data;
		mCount = count;
	}


	/**
	 * Creates a new buffer with the given size and copies the contents of the old buffer.
	 * 
	 * @param newSize
	 *            The new buffer size.
	 * 
	 * @return the new buffer.
	 */
	private long[] resizeBuffer(int newSize)
	{
		long[] newBuffer = new long[newSize];
		long[] oldBuffer = mLongs;
		System.arraycopy(oldBuffer, 0, newBuffer, 0, Math.min(oldBuffer.length, newSize));
		return mLongs = newBuffer;
	}


	/**
	 * Sort the array.
	 */
	public void sort()
	{
		if (!mSorted)
		{
			Arrays.sort(mLongs, 0, mCount);
			mSorted = true;
		}
	}


	/**
	 * Clear the buffer.
	 */
	public void clear()
	{
		mCount = 0;
		mPos = 0;
		mSorted = true;
	}


	/**
	 * Get the number of entries in the array.
	 * 
	 * @return
	 */
	public int size()
	{
		return mCount;
	}


	/**
	 * Check if there are more entries to iterate by {@link #next()}.
	 * 
	 * @return <code>true</code> if the next call to {@link #next()} will return another long, <code>false</code> otherwise.
	 */
	public boolean hasNext()
	{
		return mPos < mCount;
	}


	/**
	 * Get the next long from the array.
	 * 
	 * @return the next long value.
	 */
	public long next()
	{
		if (mPos >= mCount)
		{
			throw new ArrayIndexOutOfBoundsException("no more elements");
		}
		return mLongs[mPos++];
	}


	/**
	 * Peek at the next long from the array without actually iterating it.
	 * 
	 * @return the next long value.
	 */
	public long peek()
	{
		if (mPos >= mCount)
		{
			throw new ArrayIndexOutOfBoundsException("no more elements");
		}
		return mLongs[mPos];
	}


	/**
	 * Sort the list and remove duplicate entries.
	 */
	public void deduplicate()
	{
		if (mSorted)
		{
			// the field is strictly monotonically ordered, which means there is no need to deduplicate
			return;
		}

		int count = mCount;
		sort();
		long[] longs = mLongs;

		int next = 1;
		long last = longs[0];
		for (int i = 1; i < count; ++i)
		{
			long current = longs[i];
			if (current > last)
			{
				longs[next++] = last = current;
			}
		}
		mCount = next;
	}
}
