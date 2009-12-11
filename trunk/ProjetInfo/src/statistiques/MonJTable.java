package statistiques;

import javax.swing.JTable;

public class MonJTable extends JTable {

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public MonJTable() {
		super();
	}

	public MonJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	
	
}
