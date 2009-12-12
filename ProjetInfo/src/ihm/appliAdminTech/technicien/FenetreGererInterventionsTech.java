package ihm.appliAdminTech.technicien;

import exceptions.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAOIntervention;
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

import metier.Intervention;
import metier.Technicien;

public class FenetreGererInterventionsTech extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("Demandes d'intervention");
	private Intervention interventionEntree;
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien technicien) {
		this.technicien = technicien;
	}

	public FenetreGererInterventionsTech (Technicien t) throws ConnexionFermeeException{
		System.out.println("Fenêtre pour voir toutes les interventions non traitées");
		this.setContentPane(new PanneauTech());
		//Définit un titre pour notre fenêtre
		this.setTitle("Gérer les interventions non traitées");
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

		this.setTechnicien(t);

		labelTech = new JLabel("Vous êtes connecté en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelTech.setPreferredSize(new Dimension(300,30));
		labelTech.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,400));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		List<Intervention> listeInterventions;
		try{
			listeInterventions= DAOIntervention.getInterventionsNonTraitees();

			if(listeInterventions.size()==0){
				labelMsg.setText("Il n'y a actuellement aucune intervention en cours à gérer.");
				center.add(labelMsg);
			}
			else{
				labelMsg.setText("Veuillez sélectionner une des interventions suivantes");
				center.add(labelMsg);
				String [] tableauInterventions = new String[listeInterventions.size()+1];
				tableauInterventions[0]=listeInterventions.size()+" intervention(s) trouvée(s)";
				for (int i=0;i<listeInterventions.size();i++){
					Intervention interventioni = listeInterventions.get(i);
					tableauInterventions[i+1] = DAOIntervention.ligne(interventioni);
				}

				DefaultComboBoxModel model = new DefaultComboBoxModel(tableauInterventions);

				JComboBox combo = new JComboBox(model);
				combo.setFont(FenetreAuthentificationUtil.POLICE3);
				combo.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						Object o = ((JComboBox)ae.getSource()).getSelectedItem();
						try {
							String chaineSelectionnee = (String)(o);
							String idInterventionEntre="";
							int i=13;
							while(chaineSelectionnee.charAt(i)!=' '){
								idInterventionEntre=idInterventionEntre+chaineSelectionnee.charAt(i);
								i++;
							}
							System.out.println("id de l'intervention entrée : "+idInterventionEntre);
							interventionEntree = DAOIntervention.getInterventionById(idInterventionEntre);
						} catch (SQLException e) {
							MsgBox.affMsg(e.getMessage());
						} catch (ClassNotFoundException e) {
							MsgBox.affMsg(e.getMessage());
						}
						catch (ConnexionFermeeException e){
							MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
							new FenetreAuthentification(false);
						}
					}
				});
				center.add(combo);

				boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
				boutonValider.setBackground(Color.CYAN);
				boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
				boutonValider.addActionListener(this);
				center.add(boutonValider);

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
		} catch(NullPointerException e){
			MsgBox.affMsg("NullPointerException : "+e.getMessage());
		} catch (SQLException e) {
			MsgBox.affMsg("SQLException : "+e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg("ClassNotFoundException : "+e.getMessage());
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonValider){
			new FenetreGererUneInterventionTech(this.getTechnicien(),interventionEntree);
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}
