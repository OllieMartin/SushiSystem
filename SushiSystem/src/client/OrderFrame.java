package client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import business.Dish;
import business.MenuDish;
import business.OrderDish;

public class OrderFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Client client;
	private JPanel dishSelector;
	private JComboBox<Dish> dishes;
	private JSpinner quantity;
	private List<OrderDish> order;
	private JButton go;
	private JButton addItem;
	private JLabel priceLabel;
	private float price;
	private JLabel avaliableDishes;
	private JLabel number;

	public OrderFrame(Client client) {

		this.client = client;
		this.setLayout(new GridLayout(4,1));
		priceLabel = new JLabel();
		price = 0;
		priceLabel.setText("£" + String.valueOf(price));
		dishSelector = new JPanel();
		dishSelector.setLayout(new GridLayout(2,2));
		dishes = new JComboBox<Dish>();
		for (MenuDish dish : this.client.getDishes()) {
			dishes.addItem(dish.getDish());
		}
		
		avaliableDishes = new JLabel("Avaliable Dishes");
		number = new JLabel("Number to order");
		dishSelector.add(avaliableDishes);
		dishSelector.add(number);
		dishSelector.add(dishes);
		quantity = new JSpinner();
		quantity.setValue(1);
		dishSelector.add(quantity);
		this.add(dishSelector);
		order = new ArrayList<OrderDish>();
		addItem = new JButton("Add item to basket");
		addItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				price = price + (((Dish)dishes.getSelectedItem()).getPrice()*(int)quantity.getValue());
				priceLabel.setText("£" + String.valueOf(price));
				order.add(new OrderDish((Dish)dishes.getSelectedItem(),(int)quantity.getValue()));
				dishes.removeItem(dishes.getSelectedItem());
				if (dishes.getItemCount() == 0) {
					quantity.setEnabled(false);
					dishes.setEnabled(false);
					addItem.setEnabled(false);
				}

			}

		});
		this.add(addItem);
		this.add(priceLabel);

		go = new JButton("Place Order");
		go.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				client.placeOrder(order);
				setVisible(false);

			}

		});
		this.add(go);
		
		if (dishes.getItemCount() == 0) {
			quantity.setEnabled(false);
			dishes.setEnabled(false);
			addItem.setEnabled(false);
			go.setEnabled(false);
		}
		
		this.setMinimumSize(new Dimension(400,300));
		this.setPreferredSize(new Dimension(400,300));
		this.setVisible(true);
	}

}
