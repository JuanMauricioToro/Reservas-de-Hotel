package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.management.loading.PrivateClassLoader;
import javax.swing.JOptionPane;

import com.mysql.cj.protocol.Resultset;
import com.toedter.calendar.JDayChooser;

import Factory.ConnectionFactory;
import Modelado.Huesped;
import Modelado.Reserva;
import views.ReservasView;

public class HuespedDAO {

	private Connection conexion;
	

	public HuespedDAO(Connection conexion) {

		this.conexion = conexion;
	}

	public void guardar(Huesped huesped) {

		String nombre = huesped.getNombre();
		String apellido = huesped.getApellido();
		String id = huesped.getId();
		Date fechaNacimiento = new Date(huesped.getFechaNacimiento().getTime());
		String nacionalidad = huesped.getNacionalidad();
		String telefono = huesped.getTelefono();
		String idReserva = huesped.getIdReserva();

		System.out.println("desde huespedDao" + idReserva);
		final Connection conexion = new ConnectionFactory().recuperarConexion();

		try (conexion) {

			final PreparedStatement preStament = conexion
					.prepareStatement(
							"INSERT INTO tbHuespedes(id,nombre,apellido,fecha_nacimiento,nacionalidad"
									+ ",telefono,idReserva)" + "VALUES(?,?,?,?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
			try (preStament) {

				preStament.setString(1, id);
				preStament.setString(2, nombre);
				preStament.setString(3, apellido);
				preStament.setDate(4, fechaNacimiento);
				preStament.setString(5, nacionalidad);
				preStament.setString(6, telefono);
				preStament.setString(7, idReserva);

				preStament.execute();

				final ResultSet resultado = preStament.getGeneratedKeys();
				try (resultado) {

					while (resultado.next()) {

						System.out.println(String.format("Huesped Registrado: %s", huesped.toString()));

					}
				}
			}

		} catch (SQLException e) {

			throw new RuntimeException(e);
		}

	}
	
	public List<Huesped>mostrarPorApellido(String apellido){
		List<Huesped> listaHuespedes = new ArrayList<>();

		
			final Connection conexion = new ConnectionFactory().recuperarConexion();
			try (conexion) {

				final PreparedStatement sentenciaPreparada;

				sentenciaPreparada = conexion.prepareStatement("SELECT * FROM tbHuespedes WHERE apellido =?");
				

				try (sentenciaPreparada) {

					sentenciaPreparada.setString(1, apellido);
					sentenciaPreparada.execute();

					final ResultSet resultado = sentenciaPreparada.getResultSet();
					try (resultado) {

						while (resultado.next()) {

							Huesped huesped = new Huesped(resultado.getString("id"), resultado.getString("nombre"),
									resultado.getString("apellido"), resultado.getDate("fecha_nacimiento"),
									resultado.getString("nacionalidad"), resultado.getString("telefono"),
									resultado.getString("idReserva"));

							listaHuespedes.add(huesped);
						}

					}

				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
				
			}
		

		return listaHuespedes;
		
		
	}

	public List<Huesped> mostrarHuespedes(String id) {

		
		List<Huesped> listaHuespedes = new ArrayList<>();

		if (id.equals("") || id.equals("Id de Reserva ó Apellido del Huesped")) {
			final Connection conexion = new ConnectionFactory().recuperarConexion();
			try (conexion) {

				final PreparedStatement sentenciaPreparada;

				sentenciaPreparada = conexion.prepareStatement("SELECT * FROM tbHuespedes");

				try (sentenciaPreparada) {

					sentenciaPreparada.execute();

					final ResultSet resultado = sentenciaPreparada.getResultSet();
					try (resultado) {

						while (resultado.next()) {

							Huesped huesped = new Huesped(resultado.getString("id"), resultado.getString("nombre"),
									resultado.getString("apellido"), resultado.getDate("fecha_nacimiento"),
									resultado.getString("nacionalidad"), resultado.getString("telefono"),
									resultado.getString("idReserva"));

							listaHuespedes.add(huesped);
						}

					}

				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} else  {

			final Connection conexion = new ConnectionFactory().recuperarConexion();
			try (conexion) {

				final PreparedStatement sentenciaPreparada;

				sentenciaPreparada = conexion.prepareStatement("SELECT * FROM tbHuespedes WHERE idReserva= " + id);

				try (sentenciaPreparada) {

					sentenciaPreparada.execute();

					final ResultSet resultado = sentenciaPreparada.getResultSet();
					try (resultado) {

						while (resultado.next()) {

							Huesped huesped = new Huesped(resultado.getString("id"), resultado.getString("nombre"),
									resultado.getString("apellido"), resultado.getDate("fecha_nacimiento"),
									resultado.getString("nacionalidad"), resultado.getString("telefono"),
									resultado.getString("idReserva"));

							listaHuespedes.add(huesped);
						}

					}

				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "No existe la reserva error: "+e.getMessage());
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
				
			}
		}

		return listaHuespedes;
	}

	public int editarHuesped(String id, String nombre, String apellido2, String fecha_nacimiento,
			String nacionalidad, String telefono, String idReserva) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date fechaNacimiento = null;
		
		try {
			java.util.Date ef = sdf.parse(fecha_nacimiento.toString());
		
			fechaNacimiento = new Date(ef.getTime());
			
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}
		
		
	
		
		
		final Connection conexion = new ConnectionFactory().recuperarConexion();
		try (conexion) {

			final PreparedStatement statemen = conexion.prepareStatement("UPDATE tbHuespedes SET " + "id=?" + ",nombre=?"
					+ ",apellido=?" + ",fecha_nacimiento=?" + ",nacionalidad=?" + ",telefono=?"  + "WHERE idReserva=?");

			try (statemen) {

				
				statemen.setString(1, id);
				statemen.setString(2, nombre);
				statemen.setString(3, apellido2);
				statemen.setDate(4, fechaNacimiento);
				statemen.setString(5, nacionalidad);
				statemen.setString(6, telefono);
				statemen.setString(7, idReserva);
				
				System.out.println(id);
				System.out.println(fecha_nacimiento);
				System.out.println(nombre);
				System.out.println(apellido2);
				System.out.println(nacionalidad);
				System.out.println(telefono);
				System.out.println(idReserva);
				
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

			final PreparedStatement statemen = conexion.prepareStatement("DELETE FROM tbHuespedes WHERE id = ?");
			try (statemen) {
				statemen.setString(1, id);
				statemen.execute();

				
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
}

