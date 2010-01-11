package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.DemandeAssignation;
import metier.Lieu;
import metier.Technicien;

/**
 * la classe {@link FenetreGererDemandesAssignationTech} h�rite de {@link JFrame} et impl�mente l'interface {@link ActionListener}
 * <br>cette fen�tre appara�t lorsque le technicien a cliqu� sur le bouton "Voir les demandes d'assignation" dans le {@link MenuPrincipalTech}
 * <br>lui sont pr�sent�es sous forme de menu d�roulant toutes les {@link DemandeAssignation} �manant des constatations des {@link Administrateur}
 * @author KerGeek
 *
 */
public class FenetreGererDemandesAssignationTech extends JFrame implements ActionListener {


	/*
	 * liste des attributs priv�s de la fen�tre
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > G�rer les demandes d'assignation");
	private JLabel labelMsg = new JLabel("Demandes d'assignation");
	private DemandeAssignation demandeEntree;
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	// Accesseurs utiles

	/**
	 * @return	le {@link FenetreGererDemandesAssignationTech#technicien} de la {@link FenetreGererDemandesAssignationTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise le {@link FenetreGererDemandesAssignationTech#technicien} de la {@link FenetreGererDemandesAssignationTech}
	 * @param tech
	 * le technicien connect� sur cette fen�tre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	/**
	 * constructeur de {@link FenetreGererDemandesAssignationTech}
	 * @param t
	 * le technicien connect� sur la {@link FenetreGererDemandesAssignationTech}
	 * @throws ConnexionFermeeException
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JComboBox
	 */
	public FenetreGererDemandesAssignationTech (Technicien t) throws ConnexionFermeeException{
		System.out.println("Fen�tre pour voir toutes les demandes d'assignation");
		this.setContentPane(new PanneauTech());
		//D�finit un titre pour notre fen�tre
		this.setTitle("G�rer les demandes d'assignation");
		//D�finit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setTechnicien(t);

		labelTech = new JLabel("Vous �tes connect� en tant que "+ t.getCompte().getId());
		labelTech.setFont(UtilitaireIhm.POLICE4);
		labelTech.setPreferredSize(new Dimension(1100,50));
		labelTech.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(1200,100));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelMsg.setPreferredSize(new Dimension(1100,100));
		center.add(labelMsg);

		List<DemandeAssignation> listeDemandes;
		try {
			listeDemandes= DAODemandeAssignation.getDemandesAssignationEnAttente();
			for (int i=0;i<listeDemandes.size();i++){
				DemandeAssignation demandei = listeDemandes.get(i);
				//� cet endroit il faut regarder si la demande d'assignation n'a pas �t� prise en charge indirectement, par les mouvements de v�los des utilisateurs (emprunts et rendus)
				//si le nombre de v�los voulu par l'administrateur s'av�re �tre �gal au nombre de v�los effectivement pr�sents dans la station, on d�clare la demande d'assignation prise en charge (elle ne figurera donc pas dans le menu d�roulant)
				//l'administrateur n'avait entr� qu'un nombre de v�los voulu, aussi une demande correspondant � un ajout de v�los peut se transformer en demande de retrait de v�los suite aux mouvements de v�los par les utilisateurs
				if(demandei.getNombreVelosVoulusDansLieu()-DAOVelo.getVelosByLieu(demandei.getLieu()).size() == 0){
					demandei.setPriseEnCharge(true);
					DAODemandeAssignation.updateDemandeAssignation(demandei);
					listeDemandes.remove(i);
				}
				//dans le cas o� la demande d'assignation concernait le garage, on imagine que la demande d'assignation est prise en charge si le nombre de v�los pr�sent est sup�rieur au nombre de v�los voulu
				//en effet le "nombre de v�los voulu" est plus un nombre minimal, un seuil, pour pouvoir faire face aux diff�rentes demandes d'assignation, et si le nombre de v�los au garage est sup�rieur l'objectif de l'administrateur est atteint pareillement
				else if(demandei.getLieu().getId().equals(""+Lieu.ID_GARAGE) && demandei.getNombreVelosVoulusDansLieu()-DAOVelo.getVelosByLieu(demandei.getLieu()).size() < 0){
					demandei.setPriseEnCharge(true);
					DAODemandeAssignation.updateDemandeAssignation(demandei);
					listeDemandes.remove(i);
				}
			}
			final String [] tableauDemandes = new String[listeDemandes.size()+1];
			tableauDemandes[0]="S�lectionnez une demande";
			for (int i=0;i<listeDemandes.size();i++){
				DemandeAssignation demandei = listeDemandes.get(i);
				tableauDemandes[i+1] = DAODemandeAssignation.ligne(demandei);
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauDemandes);

			JComboBox combo = new JComboBox(model);
			combo.setFont(UtilitaireIhm.POLICE3);
			combo.setPreferredSize(new Dimension(250,50));
			combo.setMaximumSize(new Dimension(250,50));
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					try {
						String chaineSelectionnee = (String)(o);
						if(chaineSelectionnee.equals(tableauDemandes[0])){
							demandeEntree=null;
						}
						else{
							String idDemandeEntre="";
							int i=8;
							while(chaineSelectionnee.charAt(i)!=' '){
								idDemandeEntre=idDemandeEntre+chaineSelectionnee.charAt(i);
								i++;
							}
							System.out.println("id de la demande entr�e : "+idDemandeEntre);
							demandeEntree = DAODemandeAssignation.getDemandeAssignationById(idDemandeEntre);
						}
						repaint();
					} catch (SQLException e) {
						MsgBox.affMsg(e.getMessage());
					} catch (ClassNotFoundException e) {
						MsgBox.affMsg(e.getMessage());
					}
					catch (ConnexionFermeeException e){
						MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
						new FenetreAuthentification(false);
					}
				}
			});
			center.add(combo);
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}		
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setPreferredSize(new Dimension(250,50));
		boutonValider.setMaximumSize(new Dimension(250,50));
		boutonValider.addActionListener(this);
		center.add(boutonValider);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel.add(boutonRetour);
		south.add(panel,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);
		this.setVisible(true);
	}

	/**
	 * cette m�thode est ex�cut�e si le {@link Technicien} a cliqu� sur l'un des boutons qui lui �taient propos�s
	 * <br>s'il a cliqu� sur le {@link FenetreGererDemandesAssignationTech#boutonValider} une fen�tre s'ouvre d�taillant la demande d'assignation s�lectionn�e
	 * <br>s'il a cliqu� sur le {@link FenetreGererDemandesAssignationTech#boutonRetour} il retourne � son menu principal
	 * @see FenetreGererUneDemandeAssignationTech#FenetreGererUneDemandeAssignationTech(Technicien, DemandeAssignation)
	 * @see MenuPrincipalTech#MenuPrincipalTech(Technicien)
	 * @param arg0
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if (arg0.getSource()==boutonValider){
				if(demandeEntree==null){
					MsgBox.affMsg("Vous n'avez s�lectionn� aucune demande d'assignation");
					new FenetreGererDemandesAssignationTech(this.getTechnicien());
				}
				else{
					new FenetreGererUneDemandeAssignationTech(this.getTechnicien(),demandeEntree);
				}
			}
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
			}
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
			try {
				new FenetreGererDemandesAssignationTech(this.getTechnicien());
			} catch (ConnexionFermeeException e1) {
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		} catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
			try {
				new FenetreGererDemandesAssignationTech(this.getTechnicien());
			} catch (ConnexionFermeeException e1) {
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
	}
}