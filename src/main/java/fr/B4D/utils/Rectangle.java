package fr.B4D.utils;

import java.awt.Point;
import java.io.Serializable;

import fr.B4D.bot.B4D;

/** La classe {@code Rectangle} repr�sente une zone de l'�cran.<br><br>
 * Un rectangle est d�fini par un point supp�rieur gauche et un point inf�rieur droit.
 */
public class Rectangle extends java.awt.Rectangle implements Serializable{

	private static final long serialVersionUID = -4191850414544381904L;
	
	/** Constructeur de la classe {@code Rectangle}.
	 * @param topLeftHandCorner - Point supp�rieur gauche du rectangle en coordonn�es simples.
	 * @param bottomRigthHandCorner - Point inf�rieur droit du rectangle en coordonn�es simples.
	 */
	public Rectangle(Point topLeftHandCorner, Point bottomRigthHandCorner) {
		super(topLeftHandCorner.x, topLeftHandCorner.y, bottomRigthHandCorner.x - topLeftHandCorner.x, bottomRigthHandCorner.y - topLeftHandCorner.y);
	}
	
	/** Constructeur de la classe {@code Rectangle}.
	 * @param topLeftHandCorner - Point supp�rieur gauche du rectangle en coordonn�es relatives.
	 * @param bottomRigthHandCorner - Point inf�rieur droit du rectangle en coordonn�es relatives.
	 */
	public Rectangle(PointF topLeftHandCorner, PointF bottomRigthHandCorner) {
		this(B4D.converter.toPoint(topLeftHandCorner), B4D.converter.toPoint(bottomRigthHandCorner));
	}
}
