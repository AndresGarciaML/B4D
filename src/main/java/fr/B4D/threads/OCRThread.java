package fr.B4D.threads;

import java.awt.Rectangle;

import fr.B4D.bot.B4D;

/** La classe {@code OCRThread} permet d'attendre qu'une chaine de caract�re soit d�tect�e � l'�cran.
 * Cette classe �tend la classe {@code Thread}.
 */
public class OCRThread extends Thread{
	private Rectangle rectangle;
	private String regex, text;

	/** Constructeur de la classe {@code OCRThread}.
	 * @param rectangle - Zone de recherche de la chaine de caract�re.
	 * @param regex - Expression r�guli�re attendue.
	 */
	public OCRThread(Rectangle rectangle, String regex) {
		this.rectangle = rectangle;
		this.regex = regex;
	}
	
	/** Retourne la chaine de caract�re d�tect� � l'�cran.
	 * @return Chaine de caract�re. {@code null} si l'expr�ssion r�guli�re n'est pas pr�sente dans la chaine.
	 */
	public String getText() {
		return text;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		try{
			String ocr;
			do {
				ocr = B4D.screen.OCR(rectangle);
				Thread.sleep(100);
			}while(!ocr.contains(regex));
			text = ocr;
		}catch (Exception e){
			Thread.currentThread().interrupt();
		}
	}
}
