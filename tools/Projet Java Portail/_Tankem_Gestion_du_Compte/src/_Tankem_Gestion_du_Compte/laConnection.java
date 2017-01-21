package _Tankem_Gestion_du_Compte;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class laConnection {
	
public static Connection connexion;
	
	public static void seConnecter()
	{
		 try
		    {
			 if(connexion == null)
			 {
				 Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
				 connexion = (Connection) DriverManager.getConnection( "jdbc:oracle:thin:@SIGMA:1521:DECINFO", "e1232188", "E"); // serveur sigma , base de données decinfo
			 }    
		    }
		    catch ( Exception sqle)
		    {
		    	sqle.printStackTrace();
		    }
	}
	
	public static void fermerConnection()
	{
		try {
			if(connexion != null)
			{
				connexion.close();
				System.out.println("connection fermer");
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConnexion()
	{
		return connexion;
	}
	
	public static void gestionOfError(SQLException e, JFrame monInterface)
	{
		System.out.println(e.getMessage());
		String message = e.getMessage();
		if(e.getErrorCode() == 1)
		{
			Pattern p = Pattern.compile(".*_([A-Z]*)");
			Matcher m = p.matcher(message);
			if (m.find()) {
				JOptionPane.showMessageDialog(monInterface, "La valeur dans le champ " + m.group(1) + " est déja utiliser");
			}	
		}
		else if(e.getErrorCode() == 1400)
		{
			Pattern p = Pattern.compile(".*\"([A-Z]*)\"");
			Matcher m = p.matcher(message);
			if (m.find()) {
				JOptionPane.showMessageDialog(monInterface, "le champ " + m.group(1) + " ne peut pas être null");
			}
		}
		else if(e.getErrorCode() == 12899)
		{
			String variable ="",min="",max="";
			
			Pattern p = Pattern.compile(":.*:.*:(.*)[)]");
			Matcher m = p.matcher(message);
			if(m.find())
			{	System.out.println("1");
				max = m.group(1);
				p = Pattern.compile(".*\"([A-Z]*)\"");
				m = p.matcher(message);
				if(m.find())
				{	System.out.println("2");
					variable = m.group(1);
					p = Pattern.compile(":.*:(.*),");
					m = p.matcher(message);
					if (m.find()) {
						System.out.println("3");
						min = m.group(1);
						JOptionPane.showMessageDialog(monInterface, "Le champ " + variable + " ne peut pas contenir plus de " + max + " Caracteres et il en contient presentement " + min);
					}
				}	
			}
			
		}
	}

}
