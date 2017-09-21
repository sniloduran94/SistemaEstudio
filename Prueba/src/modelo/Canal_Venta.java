package modelo;

public class Canal_Venta {
	private int Id_Canal_Venta;
	private String Canal;
	private String Descripcion;
	private int Id_Estado;
	private boolean Requiere_Cupon;
	
	public Canal_Venta(int id_Canal_Venta, String canal, String descripcion, int id_Estado, boolean requiere_Cupon) {
		super();
		Id_Canal_Venta = id_Canal_Venta;
		Canal = canal;
		Descripcion = descripcion;
		Id_Estado = id_Estado;
		Requiere_Cupon = requiere_Cupon;
	}
	
	public Canal_Venta() {
		// TODO Auto-generated constructor stub
	}

	public int getId_Canal_Venta() {
		return Id_Canal_Venta;
	}
	public void setId_Canal_Venta(int id_Canal_Venta) {
		Id_Canal_Venta = id_Canal_Venta;
	}
	public String getCanal() {
		return Canal;
	}
	public void setCanal(String canal) {
		Canal = canal;
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
	public boolean isRequiere_Cupon() {
		return Requiere_Cupon;
	}
	public void setRequiere_Cupon(boolean requiere_Cupon) {
		Requiere_Cupon = requiere_Cupon;
	}
	
	
}
