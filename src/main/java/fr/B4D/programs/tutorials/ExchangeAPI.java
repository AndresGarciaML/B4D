package fr.B4D.programs.tutorials;

import java.awt.AWTException;
import java.awt.Image;
import java.io.IOException;

import fr.B4D.bot.B4D;
import fr.B4D.bot.Person;
import fr.B4D.dofus.CannotFindException;
import fr.B4D.interaction.Exchange;
import fr.B4D.interaction.ExchangeCanceledException;
import fr.B4D.interaction.chat.Channel;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.Category;
import fr.B4D.program.Place;
import fr.B4D.program.Program;
import fr.B4D.program.ProgramInterface;
import fr.B4D.program.Status;
import fr.B4D.program.StopProgramException;
import net.sourceforge.tess4j.TesseractException;

public final class ExchangeAPI {	

	/** Ce tutoriel � pour objectif de mieux comprendre le fonctionnement et l'utilisation de l'API des �changes entre joueur.<br/>
	 *  <br/>
	 *  Fonctionnement :
	 *  <ul>
	 *  	<li>Cr�ation de l'objet �change avec 100 000 kamas entrants, 0 kamas sortants.</li>
	 *  	<li>Attente d'une demande d'�change.</li>
	 *  	<li>Proc�de � l'�change avec un message de confirmation auquel la personne peut r�pondre uniquement par "oui" ou "non".</li>
	 *  	<li>Affiche le nom du joueur qui vient de r�aliser l'�change ainsi que la preuve de son consentement.</li>
	 *  </ul>
	 *  Dans le cas o� l'�change est annul� par le joueur, un exception est lev�e. Un message diff�rent est alors affich�.<br/>
	 */
	public final static Program TUTORIAL1 = new Program(Place.Aucun, Category.Tutorial, "Exchange API", "Tutorial 1", new Channel[] {Channel.PRIVATE}, Status.AVAILABLE, new ProgramInterface() {
		public void intro(Person person) {}
		public void outro(Person person) {}
		public void cycle(Person person) throws AWTException, CannotFindException, IOException, StopProgramException, CancelProgramException, TesseractException {
			Exchange exchange = new Exchange(100000,0);
			String name = exchange.waitForExchange();
			try {
				Image proof = exchange.exchange("T'es sur de ce don ?");
				B4D.logger.popUp("Echange avec " + name + " effectu�. Voici la preuve " + proof);
			} catch (ExchangeCanceledException e) {
				B4D.logger.popUp("L'�change � �t� annul�");
			}
		}
	});
}
