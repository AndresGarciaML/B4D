package fr.B4D.classes;

public final class Bot {
	public static Configuration MyConfiguration = new Configuration();		//Cr�er la variable de type onfiguration
    public static Programme MyProgramme;                            	  	//Cr�er la variable de type Programme
    //public static Monde MonMonde = new Monde();                           //Cr�er la variable de type Monde

    //public final ThreadClavier As New Thread(AddressOf Detection_Clavier)	//Cr�er le thread pour la detection des touches
    //public final ThreadProgramme As New Thread(AddressOf Programme_Test)  //Cr�er le thread pour le programme

    public static final String AdminMacAdresse = "24-0A-64-51-AB-D5";

    public static final Serialization ConfigurationSerialization = new Serialization("B4D", "Configuration.B4D");
}
