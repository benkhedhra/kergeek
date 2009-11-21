package ihm.appliUtil;

import exception.PasDeVeloEmprunteException;
import exception.VeloNonSortiException;
import gestionBaseDeDonnees.DAOEmprunt;
import gestionBaseDeDonnees.DAOLieu;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;

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

import metier.Emprunt;
import metier.Station;
import metier.Utilisateur;


public class FenetreRendreVelo extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur = LancerAppliUtil.UTEST;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonDeconnexion = new JButton("Déconnexion");
	private Station stationEntree;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public FenetreRendreVelo(Utilisateur u) {

		System.out.println("Fenêtre pour rendre un vélo");
		this.setContentPane(new Panneau());
		//Définit un titre pour notre fenêtre
		this.setTitle("Rendre un vélo");
		//Définit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		//pour que la fenêtre ne se redimensionne pas à chaque fois
		this.setResizable(false);
		//pour que la fenêtre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setUtilisateur(u);


		labelUtil = new JLabel("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		labelUtil.setPreferredSize(new Dimension(500,30));
		boutonDeconnexion.setPreferredSize(new Dimension(150,30));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(FenetreAuthentificationUtil.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelUtil);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		List<Station> listeStations;
		try {
			listeStations = DAOLieu.getAllStations();
			Station [] tableauStations = new Station[listeStations.size()];
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauStations);
			JPanel center = new JPanel();
			center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			JComboBox combo = new JComboBox(model);
			combo.setFont(FenetreAuthentificationUtil.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					stationEntree = (Station)o;
					labelMsg.setText("Station sélectionnée : " + stationEntree.getId());
					labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
				}

			});
			center.add(combo);

			boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonValider.setBackground(Color.CYAN);
			boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonValider.addActionListener(this);
			labelMsg.setText("Sélectionnez la station où vous vous trouvez");
			labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
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
		try {
			if (arg0.getSource()==boutonDeconnexion){
				new FenetreConfirmationUtil("Merci et à bientôt ! ");
			}
			else if (arg0.getSource()==boutonValider){
				if (this.getUtilisateur().rendreVelo(stationEntree)){
					// l'utilisateur a bien rendu le vélo
					DAOEmprunt.updateEmprunt(this.getUtilisateur().getVelo().getEmpruntEnCours());
					this.getUtilisateur().getVelo().setEmprunt(null);
					DAOVelo.updateVelo(this.getUtilisateur().getVelo());
					if (this.getUtilisateur().getVelo().getEmpruntEnCours().getDiff()>Emprunt.TPS_EMPRUNT_MIN){
						//emprunt trop court
						new FenetreEmpruntCourt(this.getUtilisateur());
					}
					else if (this.getUtilisateur().getVelo().getEmpruntEnCours().getDiff()>Emprunt.TPS_EMPRUNT_MAX){
						//emprunt trop long
						new FenetreEmpruntLong(this.getUtilisateur());
						this.getUtilisateur().setBloque(true);
						DAOUtilisateur.updateUtilisateur(this.getUtilisateur());
					}
					else {
						//emprunt ni trop court ni trop long
						new FenetreConfirmationUtil("Remettez le vélo dans un emplacement. Merci et à bientôt ! ");
						System.out.println("Le vélo a bien été rendu");
					}
				}
				else {
					//le vélo n'a pas été rendu
					MsgBox.affMsg("problème");
				}
			}
		}//fin du try
		catch (SQLException e) {
			// TODO Auto-generated catch block
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			MsgBox.affMsg(e.getMessage());
		} catch (VeloNonSortiException e) {
			// TODO Auto-generated catch block
			MsgBox.affMsg(e.getMessage());
		} catch (PasDeVeloEmprunteException e) {
			MsgBox.affMsg(e.getMessage());
		}
	}
}