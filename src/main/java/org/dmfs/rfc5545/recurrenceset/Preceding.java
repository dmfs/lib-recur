package org.dmfs.rfc5545.recurrenceset;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.InstanceIterator;
import org.dmfs.rfc5545.RecurrenceSet;
import org.dmfs.rfc5545.instanceiterator.PeekableInstanceIterator;

import java.util.NoSuchElementException;

/**
 * A {@link RecurrenceSet} of all elements of another {@link RecurrenceSet} that precede a
 * given {@link DateTime}.
 * A {@link Preceding} {@link RecurrenceSet} is always finite.
 *
 * <h2>Example</h2>
 * <pre>{@code
 * // a RecurrenceSet that only contains past occurrences.
 * new Preceding(DateTime.now(), recurrenceSet());
 * }</pre>
 */
public final class Preceding implements RecurrenceSet
{
    private final DateTime mBoundary;
    private final RecurrenceSet mDelegate;

    public Preceding(DateTime boundary, RecurrenceSet delegate)
    {
        mBoundary = boundary;
        mDelegate = delegate;
    }

    @Override
    public InstanceIterator iterator()
    {
        PeekableInstanceIterator delegate = new PeekableInstanceIterator(mDelegate.iterator());
        return new InstanceIterator()
        {
            @Override
            public void fastForward(DateTime until)
            {
                delegate.fastForward(until);
            }

            @Override
            public boolean hasNext()
            {
                return delegate.hasNext() && delegate.peek().before(mBoundary);
            }

            @Override
            public DateTime next()
            {
                DateTime result = delegate.next();
                if (!result.before(mBoundary))
                {
                    throw new NoSuchElementException("No more elements");
                }
                return result;
            }
        };
    }

    @Override
    public boolean isInfinite()
    {
        return false;
    }

    @Override
    public boolean isFinite()
    {
        return true;
    }
}
