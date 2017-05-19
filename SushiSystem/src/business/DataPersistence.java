package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public class DataPersistence {

	private static DataPersistence instance;

	public static DataPersistence getInstance() {
		return instance;
	}

	static {
		instance = new DataPersistence();
	}

	private DataPersistence() {



	}

	public void saveAll(String filename) {

		try {

			File file = new File(filename);
			FileOutputStream saveFile = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(saveFile);

			synchronized (KitchenStaff.getKitchenStaff()) {
				out.writeObject(KitchenStaff.getKitchenStaff());
			}
			synchronized (DroneManager.getInstance().getDrones()) {
				if (Drone.getNextId() == null) {
					Drone.setNextId(0);
				}
				synchronized (Drone.getNextId()) {
					out.writeObject(DroneManager.getInstance().getDrones());
					out.writeObject(Drone.getNextId());
				}
			}
			
			synchronized (Supplier.getSuppliers()) {
				out.writeObject(Supplier.getSuppliers());
			}
			synchronized (StockedIngredient.getStockedIngredients()) {
				out.writeObject(StockedIngredient.getStockedIngredients());
			}
			synchronized (StockedDish.getStockedDishes()) {
				out.writeObject(StockedDish.getStockedDishes());
			}
			
			synchronized (AccountManager.getInstance().getRegisteredUsers()) {
				out.writeObject(AccountManager.getInstance().getRegisteredUsers());
			}
			synchronized (OrderManager.getInstance().getOrders()) {
				if (Order.getNextId() == null) {
					Order.setNextId(0);
				}
				synchronized (Order.getNextId()) {
					out.writeObject(OrderManager.getInstance().getOrders());
					out.writeObject(Order.getNextId());
				}
			}
			
			

			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@SuppressWarnings("unchecked")
	public void loadAll(String filename) {

		try {

			File file = new File(filename);
			FileInputStream loadFile = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(loadFile);

			KitchenStaff.loadKitchenStaff((List<KitchenStaff>)in.readObject());
			DroneManager.getInstance().loadDrones((List<Drone>)in.readObject());
			Drone.setNextId((Integer)in.readObject());
			
			Supplier.loadSuppliers((List<Supplier>) in.readObject());
			StockedIngredient.loadStockedIngredients((List<StockedIngredient>)in.readObject());
			StockedDish.loadStockedDishes((List<StockedDish>)in.readObject());
			
			AccountManager.getInstance().loadRegisteredUsers((Map<String,UserAccount>)in.readObject());
			OrderManager.getInstance().loadOrders((List<Order>)in.readObject());
			Order.setNextId((Integer)in.readObject());
			
			
			
			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean fileExists(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

}
