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

/**
 * A {@link Limiter} that filters all instances after a certain date (the one specified in the UNTIL part).
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
class UntilLimiter extends Limiter
{
	/**
	 * The latest allowed instance start date.
	 */
	private final Instance mUntil;


	/**
	 * Create a new limiter for the UNTIL part.
	 * 
	 * @param rule
	 *            The {@link RecurrenceRule} to filter.
	 * @param previous
	 *            The previous filter instance.
	 * @param start
	 *            The first instance. This is used to determine if the iterated instances are floating or not.
	 */
	public UntilLimiter(RecurrenceRule rule, RuleIterator previous, Calendar start)
	{
		super(previous);
		Calendar until = rule.getUntil().clone();
		if (!start.isFloating())
		{
			// switch until to the time zone of start
			until.setTimeZone(start.getTimeZone());
		}
		mUntil = new Instance(until);
	}


	@Override
	boolean stop(Instance instance)
	{
		// stop once the instance is after the until date
		return mUntil.compareTo(instance) < 0;
	}
}
