package ihm.appliAdminTech.administrateur;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.DemandeAssignation;

public class FenetreExisteDejaDemandeAssignationAdmin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Administrateur administrateur;
	private DemandeAssignation ancienneDemande;
	private DemandeAssignation nouvelleDemande;
	private JLabel labelAdmin = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("NON (revenir à l'écran précédent)");

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public DemandeAssignation getAncienneDemande() {
		return ancienneDemande;
	}

	public void setAncienneDemande(DemandeAssignation ancienneDemande) {
		this.ancienneDemande = ancienneDemande;
	}

	public DemandeAssignation getNouvelleDemande() {
		return nouvelleDemande;
	}

	public void setNouvelleDemande(DemandeAssignation nouvelleDemande) {
		this.nouvelleDemande = nouvelleDemande;
	}

	public FenetreExisteDejaDemandeAssignationAdmin(Administrateur a, DemandeAssignation ancienneDemande, DemandeAssignation nouvelleDemande){

		this.setContentPane(new PanneauAdmin());

		this.setAdministrateur(a);
		this.setAncienneDemande(ancienneDemande);
		this.setNouvelleDemande(nouvelleDemande);

		this.setTitle("Demande d'assignation déjà existante pour cette station");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdmin.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelAdmin);
		this.add(north, BorderLayout.NORTH);

		labelMsg.setText("<html>Une demande d'assignation a déjà été envoyée et non traitée pour la station "+ancienneDemande.getLieu().getAdresse()+". <br> Le nombre de vélos désiré pour cette station est de : "+ancienneDemande.getNombreVelosVoulusDansLieu()+"<br>Souhaitez-vous remplacer cette demande par la vôtre ? <html>");
		labelMsg.setPreferredSize(new Dimension(550,30));
		boutonOui.setPreferredSize(new Dimension(200,50));
		boutonOui.setBackground(Color.CYAN);
		boutonNon.setPreferredSize(new Dimension(200,50));
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
		try {
			if(arg0.getSource()==boutonOui){
				this.getAncienneDemande().setDate(nouvelleDemande.getDate());
				this.getAncienneDemande().setNombreVelosVoulusDansLieu(nouvelleDemande.getNombreVelosVoulusDansLieu());
				if(DAODemandeAssignation.updateDemandeAssignation(this.getAncienneDemande())){
					new FenetreConfirmation(this.getAdministrateur().getCompte(),this);
				}
			}
			else if (arg0.getSource()==boutonNon){
				new MenuVoirEtatAdmin(this.getAdministrateur());
			} 
		}
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le système rencontre actuellement un problème technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur réseau et réessayer ultérieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg("ClassNotFoundException : "+e.getMessage());
		} catch (SQLException e) {
			MsgBox.affMsg("SQLException : "+e.getMessage());

		}
	}
}