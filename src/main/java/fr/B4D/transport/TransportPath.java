package fr.B4D.transport;

import java.io.Serializable;
import java.util.List;

import fr.B4D.bot.B4D;
import fr.B4D.bot.B4DException;
import fr.B4D.bot.Person;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;

/** La classe {@code TransportPath} repr�sente un chemin entre deux point de la carte.<br><br>
 * Un chemin est d�fini par une liste d'�tapes.
 */
public class TransportPath implements Serializable{
	
	private static final long serialVersionUID = 6035526052301664166L;
	
	private List<TransportStep> transportPath;
	
	  /******************/
	 /** CONSTRUCTEUR **/
	/******************/
	
	/** Constructeur de la classe {@code TransportPath}. 
	 * @param shortestPath - Liste d'�tapes.
	 */
	public TransportPath(List<TransportStep> shortestPath) {
		this.transportPath = shortestPath;
	}
	
	  /**************/
	 /** METHODES **/
	/**************/
	
	/** Permet d'utiliser le chemin en empruntant tous les transports du chemin.
	 * @param person - Joueur utilisant le chemin.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le bot programme est annul�.
	 * @throws B4DException Si une exception de type B4D est lev�e.
	 */
	public void use(Person person) throws StopProgramException, CancelProgramException, B4DException {
		for(TransportStep step:transportPath) {
			if (!person.getPosition().equals(step.getTransport().getPosition()))
				throw new B4DException("Wrong position.");
			step.use();
			B4D.screen.waitForMap();
			person.setPosition(step.getDestination());
		}
	}
	
	/** Retourne le poid total du chemin.
	 * @return Poid totale du chemin.
	 */
	public double getWeigth() {
		return transportPath.stream().mapToDouble(t -> t.getTransport().getWeight()).sum();
	}
	
	  /**************/
	 /** TOSTRING **/
	/**************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String out = "\nFrom " +  transportPath.get(0).getTransport().getPosition() + " to " + transportPath.get(transportPath.size()-1).getDestination() + "\n";
		out += "Total weight = " + getWeigth() + " (" + transportPath.size() + " steps)\n";
		for(TransportStep step : transportPath)
			out += step + "\n";
		return out;
	}
}
