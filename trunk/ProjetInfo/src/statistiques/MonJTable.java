package statistiques;

import javax.swing.JTable;

public class MonJTable extends JTable {

	private static final long serialVersionUID = 1L;

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
