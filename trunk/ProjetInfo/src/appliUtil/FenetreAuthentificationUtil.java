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

	private Panneau p = new Panneau();
	private JPanel container = new JPanel();
	private JLabel labelBienvenue = new JLabel("");
	private JLabel labelInvitation = new JLabel("");
	private JTextField idARemplir = new JTextField("");
	private JButton boutonValider = new JButton ("Valider");

	public FenetreAuthentificationUtil(Boolean erreurAuthent){

		System.out.println("Ouverture d'une fenêtre d'authentification de l'utilisateur");

		this.setTitle("Authentification");
		this.setSize(700,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// on définit un BorderLayout
		container.setLayout(new BorderLayout());
		container.add(p,BorderLayout.CENTER);

		labelBienvenue.setText("Bienvenue ! ");
		container.add(labelBienvenue,BorderLayout.NORTH);

		if(erreurAuthent){
			labelInvitation.setText("Identifiant inconnu. Veuillez à nouveau entrer votre identifiant");
			labelInvitation.setForeground(Color.RED);
		}
		else{
			labelInvitation.setText("Veuillez entrer votre identifiant");		
		}
		labelInvitation.setFont(POLICE1);

		JPanel center = new JPanel();
		labelInvitation.setFont(POLICE2);
		center.add(labelInvitation);
		idARemplir.setFont(POLICE3);
		idARemplir.setPreferredSize(new Dimension(150, 30));
		idARemplir.setForeground(Color.BLUE);
		center.add(idARemplir);
		container.add(center, BorderLayout.CENTER);

		container.add(boutonValider,BorderLayout.SOUTH);
		//On ajoute notre Fenetre à la liste des auditeurs de notre Bouton
		boutonValider.addActionListener(this);

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
		Utilisateur u = LancerAppliUtil.UTEST;
		/*try {
			u = this.getUtilisateur();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

		//vérification des paramètres de connexion.
		try {
			//test des paramètres
			String id = idARemplir.getText();
			System.out.println("id renseigné = "+id);
			authentifier(idARemplir.getText());
			//si aucune exception levée et si l'utilisateur existe bien dans la base, on ferme la fenetre
			//d'authentification et on ouvre la fenetre de l'utilisateur

			if (UtilitaireSQL.testerIdent(idARemplir.getText())){
				this.dispose();
				MenuUtilisateur m = new MenuUtilisateur(u);
				m.setVisible(true);
			}
		}
		catch (Exception e) {
			//si une exception est levée on affiche une popup d'erreur
			this.setVisible(false);
			FenetreAuthentificationUtil f = new FenetreAuthentificationUtil(true);
			f.setVisible(true);
		}
	}
}