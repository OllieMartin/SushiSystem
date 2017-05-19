package business;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class RemoveDroneFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	JComboBox<Integer> idComboBox;
	JLabel optionLabel;
	JButton deleteButton;

	public RemoveDroneFrame() {
		
		super("Remove drone");
		
		this.setMinimumSize(new Dimension(400,300));
		this.setMaximumSize(new Dimension(400,300));

		optionLabel = new JLabel("Select ID of drone to remove");
		optionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idComboBox = new JComboBox<Integer>();
		synchronized (DroneManager.getInstance().getDrones()) {
			for (Drone d : DroneManager.getInstance().getDrones()) {
				idComboBox.addItem(d.getId());
			}
		}
		
		deleteButton = new JButton("Remove selected drone");
		if (idComboBox.getItemCount() == 0) {
			deleteButton.setEnabled(false);
		}
		
		this.setLayout(new GridLayout(3,1));

		this.add(optionLabel);
		this.add(idComboBox);
		this.add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (idComboBox.getSelectedItem() != null) {
					DroneManager.getInstance().remove((Integer)idComboBox.getSelectedItem());
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Please select a valid ID from the combo box", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
			
		});
		
		this.setVisible(true);
		
	}
	
}
