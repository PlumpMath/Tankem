package _Tankem_Gestion_du_Compte;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.border.LineBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.Vector;

public class Page_Personnelle extends JFrame {

	private JPanel contentPane;
	private JButton btnDeconnecter;
	private JPanel panelInfo;
	private JLabel lblInfo;
	private JLabel lblNom;
	private JLabel lblPrenom;
	private JLabel lblUsername;
	private JLabel txtNom;
	private JLabel txtPrenom;
	private JLabel txtUsername;
	private JButton btnChangePassword;
	private JPanel panel;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	private JTable tableNiveauFavoris;
	private DefaultTableModel modelTableNiveauFavoris;
	private JTable tableNiveauCree;
	private DefaultTableModel modelTableNiveauCree;
	private JTable tableArmurerie;
	private DefaultTableModel modelTableArmurerie;
	private Ecouteur ecouteur;
	private Forgot_Password forgotPass;
	private Login login;
	private User leUser;
	private JLabel lblCouleur;
	private JButton btnChoisirCouleur;
	private Color laCouleur;
	private JLabel lblNivFav;
	private JLabel lblNivCree;
	private JLabel lblArmurerie;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, String username) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Page_Personnelle frame = new Page_Personnelle(username);
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
	public Page_Personnelle(String username) {
		setTitle("Portail Page Personnelle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 785, 729);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnDeconnecter = new JButton("Se d\u00E9connecter");
		btnDeconnecter.setBounds(10, 11, 114, 23);
		contentPane.add(btnDeconnecter);
		
		panelInfo = new JPanel();
		panelInfo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfo.setBounds(10, 45, 749, 121);
		contentPane.add(panelInfo);
		panelInfo.setLayout(null);
		
		lblInfo = new JLabel("Information personnelles");
		lblInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInfo.setBounds(10, 11, 159, 20);
		panelInfo.add(lblInfo);
		
		lblNom = new JLabel("Nom:");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setBounds(10, 42, 159, 14);
		panelInfo.add(lblNom);
		
		lblPrenom = new JLabel("Pr\u00E9nom:");
		lblPrenom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrenom.setBounds(10, 67, 159, 14);
		panelInfo.add(lblPrenom);
		
		lblUsername = new JLabel("Nom de joueur:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(10, 92, 159, 14);
		panelInfo.add(lblUsername);
		
		txtNom = new JLabel("");
		txtNom.setBounds(179, 42, 326, 14);
		panelInfo.add(txtNom);
		
		txtPrenom = new JLabel("");
		txtPrenom.setBounds(179, 67, 326, 14);
		panelInfo.add(txtPrenom);
		
		txtUsername = new JLabel("");
		txtUsername.setBounds(179, 92, 326, 14);
		panelInfo.add(txtUsername);
		
		btnChangePassword = new JButton("Changer mot de passe");
		btnChangePassword.setBounds(10, 177, 149, 23);
		contentPane.add(btnChangePassword);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 233, 749, 171);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 3, 5, 0));
		
		scrollPane1 = new JScrollPane();
		panel.add(scrollPane1);
		
		scrollPane2 = new JScrollPane();
		panel.add(scrollPane2);
		
		scrollPane3 = new JScrollPane();
		panel.add(scrollPane3);
		
		modelTableNiveauFavoris = new DefaultTableModel();
		tableNiveauFavoris = new JTable(modelTableNiveauFavoris){
			  public boolean isCellEditable(int row,int column){
				    return false;
				  }
				};
		scrollPane1.setViewportView(tableNiveauFavoris);
		
		modelTableNiveauCree = new DefaultTableModel();
		tableNiveauCree = new JTable(modelTableNiveauCree){
			  public boolean isCellEditable(int row,int column){
				    return false;
				  }
				};
		scrollPane2.setViewportView(tableNiveauCree);
		
		modelTableArmurerie = new DefaultTableModel();
		tableArmurerie = new JTable(modelTableArmurerie){
			  public boolean isCellEditable(int row,int column){
				    return false;
				  }
				};
		scrollPane3.setViewportView(tableArmurerie);
		
		lblCouleur = new JLabel("");
		lblCouleur.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCouleur.setBounds(267, 449, 230, 230);
		contentPane.add(lblCouleur);
		
		modelTableNiveauFavoris.addColumn("Nom");
		modelTableNiveauCree.addColumn("Nom");
		modelTableNiveauCree.addColumn("Nb fois favoris");
		modelTableNiveauCree.addColumn("Nb fois joué");
		modelTableArmurerie.addColumn("Armes");
		modelTableArmurerie.addColumn("Quantité");
		
		DAO_User daoUser = new DAO_User();
		leUser = daoUser.getUser(username);
		Vector<String>niveauxFavoris = daoUser.getNiveauxFavoris(leUser.getId());
		Vector<Integer> idNiveauxCree = daoUser.getIdNiveauCree(leUser.getId());
		Vector<String> niveauCree = daoUser.getNomNiveaux(idNiveauxCree);
		Vector<Integer> nbFavoris = daoUser.getNbFavoris(idNiveauxCree);
		Vector<Integer> nbJouer = daoUser.getNbJouer(idNiveauxCree);
		Vector<Integer> idArmurie = daoUser.getIdArmurerie();
		Vector<String> armurieNom = daoUser.getArmurieNom(idArmurie);
		Vector<Integer> qteArmurie = daoUser.getArmurieQte(idArmurie, leUser.getId());
		
		txtNom.setText(leUser.getNom());
		txtPrenom.setText(leUser.getPrenom());
		txtUsername.setText(leUser.getUsername());
		
		for(int i = 0; i < armurieNom.size(); i++)
		{
			modelTableArmurerie.addRow(new Object[]{armurieNom.get(i),qteArmurie.get(i)});
		}
		
		for(int i = 0; i < niveauCree.size(); i++)
		{
			modelTableNiveauCree.addRow(new Object[]{niveauCree.get(i),nbFavoris.get(i),nbJouer.get(i)});
		}
		
		for(int i = 0; i < niveauxFavoris.size(); i++)
		{
			modelTableNiveauFavoris.addRow(new Object[]{niveauxFavoris.get(i)});
		}
		
		laCouleur = new Color(leUser.getR(),leUser.getG(),leUser.getB());
		lblCouleur.setBackground(laCouleur);
		lblCouleur.setOpaque(true);
		
		URL imageURL = Login.class.getResource("/images/tank.png");
		lblCouleur.setIcon(new ImageIcon(imageURL));
		
		btnChoisirCouleur = new JButton("Choisir Couleur");
		btnChoisirCouleur.setBounds(267, 415, 230, 23);
		contentPane.add(btnChoisirCouleur);
		
		lblNivFav = new JLabel("Niveaux Favoris");
		lblNivFav.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNivFav.setHorizontalAlignment(SwingConstants.CENTER);
		lblNivFav.setBounds(10, 208, 244, 14);
		contentPane.add(lblNivFav);
		
		lblNivCree = new JLabel("Niveaux Cr\u00E9\u00E9");
		lblNivCree.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNivCree.setHorizontalAlignment(SwingConstants.CENTER);
		lblNivCree.setBounds(263, 208, 244, 14);
		contentPane.add(lblNivCree);
		
		lblArmurerie = new JLabel("Armurerie");
		lblArmurerie.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblArmurerie.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmurerie.setBounds(515, 208, 244, 14);
		contentPane.add(lblArmurerie);
		ecouteur = new Ecouteur();
		btnDeconnecter.addActionListener(ecouteur);
		btnChangePassword.addActionListener(ecouteur);
		this.addWindowListener(ecouteur);
		btnChoisirCouleur.addActionListener(ecouteur);
	}
	
	
	
	private class Ecouteur implements ActionListener, WindowListener
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
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == btnChangePassword)
			{
				forgotPass = new Forgot_Password();
				forgotPass.setLocationRelativeTo(null);
				forgotPass.setVisible(true);
				dispose();
			}
			else if(e.getSource() == btnDeconnecter)
			{
				login = new Login();
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				dispose();
			}
			else if(e.getSource() == btnChoisirCouleur)
			{
				JColorChooser cc = new JColorChooser();
				cc.setColor(laCouleur);
                AbstractColorChooserPanel[] panels = cc.getChooserPanels();
                for (AbstractColorChooserPanel accp : panels) {
                    if (accp.getDisplayName().equals("Echantillons")) {
                        JOptionPane.showMessageDialog(null, accp,"Changer la couleur du tank",1,null);
                        
                        laCouleur = cc.getColor();
                        lblCouleur.setBackground(laCouleur);
                        DAO_User daoUser = new DAO_User();
                        daoUser.changeCouleur(laCouleur, leUser.getUsername());
                    }
                }
                
                
				/*Color uneCouleur = cc.showDialog(Page_Personnelle.this,"Choisir la couleur du tank", Color.white);
			            if(uneCouleur != null){
			               laCouleur = uneCouleur;
			               lblCouleur.setBackground(laCouleur);
			            }*/
			}
			
		}
		
	}
}
