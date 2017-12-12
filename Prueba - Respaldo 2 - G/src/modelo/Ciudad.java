package modelo;

public class Ciudad {
	private int Id_Ciudad;
	private String Ciudad;
	private int Id_Estado;
	
	
	public Ciudad(int id_Ciudad, String ciudad, int id_Estado) {
		super();
		Id_Ciudad = id_Ciudad;
		Ciudad = ciudad;
		Id_Estado = id_Estado;
	}
	public Ciudad() {
		// TODO Auto-generated constructor stub
	}
	public int getId_Ciudad() {
		return Id_Ciudad;
	}
	public void setId_Ciudad(int id_Ciudad) {
		Id_Ciudad = id_Ciudad;
	}
	public String getCiudad() {
		return Ciudad;
	}
	public void setCiudad(String ciudad) {
		Ciudad = ciudad;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
	
}
