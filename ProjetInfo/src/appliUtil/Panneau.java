package appliUtil;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Panneau extends JPanel {

	public void paintComponent(Graphics g){

		/*
		 * ces deux lignes permettent de récupérer les largeurs et longueurs de l'écran
		 * int x1 = this.getWidth();
		 * int y1 = this.getHeight();
		 * */
	
		try {
			Image img = ImageIO.read(new File(/*"ProjetInfo\\src\\ressources\\velo1.jpg"*/System.getProperty("user.dir")+"/src/ressources/velo1.jpg"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		g.drawString("Vous êtes connecté en tant que Bli Blablou",15,30);
		g.drawRoundRect(10, 10, 245, 30, 10, 10);
	} 

}
