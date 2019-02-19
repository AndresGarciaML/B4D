package fr.B4D.dao;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.filechooser.FileNameExtensionFilter;

/** La classe {@code DAO<T>} est une classe abstraite.<br><br>
 * Elle permet pas de g�rer la sauvegarde des instances de la classe g�n�rique.
 */
public abstract class DAO<T> implements Serializable{	

	private static final long serialVersionUID = -1678900024637018459L;
	
	/** Retourne le filtre de l'extension du fichier.
	 * @return Filtre du fichier correspondant � l'extension.
	 */
	public abstract FileNameExtensionFilter getFilter();
	
	/** Permet de r�cup�rer la derni�re instance de la classe.
	 * @return Instance de la classe 
	 * @throws IOException Si impossible de s�rialiser.
	 * @throws ClassNotFoundException Si impossible de d�serialiser.
	 */
	public abstract T find() throws IOException, ClassNotFoundException;
	
	/** Permet de cr�er une nouvelle sauvegarde de l'instance.
	 * @return Instance de l'objet.
	 * @throws IOException Si impossible de s�rialiser.
	 */
	protected abstract T create() throws IOException;
	
	/** Permet de mettre � jour la sauvegarde de l'instance.
	 * @param obj - Nouvelle instance.
	 * @throws IOException Si impossible de s�rialiser.
	 */
	public abstract void update(T obj) throws IOException;
	
	/** Permet de s�rialiser l'instance d'un objet dans un fichier.
	 * @param object - Instance de l'objet � s�rialiser.
	 * @param file - Fichier dans lequel s�rialiser l'instance de l'objet.
	 * @throws IOException Si impossible de s�rialiser.
	 */
	public abstract void serialize(T object, File file)  throws IOException;
	
	/** Permet de d�s�rialiser l'instance d'un objet depuis un fichier.
	 * @param file - Fichier contenant l'instance de l'objet.
	 * @return Instance de l'objet d�serialis�e.
	 * @throws ClassNotFoundException Si impossible de d�serialiser.
	 * @throws IOException Si un probl�me de fichier survient.
	 */
	public abstract T deserialize(File file)  throws IOException, ClassNotFoundException;
}
