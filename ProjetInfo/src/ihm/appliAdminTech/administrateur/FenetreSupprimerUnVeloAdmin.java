package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Administrateur;
import metier.Lieu;
import metier.UtilitaireDate;
import metier.Velo;

public class FenetreSupprimerUnVeloAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur a;
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelMsg = new JLabel ("Veuillez entrer l'identifiant du v�lo");
	private JTextField idVeloARemplir = new JTextField ("");
	private JButton boutonValider = new JButton ("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");
	private Velo veloEntre = new Velo();

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

	public FenetreSupprimerUnVeloAdmin(Administrateur a){

		this.setContentPane(new PanneauAdmin());
		System.out.println("Affichage de la fen�tre pour supprimer un v�lo");
		//D�finit un titre pour votre fen�tre
		this.setTitle("Suppression d'un v�lo");
		//D�finit une taille pour celle-ci
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
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
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		labelMsg.setPreferredSize(new Dimension(600,30));
		center.add(labelMsg);
		idVeloARemplir.setFont(FenetreAuthentificationUtil.POLICE3);
		idVeloARemplir.setPreferredSize(new Dimension(150, 30));
		idVeloARemplir.setForeground(Color.BLUE);
		center.add(idVeloARemplir);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.addActionListener(this);
		center.add(boutonValider);
		this.getContentPane().add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel11 = new JPanel();
		panel11.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel11.add(boutonRetour);
		south.add(panel11,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonValider){
			try {
				if(!DAOVelo.existe(idVeloARemplir.getText())){
					MsgBox.affMsg("Le v�lo que vous avez entr� n'existe pas. ");
					new FenetreHistoriqueVeloAdmin(this.getAdministrateur());
				}
				else{
					Velo veloEntre = DAOVelo.getVeloById(idVeloARemplir.getText());
					this.setVeloEntre(veloEntre);
					if(!this.getVeloEntre().getLieu().getId().equals(Lieu.ID_SORTIE)){
						MsgBox.affMsg("<html><center>Le v�lo que vous avez entr� n'est pas en sortie. <br>Il est actuellement au lieu : "+this.getVeloEntre().getLieu().getAdresse()+". </center></html>");
						new FenetreSupprimerUnVeloAdmin(this.getAdministrateur());
					}
					else if(this.getVeloEntre().getEmpruntEnCours().getDateEmprunt().after(UtilitaireDate.retrancheJours(UtilitaireDate.dateCourante(),30))){
						MsgBox.affMsg("<html><center>Le v�lo que vous avez entr� n'est pas sorti depuis plus de 30 jours. <br>C'est la dur�e minimale pour avoir le droit de supprimer d�finitivement un v�lo. </center></html>");
						new FenetreSupprimerUnVeloAdmin(this.getAdministrateur());
					}
					else{
						// le v�lo est dans la nature depuis plus de 30 jours : l'administrateur a la possibilit� de le supprimer
						new FenetreDemandeConfirmationAdmin(this.getAdministrateur(),this);
					}
				}
			} 
			catch (SQLException e) {
				MsgBox.affMsg("SQLException"+e.getMessage());
			} 
			catch (ClassNotFoundException e) {
				MsgBox.affMsg("ClassNotFoundException"+e.getMessage());
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