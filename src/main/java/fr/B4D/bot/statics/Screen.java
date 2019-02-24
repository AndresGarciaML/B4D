package fr.B4D.bot.statics;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import fr.B4D.bot.B4D;
import fr.B4D.bot.Configuration;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;
import fr.B4D.threads.ColorThread;
import fr.B4D.threads.OCRThread;
import fr.B4D.threads.PixelThread;
import fr.B4D.utils.PointD;
import fr.B4D.utils.PointF;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/** La classe {@code Screen} permet d'acc�der � toutes les m�thodes li�s � l'�cran.
 */
public final class Screen {
	
	private Configuration configuration;
    
	/*************/
	/** BUILDER **/
	/*************/
    
	/** Constructeur de la classe {@code Screen}. 
     * @param configuration - Configuration de l'�cran de jeu.
     */
    public Screen(Configuration configuration) {
    	this.configuration = configuration;
    }
	
	  /*******************/
	 /** COULEUR PIXEL **/
	/*******************/
	
	/** Permet de r�cup�rer la couleur d'un pixel.
	 * @param point - Point en coordonn�es simples.
	 * @return Couleur du pixel.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public Color getPixelColor(Point point) throws AWTException   {
		return new Robot().getPixelColor(point.x, point.y);
	}
	
	/** Permet de r�cup�rer la couleur d'un pixel.
	 * @param point - Point en coordonn�es relatives.
	 * @return Couleur du pixel.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public Color getPixelColor(PointF point) throws AWTException {
		return getPixelColor(B4D.converter.toPoint(point));
	}
	
	/** Permet de r�cup�rer la couleur d'un pixel.
	 * @param point - Point en coordonn�es du damier de dofus.
	 * @return Couleur du pixel.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public Color getPixelColor(PointD point) throws AWTException {
		return getPixelColor(B4D.converter.toPoint(point));
	}
	
	  /*********************/
	 /** RECHERCHE PIXEL **/
	/*********************/
	
	/** Permet de rechercher un pixel correspondant � un certain crit�re parmis une plage de pixels.
	 * @param topLeftHandCorner - Point supp�rieur gauche du rectangle de recherche en coordonn�es relatives.
	 * @param bottomRightHandCorner - Point inf�rieur droit du rectangle de recherche en coordonn�es relatives.
	 * @param min - Couleur minimum.
	 * @param max - Couleur maximum.
	 * @return Position du pixel en coordonn�es relatives.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public PointF searchPixel(PointF topLeftHandCorner, PointF bottomRightHandCorner, Color min, Color max) throws AWTException {
		Point Point1 = B4D.converter.toPoint(topLeftHandCorner);
		Point Point2 = B4D.converter.toPoint(bottomRightHandCorner);
		
		Color color;
		Point point;
		
		for(int y=Point1.y;y<=Point2.y;y++) {
			for(int x=Point1.x;x<=Point2.x;x++) {
				point = new Point(x, y);
				color = getPixelColor(point);
				if(isBetween(color, min, max))
					return B4D.converter.toPointF(point);
			}
		}
		return null;
	}
	
	/** Permet de rechercher tous les pixels correspondants � un certain crit�re parmis une plage de pixels.
	 * @param topLeftHandCorner - Point supp�rieur gauche du rectangle de recherche en coordonn�es relatives.
	 * @param bottomRightHandCorner - Point inf�rieur droit du rectangle de recherche en coordonn�es relatives.
	 * @param min - Couleur minimum.
	 * @param max - Couleur maximum.
	 * @return Liste des pixels en coordonn�es relatives correspondants au crit�re de recherche.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public ArrayList<PointF> searchPixels(PointF topLeftHandCorner, PointF bottomRightHandCorner, Color min, Color max) throws AWTException {
		
		Point Point1 = B4D.converter.toPoint(topLeftHandCorner);
		Point Point2 = B4D.converter.toPoint(bottomRightHandCorner);
		ArrayList<PointF> points = new ArrayList<PointF>();
		
		Color color;
		Point point;
		
		for(int y=Point1.y;y<=Point2.y;y++) {
			for(int x=Point1.x;x<=Point2.x;x++) {
				point = new Point(x, y);
				color = getPixelColor(point);
				
				if(isBetween(color, min, max))
					points.add(B4D.converter.toPointF(point));
			}
		}
		if (points.isEmpty())
			points = null;
		return points;
	}

	  /****************/
	 /** SCREENSHOT **/
	/****************/
	
	/** Permet de faire une capture d'�crans d'une zone pr�cise.
	 * @param rectangle - Zone � capturer.
	 * @return Image de la zone captur�e.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public BufferedImage takeSreenshot(Rectangle rectangle) throws AWTException {
		return new Robot().createScreenCapture(rectangle);
	}
	
	/** Permet de faire une capture d'�crans de la zone de jeu.
	 * @return Image de la zone de jeu.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public BufferedImage takeSreenshot() throws AWTException {
		return new Robot().createScreenCapture(configuration.getGameFrame());
	}
	
	  /*********/
	 /** OCR **/
	/*********/
	
	/** Permet de faire une reconnaissance optique de caract�re sur une zone pr�cise de l'�cran.
	 * @param rectangle - Zone de l'�cran.
	 * @return Chaine de caract�re identifi�e dans la zone, {@code null} si rien n'a �t� trouv�.
	 * @throws TesseractException Si impossible de r�aliser l'OCR.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public String OCR(Rectangle rectangle) throws AWTException, TesseractException {
		BufferedImage image = takeSreenshot(rectangle);
		Tesseract tessInst = new Tesseract();
		tessInst.setLanguage("fra");
		String out = tessInst.doOCR(image);
		return out.replaceAll("\n", " ");
	}
	
	/** Permet de faire une reconnaissance optique de caract�re sur une zone pr�cise de l'�cran.
	 * @param topLeftHandCorner - Point supp�rieur gauche de la zone en coordonn�es simples.
	 * @param bottomRightHandCorner - Point inf�rieur droit de la zone en coordonn�es simples.
	 * @return Chaine de caract�re identifi�e dans la zone, {@code null} si rien n'a �t� trouv�.
	 * @throws TesseractException Si impossible de r�aliser l'OCR.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public String OCR(Point topLeftHandCorner, Point bottomRightHandCorner) throws AWTException, TesseractException {
		return OCR(new Rectangle(topLeftHandCorner.x,  topLeftHandCorner.y, bottomRightHandCorner.x - topLeftHandCorner.x, bottomRightHandCorner.y - topLeftHandCorner.y));
	}
	
	/** Permet de faire une reconnaissance optique de caract�re sur une zone pr�cise de l'�cran.
	 * @param topLeftHandCorner - Point supp�rieur gauche de la zone en coordonn�es relatives.
	 * @param bottomRightHandCorner - Point inf�rieur droit de la zone en coordonn�es relatives.
	 * @return Chaine de caract�re identifi�e dans la zone, {@code null} si rien n'a �t� trouv�.
	 * @throws TesseractException Si impossible de r�aliser l'OCR.
	 * @throws AWTException Si un probl�me d'�cran survient.
	 */
	public String OCR(PointF topLeftHandCorner, PointF bottomRightHandCorner) throws AWTException, TesseractException {
		return OCR(B4D.converter.toPoint(topLeftHandCorner), B4D.converter.toPoint(bottomRightHandCorner));
	}
	
	  /***************/
	 /** SELECTION **/
	/***************/

	/** Permet de r�cup�rer une chaine de caract�re s�l�ctionn�e en faisant un double clique et Ctrl+C.
	 * @param point - Position de la s�lection en coordonn�es simples.
	 * @return Chaine de caract�re repr�sentant la s�lection. {@code null} si rien n'a �t� s�lectionn�e o� si la s�lection est vide.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public String getSelection(Point point) throws AWTException, StopProgramException, CancelProgramException {
		Robot robot = new Robot();
		B4D.mouse.doubleLeftClick(point, false, 100);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		B4D.wait.wait(1000);
		return B4D.keyboard.getClipboard();
	}
	
	/** Permet de r�cup�rer une chaine de caract�re s�l�ctionn�e en faisant un double clique et Ctrl+C.
	 * @param position - Position de la s�lection en coordonn�es relatives.
	 * @return Chaine de caract�re repr�sentant la s�lection. {@code null} si rien n'a �t� s�lectionn�e o� si la s�lection est vide.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public String getSelection(PointF position) throws AWTException, StopProgramException, CancelProgramException {
		return getSelection(B4D.converter.toPoint(position));
	}
	
	  /************/
	 /** OTHERS **/
	/************/
	
	/** Permet de savoir si une couleur se trouve dans un intervale.
	 * @param couleur - Couleur du pixel.
	 * @param min - Couleur minimale.
	 * @param max - Couleur maximale.
	 * @return {@code true} si la couleur est dans l'intervale, {@code false} sinon.
	 */
	public boolean isBetween(Color couleur, Color min, Color max) {
		return (min.getRed() <= couleur.getRed() && couleur.getRed() <= max.getRed() && min.getGreen() <= couleur.getGreen() && couleur.getGreen() <= max.getGreen() && min.getBlue() <= couleur.getBlue() && couleur.getBlue() <= max.getBlue());
	}

	/** Permet donner le focus � la fen�tre de jeu.
	 */
	public void focusDofus() {
		// TODO Auto-generated method stub
	}
	
	  /*********************/
	 /** ATTENTE SUR MAP **/
	/*********************/
	
	/** Permet d'attendre l'appui sur une touche.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return Entier repr�sentant la touche enfonc�e. {@code -1} si timeout.
	 */
	
	/** Permet d'attendre un changement de map.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return {@code true} si le joueur � chang� de map, {@code false} si timeout.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	public boolean waitForMap(int timeOut) throws StopProgramException, CancelProgramException {
		return waitForChangingPixel(B4D.converter.toPointF(configuration.getMinimap()), timeOut) != null;
	}
	
	/** Permet d'attendre un changement de map sans limite de temps. 
	 * Cela est identique � {@code waitForMap(0)}.
	 * @return {@code true} si le joueur � chang� de map, {@code false} si timeout.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	public boolean waitForMap() throws StopProgramException, CancelProgramException {
		return waitForMap(0);
	}
	
	  /*********************/
	 /** ATTENTE SUR OCR **/
	/*********************/
	
	/** Permet d'attendre qu'une chaine de caract�re soit d�tect�e � l'�cran.
	 * @param rectangle - Zone de l'�cran.
	 * @param regex - Expr�sion r�guli�re que doit contenir la chaine de caract�re.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return Chaine de caract�re identifi�e dans la zone, {@code null} si timeout.
	 */
	public String waitForOCR(Rectangle rectangle, String regex, int timeOut) {
		OCRThread ocrThread = new OCRThread(rectangle, regex);
		ocrThread.start();
		try {
			ocrThread.join(timeOut);
		} catch (InterruptedException e) {
			B4D.logger.error(e);
		}
		
		if(ocrThread.isAlive())
			ocrThread.interrupt();
		return ocrThread.getText();
	}

	/** Permet d'attendre qu'une chaine de caract�re soit d�tect�e � l'�cran.
	 * @param topLeftHandCorner - Point supp�rieur gauche de la zone en coordonn�es simples.
	 * @param bottomRightHandCorner - Point inf�rieur droit de la zone en coordonn�es simples.
	 * @param regex - Expr�sion r�guli�re que doit contenir la chaine de caract�re.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return Chaine de caract�re identifi�e dans la zone, {@code null} si timeout.
	 */
	public String waitForOCR(Point topLeftHandCorner, Point bottomRightHandCorner, String regex, int timeOut) {
		return waitForOCR(new Rectangle(topLeftHandCorner.x,  topLeftHandCorner.y, bottomRightHandCorner.x - topLeftHandCorner.x, bottomRightHandCorner.y - topLeftHandCorner.y), regex, timeOut);
	}
	
	/** Permet d'attendre qu'une chaine de caract�re soit d�tect�e � l'�cran.
	 * @param topLeftHandCorner - Point supp�rieur gauche de la zone en coordonn�es relatives.
	 * @param bottomRightHandCorner - Point inf�rieur droit de la zone en coordonn�es relatives.
	 * @param regex - Expr�sion r�guli�re que doit contenir la chaine de caract�re.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return Chaine de caract�re identifi�e dans la zone, {@code null} si timeout.
	 */
	public String waitForOCR(PointF topLeftHandCorner, PointF bottomRightHandCorner, String regex, int timeOut) {
		return waitForOCR(B4D.converter.toPoint(topLeftHandCorner), B4D.converter.toPoint(bottomRightHandCorner), regex, timeOut);
	}
	
	  /*************************************/
	 /** ATTENTE SUR CHANGEMENT DE PIXEL **/
	/*************************************/
	
	/** Permet d'attendre qu'un pixel change de couleur.
	 * @param point - Position du pixel en coordonn�es simples.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return Nouvelle couleur du pixel, {@code null} si timeout.
	 */
	public Color waitForChangingPixel(Point point, int timeOut) {
		PixelThread pixelThread = new PixelThread(point);
		pixelThread.start();
		try {
			pixelThread.join(timeOut);
		} catch (InterruptedException e) {
			B4D.logger.error(e);
		}
		
		if(pixelThread.isAlive())
			pixelThread.interrupt();
		return pixelThread.getColor();
	}
	
	/** Permet d'attendre qu'un pixel change de couleur.
	 * @param point - Position du pixel en coordonn�es relatives.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return Nouvelle couleur du pixel, {@code null} si timeout.
	 */
	public Color waitForChangingPixel(PointF point, int timeOut) {
		return waitForChangingPixel(B4D.converter.toPoint(point), timeOut);
	}
	
	  /**********************************/
	 /** ATTENTE SUR COULEUR DE PIXEL **/
	/**********************************/
	
	/** Permet d'attendre qu'un pixel soit compris dans un intervale de couleur.
	 * @param point - Position du pixel en coordonn�es simples.
	 * @param min - Couleur minimale.
	 * @param max - Couleur maximale.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return Couleur du pixel, {@code null} si timeout.
	 */
	public Color waitForColor(Point point, Color min, Color max, int timeOut) {
		ColorThread colorThread = new ColorThread(point, min, max);
		colorThread.start();
		try {
			colorThread.join(timeOut);
		} catch (InterruptedException e) {
			B4D.logger.error(e);
		}
		
		if(colorThread.isAlive())
			colorThread.interrupt();
		return colorThread.getColor();
	}
	
	/** Permet d'attendre qu'un pixel soit compris dans un intervale de couleur.
	 * @param point - Position du pixel en coordonn�es relatives.
	 * @param min - Couleur minimale.
	 * @param max - Couleur maximale.
	 * @param timeOut - Temps d'attente avant timeout en millisecondes.
	 * @return Couleur du pixel, {@code null} si timeout.
	 */
	public Color waitForColor(PointF point, Color min, Color max, int timeOut) {
		return waitForColor(B4D.converter.toPoint(point), min, max, timeOut);
	}
}
