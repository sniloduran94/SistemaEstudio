package modelo;

public class Detalle_Campania {
	private int Id_Detalle_Campania;
	private int Id_Campania;
	private int Id_Producto;
	private int Cantidad;
	
	
	public Detalle_Campania(int id_Detalle_Campania, int id_Campania, int id_Producto, int cantidad) {
		super();
		Id_Detalle_Campania = id_Detalle_Campania;
		Id_Campania = id_Campania;
		Id_Producto = id_Producto;
		Cantidad = cantidad;
	}
	public int getId_Detalle_Campania() {
		return Id_Detalle_Campania;
	}
	public void setId_Detalle_Campania(int id_Detalle_Campania) {
		Id_Detalle_Campania = id_Detalle_Campania;
	}
	public int getId_Campania() {
		return Id_Campania;
	}
	public void setId_Campania(int id_Campania) {
		Id_Campania = id_Campania;
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
}
