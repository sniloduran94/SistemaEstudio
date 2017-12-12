package modelo;

public class Contacto_Proveedor {
	private int Id_Contacto;
	private int Id_Proveedor;
	private int Rut;
	private String Nombre;
	private String Apellido_Pat;
	private String Apellido_Mat;
	private int Fono;
	private int Cel;
	private int Id_Estado;
	
	
	public Contacto_Proveedor(int id_Contacto, int id_Proveedor, int rut, String nombre, String apellido_Pat,
			String apellido_Mat, int fono, int cel, int id_Estado) {
		super();
		Id_Contacto = id_Contacto;
		Id_Proveedor = id_Proveedor;
		Rut = rut;
		Nombre = nombre;
		Apellido_Pat = apellido_Pat;
		Apellido_Mat = apellido_Mat;
		Fono = fono;
		Cel = cel;
		Id_Estado = id_Estado;
	}
	public int getId_Contacto() {
		return Id_Contacto;
	}
	public void setId_Contacto(int id_Contacto) {
		Id_Contacto = id_Contacto;
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
	public int getFono() {
		return Fono;
	}
	public void setFono(int fono) {
		Fono = fono;
	}
	public int getCel() {
		return Cel;
	}
	public void setCel(int cel) {
		Cel = cel;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
}
