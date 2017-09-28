package modelo;

public class Hora {
	private int Id_Hora;
	private int Hora;
	private int Minuto;
	
	
	public Hora(int id_Hora, int hora, int minuto) {
		super();
		Id_Hora = id_Hora;
		Hora = hora;
		Minuto = minuto;
	}
	public int getId_Hora() {
		return Id_Hora;
	}
	public void setId_Hora(int id_Hora) {
		Id_Hora = id_Hora;
	}
	public int getHora() {
		return Hora;
	}
	public void setHora(int hora) {
		Hora = hora;
	}
	public int getMinuto() {
		return Minuto;
	}
	public void setMinuto(int minuto) {
		Minuto = minuto;
	}
	
}
