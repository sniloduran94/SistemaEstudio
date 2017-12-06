package modelo;

import java.util.Date;

public class SesionAuxiliar {
	
	private int Id_Auxiliar;
	private boolean Asistio;
	private int Numero_Ticket;
	private int Valor_Por_Cobrar;
	private int CD;
	private int Extras;
	private int Persona_Adicional;
	private int Recargo_Por_Reagendar;
	private int Monto_Extras;
	private String Fotografo;
	private boolean Fotos_Seleccionadas;
	private java.util.Date Fecha_Entrega;
	private boolean Entregadas;
	private java.util.Date Fecha_Envio_Imprimir;
	private int Monto_Impresion;
	private String Numero_Factura;
	private int Id_Reserva_Asoc;
	private int Cant_10x15 = 0;
	private int Cant_15x21 = 0;
    private int Cant_20x30 = 0;
    private int Cant_30x40 = 0;
	private int Cont_Fecha_Entrega = 0;
	private java.util.Date Fecha_Retiro;
	private String Nombre_Retira = "";
	private boolean Lista_Para_Entregar;
	private java.util.Date Fecha_Sesion;
	private int CampaniaConvetida = 0;
	
    
	public SesionAuxiliar() {
		// TODO Auto-generated constructor stub
	}

	public int getId_Auxiliar() {
		return Id_Auxiliar;
	}
	public void setId_Auxiliar(int id_Auxiliar) {
		Id_Auxiliar = id_Auxiliar;
	}
	public boolean isAsistio() {
		return Asistio;
	}
	public void setAsistio(boolean asistio) {
		Asistio = asistio;
	}
	public int getNumero_Ticket() {
		return Numero_Ticket;
	}
	public void setNumero_Ticket(int numero_Ticket) {
		Numero_Ticket = numero_Ticket;
	}
	public int getValor_Por_Cobrar() {
		return Valor_Por_Cobrar;
	}
	public void setValor_Por_Cobrar(int valor_Por_Cobrar) {
		Valor_Por_Cobrar = valor_Por_Cobrar;
	}
	public int getCD() {
		return CD;
	}
	public void setCD(int cD) {
		CD = cD;
	}
	public int getExtras() {
		return Extras;
	}
	public void setExtras(int extras) {
		Extras = extras;
	}
	public int getPersona_Adicional() {
		return Persona_Adicional;
	}
	public void setPersona_Adicional(int persona_Adicional) {
		Persona_Adicional = persona_Adicional;
	}
	public int getRecargo_Por_Reagendar() {
		return Recargo_Por_Reagendar;
	}
	public void setRecargo_Por_Reagendar(int recargo_Por_Reagendar) {
		Recargo_Por_Reagendar = recargo_Por_Reagendar;
	}
	public int getMonto_Extras() {
		return Monto_Extras;
	}
	public void setMonto_Extras(int monto_Extras) {
		Monto_Extras = monto_Extras;
	}
	public String getFotografo() {
		return Fotografo;
	}
	public void setFotografo(String fotografo) {
		Fotografo = fotografo;
	}
	public boolean isFotos_Seleccionadas() {
		return Fotos_Seleccionadas;
	}
	public void setFotos_Seleccionadas(boolean fotos_Seleccionadas) {
		Fotos_Seleccionadas = fotos_Seleccionadas;
	}
	public java.util.Date getFecha_Entrega() {
		return Fecha_Entrega;
	}
	public void setFecha_Entrega(java.util.Date fecha_Entrega) {
		Fecha_Entrega = fecha_Entrega;
	}
	public boolean isEntregadas() {
		return Entregadas;
	}
	public void setEntregadas(boolean entregadas) {
		Entregadas = entregadas;
	}
	public java.util.Date getFecha_Envio_Imprimir() {
		return Fecha_Envio_Imprimir;
	}
	public void setFecha_Envio_Imprimir(java.util.Date fecha_Envio_Imprimir) {
		Fecha_Envio_Imprimir = fecha_Envio_Imprimir;
	}
	public int getMonto_Impresion() {
		return Monto_Impresion;
	}
	public void setMonto_Impresion(int monto_Impresion) {
		Monto_Impresion = monto_Impresion;
	}
	public String getNumero_Factura() {
		return Numero_Factura;
	}
	public void setNumero_Factura(String numero_Factura) {
		Numero_Factura = numero_Factura;
	}
	public int getId_Reserva_Asoc() {
		return Id_Reserva_Asoc;
	}
	public void setId_Reserva_Asoc(int id_Reserva_Asoc) {
		Id_Reserva_Asoc = id_Reserva_Asoc;
	}

	public int getCant_10x15() {
		return Cant_10x15;
	}

	public void setCant_10x15(int cant_10x15) {
		Cant_10x15 = cant_10x15;
	}

	public int getCant_15x21() {
		return Cant_15x21;
	}

	public void setCant_15x21(int cant_15x21) {
		Cant_15x21 = cant_15x21;
	}

	public int getCant_20x30() {
		return Cant_20x30;
	}

	public void setCant_20x30(int cant_20x30) {
		Cant_20x30 = cant_20x30;
	}

	public int getCant_30x40() {
		return Cant_30x40;
	}

	public void setCant_30x40(int cant_30x40) {
		Cant_30x40 = cant_30x40;
	}

	public int getCont_Fecha_Entrega() {
		return Cont_Fecha_Entrega;
	}

	public void setCont_Fecha_Entrega(int cont_Fecha_Entrega) {
		Cont_Fecha_Entrega = cont_Fecha_Entrega;
	}

	public java.util.Date getFecha_Retiro() {
		return Fecha_Retiro;
	}

	public void setFecha_Retiro(java.util.Date fecha_Retiro) {
		Fecha_Retiro = fecha_Retiro;
	}

	public String getNombre_Retira() {
		return Nombre_Retira;
	}

	public void setNombre_Retira(String nombre_Retira) {
		Nombre_Retira = nombre_Retira;
	}

	public boolean isLista_Para_Entregar() {
		return Lista_Para_Entregar;
	}

	public void setLista_Para_Entregar(boolean lista_Para_Entregar) {
		Lista_Para_Entregar = lista_Para_Entregar;
	}

	public java.util.Date getFecha_Sesion() {
		return Fecha_Sesion;
	}

	public void setFecha_Sesion(java.util.Date fecha_Sesion) {
		Fecha_Sesion = fecha_Sesion;
	}

	public int getCampaniaConvetida() {
		return CampaniaConvetida;
	}

	public void setCampaniaConvetida(int campaniaConvetida) {
		CampaniaConvetida = campaniaConvetida;
	}
}
