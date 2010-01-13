package statistiques;

import ihm.UtilitaireIhm;

import java.awt.Font;

import javax.swing.JTable;
/**
 * Cette classe permet de créer des tableaux non modifiables afin de présenter des données.
 * @see JTable
 * @author KerGeek
 */
public class MonJTable extends JTable {

	private static final long serialVersionUID = 1L;

	// empêche l'édition des cellules du tableaux
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	/**
	 * Constructeur par défaut d'un MonJtable
	 */
	public MonJTable() {
		super();
		this.getTableHeader().setReorderingAllowed( false ); // empêche la modification de l'ordre des colonnes
		this.getTableHeader().setFont(UtilitaireIhm.POLICE2);
	}
	
	
	//permet de changer la police du texte à l'interieur des cellules du tableau
	@Override
	public Font getFont() {
	return UtilitaireIhm.POLICE3;
	}
	


	/**
	 * Constructeur d'un MonJTable à partir d'un Object[][] : les données, 
	 * et d'un Object[] : les noms des différentes colonnes.
	 * @param rowData
	 * @param columnNames
	 */
	public MonJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		this.getTableHeader().setReorderingAllowed( false ); // empêche la modification de l'ordre des colonnes
	}

	
	
}
