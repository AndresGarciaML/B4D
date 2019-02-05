package fr.B4D.interaction.chat;

import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;

/** Sub-routine de traitement des messages.
 */
public interface ChatListener {
	/** Traite les messages traversant le filtre.
	 * @param message - Message trait�.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
	 */
	public void treatMessage(Message message) throws StopProgramException, CancelProgramException;
}
