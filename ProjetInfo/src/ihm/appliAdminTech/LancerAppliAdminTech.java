package ihm.appliAdminTech;

import java.sql.SQLException;

/**
 * classe ne comportant qu'un main permettant de lancer l'application de l'administrateur et du technicien
 * @author KerGeek
 *
 */
public class LancerAppliAdminTech {

	public static void main (String [] args) throws SQLException, ClassNotFoundException{
		new FenetreAuthentification(false);
	}
	
}