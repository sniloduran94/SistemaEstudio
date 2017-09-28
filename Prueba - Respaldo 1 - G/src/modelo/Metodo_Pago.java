package modelo;

public class Metodo_Pago {
	private int Id_Metodo_Pago;
	private String Nombre;
	private String Descripcion;
	
	
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public int getId_Metodo_Pago() {
		return Id_Metodo_Pago;
	}
	public void setId_Metodo_Pago(int id_Metodo_Pago) {
		Id_Metodo_Pago = id_Metodo_Pago;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
		
	
}
