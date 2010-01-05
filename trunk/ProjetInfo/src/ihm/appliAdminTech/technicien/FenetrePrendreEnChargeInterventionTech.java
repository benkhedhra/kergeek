package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOTypeIntervention;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Intervention;
import metier.Technicien;
import metier.TypeIntervention;

/**
 * la classe {@link FenetrePrendreEnChargeInterventionTech} hérite de {@link JFrame} et implémente l'interface {@link ActionListener}
 * <br>cette fenêtre apparaît lorsque le technicien a cliqué sur "Prendre en charge" dans la {@link FenetreGererUneInterventionTech}
 * <br>elle demande au {@link Technicien} de sélectionner le type d'intervention effectué sur le vélo concerné par l'{@link Intervention}
 * <br>cette prise en charge doit s'effectuer APRES l'opération physique effectuée sur le vélo
 * <br>cette fenêtre est propre au volet Technicien de l'application AdminTech
 * @author KerGeek
 *
 */
public class FenetrePrendreEnChargeInterventionTech extends JFrame implements ActionListener {
	/*
	 * liste des attributs privés de la fenêtre
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private Intervention intervention;

	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JLabel labelTypeIntervention = new JLabel ("Veuillez entrer le type d'intervention");
	private TypeIntervention typeInterventionEntre;
	private JButton boutonValider = new JButton("Terminer l'intervention");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	// Accesseurs utiles

	/**
	 * @return	le {@link FenetrePrendreEnChargeInterventionTech#technicien} de la {@link FenetrePrendreEnChargeInterventionTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise le {@link FenetrePrendreEnChargeInterventionTech#technicien} de la {@link FenetrePrendreEnChargeInterventionTech}
	 * @param tech
	 * le technicien connecté sur cette fenêtre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	/**
	 * @return	le {@link FenetrePrendreEnChargeInterventionTech#intervention} de la {@link FenetrePrendreEnChargeInterventionTech}
	 */
	public Intervention getIntervention() {
		return intervention;
	}

	/**
	 * Initialise le {@link FenetrePrendreEnChargeInterventionTech#intervention} de la {@link FenetrePrendreEnChargeInterventionTech}
	 * @param intervention
	 * l'intervention prise en charge dans la fenêtre précédente
	 * @see Technicien
	 */
	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	/**
	 * @return	le {@link FenetrePrendreEnChargeInterventionTech#typeInterventionEntre} de la {@link FenetrePrendreEnChargeInterventionTech}
	 */
	public TypeIntervention getTypeInterventionEntre() {
		return typeInterventionEntre;
	}

	/**
	 * Initialise le {@link FenetrePrendreEnChargeInterventionTech#typeInterventionEntre} de la {@link FenetrePrendreEnChargeInterventionTech}
	 * @param typeInterventionEntre
	 * le type d'intervention réalisé sur le vélo concerné par l'intervention (y compris la destruction du vélo s'il est irréparable)
	 */
	public void setTypeInterventionEntre(TypeIntervention typeInterventionEntre) {
		this.typeInterventionEntre = typeInterventionEntre;
	}

	/**
	 * constructeur de {@link FenetrePrendreEnChargeInterventionTech}
	 * @param t
	 * le technicien connecté sur la fenêtre
	 * @param i
	 * l'intervention en train d'être prise en charge
	 * @throws ConnexionFermeeException
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JComboBox
	 */
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
		labelTech.setFont(UtilitaireIhm.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		labelTech.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(UtilitaireIhm.TRANSPARENCE);

		labelMsg.setPreferredSize(new Dimension(600,50));
		labelMsg.setMaximumSize(new Dimension(600,50));
		labelMsg.setFont(UtilitaireIhm.POLICE2);

		labelMsg.setText(i.toString());
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(550,350));
		centerWest.setBackground(UtilitaireIhm.TRANSPARENCE);

		JPanel panel1 = new JPanel();
		panel1.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelTypeIntervention.setPreferredSize(new Dimension(200,30));		
		panel1.add(labelTypeIntervention);
		centerWest.add(panel1);

		try {
			final Map<Integer, String> m = DAOTypeIntervention.getAllTypesIntervention();
			Collection<String> collection = m.values();
			final List<String> listeTypes = new ArrayList<String>(collection.size());
			listeTypes.addAll(collection);

			final String [] tableauTypes = new String[listeTypes.size()+1];
			tableauTypes[0]="Sélectionnez un type d'intervention";
			for (int k=0;k<listeTypes.size();k++){
				tableauTypes[k+1]=listeTypes.get(k).toString();
			}
			for (int k = 0 ; k<tableauTypes.length;k++){
				System.out.println("tableauTypes["+k+"]="+tableauTypes[k]);
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauTypes);

			JComboBox combo = new JComboBox(model);
			combo.setPreferredSize(new Dimension(300,40));		
			combo.setFont(UtilitaireIhm.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					String chaineSelectionnee = (String) o;
					try {
						if(chaineSelectionnee==null || chaineSelectionnee.equals(tableauTypes[0])){
							typeInterventionEntre=null;
						}
						else{
							for (int k : m.keySet()){
								if(m.get(k).equals(chaineSelectionnee)){
									typeInterventionEntre = DAOTypeIntervention.getTypeInterventionById(k);
								}
							}
						}
						repaint();
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
			panel2.setBackground(UtilitaireIhm.TRANSPARENCE);
			combo.setPreferredSize(new Dimension(300,30));
			panel2.add(combo);
			centerWest.add(panel2);		

			center.add(centerWest,BorderLayout.WEST);

			JPanel centerSouth = new JPanel();
			centerSouth.setBackground(UtilitaireIhm.TRANSPARENCE);
			boutonValider.setFont(UtilitaireIhm.POLICE3);
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
		south.setBackground(UtilitaireIhm.TRANSPARENCE);

		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/**
	 * cette méthode est exécutée si le {@link Technicien} a cliqué surl 'un des deux boutons qui lui étaient proposés
	 * <br>s'il a cliqué sur {@link FenetrePrendreEnChargeInterventionTech#boutonValider}, l'intervention est terminée
	 * <br>s'il a cliqué sur le {@link FenetrePrendreEnChargeInterventionTech#boutonRetour} il retourne à son menu principal
	 * @param arg0 
	 * @see Technicien#retirerDuParc(Intervention)
	 * @see Technicien#terminerIntervention(Intervention, TypeIntervention)
	 * @see FenetreConfirmation#FenetreConfirmation(metier.Compte, JFrame)
	 * @see MenuPrincipalTech#MenuPrincipalTech(Technicien)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if(arg0.getSource()==boutonValider){
				if (this.getTypeInterventionEntre()==null){
					MsgBox.affMsg("Vous n'avez sélectionné aucun type d'intervention.");
					new FenetrePrendreEnChargeInterventionTech(this.getTechnicien(),this.getIntervention());
				}
				else if(this.getTypeInterventionEntre().getNumero()==TypeIntervention.TYPE_DESTRUCTION){
					Intervention i = this.getTechnicien().retirerDuParc(this.getIntervention());
					DAOVelo.updateVelo(i.getVelo());
					DAOIntervention.updateIntervention(i);
				}
				else {
					Intervention i = this.getTechnicien().terminerIntervention(this.getIntervention(),this.getTypeInterventionEntre());
					DAOVelo.updateVelo(i.getVelo());
					DAOIntervention.updateIntervention(i);
				}
				new FenetreConfirmation(this.getTechnicien().getCompte(),this);
			}
			else if(arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
			}
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		}catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}