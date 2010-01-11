package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.DAOUtilisateur;
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
import metier.Compte;

/**
 * FenetreResultatsRechercheCompteAdmin hérite de {@link JFrame} et implémente {@link ActionListener}
 * <br>c'est une classe de l'application réservée à un {@link Administrateur}
 * <br>elle intervient suite à une {@link FenetreRechercherCompteAdmin}, dans deux contextes différents
 * <br>un Administrateur veut trouver un compte pour le modifier ou juste voir des informations
 * <br>un Administrateur veut afficher des statistiques sur un Utilisateur
 * @author KerGeek
 */
public class FenetreResultatsRechercheCompteAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connecté sur la fenêtre
	 */
	private Administrateur administrateur;
	/**
	 * booléen indiquant si l'on se trouve dans un contexte de recherche de statistiques sur les emprunts d'un Utilisateur ou non
	 */
	private boolean stat;
	
	/**
	 * attribut correspondant à la fenêtre précédente permettant de récupérer les paramètres de la recherche
	 */
	private FenetreRechercherCompteAdmin fenetrePrecedente;
	
	/**
	 * 3 JLabel permettant d'afficher l'id de l'administrateur connecté, l'endroit où il se trouve dans l'application, et le message introduisant le contenu de la fenêtre
	 */
	private JLabel labelAdmin = new JLabel("");;
	private JLabel labelMsg = new JLabel("");
	private JLabel labelChemin = new JLabel("");
	
	/**
	 * le {@link Compte} sélectionnée parmi les résultats de la recherche
	 */
	private Compte compteEntre;
	
	/**
	 * 3 JButton proposant à l'Administrateur de valider sa sélection, d'effectuer une nouvelle recherche, ou de retourner à son menu principal
	 */
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonNouvelleRecherche = new JButton("Nouvelle recherche");	
	private JButton boutonRetour = new JButton("Retour au menu principal");

	
	//Accesseurs utiles
	
	/*
	 * attribut administrateur
	 */
	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	/*
	 * attribut compteEntre
	 */
	public Compte getCompteEntre() {
		return compteEntre;
	}

	public void setCompteEntre(Compte compteEntre) {
		this.compteEntre = compteEntre;
	}

	/*
	 * attribut stat
	 */
	public boolean isStat() {
		return stat;
	}

	public void setStat(boolean stat) {
		this.stat = stat;
	}

	/**
	 * constructeur de {@link FenetreResultatsRechercheCompteAdmin}
	 * @param a
	 * l'administrateur connecté à la fenêtre
	 * @param fenetrePrec
	 * la fenêtre précédente dans laquelle ont été entrés les paramètres de la recherche
	 * @param stat
	 * le booléen valant true si l'ont se trouve dans un contexte de recherche d'un utilisateur pour obtenir des statistiques sur ses emprunts
	 * @throws ConnexionFermeeException
	 * @see {@link DAOCompte#getComptesByRecherche(int, String, String, String, String)}
	 */
	public FenetreResultatsRechercheCompteAdmin (Administrateur a,FenetreRechercherCompteAdmin fenetrePrec,boolean stat) throws ConnexionFermeeException{
		System.out.println("Fenêtre pour voir les résultats d'une recherche");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Résultats de la recherche");
		//Définit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(true);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		//on attribue aux attributs privés de la fenêtre les paramètres indiqués du constructeur
		this.setAdministrateur(a);
		fenetrePrecedente=fenetrePrec;
		this.setStat(stat);
		
		if(this.isStat()){
			labelChemin.setText("Menu principal > Demander des statistiques > Statistiques sur utilisateurs > Résultats de la recherche");
		}
		else{
			labelChemin.setText("Menu principal > Gérer les comptes > Afficher informations sur un compte > Résultats de la recherche");
		}

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
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

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(1200,800));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.add(labelMsg);

		List<Compte> listeComptes;
		try {
			//listeComptes est la liste de tous les comptes correspondant à la recherche lancée dans la fenêtre précédente
			listeComptes = DAOCompte.getComptesByRecherche(fenetrePrec.getTypeEntre(),fenetrePrec.getIdEntre(),fenetrePrec.getNomEntre(),fenetrePrec.getPrenomEntre(),fenetrePrec.getAdresseEMailEntree());

			System.out.println("Il y a "+listeComptes.size()+ " individu(s) trouvé(s)");
			if(listeComptes.size()>0){
				labelMsg.setText("Résultats de la recherche : "+listeComptes.size()+" individu(s) trouvé(s)");
				final String [] tableauComptes = new String[listeComptes.size()+1];
				tableauComptes[0]="Sélectionnez un compte";
				for (int i=0;i<listeComptes.size();i++){
					Compte comptei = listeComptes.get(i);
					if(comptei.getType()==Compte.TYPE_UTILISATEUR){
						tableauComptes[i+1] = DAOUtilisateur.getUtilisateurById(comptei.getId()).toString();
					}
					else if(comptei.getType()==Compte.TYPE_ADMINISTRATEUR){
						tableauComptes[i+1] = DAOAdministrateur.getAdministrateurById(comptei.getId()).toString();
					}
					else if(comptei.getType()==Compte.TYPE_TECHNICIEN){
						tableauComptes[i+1] = DAOTechnicien.getTechnicienById(comptei.getId()).toString();
					}
				}

				DefaultComboBoxModel model = new DefaultComboBoxModel(tableauComptes);

				JComboBox tableau = new JComboBox(model);
				tableau.setFont(UtilitaireIhm.POLICE3);
				tableau.setPreferredSize(new Dimension(600,50));
				tableau.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						try {
							Object o = ((JComboBox)ae.getSource()).getSelectedItem();
							String chaineSelectionnee = (String)(o);
							if(chaineSelectionnee.equals(tableauComptes[0])){
								compteEntre=null;
							}
							else{
								//il faut récupérer l'identifiant du compte entré mais on ne connaît pas la taille de la String en question
								int k=0;
								while(chaineSelectionnee.charAt(k)!=' '){
									k++;
								}
								String idCompteEntre = chaineSelectionnee.substring(0,k);
								System.out.println("idCompteEntre="+idCompteEntre);
								compteEntre = DAOCompte.getCompteById(idCompteEntre);
								System.out.println("idCompteEntre = "+idCompteEntre + " - "+compteEntre.isActif());
							}
							repaint();
						} 
						catch (SQLException e) {
							MsgBox.affMsg(e.getMessage());
						} 
						catch (ClassNotFoundException e) {
							MsgBox.affMsg(e.getMessage());
						}
						catch (ConnexionFermeeException e){
							MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
							new FenetreAuthentification(false);
						}
					}
				});
				this.setCompteEntre(compteEntre);
				center.add(labelMsg);
				center.add(tableau);
				boutonValider.setFont(UtilitaireIhm.POLICE3);
				boutonValider.setBackground(Color.CYAN);
				boutonValider.setFont(UtilitaireIhm.POLICE3);
				boutonValider.addActionListener(this);
				center.add(boutonValider);
			}
			else{
				labelMsg.setText("Il n'y a aucun résultat pour cette recherche. ");
				center.add(labelMsg);
			}

		}catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}	


		boutonNouvelleRecherche.setFont(UtilitaireIhm.POLICE3);
		boutonNouvelleRecherche.setBackground(Color.CYAN);
		boutonNouvelleRecherche.setFont(UtilitaireIhm.POLICE3);
		boutonNouvelleRecherche.addActionListener(this);
		center.add(boutonNouvelleRecherche);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/**
	 * méthode exécutée quand l'administrateur a cliqué sur l'un des 3 boutons écoutés par la fenêtre
	 * @param arg0
	 * l'action source
	 * @see FenetreInfoCompteAdmin#FenetreInfoCompteAdmin(Administrateur, Compte, boolean)
	 * @see FenetreRechercherCompteAdmin#FenetreRechercherCompteAdmin(Administrateur, boolean)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		//s'il a cliqué sur "Valider"
		if (arg0.getSource()==boutonValider){
			try {
				if(compteEntre==null){
					MsgBox.affMsg("Vous n'avez sélectionné aucun compte");
					new FenetreResultatsRechercheCompteAdmin(this.getAdministrateur(), fenetrePrecedente, this.isStat());
				}
				else{
					new FenetreInfoCompteAdmin(this.getAdministrateur(),compteEntre,stat);
				}
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		//s'il a cliqué sur "lancer une nouvelle recherche"
		else if (arg0.getSource()==boutonNouvelleRecherche){
			new FenetreRechercherCompteAdmin(this.getAdministrateur(),stat);
		}
		//s'il a cliqué sur "retourner au menu principal"
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}
}