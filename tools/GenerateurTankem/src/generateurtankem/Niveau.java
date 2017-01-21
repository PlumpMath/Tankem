package generateurtankem;

import java.util.Date;

public class Niveau {
private String nom;
private int tempsMin;
private int tempsMax;
private Date date;
private int status;
private int grosseurX;
private int grosseurY;

public Niveau(String nom, int tempsMin, int tempsMax, Date date, int status,
		int grosseurX, int grosseurY) {
	super();
	this.nom =nom.substring(0, 1).toUpperCase() + nom.substring(1);
	this.tempsMin = tempsMin;
	this.tempsMax = tempsMax;
	this.date = date;
	this.status = status;
	this.grosseurX = grosseurX;
	this.grosseurY = grosseurY;
}

public String getNom() {
	return nom;
}

public int getTempsMin() {
	return tempsMin;
}

public int getTempsMax() {
	return tempsMax;
}

public Date getDate() {
	return date;
}

public int getStatus() {
	return status;
}

public int getGrosseurX() {
	return grosseurX;
}

public int getGrosseurY() {
	return grosseurY;
}


}
