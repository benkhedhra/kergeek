package ihm.appliAdminTech.technicien;

import gestionBaseDeDonnees.DAODemandeIntervention;
import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.MsgBox;
import ihm.TextFieldLimite;
import ihm.appliAdminTech.FenetreAuthentification;
import ihm.appliAdminTech.FenetreConfirmation;
import ihm.appliUtil.FenetreAuthentificationUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.DemandeIntervention;
import metier.Intervention;
import metier.Technicien;
import metier.Velo;

public class FenetreRetirerVeloDefectueuxTech extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Technicien technicien;
	private JLabel labelTech = new JLabel("");
	private JLabel labelMsg1 = new JLabel("S�lectionnez une des demandes d'intervention formul�es par les utilisateurs");
	private DemandeIntervention demandeEntree=null;
	private JButton boutonValider = new JButton("Retirer le v�lo indiqu�");
	private JLabel labelMsg2 = new JLabel("<html><center>Si vous voulez constater un d�faut sur un v�lo qui ne figure pas dans les demandes ci-dessus, <br>veuillez entrer son identifiant : </center></html> ");
	private TextFieldLimite idVeloARemplir = new TextFieldLimite(4,"");
	private JButton boutonRetour = new JButton("Retour au menu principal");

	public Technicien getTechnicien() {
		return technicien;
	}

	public void setTechnicien(Technicien technicien) {
		this.technicien = technicien;
	}

	public DemandeIntervention getDemandeEntree() {
		return demandeEntree;
	}

	public void setDemandeEntree(DemandeIntervention demandeEntree) {
		this.demandeEntree = demandeEntree;
	}

	public FenetreRetirerVeloDefectueuxTech (Technicien t) throws ConnexionFermeeException{
		System.out.println("Fen�tre pour retirer un v�lo d�fectueux d'une station");
		this.setContentPane(new PanneauTech());
		//D�finit un titre pour notre fen�tre
		this.setTitle("G�rer les demandes d'intervention ou constater un v�lo d�fectueux");
		//D�finit une taille pour celle-ci
		this.setPreferredSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
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

		labelTech = new JLabel("Vous �tes connect� en tant que "+ t.getCompte().getId());
		labelTech.setFont(FenetreAuthentificationUtil.POLICE4);
		labelTech.setPreferredSize(new Dimension(500,30));
		labelTech.setMaximumSize(new Dimension(550,30));
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(700,50));
		north.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		north.add(labelTech);
		this.getContentPane().add(north,BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(700,400));
		center.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		center.setLayout(new BorderLayout());

		JPanel centerNorth = new JPanel();
		centerNorth.setPreferredSize(new Dimension(700,250));
		centerNorth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		labelMsg1.setPreferredSize(new Dimension(650,50));		
		labelMsg1.setMinimumSize(new Dimension(650,50));
		centerNorth.add(labelMsg1);

		List<DemandeIntervention> listeDemandes;
		try {
			listeDemandes= DAODemandeIntervention.getDemandesInterventionEnAttente();
			String [] tableauDemandes = new String[listeDemandes.size()+1];
			tableauDemandes[0]=listeDemandes.size()+" demandes formul�es";
			for (int i=0;i<listeDemandes.size();i++){
				DemandeIntervention demandei = listeDemandes.get(i);
					tableauDemandes[i+1] = DAODemandeIntervention.ligne(demandei);
			}

			DefaultComboBoxModel model = new DefaultComboBoxModel(tableauDemandes);

			JComboBox combo = new JComboBox(model);
			combo.setPreferredSize(new Dimension(400,50));		
			combo.setMinimumSize(new Dimension(400,50));
			combo.setFont(FenetreAuthentificationUtil.POLICE3);
			combo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					Object o = ((JComboBox)ae.getSource()).getSelectedItem();
					try {
						String chaineSelectionnee = (String)(o);
						String idDemandeEntre="";
						int i=8;
						while(chaineSelectionnee.charAt(i)!=' '){
							idDemandeEntre=idDemandeEntre+chaineSelectionnee.charAt(i);
							i++;
						}
						System.out.println("id de la demande entr� : "+idDemandeEntre);
						demandeEntree = DAODemandeIntervention.getDemandeInterventionById(idDemandeEntre);
					} catch (SQLException e) {
						MsgBox.affMsg(e.getMessage());
					} catch (ClassNotFoundException e) {
						MsgBox.affMsg(e.getMessage());
					}
					catch (ConnexionFermeeException e){
						MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
						new FenetreAuthentification(false);
					}
				}
			});
			centerNorth.add(combo);
		} catch (SQLException e) {
			MsgBox.affMsg(e.getMessage());
		} catch (ClassNotFoundException e) {
			MsgBox.affMsg(e.getMessage());
		}		


		center.add(centerNorth,BorderLayout.NORTH);

		JPanel centerSouth = new JPanel();
		centerSouth.setPreferredSize(new Dimension(700,150));
		centerSouth.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);

		labelMsg2.setPreferredSize(new Dimension(650,50));		
		labelMsg2.setMinimumSize(new Dimension(650,50));
		centerSouth.add(labelMsg2);
		idVeloARemplir.setPreferredSize(new Dimension(200,50));		
		idVeloARemplir.setMinimumSize(new Dimension(200,50));
		centerSouth.add(idVeloARemplir);
		
		boutonValider.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonValider.setBackground(Color.CYAN);
		boutonValider.setPreferredSize(new Dimension(250,50));		
		boutonValider.setMinimumSize(new Dimension(250,50));
		boutonValider.addActionListener(this);
		centerSouth.add(boutonValider);

		center.add(centerSouth,BorderLayout.SOUTH);

		this.getContentPane().add(center,BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(700,100));
		south.setBackground(FenetreAuthentificationUtil.TRANSPARENCE);
		boutonRetour.setPreferredSize(new Dimension(250,40));
		boutonRetour.setMaximumSize(new Dimension(250,40));
		boutonRetour.setFont(FenetreAuthentificationUtil.POLICE3);
		boutonRetour.setBackground(Color.YELLOW);
		boutonRetour.addActionListener(this);
		south.add(boutonRetour);
		this.getContentPane().add(south,BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
		if (arg0.getSource()==boutonValider){
			try {
				List<DemandeIntervention> listeDemandes= DAODemandeIntervention.getDemandesInterventionEnAttente();
				List<String> listeIdVelos = new LinkedList<String>();
				for(DemandeIntervention ddeI : listeDemandes){
					listeIdVelos.add(ddeI.getVelo().getId());
				}
				if(this.getDemandeEntree()!=null && !idVeloARemplir.getText().equals("")){
					System.out.println("Probl�me : 2 v�los entr�s");
					MsgBox.affMsg("Vous avez s�lectionn� une demande ET entr� un v�lo : veuillez faire l'un ou l'autre");
					new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());
				}
				else if(this.getDemandeEntree()!=null){
					System.out.println("Une demande d'intervention a �t� s�lectionn�e");
					Intervention i = this.getTechnicien().intervenir(demandeEntree.getVelo());
					this.getDemandeEntree().setIntervention(i);
					if(DAOVelo.updateVelo(i.getVelo())&& DAOIntervention.createIntervention(i) && DAODemandeIntervention.updateDemandeIntervention(this.getDemandeEntree())){
						new FenetreConfirmation(this.getTechnicien().getCompte(),this);
					}
				}
				else if(!idVeloARemplir.getText().equals("")){
					System.out.println("Un v�lo a �t� entr�");
					if(listeIdVelos.contains(idVeloARemplir.getText())){
						System.out.println("Probl�me : v�lo entr� fait d�j� l'objet d'une demande d'intervention");
						MsgBox.affMsg("Vous avez s�lectionn� un v�lo qui fait d�j� l'objet d'une demande d'intervention : veuillez selectionner la demande d'intervention en question");
						new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());

					}
					else{
						if(DAOVelo.existe(idVeloARemplir.getText()) &&  DAOVelo.estDisponible(idVeloARemplir.getText())){
							Velo veloEntre = DAOVelo.getVeloById(idVeloARemplir.getText());
							veloEntre.setEnPanne(true);
							Intervention i = this.getTechnicien().intervenir(veloEntre);
							if(DAOVelo.updateVelo(i.getVelo())&& DAOIntervention.createIntervention(i)){
								new FenetreConfirmation(this.getTechnicien().getCompte(),this);
							}
						}
						else if (!DAOVelo.existe(idVeloARemplir.getText())){
							System.out.println("Le v�lo entr� n'existe pas");
							MsgBox.affMsg("Le v�lo que vous avez entr� n'existe pas. ");
							new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());
						}
						else {
							System.out.println("Le v�lo entr� n'est pas en station actuellement");
							MsgBox.affMsg("Le v�lo que vous avez entr� n'est pas en station actuellement. ");
							new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());
						}
					}
				}
				else {
					System.out.println("Rien n'a �t� entr�");
					MsgBox.affMsg("Vous n'avez ni s�lectionn� de demande d'intervention, ni entr� de v�lo. ");
					new FenetreRetirerVeloDefectueuxTech(this.getTechnicien());
				}
			} catch (SQLException e) {
				MsgBox.affMsg(e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				MsgBox.affMsg(e.getMessage());
				e.printStackTrace();
			}
			catch (ConnexionFermeeException e){
				MsgBox.affMsg("<html> <center>Le syst�me rencontre actuellement un probl�me technique. <br>L'application n'est pas disponible. <br>Veuillez contacter votre administrateur r�seau et r�essayer ult�rieurement. Merci</center></html>");
				new FenetreAuthentification(false);
			}
		}
		else if (arg0.getSource()==boutonRetour){
			new MenuPrincipalTech(this.getTechnicien());
		}
	}
}