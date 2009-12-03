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

public class FenetreRechercherCompteAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private boolean stat;
	private JLabel labelAdmin = new JLabel("");;
	private JLabel labelMsg = new JLabel("Rechercher par : ");
	private JLabel labelQualite = new JLabel("Qualité");
	private int typeEntre;
	private JLabel labelId = new JLabel("Identifiant");
	private JTextField idARemplir = new JTextField("");
	private String idEntre;
	private JLabel labelNom = new JLabel("Nom");
	private JTextField nomARemplir = new JTextField("");
	private String nomEntre;
	private JLabel labelPrenom = new JLabel("Prénom");
	private JTextField prenomARemplir = new JTextField("");
	private String prenomEntre;
	private JLabel labelAdresseEMail = new JLabel("Adresse e-mail");
	private JTextField adresseEMailARemplir = new JTextField("");
	private String adresseEMailEntree;
	private JButton boutonValider = new JButton("Lancer la recherche");
	private JButton boutonRetour = new JButton("Retour au menu principal");


	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public int getTypeEntre() {
		return typeEntre;
	}

	public void setTypeEntre(int typeEntre) {
		this.typeEntre = typeEntre;
	}

	public String getIdEntre() {
		return idEntre;
	}

	public void setIdEntre(String idEntre) {
		this.idEntre = idEntre;
	}

	public String getNomEntre() {
		return nomEntre;
	}

	public void setNomEntre(String nomEntre) {
		this.nomEntre = nomEntre;
	}

	public String getPrenomEntre() {
		return prenomEntre;
	}

	public void setPrenomEntre(String prenomEntre) {
		this.prenomEntre = prenomEntre;
	}

	public String getAdresseEMailEntree() {
		return adresseEMailEntree;
	}

	public void setAdresseEMailEntree(String adresseEMailEntree) {
		this.adresseEMailEntree = adresseEMailEntree;
	}


	public FenetreRechercherCompteAdmin(Administrateur a,boolean stat){

		System.out.println("Fenêtre pour rechercher un compte");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Rechercher un compte");
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
		this.stat=stat;

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(300,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
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

		JPanel panel2 = new JPanel();
		panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		if(!stat){
			String[] types = new String[4];
			types[0] = "Sélection à faire";
			types[1] = "utilisateur";
			types[2] = "administrateur";
			types[3] = "technicien";
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

					modifieSiUtilisateur(typeEntre);
				}

			});
			panel2.add(qualiteARemplir);
		}
		else{
			JLabel labelUtil = new JLabel ("utilisateur");
			panel2.add(labelUtil);
			}

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(550,350));
		centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		centerWest.setLayout(new GridLayout(5,2));

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelQualite.setPreferredSize(new Dimension(150,30));
		labelQualite.setMaximumSize(new Dimension(150,30));
		panel1.add(labelQualite);
		centerWest.add(panel1);

		centerWest.add(panel2);	

		JPanel panel3 = new JPanel();
		panel3.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelId.setPreferredSize(new Dimension(150,30));
		labelId.setMaximumSize(new Dimension(150,30));
		panel3.add(labelId);
		centerWest.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		idARemplir.setPreferredSize(new Dimension(150,30));
		idARemplir.setMaximumSize(new Dimension(150,30));
		panel4.add(idARemplir);
		centerWest.add(panel4);	

		JPanel panel5 = new JPanel();
		panel5.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelAdresseEMail.setPreferredSize(new Dimension(150,30));
		labelAdresseEMail.setMaximumSize(new Dimension(150,30));
		panel5.add(labelAdresseEMail);
		centerWest.add(panel5);	

		JPanel panel6 = new JPanel();
		panel6.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		adresseEMailARemplir.setPreferredSize(new Dimension(150,30));
		adresseEMailARemplir.setMaximumSize(new Dimension(150,30));
		panel6.add(adresseEMailARemplir);
		centerWest.add(panel6);	

		JPanel panel7 = new JPanel();
		panel7.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNom.setPreferredSize(new Dimension(150,30));
		labelNom.setMaximumSize(new Dimension(150,30));
		panel7.add(labelNom);
		centerWest.add(panel7);	

		JPanel panel8 = new JPanel();
		panel8.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		nomARemplir.setPreferredSize(new Dimension(150,30));
		nomARemplir.setMaximumSize(new Dimension(150,30));
		panel8.add(nomARemplir);
		centerWest.add(panel8);			

		JPanel panel9 = new JPanel();
		panel9.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelPrenom.setPreferredSize(new Dimension(150,30));
		labelPrenom.setMaximumSize(new Dimension(150,30));
		panel9.add(labelPrenom);
		centerWest.add(panel9);	

		JPanel panel10 = new JPanel();
		panel10.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		prenomARemplir.setPreferredSize(new Dimension(150,30));
		prenomARemplir.setMaximumSize(new Dimension(150,30));
		panel10.add(prenomARemplir);
		centerWest.add(panel10);	

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
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void modifieSiUtilisateur(int type){
		if(type!=Compte.TYPE_UTILISATEUR){
			labelNom.setForeground(Color.GRAY);
			nomARemplir.setEnabled(true);
			labelPrenom.setForeground(Color.GRAY);
			prenomARemplir.setEnabled(true);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonValider){
			this.setTypeEntre(typeEntre);
			if(!idARemplir.getText().equals("")){this.setIdEntre(idARemplir.getText());}
			if(!nomARemplir.getText().equals("")){this.setNomEntre(nomARemplir.getText());}
			if(!prenomARemplir.getText().equals("")){this.setPrenomEntre(prenomARemplir.getText());}
			if(!adresseEMailARemplir.getText().equals("")){this.setAdresseEMailEntree(adresseEMailARemplir.getText());}
			new FenetreResultatsRechercheCompteAdmin(this.getAdministrateur(),this,typeEntre,stat);
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}


	}		

}
