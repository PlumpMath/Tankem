package tankemNiveau;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;

import java.awt.Font;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;



import java.sql.Statement;


//TANKEM Level Builder par Steven Tan et Vincent Aubry
public class Interface extends JFrame {

	private JPanel contentPane;
	private JPanel grilleNiveau; 
	private JLabel [][] tab;    //tableau de JLabels
	private GridLayout gridLayout1;
	private JButton boutonGrosseurPetitX;
	private Ecouteur ec;
	private EcouteurMouse ecM;
	private EcouteurKey ecK;
	private EcouteurWindow ecW;
	private JButton boutonGrosseurGrandX;
	private JButton boutonGrosseurPetitY;
	private JButton boutonGrosseurGrandY;
	private JButton boutonTempsMoinsMin;
	private JButton boutonTempsMoinsMax;
	private JButton boutonTempsPlusMin ;
	private JButton boutonTempsPlusMax;
	private JPanel panelOptions;
	private JLabel lblTempsMin;
	private JLabel lblTempsMax;
	private JTextField champTempsMin;
	private JTextField champTempsMax;
	private JTextField champY;
	private JTextField champX;
	private JLabel lblStatus;
	private JComboBox comboStatus;
	private JLabel lblTitre;
	private JTextField champTitre;
	private JLabel lblDate;
	private JLabel labelDate;
	private JButton boutonSauvegarder;
	
	private Date date;
	static Color couleurPlancher = new Color(192,95,42);
	static Color couleurMur = new Color(127,21,21);
	static Color couleurMurAnime = new Color(26,117,114);
	static Color couleurMurAnimeInv = new Color(49,250,240);
	static Color couleurVide = new Color(124,124,124);
	static Color couleurTank1 = new Color(50,255,50);
	static Color couleurTank2 = new Color(255,50,50);
	
	static String textPlancher="Plancher";
	static String textMur="Mur";
	static String textMurAnime="Animé";
	static String textMurAnimeInv="Animé Inv.";
	

	static int positionY=0;
	static int positionX=0;
	static int positionY2=0;
	static int positionX2=0;
	static int caseX = 12;
	static int caseY = 12;
	static int tempsMin=20;
	static int tempsMax=30;
	static String username="";
	private JLabel labelConnexion;
	private Tank tank1;
	private Tank tank2;
	private JLabel labelInfoSup;
	private JLabel lblNewLabel_2;
	private JLabel lblTank;
	private DialogueLogin dialogue;
	private JLabel labelUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
	            	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interface() {
		setTitle("Tankem!! Level Builder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 995, 752);
		contentPane = new JPanel();
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		grilleNiveau = new JPanel();
		grilleNiveau.setBounds(366, 102, 600, 600);
		contentPane.add(grilleNiveau);
		
		gridLayout1 = new GridLayout();
		grilleNiveau.setLayout(gridLayout1);
	    gridLayout1.setColumns(12);
	    gridLayout1.setRows(12);		
	    
	    ec = new Ecouteur();
	    ecM= new EcouteurMouse();
	    ecK = new EcouteurKey();
		ecW = new EcouteurWindow();
		addWindowListener(ecW);
		
	    panelOptions = new JPanel();
	    panelOptions.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	    panelOptions.setBounds(10, 102, 346, 536);
	    contentPane.add(panelOptions);
	    panelOptions.setLayout(null);
	    boutonGrosseurPetitX = new JButton("-");
	    boutonGrosseurPetitX.setBounds(159, 53, 46, 23);
	    panelOptions.add(boutonGrosseurPetitX);
	    
	    boutonGrosseurGrandY = new JButton("+");
	    boutonGrosseurGrandY.setBounds(290, 83, 46, 23);
	    panelOptions.add(boutonGrosseurGrandY);
	    
	    boutonGrosseurPetitY = new JButton("-");
	    boutonGrosseurPetitY.setBounds(159, 87, 46, 23);
	    panelOptions.add(boutonGrosseurPetitY);
	    
	    boutonGrosseurGrandX = new JButton("+");
	    boutonGrosseurGrandX.setBounds(290, 53, 46, 23);
	    panelOptions.add(boutonGrosseurGrandX);
	    
	    JLabel lblGrosseurX = new JLabel("Grosseur X:");
	    lblGrosseurX.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblGrosseurX.setFont(new Font("Tahoma", Font.BOLD, 19));
	    lblGrosseurX.setBounds(10, 55, 143, 19);
	    panelOptions.add(lblGrosseurX);
	    
	    JLabel lblGrosseurY = new JLabel("Grosseur Y:");
	    lblGrosseurY.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblGrosseurY.setFont(new Font("Tahoma", Font.BOLD, 19));
	    lblGrosseurY.setBounds(10, 85, 143, 19);
	    panelOptions.add(lblGrosseurY);
	    
	    JLabel labelMenu = new JLabel("Options");
	    labelMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    labelMenu.setBounds(140, 0, 118, 35);
	    panelOptions.add(labelMenu);
	    
	    lblTempsMin = new JLabel("Temps min:");
	    lblTempsMin.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblTempsMin.setFont(new Font("Tahoma", Font.BOLD, 19));
	    lblTempsMin.setBounds(10, 122, 143, 19);
	    panelOptions.add(lblTempsMin);
	    
	    lblTempsMax = new JLabel("Temps max:");
	    lblTempsMax.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblTempsMax.setFont(new Font("Tahoma", Font.BOLD, 19));
	    lblTempsMax.setBounds(10, 152, 143, 19);
	    panelOptions.add(lblTempsMax);
	    
	    boutonTempsMoinsMin = new JButton("-");

	    boutonTempsMoinsMin.setBounds(159, 124, 46, 23);
	    panelOptions.add(boutonTempsMoinsMin);
	    
	    boutonTempsMoinsMax = new JButton("-");

	    boutonTempsMoinsMax.setBounds(159, 154, 46, 23);
	    panelOptions.add(boutonTempsMoinsMax);
	    
	    boutonTempsPlusMin = new JButton("+");


	    boutonTempsPlusMin.setBounds(290, 124, 46, 23);
	    panelOptions.add(boutonTempsPlusMin);
	    
	    boutonTempsPlusMax = new JButton("+");

	    boutonTempsPlusMax.setBounds(290, 154, 46, 23);
	    panelOptions.add(boutonTempsPlusMax);
	    
	    champTempsMin = new JTextField();
	    champTempsMin.setText("30");
	    champTempsMin.setHorizontalAlignment(SwingConstants.CENTER);
	    champTempsMin.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    champTempsMin.setBounds(215, 125, 65, 20);
	    panelOptions.add(champTempsMin);
	    champTempsMin.setColumns(10);
	    
	    champTempsMax = new JTextField();
	    champTempsMax.setText("20");
	    champTempsMax.setHorizontalAlignment(SwingConstants.CENTER);
	    champTempsMax.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    champTempsMax.setColumns(10);
	    champTempsMax.setBounds(215, 155, 65, 20);
	    panelOptions.add(champTempsMax);
	    
	    champY = new JTextField();
	    champY.setHorizontalAlignment(SwingConstants.CENTER);
	    champY.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    champY.setText("12");
	    champY.setColumns(10);
	    champY.setBounds(215, 84, 65, 20);
	    panelOptions.add(champY);
	    
	    champX = new JTextField();
	    champX.setHorizontalAlignment(SwingConstants.CENTER);
	    champX.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    champX.setText("12");
	    champX.setColumns(10);
	    champX.setBounds(215, 54, 65, 20);
	    panelOptions.add(champX);
	    
	    lblStatus = new JLabel("Status:");
	    lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblStatus.setFont(new Font("Tahoma", Font.BOLD, 19));
	    lblStatus.setBounds(10, 196, 143, 19);
	    panelOptions.add(lblStatus);
	    
	    String[] liste = new String[]{"Public","Équipe","Privé","Inactif"};
	    comboStatus = new JComboBox(liste);
	    comboStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    comboStatus.setBounds(159, 192, 177, 28);
	    panelOptions.add(comboStatus);
	    
	    lblTitre = new JLabel("Titre:");
	    lblTitre.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblTitre.setFont(new Font("Tahoma", Font.BOLD, 19));
	    lblTitre.setBounds(62, 292, 143, 19);
	    panelOptions.add(lblTitre);
	    
	    champTitre = new JTextField();
	    champTitre.setText("Un Titre");
	    champTitre.setHorizontalAlignment(SwingConstants.CENTER);
	    champTitre.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    champTitre.setColumns(10);
	    champTitre.setBounds(10, 323, 326, 20);
	    panelOptions.add(champTitre);
	    
	    lblDate = new JLabel("Date:");
	    lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblDate.setFont(new Font("Tahoma", Font.BOLD, 19));
	    lblDate.setBounds(62, 366, 143, 19);
	    panelOptions.add(lblDate);
	    
	    labelDate = new JLabel("La date");
	    labelDate.setHorizontalAlignment(SwingConstants.CENTER);
	    labelDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    labelDate.setBounds(72, 397, 208, 23);
	    panelOptions.add(labelDate);
	    
	    boutonSauvegarder = new JButton("Sauvegarder");
	    boutonSauvegarder.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    boutonSauvegarder.setBounds(10, 431, 330, 99);
	    panelOptions.add(boutonSauvegarder);
	    
	    boutonGrosseurGrandX.addActionListener(ec);
	    boutonGrosseurPetitY.addActionListener(ec);
	    boutonGrosseurGrandY.addActionListener(ec);
	    boutonGrosseurPetitX.addActionListener(ec);
	    boutonSauvegarder.addActionListener(ec);
	    boutonTempsMoinsMax.addActionListener(ec);
	    boutonTempsPlusMax.addActionListener(ec);
	    boutonTempsMoinsMin.addActionListener(ec);
	    boutonTempsPlusMin.addActionListener(ec);

	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	    date = new Date();
	    labelDate.setText(dateFormat.format(date));
	   //Création des cases du jeu
	    tab = new JLabel[12][12];   // création du tableau de JLabel
	      for ( int i = 11; i >-1 ; i-- )
	    {
	  	    for (int j = 0; j <12; j++ )
	        {
	        tab[i][j] = new JLabel(); // création du JLabel
		    tab[i][j].setBorder(BorderFactory.createRaisedSoftBevelBorder());
	        grilleNiveau.add(tab[i][j]);  // ajouter au Panel
	        tab[i][j].setOpaque(true);
	        tab[i][j].addMouseListener(ecM);;  // ajouter l'écouteur aux sources
	        tab[i][j].setBackground(couleurPlancher);
	        tab[i][j].setText("Plancher");
		    tab[i][j].setHorizontalAlignment(SwingConstants.CENTER);
		    tab[i][j].setFont(new Font("Tahoma", Font.ITALIC, 10));

	        }
	    }

		
	    champTempsMax.setText(Integer.toString(tempsMax));
	    champTempsMin.setText(Integer.toString(tempsMin));
	    
	    JLabel lblNewLabel = new JLabel("Tankem!! Cr\u00E9ation de niveau");
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblNewLabel.setBounds(39, 57, 396, 50);
	    contentPane.add(lblNewLabel);
	    
	    JPanel panelAide = new JPanel();
	    panelAide.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	    panelAide.setBackground(Color.LIGHT_GRAY);
	    panelAide.setBounds(366, 11, 603, 81);
	    contentPane.add(panelAide);
	    panelAide.setLayout(null);
	    
	    JLabel lblNewLabel_1 = new JLabel("Appuyer sur les touches \"W-A-S-D\" pour naviguer. Chiffre 1 et 2 pour placer les Tanks.");
	    lblNewLabel_1.setBounds(10, 11, 583, 14);
	    panelAide.add(lblNewLabel_1);
	    
	    JLabel lblAppuyerSurLes = new JLabel("Appuyer sur les touches \"Espace\" ou \"CTRL\" (inverse) pour changer de type de case.");
	    lblAppuyerSurLes.setBounds(10, 36, 583, 14);
	    panelAide.add(lblAppuyerSurLes);
	    
	    JLabel lblOuCliquerSimplement = new JLabel("Ou cliquer simplement sur une case pour la changer!");
	    lblOuCliquerSimplement.setBounds(10, 61, 583, 14);
	    panelAide.add(lblOuCliquerSimplement);
	    
	    labelConnexion = new JLabel("Connexion:");
	    labelConnexion.setFont(new Font("Tahoma", Font.BOLD, 16));
	    labelConnexion.setForeground(Color.GREEN);
	    labelConnexion.setBounds(39, 16, 274, 14);
	    contentPane.add(labelConnexion);
	    
	    labelInfoSup = new JLabel("**Avez-vous plac\u00E9 vos 2 tanks?");
	    labelInfoSup.setBounds(20, 649, 274, 14);
	    contentPane.add(labelInfoSup);
	    
	    lblNewLabel_2 = new JLabel("TANK1");
	    lblNewLabel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
	    lblNewLabel_2.setFont(new Font("Tahoma", Font.ITALIC, 11));
	    lblNewLabel_2.setOpaque(true);
	    lblNewLabel_2.setBackground(couleurTank2);
	    lblNewLabel_2.setBounds(57, 670, 46, 32);
	    contentPane.add(lblNewLabel_2);
	    
	    lblTank = new JLabel("TANK2");
	    lblTank.setOpaque(true);
	    lblTank.setFont(new Font("Tahoma", Font.ITALIC, 11));
	    lblTank.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
	    lblTank.setBackground(couleurTank1);
	    lblTank.setBounds(148, 670, 46, 32);
	    contentPane.add(lblTank);
	    
	    labelUsername = new JLabel("Bienvenue:");
	    labelUsername.setBounds(39, 44, 227, 14);
	    contentPane.add(labelUsername);

	    grilleNiveau.addKeyListener(ecK);
	    
		tank2 = new Tank(0,0,couleurTank1);
		tank1 = new Tank(5,5,couleurTank2);
	    
	    resetInterface();
	    bougerCase();
	    
	    

	    
	    //Connexion à la DB dès le début.
		if(Operations.connecter())
		{
			labelConnexion.setText("Vous êtes connecté à la BD.");
		}
		else
		{
			JOptionPane.showMessageDialog(Interface.this, "Erreur de connexion! Vérifiez votre connexion.");
			System.exit(0);

		}
		
		//Faire un dialogue de menu.
		dialogue = new DialogueLogin(Interface.this,"Login", true);
		dialogue.setVisible(true);
		if(dialogue.isContinuer)
		{
			System.out.println("On continue");
			labelUsername.setText("Bienvenue "+dialogue.username);
			username= dialogue.username;
		}
		else
		{
			System.out.println("On Dispose");

			System.exit(0);
		}
	    
	}
//ÉCOUTEUR POUR LES BOUTONS ----------- ----------------------------------------------------------------
	private class Ecouteur implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			if (ae.getSource() == boutonGrosseurPetitX)
			{
				if(caseX>6)
				{
					caseX--;
					gridLayout1.setColumns(caseX);
					ajusterGrid("xMoins");
				}
			}
			if (ae.getSource() == boutonGrosseurGrandX)
			{
				if (caseX<12)
				{
					caseX++;
					ajusterGrid("xPlus");
				}
			}
			if (ae.getSource() == boutonGrosseurGrandY)
			{
				if (caseY<12)
				{
					caseY++;
					ajusterGrid("yPlus");
				}
			}
			if (ae.getSource() == boutonGrosseurPetitY)
			{
				if (caseY>6)
				{
					caseY--;
					ajusterGrid("yMoins");
				}
			}
			if (ae.getSource()==boutonTempsMoinsMax)
			{
				if (tempsMax>0)
				tempsMax--;
			}
			if (ae.getSource()==boutonTempsPlusMax)
			{
				tempsMax++;
			}
			if (ae.getSource()==boutonTempsPlusMin)
			{
				tempsMin++;
			}
			if (ae.getSource()==boutonTempsMoinsMin)
			{
				if (tempsMin>0)

				tempsMin--;
			}
			champX.setText(Integer.toString(caseX));
			champY.setText(Integer.toString(caseY));
		    champTempsMax.setText(Integer.toString(tempsMax));
		    champTempsMin.setText(Integer.toString(tempsMin));
		    
	//Quand on veut sauvegarder un niveau.... ---------------------------------------------------
		    if (ae.getSource()== boutonSauvegarder)
			{
				 if(tank2.getPositionX()+1<=caseY && tank2.getPositionY()+1<=caseX &&
						 tank1.getPositionX()+1<=caseY && tank1.getPositionY()+1<=caseX)
				 {
					//Créer un niveau
					Niveau unNiveau = new Niveau(champTitre.getText(),
													Integer.parseInt(champTempsMin.getText()),
													Integer.parseInt(champTempsMax.getText()),
													date,
													comboStatus.getSelectedIndex()+1,
													Integer.parseInt(champX.getText()),
													Integer.parseInt(champY.getText()));
					//ajouter le niveau
					Operations.ajouterNiveau(unNiveau, username);
					System.out.println("Un niveau a été ajouté!");
						
					//Créer une liste de cases spéciales
					Vector<Case> listeCase = new Vector<Case>();
					//Si case est spéciale, faut déterminer son type!
					int idNiveau=0;
						try {
							idNiveau = Operations.trouverIdNiveau();
		
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						System.out.println("Trouver le idNiveau:"+(idNiveau-1));
						for (int i = 0; i <12; i++ )
					    {
					      for ( int j = 0; j<12 ; j++ )
					      {
					    	int indexType =0;
					        if(tab[i][j].getText()==textMur)
					        	indexType=1;
					        
					        else if(tab[i][j].getText()==textMurAnime)
					        	indexType=2;
					        
					        else if(tab[i][j].getText()==textMurAnimeInv)
					        	indexType=3;
					        //Si c'est une case active....
					        if(tab[i][j].getBackground()!=couleurPlancher && tab[i][j].getBackground()!=couleurVide
					        		&& tab[i][j].getBackground()!=couleurTank1 && tab[i][j].getBackground()!=couleurTank2)
					        {
					        	Case uneCase = new Case(idNiveau-1,j,i,indexType);
					        	listeCase.add(uneCase);
					        	System.out.println("une case ajoute dans la liste!");
					        }
					      }
					    }
						//Créer les tanks
						Operations.ajouterTank(tank1, idNiveau-1);
						Operations.ajouterTank(tank2, idNiveau-1);

						
						//Ajouter chaque case dans la bd.
						for (Case uneCase: listeCase)
						{
						Operations.ajouterCase(uneCase);
						System.out.println("Une nouvelle case a été ajouté");
						}
						
						JOptionPane.showMessageDialog(Interface.this, "Bravo! Création du niveau avec succès!");
						resetInterface();
				 }
				 else
				 {
						JOptionPane.showMessageDialog(Interface.this, "Erreur!! Placez les 2 Tanks!");

				 }
			}
			grilleNiveau.requestFocus();
		}
	}
//Fonction qui ajuste la grille si on appuie sur les boutons du menu.
	 public void ajusterGrid(String effet)
	 {
		 //Ajouter/supprimer une rangée ou colonne
		 if (effet=="xMoins")
		 {
			 for (int i = 0; i <12; i++ )
		      {
		        	tab[i][caseX].setBackground(couleurVide);
		        	tab[i][caseX].setText("");
		      }
		 }
		 else if (effet=="xPlus")
		 {
			 for (int i = 0; i <caseY; i++ )
		      {
				 tab[i][caseX-1].setBackground(couleurPlancher);
				 tab[i][caseX-1].setText(textPlancher);
		      }
		 }
		 else if (effet=="yMoins")
		 {
			 for (int i = 0; i <12; i++ )
		      {
		        	tab[caseY][i].setBackground(couleurVide);
		        	tab[caseY][i].setText("");
		      }
		 }
		 else if (effet=="yPlus")
		 {
			 for (int i = 0; i <caseX; i++ )
		      {
				 tab[caseY-1][i].setBackground(couleurPlancher);
				 tab[caseY-1][i].setText(textPlancher);
	
		      }
	     }
		 //Si on efface la rangée/colonne que la case focus est dessus...
		 if (caseX<positionX+1)
		 {
			 positionX--;
			 bougerCase();
		 }
		 if (caseY<positionY+1)
		 {
			 positionY--;
			 bougerCase();
		 }
	 }

	 private class EcouteurMouse implements MouseListener
		{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		//Quand on clique sur une case, mettre la position du curseur à cette case
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			grilleNiveau.requestFocus();
			for (int i =0; i<12;i++)
				for (int j=0;j<12;j++)
				{
					if (e.getSource()== tab[i][j])
					{
						//Pour ne pas cliquer sur les cases vides...
						if (tab[i][j].getBackground()!=couleurVide)
						{
							positionY=i;
							positionX=j;
							bougerCase();
						}
						changerCase(i,j);
						
					}
				}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		}
//ECOUTEUR POUR LES KEYPRESS -------------------------------------------------------------------------------
	 private class EcouteurKey implements KeyListener
	 {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
			//Bouger la case.
			if (e.getKeyCode()==KeyEvent.VK_S)
			{
				if(positionY>0)
					positionY--;
				bougerCase();

			}
			else if (e.getKeyCode()==KeyEvent.VK_A)
			{
				if(positionX>0)
					positionX--;
				bougerCase();
			}
			else if (e.getKeyCode()==KeyEvent.VK_W)
			{	
				if(positionY<caseY-1)
					positionY++;
				bougerCase();
			}
			else if (e.getKeyCode()==KeyEvent.VK_D)
			{
				if(positionX<caseX-1)
					positionX++;
				bougerCase();
			}
			else if (e.getKeyCode()==KeyEvent.VK_SPACE)
			{
				changerCase(positionY, positionX);
			}
			else if (e.getKeyCode()==KeyEvent.VK_CONTROL)
			{
				changerCaseInv(positionY, positionX);
			}
			else if (e.getKeyCode()==KeyEvent.VK_2)
			{
				changerPosTank1(positionY, positionX);
			}
			else if (e.getKeyCode()==KeyEvent.VK_1)
			{
				changerPosTank2(positionY, positionX);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		 
	 }
	private class EcouteurWindow implements WindowListener
	{

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
//******************************************************************************************
//Fermer la connexion à la fermature de l'application ---------------------------------------------------
			Operations.seDeconnecter();
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	 //Fonction pour faire bouger la case focus.
	 private void bougerCase()
	 {
		 tab[positionY2][positionX2].setBorder(BorderFactory.createRaisedSoftBevelBorder());
		 positionY2= positionY;
		 positionX2=positionX;
		 tab[positionY][positionX].setBorder(BorderFactory.createLoweredSoftBevelBorder());
	 }
	 
	 private void changerPosTank1(int i, int j)
	 {//On ne peut pas remplacer un tank par un autre tank.
		 if (i==tank2.getPositionX() && j ==tank2.getPositionY())
		 {
			 System.out.println("Peut pas déplacer sur un Tank");
		 }
		 else
			 {
			//Remplacer l'ancienne case soit par une case vide, ou une case plancher.
			 if(tank1.getPositionX()+1<=caseY && tank1.getPositionY()+1<=caseX)
			 {
				 System.out.println("Position xy du tank:" + tank1.getPositionX()+":"+tank1.getPositionY());
				 System.out.println("Position des cases:" + caseX+":"+caseY);
	
			
			if(tab[tank1.getPositionX()][tank1.getPositionY()].getText()==textPlancher)
			{
			 tab[tank1.getPositionX()][tank1.getPositionY()].setBackground(couleurPlancher);
			 tab[tank1.getPositionX()][tank1.getPositionY()].setText(textPlancher);
			}
			else if(tab[tank1.getPositionX()][tank1.getPositionY()].getText()==textMur)
			{
				 tab[tank1.getPositionX()][tank1.getPositionY()].setBackground(couleurMur);
				 tab[tank1.getPositionX()][tank1.getPositionY()].setText(textMur);
			}
			else if(tab[tank1.getPositionX()][tank1.getPositionY()].getText()==textMurAnime)
			{
				 tab[tank1.getPositionX()][tank1.getPositionY()].setBackground(couleurMurAnime);
				 tab[tank1.getPositionX()][tank1.getPositionY()].setText(textMurAnime);
			}
			else if(tab[tank1.getPositionX()][tank1.getPositionY()].getText()==textMurAnimeInv)
			{
				 tab[tank1.getPositionX()][tank1.getPositionY()].setBackground(couleurMurAnimeInv);
				 tab[tank1.getPositionX()][tank1.getPositionY()].setText(textMurAnimeInv);
			}
			 }
			 else
			 {
				 tab[tank1.getPositionX()][tank1.getPositionY()].setBackground(couleurVide);
				 tab[tank1.getPositionX()][tank1.getPositionY()].setText("");
			 }
			 tank1.setPositionX(i);
			 tank1.setPositionY(j);
			 tab[i][j].setBackground(couleurTank1);
		 }
	 }
	 
	 private void changerPosTank2(int i, int j)
	 {//On ne peut pas remplacer un tank par un autre tank.
		 if (i==tank1.getPositionX() && j ==tank1.getPositionY())
		 {
			 System.out.println("Peut pas déplacer sur un Tank");
		 }
		 else
			 {
			//Remplacer l'ancienne case soit par une case vide, ou une case plancher.
			 if(tank2.getPositionX()+1<=caseY && tank2.getPositionY()+1<=caseX)
			 {
				 System.out.println("Position xy du tank:" + tank2.getPositionX()+":"+tank2.getPositionY());
				 System.out.println("Position des cases:" + caseX+":"+caseY);
	
			
			if(tab[tank2.getPositionX()][tank2.getPositionY()].getText()==textPlancher)
			{
			 tab[tank2.getPositionX()][tank2.getPositionY()].setBackground(couleurPlancher);
			 tab[tank2.getPositionX()][tank2.getPositionY()].setText(textPlancher);
			}
			else if(tab[tank2.getPositionX()][tank2.getPositionY()].getText()==textMur)
			{
				 tab[tank2.getPositionX()][tank2.getPositionY()].setBackground(couleurMur);
				 tab[tank2.getPositionX()][tank2.getPositionY()].setText(textMur);
			}
			else if(tab[tank2.getPositionX()][tank2.getPositionY()].getText()==textMurAnime)
			{
				 tab[tank2.getPositionX()][tank2.getPositionY()].setBackground(couleurMurAnime);
				 tab[tank2.getPositionX()][tank2.getPositionY()].setText(textMurAnime);
			}
			else if(tab[tank2.getPositionX()][tank2.getPositionY()].getText()==textMurAnimeInv)
			{
				 tab[tank2.getPositionX()][tank2.getPositionY()].setBackground(couleurMurAnimeInv);
				 tab[tank2.getPositionX()][tank2.getPositionY()].setText(textMurAnimeInv);
			}
			 }
			 else
			 {
				 tab[tank2.getPositionX()][tank2.getPositionY()].setBackground(couleurVide);
				 tab[tank2.getPositionX()][tank2.getPositionY()].setText("");
			 }
			 tank2.setPositionX(i);
			 tank2.setPositionY(j);
			 tab[i][j].setBackground(couleurTank2);
		 }
	 }
	 
	 //Changer de case
	 private void changerCase(int i, int j)
	 {
		//Quand on clique sur une case, changer de type de case.
			if(tab[i][j].getBackground()==couleurPlancher)
			{
			tab[i][j].setBackground(couleurMur);
			tab[i][j].setText(textMur);
			}
			else if (tab[i][j].getBackground()== couleurMur)
			{
				tab[i][j].setBackground(couleurMurAnime);
				tab[i][j].setText(textMurAnime);
			}
			else if (tab[i][j].getBackground()== couleurMurAnime)
			{
				tab[i][j].setBackground(couleurMurAnimeInv);
				tab[i][j].setText(textMurAnimeInv);
			}
			else if (tab[i][j].getBackground()== couleurMurAnimeInv)
			{
				tab[i][j].setBackground(couleurPlancher);
				tab[i][j].setText(textPlancher);

			}
	 }
	 //IDEM que l'autre fonction.. mais faire l'inverse
	 private void changerCaseInv(int i, int j)
	 {
		//Quand on clique sur une case, changer de type de case.
			if(tab[i][j].getBackground()==couleurPlancher)
			{
				tab[i][j].setBackground(couleurMurAnimeInv);
				tab[i][j].setText(textMurAnimeInv);
			}
			else if (tab[i][j].getBackground()== couleurMur)
			{
				tab[i][j].setBackground(couleurPlancher);
				tab[i][j].setText(textPlancher);
			}
			else if (tab[i][j].getBackground()== couleurMurAnime)
			{
				tab[i][j].setBackground(couleurMur);
				tab[i][j].setText(textMur);
			}
			else if (tab[i][j].getBackground()== couleurMurAnimeInv)
			{
				tab[i][j].setBackground(couleurMurAnime);
				tab[i][j].setText(textMurAnime);

			}
	 }
	 
	 //Après la création d'un niveau, tout remettre les données par défaut.
	 private void resetInterface()
	 {
		 caseX=12;
		 caseY=12;
		 tempsMin=20;
		 tempsMax=30;
		 champTempsMax.setText(Integer.toString(tempsMax));
		 champTempsMin.setText(Integer.toString(tempsMin));
		 champX.setText(Integer.toString(caseX));
		 champY.setText(Integer.toString(caseY));
		 champTitre.setText("Un titre");
		 for (int i = 0; i <12; i++ )
		    for ( int j = 0; j <12 ; j++ )
		    {
			    tab[i][j].setBackground(couleurPlancher);
			    tab[i][j].setText(textPlancher);
			    if( i == tank1.getPositionX() && j == tank1.getPositionY())
			    {
			    	tab[i][j].setBackground(couleurTank1);
			    }
			    if( i == tank2.getPositionX() && j == tank2.getPositionY())
			    {
			    	tab[i][j].setBackground(couleurTank2);
			    }
		    }
		 bougerCase();
	 }
}
