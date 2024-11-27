package classes.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class UserModel extends ConnectionBDModel {
	
	public static boolean connectUserToChat(Connection con, String nick) throws SQLException {
		
		CallableStatement cs = null;
		try {
			cs = con.prepareCall("{call connect(?)}");
			cs.setString(1, nick);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
			return false;
		} finally {
			if (cs != null) {
				cs.close();
			}
		}
		
		return true;
	}
	
	public static void disconnectUserFromChat(Connection con) throws SQLException {

		CallableStatement cs = null;
		try {
			cs = con.prepareCall("{call disconnect}");
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
	
	public static ArrayList<User> getConnectedUsers(Connection con) throws SQLException {
	    ArrayList<User> users = new ArrayList<>();
	    CallableStatement cs = null;
	    try {
	        cs = con.prepareCall("{call getConnectedUsers}");
	        cs.execute();
	        while (cs.getResultSet().next()) {
	            String nick = cs.getResultSet().getString("nick");
	            Timestamp dateCon = cs.getResultSet().getTimestamp("date_con");
	            users.add(new User(nick, null, dateCon, 0));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    } finally {
	        if (cs != null) {
	            cs.close();
	        }
	    }
	    return users;
	}
	
}
