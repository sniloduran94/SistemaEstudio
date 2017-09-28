package modelo;

public class Tipo_Sesion {
	private int Id_Tipo_Sesion;
	private String Tipo_Sesion;
	private int Id_Estado;
	public int getId_Tipo_Sesion() {
		return Id_Tipo_Sesion;
	}
	public void setId_Tipo_Sesion(int id_Tipo_Sesion) {
		Id_Tipo_Sesion = id_Tipo_Sesion;
	}
	public String getTipo_Sesion() {
		return Tipo_Sesion;
	}
	public void setTipo_Sesion(String tipo_Sesion) {
		Tipo_Sesion = tipo_Sesion;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	public Tipo_Sesion(int id_Tipo_Sesion, String tipo_Sesion, int id_Estado) {
		super();
		Id_Tipo_Sesion = id_Tipo_Sesion;
		Tipo_Sesion = tipo_Sesion;
		Id_Estado = id_Estado;
	}
	public Tipo_Sesion() {
		// TODO Auto-generated constructor stub
	}
	
	
}
