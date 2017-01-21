package _Tankem_Gestion_du_Compte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DAO_Question {
	
	
	public Vector<Question> getLesQuestions()
	{
		Vector<Question> lesQuestions = new Vector<Question>();
		try {
			Connection connexion = laConnection.getConnexion();
			PreparedStatement enonce = connexion.prepareStatement("SELECT * FROM tankemQuestion");
			ResultSet resultat = enonce.executeQuery();
			
			
			while(resultat.next())
			{	
				lesQuestions.add(new Question(resultat.getInt(1),resultat.getString(2)));
			}

		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lesQuestions;
	}
	
	public String getUneQuestion(int id)
	{
		try {
			Connection connexion = laConnection.getConnexion();
			PreparedStatement enonce = connexion.prepareStatement("SELECT * FROM tankemQuestion WHERE ID = ?");
			enonce.setInt(1, id);
			ResultSet resultat = enonce.executeQuery();
			
			if(resultat.next())
			{
				return resultat.getString(2);
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
