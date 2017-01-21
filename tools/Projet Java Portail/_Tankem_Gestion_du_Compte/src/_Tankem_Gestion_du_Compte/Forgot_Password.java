package _Tankem_Gestion_du_Compte;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Forgot_Password extends JFrame {

	private JPanel contentPane;
	private JLabel lblOublie;
	private JLabel lblNomJoueur;
	private JTextField txtNomJoueur;
	private JButton btnOk1;
	private JLabel lblQA;
	private JPanel panelPhase1;
	private JPanel panelPhase2;
	private JTextField txtQA;
	private JLabel lblQB;
	private JTextField txtQB;
	private JPanel panelPhase3;
	private JButton btnOk2;
	private JLabel lblPassword;
	private JPasswordField txtNewPassword;
	private JLabel lblRepetePassword;
	private JPasswordField txtNewPassword2;
	private JButton btnChanger;
	private JButton btnRetour;
	private Ecouteur ecouteur;
	private Login login;
	private String username;
	private JLabel lblQuestionA;
	private JLabel lblQuestionB;
	private int[] tableau;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Forgot_Password frame = new Forgot_Password();
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
	public Forgot_Password() {
		setTitle("Portail Forgot Password");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 578);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblOublie = new JLabel("Oubli du mot de passe");
		lblOublie.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblOublie.setBounds(10, 11, 182, 31);
		contentPane.add(lblOublie);
		
		panelPhase1 = new JPanel();
		panelPhase1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelPhase1.setBounds(10, 74, 414, 89);
		contentPane.add(panelPhase1);
		panelPhase1.setLayout(null);
		
		lblNomJoueur = new JLabel("Nom Joueur:");
		lblNomJoueur.setBounds(10, 8, 61, 14);
		panelPhase1.add(lblNomJoueur);
		
		txtNomJoueur = new JTextField();
		txtNomJoueur.setBounds(10, 21, 394, 20);
		panelPhase1.add(txtNomJoueur);
		txtNomJoueur.setColumns(10);
		
		btnOk1 = new JButton("Ok");
		btnOk1.setBounds(10, 52, 65, 23);
		panelPhase1.add(btnOk1);
		
		panelPhase2 = new JPanel();
		panelPhase2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelPhase2.setBounds(10, 174, 414, 206);
		contentPane.add(panelPhase2);
		panelPhase2.setLayout(null);
		
		lblQA = new JLabel("Question Secrete A:");
		lblQA.setBounds(10, 11, 97, 14);
		panelPhase2.add(lblQA);
		
		txtQA = new JTextField();
		txtQA.setBounds(10, 60, 394, 20);
		panelPhase2.add(txtQA);
		txtQA.setColumns(10);
		
		lblQB = new JLabel("Question Secrete B:");
		lblQB.setBounds(10, 91, 97, 14);
		panelPhase2.add(lblQB);
		
		txtQB = new JTextField();
		txtQB.setBounds(10, 141, 394, 20);
		panelPhase2.add(txtQB);
		txtQB.setColumns(10);
		
		btnOk2 = new JButton("Ok");
		btnOk2.setBounds(10, 172, 65, 23);
		panelPhase2.add(btnOk2);
		
		lblQuestionA = new JLabel("");
		lblQuestionA.setBounds(10, 35, 394, 14);
		panelPhase2.add(lblQuestionA);
		
		lblQuestionB = new JLabel("");
		lblQuestionB.setBounds(10, 116, 394, 14);
		panelPhase2.add(lblQuestionB);
		
		panelPhase3 = new JPanel();
		panelPhase3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelPhase3.setBounds(10, 391, 414, 137);
		contentPane.add(panelPhase3);
		panelPhase3.setLayout(null);
		
		lblPassword = new JLabel("Nouveau mot de passe:");
		lblPassword.setBounds(10, 11, 127, 14);
		panelPhase3.add(lblPassword);
		
		txtNewPassword = new JPasswordField();
		txtNewPassword.setColumns(10);
		txtNewPassword.setBounds(10, 25, 394, 20);
		panelPhase3.add(txtNewPassword);
		
		lblRepetePassword = new JLabel("R\u00E9p\u00E9ter le mot de passe:");
		lblRepetePassword.setBounds(10, 56, 127, 14);
		panelPhase3.add(lblRepetePassword);
		
		txtNewPassword2 = new JPasswordField();
		txtNewPassword2.setColumns(10);
		txtNewPassword2.setBounds(10, 72, 394, 20);
		panelPhase3.add(txtNewPassword2);
		
		btnChanger = new JButton("Changer");
		btnChanger.setBounds(10, 103, 89, 23);
		panelPhase3.add(btnChanger);
		
		btnRetour = new JButton("Retour");
		btnRetour.setBounds(10, 40, 89, 23);
		contentPane.add(btnRetour);
		
		Component[] lesComponents = panelPhase2.getComponents();
		for(int i = 0; i < lesComponents.length; i++)
		{
			lesComponents[i].setEnabled(false);
		}
		lesComponents = panelPhase3.getComponents();
		for(int i = 0; i < lesComponents.length; i++)
		{
			lesComponents[i].setEnabled(false);
		}
		
		ecouteur = new Ecouteur();
		this.addWindowListener(ecouteur);
		btnRetour.addActionListener(ecouteur);
		btnOk1.addActionListener(ecouteur);
		btnOk2.addActionListener(ecouteur);
		btnChanger.addActionListener(ecouteur);
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
			else if(e.getSource() == btnOk1)
			{
				DAO_User daoUser = new DAO_User();
				tableau = daoUser.validExistance(txtNomJoueur.getText());
				
				if(tableau[0]!=0)
				{
					username = txtNomJoueur.getText();
					Component[] lesComponents = panelPhase1.getComponents();
					for(int i = 0; i < lesComponents.length; i++)
					{
						lesComponents[i].setEnabled(false);
					}
					lesComponents = panelPhase2.getComponents();
					for(int i = 0; i < lesComponents.length; i++)
					{
						lesComponents[i].setEnabled(true);
					}
					
					DAO_Question daoQuestion = new DAO_Question();
					String QuestionA = daoQuestion.getUneQuestion(tableau[0]);
					String QuestionB = daoQuestion.getUneQuestion(tableau[1]);
					
					if(QuestionA != null)
					{
						lblQuestionA.setText(QuestionA);
					}
					if(QuestionB != null)
					{
						lblQuestionB.setText(QuestionB);
					}	
				}
				else
				{
					JOptionPane.showMessageDialog(Forgot_Password.this, "Ce nom d'utilisateur n'existe pas");
				}
			}
			else if(e.getSource() == btnOk2)
			{
				boolean questionA,questionB;
				DAO_User daoUser = new DAO_User();
				questionA = daoUser.validQuestionA(txtQA.getText(), tableau[0], username);
				questionB = daoUser.validQuestionB(txtQB.getText(), tableau[1], username);
				
				if(questionA && questionB)
				{
					Component[] lesComponents = panelPhase2.getComponents();
					for(int i = 0; i < lesComponents.length; i++)
					{
						lesComponents[i].setEnabled(false);
					}
					lesComponents = panelPhase3.getComponents();
					for(int i = 0; i < lesComponents.length; i++)
					{
						lesComponents[i].setEnabled(true);
					}
				}
				else
				{
					if(!questionA && !questionB)
					{
						JOptionPane.showMessageDialog(Forgot_Password.this, "Les réponses des questions A et B sont invalides");
					}
					else if(!questionA)
					{
						JOptionPane.showMessageDialog(Forgot_Password.this, "La réponse de la question A est invalide");
					}
					else if(!questionB)
					{
						JOptionPane.showMessageDialog(Forgot_Password.this, "La réponse de la question B est invalide");
					}
				}
			}
			else if(e.getSource() == btnChanger)
			{
				if(!txtNewPassword.getText().equals(txtNewPassword2.getText()))
				{
					JOptionPane.showMessageDialog(Forgot_Password.this, "Les mots de passe ne sont pas identiques");
				}
				else if(!txtNewPassword.getText().matches("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[ !@#$%?&*()]).{9,30})"))
				{
					JOptionPane.showMessageDialog(Forgot_Password.this, "Le mot de passe doit contenir au moins: "
																+ "\n- Minimum 9 lettres."
																+"\n- Au moins une letttre minuscule."
																+"\n- Au moins une lettre majuscule."
																+"\n- Au moins un chiffre."
																+"\n- Au moins un symbole. Supporter au moins ceux-ci: !@#$%?&*()");
				}
				else
				{
					boolean valide;
					DAO_User daoUser = new DAO_User();
					valide = daoUser.updatePassword(username, txtNewPassword.getText());
					
					if(valide)
					{
						JOptionPane.showMessageDialog(Forgot_Password.this, "Le mot de passe a bien été modifié");
						login = new Login();
						login.setLocationRelativeTo(null);
						login.setVisible(true);
						dispose();
					}
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
		public void windowClosing(WindowEvent arg0) {
			laConnection.fermerConnection();
			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
