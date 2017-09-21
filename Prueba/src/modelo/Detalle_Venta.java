package modelo;

public class Detalle_Venta {
	private int Id_Detalle;
	private int Id_Venta;
	private int Id_Producto;
	private int Cantidad;
	private int Descuento;
	
	
	
	public Detalle_Venta(int id_Detalle, int id_Venta, int id_Producto, int cantidad, int descuento) {
		super();
		Id_Detalle = id_Detalle;
		Id_Venta = id_Venta;
		Id_Producto = id_Producto;
		Cantidad = cantidad;
		Descuento = descuento;
	}
	public int getId_Detalle() {
		return Id_Detalle;
	}
	public void setId_Detalle(int id_Detalle) {
		Id_Detalle = id_Detalle;
	}
	public int getId_Venta() {
		return Id_Venta;
	}
	public void setId_Venta(int id_Venta) {
		Id_Venta = id_Venta;
	}
	public int getId_Producto() {
		return Id_Producto;
	}
	public void setId_Producto(int id_Producto) {
		Id_Producto = id_Producto;
	}
	public int getCantidad() {
		return Cantidad;
	}
	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}
	public int getDescuento() {
		return Descuento;
	}
	public void setDescuento(int descuento) {
		Descuento = descuento;
	}
}
