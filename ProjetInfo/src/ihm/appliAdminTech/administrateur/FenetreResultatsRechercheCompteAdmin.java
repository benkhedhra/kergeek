package ihm.appliAdminTech.administrateur;

import exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.DAOUtilisateur;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class FenetreResultatsRechercheCompteAdmin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private Compte compteEntre;
	private boolean stat;
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonNouvelleRecherche = new JButton("Nouvelle recherche");	
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public Compte getCompteEntre() {
		return compteEntre;
	}

	public void setCompteEntre(Compte compteEntre) {
		this.compteEntre = compteEntre;
	}

	public boolean isStat() {
		return stat;
	}

	public void setStat(boolean stat) {
		this.stat = stat;
	}

	public FenetreResultatsRechercheCompteAdmin (Administrateur a,FenetreRechercherCompteAdmin fenetrePrec,boolean stat) throws ConnexionFermeeException{
		System.out.println("Fen�tre pour voir les r�sultats d'une recherche");
		this.setContentPane(new PanneauAdmin());
		//D�finit un titre pour notre fen�tre
		this.setTitle("R�sultats de la recherche");
		//D�finit une taille pour celle-ci
		this.setPreferredSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
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

		this.setAdministrateur(a);
		this.setStat(stat);

		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(500,30));
		labelAdmin.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,500));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.add(labelMsg);

		List<Compte> listeComptes;
		try {
			listeComptes = DAOCompte.getComptesByRecherche(fenetrePrec.getTypeEntre(),fenetrePrec.getIdEntre(),fenetrePrec.getNomEntre(),fenetrePrec.getPrenomEntre(),fenetrePrec.getAdresseEMailEntree());
			
			System.out.println("Il y a "+listeComptes.size()+ " individu(s) trouv�(s)");
			if(listeComptes.size()>0){
				labelMsg.setText("R�sultats de la recherche : "+listeComptes.size()+" individu(s) trouv�(s)");
				String [] tableauComptes = new String[listeComptes.size()+1];
				tableauComptes[0]="S�lectionnez un compte";
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
				tableau.setFont(FenetreAuthentificationUtil.POLICE3);
				tableau.setPreferredSize(new Dimension(400,50));
				tableau.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						try {
							Object o = ((JComboBox)ae.getSource()).getSelectedItem();
							String chaineSelectionnee = (String)(o);
							//il faut r�cup�rer l'identifiant du compte entr� mais on ne conna�t pas la taille de la String en question
							int k=0;
							while(chaineSelectionnee.charAt(k)!=' '){
								k++;
							}
							String idCompteEntre = chaineSelectionnee.substring(0,k+1);
							compteEntre = DAOCompte.getCompteById(idCompteEntre);
							System.out.println("idCompteEntre = "+idCompteEntre + " - "+compteEntre.isActif());
						} 
						catch (SQLException e) {
							MsgBox.affMsg(e.getMessage());
						} 
						catch (ClassNotFoundException e) {
							MsgBox.affMsg(e.getMessage());
						}
						catch (ConnexionFermeeException e){
							MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
							new FenetreAuthentification(false);
						}
					}
				});
				this.setCompteEntre(compteEntre);
				center.add(labelMsg);
				center.add(tableau);
				boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
				boutonValider.setBackground(Color.CYAN);
				boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
				boutonValider.addActionListener(this);
				center.add(boutonValider);
			}
			else{
				labelMsg.setText("Il n'y a aucun r�sultat pour cette recherche. ");
				center.add(labelMsg);
			}

		}catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}	


		boutonNouvelleRecherche.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonNouvelleRecherche.setBackground(Color.CYAN);
		boutonNouvelleRecherche.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonNouvelleRecherche.addActionListener(this);
		center.add(boutonNouvelleRecherche);

		this.getContentPane().add(center,BorderLayout.CENTER);


		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,50));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonValider){
			try {
				new FenetreInfoCompteAdmin(this.getAdministrateur(),compteEntre,stat);
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if (arg0.getSource()==boutonNouvelleRecherche){
			new FenetreRechercherCompteAdmin(this.getAdministrateur(),stat);
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}
}
