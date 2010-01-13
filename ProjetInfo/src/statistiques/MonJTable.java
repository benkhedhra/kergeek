package statistiques;

import ihm.UtilitaireIhm;

import java.awt.Font;

import javax.swing.JTable;
/**
 * Cette classe permet de cr�er des tableaux non modifiables afin de pr�senter des donn�es.
 * @see JTable
 * @author KerGeek
 */
public class MonJTable extends JTable {

	private static final long serialVersionUID = 1L;

	// emp�che l'�dition des cellules du tableaux
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	/**
	 * Constructeur par d�faut d'un MonJtable
	 */
	public MonJTable() {
		super();
		this.getTableHeader().setReorderingAllowed( false ); // emp�che la modification de l'ordre des colonnes
		this.getTableHeader().setFont(UtilitaireIhm.POLICE2);
	}
	
	
	//permet de changer la police du texte � l'interieur des cellules du tableau
	@Override
	public Font getFont() {
	return UtilitaireIhm.POLICE3;
	}
	


	/**
	 * Constructeur d'un MonJTable � partir d'un Object[][] : les donn�es, 
	 * et d'un Object[] : les noms des diff�rentes colonnes.
	 * @param rowData
	 * @param columnNames
	 */
	public MonJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		this.getTableHeader().setReorderingAllowed( false ); // emp�che la modification de l'ordre des colonnes
	}

	
	
}
