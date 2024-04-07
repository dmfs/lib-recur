[![Build](https://github.com/dmfs/lib-recur/actions/workflows/main.yml/badge.svg?label=main)](https://github.com/dmfs/lib-recur/actions/workflows/main.yml)  
[![codecov](https://codecov.io/gh/dmfs/lib-recur/branch/main/graph/badge.svg)](https://codecov.io/gh/dmfs/lib-recur)  
[![Confidence](https://img.shields.io/badge/Tested_with-Confidence-800000?labelColor=white)](https://saynotobugs.org/confidence)

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

## Recurrence Set API

In addition to interpreting recurrence rules, this library provides a set of classes to determine the result of any combination of rrules, rdates and exdates (and exrules, for that matter) as specified in RFC 5545.

Version 0.16.0 introduces a new API that is slightly different from the previous one. The new API fixes a few design issues that
made the code more complex than necessary.

There is a new interface called `RecurrenceSet` that is implemented by a couple of adapters, decorators and composites. A `RecurrenceSet`
represents the set of occurrences of a recurrence rule or list or any combination of them (including exclusions).

`RecurrenceSet` extends the `Iterable` interface, so it can be used with any `Iterable` decorator from the jems2 library and in `for` loops.

### Iterating RRules

The most common use case is probably just iterating the occurrences of recurrence rules. Although you still can do this using the `RecurrenceRuleIterator`
returned by `RecurrenceRule.iterator(DateTime)`, you may be better off using the `OfRule` adapter that implements the
`Iterable` interface.

#### Examples

```java
RecurrenceSet occurrences = new OfRule(rrule, startDate);
```

You can combine this with the `First` or `While` decorators from the jems2 library to guard against infinite rules and use it to
loop over the occurrences.

```java
for (DateTime occurrence:new First<>(1000, // iterate at most/the first 1000 occurrences
    new OfRule(rrule, startDate))) {
    // do something with occurrence
}
```

```java
for (DateTime occurrence:new While<>(endDate::isAfter, // stop at "endDate"
    new OfRule(rrule, startDate))) {
    // do something with occurrence
}
```

#### Handling first instances that don't match the RRULE

Note that `OfRule` does not iterate the start date if it doesn't match the RRULE. If you want to
iterate any non-synchronized first date, use `OfRuleAndFirst` instead!

```java
new OfRule(
    new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=24;BYMONTH=5"),
    DateTime.parse("19820523"))
```
results in
```
19820524,19830524,19840524,19850524…
```
Note that `19820523` is not among the results because it doesn't match the rule as it doesn't fall on the 24th.

However,

```java
new OfRuleAndFirst(
    new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=24;BYMONTH=5"),
    DateTime.parse("19820523"))
```
results in
```
19820523,19820524,19830524,19840524,19850524…
```

### Iterating RDates and ExDates

Similarly, iterating comma separated Date or DateTime lists (i.e. `RDATE` and `EXDATE` ) can be done with the `OfList` adapter.

#### Example

```java
for (DateTime occurrence:new OfList(timeZone, rdates)) {
    // do something with occurrence
}
```

### Combining multiple Rules and/or Lists

You can merge the occurrences of multiple sets with the `Merged` class. A `Merged` `RecurrenceSet` iterates the occurrences
of all given `RecurrenceSet`s in chronological order.

#### Example

```java
RecurrenceSet merged = new Merged(
    new OfRule(rule, start),
    new OfList(timezone, rdates)
);
```

The result iterates the occurrences of both, the rule and the rdates in chronological order.

### Excluding Exceptions

Exceptions can be excluded by composing occurrences and exceptions using `Difference` like in

```java
RecurrenceSet withoutExceptions = new Difference(
    new OfRule(rule, start),
    new OfList(timezone, exdates));
```

This `RecurrenceSet` contains all the occurrences iterated by the given rule, except those in the exdates list. Note that these must be exact matches,
i.e. the exdate `20240216` does *not* result in the exclusion of `20240216T120000` nor of `20240216T000000`.

### Fast forwarding

Sometimes you might want to skip all the instances prior to a given date. This can be achieved by applying the `FastForwarded` decorator like in

```java
RecurrenceSet merged = new FastForwarded(
    fastForwardToDate,
    new Merged(
        new OfRule(rule, start),
        new OfList(timezone, rdates)));
```

Note, that `new FastForwarded(fastForwardTo, new OfRule(rrule, start))` and `new OfRule(rrule, fastForwardTo)` are not necessarily the same
set of occurrences.

### Dealing with infinite rules

Be aware that RRULEs are infinite if they specify neither `COUNT` nor `UNTIL`. This might easily result in an infinite loop if not taken care of.

As stated above, a simple way to deal with this is by applying a decorator like `First` or `While` from the jems2 library:

```java
RecurrenceRule rule = new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=23;BYMONTH=5");
DateTime start = new DateTime(1982, 4 /* 0-based month numbers! */,23);
for (DateTime occurrence:new First<>(1000, new OfRule(rule, start))) {
    // do something with occurrence    
}
```

This will always stop iterating after at most 1000 instances.

### Limiting RecurrenceSets

You can limit a `RecurrenceSet` to the instances that precede a certain `DateTime`
using the `Preceding` decorator. This can also serve as a way to handle infinite rules:

```java
RecurrenceRule rule = new RecurrenceRule("FREQ=MONTHLY;BYMONTHDAY=23");
DateTime start = new DateTime(1982, 4 /* 0-based month numbers! */,23);
for (DateTime occurrence:new Preceding<>(
    new DateTime(1983, 0, 1), // all instances before 1983
    new OfRule(rule, start))) {
    // do something with occurrence    
}
```

The `Within` decorator combines `Preceding` and `FastForwarded` and only iterates
occurrences that fall in the given (right-open) interval.

```java
// a RecurrenceSet that only contains occurrences in 2024
// (assuming the original iterates all-day values)
RecurrenceSet occurrencesOf2024 = new Within(
    DateTime.parse("20240101"),
    DateTime.parse("20250101"),
    recurrenceSet
);
```

Note, in both cases you must take care that the dates you supply have the same format (floating vs all-day vs absolute)
as the occurrences of your recurrence set.

### Determining the last instance of a RecurrenceSet

Finite, non-empty `RecurrenceSet`s have a last instance that can be determined with the `LastInstance` adapter.
`LastInstance` is an `Optional` of a `DateTime` value that's present when the given `RecurrenceSet` is finite and
non-empty.

#### Example

```java
new LastInstance(new OfRule(new RecurrenceRule("FREQ=DAILY;COUNT=10"), startDate));
```

### RFC 5545 Instance Iteration Example

In a recurring `VEVENT` you might find `RRULE`s, `RDATE`s, `EXDATE`s and (in RFC 2445) `EXRULE`s. Assuming you have all
these in variables with these respective names the `RecurrenceSet` might be constructed like in

```java
RecurrenceSet occurrences = new Difference(
    new Merged(
        new OfRule(new RecurrenceRule(rrule), dtstart),
        new OfList(timezone, rdates)
    ),
    new Merged(
        new OfRule(new RecurrenceRule(exrule), dtstart),
        new OfList(timezone, exdates)
    )
);
```

### Strict and lax parsing

By default, the parser is very tolerant and accepts all rules that comply with RFC 5545. You can use other modes to ensure a certain compliance level:

		RecurrenceRule rule1 = new RecurrenceRule("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU", RfcMode.RFC2445_STRICT);
		// -> will throw an InvalidRecurrenceRuleExceptionException because in RFC 2445 BYWEEKNO is only valid in
		// combination with YEARLY rules

		RecurrenceRule rule2 = new RecurrenceRule("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU", RfcMode.RFC2445_LAX);
		// -> will iterate Sunday in the first four weeks of the year

		RecurrenceRule rule3 = new RecurrenceRule("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU", RfcMode.RFC5545_STRICT);
		// -> will throw an InvalidRecurrenceRuleExceptionException because in RFC 5545 BYWEEKNO is only valid in
		// combination with YEARLY rules

		RecurrenceRule rule4 = new RecurrenceRule("FREQ=WEEKLY;BYWEEKNO=1,2,3,4;BYDAY=SU", RfcMode.RFC5545_LAX);
		// -> will iterate Sunday in the first four weeks of the year

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
		rule.setByRule(Part.BYMONTH, 1, 3, 5, 7);

		rule.setByRule(Part.BYMONTHDAY, 4, 8, 12);

		/*
		 * Alternatively set the values from a list or an array:
		 */ 
		Integer[] dayArray = new Integer[]{4, 8, 12};
		rule.setByRule(Part.BYMONTHDAY, dayArray);
		
		List<Integer> dayList = Arrays.asList(dayArray);
		rule.setByRule(Part.BYMONTHDAY, dayList);

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

Copyright (c) Marten Gajda 2024, licensed under Apache2.
