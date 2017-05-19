package client;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import business.OrderDish;
import comms.OrderStatusMessageOrder;

public class OrderStatusClientTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = {"Order ID", "Price", "Status", "Dishes"};
	private List<OrderStatusMessageOrder> orders = new ArrayList<OrderStatusMessageOrder>();
	private boolean update;

	public OrderStatusClientTableModel() {
		super();
	}

	public boolean hasUpdate() {
		return update;
	}

	public void setUpdated() {
		update = false;
	}

	public void addOrder(OrderStatusMessageOrder o) {
		orders.add(o);
	}

	public void removeOrder(OrderStatusMessageOrder o) {
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
	public String getColumnName(int index) {
		return columnNames[index];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		OrderStatusMessageOrder order;

		order = orders.get(rowIndex);

		//TODO EXCEPTIONS
		if (order == null) {
			return null;
		}

		switch (columnIndex) {
		case 0:
			return order.getId();
		case 1:
			return order.getPrice();
		case 2:
			return order.getStatus().toString();
		case 3:
			String list = "";
			for (OrderDish d : order.getDishes()) {
				list = list + d.getDish().getName() + "(" + d.getQuantity() + "), ";
			}
			list = list.substring(0, list.length() - 2);
			return list;
		default:
			return null; //TODO EXCEPTIONS
		}
	}

	@Override
	public Class<?> getColumnClass(int c) { //TODO check if the <?> is right
		return getValueAt(0, c).getClass();
	}

	public void clear() {
		orders.clear();
	}

	public void orderAdded(OrderStatusMessageOrder o) {
		addOrder(o);
		update = true;
	}

}
