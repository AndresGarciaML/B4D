package fr.B4D.bot;

/** Signal qu'une exception de type B4D a eu lieu.
 */
public class B4DException extends Exception{

	private static final long serialVersionUID = -2949855971863227080L;
	
	private boolean canSendRepport;
	
	/** Constructeur de l'exception {@code B4DException}.
	 * Aucun rapport d'erreur ne pourra �tre envoy�.
	 * Cela est identique � {@code B4DException(false)}.
	 */
	public B4DException() {
    	this(false);
    }
	
	/** Constructeur de l'exception {@code B4DException}.
	 * @param canSendRepport - {@code true} pour avoir la possibilit� d'envoyer un rapport d'erreur, {@code false} sinon.
	 */
	public B4DException(boolean canSendRepport) {
    	super();
		this.canSendRepport = canSendRepport;
    }
	
	/** Constructeur de l'exception {@code B4DException}.
	 * Un rapport d'erreur pourra �tre envoy�.
	 * Cela est identique � {@code B4DException(raison, true)}.
	 * @param raison - Raison de l'exception.
	 */
	public B4DException(String raison)
    {
    	this(raison, true);
    }
	
	/** Constructeur de l'exception {@code B4DException}.
	 * @param raison - Raison de l'exception.
	 * @param canSendRepport - {@code true} pour avoir la possibilit� d'envoyer un rapport d'erreur, {@code false} sinon.
	 */
	public B4DException(String raison, boolean canSendRepport)
    {
    	super(raison);
		this.canSendRepport = canSendRepport;
    }
	
	/** Retourne un boolean repr�sentant si un rapport d'erreur peut �tre envoy�.
	 * @return {@code true} si un rapport d'erreur peut �tre envoy�, {@code false} sinon.
	 */
	public boolean canSendRepport() {
		return this.canSendRepport;
	}
}
