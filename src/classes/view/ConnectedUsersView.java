package classes.view;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import classes.model.User;
import classes.model.UserModel;

public class ConnectedUsersView extends JPanel {
	/**
	 
	 Uso arraylist para poder añadir y eliminar usuarios de la lista de usuarios conectados. Me permite mantener un orden
	 en los mensajes y usuarios (para luego poderlos listar en orden, hashset y hashmap no me permiten esto). 
	 
	 */
	private ArrayList<User> connectedUsers;
    private JButton[] userButtons;

    public ConnectedUsersView(Connection con) {
        try {
        	connectedUsers = UserModel.getConnectedUsers(con);
            initializeUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        setLayout(new GridLayout(0, 1, 0, 10));
        userButtons = new JButton[connectedUsers.size()];

        SimpleDateFormat fechaUsuario = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaUsuario = new SimpleDateFormat("HH:mm");
        
        ImageIcon userIcon = new ImageIcon(new ImageIcon("img/steam-profile.jpg").getImage().getScaledInstance(50, 50, 0));
        Color borderColor = new Color(93, 139, 198);
        ImageIcon userIconWithBorder = new ImageIcon(addBorderToImage(userIcon, 3, borderColor));     
        
        for (int i = 0; i < connectedUsers.size(); i++) {
        	User user = connectedUsers.get(i);
            
            String fecha = "";
            String hora = "";
            
			if (user.getDate() != null) {
				fecha = fechaUsuario.format(user.getDate());
				hora = horaUsuario.format(user.getDate());
			} else {
				fecha = "Desconocido";
				hora = "Desconocido";
			}

			userButtons[i] = new JButton("<html>" + user.getNick() + "<br><small>Última vez: " + hora + " del " + fecha + "</small></html>");
			userButtons[i].setOpaque(true);
			userButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
			userButtons[i].setBackground(new Color(29, 32, 37));
			userButtons[i].setForeground(new Color(93, 139, 198));
			userButtons[i].setBorder(new EmptyBorder(10, 10, 10, 10));
			userButtons[i].setIcon(userIconWithBorder);
			userButtons[i].setIconTextGap(10);
			userButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			userButtons[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, userButtons[i].getMinimumSize().height));
            
            add(userButtons[i]);

            if (i < connectedUsers.size() - 1) {
                add(Box.createVerticalStrut(10));
            }
        }
    }
    
    private BufferedImage addBorderToImage(ImageIcon icon, int borderSize, Color borderColor) {
        BufferedImage image = new BufferedImage(icon.getIconWidth() + borderSize, icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(icon.getImage(), 0, 0, null);
        g.setColor(borderColor);
        g.fillRect(icon.getIconWidth(), 0, borderSize, icon.getIconHeight());
        g.dispose();
        return image;
    }

    public void updateConnectedUsers(Connection con) {
        try {
            connectedUsers = UserModel.getConnectedUsers(con);
            removeAll();
            initializeUI();
            revalidate();
            repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	public int getConnectedUsersCount() {
		return connectedUsers.size();
	}
    
    public ArrayList<JButton> getUserButtons() {
        return new ArrayList<>(Arrays.asList(userButtons));
    }
}