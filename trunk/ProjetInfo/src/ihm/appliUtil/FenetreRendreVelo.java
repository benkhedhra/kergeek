package ihm.appliUtil;

import envoieMail.SendMail;
import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Emprunt;
import metier.Station;
import metier.Technicien;
import metier.Utilisateur;
import metier.Velo;
import metier.exceptionsMetier.PasDeDateRetourException;
import metier.exceptionsMetier.PasDeVeloEmprunteException;


public class FenetreRendreVelo extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Utilisateur utilisateur = LancerAppliUtil.UTEST;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonDeconnexion = new JButton("D�connexion");
	private Station stationEntree;
	private Velo velo;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Velo getVelo() {
		return velo;
	}

	public void setVelo(Velo velo) {
		this.velo = velo;
	}

	public FenetreRendreVelo(Utilisateur u) throws ConnexionFermeeException {

		System.out.println("Fen�tre pour rendre un v�lo");
		this.setContentPane(new Panneau());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Rendre un v�lo");
		//D�finit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);

		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setUtilisateur(u);

		this.setVelo(this.getUtilisateur().getEmpruntEnCours().getVelo());

		labelUtil = new JLabel("Vous �tes connect� en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(UtilitaireIhm.POLICE4);
		labelUtil.setPreferredSize(new Dimension(500,30));
		boutonDeconnexion.setPreferredSize(new Dimension(150,30));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(UtilitaireIhm.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelUtil);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		List<Station> listeStations;
		try {
			listeStations = DAOLieu.getAllStations();
			String [] tableauStations = new String[listeStations.size()+1];
			tableauStations[0]="S�lectionnez une station";
			for (int i=0;i<listeStations.size();i++){
				tableauStations[i+1]=listeStations.get(i).toString();
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauStations);
			JPanel center = new JPanel();
			center.setBackground(UtilitaireIhm.TRANSPARENCE);
			JComboBox combo = new JComboBox(model);
			combo.setFont(UtilitaireIhm.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					try{
						Object o = ((JComboBox)ae.getSource()).getSelectedItem();
						String chaineSelectionnee = (String)(o);
						String idStationEntre = chaineSelectionnee.substring(0,1);
						try {
							stationEntree = (Station) DAOLieu.getLieuById(idStationEntre);
						} catch (SQLException e) {
							MsgBox.affMsg(e.getMessage());
						} catch (ClassNotFoundException e) {
							MsgBox.affMsg(e.getMessage());
						}
						labelMsg.setText("Station s�lectionn�e : " + stationEntree.getAdresse());
						labelMsg.setFont(UtilitaireIhm.POLICE3);
					}
					catch (Exception  e){
						System.out.println("Erreur dans la s�lection de la station");
					}
				}
			});
			center.add(combo);

			boutonValider.setFont(UtilitaireIhm.POLICE3);
			boutonValider.setBackground(Color.CYAN);
			boutonValider.setFont(UtilitaireIhm.POLICE3);
			boutonValider.addActionListener(this);
			labelMsg.setText("S�lectionnez la station o� vous vous trouvez");
			labelMsg.setFont(UtilitaireIhm.POLICE2);
			center.add(labelMsg);
			center.add(boutonValider);
			this.getContentPane().add(center, BorderLayout.CENTER);

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}

		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonDeconnexion){
			new FenetreConfirmationUtil("Merci et � bient�t ! ");
		}
		else if (arg0.getSource()==boutonValider){
			if(stationEntree!=null){
				try{
					System.out.println(stationEntree);
					if (UtilitaireIhm.verifieSiPlaceDisponibleDansStation(stationEntree)){
						Emprunt emprunt = this.getUtilisateur().rendreVelo(stationEntree);
						if (emprunt!=null){
							// l'utilisateur a bien rendu le v�lo
							DAOVelo.updateVelo(velo);
							Boolean b = DAOEmprunt.updateEmprunt(emprunt);
							DAOUtilisateur.updateUtilisateur(this.getUtilisateur());
							if(b){
								if (emprunt.getTempsEmprunt()<Emprunt.TPS_EMPRUNT_MIN){
									//emprunt trop court
									new FenetreEmpruntCourt(this.getUtilisateur(),this.getVelo());
								}

								else if (this.getUtilisateur().isBloque()){
									//emprunt trop long
									new FenetreEmpruntLong(this.getUtilisateur());
									DAOUtilisateur.updateUtilisateur(this.getUtilisateur());
								}

								else {
									//emprunt ni trop court ni trop long
									new FenetreConfirmationUtil("Remettez le v�lo dans un emplacement. Merci et � bient�t ! ");
									System.out.println("Le v�lo a bien �t� rendu");
								}
							}
							else{
								System.out.println("Le v�lo n'a pas �t� rendu");
								this.labelMsg.setText("Le v�lo n'a pas �t� rendu");
								new FenetreRendreVelo(this.getUtilisateur());
							}
						}
						//on v�rifie si la station est maintenant pleine
						if(!UtilitaireIhm.verifieSiPlaceDisponibleDansStation(stationEntree)){
							List<Technicien> listeTech = DAOTechnicien.getAllTechniciens();
							List<Administrateur> listeAdmin = DAOAdministrateur.getAllAdministrateurs();
							
							String adresseEMail;
							for (Technicien t : listeTech){
								adresseEMail = t.getCompte().getAdresseEmail();
								SendMail.sendMail(adresseEMail,"Station "+stationEntree.getAdresse()+" pleine","Bonjour "+t.getCompte().getId()+"\n La station "+stationEntree.getAdresse()+" est vide. Veuillez consulter les demandes d'assignation � ce sujet. ");
							}
							for (Administrateur a : listeAdmin){
								adresseEMail = a.getCompte().getAdresseEmail();
								SendMail.sendMail(adresseEMail,"Station "+stationEntree.getAdresse()+" pleine","Bonjour "+a.getCompte().getId()+"\n La station "+stationEntree.getAdresse()+" est vide. Veuillez envoyer une demande d'assignation ad�quate. ");
							}
						}
					}
					else{
						List<Station> listeStations = DAOLieu.getAllStations();
						int i = listeStations.indexOf(stationEntree);
						if(i==0){
							new FenetreConfirmationUtil("<html><center>Il n'y a plus de places disponibles dans cette station. <br> La station la plus proche est : <br>"+listeStations.get(i+1).getAdresse()+"<br>Au revoir et � bient�t !<center></html>");
						}
						else if(i==listeStations.size()-1){
							new FenetreConfirmationUtil("<html><center>Il n'y a plus de places disponibles dans cette station. <br> La station la plus proche est : <br>"+listeStations.get(i-1).getAdresse()+"<br>Au revoir et � bient�t !<center></html>");
							}
						else{
							new FenetreConfirmationUtil("<html><center>Il n'y a plus de places disponibles dans cette station. <br> Les stations les plus proches sont : <br>"+listeStations.get(i-1).getAdresse()+"<br>"+listeStations.get(i+1).getAdresse()+"<br>Au revoir et � bient�t !<center></html>");
						}
					}
				} catch (SQLException e) {
					MsgBox.affMsg("SQL exception " + e.getMessage());
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg("ClassNotFound " + e.getMessage());
				} catch (PasDeVeloEmprunteException e) {
					MsgBox.affMsg("PasDeVeloEmprunte " + e.getMessage());
				} catch (PasDeDateRetourException e) {
					MsgBox.affMsg("PasDeDateRetourException " + e.getMessage());
				}
				catch (ConnexionFermeeException e3){
					MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
					new FenetreAuthentificationUtil(false);
				} catch (UnsupportedEncodingException e) {
					MsgBox.affMsg("UnsupportedEncodingException : "+e.getMessage());
					new FenetreAuthentificationUtil(false);
				} catch (MessagingException e) {
					MsgBox.affMsg("MessagingException : "+e.getMessage());
					new FenetreAuthentificationUtil(false);
				}
			}
			else{
				MsgBox.affMsg("Aucune station s�lectionn�e");
				try {
					new FenetreRendreVelo(this.getUtilisateur());
				}
				catch (ConnexionFermeeException e3){
					MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
					new FenetreAuthentificationUtil(false);
				}
			}
		}

	}
}