package fr.B4D.programs.tutorials;

import java.awt.AWTException;
import java.io.IOException;

import fr.B4D.bot.B4D;
import fr.B4D.bot.Person;
import fr.B4D.dofus.CannotFindException;
import fr.B4D.dofus.Dofus;
import fr.B4D.interaction.chat.Channel;
import fr.B4D.interaction.chat.ChatListener;
import fr.B4D.interaction.chat.Message;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.Category;
import fr.B4D.program.Place;
import fr.B4D.program.Program;
import fr.B4D.program.ProgramInterface;
import fr.B4D.program.Status;
import fr.B4D.program.StopProgramException;

public final class MessageAPI {	
	
	/** Ce tutoriel � pour objectif de mieux comprendre le fonctionnement et l'utilisation de l'API des messages entre joueurs.<br/>
	 *  <br/>
	 *  Fonctionnement :
	 *  <ul>
	 *  	<li>Attente d'un message quelconque dans le chat.</li>
	 *  	<li>Affiche le message re�u ainsi que l'auteur et le canal.</li>
	 *  </ul>
	 */
	public final static Program TUTORIAL1 = new Program(Place.Aucun, Category.Tutorial, "Message API", "Tutorial 1", new Channel[] {Channel.PRIVATE}, Status.AVAILABLE, new ProgramInterface() {
		public void intro(Person person) {}
		public void outro(Person person) {}
		public void cycle(Person person) throws AWTException, CannotFindException, IOException {
			Message message = Dofus.chat.waitForMessage(0);
			B4D.logger.popUp("Message de " + message.getPseudo() + "(" + message.getChannel() + ") : " + message.getText());
		}
	});

	/** Ce tutoriel � pour objectif de mieux comprendre le fonctionnement et l'utilisation de l'API des �changes entre joueurs.<br/>
	 *  <br/>
	 *  Fonctionnement :
	 *  <ul>
	 *  	<li>Envoi d'un message priv� � "Solwy".</li>
	 *  	<li>Envoi du message.</li>
	 *  	<li>Attente de la r�ponse du joueur.</li>
	 *  	<li>R�pond "�a va ?" si le joueur � r�pondu. Affiche un message si il n'a pas r�pondu apr�s 1 min = 60000 ms.</li>
	 *  </ul>
	 *  Dans le cas o� l'�change est annul� par le joueur, un exception est lev�e. Un message diff�rent est alors affich�.<br/>
	 */
	public final static Program TUTORIAL2 = new Program(Place.Aucun, Category.Tutorial, "Message API", "Tutorial 2", new Channel[] {Channel.PRIVATE}, Status.AVAILABLE, new ProgramInterface() {
		public void intro(Person person) {}
		public void outro(Person person) {}
		public void cycle(Person person) throws AWTException, CannotFindException, IOException, StopProgramException, CancelProgramException {
			Message message = new Message("Solwy", "Salut !");
			message.send();
			message = message.waitForReply(60000);
			if(message != null)
				message.reply("�a va ?");
			else	
				B4D.logger.popUp("Le temps d'attente de 1 min est d�pass�.");				
		}
	});

	/** Ce tutoriel � pour objectif de mieux comprendre le fonctionnement et l'utilisation de l'API des �changes entre joueurs.<br/>
	 *  <br/>
	 *  Fonctionnement :
	 *  <ul>
	 *  	<li>Ajout d'un filtre canal : Seul les messages du canal commerce seront trait�s.</li>
	 *  	<li>Ajout d'un filtre texte : Seul les messages contenant "moi" seront trait�s.</li>
	 *  	<li>Ajout d'une op�ration � �ffectuer pour chaque message correspondant au filtre.</li>
	 *  	<li>D�bute la lecture du chat en pr�cisant � 3 le nombre de messages qui seront trait�s.</li>
	 *  </ul>
	 */
	public final static Program TUTORIAL3 = new Program(Place.Aucun, Category.Tutorial, "Message API", "Tutorial 3", new Channel[] {Channel.BUSINESS, Channel.PRIVATE}, Status.AVAILABLE, new ProgramInterface() {
		public void intro(Person person) {}
		public void outro(Person person) {}
		public void cycle(Person person) throws AWTException, CannotFindException, IOException, StopProgramException, CancelProgramException {
			Dofus.chat.addChannelFilter(Channel.BUSINESS);
			Dofus.chat.addTextFilter("moi");
			Dofus.chat.setChatListener(new ChatListener() {
				public void treatMessage(Message message) throws StopProgramException, CancelProgramException {
					message.reply("C'est qui moi ?");
				}
			});
			Dofus.chat.read(3);
		}
	});
}
