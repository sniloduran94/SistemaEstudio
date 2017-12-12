package modelo;

import java.sql.Date;

public class Registro_Evento {
	private int Id_Registro;
	private int Id_Evento;
	private int Id_Cliente;
	private Date Fecha_Evento;
	
	
	public Registro_Evento(int id_Registro, int id_Evento, int id_Cliente, Date fecha_Evento) {
		super();
		Id_Registro = id_Registro;
		Id_Evento = id_Evento;
		Id_Cliente = id_Cliente;
		Fecha_Evento = fecha_Evento;
	}
	public int getId_Registro() {
		return Id_Registro;
	}
	public void setId_Registro(int id_Registro) {
		Id_Registro = id_Registro;
	}
	public int getId_Evento() {
		return Id_Evento;
	}
	public void setId_Evento(int id_Evento) {
		Id_Evento = id_Evento;
	}
	public int getId_Cliente() {
		return Id_Cliente;
	}
	public void setId_Cliente(int id_Cliente) {
		Id_Cliente = id_Cliente;
	}
	public Date getFecha_Evento() {
		return Fecha_Evento;
	}
	public void setFecha_Evento(Date fecha_Evento) {
		Fecha_Evento = fecha_Evento;
	}	
}
