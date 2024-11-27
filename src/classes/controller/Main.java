package classes.controller;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import classes.model.ConnectionBDModel;
import classes.model.MessageModel;
import classes.model.UserModel;
import classes.view.ChatView;
import classes.model.User;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		Connection con = null;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatView.main(null);
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error al conectar a la base de datos", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error al conectar a la base de datos", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } catch (Exception e) {
                	JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                	e.printStackTrace();
                }
			}
		});
		
	}
	
}
