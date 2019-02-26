package fr.B4D.socket.os;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.B4D.bot.B4DException;

/** La classe {@code OperatingSystem} repr�sente un syst�me d'exploitation.<br><br>
 * Un syst�me d'exploitation est d�finit par un nom et une librairie jpcap.
 */
public abstract class OperatingSystem {
	
	private static final OperatingSystem WINDOWS = new Windows();
	private static final OperatingSystem LINUX = new Linux();
	
	private String name;
	private String library;
	
	/** Constructeur de la classe {@code OperatingSystem}.
	 * @param name - Nom du syst�me d'exploitation.
	 * @param library - Nom de la librairie jpcap utilis�e.
	 */
	public OperatingSystem(String name, String library) {
		String paths = System.getProperty("java.library.path");
		if(!Arrays.asList(paths.split(":")).contains("."))
			System.setProperty("java.library.path", paths + ":.");
		
		this.name = name;
		this.library = library;
	}
	
	/** Retourne le nom du syst�me d'exploitation.
	 * @return Nom du syst�me d'exploitation.
	 */
	public String getName() {
		return name;
	}
	
	/** Retourne le nom de la librairie jpcap correspondant au syst�me d'exploitation.
	 * @return Nom de la librairie jpcap.
	 */
	public String getLibrary() {
		return library;
	}
	
	/** Test si la librairie jpcap existe.
	 * @return {@code true} si la librairie existe, {@code false} sinon.
	 */
	public boolean libraryExists() {
		return new File(library).exists();
	}
	
	/** Permet de r�cup�rer le r�seau actif.
	 * @return Nom du r�seau utilis�.
	 * @throws B4DException Si aucuns des r�seaux n'est actif.
	 */
	public abstract String findActiveDevice() throws B4DException;
	
	/** Retourne le syst�me d'exploitation utilis�.
	 * @return Syst�me d'exploitation utilis�.
	 * @throws B4DException Si le syst�me d'exploitation est inconnu.
	 */
	public static OperatingSystem getCurrent() throws B4DException {
		String OS = System.getProperty("os.name");
		switch(OS) {
			case "Linux":
				return LINUX;
			case "Windows 7":
			case "Windows 8":
			case "Windows 8.1":
			case "Windows 10":
				return WINDOWS;
			default:
				throw new B4DException("Unknow operating system : " + OS);
		}
	}
	
	/** Permet d'ex�cuter une commande dans le terminal.
	 * @param command - Commande � ex�cuter.
	 * @return R�sultat de la commande
	 */
	public static List<String> exec(String command) {
		List<String> lines = new ArrayList<String>();
		try {
			Process proc = Runtime.getRuntime().exec(command);
	        BufferedReader reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
	        String line = "";
	        while((line = reader.readLine()) != null)
	        	lines.add(line);
	        proc.waitFor();
		} catch (IOException | InterruptedException e) {
			//Do nothing
		}
        return lines;
	}
}
