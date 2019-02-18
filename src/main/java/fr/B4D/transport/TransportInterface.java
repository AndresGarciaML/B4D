package fr.B4D.transport;

import java.awt.AWTException;
import java.awt.Point;
import java.io.Serializable;

import fr.B4D.dofus.CannotFindException;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;

/** Interface repr�sentant un transport.
 */
public interface TransportInterface extends Serializable {
	final double zaapCost = 10;
	final double zaapiCost = 1;
	final double boosterPotionCost = 3;
	final double bontaPotionCost = 3;
	final double brakmarPotionCost = 3;
	final double almanaxPotionCost = 5;
	final double walkCost = 1;
	
	final Point bontaPotionDestination = new Point(-33, -56);
	final Point brakmarPotionDestination = new Point(-26,36);

	/** Permet d'utiliser le transport.
	 * @param destination - Destination du transport.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws CannotFindException Si la destination d'une de l'�tape est introuvable.
	 * @throws WrongPositionException Si le joueur est mal plac� pour emprunter le moyen de transport.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void goTo(Point destination) throws AWTException, CannotFindException, WrongPositionException, StopProgramException, CancelProgramException;
}
