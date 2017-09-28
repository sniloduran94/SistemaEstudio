package modelo;

public class Comuna {
	private int Id_Comuna;
	private int Id_Ciudad_Asoc;
	private String Comuna;
	private int Id_Estado;
	
	
	
	public Comuna(int id_Comuna, int id_Ciudad_Asoc, String comuna, int id_Estado) {
		super();
		Id_Comuna = id_Comuna;
		Id_Ciudad_Asoc = id_Ciudad_Asoc;
		Comuna = comuna;
		Id_Estado = id_Estado;
	}
	public Comuna() {
		// TODO Auto-generated constructor stub
	}
	public int getId_Comuna() {
		return Id_Comuna;
	}
	public void setId_Comuna(int id_Comuna) {
		Id_Comuna = id_Comuna;
	}
	public String getComuna() {
		return Comuna;
	}
	public void setComuna(String comuna) {
		Comuna = comuna;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	public int getId_Ciudad_Asoc() {
		return Id_Ciudad_Asoc;
	}
	public void setId_Ciudad_Asoc(int id_Ciudad_Asoc) {
		Id_Ciudad_Asoc = id_Ciudad_Asoc;
	}
}
