package modelo;

public class Giro {
	private int Id_Giro;
	private String Giro;
	private String Descripcion;
	private int Id_Estado;
	
	
	public Giro(int id_Giro, String giro, String descripcion, int id_Estado) {
		super();
		Id_Giro = id_Giro;
		Giro = giro;
		Descripcion = descripcion;
		Id_Estado = id_Estado;
	}
	public int getId_Giro() {
		return Id_Giro;
	}
	public void setId_Giro(int id_Giro) {
		Id_Giro = id_Giro;
	}
	public String getGiro() {
		return Giro;
	}
	public void setGiro(String giro) {
		Giro = giro;
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
