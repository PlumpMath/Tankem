package generateurtankem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;



public class DAOOracle {



  private static Connection connexion;
 
  //CONNEXION À LA BD
  public static boolean connecter ()
  {
    try
    {
    Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
    System.out.println("apres driver"); //                 sigma = serveur |1521 = port | DECINFO = nom de la base de données
    connexion = DriverManager.getConnection( "jdbc:oracle:thin:@SIGMA:1521:DECINFO", "e1232188", "E"); // serveur sigma , base de données decinfo
    System.out.println("apres auth");
    return true;
    }
    catch ( Exception sqle)
    {
      sqle.printStackTrace();
      return false;
    }
}
  //DECONNEXION À LA BD

  public static void seDeconnecter ()
  {
    try
    {
    connexion.close();
    connexion = null;
    System.gc(); //garbage collector 
    }
    catch ( SQLException sqle )
    {
      sqle.printStackTrace();
    }
  }
  //Ajouter un niveau à la BD
  public static void ajouterNiveau (Niveau n, int idUser)
  {
	  PreparedStatement enonce;
	try {
		enonce = connexion.prepareStatement("INSERT INTO tankemNiveau values(seq_niv.nextval,?,?,?,?,?,?,?,?)");
		enonce.setString(1, n.getNom()); //en SQL ca part toujours à 1, pas à 0.
		enonce.setInt(2, n.getTempsMin());
		enonce.setInt(3, n.getTempsMax());
		enonce.setDate(4,new java.sql.Date(n.getDate().getTime()));
		enonce.setInt(5, n.getStatus());
		enonce.setInt(6, n.getGrosseurX());
		enonce.setInt(7, n.getGrosseurY());
		enonce.setInt(8, idUser);

		enonce.executeUpdate(); //nb de ligne que j'ai ajouté
		enonce.close();

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
  //Ajouter une case à la BD
  public static void ajouterCase (Case c)
  {
		System.out.println("id"+c.getIdNiveau());
		System.out.println("x"+c.getPositionX());
		System.out.println("y"+c.getPositionY());
		System.out.println("tyepe:"+c.getType());
	  PreparedStatement enonce;
	try {
		enonce = connexion.prepareStatement("INSERT INTO tankemCase values(?,?,?,?)");


		enonce.setInt(1, c.getIdNiveau()); //en SQL ca part toujours à 1, pas à 0.
		enonce.setInt(2, c.getPositionX());
		enonce.setInt(3, c.getPositionY());
		enonce.setInt(4, c.getType());
		
		enonce.executeUpdate(); //nb de ligne que j'ai ajouté
		enonce.close();

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

  }
  
  //Ajouter un tank à la BD
  public static void ajouterTank(Tank t, int id)
  {
	  PreparedStatement enonce;
	try {
		enonce = connexion.prepareStatement("INSERT INTO tankemTank values(?,?,?)");
		enonce.setInt(1, id); //en SQL ca part toujours à 1, pas à 0.
		enonce.setInt(2, t.getPositionX());
		enonce.setInt(3, t.getPositionY());

		enonce.executeUpdate(); //nb de ligne que j'ai ajouté
		enonce.close();

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  //Trouver le ID du niveau en question
  public static int trouverIdNiveau() throws SQLException
  {
	  
	  Statement enonce = connexion.createStatement();

	  ResultSet result = enonce.executeQuery("select MAX(ID) FROM TANKEMNIVEAU");

	  if (!result.next()) //change de ligne et vérifie vrai ou faut
		 {
			 throw new SQLException("Erreur avec le niveau.");
		 }
	  int resultatTrouver = result.getInt(1);
		enonce.close();

		 return resultatTrouver;

  }
  
  //Trouver le mdp du serveur
  public static String trouverMdpEncrypte(String userName) throws SQLException
  {
	  
		 
	  PreparedStatement enonce = connexion.prepareStatement("SELECT MOT_DE_PASSE FROM TANKEMUtilisateur where USERNAME =?");
		 
	  enonce.setString(1,userName);
	  ResultSet result = enonce.executeQuery();
		enonce.close();

	  if (!result.next()) //change de ligne et vérifie vrai ou faut 
	  {
		  throw new SQLException("pas d'utilisateur avec ce code");
	  }
	  return result.getString(1);
	  
  }
  //Trouver le mdp du serveur
  public static int trouverIdUsername(String userName) throws SQLException
  {
	  
		
	  PreparedStatement enonce = connexion.prepareStatement("SELECT ID FROM TANKEMUtilisateur where USERNAME =?");
		 
	  enonce.setString(1,userName);
	  ResultSet result = enonce.executeQuery();
		enonce.close();

	  if (!result.next()) //change de ligne et vérifie vrai ou faut 
	  {
		  throw new SQLException("erreur avec le username");
	  }
	  return result.getInt(1);
	  
  }
  
	
	public static Vector<Integer> chercherIdUser()
	{
		Vector<Integer> lesId = new Vector<Integer>();
		try {
			PreparedStatement enonce = connexion.prepareStatement("select ID from TANKEMUtilisateur");
			ResultSet resultat = enonce.executeQuery();

			while(resultat.next())
			{	
				lesId.add(resultat.getInt(1));
			}
			enonce.close();

		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lesId;
	}
	public static Vector<Integer> chercherIdNiveaux()
	{
		Vector<Integer> lesId = new Vector<Integer>();
		try {
			PreparedStatement enonce = connexion.prepareStatement("select ID from TANKEMNIVEAU");
			ResultSet resultat = enonce.executeQuery();

			while(resultat.next())
			{	
				lesId.add(resultat.getInt(1));
			}
			enonce.close();

		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lesId;
	}
	public static boolean enregistrerUser(Utilisateur unUtilisateur, Vector<Integer> liste)
	{
		try {
			
			PreparedStatement enonce = connexion.prepareStatement("select seq_utilisateur.nextVal from dual");
			ResultSet resultat = enonce.executeQuery();
			resultat.next();
			int idUser = resultat.getInt(1);
			
			
			enonce = connexion.prepareStatement("INSERT INTO tankemUtilisateur(id,username,nom,prenom,mot_de_passe,id_questionA,reponse_a,id_questionB,reponse_b) VALUES(?,?,?,?,?,?,?,?,?)");
			enonce.setInt(1, idUser);
			enonce.setString(2, unUtilisateur.getUsername());
			enonce.setString(3, unUtilisateur.getNom());
			enonce.setString(4, unUtilisateur.getPrenom());
			String password = "$2a$12$TiwJOWDlaeGsLsIv2y3CMee/FNEuJ54UgET4hXzy6FTaCk0y1fJPW";
			enonce.setString(5, password);
			enonce.setInt(6, unUtilisateur.getId_questionA());
			enonce.setString(7, unUtilisateur.getReponse_a());
			enonce.setInt(8, unUtilisateur.getId_questionB());
			enonce.setString(9, unUtilisateur.getReponse_b());
			enonce.executeUpdate();
			enonce.close();

			enonce = connexion.prepareStatement("INSERT ALL "
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK,NBTIR,QTEDMG) VALUES(?,1,?,0,0) "
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK,NBTIR,QTEDMG) VALUES(?,2,?,0,0) "
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK,NBTIR,QTEDMG) VALUES(?,3,?,0,0)"
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK,NBTIR,QTEDMG) VALUES(?,4,?,0,0)"
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK,NBTIR,QTEDMG) VALUES(?,5,?,0,0)"
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK,NBTIR,QTEDMG) VALUES(?,6,?,0,0)"
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK,NBTIR,QTEDMG) VALUES(?,7,1,0,0)"

					+ "SELECT * FROM dual");
			
			enonce.setInt(1,idUser);
			enonce.setInt(2,liste.get(0));
			enonce.setInt(3,idUser);
			enonce.setInt(4,liste.get(1));
			enonce.setInt(5,idUser);
			enonce.setInt(6,liste.get(2));
			enonce.setInt(7,idUser);
			enonce.setInt(8,liste.get(3));
			enonce.setInt(9,idUser);
			enonce.setInt(10,liste.get(4));
			enonce.setInt(11,idUser);
			enonce.setInt(12,liste.get(5));
			enonce.setInt(13,idUser);
			enonce.executeUpdate();
			enonce.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		
		return false;
	}
	
	static public Niveau chercherUnNiveau(int id)
	{
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT * FROM tankemNiveau WHERE id = ?");
			enonce.setInt(1,id);
			ResultSet resultat = enonce.executeQuery();
			Niveau unNiveau;
			resultat.next();
			
			unNiveau =  new Niveau(resultat.getString(2),resultat.getInt(3),resultat.getInt(4),resultat.getDate(5),resultat.getInt(6),resultat.getInt(7),resultat.getInt(8));
			
			enonce.close();
			return unNiveau;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public static void lesJoursImplique(int idNiveau, int idUser)
	{
		  PreparedStatement enonce;
			try {
				enonce = connexion.prepareStatement("INSERT INTO TANKEMJOUEURIMPLIQUE values(?,?)");
				enonce.setInt(1,idNiveau ); //en SQL ca part toujours à 1, pas à 0.
				enonce.setInt(2, idUser);

				enonce.executeUpdate(); //nb de ligne que j'ai ajouté
				enonce.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	  public static void ajouterCaseThermique (CaseThermique caseT, int idPartie)
	  {
		  PreparedStatement enonce;
		try {
			enonce = connexion.prepareStatement("INSERT INTO tankemCaseThermique values(seq_caseThermique.nextval,?,?,?)");
			enonce.setInt(1, idPartie); //en SQL ca part toujours à 1, pas à 0.
			enonce.setInt(2, caseT.getPositionX());
			enonce.setInt(3, caseT.getPositionY());

			enonce.executeUpdate(); //nb de ligne que j'ai ajouté
			enonce.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }
	  //Ajouter un niveau à la BD
	  public static void ajouterJoueurCase (CaseThermique caseT,int idCase, int idPartie, int idJoueur)
	  {
		  PreparedStatement enonce;
		try {


			enonce = connexion.prepareStatement("INSERT INTO TANKEMJOUEURCASE values(?,?,?,?,?,?)");
			enonce.setInt(1, idJoueur); //en SQL ca part toujours à 1, pas à 0.
			enonce.setInt(2, idCase);
			enonce.setInt(3, idPartie);
			enonce.setInt(4, caseT.getQteDomageFait()); //en SQL ca part toujours à 1, pas à 0.
			enonce.setInt(5, caseT.getQteDomagePris());
			enonce.setFloat(6, (float)caseT.getTempsDessus());			

			enonce.executeUpdate(); //nb de ligne que j'ai ajouté
			enonce.close();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		 
	  }
	  //Ajouter un niveau à la BD
	  public static int chercherDernierCaseThermique () throws SQLException
	  {
		  Statement enonce = connexion.createStatement();

		  ResultSet result = enonce.executeQuery("select MAX(ID) FROM TANKEMCASETHERMIQUE");

		  if (!result.next()) //change de ligne et vérifie vrai ou faut
			 {
				 throw new SQLException("Erreur avec le niveau.");
			 }
		  int idCase = result.getInt(1);
			enonce.close();

			 return idCase;
		  
	  }
	  public static int chercherDernierePartie () throws SQLException
	  {
		  Statement enonce = connexion.createStatement();

		  ResultSet result = enonce.executeQuery("select MAX(ID) FROM TANKEMPARTIE");

		  if (!result.next()) //change de ligne et vérifie vrai ou faut
			 {
				 throw new SQLException("Erreur avec le niveau.");
			 }
		  int idNiveau =result.getInt(1);
			enonce.close();

			 return idNiveau;
		  
	  }
	  //Ajouter un niveau à la BD
	  public static void ajouterPartie (int idGagnant, int idNiveau)
	  {
		  PreparedStatement enonce;
		  Date date = new Date();
		try {
			enonce = connexion.prepareStatement("INSERT INTO TANKEMPARTIE values(seq_partie.nextval,?,?,?)");
			enonce.setInt(1, idNiveau);
			enonce.setDate(2, new java.sql.Date(date.getTime()));
			enonce.setInt(3, idGagnant);

			enonce.executeUpdate(); //nb de ligne que j'ai ajouté
			enonce.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }
	  
		static public void updateArmurie(int nbTirs, int idArme, int QteDommage, int idUser )
		{
			try {
				PreparedStatement enonce = connexion.prepareStatement("UPDATE tankemArmurieUtilisateur SET "
						+ "NBTIR = ?, QTEDMG = ? "
						+ "WHERE ID_UTILISATEUR = ? AND ID_ARME=?");
				enonce.setInt(1, nbTirs);
				enonce.setInt(2, QteDommage);
				enonce.setInt(3, idUser);
				enonce.setInt(4, idArme);

				enonce.executeUpdate();
				enonce.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


static public int chercherIdCaseTherm(int idPartie, int posX, int posY)
{
	try {
		PreparedStatement enonce = connexion.prepareStatement("SELECT ID FROM tankemCASETHERMIQUE WHERE id_partie = ? AND valeurx =? AND valeury =?");
		enonce.setInt(1,idPartie);
		enonce.setInt(2,posX);
		enonce.setInt(3,posY);

		ResultSet resultat = enonce.executeQuery();
		Niveau unNiveau;
		resultat.next();
		
		int resultatID = resultat.getInt(1);
		enonce.close();
		return resultatID;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return 0;
}
}

