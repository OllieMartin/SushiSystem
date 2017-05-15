
package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import comms.ClientComms;

//http://www.mediafire.com/file/9gn4vsbqpdwfmol/TeaParty.jar

public class Client {

	ClientComms comms = new ClientComms(this);
    protected JFrame frame = new JFrame("TeaParty");
    public JTextField textField = new JTextField(40);
    protected JTextPane messageArea = new JTextPane();
    public StyledDocument doc = messageArea.getStyledDocument();
    
    public Client() {
    	Thread t = new Thread(comms);
    	t.start();
        // Layout GUI
        textField.setEditable(false);
        textField.setMaximumSize(
            new Dimension(Integer.MAX_VALUE,
            textField.getPreferredSize().height));
        //textField.setMaximumSize( textField.getPreferredSize() );
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalGlue());
        box.add(textField);
        box.add(Box.createVerticalGlue());
        messageArea.setEditable(false);
        frame.getContentPane().add(box, "Center");
        //messageArea.setBounds(0,0,400,400);
        JScrollPane p = new JScrollPane(messageArea);
        p.setPreferredSize(new Dimension(200,200));
        frame.getContentPane().add(p, "South");
        frame.pack();

        // Add Listeners
        textField.addActionListener(new ActionListener() {
            /**
			 * When enter is pressed the message will be sent to the server
			 * The box is then cleared
             */
            public void actionPerformed(ActionEvent e) {
                comms.sendMessage(textField.getText());
                textField.setText("");
            }
        });
    }

    //Get their display name from a message box
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }
    
    public static void main(String[] args) throws Exception {
       Client client = new Client(); //Create a new chat client
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //When JFrame closed, close application
        client.frame.setVisible(true); //Make the JFrame visible
    }
	
}

