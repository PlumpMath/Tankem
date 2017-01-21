package _Tankem_Gestion_du_Compte;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.UIManager;

public class SignUp extends JFrame {
	private JLabel lblTitre;
	private JPanel panel;
	private JLabel lblInscription;
	private JLabel lblNom;
	private JLabel lblPrenom;
	private JLabel lblNomDeJoueur;
	private JLabel lblPassword;
	private JLabel lblRepeteMotDePasse;
	private JLabel lblQuestionA;
	private JLabel lblReponseA;
	private JLabel lblQuestionB;
	private JLabel lblReponseB;
	private JTextField txtNom;
	private JTextField txtPrenom;
	private JTextField txtNomDeJoueur;
	private JPasswordField txtPassword;
	private JPasswordField txtRepetePassword;
	private JTextField txtA;
	private JTextField txtB;
	private JComboBox comboBoxA;
	private JComboBox comboBoxB;
	private JButton btnInscricre;
	private Ecouteur ecouteur;
	private Vector<Question> lesQuestions;
	private Login login;
	private JButton btnRetour;

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
					SignUp frame = new SignUp();
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
	public SignUp() {
		setBounds(100, 100, 545, 415);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		lblTitre = new JLabel("Page d'inscription");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblTitre.setBounds(10, 11, 509, 44);
		getContentPane().add(lblTitre);
		
		panel = new JPanel();
		panel.setBounds(10, 67, 509, 262);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lblInscription = new JLabel("Formulaire d'inscription");
		lblInscription.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInscription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInscription.setBounds(10, 11, 172, 14);
		panel.add(lblInscription);
		
		lblNom = new JLabel("Nom:");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setBounds(10, 36, 172, 14);
		panel.add(lblNom);
		
		lblPrenom = new JLabel("Pr\u00E9nom:");
		lblPrenom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrenom.setBounds(10, 61, 172, 14);
		panel.add(lblPrenom);
		
		lblNomDeJoueur = new JLabel("Nom de joueur:");
		lblNomDeJoueur.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomDeJoueur.setBounds(10, 86, 172, 14);
		panel.add(lblNomDeJoueur);
		
		lblPassword = new JLabel("Mot de passe:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(10, 111, 172, 14);
		panel.add(lblPassword);
		
		lblRepeteMotDePasse = new JLabel("R\u00E9peter le mot de passe:");
		lblRepeteMotDePasse.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepeteMotDePasse.setBounds(10, 136, 172, 14);
		panel.add(lblRepeteMotDePasse);
		
		lblQuestionA = new JLabel("Question secr\u00E8te A:");
		lblQuestionA.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQuestionA.setBounds(10, 161, 172, 14);
		panel.add(lblQuestionA);
		
		lblReponseA = new JLabel("R\u00E9ponse \u00E0 la question secr\u00E8te A:");
		lblReponseA.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReponseA.setBounds(10, 186, 172, 14);
		panel.add(lblReponseA);
		
		lblQuestionB = new JLabel("Question secr\u00E8te B:");
		lblQuestionB.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQuestionB.setBounds(10, 211, 172, 14);
		panel.add(lblQuestionB);
		
		lblReponseB = new JLabel("R\u00E9ponse \u00E0 la question secr\u00E8te B:");
		lblReponseB.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReponseB.setBounds(10, 236, 172, 14);
		panel.add(lblReponseB);
		
		txtNom = new JTextField();
		txtNom.setBounds(192, 33, 307, 20);
		panel.add(txtNom);
		txtNom.setColumns(10);
		
		txtPrenom = new JTextField();
		txtPrenom.setColumns(10);
		txtPrenom.setBounds(192, 58, 307, 20);
		panel.add(txtPrenom);
		
		txtNomDeJoueur = new JTextField();
		txtNomDeJoueur.setColumns(10);
		txtNomDeJoueur.setBounds(192, 83, 307, 20);
		panel.add(txtNomDeJoueur);
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(192, 108, 307, 20);
		panel.add(txtPassword);
		
		txtRepetePassword = new JPasswordField();
		txtRepetePassword.setColumns(10);
		txtRepetePassword.setBounds(192, 133, 307, 20);
		panel.add(txtRepetePassword);
		
		txtA = new JTextField();
		txtA.setColumns(10);
		txtA.setBounds(192, 183, 307, 20);
		panel.add(txtA);
		
		txtB = new JTextField();
		txtB.setColumns(10);
		txtB.setBounds(192, 233, 307, 20);
		panel.add(txtB);
		
		comboBoxA = new JComboBox();
		comboBoxA.setBounds(192, 158, 307, 20);
		panel.add(comboBoxA);
		
		comboBoxB = new JComboBox();
		comboBoxB.setBounds(192, 208, 307, 20);
		panel.add(comboBoxB);
		
		btnInscricre = new JButton("S'inscrire");
		btnInscricre.setBounds(156, 340, 216, 23);
		getContentPane().add(btnInscricre);
		
		btnRetour = new JButton("Retour");
		btnRetour.setBounds(10, 340, 89, 23);
		getContentPane().add(btnRetour);
		
		DAO_Question daoQuestion = new DAO_Question();
		lesQuestions = daoQuestion.getLesQuestions();
		
		for(int i = 0; i < lesQuestions.size(); i++)
		{
			comboBoxA.addItem(lesQuestions.get(i).getQuestion());
			comboBoxB.addItem(lesQuestions.get(i).getQuestion());
		}
		
		ecouteur = new Ecouteur();
		btnInscricre.addActionListener(ecouteur);
		this.addWindowListener(ecouteur);
		btnRetour.addActionListener(ecouteur);

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
			else if(e.getSource() == btnInscricre)
			{
				if(!txtPassword.getText().equals(txtRepetePassword.getText()))
				{
					JOptionPane.showMessageDialog(SignUp.this, "Les mots de passe ne sont pas identiques");
				}
				else if(!txtPassword.getText().matches("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[ !@#$%?&*()]).{9,30})"))
				{
					JOptionPane.showMessageDialog(SignUp.this, "Le mot de passe doit contenir au moins: "
																+ "\n- Minimum 9 lettres."
																+"\n- Au moins une letttre minuscule."
																+"\n- Au moins une lettre majuscule."
																+"\n- Au moins un chiffre."
																+"\n- Au moins un symbole. Supporter au moins ceux-ci: !@#$%?&*()");
				}
				else if(comboBoxA.getSelectedIndex() == comboBoxB.getSelectedIndex())
				{
					JOptionPane.showMessageDialog(SignUp.this, "Les questions secretes ne peuvent pas être identiques");
				}
				else
				{
					DAO_User daoUser = new DAO_User();
					boolean estCree = daoUser.enregistrerUser(txtNomDeJoueur.getText(), txtNom.getText(), txtPrenom.getText(), txtPassword.getText(), lesQuestions.get(comboBoxA.getSelectedIndex()).getId(), txtA.getText(), lesQuestions.get(comboBoxB.getSelectedIndex()).getId(), txtB.getText(), SignUp.this);
					if(estCree)
					{
						JOptionPane.showMessageDialog(SignUp.this, "L'account a bien été créé");
						login = new Login();
						login.setLocationRelativeTo(null);
						login.setVisible(true);
						dispose();
					}
				}
			}
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("Hello");
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
