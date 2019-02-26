package fr.B4D.interaction.chat;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import fr.B4D.bot.B4D;
import fr.B4D.dofus.Dofus;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;

/** La classe {@code Message} repr�sente un message, g�n�ralement un message du chat.<br><br>
 * Un message est d�fini par un pseudo, un canal et un texte.
 */
public class Message implements Serializable{
	
	private static final long serialVersionUID = 5508700695491701427L;

	  /**************/
	 /** ATRIBUTS **/
	/**************/
	
	private String pseudo, text;
	private Channel channel;
	
	  /*************/
	 /** BUILDER **/
	/*************/
	
	/** Constructeur de la classe {@code Message}. 
	 * Cela est identique � {@code Message(null, null, text)}.
	 * Ce constructeur est utilis� pour envoyer un message sans destinataire particulier et sans canal particulier.
	 * @param text - Texte du message.
	 */
	public Message(String text) {
		this(null, null, text);
	}
	
	/** Constructeur de la classe {@code Message}. 
	 * Cela est identique � {@code Message(null, channel, text)}.
	 * Ce constructeur est utilis� pour envoyer un message sans destinataire particulier.
	 * @param channel - Canal du message.
	 * @param text - Texte du message.
	 */
	public Message(Channel channel, String text) {
		this(null, channel, text);
	}
	
	/** Constructeur de la classe {@code Message}. 
	 * Cela est identique � {@code Message(pseudo, Channel.PRIVATE, text)}.
	 * Ce constructeur est utilis� pour envoyer un message priv�.
	 * @param pseudo - Pseudo du destinataire.
	 * @param text - Texte du message.
	 */
	public Message(String pseudo, String text) {
		this(pseudo, Channel.PRIVATE, text);
	}
	
	/** Constructeur de la classe {@code Message}. 
	 * Cela est identique � {@code Message(pseudo, channel, text)}.
	 * @param pseudo - Pseudo du destinataire.
	 * @param channel - Canal du message.
	 * @param text - Texte du message.
	 */
	public Message(String pseudo, Channel channel, String text) {
		this.pseudo = pseudo;
		this.channel = channel;
		this.text = text;
	}

	  /*************/
	 /** GETTERS **/
	/*************/
	
	/** Retourne le pseudo du joueur.
	 * @return Pseudo du joueur.
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/** Retourne le texte du message.
	 * @return Texte du message.
	 */
	public String getText() {
		return text;
	}
	
	/** Retourne le canal du message.
	 * @return Canal du message.
	 */
	public Channel getChannel() {
		return channel;
	}
	
	  /**********/
	 /** SEND **/
	/**********/
	
	/** Envoi du message avec un temps d'attente par d�faut de 500ms.
	 * Cela est identique � {@code send(500)}.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
	 */
	public void send() throws StopProgramException, CancelProgramException {
		send(500);
	}
	
	/** Envoi du message avec un temps d'attente.
	 * @param millis - Temps d'attente apr�s envoi.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
	 */
	public void send(int millis) throws StopProgramException, CancelProgramException {
		if (channel == null) {
			if (pseudo == null) {
				send(text, millis);
			}
			else {
				send(pseudo + " " + text, millis);
			}
		}
		else {
			if (channel == Channel.PRIVATE)
				send(channel.getPrefix() + " " + pseudo + " " + text, millis);
			else
				send(channel.getPrefix() + " " + text, millis);
		}
	}
	
	/** Envoi un message sur le chat.
	 * @param text - Texte du message.
	 * @param millis - Temps d'attente apr�s l'envoi.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
	 */
	private void send(String text, int millis) throws StopProgramException, CancelProgramException {
		B4D.mouse.chatClick();
		B4D.keyboard.writeKeyboard(text, millis);
		B4D.keyboard.sendKey(KeyEvent.VK_ENTER, 100);
	}
	
	  /***********/
	 /** REPLY **/
	/***********/
	
	/** Renpond au joueur avec un temps d'attente par d�faut de 500ms.
	 * Cela est identique � {@code reply(text, 500)}.
	 * Si le pseudo du joueur est {@code null}, la m�thode n'a aucun effet.
	 * @param text - R�ponse au joueur.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
	 */
	public void reply(String text) throws StopProgramException, CancelProgramException {
		reply(text, 500);
	}

	/** Renpond au joueur avec un temps d'attente.
	 * Si le pseudo du joueur est {@code null}, la m�thode n'a aucun effet.
	 * @param text - R�ponse au joueur.
	 * @param millis - Temps d'attente apr�s l'envoi.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
	 */
	public void reply(String text, int millis) throws StopProgramException, CancelProgramException {
		if(pseudo != null) {
			if(channel == Channel.BUSINESS || channel == Channel.RECRUITMENT)
				channel = Channel.PRIVATE;
			this.text = text;
			send(millis);
		}
	}
	
	/** Permet d'attendre la r�ponse du joueur sans limite de temps.
	 * Si le pseudo est connu, la r�ponse sera d�tect�e sur tous les canaux possible.
	 * Sinon, seul le canal priv� sera accept�.
	 * Cela est identique � {@code waitForReply(0)}.
	 * @return Message d�tect�. {@code null} si timeout.
	 */
	public Message waitForReply() {
		return waitForReply(0);
	}
	
	/** Permet d'attendre la r�ponse du joueur avec limite de temps.
	 * Si le pseudo est connu, la r�ponse sera d�tect�e sur tous les canaux possible.
	 * Sinon, seul le canal priv� sera accept�.
	 * @param timeout - Dur�e d'attente maximale en millisecondes.
	 * @return Message d�tect�. {@code null} si timeout.
	 */
	public Message waitForReply(long timeout) {
		B4D.logger.debug(this, "Attente d'une r�ponse");
		if(pseudo != null)
			Dofus.chat.addPseudoFilter(pseudo);
		else
			Dofus.chat.addChannelFilter(Channel.PRIVATE);
		
		Message message = Dofus.chat.waitForMessage(timeout);
		
		Dofus.chat.addPseudoFilter(null);
		Dofus.chat.addChannelFilter(null);
		return message;
	}
	
	  /***************/
	 /** TO STRING **/
	/***************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if(pseudo != null)
			return "[" + channel.toString() +"][" + pseudo + "] " + text;
		else
			return "[" + channel.toString() +"] " + text;
	}
}
