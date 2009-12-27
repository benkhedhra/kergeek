package ihm.appliAdminTech.technicien;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanneauTech extends JPanel {

	/*
	 * attribut de sérialisation par défaut
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * méthode qui sera appelé par défaut à chaque construction d'un nouveau panneau et qui peint l'image panneauTechnicien.jpg en image de fond
	 */
	public void paintComponent(Graphics g){
		try {
			Image img = ImageIO.read(new File(System.getProperty("user.dir")+"/src/ressources/panneauTechnicien.jpg"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException : image de fond innaccessible");
		}
	}

}