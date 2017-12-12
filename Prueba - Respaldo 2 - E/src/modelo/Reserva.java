package modelo;

import java.util.Date;

public class Reserva {
	private int Id_Reserva = -1;
	private int Id_Agenda = -1;
	private int Id_Cliente = -1;
	private int Tipo_Cliente = -1;
	private int Id_Campania = -1;
	private int Id_Trabajador = -1;
	private int Cantidad_Personas;
	private int Cantidad_Adicionales;
	private int Cantidad_Reagendamiento;
	private int Monto_Pago_Estudio = -1;
	private int Monto_Pago_Adelantado = -1 ;
	private int Id_Estado = -1;
	private Date Fecha;
	private String Codigo;
	private boolean Validado = false;
	private int Id_Tipo_Sesion;
	private boolean PreReserva = false;
	private int Vendedor;
	private int Id_Metodo_Pago;
	private String Observacion;
	
	// Variables nuevas 7/22/2016
	private String Nombre_Anticipo;
	private String Fecha_Anticipo;
	private String Hora_Anticipo;
	private String Tipo_Anticipo;
		
	public int getId_Tipo_Sesion() {
		return Id_Tipo_Sesion;
	}
	public void setId_Tipo_Sesion(int id_Tipo_Sesion) {
		Id_Tipo_Sesion = id_Tipo_Sesion;
	}
	public Reserva(int id_Reserva, int id_Agenda, int id_Cliente, int tipo_Cliente, int id_Campania, int id_Ticket,
			int id_Trabajador, int cantidad_Personas, int cantidad_Adicionales, int cantidad_Reagendamiento,
			int monto_Pago_Estudio, int monto_Pago_Adelantado, int id_Estado, Date fecha, String codigo,
			boolean validado) {
		super();
		Id_Reserva = id_Reserva;
		Id_Agenda = id_Agenda;
		Id_Cliente = id_Cliente;
		Tipo_Cliente = tipo_Cliente;
		Id_Campania = id_Campania;
		Id_Trabajador = id_Trabajador;
		Cantidad_Personas = cantidad_Personas;
		Cantidad_Adicionales = cantidad_Adicionales;
		Cantidad_Reagendamiento = cantidad_Reagendamiento;
		Monto_Pago_Estudio = monto_Pago_Estudio;
		Monto_Pago_Adelantado = monto_Pago_Adelantado;
		Id_Estado = id_Estado;
		Fecha = fecha;
		Codigo = codigo;
		Validado = validado;
	}
	public Reserva() {
		// TODO Auto-generated constructor stub
	}
	public int getId_Reserva() {
		return Id_Reserva;
	}
	public void setId_Reserva(int id_Reserva) {
		Id_Reserva = id_Reserva;
	}
	public int getId_Agenda() {
		return Id_Agenda;
	}
	public void setId_Agenda(int id_Agenda) {
		Id_Agenda = id_Agenda;
	}
	public int getId_Cliente() {
		return Id_Cliente;
	}
	public void setId_Cliente(int id_Cliente) {
		Id_Cliente = id_Cliente;
	}
	public int getTipo_Cliente() {
		return Tipo_Cliente;
	}
	public void setTipo_Cliente(int tipo_Cliente) {
		Tipo_Cliente = tipo_Cliente;
	}
	public int getId_Campania() {
		return Id_Campania;
	}
	public void setId_Campania(int id_Campania) {
		Id_Campania = id_Campania;
	}
	public int getId_Trabajador() {
		return Id_Trabajador;
	}
	public void setId_Trabajador(int id_Trabajador) {
		Id_Trabajador = id_Trabajador;
	}
	public int getCantidad_Personas() {
		return Cantidad_Personas;
	}
	public void setCantidad_Personas(int cantidad_Personas) {
		Cantidad_Personas = cantidad_Personas;
	}
	public int getCantidad_Adicionales() {
		return Cantidad_Adicionales;
	}
	public void setCantidad_Adicionales(int cantidad_Adicionales) {
		Cantidad_Adicionales = cantidad_Adicionales;
	}
	public int getCantidad_Reagendamiento() {
		return Cantidad_Reagendamiento;
	}
	public void setCantidad_Reagendamiento(int cantidad_Reagendamiento) {
		Cantidad_Reagendamiento = cantidad_Reagendamiento;
	}
	public int getMonto_Pago_Estudio() {
		return Monto_Pago_Estudio;
	}
	public void setMonto_Pago_Estudio(int monto_Pago_Estudio) {
		Monto_Pago_Estudio = monto_Pago_Estudio;
	}
	public int getMonto_Pago_Adelantado() {
		return Monto_Pago_Adelantado;
	}
	public void setMonto_Pago_Adelantado(int monto_Pago_Adelantado) {
		Monto_Pago_Adelantado = monto_Pago_Adelantado;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	public String getCodigo() {
		return Codigo;
	}
	public void setCodigo(String codigo) {
		Codigo = codigo;
	}
	public boolean isValidado() {
		return Validado;
	}
	public void setValidado(boolean validado) {
		Validado = validado;
	}
	public String getNombre_Anticipo() {
		return Nombre_Anticipo;
	}
	public void setNombre_Anticipo(String nombre_Anticipo) {
		Nombre_Anticipo = nombre_Anticipo;
	}
	public String getFecha_Anticipo() {
		return Fecha_Anticipo;
	}
	public void setFecha_Anticipo(String fecha_Anticipo) {
		Fecha_Anticipo = fecha_Anticipo;
	}
	public String getHora_Anticipo() {
		return Hora_Anticipo;
	}
	public void setHora_Anticipo(String hora_Anticipo) {
		Hora_Anticipo = hora_Anticipo;
	}
	public String getTipo_Anticipo() {
		return Tipo_Anticipo;
	}
	public void setTipo_Anticipo(String tipo_Anticipo) {
		Tipo_Anticipo = tipo_Anticipo;
	}
	public boolean isPreReserva() {
		return PreReserva;
	}
	public void setPreReserva(boolean preReserva) {
		PreReserva = preReserva;
	}
	public int getVendedor() {
		return Vendedor;
	}
	public void setVendedor(int vendedor) {
		Vendedor = vendedor;
	}
	public int getId_Metodo_Pago() {
		return Id_Metodo_Pago;
	}
	public void setId_Metodo_Pago(int id_Metodo_Pago) {
		Id_Metodo_Pago = id_Metodo_Pago;
	}
	public String getObservacion() {
		return Observacion;
	}
	public void setObservacion(String observacion) {
		Observacion = observacion;
	}
	
}
