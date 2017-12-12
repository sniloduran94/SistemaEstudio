package modelo;

public class Comision {
	private int Id_Comision;
	private int Id_Trabajador;
	private double Comision;
	private int Id_Estado;
	
	
	public Comision(int id_Comision, int id_Trabajador, double comision, int id_Estado) {
		super();
		Id_Comision = id_Comision;
		Id_Trabajador = id_Trabajador;
		Comision = comision;
		Id_Estado = id_Estado;
	}
	public int getId_Comision() {
		return Id_Comision;
	}
	public void setId_Comision(int id_Comision) {
		Id_Comision = id_Comision;
	}
	public int getId_Trabajador() {
		return Id_Trabajador;
	}
	public void setId_Trabajador(int id_Trabajador) {
		Id_Trabajador = id_Trabajador;
	}
	public double getComision() {
		return Comision;
	}
	public void setComision(double comision) {
		Comision = comision;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
	
}
