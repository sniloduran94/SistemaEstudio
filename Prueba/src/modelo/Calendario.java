package modelo;

public class Calendario {
	private int Id_Calendario;
	private int Anio;
	private int Mes;
	private int Dia;
	private int Id_Estado;
	
	
	public Calendario(int id_Calendario, int anio, int mes, int dia, int id_Estado) {
		super();
		Id_Calendario = id_Calendario;
		Anio = anio;
		Mes = mes;
		Dia = dia;
		Id_Estado = id_Estado;
	}
	public int getId_Calendario() {
		return Id_Calendario;
	}
	public void setId_Calendario(int id_Calendario) {
		Id_Calendario = id_Calendario;
	}
	public int getAnio() {
		return Anio;
	}
	public void setAnio(int anio) {
		Anio = anio;
	}
	public int getMes() {
		return Mes;
	}
	public void setMes(int mes) {
		Mes = mes;
	}
	public int getDia() {
		return Dia;
	}
	public void setDia(int dia) {
		Dia = dia;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
}
