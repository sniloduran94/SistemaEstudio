package modelo;

public class Relacion_Grupo_Producto {
	private int Id_Grupo_Producto;
	private int Id_Producto;
	
	
	public Relacion_Grupo_Producto(int id_Grupo_Producto, int id_Producto) {
		super();
		Id_Grupo_Producto = id_Grupo_Producto;
		Id_Producto = id_Producto;
	}
	public int getId_Grupo_Producto() {
		return Id_Grupo_Producto;
	}
	public void setId_Grupo_Producto(int id_Grupo_Producto) {
		Id_Grupo_Producto = id_Grupo_Producto;
	}
	public int getId_Producto() {
		return Id_Producto;
	}
	public void setId_Producto(int id_Producto) {
		Id_Producto = id_Producto;
	}
	
	
}
