package ihm.appliAdminTech.administrateur;

import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Administrateur;
import metier.Compte;

public class FenetreCreationCompteAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private JLabel labelAdmin = new JLabel("");;
	private JLabel labelMsg = new JLabel("Entrer les données du titulaire du nouveau compte");
	private JLabel labelQualite = new JLabel("Qualité");
	private int typeEntre;
	private JLabel labelNom = new JLabel("Nom");
	private JTextField nomARemplir = new JTextField("");
	private JLabel labelPrenom = new JLabel("Prénom");
	private JTextField prenomARemplir = new JTextField("");
	private JLabel labelAdresseEMail = new JLabel("Adresse e-mail");
	private JTextField adresseEMailARemplir = new JTextField("");
	private JLabel labelAdressePostale = new JLabel("Adresse Postale");
	private JTextField adressePostaleARemplir = new JTextField("");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");


	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}


	public FenetreCreationCompteAdmin(Administrateur a){

		System.out.println("Fenêtre pour créer un nouveau compte");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Création d'un compte");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(300,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,100));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		center.setLayout(new BorderLayout());
		
		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		String[] types = new String[3];
		//idée : getAllStations : ici lignes suivantes pour tester provisoirement
		types[0] = "utilisateur";
		types[1] = "administrateur";
		types[2] = "technicien";
		DefaultComboBoxModel model = new DefaultComboBoxModel(types);
		JComboBox qualiteARemplir = new JComboBox(model);
		qualiteARemplir.setPreferredSize(new Dimension(150,30));
		qualiteARemplir.setMaximumSize(new Dimension(150,30));
		qualiteARemplir.setFont(FenetreAuthentificationUtil.POLICE3);
		qualiteARemplir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Object o = ((JComboBox)ae.getSource()).getSelectedItem();
				String qualiteEntree = (String)o;

				if(qualiteEntree.equals("utilisateur")){typeEntre=Compte.TYPE_UTILISATEUR;}
				if(qualiteEntree.equals("administrateur")){typeEntre=Compte.TYPE_ADMINISTRATEUR;}
				if(qualiteEntree.equals("technicien")){typeEntre=Compte.TYPE_TECHNICIEN;}

			}

		});

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(550,350));
		centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerWest.setLayout(new GridLayout(5,2));

		labelQualite.setPreferredSize(new Dimension(150,30));
		labelQualite.setMaximumSize(new Dimension(150,30));
		centerWest.add(labelQualite);
		centerWest.add(qualiteARemplir);
		labelNom.setPreferredSize(new Dimension(150,30));
		labelNom.setMaximumSize(new Dimension(150,30));
		centerWest.add(labelNom);
		nomARemplir.setPreferredSize(new Dimension(150,30));
		nomARemplir.setMaximumSize(new Dimension(150,30));
		centerWest.add(nomARemplir);
		labelPrenom.setPreferredSize(new Dimension(150,30));
		labelPrenom.setMaximumSize(new Dimension(150,30));
		centerWest.add(labelPrenom);
		prenomARemplir.setPreferredSize(new Dimension(150,30));
		prenomARemplir.setMaximumSize(new Dimension(150,30));
		centerWest.add(prenomARemplir);

		if(typeEntre==Compte.TYPE_UTILISATEUR){
			labelAdresseEMail.setPreferredSize(new Dimension(150,30));
			labelAdresseEMail.setMaximumSize(new Dimension(150,30));
			centerWest.add(labelAdresseEMail);
			adresseEMailARemplir.setPreferredSize(new Dimension(150,30));
			adresseEMailARemplir.setMaximumSize(new Dimension(150,30));
			centerWest.add(adresseEMailARemplir);
			labelAdressePostale.setPreferredSize(new Dimension(150,30));
			labelAdressePostale.setMaximumSize(new Dimension(150,30));
			centerWest.add(labelAdressePostale);
			adressePostaleARemplir.setPreferredSize(new Dimension(150,30));
			adressePostaleARemplir.setMaximumSize(new Dimension(150,30));
			centerWest.add(adressePostaleARemplir);
		}
		
		center.add(centerWest,BorderLayout.WEST);
		
		JPanel centerEast = new JPanel();
		centerEast.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(200,350));
		boutonValider.setPreferredSize(new Dimension(80,40));
		boutonValider.setMaximumSize(new Dimension(80,40));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.addActionListener(this);
		centerEast.add(boutonValider);
		center.add(centerEast,BorderLayout.EAST);

		this.getContentPane().add(center,BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,50));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);
		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}		

}
