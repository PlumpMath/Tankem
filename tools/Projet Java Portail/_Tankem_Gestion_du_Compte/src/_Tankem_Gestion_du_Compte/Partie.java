package _Tankem_Gestion_du_Compte;

import java.util.Date;


public class Partie {
	
	int id;
	int idNiveau;
	Date laDate;
	Integer idGagnant;
	public Partie(int id, int idNiveau, Date laDate, int idGagnant) {
		this.id = id;
		this.idNiveau = idNiveau;
		this.laDate = laDate;
		this.idGagnant = idGagnant;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdNiveau() {
		return idNiveau;
	}
	public void setIdNiveau(int idNiveau) {
		this.idNiveau = idNiveau;
	}
	public Date getLaDate() {
		return laDate;
	}
	public void setLaDate(Date laDate) {
		this.laDate = laDate;
	}
	public Integer getIdGagnant() {
		return idGagnant;
	}
	public void setIdGagnant(int idGagnant) {
		this.idGagnant = idGagnant;
	}
	
	

}
