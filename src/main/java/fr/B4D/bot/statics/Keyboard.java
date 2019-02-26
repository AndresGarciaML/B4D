package fr.B4D.bot.statics;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import fr.B4D.bot.B4D;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;
import fr.B4D.threads.KeyboardThread;

/** La classe {@code Keyboard} permet d'acc�der � toutes les m�thodes li�s au clavier.
 */
public final class Keyboard{
	
	private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	private Robot robot;
	
	/*************/
	/** BUILDER **/
	/*************/

	/** Constructeur de la classe {@code Mouse}. 
	 * @throws AWTException Si la configuration de l'ordinateur ne permet pas l'automatisation du clavier
     */
    public Keyboard() throws AWTException {
		this.robot = new Robot();
    }
	
	  /****************/
	 /** SINGLE KEY **/
	/****************/
	
	/** Permet de simuler l'appui sur une touche du clavier. 
	 * @param keyEvent - Entier repr�sentant le touche du clavier.
	 * @param time - Temps d'attente apr�s l'appui sur la touche.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	public void sendKey(int keyEvent, int time) throws StopProgramException, CancelProgramException {
		robot.keyPress(keyEvent);
		robot.keyRelease(keyEvent);
		B4D.wait.wait(time);
	}
	
	/** Permet de simuler l'appui sur une touche du clavier avec un temps d'attente par d�faut de 100ms.
	 * Cela est identique � {@code sendKey(keyEvent, 100)}.
	 * @param keyEvent - Entier repr�sentant le touche du clavier.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	public void sendKey(int keyEvent) throws StopProgramException, CancelProgramException {
		sendKey(keyEvent, 100);
	}
	
	  /********************/
	 /** WRITE KEYBOARD **/
	/********************/
	
	/** Permet d'�crire un texte au clavier.
	 * Cette m�thode copy en r�alit� la chaine de caract�re dans le presse papier puis fait un Ctrl+V.
	 * @param text - Texte � �crire.
	 * @param time - Temps d'attente apr�s �criture du texte.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	public void writeKeyboard(String text, int time) throws StopProgramException, CancelProgramException {
		setClipboard(text);		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		B4D.wait.wait(time);
	}
	
	/** Permet d'�crire un texte au clavier avec un temps d'attente par d�faut de 500ms.
	 * Cela est identique � {@code writeKeyboard(text, 500)}.
	 * Cette m�thode copy en r�alit� la chaine de caract�re dans le presse papier puis fait un Ctrl+V
	 * @param text - Texte � �crire.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	public void writeKeyboard(String text) throws StopProgramException, CancelProgramException {
		writeKeyboard(text, 500);
	}
	
	  /***************/
	 /** CLIPBOARD **/
	/***************/
	
	/** Permet de copier une chaine de caract�re dans le presse papier.
	 * @param text - Texte � copier.
	 */
	public void setClipboard(String text) {
		clipboard.setContents(new StringSelection(text), null);
	}
	
	/** Permet de retourner une chaine de caract�re
	 * @return Chaine de caract�re pr�sente dans le presse papier.
	 * {@code null} si la donn�e du presse papier n'est pas une chaine de caract�re ou si le presse papier est vide.
	 */
	public String getClipboard(){
        try {
			return (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (IOException | UnsupportedFlavorException e) {
			return null;
		}
	}
	
	  /************************/
	 /** ATTENTE SUR TOUCHE **/
	/************************/
	
	/** Permet d'attendre l'appui sur une touche.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return Entier repr�sentant la touche enfonc�e. {@code -1} si timeout.
	 */
	public int waitForKeyboard(int timeOut) {
		KeyboardThread keyboardThread = new KeyboardThread();
		keyboardThread.start();
		try {
			keyboardThread.join(timeOut);
		} catch (InterruptedException e) {
			B4D.logger.error(e);
		}
		
		if(keyboardThread.isAlive()) 
			keyboardThread.interrupt();
		return keyboardThread.getKey();
	}
}
