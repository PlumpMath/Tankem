package generateurtankem;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class CadrePrincipal extends JFrame {

	private JPanel contentPane;

	private Generateur generateur;
	private Vector<Integer> listeIdNiveau;

	private Vector<Integer> listeIdUsers;
	private JLabel lblVousAllezGnrer;
	private JButton boutonStart;
	private JLabel labelStatut;
	private JLabel labelAttendre;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadrePrincipal frame = new CadrePrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadrePrincipal() {
		setTitle("Tankem Generateur");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 153);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "GEEEEEE-GEEEEENRATEUR", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 501, 104);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblVousAllezGnrer = new JLabel("Vous allez g\u00E9n\u00E9rer 30 joueurs, 20 niveaux et 50 parties.");
		lblVousAllezGnrer.setBounds(109, 25, 381, 14);
		panel.add(lblVousAllezGnrer);
		
		boutonStart = new JButton("Start");
		boutonStart.setBounds(10, 63, 89, 23);
		panel.add(boutonStart);
		labelStatut = new JLabel("READY!");
		labelStatut.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 25));
		labelStatut.setForeground(Color.GREEN);
		labelStatut.setBounds(109, 63, 382, 23);
		panel.add(labelStatut);
		
		labelAttendre = new JLabel("");
		labelAttendre.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		labelAttendre.setBounds(10, 128, 454, 23);
		panel.add(labelAttendre);
		
		generateur = new Generateur();
		for (int i =0; i<20;i++)
		{
			System.out.println(generateur.genererNombre(0, 3));
		}
		
	    //Connexion à la DB dès le début.
		if(DAOOracle.connecter())
		{
			System.out.println("Vous êtes connecté à la BD.");
		}
		else
		{
			JOptionPane.showMessageDialog(CadrePrincipal.this, "Erreur de connexion! Vérifiez votre connexion.");
			System.exit(0);

		}

		boutonStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				labelStatut.setForeground(Color.BLUE);
				labelAttendre.setText("Veuillez patienter....");
				boutonStart.setEnabled(false);
				try {
					creerLesUtilisateurs(30);
					creerLesNiveaux(20);
					creerLesParties(50);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				labelStatut.setForeground(Color.GREEN);
				labelStatut.setText("Merci!La création est terminée.");
				labelAttendre.setText("");

			}
			
		});
		
	}
	
	public  void creerLesNiveaux(int nbCreation) throws SQLException
	{
		labelStatut.setText("Création des Niveaux...");
		//Jai besoin de tous les id existants pour generer des niveaux
		listeIdUsers = new Vector<Integer>();
		listeIdUsers = DAOOracle.chercherIdUser();
		for (int i=0;i<nbCreation;i++)
		{
			//Créer le niveau
			Niveau unNiveau = generateur.genererUnNiveau();
			int nbRandListe= generateur.genererNombre(0, listeIdUsers.size());
			int idUser = listeIdUsers.get(Math.abs(nbRandListe-1));
			DAOOracle.ajouterNiveau(unNiveau, idUser);
			
			//trouver son id
			int idNiveau = DAOOracle.trouverIdNiveau();
			
			int compteur =0;
			//Pour chaque case (afin d'eviter la duplication)
			for (int m=0; m< unNiveau.getGrosseurX();m++)
				for (int n=0; n< unNiveau.getGrosseurY();n++)
				{
					//Changer de temps en temps la case.
					int unNombre = generateur.genererNombre(0, 12);
					if (unNombre%3==0)
					{
						Case uneCase = generateur.genererUneCase(idNiveau, m,n);
						DAOOracle.ajouterCase(uneCase);
						compteur++;
					}


				}
			//Creer les Tanks
			boolean valideDifferent= false;
			//Les 2 tanks ne doivent pas occuper la meme case...
			while (!valideDifferent)
			{
				int posX1 = generateur.genererNombre(0, unNiveau.getGrosseurX()-2);
				int posY1 = generateur.genererNombre(0,unNiveau.getGrosseurX()-2);
				int posX2 = generateur.genererNombre(0, unNiveau.getGrosseurX()-2);
				int posY2 = generateur.genererNombre(0, unNiveau.getGrosseurX()-2);
				//Il faut juste que la position soit différente
				if (posX1!= posX2 || posY1 != posY2)
				{
					valideDifferent=true;
					Tank tank1= generateur.genererUnTank(idNiveau,posX1 , posY1);
					Tank tank2= generateur.genererUnTank(idNiveau,posX2 , posY2);
					DAOOracle.ajouterTank(tank1, idNiveau);
					DAOOracle.ajouterTank(tank2, idNiveau);

				}
			}//while
			
		}//chaque niveau
	}//methode
	
	public  void creerLesUtilisateurs(int nbCreation) throws SQLException
	{
		labelStatut.setText("Création des usagers...");

		for (int i=0;i<nbCreation;i++)
		{

			Utilisateur unUtilisateur = generateur.genererUser();
			Vector<Integer> liste = generateur.genererListeArme();
			DAOOracle.enregistrerUser(unUtilisateur,liste);
		}
	}
	public  void creerLesParties(int nbCreation) throws SQLException
	{
		labelStatut.setText("Création des parties...");

		//Jai besoin de tous les id users existants pour generer des parties
		listeIdUsers = new Vector<Integer>();
		listeIdUsers = DAOOracle.chercherIdUser();
		//Jai besoin de tous les id niveaux existants pour generer des parties
		listeIdNiveau = new Vector<Integer>();
		listeIdNiveau = DAOOracle.chercherIdNiveaux();
		
		//Pour chaque niveau
		for (int i = 0; i<nbCreation;i++)
		{

			//Une partie dure 60 secondes.
			int tempsPartie = 60;
			int idNiveau = listeIdNiveau.get(generateur.genererNombre(0, listeIdNiveau.size()-1));
			int idUtilisateur1=0;
			int idUtilisateur2=0;
			boolean valideDifferent= false;
		
		//Les 2 utilisateurs ne doivent pas etre identique...
			while (!valideDifferent)
			{
				//Id du premier user
				int nbRandListe1= generateur.genererNombre(0, listeIdUsers.size()-1);
				int idUser1 = listeIdUsers.get(nbRandListe1);
				//Id du deuxieme user
				int nbRandListe2= generateur.genererNombre(0, listeIdUsers.size()-1);
				int idUser2 = listeIdUsers.get(nbRandListe2);
				
				if (idUser1!=idUser2)
				{
					valideDifferent=true;
					idUtilisateur1 = idUser1;
					idUtilisateur2 = idUser2;
				}
			}
			Niveau unNiveau = DAOOracle.chercherUnNiveau(idNiveau);
			int grosseurX = unNiveau.getGrosseurX();
			int grosseurY = unNiveau.getGrosseurY();
			CaseThermique[][] tabCase1 = new CaseThermique[grosseurX][grosseurY];
			CaseThermique[][] tabCase2 = new CaseThermique[grosseurX][grosseurY];
			int[] tabArmes1 = new int[6];
			int[] tabArmes2 = new int[6];
			for (int m = 0; m<grosseurX;m++)
				for (int n = 0; n<grosseurY;n++)
				{
					tabCase1[m][n] = new CaseThermique(m, n,0, 0, 0);
					tabCase2[m][n] = new CaseThermique(m, n,0, 0, 0);
				}
			//temps secondes
			int secondes = 60;
			while (secondes>0)
			{
				int posX1 = generateur.genererNombre(0, unNiveau.getGrosseurX()-1);
				int posY1 = generateur.genererNombre(0, unNiveau.getGrosseurY()-1);
				int posX2 = generateur.genererNombre(0, unNiveau.getGrosseurX()-1);
				int posY2 = generateur.genererNombre(0, unNiveau.getGrosseurY()-1);
				tabCase1[posX1][posY1].setTempsDessus(tabCase1[posX1][posY1].getTempsDessus()+1);
				tabCase2[posX2][posY2].setTempsDessus(tabCase2[posX2][posY2].getTempsDessus()+1);
				
				secondes--;
				int nbRand = generateur.genererNombre(0, 12);
				{
					if (nbRand%2==0)
					{
						tabCase1[posX1][posY1].setQteDomageFait(tabCase1[posX1][posY1].getQteDomageFait()+9);
						tabArmes1[generateur.genererNombre(0, 5)]+=9;
						tabCase2[posX2][posY2].setQteDomagePris(tabCase2[posX2][posY2].getQteDomagePris()+9);		
					}
					if (nbRand%2==0)
					{
						tabCase2[posX1][posY1].setQteDomageFait(tabCase2[posX1][posY1].getQteDomageFait()+9);
						tabArmes2[generateur.genererNombre(0, 5)]+=9;
						tabCase1[posX2][posY2].setQteDomagePris(tabCase1[posX2][posY2].getQteDomagePris()+9);		
					}
					
				}
				
			}
			int idGagnant = idUtilisateur1;
			if (generateur.genererNombre(0,4)%2==0)
			{
			idGagnant = idUtilisateur2;

			}
			//Créer la partie
			DAOOracle.ajouterPartie(idGagnant, idNiveau);

			
			
			//Trouver id de la partie
			int idPartie = DAOOracle.chercherDernierePartie();
			System.out.println("idPartie:"+idPartie);
			System.out.println("Nouvelle aprtie!:"+idPartie);

			
			
			//Ajouter case thermique dans la bds
			for (int m = 0; m<grosseurX;m++)
				for (int n = 0; n<grosseurY;n++)
				{
					DAOOracle.ajouterCaseThermique(tabCase1[m][n], idPartie);
					DAOOracle.ajouterCaseThermique(tabCase2[m][n], idPartie);
				}
			
			
			DAOOracle.lesJoursImplique(idPartie, idUtilisateur1);
			DAOOracle.lesJoursImplique(idPartie, idUtilisateur2);
			
			//Pour les armes
			for (int k=0; k<6;k++)
			{
				DAOOracle.updateArmurie(generateur.genererNombre(2, 10),k+1,tabArmes1[k],idUtilisateur1);
				DAOOracle.updateArmurie(generateur.genererNombre(2, 10),k+1,tabArmes2[k],idUtilisateur2);

			}
			
			for (int m = 0; m<grosseurX;m++)
				for (int n = 0; n<grosseurY;n++)
				{
					int idCase = DAOOracle.chercherIdCaseTherm(idPartie,m,n);
					DAOOracle.ajouterJoueurCase(tabCase1[m][n],idCase,idPartie,idUtilisateur1);
					DAOOracle.ajouterJoueurCase(tabCase2[m][n],idCase,idPartie,idUtilisateur2);
				}
	
			
		}//for chaque niveau
	}
}
