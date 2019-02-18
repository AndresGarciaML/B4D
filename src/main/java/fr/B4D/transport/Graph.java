package fr.B4D.transport;

import java.awt.Point;
import java.io.Serializable;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import fr.B4D.transport.transports.Walk;

/** La classe {@code Graph} repr�sente la carte du monde de dofus.<br/>
 * Un graph est repr�sent� par des noeuds (vertex) et des ar�tes (edges).
 * Les diff�rents moyens de transport sont mod�lis�s par les ar�tes.
 */
public class Graph implements Serializable{

	private static final long serialVersionUID = -2532108486622081288L;
	
	private DirectedWeightedPseudograph<Point, TransportStep> graph;
	
	  /*************/
	 /** BUILDER **/
	/*************/
	
	/** Constructeur de la classe {@code Graph}. 
	 */
	public Graph() {
		graph = new DirectedWeightedPseudograph<Point, TransportStep>(TransportStep.class);
	}
	
	  /************/
	 /** VERTEX **/
	/************/
	
	/** Permet d'ajouter un noeud au graph.
	 * @param vertex - Coordonn�e du noeud.
	 * @param autoConnect - {@code true} pour connecter automatiquement le noeud � ces voisins, {@code false} sinon.  
	 */
	public void addVertex(Point vertex, Boolean autoConnect) {
		this.graph.addVertex(vertex);
		
		if(autoConnect) {
			Point up = new Point(vertex.x,vertex.y - 1);
			Point down = new Point(vertex.x,vertex.y + 1);
			Point right = new Point(vertex.x + 1,vertex.y);
			Point left = new Point(vertex.x - 1,vertex.y);
			
			if(graph.containsVertex(up)) {
				addEdge(new TransportStep(new Walk(vertex), up));
				addEdge(new TransportStep(new Walk(up), vertex));
			}
			if(graph.containsVertex(down)) {
				addEdge(new TransportStep(new Walk(vertex), down));
				addEdge(new TransportStep(new Walk(down), vertex));
			}
			if(graph.containsVertex(right)) {
				addEdge(new TransportStep(new Walk(vertex), right));
				addEdge(new TransportStep(new Walk(right), vertex));
			}
			if(graph.containsVertex(left)) {
				addEdge(new TransportStep(new Walk(vertex), left));
				addEdge(new TransportStep(new Walk(left), vertex));
			}
		}
	}
	
	/** Permet de retirer un noeud du graph ainsi que toutes les ar�tes le reliant.
	 * Ne fait rien si le noeud n'existe pas.
	 * @param vertex - Coordonn�es du noeud.
	 */
	public void removeVertex(Point vertex) {
		graph.removeVertex(vertex);
	}
	
	  /*******************/
	 /** METHODES EDGE **/
	/*******************/
	
	/** Permet d'ajouter une ar�te au graph.
	 * Les ar�tes sont repr�sent�es par la classe {@code TransportStep}.
	 * @param transportStep - Ar�te reliant deux vertex.
	 */
	public void addEdge(TransportStep transportStep) {
		graph.addEdge(transportStep.getTransport().getPosition(), transportStep.getDestination(), transportStep);
		graph.setEdgeWeight(transportStep, transportStep.getTransport().getWeight());
	}
	
	/** Permet de retirer une ar�te du graph. Cette fonction n'est pas symetrique : A vers B sera supprim� mais pas B vers A.
	 * @param transportStep - Ar�te reliant deux vertex.
	 */
	public void removeEdge(TransportStep transportStep) {
		removeEdge(transportStep.getTransport().getPosition(), transportStep.getDestination(), false);
	}
	
	/** Permet de retirer une ar�te du graph.
	 * @param origin - Point de d�part de l'ar�te.
	 * @param destination - Point d'arriv�e de l'ar�te.
	 * @param symetric - {@code true} pour relier sym�triquement (A vers B et B vers A), {@code false} sinon.  
	 */
	public void removeEdge(Point origin, Point destination, boolean symetric) {
		graph.removeEdge(origin, destination);
		if(symetric)
			graph.removeEdge(destination, origin);
	}
	
	
	  /**************/
	 /** METHODES **/
	/**************/
	
	/** Permet de trouver le plus court chemin entre deux points de la map en utilisant tous les moyens de transport possible.
	 * @param source - Point de d�part.
	 * @param target - Point d'arriv�.
	 * @return Chemin le plus court entre le point de d�part et le point d'arriv�.
	 */
	public GraphPath<Point, TransportStep> getPath(Point source, Point target) {
		return new DijkstraShortestPath<Point, TransportStep>(graph).getPath(source, target);
	}
}
