package generateurtankem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class Generateur {
    static final String CONSONNES = "qwrtpsdfghjklzxcvbnm";
    static final String VOYELLES = "eyuioa";
    static final String CHIFFRES = "0123456789";
    static Random random = new Random(); 


    //Le username sera de 6 à 11 caractères + 1 chiffre. 
    //On alternera les voyelles et chiffres pour des meilleurs noms. 
    public String genererUsername()
    {
    	String leNom="";
    	Random ran = new Random();
    	int nbRand = ran.nextInt(7) + 5;
    	
    	for (int i = 0;i<nbRand ;i++)
    		if ((i & 1) == 0 )
    			leNom+=CONSONNES.charAt(random.nextInt(CONSONNES.length()));
    		else
    			leNom+=VOYELLES.charAt(random.nextInt(VOYELLES.length()));
        
    	leNom+=CHIFFRES.charAt(random.nextInt(CHIFFRES.length()));
    	return leNom;
    }
    //Generer un nom normal.
    public String genererNom()
    {
    	String leNom="";
    	Random ran = new Random();
    	int nbRand = ran.nextInt(7) + 3;
    	
    	for (int i = 0;i<nbRand ;i++)
    		if ((i & 1) == 0 )
    			leNom+=VOYELLES.charAt(random.nextInt(VOYELLES.length()));
    		else
    			leNom+=CONSONNES.charAt(random.nextInt(CONSONNES.length()));
    	return leNom;
    }
    
    //Pour generer un nom plus cool.
    public String genererNomNiveau()
    {
    	String leNom="";
    	Random ran = new Random();
    	int nbRand = ran.nextInt(10) + 5;
    	int nbRandPetit = ran.nextInt(3) + 2;

    	for (int i = 0;i<nbRandPetit ;i++)
    		if ((i & 1) == 0 )
    			leNom+=CONSONNES.charAt(random.nextInt(CONSONNES.length()));
    		else
    			leNom+=VOYELLES.charAt(random.nextInt(VOYELLES.length()));
        
    	leNom+=" ";
    	for (int i = 0;i<nbRand ;i++)
    		if ((i & 1) == 0 )
    			leNom+=CONSONNES.charAt(random.nextInt(CONSONNES.length()));
    		else
    			leNom+=VOYELLES.charAt(random.nextInt(VOYELLES.length()));
    	
    	return leNom;
    }
    
    //Methode qui genere un nombre entre le min defini, et le max defini.
   public int genererNombre(int min, int max)
   {
	   int leInt = random.nextInt((max - min) + 1) + min;
	   return leInt;
   }
   //Methode pour generer un niveau.
   public Niveau genererUnNiveau()
   {
	    Date date = new Date();
	    
	    String nom = genererNomNiveau();
	    int tempsMin = genererNombre(3, 14);
	    int tempsMax = genererNombre(15,30);
	    int status = genererNombre(1,4);
	    int grosseurX = genererNombre(5,11);
	    int grosseurY = genererNombre(5,11);
	   
	   Niveau unNiveau = new Niveau(nom,tempsMin,tempsMax, date, status, grosseurX, grosseurY);
	   return unNiveau;
   }
   public Case genererUneCase(int idNiveau, int posX, int posY) //1 2 ou 3 
   {
	    int unIdNiveau = idNiveau;
	    int positionX =posX;
	    int positionY = posY;
	    int type = genererNombre(1,3);
	   Case uneCase = new Case(unIdNiveau,positionX,positionY,type);
	   return uneCase;
   }
   public Tank genererUnTank(int idNiveau, int posX, int posY) //1 2 ou 3 
   {
	    int unIdNiveau = idNiveau;
	    int positionX =posX;
	    int positionY = posY;
	    Tank unTank = new Tank(posX,posY);
	   return unTank;
   } 
   
   public Utilisateur genererUser()
   {
		String username = genererUsername();
		String nom = genererNom();
		String prenom = genererNom();
		String mot_de_passe = "AAAaaa111";
		int id_questionA = 1;
		String reponse_a = "Rep A";
		int id_questionB = 2;
		String reponse_b = "Rep B";
		Utilisateur unUtilisateur = new Utilisateur(username,nom,prenom, mot_de_passe,id_questionA,reponse_a,id_questionB,reponse_b);
		return unUtilisateur;
   }
   public Vector<Integer> genererListeArme()
   {
	   Vector<Integer> liste = new Vector<Integer>();
	   for (int i =0; i<6;i++)
	   {
		   int nbArme = genererNombre(0, 10);
		   liste.add(nbArme);
	   }
	   return liste;
   }

}
