package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Factory.ConnectionFactory;
import Modelado.Huesped;
import Modelado.Reserva;
import views.Busqueda;
import views.ReservasView;

public class ReservaDAO {

	private Connection conexion;

	public ReservaDAO(Connection conexion) {

		this.conexion = conexion;
	}

	public void guardar(Reserva reserva) {

		Date fechaEntrada = new Date(reserva.getFechaEntrada().getTime());
		Date fechaSalida = new Date(reserva.getFechaSalida().getTime());

		String id = reserva.getId();
		Double valor = reserva.getValor();
		String metodo = reserva.getMetodoPago();

		final Connection conexion = new ConnectionFactory().recuperarConexion();

		try (conexion) {

			final PreparedStatement preStament = conexion.prepareStatement(
					"INSERT INTO tbReservas(id,fecha_entrada,fecha_salida,valor" + ",forma_pago)" + "VALUES(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try (preStament) {

				preStament.setString(1, id);
				preStament.setDate(2, fechaEntrada);
				preStament.setDate(3, fechaSalida);
				preStament.setDouble(4, valor);
				preStament.setString(5, metodo);

				preStament.execute();

				final ResultSet resultado = preStament.getGeneratedKeys();
				try (resultado) {

					while (resultado.next()) {

						System.out.println(String.format("Huesped Registrado: %s", reserva.toString()));

					}
				}
			}

		} catch (SQLException e) {

			throw new RuntimeException(e);
		}

	}

	public boolean validarIdReservaRepetido(int idReserva) {

		List<String> listId = new ArrayList<>();
		String id = String.valueOf(idReserva);
		boolean existe = false;

		final Connection conexion = new ConnectionFactory().recuperarConexion();

		try (conexion) {

			final PreparedStatement sentencia = conexion.prepareStatement("SELECT idReserva FROM tbHuespedes");
			try (sentencia) {
				sentencia.execute();

				final ResultSet resultado = sentencia.getResultSet();

				try (resultado) {

					while (resultado.next()) {

						listId.add(resultado.getString("idReserva"));

					}

				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		for (String string : listId) {

			if (string.equals(id)) {
				existe = true;
			} else {
				existe = false;
			}
		}

		return existe;
	}

	public List<Reserva> mostrarReservas(String idReserva) {
		
		List<Reserva> listaReserva = new ArrayList<>();

		if (idReserva.equals("") || idReserva.equals("Id de Reserva ó Apellido del Huesped")) {
			final Connection conexion = new ConnectionFactory().recuperarConexion();
			try (conexion) {

				final PreparedStatement sentenciaPreparada;

				sentenciaPreparada = conexion.prepareStatement("SELECT * FROM tbReservas");

				try (sentenciaPreparada) {

					sentenciaPreparada.execute();

					final ResultSet resultado = sentenciaPreparada.getResultSet();
					try (resultado) {

						while (resultado.next()) {

							Reserva reservas = new Reserva(resultado.getString("id"),
									resultado.getDate("fecha_entrada"), resultado.getDate("fecha_salida"),
									resultado.getDouble("valor"), resultado.getString("forma_pago"));

							listaReserva.add(reservas);
						}

					

					}

				}catch (SQLException e) {
					throw new RuntimeException(e.getMessage());
				}

			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}

		}
		
		

		else {

			final Connection conexion = new ConnectionFactory().recuperarConexion();
			try (conexion) {

				final PreparedStatement sentenciaPreparada;

				sentenciaPreparada = conexion.prepareStatement("SELECT * FROM tbReservas WHERE id= " + idReserva);

				try (sentenciaPreparada) {

					sentenciaPreparada.execute();

					final ResultSet resultado = sentenciaPreparada.getResultSet();
					try (resultado) {

						while (resultado.next()) {

							Reserva reservas = new Reserva(resultado.getString("id"),
									resultado.getDate("fecha_entrada"), resultado.getDate("fecha_salida"),
									resultado.getDouble("valor"), resultado.getString("forma_pago"));

							listaReserva.add(reservas);
						}

					}

				}
				catch (SQLException e) {
					System.out.println("valor incorrecto error: "+e.getMessage());
					JOptionPane.showMessageDialog(null, "La reserva no existe");
					Busqueda b = new Busqueda();
					b.setVisible(true);
					
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(null, "La reserva no existe");
				e.printStackTrace();
				
			}
		
		
	}

		return listaReserva;
	}
	
	
	
	public List<Reserva>mostrarPorApellidoReserva(String id){
		List<Reserva> listaReservas = new ArrayList<>();

		
			final Connection conexion = new ConnectionFactory().recuperarConexion();
			try (conexion) {

				final PreparedStatement sentenciaPreparada;

				sentenciaPreparada = conexion.prepareStatement("SELECT * FROM tbReservas WHERE id= "+id);

				try (sentenciaPreparada) {

					sentenciaPreparada.execute();

					final ResultSet resultado = sentenciaPreparada.getResultSet();
					try (resultado) {

						while (resultado.next()) {

							Reserva reserva = new Reserva(resultado.getString("id"), resultado.getDate("fecha_entrada"),
									resultado.getDate("fecha_salida"), resultado.getDouble("valor"),
									resultado.getString("forma_pago"));

							listaReservas.add(reserva);
						}

					}

				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		return listaReservas;
		
		
	}

	public int editarReserva(String id, String fecha_entrada, String fecha_salida, Double valor, String forma_pago) {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		Date fechaEntrada = null;
		Date fechaSalida =null;
		
		try {
			java.util.Date ef = sdf.parse(fecha_entrada);
			java.util.Date s = sdf.parse(fecha_salida);
			
			
			fechaEntrada = new Date(ef.getTime());
			fechaSalida = new Date(s.getTime());
			
			
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}
		
		
		ReservasView val = new ReservasView();
		valor = val.valorReserva(fecha_entrada, fecha_salida);
		
		
		final Connection conexion = new ConnectionFactory().recuperarConexion();
		try (conexion) {

			final PreparedStatement statemen = conexion.prepareStatement("UPDATE tbReservas SET " + "fecha_entrada=?"
					+ ",fecha_salida=?" + ",valor=?" + ",forma_pago=?" + "WHERE id=?");

			try (statemen) {

				
				statemen.setDate(1, fechaEntrada);
				statemen.setDate(2, fechaSalida);
				statemen.setDouble(3, valor);
				statemen.setString(4, forma_pago);
				statemen.setString(5, id);
				
				System.out.println(id);
				System.out.println(fechaEntrada);
				System.out.println(fechaSalida);
				System.out.println(valor);
				System.out.println(forma_pago);
				
				statemen.execute();
				int updateCount = statemen.getUpdateCount();
				System.out.println(updateCount);
				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}

	public void eliminar(String id) {
		final Connection conexion = new ConnectionFactory().recuperarConexion();
		try (conexion) {

			final PreparedStatement statemen = conexion.prepareStatement("DELETE FROM tbReservas WHERE id = ?");
			try (statemen) {
				statemen.setString(1, id);
				statemen.execute();

				
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
