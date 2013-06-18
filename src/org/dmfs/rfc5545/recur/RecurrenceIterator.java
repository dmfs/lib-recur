/*
 * Copyright (C) 2013 Marten Gajda <marten@dmfs.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package org.dmfs.rfc5545.recur;

import java.util.Iterator;


/**
 * An iterator for recurrence rules.
 * <p>
 * <strong>Note:</strong> Some rules may recur forever, so be sure to add some limitation to your code that stops iterating after a certain number of instances
 * or at a certain date.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class RecurrenceIterator implements Iterator<Calendar>
{
	/**
	 * The previous iterator instance. This is <code>null</code> for the {@link FreqIterator}.
	 */
	private final RuleIterator mRuleIterator;

	/**
	 * The first instance. This is used to adjust the time zone and the all-day flag of the instances.
	 */
	private final Calendar mStart;

	/**
	 * The upcoming instance, if any.
	 */
	private Instance mNextInstance;


	/**
	 * Creates a new iterator that gets its input from <code>previous</code>.
	 * 
	 * @param ruleIterator
	 *            A RuleIterator that precedes this one in the chain of iterators or <code>null</code> if this is the first iterator (i.e. {@link FreqIterator}
	 *            ).
	 */
	RecurrenceIterator(RuleIterator ruleIterator, Calendar start)
	{
		mRuleIterator = ruleIterator;
		mStart = start;
	}


	/**
	 * Get the next instance. The instances are guaranteed to be strictly increasing in time.
	 * 
	 * @return A {@link Calendar} object for the next instance.
	 */
	@Override
	public Calendar next()
	{
		Instance instance;
		if (mNextInstance == null)
		{
			instance = mRuleIterator.next();
		}
		else
		{
			instance = mNextInstance;
			mNextInstance = null;
		}

		if (instance == null)
		{
			throw new ArrayIndexOutOfBoundsException("No more instances to iterate.");
		}

		if (mStart.isFloating())
		{
			if (mStart.isAllDay())
			{
				return new Calendar(instance.year, instance.month, instance.dayOfMonth);
			}
			else
			{
				return new Calendar(instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
			}
		}
		else
		{
			return new Calendar(mStart.getTimeZone(), instance.year, instance.month, instance.dayOfMonth, instance.hour, instance.minute, instance.second);
		}
	}


	@Override
	public boolean hasNext()
	{
		return (mNextInstance != null || (mNextInstance = mRuleIterator.next()) != null);
	}


	/**
	 * Skip the given number of instances.
	 * <p>
	 * <strong>Note:</strong> After calling this method you should call {@link #hasNext()} before you continue because there might be less than
	 * <code>skip</code> instances left when you call this.
	 * </p>
	 * 
	 * @param skip
	 *            The number of instances to skip.
	 */
	public void skip(int skip)
	{
		if (mNextInstance != null)
		{
			--skip;
			mNextInstance = null;
		}
		while (skip > 0 && mRuleIterator.next() != null)
		{
			--skip;
		}
	}


	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("Remove is not supported by this iterator");
	}
}
