package _Tankem_Gestion_du_Compte;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Login extends JFrame {
	private JLabel lblSignIn;
	private JLabel lblUsername;
	private JTextField txtUsername;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JButton btnSignIn;
	private JLabel lblForgotPassword;
	private JLabel lblNewUser;
	private JButton btnSignUp;
	private JButton btnSupport;
	private Ecouteur ecouteur;
	private SignUp signUp;
	private Forgot_Password forgotPass;
	private Page_Personnelle pagePersonnelle;
	private JLabel lblimg;
	private JPanel panelDerniereHeure;
	private JLabel lblNbPartieDebuter;
	private JLabel lblTotal;
	private JPanel panelTotal;
	private JLabel lblRecherche;
	private JTextField txtRecherche;
	private JButton btnRechercher;
	private Page_Public pagePublic;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setLocationRelativeTo(null);
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
	public Login() {
		setTitle("Portail Tankem!");
		setBounds(100, 100, 1060, 523);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		lblSignIn = new JLabel("Sign In");
		lblSignIn.setBounds(839, 11, 46, 14);
		getContentPane().add(lblSignIn);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(839, 36, 55, 14);
		getContentPane().add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(839, 50, 195, 20);
		getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(839, 81, 55, 14);
		getContentPane().add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(839, 93, 195, 20);
		getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		btnSignIn = new JButton("Sign In");
		btnSignIn.setBounds(945, 132, 89, 23);
		getContentPane().add(btnSignIn);
		
		lblForgotPassword = new JLabel("Forgot Password?");
		lblForgotPassword.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblForgotPassword.setForeground(Color.BLUE);
		lblForgotPassword.setBounds(839, 166, 95, 14);
		getContentPane().add(lblForgotPassword);
		
		lblNewUser = new JLabel("New User?");
		lblNewUser.setBounds(839, 202, 55, 14);
		getContentPane().add(lblNewUser);
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.setBounds(839, 215, 80, 23);
		getContentPane().add(btnSignUp);
		
		btnSupport = new JButton("Support");
		btnSupport.setBounds(945, 215, 89, 23);
		getContentPane().add(btnSupport);
		
		lblimg = new JLabel("");
		lblimg.setBounds(10, 11, 800, 460);
		getContentPane().add(lblimg);
		URL imageURL = Login.class.getResource("/images/cartoon-tank-52121.png");
		lblimg.setIcon(new ImageIcon(imageURL));

		panelDerniereHeure = new JPanel();
		panelDerniereHeure.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Dans la derni\u00E8re heure", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDerniereHeure.setBounds(820, 249, 224, 63);
		getContentPane().add(panelDerniereHeure);
		panelDerniereHeure.setLayout(null);
		
		lblNbPartieDebuter = new JLabel("Nombre de parties d\u00E9but\u00E9es:");
		lblNbPartieDebuter.setBounds(10, 27, 204, 14);
		panelDerniereHeure.add(lblNbPartieDebuter);
		
		panelTotal = new JPanel();
		panelTotal.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Au total", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTotal.setBounds(820, 323, 224, 63);
		getContentPane().add(panelTotal);
		panelTotal.setLayout(null);
		
		lblTotal = new JLabel("Nombre de parties jou\u00E9es:");
		lblTotal.setBounds(10, 27, 204, 14);
		panelTotal.add(lblTotal);
		
		lblRecherche = new JLabel("Rechercher un joueur:");
		lblRecherche.setBounds(820, 397, 214, 14);
		getContentPane().add(lblRecherche);
		
		txtRecherche = new JTextField();
		txtRecherche.setColumns(10);
		txtRecherche.setBounds(820, 416, 214, 20);
		getContentPane().add(txtRecherche);
		
		btnRechercher = new JButton("Rechercher");
		btnRechercher.setBounds(931, 447, 103, 23);
		getContentPane().add(btnRechercher);
		
		ecouteur = new Ecouteur();
		this.addWindowListener(ecouteur);
		btnSignIn.addActionListener(ecouteur);
		btnSignUp.addActionListener(ecouteur);
		btnSupport.addActionListener(ecouteur);
		lblForgotPassword.addMouseListener(ecouteur);
		btnRechercher.addActionListener(ecouteur);
		
		laConnection.seConnecter();
		if(laConnection.getConnexion() == null)
		{
			JOptionPane.showMessageDialog(this, "Imposible de se connecter au serveur.\nRéssayez plus tard ou contactez le support.");
			txtUsername.enable(false);
			txtPassword.enable(false);
			btnSignUp.setVisible(false);
			btnSignIn.setVisible(false);
			lblForgotPassword.setVisible(false);
			lblNewUser.setVisible(false);
		}
		else
		{
			DAO_Partie daoPartie =  new DAO_Partie();
			int partieDerniereHeure = daoPartie.getPartieDerniereHeure();
			int partieTotale = daoPartie.getPartieTotal();
			lblNbPartieDebuter.setText(lblNbPartieDebuter.getText() + " " + partieDerniereHeure);
			lblTotal.setText(lblTotal.getText() + " " + partieTotale);
		}
	}
	
	private class Ecouteur implements ActionListener, WindowListener, MouseListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == btnSignIn)
			{
				DAO_User daoUser = new DAO_User();
				String reponse = daoUser.valideUser(txtUsername.getText(), txtPassword.getText());
				
				if(reponse.equals("good"))
				{
					pagePersonnelle = new Page_Personnelle(txtUsername.getText());
					pagePersonnelle.setLocationRelativeTo(null);
					pagePersonnelle.setVisible(true);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(Login.this, reponse);
				}
			}
			else if(e.getSource() == btnSignUp)
			{
				signUp = new SignUp();
				signUp.setLocationRelativeTo(null);
				signUp.setVisible(true);
				dispose();
			}
			else if(e.getSource() == btnSupport)
			{
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.mail(new URI("mailto:support@example.com"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(e.getSource() == btnRechercher)
			{
				DAO_User daoUser = new DAO_User();
				User userRechercher = daoUser.getUser(txtRecherche.getText());
				
				if(userRechercher !=null)
				{
					pagePublic = new Page_Public(userRechercher);
					pagePublic.setLocationRelativeTo(null);
					pagePublic.setVisible(true);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(Login.this, "L'utilisateur recherché n'existe pas.");
				}
			}
			
			
			
		}

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
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
			// TODO Auto-generated method stub
			
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
			
			if(e.getSource() == lblForgotPassword)
			{
				forgotPass = new Forgot_Password();
				forgotPass.setLocationRelativeTo(null);
				forgotPass.setVisible(true);
				dispose();
			}
			
		}
		
	}
}
