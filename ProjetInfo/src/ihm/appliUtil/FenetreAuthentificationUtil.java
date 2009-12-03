package ihm.appliUtil;

import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOUtilisateur;
import ihm.MsgBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Utilisateur;
import metier.UtilitaireDate;

public class FenetreAuthentificationUtil extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// d�finition des polices
	public static final Font POLICE1 = new Font("Arial Narrow", Font.BOLD, 24);
	public static final Font POLICE2 = new Font("Arial Narrow", Font.BOLD, 20);
	public static final Font POLICE3 = new Font("Arial Narrow", Font.PLAIN,16);
	public static final Font POLICE4 = new Font("Arial Narrow", Font.ITALIC,16);

	public static final Color TRANSPARENCE = new Color(0,0,0,0);

	private JLabel labelBienvenue = new JLabel("");
	private JLabel labelInvitation = new JLabel("");
	private JTextField idARemplir = new JTextField("");
	private JButton boutonValider = new JButton ("Valider");

	public FenetreAuthentificationUtil(Boolean erreurAuthent){

		System.out.println("Ouverture d'une fen�tre d'authentification de l'utilisateur");

		this.setContentPane(new Panneau());
		this.setTitle("Authentification");
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		labelBienvenue.setPreferredSize(new Dimension(200,50));		
		labelBienvenue.setMaximumSize(new Dimension(500,50));
		labelBienvenue.setText("   Bienvenue ! ");
		labelBienvenue.setFont(POLICE1);
		this.getContentPane().add(labelBienvenue,BorderLayout.NORTH);

		if(erreurAuthent){
			labelInvitation.setText("Identifiant inconnu. Veuillez � nouveau entrer votre identifiant");
			labelInvitation.setForeground(Color.RED);
			labelInvitation.setPreferredSize(new Dimension(480,300));
			labelInvitation.setMaximumSize(new Dimension(480,300));
		}
		else{
			labelInvitation.setText("Veuillez entrer votre identifiant");
			labelInvitation.setPreferredSize(new Dimension(300,300));
			labelInvitation.setMaximumSize(new Dimension(300,300));
		}

		JPanel center = new JPanel();

		center.setBackground(TRANSPARENCE);
		center.setPreferredSize(new Dimension());
		labelInvitation.setFont(POLICE2);
		center.add(labelInvitation);
		idARemplir.setFont(POLICE3);
		idARemplir.setPreferredSize(new Dimension(150, 30));
		idARemplir.setForeground(Color.BLUE);
		center.add(idARemplir);
		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setBackground(TRANSPARENCE);
		south.setPreferredSize(new Dimension(700,150));
		boutonValider.setPreferredSize(new Dimension(100,30));
		boutonValider.setMaximumSize(new Dimension(100,30));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(POLICE3);
		boutonValider.addActionListener(this);
		south.add(boutonValider);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);

	}

	public Utilisateur getUtilisateur() throws SQLException, ClassNotFoundException {
		return gestionBaseDeDonnees.DAOUtilisateur.getUtilisateurById(idARemplir.getText());
	}

	public boolean testerIdent (String idUtilisateur) throws SQLException, ClassNotFoundException{
		boolean resul;
		if(DAOCompte.estDansLaBdd(idUtilisateur)){resul=true;}
		else {resul=false;}
		return resul;
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();

		String id = idARemplir.getText();
		System.out.println("id renseign� = "+id);
		try {
			if (testerIdent(id)){
				Utilisateur u;
				u = DAOUtilisateur.getUtilisateurById(id);
				Date dateDernierRetour = DAOUtilisateur.getDerniereDateRetour(u);
				System.out.println(u.isBloque());
				if(u.isBloque()){
					if(dateDernierRetour.before(UtilitaireDate.retrancheJours(UtilitaireDate.dateCourante(),7))){
						u.setBloque(false);
					}
				}
				if(!u.isBloque()){
					new MenuUtilisateur(u);
				}
				else{
					new FenetreConfirmationUtil("Votre compte est bloqu� jusqu'au "+UtilitaireDate.ajouteJours(dateDernierRetour,7).getDate()+" � "+dateDernierRetour.getTime());
				}
				
			}
			else{
				new FenetreAuthentificationUtil(true);
			}
		}catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
	}
}