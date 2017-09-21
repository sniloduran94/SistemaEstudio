package modelo;

import java.sql.Date;

public class Comprobante {
	private int Id_Comprobante;
	private Date Fecha_Emision;
	private int Id_Venta;
	
	
	public Comprobante(int id_Comprobante, Date fecha_Emision, int id_Venta) {
		super();
		Id_Comprobante = id_Comprobante;
		Fecha_Emision = fecha_Emision;
		Id_Venta = id_Venta;
	}
	public int getId_Comprobante() {
		return Id_Comprobante;
	}
	public void setId_Comprobante(int id_Comprobante) {
		Id_Comprobante = id_Comprobante;
	}
	public Date getFecha_Emision() {
		return Fecha_Emision;
	}
	public void setFecha_Emision(Date fecha_Emision) {
		Fecha_Emision = fecha_Emision;
	}
	public int getId_Venta() {
		return Id_Venta;
	}
	public void setId_Venta(int id_Venta) {
		Id_Venta = id_Venta;
	}
	
}
