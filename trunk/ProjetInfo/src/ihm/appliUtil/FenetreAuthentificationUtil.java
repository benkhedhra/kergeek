package ihm.appliUtil;

import gestionBaseDeDonnees.UtilitaireSQL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Utilisateur;

public class FenetreAuthentificationUtil extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// définition des polices
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

		System.out.println("Ouverture d'une fenêtre d'authentification de l'utilisateur");

		this.setContentPane(new Panneau());
		this.setTitle("Authentification");
		this.setSize(new Dimension(700,500));		
		this.setMinimumSize(new Dimension(700,500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// on définit un BorderLayout
		this.getContentPane().setLayout(new BorderLayout());
		
		labelBienvenue.setPreferredSize(new Dimension(200,50));		
		labelBienvenue.setMaximumSize(new Dimension(500,50));
		labelBienvenue.setText("   Bienvenue ! ");
		labelBienvenue.setFont(POLICE1);
		this.getContentPane().add(labelBienvenue,BorderLayout.NORTH);

		if(erreurAuthent){
			labelInvitation.setText("Identifiant inconnu. Veuillez à nouveau entrer votre identifiant");
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

	//on vérifie que les paramètres de connexion sont bons
	protected void authentifier(String pseudo) throws SQLException, ClassNotFoundException {
		UtilitaireSQL.testerIdent(pseudo);
		System.out.println("résultat de testerIdent = "+UtilitaireSQL.testerIdent(pseudo));
	}

	/**
	 * C'est la méthode qui sera appelée lors d'un clic sur notre bouton
	 */
	public void actionPerformed(ActionEvent arg0) {
		Utilisateur u = LancerAppliUtil.UTEST;
		/*try {
			u = this.getUtilisateur();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			MsgBox.afficheMsg(e1.printStackTrace());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			MsgBox.afficheMsg(e1.printStackTrace());
		}*/

		//vérification des paramètres de connexion.
		try {
			//test des paramètres
			String id = idARemplir.getText();
			System.out.println("id renseigné = "+id);
			authentifier(idARemplir.getText());
			//si aucune exception levée et si l'utilisateur existe bien dans la base, on ferme la fenetre
			//d'authentification et on ouvre la fenetre de l'utilisateur

			if (UtilitaireSQL.testerIdent(idARemplir.getText())){
				this.dispose();
				MenuUtilisateur m = new MenuUtilisateur(u);
				m.setVisible(true);
			}
		}
		catch (Exception e) {
			this.setVisible(false);
			FenetreAuthentificationUtil f = new FenetreAuthentificationUtil(true);
			f.setVisible(true);
		}
	}
}