package appliUtil;

import gestionBaseDeDonnees.ConnexionOracleViaJdbc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AccueilUtil extends JFrame {
	
	JLabel labelPseudo;
	JTextField fieldPseudo;
	JButton boutonValider;

	private JPanel panneau1;
	private JLabel label;

	public AccueilUtil (String titre){
			super(titre);
			setSize(500, 300);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setLayout(new BorderLayout());

			panneau1 = new JPanel();
			panneau1.setLayout(new BorderLayout());
			label = new JLabel();
			panneau1.add(label, BorderLayout.WEST);
			add(panneau1, BorderLayout.CENTER);

			class ActionTrain implements ActionListener {

				public void actionPerformed(ActionEvent actionevent) {
					try {
						ConnexionOracleViaJdbc.ouvrir();
					} catch (Exception e) {
						//en cas d'erreur on affiche une popup
						JOptionPane.showMessageDialog(null, "erreur de connexion",
								"erreur", JOptionPane.ERROR_MESSAGE);
					}
				}

			}


	}
	 
}