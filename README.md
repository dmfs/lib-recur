# lib-recur

__A recurrence processor for Java__

This library parses recurrence strings as defined in [RFC 5545](http://tools.ietf.org/html/rfc5545#section-3.3.10) and [RFC 2445](http://tools.ietf.org/html/rfc2445#section-4.3.10) and iterates the instances.
In addition it can be used to build valid recurrence strings in a convenient manner.

Check out the "recurrence expansion as a service" demo at http://recurrence-expansion-service.appspot.com

Please note that the interface of the classes in this library is not finalized yet and subject to change. We're going to refactor this to make it more object-oriented and make more classes immutable (in particular the RecurrenceRule class itself).

## Requirements

[rfc5545-datetime](https://github.com/dmfs/rfc5545-datetime)

## RSCALE support

The iterator has support for [RSCALE](https://tools.ietf.org/html/rfc7529). At this time is supports four calendar scales:

* GREGORIAN
* JULIAN
* ISLAMIC-CIVIL (aka ISLAMICC)
* ISLAMIC-TBLA

RSCALE is supported in all RFC2445 and RFC5545 modes.

## Example code

### Iterating instances

The basic use case is to iterate over all instances of a given rule starting on a specific day. Note that some rules may recur forever. In that case you must limit the number of instances in order to avoid an infinite loop.

The following code iterates over the instances of a recurrence rule:

		RecurrenceRule rule = new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=23;BYMONTH=5");
		
		DateTime start = new DateTime(1982, 4 /* 0-based month numbers! */,23);

		RecurrenceRuleIterator it = rule.iterator(start);

		int maxInstances = 100; // limit instances for rules that recur forever

		while (it.hasNext() && (!rule.isInfinite() || maxInstances-- > 0))
		{
			DateTime nextInstance = it.nextDateTime();
			// do something with nextInstance
		}

This library also supports processing of EXRULEs, RDATEs and EXDATEs, i.e. complete recurrence sets. To iterate a recurrence set use the following code:

		// create a recurence set
		RecurrenceSet rset = new RecurrenceSet();

		// add instances from a recurrence rule
		// you can add any number of recurrence rules or RDATEs (RecurrenceLists).
		rset.addInstances(new RecurrenceRuleAdapter(rule));

		// optionally add exceptions
		// rset.addExceptions(new RecurrenceList(timestamps));

		// get an iterator
		RecurrenceSetIterator iterator = rset.iterator(start.getTimeZone(), start.getTimestamp());

		while (iterator.hasNext() && --limit >= 0)
		{
			long nextInstance = iterator.next();
			// do something with nextInstance
		}

Note: at this time RecurrenceSetIterator supports iterating timestamps only. All-day dates will be iterated as timestamps at 00:00 UTC.

By default the parser is very tolerant and accepts all rules that comply with RFC 5545. You can use other modes to ensure a certain compliance level:

		RecurrenceRule rule1 = new RecurrenceRule("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU", RfcMode.RFC2445_STRICT);
		// -> will iterate Sunday in the first four weeks of the year


		RecurrenceRule rule2 = new RecurrenceRule("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU", RfcMode.RFC2445_LAX);
		// -> will iterate Sunday in the first four weeks of the year (just like RfcMode.RFC2445_STRICT)


		RecurrenceRule rule3 = new RecurrenceRule("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU", RfcMode.RFC5545_STRICT);
		// -> will throw an InvalidRecurrenceRuleExceptionException because in RFC 5545 BYWEEKNO is only valid in
		// combination to YEARLY rules


		RecurrenceRule rule4 = new RecurrenceRule("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU", RfcMode.RFC5545_LAX);
		// -> will iterate every Sunday of the year
		// since BYWEEKNO is not valid in WEEKLY rules this part is just dropped and the rule left is "FREQ=WEEKLY;BYDAY=SU"


		RecurrenceRule rule5 = new RecurrenceRule("BYWEEKNO=1,2,3,4;BYDAY=SU;FREQ=WEEKLY", RfcMode.RFC2445_STRICT);
		// -> will throw an InvalidRecurrenceRuleExceptionException because in RFC 2445 the rule must start with "FREQ="


		RecurrenceRule rule6 = new RecurrenceRule("FREQ=MONTHLY;BYMONTH=4;", RfcMode.RFC2445_STRICT);
		// -> will throw an InvalidRecurrenceRuleExceptionException because the trailing ";" is invalid

The default mode for parsing rules is ```RfcMode.RFC5545_LAX```. To support as many rules as possible use ```RfcMode.RFC2445_LAX```;


### Building rules

To build a rule you have to specify a base frequency and optionally an RfcMode. Then you can start adding BY* rules.

		RecurrenceRule rule = new RecurrenceRule(Freq.MONTHLY); // will create a new rule using RfcMode.RFC5545_STRICT mode

		rule.setCount(20);

		// note that unlike with java.util.Calendar the months in this list are 1-based not 0-based
		rule.setByRule(Parts.BYMONTH, 1, 3, 5, 7);

		rule.setByRule(Parts.BYMONTHDAY, 4, 8, 12);

		/*
		 * Alternatively set the values from a list or an array:
		 */ 
		Integer[] dayArray = new Integer[]{4, 8, 12};
		rule.setByRule(Parts.BYMONTHDAY, dayArray);
		
		List<Integer> dayList = Arrays.asList(dayArray);
		rule.setByRule(Parts.BYMONTHDAY, dayList);

		String ruleStr = rule.toString(); 
		// ruleStr is "FREQ=MONTHLY;BYMONTH=1,3,5,7;BYMONTHDAY=4,8,12;COUNT=20"

## Related work

There are at least two other implentations of recurrence iterators for Java:

* [google-rfc-2445](https://code.google.com/p/google-rfc-2445/)
* Android's [calendarcommon project](https://android.googlesource.com/platform/frameworks/opt/calendar/+/master/src/com/android/calendarcommon2)

## TODO

* Add more tests
* Add tests for edge cases
* Add an RRuleBuilder to build RRULEs and make RecurrenceRule immutable
* Add support for more calendar scales
* Fix handling of calendar scales with leap months
* Fix RecurrenceRule.toString() when RSCALE is set
* Add validator and a validator log


## License

Copyright (c) Marten Gajda 2015, licensed under Apache2.
