package business;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DishTableModel extends AbstractTableModel implements DishListener {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = {"Dish", "Stock", "Restocking Level"};
	private List<StockedDish> stockedDishes = new ArrayList<StockedDish>();
	private boolean update;

	public boolean hasUpdate() {
		return update;
	}

	public void setUpdated() {
		update = false;
	}

	public void addDish(StockedDish d) {
		stockedDishes.add(d);
	}

	@Override
	public String getColumnName(int index) {
		return columnNames[index];
	}

	public void removeDish(StockedDish d) {
		stockedDishes.remove(d);
	}

	@Override
	public int getRowCount() {
		return stockedDishes.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		StockedDish dish;

		dish = stockedDishes.get(rowIndex);

		//TODO EXCEPTIONS
		if (dish == null) {
			return null;
		}

		switch (columnIndex) {
		case 0:
			return dish.getDish().getName();
		case 1:
			return dish.getNumberInStock();
		case 2:
			return dish.getRestockingLevel();
		default:
			return null; //TODO EXCEPTIONS
		}
	}

	@Override
	public Class<?> getColumnClass(int c) { //TODO check if the <?> is right
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
		stockedDishes.clear();
	}

	@Override
	public void stockIncreased(StockedDish d) {
		update = true;

	}

	@Override
	public void stockDecreased(StockedDish d) {
		update = true;

	}

	@Override
	public void sufficientStock(StockedDish d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void outOfStock(StockedDish d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dishAdded(StockedDish d) {
		addDish(d);
		update = true;
	}

	@Override
	public void restockingLevelChanged(StockedDish d) {
		update = true;

	}

}
