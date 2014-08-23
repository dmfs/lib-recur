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
import java.util.List;


/**
 * A collection of static methods.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class StaticUtils
{

	/**
	 * Convert a {@link List} of {@link Integer}s to a sorted array of <code>int</code>s.
	 * 
	 * @param list
	 *            The {@link List} to convert.
	 * @return an int[] or <code>null</code> if <code>list</code> is <code>null</code>.
	 */
	public static int[] ListToSortedArray(List<Integer> list)
	{
		if (list == null)
		{
			return null;
		}

		int count = list.size();
		int[] result = new int[count];
		int last = Integer.MIN_VALUE;
		boolean needsSorting = false;

		for (int i = 0; i < count; ++i)
		{
			int element = result[i] = list.get(i);
			needsSorting |= last > element;
			last = element;
		}
		if (needsSorting)
		{
			Arrays.sort(result);
		}
		return result;
	}


	/**
	 * Convert a {@link List} of {@link Integer}s to an array of <code>int</code>s.
	 * 
	 * @param list
	 *            The {@link List} to convert.
	 * @return an int[] or <code>null</code> if <code>list</code> is <code>null</code>.
	 */
	public static int[] ListToArray(List<Integer> list)
	{
		if (list == null)
		{
			return null;
		}

		int count = list.size();
		int[] result = new int[count];

		for (int i = 0; i < count; ++i)
		{
			result[i] = list.get(i);
		}

		return result;
	}


	/**
	 * Perform a linear search for an integer in a given array. For small fields a linear search can be faster than a binary search. So use this if you know
	 * your field contains only few entries.
	 * 
	 * @param array
	 *            The array to search (the hay stack). Must not be <code>null</code>!
	 * @param i
	 *            The value to search for (the needle).
	 * @return the position of the value in the array or <code>-1</code> if the value has not been found.
	 */
	public static int linearSearch(int[] array, int i)
	{
		for (int c = 0, len = array.length; c < len; ++c)
		{
			if (array[c] == i)
			{
				return c;
			}
		}
		return -1;
	}


	/**
	 * Perform a linear search for a long in a given array. For small fields a linear search can be faster than a binary search. So use this if you know your
	 * field contains only few entries.
	 * 
	 * @param array
	 *            The array to search (the hay stack). Must not be <code>null</code>!
	 * @param l
	 *            The value to search for (the needle).
	 * @return the position of the value in the array or <code>-1</code> if the value has not been found.
	 */
	public static int linearSearch(long[] array, long l)
	{
		return linearSearch(array, 0, array.length, l);
	}


	/**
	 * Perform a linear search for a long in a given array. For small fields a linear search can be faster than a binary search. So use this if you know your
	 * field contains only few entries.
	 * 
	 * @param array
	 *            The array to search (the hay stack). Must not be <code>null</code>!
	 * @param start
	 *            the inclusive start position of the range to search
	 * @param end
	 *            the exclusive end position of the range to search
	 * @param l
	 *            The value to search for (the needle).
	 * @return the position of the value in the array or <code>-1</code> if the value has not been found.
	 */
	public static int linearSearch(long[] array, int start, int end, long l)
	{
		for (int c = start; c < end; ++c)
		{
			if (array[c] == l)
			{
				return c;
			}
		}
		return -1;
	}
}
