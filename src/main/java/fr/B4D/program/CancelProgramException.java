package fr.B4D.program;

import fr.B4D.bot.B4DException;

/** Signal que le programme a �t� annul�.
 * La fonction de sortie (outro) ne sera pas ex�cut�.
 */
public class CancelProgramException extends B4DException{

	private static final long serialVersionUID = 6971038782335799708L;

	/** Constructeur de l'exception {@code CancelProgramException}.
	 */
	public CancelProgramException() {
    	super();
    }
	
	/** Constructeur de l'exception {@code CancelProgramException}.
	 * @param raison - Raison pour laquelle le programme � �t� annul�.
	 */
	public CancelProgramException(String raison) {
    	super("Le programme a �t� int�rompu pour la raison suivante :\n" + raison);
    }
}
