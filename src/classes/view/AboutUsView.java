package classes.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.ComponentOrientation;
import java.awt.Color;

public class AboutUsView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutUsView window = new AboutUsView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AboutUsView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(53, 53, 53));
		frame.setBounds(100, 100, 450, 150);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel pnl_about_us = new JPanel();
		pnl_about_us.setBackground(new Color(53, 53, 53));
		pnl_about_us.setBounds(0, 0, 450, 1);
		frame.getContentPane().add(pnl_about_us);
		pnl_about_us.setLayout(new BoxLayout(pnl_about_us, BoxLayout.X_AXIS));
		
		JLabel lblAboutUs = new JLabel("About Us");
		lblAboutUs.setForeground(new Color(255, 255, 255));
		lblAboutUs.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblAboutUs.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblAboutUs.setFont(new Font("URW Gothic", Font.BOLD, 24));
		lblAboutUs.setBounds(12, 13, 426, 47);
		lblAboutUs.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblAboutUs);
		
		JLabel lblAixsUna = new JLabel("<html>Chat de la clase de DAW2 del institut Thos i Codina. App creada per a la pràctica de M3 - UF6</html>");
		lblAixsUna.setForeground(new Color(255, 255, 255));
		lblAixsUna.setFont(new Font("URW Gothic", Font.BOLD, 12));
	    lblAixsUna.setHorizontalAlignment(JLabel.LEFT);
	    lblAixsUna.setBounds(22, 44, 400, 40);
	    frame.getContentPane().add(lblAixsUna);
	    
	    JLabel lblFetPerHctor = new JLabel("Fet per: Héctor Monroy");
	    lblFetPerHctor.setForeground(new Color(255, 255, 255));
	    lblFetPerHctor.setFont(new Font("URW Gothic", Font.BOLD, 12));
	    lblFetPerHctor.setBounds(22, 103, 416, 17);
	    frame.getContentPane().add(lblFetPerHctor);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setResizable(false);
	}
	
	public JFrame getFrame() {
        return frame;
    }
}
