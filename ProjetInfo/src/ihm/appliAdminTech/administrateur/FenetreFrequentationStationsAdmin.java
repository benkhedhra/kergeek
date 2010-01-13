package ihm.appliAdminTech.administrateur;

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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;

/**
 * FenetreFrequentationStationsAdmin h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>c'est une classe de l'application r�serv�e � un {@link Administrateur}
 * <br>elle intervient lorsqu'un Administrateur a cliqu� sur "Fr�quentation des stations" dans son {@link MenuDemanderStatsAdmin}
 * <br>elle lui demande de s�lectionner une p�riode de temps pour laquelle on tracera le graphique, parmi les 4 p�riodes propos�es
 * @author KerGeek
 */
public class FenetreFrequentationStationsAdmin extends JFrame implements ActionListener {
	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connect� sur la fen�tre
	 */
	private Administrateur administrateur;

	/**
	 * la p�riode de temps s�lectionn�e par l'administrateur
	 */
	private String periodeEntree;

	/**
	 * 3 JLabel permettant d'afficher l'id de l'{@link Administrateur} connect�n l'endroit o� il se trouve dans l'application et le message d'invitation � faire son choix
	 */
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > Demander des statistiques > Fr�quentation des stations");
	private JLabel labelMsg = new JLabel("Veuillez entrer la p�riode sur laquelle vous voulez les statistiques");

	/**
	 * 2JButton permettant � l'administrateur de valider sa p�riode s�lectionn�e ou de retourner au menu principal
	 */
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	//Accesseurs utiles
	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public String getPeriodeEntree() {
		return periodeEntree;
	}

	public void setPeriodeEntree(String periodeEntree) {
		this.periodeEntree = periodeEntree;
	}

	/**
	 * constructeur de {@link FenetreFrequentationStationsAdmin}
	 * @param a
	 * l'administrateur connect� � la fen�tre
	 * @see JComboBox
	 */
	public FenetreFrequentationStationsAdmin(Administrateur a){

		System.out.println("Fen�tre pour avoir la fr�quentation des stations");
		this.setContentPane(new PanneauAdmin());
		//D�finit un titre pour notre fen�tre
		this.setTitle("Fr�quentation des stations");
		//D�finit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(true);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setAdministrateur(a);
		
		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(1100,50));
		labelAdmin.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
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

		final String[] periodes = new String[5];
		periodes[0] = "S�lectionnez une p�riode";
		periodes[1] = "30 derniers jours";
		periodes[2] = "60 derniers jours";
		periodes[3] = "6 derniers mois";
		periodes[4] = "365 derniers jours";
		DefaultComboBoxModel model = new DefaultComboBoxModel(periodes);
		JComboBox periodeARemplir = new JComboBox(model);
		periodeARemplir.setPreferredSize(new Dimension(300,40));
		periodeARemplir.setMaximumSize(new Dimension(300,40));
		periodeARemplir.setFont(UtilitaireIhm.POLICE3);
		periodeARemplir.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ae){
				Object o = ((JComboBox)ae.getSource()).getSelectedItem();
				periodeEntree = (String)o;
				if(periodeEntree.equals(periodes[0])){
					periodeEntree=null;
				}
				repaint();
			}
		});

		JPanel centerWest = new JPanel();
		centerWest.setPreferredSize(new Dimension(900,800));
		centerWest.setBackground(UtilitaireIhm.TRANSPARENCE);
		centerWest.add(periodeARemplir);

		center.add(centerWest,BorderLayout.WEST);

		JPanel centerEast = new JPanel();
		centerEast.setBackground(UtilitaireIhm.TRANSPARENCE);
		centerEast.setPreferredSize(new Dimension(300,800));
		boutonValider.setPreferredSize(new Dimension(250,50));
		boutonValider.setMaximumSize(new Dimension(250,50));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		centerEast.add(boutonValider);
		center.add(centerEast,BorderLayout.EAST);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);
		this.setVisible(true);
	}

	/**
	 * m�thode ex�cut�e quand l'administrateur a soit cliqu� sur "valider", soit cliqu� sur "retour au menu principal"
	 * @param arg0 
	 * @see FenetreAffichageResultatsAdmin#FenetreAffichageResultatsAdmin(Administrateur, JFrame)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonValider){
			try {
				if(this.getPeriodeEntree()!=null){
					new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
				}
				else{
					MsgBox.affMsg("<html> <center>Vous n'avez s�lectionn� aucune p�riode. </center></html>");
					new FenetreFrequentationStationsAdmin(this.getAdministrateur());
				}
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
				new MenuPrincipalAdmin(this.getAdministrateur());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
				new MenuPrincipalAdmin(this.getAdministrateur());
			} catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}

	}
}
