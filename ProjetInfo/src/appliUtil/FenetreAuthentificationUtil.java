package appliUtil;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import gestionBaseDeDonnees.UtilitaireSQL;

public class FenetreAuthentificationUtil extends JFrame {


	/*
	 * Rappel : JLabel = Simple étiquette pour écrire du texte 
	 * JTextField = champ de saisie de texte
	 * JPasswordField = idem mais avec texte saisi non affiché
	 * JButton = bouton (si si c'est vrai)
	 */
	JLabel labelPseudo;
	JTextField fieldPseudo;
	JButton boutonValider;

	public FenetreAuthentificationUtil(String titre) {
		super(titre);

		/* juste pour que le programme s'arrete bien quand vous cliquer sur la
		 * petite croix en haut de la fenetre */
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		/*définition d'un moyen de disposer les composants*/
		this.setLayout(new GridLayout(3, 2));
		labelPseudo = new JLabel("login");
		fieldPseudo = new JTextField();

		JButton boutonValider = new JButton("valider");
		/*Ajout d'un action Listener pour effectuer une action quand
		 * on clic sur le bouton. Ce listener est défini dans une classe
		 * interne un peu plus loin */
		boutonValider.addActionListener(new ActionLogin());

		setSize(400, 250);
		//ajout des composants à la fenetre
		add(labelPseudo);
		add(fieldPseudo);
		add(boutonValider);
	}

	//on vérifie que les paramètres de connexion sont bons
	protected void authentifier(String pseudo)
	throws SQLException, ClassNotFoundException {
		UtilitaireSQL.testerIdent(pseudo);
	}

	//ActionListener vérifiant les paramètres de connexion.
	class ActionLogin implements ActionListener {

		public void actionPerformed(ActionEvent actionevent) {
			try {
				//test des paramètres
				authentifier(fieldPseudo.getText());
				//si aucune exception levée, on ferme la fenetre
				//d'authentification et on ouvre la fenetre d accueil
				dispose();
				AccueilUtil f = new AccueilUtil("Accueil");
				f.setVisible(true);
			} catch (Exception e) {
				//si une exception est levée on affiche une popup d'erreur
				JOptionPane.showMessageDialog(null, "erreur de connexion",
						"erreur", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	//fonction pour afficher la fenêtre
	public static void main(String[] args) {

		FenetreAuthentificationUtil f = new FenetreAuthentificationUtil("Authentification de l'utilisateur");
		f.setVisible(true);

	}

}
