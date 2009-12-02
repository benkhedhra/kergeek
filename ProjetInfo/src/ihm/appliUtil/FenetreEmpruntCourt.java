package ihm.appliUtil;

import gestionBaseDeDonnees.DAODemandeIntervention;
import gestionBaseDeDonnees.DAOVelo;
import ihm.MsgBox;

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

import metier.DemandeIntervention;
import metier.Utilisateur;
import metier.Velo;

public class FenetreEmpruntCourt extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur;
	private Velo velo;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("NON (D�connexion)");

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public Velo getVelo() {
		return velo;
	}

	public void setVelo(Velo velo) {
		this.velo = velo;
	}

	public FenetreEmpruntCourt (Utilisateur u,Velo v){

		this.setContentPane(new Panneau());
		System.out.println("Le temps d'emprunt a �t� tr�s court");
		//D�finit un titre pour notre fen�tre
		this.setTitle("Temps d'emprunt < 2 minutes");
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
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);

		this.setUtilisateur(u);
		this.setVelo(v);
		
		labelUtil = new JLabel("Vous �tes connect� en tant que "+ u.getPrenom()+" "+u.getNom());
		labelUtil.setFont(FenetreAuthentificationUtil.POLICE4);
		labelUtil.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelUtil);
		this.getContentPane().add(north,BorderLayout.NORTH);

		labelMsg.setText("Vous avez emprunt� ce v�lo pendant un laps de temps tr�s court. Souhaitez-vous signaler un d�faut sur celui-ci ? ");
		labelMsg.setPreferredSize(new Dimension(650,30));
		boutonOui.setPreferredSize(new Dimension(150,50));
		boutonOui.setBackground(Color.CYAN);
		boutonNon.setPreferredSize(new Dimension(150,50));
		boutonNon.setBackground(Color.CYAN);

		boutonOui.addActionListener(this);
		boutonNon.addActionListener(this);
		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.add(labelMsg);
		center.add(boutonOui);
		center.add(boutonNon);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonOui){
			this.getVelo().setEnPanne(true);
			DemandeIntervention demande = new DemandeIntervention(this.getUtilisateur(),this.getUtilisateur().getEmpruntEnCours().getVelo());
			try {
				DAODemandeIntervention.createDemandeIntervention(demande);
				DAOVelo.updateVelo(this.getVelo());
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			}
			new FenetreDefautDeclare(this.getUtilisateur());
		}
		else if (arg0.getSource()==boutonNon){
			new FenetreConfirmationUtil("Au revoir et � bient�t ! ");
		}
	}
}