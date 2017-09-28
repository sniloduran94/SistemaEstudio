package modelo;

public class Tipo_Cliente {
	private int Id_Tipo_Cliente;
	private String Tipo;
	private String Descripcion;
	private int Id_Estado;
	
	
	public Tipo_Cliente(int id_Tipo_Cliente, String tipo, String descripcion, int id_Estado) {
		super();
		Id_Tipo_Cliente = id_Tipo_Cliente;
		Tipo = tipo;
		Descripcion = descripcion;
		Id_Estado = id_Estado;
	}
	public int getId_Tipo_Cliente() {
		return Id_Tipo_Cliente;
	}
	public void setId_Tipo_Cliente(int id_Tipo_Cliente) {
		Id_Tipo_Cliente = id_Tipo_Cliente;
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
