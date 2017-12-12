package modelo;

public class Ticket {
	private int Id_Ticket;
	private String Codigo;
	private int Id_Estado;
	private int Valor;
	
	
	
	public Ticket(int id_Ticket, String codigo, int id_Estado, int valor) {
		super();
		Id_Ticket = id_Ticket;
		Codigo = codigo;
		Id_Estado = id_Estado;
		Valor = valor;
	}
	public int getId_Ticket() {
		return Id_Ticket;
	}
	public void setId_Ticket(int id_Ticket) {
		Id_Ticket = id_Ticket;
	}
	public String getCodigo() {
		return Codigo;
	}
	public void setCodigo(String codigo) {
		Codigo = codigo;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	public int getValor() {
		return Valor;
	}
	public void setValor(int valor) {
		Valor = valor;
	}
}
