package modelo;

import java.util.Date;

public class Campania {
	private int Id_Campania;
	private int Id_Canal_Venta;
	private java.util.Date Inicio_Vigencia;
	private java.util.Date Fin_Vigencia;
	private int Precio;
	private int Maximo_Personas;
	private String Descripcion = "";
	private int Id_Estado;
	private String Nombre = "";
	private boolean CD = false;
	private int Cant_Fotos_CD = 0;
	private int Cant_10x15 = 0;
	private int Cant_15x21 = 0;
    private int Cant_20x30 = 0;
    private int Cant_30x40 = 0;
    private int Monto_Cv = 0;
    private int Precio_Adicional = 0;
    private int Precio_Reagendamiento = 0;
    private String Descripcion_Adicional = "";
    private int Abono = 0;
   
	public int getAbono() {
		return Abono;
	}
	public void setAbono(int abono) {
		Abono = abono;
	}
	public Campania(int id_Campania, int id_Canal_Venta, Date inicio_Vigencia, Date fin_Vigencia, int precio,
			int maximo_Personas, String descripcion, int id_Estado, String nombre, int id_Tipo_Sesion, boolean cD,
			int cant_Fotos_CD, int cant_10x15, int cant_15x21, int cant_20x30, int cant_30x40) {
		super();
		Id_Campania = id_Campania;
		Id_Canal_Venta = id_Canal_Venta;
		Inicio_Vigencia = inicio_Vigencia;
		Fin_Vigencia = fin_Vigencia;
		Precio = precio;
		Maximo_Personas = maximo_Personas;
		Descripcion = descripcion;
		Id_Estado = id_Estado;
		Nombre = nombre;
		CD = cD;
		Cant_Fotos_CD = cant_Fotos_CD;
		Cant_10x15 = cant_10x15;
		Cant_15x21 = cant_15x21;
		Cant_20x30 = cant_20x30;
		Cant_30x40 = cant_30x40;
	}
	public boolean isCD() {
		return CD;
	}
	public void setCD(boolean cD) {
		CD = cD;
	}
	public int getCant_Fotos_CD() {
		return Cant_Fotos_CD;
	}
	public void setCant_Fotos_CD(int cant_Fotos_CD) {
		Cant_Fotos_CD = cant_Fotos_CD;
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
	public Campania() {
		// TODO Auto-generated constructor stub
	}
	public int getId_Campania() {
		return Id_Campania;
	}
	public void setId_Campania(int id_Campania) {
		Id_Campania = id_Campania;
	}
	public int getId_Canal_Venta() {
		return Id_Canal_Venta;
	}
	public void setId_Canal_Venta(int id_Canal_Venta) {
		Id_Canal_Venta = id_Canal_Venta;
	}
	public java.util.Date getInicio_Vigencia() {
		return Inicio_Vigencia;
	}
	public void setInicio_Vigencia(java.util.Date date) {
		Inicio_Vigencia = date;
	}
	public java.util.Date getFin_Vigencia() {
		return Fin_Vigencia;
	}
	public void setFin_Vigencia(java.util.Date date) {
		Fin_Vigencia = date;
	}
	public int getPrecio() {
		return Precio;
	}
	public void setPrecio(int precio) {
		Precio = precio;
	}
	public int getMaximo_Personas() {
		return Maximo_Personas;
	}
	public void setMaximo_Personas(int maximo_Personas) {
		Maximo_Personas = maximo_Personas;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public int getMonto_Cv() {
		return Monto_Cv;
	}
	public void setMonto_Cv(int monto_Cv) {
		Monto_Cv = monto_Cv;
	}
	public int getPrecio_Adicional() {
		return Precio_Adicional;
	}
	public void setPrecio_Adicional(int precio_Adicional) {
		Precio_Adicional = precio_Adicional;
	}
	public int getPrecio_Reagendamiento() {
		return Precio_Reagendamiento;
	}
	public void setPrecio_Reagendamiento(int precio_Reagendamiento) {
		Precio_Reagendamiento = precio_Reagendamiento;
	}
	public String getDescripcion_Adicional() {
		return Descripcion_Adicional;
	}
	public void setDescripcion_Adicional(String descripcion_Adicional) {
		Descripcion_Adicional = descripcion_Adicional;
	}
	
}
