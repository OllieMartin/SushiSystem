package business;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import comms.ServerComms;

public class BusinessApplication extends JFrame {

	public static void main(String args[]) {
		BusinessApplication.getInstance();
	}
	
	private static final long serialVersionUID = 1L;

	static {
		SwingUtilities.invokeLater( new Runnable() { 
			@Override
			public void run() {  
				instance = new BusinessApplication();
			}
		});
	}
	
	public static BusinessApplication getInstance() {
		return instance;
	}
	
	private static final int DEFAULT_WIDTH = 1000; // Default width for the main window pixels
	private static final int DEFAULT_HEIGHT = 500; // Default height for the main window pixels
	private static final int MINIMUM_WIDTH = 750; // Minimum width for the main window pixels
	private static final int MINIMUM_HEIGHT = 300; // Minimum height for the main window pixels
	private static BusinessApplication instance; // The instance of the application for the singleton pattern
	
	private BusinessApplicationPane mainPane;
	
	private BusinessApplication() {
		
		super("Sushi System - Business Application");
		
		new Thread(new ServerComms()).start();
		AccountManager.getInstance();
		DroneManager.getInstance();
		OrderManager.getInstance();
		
		mainPane = new BusinessApplicationPane(this);
		this.add(mainPane);
		
		if (DataPersistence.getInstance().fileExists("save.dat")) {
			DataPersistence.getInstance().loadAll("save.dat");
		}

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));

		this.setVisible(true);
		
	}
	
}
