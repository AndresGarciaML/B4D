package fr.B4D.bot.statics;

import java.awt.AWTException;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import fr.B4D.bot.B4D;
import fr.B4D.bot.Configuration;
import fr.B4D.gui.JFrame_GetPoint;
import fr.B4D.gui.JFrame_GetPointImage;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;
import fr.B4D.utils.PointD;
import fr.B4D.utils.PointF;

/** La classe {@code Mouse} permet d'acc�der � toutes les m�thodes li�s � la souris.
 */
public final class Mouse {

	private Configuration configuration;
    
	/*************/
	/** BUILDER **/
	/*************/

	/** Constructeur de la classe {@code Mouse}. 
     * @param configuration - Configuration de l'�cran de jeu.
     */
    public Mouse(Configuration configuration) {
    	this.configuration = configuration;
    }
    
	/***************/
	/** GET POINT **/
	/***************/

	/** Permet de r�cup�rer la position d'un �l�ment en coordonn�es simples.
	 * @param text - Texte affich�.
	 * @param mouseAdapter - Action � r�aliser l'or de l'appui.
	 */
	public void getPoint(String text, MouseAdapter mouseAdapter) {
		JFrame_GetPoint window = new JFrame_GetPoint(text, mouseAdapter);
		window.frame.setVisible(true);
	}
	
	/** Permet de r�cup�rer la position d'un �l�ment en coordonn�es simples.
	 * @param text - Texte affich�e.
	 * @param image - Image affich�e.
	 * @param mouseAdapter - Action � r�aliser l'or de l'appui.
	 */
	public void getPoint(String text, ImageIcon image, MouseAdapter mouseAdapter) {
		JFrame_GetPointImage window = new JFrame_GetPointImage(text, image, mouseAdapter);
		window.frame.setVisible(true);
	}
	
	/** Permet de r�cup�rer la position de deux �l�ments en coordonn�es simples.
	 * @param text1 - Texte affich� pour le premier appui.
	 * @param mouseAdapter1 - Action � r�aliser l'or du premier appui.
	 * @param text2 - Texte affich� pour le second appui.
	 * @param mouseAdapter2 - Action � r�aliser l'or du second appui.
	 */
	public void getPoints(String text1, MouseAdapter mouseAdapter1, String text2, MouseAdapter mouseAdapter2) {
		JFrame_GetPoint window1 = new JFrame_GetPoint(text1, mouseAdapter1);
		JFrame_GetPoint window2 = new JFrame_GetPoint(text2, mouseAdapter2);
		window1.frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				window2.frame.setVisible(true);
			}
		});
		window1.frame.setVisible(true);
	}
	
	/** Permet de r�cup�rer la position de deux �l�ments en coordonn�es simples.
	 * @param text1 - Texte affich� pour le premier appui
	 * @param image1 - Image affich�e pour le premier appui.
	 * @param mouseAdapter1 - Action � r�aliser l'or du premier appui.
	 * @param text2 - Texte affich� pour le second appui.
	 * @param image2 - Image affich�e pour le second appui.
	 * @param mouseAdapter2 - Action � r�aliser l'or du second appui.
	 */
	public void getPoints(String text1, ImageIcon image1, MouseAdapter mouseAdapter1, String text2, ImageIcon image2, MouseAdapter mouseAdapter2) {
		JFrame_GetPointImage window1 = new JFrame_GetPointImage(text1, image1, mouseAdapter1);
		JFrame_GetPointImage window2 = new JFrame_GetPointImage(text2, image2, mouseAdapter2);
		window1.frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				window2.frame.setVisible(true);
			}
		});
		window1.frame.setVisible(true);
	}
	
	  /************/
	 /** PLACER **/
	/************/
	
	/** Permet de placer la souris � une certaine position.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @param millis - Temps d'attente apr�s positionnement.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void place(Point position, int millis) throws AWTException, StopProgramException, CancelProgramException {
		Robot robot = new Robot();
		robot.mouseMove((int)position.getX(),(int)position.getY());
		B4D.wait.wait(millis);
	}
	
	/** Permet de placer la souris � une certaine position avec un temps d'attente par d�faut de 0ms.
	 * Cela est identique � {@code place(position, 0)}.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void place(Point position) throws AWTException, StopProgramException, CancelProgramException {
		place(position, 0);
	}
	
	/** Permet de placer la souris � une certaine position.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @param millis - Temps d'attente apr�s positionnement.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void place(PointF position, int millis) throws AWTException, StopProgramException, CancelProgramException {
		place(B4D.converter.toPoint(position), millis);
	}
	
	/** Permet de placer la souris � une certaine position avec un temps d'attente par d�faut de 0ms.
	 * Cela est identique � {@code place(position, 0)}.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void place(PointF position) throws AWTException, StopProgramException, CancelProgramException {
		place(B4D.converter.toPoint(position), 0);
	}

	/** Permet de placer la souris � une certaine position.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @param millis - Temps d'attente apr�s positionnement.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void place(PointD position, int millis) throws AWTException, StopProgramException, CancelProgramException {
		place(B4D.converter.toPoint(position), millis);
	}
	
	/** Permet de placer la souris � une certaine position avec un temps d'attente par d�faut de 0ms.
	 * Cela est identique � {@code place(position, 0)}.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void place(PointD position) throws AWTException, StopProgramException, CancelProgramException {
		place(B4D.converter.toPoint(position), 0);
	}
	
	  /******************/
	 /** CLIQUE DROIT **/
	/******************/

	/** Permet de simuler un clique droit.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void rightClick(Point position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException{
		Robot robot = new Robot();
		robot.mouseMove((int)position.getX(),(int)position.getY());
			
		if(maj)
			robot.keyPress(KeyEvent.VK_SHIFT);
		robot.mousePress(KeyEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(KeyEvent.BUTTON3_DOWN_MASK);
		if(maj)	
			robot.keyRelease(KeyEvent.VK_SHIFT);

		B4D.wait.wait(millis);		
	}
	
	/** Permet de simuler un clique droit avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code rightClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void rightClick(Point position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		rightClick(position, maj, 1000);
	}
	
	/** Permet de simuler un clique droit.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void rightClick(PointF position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException{
		rightClick(B4D.converter.toPoint(position), maj, millis);
	}
	
	/** Permet de simuler un clique droit avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code rightClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void rightClick(PointF position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		rightClick(B4D.converter.toPoint(position), maj, 1000);
	}
	
	/** Permet de simuler un clique droit.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void rightClick(PointD position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException{
		rightClick(B4D.converter.toPoint(position), maj, millis);
	}
	
	/** Permet de simuler un clique droit avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code rightClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void rightClick(PointD position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		rightClick(B4D.converter.toPoint(position), maj, 1000);
	}
	
	  /*******************/
	 /** CLIQUE GAUCHE **/
	/*******************/
	
	/** Permet de simuler un clique gauche.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void leftClick(Point position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException {
		Robot robot = new Robot();
		robot.mouseMove((int)position.getX(),(int)position.getY());
			
		if(maj)
			robot.keyPress(KeyEvent.VK_SHIFT);
		robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
		if(maj)	
			robot.keyRelease(KeyEvent.VK_SHIFT);

		B4D.wait.wait(millis);
	}
	
	/** Permet de simuler un clique gauche avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code leftClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void leftClick(Point position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		leftClick(position, maj, 1000);
	}
	
	/** Permet de simuler un clique gauche.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void leftClick(PointF position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException{
		leftClick(B4D.converter.toPoint(position), maj, millis);
	}
	
	/** Permet de simuler un clique gauche avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code leftClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void leftClick(PointF position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		leftClick(B4D.converter.toPoint(position), maj, 1000);
	}
	
	/** Permet de simuler un clique gauche.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void leftClick(PointD position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException{
		leftClick(B4D.converter.toPoint(position), maj, millis);
	}
	
	/** Permet de simuler un clique gauche avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code leftClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void leftClick(PointD position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		leftClick(B4D.converter.toPoint(position), maj, 1000);
	}
	
	  /**************************/
	 /** DOUBLE CLIQUE GAUCHE **/
	/**************************/
	
	/** Permet de simuler un double clique gauche.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void doubleLeftClick(Point position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException {
		leftClick(position, maj, 0);
		leftClick(position, maj, millis);
	}
	
	/** Permet de simuler un clique gauche avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code doubleLeftClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void doubleLeftClick(Point position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		doubleLeftClick(position, maj, 1000);
	}
	
	/** Permet de simuler un double clique gauche.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void doubleLeftClick(PointF position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException{
		doubleLeftClick(B4D.converter.toPoint(position), maj, millis);
	}
	
	/** Permet de simuler un clique gauche avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code doubleLeftClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void doubleLeftClick(PointF position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		doubleLeftClick(B4D.converter.toPoint(position), maj, 1000);
	}
	
	/** Permet de simuler un double clique gauche.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void doubleLeftClick(PointD position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException{
		doubleLeftClick(B4D.converter.toPoint(position), maj, millis);
	}
	
	/** Permet de simuler un clique gauche avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code doubleLeftClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void doubleLeftClick(PointD position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		doubleLeftClick(B4D.converter.toPoint(position), maj, 1000);
	}
	
	  /**************************/
	 /** TRIPLE CLIQUE GAUCHE **/
	/**************************/
	
	/** Permet de simuler un triple clique gauche.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void tripleLeftClick(Point position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException {
		leftClick(position, maj, 0);
		leftClick(position, maj, 0);
		leftClick(position, maj, millis);
	}
	
	/** Permet de simuler un clique gauche avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code tripleLeftClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es simples.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void tripleLeftClick(Point position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		tripleLeftClick(position, maj, 1000);
	}
	
	/** Permet de simuler un triple clique gauche.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void tripleLeftClick(PointF position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException{
		tripleLeftClick(B4D.converter.toPoint(position), maj, millis);
	}
	
	/** Permet de simuler un clique gauche avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code tripleLeftClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es relatives.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void tripleLeftClick(PointF position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		tripleLeftClick(B4D.converter.toPoint(position), maj, 1000);
	}
	
	/** Permet de simuler un triple clique gauche.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void tripleLeftClick(PointD position, boolean maj, int millis) throws AWTException, StopProgramException, CancelProgramException{
		tripleLeftClick(B4D.converter.toPoint(position), maj, millis);
	}
	
	/** Permet de simuler un clique gauche avec un temps d'attente par d�faut de 1 secondes.
	 * Cela est identique � {@code tripleLeftClick(position, 1000)}.
	 * @param position - Position de la souris en coordonn�es du damier de dofus.
	 * @param maj - {@code true} si Shift (Maj) doit �tre enfonc� en m�me temps. Cela peut �tre utiliser pour empiler des actions.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void tripleLeftClick(PointD position, boolean maj) throws AWTException, StopProgramException, CancelProgramException{
		tripleLeftClick(B4D.converter.toPoint(position), maj, 1000);
	}

	  /**********/
	 /** CHAT **/
	/**********/
	
	/** Permet de cliquer dans la barre de chat.
	 * @param millis - Temps d'attente apr�s clique.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void chatClick(int millis) throws AWTException, StopProgramException, CancelProgramException{
		leftClick(configuration.getChatBar(), false, millis);
	}
	
	/** Permet de cliquer dans la barre de chat avec un temps d'attente par d�faut de 500 millisecondes.
	 * Cela est identique � {@code chatClick(500)}.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public void chatClick() throws AWTException, StopProgramException, CancelProgramException{
		chatClick(500);
	}
}
