package generateurtankem;

public class Utilisateur {
	private String username;
	private String nom;
	private String prenom;
	private String mot_de_passe;
	private int id_questionA;
	private String reponse_a;
	private int id_questionB;
	private String reponse_b;
	
	public Utilisateur(String username, String nom, String prenom,
			String mot_de_passe, int id_questionA, String reponse_a,
			int id_questionB, String reponse_b) {
		this.username = username;
		this.nom = nom;
		this.prenom = prenom;
		this.mot_de_passe = mot_de_passe;
		this.id_questionA = id_questionA;
		this.reponse_a = reponse_a;
		this.id_questionB = id_questionB;
		this.reponse_b = reponse_b;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMot_de_passe() {
		return mot_de_passe;
	}
	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}
	public int getId_questionA() {
		return id_questionA;
	}
	public void setId_questionA(int id_questionA) {
		this.id_questionA = id_questionA;
	}
	public String getReponse_a() {
		return reponse_a;
	}
	public void setReponse_a(String reponse_a) {
		this.reponse_a = reponse_a;
	}
	public int getId_questionB() {
		return id_questionB;
	}
	public void setId_questionB(int id_questionB) {
		this.id_questionB = id_questionB;
	}
	public String getReponse_b() {
		return reponse_b;
	}
	public void setReponse_b(String reponse_b) {
		this.reponse_b = reponse_b;
	}
	
}
