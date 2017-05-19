package business;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class OrderTableModel extends AbstractTableModel implements OrderListener {

	private static final long serialVersionUID = 1L;
	
	private String[] columnNames = {"Order ID", "Status", "Customer", "Price"};
	private List<Order> orders = new ArrayList<Order>();
	private boolean update;
	
	public boolean hasUpdate() {
		return update;
	}
	
	public void setUpdated() {
		update = false;
	}
	
	public void addOrder(Order o) {
		orders.add(o);
	}
	
	@Override
	public String getColumnName(int index) {
	    return columnNames[index];
	}
	
	public void removeOrder(Order o) {
		orders.remove(o);
	}
	
	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Order order;
		
		order = orders.get(rowIndex);
		
		if (order == null) {
			return null;
		}
		
		switch (columnIndex) {
		case 0:
			return order.getId();
		case 1:
			return order.getStatus();
		case 2:
			return order.getUser();
		case 3:
			return order.getPrice();
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int c) {
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
		orders.clear();
	}

	@Override
	public void orderStatusChanged(Order o) {
		update = true;
		
	}

	@Override
	public void orderPlaced(Order o) {
		addOrder(o);
		update = true;
	}

}
