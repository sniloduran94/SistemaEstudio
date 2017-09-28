package modelo;

public class Estado {
	private int Id_Estado;
	private String Estado;
	private int Id_Tabla;
	
	
	public Estado(int id_Estado, String estado, int id_Tabla) {
		super();
		Id_Estado = id_Estado;
		Estado = estado;
		Id_Tabla = id_Tabla;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	public String getEstado() {
		return Estado;
	}
	public void setEstado(String estado) {
		Estado = estado;
	}
	public int getId_Tabla() {
		return Id_Tabla;
	}
	public void setId_Tabla(int id_Tabla) {
		Id_Tabla = id_Tabla;
	}
}
