package ihm.appliUtil;

import ihm.MsgBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Station;
import metier.Utilisateur;
import exception.PasDeVeloEmprunteException;
import exception.VeloNonSortiException;


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
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public FenetreRendreVelo(Utilisateur u) {
		
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

		
		labelUtil = new JLabel("Vous �tes connect� en tant que "+ u.getPrenom()+" "+u.getNom());
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

		
		//pour la suite besoin de DAOStation avec getAllStations 
		//List<Station> stations = DAOLieu.getAllStations();
		Station[] stations = new Station[3];
		//id�e : getAllStations : ici lignes suivantes pour tester provisoirement
		stations[0] = new Station("Etangs", "1 rue des lilas", 30);
		stations[1] = new Station("Coeur de campus","2 rue des lilas", 20);
		stations[2] = new Station("Terrains de sport", "3 rue des lilas", 10);
		DefaultComboBoxModel model = new DefaultComboBoxModel(stations);
		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		JComboBox combo = new JComboBox(model);
		combo.setFont(FenetreAuthentificationUtil.POLICE3);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Object o = ((JComboBox)ae.getSource()).getSelectedItem();
				stationEntree = (Station)o;
				labelMsg.setText("Station s�lectionn�e : " + stationEntree.getId());
				labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
			}

		});
		center.add(combo);

		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.addActionListener(this);
		labelMsg.setText("S�lectionnez la station o� vous vous trouvez");
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		center.add(labelMsg);
		center.add(boutonValider);
		this.getContentPane().add(center, BorderLayout.CENTER);
		
		this.setVisible(true);
	}


	public static void main(String[] args) {
		new FenetreRendreVelo(LancerAppliUtil.UTEST);
	}


	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonValider && this.getUtilisateur().getVelo().getEmprunt().getDiff()>120){
			//le 300 est arbitraire : � v�rifier s'il s'agit de secondes ? 
			try {
				this.getUtilisateur().rendreVelo(stationEntree);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				MsgBox.affMsg(e.getMessage());
			} catch (VeloNonSortiException e) {
				// TODO Auto-generated catch block
				MsgBox.affMsg(e.getMessage());
			} catch (PasDeVeloEmprunteException e) {
				// TODO Auto-generated catch block
				MsgBox.affMsg(e.getMessage());
			}
			FenetreConfirmationUtil f = new FenetreConfirmationUtil("Remettez le v�lo dans un emplacement. Merci et � bient�t ! ");
			f.setVisible(true);
			System.out.println("Le v�lo a bien �t� rendu");
		}
		else if (arg0.getSource()==boutonDeconnexion){
			FenetreConfirmationUtil f = new FenetreConfirmationUtil("Merci et � bient�t ! ");
			f.setVisible(true);
		}
		else{
			//c'est donc que l'utilisateur a cliqu� sur valider et que l'emprunt est inf�rieur � 300 s
			FenetreEmpruntCourt f = new FenetreEmpruntCourt(this.getUtilisateur());
			f.setVisible(true);
		}

	}

}