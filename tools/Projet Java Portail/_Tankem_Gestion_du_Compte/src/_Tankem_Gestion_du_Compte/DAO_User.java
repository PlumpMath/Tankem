package _Tankem_Gestion_du_Compte;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;

public class DAO_User {

	public String valideUser(String username, String password)
	{
		Connection connexion = laConnection.getConnexion();
		
		PreparedStatement enonce;
		try {
			enonce = connexion.prepareStatement("SELECT mot_de_passe FROM tankemUtilisateur WHERE username = ?");
			enonce.setString(1,username);
			ResultSet resultat = enonce.executeQuery();
			
			if(!resultat.next())
			{
				return "Le nom d'utilisateur n'existe pas";
			}
			else
			{
				String mdpSalt=resultat.getString(1);
				//Passer le salt du premier mdp
				//reponseHashe est le mdp encrypter.
				String reponseHashe = BCrypt.hashpw(password, mdpSalt);
				//On compare les 2 mots de passe:
				if (reponseHashe.equals(mdpSalt))
				{
					//Le mot de passe est bon
					return "good";
				}
				else //sinon envoyer un message
				{
					return "Mauvais mot de passe...";
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int[] validExistance(String username)
	{
		
		Connection connexion = laConnection.getConnexion();
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT * FROM tankemUtilisateur WHERE username = ?");
			enonce.setString(1,username);
			ResultSet resultat = enonce.executeQuery();
			
			if(resultat.next())
			{
				int[] tableau={resultat.getInt(6),resultat.getInt(8)};
				return tableau;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] tableau={0,0};
		return tableau;
	}
	
	public boolean validQuestionA(String question, int idQuestion, String username)
	{
		Connection connexion = laConnection.getConnexion();
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT REPONSE_A FROM tankemUtilisateur WHERE username = ? and ID_QUESTIONA = ?");
			enonce.setString(1,username);
			enonce.setInt(2, idQuestion);
			ResultSet resultat = enonce.executeQuery();
			
			if(resultat.next())
			{
				if(resultat.getString(1).equals(question))
				{
					return true;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean validQuestionB(String question, int idQuestion, String username)
	{
		Connection connexion = laConnection.getConnexion();
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT REPONSE_B FROM tankemUtilisateur WHERE username = ? and ID_QUESTIONB = ?");
			enonce.setString(1,username);
			enonce.setInt(2, idQuestion);
			ResultSet resultat = enonce.executeQuery();
			
			if(resultat.next())
			{
				if(resultat.getString(1).equals(question))
				{
					return true;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updatePassword(String username, String password)
	{
		Connection connexion = laConnection.getConnexion();
		try {
			PreparedStatement enonce = connexion.prepareStatement("UPDATE tankemUtilisateur SET "
					+ "MOT_DE_PASSE = ? "
					+ "WHERE USERNAME = ?");
			String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			enonce.setString(1,newPassword);
			enonce.setString(2, username);
			enonce.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public User getUser(String username)
	{
		Connection connexion = laConnection.getConnexion();
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT * FROM tankemUtilisateur WHERE username = ?");
			enonce.setString(1,username);
			ResultSet resultat = enonce.executeQuery();
			
			if(resultat.next())
			{
				return new User(resultat.getInt(1), resultat.getString(2), resultat.getString(3), resultat.getString(4), resultat.getString(5), resultat.getInt(6), resultat.getString(7), resultat.getInt(8), resultat.getString(9), resultat.getDouble(10), resultat.getDouble(11),resultat.getDouble(12));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void changeCouleur(Color laCouleur, String username)
	{
		Connection connexion = laConnection.getConnexion();
		try {
			System.out.println(laCouleur.getRed());
			
			double r = (double)laCouleur.getRed()/255.0;
			double g = (double)laCouleur.getGreen()/255.0;
			double b = (double)laCouleur.getBlue()/255.0;
			
			PreparedStatement enonce = connexion.prepareStatement("UPDATE tankemUtilisateur SET "
					+ "r = ?, "
					+ "g = ?, "
					+ "b = ? "
					+ "WHERE USERNAME = ?");
			enonce.setDouble(1,r);
			enonce.setDouble(2,g);
			enonce.setDouble(3,b);
			enonce.setString(4,username);
			enonce.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<String> getNiveauxFavoris(int idUser)
	{
		Connection connexion = laConnection.getConnexion();
		Vector<String> lesNiveaux = new Vector<String>();
		try {
			Vector<Integer> idNiveaux = new Vector<Integer>();
			PreparedStatement enonce = connexion.prepareStatement("SELECT ID_NIVEAU FROM TANKEMNIVEAUFAVORI WHERE ID_UTILISATEUR = ?");
			enonce.setInt(1,idUser);
			ResultSet resultat = enonce.executeQuery();
			
			while(resultat.next())
			{
				idNiveaux.add(resultat.getInt(1));
			}
			
			for(int i = 0; i < idNiveaux.size(); i++)
			{
				enonce = connexion.prepareStatement("SELECT NOM FROM TANKEMNIVEAU WHERE ID = ?");
				enonce.setInt(1,idNiveaux.get(i));
				resultat = enonce.executeQuery();
				if(resultat.next())
				{
					lesNiveaux.add(resultat.getString(1));
				}
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lesNiveaux;
	}
	
	public Vector<Integer> getIdNiveauCree(int idUser)
	{
		Connection connexion = laConnection.getConnexion();
		Vector<Integer> lesId = new Vector<Integer>();
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT ID FROM TANKEMNIVEAU WHERE PROPRIETAIRE = ?");
			enonce.setInt(1,idUser);
			ResultSet resultat = enonce.executeQuery();
			
			while(resultat.next())
			{
				lesId.add(resultat.getInt(1));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lesId;
	}
	
	public Vector<String> getNomNiveaux(Vector<Integer> lesId)
	{
		Connection connexion = laConnection.getConnexion();
		Vector<String> lesNiveaux = new Vector<String>();
		try {
			PreparedStatement enonce;
			ResultSet resultat;
			for(int i = 0; i < lesId.size(); i++)
			{
				enonce = connexion.prepareStatement("SELECT NOM FROM TANKEMNIVEAU WHERE ID = ?");
				enonce.setInt(1,lesId.get(i));
				resultat = enonce.executeQuery();
				if(resultat.next())
				{
					lesNiveaux.add(resultat.getString(1));
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lesNiveaux;
	}
	
	public Vector<Integer> getNbFavoris(Vector<Integer> lesId)
	{
		Connection connexion = laConnection.getConnexion();
		Vector<Integer> nbFavoris = new Vector<Integer>();
		try {
			PreparedStatement enonce;
			ResultSet resultat;
			for(int i = 0; i < lesId.size(); i++)
			{
				enonce = connexion.prepareStatement("select count(*) from TANKEMNIVEAUFAVORI where ID_NIVEAU=?");
				enonce.setInt(1,lesId.get(i));
				resultat = enonce.executeQuery();
				if(resultat.next())
				{
					nbFavoris.add(resultat.getInt(1));
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nbFavoris;
	}
	
	public Vector<Integer> getNbJouer(Vector<Integer> lesId)
	{
		Connection connexion = laConnection.getConnexion();
		Vector<Integer> nbJouer = new Vector<Integer>();
		try {
			PreparedStatement enonce;
			ResultSet resultat;
			for(int i = 0; i < lesId.size(); i++)
			{
				enonce = connexion.prepareStatement("select SUM(NB_FOIS_JOUE) from TANKEMNIVEAUNBFOIS where ID_NIVEAU=?");
				enonce.setInt(1,lesId.get(i));
				resultat = enonce.executeQuery();
				if(resultat.next())
				{
					nbJouer.add(resultat.getInt(1));
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nbJouer;
	}
	
	public int getNbNiveauCree(int idJoueur)
	{
		Connection connexion = laConnection.getConnexion();
		Vector<Integer> nbJouer = new Vector<Integer>();
		int nbNiveauCree = 0;
		try {
			 
			PreparedStatement enonce = connexion.prepareStatement("select COUNT(*) from TANKEMNIVEAU where proprietaire=?");
			enonce.setInt(1,idJoueur);
			ResultSet resultat = enonce.executeQuery();
			
			if(resultat.next())
			{
				nbNiveauCree = resultat.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nbNiveauCree;	
	}
	
	
	public Vector<Integer> getIdArmurerie()
	{
		Connection connexion = laConnection.getConnexion();
		Vector<Integer> idArmurerie = new Vector<Integer>();
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT ID FROM tankemArmurie");
			ResultSet resultat = enonce.executeQuery();
			
			while(resultat.next())
			{
				idArmurerie.add(resultat.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idArmurerie;
	}
	
	public Vector<String> getArmurieNom(Vector<Integer> lesId)
	{
		Connection connexion = laConnection.getConnexion();
		Vector<String> lesNoms = new Vector<String>();
		try {
			PreparedStatement enonce;
			ResultSet resultat;
			for(int i = 0; i < lesId.size(); i++)
			{
				enonce = connexion.prepareStatement("select nom_arme from tankemArmurie where id=?");
				enonce.setInt(1,lesId.get(i));
				resultat = enonce.executeQuery();
				if(resultat.next())
				{
					lesNoms.add(resultat.getString(1));
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lesNoms;
	}
	
	public Vector<Integer> getArmurieQte(Vector<Integer> lesId, int idUser)
	{
		Connection connexion = laConnection.getConnexion();
		Vector<Integer> qte = new Vector<Integer>();
		try {
			PreparedStatement enonce;
			ResultSet resultat;
			for(int i = 0; i < lesId.size(); i++)
			{
				enonce = connexion.prepareStatement("select NB_STOCK from tankemArmurieUtilisateur where ID_UTILISATEUR=? and ID_ARME=?");
				enonce.setInt(1,idUser);
				enonce.setInt(2,lesId.get(i));
				resultat = enonce.executeQuery();
				if(resultat.next())
				{
					qte.add(resultat.getInt(1));
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return qte;
	}
	
	
	public boolean enregistrerUser(String username,String nom,String prenom,String mot_de_passe, int id_questionA, String reponse_a, int id_questionB, String reponse_b, JFrame monInterface)
	{
		Connection connexion = laConnection.getConnexion();
		try {
			
			PreparedStatement enonce = connexion.prepareStatement("select seq_utilisateur.nextVal from dual");
			ResultSet resultat = enonce.executeQuery();
			resultat.next();
			int idUser = resultat.getInt(1);
			
			
			enonce = connexion.prepareStatement("INSERT INTO tankemUtilisateur(id,username,nom,prenom,mot_de_passe,id_questionA,reponse_a,id_questionB,reponse_b) VALUES(?,?,?,?,?,?,?,?,?)");
			enonce.setInt(1, idUser);
			enonce.setString(2, username);
			enonce.setString(3, nom);
			enonce.setString(4, prenom);
			String password = BCrypt.hashpw(mot_de_passe, BCrypt.gensalt());
			enonce.setString(5, password);
			enonce.setInt(6, id_questionA);
			enonce.setString(7, reponse_a);
			enonce.setInt(8, id_questionB);
			enonce.setString(9, reponse_b);
			enonce.executeUpdate();
			
			enonce = connexion.prepareStatement("INSERT ALL "
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK) VALUES(?,1,0) "
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK) VALUES(?,2,0) "
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK) VALUES(?,3,0)"
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK) VALUES(?,4,0)"
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK) VALUES(?,5,0)"
					+ "INTO TANKEMARMURIEUTILISATEUR (ID_UTILISATEUR,ID_ARME,NB_STOCK) VALUES(?,6,0)"
					+ "SELECT * FROM dual");
			
			enonce.setInt(1,idUser);
			enonce.setInt(2,idUser);
			enonce.setInt(3,idUser);
			enonce.setInt(4,idUser);
			enonce.setInt(5,idUser);
			enonce.setInt(6,idUser);
			enonce.executeUpdate();
			System.out.println("hello");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			laConnection.gestionOfError(e, monInterface);
		}
		
		return false;
	}
}
