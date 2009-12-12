package ihm.appliAdminTech.technicien;

import exceptions.exceptionsTechniques.ConnexionFermeeException;
import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOTypeIntervention;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Intervention;
import metier.Technicien;
import metier.TypeIntervention;

public class FenetrePrendreEnChargeInterventionTech extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private Intervention intervention;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JLabel labelTypeIntervention = new JLabel ("Veuillez entrer le type d'intervention");
	private TypeIntervention typeInterventionEntre;
	private JButton boutonValider = new JButton("Commencer l'intervention");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien technicien) {
		this.technicien = technicien;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public FenetrePrendreEnChargeInterventionTech(Technicien t, Intervention i) throws ConnexionFermeeException{
		System.out.println("Fenêtre pour prendre en charge une intervention");
		this.setContentPane(new PanneauTech());
		//Définit un titre pour notre fenêtre
		this.setTitle("Prendre en charge une intervention");
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
		this.setIntervention(i);

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
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		labelMsg.setPreferredSize(new Dimension(600,50));
		labelMsg.setMaximumSize(new Dimension(600,50));
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);

		labelMsg.setText(DAOIntervention.ligne(i));
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(550,350));
		centerWest.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelTypeIntervention.setPreferredSize(new Dimension(200,30));		
		panel1.add(labelTypeIntervention);
		centerWest.add(panel1);

		try {
			Collection<String> collection = DAOTypeIntervention.getAllTypesIntervention().values();
			List<String> listeTypes = new ArrayList<String>(collection.size());
			listeTypes.addAll(collection);
			String [] tableauTypes = new String[listeTypes.size()+1];
			tableauTypes[0]="Sélectionnez un type d'intervention";
			for (int k=0;k<listeTypes.size();k++){
				tableauTypes[k+1]=listeTypes.get(k).toString();
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauTypes);

			JComboBox combo = new JComboBox(model);
			combo.setFont(FenetreAuthentificationUtil.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					String chaineSelectionnee = (String) o;
					try {
						typeInterventionEntre = DAOTypeIntervention.getTypeInterventionById(Integer.parseInt(chaineSelectionnee.substring(0,1)));
					} catch (NumberFormatException e) {
						MsgBox.affMsg(e.getMessage());
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

			JPanel panel2 = new JPanel();
			panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			combo.setPreferredSize(new Dimension(300,30));
			panel2.add(combo);
			centerWest.add(panel2);		

			center.add(centerWest,BorderLayout.WEST);

			JPanel centerSouth = new JPanel();
			centerSouth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
			boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
			boutonValider.setBackground(Color.CYAN);
			boutonValider.addActionListener(this);
			centerSouth.add(boutonValider);
			center.add(centerSouth, BorderLayout.SOUTH);

		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (NullPointerException e) {
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
			Intervention i = this.getTechnicien().terminerIntervention(this.getIntervention(),typeInterventionEntre);
			try {
				DAOIntervention.updateIntervention(i);
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			}
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
			new FenetreConfirmation(this.getTechnicien().getCompte(),this);
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}