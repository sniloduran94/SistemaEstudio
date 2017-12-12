package modelo;

import java.sql.Date;

public class Venta {
	private int Id_Venta;
	private int Id_Cliente;
	private Date Fecha_Venta;
	private int Id_Sesion;
	
	
	public Venta(int id_Venta, int id_Cliente, Date fecha_Venta, int id_Sesion) {
		super();
		Id_Venta = id_Venta;
		Id_Cliente = id_Cliente;
		Fecha_Venta = fecha_Venta;
		Id_Sesion = id_Sesion;
	}
	public int getId_Venta() {
		return Id_Venta;
	}
	public void setId_Venta(int id_Venta) {
		Id_Venta = id_Venta;
	}
	public int getId_Cliente() {
		return Id_Cliente;
	}
	public void setId_Cliente(int id_Cliente) {
		Id_Cliente = id_Cliente;
	}
	public Date getFecha_Venta() {
		return Fecha_Venta;
	}
	public void setFecha_Venta(Date fecha_Venta) {
		Fecha_Venta = fecha_Venta;
	}
	public int getId_Sesion() {
		return Id_Sesion;
	}
	public void setId_Sesion(int id_Sesion) {
		Id_Sesion = id_Sesion;
	}
}
