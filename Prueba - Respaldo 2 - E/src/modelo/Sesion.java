package modelo;

public class Sesion {
	private int Id_Sesion;
	private int Id_Reserva;
	private int Id_Trabajador;
	private int Id_Estado;
	
	
	public Sesion(int id_Sesion, int id_Reserva, int id_Trabajador, int id_Estado) {
		super();
		Id_Sesion = id_Sesion;
		Id_Reserva = id_Reserva;
		Id_Trabajador = id_Trabajador;
		Id_Estado = id_Estado;
	}
	public int getId_Sesion() {
		return Id_Sesion;
	}
	public void setId_Sesion(int id_Sesion) {
		Id_Sesion = id_Sesion;
	}
	public int getId_Reserva() {
		return Id_Reserva;
	}
	public void setId_Reserva(int id_Reserva) {
		Id_Reserva = id_Reserva;
	}
	public int getId_Trabajador() {
		return Id_Trabajador;
	}
	public void setId_Trabajador(int id_Trabajador) {
		Id_Trabajador = id_Trabajador;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
}
