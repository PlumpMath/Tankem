package tankemNiveau;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.Color;

public class DialogueLogin extends JDialog {
private Interface parent;
private JLabel labelUsername;
private Ecouteur ec;
private JLabel labelMdp;
private JTextField champUsername;
private JPasswordField champMdp;
private JButton boutonConnecter;
private JButton boutonAnnuler;
static boolean isContinuer = false;
static String username="";
private JLabel lblNewLabel;
private JLabel labelText;
//Avec annexe9a
	/**
	 * Create the dialog.
	 */
	public DialogueLogin(Interface parent, String title, boolean etatDialogue) {
		super(parent,title,etatDialogue);
		//Super s'occupe des trois codes.
		//setModal(etatDialogue);
		//this.parent = parent;
		//setTitle(title);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		labelUsername = new JLabel("Username:");
		labelUsername.setBounds(40, 93, 102, 50);
		getContentPane().add(labelUsername);
		
		labelMdp = new JLabel("Mot de passe:");
		labelMdp.setBounds(40, 154, 102, 50);
		getContentPane().add(labelMdp);
		
		champUsername = new JTextField();
		champUsername.setBounds(146, 104, 200, 29);
		getContentPane().add(champUsername);
		champUsername.setColumns(10);
		
		champMdp = new JPasswordField();
		champMdp.setColumns(10);
		champMdp.setBounds(146, 165, 200, 29);
		getContentPane().add(champMdp);
		
		boutonConnecter = new JButton("Login");
		boutonConnecter.setBounds(151, 227, 89, 23);
		getContentPane().add(boutonConnecter);
		
		boutonAnnuler = new JButton("Annuler");
		boutonAnnuler.setBounds(257, 227, 89, 23);
		getContentPane().add(boutonAnnuler);
		
		lblNewLabel = new JLabel("LEVEL BUILDER: TANKEM!!");
		lblNewLabel.setFont(new Font("Tekton Pro", Font.PLAIN, 24));
		lblNewLabel.setBounds(87, 11, 384, 71);
		getContentPane().add(lblNewLabel);
		
		labelText = new JLabel("");
		labelText.setForeground(Color.RED);
		labelText.setBounds(146, 79, 230, 14);
		getContentPane().add(labelText);
		
		ec = new Ecouteur();
		boutonConnecter.addActionListener(ec);
		boutonAnnuler.addActionListener(ec);

	}
	private class Ecouteur implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource()==boutonAnnuler)
			{
				System.out.println("ehllloo");
				isContinuer= false;
				dispose();
			}
			else if (e.getSource()==boutonConnecter)
			{
				//Tenter de se connecter...
				String mdpSalt ="";
				try {
					//Chercher le salt du mdp du serveur
					mdpSalt=Operations.trouverMdpEncrypte(champUsername.getText());
					//Passer le salt du premier mdp
					//reponseHashe est le mdp encrypter.
					String reponseHashe = BCrypt.hashpw(champMdp.getText(), mdpSalt);
					System.out.println("saltora :"+mdpSalt);
					System.out.println("resultat:"+reponseHashe);
					
					//On compare les 2 mots de passe:
					if (reponseHashe.equals(mdpSalt))
					{
						//Le mot de passe est bon
						isContinuer= true;
						System.out.println("Bravo!!!");
						username = champUsername.getText();
						dispose();
					}
					else //sinon envoyer un message
					{
						labelText.setText("Mauvais mot de passe...");
					}

					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					labelText.setText("Username non existant...");
				}
				
			}
			
		}
		
	}
}
