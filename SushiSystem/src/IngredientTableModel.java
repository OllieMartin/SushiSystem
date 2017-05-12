import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class IngredientTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Ingredient", "Stock", "Restocking Level"};
	private List<StockedIngredient> stockedIngredients = new ArrayList<StockedIngredient>();
	
	public void addIngredient(StockedIngredient i) {
		stockedIngredients.add(i);
	}
	
	public void removeIngredient(StockedIngredient i) {
		stockedIngredients.remove(i);
	}
	
	@Override
	public int getRowCount() {
		return stockedIngredients.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		StockedIngredient ingredient;
		
		ingredient = stockedIngredients.get(rowIndex);
		
		//TODO EXCEPTIONS
		if (ingredient == null) {
			return null;
		}
		
		switch (columnIndex) {
		case 0:
			return ingredient.getIngredient().getName();
		case 1:
			return ingredient.getNumberInStock();
		case 2:
			return ingredient.getRestockingLevel();
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
		stockedIngredients.clear();
	}

}
