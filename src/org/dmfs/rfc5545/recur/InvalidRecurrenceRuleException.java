package org.dmfs.rfc5545.recur;

public class InvalidRecurrenceRuleException extends Exception
{

	/**
	 * generated serial id.
	 */
	private static final long serialVersionUID = 2282570760598972553L;


	public InvalidRecurrenceRuleException(String msg)
	{
		super(msg);
	}


	public InvalidRecurrenceRuleException(String msg, Throwable e)
	{
		super(msg, e);
	}
}
