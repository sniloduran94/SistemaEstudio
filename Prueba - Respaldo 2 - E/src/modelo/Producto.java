package modelo;

public class Producto {
	private int Id_Producto;
	private String Producto;
	private String Descripcion;
	private int Id_Tipo_Producto;
	private int Id_Grupo_Producto;
	private int Id_Proveedor;
	private int Precio_Vta;
	private int Precio_Cto;
	private int Id_Estado;
	private boolean Mueve_Stock;
	private int Stock_Producto;
	
	
	public Producto(int id_Producto, String producto, String descripcion, int id_Tipo_Producto, int id_Grupo_Producto,
			int id_Proveedor, int precio_Vta, int precio_Cto, int id_Estado, boolean mueve_Stock, int stock_Producto) {
		super();
		Id_Producto = id_Producto;
		Producto = producto;
		Descripcion = descripcion;
		Id_Tipo_Producto = id_Tipo_Producto;
		Id_Grupo_Producto = id_Grupo_Producto;
		Id_Proveedor = id_Proveedor;
		Precio_Vta = precio_Vta;
		Precio_Cto = precio_Cto;
		Id_Estado = id_Estado;
		Mueve_Stock = mueve_Stock;
		Stock_Producto = stock_Producto;
	}
	public int getId_Producto() {
		return Id_Producto;
	}
	public void setId_Producto(int id_Producto) {
		Id_Producto = id_Producto;
	}
	public String getProducto() {
		return Producto;
	}
	public void setProducto(String producto) {
		Producto = producto;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public int getId_Tipo_Producto() {
		return Id_Tipo_Producto;
	}
	public void setId_Tipo_Producto(int id_Tipo_Producto) {
		Id_Tipo_Producto = id_Tipo_Producto;
	}
	public int getId_Grupo_Producto() {
		return Id_Grupo_Producto;
	}
	public void setId_Grupo_Producto(int id_Grupo_Producto) {
		Id_Grupo_Producto = id_Grupo_Producto;
	}
	public int getId_Proveedor() {
		return Id_Proveedor;
	}
	public void setId_Proveedor(int id_Proveedor) {
		Id_Proveedor = id_Proveedor;
	}
	public int getPrecio_Vta() {
		return Precio_Vta;
	}
	public void setPrecio_Vta(int precio_Vta) {
		Precio_Vta = precio_Vta;
	}
	public int getPrecio_Cto() {
		return Precio_Cto;
	}
	public void setPrecio_Cto(int precio_Cto) {
		Precio_Cto = precio_Cto;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	public boolean isMueve_Stock() {
		return Mueve_Stock;
	}
	public void setMueve_Stock(boolean mueve_Stock) {
		Mueve_Stock = mueve_Stock;
	}
	public int getStock_Producto() {
		return Stock_Producto;
	}
	public void setStock_Producto(int stock_Producto) {
		Stock_Producto = stock_Producto;
	}
}
