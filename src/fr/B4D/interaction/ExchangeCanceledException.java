package fr.B4D.interaction;

/** Signal que l'�change en cours � �t� annul�.
 *
 */
public class ExchangeCanceledException extends Exception{

	private static final long serialVersionUID = 1721525819432312605L;

	/** Constructeur de l'exception {@code ExchangeCanceled}.
	 * 
	 */
	public ExchangeCanceledException()
    {
    	super("The exchange have been canceled.");
    }
}
