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
 * Helper to maintain a list of <code>long</code> values.
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
		int len = mLongs.length;
		if (mCount == len)
		{
			resizeBuffer(len + (len >> 1));
		}
		mLongs[mCount++] = data;
	}


	/**
	 * Creates a new buffer with the given size and copies the contents of the old buffer.
	 * 
	 * @param newSize
	 *            The new buffer size.
	 */
	private void resizeBuffer(int newSize)
	{
		long[] newBuffer = new long[newSize];
		System.arraycopy(mLongs, 0, newBuffer, 0, Math.min(mLongs.length, newSize));
		mLongs = newBuffer;
	}


	/**
	 * Sort the array.
	 */
	public void sort()
	{
		int count = mCount;
		if (count > 1)
		{
			Arrays.sort(mLongs, 0, count);
		}
	}


	/**
	 * Clear the buffer.
	 */
	public void clear()
	{
		mCount = 0;
		mPos = 0;
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
}
