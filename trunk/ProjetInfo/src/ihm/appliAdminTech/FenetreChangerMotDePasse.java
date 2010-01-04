package ihm.appliAdminTech;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.PasswordFieldLimite;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.administrateur.MenuPrincipalAdmin;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliAdminTech.technicien.MenuPrincipalTech;
import ihm.appliAdminTech.technicien.PanneauTech;
import ihm.exceptionsInterface.MotDePasseNonRempliException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;

/**
 * FenetreAuthentification hérite de JFrame et implémente ActionListener
 * cette fenêtre permet à un administrateur ou à un technicien de changer de mot de passe
 * cette fenêtre est commune à l'administrateur et au technicien
 * @see MenuPrincipalAdmin
 * @see MenuPrincipalTech
 * @author KerGeek
 */

public class FenetreChangerMotDePasse extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * attributs privés : composants de la fenêtre
	 */
	private Compte compte;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelMsg = new JLabel("Veuillez compléter les champs suivants");
	private JLabel labelAncienMdp = new JLabel("ancien mot de passe");
	private PasswordFieldLimite ancienMdpARemplir = new PasswordFieldLimite(20,"");
	private JLabel labelNouveauMdp1 = new JLabel("nouveau mot de passe (6 caractères minimum)");
	private PasswordFieldLimite nouveauMdpARemplir1 = new PasswordFieldLimite(20,"");
	private JLabel labelNouveauMdp2 = new JLabel("confirmation du nouveau mot de passe");
	private PasswordFieldLimite nouveauMdpARemplir2 = new PasswordFieldLimite(20,"");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	// Accesseurs utiles
	
	/**
	 * @return	le {@link Administrateur#compte} de l'Administrateur connecté ou le {@link Technicien#compte} du Technicien connecté sur la FenetreChangerMotDePasse
	 */

	public Compte getCompte() {
		return compte;
	}

	/**
	 * Initialise le {@link Administrateur#compte} de la {@link FenetreChangerMotDePasse}
	 * @param compte
	 * le compte de l'individu connecté sur cette fenêtre
	 * @see Compte
	 */

	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	/**
	 * constructeur de FenetreChangerMotDePasse
	 * @param c
	 * le compte de l'individu connecté (Administrateur ou Technicien)
	 * @see BorderLayout
	 * @see JPanel
	 * @see 
	 */

	public FenetreChangerMotDePasse(Compte c){

		if(c.getType()==Compte.TYPE_ADMINISTRATEUR){
			this.setContentPane(new PanneauAdmin());
		}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){
			this.setContentPane(new PanneauTech());
		}

		this.setCompte(c);

		this.setTitle("Changer son mot de passe");
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(1200,800));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);

		labelAdminTech = new JLabel("Vous êtes connecté en tant que "+ this.getCompte().getId());
		labelAdminTech.setFont(UtilitaireIhm.POLICE4);
		labelAdminTech.setPreferredSize(new Dimension(1000,100));
		north.add(labelAdminTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		labelMsg.setPreferredSize(new Dimension(1000,50));
		labelMsg.setFont(UtilitaireIhm.POLICE2);
		center.add(labelMsg);

		JPanel panel1 = new JPanel();
		panel1.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelAncienMdp.setPreferredSize(new Dimension(600,50));
		labelAncienMdp.setMinimumSize(new Dimension(600,50));
		panel1.add(labelAncienMdp);
		center.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(UtilitaireIhm.TRANSPARENCE);	
		ancienMdpARemplir.setPreferredSize(new Dimension(300,50));
		ancienMdpARemplir.setMinimumSize(new Dimension(300,50));
		panel2.add(ancienMdpARemplir);
		center.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelNouveauMdp1.setPreferredSize(new Dimension(600,50));
		labelNouveauMdp1.setMinimumSize(new Dimension(600,50));
		panel3.add(labelNouveauMdp1);
		center.add(panel3);

		JPanel panel4 = new JPanel();
		panel4.setBackground(UtilitaireIhm.TRANSPARENCE);	
		nouveauMdpARemplir1.setPreferredSize(new Dimension(300,50));
		nouveauMdpARemplir1.setMinimumSize(new Dimension(300,50));
		panel4.add(nouveauMdpARemplir1);
		center.add(panel4);

		JPanel panel5 = new JPanel();
		panel5.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelNouveauMdp2.setPreferredSize(new Dimension(600,50));
		labelNouveauMdp2.setMinimumSize(new Dimension(600,50));
		panel5.add(labelNouveauMdp2);
		center.add(panel5);

		JPanel panel6 = new JPanel();
		panel6.setBackground(UtilitaireIhm.TRANSPARENCE);	
		nouveauMdpARemplir2.setPreferredSize(new Dimension(300,50));
		nouveauMdpARemplir2.setMinimumSize(new Dimension(300,50));
		panel6.add(nouveauMdpARemplir2);
		center.add(panel6);

		boutonValider.setPreferredSize(new Dimension(350,70));
		boutonValider.setMinimumSize(new Dimension(350,70));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.addActionListener(this);
		center.add(boutonValider);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);

		boutonRetour.setPreferredSize(new Dimension(300,50));
		boutonRetour.setMaximumSize(new Dimension(300,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		south.add(boutonRetour);
		boutonRetour.addActionListener(this);

		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}


	/**
	 * méthode qui est exécutée si l'individu a appuyé sur le bouton "Valider"
	 * affiche une fenetre de confirmation si les champs entrés sont corrects (ancien mot de passe correct et nouveau mot de passe respectant les contraintes et confirmé
	 * sinon affiche une MsgBox adaptée puis ouvre une nouvelle FenetreChangerMotDePasse
	 * @param arg0 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws MotDePasseNonRempliException
	 * @throws ConnexionFermeeException
	 */

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try{
			if(arg0.getSource()==boutonValider){
				//TODO verifier que ca marche, y compris quand on ne remplit rien,
				//il semble que l'exception MotDePasseNonRempliException ne serve a rien...
				
				String ancienMdp = UtilitaireIhm.obtenirMotDePasse(ancienMdpARemplir);
				String nouveauMdp1 = UtilitaireIhm.obtenirMotDePasse(nouveauMdpARemplir1);
				String nouveauMdp2 = UtilitaireIhm.obtenirMotDePasse(nouveauMdpARemplir2);
				
				if (UtilitaireIhm.verifieChampsModifMdp(this.getCompte(), ancienMdp, nouveauMdp1, nouveauMdp2)){
					this.getCompte().setMotDePasse(nouveauMdp1);
					DAOCompte.updateCompte(this.getCompte());
					new FenetreConfirmation(this.getCompte(),this);
					//à voir s'il ne faut pas passer par updateAdmin et updateTech (mais normalement non)
				}
				else{
					MsgBox.affMsg("L'un des champs entrés au moins est incorrect");
					new FenetreChangerMotDePasse(this.getCompte());
				}
				ancienMdp = null;//pour augmenter la sécurité de l'application
				nouveauMdp1 = null;//pour augmenter la sécurité de l'application
				nouveauMdp2 = null;//pour augmenter la sécurité de l'application
			}
			else if (arg0.getSource()==boutonRetour){
				if(this.getCompte().getType()==Compte.TYPE_ADMINISTRATEUR){
					new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				}
				else{
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
			}
		} 
		catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} 
		catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
		catch(MotDePasseNonRempliException e){
			MsgBox.affMsg("Veuillez bien remplir les trois champs");
			new FenetreChangerMotDePasse(this.getCompte());
		}
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}