package appliUtil;

import java.awt.BorderLayout;
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
import metier.Velo;

public class FenetreRendreVelo extends JFrame implements ActionListener {

	private Utilisateur utilisateur = LancerAppliUtil.UTEST;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonDeconnexion = new JButton("Déconnexion");

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public FenetreRendreVelo(Utilisateur u) {

		System.out.println("Fenêtre pour rendre un vélo");
		this.setUtilisateur(u);
		this.setSize(700, 500);
		this.setTitle("Rendre un vélo");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.setContentPane(new Panneau());	
		this.getContentPane().setLayout(new BorderLayout());

		labelUtil = new JLabel("Vous êtes connecté en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.add(labelUtil);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		Station[] stations = new Station[3];
		//idée : getAllStations : ici lignes suivantes pour tester provisoirement
		stations[0] = new Station("Etangs", "1 rue des lilas", 30);
		stations[1] = new Station("Coeur de campus","2 rue des lilas", 20);
		stations[2] = new Station("Terrains de sport", "3 rue des lilas", 10);
		DefaultComboBoxModel model = new DefaultComboBoxModel(stations);
		JPanel center = new JPanel();
		JComboBox combo = new JComboBox(model);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Object o = ((JComboBox)ae.getSource()).getSelectedItem();
				Station s = (Station)o;
				labelMsg.setText("Station sélectionnée : " + s.getId());
			}

		});
		center.add(combo);

		boutonValider.addActionListener(this);
		labelMsg = new JLabel("Sélectionnez la station où vous vous trouvez");
		center.add(labelMsg);
		center.add(boutonValider);
		this.getContentPane().add(center, BorderLayout.CENTER);
	}


	public static void main(String[] args) {
		FenetreRendreVelo combo = new FenetreRendreVelo(LancerAppliUtil.UTEST);
		combo.pack();
		combo.setVisible(true);
	}


	public void actionPerformed(ActionEvent arg0) {
		Velo velo;
		this.dispose();
		if(arg0.getSource()==boutonValider && this.getUtilisateur().getVelo().getEmprunt().getDiff()>300){
			//le 300 est arbitraire : à vérifier s'il s'agit de secondes ? 
			//à trouver : moyen de récupérer la station sélectionnée
			//Station s = (Station)(JComboBox)ae.getSource()).getSelectedItem();
			//this.getUtilisateur().rendreVelo(s);
			FenetreConfirmation f = new FenetreConfirmation("Remettez le vélo dans un emplacement. Merci et à bientôt ! ");
			f.setVisible(true);
			System.out.println("Le vélo a bien été rendu");
		}
		else if (arg0.getSource()==boutonDeconnexion){
			FenetreConfirmation f = new FenetreConfirmation("Merci et à bientôt ! ");
			f.setVisible(true);
		}
		else{
			//c'est donc que l'utilisateur a cliqué sur valider et que l'emprunt est inférieur à 300 s
			FenetreEmpruntCourt f = new FenetreEmpruntCourt(this.getUtilisateur());
			f.setVisible(true);
		}

	}

}