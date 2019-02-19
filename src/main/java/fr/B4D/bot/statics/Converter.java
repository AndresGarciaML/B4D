package fr.B4D.bot.statics;

import java.awt.Point;

import fr.B4D.bot.Configuration;
import fr.B4D.utils.PointD;
import fr.B4D.utils.PointF;

/** La classe {@code Converter} permet de convertir une position entre diff�rents syst�mes de coordonn�es.<br/><br/>
 * 
 * Coordonn�es simples :<br/>
 * 
 *	&nbsp;_______<br/>
 *	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Y<br/>
 *	|<br/>
 *	|&nbsp;&nbsp;X<br/><br/>
 *
 * Coordonn�es relatives :<br/>
 * 
 *	&nbsp;_______<br/>
 *	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1<br/>
 *	|<br/>
 *	|&nbsp;&nbsp;1<br/><br/>
 * 
 * Coordonn�es du damier de dofus :<br/>
 * 
 *	&nbsp;&nbsp;/&nbsp;X<br/>
 *	/<br/>
 *	\<br/>
 *  &nbsp;&nbsp;\&nbsp;Y<br/><br/>
 * 
 */
public final class Converter {
	
	private static final double Precision = 10000;
    private static final double CaseWidthF = 0.0346588315;
    private static final double CaseHeightF  = 0.0216600199;

    private Configuration configuration;
    
	/*************/
	/** BUILDER **/
	/*************/
    
	/** Constructeur de la classe {@code Converter}. 
     * @param configuration - Configuration actuelle de l'�cran de jeu.
     */
    public Converter(Configuration configuration) {
    	this.configuration = configuration;
    }

      /********************************/
     /** CONVERSION > Proportionnel **/
    /********************************/

    /** Permet de convertir un point en coordonn�es relative.
     * @param point - Point en coordonn�es simple.
     * @return Point en coordonn�es relatives.
     */
    public PointF toPointF(Point point){
    	double X = (point.getX() - configuration.getGameFrame().getX()) / configuration.getGameFrame().getWidth();
    	double Y = (point.getY() - configuration.getGameFrame().getY()) / configuration.getGameFrame().getHeight();
        return new PointF(Math.round(X*Precision)/Precision, Math.round(Y*Precision)/Precision);
    }

    /** Permet de convertir un point en coordonn�es relative.
     * @param point - Point en coordonn�es du damier de dofus.
     * @return Point en coordonn�es relatives.
     */
    public PointF toPointF(PointD point){
    	double X = (point.getX() + point.getY()) * CaseWidthF;
    	double Y = (-point.getX() + point.getY()) * CaseHeightF;
        return new PointF(Math.round(X*Precision)/Precision, Math.round(Y*Precision)/Precision);
    }

      /************************/
     /** CONVERSION > Ecran **/
    /************************/
    
    /** Permet de convertir un point en coordonn�es simples.
     * @param point - Point en coordonn�es relatives.
     * @return Point en coordonn�es simples.
     */
    public Point toPoint(PointF point) {
    	double X = (point.getX() * configuration.getGameFrame().getWidth()) + configuration.getGameFrame().getX();
    	double Y = (point.getY() * configuration.getGameFrame().getHeight()) + configuration.getGameFrame().getY();
        return new Point((int)Math.round(X), (int)Math.round(Y));
    }

    /** Permet de convertir un point en coordonn�es simples.
     * @param point - Point en coordonn�es du damier de dofus.
     * @return Point en coordonn�es simples.
     */
    public Point toPoint(PointD point) {
    	return toPoint(toPointF(point));
    }

    /************************/
   /** CONVERSION > Dofus **/
  /************************/

    /** Permet de convertir un point en coordonn�es du damier de dofus.
     * @param point - Point en coordonn�es simples.
     * @return Point en coordonn�es du damier de dofus.
     */
    public PointD toPointD(Point point) {
    	return toPointD(toPointF(point));
    }

    /** Permet de convertir un point en coordonn�es du damier de dofus.
     * @param point - Point en coordonn�es relatives.
     * @return Point en coordonn�es du damier de dofus.
     */
    public PointD toPointD(PointF point) {
    	double X = (point.getX() / CaseWidthF - point.getY() / CaseHeightF) / 2;
    	double Y = (point.getX() / CaseWidthF + point.getY() / CaseHeightF) / 2;
    	return new PointD((int)Math.round(X), (int)Math.round(Y));
    }    
}
