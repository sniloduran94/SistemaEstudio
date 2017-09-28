package modelo;

public class Tipo_Evento {
	private int Id_Tipo_Evento;
	private String Tipo_Evento;
	private int Id_Estado;
	
	
	
	public Tipo_Evento(int id_Tipo_Evento, String tipo_Evento, int id_Estado) {
		super();
		Id_Tipo_Evento = id_Tipo_Evento;
		Tipo_Evento = tipo_Evento;
		Id_Estado = id_Estado;
	}
	public int getId_Tipo_Evento() {
		return Id_Tipo_Evento;
	}
	public void setId_Tipo_Evento(int id_Tipo_Evento) {
		Id_Tipo_Evento = id_Tipo_Evento;
	}
	public String getTipo_Evento() {
		return Tipo_Evento;
	}
	public void setTipo_Evento(String tipo_Evento) {
		Tipo_Evento = tipo_Evento;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
}
