package modelo;

public class Evento {
	private int Id_Evento;
	private String Fecha;
	private String Forma_Pago;
	private int Valor;
	private int Trabajador;
	private String Item;
	private String Descripcion;
	private int Estado;
	private int Numero_Boleta;
	private int Id_Auxiliar;
	private String Movimiento;
	
	public int getId_Evento() {
		return Id_Evento;
	}
	public void setId_Evento(int id_Evento) {
		Id_Evento = id_Evento;
	}
	public String getFecha() {
		return Fecha;
	}
	public void setFecha(String fecha) {
		Fecha = fecha;
	}
	public String getForma_Pago() {
		return Forma_Pago;
	}
	public void setForma_Pago(String forma_Pago) {
		Forma_Pago = forma_Pago;
	}
	public int getValor() {
		return Valor;
	}
	public void setValor(int valor) {
		Valor = valor;
	}
	public int getTrabajador() {
		return Trabajador;
	}
	public void setTrabajador(int trabajador) {
		Trabajador = trabajador;
	}
	public String getItem() {
		return Item;
	}
	public void setItem(String item) {
		Item = item;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public int getEstado() {
		return Estado;
	}
	public void setEstado(int estado) {
		Estado = estado;
	}
	public int getNumero_Boleta() {
		return Numero_Boleta;
	}
	public void setNumero_Boleta(int numero_Boleta) {
		Numero_Boleta = numero_Boleta;
	}
	public int getId_Auxiliar() {
		return Id_Auxiliar;
	}
	public void setId_Auxiliar(int id_Auxiliar) {
		Id_Auxiliar = id_Auxiliar;
	}
	public String getMovimiento() {
		return Movimiento;
	}
	public void setMovimiento(String movimiento) {
		Movimiento = movimiento;
	}

}
