package generateurtankem;

public class CaseThermique {
private int positionX;
private int positionY;
private int qteDomagePris;
private int qteDomageFait;
private int tempsDessus;
public CaseThermique(int positionX, int positionY, int qteDomagePris,
		int qteDomageFait, int tempsDessus) {
	super();
	this.positionX = positionX;
	this.positionY = positionY;
	this.qteDomagePris = qteDomagePris;
	this.qteDomageFait = qteDomageFait;
	this.tempsDessus = tempsDessus;
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
public int getQteDomagePris() {
	return qteDomagePris;
}
public void setQteDomagePris(int qteDomagePris) {
	this.qteDomagePris = qteDomagePris;
}
public int getQteDomageFait() {
	return qteDomageFait;
}
public void setQteDomageFait(int qteDomageFait) {
	this.qteDomageFait = qteDomageFait;
}
public int getTempsDessus() {
	return tempsDessus;
}
public void setTempsDessus(int tempsDessus) {
	this.tempsDessus = tempsDessus;
}

}
