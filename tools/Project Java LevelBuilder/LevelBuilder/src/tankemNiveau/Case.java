package tankemNiveau;

public class Case {
private int idNiveau;
private int positionX;
private int positionY;
private int type;

public int getIdNiveau() {
	return idNiveau;
}

public int getPositionX() {
	return positionX;
}

public int getPositionY() {
	return positionY;
}

public int getType() {
	return type;
}

public Case(int idNiveau, int positionX, int positionY, int type) {
	super();
	this.idNiveau = idNiveau;
	this.positionX = positionX;
	this.positionY = positionY;
	this.type = type;
}
}
