package Controller;


import java.util.Date;
import java.util.List;

import DAO.ReservaDAO;
import Factory.ConnectionFactory;
import Modelado.Reserva;
import views.RegistroHuesped;

public class ControladorReserva {

	private ReservaDAO reservaDao;
	private RegistroHuesped registroHuesped = new RegistroHuesped();
	
	public ControladorReserva() {
		
		this.reservaDao = new ReservaDAO(new ConnectionFactory().recuperarConexion());
	}
	
	public void guardar(Reserva reserva) {
		
		this.reservaDao.guardar(reserva);
		this.registroHuesped.setIdReserva(reserva.getId());
		
	}
	
	public List<Reserva>mostrarReservas(String id){
		return this.reservaDao.mostrarReservas(id);
		
	}
	
	public List<Reserva>mostrarReservasApellido(String id){
		return this.reservaDao.mostrarPorApellidoReserva(id);
		
	}

	

	public int editarReserva(String id, String fecha_entrada, String fecha_salida, Double valor, String forma_pago) {
		
		return reservaDao.editarReserva(id, fecha_entrada, fecha_salida, valor, forma_pago);
	}

	public void eliminar(String id) {
		
		this.reservaDao.eliminar(id);
		
	}
	
}
