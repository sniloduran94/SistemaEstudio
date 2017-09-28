package modelo;

public class Grupo_Producto {
	private int Id_Grupo_Producto;
	private String Grupo;
	private String Descripcion;
	private int Id_Estado;
	
	
	public Grupo_Producto(int id_Grupo_Producto, String grupo, String descripcion, int id_Estado) {
		super();
		Id_Grupo_Producto = id_Grupo_Producto;
		Grupo = grupo;
		Descripcion = descripcion;
		Id_Estado = id_Estado;
	}
	public int getId_Grupo_Producto() {
		return Id_Grupo_Producto;
	}
	public void setId_Grupo_Producto(int id_Grupo_Producto) {
		Id_Grupo_Producto = id_Grupo_Producto;
	}
	public String getGrupo() {
		return Grupo;
	}
	public void setGrupo(String grupo) {
		Grupo = grupo;
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
