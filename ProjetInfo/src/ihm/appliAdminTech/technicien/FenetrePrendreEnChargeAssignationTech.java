package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.DemandeAssignation;
import metier.Technicien;

public class FenetrePrendreEnChargeAssignationTech extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private DemandeAssignation demande;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JLabel labelVelo = new JLabel("");
	private JTextField veloARemplir = new JTextField();
	private JButton boutonValider = new JButton("Confirmer les d�placements");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien technicien) {
		this.technicien = technicien;
	}


	public DemandeAssignation getDemande() {
		return demande;
	}

	public void setDemande(DemandeAssignation d) {
		this.demande = d;
	}

	public FenetrePrendreEnChargeAssignationTech(Technicien t, DemandeAssignation d){
		System.out.println("Fen�tre pour prendre en charge une demande d'assignation");
		this.setContentPane(new PanneauAdmin());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Prendre en charge une assignation");
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

		this.setTechnicien(t);
		this.setDemande(d);

		labelTech = new JLabel("Vous �tes connect� en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		labelTech.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		int nbVelosADeplacer;

		try {
			int diff=DAOVelo.getVelosByLieu(demande.getLieu()).size()-demande.getNombreVelosVoulusDansLieu();
			/* si diff<0 cela signifie qu'il faut ajouter des v�los depuis le garage vers la station
			 * si diff>0 c'est qu'il faudra en retirer de la station pour les mettre au garage
			 */

			nbVelosADeplacer = Math.abs(diff);

			labelMsg.setPreferredSize(new Dimension(600,50));
			labelMsg.setMaximumSize(new Dimension(600,50));
			labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);

			//a priori ne sert pas
			if(nbVelosADeplacer==0){
				labelMsg.setText("Il n'y a aucun v�lo � d�placer. Le nombre de v�los dans la station est le nombre voulu. ");
				centerNorth.add(labelMsg);
				center.add(centerNorth,BorderLayout.NORTH);
			}
			else{
				JPanel centerCenter = new JPanel();
				centerCenter.setPreferredSize(new Dimension(550,350));
				centerCenter.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

				labelVelo.setPreferredSize(new Dimension(200,30));
				veloARemplir.setPreferredSize(new Dimension(300,30));

				if(diff<0){
					labelMsg.setText("Veuillez entrer les identifiants des v�los affect�s du garage � la station "+d.getLieu().getId());

					for (int i =0;i<nbVelosADeplacer;i++){
						JPanel panel1 = new JPanel();
						panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
						panel1.setPreferredSize(new Dimension(250,30));
						labelVelo.setText("V�lo "+(i+1));
						panel1.add(labelVelo);
						centerCenter.add(panel1);

						JPanel panel2 = new JPanel();
						panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
						panel2.setPreferredSize(new Dimension(250,30));
						panel2.add(veloARemplir);
						centerCenter.add(panel2);
					}
				}
				else{
					labelMsg.setText("Veuillez entrer les identifiants des v�los affect�s de la station "+d.getLieu().getId()+" au garage");

					for (int i =0;i<nbVelosADeplacer;i++){
						JPanel panel1 = new JPanel();
						panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
						panel1.setPreferredSize(new Dimension(250,30));
						labelVelo.setText("V�lo "+(i+1));
						panel1.add(labelVelo);
						centerCenter.add(panel1);

						JPanel panel2 = new JPanel();
						panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
						panel2.setPreferredSize(new Dimension(250,30));
						panel2.add(veloARemplir);
						centerCenter.add(panel2);
					}
				}
				centerNorth.add(labelMsg);
				center.add(centerNorth,BorderLayout.NORTH);
				center.add(centerCenter,BorderLayout.CENTER);

				boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
				boutonValider.setBackground(Color.CYAN);
				boutonValider.addActionListener(this);
				centerCenter.add(boutonValider);

				center.add(centerCenter, BorderLayout.CENTER);
			}

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
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
		if(arg0.getSource()==boutonValider){
			demande.setPriseEnCharge(true);
			try {
				DAODemandeAssignation.updateDemandeAssignation(demande);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new FenetreConfirmation(this.getTechnicien().getCompte(),this);
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}