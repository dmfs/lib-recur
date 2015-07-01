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

import org.dmfs.rfc5545.Instance;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;


/**
 * Enumeration of valid FREQ values. Each value provides methods to get the successor of a given date based the respective frequency an a given interval.
 */
public enum Freq
{
	YEARLY {
		@Override
		long next(CalendarMetrics calendarMetrics, long packedInstance, int interval)
		{
			return Instance.setYear(packedInstance, Instance.year(packedInstance) + interval);
		}


		@Override
		long next(CalendarMetrics calendarMetrics, long packedInstance, int interval, long minInstance)
		{
			int untilYear = Instance.year(minInstance);
			int nextYear = Instance.year(packedInstance);
			if (untilYear <= nextYear)
			{
				// already there
				return packedInstance;
			}
			return Instance.setYear(packedInstance, nextYear + ((untilYear - nextYear - 1) / interval + 1) * interval);
		}
	},

	MONTHLY {
		@Override
		long next(CalendarMetrics calendarMetrics, long packedInstance, int interval)
		{
			if (interval == 1)
			{
				return calendarMetrics.nextMonth(packedInstance);
			}
			else
			{
				return calendarMetrics.nextMonth(packedInstance, interval);
			}
		}
	},

	WEEKLY {
		@Override
		long next(CalendarMetrics calendarMetrics, long packedInstance, int interval)
		{
			return calendarMetrics.nextDay(packedInstance, interval * 7);
		}
	},

	DAILY {
		@Override
		long next(CalendarMetrics calendarMetrics, long packedInstance, int interval)
		{
			if (interval == 1)
			{
				return calendarMetrics.nextDay(packedInstance);
			}
			else
			{
				return calendarMetrics.nextDay(packedInstance, interval);
			}
		}
	},

	HOURLY {
		@Override
		long next(CalendarMetrics calendarMetrics, long packedInstance, int interval)
		{
			int nextHour = Instance.hour(packedInstance) + interval;

			if (nextHour > 23)
			{
				// roll over to the next day
				packedInstance = DAILY.next(calendarMetrics, packedInstance, nextHour / 24);
				nextHour %= 24;
			}
			return Instance.setHour(packedInstance, nextHour);
		}
	},

	MINUTELY {
		@Override
		long next(CalendarMetrics calendarMetrics, long packedInstance, int interval)
		{
			int nextMinute = Instance.minute(packedInstance) + interval;

			if (nextMinute > 59)
			{
				// roll over to the next hour
				packedInstance = HOURLY.next(calendarMetrics, packedInstance, nextMinute / 60);
				nextMinute %= 60;
			}
			return Instance.setMinute(packedInstance, nextMinute);
		}
	},

	SECONDLY {
		@Override
		long next(CalendarMetrics calendarMetrics, long packedInstance, int interval)
		{
			int nextSecond = Instance.second(packedInstance) + interval;

			if (nextSecond > 59)
			{
				// roll over to the next minute
				packedInstance = MINUTELY.next(calendarMetrics, packedInstance, nextSecond / 60);
				nextSecond %= 60;
			}
			return Instance.setSecond(packedInstance, nextSecond);
		}
	};

	/**
	 * Get the packed instance value that follows the given instance value after the given interval with respect to the current frequency.
	 * <p>
	 * Note that the result is not necessarily a valid date. Getting the successor of 2004-02-29 for {@link #YEARLY} at an interval of 1 will result in the
	 * invalid date 2005-02-29.
	 * </p>
	 * 
	 * This method doesn't perform any parameter validation.
	 * 
	 * @param calendarMetrics
	 *            The {@link CalendarMetrics} to use for the calculation.
	 * @param packedInstance
	 *            The packet instance to start from.
	 * @param interval
	 *            The interval, must be >0.
	 * @return The new packet instance value.
	 */
	abstract long next(CalendarMetrics calendarMetrics, long packedInstance, int interval);


	long next(CalendarMetrics calendarMetrics, long packedInstance, int interval, long minInstance)
	{
		// by default, we just iterate instances until we found the first one after untilInstance
		long upcomingInstance = packedInstance;
		while (upcomingInstance < minInstance)
		{
			packedInstance = upcomingInstance;
			upcomingInstance = next(calendarMetrics, packedInstance, interval);
		}
		return packedInstance;
	}
}