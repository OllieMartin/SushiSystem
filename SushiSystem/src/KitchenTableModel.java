import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

public class KitchenTableModel extends AbstractTableModel implements KitchenStaffListener {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Kitchen Staff", "Status", "Dish Preparing"};
	private List<KitchenStaff> kitchenStaff = new ArrayList<KitchenStaff>();
	private Map<KitchenStaff,Dish> kitchenMap = new HashMap<KitchenStaff,Dish>();
	private static KitchenTableModel instance;
	public static KitchenTableModel getInstance() {
		return instance;
	}
	
	public KitchenTableModel() {
		super();
		instance = this;
	}
	
	public void addStaff(KitchenStaff k) {
		kitchenMap.put(k,null);
		kitchenStaff.add(k);
	}
	
	public void removeStaff(KitchenStaff k) {
		kitchenStaff.remove(k);
		kitchenMap.remove(k);
	}
	
	@Override
	public int getRowCount() {
		return kitchenStaff.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		KitchenStaff staff;
		
		staff = kitchenStaff.get(rowIndex);
		
		//TODO EXCEPTIONS
		if (staff == null) {
			return null;
		}
		
		switch (columnIndex) {
		case 0:
			return "STAFF"; // ID
		case 1:
			if (kitchenMap.get(staff) != null) {
				return "Busy";
			} else {
				return "Free";
			}
		case 2:
			if (kitchenMap.get(staff) != null) {
				return kitchenMap.get(staff);
			} else {
				return "N/A";
			}
		default:
			return null; //TODO EXCEPTIONS
		}
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's
	 * editable.
	 
	public boolean isCellEditable(int row, int col) {
		//Note that the data/cell address is constant,
		//no matter where the cell appears onscreen.
		if (col < 2) {
			return false;
		} else {
			return true;
		}
	}*/

	/*
	 * Don't need to implement this method unless your table's
	 * data can change.
	 
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}*/
	
	public void clear() {
		kitchenStaff.clear();
		kitchenMap.clear();
	}

	@Override
	public void kitchenStaffBusy(KitchenStaff k, Dish d) {
		kitchenMap.put(k, d);
		
	}

	@Override
	public void kitchenStaffFree(KitchenStaff k) {
		kitchenMap.put(k, null);
		
	}

	@Override
	public void kitchenStaffAdded(KitchenStaff k) {
		addStaff(k);
		
	}

}
