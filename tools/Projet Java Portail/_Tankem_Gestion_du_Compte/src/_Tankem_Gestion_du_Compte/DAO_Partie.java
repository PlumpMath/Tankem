package _Tankem_Gestion_du_Compte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DAO_Partie {
	
	public int getPartieDerniereHeure()
	{
		Connection connexion = laConnection.getConnexion();
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT COUNT(*) FROM TANKEMPARTIE WHERE debut_partie > SYSDATE - (1/24)");
			ResultSet resultat = enonce.executeQuery();
			
			if(resultat.next())
			{
				return resultat.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int getPartieTotal()
	{
		Connection connexion = laConnection.getConnexion();
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT COUNT(*) FROM TANKEMPARTIE");
			ResultSet resultat = enonce.executeQuery();
			
			if(resultat.next())
			{
				return resultat.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public Vector<Integer> getIdPartie(int idUser)
	{
		Vector<Integer> lesid = new Vector<Integer>();
		Connection connexion = laConnection.getConnexion();
		try {
			PreparedStatement enonce = connexion.prepareStatement("SELECT ID_PARTIE FROM TANKEMJOUEURIMPLIQUE WHERE ID_JOUEUR = ?");
			enonce.setInt(1,idUser);
			ResultSet resultat = enonce.executeQuery();
			
			while(resultat.next())
			{
				lesid.add(resultat.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lesid;
	}
	
	public Vector<Partie> getPartie(Vector<Integer> lesids)
	{
		Vector<Partie> lesParties = new Vector<Partie>();
		Connection connexion = laConnection.getConnexion();
		try {
			
			for(int i = 0; i < lesids.size(); i++)
			{
				PreparedStatement enonce = connexion.prepareStatement("SELECT * FROM TANKEMPARTIE WHERE ID = ?");
				enonce.setInt(1,lesids.get(i));
				ResultSet resultat = enonce.executeQuery();
				
				if(resultat.next())
				{
					System.out.println(resultat.getInt(4));
					lesParties.add(new Partie(resultat.getInt(1),resultat.getInt(2),resultat.getDate(3),resultat.getInt(4)));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lesParties;
	}
	
	public Vector<String> getUsername(int idPartie)
	{	
		Vector<String> usernames = new Vector<String>();
		Vector<Integer> idJoueurs = getIdJoueurImplique(idPartie);
		Connection connexion = laConnection.getConnexion();
		try {
			
			PreparedStatement enonce;
			ResultSet resultat;
			for(int i = 0; i< idJoueurs.size(); i++)
			{
				enonce = connexion.prepareStatement("SELECT USERNAME FROM TANKEMUTILISATEUR WHERE ID = ?");
				enonce.setInt(1,idJoueurs.get(i));
				resultat = enonce.executeQuery();
				if(resultat.next())
				{
					usernames.add(resultat.getString(1));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usernames;
	}
	
	public Vector <CaseThermique> getCasesThermiques(int idPartie,int idJoueur1,int idJoueur2)
	{
		
		Vector<CaseThermique> lesCases = new Vector<CaseThermique>();
		Connection connexion = laConnection.getConnexion();
		try {
			
			PreparedStatement enonce = connexion.prepareStatement("SELECT * FROM TANKEMCASETHERMIQUE WHERE ID_PARTIE = ? ORDER BY VALEURX,VALEURY");
			enonce.setInt(1,idPartie);
			ResultSet resultat = enonce.executeQuery();
			
			while(resultat.next())
			{
				lesCases.add(new CaseThermique(resultat.getInt(1),resultat.getInt(3),resultat.getInt(4)));
			}
			
			enonce = connexion.prepareStatement("SELECT * FROM TANKEMJOUEURCASE WHERE ID_PARTIE = ? ORDER BY ID_JOUEUR");
			enonce.setInt(1,idPartie);
			resultat = enonce.executeQuery();
			
			while(resultat.next())
			{
				for(int i = 0; i < lesCases.size(); i++)
				{
					if(lesCases.get(i).getId() == resultat.getInt(2) && idJoueur1 == resultat.getInt(1))
					{
						lesCases.get(i).setQteDmgDonneJoueur1(resultat.getInt(4));
						lesCases.get(i).setQteDmgRecuJoueur1(resultat.getInt(5));
						lesCases.get(i).setQteTempsJoueur1(resultat.getInt(6));
					}
					else if(lesCases.get(i).getId() == resultat.getInt(2) && idJoueur2 == resultat.getInt(1))
					{
						lesCases.get(i).setQteDmgDonneJoueur2(resultat.getInt(4));
						lesCases.get(i).setQteDmgRecuJoueur2(resultat.getInt(5));
						lesCases.get(i).setQteTempsJoueur2(resultat.getInt(6));
					}
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lesCases;
	}
	
	public Vector<Integer> getIdJoueurImplique(int idPartie)
	{
		Vector<Integer> idJoueurs = new Vector<Integer>();
		Connection connexion = laConnection.getConnexion();
		try {
			
			PreparedStatement enonce = connexion.prepareStatement("SELECT ID_JOUEUR FROM TANKEMJOUEURIMPLIQUE WHERE ID_PARTIE = ?");
			enonce.setInt(1,idPartie);
			ResultSet resultat = enonce.executeQuery();
			
			while(resultat.next())
			{
				idJoueurs.add(resultat.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idJoueurs;
	}

}
