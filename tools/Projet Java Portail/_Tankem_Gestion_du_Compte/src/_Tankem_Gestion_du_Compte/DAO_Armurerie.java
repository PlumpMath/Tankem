package _Tankem_Gestion_du_Compte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DAO_Armurerie {
	
	public String getArmeAvecPlusTire(int idJoueur)
	{
		Connection connexion = laConnection.getConnexion();
		Vector<Integer> nbJouer = new Vector<Integer>();
		String armeAvecPlusTire = "";
		Vector<Integer> idArmes = new Vector<Integer>();
		try {
			 
			PreparedStatement enonce = connexion.prepareStatement("select ID_ARME from tankemArmurieUtilisateur WHERE NBTIR=(select max(NBTIR) from tankemArmurieUtilisateur WHERE ID_UTILISATEUR = ?)");
			enonce.setInt(1,idJoueur);
			ResultSet resultat = enonce.executeQuery();
			
			while(resultat.next())
			{
				idArmes.add(resultat.getInt(1));
			}
			
			
			for(int i = 0; i < idArmes.size(); i++)
			{
				enonce = connexion.prepareStatement("select NOM_ARME FROM tankemArmurie WHERE id = ?");
				enonce.setInt(1,idArmes.get(i));
				resultat = enonce.executeQuery();
				
				if(resultat.next())
				{
					armeAvecPlusTire += resultat.getString(1) + " ";
				}
			}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return armeAvecPlusTire;	
	}
	
	public double getDmgMoyenArme(String nomArme, int idJoueur)
	{
		Connection connexion = laConnection.getConnexion();
		int nbTire = 1;
		int qteDmg = 1;
		try {
			PreparedStatement enonce = connexion.prepareStatement("select ID from tankemArmurie WHERE NOM_ARME = ?");
			enonce.setString(1,nomArme);
			ResultSet resultat = enonce.executeQuery();
			
			int idArme = 0;
			
			if(resultat.next())
			{
				idArme = resultat.getInt(1);
			}

			enonce = connexion.prepareStatement("select * from tankemArmurieUtilisateur WHERE ID_ARME = ? and ID_UTILISATEUR = ?");
			enonce.setInt(1,idArme);
			enonce.setInt(2,idJoueur);
			resultat = enonce.executeQuery();
			
			if(resultat.next())
			{
				nbTire = resultat.getInt(4);
				qteDmg = resultat.getInt(5);
			}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return qteDmg/(double)nbTire;
		
	}
	
	public double getDmgMoyenTotal(int idJoueur)
	{
		Connection connexion = laConnection.getConnexion();
		double dmgMoyenTotal = 0;
		try {
			
			PreparedStatement enonce = connexion.prepareStatement("select SUM(NBTIR) from tankemArmurieUtilisateur WHERE ID_UTILISATEUR = ?");
			enonce.setInt(1,idJoueur);
			ResultSet resultat = enonce.executeQuery();
			
			int nbTire = 0;
			if(resultat.next())
			{
				nbTire = resultat.getInt(1);
			}
			
			if(nbTire!=0)
			{
				enonce = connexion.prepareStatement("select SUM(QTEDMG)/SUM(NBTIR) from tankemArmurieUtilisateur WHERE ID_UTILISATEUR = ?");
				enonce.setInt(1,idJoueur);
				resultat = enonce.executeQuery();
			
				if(resultat.next())
				{
					dmgMoyenTotal = resultat.getDouble(1);
				}
			}
			else
			{
				return 0;
			}
			
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dmgMoyenTotal;
	}

}
