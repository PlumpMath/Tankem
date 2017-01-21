package _Tankem_Gestion_du_Compte;

public class Question {
	
	String question;
	int id;
	
	
	public Question(int id,String question) {
		this.question = question;
		this.id = id;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	

}
