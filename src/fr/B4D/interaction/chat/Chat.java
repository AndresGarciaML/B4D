package fr.B4D.interaction.chat;

import java.util.concurrent.ArrayBlockingQueue;

import fr.B4D.bot.B4D;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;

/** La classe {@code Chat} repr�sente le chat de dofus.<br/>
 * Un chat est d�fini par une queue de messages, un filtre et une sub-routine.
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
	 * Cela est identique � {@code Chat(100)}.
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
	 * @param chatListener - Nouvelle sub-routine
	 */
	public void setChatListener(ChatListener chatListener) {
		this.chatListener = chatListener;
	}
	
	  /************/
	 /** FILTER **/
	/************/
	
	/** Ajoute un filtre de pseudo. Seul les messages provenants de ce joueur seront trait�s.
	 * @param pseudo - Pseudo du joueur. {@code null} pour retirer le filtre.
	 */
	public void addPseudoFilter(String pseudo) {
		filter.setPseudo(pseudo);
	}

	/** Ajoute un filtre de canal. Seul les messages de ce canal seront trait�s.
	 * @param channel - Canal du message. {@code null} pour retirer le filtre.
	 */
	public void addChannelFilter(Channel channel) {
		filter.setChannel(channel);
	}

	/** Ajoute un filtre de texte. Seul les messages contenants une chaine de caract�re sp�cifique seront trait�s.
	 * @param regex - Expression r�guli�re que doit contenir le message. {@code null} pour retirer le filtre.
	 */
	public void addTextFilter(String regex) {
		filter.setRegex(regex);
	}
	
	  /**********/
	 /** QUEU **/
	/**********/
	
	/** Ajoute un message � la queue du chat. Si celui-ci est plein, le plus vieux message sera supprim�.
	 * @param message - Message � ajouter � la queu.
	 */
	public void addMessage(Message message) {
		synchronized(messages){
			if(!messages.offer(message)) {
				messages.poll();
				messages.offer(message);
			}
			messages.notifyAll();
		}
	}

	  /**************/
	 /** LISTENER **/
	/**************/
	
	/** Traite un nombre infini de messages. Cela est identique � {@code read(-1)}.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
	 */
	public void read() throws StopProgramException, CancelProgramException {
		read(-1);
	}
	
	/** Traite un nombre fini de messages.
	 * @param countTo - Nombre de message � traiter.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
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
	
	/** Attend un message pour une dur�e infinie. Cela est identique � {@code waitForMessage(0)}.
	 * @return Plus vieux message correspondant au filtre.
	 */
	public Message waitForMessage() {
		return waitForMessage(0);
	}
	/** Attend un message pour une dur�e finie.
	 * @param timeout - Dur�e d'attente maximale en millisecondes.
	 * @return Plus vieux message correspondant au filtre et {@code null} si timeout.
	 */
	public Message waitForMessage(long timeout) {		
		Message message = null;
		try {
			B4D.logger.debug(this, "Attente d'un message");
			do {
				message = messages.poll();
				if(message == null) {
					synchronized(messages){
						messages.wait(timeout);
						message = messages.poll();
						if(message == null) 
							break;
					}
				}
			}while(!filter.filter(message));
			
			if(message == null)
				B4D.logger.debug(this, "Aucun message re�u (timeout)");
			else
				B4D.logger.debug(this, "Message re�u [" + message + "]");
		}
		catch(InterruptedException e) {
			B4D.logger.error(e);
		}
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
