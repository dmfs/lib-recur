package org.dmfs.rfc5545.recurrenceset;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.Confidence;
import org.saynotobugs.confidence.junit5.engine.assertion.AssertionThat;

import static org.dmfs.rfc5545.confidence.Recur.emptyRecurrenceSet;
import static org.dmfs.rfc5545.confidence.Recur.finite;
import static org.saynotobugs.confidence.quality.Core.*;

@Confidence
class PrecedingTest
{

    Assertion preceding_empty_RecurrenceSet_remains_empty =
        new AssertionThat<>(
            new Preceding(DateTime.parse("20240406"),
                new OfList("")),
            is(emptyRecurrenceSet()));

    Assertion preceding_first_element_is_empty =
        new AssertionThat<>(
            new Preceding(DateTime.parse("20240406"),
                new OfList("20240505,20240606,20240707")),
            is(emptyRecurrenceSet()));

    Assertion preceding_second_element_is_first_element =
        new AssertionThat<>(
            new Preceding(DateTime.parse("20240406"),
                new OfList("20240404,20240505,20240606,20240707")),
            is(allOf(
                finite(),
                iterates(DateTime.parse("20240404"))
            )));

    Assertion preceding_third_exactly_matching_element_is_first_two_elements =
        new AssertionThat<>(
            new Preceding(DateTime.parse("20240606"),
                new OfList("20240404,20240505,20240606,20240707")),
            is(allOf(
                finite(),
                iterates(
                    DateTime.parse("20240404"),
                    DateTime.parse("20240505"))
            )));

    Assertion preceding_after_last_element_is_all_elements =
        new AssertionThat<>(
            new Preceding(DateTime.parse("20240909"),
                new OfList("20240404,20240505,20240606,20240707")),
            is(allOf(
                finite(),
                iterates(
                    DateTime.parse("20240404"),
                    DateTime.parse("20240505"),
                    DateTime.parse("20240606"),
                    DateTime.parse("20240707"))
            )));

    Assertion fast_forwarded_preceding_some_values =
        new AssertionThat<>(
            new FastForwarded(DateTime.parse("20240505"),
                new Preceding(DateTime.parse("20240707"),
                    new OfList("20240404,20240505,20240606,20240707"))),
            is(allOf(
                finite(),
                iterates(
                    DateTime.parse("20240505"),
                    DateTime.parse("20240606"))
            )));

    Assertion fast_forwarded_preceding_no_values =
        new AssertionThat<>(
            new FastForwarded(DateTime.parse("20240606"),
                new Preceding(DateTime.parse("20240606"),
                    new OfList("20240404,20240505,20240606,20240707"))),
            is(emptyRecurrenceSet()));

    Assertion preceding_an_infinite_set_is_finite =
        new AssertionThat<>(
            new Preceding(DateTime.parse("20240410"),
                new OfRule(recurrenceRule("FREQ=DAILY"), DateTime.parse("20240406"))),
            is(allOf(
                finite(),
                iterates(
                    DateTime.parse("20240406"),
                    DateTime.parse("20240407"),
                    DateTime.parse("20240408"),
                    DateTime.parse("20240409")))
            ));

    // hmm, the confidence engine approach doesn't play well with throwing constructors
    private static RecurrenceRule recurrenceRule(String rule)
    {
        try
        {
            return new RecurrenceRule(rule);
        }
        catch (InvalidRecurrenceRuleException e)
        {
            throw new RuntimeException(e);
        }
    }
}