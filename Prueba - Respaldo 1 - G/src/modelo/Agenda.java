package modelo;

public class Agenda {
	private int Id_Agenda;
	private int Id_Calendario;
	private int Hora;
	
	
	public Agenda(int id_Agenda, int id_Calendario, int hora) {
		super();
		Id_Agenda = id_Agenda;
		Id_Calendario = id_Calendario;
		Hora = hora;
	}
	public int getId_Agenda() {
		return Id_Agenda;
	}
	public void setId_Agenda(int id_Agenda) {
		Id_Agenda = id_Agenda;
	}
	public int getId_Calendario() {
		return Id_Calendario;
	}
	public void setId_Calendario(int id_Calendario) {
		Id_Calendario = id_Calendario;
	}
	public int getHora() {
		return Hora;
	}
	public void setHora(int hora) {
		Hora = hora;
	}
	
	
}
