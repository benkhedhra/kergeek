package ihm.appliAdminTech.administrateur;


import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.TextFieldLimite;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Velo;

/**
 * FenetreHistoriqueVeloAdmin h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>c'est une classe de l'application r�serv�e � un {@link Administrateur}
 * <br>elle intervient lorsqu'un Administrateur a cliqu� sur "Historique d'un v�lo" dans son {@link MenuInterventionsMaintenanceAdmin}
 * <br>elle lui demande d'entrer l'identifiant du v�lo sur lequel il souhaite avoir l'historique
 * @author KerGeek
 */
public class FenetreHistoriqueVeloAdmin extends JFrame implements ActionListener {

	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'administrateur connect� sur la fen�tre
	 */
	private Administrateur a;

	/**
	 * le v�lo dont l'identifiant est entr� par l'administrateur
	 */
	private Velo veloEntre = new Velo();

	/**
	 * 3 JLabel permettant d'afficher l'id de l'{@link Administrateur} connect�n l'endroit o� il se trouve dans l'application et le message d'invitation � faire son choix
	 */
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > Demander des statistiques > Maintenance > Historique d'un v�lo");
	private JLabel labelMsg = new JLabel ("Veuillez entrer l'identifiant du v�lo");
	/**
	 * 1 TextFieldLimite pour que l'administrateur entre l'identifiant du v�lo
	 */
	private TextFieldLimite idVeloARemplir = new TextFieldLimite(4,"");
	/**
	 * 2 JButton pour lui permettre soit d'afficher le tableau d'historique des interventions de maintenance pour ce v�lo, soit de retourner au menu principal
	 */
	private JButton boutonAfficher = new JButton ("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	
	//Accesseurs utiles

	public Administrateur getAdministrateur() {
		return a;
	}

	public void setAdministrateur(Administrateur a) {
		this.a = a;
	}
	
	public Velo getVeloEntre() {
		return veloEntre;
	}

	public void setVeloEntre(Velo veloEntre) {
		this.veloEntre = veloEntre;
	}

	/**
	 * constructeur de {@link FenetreHistoriqueVeloAdmin}
	 * @param a
	 * l'administrateur connect� � la fen�tre
	 */
	public FenetreHistoriqueVeloAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Affichage de la fen�tre d'historique d'un v�lo");
		//D�finit un titre pour votre fen�tre
		this.setTitle("Historique d'un v�lo");
		//D�finit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(true);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);

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
		labelMsg.setPreferredSize(new Dimension(1100,30));
		center.add(labelMsg);
		idVeloARemplir.setFont(UtilitaireIhm.POLICE3);
		idVeloARemplir.setPreferredSize(new Dimension(200,40));
		idVeloARemplir.setForeground(Color.BLUE);
		center.add(idVeloARemplir);
		boutonAfficher.setFont(UtilitaireIhm.POLICE3);
		boutonAfficher.setBackground(Color.CYAN);
		boutonAfficher.setFont(UtilitaireIhm.POLICE3);
		boutonAfficher.setPreferredSize(new Dimension(250,50));
		boutonAfficher.addActionListener(this);
		center.add(boutonAfficher);
		this.getContentPane().add(center, BorderLayout.CENTER);

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
	 * m�thode ex�cut�e soit lorsque l'Administrateur a cliqu� sur "Afficher l'historique", soit sur "Retour au menu principal"
	 * @param arg0 
	 * @see DAOVelo#existe(String)
	 * @see FenetreAffichageResultatsAdmin#FenetreAffichageResultatsAdmin(Administrateur, JFrame)
	 * @see MenuPrincipalAdmin#MenuPrincipalAdmin(Administrateur)
	 */

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonAfficher){
			try {
				if(DAOVelo.existe(idVeloARemplir.getText())){
					this.setVeloEntre(DAOVelo.getVeloById(idVeloARemplir.getText()));
					new FenetreAffichageResultatsAdmin(this.getAdministrateur(),this);
				}
				else{
					MsgBox.affMsg("Le v�lo que vous avez entr� n'existe pas. ");
					new FenetreHistoriqueVeloAdmin(this.getAdministrateur());
				}
			} 
			catch (SQLException e) {
				MsgBox.affMsg("SQLException"+e.getMessage());
				new MenuPrincipalAdmin(this.getAdministrateur());
			} 
			catch (ClassNotFoundException e) {
				MsgBox.affMsg("ClassNotFoundException"+e.getMessage());
				new MenuPrincipalAdmin(this.getAdministrateur());
			} 
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if(arg0.getSource()==boutonRetour){
			new MenuPrincipalAdmin(this.getAdministrateur());
		}
	}
}
