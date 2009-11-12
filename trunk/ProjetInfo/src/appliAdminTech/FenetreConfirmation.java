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
		if (fenetrePrec.getTitle().equals("Cr�ation d'un nouveau compte")){
			labelConfirm.setText("Le nouveau compte a bien �t� cr��. ");
			bouton1.setText("Cr�er un autre compte");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Modification d'un compte")){
			labelConfirm.setText("La modification ou r�siliation a bien �t� enregistr�e. ");
			bouton1.setText("Afficher informations sur un autre compte");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Envoyer une demande d'assignation")){
			labelConfirm.setText("La demande d'assignation a bien �t� envoy�e. ");
			bouton1.setText("Voir l'�tat d'une autre station");
			bouton1.addActionListener(this);
			bouton2.setText("Voir les stations sur et sous-occup�es");
			bouton2.addActionListener(this);
			bouton3.setText("Retour au menu principal");
			bouton3.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
			south.add(bouton3);
		}

		//situations possibles pour un technicien
		else if(fenetrePrec.getTitle().equals("Enregistrer un nouveau v�lo")){
			labelConfirm.setText("Le v�lo a bien �t� ajout� et affect� au garage. ");
			bouton1.setText("Enregistrer un autre nouveau v�lo");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Retirer un v�lo d�fectueux d'une station")){
			labelConfirm.setText("Le v�lo d�fectueux a bien �t� retir� de la station et affect� au garage. ");
			bouton1.setText("Retirer un autre v�lo d�fectueux");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		else if(fenetrePrec.getTitle().equals("Prendre en charge l'assignation")){
			labelConfirm.setText("Le d�placement de ces v�los a bien �t� confirm�. ");
			bouton1.setText("G�rer une autre demande d'assignation");
			bouton1.addActionListener(this);
			bouton2.setText("Retour au menu principal");
			bouton2.addActionListener(this);
			south.add(bouton1);
			south.add(bouton2);
		}
		
		//situations possibles dans tous les cas : d�connexion
		else if(fenetrePrec.getTitle().equals("Menu principal de l'administrateur") || fenetrePrec.getTitle().equals("Menu principal du technicien")){
			labelConfirm.setText("Au revoir et � bient�t ! ");
			bouton1.setText("Nouvelle authentification");
			// dans l'id�e l'�cran ne reste affich� que 3 secondes et la fen�tre d'authentification appara�tautomatiquement au terme des 3 secondes
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