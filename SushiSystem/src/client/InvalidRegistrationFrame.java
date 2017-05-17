package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InvalidRegistrationFrame extends RegistrationFrame {

	private static final long serialVersionUID = 1L;
	
	JLabel invalid;

	public InvalidRegistrationFrame(Client c) {
		super(c);
		this.setLayout(new GridLayout(9,1));
		invalid = new JLabel("Registration Failed - Make sure to fill in all fields and pick a unique username",SwingConstants.CENTER);
		invalid.setForeground(Color.RED);
		this.add(invalid,0);
		this.setMinimumSize(new Dimension(300,550));
		this.setPreferredSize(new Dimension(300,550));
	}

}
