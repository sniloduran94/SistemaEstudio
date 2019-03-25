package modelo;

public class Item_Pago {

	private int Id_Item_Pago;
	private String Item_Pago;
	private int Id_Estado;
	
	
	public Item_Pago(int id_Item_Pago, String item_Pago, int id_Estado) {
		super();
		Id_Item_Pago = id_Item_Pago;
		Item_Pago = item_Pago;
		Id_Estado = id_Estado;
	}
	public int getId_Item_Pago() {
		return Id_Item_Pago;
	}
	public void setId_Item_Pago(int id_Item_Pago) {
		Id_Item_Pago = id_Item_Pago;
	}
	public String getItem_Pago() {
		return Item_Pago;
	}
	public void setItem_Pago(String item_Pago) {
		Item_Pago = item_Pago;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	
	
}
