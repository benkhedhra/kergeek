package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Compte;
import metier.Technicien;
import metier.Velo;

/**
 * {@link FenetreEnregistrerArrivageVelosTech} hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>cette fenêtre permet au technicien connecté de confirmer l'enregistrement d'un nouveau arrivage de vélos
 * <br>cette action est précédée de la création physique d'un nouveau vélo (arrivage...)
 * <br>le technicien ne peut enregistrer de vélos que dix par dix au maximum
 * <br>s'il a un arrivage de 14 vélos, il devra le déclarer en deux fois
 * <br>cette fenêtreest propre au volet technicien de l'application AdminTech
 * @author KerGeek
 * @see {@link FenetreEnregistrerArrivageVelosTech#FenetreEnregistrerArrivageVelosTech(Technicien)}
 *
 */
public class FenetreEnregistrerArrivageVelosTech extends JFrame implements ActionListener{

	/*
	 * déclaration des attributs 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private int nbVelosEntre;
	private List<Velo> listeVelosCrees = new ArrayList<Velo>();

	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("Combien de vélos souhaitez-vous enregistrer ? ");
	private JButton boutonValider = new JButton ("Valider");
	private JButton boutonRetour = new JButton ("Retour au Menu Principal");

	// Accesseurs utiles

	/**
	 * @return	le {@link FenetreEnregistrerArrivageVelosTech#technicien} de la {@link FenetreEnregistrerArrivageVelosTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise le {@link FenetreEnregistrerArrivageVelosTech#technicien} de la {@link FenetreEnregistrerArrivageVelosTech}
	 * @param tech
	 * le technicien connecté sur cette fenêtre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	/**
	 * @return	le {@link FenetreEnregistrerArrivageVelosTech#nbVelosEntre} de la {@link FenetreEnregistrerArrivageVelosTech}
	 */
	public int getNbVelosEntre() {
		return nbVelosEntre;
	}

	/**
	 * Initialise le {@link FenetreEnregistrerArrivageVelosTech#nbVelosEntre} de la {@link FenetreEnregistrerArrivageVelosTech}
	 * @param nbVelosEntre
	 * le nombre de nouveaux vélos à enregistrer
	 */
	public void setNbVelosEntre(int nbVelosEntre) {
		this.nbVelosEntre = nbVelosEntre;
	}

	/**
	 * @return	la {@link FenetreEnregistrerArrivageVelosTech#listeVelosCrees} de la {@link FenetreEnregistrerArrivageVelosTech}
	 */
	public List<Velo> getListeVelosCrees() {
		return listeVelosCrees;
	}

	/**
	 * Initialise le {@link FenetreEnregistrerArrivageVelosTech#listeVelosCrees} de la {@link FenetreEnregistrerArrivageVelosTech}
	 * @param listeVelosCrees
	 * la liste des nouveaux vélos enregistrés
	 */
	public void setListeVelosCrees(List<Velo> listeVelosCrees) {
		this.listeVelosCrees = listeVelosCrees;
	}

	/**
	 * constructeur de {@link FenetreEnregistrerArrivageVelosTech}
	 * @param t
	 * le technicien connecté sur la fenêtre
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JComboBox
	 */
	public FenetreEnregistrerArrivageVelosTech(Technicien t) throws SQLException, ClassNotFoundException{

		System.out.println("Ouverture d'une fenêtre d'enregistrement de vélos du technicien");

		this.setTechnicien(t);
		this.setContentPane(new PanneauTech());

		//Définit un titre pour votre fenêtre
		this.setTitle("Enregistrer un nouvel arrivage de vélos");
		//Définit une taille pour celle-ci
		this.setSize(700, 500);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());


		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(UtilitaireIhm.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		labelMsg.setPreferredSize(new Dimension(600,30));
		labelMsg.setMaximumSize(new Dimension(600,30));
		labelMsg.setFont(UtilitaireIhm.POLICE2);
		center.add(labelMsg);

		Object [] tableauEntiers = new Object[11];
		tableauEntiers[0]="Sélectionnez un nombre de vélos";
		for (int i=0;i<10;i++){
			tableauEntiers[i+1] = i+1;
		}
		DefaultComboBoxModel model = new DefaultComboBoxModel(tableauEntiers);

		JComboBox combo = new JComboBox(model);
		combo.setFont(UtilitaireIhm.POLICE3);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Object o = ((JComboBox)ae.getSource()).getSelectedItem();
				if (o instanceof Integer){
					nbVelosEntre = (Integer)(o);
				}
				else{
					nbVelosEntre = 0;
				}
				System.out.println("nombre de vélos entré : "+nbVelosEntre);
				repaint();
			}
		});
		center.add(combo);

		boutonValider.setPreferredSize(new Dimension(150,30));
		boutonValider.setMaximumSize(new Dimension(150,30));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		center.add(boutonValider);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/**
	 * cette méthode est exécutée si le technicien a cliqué sur l'un des deux boutons qui lui étaient proposés
	 * <br>s'il a cliqué sur "Valider", une nouvelle fenêtre lui indiquera les identifiants à apposer sur les nouveaux vélos
	 * <br>s'il a cliqué sur "Retour au menu principal", il retourne au menu principal
	 * <br>cette méthode est propre au volet "Technicien" de l'application AdminTech
	 * @param arg0
	 * @see Technicien#enregistrerVelo()
	 * @see FenetreConfirmation#FenetreConfirmation(Compte, JFrame)
	 * @see MenuPrincipalTech#MenuPrincipalTech(Technicien)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if(arg0.getSource()==boutonValider){
				for(int k = 0;k<this.getNbVelosEntre();k++){
					Velo veloEnregistre = this.getTechnicien().enregistrerVelo();
					this.getListeVelosCrees().add(veloEnregistre);
					DAOVelo.createVelo(veloEnregistre);
				}
				new FenetreConfirmation(this.getTechnicien().getCompte(),this);
			}
			else if(arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
			}
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
	
	public static void main (String[]args) throws SQLException, ClassNotFoundException{
		new FenetreEnregistrerArrivageVelosTech(new Technicien(new Compte(12,"lalilalou")));
	}
}
