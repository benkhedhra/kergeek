package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeAssignation;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.UtilitaireIhm;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.DemandeAssignation;
import metier.Garage;
import metier.Lieu;
import metier.Technicien;
import metier.Velo;

/**
 * la classe {@link FenetreGererUneDemandeAssignationTech} h�rite de {@link JFrame} et impl�mente l'interface {@link ActionListener}
 * <br>cette fen�tre appara�t lorsque le technicien a s�lectionn� une {@link DemandeAssignation} dans la {@link FenetreGererDemandesAssignationTech}
 * <br>elle offre au {@link Technicien} la possibilit� de prendre en charge cette {@link DemandeAssignation}, lorsqu'il vient de r�aliser un ajout ou un retrait physique de v�los
 * <br>cette action s'accompagne d'une action physique qui a lieu quasiment simultan�ment (on peut imaginer que le technicien a acc�s � l'application depuis un ordinateur pr�sent dans son camion)
 * <br>cette fen�tre est propre au volet Technicien de l'application AdminTech
 * @author KerGeek
 *
 */
public class FenetreGererUneDemandeAssignationTech extends JFrame implements ActionListener {

	/*
	 * attributs priv�s de la fen�tre 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private DemandeAssignation demande;
	
	private JLabel labelTech = new JLabel("");
	private JLabel labelChemin = new JLabel("Menu principal > G�rer les demandes d'assignation > G�rer une demande");
	private JLabel labelMsg = new JLabel("Demande d'assignation � traiter");
	private JLabel labelLieu = new JLabel("Lieu concern� par la demande d'assignation");
	private JLabel labelLieuDemande = new JLabel("");
	private JLabel labelNbVelosSouhaite = new JLabel("Nombre de v�los souhait�");
	private JLabel labelNbVelosSouhaiteDemande = new JLabel("");
	private JLabel labelNbVelosActuel = new JLabel("Nombre de v�los actuel");
	private JLabel labelNbVelosActuelDemande = new JLabel("");
	private JLabel labelOperation = new JLabel("Op�ration � effectuer");
	private JLabel labelOperationDemande = new JLabel("");

	private JButton boutonPrendreEnCharge = new JButton("");
	private JButton boutonRetour = new JButton("Retour au menu principal");


	// Accesseurs utiles

	/**
	 * @return	le {@link FenetreGererUneDemandeAssignationTech#technicien} de la {@link FenetreGererUneDemandeAssignationTech}
	 */
	public Technicien getTechnicien() {
		return technicien;
	}

	/**
	 * Initialise le {@link FenetreGererUneDemandeAssignationTech#technicien} de la {@link FenetreGererUneDemandeAssignationTech}
	 * @param tech
	 * le technicien connect� sur cette fen�tre
	 * @see Technicien
	 */
	public void setTechnicien(Technicien tech) {
		this.technicien = tech;
	}

	/**
	 * @return	la {@link FenetreGererUneDemandeAssignationTech#demande} de la {@link FenetreGererUneDemandeAssignationTech}
	 */
	public DemandeAssignation getDemande() {
		return demande;
	}

	/**
	 * Initialise la {@link FenetreGererUneDemandeAssignationTech#demande} de la {@link FenetreGererUneDemandeAssignationTech}
	 * @param d
	 * la demande d'assignation s�lectionn�e � la fen�tre pr�c�dente
	 * @see DemandeAssignation
	 */
	public void setDemande(DemandeAssignation d) {
		this.demande = d;
	}

	/**
	 * constructeur de la {@link FenetreGererUneDemandeAssignationTech}
	 * @param t
	 * le technicien connect� sur la fen�tre
	 * @param d
	 * la demande d'assignation s�lectionn�e � la fen�tre pr�c�dente
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ConnexionFermeeException
	 * @see BorderLayout
	 * @see JPanel
	 * @see JLabel
	 * @see JButton
	 */
	public FenetreGererUneDemandeAssignationTech(Technicien t,DemandeAssignation d) throws SQLException, ClassNotFoundException, ConnexionFermeeException{

		System.out.println("Fen�tre pour g�rer une demande d'assignation");
		this.setContentPane(new PanneauTech());
		//D�finit un titre pour notre fen�tre
		this.setTitle("G�rer une demande d'assignation");
		//D�finit une taille pour celle-ci
	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Rectangle bounds = env.getMaximumWindowBounds();
	    this.setBounds(bounds);
		//Terminer le processus lorsqu'on clique sur "Fermer"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Nous allons maintenant dire � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//pour que la fen�tre ne se redimensionne pas � chaque fois
		this.setResizable(false);
		//pour que la fen�tre soit toujours au premier plan
		this.setAlwaysOnTop(true);


		// on d�finit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());

		this.setTechnicien(t);
		this.setDemande(d);

		labelTech = new JLabel("Vous �tes connect� en tant que "+ t.getCompte().getId());
		labelTech.setFont(UtilitaireIhm.POLICE4);
		labelTech.setPreferredSize(new Dimension(1100,50));
		labelTech.setMaximumSize(new Dimension(1100,50));
		labelChemin.setFont(UtilitaireIhm.POLICE4);
		labelChemin.setPreferredSize(new Dimension(1100,50));
		labelChemin.setMaximumSize(new Dimension(1100,50));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200,100));
		north.setBackground(UtilitaireIhm.TRANSPARENCE);
		north.add(labelTech);
		north.add(labelChemin);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setBackground(UtilitaireIhm.TRANSPARENCE);
		center.setPreferredSize(new Dimension(1200,800));
		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelMsg.setFont(UtilitaireIhm.POLICE2);
		labelMsg.setPreferredSize(new Dimension(1100,100));
		centerNorth.add(labelMsg);
		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerCenter = new JPanel();
		centerCenter.setBackground(UtilitaireIhm.TRANSPARENCE);

		JPanel panel1 = new JPanel();
		panel1.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelLieu.setPreferredSize(new Dimension(500,40));
		labelLieu.setMaximumSize(new Dimension(500,40));
		panel1.add(labelLieu);
		centerCenter.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(UtilitaireIhm.TRANSPARENCE);
		System.out.println(DAODemandeAssignation.ligne(d));
		System.out.println(d.getLieu().getAdresse());
		labelLieuDemande.setText(d.getLieu().getAdresse());
		labelLieuDemande.setPreferredSize(new Dimension(350,40));
		labelLieuDemande.setMaximumSize(new Dimension(350,40));
		panel2.add(labelLieuDemande);
		centerCenter.add(panel2);	

		JPanel panel3 = new JPanel();
		panel3.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelNbVelosSouhaite.setPreferredSize(new Dimension(500,40));
		labelNbVelosSouhaite.setMaximumSize(new Dimension(500,40));
		panel3.add(labelNbVelosSouhaite);
		centerCenter.add(panel3);	

		JPanel panel4 = new JPanel();
		panel4.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelNbVelosSouhaiteDemande.setText(""+d.getNombreVelosVoulusDansLieu());
		labelNbVelosSouhaiteDemande.setPreferredSize(new Dimension(350,40));
		labelNbVelosSouhaiteDemande.setMaximumSize(new Dimension(350,40));
		panel4.add(labelNbVelosSouhaiteDemande);
		centerCenter.add(panel4);	

		JPanel panel5 = new JPanel();
		panel5.setBackground(UtilitaireIhm.TRANSPARENCE);	
		labelNbVelosActuel.setPreferredSize(new Dimension(500,40));
		labelNbVelosActuel.setMaximumSize(new Dimension(500,40));
		panel5.add(labelNbVelosActuel);
		centerCenter.add(panel5);

		JPanel panel6 = new JPanel();
		panel6.setBackground(UtilitaireIhm.TRANSPARENCE);
		List<Velo> velos = DAOVelo.getVelosByLieu(d.getLieu());
		if(d.getLieu().getId().equals(Lieu.ID_GARAGE)){
			for(int k=0;k<velos.size();k++){
				if(velos.get(k).isEnPanne()){
					velos.remove(velos.get(k));
				}
			}
		}
		labelNbVelosActuelDemande.setText(""+velos.size());
		labelNbVelosActuelDemande.setPreferredSize(new Dimension(350,40));
		labelNbVelosActuelDemande.setMaximumSize(new Dimension(350,40));
		panel6.add(labelNbVelosActuelDemande);
		centerCenter.add(panel6);			

		JPanel panel7 = new JPanel();
		panel7.setBackground(UtilitaireIhm.TRANSPARENCE);
		labelOperation.setPreferredSize(new Dimension(500,40));
		labelOperation.setMaximumSize(new Dimension(500,40));
		labelOperation.setForeground(Color.RED);
		panel7.add(labelOperation);
		centerCenter.add(panel7);

		JPanel panel8 = new JPanel();
		panel8.setBackground(UtilitaireIhm.TRANSPARENCE);
		String operation="";
		int diff = DAODemandeAssignation.getDiff(d);
		if(diff<0){
			operation=operation+"ajout";
		}
		else if (diff>0){
			operation=operation+"retrait";
		}
		operation=operation+" de "+Math.abs(diff)+" v�los";
		labelOperationDemande.setText(operation);
		labelOperationDemande.setPreferredSize(new Dimension(350,40));
		labelOperationDemande.setMaximumSize(new Dimension(350,40));
		labelOperationDemande.setForeground(Color.RED);
		panel8.add(labelOperationDemande);
		centerCenter.add(panel8);

		if(this.getDemande().getLieu().equals(Garage.getInstance())){
			boutonPrendreEnCharge.setText("Cr�er de nouveaux v�los");
		}
		else{
			boutonPrendreEnCharge.setText("Prendre en charge cette demande d'assignation");
		}

		JPanel panel9 = new JPanel();
		panel9.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonPrendreEnCharge.setPreferredSize(new Dimension(400,50));
		boutonPrendreEnCharge.setMaximumSize(new Dimension(400,50));
		boutonPrendreEnCharge.setBackground(Color.CYAN);
		boutonPrendreEnCharge.setFont(UtilitaireIhm.POLICE3);
		boutonPrendreEnCharge.addActionListener(this);
		panel9.add(boutonPrendreEnCharge);
		centerCenter.add(panel9);

		center.add(centerCenter,BorderLayout.CENTER);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,100));
		south.setBackground(UtilitaireIhm.TRANSPARENCE);
		south.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(UtilitaireIhm.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,50));
		boutonRetour.setMaximumSize(new Dimension(250,50));
		boutonRetour.setFont(UtilitaireIhm.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		panel.add(boutonRetour);
		south.add(panel,BorderLayout.EAST);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/**
	 * cette m�thode est ex�cut�e lorsque le {@link Technicien} a cliqu� sur l'un des deux boutons qui se pr�sentaient � lui
	 * <br>s'il a cliqu� sur {@link FenetreGererUneDemandeAssignationTech#boutonPrendreEnCharge} une nouvelle fen�tre appara�t lui demandant de renseigner les identifiants des v�los d�plac�s
	 * <br>s'il a cliqu� sur le {@link FenetreGererUneDemandeAssignationTech#boutonRetour} il retourne � son menu principal
	 * @see FenetrePrendreEnChargeAssignationTech#FenetrePrendreEnChargeAssignationTech(Technicien, DemandeAssignation, ArrayList, boolean)
	 * @see MenuPrincipalTech#MenuPrincipalTech(Technicien)
	 * @param arg0
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		try {
			if(arg0.getSource()==boutonPrendreEnCharge){
				//le technicien doit cliquer sur le bouton "prendre en charge" juste avant ou juste apr�s avoir effectivement d�plac� les v�los, de mani�re � �tre s�r qu'aucun autre technicien n'ait effectu� l'op�ration (il a acc�s � l'application depuis son camion)
				if(this.getDemande().getLieu().getId().equals(""+Lieu.ID_GARAGE)){
					new FenetreConfirmation(this.getTechnicien().getCompte(),this);
				}
				else{
					ArrayList<String> listeVide = new ArrayList<String>();
					int diff;

					diff = DAOVelo.getVelosByLieu(demande.getLieu()).size()-demande.getNombreVelosVoulusDansLieu();
					int nbVelosADeplacer = Math.abs(diff);
					for(int i=0;i<nbVelosADeplacer;i++){
						listeVide.add("");
					}
					new FenetrePrendreEnChargeAssignationTech(this.getTechnicien(),this.getDemande(),listeVide,true);
				}
			}
			else if (arg0.getSource()==boutonRetour){
				new MenuPrincipalTech(this.getTechnicien());
			}
		} catch (SQLException e) {
			MsgBox.affMsg("SQL Exception : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg("Class Not Found Exception : " + e.getMessage());
		}
		catch (ConnexionFermeeException e){
			MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
			new FenetreAuthentification(false);
		}
	}		
}