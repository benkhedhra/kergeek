package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;

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
import metier.DemandeAssignation;
import metier.Garage;

/**
 * FenetreExisteDejaDemandeAssignationAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient lorsqu'un Administrateur a validé la création d'une {@link DemandeAssignation} alors qu'il en existe déjà une en cours pour le même lieu (@link Station} ou {@link Garage}
 * <br>elle lui propose soit de remplacer la demande existante par la sienne, soit d'annuler sa demande
 * @author KerGeek
 */
public class FenetreExisteDejaDemandeAssignationAdmin extends JFrame implements ActionListener {

		/**
		 * attribut de sérialisation par défaut
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * l'administrateur connecté sur la fenêtre
		 */
		private Administrateur administrateur;

		/** 
		 * ancienneDemande est la {@link DemandeAssignation} non traitée déjà existante pour le lieu
		 */
		private DemandeAssignation ancienneDemande;
		/**
		 * nouvelleDemande est la {@link DemandeAssignation} qui vient d'être créée par l'{@lik Administrateur} à l'étape précédente
		 */
		private DemandeAssignation nouvelleDemande;
		
		/**
		 * 3 JLabel permettant d'afficher l'id de l'{@link Administrateur} connectén l'endroit où il se trouve dans l'application et le message d'invitation à faire son choix
		 */
		private JLabel labelAdmin = new JLabel("");
		private JLabel labelChemin = new JLabel("");
		private JLabel labelMsg = new JLabel("");
		
		/**
		 * 2 JButton permettant de remplacer l'ancienne demande par la nouvelle ou d'annuler la nouvelle demande
		 */
		private JButton boutonOui = new JButton("OUI");
		private JButton boutonNon = new JButton("<html><center>NON <br>(retour au menu principal)</center></html>");

		//Accesseurs utiles
		
		public Administrateur getAdministrateur() {
			return administrateur;
		}

		public void setAdministrateur(Administrateur administrateur) {
			this.administrateur = administrateur;
		}

		public DemandeAssignation getAncienneDemande() {
			return ancienneDemande;
		}

		public void setAncienneDemande(DemandeAssignation ancienneDemande) {
			this.ancienneDemande = ancienneDemande;
		}

		public DemandeAssignation getNouvelleDemande() {
			return nouvelleDemande;
		}

		public void setNouvelleDemande(DemandeAssignation nouvelleDemande) {
			this.nouvelleDemande = nouvelleDemande;
		}

		/**
		 * constructeur de {@link FenetreExisteDejaDemandeAssignationAdmin}
		 * @param a
		 * l'administrateur connecté sur cette fenêtre
		 * @param ancienneDemande
		 * la {@link DemandeAssignation} déjà existante pour le lieu de la nouvelle demande
		 * @param nouvelleDemande
		 * la {@link DemandeAssignation} que vient de formuler l'administrateur lors de l'étape précédente
		 */
		
		public FenetreExisteDejaDemandeAssignationAdmin(Administrateur a, DemandeAssignation ancienneDemande, DemandeAssignation nouvelleDemande){

			this.setContentPane(new PanneauAdmin());

			this.setAdministrateur(a);
			this.setAncienneDemande(ancienneDemande);
			this.setNouvelleDemande(nouvelleDemande);

			this.setTitle("Demande d'assignation déjà existante pour cette station");
		    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    Rectangle bounds = env.getMaximumWindowBounds();
		    this.setBounds(bounds);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(true);
			this.setAlwaysOnTop(true);

			this.getContentPane().setLayout(new BorderLayout());

			labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
			labelChemin.setText("Menu principal > Voir l'état du parc > Etat d'un lieu > "+this.getAncienneDemande().getLieu().getAdresse()+" > Envoyer une demande d'assignation > Demande déjà existante");
			labelAdmin.setFont(UtilitaireIhm.POLICE4);
			labelAdmin.setPreferredSize(new Dimension(1100,50));
			labelAdmin.setMaximumSize(new Dimension(1100,50));
			labelChemin.setFont(UtilitaireIhm.POLICE4);
			labelChemin.setPreferredSize(new Dimension(1100,50));
			labelChemin.setMaximumSize(new Dimension(1100,50));
			JPanel north = new JPanel();
			north.setPreferredSize(new Dimension(1200,150));
			north.setBackground(UtilitaireIhm.TRANSPARENCE);
			north.add(labelAdmin);
			north.add(labelChemin);
			this.getContentPane().add(north,BorderLayout.NORTH);

			labelMsg.setText("<html><center>Une demande d'assignation a déjà été envoyée et non traitée pour la station "+ancienneDemande.getLieu().getAdresse()+".  Le nombre de vélos désiré pour cette station est de : "+ancienneDemande.getNombreVelosVoulusDansLieu()+"<br>Souhaitez-vous remplacer cette demande par la vôtre ("+nouvelleDemande.getNombreVelosVoulusDansLieu()+" vélos) ? <html>");
			labelMsg.setPreferredSize(new Dimension(1100,100));
			boutonOui.setPreferredSize(new Dimension(200,50));
			boutonOui.setBackground(Color.CYAN);
			boutonNon.setPreferredSize(new Dimension(200,50));
			boutonNon.setBackground(Color.CYAN);

			boutonOui.addActionListener(this);
			boutonNon.addActionListener(this);
			JPanel center = new JPanel();
			center.setBackground(UtilitaireIhm.TRANSPARENCE);
			center.add(labelMsg);
			center.add(boutonOui);
			center.add(boutonNon);
			this.add(center, BorderLayout.CENTER);

			this.setVisible(true);
		}

		/**
		 * méthode exécutée lorsque l'administrateur connecté a entré son choix (remplacer la demande existante ou conserver l'ancienne)
		 * @param arg0
		 * @see DemandeAssignation#setNombreVelosVoulusDansLieu(int)
		 * @see FenetreConfirmation#FenetreConfirmation(metier.Compte, JFrame)
		 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
		 */
		public void actionPerformed(ActionEvent arg0) {
			this.dispose();
			try {
				if(arg0.getSource()==boutonOui){
					this.getAncienneDemande().setDate(this.getNouvelleDemande().getDate());
					System.out.println("ancienne demande : "+DAODemandeAssignation.ligne(this.getAncienneDemande()));
					System.out.println("nouvelle demande : "+DAODemandeAssignation.ligne(nouvelleDemande));
					//on modifie uniquement l'attribut nbVelosVoulusDansLieu dans l'ancienne demande (on garde le meme identifiant, la même date ... )
					//le fait de conserver la date de l'ancienne demande n'a pas d'importance puisqu'aucune statistique n'est effectuée sur les demandes d'assignation
					this.getAncienneDemande().setNombreVelosVoulusDansLieu(nouvelleDemande.getNombreVelosVoulusDansLieu());
					if(DAODemandeAssignation.updateDemandeAssignation(this.getAncienneDemande())){
						new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
					}
				}
				else if (arg0.getSource()==boutonNon){
					new MenuVoirEtatAdmin(this.getAdministrateur());
				} 
			}
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg("ClassNotFoundException : "+e.getMessage());
			} catch (SQLException e) {
				MsgBox.affMsg("SQLException : "+e.getMessage());
			}
		}
	}