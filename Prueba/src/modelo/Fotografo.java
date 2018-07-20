package modelo;

public class Fotografo {
	private int Id_Fotografo;
	private String Fotografo;
	private int Id_Estado;
	
	
	
	public Fotografo(int id_Fotografo, String fotografo, int id_Estado) {
		super();
		Id_Fotografo = id_Fotografo;
		Fotografo = fotografo;
		Id_Estado = id_Estado;
	}
	public int getId_Fotografo() {
		return Id_Fotografo;
	}
	public void setId_Fotografo(int id_Fotografo) {
		Id_Fotografo = id_Fotografo;
	}
	public String getFotografo() {
		return Fotografo;
	}
	public void setFotografo(String fotografo) {
		Fotografo = fotografo;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
		
}
