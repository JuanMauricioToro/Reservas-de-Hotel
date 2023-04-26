package Controller;


import java.util.List;

import DAO.HuespedDAO;
import Factory.ConnectionFactory;
import Modelado.Huesped;


public class ControladorHuesped {

	private HuespedDAO huespedDao;
	
	
	
	public ControladorHuesped() {
		
		this.huespedDao = new HuespedDAO(new ConnectionFactory().recuperarConexion());
	}
	
	public void guardar(Huesped huesped) {
		
		this.huespedDao.guardar(huesped);
		
		System.out.println("desde controladorHuesped"+huesped.getIdReserva());
	}

	
	public List<Huesped>mostrarHuespedes(String id){
		
		return this.huespedDao.mostrarHuespedes(id);
		
	}
	
	public List<Huesped>mostrarHuespedesApellido(String apellido){
				return this.huespedDao.mostrarPorApellido(apellido);
		
	}

	public int editarHuesped(String id, String nombre, String apellido, String fecha_nacimiento, String nacionalidad,
			String telefono, String idReserva) {
		
		return this.huespedDao.editarHuesped(id,nombre,apellido,fecha_nacimiento,nacionalidad,telefono,idReserva);
	}

	public void eliminar(String id) {
		this.huespedDao.eliminar(id);
		
	}
	
	
}
