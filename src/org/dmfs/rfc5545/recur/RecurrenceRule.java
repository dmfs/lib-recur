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

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Builder and parser for recurrence rule strings that comply with <a href="http://tools.ietf.org/html/rfc2445#section-4.3.10">RFC 2445</a> or <a
 * href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a>.
 * <p>
 * This class implements {@link Iterable} to iterate all instances of a rule. To use this feature be sure to set a start date using {@link #setStart(Calendar)}
 * first. Also note that some rule may recur forever (or at least many times), so don't forget to add some kind of limiter.
 * </p>
 * <p>
 * The goal if this implementation is to satisfy the following qualities:
 * </p>
 * <ul>
 * <li>correctness: The instances returned by the iterator shall be correct for all common cases, i.e. they follow all rules defined in RFC 5545/RFC 2445.</li>
 * <li>completeness: The iterator shall support all valid combinations defined RFC 5545/RFC 2445 and return reasonable results for edge cases that are not
 * explicitly mentioned.</li>
 * <li>performance: The iterator shall be as efficient as possible.
 * </ul>
 * <p>
 * TODO: Add validator and a validator log.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class RecurrenceRule
{

	/**
	 * Enumeration of supported rule versions.
	 */
	public enum RfcMode
	{
		/**
		 * Parses recurrence rules according to <a href="http://tools.ietf.org/html/rfc2445#section-4.3.10">RFC 2445</a>. Every error will cause an exception to
		 * be thrown.
		 */
		RFC2445_STRICT,

		/**
		 * Parses recurrence rules according to <a href="http://tools.ietf.org/html/rfc2445#section-4.3.10">RFC 2445</a> in a more tolerant way. The parser will
		 * just skip invalid parts in the rule and won't complain as long as the result is a valid rule.
		 * <p>
		 * This mode also accepts rules that comply with <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a>.
		 * </p>
		 * <p>
		 * <strong>Note:</strong> Using this mode rules are evaluated differently than with {@link #RFC5545_LAX}. {@link #RFC5545_LAX} will just drop all
		 * invalid parts and evaluate the rule according to RFC 5545. This mode will evaluate all rules.
		 * 
		 * Also this mode will output rules that comply with RFC 2445.
		 * </p>
		 */
		RFC2445_LAX,

		/**
		 * Parses recurrence rules according to <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a>. Every error will cause an exception to
		 * be thrown.
		 */
		RFC5545_STRICT,

		/**
		 * Parses recurrence rules according to <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a> in a more tolerant way. The parser will
		 * just skip invalid parts in the rule and won't complain as long as the result is a valid rule.
		 * <p>
		 * This mode also accepts rules that comply with <a href="http://tools.ietf.org/html/rfc2445#section-4.3.10">RFC 2445</a> but not with RFC 5545.
		 * </p>
		 * <p>
		 * <strong>Note:</strong> Using this mode rules are evaluated differently than with {@link #RFC2445_LAX}. This mode will just drop all invalid parts and
		 * evaluate the rule according to RFC 5545. {@link #RFC2445_LAX} will evaluate all rules.
		 * 
		 * Also this mode will output rules that comply with RFC 5545.
		 * </p>
		 */
		RFC5545_LAX;
	}

	/**
	 * Enumeration of valid FREQ values.
	 */
	public enum Freq
	{
		SECONDLY, MINUTELY, HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY;
	}

	/**
	 * Enumeration of valid week days. The weekdays are ordered, so you can use the ordinal value to get the week day number (i.e.
	 * <code>Weekday.SU.ordinal() == 0</code>, <code>Weekday.MO.ordinal() == 1 </code>...).
	 * <p>
	 * Please not that the ordinal value is not compatible with the day values of {@link java.util.Calendar}. Use {@link #toCalendarDay()} to get a value that
	 * is compatible to {@link java.util.Calendar}.
	 * </p>
	 */
	public enum Weekday
	{
		SU, MO, TU, WE, TH, FR, SA;

		/**
		 * Get an ordinal value that is compatible with {@link java.util.Calendar}.
		 * 
		 * @return A number between 1 (for {@link #SU}) and 7 (for {@link #SA}).
		 */
		public int toCalendarDay()
		{
			// in java.util.Calendar SU is 1
			return ordinal() + 1;
		}
	}

	/**
	 * Enumeration of valid recurrence rule parts. Each of these parts may occur once in a rule. {@link #FREQ} is the only mandatory part.
	 * <p>
	 * Each part has a {@link ValueConverter} that knows how to parse and serialize the values the part can have. Also each part has a factory method to return
	 * a {@link RuleIterator} for this part. {@link #FREQ}, {@link #INTERVAL} and {@link #WKST} don't support the iterator and will throw an
	 * {@link UnsupportedOperationException} when calling {@link Part#getRuleIterator(RecurrenceRule, RuleIterator)}.
	 * </p>
	 */
	public enum Part
	{
		/**
		 * Base frequency of the recurring instances. This value is mandatory in every recurrence rule. The value must be a {@link Freq}.
		 */
		FREQ(new FreqConverter()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new FreqIterator(rule, calendarTools, start);
			}
		},

		/**
		 * The base interval of the recurring instances. If not specified the interval is <code>1</code>. The value must be a positive integer.
		 */
		INTERVAL(new IntConverter()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				throw new UnsupportedOperationException("INTERVAL doesn't have an iterator.");
			}
		},
		/**
		 * The start day of a week. The value must be a {@link Weekday}. This is relevant if any of {@link Part#BYDAY} or {@link Part#BYWEEKNO} are present.
		 */
		WKST(new WeekdayConverter()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				throw new UnsupportedOperationException("WKST doesn't have an iterator.");
			}
		},

		/**
		 * A list of months that specify in which months the instances recur. The value is a list of integers in the range 1 to 12.
		 */
		BYMONTH(new IntListConverter(1, 12)) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new ByMonthFilter(rule, previous, calendarTools, start);
			}
		},

		/**
		 * A list of week numbers that specify in which weeks the instances recur. The value is a list of integers in the range -53 to -1 or 1 to 53.
		 */
		BYWEEKNO(new IntListConverter(-53, 53).noZero()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new ByWeekNoFilter(rule, previous, calendarTools, start);
			}
		},

		/**
		 * A list of year days that specify on which year days the instances recur. The value is a list of integers in the range -366 to -1 or 1 to 366.
		 */
		BYYEARDAY(new IntListConverter(-366, 366).noZero()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new ByYearDayFilter(rule, previous, calendarTools, start);
			}
		},

		/**
		 * A list of month days on which the event recurs. Valid values are in the range of -31 to -1 or 1 to 31.
		 */
		BYMONTHDAY(new IntListConverter(-31, 31).noZero()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new ByMonthDayFilter(rule, previous, calendarTools, start);
			}
		},

		/**
		 * A list of {@link WeekdayNum}s on which the event recurs.
		 */
		BYDAY(new WeekdayListConverter()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new ByDayFilter(rule, previous, calendarTools, start);
			}
		},

		/**
		 * The hours on which the event recurs. The value must be a list of integers in the range 0 to 23.
		 */
		BYHOUR(new IntListConverter(0, 23)) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new ByHourFilter(rule, previous, calendarTools, start);
			}
		},
		/**
		 * The minutes on which the event recurs. The value must be a list of integers in the range 0 to 59.
		 */
		BYMINUTE(new IntListConverter(0, 59)) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new ByMinuteFilter(rule, previous, calendarTools, start);
			}
		},
		/**
		 * The seconds on which the event recurs. The value must be a list of integers in the range 0 to 60.
		 */
		BYSECOND(new IntListConverter(0, 60)) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new BySecondFilter(rule, previous, calendarTools, start);
			}
		},

		/**
		 * A list of set positions to consider when iterating the instances. The value is a list of integers between -366 and -1 or 1 and 366.
		 */
		BYSETPOS(new IntListConverter(-366, 366).noZero()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new BySetPosFilter(rule, previous, start);
			}
		},

		/**
		 * This part specifies the latest date of the last instance. The value is a {@link DateTime} in UTC or the local time zone. This part is mutually
		 * exclusive with {@link #COUNT}. If neither {@link #UNTIL} nor {@link #COUNT} are specified the instances recur forever.
		 */
		UNTIL(new DateTimeConverter()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new UntilLimiter(rule, previous, start);
			}
		},

		/**
		 * This part specifies total number of instances. The value is a positive integer. This part is mutually exclusive with {@link #UNTIL}. If neither
		 * {@link #COUNT} nor {@link #UNTIL} are specified the instances recur forever.
		 */
		COUNT(new IntConverter()) {
			@Override
			RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			{
				return new CountLimiter(rule, previous);
			}
		};

		/**
		 * A {@link ValueConverter} that can parse and serialize the value for a specific part. The generic type depends on the actual part.
		 */
		final ValueConverter<?> converter;


		/**
		 * Private constructor.
		 * 
		 * @param converter
		 *            The {@link ValueConverter} for that knows how to parse and serialize this part.
		 */
		private Part(ValueConverter<?> converter)
		{
			this.converter = converter;
		}


		/**
		 * Return a {@link RuleIterator} that is suitable to build a recurrence rule filter chain in order to iterate all instances.
		 * <p>
		 * <strong>Note:</strong> {@link #FREQ}, {@link #INTERVAL} and {@link #WKST} don't support this method and throw an
		 * {@link UnsupportedOperationException}.
		 * </p>
		 * 
		 * @param rule
		 *            The rule to iterate.
		 * @param previous
		 *            The previous element in the filter chain.
		 * @return The {@link RuleIterator} for this part.
		 * 
		 * @throws UnsupportedOperationException
		 *             If this part does not have a {@link RuleIterator}.
		 */
		abstract RuleIterator getRuleIterator(RecurrenceRule rule, RuleIterator previous, CalendarMetrics calendarTools, Calendar start)
			throws UnsupportedOperationException;
	}

	/**
	 * This class represents the position of a {@link Weekday} in a specific range. It parses values like <code>-4SU</code> which means the fourth last Sunday
	 * in the interval or <code>2MO</code> which means the second Monday in the interval. In addition this class accepts simple weekdays like <code>SU</code>
	 * which means every Sunday in the interval.
	 * <p>
	 * These values are defined as:
	 * </p>
	 * 
	 * <pre>
	 *        weekdaynum  = [[plus / minus] ordwk] weekday
	 * 
	 *        plus        = "+"
	 * 
	 *        minus       = "-"
	 * 
	 *        ordwk       = 1*2DIGIT       ;1 to 53
	 * 
	 *        weekday     = "SU" / "MO" / "TU" / "WE" / "TH" / "FR" / "SA"
	 *        ;Corresponding to SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY,
	 *        ;FRIDAY, and SATURDAY days of the week.
	 * </pre>
	 */
	static class WeekdayNum
	{
		/**
		 * A regular expression that matches the String representation of the weekday set position.
		 */
		private final static Pattern WEEKDAYNUM_PATTERN = Pattern.compile("^((-|\\+)?\\d+)?(SU|MO|TU|WE|TH|FR|SA)$");

		/**
		 * The position of this weekday in the interval. This value is <code>0</code> if this instance means every occurrence of {@link #weekday} in the
		 * interval.
		 */
		public final int pos;

		/**
		 * The {@link Weekday}.
		 */
		public final Weekday weekday;


		/**
		 * Create a new WeekdayNum instance.
		 * 
		 * @param pos
		 *            The position of the weekday in the Interval or <code>0</code> for every occurrence of the weekday.
		 * @param weekday
		 *            The {@link Weekday}.
		 */
		public WeekdayNum(int pos, Weekday weekday)
		{
			if (pos < -53 || pos > 53)
			{
				throw new IllegalArgumentException("position " + pos + " of week day out of range");
			}
			this.pos = pos;
			this.weekday = weekday;
		}


		/**
		 * Parse a weekdaynum String as defined in <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545<a> (this definition equals the
		 * definition in RFC 2445).
		 * 
		 * @param value
		 *            The weekdaynum String to parse.
		 * @param tolerant
		 *            Set to <code>true</code> to be tolerant and accept values outside of the allowed range.
		 * @return A new {@link WeekdayNum} instance.
		 * @throws InvalidRecurrenceRuleException
		 *             If the weekdaynum string is invalid.
		 */
		public static WeekdayNum valueOf(String value, boolean tolerant) throws InvalidRecurrenceRuleException
		{
			Matcher m = WEEKDAYNUM_PATTERN.matcher(value);
			if (m.matches())
			{
				String pos = m.group(1);
				int setpos = 0;
				if (pos != null)
				{
					setpos = Integer.parseInt(pos.charAt(0) == '+' ? pos.substring(1) : pos);
					if (!tolerant && (setpos < -53 || setpos > 53 || setpos == 0))
					{
						throw new InvalidRecurrenceRuleException("position " + setpos + " of week day out of range");
					}
				}

				return new WeekdayNum(setpos, Weekday.valueOf(m.group(3)));
			}
			else
			{
				return null;
			}
		}


		/**
		 * Parse a weekdaynum String as defined in <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545<a> (this definition equals the
		 * definition in RFC 2445). In contrast to {@link #valueOf(String, boolean)} this method is always strict and throws on every invalid value.
		 * 
		 * @param value
		 *            The weekdaynum String to parse.
		 * @return A new {@link WeekdayNum} instance.
		 * @throws InvalidRecurrenceRuleException
		 *             If the weekdaynum string is invalid.
		 */
		public static WeekdayNum valueOf(String value) throws InvalidRecurrenceRuleException
		{
			return valueOf(value, false);
		}


		@Override
		public String toString()
		{
			return pos == 0 ? weekday.name() : Integer.valueOf(pos) + weekday.name();
		}
	}

	/**
	 * The parser mode. This can not be changed once the rule has been created.
	 */
	public final RfcMode mode;

	/**
	 * The parts of this rule.
	 */
	private EnumMap<Part, Object> mParts = new EnumMap<Part, Object>(Part.class);

	/**
	 * The first instance to iterate, if any.
	 */
	private Calendar mStart;

	/**
	 * Pre-built "FREQ=" string, used for validation in RFC2445_STRICT mode.
	 */
	private final static String FREQ_PREFIX = Part.FREQ.name() + "=";


	/**
	 * Create a new recurrence rule from String using the {@link RfcMode} {@link RfcMode#RFC5545_LAX}. The parser will be quite tolerant and skip any invalid
	 * parts to produce a valid recurrence rule.
	 * 
	 * @param recur
	 *            A recurrence rule string as defined in <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a>.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 *             If an unrecoverable error occurs when parsing the rule (like FREQ is missing, or mutually exclusive parts have been found).
	 */
	public RecurrenceRule(String recur) throws InvalidRecurrenceRuleException
	{
		this(recur, RfcMode.RFC5545_LAX);
	}


	/**
	 * Create a new recurrence rule from String using a custom {@link RfcMode}.
	 * 
	 * @param recur
	 *            A recurrence rule string as defined in <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545</a>.
	 * @param mode
	 *            A {@link RfcMode} to change the parsing behaviour in case of errors.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 *             If the rule is invalid with respect to the chosen mode or if an unrecoverable error occurs when parsing the rule (like FREQ is missing, or
	 *             mutually exclusive parts have been found).
	 */
	public RecurrenceRule(String recur, RfcMode mode) throws InvalidRecurrenceRuleException
	{
		this.mode = mode;
		parseString(recur);
	}


	/**
	 * Create a new recurrence rule with the given base frequency. This constructor will use {@link RfcMode#RFC5545_STRICT}, so created rules will have to
	 * comply with RFC 5545, otherwise an exception is thrown.
	 * 
	 * @param freq
	 *            The {@link Freq} values that specified the base frequency for this rule.
	 */
	public RecurrenceRule(Freq freq)
	{
		this(freq, RfcMode.RFC5545_STRICT);
	}


	/**
	 * Create a new recurrence rule with the given base frequency using a custom {@link RfcMode}.
	 * 
	 * @param freq
	 *            The {@link Freq} values that specified the base frequency for this rule.
	 * @param mode
	 *            A {@link RfcMode} to change the behavior in case of errors.
	 */
	public RecurrenceRule(Freq freq, RfcMode mode)
	{
		this.mode = mode;
		mParts.put(Part.FREQ, freq);
	}


	/**
	 * Parse the given recurrence rule and populate {@link #mParts}. This method is tolerant in a way that it just drops invalid parts not allowed in the
	 * current {@link RfcMode}. Also, it doesn't require FREQ to be the first part (that's required in <a
	 * href="http://tools.ietf.org/html/rfc2445#section-4.3.10">RFC 2445</a> but not in <a href="http://tools.ietf.org/html/rfc5545#section-3.3.10">RFC
	 * 5545</a>).
	 * 
	 * @param recur
	 *            A recurrence rule string.
	 * @throws InvalidRecurrenceRuleException
	 */
	private void parseString(String recur) throws InvalidRecurrenceRuleException
	{
		if (recur == null)
		{
			// definitely invalid!
			throw new NullPointerException("recur must not be null");
		}

		mParts.clear();

		if (mode == RfcMode.RFC2445_LAX || mode == RfcMode.RFC5545_LAX)
		{
			// remove any spaces in LAX modes
			recur = recur.trim();
		}

		String[] parts = recur.split(";");

		if (mode == RfcMode.RFC2445_STRICT && parts.length > 0 && !parts[0].startsWith(FREQ_PREFIX))
		{
			// in RFC2445 rules must start with "FREQ=" !
			throw new InvalidRecurrenceRuleException("RFC 2445 requires FREQ to be the first part of the rule: " + recur);
		}

		// now parse each part and add it to mParts.
		for (String keyvalue : parts)
		{
			int equals = keyvalue.indexOf("=");
			if (equals > 0)
			{
				String key = keyvalue.substring(0, equals);
				String value = keyvalue.substring(equals + 1);

				Part part = Part.valueOf(key);
				if (mParts.containsKey(part))
				{
					throw new InvalidRecurrenceRuleException(key + " must not occur twice.");
				}

				try
				{
					Object partValue = part.converter.parse(value);
					if (partValue != null)
					{
						this.mParts.put(part, partValue);
					}
				}
				catch (InvalidRecurrenceRuleException e)
				{
					if (mode == RfcMode.RFC2445_STRICT || mode == RfcMode.RFC5545_STRICT)
					{
						throw e;
					}
					else
					{
						// just skip invalid parts in lax modes
					}
				}

			}
			else if (mode == RfcMode.RFC2445_STRICT || mode == RfcMode.RFC5545_STRICT)
			{
				// strict modes throw on empty parts
				throw new InvalidRecurrenceRuleException("Found empty part in " + recur);
			}

		}

		// validate the rule
		validate();
	}


	/**
	 * Validate this rule.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 *             if the rule is not valid with respect to the current {@link #mode}.
	 */
	private void validate() throws InvalidRecurrenceRuleException
	{
		Freq freq = (Freq) mParts.get(Part.FREQ);

		// FREQ is mandatory
		if (!mParts.containsKey(Part.FREQ))
		{
			throw new InvalidRecurrenceRuleException("FREQ part is missing");
		}

		if (mParts.containsKey(Part.UNTIL) && mParts.containsKey(Part.COUNT))
		{
			throw new InvalidRecurrenceRuleException("UNTIL and COUNT must not occur in the same rule.");
		}

		if (getInterval() == 0)
		{
			if (mode == RfcMode.RFC5545_STRICT || mode == RfcMode.RFC2445_STRICT)
			{
				throw new InvalidRecurrenceRuleException("INTERVAL must not be 0");
			}
			else
			{
				// just remove interval and assume 1
				mParts.remove(Part.INTERVAL);
			}
		}

		if (mode == RfcMode.RFC5545_STRICT || mode == RfcMode.RFC2445_STRICT)
		{
			if (freq != Freq.YEARLY && mParts.containsKey(Part.BYWEEKNO))
			{
				throw new InvalidRecurrenceRuleException("BYWEEKNO is allowed in YEARLY rules only");
			}
		}

		if (mode == RfcMode.RFC5545_STRICT)
		{
			// in RFC 5545 BYYEARDAY does not support DAILY, WEEKLY and MONTHLY rules
			if ((freq == Freq.DAILY || freq == Freq.WEEKLY || freq == Freq.MONTHLY) && mParts.containsKey(Part.BYYEARDAY))
			{
				throw new InvalidRecurrenceRuleException("In RFC 5545, BYYEARDAY is not allowed in DAILY, WEEKLY or MONTHLY rules");
			}

			// in RFC 5545 BYMONTHAY must not be used in WEEKLY rules
			if (freq == Freq.WEEKLY && mParts.containsKey(Part.BYMONTHDAY))
			{
				throw new InvalidRecurrenceRuleException("In RFC 5545, BYMONTHDAY is not allowed in WEEKLY rules");
			}
		}

		if (mode == RfcMode.RFC2445_LAX || mode == RfcMode.RFC5545_LAX)
		{
			// in RFC 5545 BYWEEKNO can be used with YEARLY rules only
			if (freq != Freq.YEARLY && mParts.containsKey(Part.BYWEEKNO))
			{
				mParts.remove(Part.BYWEEKNO);
			}
		}

		if (mode == RfcMode.RFC5545_LAX)
		{
			// in RFC 5545 BYYEARDAY does not support DAILY, WEEKLY and MONTHLY rules
			if ((freq == Freq.DAILY || freq == Freq.WEEKLY || freq == Freq.MONTHLY) && mParts.containsKey(Part.BYYEARDAY))
			{
				mParts.remove(Part.BYYEARDAY);
			}

			// in RFC 5545 BYMONTHAY must not be used in WEEKLY rules
			if (freq == Freq.WEEKLY && mParts.containsKey(Part.BYMONTHDAY))
			{
				mParts.remove(Part.BYMONTHDAY);
			}
		}

		/**
		 * BYSETPOS is only valid in combination with another BYxxx rule. We therefore check the number of elements. If this number is larger than cnt the rule
		 * contains another BYxxx rule and is therefore valid.
		 */
		if (mParts.containsKey(Part.BYSETPOS))
		{

			int cnt = 2; // FREQ and BYSETPOS
			if (mParts.containsKey(Part.UNTIL) || mParts.containsKey(Part.COUNT))
			{
				cnt++;
			}
			if (mParts.size() - cnt <= 0)
			{
				if (mode == RfcMode.RFC2445_STRICT || mode == RfcMode.RFC5545_STRICT)
				{
					// we're in strict mode => throw exception
					throw new InvalidRecurrenceRuleException("BYSETPOS must only be used in conjunction with another BYxxx rule.");
				}
				else
				{
					// we're in lax mode => drop BYSETPOS
					mParts.remove(Part.BYSETPOS);
				}
			}

		}
	}


	/**
	 * Validate if adding a specific list part would result in a valid rule.
	 * 
	 * @param part
	 *            The part to be added.
	 * @param value
	 *            The value of this part.
	 * 
	 * @throws InvalidRecurrenceRuleException
	 *             if the rule is not valid with respect to the current {@link #mode}.
	 */
	private void validate(Part part, List<Integer> value) throws InvalidRecurrenceRuleException
	{
		Freq freq = (Freq) mParts.get(Part.FREQ);

		if (mode == RfcMode.RFC5545_STRICT)
		{
			// in RFC 5545 BYWEEKNO can be used with YEARLY rules only
			if (freq != Freq.YEARLY && part == Part.BYWEEKNO)
			{
				throw new InvalidRecurrenceRuleException("In RFC 5545, BYWEEKNO is allowed in YEARLY rules only");
			}

			// in RFC 5545 BYYEARDAY does not support DAILY, WEEKLY and MONTHLY rules
			if ((freq == Freq.DAILY || freq == Freq.WEEKLY || freq == Freq.MONTHLY) && part == Part.BYYEARDAY)
			{
				throw new InvalidRecurrenceRuleException("In RFC 5545, BYYEARDAY is not allowed in DAILY, WEEKLY or MONTHLY rules");
			}

			// in RFC 5545 BYMONTHAY must not be used in WEEKLY rules
			if (freq == Freq.WEEKLY && part == Part.BYMONTHDAY)
			{
				throw new InvalidRecurrenceRuleException("In RFC 5545, BYMONTHDAY is not allowed in WEEKLY rules");
			}
		}
	}


	/**
	 * Optimizes the rule for the recurrence processor for the given start date. In particular this method checks if there are any redundant BYMONTH,
	 * BYMONTHDAY, BYHOUR, BYMINUTE or BYSECOND parts are present.
	 * <p>
	 * <strong>NOTE:</strong> After optimizing the rule it must not be used with a different start date. The optimized rule will be wrong for a different start
	 * date.
	 * </p>
	 * <p>
	 * Also, for compatibility you must not export an optimized rule. Use it for recurrence iterations only.
	 * </p>
	 * 
	 * @param start
	 *            The recurrence start.
	 */
	@SuppressWarnings("unchecked")
	public void optimize(Calendar start)
	{
		List<Integer> byMonth = (List<Integer>) mParts.get(Part.BYMONTH);
		if (getFreq() == Freq.YEARLY && !hasPart(Part.BYWEEKNO) && !hasPart(Part.BYYEARDAY) && !hasPart(Part.BYDAY) && !hasPart(Part.BYMONTHDAY)
			&& byMonth != null && byMonth.size() == 1 && byMonth.get(0) == start.get(Calendar.MONTH) + 1)
		{
			// BYMONTH is redundant if it specifies the original month and the rule doesn't have monthly scope.
			mParts.remove(Part.BYMONTH);
		}

		List<Integer> byMonthDay = (List<Integer>) mParts.get(Part.BYMONTHDAY);
		if (getFreq() == Freq.MONTHLY && !hasPart(Part.BYWEEKNO) && !hasPart(Part.BYYEARDAY) && !hasPart(Part.BYDAY) && byMonthDay != null
			&& byMonthDay.size() == 1 && byMonthDay.get(0) == start.get(Calendar.DAY_OF_MONTH))
		{
			// BYMONTHDAY is redundant if it specifies the original month day and the rule doesn't have monthly scope.
			mParts.remove(Part.BYMONTHDAY);
		}

		List<Integer> byHour = (List<Integer>) mParts.get(Part.BYHOUR);
		if (getFreq() != Freq.HOURLY && getFreq() != Freq.MINUTELY && getFreq() != Freq.SECONDLY && byHour != null && byHour.size() == 1
			&& byHour.get(0) == start.get(Calendar.HOUR))
		{
			// BYHOUR is redundant if it specifies the original hour and the rule doesn't have hourly scope.
			mParts.remove(Part.BYHOUR);
		}

		List<Integer> byMinute = (List<Integer>) mParts.get(Part.BYMINUTE);
		if (getFreq() != Freq.MINUTELY && getFreq() != Freq.SECONDLY && byMinute != null && byMinute.size() == 1
			&& byMinute.get(0) == start.get(Calendar.MINUTE))
		{
			// BYMINUTE is redundant if it specifies the original minute and the rule doesn't have minutely scope.
			mParts.remove(Part.BYMINUTE);
		}

		List<Integer> bySecond = (List<Integer>) mParts.get(Part.BYSECOND);
		if (getFreq() != Freq.SECONDLY && bySecond != null && bySecond.size() == 1 && bySecond.get(0) == start.get(Calendar.SECOND))
		{
			// BYSECOND is redundant if it specifies the original second and the rule doesn't have secondly scope.
			mParts.remove(Part.BYSECOND);
		}
	}


	/**
	 * Optimizes the rule for the recurrence processor for a previously set start date (see {@link #setStart(Calendar)}). In particular this method checks if
	 * there are any redundant BYMONTH, BYMONTHDAY, BYHOUR, BYMINUTE or BYSECOND parts are present.
	 * <p>
	 * <strong>NOTE:</strong> After optimizing the rule it must not be used with a different start date. The optimized rule will be wrong for a different start
	 * date.
	 * </p>
	 * <p>
	 * Also, for compatibility you must not export an optimized rule. Use it for recurrence iterations only.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             is no start date has been set using {@link #setStart(Calendar)}.
	 */
	public void optimize()
	{
		if (mStart == null)
		{
			throw new IllegalStateException("No start date has been set yet. Use setStart(Calendar) ");
		}
		optimize(mStart);
	}


	/**
	 * Return the base frequency of this recurrence rule.
	 * 
	 * @return The {@link Freq} value of this rule.
	 */
	public Freq getFreq()
	{
		return (Freq) mParts.get(Part.FREQ);
	}


	/**
	 * Set the base frequency of this recurrence rule.
	 * <p>
	 * TODO: check if the rule is still valid afterwards (honor the silent parameter)
	 * </p>
	 * 
	 * @param freq
	 *            The new {@link Freq} value of this rule.
	 * @param silent
	 *            <code>true</code> to drop {@link Part}s that are no longer valid with the new frequency silently, <code>false</code> to throw an exception in
	 *            that case.
	 */
	public void setFreq(Freq freq, boolean silent)
	{
		mParts.put(Part.FREQ, freq);

		if (mode == RfcMode.RFC5545_STRICT || mode == RfcMode.RFC5545_LAX)
		{
			// me might end up with an invalid rule when changing the base frequency.
		}
	}


	/**
	 * Get the INTERVAL of this rule.
	 * 
	 * @return The INVERAL of this rule or <code>1</code> if no INTERVAL has been specified.
	 */
	public int getInterval()
	{
		Integer interval = (Integer) mParts.get(Part.INTERVAL);
		// return the default value of 1 if no interval is given
		return interval == null ? 1 : interval;
	}


	/**
	 * Set the INTERVAL of this rule. The interval must be a positive integer. A value of <code>1</code> will just remove the INTERVAL part since that's the
	 * default value anyway.
	 * 
	 * @param interval
	 *            The new interval of this rule.
	 * 
	 * @throws IllegalArgumentException
	 *             if interval is not a positive integer value.
	 */
	public void setInterval(int interval)
	{
		if (interval > 1)
		{
			mParts.put(Part.INTERVAL, interval);
		}
		else if (interval <= 0)
		{
			throw new IllegalArgumentException("Interval must be a positive integer value");
		}
		else
		{
			// interval == 1, since that's the default we just remove it
			mParts.remove(Part.INTERVAL);
		}
	}


	/**
	 * Get the last date an instance my have. If the rule has an UNTIL part the result is a {@link java.util.Calendar} set to the correct time. The time zone is
	 * either UTC or floating.
	 * 
	 * @return A {@link java.util.Calendar} set to the UNTIL value if an UNTIL part is present, <code>null</code> otherwise.
	 */
	public Calendar getUntil()
	{
		return (Calendar) mParts.get(Part.UNTIL);
	}


	/**
	 * Set the latest possible date of an instance. This will remove any COUNT rule if present. If the time zone of <code>until</code> is not UTC and until is
	 * not floating it's automatically converted to UTC.
	 * 
	 * @param until
	 *            The UNTIL part of this rule or <code>null</code> to let the instances recur forever.
	 */
	public void setUntil(Calendar until)
	{
		if (until == null)
		{
			mParts.remove(Part.UNTIL);
			mParts.remove(Part.COUNT);
		}
		else
		{
			if (!until.isFloating() && !Calendar.UTC.equals(until.getTimeZone()))
			{
				until.setTimeZone(Calendar.UTC);
			}
			mParts.put(Part.UNTIL, until);
			mParts.remove(Part.COUNT);
		}
	}


	/**
	 * Get the number if instances in the recurrence set. If this rule has no COUNT limit this will return <code>null</code>
	 * 
	 * @return The number of instances or <code>null</code>.
	 */
	public Integer getCount()
	{
		return (Integer) mParts.get(Part.COUNT);
	}


	/**
	 * Set the number of instances in the recurrence set. This will remove any UNTIL rule if present.
	 * 
	 * @param count
	 *            The number if instances.
	 */
	public void setCount(int count)
	{
		mParts.put(Part.COUNT, count);
		mParts.remove(Part.UNTIL);
	}


	/**
	 * Returns whether this recurrence rule recurs forever.
	 * 
	 * @return <code>true</code> if this rule contains neither an {@link Part#UNTIL} nor a {@link Part#COUNT} part, <code>false</code> otherwise.
	 */
	public boolean isInfinite()
	{
		return !mParts.containsKey(Part.UNTIL) && !mParts.containsKey(Part.COUNT);
	}


	/**
	 * Checks if a specific part is present in this rule.
	 * 
	 * @param part
	 *            The part if interest.
	 * @return <code>true</code> if this rule has this part, <code>false</code> otherwise
	 */
	public boolean hasPart(Part part)
	{
		return mParts.containsKey(part);
	}


	/**
	 * Returns a specific by-rule. <code>part</code> may be one of {@link Part#BYSECOND}, {@link Part#BYMINUTE}, {@link Part#BYHOUR}, {@link Part#BYMONTHDAY},
	 * {@link Part#BYYEARDAY}, {@link Part#BYWEEKNO}, {@link Part#BYMONTH}, or {@link Part#BYSETPOS}.
	 * <p>
	 * To get {@link Part#BYDAY} use {@link #getByDayPart()}.
	 * </p>
	 * 
	 * @param part
	 *            The by-rule to return.
	 * @return A list of integer values.
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getByPart(Part part)
	{
		switch (part)
		{
			case BYSECOND:
			case BYMINUTE:
			case BYHOUR:
			case BYMONTHDAY:
			case BYYEARDAY:
			case BYWEEKNO:
			case BYMONTH:
			case BYSETPOS:
				return (List<Integer>) mParts.get(part);
			default:
				throw new IllegalArgumentException(part.name() + " is not a list type");
		}
	}


	/**
	 * Set a specific by-rule. <code>part</code> may be one of {@link Part#BYSECOND}, {@link Part#BYMINUTE}, {@link Part#BYHOUR}, {@link Part#BYMONTHDAY},
	 * {@link Part#BYYEARDAY}, {@link Part#BYWEEKNO}, {@link Part#BYMONTH}, or {@link Part#BYSETPOS}.
	 * <p>
	 * To set {@link Part#BYDAY} use {@link #setByDayPart()}.
	 * </p>
	 * 
	 * @param part
	 *            The by-rule to set.
	 * @param value
	 *            A list of integers that specify the rule or <code>null</code> (or an empty list) to remove the part.
	 * @throws InvalidRecurrenceRuleException
	 *             if the list would become invalid by adding this part (this respects the current {@link RfcMode}.
	 */
	public void setByPart(Part part, List<Integer> value) throws InvalidRecurrenceRuleException
	{
		if (value == null || value.size() == 0)
		{
			mParts.remove(part);
		}
		else
		{
			switch (part)
			{
				case BYSECOND:
				case BYMINUTE:
				case BYHOUR:
				case BYMONTHDAY:
				case BYYEARDAY:
				case BYWEEKNO:
				case BYMONTH:
				case BYSETPOS:
					validate(part, value);
					mParts.put(part, value);

				default:
					throw new IllegalArgumentException(part.name() + " is not a list type");
			}
		}
	}


	/**
	 * Set a specific by-rule. <code>part</code> may be one of {@link Part#BYSECOND}, {@link Part#BYMINUTE}, {@link Part#BYHOUR}, {@link Part#BYMONTHDAY},
	 * {@link Part#BYYEARDAY}, {@link Part#BYWEEKNO}, {@link Part#BYMONTH}, or {@link Part#BYSETPOS}.
	 * <p>
	 * To set {@link Part#BYDAY} use {@link #setByDayRule()}.
	 * </p>
	 * 
	 * @param part
	 *            The by-rule to set.
	 * @param values
	 *            Integers that specify the rule or <code>null</code> (or an empty list) to remove the part.
	 * @throws InvalidRecurrenceRuleException
	 *             if the list would become invalid by adding this part (this respects the current {@link RfcMode}.
	 */
	public void setByPart(Part part, Integer... values) throws InvalidRecurrenceRuleException
	{
		if (values == null || values.length == 0)
		{
			mParts.remove(part);
		}

		setByPart(part, Arrays.asList(values));
	}


	/**
	 * Set the BYDAY part of this rule.
	 * 
	 * @param value
	 *            A {@link List} of {@link WeekdayNum}s or <code>null</code> or an empty List to remove the part
	 */
	public void setByDayPart(List<WeekdayNum> value)
	{
		if (value == null || value.size() == 0)
		{
			mParts.remove(Part.BYDAY);
		}

		mParts.put(Part.BYDAY, value);
	}


	/**
	 * Return the value of the BYDAY part of the rule if there is any.
	 * 
	 * @return A {@link List} of {@link WeekdayNum}s if the part is present or <code>null</code> if there is no such part.
	 */
	@SuppressWarnings("unchecked")
	public List<WeekdayNum> getByDayPart()
	{
		return (List<WeekdayNum>) mParts.get(Part.BYDAY);
	}


	/**
	 * Get the start of the week as defined in the rule. If no WKST part is set this method will return the default value, which is {@link Weekday#MO}.
	 * 
	 * @return A {@link Weekday}.
	 */
	public Weekday getWeekStart()
	{
		Weekday wkst = (Weekday) mParts.get(Part.WKST);
		return wkst == null ? Weekday.MO /* weeks start with Monday by default */: wkst;
	}


	/**
	 * Set the start of the week. If the start is set to {@link Weekday#MO} the WKST part is effectively removed, since that's the default value. This value is
	 * important for rules having a BYWEEKNO or BYDAY part.
	 * 
	 * @param wkst
	 *            The start of the week to use when calculating the instances.
	 */
	public void setWeekStart(Weekday wkst)
	{
		if (wkst == Weekday.MO)
		{
			// Monday is the default, so just remove the part
			mParts.remove(Part.WKST);
		}
		else
		{
			mParts.put(Part.WKST, wkst);
		}
	}


	/**
	 * Set a start date for the iterator.
	 * 
	 * @param start
	 *            The first instance to iterate.
	 */
	public void setStart(Calendar start)
	{
		Calendar until = getUntil();
		if (until != null)
		{
			if (until.isFloating() != start.isFloating())
			{
				throw new IllegalArgumentException("using floating start times with absolute until values (and vice versa) is not allowed");
			}
			if (until.isAllDay() != start.isAllDay())
			{
				throw new IllegalArgumentException("using allday start times with non-allday until values (and vice versa) is not allowed");
			}
		}
		mStart = start;
	}


	/**
	 * Get a new {@link RuleIterator} that iterates all instances of this rule.
	 * <p>
	 * <strong>Note:</strong> if an UNTIL part is present and it's value is a floating time then start must be floating as well and vice versa. The same applies
	 * if the UNTIL value is an all-day value
	 * </p>
	 * 
	 * @param start
	 *            The first instance.
	 * @return A {@link RuleIterator}.
	 */
	public RecurrenceIterator iterator(Calendar start)
	{
		Calendar until = getUntil();
		if (until != null)
		{
			if (until.isFloating() != start.isFloating())
			{
				throw new IllegalArgumentException("using floating start times with absolute until values (and vice versa) is not allowed");
			}
			if (until.isAllDay() != start.isAllDay())
			{
				throw new IllegalArgumentException("using allday start times with non-allday until values (and vice versa) is not allowed");
			}
		}

		CalendarMetrics calendarTools = new GregorianCalendarMetrics(getWeekStart().ordinal(), 4);
		boolean sanityFilterAdded = false;
		RuleIterator iterator = null;

		// since FREQ is the first part anyway we don't have to create it separately
		for (Part p : mParts.keySet())
		{
			// add a filter for each rule part
			if (p != Part.INTERVAL && p != Part.WKST)
			{
				if (p == Part.UNTIL || p == Part.COUNT || p == Part.BYSETPOS)
				{
					// insert SanityFilter before adding limiting filter or BYSETPOS, otherwise we may count filtered elements
					iterator = new SanityFilter(this, iterator, calendarTools, start);
					sanityFilterAdded = true;
				}
				iterator = p.getRuleIterator(this, iterator, calendarTools, start);
			}
		}
		// add a SanityFilter if not already done.
		return new RecurrenceIterator(sanityFilterAdded ? iterator : new SanityFilter(this, iterator, calendarTools, start), start);
	}


	public RecurrenceIterator iterator()
	{
		if (mStart == null)
		{
			throw new IllegalStateException("No start date has been set yet. Use setStart(Calendar) ");
		}
		return iterator(mStart);
	}


	@Override
	public String toString()
	{
		// the average rule is not longer than 100 characters, we add some buffer to avoid a copy operation
		StringWriter result = new StringWriter(160);
		boolean first = true;
		// just write all parts separated by semicolon to the result string
		// the order of the parts guarantees that FREQ is always the first part (as required by RFC 2445)
		for (Part part : Part.values())
		{
			Object value = mParts.get(part);
			if (value != null)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					result.append(";");
				}
				result.append(part.name());
				result.append("=");
				part.converter.serialize(result, value);
			}
		}
		return result.toString();
	}

	/**
	 * Abstract class to parse and serialize a specific part of a RRULE.
	 * 
	 * @author Marten Gajda <marten@dmfs.org>
	 * 
	 * @param <T>
	 *            The type returned by the parser and expected by the serializer.
	 */
	private static abstract class ValueConverter<T>
	{
		/**
		 * Parses a string for a specific value <T>.
		 * 
		 * @param value
		 *            The string representation of the value.
		 * @return An instance of <T> with the correct value.
		 * @throws InvalidRecurrenceRuleException
		 *             if the value is invalid.
		 */
		public abstract T parse(String value) throws InvalidRecurrenceRuleException;


		/**
		 * Write the string representation of a value of type <T> to a {@link StringWriter}.
		 * <p>
		 * The default implementation just calls {@link #toString()} on the value.
		 * </p>
		 * 
		 * @param out
		 *            The {@link StringWriter} to write to.
		 * @param value
		 *            The value to serialize.
		 */
		public void serialize(StringWriter out, Object value)
		{
			out.write(value.toString());
		}
	}

	/**
	 * Generic converter for comma separated list values.
	 * 
	 * @author Marten Gajda <marten@dmfs.org>
	 * 
	 * @param <T>
	 *            The type of the list elements.
	 */
	private static abstract class ListValueConverter<T> extends ValueConverter<Collection<T>>
	{
		/**
		 * Parses the value of a single list element.
		 * 
		 * @param value
		 *            The list element to parse.
		 * @return The value of the list element.
		 * @throws InvalidRecurrenceRuleException
		 *             if the list element is invalid.
		 */
		abstract T parseValue(String value) throws InvalidRecurrenceRuleException;


		/**
		 * Serialize a single list element value. The default implementation just calls {@link #toString()} on the valie instancel.
		 * 
		 * @param out
		 *            The writer to write to.
		 * @param value
		 *            The value to serialize.
		 */
		void serializeValue(StringWriter out, Object value)
		{
			out.append(value.toString());
		}


		@Override
		public Collection<T> parse(String value) throws InvalidRecurrenceRuleException
		{
			List<T> result = new ArrayList<T>();
			String[] values = value.split(",");
			for (String val : values)
			{
				result.add(parseValue(val));
			}
			if (result.size() > 0)
			{
				return result;
			}
			else
			{
				throw new InvalidRecurrenceRuleException("empty lists are not allowed");
			}
		}


		@Override
		public void serialize(StringWriter out, Object value)
		{
			boolean first = true;
			for (Object v : (Collection<?>) value)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					out.append(",");
				}

				serializeValue(out, v);
			}
		}
	}

	/**
	 * A converter for integer list values.
	 * 
	 * @author Marten Gajda <marten@dmfs.org>
	 */
	private static class IntListConverter extends ListValueConverter<Integer>
	{
		private final int mMinValue;
		private final int mMaxValue;
		private boolean mNoZero = false;


		/**
		 * Creates a new converter for integer lists.
		 * 
		 * @param min
		 *            The lowest allowed value.
		 * @param max
		 *            The highest allowed value.
		 */
		public IntListConverter(int min, int max)
		{
			mMaxValue = max;
			mMinValue = min;
		}


		/**
		 * Disallow the value <code>0</code>
		 * 
		 * @return This instance.
		 */
		public IntListConverter noZero()
		{
			mNoZero = true;
			return this;
		}


		@Override
		Integer parseValue(String value) throws InvalidRecurrenceRuleException
		{
			try
			{
				int val = Integer.parseInt(value);
				if (val < mMinValue || val > mMaxValue || mNoZero && val == 0)
				{
					throw new InvalidRecurrenceRuleException("int value out of range: " + val);
				}
				return val;
			}
			catch (NumberFormatException e)
			{
				throw new InvalidRecurrenceRuleException("illegal int value: " + value);
			}
		}
	}

	/**
	 * Converts a list of {@link WeekdayNum} values.
	 * 
	 * @author Marten Gajda <marten@dmfs.org>
	 */
	private static class WeekdayListConverter extends ListValueConverter<WeekdayNum>
	{
		@Override
		WeekdayNum parseValue(String value) throws InvalidRecurrenceRuleException
		{
			return WeekdayNum.valueOf(value, true);
		}
	}

	/**
	 * Converts a {@link Weekday} value.
	 * 
	 * @author Marten Gajda <marten@dmfs.org>
	 */
	private static class WeekdayConverter extends ValueConverter<Weekday>
	{
		@Override
		public Weekday parse(String value)
		{
			return Weekday.valueOf(value);
		}
	}

	/**
	 * Converts any arbitrary integer value.
	 * 
	 * @author Marten Gajda <marten@dmfs.org>
	 */
	private static class IntConverter extends ValueConverter<Integer>
	{
		@Override
		public Integer parse(String value)
		{
			return Integer.parseInt(value);
		}
	}

	/**
	 * Converts the value of the FREQ part from/to a {@link Freq} instance.
	 * 
	 * @author Marten Gajda <marten@dmfs.org>
	 */
	private static class FreqConverter extends ValueConverter<Freq>
	{
		@Override
		public Freq parse(String value) throws InvalidRecurrenceRuleException
		{
			try
			{
				return Freq.valueOf(value);
			}
			catch (IllegalArgumentException e)
			{
				throw new InvalidRecurrenceRuleException("Unknown FREQ value " + value);
			}
		}
	}

	/**
	 * Converts the date-time value of an UNTIL part from/to a {@link Calendar} instance.
	 * 
	 * @author Marten Gajda <marten@dmfs.org>
	 */
	private static class DateTimeConverter extends ValueConverter<Calendar>
	{
		@Override
		public Calendar parse(String value) throws InvalidRecurrenceRuleException
		{
			try
			{
				return Calendar.parse(value);
			}
			catch (Exception e)
			{
				throw new InvalidRecurrenceRuleException("Invalid UNTIL date: " + value, e);
			}
		}
	}
}
