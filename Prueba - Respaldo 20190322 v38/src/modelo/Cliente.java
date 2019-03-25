package modelo;

public class Cliente {
	private int Id_Cliente;
	private int Rut;
	private String Nombre;
	private String Apellido_Pat;
	private String Apellido_Mat;
	private String Direccion;
	private int Id_Comuna;
	private int Id_Ciudad;
	private int Fono;
	private int Celular;
	private String Mail;
	private int Id_Tipo_Cliente;
	private boolean Reclamo;
	private String Observacion;
	private int Id_Estado;
	private String Constrasenia;
	
	public Cliente(int id_Cliente, int rut, String nombre, String apellido_Pat, String apellido_Mat, String direccion,
			int id_Comuna, int id_Ciudad, int fono, int celular, String mail, int id_Tipo_Cliente, boolean reclamo,
			String observacion, int id_Estado, String constrasenia) {
		super();
		Id_Cliente = id_Cliente;
		Rut = rut;
		Nombre = nombre;
		Apellido_Pat = apellido_Pat;
		Apellido_Mat = apellido_Mat;
		Direccion = direccion;
		Id_Comuna = id_Comuna;
		Id_Ciudad = id_Ciudad;
		Fono = fono;
		Celular = celular;
		Mail = mail;
		Id_Tipo_Cliente = id_Tipo_Cliente;
		Reclamo = reclamo;
		Observacion = observacion;
		Id_Estado = id_Estado;
		Constrasenia = constrasenia;
	}
	public Cliente() {
		// TODO Auto-generated constructor stub
	}
	public int getId_Cliente() {
		return Id_Cliente;
	}
	public void setId_Cliente(int id_Cliente) {
		Id_Cliente = id_Cliente;
	}
	public int getRut() {
		return Rut;
	}
	public void setRut(int rut) {
		Rut = rut;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getApellido_Pat() {
		return Apellido_Pat;
	}
	public void setApellido_Pat(String apellido_Pat) {
		Apellido_Pat = apellido_Pat;
	}
	public String getApellido_Mat() {
		return Apellido_Mat;
	}
	public void setApellido_Mat(String apellido_Mat) {
		Apellido_Mat = apellido_Mat;
	}
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public int getId_Comuna() {
		return Id_Comuna;
	}
	public void setId_Comuna(int id_Comuna) {
		Id_Comuna = id_Comuna;
	}
	public int getId_Ciudad() {
		return Id_Ciudad;
	}
	public void setId_Ciudad(int id_Ciudad) {
		Id_Ciudad = id_Ciudad;
	}
	public int getFono() {
		return Fono;
	}
	public void setFono(int fono) {
		Fono = fono;
	}
	public int getCelular() {
		return Celular;
	}
	public void setCelular(int celular) {
		Celular = celular;
	}
	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	public int getId_Tipo_Cliente() {
		return Id_Tipo_Cliente;
	}
	public void setId_Tipo_Cliente(int id_Tipo_Cliente) {
		Id_Tipo_Cliente = id_Tipo_Cliente;
	}
	public boolean isReclamo() {
		return Reclamo;
	}
	public void setReclamo(boolean reclamo) {
		Reclamo = reclamo;
	}
	public String getObservacion() {
		return Observacion;
	}
	public void setObservacion(String observacion) {
		Observacion = observacion;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	public String getConstrasenia() {
		return Constrasenia;
	}
	public void setConstrasenia(String constrasenia) {
		Constrasenia = constrasenia;
	}
	
	
}
