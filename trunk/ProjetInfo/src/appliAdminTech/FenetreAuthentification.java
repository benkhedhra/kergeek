package appliAdminTech;

import gestionBaseDeDonnees.UtilitaireSQL;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import technicien.MenuPrincipalTech;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;
import metier.Utilisateur;
import administrateur.MenuPrincipalAdmin;
import appliUtil.FenetreAuthentificationUtil;
import appliUtil.LancerAppliUtil;
import appliUtil.Panneau;

public class FenetreAuthentification extends JFrame implements ActionListener {


	// définition des polices
	public static final Font POLICE1 = new Font("Arial Narrow", Font.BOLD, 18);
	public static final Font POLICE2 = new Font("Arial Narrow", Font.BOLD, 16);
	public static final Font POLICE3 = new Font("Arial Narrow", Font.PLAIN,16);
	public static final Font POLICE4 = new Font("Arial Narrow", Font.ITALIC,14);

	private Panneau p = new Panneau();
	private JPanel container = new JPanel();
	private JLabel labelInvitation = new JLabel("");
	private JLabel labelId = new JLabel("identifiant");
	private JTextField idARemplir = new JTextField("");
	private JLabel labelMotDePasse = new JLabel("mot de passe");
	private JPasswordField motDePasseARemplir = new JPasswordField();
	private JButton boutonValider = new JButton ("Valider");

	public FenetreAuthentification (Boolean erreurAuthent){

		System.out.println("Ouverture d'une fenêtre d'authentification de l'utilisateur");

		this.setTitle("Authentification");
		this.setSize(700,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// on définit un BorderLayout
		container.setLayout(new BorderLayout());
		container.add(p,BorderLayout.CENTER);

		if(erreurAuthent){
			labelInvitation.setText("Combinaison identifiant / mot de passe incorrecte. Veuillez à nouveau vous authentifier");
			labelInvitation.setForeground(Color.RED);
		}
		else{
			labelInvitation.setText("Bienvenue ! Veuillez vous authentifier");		
		}
		labelInvitation.setFont(POLICE1);
		container.add(labelInvitation,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setLayout(new GridLayout(2,2));
		labelId.setFont(POLICE3);
		labelId.setPreferredSize(new Dimension(150,30));
		center.add(labelId);

		idARemplir.setFont(POLICE3);
		idARemplir.setPreferredSize(new Dimension(150, 30));
		idARemplir.setForeground(Color.BLUE);
		center.add(idARemplir);
		
		labelMotDePasse.setFont(POLICE3);
		labelMotDePasse.setPreferredSize(new Dimension(150,30));
		center.add(labelMotDePasse);
		
		motDePasseARemplir.setFont(POLICE3);
		motDePasseARemplir.setPreferredSize(new Dimension(150, 30));
		motDePasseARemplir.setForeground(Color.BLUE);
		center.add(motDePasseARemplir);

		container.add(center, BorderLayout.CENTER);

		boutonValider.setPreferredSize(new Dimension(50,50));
		container.add(boutonValider,BorderLayout.SOUTH);
		//On ajoute notre Fenetre à la liste des auditeurs de notre Bouton
		boutonValider.addActionListener(this);

		this.setContentPane(container);
		this.setVisible(true);
	}

	public Administrateur getAdministrateur() throws SQLException, ClassNotFoundException {
		return gestionBaseDeDonnees.DAOAdministrateur.getAdministrateurById(idARemplir.getText());
	}
	
	public Technicien getTechnicien() throws SQLException, ClassNotFoundException {
		return gestionBaseDeDonnees.DAOTechnicien.getTechnicienById(idARemplir.getText());
	}
	
	//on vérifie que les paramètres de connexion sont bons
	protected void authentifier(String pseudo, String motDePasse) throws SQLException, ClassNotFoundException {
		UtilitaireSQL.testerAuthent(pseudo,motDePasse);
		System.out.println("résultat de testerAuthent = "+UtilitaireSQL.testerAuthent(pseudo,motDePasse));
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
			String mdp = motDePasseARemplir.getText();
			System.out.println("id renseigné = "+id + "\n mot de passe renseigné = "+mdp);
			authentifier(id,mdp);
			int type = UtilitaireSQL.testerAuthent(id,mdp);
			//si aucune exception levée et si l'utilisateur existe bien dans la base, on ferme la fenetre
			//d'authentification et on ouvre la fenetre de l'utilisateur

			this.dispose();

			if (type==Compte.TYPE_ADMINISTRATEUR){
				MenuPrincipalAdmin m = new MenuPrincipalAdmin(this.getAdministrateur());
				m.setVisible(true);
			}
			else if (type==Compte.TYPE_TECHNICIEN){
				MenuPrincipalTech m = new MenuPrincipalTech(this.getTechnicien());
				m.setVisible(true);
			}
		}
		catch (Exception e) {
			//si une exception est levée on affiche une popup d'erreur
			this.setVisible(false);
			FenetreAuthentification f = new FenetreAuthentification(true);
			f.setVisible(true);
		}
	}
	
	
	
	
	public static void main (String [] args){
		//System.out.println("Création d'une fenêtre d'authentification pour l'application AdminTech");
		//FenetreAuthentification f = new FenetreAuthentification(false);
		Administrateur ATEST = new Administrateur(new Compte("","",Compte.TYPE_ADMINISTRATEUR));
		MenuPrincipalAdmin m = new MenuPrincipalAdmin(ATEST);
	}
}