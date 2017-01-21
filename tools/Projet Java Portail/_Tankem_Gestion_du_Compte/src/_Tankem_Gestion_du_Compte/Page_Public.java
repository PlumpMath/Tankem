package _Tankem_Gestion_du_Compte;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.Font;

import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

public class Page_Public extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tablePartie;
	private DefaultTableModel modelTable;
	private JButton btnPartie;
	private JLabel lblPartieJouer;
	private JPanel panelInfo;
	private JLabel lblInfo;
	private JLabel lblNom;
	private JLabel lblWinLose;
	private JLabel lblAbandon;
	private JLabel lblPartieGagner;
	private JLabel lblPartiePerdu;
	private JLabel lblPartieAbandonner;
	private JLabel lblTotal;
	private JLabel lblNiveauCreer;
	private JLabel lblArmeFav;
	private JLabel lblDommageMoyen;
	private JLabel lblMitrallette;
	private JLabel lblShotgun;
	private JLabel lblGrenade;
	private JLabel lblPiege;
	private JLabel lblGuide;
	private JLabel lblSpring;
	private JLabel lblDommageMoyenPar;
	private User leUser;
	private JButton btnRetour;
	private Vector<Partie> lesParties;
	private Ecouteur ecouteur;
	private Login login;
	private Information_partie informationPartie;
	String pattern;
	private DecimalFormat decimalFormat;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args, User unUser) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Page_Public frame = new Page_Public(unUser);
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
	public Page_Public(User unUser) {
		pattern = "###.##";
		decimalFormat= new DecimalFormat(pattern);
		setTitle("Portail Page Public");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 674, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		leUser = unUser;
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 254, 426);
		contentPane.add(scrollPane);
		
		modelTable = new DefaultTableModel();
		tablePartie = new JTable(modelTable);
		scrollPane.setViewportView(tablePartie);
		
		btnPartie = new JButton("Consulter la partie");
		btnPartie.setBounds(10, 478, 235, 23);
		contentPane.add(btnPartie);
		
		lblPartieJouer = new JLabel("Les parties jou\u00E9es");
		lblPartieJouer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPartieJouer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartieJouer.setBounds(10, 11, 235, 25);
		contentPane.add(lblPartieJouer);
		
		panelInfo = new JPanel();
		panelInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInfo.setBounds(274, 41, 372, 432);
		contentPane.add(panelInfo);
		panelInfo.setLayout(null);
		
		lblWinLose = new JLabel("Taux Win/Lose: ");
		lblWinLose.setBounds(10, 36, 355, 14);
		panelInfo.add(lblWinLose);
		
		lblNom = new JLabel("Nom de r\u00E9putation: ");
		lblNom.setBounds(10, 11, 355, 14);
		panelInfo.add(lblNom);
		
		lblAbandon = new JLabel("Taux d'abandon: ");
		lblAbandon.setBounds(10, 61, 355, 14);
		panelInfo.add(lblAbandon);
		
		lblPartieGagner = new JLabel("Nombre parties gagn\u00E9es: ");
		lblPartieGagner.setBounds(10, 86, 355, 14);
		panelInfo.add(lblPartieGagner);
		
		lblPartiePerdu = new JLabel("Nombre parties perdues:  ");
		lblPartiePerdu.setBounds(10, 111, 355, 14);
		panelInfo.add(lblPartiePerdu);
		
		lblPartieAbandonner = new JLabel("Nombre parties abandonn\u00E9es: ");
		lblPartieAbandonner.setBounds(10, 136, 355, 14);
		panelInfo.add(lblPartieAbandonner);
		
		lblTotal = new JLabel("Nombre parties totales: ");
		lblTotal.setBounds(10, 161, 355, 14);
		panelInfo.add(lblTotal);
		
		lblNiveauCreer = new JLabel("Nombre de niveaux cr\u00E9\u00E9s: ");
		lblNiveauCreer.setBounds(10, 186, 355, 14);
		panelInfo.add(lblNiveauCreer);
		
		lblArmeFav = new JLabel("Arme avec le plus de tir: ");
		lblArmeFav.setBounds(10, 211, 355, 14);
		panelInfo.add(lblArmeFav);
		
		lblDommageMoyen = new JLabel("Dommage moyen par arme:");
		lblDommageMoyen.setBounds(10, 236, 355, 14);
		panelInfo.add(lblDommageMoyen);
		
		lblMitrallette = new JLabel("        Mitrallette: ");
		lblMitrallette.setBounds(10, 261, 355, 14);
		panelInfo.add(lblMitrallette);
		
		lblShotgun = new JLabel("        Shotgun:  ");
		lblShotgun.setBounds(10, 286, 355, 14);
		panelInfo.add(lblShotgun);
		
		lblGrenade = new JLabel("        Grenade: ");
		lblGrenade.setBounds(10, 311, 355, 14);
		panelInfo.add(lblGrenade);
		
		lblPiege = new JLabel("        Piege: ");
		lblPiege.setBounds(10, 336, 355, 14);
		panelInfo.add(lblPiege);
		
		lblGuide = new JLabel("        Missible Guid\u00E9: ");
		lblGuide.setBounds(10, 361, 355, 14);
		panelInfo.add(lblGuide);
		
		lblSpring = new JLabel("        Spring: ");
		lblSpring.setBounds(10, 386, 355, 14);
		panelInfo.add(lblSpring);
		
		lblDommageMoyenPar = new JLabel("Dommage moyen par tir:");
		lblDommageMoyenPar.setBounds(10, 411, 355, 14);
		panelInfo.add(lblDommageMoyenPar);
		
		lblInfo = new JLabel("Information sur le joueur");
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInfo.setBounds(274, 11, 476, 21);
		contentPane.add(lblInfo);
		
		btnRetour = new JButton("Retour");
		btnRetour.setBounds(557, 478, 89, 23);
		contentPane.add(btnRetour);
		
		modelTable.addColumn("Date");
		modelTable.addColumn("Win/Lose");
		tablePartie.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
		tablePartie.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
		DAO_Partie daoPartie = new DAO_Partie();
		Vector<Integer> idParties = daoPartie.getIdPartie(leUser.getId());
		lesParties = daoPartie.getPartie(idParties);
		
		Collections.sort(lesParties, new CustomComparator());
		
		Vector<Partie>temp = new Vector<Partie>();
		for(int i = lesParties.size()-1; i >= 0; i--)
		{
			temp.add(lesParties.get(i));
		}
		
		lesParties = temp;
		
		
		int winTotal = 0, loseTotal = 0, abandonTotal = 0;
		for(int i = 0; i < lesParties.size(); i++)
		{
			if(lesParties.get(i).getIdGagnant() != 0)
			{
				if(lesParties.get(i).getIdGagnant() == leUser.getId())
				{
					modelTable.addRow(new Object[]{lesParties.get(i).getLaDate(),"WIN"});
					winTotal += 1;
				}
				else
				{
					modelTable.addRow(new Object[]{lesParties.get(i).getLaDate(),"LOSE"});
					loseTotal += 1;
				}
			}
			else
			{
				modelTable.addRow(new Object[]{lesParties.get(i).getLaDate(),"NULL"});
				abandonTotal+=1;
			}
		}
		
		double totalPartie = winTotal + loseTotal + abandonTotal;
		lblWinLose.setText(lblWinLose.getText() + (decimalFormat.format((double)winTotal/loseTotal)));
		lblAbandon.setText(lblAbandon.getText() + decimalFormat.format(abandonTotal/totalPartie));
		lblPartieGagner.setText(lblPartieGagner.getText() + winTotal);
		lblPartiePerdu.setText(lblPartiePerdu.getText() + loseTotal);
		lblPartieAbandonner.setText(lblPartieAbandonner.getText() + abandonTotal);
		lblTotal.setText(lblTotal.getText() + (int)totalPartie);
		
		DAO_User daoUser = new DAO_User();
		int nbNiveauCree = daoUser.getNbNiveauCree(leUser.getId());
		lblNiveauCreer.setText(lblNiveauCreer.getText() + nbNiveauCree);
		
		DAO_Armurerie daoArmurerie = new DAO_Armurerie();
		String armeAvecPlusTire = daoArmurerie.getArmeAvecPlusTire(leUser.getId());
		lblArmeFav.setText(lblArmeFav.getText() + armeAvecPlusTire);
		
		lblMitrallette.setText(lblMitrallette.getText() + decimalFormat.format(daoArmurerie.getDmgMoyenArme("Mitraillette", leUser.getId())));
		lblShotgun.setText(lblShotgun.getText() + decimalFormat.format(daoArmurerie.getDmgMoyenArme("Shotgun", leUser.getId())));
		lblGrenade.setText(lblGrenade.getText() + decimalFormat.format(daoArmurerie.getDmgMoyenArme("Grenade", leUser.getId())));
		lblPiege.setText(lblPiege.getText() + decimalFormat.format(daoArmurerie.getDmgMoyenArme("Piege", leUser.getId())));
		lblGuide.setText(lblGuide.getText() + decimalFormat.format(daoArmurerie.getDmgMoyenArme("Guide", leUser.getId())));
		lblSpring.setText(lblSpring.getText() + decimalFormat.format(daoArmurerie.getDmgMoyenArme("Spring", leUser.getId())));
		
		lblDommageMoyenPar.setText(lblDommageMoyenPar.getText() + decimalFormat.format(daoArmurerie.getDmgMoyenTotal(leUser.getId())));
		
		String nomReputation = leUser.getUsername() + " ";
		
		if(winTotal < 2)
		{
			nomReputation += "Le généraliste";
		}
		else if(armeAvecPlusTire.equals("Mitraillette "))
		{
			nomReputation += "Le gangster ";
		}
		else if(armeAvecPlusTire == "Shotgun ")
		{
			nomReputation += "Le déchiqueteur";
		}
		else if(armeAvecPlusTire.equals("Grenade "))
		{
			nomReputation += "L'exploseur de tête";
		}
		else if(armeAvecPlusTire.equals("Piege "))
		{
			nomReputation += "Le subtil";
		}
		else if(armeAvecPlusTire.equals("Guide "))
		{
			nomReputation += "Le hitman";
		}
		else if(armeAvecPlusTire.equals("Spring "))
		{
			nomReputation += "La puce mexicaine";
		}
		
		Date fromDate;
		Calendar c = null;
		boolean longtemps = false;
		if(lesParties.size() > 0)
		{
			fromDate = lesParties.get(lesParties.size()-1).getLaDate();
			c=Calendar.getInstance();
			  c.setTime(fromDate);
			  c.add(Calendar.DATE,7);
			  
			if(c.getTime().compareTo(new Date())<0)
			{
				longtemps = true;
			}
		}
		
		if(abandonTotal/totalPartie > 0.10)
		{
			nomReputation += " poltron";
		}
		else if(longtemps)
		{
			nomReputation += " fantôme";
		}
		else if(lesParties.size() < 5)
		{
			nomReputation += " néophyte";
		}
		else if((double)winTotal/loseTotal > 2)
		{
			nomReputation += " vainqueur";
		}
		
		
		System.out.println(nomReputation);
		lblNom.setText(lblNom.getText() + nomReputation);
		ecouteur = new Ecouteur();
		btnRetour.addActionListener(ecouteur);
		btnPartie.addActionListener(ecouteur);
		this.addWindowListener(ecouteur);
		
	}
	
	
	private class Ecouteur implements ActionListener, WindowListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == btnRetour)
			{
				login = new Login();
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				dispose();
			}
			else if(e.getSource() == btnPartie)
			{
				if(tablePartie.getSelectedRow() != -1)
				{
					System.out.println(tablePartie.getSelectedRow());
					System.out.println(lesParties.size());
					informationPartie = new Information_partie(lesParties.get(tablePartie.getSelectedRow()));
					informationPartie.setLocationRelativeTo(null);
					informationPartie.setVisible(true);
					dispose();
				}
			}
			
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
		
	}
}


