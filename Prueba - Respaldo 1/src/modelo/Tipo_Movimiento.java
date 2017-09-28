package modelo;

public class Tipo_Movimiento {
	private int Id_Tipo_Movimiento;
	private String Tipo_Movimiento;
	private String Descripcion_Tipo;
	private int Id_Estado;
	
	
	public Tipo_Movimiento(int id_Tipo_Movimiento, String tipo_Movimiento, String descripcion_Tipo, int id_Estado) {
		super();
		Id_Tipo_Movimiento = id_Tipo_Movimiento;
		Tipo_Movimiento = tipo_Movimiento;
		Descripcion_Tipo = descripcion_Tipo;
		Id_Estado = id_Estado;
	}
	public int getId_Tipo_Movimiento() {
		return Id_Tipo_Movimiento;
	}
	public void setId_Tipo_Movimiento(int id_Tipo_Movimiento) {
		Id_Tipo_Movimiento = id_Tipo_Movimiento;
	}
	public String getTipo_Movimiento() {
		return Tipo_Movimiento;
	}
	public void setTipo_Movimiento(String tipo_Movimiento) {
		Tipo_Movimiento = tipo_Movimiento;
	}
	public String getDescripcion_Tipo() {
		return Descripcion_Tipo;
	}
	public void setDescripcion_Tipo(String descripcion_Tipo) {
		Descripcion_Tipo = descripcion_Tipo;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
}
