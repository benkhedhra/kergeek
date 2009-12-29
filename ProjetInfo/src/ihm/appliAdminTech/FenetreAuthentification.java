package ihm.appliAdminTech;

import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.PasswordFieldLimite;
import ihm.TextFieldLimite;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.administrateur.MenuPrincipalAdmin;
import ihm.appliAdminTech.technicien.MenuPrincipalTech;
import ihm.appliUtil.PanneauUtil;
import ihm.exceptionsInterface.MotDePasseNonRempliException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Administrateur;
import metier.Compte;
import metier.Technicien;

/**
 * FenetreAuthentification h�rite de JFrame et impl�mente ActionListener
 * cette fen�tre permet � un administrateur ou � un technicien de s'authentifier en entrant son identifiant et son mot de passe
 * cette fen�tre est commune � l'administrateur et au technicien
 * @see MenuPrincipalAdmin
 * @see MenuPrincipalTech
 * @author KerGeek
 */

public class FenetreAuthentification extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * liste des attributs priv�s : composants de la fen�tre
	 */
	private JLabel labelInvitation = new JLabel("");
	private JLabel labelId = new JLabel("identifiant");
	private TextFieldLimite idARemplir = new TextFieldLimite(4,"");
	private JLabel labelMotDePasse = new JLabel("mot de passe");
	private PasswordFieldLimite motDePasseARemplir = new PasswordFieldLimite(20,"");
	private JButton boutonValider = new JButton ("Valider");

	/**
	 * constructeur de FenetreAuthentification
	 * @param erreurAuthent : vaut true si l'individu a d�j� essay� de s'identifier pr�c�demment sans succ�s
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see TextFieldLimite
	 * @see PasswordFieldLimite
	 * @see JButton
	 */
	public FenetreAuthentification (Boolean erreurAuthent){

		System.out.println("Ouverture d'une fen�tre d'authentification de l'appli admin tech");

		this.setContentPane(new PanneauUtil());
		this.setTitle("Authentification");
		Dimension d=getToolkit().getScreenSize();
		this.setPreferredSize(d);
		this.setSize(d);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);

		if(erreurAuthent){
			labelInvitation.setText("Combinaison identifiant/mot de passe incorrecte. Veuillez � nouveau vous authentifier");
			labelInvitation.setForeground(Color.RED);
			labelInvitation.setPreferredSize(new Dimension(650,40));
		}
		else{
			labelInvitation.setText("Bienvenue ! Veuillez vous authentifier");		
			labelInvitation.setPreferredSize(new Dimension(400,40));
		}
		labelInvitation.setFont(UtilitaireIhm.POLICE2);
		north.add(labelInvitation);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setLayout(new GridLayout(3,2));

		JPanel panel1 = new JPanel();
		panel1.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelId.setFont(UtilitaireIhm.POLICE3);
		labelId.setPreferredSize(new Dimension(150,30));
		labelId.setMaximumSize(new Dimension(150,30));
		panel1.add(labelId);
		center.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(UtilitaireIhm.TRANSPARENCE);	
		idARemplir.setFont(UtilitaireIhm.POLICE3);
		idARemplir.setPreferredSize(new Dimension(150, 30));
		idARemplir.setMaximumSize(new Dimension(150, 30));
		idARemplir.setForeground(Color.BLUE);
		panel2.add(idARemplir);
		center.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelMotDePasse.setFont(UtilitaireIhm.POLICE3);
		labelMotDePasse.setPreferredSize(new Dimension(150,30));
		labelMotDePasse.setMaximumSize(new Dimension(150,30));
		panel3.add(labelMotDePasse);
		center.add(panel3);

		JPanel panel4 = new JPanel();
		panel4.setBackground(UtilitaireIhm.TRANSPARENCE);	
		motDePasseARemplir.setFont(UtilitaireIhm.POLICE3);
		motDePasseARemplir.setPreferredSize(new Dimension(150, 30));
		motDePasseARemplir.setMaximumSize(new Dimension(150, 30));
		panel4.add(motDePasseARemplir);
		center.add(panel4);

		JPanel panel5 = new JPanel();
		panel5.setBackground(UtilitaireIhm.TRANSPARENCE);	
		center.add(panel5);

		JPanel panel6 = new JPanel();
		panel6.setBackground(UtilitaireIhm.TRANSPARENCE);		
		boutonValider.setPreferredSize(new Dimension(150,30));
		boutonValider.setMaximumSize(new Dimension(150,30));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		//On ajoute notre Fenetre � la liste des auditeurs de notre Bouton
		boutonValider.addActionListener(this);
		panel6.add(boutonValider);
		center.add(panel6);

		this.getContentPane().add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}
	
	/**
	 * m�thode utile si l'individu qui s'est authentifi� est un administrateur
	 * @return l'administrateur une fois qu'il est correctement authentifi�
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */

	public Administrateur getAdministrateur() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		return gestionBaseDeDonnees.DAOAdministrateur.getAdministrateurById(idARemplir.getText());
	}

	/**
	 * m�thode utile si l'individu qui s'est authentifi� est un technicien
	 * @return le technicien une fois qu'il est correctement authentifi�
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public Technicien getTechnicien() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		return gestionBaseDeDonnees.DAOTechnicien.getTechnicienById(idARemplir.getText());
	}

	/**
	 * m�thode permettant de tester les param�tres d'authentification entr�s par l'individu qui cherche � se connecter
	 * @param id : l'identifiant entr�
	 * @param motDePasse : le mot de passe entr�
	 * @return un entier correspondant au type du compte si l'individu s'est correctement identifi�, � -1 sinon
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public int testerAuthent (String id,String motDePasse) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		//cette methode rend -1 si la combinaison id/mdp est erronee, ou le type du compte si la combinaison est correcte
		int resul=-1;
		if(DAOCompte.estDansLaBddCompte(id)){
			Compte compte = DAOCompte.getCompteById(id);
			if(motDePasse.equals(compte.getMotDePasse()) && compte.isActif()){
				resul=compte.getType();
			}
		}
		return resul;
	}

	/**
	 * si l'individu a cliqu� sur le bouton "Valider", cette m�thode r�cup�re les param�tres entr�s et les teste
	 * le menu principal s'affiche si l'individu s'est correctement identifi�
	 * sinon une nouvelle fen�tre d'authentification s'affiche avec un message diff�rent
	 * @param arg0 
	 * @throws {@SQLException}
	 * @throws {@ClassNotFoundException}
	 * @throws {@ConnexionFermeeException}
	 * @throws {@link MotDePasseNonRempliException}
	 */

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();

		String id = idARemplir.getText();
		try {
			String mdp = UtilitaireIhm.obtenirMotDePasse(motDePasseARemplir);
			//TODO
			System.out.println("mdp : " + mdp);
			//TODO verifier que ca marche, y compris quand on ne remplit rien,
			//il semble que l'exception MotDePasseNonRempliException ne serve a rien...


			Compte c = DAOCompte.getCompteById(id);
			System.out.println("id renseigne = "+id + "\nmot de passe renseigne = "+mdp);
			System.out.println("id = "+c.getId()+ " et mdp = "+c.getMotDePasse());
			int resultatAuthent = testerAuthent(id,mdp);
			mdp = null;//pour augmenter la s�curit� de l'application

			//si aucune exception levee et si l'utilisateur existe bien dans la base, on ferme la fenetre
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
		} 
		catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}
		catch (MotDePasseNonRempliException e){
			MsgBox.affMsg("Veillez � bien renseigner votre mot de passe");
			new FenetreAuthentification(true);
		}
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}
}