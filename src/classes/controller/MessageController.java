package classes.controller;

import classes.model.Message;
import classes.model.MessageModel;
import classes.view.MessageView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class MessageController {
	
	public MessageController() {}
	
	public ArrayList<JPanel> getMessages(Connection con) {
        ArrayList<JPanel> messagePanels = new ArrayList<>();
        try {
            ArrayList<Message> messages = MessageModel.getMessages(con);
            for (int i = 0; i < messages.size(); i++) {
                Message message = messages.get(i);
                JPanel messagePanel = MessageView.createMessagePanel(message);
                messagePanels.add(messagePanel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return messagePanels;
    }
    
}