package appliAdminTech;

import gestionBaseDeDonnees.UtilitaireSQL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import appliUtil.Panneau;

public class FenetreAuthentification extends JFrame {

	private Panneau container = new Panneau();
	private JLabel labelLogin = new JLabel("login");
	private JTextField idARemplir = new JTextField("");
	private JLabel labelPassword = new JLabel("Mot de passe");
	private JPasswordField motDePasseARemplir = new JPasswordField();
	private JButton boutonValider = new JButton ("Valider");

	public FenetreAuthentification() {

		super();

		this.setTitle("Authentification");
		this.setSize(700,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		/*définition d'un moyen de disposer les composants*/
		GridLayout g = new GridLayout(3,2);
		this.setLayout(g);

		Font police = new Font("Arial Narrow", Font.BOLD, 16);
		
		labelLogin.setFont(police);
		labelLogin.setPreferredSize(new Dimension(150, 30));
		labelLogin.setForeground(Color.BLACK);
		
		idARemplir.setFont(police);
		idARemplir.setPreferredSize(new Dimension(150, 30));
		idARemplir.setForeground(Color.BLUE);
		
		labelPassword.setFont(police);
		labelPassword.setPreferredSize(new Dimension(150, 30));
		labelPassword.setForeground(Color.BLACK);
		
		motDePasseARemplir.setFont(police);
		motDePasseARemplir.setPreferredSize(new Dimension(150, 30));
		motDePasseARemplir.setForeground(Color.BLUE);

		this.setContentPane(container);
		
		this.getContentPane().add(labelLogin);
		this.getContentPane().add(idARemplir);
		this.getContentPane().add(labelPassword);
		this.getContentPane().add(motDePasseARemplir);
		this.getContentPane().add(boutonValider);
		
		this.setVisible(true);




	}

	//on vérifie que les paramètres de connexion sont bons
	protected void authentifier(String pseudo, String motDePasse) throws SQLException, ClassNotFoundException {
		UtilitaireSQL.testerAuthent(pseudo,motDePasse);
		System.out.println("résultat de testerAuthent = "+UtilitaireSQL.testerAuthent(pseudo,motDePasse));
	}

	//ActionListener vérifiant les paramètres de connexion.
	class ActionLogin implements ActionListener {

		public void actionPerformed(ActionEvent actionevent) {
			try {
				//test des paramètres
				authentifier(idARemplir.getText(),motDePasseARemplir.getText());
				//si aucune exception levée et si l'utilisateur existe bien dans la base, on ferme la fenetre
				//d'authentification et on ouvre la fenetre de l'utilisateur
				dispose();
				
				if (UtilitaireSQL.testerAuthent(idARemplir.getText(),motDePasseARemplir.getText())==-1){
					dispose();
				}
			} catch (Exception e) {
				//si une exception est levée on affiche une popup d'erreur
				JOptionPane.showMessageDialog(null, "erreur de connexion de l'utilisateur",
						"erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void main (String [] args){
		System.out.println("Création d'une fenêtre d'authentification pour l'application AdminTech");
		FenetreAuthentification f = new FenetreAuthentification();
	}
}








//si testerAuthent = -1 : nouvelle fenêtre avec "d'authentification" mais sinon la même
//sinon ouvrir la connexion

//si testerAuthent=1 : on renvoie sur la fenêtre MenuPrincipalAdmin
//si testerAuthent=2 : on renvoie sur la fenêtre MenuPrincipalTech

