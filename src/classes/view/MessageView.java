package classes.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import classes.model.Message;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessageView extends JPanel {
    
	public MessageView() {}
	
	public static JPanel createMessagePanel(Message message) {
		JPanel pnlMensaje = new JPanel();
        pnlMensaje.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlMensaje.setBackground(new Color(30, 33, 38));
        pnlMensaje.setLayout(new BorderLayout());

        JPanel pnlInfoUsuario = new JPanel();
        pnlInfoUsuario.setBackground(new Color(50, 55, 60));
        pnlInfoUsuario.setLayout(new BoxLayout(pnlInfoUsuario, BoxLayout.X_AXIS));
        pnlInfoUsuario.setBorder(new EmptyBorder(10, 10, 10, 10));

        ImageIcon iconoUsuario = new ImageIcon("img/steam-profile.jpg");
        iconoUsuario = new ImageIcon(iconoUsuario.getImage().getScaledInstance(60, 60, 0));
        JLabel lblImagenUsuario = new JLabel();
        lblImagenUsuario.setIcon(iconoUsuario);
        pnlInfoUsuario.add(lblImagenUsuario);

        JPanel pnlNombreFecha = new JPanel();
        pnlNombreFecha.setBackground(new Color(50, 55, 60));
        pnlNombreFecha.setLayout(new BoxLayout(pnlNombreFecha, BoxLayout.Y_AXIS));

        JLabel lblNombreUsuario = new JLabel(message.getNick());
        lblNombreUsuario.setForeground(Color.WHITE);
        lblNombreUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        pnlNombreFecha.add(lblNombreUsuario);

        
        SimpleDateFormat fechaSDF = new SimpleDateFormat("HH:mm");
        JLabel lblFechaMensaje = new JLabel(fechaSDF.format(message.getDate()));
        lblFechaMensaje.setForeground(Color.WHITE);
        lblFechaMensaje.setFont(new Font("Arial", Font.PLAIN, 14));
        pnlNombreFecha.add(lblFechaMensaje);

        JTextArea txtMensajeEnviado = new JTextArea(message.getMessage());
        txtMensajeEnviado.setForeground(Color.WHITE);
        txtMensajeEnviado.setFont(new Font("Arial", Font.PLAIN, 14));
        txtMensajeEnviado.setBackground(new Color(50, 55, 60));
        txtMensajeEnviado.setLineWrap(true);
        txtMensajeEnviado.setWrapStyleWord(true);
        txtMensajeEnviado.setEditable(false);
        txtMensajeEnviado.setBorder(new EmptyBorder(10, 80, 10, 10));

        pnlInfoUsuario.add(Box.createHorizontalStrut(10));
        pnlInfoUsuario.add(pnlNombreFecha);
        pnlMensaje.add(pnlInfoUsuario, BorderLayout.NORTH);
        pnlMensaje.add(txtMensajeEnviado, BorderLayout.CENTER);

        pnlMensaje.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        return pnlMensaje;
    }
	
}