package ihm;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** 
 * MsgBox est une classe servant à afficher des messages ponctuels si on ne peut pas accéder à la fenêtre suivante de manière normale
 * par exemple si une exception est levée ou si l'individu connecté à l'application entre des champs incorrects
 * @author KerGeek
 */

public class MsgBox extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * l'attribut ok est le bouton qui ne permet de montrer que l'on a lu le message et qui ferme la MsgBox
	 */
	private Button ok;
	
	public MsgBox(JFrame fr, String msg) {


		//constructeur hérité
		super(fr, "Message");

		setModal(true);
		//gestionnaire de positionnement
		setLayout(new BorderLayout());
		//ligne de message
		JLabel label = new JLabel(msg,JLabel.CENTER);
		label.setFont(UtilitaireIhm.POLICE3);
		label.setForeground(Color.RED);
		add(BorderLayout.CENTER,label);
		//boutons
		JPanel p=new JPanel();
		p.setLayout(new FlowLayout());
		ok=new Button(" OK ");
		ok.setFont(UtilitaireIhm.POLICE3);
		ok.setForeground(Color.RED);
		ok.setBackground(Color.YELLOW);
		p.add(ok);
		ok.addActionListener(this);
		add(BorderLayout.SOUTH,p);
		//dimensions et positionnement
		pack();
		Dimension d=getToolkit().getScreenSize();
		setLocation(d.width/3,d.height/3);
		//affichage
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ok) {
			setVisible(false);
			dispose();
		}
	}

	/**
	 * méthode permettant d'afficher un message en construisant une MsgBox adaptée 
	 * @param msg : le message à afficher
	 */
	public static void affMsg(String msg) {
		JFrame frame = new JFrame();
		new MsgBox(frame, msg);
	}
}