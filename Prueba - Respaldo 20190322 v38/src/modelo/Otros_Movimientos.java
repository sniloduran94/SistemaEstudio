package modelo;

import java.sql.Date;

public class Otros_Movimientos {
	private int Otros_Movimientos;
	private int Id_Tipo_Movimiento;
	private int Id_Producto;
	private int Id_Trabajador;
	private Date Fecha_Movimiento;
	private String Comentario;
	
	
	public Otros_Movimientos(int otros_Movimientos, int id_Tipo_Movimiento, int id_Producto, int id_Trabajador,
			Date fecha_Movimiento, String comentario) {
		super();
		Otros_Movimientos = otros_Movimientos;
		Id_Tipo_Movimiento = id_Tipo_Movimiento;
		Id_Producto = id_Producto;
		Id_Trabajador = id_Trabajador;
		Fecha_Movimiento = fecha_Movimiento;
		Comentario = comentario;
	}
	public int getOtros_Movimientos() {
		return Otros_Movimientos;
	}
	public void setOtros_Movimientos(int otros_Movimientos) {
		Otros_Movimientos = otros_Movimientos;
	}
	public int getId_Tipo_Movimiento() {
		return Id_Tipo_Movimiento;
	}
	public void setId_Tipo_Movimiento(int id_Tipo_Movimiento) {
		Id_Tipo_Movimiento = id_Tipo_Movimiento;
	}
	public int getId_Producto() {
		return Id_Producto;
	}
	public void setId_Producto(int id_Producto) {
		Id_Producto = id_Producto;
	}
	public int getId_Trabajador() {
		return Id_Trabajador;
	}
	public void setId_Trabajador(int id_Trabajador) {
		Id_Trabajador = id_Trabajador;
	}
	public Date getFecha_Movimiento() {
		return Fecha_Movimiento;
	}
	public void setFecha_Movimiento(Date fecha_Movimiento) {
		Fecha_Movimiento = fecha_Movimiento;
	}
	public String getComentario() {
		return Comentario;
	}
	public void setComentario(String comentario) {
		Comentario = comentario;
	}
}
