package client;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import business.MenuDish;

public class DishClientTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = {"Dish", "Stock", "Price", "Description"};
	private List<MenuDish> stockedDishes = new ArrayList<MenuDish>();
	private boolean update;

	public DishClientTableModel() {
		super();
	}

	public boolean hasUpdate() {
		return update;
	}

	public void setUpdated() {
		update = false;
	}

	public void addDish(MenuDish d) {
		stockedDishes.add(d);
	}

	public void removeDish(MenuDish d) {
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
	public String getColumnName(int index) {
		return columnNames[index];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		MenuDish dish;

		dish = stockedDishes.get(rowIndex);

		//TODO EXCEPTIONS
		if (dish == null) {
			return null;
		}

		switch (columnIndex) {
		case 0:
			return dish.getDish().getName();
		case 1:
			return dish.getStock();
		case 2:
			return dish.getDish().getPrice();
		case 3:
			return dish.getDish().getDescription();
		default:
			return null; //TODO EXCEPTIONS
		}
	}

	@Override
	public Class<?> getColumnClass(int c) { //TODO check if the <?> is right
		return getValueAt(0, c).getClass();
	}

	public void clear() {
		stockedDishes.clear();
	}

	public void dishAdded(MenuDish d) {
		addDish(d);
		update = true;
	}

}
