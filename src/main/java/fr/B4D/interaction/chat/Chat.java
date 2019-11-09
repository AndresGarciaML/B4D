package fr.B4D.interaction.chat;

import java.awt.AWTException;
import java.util.concurrent.ArrayBlockingQueue;

import fr.B4D.bot.B4D;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;

/** La classe {@code Chat} représente le chat de dofus.<br><br>
 * Un chat est défini par une queue de messages, un filtre et une sub-routine.
 */
public class Chat extends Thread{

	  /***************/
	 /** ATTRIBUTS **/
	/***************/
	
	private ArrayBlockingQueue<Message> messages;
	private ChatFilter filter;
	private ChatListener chatListener;

	  /*************/
	 /** BUILDER **/
	/*************/
	
	/** Constructeur de la classe {@code Chat} avec un nombre de messages enregistrables de 100.
	 * Cela est identique à {@code Chat(100)}.
	 */
	public Chat() {
		this(100);
	}

	/** Constructeur de la classe {@code Chat}.
	 * @param capacity - Nombre maximum de message enregistrables.
	 */
	public Chat(int capacity) {
		messages = new ArrayBlockingQueue<Message>(capacity);
		filter = new ChatFilter();
		chatListener = new ChatListener() {
			public void treatMessage(Message message) {
				//Do nothing by default
			}
		};
	}

	  /*************/
	 /** SETTERS **/
	/*************/
	
	/** Modifie la sub-routine de traitement des messages.
	 * @param chatListener - Nouvelle sub-routine.
	 */
	public void setChatListener(ChatListener chatListener) {
		this.chatListener = chatListener;
	}
	
	  /************/
	 /** FILTER **/
	/************/
	
	/** Ajoute un filtre de pseudo. Seul les messages provenants de ce joueur seront traités.
	 * @param pseudo - Pseudo du joueur. {@code null} pour retirer le filtre.
	 */
	public void addPseudoFilter(String pseudo) {
		synchronized(filter) {
			filter.setPseudo(pseudo);
		}
	}

	/** Ajoute un filtre de canal. Seul les messages de ce canal seront traités.
	 * @param channel - Canal du message. {@code null} pour retirer le filtre.
	 */
	public void addChannelFilter(Channel channel) {		
		synchronized(filter) {
			filter.setChannel(channel);
		}
	}

	/** Ajoute un filtre de texte. Seul les messages contenants une chaine de caractère spécifique seront traités.
	 * @param regex - Expression régulière que doit contenir le message. {@code null} pour retirer le filtre.
	 */
	public void addTextFilter(String regex) {
		synchronized(filter) {
			filter.setRegex(regex);
		}
	}
	
	  /**********/
	 /** QUEU **/
	/**********/
	
	/** Ajoute un message à la queue du chat. Si celui-ci est plein, le plus vieux message sera supprimé.
	 * @param message - Message à ajouter â la queu.
	 */
	public void addMessage(Message message) {
		boolean pass;
		synchronized(filter) {
			pass = filter.filter(message);
		}
		if(pass) {
			synchronized(messages){
				if(!messages.offer(message)) {
					messages.poll();
					messages.offer(message);
				}
				messages.notifyAll();
			}
		}
	}

	  /**************/
	 /** LISTENER **/
	/**************/
	
	/** Traite un nombre infini de messages. Cela est identique à {@code read(-1)}.
	 * @throws StopProgramException Si le programme est stoppé.
	 * @throws CancelProgramException Si le bot programme est annulé.
	 * @throws AWTException Si un problème d'interface survient.
	 */
	public void read() throws StopProgramException, CancelProgramException, AWTException {
		read(-1);
	}
	
	/** Traite un nombre fini de messages.
	 * @param countTo - Nombre de message à traiter.
	 * @throws StopProgramException Si le programme est stoppé.
	 * @throws CancelProgramException Si le bot programme est annulé.
	 */
	public void read(int countTo) throws StopProgramException, CancelProgramException {		
		int count = 0;
		Message message;
		while(count != countTo) {
			message = waitForMessage(0);
			chatListener.treatMessage(message);
			count++;
		}
	}
	
	  /**********************/
	 /** WAIT FOR MESSAGE **/
	/**********************/
	
	/** Attend un message pour une durée infinie. Cela est identique à {@code waitForMessage(0)}.
	 * @return Plus vieux message correspondant au filtre.
	 * @throws StopProgramException Si le programme est stoppé.
	 * @throws CancelProgramException Si le bot programme est annulé.
	 */
	public Message waitForMessage() throws StopProgramException, CancelProgramException {
		return waitForMessage(0);
	}
	/** Attend un message pour une durée finie.
	 * @param timeout - Durée d'attente maximale en millisecondes.
	 * @return Plus vieux message correspondant au filtre et {@code null} si timeout.
	 * @throws StopProgramException Si le programme est stoppé.
	 * @throws CancelProgramException Si le bot programme est annulé.
	 */
	public Message waitForMessage(long timeout) throws StopProgramException, CancelProgramException {		
		Message message = null;

		B4D.logger.debug("Attente d'un message");
		message = messages.poll();
		if(message == null) {
			synchronized(messages){
				B4D.wait.waitOnObject(messages, timeout);
				message = messages.poll();
			}
		}
		if(message == null)
			B4D.logger.debug("Aucun message reçu (timeout)");
		else
			B4D.logger.debug("Message reçu [" + message + "]");

		return message;
	}

	  /***********/
	 /** OTHER **/
	/***********/
	
	/** Efface les messages contenues dans la queue.
	 */
	public void clear() {
		synchronized(messages){
			messages.clear();
		}
	}
}
