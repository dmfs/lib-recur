/*
 * Copyright (C) 2012 Marten Gajda <marten@dmfs.org>
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

/**
 * A {@link Limiter} that limits by the number of iterated instances.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
class CountLimiter extends Limiter
{
	/**
	 * The number of instances to let pass.
	 */
	private final int mLimit;

	/**
	 * The number of instances already passed.
	 */
	private int mCounter = 0;


	/**
	 * Creates a limiter that limits by instance number.
	 * 
	 * @param rule
	 *            The {@link RecurrenceRule} to limit.
	 * @param previous
	 *            The previous iterator instance.
	 */
	CountLimiter(RecurrenceRule rule, RuleIterator previous)
	{
		super(previous);
		mLimit = rule.getCount();
	}


	@Override
	boolean stop(Instance instance)
	{
		// Stop if more than mLimit instances have been iterated.
		return ++mCounter > mLimit;
	}

}
