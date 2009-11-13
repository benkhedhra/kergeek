package administrateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import appliAdminTech.FenetreAuthentification;

public class MenuGererComptesAdmin extends JFrame implements ActionListener {
	
	private Administrateur admin;
	private JLabel labelAdmin = new JLabel("");
	private JButton boutonRetour = new JButton("Retour au menu principal");
	private JButton boutonCreation = new JButton("Création d'un nouveau compte");
	private JButton boutonInformations = new JButton("Afficher informations sur un compte");

	public Administrateur getAdministrateur() {
		return admin;
	}

	public void setAdministrateur(Administrateur admin) {
		this.admin = admin;
	}

	public MenuGererComptesAdmin(Administrateur a){

		this.setAdministrateur(a);
		System.out.println("Menu gérer comptes de l'administrateur");
		this.setTitle("Menu gérer comptes de l'administrateur");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.getContentPane().setBackground(Color.BLUE);
		this.setLayout(new BorderLayout());

		labelAdmin = new JLabel("Vous êtes connecté en tant que "+ a.getCompte().getId());
		labelAdmin.setFont(FenetreAuthentification.POLICE4);
		
		this.getContentPane().add(labelAdmin,BorderLayout.CENTER);
		
		JPanel center = new JPanel();
		boutonCreation.addActionListener(this);
		boutonCreation.setPreferredSize(new Dimension(50,50));
		center.add(boutonCreation);
		boutonInformations.addActionListener(this);
		boutonInformations.setPreferredSize(new Dimension(50,50));
		center.add(boutonInformations);
		this.getContentPane().add(center,BorderLayout.CENTER);
		
		boutonRetour.addActionListener(this);
		this.getContentPane().add(boutonRetour,BorderLayout.SOUTH);
		
		this.setVisible(true);
	} 
	
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		/*if(arg0.getSource()==boutonRetour){
			MenuPrincipalAdmin m = new MenuPrincipalAdmin();
			m.setVisible(true);
		}
		else if (arg0.getSource()==boutonCreation){
			FenetreCreationCompteAdmin f = new FenetreCreationCompteAdmin();
			f.setVisible(true);
		}
		else if (arg0.getSource()==boutonInformations){
			FenetreRechercherCompteAdmin f = new FenetreRechercherCompteAdmin();
			f.setVisible(true);
		}*/
	}

}
