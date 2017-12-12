package modelo;

public class Cargo {
	private int Id_Cargo;
	private String Cargo;
	private String Descripcion;
	private int Id_Estado;
	
	
	public Cargo(int id_Cargo, String cargo, String descripcion, int id_Estado) {
		super();
		Id_Cargo = id_Cargo;
		Cargo = cargo;
		Descripcion = descripcion;
		Id_Estado = id_Estado;
	}
	public int getId_Cargo() {
		return Id_Cargo;
	}
	public void setId_Cargo(int id_Cargo) {
		Id_Cargo = id_Cargo;
	}
	public String getCargo() {
		return Cargo;
	}
	public void setCargo(String cargo) {
		Cargo = cargo;
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
