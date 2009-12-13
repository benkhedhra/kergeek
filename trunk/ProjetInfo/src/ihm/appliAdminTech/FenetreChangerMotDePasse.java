package ihm.appliAdminTech;

import gestionBaseDeDonnees.DAOAdministrateur;
import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOTechnicien;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.administrateur.MenuPrincipalAdmin;
import ihm.appliAdminTech.administrateur.PanneauAdmin;
import ihm.appliAdminTech.technicien.MenuPrincipalTech;
import ihm.appliAdminTech.technicien.PanneauTech;
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
import javax.swing.JPasswordField;

import metier.Compte;

public class FenetreChangerMotDePasse extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Compte compte;
	private JLabel labelAdminTech = new JLabel("");
	private JLabel labelMsg = new JLabel("Veuillez compléter les champs suivants");
	private JLabel labelAncienMdp = new JLabel("ancien mot de passe");
	private JPasswordField ancienMdpARemplir = new JPasswordField("");
	private JLabel labelNouveauMdp1 = new JLabel("nouveau mot de passe");
	private JPasswordField nouveauMdpARemplir1 = new JPasswordField("");
	private JLabel labelNouveauMdp2 = new JLabel("confirmation du nouveau mot de passe");
	private JPasswordField nouveauMdpARemplir2 = new JPasswordField("");
	private JButton boutonValider = new JButton("Valider");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public FenetreChangerMotDePasse(Compte c){

		if(c.getType()==Compte.TYPE_ADMINISTRATEUR){
			this.setContentPane(new PanneauAdmin());
		}
		else if(c.getType()==Compte.TYPE_TECHNICIEN){
			this.setContentPane(new PanneauTech());
		}

		this.setCompte(c);

		this.setTitle("Changer son mot de passe");
		this.setSize(700,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,600));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		labelAdminTech = new JLabel("Vous êtes connecté en tant que "+ this.getCompte().getId());
		labelAdminTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelAdminTech.setPreferredSize(new Dimension(500,30));
		north.add(labelAdminTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		labelMsg.setPreferredSize(new Dimension(600,30));
		labelMsg.setFont(FenetreAuthentificationUtil.POLICE2);
		center.add(labelMsg);

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelAncienMdp.setPreferredSize(new Dimension(250,30));
		labelAncienMdp.setMinimumSize(new Dimension(250,30));
		panel1.add(labelAncienMdp);
		center.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		ancienMdpARemplir.setPreferredSize(new Dimension(250,30));
		ancienMdpARemplir.setMinimumSize(new Dimension(250,30));
		panel2.add(ancienMdpARemplir);
		center.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNouveauMdp1.setPreferredSize(new Dimension(250,30));
		labelNouveauMdp1.setMinimumSize(new Dimension(250,30));
		panel3.add(labelNouveauMdp1);
		center.add(panel3);

		JPanel panel4 = new JPanel();
		panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		nouveauMdpARemplir1.setPreferredSize(new Dimension(250,30));
		nouveauMdpARemplir1.setMinimumSize(new Dimension(250,30));
		panel4.add(nouveauMdpARemplir1);
		center.add(panel4);

		JPanel panel5 = new JPanel();
		panel5.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelNouveauMdp2.setPreferredSize(new Dimension(250,30));
		labelNouveauMdp2.setMinimumSize(new Dimension(250,30));
		panel5.add(labelNouveauMdp2);
		center.add(panel5);

		JPanel panel6 = new JPanel();
		panel6.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		nouveauMdpARemplir2.setPreferredSize(new Dimension(250,30));
		nouveauMdpARemplir2.setMinimumSize(new Dimension(250,30));
		panel6.add(nouveauMdpARemplir2);
		center.add(panel6);

		boutonValider.setPreferredSize(new Dimension(170,30));
		boutonValider.setMinimumSize(new Dimension(170,30));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.addActionListener(this);
		center.add(boutonValider);

		this.getContentPane().add(center,BorderLayout.CENTER);


		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,40));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		south.add(boutonRetour);
		boutonRetour.addActionListener(this);

		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try{
			if(arg0.getSource()==boutonValider){
				if (UtilitaireIhm.verifieChampsModifMdp(this.getCompte(), ancienMdpARemplir.getText(), nouveauMdpARemplir1.getText(), nouveauMdpARemplir2.getText())){
					this.getCompte().setMotDePasse(nouveauMdpARemplir1.getText());
					DAOCompte.updateCompte(this.getCompte());
					new FenetreConfirmation(this.getCompte(),this);
					//à voir s'il ne faut pas passer par updateAdmin et updateTech (mais normalement non)
				}
				else{
					MsgBox.affMsg("L'un des champs entrés au moins est incorrect");
					new FenetreChangerMotDePasse(this.getCompte());
				}
			}
			else if (arg0.getSource()==boutonRetour){
				if(this.getCompte().getType()==Compte.TYPE_ADMINISTRATEUR){
					new MenuPrincipalAdmin(DAOAdministrateur.getAdministrateurById(compte.getId()));
				}
				else{
					new MenuPrincipalTech(DAOTechnicien.getTechnicienById(compte.getId()));
				}
			}
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
}