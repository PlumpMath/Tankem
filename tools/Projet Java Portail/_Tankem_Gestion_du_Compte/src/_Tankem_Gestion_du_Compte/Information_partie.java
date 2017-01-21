package _Tankem_Gestion_du_Compte;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Vector;
import java.awt.Component;
import java.awt.CardLayout;

public class Information_partie extends JFrame {

	private JPanel contentPane;
	private JLabel lblInfoPartie;
	private JLabel lblNiveauJoue;
	private JPanel panelGagnant;
	private JPanel panelPerdant;
	private JLabel lblGagnant;
	private JLabel lblPerdant;
	private JLabel lblNomGagnant;
	private JLabel lblNomPerdant;
	private JPanel panelMode1;
	private JPanel panelMode2;
	private JRadioButton radio1Mode1;
	private JRadioButton radio1Mode2;
	private JRadioButton radio1Mode3;
	private JRadioButton radio1Mode4;
	private JRadioButton radio2Mode1;
	private JRadioButton radio2Mode2;
	private JRadioButton radio2Mode3;
	private JRadioButton radio2Mode4;
	private MapThermique panelMapThermique;
	private ButtonGroup groupeJoueur1;
	private ButtonGroup groupeJoueur2;
	private Vector<User> joueurImplique;
	private Vector<Integer> idDesJoueursEnOrdre;
	private Ecouteur ecouteur;
	private Vector<CaseThermique> lesCasesThermiques;
	private int dmgRecuJoueur1[][];
	private int dmgRecuJoueur2[][];
	private int dmgDonneJoueur1[][];
	private int dmgDonneJoueur2[][];
	private double tempsJoueur1[][];
	private double tempsJoueur2[][];
	private double pourcentdmgRecuJoueur1[][];
	private double pourcentdmgRecuJoueur2[][];
	private double pourcentdmgDonneJoueur1[][];
	private double pourcentdmgDonneJoueur2[][];
	private double pourcenttempsJoueur1[][];
	private double pourcenttempsJoueur2[][];
	private double aucuneDonne[][];
	private int x;
	private int y;
	private JLabel lblAucun;
	private JPanel PanelLegende;
	private JLabel lblJoueur1;
	private JLabel lblJoueur2;
	private JLabel lblNomX;
	private JLabel lblNomY;
	private JLabel lblLegende;
	private JLabel lblTitre;
	private String titreP1;
	private String titreP2;
	private JLabel lblLegendeIcon;
	private JLabel lblMaxX;
	private JLabel lblMaxY;
	private Page_Public pagePublic;
	String pattern;
	private DecimalFormat decimalFormat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Partie laPartie) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Information_partie frame = new Information_partie(laPartie);
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
	public Information_partie(Partie laPartie) {
		pattern = "###.##";
		decimalFormat= new DecimalFormat(pattern);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 724, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblInfoPartie = new JLabel("Information sur la partie");
		lblInfoPartie.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoPartie.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInfoPartie.setBounds(10, 11, 688, 30);
		contentPane.add(lblInfoPartie);
		
		lblNiveauJoue = new JLabel("Niveau jouer:");
		lblNiveauJoue.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNiveauJoue.setHorizontalAlignment(SwingConstants.CENTER);
		lblNiveauJoue.setBounds(10, 52, 688, 14);
		contentPane.add(lblNiveauJoue);
		
		panelGagnant = new JPanel();
		panelGagnant.setBounds(54, 77, 281, 210);
		contentPane.add(panelGagnant);
		panelGagnant.setLayout(null);
		
		lblGagnant = new JLabel("Gagnant:");
		lblGagnant.setHorizontalAlignment(SwingConstants.CENTER);
		lblGagnant.setBounds(10, 11, 261, 14);
		panelGagnant.add(lblGagnant);
		
		lblNomGagnant = new JLabel("");
		lblNomGagnant.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomGagnant.setBounds(10, 36, 261, 14);
		panelGagnant.add(lblNomGagnant);
		
		panelMode1 = new JPanel();
		panelMode1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMode1.setBounds(10, 61, 261, 138);
		panelGagnant.add(panelMode1);
		panelMode1.setLayout(null);
		
		radio1Mode1 = new JRadioButton("Temps total pass\u00E9");
		radio1Mode1.setHorizontalAlignment(SwingConstants.LEFT);
		radio1Mode1.setBounds(6, 30, 249, 23);
		panelMode1.add(radio1Mode1);
		
		radio1Mode2 = new JRadioButton("Endroit o\u00F9 le joueur a pris du dommage");
		radio1Mode2.setHorizontalAlignment(SwingConstants.LEFT);
		radio1Mode2.setBounds(6, 56, 249, 23);
		panelMode1.add(radio1Mode2);
		
		radio1Mode3 = new JRadioButton("Endroit o\u00F9 le joueur a donn\u00E9 du dommage");
		radio1Mode3.setHorizontalAlignment(SwingConstants.LEFT);
		radio1Mode3.setBounds(6, 82, 249, 23);
		panelMode1.add(radio1Mode3);
		
		radio1Mode4 = new JRadioButton("Aucune Donn\u00E9e");
		radio1Mode4.setHorizontalAlignment(SwingConstants.LEFT);
		radio1Mode4.setBounds(6, 108, 249, 23);
		panelMode1.add(radio1Mode4);
		
		panelPerdant = new JPanel();
		panelPerdant.setBounds(373, 77, 281, 210);
		contentPane.add(panelPerdant);
		panelPerdant.setLayout(null);
		
		lblPerdant = new JLabel("Perdant:");
		lblPerdant.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerdant.setBounds(10, 11, 261, 14);
		panelPerdant.add(lblPerdant);
		
		lblNomPerdant = new JLabel("");
		lblNomPerdant.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomPerdant.setBounds(10, 36, 261, 14);
		panelPerdant.add(lblNomPerdant);
		
		panelMode2 = new JPanel();
		panelMode2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMode2.setBounds(10, 61, 261, 138);
		panelPerdant.add(panelMode2);
		panelMode2.setLayout(null);
		
		radio2Mode1 = new JRadioButton("Temps total pass\u00E9");
		radio2Mode1.setHorizontalAlignment(SwingConstants.LEFT);
		radio2Mode1.setBounds(6, 30, 249, 23);
		panelMode2.add(radio2Mode1);
		
		radio2Mode2 = new JRadioButton("Endroit o\u00F9 le joueur a pris du dommage");
		radio2Mode2.setHorizontalAlignment(SwingConstants.LEFT);
		radio2Mode2.setBounds(6, 56, 249, 23);
		panelMode2.add(radio2Mode2);
		
		radio2Mode3 = new JRadioButton("Endroit o\u00F9 le joueur a donn\u00E9 du dommage");
		radio2Mode3.setHorizontalAlignment(SwingConstants.LEFT);
		radio2Mode3.setBounds(6, 82, 249, 23);
		panelMode2.add(radio2Mode3);
		
		radio2Mode4 = new JRadioButton("Aucune Donn\u00E9e");
		radio2Mode4.setHorizontalAlignment(SwingConstants.LEFT);
		radio2Mode4.setBounds(6, 108, 249, 23);
		panelMode2.add(radio2Mode4);
		
		groupeJoueur1 = new ButtonGroup();
		groupeJoueur1.add(radio1Mode1);
		groupeJoueur1.add(radio1Mode2);
		groupeJoueur1.add(radio1Mode3);
		groupeJoueur1.add(radio1Mode4);
		
		groupeJoueur2 = new ButtonGroup();
		groupeJoueur2.add(radio2Mode1);
		groupeJoueur2.add(radio2Mode2);
		groupeJoueur2.add(radio2Mode3);
		groupeJoueur2.add(radio2Mode4);
		
		
		Vector <Integer> idNiveau = new Vector<Integer>();
		idNiveau.add(laPartie.getIdNiveau());
		DAO_User daoUser = new DAO_User();
		String nomNiveau = daoUser.getNomNiveaux(idNiveau).get(0);
		lblNiveauJoue.setText("Niveau jouer: " + nomNiveau);
		
		lblAucun = new JLabel("Puisque le r\u00E9sultat de la partie est null, aucune map thermique ne peut \u00EAtre afficher");
		lblAucun.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAucun.setHorizontalAlignment(SwingConstants.CENTER);
		lblAucun.setBounds(54, 314, 600, 14);
		contentPane.add(lblAucun);
		
		PanelLegende = new JPanel();
		PanelLegende.setBorder(new LineBorder(new Color(0, 0, 0)));
		PanelLegende.setBounds(436, 383, 218, 215);
		contentPane.add(PanelLegende);
		PanelLegende.setLayout(new CardLayout(0, 0));
		
		lblLegendeIcon = new JLabel("");
		URL imageURL = Information_partie.class.getResource("/images/legende.jpg");
		lblLegendeIcon.setIcon(new ImageIcon(imageURL));
		PanelLegende.add(lblLegendeIcon, "name_3733721553236");
		
		lblJoueur1 = new JLabel("");
		lblJoueur1.setHorizontalAlignment(SwingConstants.CENTER);
		lblJoueur1.setBounds(394, 383, 14, 215);
		lblJoueur1.setUI(new VerticalLabelUI(false));
		contentPane.add(lblJoueur1);
		
		lblJoueur2 = new JLabel("New label");
		lblJoueur2.setHorizontalAlignment(SwingConstants.CENTER);
		lblJoueur2.setBounds(436, 616, 218, 14);
		contentPane.add(lblJoueur2);
		
		lblNomX = new JLabel("");
		lblNomX.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomX.setBounds(412, 383, 14, 215);
		lblNomX.setUI(new VerticalLabelUI(false));
		contentPane.add(lblNomX);
		
		lblNomY = new JLabel("New label");
		lblNomY.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomY.setBounds(436, 603, 218, 14);
		contentPane.add(lblNomY);
		
		lblLegende = new JLabel("L\u00C9GENDE");
		lblLegende.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblLegende.setHorizontalAlignment(SwingConstants.CENTER);
		lblLegende.setBounds(436, 361, 218, 14);
		contentPane.add(lblLegende);
		
		lblTitre = new JLabel("New label");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(22, 298, 354, 14);
		contentPane.add(lblTitre);
		
		lblMaxX = new JLabel("New label");
		lblMaxX.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxX.setBounds(380, 374, 46, 14);
		contentPane.add(lblMaxX);
		
		lblMaxY = new JLabel("New label");
		lblMaxY.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaxY.setBounds(652, 603, 46, 14);
		contentPane.add(lblMaxY);
		
		DAO_Partie daoPartie = new DAO_Partie();
		Vector<String>usernameJoueurs = daoPartie.getUsername(laPartie.getId());
		joueurImplique = new Vector<User>();
		for(int i=0; i < usernameJoueurs.size(); i++)
		{
			joueurImplique.add(daoUser.getUser(usernameJoueurs.get(i)));
		}
		
		idDesJoueursEnOrdre = new Vector<Integer>();
		if(laPartie.idGagnant != 0)
		{
			for(int i = 0; i < joueurImplique.size(); i++)
			{
				if(joueurImplique.get(i).getId() == laPartie.getIdGagnant())
				{
					lblNomGagnant.setText(joueurImplique.get(i).getUsername());
					lblJoueur1.setText(joueurImplique.get(i).getUsername());
					idDesJoueursEnOrdre.add(joueurImplique.get(i).getId());
				}
				else
				{
					lblNomPerdant.setText(joueurImplique.get(i).getUsername());
					lblJoueur2.setText(joueurImplique.get(i).getUsername());
				}
			}
		}
		else
		{
			lblGagnant.setText("");
			lblPerdant.setText("");
			lblNomGagnant.setText(joueurImplique.get(0).getUsername());
			lblNomPerdant.setText(joueurImplique.get(1).getUsername());
			idDesJoueursEnOrdre.add(joueurImplique.get(0).getId());
		}
		
		if(idDesJoueursEnOrdre.get(0) == joueurImplique.get(0).getId())
		{
			idDesJoueursEnOrdre.add(joueurImplique.get(1).getId());
		}
		else
		{
			idDesJoueursEnOrdre.add(joueurImplique.get(0).getId());
		}
		
		if(laPartie.getIdGagnant() != 0)
		{
			lesCasesThermiques = daoPartie.getCasesThermiques(laPartie.getId(), idDesJoueursEnOrdre.get(0), idDesJoueursEnOrdre.get(1));
			
			x = 0;
			y = 0;
			for(int i = 0; i < lesCasesThermiques.size(); i++)
			{
				if(lesCasesThermiques.get(i).getX() > x)
				{
					x = lesCasesThermiques.get(i).getX();
				}
				else if(lesCasesThermiques.get(i).getY() > y)
				{
					y = lesCasesThermiques.get(i).getY();
				}
			}
			
			x += 1;
			y += 1;
			
			dmgRecuJoueur1 = new int[x][y];
			dmgRecuJoueur2 = new int[x][y];
			dmgDonneJoueur1 = new int[x][y];
			dmgDonneJoueur2 = new int[x][y];
			tempsJoueur1 = new double[x][y];
			tempsJoueur2 = new double[x][y];
			aucuneDonne = new double [x][y];
			
			int compteur = 0;
			for(int i = 0; i < x; i ++)
			{
				for(int j= 0; j < y; j ++)
				{
					dmgRecuJoueur1[i][j] = lesCasesThermiques.get(compteur).getQteDmgRecuJoueur1();
					dmgRecuJoueur2[i][j] = lesCasesThermiques.get(compteur).getQteDmgRecuJoueur2();
					dmgDonneJoueur1[i][j] = lesCasesThermiques.get(compteur).getQteDmgDonneJoueur1();
					dmgDonneJoueur2[i][j] = lesCasesThermiques.get(compteur).getQteDmgDonneJoueur2();
					tempsJoueur1[i][j] = lesCasesThermiques.get(compteur).getQteTempsJoueur1();
					tempsJoueur2[i][j] = lesCasesThermiques.get(compteur).getQteTempsJoueur2();
					aucuneDonne[i][j] = 0;
					compteur++;
				}
			}
			
			pourcentdmgRecuJoueur1 = getTabIntPourcent(dmgRecuJoueur1);
			pourcentdmgRecuJoueur2 = getTabIntPourcent(dmgRecuJoueur2);
			pourcentdmgDonneJoueur1 = getTabIntPourcent(dmgDonneJoueur1);
			pourcentdmgDonneJoueur2 = getTabIntPourcent(dmgDonneJoueur2);
			pourcenttempsJoueur1 = getTabDoublePourcent(tempsJoueur1);
			pourcenttempsJoueur2 = getTabDoublePourcent(tempsJoueur2);
			
			panelMapThermique = new MapThermique(x, y, pourcenttempsJoueur1, pourcenttempsJoueur2, 300, 300,0,0);
			panelMapThermique.setBounds(54, 316, 310, 310);
			contentPane.add(panelMapThermique);
			
			
			lblAucun.setVisible(false);
			radio1Mode1.setSelected(true);
			radio2Mode1.setSelected(true);
			lblNomX.setText("Temps passé sur une casse");
			lblNomY.setText("Temps passé sur une casse");
			lblTitre.setText("Temps passé sur une casse VS Temps passé sur une casse");
			titreP1 = "Temps passé sur une casse";
			titreP2 = "Temps passé sur une casse";
			lblMaxX.setText(decimalFormat.format(getMaxDouble(tempsJoueur1)) + "");
			lblMaxY.setText(decimalFormat.format(getMaxDouble(tempsJoueur2)) + "");
			
		}
		else
		{
			radio1Mode1.setEnabled(false);
			radio1Mode2.setEnabled(false);
			radio1Mode3.setEnabled(false);
			radio1Mode4.setEnabled(false);
			radio2Mode1.setEnabled(false);
			radio2Mode2.setEnabled(false);
			radio2Mode3.setEnabled(false);
			radio2Mode4.setEnabled(false);
			
			lblMaxX.setVisible(false);
			lblMaxY.setVisible(false);
			lblTitre.setVisible(false);
			PanelLegende.setVisible(false);
			lblLegende.setVisible(false);
			lblJoueur1.setVisible(false);
			lblJoueur2.setVisible(false);
			lblNomX.setVisible(false);
			lblNomY.setVisible(false);
		}
		
		
		ecouteur = new Ecouteur();
		this.addWindowListener(ecouteur);
		
		radio1Mode1.addActionListener(ecouteur);
		radio1Mode2.addActionListener(ecouteur);
		radio1Mode3.addActionListener(ecouteur);
		radio1Mode4.addActionListener(ecouteur);
		radio2Mode1.addActionListener(ecouteur);
		radio2Mode2.addActionListener(ecouteur);
		radio2Mode3.addActionListener(ecouteur);
		radio2Mode4.addActionListener(ecouteur);
		lblNomGagnant.addMouseListener(ecouteur);
		lblNomPerdant.addMouseListener(ecouteur);
		
		
	}
	
	private class Ecouteur implements ActionListener, WindowListener, MouseListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == radio1Mode1)
			{
				if(radio1Mode1.isSelected())
				{
					panelMapThermique.updateDataSetA(pourcenttempsJoueur1);
					lblNomX.setText("Temps passé sur une casse");
					titreP1 = "Temps passé sur une casse";
					lblMaxX.setText(decimalFormat.format(getMaxDouble(tempsJoueur1)) + "");
				}
			}
			else if(e.getSource() == radio1Mode2)
			{
				if(radio1Mode2.isSelected())
				{
					panelMapThermique.updateDataSetA(pourcentdmgRecuJoueur1);
					lblNomX.setText("Dommage recu sur une casse");
					titreP1 = "Dommage recu sur une casse";
					lblMaxX.setText(getMaxInt(dmgRecuJoueur1) + "");
				}
			}
			else if(e.getSource() == radio1Mode3)
			{
				if(radio1Mode3.isSelected())
				{
					panelMapThermique.updateDataSetA(pourcentdmgDonneJoueur1);
					lblNomX.setText("Dommage donné sur une casse");
					titreP1 = "Dommage donné sur une casse";
					lblMaxX.setText(getMaxInt(dmgDonneJoueur1) + "");
				}
			}
			else if(e.getSource() == radio1Mode4)
			{
				if(radio1Mode4.isSelected())
				{
					panelMapThermique.updateDataSetA(aucuneDonne);
					lblNomX.setText("Aucune donnée");
					titreP1 = "Aucune donnée";
					lblMaxX.setText("0");
				}
			}
			else if(e.getSource() == radio2Mode1)
			{
				if(radio2Mode1.isSelected())
				{
					panelMapThermique.updateDataSetB(pourcenttempsJoueur2);
					lblNomY.setText("Temps passé sur une casse");
					titreP2 = "Temps passé sur une casse";
					lblMaxY.setText(decimalFormat.format(getMaxDouble(tempsJoueur2)) + "");
				}
			}
			else if(e.getSource() == radio2Mode2)
			{
				if(radio2Mode2.isSelected())
				{
					panelMapThermique.updateDataSetB(pourcentdmgRecuJoueur2);
					lblNomY.setText("Dommage recu sur une casse");
					titreP2 = "Dommage recu sur une casse";
					lblMaxY.setText(getMaxInt(dmgRecuJoueur2) + "");
				}
			}
			else if(e.getSource() == radio2Mode3)
			{
				if(radio2Mode3.isSelected())
				{
					panelMapThermique.updateDataSetB(pourcentdmgDonneJoueur2);
					lblNomY.setText("Dommage donné sur une casse");
					titreP2 = "Dommage donné sur une casse";
					lblMaxY.setText(getMaxInt(dmgDonneJoueur2) + "");
				}
			}
			else if(e.getSource() == radio2Mode4)
			{
				if(radio2Mode4.isSelected())
				{
					panelMapThermique.updateDataSetB(aucuneDonne);
					lblNomY.setText("Aucune donnée");
					titreP2 = "Aucune donnée";
					lblMaxY.setText("0");
				}
			}
			
			lblTitre.setText(titreP1 + " VS " + titreP2);
		}

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
			laConnection.fermerConnection();
			
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

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == lblNomGagnant)
			{
				if(joueurImplique.get(0).getUsername() == lblNomGagnant.getText())
				{
					pagePublic = new Page_Public(joueurImplique.get(0));
					pagePublic.setLocationRelativeTo(null);
					pagePublic.setVisible(true);
					dispose();
				}
				else
				{
					pagePublic = new Page_Public(joueurImplique.get(1));
					pagePublic.setLocationRelativeTo(null);
					pagePublic.setVisible(true);
					dispose();
				}
			}
			else if(e.getSource() == lblNomPerdant)
			{
				if(joueurImplique.get(0).getUsername() == lblNomPerdant.getText())
				{
					pagePublic = new Page_Public(joueurImplique.get(0));
					pagePublic.setLocationRelativeTo(null);
					pagePublic.setVisible(true);
					dispose();
				}
				else
				{
					pagePublic = new Page_Public(joueurImplique.get(1));
					pagePublic.setLocationRelativeTo(null);
					pagePublic.setVisible(true);
					dispose();
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

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public double[][] getTabIntPourcent(int tab[][])
	{
		int max = getMaxInt(tab);
		double tableau[][] = new double [x][y];
		if(max == 0)
		{
			for(int i = 0; i < x; i++)
			{
				for(int j = 0; j < y; j++)
				{
					tableau[i][j] = 0;
				}
			}
			
			return tableau;
		}
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				tableau[i][j] = (double)tab[i][j]/(double)max;
			}
		}
		
		return tableau;
	}
	
	public int getMaxInt(int tab[][])
	{
		int max = 0;
		
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				if(tab[i][j] > max)
				{
					max = tab[i][j];
				}
			}
		}
		
		return max;
	}
	
	public double[][] getTabDoublePourcent(double tab[][])
	{
		double max = getMaxDouble(tab);
		double tableau[][] = new double [x][y];
		if(max == 0)
		{
			for(int i = 0; i < x; i++)
			{
				for(int j = 0; j < y; j++)
				{
					tableau[i][j] = 0;
				}
			}
			
			return tableau;
		}
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				tableau[i][j] = (double)tab[i][j]/(double)max;
			}
		}
		
		return tableau;
	}
	
	public double getMaxDouble(double tab[][])
	{
		double max = 0;
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				if(tab[i][j] > max)
				{
					max = tab[i][j];
				}
			}
		}
		
		return max;
	}
}
