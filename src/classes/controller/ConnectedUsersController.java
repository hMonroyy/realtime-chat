package classes.controller;

import classes.view.ConnectedUsersView;

import javax.swing.JButton;

import java.sql.Connection;
import java.util.ArrayList;

public class ConnectedUsersController {
    private ConnectedUsersView view;

    public ConnectedUsersController(Connection con) {
        this.view = new ConnectedUsersView(con);
        this.view.updateConnectedUsers(con);
    }

    public void updateConnectedUsers(Connection con) {
        view.updateConnectedUsers(con);
    }
    
    public void showView() {
        view.setVisible(true);
    }
    
    public int getConnectedUsersCount() {
        return view.getConnectedUsersCount();
    }
    
    public ArrayList<JButton> getUserButtons() {
        return view.getUserButtons();
    }
}