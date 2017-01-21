package tankemNiveau;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



//Static car on a juste une base de donn�es, de cet mani�re, pas besoin de cr�er un objet.

public class Operations
{
  private static Connection connexion;
 
  //CONNEXION � LA BD
  public static boolean connecter ()
  {
    try
    {
    Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
    System.out.println("apres driver"); //                 sigma = serveur |1521 = port | DECINFO = nom de la base de donn�es
    connexion = DriverManager.getConnection( "jdbc:oracle:thin:@SIGMA:1521:DECINFO", "e1041268", "AAAaaa111"); // serveur sigma , base de donn�es decinfo
    System.out.println("apres auth");
    return true;
    }
    catch ( Exception sqle)
    {
      sqle.printStackTrace();
      return false;
    }
}
  //DECONNEXION � LA BD

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
  //Ajouter un niveau � la BD
  public static void ajouterNiveau (Niveau n, String username)
  {
	  PreparedStatement enonce;
	try {
		enonce = connexion.prepareStatement("INSERT INTO tankemNiveau values(seq_niv.nextval,?,?,?,?,?,?,?,?)");
		enonce.setString(1, n.getNom()); //en SQL ca part toujours � 1, pas � 0.
		enonce.setInt(2, n.getTempsMin());
		enonce.setInt(3, n.getTempsMax());
		enonce.setDate(4,new java.sql.Date(n.getDate().getTime()));
		enonce.setInt(5, n.getStatus());
		enonce.setInt(6, n.getGrosseurX());
		enonce.setInt(7, n.getGrosseurY());
		enonce.setInt(8, trouverIdUsername(username));

		enonce.executeUpdate(); //nb de ligne que j'ai ajout�
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
  //Ajouter une case � la BD
  public static void ajouterCase (Case c)
  {
	  PreparedStatement enonce;
	try {
		enonce = connexion.prepareStatement("INSERT INTO tankemCase values(?,?,?,?)");
		enonce.setInt(1, c.getIdNiveau()); //en SQL ca part toujours � 1, pas � 0.
		enonce.setInt(2, c.getPositionX());
		enonce.setInt(3, c.getPositionY());
		enonce.setInt(4, c.getType());
		
		System.out.println("x:"+c.getPositionX()+"--- y:"+c.getPositionY());
		enonce.executeUpdate(); //nb de ligne que j'ai ajout�
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  //Ajouter un tank � la BD
  public static void ajouterTank(Tank t, int id)
  {
	  PreparedStatement enonce;
	try {
		enonce = connexion.prepareStatement("INSERT INTO tankemTank values(?,?,?)");
		enonce.setInt(1, id); //en SQL ca part toujours � 1, pas � 0.
		enonce.setInt(2, t.getPositionX());
		enonce.setInt(3, t.getPositionY());

		enonce.executeUpdate(); //nb de ligne que j'ai ajout�
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  //Trouver le ID du niveau en question
  public static int trouverIdNiveau() throws SQLException
  {
	  
	  Statement enonce = connexion.createStatement();

	  ResultSet result = enonce.executeQuery("select seq_niv.nextval from dual");
	  if (!result.next()) //change de ligne et v�rifie vrai ou faut
		 {
			 throw new SQLException("Erreur avec le niveau.");
		 }
		 return result.getInt(1);

  }
  
  //Trouver le mdp du serveur
  public static String trouverMdpEncrypte(String userName) throws SQLException
  {
	  
		 
	  PreparedStatement enonce = connexion.prepareStatement("SELECT MOT_DE_PASSE FROM TANKEMUtilisateur where USERNAME =?");
		 
	  enonce.setString(1,userName);
	  ResultSet result = enonce.executeQuery();
	  if (!result.next()) //change de ligne et v�rifie vrai ou faut 
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
	  if (!result.next()) //change de ligne et v�rifie vrai ou faut 
	  {
		  throw new SQLException("erreur avec le username");
	  }
	  return result.getInt(1);
	  
  }
  
}

