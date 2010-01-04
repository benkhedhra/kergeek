package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreChangerMotDePasse;
import ihm.appliAdminTech.FenetreConfirmation;

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

public class MenuPrincipalAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Administrateur admin;
	private JLabel labelAdmin = new JLabel("");
	private JButton boutonDeconnexion = new JButton("D�connexion");
	private JButton boutonComptes = new JButton("G�rer les comptes");
	private JButton boutonStats = new JButton("Demander des statistiques");
	private JButton boutonEtat = new JButton("Voir l'�tat actuel du parc");
	private JButton boutonChangeMdp = new JButton("Changer votre mot de passe");

	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	public MenuPrincipalAdmin(Administrateur a){

		this.setAdministrateur(a);
		
		this.setContentPane(new PanneauAdmin());
		System.out.println("Affichage du menu principal de l'administrateur");
		//D�finit un titre pour notre fen�tre
		this.setTitle("Menu principal de l'administrateur");
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

			
		labelAdmin = new JLabel("Vous �tes connect� en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(UtilitaireIhm.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(800,100));
		labelAdmin.setMaximumSize(new Dimension(110,100));
		boutonDeconnexion.setPreferredSize(new Dimension(250,40));
		boutonDeconnexion.setBackground(Color.MAGENTA);
		boutonDeconnexion.setFont(UtilitaireIhm.POLICE4);
		boutonDeconnexion.addActionListener(this);
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1100,300));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelAdmin);
		north.add(boutonDeconnexion);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(1100,800));
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonComptes.setPreferredSize(new Dimension(300,200));
		boutonComptes.setMaximumSize(new Dimension(300,200));
		boutonComptes.setFont(UtilitaireIhm.POLICE3);
		boutonComptes.addActionListener(this);
		center.add(boutonComptes);
		boutonStats.setPreferredSize(new Dimension(300,200));
		boutonStats.setMaximumSize(new Dimension(300,200));
		boutonStats.setFont(UtilitaireIhm.POLICE3);
		boutonStats.addActionListener(this);
		center.add(boutonStats);
		boutonEtat.setPreferredSize(new Dimension(300,200));
		boutonEtat.setMaximumSize(new Dimension(300,200));
		boutonEtat.setFont(UtilitaireIhm.POLICE3);
		boutonEtat.addActionListener(this);
		center.add(boutonEtat);
		this.getContentPane().add(center, BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1100,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonChangeMdp.setPreferredSize(new Dimension(300,40));
		boutonChangeMdp.setMaximumSize(new Dimension(300,40));
		boutonChangeMdp.setFont(UtilitaireIhm.POLICE3);
		boutonChangeMdp.setForeground(Color.WHITE);
		boutonChangeMdp.setBackground(Color.BLUE);
		boutonChangeMdp.addActionListener(this);
		south.add(boutonChangeMdp);
		this.getContentPane().add(south, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		

		if(arg0.getSource()==boutonComptes){
			new MenuGererComptesAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonStats){
			new MenuDemanderStatsAdmin(this.getAdministrateur());
		}
		else if (arg0.getSource()==boutonEtat){
			new MenuVoirEtatAdmin(this.getAdministrateur());
		}	
		else if (arg0.getSource()==boutonChangeMdp){
			new FenetreChangerMotDePasse(this.getAdministrateur().getCompte());
		}
		else if (arg0.getSource()==boutonDeconnexion){
			try {
				new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
				e.printStackTrace();
			} catch (ConnexionFermeeException e) {
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
	}
}