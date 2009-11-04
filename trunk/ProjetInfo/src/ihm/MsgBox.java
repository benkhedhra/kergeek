package ihm;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MsgBox extends JDialog implements ActionListener {
	private boolean id=false;  //permet de connaître le bouton utilisé
	private Button ok,can;

	public MsgBox(JFrame fr, String msg) {

		//constructeur hérité
		super(fr, "Message");
		setModal(true);
		//gestionnaire de positionnement
		setLayout(new BorderLayout());
		//ligne de message
		add(BorderLayout.CENTER,new JLabel(msg,JLabel.CENTER));
		//boutons
		JPanel p=new JPanel();
		p.setLayout(new FlowLayout());
		ok=new Button(" OK ");
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
			id=true;
			setVisible(false);
			dispose();
		}
	}

	public static void affMsg(String msg) {
		JFrame frame = new JFrame();
		MsgBox message=new MsgBox(frame, msg);
	}
}
