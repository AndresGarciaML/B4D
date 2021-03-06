package fr.B4D.transport.transports;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import fr.B4D.bot.B4D;
import fr.B4D.bot.B4DException;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;
import fr.B4D.transport.Transport;
import fr.B4D.utils.PointF;

/** La classe {@code Zaap} représente un zaap.<br><br>
 * Cette classe étend la classe {@code Transport}.
 */
public class Zaap extends Transport implements Serializable{
	
	private static final long serialVersionUID = -1582072889545306350L;
	
	  /****************/
	 /** COLLECTION **/
	/****************/
	
	public final static Zaap Amakna_Foret_Malefique = new Zaap("Amakna (Bord de la foret malefique)", new Point(-1, 13), new PointF(0.4052, 0.4068));
    public final static Zaap Amakna_Chateau = new Zaap("Amakna (Chateau d'Amakna)", new Point(3, -5), new PointF(0.5024, 0.3426));
    public final static Zaap Amakna_Coin_Bouftous = new Zaap("Amakna (Coin des Bouftous)", new Point(5, 7), new PointF(0.6806, 0.3437));
    public final static Zaap Amakna_Montagne_Craqueleurs = new Zaap("Amakna (Montagne des Craqueleurs)", new Point(-5, -8), new PointF(0.7167, 0.1663));
    public final static Zaap Amakna_Plaines_Scarafeuilles = new Zaap("Amakna (Plaine des Scarafeuilles)", new Point(-1, 24), new PointF(0.2576, 0.509));
    public final static Zaap Amakna_Port_Madrestam = new Zaap("Amakna (Port de Madrestam)", new Point(7, -4), new PointF(0.6027, 0.2234));
    public final static Zaap Amakna_Village = new Zaap("Amakna (Village d'Amakna)", new Point(-2, 0), new PointF(0.6798, 0.2325));
    public final static Zaap Astrub = new Zaap("Astrub (Cite d'astrub)", new Point(5, -18), new PointF(0.6056, 0.3104));
    public final static Zaap Sufokia_Rivage = new Zaap("Baie de Sufokia (Rivage sufokien)", new Point(10, 22), new PointF(0.5401, 0.3377));
    public final static Zaap Sufokia_Village = new Zaap("Baie de Sufokia (Sufokia)", new Point(13, 26), new PointF(0.7376, 0.2896));
    public final static Zaap Sufokia_Temple_Alliances = new Zaap("Baie de Sufokia (Temple des alliances)", new Point(13, 35), new PointF(0.8122, 0.1162));
    public final static Zaap Bonta = new Zaap("Bonta (Centre-ville)", new Point(-32, -56), new PointF(0.4406, 0.3257));
    public final static Zaap Brakmar = new Zaap("Brakmar (Centre-ville)", new Point(-26, 35), new PointF(0.5323741, 0.260479));
    public final static Zaap Otomai_Village_Cotier = new Zaap("Ile d'Otomai (Village cotier)", new Point(-46, 18), new PointF(0.4647, 0.3437));
    public final static Zaap Otomai_Village_Canopee = new Zaap("Ile d'Otomai (Village de la Canopee)", new Point(-54, 16), new PointF(0.496, 0.4228));
    public final static Zaap Frigost_Bourgade = new Zaap("Ile de Frigost (La Bourgade)", new Point(-78, -41), new PointF(0.4848, 0.3367));
    public final static Zaap Frigost_Village_Enseveli = new Zaap("Ile de Frigost (Village enseveli)", new Point(-77, -73), new PointF(0.3636, 0.5771));
    public final static Zaap Moon_Plage_Tortue = new Zaap("Ile de Moon (Plage de la Tortue)", new Point(35, 12), new PointF(0.1549, 0.1222));
    public final static Zaap Wabbits_Cawotte = new Zaap("Ile des Wabbits (Ile de la Cawotte)", new Point(25, -4), new PointF(0.626, 0.4509));
    public final static Zaap Landres_Sidimote = new Zaap("Landres de Sidimote (Route des Roulottes)", new Point(-25, 12), new PointF(0.175, 0.497));
    public final static Zaap Montagne_Koalaks = new Zaap("Montagne des Koalaks (Village des Eleveurs)", new Point(-16, 1), new PointF(0.4888, 0.3667));
    public final static Zaap Pandala = new Zaap("Pandala Neutre (Faubourgs de Pandala)", new Point(26, -37), new PointF(0.2006, 0.2455));
    public final static Zaap Cania_Champs_Cania = new Zaap("Plaine de Cania (Champs de Cania)", new Point(-27, -36), new PointF(0.3949, 0.4089));
    public final static Zaap Cania_Lac_Cania = new Zaap("Plaine de Cania (Lac de Cania)", new Point(-3, -42), new PointF(0.7504, 0.2234));
    public final static Zaap Cania_Massif_Cania = new Zaap("Plaine de Cania (Massif de Cania)", new Point(-13, -28), new PointF(0.6404, 0.3988));
    public final static Zaap Cania_Plaine_Porkass = new Zaap("Plaine de Cania (Plaine des Porkass)", new Point(-5, -23), new PointF(0.3258, 0.3266));
    public final static Zaap Cania_Plaines_Rocheuses = new Zaap("Plaine de Cania (Plaines Rocheuses)", new Point(-17, -47), new PointF(0.3234, 0.3337));
    public final static Zaap Cania_Routes_Rocailleuses = new Zaap("Plaine de Cania (Routes Rocailleuses)", new Point(-20, -20), new PointF(0.4302, 0.3537));
    public final static Zaap Saharach = new Zaap("Saharach (Dunes des ossements)", new Point(15, -58), new PointF(0.3595, 0.3076));
    public final static Zaap Tainela = new Zaap("Tainéla (Berceau)", new Point(1, -32), new PointF(0.4992, 0.3978));    
    
	  /******************/
	 /** CONSTRUCTEUR **/
	/******************/
	
	/** Constructeur de la classe {@code Zaap}.
	 * @param name - Nom du zaap.
	 * @param position - Position du zaap sur la carte.
	 * @param positionF - Position relative du zaap.
	 */
	public Zaap(String name, Point position, PointF positionF) {
		super(name, position, positionF, zaapCost);
	}
	
	  /************************/
	 /** METHODES STATIQUES **/
	/************************/
	
    /** Retourne la liste de tous les zaaps.
     * @return Liste de tous les zaaps.
     */
    public final static ArrayList<Transport> getAll(){
    	ArrayList<Transport> zaaps = new ArrayList<Transport>();
        zaaps.add(Amakna_Foret_Malefique);
        zaaps.add(Amakna_Chateau);
        zaaps.add(Amakna_Coin_Bouftous);
        zaaps.add(Amakna_Montagne_Craqueleurs);
        zaaps.add(Amakna_Plaines_Scarafeuilles);
        zaaps.add(Amakna_Port_Madrestam);
        zaaps.add(Amakna_Village);
        zaaps.add(Astrub);
        zaaps.add(Sufokia_Rivage);
        zaaps.add(Sufokia_Village);
        zaaps.add(Sufokia_Temple_Alliances);
        zaaps.add(Bonta);
        zaaps.add(Brakmar);
        zaaps.add(Otomai_Village_Cotier);
        zaaps.add(Otomai_Village_Canopee);
        zaaps.add(Frigost_Bourgade);
        zaaps.add(Frigost_Village_Enseveli);
        zaaps.add(Moon_Plage_Tortue);
        zaaps.add(Wabbits_Cawotte);
        zaaps.add(Landres_Sidimote);
        zaaps.add(Montagne_Koalaks);
        zaaps.add(Pandala);
        zaaps.add(Cania_Champs_Cania);
        zaaps.add(Cania_Lac_Cania);
        zaaps.add(Cania_Massif_Cania);
        zaaps.add(Cania_Plaine_Porkass);
        zaaps.add(Cania_Plaines_Rocheuses);
        zaaps.add(Cania_Routes_Rocailleuses);
        zaaps.add(Saharach);
        zaaps.add(Tainela);
        return zaaps;
    }
    
    /** Permet de retrouver un zaap à partir de sa position.
     * @param position - Position du zaap.
     * @return Zaap correspondant.
     * @throws B4DException Si aucun zaap ne correspond à cette position.
     */
    public final static Zaap getZaap(Point position) throws B4DException{
    	for(Transport zaap: getAll()) {
			if(zaap.getPosition().equals(position))
				return (Zaap) zaap;
		}
		throw new B4DException("Cannot found the zaap in position [" + position.x + ":" + position.y + "] on the map.");
    }
	
    /** Permet de retrouver un zaap à partir de son nom.
     * @param name - Nom du zaap.
     * @return Zaap correspondant.
     * @throws B4DException Si aucun zaap ne possède ce nom.
     */
	public static Zaap getZaap(String name) throws B4DException {
		for(Transport zaap: getAll()) {
			if(zaap.getName().equals(name))
				return (Zaap) zaap;
		}
		throw new B4DException("Cannot found the zaap \"" + name + "\" on the map.");
	}
	
	  /**************/
	 /** METHODES **/
	/**************/
	
	/* (non-Javadoc)
	 * @see fr.B4D.transport.TransportInterface#goTo(java.awt.Point)
	 */
	public void goTo(Point destination) throws StopProgramException, CancelProgramException, B4DException {		
		B4D.mouse.leftClick(super.getPositionF(), false);
		B4D.screen.waitForColor(new PointF(0.4432, 0.7365), new Color(170, 200, 0), new Color(210, 255, 50), 10000);
		B4D.mouse.leftClick(new PointF(0.6632,0.2006), false, 200);
		B4D.keyboard.writeKeyboard(getZaap(destination).getName());
		B4D.mouse.doubleLeftClick(new PointF(0.46,0.2884), false);
	    B4D.screen.waitForMap(20000);
	}
}
