package modelo;

public class Evento {
	private int Id_Evento;
	private String Evento;
	private String Descripcion_Evento;
	private int Id_Tipo_Evento;
	private int Id_Estado;
	
	
	public Evento(int id_Evento, String evento, String descripcion_Evento, int id_Tipo_Evento, int id_Estado) {
		super();
		Id_Evento = id_Evento;
		Evento = evento;
		Descripcion_Evento = descripcion_Evento;
		Id_Tipo_Evento = id_Tipo_Evento;
		Id_Estado = id_Estado;
	}
	public int getId_Evento() {
		return Id_Evento;
	}
	public void setId_Evento(int id_Evento) {
		Id_Evento = id_Evento;
	}
	public String getEvento() {
		return Evento;
	}
	public void setEvento(String evento) {
		Evento = evento;
	}
	public String getDescripcion_Evento() {
		return Descripcion_Evento;
	}
	public void setDescripcion_Evento(String descripcion_Evento) {
		Descripcion_Evento = descripcion_Evento;
	}
	public int getId_Tipo_Evento() {
		return Id_Tipo_Evento;
	}
	public void setId_Tipo_Evento(int id_Tipo_Evento) {
		Id_Tipo_Evento = id_Tipo_Evento;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
}
