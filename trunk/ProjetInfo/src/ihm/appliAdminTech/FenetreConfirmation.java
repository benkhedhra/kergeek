package ihm.appliAdminTech;

import ihm.MsgBox;
import ihm.appliAdminTech.administrateur.FenetreCreationCompteAdmin;
import ihm.appliAdminTech.administrateur.MenuPrincipalAdmin;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliAdminTech.technicien.FenetreEnregistrerVeloTech;
import ihm.appliAdminTech.technicien.MenuPrincipalTech;
import ihm.appliAdminTech.technicien.PanneauTech;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;

public class FenetreConfirmation extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Compte compte;
	private JFrame fenetrePrecedente;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelConfirm = new JLabel("");
	private JButton bouton1 = new JButton("");
	private JButton bouton2 = new JButton("");
	private JButton bouton3 = new JButton("");

	public FenetreConfirmation(String msg,Compte c,JFrame fenetrePrec){

		if(c.getType()==Compte.TYPE_ADMINISTRATEUR){
			this.setContentPane(new PanneauAdmin());
		}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){
			this.setContentPane(new PanneauTech());
		}

		compte=c;
		fenetrePrecedente=fenetrePrec;

		this.setTitle("Ecran de confirmation");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		labelAdminTech = new JLabel("Vous êtes connecté en tant que "+ c.getId());
		labelAdminTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdminTech.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,40));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new GridLayout(1,3));

		//situations possibles pour un administrateur
		if (fenetrePrec.getTitle().equals("Création d'un nouveau compte")){
			north.add(labelAdminTech);
			this.getContentPane().add(north,BorderLayout.NORTH);
			labelConfirm.setText("Le nouveau compte a bien été créé. ");
			bouton1.setText("Créer un autre compte");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			JPanel panel = new JPanel();
			panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			south.add(panel);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Modification d'un compte")){
			north.add(labelAdminTech);
			this.getContentPane().add(north,BorderLayout.NORTH);
			labelConfirm.setText("La modification ou résiliation a bien été enregistrée. ");
			bouton1.setText("Afficher informations sur un autre compte");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			JPanel panel = new JPanel();
			panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			south.add(panel);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Envoyer une demande d'assignation")){
			north.add(labelAdminTech);
			this.getContentPane().add(north,BorderLayout.NORTH);
			labelConfirm.setText("La demande d'assignation a bien été envoyée. ");
			bouton1.setText("Voir l'état d'une autre station");
			bouton1.addActionListener(this);
			bouton2.setText("Voir les stations sur et sous-occupées");
			bouton2.addActionListener(this);
			bouton3.setText("Retour au menu principal");
			bouton3.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
			south.add(bouton3);
		}

		//situations possibles pour un technicien
		else if(fenetrePrec.getTitle().equals("Enregistrer un nouveau vélo")){
			north.add(labelAdminTech);
			this.getContentPane().add(north,BorderLayout.NORTH);
			labelConfirm.setText("Le vélo a bien été ajouté et affecté au garage. ");
			bouton1.setText("Enregistrer un autre nouveau vélo");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			JPanel panel = new JPanel();
			panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			south.add(panel);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Retirer un vélo défectueux d'une station")){
			north.add(labelAdminTech);
			this.getContentPane().add(north,BorderLayout.NORTH);
			labelConfirm.setText("Le vélo défectueux a bien été retiré de la station et affecté au garage. ");
			bouton1.setText("Retirer un autre vélo défectueux");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			JPanel panel = new JPanel();
			panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			south.add(panel);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Prendre en charge l'assignation")){
			north.add(labelAdminTech);
			this.getContentPane().add(north,BorderLayout.NORTH);
			labelConfirm.setText("Le déplacement de ces vélos a bien été confirmé. ");
			bouton1.setText("Gérer une autre demande d'assignation");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			JPanel panel = new JPanel();
			panel.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
			south.add(panel);
			south.add(bouton1);
			south.add(bouton2);
		}

		//situations possibles dans tous les cas : déconnexion
		else if(fenetrePrec.getTitle().equals("Menu principal de l'administrateur") || fenetrePrec.getTitle().equals("Menu principal du technicien")){
			labelConfirm.setText("Au revoir et à bientôt ! ");
			// dans l'idée l'écran ne reste affiché que 3 secondes et la fenêtre d'authentification apparaîtautomatiquement au terme des 3 secondes
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				MsgBox.affMsg(e.getMessage());
			}
			new FenetreAuthentification(false);
		}
		labelConfirm.setFont(FenetreAuthentificationUtil.POLICE2);
		this.getContentPane().add(labelConfirm,BorderLayout.CENTER);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(fenetrePrecedente.getTitle().equals("Création d'un nouveau compte")){
			if(arg0.getSource()==bouton1){
				new FenetreCreationCompteAdmin(new Administrateur (compte));
			}
			else if (arg0.getSource()==bouton2){
				new MenuPrincipalAdmin(new Administrateur (compte));
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Modification d'un compte")){
			if(arg0.getSource()==bouton1){
				//new FenetreRechercherCompteAdmin(new Administrateur (compte));
			}
			else if (arg0.getSource()==bouton2){
				new MenuPrincipalAdmin(new Administrateur (compte));
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Envoyer une demande d'assignation")){
			if(arg0.getSource()==bouton1){
				//new FenetreEtatStation(new Administrateur (compte));
			}
			else if (arg0.getSource()==bouton2){
				//new FenetreStationsSurSous(new Administrateur (compte));
			}
			else if (arg0.getSource()==bouton3){
				new MenuPrincipalAdmin(new Administrateur (compte));
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Enregistrer un nouveau vélo")){
			if(arg0.getSource()==bouton1){
				//new FenetreEnregistrerVeloTech(new Technicien (compte));
			}
			else if (arg0.getSource()==bouton2){
				new MenuPrincipalTech(new Technicien (compte));
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Retirer un vélo défectueux d'une station")){
			if(arg0.getSource()==bouton1){
				//new FenetreRetirerVeloTech(new Technicien (compte));
			}
			else if (arg0.getSource()==bouton2){
				new MenuPrincipalTech(new Technicien (compte));
			}
		}
		else if(fenetrePrecedente.getTitle().equals("Prendre en charge l'assignation")){
			if(arg0.getSource()==bouton1){
				//new FenetreGererUneDemandeTech(new Technicien (compte));
			}
			else if (arg0.getSource()==bouton2){
				new MenuPrincipalTech(new Technicien (compte));
			}
		}

	}

}