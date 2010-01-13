package statistiques;

import gestionBaseDeDonnees.DAOIntervention;
import gestionBaseDeDonnees.DAOVelo;
import gestionBaseDeDonnees.exceptionsTechniques.ConnexionFermeeException;
import ihm.UtilitaireIhm;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import metier.Intervention;
import metier.Velo;

/**
 * La classe TableauInterventionVelo permet de cr�er le tableau relatif aux interventions concernant un v�lo.
 * @author KerGeek
 */

public class TableauInterventionVelo extends JPanel {

	//Attributs
	
	private static final long serialVersionUID = 1L;

	//Constructeur
	
	/**
	 * Cr�ation d'un tableau des {@link Intervention} sur un {@link Velo} � partir de son identifiant.
	 * @param id
	 * identifiant du v�lo sur lequel on souhaite obtenir des informations
	 * @throws ConnexionFermeeException
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws ClassNotFoundExceptionException
	 * @see DAOIntervention#getInterventionsByVelo(metier.Velo)
	 * @see DAOVelo#getVeloById(String)
	 */
	public TableauInterventionVelo(String id) throws SQLException, ClassNotFoundException, ConnexionFermeeException {
		super(new GridLayout(1,0));
		
		//Noms des colonnes du tableau
		String[] columnNames = {
				"Date d'entr�e au garage",
				"Type d'intervention"
		};

		List<Intervention> listeInterventions = DAOIntervention.getInterventionsByVelo(DAOVelo.getVeloById(id));
		
		// Si on ne dispose d'aucune interventions pour ce v�lo
		if(listeInterventions == null){
			listeInterventions = new ArrayList<Intervention>();
		}
		// cr�ation des donn�es
		Object[][] donnees = new Object[listeInterventions.size()][4];
		for(int k=0;k<listeInterventions.size();k++){
			// initialisation des donn�es du tableau
			donnees[k][0] = listeInterventions.get(k).getDate();
			donnees[k][1] = listeInterventions.get(k).getTypeIntervention();
		}
		
		//cr�ation du tableau
		final MonJTable table = new MonJTable(donnees, columnNames);

		//cr�ation du d�filement (au cas ou le tableau serait trop grand)
		JScrollPane scrollPane = new JScrollPane(table);

		//ajout du d�filement
		add(scrollPane);
		
		//TODO a commenter
		for(int i=0; i<table.getColumnModel().getColumnCount(); i++){
			TableColumn col = table.getColumnModel().getColumn(i);
			col.setMinWidth(col.getHeaderValue().toString().length()*9);
			col.setMaxWidth(col.getHeaderValue().toString().length()*9);
		}
		table.setPreferredScrollableViewportSize(new Dimension(table.getColumnModel().getTotalColumnWidth(), table.getRowCount()*16));
		table.setFillsViewportHeight(true);
		table.getTableHeader().setFont(UtilitaireIhm.POLICE2);
	}
}
