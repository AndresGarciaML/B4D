package fr.B4D.google;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

/** La classe {@code GoogleDrive} permet de g�rer un drive grace � l'API drive de google.
 */
public class GoogleDrive {
	
	  /***************/
	 /** CONSTANTS **/
	/***************/
	
    private static final String APPLICATION_NAME = "B4D-program";
 
	  /**************/
	 /** ATRIBUTS **/
	/**************/
    
    private Drive drive;
    private ArrayList<String> idS;
    
	  /*************/
	 /** BUILDER **/
	/*************/

	/** Constructeur de la classe {@code GoogleDrive}. 
     * @param id - Id du r�pertoire parent.
     * @param credentials - Chemin vers le fichier contenant le certificat d'utilisation des API google.
     * @throws IOException Si aucun fichier n'a �t� trouv�.
     * @throws GeneralSecurityException Si probl�mes de s�curit� survient.
     */
    public GoogleDrive(String id, String credentials) throws IOException, GeneralSecurityException {
    	if (id == null)
    			id = "root";
    	
       	this.idS = new ArrayList<String>();
    	this.idS.add(id);
    	
        Credential credential = GoogleAuthorize.authorize(credentials);
    	drive = new Drive.Builder(
    	          GoogleNetHttpTransport.newTrustedTransport(), 
    	          JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME).build();
    }
    
	  /**********/
	 /** LIST **/
	/**********/
    
    /** Liste les fichiers et dossiers du r�pertoire courant.
     * @return Liste des fichiers et dossiers.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
     */
    public List<File> listAll() throws IOException{
    	String query = "'" + getCurrentId() + "' in parents";
    	return drive.files().list().setQ(query).setPageSize(100).execute().getFiles();
    }
    
    /** Liste les fichiers du r�pertoire courant.
     * @return Liste des fichiers.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
     */
    public List<File> listFiles() throws IOException{
    	String query = " mimeType != 'application/vnd.google-apps.folder' and '" + getCurrentId() + "' in parents";
    	return drive.files().list().setQ(query).setPageSize(100).execute().getFiles();
    }
    
    /** Liste les dossiers du r�pertoire courant.
     * @return Liste des dossiers.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
     */
    public List<File> listFolders() throws IOException{
    	String query = " mimeType = 'application/vnd.google-apps.folder' and '" + getCurrentId() + "' in parents";
        return drive.files().list().setQ(query).setPageSize(100).execute().getFiles();
    }
    
	  /************************/
	 /** CREATE/REMOVE FILE **/
	/************************/
    
    /** Cr�er un fichier.
     * @param type - Type du fichier.
     * @param name - Nom du fichier.
     * @return Fichier cr��.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
     */
    public File createFile(String type, String name) throws IOException {
    	File file = new File();
    	file.setName(name);
        file.setParents(Arrays.asList(getCurrentId()));
        file.setMimeType(type);
        return drive.files().create(file).execute();
    }
    
    /** Permet de supprimer un fichier. Ne fai rien si celui-ci n'existe pas.
     * @param fileId - Id du fichier.
     */
    public void removeFile(String fileId) {
    	try {
			drive.files().delete(fileId).execute();
		} catch (IOException e) {
			//Do nothing if the file does not exist
		}
    }
    
	  /*******************/
	 /** MANAGING FILE **/
	/*******************/
    
    /** Permet de copier un fichier.
     * @param fileId - Id du fichier � copier.
     * @param name - Nom de la copie.
     * @return Fichier copi�.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
     */
    public File copyFile(String fileId, String name) throws IOException {
    	File file = new File();
    	file.setName(name);
    	file.setParents(Arrays.asList(getCurrentId()));
        return drive.files().copy(fileId, file).execute();
    }
    
    /** Permet de d�placer un fichier.
     * @param fileId - Id du fichier � d�placer.
     * @param folderId - Id du r�pertoire vers lequel d�placer le fichier.
     * @return Fichier d�plac�.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
     */
    public File moveFile(String fileId, String folderId) throws IOException {
    	File file = drive.files().get(fileId).execute();
    	File newFile = new File();
    	newFile.setName(file.getName());
    	newFile.setParents(Arrays.asList(folderId));
    	File out = drive.files().copy(fileId, newFile).execute();
    	removeFile(file.getId());
    	return out;
    }
    
    /** Permet de renommer un fichier.
     * @param fileId - Id du fichier � renommer.
     * @param name - Nouveau nom du fichier.
     * @return Fichier renomm�.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
     */
    public File renameFile(String fileId, String name) throws IOException {
    	File file = copyFile(fileId, name);
    	removeFile(fileId);
    	return file;
    }
    
	  /**************************/
	 /** UPLOAD/DOWNLOAD FILE **/
	/**************************/
    
    /** Permet d'uploader un fichier vers le drive.
     * @param type - Type du fichier.
     * @param name - Nom du fichier sur le drive.
     * @param _file - Fichier � uploader sur le drive.
     * @return Fichier upload� sur le drive.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
     */
    public File uploadFile(String type, String name, java.io.File _file) throws IOException {
        AbstractInputStreamContent uploadStreamContent = new FileContent(type, _file);
    	File file = new File();
    	file.setName(name);
        file.setParents(Arrays.asList(getCurrentId()));
        file.setMimeType(type);
        return drive.files().create(file, uploadStreamContent).execute();
    }
    
    /** Permet de t�l�charger un fichier depuis le drive.
     * @param fileId - Id du fichier � t�l�charger.
     * @param name - Nom du fichier une fois t�l�charg�.
     * @return Fichier t�l�charg�.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
     */
    public java.io.File downloadFile(String fileId, String name) throws IOException{  	
    	OutputStream outputStream = new ByteArrayOutputStream();
    	drive.files().export(fileId, "text/plain").executeMediaAndDownloadTo(outputStream);
    	return null;
    }
    
	  /************/
	 /** FOLDER **/
	/************/
    	
	/** Permet de cr�er un dossier.
	 * @param name - Nom du dossier.
	 * @return Dossier cr��.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
	 */
	public File createFolder(String name) throws IOException {
		return createFile("application/vnd.google-apps.folder",  name);
	}
	
	/** Permet de supprimer un dossier.
	 * @param fileId - Id du dossier � supprimer.
	 */
	public void removeFolder(String fileId) {
		removeFile(fileId);
	}
	
	  /****************/
	 /** NAVIGATION **/
	/****************/
	
	/** Permet de se d�placer dans un dossier.
	 * @param folderId - Id du dossier dans lequel se d�placer.
     * @throws IOException Si impossible d'�xecuter l'op�ration.
	 */
	public void stepInto(String folderId) throws IOException {
		File folder = drive.files().get(folderId).execute();
		if(folder != null)
			this.idS.add(folder.getId());
	}
	
	/** Permet de revenir un dossier en arri�re.
	 */
	public void stepBack() {
		if(this.idS.size() > 1)
			this.idS.remove(getCurrentId());
	}
	
	  /************/
	 /** STATIC **/
	/************/
	
    /** Permet de r�cup�rer l'id d'un r�pertoire � partir de l'url.
     * @param url - Url du drive.
     * @return Id du dossier parent du drive.
     */
    public static String getIdFromUrl(String url) {
    	int begin = url.indexOf("/folders/");
    	String subUrl = url.substring(begin+9);
    	return subUrl.substring(0, subUrl.indexOf("?"));
    }
    
      /*************/
     /** PRIVATE **/
    /*************/

    /** Permet de r�cup�rer l'id du dossier courant.
     * @return Id du dossier courant.
     */
    private String getCurrentId() {
    	return this.idS.get(idS.size() - 1);
    }
}
