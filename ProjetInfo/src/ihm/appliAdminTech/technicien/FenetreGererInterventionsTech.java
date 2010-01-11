package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAOIntervention;
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

/**
 * la classe {@link FenetreGererInterventionsTech} h�rite de {@link JFrame} et impl�mente l'interface {@link ActionListener}
 * <br>cette fen�tre appara�t lorsque le technicien a cliqu� sur le bouton "Voir les interventions" dans le {@link MenuPrincipalTech}
 * <br>lui sont pr�sent�es sous forme de menu d�roulant toutes les {@link Intervention} non prises en charge
 * <br>les v�los concern�s par ces {@link Intervention} ont d�j� �t� retir�s des stations et sont donc pr�sents au garage
 * <br>cette fen�tre est propre au volet Technicien de l'application AdminTech
 * @author KerGeek
 *
 */
public class FenetreGererInterventionsTech extends JFrame implements ActionListener {

	/*
	 * liste des attributs de la fen�tre
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > G�rer les interventions");
	private JLabel labelMsg = new JLabel("Demandes d'intervention");
	private Intervention interventionEntree;
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	// Accesseurs utiles

	/**
	 * @return	le {@link FenetreGererInterventionsTech#technicien} de la {@link FenetreGererInterventionsTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise le {@link FenetreGererInterventionsTech#technicien} de la {@link FenetreGererInterventionsTech}
	 * @param tech
	 * le technicien connect� sur cette fen�tre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	public Intervention getInterventionEntree() {
		return interventionEntree;
	}

	public void setInterventionEntree(Intervention interventionEntree) {
		this.interventionEntree = interventionEntree;
	}

	/**
	 * constructeur de {@link FenetreGererInterventionsTech}
	 * @param t
	 * le technicien connect� sur la {@link FenetreGererInterventionsTech}
	 * @throws ConnexionFermeeException
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JComboBox
	 */
	public FenetreGererInterventionsTech (Technicien t) throws ConnexionFermeeException{
		System.out.println("Fen�tre pour voir toutes les interventions non trait�es");
		this.setContentPane(new PanneauTech());
		//D�finit un titre pour notre fen�tre
		this.setTitle("G�rer les interventions non trait�es");
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
		center.setPreferredSize(new Dimension(1200,800));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		
		labelMsg.setPreferredSize(new Dimension(1100,100));

		List<Intervention> listeInterventions;
		try{
			listeInterventions= DAOIntervention.getInterventionsNonTraitees();

			if(listeInterventions.size()==0){
				labelMsg.setText("Il n'y a actuellement aucune intervention en cours � g�rer.");
				center.add(labelMsg);
			}
			else{
				labelMsg.setText("Veuillez s�lectionner une des interventions suivantes");
				center.add(labelMsg);
				final String [] tableauInterventions = new String[listeInterventions.size()+1];
				tableauInterventions[0]=listeInterventions.size()+" intervention(s) trouv�e(s)";
				for (int i=0;i<listeInterventions.size();i++){
					Intervention interventioni = listeInterventions.get(i);
					tableauInterventions[i+1] = interventioni.toString();
				}

				DefaultComboBoxModel model = new DefaultComboBoxModel(tableauInterventions);

				JComboBox combo = new JComboBox(model);
				combo.setFont(UtilitaireIhm.POLICE3);
				combo.setPreferredSize(new Dimension(400,50));
				combo.setMinimumSize(new Dimension(400,50));
				combo.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						Object o = ((JComboBox)ae.getSource()).getSelectedItem();
						try {
							String chaineSelectionnee = (String)(o);
							if(chaineSelectionnee==null || chaineSelectionnee.equals(tableauInterventions[0])){
								interventionEntree = null;
							}
							String idInterventionEntre="";
							int i=13;
							while(chaineSelectionnee.charAt(i)!=' '){
								idInterventionEntre=idInterventionEntre+chaineSelectionnee.charAt(i);
								i++;
							}
							System.out.println("id de l'intervention entr�e : "+idInterventionEntre);
							interventionEntree = DAOIntervention.getInterventionById(idInterventionEntre);
							repaint();
						} catch (SQLException e) {
							MsgBox.affMsg(e.getMessage());
						} catch (ClassNotFoundException e) {
							MsgBox.affMsg(e.getMessage());
						}
						catch (ConnexionFermeeException e){
							MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
							new FenetreAuthentification(false);
						}
					}
				});
				center.add(combo);

				boutonValider.setFont(UtilitaireIhm.POLICE3);
				boutonValider.setPreferredSize(new Dimension(250,50));
				boutonValider.setMaximumSize(new Dimension(250,50));
				boutonValider.setBackground(Color.CYAN);
				boutonValider.addActionListener(this);
				center.add(boutonValider);

			}
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
		} catch (SQLException e) {
			MsgBox.affMsg("SQLException : "+e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg("ClassNotFoundException : "+e.getMessage());
		}
	}

	/**
	 * cette m�thode est ex�cut�e si le {@link Technicien} a cliqu� sur l'un des boutons qui lui �taient propos�s
	 * <br>s'il a cliqu� sur le {@link FenetreGererInterventionsTech#boutonValider} une fen�tre s'ouvre d�taillant l'intervention s�lectionn�e
	 * <br>s'il a cliqu� sur le {@link FenetreGererInterventionsTech#boutonRetour} il retourne � son menu principal
	 * @see FenetreGererUneInterventionTech#FenetreGererUneInterventionTech(Technicien, Intervention)
	 * @see MenuPrincipalTech#MenuPrincipalTech(Technicien)
	 * @param arg0
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if (arg0.getSource()==boutonValider){
				if(this.getInterventionEntree()==null){
					MsgBox.affMsg("Vous n'avez s�lectionn� aucune intervention");
					new FenetreGererInterventionsTech(this.getTechnicien());
				}
				else{
					new FenetreGererUneInterventionTech(this.getTechnicien(),this.getInterventionEntree());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
			}
		} catch (ConnexionFermeeException e) {
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}