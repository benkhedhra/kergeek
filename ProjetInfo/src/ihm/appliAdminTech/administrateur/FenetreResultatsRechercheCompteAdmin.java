package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.DAOUtilisateur;
import ihm.MsgBox;
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

	public FenetreResultatsRechercheCompteAdmin (Administrateur a,FenetreRechercherCompteAdmin fenetrePrec,boolean stat){
		System.out.println("Fenêtre pour voir les résultats d'une recherche");
		this.setContentPane(new PanneauAdmin());
		//Définit un titre pour notre fenêtre
		this.setTitle("Résultats de la recherche");
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
		this.setStat(stat);

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
		center.setPreferredSize(new Dimension(700,500));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.add(labelMsg);

		List<Compte> listeComptes;
		try {
			listeComptes = DAOCompte.getComptesByRecherche(fenetrePrec.getTypeEntre(),fenetrePrec.getIdEntre(),fenetrePrec.getNomEntre(),fenetrePrec.getPrenomEntre(),fenetrePrec.getAdresseEMailEntree());
			System.out.println("Il y a "+listeComptes.size()+ " utilisateur(s) trouvé(s)");
			if(listeComptes.size()>0){
				labelMsg.setText("Résultats de la recherche : "+listeComptes.size()+" individu(s) trouvé(s)");
				String [] tableauComptes = new String[listeComptes.size()];

				for (int i=0;i<tableauComptes.length;i++){
					Compte comptei = listeComptes.get(i);
					if(comptei.getType()==Compte.TYPE_UTILISATEUR){
						tableauComptes[i] = DAOUtilisateur.getUtilisateurById(comptei.getId()).toString();
					}
					else if(comptei.getType()==Compte.TYPE_ADMINISTRATEUR){
						tableauComptes[i] = DAOAdministrateur.getAdministrateurById(comptei.getId()).toString();
					}
					else if(comptei.getType()==Compte.TYPE_TECHNICIEN){
						tableauComptes[i] = DAOTechnicien.getTechnicienById(comptei.getId()).toString();
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
							String idCompteEntre = chaineSelectionnee.substring(0,2);
							System.out.println("idCompteEntre = "+idCompteEntre);
							compteEntre = DAOCompte.getCompteById(idCompteEntre);
						} catch (SQLException e) {
							MsgBox.affMsg(e.getMessage());
						} catch (ClassNotFoundException e) {
							MsgBox.affMsg(e.getMessage());
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
				labelMsg.setText("Il n'y a aucun résultat pour cette recherche. ");
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
			new FenetreInfoCompteAdmin(this.getAdministrateur(),compteEntre,stat);
		}
		else if (arg0.getSource()==boutonNouvelleRecherche){
			new FenetreRechercherCompteAdmin(this.getAdministrateur(),stat);
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}
}
