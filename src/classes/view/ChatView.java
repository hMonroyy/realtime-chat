package classes.view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import Exceptions.MessageInvalidException;
import Exceptions.NickInvalidException;
import classes.controller.ConnectedUsersController;
import classes.model.ConnectionBDModel;
import classes.model.MessageModel;
import classes.model.UserModel;
import classes.controller.MessageController;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.ScrollPaneConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ChatView {

	private JFrame frame;
	private JTextField inputNick;
	private boolean isConnected = false;

	private Connection con = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatView window = new ChatView();
					window.frame.setVisible(true);
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error al conectar a la base de datos", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error al conectar a la base de datos", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } catch (SQLException e) {
                	JOptionPane.showMessageDialog(null, e.getMessage(), "Error al conectar a la base de datos", JOptionPane.ERROR_MESSAGE);
                	e.printStackTrace();
                } catch (Exception e) {
                	JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                	e.printStackTrace();
                }
			}
		});
	}
	
	private Connection tryNewConnection(Connection newCon) {
        if (newCon != null) {
            con = newCon;
            JOptionPane.showMessageDialog(null, "Conexión cambiada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión.");
        }
		return newCon;
    }

	/**
	 * Create the application.
	 * 
	 * @throws SQLException
	 * @throws NullPointerException
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public ChatView() throws ArrayIndexOutOfBoundsException, NullPointerException, SQLException {
		try {
			// LOCAL
			con = ConnectionBDModel.connectToBDLocal();

			// CLASE
//			con = ConnectionBDModel.connectToBDClase();
			
			// Personalizado
//			con = ConnectionBDModel.connectToBDPersonalizado("192.168.119.26");
			if (con == null) {
				throw new NullPointerException("Error al conectar a la base de datos");
			}
			initialize();
		} catch (NullPointerException e) {
			throw new NullPointerException(e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException(e.getMessage());
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws SQLException, NullPointerException, ArrayIndexOutOfBoundsException {
		frame = new JFrame();
		frame.setBounds(100, 100, 1600, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Chat DAW2 - M3 UF6");
		frame.setIconImage(new ImageIcon("img/steam-chat-icon.jpg").getImage());

		// AL CERRAR LA VENTANA
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					UserModel.disconnectUserFromChat(con);
					ConnectionBDModel.disconnectFromBD(con);
				} catch (SQLException e1) {
					throw new RuntimeException(e1);
				}
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnArchivoMenu = new JMenu("Archivo");
		menuBar.add(mnArchivoMenu);
		
		
		JMenuItem mntmAboutUs = new JMenuItem("Sobre el chat");
		mntmAboutUs.setAccelerator(KeyStroke.getKeyStroke("control shift A"));
		mnArchivoMenu.add(mntmAboutUs);
		mntmAboutUs.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	AboutUsView aboutUsPanel = new AboutUsView();
		        aboutUsPanel.initialize();
		        aboutUsPanel.getFrame().setVisible(true);
		    }
		});
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setAccelerator(KeyStroke.getKeyStroke("control shift S"));
		mnArchivoMenu.add(mntmSalir);
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenu mnOpcionesMenu = new JMenu("Opciones");
		menuBar.add(mnOpcionesMenu);
		
		JMenuItem mntmCambiarCon = new JMenuItem("Cambiar conexión");
		mntmCambiarCon.setAccelerator(KeyStroke.getKeyStroke("control shift C"));
		mnOpcionesMenu.add(mntmCambiarCon);
		
		
		// PANEL PRINCIPAL
		JPanel pnlApp = new JPanel();
		frame.getContentPane().add(pnlApp, BorderLayout.CENTER);
		pnlApp.setLayout(new BorderLayout());
		pnlApp.setBackground(new Color(29, 32, 37));

		
		// PANEL NORTE DE APP
		JPanel pnlConectar = new JPanel();
		pnlConectar.setBorder(null);
		pnlApp.add(pnlConectar, BorderLayout.NORTH);
		pnlConectar.setBackground(new Color(58, 62, 71));
		
		
		// PANEL GRUPO
		ImageIcon icono = new ImageIcon("img/steam-chat-icon.jpg");
		icono = new ImageIcon(icono.getImage().getScaledInstance(60, 60, 0));
		pnlConectar.setLayout(new BorderLayout(0, 0));

		JPanel pnlFotoChat = new JPanel();
		pnlFotoChat.setBackground(new Color(58, 62, 71));
		pnlConectar.add(pnlFotoChat, BorderLayout.WEST);
		
		JLabel lblFotoChat = new JLabel(icono);
		pnlFotoChat.add(lblFotoChat);
		
		JLabel lblTextoChat = new JLabel("Chat DAW2");
		lblTextoChat.setForeground(Color.WHITE);
		lblTextoChat.setFont(new Font("Arial", Font.BOLD, 24));
		lblTextoChat.setBorder(new EmptyBorder(0, 10, 0, 0));
		pnlFotoChat.add(lblTextoChat);

		
		// PANEL DE CONEXIÓN
		JPanel pnlConexion = new JPanel();
		pnlConexion.setBackground(new Color(58, 62, 71));
		pnlConectar.add(pnlConexion);
		
		inputNick = new JTextField();
		inputNick.setPreferredSize(new Dimension(300, 45));
		inputNick.setForeground(new Color(255, 255, 255));
		inputNick.setBackground(new Color(68, 78, 87));
		inputNick.setFont(new Font("Arial", Font.PLAIN, 18));
		inputNick.setCaretColor(new Color(255, 255, 255));
		inputNick.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlConexion.add(inputNick);

		JButton btnConectarse = new JButton("Conectarse");
		btnConectarse.setFocusable(false);
		btnConectarse.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnConectarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConectarse.setPreferredSize(new Dimension(200, 45));
		btnConectarse.setFont(new Font("Arial", Font.BOLD, 16));
		btnConectarse.setBackground(new Color(93, 139, 198));
		btnConectarse.setForeground(Color.WHITE);
		
		btnConectarse.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        btnConectarse.setBackground(new Color(123, 169, 228));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        btnConectarse.setBackground(new Color(93, 139, 198));
		    }

		    @Override
		    public void mousePressed(MouseEvent e) {
		        btnConectarse.setBackground(new Color(73, 119, 178));
		    }

		    @Override
		    public void mouseReleased(MouseEvent e) {
		        btnConectarse.setBackground(new Color(93, 139, 198));
		    }
		});
		
		pnlConexion.add(btnConectarse);

		JButton btnDesconectarse = new JButton("Desconectarse");
		btnDesconectarse.setFocusable(false);
		btnDesconectarse.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDesconectarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDesconectarse.setPreferredSize(new Dimension(200, 45));
		btnDesconectarse.setFont(new Font("Arial", Font.BOLD, 16));
		btnDesconectarse.setBackground(new Color(93, 139, 198));
		btnDesconectarse.setForeground(Color.WHITE);
		btnDesconectarse.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        btnDesconectarse.setBackground(new Color(123, 169, 228));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        btnDesconectarse.setBackground(new Color(93, 139, 198));
		    }

		    @Override
		    public void mousePressed(MouseEvent e) {
		        btnDesconectarse.setBackground(new Color(73, 119, 178));
		    }

		    @Override
		    public void mouseReleased(MouseEvent e) {
		        btnDesconectarse.setBackground(new Color(93, 139, 198));
		    }
		});
		pnlConexion.add(btnDesconectarse);
		btnDesconectarse.setVisible(false);
		
		ImageIcon iconoInvisible = new ImageIcon("img/Transparente.png");
		iconoInvisible = new ImageIcon(iconoInvisible.getImage().getScaledInstance(60, 60, BufferedImage.TYPE_INT_ARGB));
		
		JLabel lblInvisible = new JLabel(iconoInvisible);
		pnlConexion.add(lblInvisible);

		
        // PANEL DE USUARIOS CONECTADOS
		JPanel pnlConectados = new JPanel();
		pnlApp.add(pnlConectados, BorderLayout.EAST);
		pnlConectados.setLayout(new BoxLayout(pnlConectados, BoxLayout.Y_AXIS));
		pnlConectados.setBackground(new Color(29, 32, 37));

		
		// USUARIOS CONECTADOS
		ConnectedUsersController connectedUsersController = new ConnectedUsersController(con);
		int connectedUsersCount = connectedUsersController.getConnectedUsersCount();
		JLabel lblNumUsers = new JLabel("Usuarios conectados: " + connectedUsersCount);
		lblNumUsers.setFont(new Font("Arial", Font.BOLD, 18));
		lblNumUsers.setForeground(Color.WHITE);
		lblNumUsers.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lblNumUsers.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlConectados.add(lblNumUsers);

		ArrayList<JButton> userButtons = connectedUsersController.getUserButtons();

		pnlConectados.setBorder(null);
		pnlConectados.setPreferredSize(new Dimension(300, 200));
		pnlConectados.setLayout(new BoxLayout(pnlConectados, BoxLayout.Y_AXIS));

		for (int i = 0; i < userButtons.size(); i++) {
			JButton userButton = userButtons.get(i);
			userButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			pnlConectados.add(userButton);

			if (i < userButtons.size() - 1) {
				pnlConectados.add(Box.createVerticalStrut(10));
			}
		}
		
		
		// PANEL DE CHAT
		JPanel pnlChat = new JPanel();
		pnlChat.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		pnlChat.setBorder(null);
		pnlChat.setLayout(new BorderLayout());

		
		// PANEL PARA MOSTRAR MENSAJES
		JPanel pnlMensajes = new JPanel();
		pnlMensajes.setBackground(new Color(30, 33, 38));
		pnlMensajes.setLayout(new BoxLayout(pnlMensajes, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(pnlMensajes);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBorder(null);
		pnlChat.add(scrollPane, BorderLayout.CENTER);

		
		// PANEL PARA ENVIAR MENSAJES
		JPanel pnlEnviarMensaje = new JPanel();
		pnlEnviarMensaje.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		pnlEnviarMensaje.setBackground(new Color(58, 62, 71));
		pnlEnviarMensaje.setLayout(new BoxLayout(pnlEnviarMensaje, BoxLayout.X_AXIS));
		pnlEnviarMensaje.setBorder(new EmptyBorder(10, 10, 10, 10));

		JTextField txtMensaje = new JTextField();
		txtMensaje.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		txtMensaje.setBorder(new EmptyBorder(10, 10, 10, 10));
		txtMensaje.setPreferredSize(new Dimension(300, 45));
		txtMensaje.setForeground(new Color(255, 255, 255));
		txtMensaje.setBackground(new Color(26, 28, 32));
		txtMensaje.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMensaje.setCaretColor(new Color(255, 255, 255));

		ImageIcon iconoEnviar = new ImageIcon("img/flecha-enviar.png");
		iconoEnviar = new ImageIcon(iconoEnviar.getImage().getScaledInstance(35, 35, 0));

		JButton btnEnviar = new JButton();
		btnEnviar.setFocusable(false);
		btnEnviar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEnviar.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnEnviar.setIcon(iconoEnviar);
		btnEnviar.setPreferredSize(new Dimension(45, 45));
		btnEnviar.setBackground(new Color(26, 28, 32));
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseEntered(MouseEvent e) {
				btnEnviar.setBackground(new Color(19, 20, 23));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnEnviar.setBackground(new Color(26, 28, 32));
		    }

		    @Override
		    public void mousePressed(MouseEvent e) {
		    	btnEnviar.setBackground(new Color(18, 15, 15));
		    }

		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	btnEnviar.setBackground(new Color(26, 28, 32));
		    }			
		});

		pnlEnviarMensaje.add(txtMensaje);
		pnlEnviarMensaje.add(Box.createHorizontalStrut(10));
		pnlEnviarMensaje.add(btnEnviar);
		pnlEnviarMensaje.setVisible(false);
		pnlChat.add(pnlEnviarMensaje, BorderLayout.SOUTH);		
		
		
		// TIMER
		Timer timer = new Timer(2000, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        connectedUsersController.updateConnectedUsers(con);
		        int connectedUsersCount = connectedUsersController.getConnectedUsersCount();
		        lblNumUsers.setText("Usuarios conectados: " + connectedUsersCount);

		        ArrayList<JButton> newUserButtons = connectedUsersController.getUserButtons();

		        pnlConectados.removeAll();
		        pnlConectados.add(lblNumUsers);

		        for (int i = 0; i < newUserButtons.size(); i++) {
		            JButton userButton = newUserButtons.get(i);
		            userButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		            pnlConectados.add(userButton);

		            if (i < newUserButtons.size() - 1) {
		                pnlConectados.add(Box.createVerticalStrut(10));
		            }
		        }

		        pnlConectados.revalidate();
		        pnlConectados.repaint();

		        if (isConnected) {
		            MessageController messageController = new MessageController();
		            ArrayList<JPanel> messagePanels = messageController.getMessages(con);
		            for (int i = 0; i < messagePanels.size(); i++) {
		                JPanel messagePanel = messagePanels.get(i);
		                pnlMensajes.add(messagePanel);
		            }
		            pnlMensajes.revalidate();
		            pnlMensajes.repaint();
		        }
		    }
		});
		timer.start();

		
		// KEYLISTENER
		inputNick.addKeyListener(new KeyAdapter() {
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	try {
						if (inputNick.getText().equals("")) {
							throw new NickInvalidException("El nick no puede estar vacío", "A01");
						} else if (inputNick.getText().length() > 50) {
							throw new NickInvalidException("El nick no puede tener más de 50 caracteres", "A02");
						}

						isConnected = UserModel.connectUserToChat(con, inputNick.getText());

						if (isConnected) {

							System.out.println("Usuario \"" + inputNick.getText() + "\" conectado");
							btnConectarse.setVisible(false);
							pnlEnviarMensaje.setVisible(true);
							inputNick.setVisible(false);
							btnDesconectarse.setVisible(true);

						} else {
							System.out.println("Error: Ya existe un usuario con ese nick");
						}

					} catch (NickInvalidException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al conectarse al chat", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al conectarse al chat", JOptionPane.ERROR_MESSAGE);
					}
		        }
		    }
		});
		
		txtMensaje.addKeyListener(new KeyAdapter() {
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            try {
		                if (txtMensaje.getText().equals("")) {
		                    throw new MessageInvalidException("El mensaje no puede estar vacío", "A01");
		                } else if (txtMensaje.getText().length() > 255) {
		                    throw new MessageInvalidException("El mensaje no puede tener más de 255 caracteres", "A02");
		                }

		                MessageModel.sendMessage(con, txtMensaje.getText());
		                System.out.println("Mensaje enviado");
		                txtMensaje.setText("");
		            } catch (MessageInvalidException e1) {
		                e1.printStackTrace();
		                JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al enviar mensaje", JOptionPane.ERROR_MESSAGE);
		            } catch (SQLException e1) {
		                e1.printStackTrace();
		            } 
		        }
		    }
		});
		
		
		// ACTION LISTENERS
		btnConectarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (inputNick.getText().equals("")) {
						throw new NickInvalidException("El nick no puede estar vacío", "A01");
					} else if (inputNick.getText().length() > 50) {
						throw new NickInvalidException("El nick no puede tener más de 50 caracteres", "A01");
					}

					isConnected = UserModel.connectUserToChat(con, inputNick.getText());

					if (isConnected) {

						System.out.println("Usuario \"" + inputNick.getText() + "\" conectado");
						btnConectarse.setVisible(false);
						pnlEnviarMensaje.setVisible(true);
						inputNick.setVisible(false);
						btnDesconectarse.setVisible(true);

					} else {
						System.out.println("Error: Ya existe un usuario con ese nick");
					}

				} catch (NickInvalidException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al conectarse al chat", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al conectarse al chat", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnDesconectarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					UserModel.disconnectUserFromChat(con);
					isConnected = false;
					System.out.println("Usuario \"" + inputNick.getText() + "\" desconectado");

					btnDesconectarse.setVisible(false);
					pnlEnviarMensaje.setVisible(false);
					btnConectarse.setVisible(true);
					inputNick.setText("");
					inputNick.setVisible(true);

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al desconectarse del chat", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					if (txtMensaje.getText().equals("")) {
						throw new MessageInvalidException("El mensaje no puede estar vacío", "A01");
					} else if (txtMensaje.getText().length() > 255) {
						throw new MessageInvalidException("El mensaje no puede tener más de 255 caracteres", "A02");
					}
					
					MessageModel.sendMessage(con, txtMensaje.getText());
					System.out.println("Mensaje enviado");
					txtMensaje.setText("");
				} catch (MessageInvalidException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al enviar mensaje", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
			}
		});		
		
		mntmCambiarCon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Object[] opciones = {"Localhost", "Toni", "Personalizado"};
		        int n = JOptionPane.showOptionDialog(null, "Seleccione la conexión:", "Cambiar conexión", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
		        
		        Connection antiguaCon = con;

		        switch (n) {
		            case 0:
						try {
							UserModel.disconnectUserFromChat(con);
							isConnected = false;
							con = tryNewConnection(ConnectionBDModel.connectToBDLocal());
							pnlMensajes.removeAll();
							btnDesconectarse.setVisible(false);
							pnlEnviarMensaje.setVisible(false);
							btnConectarse.setVisible(true);
							inputNick.setText("");
							inputNick.setVisible(true);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al desconectarse del chat", JOptionPane.ERROR_MESSAGE);	
							e1.printStackTrace();
							con = antiguaCon;
							isConnected = true;
						}
		                break;
		            case 1:
						try {
							UserModel.disconnectUserFromChat(con);
							isConnected = false;
							con = tryNewConnection(ConnectionBDModel.connectToBDToni());
							pnlMensajes.removeAll();
							btnDesconectarse.setVisible(false);
							pnlEnviarMensaje.setVisible(false);
							btnConectarse.setVisible(true);
							inputNick.setText("");
							inputNick.setVisible(true);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al desconectarse del chat", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
							con = antiguaCon;
							isConnected = true;
						}
		                break;
		            case 2:
		                String nuevaURL = JOptionPane.showInputDialog("Ingrese la nueva IP:");
		                if (nuevaURL != null && !nuevaURL.isEmpty()) {
		                	try {
								UserModel.disconnectUserFromChat(con);
								isConnected = false;
								con = tryNewConnection(ConnectionBDModel.connectToBDPersonalizado(nuevaURL));
								pnlMensajes.removeAll();
								btnDesconectarse.setVisible(false);
								pnlEnviarMensaje.setVisible(false);
								btnConectarse.setVisible(true);
								inputNick.setText("");
								inputNick.setVisible(true);
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al desconectarse del chat", JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
								con = antiguaCon;
								isConnected = true;
							}
							
		                }
		                break;
		        }
		    }
		});

		pnlApp.add(pnlChat, BorderLayout.CENTER);
		
	}

}
