package generateurtankem;

public class Case {
private int idNiveau;
private int positionX;
private int positionY;
private int type;



public Case(int idNiveau, int positionX, int positionY, int type) {
	super();
	this.idNiveau = idNiveau;
	this.positionX = positionX;
	this.positionY = positionY;
	this.type = type;

}



public int getIdNiveau() {
	return idNiveau;
}



public void setIdNiveau(int idNiveau) {
	this.idNiveau = idNiveau;
}



public int getPositionX() {
	return positionX;
}



public void setPositionX(int positionX) {
	this.positionX = positionX;
}



public int getPositionY() {
	return positionY;
}



public void setPositionY(int positionY) {
	this.positionY = positionY;
}



public int getType() {
	return type;
}



public void setType(int type) {
	this.type = type;
}

}

