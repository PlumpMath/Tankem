package tankemNiveau;

import java.awt.Color;

public class Tank {

	private int positionX;
	private int positionY;
	private Color couleur;
	
	public Tank(int positionX, int positionY, Color couleur) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.couleur = couleur;
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
	public Color getCouleur() {
		return couleur;
	}
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	
}
