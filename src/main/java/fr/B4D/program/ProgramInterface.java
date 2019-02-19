package fr.B4D.program;

import java.awt.AWTException;
import java.io.IOException;
import java.security.GeneralSecurityException;

import fr.B4D.bot.B4DException;
import fr.B4D.bot.Person;
import fr.B4D.dofus.CannotFindException;
import net.sourceforge.tess4j.TesseractException;

public interface ProgramInterface{
	
	/** Fonction d'introduction du programme. Celle-ci ne sera �xecut�e qu'une seule fois en d�but de programme.
	 * @param person - Personnage avec lequel lancer l'introduction.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws B4DException Si une exception de type B4D est lev�e.
	 * @throws IOException Si un probl�me de fichier survient.
     * @throws GeneralSecurityException Si probl�mes de s�curit� survient.
	 */
	public void intro(Person person) throws StopProgramException, CancelProgramException, B4DException, IOException, GeneralSecurityException;
	
	/** Fonction principale du programme. Celle-ci sera �xecut�e plusieurs fois.
	 * @param person - Personnage avec lequel lancer le programme.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws B4DException Si une exception de type B4D est lev�e.
	 * @throws FullInventoryException Si l'inventaire est plein.
	 * @throws TesseractException Si une exception Tesseract est lev�e.
	 * @throws AWTException Si un probl�me de souris ou clavier survient. 
	 * @throws IOException Si un probl�me de fichier survient.
	 * @throws NumberFormatException Si un probl�me d'encodage survient.
	 */
	public void cycle(Person person) throws StopProgramException, CancelProgramException, FullInventoryException, AWTException, CannotFindException, IOException, TesseractException, NumberFormatException, B4DException;
	
	/** Fonction de fin du programme. Celle-ci ne sera �xecut�e qu'une seule fois en fin de programme.
	 * @param person - Personnage avec lequel lancer la fonction de fin.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	public void outro(Person person) throws CancelProgramException;
}
