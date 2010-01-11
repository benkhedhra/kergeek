package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Intervention;
import metier.Technicien;

/**
 * la classe {@link FenetreGererUneInterventionTech} h�rite de {@link JFrame} et impl�mente l'interface {@link ActionListener}
 * <br>cette fen�tre appara�t lorsque le technicien a s�lectionn� une {@link Intervention} dans la {@link FenetreGererInterventionsTech}
 * <br>elle offre au {@link Technicien} la possibilit� de prendre en charge cette {@link Intervention}, lorsque le v�lo est � nouveau en �tat de marche
 * <br>cette fen�tre est propre au volet Technicien de l'application AdminTech
 * @author KerGeek
 *
 */
public class FenetreGererUneInterventionTech extends JFrame implements ActionListener {

	/*
	 * attributs priv�s de la fen�tre 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private Intervention intervention;
	private JLabel labelTech = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > G�rer les interventions > G�rer une intervention");
	private JLabel labelMsg = new JLabel("Intervention � traiter");
	private JLabel labelId = new JLabel("intervention n�");
	private JLabel labelIdInter = new JLabel("");
	private JLabel labelVelo = new JLabel("V�lo concern�");
	private JLabel labelVeloInter = new JLabel("");
	private JLabel labelDate = new JLabel("Date de la mise en garage");
	private JLabel labelDateInter = new JLabel("");
	private JButton boutonPrendreEnCharge = new JButton("Terminer cette intervention");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	// Accesseurs utiles

	/**
	 * @return	le {@link FenetreGererUneInterventionTech#technicien} de la {@link FenetreGererUneInterventionTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise le {@link FenetreGererUneInterventionTech#technicien} de la {@link FenetreGererUneInterventionTech}
	 * @param tech
	 * le technicien connect� sur cette fen�tre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	/**
	 * @return	l' {@link FenetreGererUneInterventionTech#intervention} de la {@link FenetreGererUneInterventionTech}
	 */
	public Intervention getIntervention() {
		return intervention;
	}
	/**
	 * Initialise la {@link FenetreGererUneInterventionTech#intervention} de la {@link FenetreGererUneInterventionTech}
	 * @param intervention
	 * l'intervention s�lectionn�e � la fen�tre pr�c�dente
	 * @see Intervention
	 */
	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	/**
	 * constructeur de la {@link FenetreGererUneInterventionTech}
	 * @param t
	 * le technicien connect� sur la fen�tre
	 * @param i
	 * l'intervention s�lectionn�e � la fen�tre pr�c�dente
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */

	public FenetreGererUneInterventionTech(Technicien t,Intervention i){

		System.out.println("Fen�tre pour g�rer une intervention");
		this.setContentPane(new PanneauTech());
		//D�finit un titre pour notre fen�tre
		this.setTitle("G�rer une intervention");
		//D�finit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
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
		this.setIntervention(i);

		labelTech = new JLabel("Vous �tes connect� en tant que "+ t.getCompte().getId());
		labelTech.setFont(UtilitaireIhm.POLICE4);
		labelTech.setPreferredSize(new Dimension(1100,50));
		labelTech.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(1200,800));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelMsg.setFont(UtilitaireIhm.POLICE2);
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerCenter = new JPanel();
		centerCenter.setPreferredSize(new Dimension(1000,800));
		centerCenter.setBackground(UtilitaireIhm.TRANSPARENCE);

		JPanel panel1 = new JPanel();
		panel1.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelId.setPreferredSize(new Dimension(500,40));
		labelId.setMaximumSize(new Dimension(500,40));
		panel1.add(labelId);
		centerCenter.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelIdInter.setText(i.getId());
		labelIdInter.setPreferredSize(new Dimension(350,40));
		labelIdInter.setMaximumSize(new Dimension(350,40));
		panel2.add(labelIdInter);
		centerCenter.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelVelo.setPreferredSize(new Dimension(500,40));
		labelVelo.setMaximumSize(new Dimension(500,40));
		panel3.add(labelVelo);
		centerCenter.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelVeloInter.setText(i.getVelo().getId());
		labelVeloInter.setPreferredSize(new Dimension(350,40));
		labelVeloInter.setMaximumSize(new Dimension(350,40));
		panel4.add(labelVeloInter);
		centerCenter.add(panel4);	

		JPanel panel5 = new JPanel();
		panel5.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelDate.setPreferredSize(new Dimension(500,40));
		labelDate.setMaximumSize(new Dimension(500,40));
		panel5.add(labelDate);
		centerCenter.add(panel5);	

		JPanel panel6 = new JPanel();
		panel6.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelDateInter.setText(i.getDate().toString());
		labelDateInter.setPreferredSize(new Dimension(350,40));
		labelDateInter.setMaximumSize(new Dimension(350,40));
		panel6.add(labelDateInter);
		centerCenter.add(panel6);	

		center.add(centerCenter,BorderLayout.CENTER);

		boutonPrendreEnCharge.setPreferredSize(new Dimension(350,40));
		boutonPrendreEnCharge.setMaximumSize(new Dimension(350,40));
		boutonPrendreEnCharge.setBackground(Color.CYAN);
		boutonPrendreEnCharge.setFont(UtilitaireIhm.POLICE3);
		boutonPrendreEnCharge.addActionListener(this);
		centerCenter.add(boutonPrendreEnCharge);
		center.add(centerCenter,BorderLayout.EAST);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel.add(boutonRetour);
		south.add(panel,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}
	/**
	 * cette m�thode est ex�cut�e lorsque le {@link Technicien} a cliqu� sur l'un des deux boutons qui se pr�sentaient � lui
	 * <br>s'il a cliqu� sur {@link FenetreGererUneInterventionTech#boutonPrendreEnCharge} une nouvelle fen�tre appara�t lui demandant de renseigner le type d'intervention effectu�
	 * <br>s'il a cliqu� sur le {@link FenetreGererUneInterventionTech#boutonRetour} il retourne � son menu principal
	 * @see FenetrePrendreEnChargeInterventionTech#FenetrePrendreEnChargeInterventionTech(Technicien, Intervention)
	 * @see MenuPrincipalTech#MenuPrincipalTech(Technicien)
	 * @param arg0
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if(arg0.getSource()==boutonPrendreEnCharge){
				//le technicien ne doit cliquer sur le bouton "prendre en charge" que s'il a DEJA r�alis� l'op�ration physique sur le v�lo, et qu'il souhaite le re-d�clarer en �tat de marche (et donc le rendre assignable)
				new FenetrePrendreEnChargeInterventionTech(this.getTechnicien(),this.getIntervention());
			}
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
			}
		} catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}		
}
