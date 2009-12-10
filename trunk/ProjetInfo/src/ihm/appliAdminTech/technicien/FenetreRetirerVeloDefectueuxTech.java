package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeIntervention;
import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreConfirmation;
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
import javax.swing.JTextField;

import metier.DemandeIntervention;
import metier.Intervention;
import metier.Technicien;

public class FenetreRetirerVeloDefectueuxTech extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg1 = new JLabel("Sélectionnez une des demandes d'intervention formulées par les utilisateurs");
	private DemandeIntervention demandeEntree=null;
	private JButton boutonValider = new JButton("Retirer le vélo indiqué");
	private JLabel labelMsg2 = new JLabel("<html><center>Si vous voulez constater un défaut sur un vélo qui ne figure pas dans les demandes ci-dessus, <br>veuillez entrer son identifiant : </center></html> ");
	private JTextField idVeloARemplir = new JTextField("");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien technicien) {
		this.technicien = technicien;
	}

	public FenetreRetirerVeloDefectueuxTech (Technicien t){
		System.out.println("Fenêtre pour voir toutes les demandes d'intervention");
		this.setContentPane(new PanneauTech());
		//Définit un titre pour notre fenêtre
		this.setTitle("Gérer les demandes d'intervention");
		//Définit une taille pour celle-ci
		this.setPreferredSize(new Dimension(700,500));		
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

		this.setTechnicien(t);

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		labelTech.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,400));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setPreferredSize(new Dimension(700,250));
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		labelMsg1.setPreferredSize(new Dimension(600,50));		
		labelMsg1.setMinimumSize(new Dimension(600,50));
		centerNorth.add(labelMsg1);

		List<DemandeIntervention> listeDemandes;
		try {
			listeDemandes= DAODemandeIntervention.getDemandesInterventionEnAttente();
			String [] tableauDemandes = new String[listeDemandes.size()+1];
			tableauDemandes[0]=listeDemandes.size()+" demandes formulées";
			for (int i=0;i<listeDemandes.size();i++){
				DemandeIntervention demandei = listeDemandes.get(i);
				tableauDemandes[i+1] = DAODemandeIntervention.ligne(demandei);
			}

			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauDemandes);

			JComboBox combo = new JComboBox(model);
			combo.setFont(FenetreAuthentificationUtil.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					try {
						String chaineSelectionnee = (String)(o);
						String idDemandeEntre="";
						int i=8;
						while(chaineSelectionnee.charAt(i)!=' '){
							idDemandeEntre=idDemandeEntre+chaineSelectionnee.charAt(i);
							i++;
						}
						System.out.println("id de la demande entré : "+idDemandeEntre);
						demandeEntree = DAODemandeIntervention.getDemandeInterventionById(idDemandeEntre);
					} catch (SQLException e) {
						MsgBox.affMsg(e.getMessage());
					} catch (ClassNotFoundException e) {
						MsgBox.affMsg(e.getMessage());
					}
				}
			});
			combo.setPreferredSize(new Dimension(250,50));
			combo.setMinimumSize(new Dimension(250,50));
			centerNorth.add(combo);
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}		
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.addActionListener(this);
		centerNorth.add(boutonValider);

		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerSouth = new JPanel();
		centerSouth.setPreferredSize(new Dimension(700,150));
		centerSouth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		labelMsg2.setPreferredSize(new Dimension(650,50));		
		labelMsg2.setMinimumSize(new Dimension(650,50));
		centerSouth.add(labelMsg2);
		idVeloARemplir.setPreferredSize(new Dimension(200,50));		
		idVeloARemplir.setMinimumSize(new Dimension(200,50));
		centerSouth.add(idVeloARemplir);

		center.add(centerSouth,BorderLayout.SOUTH);

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
		if (arg0.getSource()==boutonValider){
			if(demandeEntree!=null){
				Intervention i = this.getTechnicien().intervenir(demandeEntree.getVelo());
				try {
					DAOIntervention.updateIntervention(i);
				} catch (ClassNotFoundException e) {
					MsgBox.affMsg(e.getMessage());
				} catch (SQLException e) {
					MsgBox.affMsg(e.getMessage());
				}
				new FenetreConfirmation(this.getTechnicien().getCompte(),this);
			}
			try {
				if(DAOVelo.estDansLaBdd(idVeloARemplir.getText()) &&  DAOVelo.estDisponible(idVeloARemplir.getText())){
					//TODO
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}