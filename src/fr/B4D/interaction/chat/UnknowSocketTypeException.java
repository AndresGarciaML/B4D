package fr.B4D.interaction.chat;

import net.sourceforge.jpcap.util.HexHelper;

/** Signal que le type de trame est inconnu.
*
*/
public class UnknowSocketTypeException extends Exception {

	private static final long serialVersionUID = 924080672225339433L;
	
	/** Constructeur de l'exception {@code UnknowSocketTypeException}.
	 * @param data - Tableau d'octet repr�sentant la trame compl�te.
	 */
	public UnknowSocketTypeException(byte[] data) {
		super("Unknow socket type [" + HexHelper.toString(data) + "]");
	}
}
