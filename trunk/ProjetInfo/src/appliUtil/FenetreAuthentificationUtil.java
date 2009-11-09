package appliUtil;

import gestionBaseDeDonnees.UtilitaireSQL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Compte;
import metier.Utilisateur;

public class FenetreAuthentificationUtil extends JFrame implements ActionListener {
	// définition des polices
	public static final Font POLICE1 = new Font("Arial Narrow", Font.BOLD, 18);
	public static final Font POLICE2 = new Font("Arial Narrow", Font.BOLD, 16);
	public static final Font POLICE3 = new Font("Arial Narrow", Font.PLAIN,16);
	public static final Font POLICE4 = new Font("Arial Narrow", Font.ITALIC,14);

	private final JLabel labelUtil = new JLabel("");
	private final JLabel labelInvitation = new JLabel ("Veuillez vous identifier. ");
	private final JLabel labelErreur = new JLabel("Login inconnu. Veuillez vous réidentifier. ");
	private final Panneau container = new Panneau();
	private final JLabel labelLogin = new JLabel("login");
	private final JTextField idARemplir = new JTextField("");
	private final JButton boutonValider = new JButton ("Valider");

	public FenetreAuthentificationUtil(){

		System.out.println("Ouverture d'une fenêtre d'authentification de l'utilisateur");
		this.setTitle("Authentification");
		this.setSize(700,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		container.setLayout(new BorderLayout());

		JPanel top = new JPanel();

		labelInvitation.setFont(POLICE1);
		top.add(labelInvitation);
		labelLogin.setFont(POLICE2);
		top.add(labelLogin);
		idARemplir.setFont(POLICE3);
		idARemplir.setPreferredSize(new Dimension(150, 30));
		idARemplir.setForeground(Color.BLUE);
		top.add(idARemplir);
		top.add(boutonValider);

		//On ajoute notre Fenetre à la liste des auditeurs de notre Bouton
		boutonValider.addActionListener(this);

		container.add(top, BorderLayout.SOUTH);
		this.setContentPane(container);
		this.setVisible(true);
	}

	public Utilisateur getUtilisateur() throws SQLException, ClassNotFoundException {
		return gestionBaseDeDonnees.DAOUtilisateur.getUtilisateurById(idARemplir.getText());
	}

	//on vérifie que les paramètres de connexion sont bons
	protected void authentifier(String pseudo) throws SQLException, ClassNotFoundException {
		UtilitaireSQL.testerIdent(pseudo);
		System.out.println("résultat de testerIdent = "+UtilitaireSQL.testerIdent(pseudo));
	}

	/**
	 * C'est la méthode qui sera appelée lors d'un clic sur notre bouton
	 */
	public void actionPerformed(ActionEvent arg0) {
		//Lorsque nous cliquons sur notre bouton, on met à jour le JLabel
		Utilisateur u = new Utilisateur("000", new Compte("000","",Compte.TYPE_UTILISATEUR),"Utilisateur","Test","1 rue des Lilas");
		/*try {
			u = this.getUtilisateurById(idARemplir.getText());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

		labelUtil.setFont(POLICE4);
		labelUtil.setText("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		this.add(labelUtil,BorderLayout.NORTH);

		//vérification des paramètres de connexion.
		try {
			//test des paramètres
			String id = idARemplir.getText();
			System.out.println("id renseigné = "+id);
			authentifier(idARemplir.getText());
			//si aucune exception levée et si l'utilisateur existe bien dans la base, on ferme la fenetre
			//d'authentification et on ouvre la fenetre de l'utilisateur

			if (UtilitaireSQL.testerIdent(idARemplir.getText())){
				MenuUtilisateur m = new MenuUtilisateur(u);
				m.setVisible(true);
			}
		}
		catch (Exception e) {
			//si une exception est levée on affiche une popup d'erreur
			dispose();
			FenetreAuthentificationUtil f = new FenetreAuthentificationUtil();
			f.add(labelErreur,BorderLayout.NORTH);
			labelErreur.setForeground(Color.RED);
			f.setVisible(true);
		}
	}
}