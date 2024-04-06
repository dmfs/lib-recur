package org.dmfs.rfc5545.recurrenceset;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.RecurrenceSet;
import org.dmfs.rfc5545.RecurrenceSetComposition;

/**
 * {@link RecurrenceSet} of the elements of another {@link RecurrenceSet} that fall
 * in the given right-open interval of time.
 * A {@link Within} {@link RecurrenceSet} is always finite.
 *
 * <h2>Example</h2>
 * <pre>{@code
 * // every occurrence in 2024 (UTC)
 * new Within(
 *   DateTime.parse("20240101T000000Z"),
 *   DateTime.parse("20250101T000000Z"),
 *   recurrenceSet());
 * }</pre>
 */
public final class Within extends RecurrenceSetComposition
{
    public Within(DateTime fromIncluding, DateTime toExcluding, RecurrenceSet delegate)
    {
        super(new Preceding(toExcluding, new FastForwarded(fromIncluding, delegate)));
    }
}
