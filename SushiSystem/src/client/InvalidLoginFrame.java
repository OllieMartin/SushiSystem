package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InvalidLoginFrame extends LoginFrame {

	private static final long serialVersionUID = 1L;
	
	JLabel invalid;

	public InvalidLoginFrame(Client c) {
		super(c);
		this.setLayout(new GridLayout(4,1));
		invalid = new JLabel("INVALID CREDENTIALS, TRY AGAIN",SwingConstants.CENTER);
		invalid.setForeground(Color.RED);
		this.add(invalid,0);
		this.setMinimumSize(new Dimension(300,200));
		this.setPreferredSize(new Dimension(300,200));
	}

}
