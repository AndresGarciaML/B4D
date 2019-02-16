package fr.B4D.programs.tutorials;

import java.awt.AWTException;
import java.awt.Point;

import fr.B4D.bot.Person;
import fr.B4D.dofus.CannotFindException;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.Category;
import fr.B4D.program.Place;
import fr.B4D.program.Program;
import fr.B4D.program.ProgramInterface;
import fr.B4D.program.StopProgramException;
import fr.B4D.transport.WrongPositionException;

public final class TransportAPI {
	
	/** Ce tutoriel � pour objectif de mieux comprendre le fonctionnement et l'utilisation de l'API des transports.<br/>
	 *  <br/>
	 *  Fonctionnement :
	 *  <ul>
	 *  	<li>Se rend en (4, -18).</li>
	 *  	<li>Se rend en (3, -19).</li>
	 *  </ul>
	 */
	public final static Program TUTORIAL1 = new Program(Place.Aucun, Category.Tutorial, "Transport API", "Tutorial 1", null, null, new ProgramInterface() {
		public void intro(Person person) {}
		public void outro(Person person) {}
		public void cycle(Person person) throws AWTException, CannotFindException, WrongPositionException, StopProgramException, CancelProgramException {
			person.goTo(new Point(4, -18));
			person.goTo(new Point(3, -19));
		}
	});

	/** Ce tutoriel � pour objectif de mieux comprendre le fonctionnement et l'utilisation de l'API des transports.<br/>
	 *  <br/>
	 *  Fonctionnement :
	 *  <ul>
	 *  	<li>Se rend au zaap enregistr� par le joueur.</li>
	 *  </ul>
	 */
	public final static Program TUTORIAL2 = new Program(Place.Aucun, Category.Tutorial, "Transport API", "Tutorial 2", null, null, new ProgramInterface() {
		public void intro(Person person) {}
		public void outro(Person person) {}
		public void cycle(Person person) throws AWTException, CannotFindException, WrongPositionException, StopProgramException, CancelProgramException {
			person.goTo(person.getBontaPotion().getDestination());
		}
	});
}
