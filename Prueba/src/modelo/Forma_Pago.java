package modelo;

public class Forma_Pago {

	private int Id_Forma_Pago;
	private String Forma_Pago;
	private int Id_Estado;
		
	public Forma_Pago(int id_Forma_Pago, String forma_Pago, int id_Estado) {
		super();
		Id_Forma_Pago = id_Forma_Pago;
		Forma_Pago = forma_Pago;
		Id_Estado = id_Estado;
	}
	public int getId_Forma_Pago() {
		return Id_Forma_Pago;
	}
	public void setId_Forma_Pago(int id_Forma_Pago) {
		Id_Forma_Pago = id_Forma_Pago;
	}
	public String getForma_Pago() {
		return Forma_Pago;
	}
	public void setForma_Pago(String forma_Pago) {
		Forma_Pago = forma_Pago;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}	
	
}
