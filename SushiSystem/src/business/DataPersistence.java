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

	private DataPersistence() {/* Empty */}

	public void saveAll(String filename) {

		try {

			File file = new File(filename);
			FileOutputStream saveFile = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(saveFile);

			synchronized (KitchenStaff.getKitchenStaff()) {
				if (KitchenStaff.getNextId() == null) {
					KitchenStaff.setNextId(0);
				}
				synchronized (KitchenStaff.getNextId()) {
					out.writeObject(KitchenStaff.getKitchenStaff());
					out.writeObject(KitchenStaff.getNextId());
				}
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
			System.err.println("File has been deleted, could not save data");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error occurred saving data");
		}


	}

	@SuppressWarnings("unchecked")
	public void loadAll(String filename) {

		try {

			File file = new File(filename);
			FileInputStream loadFile = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(loadFile);

			KitchenStaff.loadKitchenStaff((List<KitchenStaff>)in.readObject());
			KitchenStaff.setNextId((Integer)in.readObject());
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
			System.err.println("File does not exist, could not load data");
		} catch (IOException e) {
			System.err.println("Error occurred reading saved data");
		} catch (ClassNotFoundException e) {
			System.err.println("Error occured casting data, could be an old version, try deleting the saved file and trying again");
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
