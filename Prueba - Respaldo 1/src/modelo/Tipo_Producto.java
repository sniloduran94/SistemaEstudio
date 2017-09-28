package modelo;

public class Tipo_Producto {
	private int Id_Tipo_Producto;
	private String Tipo;
	private String Descripcion;
	private int Id_Estado;
	
	
	public Tipo_Producto(int id_Tipo_Producto, String tipo, String descripcion, int id_Estado) {
		super();
		Id_Tipo_Producto = id_Tipo_Producto;
		Tipo = tipo;
		Descripcion = descripcion;
		Id_Estado = id_Estado;
	}
	public int getId_Tipo_Producto() {
		return Id_Tipo_Producto;
	}
	public void setId_Tipo_Producto(int id_Tipo_Producto) {
		Id_Tipo_Producto = id_Tipo_Producto;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
}
