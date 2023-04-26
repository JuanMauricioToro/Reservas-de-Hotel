package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

import Controller.ControladorHuesped;
import Controller.ControladorReserva;
import Modelado.Huesped;
import Modelado.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.naming.InitialContext;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private ControladorReserva controllerReserva = new ControladorReserva();
	private ControladorHuesped controllerHuesped = new ControladorHuesped();
	private List<Huesped> huespedes = new ArrayList<>();
	private List<Huesped> listHues = new ArrayList<>();
	private List<Reserva> reservas = new ArrayList<>();
	private List<Reserva> listReserv = new ArrayList<>();
	private static int control = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 210, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtBuscar.setText("Id de Reserva ó Apellido del Huesped");
		txtBuscar.setFont(new Font("Roboto Black", Font.BOLD, 10));
		txtBuscar.setForeground(new Color(208, 208, 225));
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 18));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table,
				null);
		scroll_table.setVisible(true);

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 200, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		if (panel.getSelectedIndex() == 0) {
			txtBuscar.setText("Id de Reserva ó Apellido del Huesped");
			txtBuscar.setForeground(new Color(208, 208, 225));
		}
		panel.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {

				if (panel.getSelectedIndex() == 0) {
					txtBuscar.setVisible(true);
					txtBuscar.setFocusable(true);
					btnbuscar.setVisible(true);
					separator_1_2.setVisible(true);
					txtBuscar.setText("Id de Reserva ó Apellido del Huesped");
					txtBuscar.setForeground(new Color(208, 208, 225));

				}

				if (panel.getSelectedIndex() == 1) {
					txtBuscar.setVisible(false);
					btnbuscar.setVisible(false);
					separator_1_2.setVisible(false);

				}
			}
		});

		txtBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtBuscar.getText().equals("Id de Reserva ó Apellido del Huesped")) {
					txtBuscar.setText("");
				}

			}

		});

	

		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
		reservas = this.controllerReserva.mostrarReservas(txtBuscar.getText());
		huespedes = this.controllerHuesped.mostrarHuespedes(txtBuscar.getText());
		
		//muestra los registros de reservas y huespedes al cargar la view
		mostrarReservas();
		
		
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				mostrarReservas();

			}
		});

		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(panel.getSelectedIndex()==0) {
					eliminarReserva();
					}
				if(panel.getSelectedIndex()==1) {
					eliminarHuesped();
				}
				
			}
			
		});
		
		
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(panel.getSelectedIndex()==0) {
				editarReserva();
				}
				if(panel.getSelectedIndex()==1) {
					editarHuesped();
				}
				

			}
		});

	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}

	
	//busca y muestra una reserva y su huesped relacionado,busqueda por idReserva, por nombre del huesped ó por apellido del huesped
	public void mostrarReservas() {
		limpiarTablaReserva();
		limpiarTablaHuespedes();

		String ape = null;
		String idReser = null;
		
		for (Huesped huesped : huespedes) {
			if (huesped.getApellido().equalsIgnoreCase(txtBuscar.getText()) || huesped.getNombre().equalsIgnoreCase(txtBuscar.getText())) {
				ape = huesped.getApellido();
				idReser = huesped.getIdReserva();
				break;
			}
		}
		
		if(ape==null && idReser==null) {
			reservas = this.controllerReserva.mostrarReservas(txtBuscar.getText());
			huespedes = this.controllerHuesped.mostrarHuespedes(txtBuscar.getText());
		}
			
		
		if (idReser != null && ape != null) {
			listReserv = this.controllerReserva.mostrarReservasApellido(idReser);
			listHues = this.controllerHuesped.mostrarHuespedesApellido(ape);
		}
		boolean bandera = true;

		for (Reserva reserva2 : reservas) {
			if (reserva2.getId().equals(txtBuscar.getText())
					|| txtBuscar.getText().equals("Id de Reserva ó Apellido del Huesped")
					|| txtBuscar.getText().equals("")) {

				reservas.forEach(reserva -> modelo.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(),
						reserva.getFechaSalida(), reserva.getValor(), reserva.getMetodoPago() }));

				huespedes.forEach(huesp -> modeloHuesped.addRow(new Object[] { huesp.getId(), huesp.getNombre(),
						huesp.getApellido(), huesp.getFechaNacimiento(), huesp.getNacionalidad(), huesp.getTelefono(),
						huesp.getIdReserva() }));
				bandera = false;

				break;

			}
		}

		if (bandera) {

			listReserv.forEach(reserva -> modelo.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(),
					reserva.getFechaSalida(), reserva.getValor(), reserva.getMetodoPago() }));

			listHues.forEach(huesp -> modeloHuesped.addRow(
					new Object[] { huesp.getId(), huesp.getNombre(), huesp.getApellido(), huesp.getFechaNacimiento(),
							huesp.getNacionalidad(), huesp.getTelefono(), huesp.getIdReserva() }));

		}

	}

	//edita una reserva
	public void editarReserva() {
		if (tieneFilaElegidaReserva()) {
			JOptionPane.showMessageDialog(this, "Por favor, elije una reserva");

		} else

			Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
					.ifPresentOrElse(fila -> {
						String id = (String) (modelo.getValueAt(tbReservas.getSelectedRow(), 0));
						String fecha_entrada = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString();
						String fecha_salida = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString();
						Double valor = Double.valueOf((modelo.getValueAt(tbReservas.getSelectedRow(), 3)).toString());
						String forma_pago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
						
						boolean res = false;
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-DD");
						try {
							Date feI = sf.parse(fecha_entrada);
							Date feS = sf.parse(fecha_salida);
							res = feI.before(feS);
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
						int filasModificadas=0;
						
						if(res) {
						
						filasModificadas = this.controllerReserva.editarReserva(id, fecha_entrada, fecha_salida,
								valor, forma_pago);
						}else {
							JOptionPane.showMessageDialog(null, "Ingrese una fecha válida");
						}
						
						
						if(filasModificadas>0) {
						JOptionPane.showMessageDialog(this,
								String.format("%d item modificado con éxito!", filasModificadas));
								Busqueda b = new Busqueda();
								b.setVisible(true);
								dispose();
						}else if(filasModificadas<=0){
							JOptionPane.showMessageDialog(this,
									String.format("El id de la reserva no se puede modificar"));
							Busqueda b = new Busqueda();
							b.setVisible(true);
							dispose();
						}

						tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));

	}
	
	//edita un huesped
	public void editarHuesped() {
		if(tieneFilaElegidaHuesped()) {
			JOptionPane.showMessageDialog(this, "Por favor, elije un huesped");
		}else
			Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
			.ifPresentOrElse(fila -> {
				String id = (String) (modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0));
				String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
				String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
				String fecha_nacimiento = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString();
				String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
				String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
				String idReserva = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6);

				int filasModificadas = this.controllerHuesped.editarHuesped(id, nombre, apellido,
						fecha_nacimiento, nacionalidad,telefono,idReserva);
				if(filasModificadas>0) {
				JOptionPane.showMessageDialog(this,
						String.format("%d item modificado con éxito!", filasModificadas));
						Busqueda b = new Busqueda();
						b.setVisible(true);
						dispose();
						
				}else {
					JOptionPane.showMessageDialog(this,
							String.format("El id de la reserva no se puede modificar"));
					Busqueda b = new Busqueda();
					b.setVisible(true);
					dispose();
				}

				tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
			}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));

			
		
	}
	//elimina reserva y su huesped relacionado
	public void eliminarReserva() {
		
		 if (tieneFilaElegidaReserva()) {
	            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	        return;
	        }

		 
	        Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
	                .ifPresentOrElse(fila -> {
	                    String id = (String)  tbReservas.getValueAt(tbReservas.getSelectedRow(), 0).toString();
	                    this.controllerReserva.eliminar(id);
	                   
	                    Busqueda b = new Busqueda();
						b.setVisible(true);
						dispose();
	                    
	                   

	                    JOptionPane.showMessageDialog(this,"Item eliminado con éxito!");
	                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
	}
	
	//elimina huesped y su reserva relacionado
	public void eliminarHuesped() {
		
		 if (tieneFilaElegidaHuesped()) {
	            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	        return;
	        }

		 
	        Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
	                .ifPresentOrElse(fila -> {
	                    String id = (String)  tbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 6).toString();
	                    this.controllerReserva.eliminar(id);
	                    Busqueda b = new Busqueda();
						b.setVisible(true);
						dispose();
	                    
	                    

	                    JOptionPane.showMessageDialog(this,"Item eliminado con éxito!");
	                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
	}

	public boolean tieneFilaElegidaReserva() {

		return tbReservas.getSelectedRowCount() == 0 || tbReservas.getSelectedColumnCount() == 0;
	}

	public boolean tieneFilaElegidaHuesped() {

		return tbHuespedes.getSelectedRowCount() == 0 || tbHuespedes.getSelectedColumnCount() == 0;
	}

	public void limpiarTablaReserva() {

		modelo.getDataVector().clear();
	}

	public void limpiarTablaHuespedes() {

		modeloHuesped.getDataVector().clear();
	}
}
