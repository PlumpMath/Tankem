package _Tankem_Gestion_du_Compte;

public class User {
	
	private int id;
	private String username;
	private String nom;
	private String prenom;
	private String password;
	private int idQuestionA;
	private String reponseA;
	private int idQuestionB;
	private String reponseB;
	private int r;
	private int g;
	private int b;
	public User(int id, String username, String nom, String prenom,
			String password, int idQuestionA, String reponseA, int idQuestionB,
			String reponseB, double r,double g, double b) {
		
		this.id = id;
		this.username = username;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.idQuestionA = idQuestionA;
		this.reponseA = reponseA;
		this.idQuestionB = idQuestionB;
		this.reponseB = reponseB;
		this.r = (int) ((r*255)/1);
		this.g = (int) ((g*255)/1);
		this.b = (int) ((b*255)/1);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIdQuestionA() {
		return idQuestionA;
	}
	public void setIdQuestionA(int idQuestionA) {
		this.idQuestionA = idQuestionA;
	}
	public String getReponseA() {
		return reponseA;
	}
	public void setReponseA(String reponseA) {
		this.reponseA = reponseA;
	}
	public int getIdQuestionB() {
		return idQuestionB;
	}
	public void setIdQuestionB(int idQuestionB) {
		this.idQuestionB = idQuestionB;
	}
	public String getReponseB() {
		return reponseB;
	}
	public void setReponseB(String reponseB) {
		this.reponseB = reponseB;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}

}
