package Modelado;

import java.security.SecureRandom;
import java.util.Date;

import DAO.ReservaDAO;
import Factory.ConnectionFactory;


public class Reserva {

	private String id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private double valor;
	private String metodoPago;
	
	
	public Reserva(Date fechaEntrada, Date fechaSalida, double valor, String metodoPago) {
		
		this.id = String.valueOf(generarIdReserva());
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.metodoPago = metodoPago;
		
		
		
	}
	
	
	
	
	public Reserva(String id, Date fechaEntrada, Date fechaSalida, double valor, String metodoPago) {
		
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.metodoPago = metodoPago;
	}




	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Date getFechaEntrada() {
		return fechaEntrada;
	}



	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}



	public Date getFechaSalida() {
		return fechaSalida;
	}



	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}



	public double getValor() {
		return valor;
	}



	public void setValor(double valor) {
		this.valor = valor;
	}



	public String getMetodoPago() {
		return metodoPago;
	}



	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	//genera un id de reserva no repetido
	private int generarIdReserva() {
		ReservaDAO reservaDao = new ReservaDAO(new ConnectionFactory().recuperarConexion());
		SecureRandom numero = new SecureRandom();
		int random = 100000 + numero.nextInt(99999);
		
		if(reservaDao.validarIdReservaRepetido(random)) {
			return generarIdReserva();
			
		}else {
			  return random ;
		}
		
		
		
	}



	@Override
	public String toString() {
		return "Reserva id=" + id + "\nfechaEntrada=" + fechaEntrada + "\nfechaSalida=" + fechaSalida + "\nvalor="
				+ valor + "\nmetodoPago=" + metodoPago;
	}

	
	
}
