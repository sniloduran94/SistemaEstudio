package modelo;

public class Proveedor {
	private int Id_Proveedor;
	private int Rut;
	private String Nombre;
	private int Id_Giro;
	private String Direccion;
	private int Id_Comuna;
	private int Id_Ciudad;
	private int Id_Estado;
	
	
	public Proveedor(int id_Proveedor, int rut, String nombre, int id_Giro, String direccion, int id_Comuna,
			int id_Ciudad, int id_Estado) {
		super();
		Id_Proveedor = id_Proveedor;
		Rut = rut;
		Nombre = nombre;
		Id_Giro = id_Giro;
		Direccion = direccion;
		Id_Comuna = id_Comuna;
		Id_Ciudad = id_Ciudad;
		Id_Estado = id_Estado;
	}
	public int getId_Proveedor() {
		return Id_Proveedor;
	}
	public void setId_Proveedor(int id_Proveedor) {
		Id_Proveedor = id_Proveedor;
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
	public int getId_Giro() {
		return Id_Giro;
	}
	public void setId_Giro(int id_Giro) {
		Id_Giro = id_Giro;
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
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
}
