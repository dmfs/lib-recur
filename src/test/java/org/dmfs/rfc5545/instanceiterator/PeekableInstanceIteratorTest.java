package org.dmfs.rfc5545.instanceiterator;

import org.dmfs.jems2.ThrowingFunction;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recurrenceset.OfList;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.object.Throwing;

import java.util.NoSuchElementException;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;

class PeekableInstanceIteratorTest
{
    @Test
    void testEmpty()
    {
        assertThat(() -> new PeekableInstanceIterator(new EmptyIterator()),
            allOf(
                mutatedBy(
                    new Text("nothing"),
                    testee -> {},
                    soIt(allOf(not(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext"))),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::next, throwing(NoSuchElementException.class)),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::peek, throwing(NoSuchElementException.class)))))
            ));
    }


    @Test
    void test()
    {
        assertThat(() -> new PeekableInstanceIterator(new OfList("20240406,20240408,20240410,20240412").iterator()),
            allOf(
                mutatedBy(
                    new Text("nothing"),
                    testee -> {},
                    soIt(allOf(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext")),
                        has(PeekableInstanceIterator::next, equalTo(DateTime.parse("20240406"))),
                        has(PeekableInstanceIterator::peek, equalTo(DateTime.parse("20240408")))))),
                mutatedBy(
                    new Text("pulling one element"),
                    testee -> {
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                    },
                    soIt(allOf(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext")),
                        has(PeekableInstanceIterator::next, equalTo(DateTime.parse("20240408"))),
                        has(PeekableInstanceIterator::peek, equalTo(DateTime.parse("20240410")))))),
                mutatedBy(
                    new Text("pulling two elements"),
                    testee -> {
                        testee.next();
                        testee.next();
                    },
                    soIt(allOf(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext")),
                        has(PeekableInstanceIterator::next, equalTo(DateTime.parse("20240410"))),
                        has(PeekableInstanceIterator::peek, equalTo(DateTime.parse("20240412")))))),
                mutatedBy(
                    new Text("pulling three elements"),
                    testee -> {
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                    },
                    soIt(allOf(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext")),
                        has((ThrowingFunction<PeekableInstanceIterator, DateTime>) PeekableInstanceIterator::next, equalTo(DateTime.parse("20240412"))),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::peek, throwing(NoSuchElementException.class))))),
                mutatedBy(
                    new Text("pulling four elements"),
                    testee -> {
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                    },
                    soIt(allOf(not(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext"))),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::next, throwing(NoSuchElementException.class)),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::peek, throwing(NoSuchElementException.class)))))
            ));
    }


    @Test
    void testFastForward()
    {
        assertThat(() -> new PeekableInstanceIterator(new OfList("20240406,20240408,20240410,20240412").iterator()),
            allOf(
                mutatedBy(
                    new Text("fast forwarding to element before first"),
                    testee -> testee.fastForward(DateTime.parse("20240210")),
                    soIt(allOf(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext")),
                        has(PeekableInstanceIterator::next, equalTo(DateTime.parse("20240406"))),
                        has(PeekableInstanceIterator::peek, equalTo(DateTime.parse("20240408")))))),
                mutatedBy(
                    new Text("fast forwarding to first element"),
                    testee -> testee.fastForward(DateTime.parse("20240406")),
                    soIt(allOf(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext")),
                        has(PeekableInstanceIterator::next, equalTo(DateTime.parse("20240406"))),
                        has(PeekableInstanceIterator::peek, equalTo(DateTime.parse("20240408")))))),
                mutatedBy(
                    new Text("fast forwarding to third element"),
                    testee -> testee.fastForward(DateTime.parse("20240410")),
                    soIt(allOf(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext")),
                        has(PeekableInstanceIterator::next, equalTo(DateTime.parse("20240410"))),
                        has(PeekableInstanceIterator::peek, equalTo(DateTime.parse("20240412")))))),
                mutatedBy(
                    new Text("fast forwarding to third element and pulling one element"),
                    testee -> {
                        testee.fastForward(DateTime.parse("20240410"));
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                    },
                    soIt(allOf(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext")),
                        has((ThrowingFunction<PeekableInstanceIterator, DateTime>) PeekableInstanceIterator::next, equalTo(DateTime.parse("20240412"))),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::peek, throwing(NoSuchElementException.class))))),
                mutatedBy(
                    new Text("fast forwarding to third element and pulling two element"),
                    testee -> {
                        testee.fastForward(DateTime.parse("20240410"));
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                    },
                    soIt(allOf(not(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext"))),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::next, throwing(NoSuchElementException.class)),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::peek, throwing(NoSuchElementException.class))))),
                mutatedBy(
                    new Text("fast forwarding to last element"),
                    testee -> {
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.fastForward(DateTime.parse("20240412"));
                    },
                    soIt(allOf(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext")),
                        has((ThrowingFunction<PeekableInstanceIterator, DateTime>) PeekableInstanceIterator::next, equalTo(DateTime.parse("20240412"))),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::peek, throwing(NoSuchElementException.class))))),
                mutatedBy(
                    new Text("fast forwarding beyond last element"),
                    testee -> {
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.peek();
                        testee.hasNext();
                        testee.next();
                        testee.fastForward(DateTime.parse("20240413"));
                    },
                    soIt(allOf(not(satisfies(PeekableInstanceIterator::hasNext, new Text("hasNext"))),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::next, throwing(NoSuchElementException.class)),
                        has((ThrowingFunction<PeekableInstanceIterator, Throwing.Breakable>) iterator -> iterator::peek, throwing(NoSuchElementException.class)))))

                ));
    }
}