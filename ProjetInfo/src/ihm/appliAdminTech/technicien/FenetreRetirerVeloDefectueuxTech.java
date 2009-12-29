package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeIntervention;
import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.TextFieldLimite;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.DemandeIntervention;
import metier.Intervention;
import metier.Technicien;
import metier.Velo;

/**
 * la classe {@link FenetreRetirerVeloDefectueuxTech} hérite de {@link JFrame} et implémente l'interface {@link ActionListener}
 * <br>cette fenêtre apparaît lorsque le technicien a cliqué sur "Retirer un vélo défectueux" dans la {@link MenuPrincipalTech}
 * <br>elle demande au {@link Technicien} soit de sélectionner une demande d'intervention pour la prendre en charge, soit de retirer un vélo de sa propre initiative s'il a constaté un défaut de lui-même sur un vélo
 * <br>cette action doit se réaliser juste avant le retrait physique du vélo
 * <br>cette fenêtre est propre au volet Technicien de l'application AdminTech
 * @author KerGeek
 *
 */
public class FenetreRetirerVeloDefectueuxTech extends JFrame implements ActionListener {

	/*
	 * liste des attributs privés de la fenêtre
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg1 = new JLabel("Sélectionnez une des demandes d'intervention formulées par les utilisateurs");
	private DemandeIntervention demandeEntree=null;
	private JButton boutonValider = new JButton("Retirer le vélo indiqué");
	private JLabel labelMsg2 = new JLabel("<html><center>Si vous voulez constater un défaut sur un vélo qui ne figure pas dans les demandes ci-dessus, <br>veuillez entrer son identifiant : </center></html> ");
	private TextFieldLimite idVeloARemplir = new TextFieldLimite(4,"");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	
	/**
	 * @return	le {@link FenetreRetirerVeloDefectueuxTech#technicien} de la {@link FenetreRetirerVeloDefectueuxTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise {@link FenetreRetirerVeloDefectueuxTech#technicien}
	 * @param tech
	 * le technicien connecté sur cette fenêtre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	/**
	 * @return {@link FenetreRetirerVeloDefectueuxTech#demandeEntree}
	 */
	public DemandeIntervention getDemandeEntree() {
		return demandeEntree;
	}
	
	/**
	 * Initialise la {@link FenetreRetirerVeloDefectueuxTech#demandeEntree} de la {@link FenetreRetirerVeloDefectueuxTech}
	 * @param demandeEntree
	 * la demande d'assignation sélectionnée à la fenêtre précédente
	 * @see DemandeIntervention
	 */
	public void setDemandeEntree(DemandeIntervention demandeEntree) {
		this.demandeEntree = demandeEntree;
	}

	/**
	 * constructeur de la {@link FenetreRetirerVeloDefectueuxTech}
	 * @param t : le technicien connecté sur la fenêtre
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 * @see JComboBox
	 */
	public FenetreRetirerVeloDefectueuxTech (Technicien t) throws ConnexionFermeeException{
		System.out.println("Fenêtre pour retirer un vélo défectueux d'une station");
		this.setContentPane(new PanneauTech());
		//Définit un titre pour notre fenêtre
		this.setTitle("Gérer les demandes d'intervention ou constater un vélo défectueux");
		//Définit une taille pour celle-ci
		this.setPreferredSize(new Dimension(700,500));		
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

		this.setTechnicien(t);

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(UtilitaireIhm.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		labelTech.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,400));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);

		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setPreferredSize(new Dimension(700,250));
		centerNorth.setBackground(UtilitaireIhm.TRANSPARENCE);

		labelMsg1.setPreferredSize(new Dimension(650,50));		
		labelMsg1.setMinimumSize(new Dimension(650,50));
		centerNorth.add(labelMsg1);

		List<DemandeIntervention> listeDemandes;
		try {
			listeDemandes= DAODemandeIntervention.getDemandesInterventionEnAttente();
			final String [] tableauDemandes = new String[listeDemandes.size()+1];
			tableauDemandes[0]=listeDemandes.size()+" demandes formulées";
			for (int i=0;i<listeDemandes.size();i++){
				DemandeIntervention demandei = listeDemandes.get(i);
					tableauDemandes[i+1] = DAODemandeIntervention.ligne(demandei);
			}

			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauDemandes);

			JComboBox combo = new JComboBox(model);
			combo.setPreferredSize(new Dimension(400,50));		
			combo.setMinimumSize(new Dimension(400,50));
			combo.setFont(UtilitaireIhm.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					try {
						String chaineSelectionnee = (String)(o);
						if(chaineSelectionnee==null || chaineSelectionnee.equals(tableauDemandes[0])){
							demandeEntree=null;
						}
						else{
						String idDemandeEntre="";
						int i=8;
						while(chaineSelectionnee.charAt(i)!=' '){
							idDemandeEntre=idDemandeEntre+chaineSelectionnee.charAt(i);
							i++;
						}
						System.out.println("id de la demande entré : "+idDemandeEntre);
						demandeEntree = DAODemandeIntervention.getDemandeInterventionById(idDemandeEntre);
						}repaint();
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
			});
			centerNorth.add(combo);
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}		


		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerSouth = new JPanel();
		centerSouth.setPreferredSize(new Dimension(700,150));
		centerSouth.setBackground(UtilitaireIhm.TRANSPARENCE);

		labelMsg2.setPreferredSize(new Dimension(650,50));		
		labelMsg2.setMinimumSize(new Dimension(650,50));
		centerSouth.add(labelMsg2);
		idVeloARemplir.setPreferredSize(new Dimension(200,50));		
		idVeloARemplir.setMinimumSize(new Dimension(200,50));
		centerSouth.add(idVeloARemplir);
		
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setPreferredSize(new Dimension(250,50));		
		boutonValider.setMinimumSize(new Dimension(250,50));
		boutonValider.addActionListener(this);
		centerSouth.add(boutonValider);

		center.add(centerSouth,BorderLayout.SOUTH);

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
	 * cette méthode est exécutée si le {@link Technicien} a cliqué sur l'un des deux boutons qui lui étaient proposés
	 * <br>s'il a cliqué sur {@link FenetreRetirerVeloDefectueuxTech#boutonValider}, le technicien commence une intervention sur le vélo sélectionné ou entré
	 * <br>s'il a cliqué sur le {@link FenetreRetirerVeloDefectueuxTech#boutonRetour} il retourne à son menu principal
	 * @param arg0 
	 * @see Technicien#intervenir(Velo)
	 * @see FenetreConfirmation#FenetreConfirmation(metier.Compte, JFrame)
	 * @see MenuPrincipalTech#MenuPrincipalTech(Technicien)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonValider){
			try {
				List<DemandeIntervention> listeDemandes= DAODemandeIntervention.getDemandesInterventionEnAttente();
				List<String> listeIdVelos = new LinkedList<String>();
				for(DemandeIntervention ddeI : listeDemandes){
					listeIdVelos.add(ddeI.getVelo().getId());
				}
				if(this.getDemandeEntree()!=null && !idVeloARemplir.getText().equals("")){
					//le technicien a à la fois sélectionné une demande et entré un id de vélo. Il doit faire l'un OU l'autre
					MsgBox.affMsg("Vous avez sélectionné une demande ET entré un vélo : veuillez faire l'un ou l'autre");
					new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());
				}
				else if(this.getDemandeEntree()!=null){
					//le technicien a sélectionné une demande d'intervention
					System.out.println("Une demande d'intervention a été sélectionnée");
					Intervention i = this.getTechnicien().intervenir(demandeEntree.getVelo());
					this.getDemandeEntree().setIntervention(i);
					if(DAOVelo.updateVelo(i.getVelo())&& DAOIntervention.createIntervention(i) && DAODemandeIntervention.updateDemandeIntervention(this.getDemandeEntree())){
						new FenetreConfirmation(this.getTechnicien().getCompte(),this);
					}
				}
				else if(!idVeloARemplir.getText().equals("")){
					//le technicien a entré un id de vélo
					System.out.println("Un vélo a été entré");
					if(listeIdVelos.contains(idVeloARemplir.getText())){
						//l'id de vélo entré correspond déjà à une demande d'intervention : il faut qu'il sélectionne la demande
						System.out.println("Problème : vélo entré fait déjà l'objet d'une demande d'intervention");
						MsgBox.affMsg("Vous avez sélectionné un vélo qui fait déjà l'objet d'une demande d'intervention : veuillez selectionner la demande d'intervention en question");
						new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());

					}
					else{
						//l'id de vélo entré ne correspond à aucune demande d'intervention
						if(DAOVelo.existe(idVeloARemplir.getText()) &&  DAOVelo.estDisponible(idVeloARemplir.getText())){
							//l'id de vélo entré est valide
							Velo veloEntre = DAOVelo.getVeloById(idVeloARemplir.getText());
							veloEntre.setEnPanne(true);
							Intervention i = this.getTechnicien().intervenir(veloEntre);
							if(DAOVelo.updateVelo(i.getVelo())&& DAOIntervention.createIntervention(i)){
								new FenetreConfirmation(this.getTechnicien().getCompte(),this);
							}
						}
						else if (!DAOVelo.existe(idVeloARemplir.getText())){
							//l'id de vélo entré ne correspond à aucun vélo existant dans la bdd
							System.out.println("Le vélo entré n'existe pas");
							MsgBox.affMsg("Le vélo que vous avez entré n'existe pas. ");
							new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());
						}
						else {
							//l'id de vélo entré correspond à un vélo qui n'est pas disponible en station
							System.out.println("Le vélo entré n'est pas en station actuellement");
							MsgBox.affMsg("Le vélo que vous avez entré n'est pas en station actuellement. ");
							new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());
						}
					}
				}
				else {
					System.out.println("Rien n'a été entré");
					MsgBox.affMsg("Vous n'avez ni sélectionné de demande d'intervention, ni entré de vélo. ");
					new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());
				}
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
				e.printStackTrace();
			}
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}