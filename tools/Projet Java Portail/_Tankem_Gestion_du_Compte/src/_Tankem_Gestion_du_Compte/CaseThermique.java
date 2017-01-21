package _Tankem_Gestion_du_Compte;

public class CaseThermique {
	
	private int id;
	private int x;
	private int y;
	private int qteDmgRecuJoueur1;
	private int qteDmgRecuJoueur2;
	private int qteDmgDonneJoueur1;
	private int qteDmgDonneJoueur2;

	private double qteTempsJoueur1;
	private double qteTempsJoueur2;
	
	public CaseThermique(int id, int x, int y)
	{
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getQteDmgRecuJoueur1() {
		return qteDmgRecuJoueur1;
	}

	public void setQteDmgRecuJoueur1(int qteDmgJoueur1) {
		this.qteDmgRecuJoueur1 = qteDmgJoueur1;
	}

	public int getQteDmgRecuJoueur2() {
		return qteDmgRecuJoueur2;
	}

	public void setQteDmgRecuJoueur2(int qteDmgJoueur2) {
		this.qteDmgRecuJoueur2 = qteDmgJoueur2;
	}

	public double getQteTempsJoueur1() {
		return qteTempsJoueur1;
	}

	public void setQteTempsJoueur1(double qteTempsJoueur1) {
		this.qteTempsJoueur1 = qteTempsJoueur1;
	}

	public double getQteTempsJoueur2() {
		return qteTempsJoueur2;
	}

	public void setQteTempsJoueur2(double qteTempsJoueur2) {
		this.qteTempsJoueur2 = qteTempsJoueur2;
	}
	
	public int getQteDmgDonneJoueur1() {
		return qteDmgDonneJoueur1;
	}

	public void setQteDmgDonneJoueur1(int qteDmgDonneJoueur1) {
		this.qteDmgDonneJoueur1 = qteDmgDonneJoueur1;
	}

	public int getQteDmgDonneJoueur2() {
		return qteDmgDonneJoueur2;
	}

	public void setQteDmgDonneJoueur2(int qteDmgDonneJoueur2) {
		this.qteDmgDonneJoueur2 = qteDmgDonneJoueur2;
	}
	
	

}
