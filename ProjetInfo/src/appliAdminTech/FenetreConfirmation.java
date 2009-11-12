package appliAdminTech;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Compte;
import appliUtil.FenetreAuthentificationUtil;

public class FenetreConfirmation extends JFrame implements ActionListener {

	private Compte compte;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelConfirm = new JLabel("");
	private JButton bouton1 = new JButton("");
	private JButton bouton2 = new JButton("");
	private JButton bouton3 = new JButton("");

	public FenetreConfirmation(String msg,Compte c,JFrame fenetrePrec){
		this.setTitle("Ecran de confirmation");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel south = new JPanel();

		//situations possibles pour un administrateur
		if (fenetrePrec.getTitle().equals("Création d'un nouveau compte")){
			labelConfirm.setText("Le nouveau compte a bien été créé. ");
			bouton1.setText("Créer un autre compte");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Modification d'un compte")){
			labelConfirm.setText("La modification ou résiliation a bien été enregistrée. ");
			bouton1.setText("Afficher informations sur un autre compte");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Envoyer une demande d'assignation")){
			labelConfirm.setText("La demande d'assignation a bien été envoyée. ");
			bouton1.setText("Voir l'état d'une autre station");
			bouton1.addActionListener(this);
			bouton2.setText("Voir les stations sur et sous-occupées");
			bouton2.addActionListener(this);
			bouton3.setText("Retour au menu principal");
			bouton3.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
			south.add(bouton3);
		}

		//situations possibles pour un technicien
		else if(fenetrePrec.getTitle().equals("Enregistrer un nouveau vélo")){
			labelConfirm.setText("Le vélo a bien été ajouté et affecté au garage. ");
			bouton1.setText("Enregistrer un autre nouveau vélo");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Retirer un vélo défectueux d'une station")){
			labelConfirm.setText("Le vélo défectueux a bien été retiré de la station et affecté au garage. ");
			bouton1.setText("Retirer un autre vélo défectueux");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Prendre en charge l'assignation")){
			labelConfirm.setText("Le déplacement de ces vélos a bien été confirmé. ");
			bouton1.setText("Gérer une autre demande d'assignation");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		
		//situations possibles dans tous les cas : déconnexion
		else if(fenetrePrec.getTitle().equals("Menu principal de l'administrateur") || fenetrePrec.getTitle().equals("Menu principal du technicien")){
			labelConfirm.setText("Au revoir et à bientôt ! ");
			bouton1.setText("Nouvelle authentification");
			// dans l'idée l'écran ne reste affiché que 3 secondes et la fenêtre d'authentification apparaîtautomatiquement au terme des 3 secondes
			south.add(bouton1);
		}
		this.getContentPane().add(labelConfirm,BorderLayout.CENTER);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}