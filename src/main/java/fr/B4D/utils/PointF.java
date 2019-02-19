package fr.B4D.utils;

import java.io.Serializable;

/** La classe {@code PointF} repr�sente un point en coordonn�e relative.<br><br>
 * 
 *	&nbsp;_______<br>
 *	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1<br>
 *	|<br>
 *	|&nbsp;&nbsp;1<br><br>
 *
 */
public class PointF extends java.awt.geom.Point2D.Double implements Serializable{

	private static final long serialVersionUID = -8987300386100923141L;
	
	/** Constructeur de la classe {@code PointF} en (0, 0).
     */
	public PointF() {
		super();
	}
	
	/** Constructeur de la classe {@code PointF} en (x, y)
	 * @param x - Coordonn�e x du point.
	 * @param y - Coordonn�e y du point.
	 */
	public PointF(double x, double y) {
		super(x,y);
	}
}
