package classes.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MessageModel extends ConnectionBDModel {

	public static ArrayList<Message> getMessages(Connection con) throws SQLException {
	    ArrayList<Message> messages = new ArrayList<>();
	    CallableStatement cs = null;
	    try {
	        cs = con.prepareCall("{call getMessages}");
	        cs.execute();
	        while (cs.getResultSet().next()) {
	            Message message = new Message(
	                cs.getResultSet().getString("nick"),
	                cs.getResultSet().getString("message"),
	                cs.getResultSet().getTimestamp("ts")
	            );
	            messages.add(message);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    } finally {
	        if (cs != null) {
	            try {
	                cs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }
	    return messages;
	}
	
	public static void sendMessage(Connection con, String message) throws SQLException {
		CallableStatement cs = null;
		try {
			cs = con.prepareCall("{call send(?)}");
			cs.setString(1, message);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (cs != null) {
				cs.close();
			}
		}
	}
	
	
}
