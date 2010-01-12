package statistiques;

import java.awt.Component;
import java.awt.Font;

import ihm.UtilitaireIhm;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
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
	
	
	
	//TODO a commenter
	@Override
	public JTableHeader getTableHeader() {
		setFont(UtilitaireIhm.POLICE2);
		return super.getTableHeader();
	}
	
	//TODO a commenter
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
