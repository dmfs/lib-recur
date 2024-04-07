package org.dmfs.rfc5545.recurrenceset;

import org.dmfs.rfc5545.DateTime;
import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.Confidence;
import org.saynotobugs.confidence.junit5.engine.assertion.AssertionThat;

import static org.dmfs.rfc5545.confidence.Recur.emptyRecurrenceSet;
import static org.dmfs.rfc5545.confidence.Recur.finite;
import static org.saynotobugs.confidence.quality.Core.*;

@Confidence
class WithinTest
{

    Assertion within_empty_RecurrenceSet_remains_empty =
        new AssertionThat<>(
            new Within(
                DateTime.parse("20240406"),
                DateTime.parse("20240408"),
                new OfList("")),
            is(emptyRecurrenceSet()));

    Assertion within_before_delegate_is_empty =
        new AssertionThat<>(
            new Within(
                DateTime.parse("20240306"),
                DateTime.parse("20240406"),
                new OfList("20240505,20240606,20240707")),
            is(emptyRecurrenceSet()));

    Assertion within_after_delegate_is_empty =
        new AssertionThat<>(
            new Within(
                DateTime.parse("20241006"),
                DateTime.parse("20241106"),
                new OfList("20240505,20240606,20240707")),
            is(emptyRecurrenceSet()));

    Assertion within_entire_delegate_is_delegate =
        new AssertionThat<>(
            new Within(
                DateTime.parse("20240106"),
                DateTime.parse("20241106"),
                new OfList("20240505,20240606,20240707")),
            is(allOf(
                finite(),
                iterates(
                    DateTime.parse("20240505"),
                    DateTime.parse("20240606"),
                    DateTime.parse("20240707")
                )
            )));

    Assertion within_partial_delegate_is_partial_delegate =
        new AssertionThat<>(
            new Within(
                DateTime.parse("20240506"),
                DateTime.parse("20240706"),
                new OfList("20240505,20240606,20240707")),
            is(allOf(
                finite(),
                iterates(DateTime.parse("20240606"))
            )));

    Assertion within_same_start_and_end_is_empty =
        new AssertionThat<>(
            new Within(
                DateTime.parse("20240606"),
                DateTime.parse("20240606"),
                new OfList("20240505,20240606,20240707")),
            is(emptyRecurrenceSet()));

}