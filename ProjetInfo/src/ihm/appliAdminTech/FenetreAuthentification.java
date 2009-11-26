package ihm.appliAdminTech;

import gestionBaseDeDonnees.DAOCompte;
import ihm.MsgBox;
import ihm.appliAdminTech.administrateur.MenuPrincipalAdmin;
import ihm.appliAdminTech.technicien.MenuPrincipalTech;
import ihm.appliUtil.FenetreAuthentificationUtil;
import ihm.appliUtil.Panneau;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;

public class FenetreAuthentification extends JFrame implements ActionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// d�finition des polices
	public static final Font POLICE1 = new Font("Arial Narrow", Font.BOLD, 18);
	public static final Font POLICE2 = new Font("Arial Narrow", Font.BOLD, 16);
	public static final Font POLICE3 = new Font("Arial Narrow", Font.PLAIN,16);
	public static final Font POLICE4 = new Font("Arial Narrow", Font.ITALIC,14);

	private JLabel labelInvitation = new JLabel("");
	private JLabel labelId = new JLabel("identifiant");
	private JTextField idARemplir = new JTextField("");
	private JLabel labelMotDePasse = new JLabel("mot de passe");
	private JPasswordField motDePasseARemplir = new JPasswordField();
	private JButton boutonValider = new JButton ("Valider");

	public FenetreAuthentification (Boolean erreurAuthent){

		System.out.println("Ouverture d'une fen�tre d'authentification de l'appli admin tech");

		this.setContentPane(new Panneau());
		this.setTitle("Authentification");
		this.setSize(700,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		if(erreurAuthent){
			labelInvitation.setText("Combinaison identifiant/mot de passe incorrecte. Veuillez � nouveau vous authentifier");
			labelInvitation.setForeground(Color.RED);
			labelInvitation.setPreferredSize(new Dimension(650,40));
		}
		else{
			labelInvitation.setText("Bienvenue ! Veuillez vous authentifier");		
			labelInvitation.setPreferredSize(new Dimension(400,40));
		}
		labelInvitation.setFont(POLICE2);
		north.add(labelInvitation);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		center.setLayout(new GridLayout(3,2));

		JPanel panel1 = new JPanel();
		panel1.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelId.setFont(POLICE3);
		labelId.setPreferredSize(new Dimension(150,30));
		labelId.setMaximumSize(new Dimension(150,30));
		panel1.add(labelId);
		center.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		idARemplir.setFont(POLICE3);
		idARemplir.setPreferredSize(new Dimension(150, 30));
		idARemplir.setMaximumSize(new Dimension(150, 30));
		idARemplir.setForeground(Color.BLUE);
		panel2.add(idARemplir);
		center.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		labelMotDePasse.setFont(POLICE3);
		labelMotDePasse.setPreferredSize(new Dimension(150,30));
		labelMotDePasse.setMaximumSize(new Dimension(150,30));
		panel3.add(labelMotDePasse);
		center.add(panel3);

		JPanel panel4 = new JPanel();
		panel4.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		motDePasseARemplir.setFont(POLICE3);
		motDePasseARemplir.setPreferredSize(new Dimension(150, 30));
		motDePasseARemplir.setMaximumSize(new Dimension(150, 30));
		panel4.add(motDePasseARemplir);
		center.add(panel4);

		JPanel panel5 = new JPanel();
		panel5.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);	
		center.add(panel5);

		JPanel panel6 = new JPanel();
		panel6.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);		
		boutonValider.setPreferredSize(new Dimension(150,30));
		boutonValider.setMaximumSize(new Dimension(150,30));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(POLICE3);
		//On ajoute notre Fenetre � la liste des auditeurs de notre Bouton
		boutonValider.addActionListener(this);
		panel6.add(boutonValider);
		center.add(panel6);

		this.getContentPane().add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public Administrateur getAdministrateur() throws SQLException, ClassNotFoundException {
		return gestionBaseDeDonnees.DAOAdministrateur.getAdministrateurById(idARemplir.getText());
	}

	public Technicien getTechnicien() throws SQLException, ClassNotFoundException {
		return gestionBaseDeDonnees.DAOTechnicien.getTechnicienById(idARemplir.getText());
	}

	public int testerAuthent (String id,String motDePasse) throws SQLException, ClassNotFoundException{
		//cette m�thode rend -1 si la combinaison id/mdp est erron�e, ou le type du compte si la combinaison est correcte
		int resul=-1;
		if(DAOCompte.estDansLaBdd(id)){
			Compte compte = DAOCompte.getCompteById(id);
			System.out.println("id = "+compte.getId()+ " et mdp = "+compte.getMotDePasse());
			if(motDePasse.equals(compte.getMotDePasse())){
				resul=compte.getType();
			}
		}
		return resul;
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();

		String id = idARemplir.getText();
		String mdp = motDePasseARemplir.getText();
		System.out.println("id renseign� = "+id + "\nmot de passe renseign� = "+mdp);

		try {
			Compte c = DAOCompte.getCompteById(id);
			gestionBaseDeDonnees.DAOAdministrateur.getAdministrateurById(idARemplir.getText());
			System.out.println("id renseign� = "+id + "\n mot de passe renseign� = "+mdp);
			int resultatAuthent = testerAuthent(id,mdp);
			System.out.println("resultatAuthent = "+resultatAuthent);

			//si aucune exception lev�e et si l'utilisateur existe bien dans la base, on ferme la fenetre
			//d'authentification et on ouvre la fenetre de l'utilisateur

			if (resultatAuthent==Compte.TYPE_ADMINISTRATEUR){
				new MenuPrincipalAdmin(this.getAdministrateur());
			}
			else if (resultatAuthent==Compte.TYPE_TECHNICIEN){
				new MenuPrincipalTech(this.getTechnicien());
			}
			else {
				new FenetreAuthentification(true);
			}
		}
		catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
	}

	public static void main (String [] args){
		new FenetreAuthentification(false);
	}
}