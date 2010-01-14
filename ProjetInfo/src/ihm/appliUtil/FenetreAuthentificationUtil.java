package ihm.appliUtil;


import gestionBaseDeDonnees.DAOCompte;
import gestionBaseDeDonnees.DAOUtilisateur;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.PasswordFieldLimite;
import ihm.TextFieldLimite;
import ihm.UtilitaireIhm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Utilisateur;
import metier.UtilitaireDate;

/**
 * FenetreAuthentificationUtil h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>cette fen�tre permet � un utilisateur du parc de s'authentifier en entrant son identifiant
 * <br>cette fen�tre est propre � l'application {@link Utilisateur}
 * @see MenuUtil#MenuUtil(Utilisateur)
 * @author KerGeek
 */
public class FenetreAuthentificationUtil extends JFrame implements ActionListener {

	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * liste des attributs priv�s : composants de la fen�tre
	 */
	private JLabel labelBienvenue = new JLabel("");
	private JLabel labelInvitation = new JLabel("");
	private TextFieldLimite idARemplir = new TextFieldLimite(4,"");
	private JButton boutonValider = new JButton ("Valider");
	private JButton boutonInfo = new JButton ("Plus d'informations ... ");

	/**
	 * constructeur
	 * @param erreurAuthent
	 * vaut true si l'utilisateur a d�j� essay� de s'identifier pr�c�demment sans succ�s
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see TextFieldLimite
	 * @see PasswordFieldLimite
	 * @see JButton
	 */
	public FenetreAuthentificationUtil(Boolean erreurAuthent){

		System.out.println("Ouverture d'une fen�tre d'authentification de l'utilisateur");

		this.setContentPane(new PanneauUtil());
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
		labelBienvenue.setFont(UtilitaireIhm.POLICE1);
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
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(700,350));center.setLayout(new BorderLayout());

		JPanel centerCenter = new JPanel();
		centerCenter.setBackground(UtilitaireIhm.TRANSPARENCE);
		centerCenter.setPreferredSize(new Dimension(700,300));

		labelInvitation.setFont(UtilitaireIhm.POLICE2);
		centerCenter.add(labelInvitation);
		idARemplir.setFont(UtilitaireIhm.POLICE3);
		idARemplir.setPreferredSize(new Dimension(150,30));
		idARemplir.setForeground(Color.BLUE);
		centerCenter.add(idARemplir);
		center.add(centerCenter,BorderLayout.CENTER);

		JPanel centerSouth = new JPanel();
		centerSouth.setBackground(UtilitaireIhm.TRANSPARENCE);
		centerSouth.setPreferredSize(new Dimension(700,50));
		boutonValider.setPreferredSize(new Dimension(100,30));
		boutonValider.setMaximumSize(new Dimension(100,30));
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setFont(UtilitaireIhm.POLICE3);
		boutonValider.addActionListener(this);
		centerSouth.add(boutonValider);
		center.add(centerSouth,BorderLayout.SOUTH);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setPreferredSize(new Dimension(700,100));
		boutonInfo.setPreferredSize(new Dimension(350,30));
		boutonInfo.setMaximumSize(new Dimension(250,30));
		boutonInfo.setBackground(Color.MAGENTA);
		boutonInfo.setFont(UtilitaireIhm.POLICE3);
		boutonInfo.addActionListener(this);
		south.add(boutonInfo);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);

	}

	/**
	 * @return l'utilisateur une fois qu'il est correctement authentifi�
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */

	public Utilisateur getUtilisateur() throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		return gestionBaseDeDonnees.DAOUtilisateur.getUtilisateurById(idARemplir.getText());
	}

	/**
	 * m�thode permettant de tester le champ entr� par l'utilisateur qui cherche � s'identifier � la borne
	 * @param idUtilisateur
	 * l'identifiant entr� par l'utilisateur
	 * @return un bool�en valant true si l'identifiant entr� correspond bien � un utilisateur existant dans la base de donn�es
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */
	public boolean testerIdent (String idUtilisateur) throws SQLException, ClassNotFoundException, ConnexionFermeeException{
		boolean resul;
		if(DAOCompte.estDansLaBddUtil(idUtilisateur)){resul=true;}
		else {resul=false;}
		return resul;
	}

	/**
	 * m�thode ex�cut�e une fois que l'utilisateur � la borne a cliqu� sur le bouton "Valider"
	 * <br>ferme la fen�tre courante et ouvre la fen�tre suivante
	 * <br>il s'agit du menu de l'utilisateur si celui-ci s'est correctement authentifi�
	 * <br>d'une fen�tre lui indiquant que son compte est bloqu� s'il s'est correctement identifi� mais que son compte est bloqu�
	 * <br>d'une nouvelle fen�tre d'authentification si l'identifiant entr� ne correspond � aucun utilisateur
	 * @param arg0 
	 * @see Utilisateur#isBloque()
	 * @see FenetreConfirmationUtil#FenetreConfirmationUtil(String)
	 * @see MenuUtil#MenuUtil(Utilisateur)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 */

	public void actionPerformed(ActionEvent arg0) {
		try {
			if(arg0.getSource()==boutonInfo){
				new FenetreConfirmationUtil("<html><center>B�lo Breizh est un syst�me de location de v�los sur le campus de Ker Lann. <br>Notre r�seau comporte 7 stations. <br><br>Pour plus de d�tails, contactez-nous � l'adresse<br>kergeek@gmail.com<br><br> Pour vous inscrire et pouvoir utiliser notre r�seau, veuillez vous rendre � notre agence au <br>1, rue des v�los (Bruz)<br><br>muni d'une pi�ce d'identit� et d'une photo. <br><br>A tr�s bient�t ! ");
			}
			else{		
				String id = idARemplir.getText();
				if (testerIdent(id)){
					Utilisateur u;
					u = DAOUtilisateur.getUtilisateurById(id);

					Date dateDernierRetour = DAOUtilisateur.getDerniereDateRetour(u);

					if(u.isBloque()){
						if(dateDernierRetour.before(UtilitaireDate.retrancheJours(UtilitaireDate.dateCourante(),7))){
							u.setBloque(false);
						}
					}
					if(!u.isBloque()){
						new MenuUtil(u);
					}
					else{
						Date dateLimite = UtilitaireDate.ajouteJours(dateDernierRetour,7);
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(dateLimite);

						new FenetreConfirmationUtil("Votre compte est bloqu� jusqu'au " + cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG , getLocale())+" "+ cal.get(Calendar.DAY_OF_MONTH)+" "+ cal.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocale()) + " � " + (cal.get(Calendar.HOUR_OF_DAY)+1) + "h");
					}
				}
				else{
					new FenetreAuthentificationUtil(true);
				}
			}
		}
		catch (SQLException e1) {
			MsgBox.affMsg(e1.getMessage());
			new FenetreAuthentificationUtil(false);
		}
		catch (ClassNotFoundException e2) {
			MsgBox.affMsg(e2.getMessage());
			new FenetreAuthentificationUtil(false);
		}
		catch (ConnexionFermeeException e3){
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentificationUtil(false);
		}
		finally{
			this.dispose();
		}
	}
}