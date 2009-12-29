package ihm.appliUtil;

import gestionBaseDeDonnees.DAODemandeIntervention;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;

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

/**
 * FenetreEmpruntCourtUtil h�rite de {@link JFrame} et impl�mente {@link ActionListener}
 * <br>cette fen�tre s'ouvre dans le cas o� l'utilisateur vient de rendre un v�lo moins de 2 minutes apr�s l'avoir emprunt�
 * <br>elle propose � l'utilisateur de d�clarer ou non un d�faut sur le v�lo qu'il vient de rendre
 * <br>cette fen�tre est propre � l'application Utilisateur
 * @author KerGeek
 */
public class FenetreEmpruntCourtUtil extends JFrame implements ActionListener {

	/**
	 * attribut de s�rialisation par d�faut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * liste des attributs priv�s : composants de la fen�tre
	 */
	private Utilisateur utilisateur;
	private Velo velo;
	private JLabel labelUtil = new JLabel("");
	private JLabel labelMsg = new JLabel("");
	private JButton boutonOui = new JButton("OUI");
	private JButton boutonNon = new JButton("NON (D�connexion)");

	/**
	 * @return l'{@link Utilisateur} de la {@link FenetreEmpruntCourtUtil}
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	/**
	 * Initialise l'{@link Utilisateur} de la {@link FenetreEmpruntCourtUtil}
	 * @param utilisateur
	 * 
	 * le nouvel utilisateur de la FenetreEmpruntCourtUtil
	 * @see Utilisateur
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * @return le {@link Velo} qui vient d'�tre rendu moins de 2 minutes apr�s avoir �t� emprunt�
	 */
	public Velo getVelo() {
		return velo;
	}

	/**
	 * Initialise le {@link Velo} de la FenetreEmpruntCourtUtil
	 * @param velo
	 * le nouveau v�lo de la FenetreEmpruntCourtUtil
	 * @see Velo
	 */
	public void setVelo(Velo velo) {
		this.velo = velo;
	}

	/**
	 * constructeur de {@link FenetreEmpruntCourtUtil}
	 * @param u
	 * l'utilisateur connect� sur la fen�tre
	 * @param v
	 * le v�lo qui vient d'�tre emprunt� et rendu moins de 2 minutes apr�s
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */
	public FenetreEmpruntCourtUtil (Utilisateur u,Velo v){

		this.setContentPane(new PanneauUtil());
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
		labelUtil.setFont(UtilitaireIhm.POLICE4);
		labelUtil.setPreferredSize(new Dimension(500,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,150));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
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
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.add(labelMsg);
		center.add(boutonOui);
		center.add(boutonNon);
		this.add(center, BorderLayout.CENTER);

		this.setVisible(true);
	}

	/**
	 * m�thode ex�cut�e quand l'utilisateur a cliqu� sur l'un des deux boutons qui lui �taient propos�s
	 * <br>si l'utilisateur veut d�clarer le v�lo d�fectueux, le v�lo est d�clar� en panne et une nouvelle demande d'intervention est cr��e dans la base de donn�es
	 * <br>sinon, l'utilisateur a termin� son action, il est d�connect� et un message d'au-revoir appara�t
	 * @param arg0 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see {@link DemandeIntervention#DemandeIntervention(Utilisateur, Velo)}
	 * @see DAODemandeIntervention#createDemandeIntervention(DemandeIntervention)
	 */

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if(arg0.getSource()==boutonOui){
			this.getVelo().setEnPanne(true);
			DemandeIntervention demande = new DemandeIntervention(this.getUtilisateur(),velo);
			try {
				DAODemandeIntervention.createDemandeIntervention(demande);
				DAOVelo.updateVelo(this.getVelo());
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
			} catch (ConnexionFermeeException e3){
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentificationUtil(false);
			}
			new FenetreDefautDeclareUtil(this.getUtilisateur());
		}
		else if (arg0.getSource()==boutonNon){
			new FenetreConfirmationUtil("Au revoir et � bient�t ! ");
		}
	}
}