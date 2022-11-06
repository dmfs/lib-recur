[![Build Status](https://travis-ci.org/dmfs/lib-recur.svg?branch=master)](https://travis-ci.org/dmfs/lib-recur)
[![codecov](https://codecov.io/gh/dmfs/lib-recur/branch/master/graph/badge.svg)](https://codecov.io/gh/dmfs/lib-recur)

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

		
```java
DateTime start = RecurrenceRuleIterator it = rule.iterator(start);

int maxInstances = 100; // limit instances for rules that recur forever

while (it.hasNext() && (!rule.isInfinite() || maxInstances-- > 0))
{
    DateTime nextInstance = it.nextDateTime();
    // do something with nextInstance
}
```

### Iterating Recurrence Sets

This library also supports processing of EXRULEs, RDATEs and EXDATEs, i.e. complete recurrence sets.

In order to iterate a recurrence set you first compose the set from its components:

```java
RecurrenceRule rule = new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=23;BYMONTH=5");

DateTime firstInstance = new DateTime(1982, 4 /* 0-based month numbers! */,23);

for (DateTime instance:new RecurrenceSet(firstInstance, new RuleInstances(rule))) {
    // do something with instance    
}
```

`RecurrenceSet` takes two `InstanceIterable` arguments the first one is expected to iterate the actual
occurrences, the second, optional one iterates exceptions:

```java
RecurrenceRule rule = new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=23;BYMONTH=5");

DateTime firstInstance = new DateTime(1982, 4 /* 0-based month numbers! */,23);

for (DateTime instance:
    new RecurrenceSet(firstInstance,
        new RuleInstances(rule),
        new InstanceList(exceptions))) {
    // do something with instance    
}
```

You can compose multiple rules or `InstanceList`s using `Composite` like this

```java
RecurrenceRule rule1 = new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=23;BYMONTH=5");
RecurrenceRule rule2 = new RecurrenceRule("FREQ=MONTHLY;BYMONTHDAY=20");

DateTime firstInstance = new DateTime(1982, 4 /* 0-based month numbers! */,23);

for (DateTime instance:
    new RecurrenceSet(firstInstance,
        new Composite(new RuleInstances(rule1), new RuleInstances(rule2)),
        new InstanceList(exceptions))) {
    // do something with instance    
}
```

or simply by providing a `List` of `InstanceIterable`s:

```java
RecurrenceRule rule1 = new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=23;BYMONTH=5");
RecurrenceRule rule2 = new RecurrenceRule("FREQ=MONTHLY;BYMONTHDAY=20");

DateTime firstInstance = new DateTime(1982, 4 /* 0-based month numbers! */,23);

for (DateTime instance:
    new RecurrenceSet(firstInstance,
        List.of(new RuleInstances(rule1), new RuleInstances(rule2)),
        new InstanceList(exceptions))) {
    // do something with instance    
}
```

#### Handling first instances that don't match the RRULE

Note that `RuleInstances` does not iterate the start date if it doesn't match the RRULE. If you want to
iterate any non-synchronized first date, use `FirstAndRuleInstances` instead!

```java
new RecurrenceSet(DateTime.parse("19820523"),
    new RuleInstances(
        new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=24;BYMONTH=5")))) {
    // do something with instance    
}
```
results in
```
19820524,19830524,19840524,19850524…
```
Note that `19820523` is not among the results.

However,

```java
new RecurrenceSet(DateTime.parse("19820523"),
    new RuleInstances(
        new FirstAndRuleInstances("FREQ=YEARLY;BYMONTHDAY=24;BYMONTH=5")))) {
    // do something with instance    
}
```
results in
```
19820523,19820524,19830524,19840524,19850524…
```


#### Dealing with infinite rules

Be aware that RRULEs are infinite if they specify neither `COUNT` nor `UNTIL`. This might easily result in an infinite loop when you just iterate over the recurrence set like above.

One way to address this is by adding a decorator like `First` from the `jems2`  library:

```java
RecurrenceRule rule = new RecurrenceRule("FREQ=YEARLY;BYMONTHDAY=23;BYMONTH=5");
DateTime firstInstance = new DateTime(1982, 4 /* 0-based month numbers! */,23);
for (DateTime instance: new First(1000, new RecurrenceSet(firstInstance, new RuleInstances(rule)))) {
    // do something with instance    
}
```

This will always stop iterating after at most 1000 instances.

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

Copyright (c) Marten Gajda 2022, licensed under Apache2.
