package business;
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
	boolean update;

	public KitchenTableModel() {
		super();
	}

	public boolean hasUpdate() {
		return update;
	}

	public void setUpdated() {
		update = false;
	}

	private void addStaff(KitchenStaff k) {
		kitchenMap.put(k,null);
		kitchenStaff.add(k);
		update = true;
	}

	public void removeStaff(KitchenStaff k) {
		kitchenStaff.remove(k);
		kitchenMap.remove(k);
		update = true;
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

	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void clear() {
		kitchenStaff.clear();
		kitchenMap.clear();
		update = true;
	}

	@Override
	public void kitchenStaffBusy(KitchenStaff k, Dish d) {
		kitchenMap.put(k, d);
		update = true;

	}

	@Override
	public void kitchenStaffFree(KitchenStaff k) {
		kitchenMap.put(k, null);
		update = true;

	}

	@Override
	public void kitchenStaffAdded(KitchenStaff k) {
		addStaff(k);

	}

}
