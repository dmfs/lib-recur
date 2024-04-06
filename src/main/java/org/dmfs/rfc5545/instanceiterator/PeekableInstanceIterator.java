package org.dmfs.rfc5545.instanceiterator;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.InstanceIterator;

import java.util.NoSuchElementException;

public final class PeekableInstanceIterator implements InstanceIterator
{
    private final InstanceIterator mDelegate;
    private DateTime mNext;
    private boolean mHasNext;

    public PeekableInstanceIterator(InstanceIterator delegate)
    {
        mDelegate = delegate;
        pullNext();
    }

    @Override
    public void fastForward(DateTime until)
    {
        if (mHasNext && mNext.before(until))
        {
            mDelegate.fastForward(until);
            pullNext();
        }
    }

    @Override
    public boolean hasNext()
    {
        return mHasNext;
    }

    @Override
    public DateTime next()
    {
        if (!mHasNext)
        {
            throw new NoSuchElementException("no further elements to return");
        }
        DateTime result = mNext;
        pullNext();
        return result;
    }

    public DateTime peek()
    {
        if (!mHasNext)
        {
            throw new NoSuchElementException("no further elements to peek at");
        }
        return mNext;
    }

    private void pullNext()
    {
        mHasNext = mDelegate.hasNext();
        if (mHasNext)
        {
            mNext = mDelegate.next();
        }

    }
}
