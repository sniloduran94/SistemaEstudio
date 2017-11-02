package conexion;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import modelo.*;

public class SQLS_conexion {
	//private static String cadenaConexion = "jdbc:sqlserver://EXPRESSIONES\\SQLEXPRESS;databaseName=EstudioFotografico";
	private static String cadenaConexion = "jdbc:sqlserver://localhost:1433;databaseName=Prueba";
	
	
	public static Connection cn = getConexion();

	/**
	 * @author Advancing
	 * Método que devuelve la conexión con la base de datos 
	 */
	private static Connection getConexion() {
		Connection cn = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			cn = DriverManager.getConnection(cadenaConexion,"expressiones","Advancing2016");
			//cn = DriverManager.getConnection(cadenaConexion,"foto","1234");
			
		} catch (ClassNotFoundException e) {
			System.out.println("Ocurrió un error: " + e);
		} catch (SQLException e) {
			System.out.println("Ocurrió un error: " + e);
		}
		return cn;
	}

	/**
	 * @author Advancing
	 * Método que devuelve el estado de la conexión con la base de datos 
	 */
	public static void estadoConexion() {
		if (getConexion() != null)
			System.out.println("Conexión exitosa");
		else
			System.out.println("Conexión no lograda");
	}

	/**
	 * @author Advancing
	 * @param String SQL que contiene una consulta
	 * Método que ejecuta una consulta en la base de datos 
	 */
	public ResultSet Consultar(String SQL) {
		
		Connection CN = getConexion();
		ResultSet rs = null;
		Statement Sentencia = null;

		try {
			Sentencia = CN.createStatement();
			rs = Sentencia.executeQuery(SQL);
		} catch (SQLException e) {

		}
		return rs;
	}
	
	/**
	 * @author Advancing
	 * Método que cierra la conexión a la base de datos 
	 */
	public Connection cerrarConexion(){
        try {
            cn.close();
             System.out.println("Cerrando conexion");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        cn = null;
        return cn;
    }
		
	/**
	 * @author Advancing
	 * @param int id que indica la ID del usuario a buscar(rut) 
	 * @param String Contrasenia que indica la contraseña para dada ID
	 * Obtención de trabajador para un usuario y contraseña determinados
	 */
	public ArrayList<Object> ExisteUsuario(String id, String contrasenia) throws SQLException{
		String SQL = "SELECT * FROM [DBO].[04_TRABAJADOR] WHERE [04_Email] = '"+id+"' AND [04_CONTRASENIA] = '"+contrasenia+"';";
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Object> array = new ArrayList<Object>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Trabajador cc = new Trabajador ();
			cc.setApellido_Mat(rs.getString("04_APELLIDO_MAT"));
			cc.setApellido_Pat(rs.getString("04_APELLIDO_PAT"));
			cc.setContrasenia(rs.getString("04_CONTRASENIA"));
			cc.setEsAdmin(rs.getInt("04_ESADMIN"));
			cc.setId_Cargo(rs.getInt("19_ID_CARGO"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
			cc.setNombre(rs.getString("04_NOMBRE"));
			cc.setRut(rs.getInt("04_RUT"));
			cc.setEmail(rs.getString("04_EMAIL"));
			array.add(cc);
		}
		return array;
	}

	/**
	 * @author Advancing
	 * @param int id que indica la ID del usuario a buscar(rut)
	 * Obtención de trabajador para un usuario determinado (Sólo si existe, sin saber su contraseña)
	 */
	public ArrayList<Object> ExisteUsuarioSC(int id) throws SQLException{
		String SQL = "SELECT * FROM [DBO].[04_TRABAJADOR] WHERE [04_RUT] = "+id+";";
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Object> array = new ArrayList<Object>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Trabajador cc = new Trabajador ();
			cc.setApellido_Mat(rs.getString("04_APELLIDO_MAT"));
			cc.setApellido_Pat(rs.getString("04_APELLIDO_PAT"));
			cc.setContrasenia(rs.getString("04_CONTRASENIA"));
			cc.setEsAdmin(rs.getInt("04_ESADMIN"));
			cc.setId_Cargo(rs.getInt("19_ID_CARGO"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
			cc.setNombre(rs.getString("04_NOMBRE"));
			cc.setRut(rs.getInt("04_RUT"));
			cc.setEmail(rs.getString("04_EMAIL"));
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * Obtención de campañas
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Campania> getCampañas() throws SQLException, java.text.ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		String SQL = "SELECT [17_Campania].*"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania]";
		ResultSet rs = Consultar(SQL);
				
		ArrayList<Campania> array = new ArrayList<Campania>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Campania cc = new Campania ();
			cc.setDescripcion(rs.getString("17_DESCRIPCION"));
			if(rs.getString("fecha2")==null){
				cc.setFin_Vigencia(null);				
			}else{
				cc.setFin_Vigencia(sdf.parse(rs.getString("fecha2")));
			}
			if(rs.getString("fecha1")==null){
				cc.setInicio_Vigencia(null);
			}else{
				cc.setInicio_Vigencia(sdf.parse(rs.getString("fecha1")));
			}
			
			cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
			cc.setId_Canal_Venta(rs.getInt("14_ID_CANAL_VENTA"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			cc.setMaximo_Personas(rs.getInt("17_MAXIMO_PERSONAS"));
			cc.setPrecio(rs.getInt("17_PRECIO"));
			cc.setNombre(rs.getString("17_NOMBRE"));
			cc.setCD(rs.getBoolean("17_POSEE_CD"));
			cc.setCant_Fotos_CD(rs.getInt("17_CANT_FOTOS_CD"));
			cc.setCant_10x15(rs.getInt("17_CANT_10x15"));
			cc.setCant_15x21(rs.getInt("17_CANT_15X21"));
			cc.setCant_20x30(rs.getInt("17_CANT_20X30"));
			cc.setCant_30x40(rs.getInt("17_CANT_30X40"));
			cc.setPrecio_Adicional(rs.getInt("17_Precio_Adicional"));
			cc.setPrecio_Reagendamiento(rs.getInt("17_Precio_Reagendamiento"));
			cc.setDescripcion_Adicional(rs.getString("17_Descripcion_Adicional"));
			cc.setAbono(rs.getInt("17_ABONO"));
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * Obtención de campañas
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Campania> getCampañasVigentes() throws SQLException, java.text.ParseException{
		
		 java.util.Date ahora = new java.util.Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String hoy = sdf.format(ahora);
		
		String SQL = "   SELECT * FROM (SELECT [17_Campania].*"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania]) AS T"
					+ "  WHERE '"+hoy+"' BETWEEN fecha1 AND fecha2";
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Campania> array = new ArrayList<Campania>();
				
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Campania cc = new Campania ();
			cc.setDescripcion(rs.getString("17_DESCRIPCION"));
			if(rs.getString("fecha2")==null){
				cc.setFin_Vigencia(null);				
			}else{
				cc.setFin_Vigencia(sdf.parse(rs.getString("fecha2")));
			}
			if(rs.getString("fecha1")==null){
				cc.setInicio_Vigencia(null);
			}else{
				cc.setInicio_Vigencia(sdf.parse(rs.getString("fecha1")));
			}
			
			cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
			cc.setId_Canal_Venta(rs.getInt("14_ID_CANAL_VENTA"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			cc.setMaximo_Personas(rs.getInt("17_MAXIMO_PERSONAS"));
			cc.setPrecio(rs.getInt("17_PRECIO"));
			cc.setNombre(rs.getString("17_NOMBRE"));
			cc.setCD(rs.getBoolean("17_POSEE_CD"));
			cc.setCant_Fotos_CD(rs.getInt("17_CANT_FOTOS_CD"));
			cc.setCant_10x15(rs.getInt("17_CANT_10x15"));
			cc.setCant_15x21(rs.getInt("17_CANT_15X21"));
			cc.setCant_20x30(rs.getInt("17_CANT_20X30"));
			cc.setCant_30x40(rs.getInt("17_CANT_30X40"));
			cc.setPrecio_Adicional(rs.getInt("17_Precio_Adicional"));
			cc.setPrecio_Reagendamiento(rs.getInt("17_Precio_Reagendamiento"));
			cc.setDescripcion_Adicional(rs.getString("17_Descripcion_Adicional"));
			cc.setAbono(rs.getInt("17_ABONO"));
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param Canal_Venta cv que se ingresará a la BD
	 * Inserción de un canal de venta a la BD 
	 */
	public int IngresarCanalDeVenta(Canal_Venta cv) throws SQLException {
		
		int bool = (cv.isRequiere_Cupon())?1:0;
		
		String Insert = "INSERT INTO [dbo].[14_Canal_Venta]"
				+ "	           ([14_Canal]"
				+ "	           ,[14_Descripcion]"
				+ "	           ,[14_Requiere_Cupon]"
				+ "	           ,[11_Id_Estado])"
				+ "	     VALUES"
				+ "	           ('"+cv.getCanal()+"'"
				+ "	           ,'"+cv.getDescripcion()+"'"
				+ "	           ,"+bool+""
				+ "	           ,"+cv.getId_Estado()+")";
	           
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(Insert);
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	
	
	/**
	 * @author Advancing
	 * @param Metodo_Pago que se ingresará a la BD
	 * Inserción de un método de pago a la BD 
	 */
	public int IngresarMetodoPago(Metodo_Pago cv) throws SQLException {
				
		String Insert = "INSERT INTO [dbo].[37_Metodo_Pago]"
				+ "	           ([37_Nombre],[37_Descripcion])"
				+ "	     VALUES"
				+ "	           ('"+cv.getNombre()+"','"+cv.getDescripcion()+"')";
	           
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(Insert);
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	
	
	/**
	 * @author Advancing
	 * Obtención de canales de venta
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Canal_Venta> getCanalesVentas() throws SQLException, java.text.ParseException{
		String SQL = "SELECT * FROM [DBO].[14_Canal_Venta];";
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Canal_Venta> array = new ArrayList<Canal_Venta>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Canal_Venta cc = new Canal_Venta (rs.getInt("14_ID_CANAL_VENTA"),rs.getString("14_CANAL"),
				  rs.getString("14_DESCRIPCION"), rs.getInt("11_ID_ESTADO"), rs.getBoolean("14_Requiere_Cupon"));
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * Obtención de canales de venta
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Canal_Venta> getCanalesVentasFiltro(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";				
		
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT * FROM [DBO].[14_Canal_Venta] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT * FROM [DBO].[14_Canal_Venta];";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Canal_Venta> array = new ArrayList<Canal_Venta>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Canal_Venta cc = new Canal_Venta (rs.getInt("14_ID_CANAL_VENTA"),rs.getString("14_CANAL"),
				  rs.getString("14_DESCRIPCION"), rs.getInt("11_ID_ESTADO"), rs.getBoolean("14_Requiere_Cupon"));
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * Obtención de canales de venta en un arreglo
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<String>> getCanalesVentasArray() throws SQLException, java.text.ParseException{
		String SQL = "SELECT [dbo].[17_Campania].[17_Id_Campania],[dbo].[17_Campania].[14_Id_Canal_Venta] AS CANAL,"
				+ "[dbo].[14_Canal_Venta].[14_Id_Canal_Venta], [dbo].[14_Canal_Venta].[14_Canal],"
				+ "[dbo].[14_Canal_Venta].[14_Requiere_Cupon], [dbo].[17_Campania].[17_Abono] "
				+ "FROM [dbo].[17_Campania],[dbo].[14_Canal_Venta]"
				+ "WHERE [dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_Canal_Venta].[14_Id_Canal_Venta];";
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		int cont  = 0 ;
		while (rs.next()) {
			ArrayList<String> arreglo = new ArrayList<String>();
			arreglo.add("['"+rs.getString("17_Id_Campania")+"']");
			arreglo.add("['"+rs.getString("14_Requiere_Cupon")+"']");
			arreglo.add("['"+rs.getString("17_Abono")+"']");
			array.add(cont, arreglo);
			cont++;
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param Canal_Venta cv que se actualizado en la BD
	 * Actualización de un canal de venta a la BD 
	 */
	public int ActualizarCanalDeVenta(Canal_Venta cv) throws SQLException {
		
		int bool = (cv.isRequiere_Cupon())?1:0;
		
		String SQL = "UPDATE [dbo].[14_Canal_Venta]"
				+ "	SET [14_Canal] = '"+cv.getCanal()+"'"
				+ ",[14_Descripcion] = '"+cv.getDescripcion()+"'"
				+ ",[14_Requiere_Cupon] = "+bool+""
				+ ",[11_Id_Estado] = "+cv.getId_Estado()+""
				+ "WHERE [14_Id_Canal_Venta] = "+cv.getId_Canal_Venta()+"";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	
	
	public int ActualizarMetodoPago(Metodo_Pago cv) throws SQLException {
				
		String SQL = "UPDATE [dbo].[37_Metodo_Pago]"
				+ "	SET [37_Nombre] = '"+cv.getNombre()+"'"
				+ ",[37_Descripcion] = '"+cv.getDescripcion()+"'"
				+ " WHERE [37_Id_Metodo_Pago] = "+cv.getId_Metodo_Pago()+"";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	
	
	/**
	 *  @author Advancing
	 * 	@param int id que indica la ID de un canal que se eliminará
	 * 	Eliminar una Canal De Venta
	 */
	public int EliminarCanalDeVenta(int id){
		
		String SQL = "DELETE FROM [dbo].[14_Canal_Venta] WHERE [dbo].[14_Canal_Venta].[14_Id_Canal_Venta] = "+id+";";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	/**
	 * @author Advancing
	 * Obtención de Tipos de Sesion
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Tipo_Sesion> getTiposSesiones() throws SQLException, java.text.ParseException{
		String SQL = "SELECT * FROM [DBO].[34_TIPO_SESION];";
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Tipo_Sesion> array = new ArrayList<Tipo_Sesion>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Tipo_Sesion cc = new Tipo_Sesion (rs.getInt("34_ID_TIPO_SESION"),
					rs.getString("34_TIPO_SESION"),
					rs.getInt("34_ID_ESTADO"));
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla campaña
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de campañas con un filtro
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Campania> getCampañasFiltro(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT [17_Campania].*"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT [17_Campania].*"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania]";
		}
		
		ResultSet rs = Consultar(SQL);
		System.out.println(SQL);
		
		ArrayList<Campania> array = new ArrayList<Campania>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Campania cc = new Campania ();
			cc.setDescripcion(rs.getString("17_Descripcion"));
			cc.setFin_Vigencia(this.ConvertirDia(rs.getString("fecha2")));
			cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
			cc.setId_Canal_Venta(rs.getInt("14_ID_CANAL_VENTA"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			cc.setInicio_Vigencia(this.ConvertirDia(rs.getString("fecha1")));
			
			cc.setMaximo_Personas(rs.getInt("17_MAXIMO_PERSONAS"));
			cc.setPrecio(rs.getInt("17_PRECIO"));			
			cc.setNombre(rs.getString("17_NOMBRE"));
			cc.setCD(rs.getBoolean("17_Posee_CD"));
			cc.setCant_Fotos_CD(rs.getInt("17_Cant_Fotos_CD"));
			cc.setCant_10x15(rs.getInt("17_Cant_10x15"));
			cc.setCant_15x21(rs.getInt("17_Cant_15x21"));
			cc.setCant_20x30(rs.getInt("17_Cant_20x30"));
			cc.setCant_30x40(rs.getInt("17_Cant_30x40"));
			cc.setPrecio_Adicional(rs.getInt("17_Precio_Adicional"));
			cc.setPrecio_Reagendamiento(rs.getInt("17_Precio_Reagendamiento"));
			cc.setDescripcion_Adicional(rs.getString("17_Descripcion_Adicional"));
			cc.setAbono(rs.getInt("17_ABONO"));
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla campaña
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de campañas con un filtro
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getCampañasSinId(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";
		
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = " SELECT [17_Campania].*,[14_Canal_Venta].[14_Id_Canal_Venta],[14_Canal_Venta].[14_Canal]"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania], [dbo].[14_Canal_Venta]"
					+ "  WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + " AND  [17_Campania].[14_Id_Canal_Venta]=[14_Canal_Venta].[14_Id_Canal_Venta] "
					+ "		ORDER BY [17_Fin_Vigencia] DESC";
		}else{
			SQL = "  SELECT [17_Campania].*,[14_Canal_Venta].[14_Id_Canal_Venta],[14_Canal_Venta].[14_Canal]"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania], [dbo].[14_Canal_Venta]"
					+ "  WHERE [17_Campania].[14_Id_Canal_Venta]=[14_Canal_Venta].[14_Id_Canal_Venta]"
					+ " ORDER BY [17_Fin_Vigencia] DESC";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
	
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			ArrayList<Object> min = new ArrayList<Object>();
			Campania cc = new Campania ();
			cc.setDescripcion(rs.getString("17_DESCRIPCION"));
			cc.setFin_Vigencia(this.ConvertirDia(rs.getString("FECHA2")));
			cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
			cc.setId_Canal_Venta(rs.getInt("14_ID_CANAL_VENTA"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			cc.setInicio_Vigencia(this.ConvertirDia(rs.getString("FECHA1")));
			cc.setMaximo_Personas(rs.getInt("17_MAXIMO_PERSONAS"));
			cc.setPrecio(rs.getInt("17_PRECIO"));
			cc.setNombre(rs.getString("17_NOMBRE"));
			cc.setCD(rs.getBoolean("17_Posee_CD"));
			cc.setCant_Fotos_CD(rs.getInt("17_Cant_Fotos_CD"));
			cc.setCant_10x15(rs.getInt("17_Cant_10x15"));
			cc.setCant_15x21(rs.getInt("17_Cant_15x21"));
			cc.setCant_20x30(rs.getInt("17_Cant_20x30"));
			cc.setCant_30x40(rs.getInt("17_Cant_30x40"));
			cc.setPrecio_Adicional(rs.getInt("17_Precio_Adicional"));
			cc.setPrecio_Reagendamiento(rs.getInt("17_Precio_Reagendamiento"));
			cc.setDescripcion_Adicional(rs.getString("17_Descripcion_Adicional"));
			cc.setAbono(rs.getInt("17_ABONO"));
			
			//ATRIBUTOS DE TABLA CAMPAÑA
			min.add(cc);
			//ATRIBUTOS DE TABLA CANALVENTA
			min.add(rs.getString("14_CANAL"));
			array.add(min);
		}
		return array;
	}
	
	public ArrayList<ArrayList<Object>> getCampañasSinId(String Excepciones) throws SQLException, java.text.ParseException{
		String SQL = "";
		
		if(!Excepciones.equals("")){
			SQL = " SELECT [17_Campania].*,[14_Canal_Venta].[14_Id_Canal_Venta],[14_Canal_Venta].[14_Canal]"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania], [dbo].[14_Canal_Venta] "
					+ "  WHERE [17_Campania].[14_Id_Canal_Venta]=[14_Canal_Venta].[14_Id_Canal_Venta] "+ Excepciones
					+ "		ORDER BY [17_Fin_Vigencia] DESC";
		}else{
			SQL = "  SELECT [17_Campania].*,[14_Canal_Venta].[14_Id_Canal_Venta],[14_Canal_Venta].[14_Canal]"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania], [dbo].[14_Canal_Venta]"
					+ "  WHERE [17_Campania].[14_Id_Canal_Venta]=[14_Canal_Venta].[14_Id_Canal_Venta]"
					+ " ORDER BY [17_Fin_Vigencia] DESC";
		}
		System.out.println(SQL);
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
	
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			ArrayList<Object> min = new ArrayList<Object>();
			Campania cc = new Campania ();
			cc.setDescripcion(rs.getString("17_DESCRIPCION"));
			cc.setFin_Vigencia(this.ConvertirDia(rs.getString("FECHA2")));
			cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
			cc.setId_Canal_Venta(rs.getInt("14_ID_CANAL_VENTA"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			cc.setInicio_Vigencia(this.ConvertirDia(rs.getString("FECHA1")));
			cc.setMaximo_Personas(rs.getInt("17_MAXIMO_PERSONAS"));
			cc.setPrecio(rs.getInt("17_PRECIO"));
			cc.setNombre(rs.getString("17_NOMBRE"));
			cc.setCD(rs.getBoolean("17_Posee_CD"));
			cc.setCant_Fotos_CD(rs.getInt("17_Cant_Fotos_CD"));
			cc.setCant_10x15(rs.getInt("17_Cant_10x15"));
			cc.setCant_15x21(rs.getInt("17_Cant_15x21"));
			cc.setCant_20x30(rs.getInt("17_Cant_20x30"));
			cc.setCant_30x40(rs.getInt("17_Cant_30x40"));
			cc.setPrecio_Adicional(rs.getInt("17_Precio_Adicional"));
			cc.setPrecio_Reagendamiento(rs.getInt("17_Precio_Reagendamiento"));
			cc.setDescripcion_Adicional(rs.getString("17_Descripcion_Adicional"));
			cc.setAbono(rs.getInt("17_ABONO"));
			
			//ATRIBUTOS DE TABLA CAMPAÑA
			min.add(cc);
			//ATRIBUTOS DE TABLA CANALVENTA
			min.add(rs.getString("14_CANAL"));
			array.add(min);
		}
		return array;
	}
	
	public ArrayList<ArrayList<Object>> getCampañasSinIdLike(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";
		
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = " SELECT [17_Campania].*,[14_Canal_Venta].[14_Id_Canal_Venta],[14_Canal_Venta].[14_Canal]"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania], [dbo].[14_Canal_Venta]"
					+ "  WHERE ["+Columna+"] LIKE ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'%"+Valor+"%'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + " AND  [17_Campania].[14_Id_Canal_Venta]=[14_Canal_Venta].[14_Id_Canal_Venta] "
					+ "		ORDER BY [17_Fin_Vigencia] DESC";
		}else{
			SQL = "  SELECT [17_Campania].*,[14_Canal_Venta].[14_Id_Canal_Venta],[14_Canal_Venta].[14_Canal]"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS fecha1"
					+ "  ,CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS fecha2"
					+ "  FROM [dbo].[17_Campania], [dbo].[14_Canal_Venta]"
					+ "  WHERE [17_Campania].[14_Id_Canal_Venta]=[14_Canal_Venta].[14_Id_Canal_Venta]"
					+ " ORDER BY [17_Fin_Vigencia] DESC";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		System.out.println(SQL);
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			ArrayList<Object> min = new ArrayList<Object>();
			Campania cc = new Campania ();
			cc.setDescripcion(rs.getString("17_DESCRIPCION"));
			cc.setFin_Vigencia(this.ConvertirDia(rs.getString("FECHA2")));
			cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
			cc.setId_Canal_Venta(rs.getInt("14_ID_CANAL_VENTA"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			cc.setInicio_Vigencia(this.ConvertirDia(rs.getString("FECHA1")));
			cc.setMaximo_Personas(rs.getInt("17_MAXIMO_PERSONAS"));
			cc.setPrecio(rs.getInt("17_PRECIO"));
			cc.setNombre(rs.getString("17_NOMBRE"));
			cc.setCD(rs.getBoolean("17_Posee_CD"));
			cc.setCant_Fotos_CD(rs.getInt("17_Cant_Fotos_CD"));
			cc.setCant_10x15(rs.getInt("17_Cant_10x15"));
			cc.setCant_15x21(rs.getInt("17_Cant_15x21"));
			cc.setCant_20x30(rs.getInt("17_Cant_20x30"));
			cc.setCant_30x40(rs.getInt("17_Cant_30x40"));
			cc.setPrecio_Adicional(rs.getInt("17_Precio_Adicional"));
			cc.setPrecio_Reagendamiento(rs.getInt("17_Precio_Reagendamiento"));
			cc.setDescripcion_Adicional(rs.getString("17_Descripcion_Adicional"));
			cc.setAbono(rs.getInt("17_ABONO"));
			
			//ATRIBUTOS DE TABLA CAMPAÑA
			min.add(cc);
			//ATRIBUTOS DE TABLA CANALVENTA
			min.add(rs.getString("14_CANAL"));
			array.add(min);
		}
		return array;
	}
		
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla clientes
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de clientes con un filtro
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Cliente> getClientesFiltro(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT * FROM [DBO].[15_CLIENTE] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT * FROM [DBO].[15_CLIENTE]";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Cliente> array = new ArrayList<Cliente>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			//Crear ID COMUNA Y DE CIUDAD
			Cliente cc = new Cliente ();
			cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
			cc.setRut(rs.getInt("15_RUT"));
			cc.setNombre(rs.getString("15_NOMBRE"));
			cc.setApellido_Pat(rs.getString("15_APELLIDO_PAT"));
			cc.setApellido_Mat(rs.getString("15_APELLIDO_MAT"));
			cc.setDireccion(rs.getString("15_DIRECCION"));
			cc.setId_Comuna(rs.getInt("08_ID_COMUNA"));
			cc.setId_Ciudad(rs.getInt("06_ID_CIUDAD"));
			cc.setFono(rs.getInt("15_FONO"));
			cc.setCelular(rs.getInt("15_CELULAR"));
			cc.setMail(rs.getString("15_MAIL"));
			cc.setId_Tipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
			cc.setReclamo(rs.getBoolean("15_OBSERVACION"));
			cc.setObservacion(rs.getString("15_OBSERVACION"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			cc.setConstrasenia(rs.getString("15_CONTRASENIA"));
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla clientes
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de clientes con nombres y sin números de referencia (Ejemplo, una ID)
	 */
	public ArrayList<ArrayList<Object>> getClientesSinId(String Columna, String Valor, String Tipo) throws SQLException{
		String SQL = "";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT [DBO].[15_CLIENTE].*, [DBO].[06_CIUDAD].[06_ID_CIUDAD], [DBO].[06_CIUDAD].[06_CIUDAD] "
					+ ",[DBO].[08_COMUNA].[08_ID_COMUNA], [DBO].[08_COMUNA].[08_COMUNA]"
					+ "FROM [DBO].[15_CLIENTE] , [DBO].[06_CIUDAD], [DBO].[08_COMUNA] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + "AND [DBO].[15_CLIENTE].[06_ID_CIUDAD] = [DBO].[06_CIUDAD].[06_ID_CIUDAD] "
					+ "AND [DBO].[15_CLIENTE].[08_ID_COMUNA] = [DBO].[08_COMUNA].[08_ID_COMUNA];";
		}else{
			SQL = "SELECT [DBO].[15_CLIENTE].* "
					+ "FROM [DBO].[15_CLIENTE]; ";
		}
		
		ResultSet rs = Consultar(SQL);
		
		System.out.println(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			ArrayList<Object> min = new ArrayList<Object>();
			Cliente cc = new Cliente ();
			cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
			cc.setRut(rs.getInt("15_RUT"));
			cc.setNombre(rs.getString("15_NOMBRE"));
			cc.setApellido_Pat(rs.getString("15_APELLIDO_PAT"));
			cc.setApellido_Mat(rs.getString("15_APELLIDO_MAT"));
			cc.setDireccion(rs.getString("15_DIRECCION"));
			cc.setId_Comuna(rs.getInt("08_ID_COMUNA"));
			cc.setId_Ciudad(rs.getInt("06_ID_CIUDAD"));
			cc.setFono(rs.getInt("15_FONO"));
			cc.setCelular(rs.getInt("15_CELULAR"));
			cc.setMail(rs.getString("15_MAIL"));
			cc.setId_Tipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
			cc.setReclamo(rs.getBoolean("15_OBSERVACION"));
			cc.setObservacion(rs.getString("15_OBSERVACION"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			
			//ATRIBUTOS DE TABLA RESERVA
			min.add(cc);
			//ATRIBUTOS DE TABLA COMUNA Y CIUDAD
			array.add(min);
		}
		return array;		
	}
	
	public ArrayList<ArrayList<Object>> getClientesSinIdLike(String Columna, String Valor, String Tipo) throws SQLException{
		String SQL = "";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT [DBO].[15_CLIENTE].* "
					+ "FROM [DBO].[15_CLIENTE]  WHERE ["+Columna+"] LIKE ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'%"+Valor+"%'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT * "
					+ " FROM [DBO].[15_CLIENTE]; ";
		}
		
		ResultSet rs = Consultar(SQL);
		System.out.println(SQL);
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			ArrayList<Object> min = new ArrayList<Object>();
			Cliente cc = new Cliente ();
			cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
			cc.setRut(rs.getInt("15_RUT"));
			cc.setNombre(rs.getString("15_NOMBRE"));
			cc.setApellido_Pat(rs.getString("15_APELLIDO_PAT"));
			cc.setApellido_Mat(rs.getString("15_APELLIDO_MAT"));
			cc.setDireccion(rs.getString("15_DIRECCION"));
			cc.setId_Comuna(rs.getInt("08_ID_COMUNA"));
			cc.setId_Ciudad(rs.getInt("06_ID_CIUDAD"));
			cc.setFono(rs.getInt("15_FONO"));
			cc.setCelular(rs.getInt("15_CELULAR"));
			cc.setMail(rs.getString("15_MAIL"));
			cc.setId_Tipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
			cc.setReclamo(rs.getBoolean("15_OBSERVACION"));
			cc.setObservacion(rs.getString("15_OBSERVACION"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			
			//ATRIBUTOS DE TABLA RESERVA
			min.add(cc);
			//ATRIBUTOS DE TABLA COMUNA Y CIUDAD
			array.add(min);
		}
		return array;		
	}
	
	public ArrayList<ArrayList<Object>> getClientesSinIdLike(String Excepciones) throws SQLException{
		String SQL = "";
		if(!Excepciones.equals("")){
			SQL = "SELECT [DBO].[15_CLIENTE].* "
					+ "FROM [DBO].[15_CLIENTE] "+Excepciones;
			SQL = SQL + ";";
		}else{
			SQL = "SELECT * "
					+ " FROM [DBO].[15_CLIENTE]; ";
		}
		
		ResultSet rs = Consultar(SQL);
		System.out.println(SQL);
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			ArrayList<Object> min = new ArrayList<Object>();
			Cliente cc = new Cliente ();
			cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
			cc.setRut(rs.getInt("15_RUT"));
			cc.setNombre(rs.getString("15_NOMBRE"));
			cc.setApellido_Pat(rs.getString("15_APELLIDO_PAT"));
			cc.setApellido_Mat(rs.getString("15_APELLIDO_MAT"));
			cc.setDireccion(rs.getString("15_DIRECCION"));
			cc.setId_Comuna(rs.getInt("08_ID_COMUNA"));
			cc.setId_Ciudad(rs.getInt("06_ID_CIUDAD"));
			cc.setFono(rs.getInt("15_FONO"));
			cc.setCelular(rs.getInt("15_CELULAR"));
			cc.setMail(rs.getString("15_MAIL"));
			cc.setId_Tipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
			cc.setReclamo(rs.getBoolean("15_OBSERVACION"));
			cc.setObservacion(rs.getString("15_OBSERVACION"));
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			
			//ATRIBUTOS DE TABLA RESERVA
			min.add(cc);
			//ATRIBUTOS DE TABLA COMUNA Y CIUDAD
			array.add(min);
		}
		return array;		
	}
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla clientes
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de clientes con nombres y sin números de referencia (Ejemplo, una ID)
	 */
	public ArrayList<String> getMailsClientes(String id_cliente) throws SQLException{
		String SQL = "";
		
		if(id_cliente.equals("")){
			SQL = "SELECT [15_Mail] FROM [DBO].[15_Cliente]; ";
		}else{
			SQL = "SELECT [15_Id_Cliente],[15_Mail] FROM [DBO].[15_Cliente] WHERE [15_Id_Cliente] != "+id_cliente+";";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<String> min = new ArrayList<String>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			
			min.add("['"+rs.getString("15_MAIL")+"']");
		}
		return min;		
	}
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de Reservas con un filtro
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Reserva> getReservasFiltro(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
+"				CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
+"						  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
+"						  [DBO].[16_RESERVA].*"
+"						  FROM  [DBO].[16_RESERVA] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
					+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
					+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
					+ "  [DBO].[16_RESERVA].*"
					+ "  FROM  [DBO].[16_RESERVA]";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Reserva> array = new ArrayList<Reserva>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Reserva cc = new Reserva();
			cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
			cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
			cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
			cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
			cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
			cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
			cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
			cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
			cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
			cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
			cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
			cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
			
			cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
			if(rs.getString("FECHA")!=null){
				cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
			}else{
				cc.setFecha(null);
			}
			cc.setCodigo(rs.getString("16_CODIGO"));
			cc.setValidado(rs.getBoolean("16_Validado"));
			cc.setId_Tipo_Sesion(rs.getInt("34_ID_TIPO_SESION"));
						
			//Nuevas columnas Anticipo
			cc.setFecha_Anticipo(rs.getString("16_Fecha_Anticipo"));
			cc.setHora_Anticipo(rs.getString("16_Hora_Anticipo"));
			cc.setNombre_Anticipo(rs.getString("16_Nombre_Anticipo"));
			cc.setTipo_Anticipo(rs.getString("16_Tipo_Anticipo"));
			cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));			
			cc.setVendedor(rs.getInt("38_Id_Vendedor"));
			cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
			cc.setObservacion(rs.getString("16_Observacion"));
			
			array.add(cc);
		}
		return array;
	}

	/**
	 *  @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de Reservas con nombres y sin números de referencia (Ejemplo, una ID)
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getReservasSinId(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		
		String SQL = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		String Excluyente ="";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			Excluyente = " AND ["+Columna+"] = ";
			if(Tipo.equals("String")){
				Excluyente = Excluyente+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				Excluyente = Excluyente + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
		}
		
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
				+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
				+ "  [DBO].[16_RESERVA].*,"
				+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
				+ "  [DBO].[15_CLIENTE].[15_RUT], "
				+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat],"
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat], "
				+ "  [DBO].[15_CLIENTE].[15_Mail], "
				
				+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], "
				+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS iniciovigencia,"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS finvigencia,"
				+ "  [DBO].[17_CAMPANIA].[17_Precio],"
				+ "  [DBO].[17_CAMPANIA].[17_Maximo_Personas],"
				+ "  [DBO].[17_CAMPANIA].[17_Posee_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_Fotos_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Adicional],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Reagendamiento],"
				+ "  [DBO].[17_CAMPANIA].[17_Abono],"
				
				+ "  [DBO].[34_TIPO_SESION].[34_ID_TIPO_SESION],"
				+ "  [DBO].[34_TIPO_SESION].[34_TIPO_SESION],"
				+ "  [dbo].[04_Trabajador].[04_Id_Trabajador],"
				+ "  [dbo].[04_Trabajador].[04_Nombre] "
				+ " FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_TIPO_SESION]"
				+" WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
				+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND"
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				+" [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] "+Excluyente
				+ "ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
		
		ResultSet rs = Consultar(SQL);
			
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_ID_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				
				cc.setObservacion(rs.getString("16_Observacion"));
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc); //0
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT")); //1
				min.add(rs.getString("15_NOMBRE")); //2
				min.add(rs.getString("15_APELLIDO_PAT")); //3
				min.add(rs.getString("15_APELLIDO_MAT")); //4
				min.add(rs.getObject("17_NOMBRE")); //5
				min.add(rs.getString("04_NOMBRE")); //6
				min.add(rs.getString("17_Cant_10x15")); //7
				min.add(rs.getString("17_Cant_15x21")); //8
				min.add(rs.getString("17_Cant_20x30")); //9
				min.add(rs.getString("17_Cant_30x40")); //10
				min.add(rs.getString("15_MAIL")); //11
				min.add(rs.getString("34_TIPO_SESION")); //12
				
				if(rs.getString("iniciovigencia")==null){
					min.add(null);									    //13
				}else{
					min.add(sdf.parse(rs.getString("iniciovigencia"))); //13
				}
				if(rs.getString("finvigencia")==null){
					min.add(null);                                       //14  
				}else{ 
					min.add(sdf.parse(rs.getString("finvigencia")));	 //14
				}
				
				min.add(rs.getInt("17_MAXIMO_PERSONAS"));		 //15
				min.add(rs.getInt("17_PRECIO"));				 //16
				min.add(rs.getBoolean("17_POSEE_CD"));			 //17
				min.add(rs.getInt("17_CANT_FOTOS_CD"));			 //18
				min.add(rs.getInt("17_Precio_Adicional"));		 //19
				min.add(rs.getInt("17_Precio_Reagendamiento"));	 //20
				min.add(rs.getInt("17_ABONO"));				 	 //21
				
				array.add(min);
			}
		}
		return array;
	}	
	
public ArrayList<ArrayList<Object>> getReservasSinId(String Excluyente) throws SQLException, java.text.ParseException{
		
		String SQL = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
				+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
				+ "  [DBO].[16_RESERVA].*,"
				+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
				+ "  [DBO].[15_CLIENTE].[15_RUT], "
				+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat],"
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat], "
				+ "  [DBO].[15_CLIENTE].[15_Mail], "
				
				+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], "
				+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS iniciovigencia,"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS finvigencia,"
				+ "  [DBO].[17_CAMPANIA].[17_Precio],"
				+ "  [DBO].[17_CAMPANIA].[17_Maximo_Personas],"
				+ "  [DBO].[17_CAMPANIA].[17_Posee_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_Fotos_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Adicional],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Reagendamiento],"
				+ "  [DBO].[17_CAMPANIA].[17_Abono],"
				
				+ "  [DBO].[34_TIPO_SESION].[34_ID_TIPO_SESION],"
				+ "  [DBO].[34_TIPO_SESION].[34_TIPO_SESION],"
				+ "  [dbo].[04_Trabajador].[04_Id_Trabajador],"
				+ "  [dbo].[04_Trabajador].[04_Nombre] "
				+ " FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_TIPO_SESION]"
				+" WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
				+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND"
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				+" [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] "+Excluyente
				+ "ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
		
		ResultSet rs = Consultar(SQL);
			
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_ID_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				
				cc.setObservacion(rs.getString("16_Observacion"));
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc); //0
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT")); //1
				min.add(rs.getString("15_NOMBRE")); //2
				min.add(rs.getString("15_APELLIDO_PAT")); //3
				min.add(rs.getString("15_APELLIDO_MAT")); //4
				min.add(rs.getObject("17_NOMBRE")); //5
				min.add(rs.getString("04_NOMBRE")); //6
				min.add(rs.getString("17_Cant_10x15")); //7
				min.add(rs.getString("17_Cant_15x21")); //8
				min.add(rs.getString("17_Cant_20x30")); //9
				min.add(rs.getString("17_Cant_30x40")); //10
				min.add(rs.getString("15_MAIL")); //11
				min.add(rs.getString("34_TIPO_SESION")); //12
				
				if(rs.getString("iniciovigencia")==null){
					min.add(null);									    //13
				}else{
					min.add(sdf.parse(rs.getString("iniciovigencia"))); //13
				}
				if(rs.getString("finvigencia")==null){
					min.add(null);                                       //14  
				}else{ 
					min.add(sdf.parse(rs.getString("finvigencia")));	 //14
				}
				
				min.add(rs.getInt("17_MAXIMO_PERSONAS"));		 //15
				min.add(rs.getInt("17_PRECIO"));				 //16
				min.add(rs.getBoolean("17_POSEE_CD"));			 //17
				min.add(rs.getInt("17_CANT_FOTOS_CD"));			 //18
				min.add(rs.getInt("17_Precio_Adicional"));		 //19
				min.add(rs.getInt("17_Precio_Reagendamiento"));	 //20
				min.add(rs.getInt("17_ABONO"));				 	 //21
				
				array.add(min);
			}
		}
		return array;
	}	
	
	/**
	 *  @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de Reservas con nombres y sin números de referencia (Ejemplo, una ID)
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getReservasSinIdPendientes(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		
		String SQL = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		String Excluyente ="";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			Excluyente = " AND ["+Columna+"] = ";
			if(Tipo.equals("String")){
				Excluyente = Excluyente+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				Excluyente = Excluyente + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
		}
		
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
				+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
				+ "  [DBO].[16_RESERVA].*,"
				+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
				+ "  [DBO].[15_CLIENTE].[15_RUT], "
				+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat],"
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat], "
				+ "  [DBO].[15_CLIENTE].[15_Mail], "
				+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], "
				+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS iniciovigencia,"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS finvigencia,"
				+ "  [DBO].[17_CAMPANIA].[17_Precio],"
				+ "  [DBO].[17_CAMPANIA].[17_Maximo_Personas],"
				+ "  [DBO].[17_CAMPANIA].[17_Posee_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_Fotos_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Adicional],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Reagendamiento],"
				+ "  [DBO].[17_CAMPANIA].[17_Abono],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "  [DBO].[34_TIPO_SESION].[34_ID_TIPO_SESION],"
				+ "  [DBO].[34_TIPO_SESION].[34_TIPO_SESION],"
				+ "  [dbo].[04_Trabajador].[04_Id_Trabajador],"
				+ "  [dbo].[04_Trabajador].[04_Nombre] "
				+ " FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_TIPO_SESION]"
				+" WHERE [16_Fecha] IS NULL AND" 
				+ "[dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
				+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND"
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				+" [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] "+Excluyente
				+ "ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
		
		ResultSet rs = Consultar(SQL);
				
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_ID_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				cc.setObservacion(rs.getString("16_Observacion"));
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc);
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT"));
				min.add(rs.getString("15_NOMBRE"));
				min.add(rs.getString("15_APELLIDO_PAT"));
				min.add(rs.getString("15_APELLIDO_MAT"));
				min.add(rs.getObject("17_NOMBRE"));
				min.add(rs.getString("04_NOMBRE"));
				min.add(rs.getString("17_Cant_10x15")); //7
				min.add(rs.getString("17_Cant_15x21")); //8
				min.add(rs.getString("17_Cant_20x30")); //9
				min.add(rs.getString("17_Cant_30x40")); //10
				min.add(rs.getString("15_MAIL"));
				min.add(rs.getString("34_TIPO_SESION"));
				
				if(rs.getString("iniciovigencia")==null){
					min.add(null);									    //13
				}else{
					min.add(sdf.parse(rs.getString("iniciovigencia"))); //13
				}
				if(rs.getString("finvigencia")==null){
					min.add(null);                                       //14  
				}else{ 
					min.add(sdf.parse(rs.getString("finvigencia")));	 //14
				}
				
				min.add(rs.getInt("17_MAXIMO_PERSONAS"));		 //15
				min.add(rs.getInt("17_PRECIO"));				 //16
				min.add(rs.getBoolean("17_POSEE_CD"));			 //17
				min.add(rs.getInt("17_CANT_FOTOS_CD"));			 //18
				min.add(rs.getInt("17_Precio_Adicional"));		 //19
				min.add(rs.getInt("17_Precio_Reagendamiento"));	 //20
				min.add(rs.getInt("17_ABONO"));				 	 //21
				
				
				array.add(min);
			}
		}
		return array;
	}	
	
	/**
	 *  @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de Reservas con nombres y sin números de referencia con un like (Ejemplo, una ID)
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getReservasSinIdLike(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		
		String SQL = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Excluyente ="";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			Excluyente = " AND ["+Columna+"] LIKE ";
			if(Tipo.equals("String")){
				Excluyente = Excluyente+ "'%"+Valor+"%'";
			}if(Tipo.equals("Int")){
				Excluyente = Excluyente + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
		}
		
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
				+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
				+ "  [DBO].[16_RESERVA].*,"
				+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
				+ "  [DBO].[15_CLIENTE].[15_RUT], "
				+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat],"
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat], "
				+ "  [DBO].[15_CLIENTE].[15_Mail], "
				+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], "
				+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS iniciovigencia,"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS finvigencia,"
				+ "  [DBO].[17_CAMPANIA].[17_Precio],"
				+ "  [DBO].[17_CAMPANIA].[17_Maximo_Personas],"
				+ "  [DBO].[17_CAMPANIA].[17_Posee_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_Fotos_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Adicional],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Reagendamiento],"
				+ "  [DBO].[17_CAMPANIA].[17_Abono],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "  [DBO].[34_TIPO_SESION].[34_ID_TIPO_SESION],"
				+ "  [DBO].[34_TIPO_SESION].[34_TIPO_SESION],"
				+ "  [dbo].[04_Trabajador].[04_Id_Trabajador],"
				+ "  [dbo].[04_Trabajador].[04_Nombre] "
				+ " FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_TIPO_SESION]"
				+" WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
				+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				+" [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] "+Excluyente
				+ " ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
				
		ResultSet rs = Consultar(SQL);
		
		System.out.println(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));				
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_Id_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				cc.setObservacion(rs.getString("16_Observacion"));
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc);
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT"));
				min.add(rs.getString("15_NOMBRE"));
				min.add(rs.getString("15_APELLIDO_PAT"));
				min.add(rs.getString("15_APELLIDO_MAT"));
				min.add(rs.getObject("17_NOMBRE"));
				min.add(rs.getString("04_NOMBRE"));
				min.add(rs.getString("17_Cant_10x15")); //7
				min.add(rs.getString("17_Cant_15x21")); //8
				min.add(rs.getString("17_Cant_20x30")); //9
				min.add(rs.getString("17_Cant_30x40")); //10
				min.add(rs.getString("15_MAIL"));
				min.add(rs.getString("34_TIPO_SESION"));
				
				if(rs.getString("iniciovigencia")==null){
					min.add(null);									    //13
				}else{
					min.add(sdf.parse(rs.getString("iniciovigencia"))); //13
				}
				if(rs.getString("finvigencia")==null){
					min.add(null);                                       //14  
				}else{ 
					min.add(sdf.parse(rs.getString("finvigencia")));	 //14
				}
				
				min.add(rs.getInt("17_MAXIMO_PERSONAS"));		 //15
				min.add(rs.getInt("17_PRECIO"));				 //16
				min.add(rs.getBoolean("17_POSEE_CD"));			 //17
				min.add(rs.getInt("17_CANT_FOTOS_CD"));			 //18
				min.add(rs.getInt("17_Precio_Adicional"));		 //19
				min.add(rs.getInt("17_Precio_Reagendamiento"));	 //20
				min.add(rs.getInt("17_ABONO"));				 	 //21
				
				array.add(min);
			}
		}
		return array;
	}	
	
	/*  @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de Reservas con nombres y sin números de referencia con un like (Ejemplo, una ID)
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getNoPreReservasSinIdLike(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		
		String SQL = "";
		
		String Excluyente ="";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			Excluyente = " AND ["+Columna+"] LIKE ";
			if(Tipo.equals("String")){
				Excluyente = Excluyente+ "'%"+Valor+"%'";
			}if(Tipo.equals("Int")){
				Excluyente = Excluyente + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
		}
		
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
				+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
				+ "  [DBO].[16_RESERVA].*,"
				+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
				+ "  [DBO].[15_CLIENTE].[15_RUT], "
				+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat],"
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat], "
				+ "  [DBO].[15_CLIENTE].[15_Mail], "
				+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], "
				+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "  [DBO].[34_TIPO_SESION].[34_ID_TIPO_SESION],"
				+ "  [DBO].[34_TIPO_SESION].[34_TIPO_SESION],"
				+ "  [dbo].[04_Trabajador].[04_Id_Trabajador],"
				+ "  [dbo].[04_Trabajador].[04_Nombre] "
				+ " FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_TIPO_SESION]"
				+" WHERE [dbo].[16_Reserva].[16_Pre_Reserva] = 0 AND [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
				+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				+" [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] "+Excluyente
				+ "ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
				
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));				
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_Id_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc);
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT"));
				min.add(rs.getString("15_NOMBRE"));
				min.add(rs.getString("15_APELLIDO_PAT"));
				min.add(rs.getString("15_APELLIDO_MAT"));
				min.add(rs.getObject("17_NOMBRE"));
				min.add(rs.getString("04_NOMBRE"));
				min.add(rs.getString("17_Cant_10x15"));
				min.add(rs.getString("17_Cant_15x21"));
				min.add(rs.getString("17_Cant_20x30"));
				min.add(rs.getString("17_Cant_30x40"));
				min.add(rs.getString("15_MAIL"));
				min.add(rs.getString("34_Tipo_Sesion"));
				
				array.add(min);
			}
		}
		return array;
	}	
	
	public ArrayList<ArrayList<Object>> getNoPreReservasSinIdLike(String Excepciones) throws SQLException, java.text.ParseException{
		

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String SQL = "";
				
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
				+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
				+ "  [DBO].[16_RESERVA].*,"
				+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
				+ "  [DBO].[15_CLIENTE].[15_RUT], "
				+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat],"
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat], "
				+ "  [DBO].[15_CLIENTE].[15_Mail], "
				
				+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], "
				+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS iniciovigencia,"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS finvigencia,"
				+ "  [DBO].[17_CAMPANIA].[17_Precio],"
				+ "  [DBO].[17_CAMPANIA].[17_Maximo_Personas],"
				+ "  [DBO].[17_CAMPANIA].[17_Posee_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_Fotos_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Adicional],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Reagendamiento],"
				+ "  [DBO].[17_CAMPANIA].[17_Abono],"
				
				+ "  [DBO].[34_TIPO_SESION].[34_ID_TIPO_SESION],"
				+ "  [DBO].[34_TIPO_SESION].[34_TIPO_SESION],"
				+ "  [dbo].[04_Trabajador].[04_Id_Trabajador],"
				+ "  [dbo].[04_Trabajador].[04_Nombre] "
				+ " FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_TIPO_SESION]"
				+" WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
				+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND"
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				+" [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]"
				+ Excepciones
				+ "ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
				
		ResultSet rs = Consultar(SQL);
		
		System.out.println(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));				
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_Id_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc);
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT"));
				min.add(rs.getString("15_NOMBRE"));
				min.add(rs.getString("15_APELLIDO_PAT"));
				min.add(rs.getString("15_APELLIDO_MAT"));
				min.add(rs.getObject("17_NOMBRE"));
				min.add(rs.getString("04_NOMBRE"));
				min.add(rs.getString("17_Cant_10x15"));
				min.add(rs.getString("17_Cant_15x21"));
				min.add(rs.getString("17_Cant_20x30"));
				min.add(rs.getString("17_Cant_30x40"));
				min.add(rs.getString("15_MAIL"));
				min.add(rs.getString("34_Tipo_Sesion"));

				if(rs.getString("iniciovigencia")==null){
					min.add(null);									    //13
				}else{
					min.add(sdf.parse(rs.getString("iniciovigencia"))); //13
				}
				if(rs.getString("finvigencia")==null){
					min.add(null);                                       //14  
				}else{ 
					min.add(sdf.parse(rs.getString("finvigencia")));	 //14
				}
				
				min.add(rs.getInt("17_MAXIMO_PERSONAS"));		 //15
				min.add(rs.getInt("17_PRECIO"));				 //16
				min.add(rs.getBoolean("17_POSEE_CD"));			 //17
				min.add(rs.getInt("17_CANT_FOTOS_CD"));			 //18
				min.add(rs.getInt("17_Precio_Adicional"));		 //19
				min.add(rs.getInt("17_Precio_Reagendamiento"));	 //20
				min.add(rs.getInt("17_ABONO"));				 	 //21
				
				array.add(min);
			}
		}
		return array;
	}	
	
	/**
	 *  @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de Reservas con nombres y sin números de referencia con un like (Ejemplo, una ID)
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getReservasSinIdLikeAnticipo(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		
		String SQL = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Excluyente ="";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			Excluyente = " AND ["+Columna+"] LIKE ";
			if(Tipo.equals("String")){
				Excluyente = Excluyente+ "'%"+Valor+"%'";
			}if(Tipo.equals("Int")){
				Excluyente = Excluyente + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
		}
		
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
				+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
				+ "  [DBO].[16_RESERVA].*,"
				+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
				+ "  [DBO].[15_CLIENTE].[15_RUT], "
				+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat],"
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat], "
				+ "  [DBO].[15_CLIENTE].[15_Mail], "
				+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], "
				+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS iniciovigencia,"
				+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS finvigencia,"
				+ "  [DBO].[17_CAMPANIA].[17_Precio],"
				+ "  [DBO].[17_CAMPANIA].[17_Maximo_Personas],"
				+ "  [DBO].[17_CAMPANIA].[17_Posee_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_Fotos_CD],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Adicional],"
				+ "  [DBO].[17_CAMPANIA].[17_Precio_Reagendamiento],"
				+ "  [DBO].[17_CAMPANIA].[17_Abono],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
				+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
				+ "	 [DBO].[17_CAMPANIA].[14_Id_Canal_Venta],"
				+ "	 [DBO].[14_CANAL_VENTA].[14_ID_CANAL_VENTA] AS IDCANALVENTA,"
				+ "	 [DBO].[14_CANAL_VENTA].[14_REQUIERE_CUPON],"
				+ "  [dbo].[04_Trabajador].[04_Id_Trabajador],"
				+ "  [dbo].[04_Trabajador].[04_Nombre] "
				+ " FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[14_CANAL_VENTA]"
				+" WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
				+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				+" [dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_CANAL_VENTA].[14_ID_CANAL_VENTA] AND "
				+" [dbo].[14_CANAL_VENTA].[14_REQUIERE_CUPON] = 0 "+Excluyente
				+ " ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
				
		System.out.println(SQL);
		
		ResultSet rs = Consultar(SQL);
			
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
				
				//Nuevas columnas Anticipo
				cc.setFecha_Anticipo(rs.getString("16_Fecha_Anticipo"));
				cc.setHora_Anticipo(rs.getString("16_Hora_Anticipo"));
				cc.setNombre_Anticipo(rs.getString("16_Nombre_Anticipo"));
				cc.setTipo_Anticipo(rs.getString("16_Tipo_Anticipo"));
				cc.setObservacion(rs.getString("16_Observacion"));
				
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_ID_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc);
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT"));
				min.add(rs.getString("15_NOMBRE"));
				min.add(rs.getString("15_APELLIDO_PAT"));
				min.add(rs.getString("15_APELLIDO_MAT"));
				min.add(rs.getObject("17_NOMBRE"));
				min.add(rs.getString("04_NOMBRE"));
				min.add(rs.getString("17_Cant_10x15")); //7
				min.add(rs.getString("17_Cant_15x21")); //8
				min.add(rs.getString("17_Cant_20x30")); //9
				min.add(rs.getString("17_Cant_30x40")); //10
				min.add(rs.getString("15_MAIL"));
				min.add("");
				
				if(rs.getString("iniciovigencia")==null){
					min.add(null);									    //13
				}else{
					min.add(sdf.parse(rs.getString("iniciovigencia"))); //13
				}
				if(rs.getString("finvigencia")==null){
					min.add(null);                                       //14  
				}else{ 
					min.add(sdf.parse(rs.getString("finvigencia")));	 //14
				}
				
				min.add(rs.getInt("17_MAXIMO_PERSONAS"));		 //15
				min.add(rs.getInt("17_PRECIO"));				 //16
				min.add(rs.getBoolean("17_POSEE_CD"));			 //17
				min.add(rs.getInt("17_CANT_FOTOS_CD"));			 //18
				min.add(rs.getInt("17_Precio_Adicional"));		 //19
				min.add(rs.getInt("17_Precio_Reagendamiento"));	 //20
				min.add(rs.getInt("17_ABONO"));				 	 //21
				
				array.add(min);
			}
		}
		return array;
	}	
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de Reservas con nombres y sin números de referencia (Ejemplo, una ID) para asignar anticipos
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getReservasSinIdAnticipo(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
				+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
				+ "	RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
				+ "	 [DBO].[16_RESERVA].*, [DBO].[15_CLIENTE].[15_ID_CLIENTE], [DBO].[15_CLIENTE].[15_RUT], "
				+ "	 [DBO].[15_CLIENTE].[15_NOMBRE], [DBO].[15_CLIENTE].[15_Apellido_Pat], "
				+ "	 [DBO].[15_CLIENTE].[15_Apellido_Mat] , [DBO].[15_CLIENTE].[15_Mail] , [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], "
				+ "	 [DBO].[17_CAMPANIA].[17_NOMBRE],[dbo].[04_Trabajador].[04_Id_Trabajador],[dbo].[04_Trabajador].[04_Nombre],"
				+ "	 [dbo].[14_Canal_Venta].[14_Id_Canal_Venta], [dbo].[14_Canal_Venta].[14_Requiere_Cupon]"
					+ "	 FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR] , [dbo].[14_Canal_Venta]"
						+ "	 WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
						+ "	[dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
						+ "	[dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND"
						+ "	[dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_Canal_Venta].[14_Id_Canal_Venta] AND "
						+ "	[dbo].[14_Canal_Venta].[14_Requiere_Cupon] = 0 "
						+ "	ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_ID_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				
				
				//Nuevas columnas Anticipo
				cc.setFecha_Anticipo(rs.getString("16_Fecha_Anticipo"));
				cc.setHora_Anticipo(rs.getString("16_Hora_Anticipo"));
				cc.setNombre_Anticipo(rs.getString("16_Nombre_Anticipo"));
				cc.setTipo_Anticipo(rs.getString("16_Tipo_Anticipo"));
				
				cc.setObservacion(rs.getString("16_Observacion"));
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc);
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT"));
				min.add(rs.getString("15_NOMBRE"));
				min.add(rs.getString("15_APELLIDO_PAT"));
				min.add(rs.getString("15_APELLIDO_MAT"));
				min.add(rs.getObject("17_NOMBRE"));
				min.add(rs.getString("04_NOMBRE"));
				min.add(rs.getString("15_MAIL"));
				
				array.add(min);
			}
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getReservasSinIdConFechas(String Columna, String Valor, String Tipo, String Fecha1, String Fecha2) throws SQLException, java.text.ParseException{
		String SQL = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Fecha12 = null;
		
		if(!(Fecha1.equals(""))){
			java.util.Date DiaSiguiente = (java.util.Date) sdf.parse(Fecha1);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DiaSiguiente); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
			
			Fecha12 = sdf.format(calendar.getTime());	
		}
				
		if((!(Fecha1.equals("")))||(!(Fecha2.equals("")))){
			 if((!(Fecha1.equals("")))&&(Fecha2.equals(""))){
				 SQL = "SELECT * FROM (SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + 	"
				 		+ "		CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +   "
				 		+ "		RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,  "
				 		+ "		[DBO].[16_RESERVA].*, "
				 		+ "		[DBO].[15_CLIENTE].[15_ID_CLIENTE] AS IDCLIENTE, "
				 		+ "     [DBO].[15_CLIENTE].[15_RUT],"
				 		+ "     [DBO].[15_CLIENTE].[15_NOMBRE],"
				 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Pat],"
				 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Mat] ,"
				 		+ "     [DBO].[15_CLIENTE].[15_Mail],"
				 		
						+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA] AS IDCAMPANIA, "
						+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
						+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS iniciovigencia,"
						+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS finvigencia,"
						+ "  [DBO].[17_CAMPANIA].[17_Precio],"
						+ "  [DBO].[17_CAMPANIA].[17_Maximo_Personas],"
						+ "  [DBO].[17_CAMPANIA].[17_Posee_CD],"
						+ "  [DBO].[17_CAMPANIA].[17_Cant_Fotos_CD],"
						+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
						+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
						+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
						+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
						+ "  [DBO].[17_CAMPANIA].[17_Precio_Adicional],"
						+ "  [DBO].[17_CAMPANIA].[17_Precio_Reagendamiento],"
						+ "  [DBO].[17_CAMPANIA].[17_Abono],"
				 		
				 		+ "		[dbo].[04_Trabajador].[04_Id_Trabajador] AS IDTRABAJADOR, "
				 		+ "     [dbo].[04_Trabajador].[04_Nombre],"
				 		+ "		CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +   		RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS HORA,"
				 		+ "		 [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] AS IDTIPOSESION, [dbo].[34_Tipo_Sesion].[34_Tipo_Sesion]"
				 		+ "		FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_Tipo_Sesion]  "
				 		+ "		WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND  "
				 		+ "		[dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND  "
				 		+ "		[dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				 		+ "  	[dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]) AS TABLA1"
				 		+ "		WHERE FECHA BETWEEN '"+Fecha1+"' AND '"+Fecha12+"'"
				 		+ "     ORDER BY [16_FECHA];";
			 }
			 if((!(Fecha2.equals("")))&&(!(Fecha1.equals("")))){
				 SQL = "SELECT * FROM (SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + 	"
					 		+ "		CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +   "
					 		+ "		RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,  "
					 		+ "		[DBO].[16_RESERVA].*, "
					 		+ "		[DBO].[15_CLIENTE].[15_ID_CLIENTE] AS IDCLIENTE, "
					 		+ "     [DBO].[15_CLIENTE].[15_RUT], "
					 		+ "     [DBO].[15_CLIENTE].[15_NOMBRE], "
					 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Pat], "
					 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Mat] ,"
					 		+ "     [DBO].[15_CLIENTE].[15_Mail],"
					 		+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA] AS IDCAMPANIA, "
							+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
							+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS iniciovigencia,"
							+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS finvigencia,"
							+ "  [DBO].[17_CAMPANIA].[17_Precio],"
							+ "  [DBO].[17_CAMPANIA].[17_Maximo_Personas],"
							+ "  [DBO].[17_CAMPANIA].[17_Posee_CD],"
							+ "  [DBO].[17_CAMPANIA].[17_Cant_Fotos_CD],"
							+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
							+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
							+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
							+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
							+ "  [DBO].[17_CAMPANIA].[17_Precio_Adicional],"
							+ "  [DBO].[17_CAMPANIA].[17_Precio_Reagendamiento],"
							+ "  [DBO].[17_CAMPANIA].[17_Abono],"
					 		+ "		[dbo].[04_Trabajador].[04_Id_Trabajador] AS IDTRABAJADOR, [dbo].[04_Trabajador].[04_Nombre], "
					 		+ "     [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] AS IDTIPOSESION, [dbo].[34_Tipo_Sesion].[34_Tipo_Sesion]"
					 		+ "		FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_Tipo_Sesion] "
					 		+ "		WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND  "
					 		+ "		[dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND  "
					 		+ "		[dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND"
					 		+ "		[dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]) AS TABLA1"
					 		+ "		WHERE FECHA BETWEEN '"+Fecha1+"' AND '"+Fecha2+"'"
					 		+ "     ORDER BY [16_FECHA];";
			 }
	}else{
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
			+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
			+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
			+ "  [DBO].[16_RESERVA].*, "
			+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
			+ "  [DBO].[15_CLIENTE].[15_RUT],"
			+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
			+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat], "
			+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat] ,"
			+ "  [DBO].[15_CLIENTE].[15_Mail],"
			+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA] AS IDCAMPANIA, "
			+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
			+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Inicio_Vigencia], 111)AS iniciovigencia,"
			+ "	 CONVERT(VARCHAR,  [17_Campania].[17_Fin_Vigencia], 111)AS finvigencia,"
			+ "  [DBO].[17_CAMPANIA].[17_Precio],"
			+ "  [DBO].[17_CAMPANIA].[17_Maximo_Personas],"
			+ "  [DBO].[17_CAMPANIA].[17_Posee_CD],"
			+ "  [DBO].[17_CAMPANIA].[17_Cant_Fotos_CD],"
			+ "  [DBO].[17_CAMPANIA].[17_Cant_10x15],"
			+ "  [DBO].[17_CAMPANIA].[17_Cant_15x21],"
			+ "  [DBO].[17_CAMPANIA].[17_Cant_20x30],"
			+ "  [DBO].[17_CAMPANIA].[17_Cant_30x40],"
			+ "  [DBO].[17_CAMPANIA].[17_Precio_Adicional],"
			+ "  [DBO].[17_CAMPANIA].[17_Precio_Reagendamiento],"
			+ "  [DBO].[17_CAMPANIA].[17_Abono],"
			+ " [dbo].[04_Trabajador].[04_Id_Trabajador],[dbo].[04_Trabajador].[04_Nombre], "
			+ " [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion], [dbo].[34_Tipo_Sesion].[34_Tipo_Sesion]"
			+ "FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_Tipo_Sesion] "
			+" WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
			+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
			+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND"
			+" [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]"
			+ "ORDER BY [16_FECHA] DESC;";
	}
		
		System.out.println(SQL);
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_ID_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				cc.setObservacion(rs.getString("16_Observacion"));
				
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc);
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT"));
				min.add(rs.getString("15_NOMBRE"));
				min.add(rs.getString("15_APELLIDO_PAT"));
				min.add(rs.getString("15_APELLIDO_MAT"));
				min.add(rs.getObject("17_NOMBRE"));
				min.add(rs.getString("04_NOMBRE"));
				min.add(rs.getString("17_Cant_10x15")); //7
				min.add(rs.getString("17_Cant_15x21")); //8
				min.add(rs.getString("17_Cant_20x30")); //9
				min.add(rs.getString("17_Cant_30x40")); //10
				min.add(rs.getString("15_MAIL"));
				min.add(rs.getString("34_TIPO_SESION"));
				
				if(rs.getString("iniciovigencia")==null){
					min.add(null);									    //13
				}else{
					min.add(sdf.parse(rs.getString("iniciovigencia"))); //13
				}
				if(rs.getString("finvigencia")==null){
					min.add(null);                                       //14  
				}else{ 
					min.add(sdf.parse(rs.getString("finvigencia")));	 //14
				}
				
				min.add(rs.getInt("17_MAXIMO_PERSONAS"));		 //15
				min.add(rs.getInt("17_PRECIO"));				 //16
				min.add(rs.getBoolean("17_POSEE_CD"));			 //17
				min.add(rs.getInt("17_CANT_FOTOS_CD"));			 //18
				min.add(rs.getInt("17_Precio_Adicional"));		 //19
				min.add(rs.getInt("17_Precio_Reagendamiento"));	 //20
				min.add(rs.getInt("17_ABONO"));				 	 //21
				
				array.add(min);
			}
		}
		return array;
	}	
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getNoPreReservasSinIdConFechas(String Columna, String Valor, String Tipo, String Fecha1, String Fecha2) throws SQLException, java.text.ParseException{
		String SQL = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Fecha12 = null;
		
		if(!(Fecha1.equals(""))){
			java.util.Date DiaSiguiente = (java.util.Date) sdf.parse(Fecha1);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DiaSiguiente); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
			
			Fecha12 = sdf.format(calendar.getTime());	
		}
				
		if((!(Fecha1.equals("")))||(!(Fecha2.equals("")))){
			 if((!(Fecha1.equals("")))&&(Fecha2.equals(""))){
				 SQL = "SELECT * FROM (SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + 	"
				 		+ "		CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +   "
				 		+ "		RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,  "
				 		+ "		[DBO].[16_RESERVA].*, "
				 		+ "		[DBO].[15_CLIENTE].[15_ID_CLIENTE] AS IDCLIENTE, "
				 		+ "     [DBO].[15_CLIENTE].[15_RUT],"
				 		+ "     [DBO].[15_CLIENTE].[15_NOMBRE],"
				 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Pat],"
				 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Mat] ,"
				 		+ "     [DBO].[15_CLIENTE].[15_Mail],"
				 		+ "		[DBO].[17_CAMPANIA].[17_ID_CAMPANIA] AS IDCAMPANIA, "
				 		+ "     [DBO].[17_CAMPANIA].[17_NOMBRE],"
				 		+ "		[dbo].[04_Trabajador].[04_Id_Trabajador] AS IDTRABAJADOR, "
				 		+ "     [dbo].[04_Trabajador].[04_Nombre],"
				 		+ "		CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +   		RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS HORA,"
				 		+ "		 [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] AS IDTIPOSESION, [dbo].[34_Tipo_Sesion].[34_Tipo_Sesion]"
				 		+ "		FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_Tipo_Sesion]  "
				 		+ "		WHERE [dbo].[16_Reserva].[16_Pre_Reserva] = 0 AND [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND  "
				 		+ "		[dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND  "
				 		+ "		[dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				 		+ "  	[dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]) AS TABLA1"
				 		+ "		WHERE [16_Pre_Reserva] = 0 AND  FECHA BETWEEN '"+Fecha1+"' AND '"+Fecha12+"'"
				 		+ "     ORDER BY HORA;";
			 }
			 if((!(Fecha2.equals("")))&&(!(Fecha1.equals("")))){
				 SQL = "SELECT * FROM (SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + 	"
					 		+ "		CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +   "
					 		+ "		RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,  "
					 		+ "		[DBO].[16_RESERVA].*, "
					 		+ "		[DBO].[15_CLIENTE].[15_ID_CLIENTE] AS IDCLIENTE, "
					 		+ "     [DBO].[15_CLIENTE].[15_RUT], "
					 		+ "     [DBO].[15_CLIENTE].[15_NOMBRE], "
					 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Pat], "
					 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Mat] ,"
					 		+ "     [DBO].[15_CLIENTE].[15_Mail],"
					 		+ "		[DBO].[17_CAMPANIA].[17_ID_CAMPANIA] AS IDCAMPANIA, [DBO].[17_CAMPANIA].[17_NOMBRE],"
					 		+ "		[dbo].[04_Trabajador].[04_Id_Trabajador] AS IDTRABAJADOR, [dbo].[04_Trabajador].[04_Nombre], "
					 		+ "     [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] AS IDTIPOSESION, [dbo].[34_Tipo_Sesion].[34_Tipo_Sesion]"
					 		+ "		FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_Tipo_Sesion] "
					 		+ "		WHERE  [dbo].[16_Reserva].[16_Pre_Reserva] = 0 AND [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND  "
					 		+ "		[dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND  "
					 		+ "		[dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND"
					 		+ "		[dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]) AS TABLA1"
					 		+ "		WHERE [16_Pre_Reserva] = 0 AND FECHA BETWEEN '"+Fecha1+"' AND '"+Fecha2+"'"
					 		+ "     ORDER BY FECHA;";
			 }
	}else{
		SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
			+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
			+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
			+ "  [DBO].[16_RESERVA].*, "
			+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
			+ "  [DBO].[15_CLIENTE].[15_RUT],"
			+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
			+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat], "
			+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat] ,"
			+ "  [DBO].[15_CLIENTE].[15_Mail],"
			+ " [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], [DBO].[17_CAMPANIA].[17_NOMBRE],[dbo].[04_Trabajador].[04_Id_Trabajador],[dbo].[04_Trabajador].[04_Nombre], "
			+ " [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion], [dbo].[34_Tipo_Sesion].[34_Tipo_Sesion]"
			+ "FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[34_Tipo_Sesion] "
			+" WHERE [16_Pre_Reserva] = 0 AND [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
			+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
			+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND"
			+" [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]"
			+ "ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
	}
		
		
		ResultSet rs = Consultar(SQL);
		
	System.out.println("Actualizando");
	System.out.println(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_ID_TIPO_SESION"));
				cc.setPreReserva(rs.getBoolean("16_Pre_Reserva"));
				cc.setVendedor(rs.getInt("38_Id_Vendedor"));
				cc.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
				
				
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc);
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT"));
				min.add(rs.getString("15_NOMBRE"));
				min.add(rs.getString("15_APELLIDO_PAT"));
				min.add(rs.getString("15_APELLIDO_MAT"));
				min.add(rs.getObject("17_NOMBRE"));
				min.add(rs.getString("04_NOMBRE"));
				min.add("");
				min.add("");
				min.add("");
				min.add("");
				min.add(rs.getString("15_MAIL"));
				min.add(rs.getString("34_TIPO_SESION"));
				
				array.add(min);
			}
		}
		return array;
	}	
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * @throws java.text.ParseException 
	 */
	public ArrayList<ArrayList<Object>> getReservasSinIdConFechasAnticipo(String Columna, String Valor, String Tipo, String Fecha1, String Fecha2) throws SQLException, java.text.ParseException{
		String SQL = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Fecha12 = null;
		
		if(!(Fecha1.equals(""))){
			java.util.Date DiaSiguiente = (java.util.Date) sdf.parse(Fecha1);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DiaSiguiente); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
			
			Fecha12 = sdf.format(calendar.getTime());	
		}
				
		if((!(Fecha1.equals("")))||(!(Fecha2.equals("")))){
				 if((!(Fecha1.equals("")))&&(Fecha2.equals(""))){
					 SQL = "SELECT * FROM (SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + 	"
					 		+ "		CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +   "
					 		+ "		RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,  "
					 		+ "		[DBO].[16_RESERVA].*, "
					 		+ "		[DBO].[15_CLIENTE].[15_ID_CLIENTE] AS IDCLIENTE, "
					 		+ "     [DBO].[15_CLIENTE].[15_RUT],"
					 		+ "     [DBO].[15_CLIENTE].[15_NOMBRE],"
					 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Pat],"
					 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Mat] ,"
					 		+ "     [DBO].[15_CLIENTE].[15_Mail],"
					 		+ "		[DBO].[17_CAMPANIA].[17_ID_CAMPANIA] AS IDCAMPANIA, "
					 		+ "     [DBO].[17_CAMPANIA].[17_NOMBRE],"
					 		+ "		[DBO].[17_CAMPANIA].[14_Id_Canal_Venta],"
					 		+ "		[DBO].[14_CANAL_VENTA].[14_ID_CANAL_VENTA] AS IDCANALVENTA,"
					 		+ "		[DBO].[14_CANAL_VENTA].[14_REQUIERE_CUPON],"
					 		+ "		[dbo].[04_Trabajador].[04_Id_Trabajador] AS IDTRABAJADOR, "
					 		+ "     [dbo].[04_Trabajador].[04_Nombre],"
					 		+ "		CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +   		RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS HORA"
					 		+ "		FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[14_CANAL_VENTA] "
					 		+ "		WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND  "
					 		+ "		[dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND  "
					 		+ "		[dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
					 		+ "		[dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_CANAL_VENTA].[14_ID_CANAL_VENTA] AND "
					 		+ "     [dbo].[14_CANAL_VENTA].[14_REQUIERE_CUPON] = 0) AS TABLA1"
					 		+ "		WHERE FECHA BETWEEN '"+Fecha1+"' AND '"+Fecha12+"'"
					 		+ "     ORDER BY HORA;";
				 }
				 if((!(Fecha2.equals("")))&&(!(Fecha1.equals("")))){
					 SQL = "SELECT * FROM (SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + 	"
						 		+ "		CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +   "
						 		+ "		RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,  "
						 		+ "		[DBO].[16_RESERVA].*, "
						 		+ "		[DBO].[15_CLIENTE].[15_ID_CLIENTE] AS IDCLIENTE, "
						 		+ "     [DBO].[15_CLIENTE].[15_RUT], "
						 		+ "     [DBO].[15_CLIENTE].[15_NOMBRE], "
						 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Pat], "
						 		+ "     [DBO].[15_CLIENTE].[15_Apellido_Mat] ,"
						 		+ "     [DBO].[15_CLIENTE].[15_Mail],"
						 		+ "		[DBO].[17_CAMPANIA].[17_ID_CAMPANIA] AS IDCAMPANIA, "
						 		+ "		[DBO].[17_CAMPANIA].[17_NOMBRE],"
						 		+ "		[DBO].[17_CAMPANIA].[14_Id_Canal_Venta],"
						 		+ "		[DBO].[14_CANAL_VENTA].[14_ID_CANAL_VENTA] AS IDCANALVENTA,"
						 		+ "		[DBO].[14_CANAL_VENTA].[14_REQUIERE_CUPON],"
						 		+ "		[dbo].[04_Trabajador].[04_Id_Trabajador] AS IDTRABAJADOR, [dbo].[04_Trabajador].[04_Nombre] "
						 		+ "		FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[14_CANAL_VENTA] "
						 		+ "		WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND  "
						 		+ "		[dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND  "
						 		+ "		[dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
						 		+ "		[dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_CANAL_VENTA].[14_ID_CANAL_VENTA] AND "
						 		+ "     [dbo].[14_CANAL_VENTA].[14_REQUIERE_CUPON] = 0) AS TABLA1"
						 		+ "		WHERE FECHA BETWEEN '"+Fecha1+"' AND '"+Fecha2+"'"
						 		+ "     ORDER BY FECHA;";
				 }
		}else{
			SQL = "SELECT  CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) + ' ' + "
				+ "	CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' + "
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha,"
				+ "  [DBO].[16_RESERVA].*, "
				+ "  [DBO].[15_CLIENTE].[15_ID_CLIENTE], "
				+ "  [DBO].[15_CLIENTE].[15_RUT],"
				+ "  [DBO].[15_CLIENTE].[15_NOMBRE], "
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Pat], "
				+ "  [DBO].[15_CLIENTE].[15_Apellido_Mat] ,"
				+ "  [DBO].[15_CLIENTE].[15_Mail],"
				+ "  [DBO].[17_CAMPANIA].[17_ID_CAMPANIA], "
				+ "  [DBO].[17_CAMPANIA].[17_NOMBRE],"
				+ "	 [DBO].[17_CAMPANIA].[14_Id_Canal_Venta],"
		 		+ "	 [DBO].[14_CANAL_VENTA].[14_ID_CANAL_VENTA] AS IDCANALVENTA,"
		 		+ "	 [DBO].[14_CANAL_VENTA].[14_REQUIERE_CUPON],"
				+ "  [dbo].[04_Trabajador].[04_Id_Trabajador],"
				+ "  [dbo].[04_Trabajador].[04_Nombre] "
				+ "  FROM [DBO].[16_RESERVA], [DBO].[15_CLIENTE], [DBO].[17_CAMPANIA], [DBO].[04_TRABAJADOR], [DBO].[14_CANAL_VENTA] "
				+" WHERE [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente] AND "
				+" [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania] AND "
				+" [dbo].[16_Reserva].[04_Id_Trabajador] =  [dbo].[04_Trabajador].[04_Id_Trabajador] AND "
				+ "[dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_CANAL_VENTA].[14_ID_CANAL_VENTA] AND "
				+ "[dbo].[14_CANAL_VENTA].[14_REQUIERE_CUPON] = 0"
				+ "ORDER BY [DBO].[16_RESERVA].[16_FECHA] ASC;";
		}
				
		ResultSet rs = Consultar(SQL);
		
		System.out.println(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<Object> min = new ArrayList<Object>();
				Reserva cc = new Reserva ();
				cc.setId_Reserva(rs.getInt("16_ID_RESERVA"));
				cc.setId_Agenda(rs.getInt("03_ID_AGENDA"));
				cc.setId_Cliente(rs.getInt("15_ID_CLIENTE"));
				cc.setTipo_Cliente(rs.getInt("05_ID_TIPO_CLIENTE"));
				cc.setId_Campania(rs.getInt("17_ID_CAMPANIA"));
				cc.setId_Ticket(rs.getInt("24_ID_TICKET"));
				cc.setId_Trabajador(rs.getInt("04_ID_TRABAJADOR"));
				cc.setCantidad_Personas(rs.getInt("16_CANTIDAD_PERSONAS"));
				cc.setCantidad_Adicionales(rs.getInt("16_CANTIDAD_ADICIONALES"));
				cc.setCantidad_Reagendamiento(rs.getInt("16_CANTIDAD_REAGENDAMIENTO"));
				cc.setMonto_Pago_Estudio(rs.getInt("16_MONTO_PAGO_ESTUDIO"));
				cc.setMonto_Pago_Adelantado(rs.getInt("16_MONTO_PAGO_ADELANTADO"));
				cc.setId_Estado(rs.getInt("11_ID_ESTADO"));
				
				if(rs.getString("FECHA")!=null){
					cc.setFecha(this.ConvertirFecha(rs.getString("FECHA")));
				}else{
					
					cc.setFecha(null);
				}
				cc.setCodigo(rs.getString("16_CODIGO"));
				cc.setValidado(rs.getBoolean("16_Validado"));
				cc.setId_Tipo_Sesion(rs.getInt("34_ID_TIPO_SESION"));
				cc.setObservacion(rs.getString("16_Observacion"));
				
				//ATRIBUTOS DE TABLA RESERVA
				min.add(cc);
				//ATRIBUTOS DE TABLA CLIENTE, CAMPAÑA Y TRABAJADOR
				min.add(rs.getInt("15_RUT"));
				min.add(rs.getString("15_NOMBRE"));
				min.add(rs.getString("15_APELLIDO_PAT"));
				min.add(rs.getString("15_APELLIDO_MAT"));
				min.add(rs.getObject("17_NOMBRE"));
				min.add(rs.getString("04_NOMBRE"));
				min.add(rs.getString("15_MAIL"));
				min.add("");
				min.add("");
				min.add("");
				min.add("");
				
				
				array.add(min);
			}
		}
		return array;
	}	
	
	/**
	 * @author Advancing
	 * @param String Fecha que entrega una fecha en una cadena de caracteres que se transformará al tipo Date con formato yyyy/MM/dd HH:mm
	 * Conversión de fechas, de String a fecha(Date)
	 */
	public java.util.Date ConvertirFecha(String fecha) throws java.text.ParseException{
		java.util.Date date = null;
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    	try
        {
            date = simpleDateFormat.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
    	return date;
	}
	
	/**
	 * @author Advancing
	 * @param String Fecha que entrega una fecha en una cadena de caracteres que se transformará al tipo Date con formato yyyy/MM/dd
	 * Conversión de fecha, solo el dia(Date)
	 */
	public java.util.Date ConvertirDia(String dia) throws java.text.ParseException{
		java.util.Date date = null;
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	try
        {
            date = simpleDateFormat.parse(dia);
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
    	return date;
	}
	
	/**
	 * @author Advancing
	 * @param Date Fecha que entrega una fecha en tipo Date y retorna una cadena de caracteres en formato yyyy/MM/dd HH:mm
	 * Conversión de fechas, de String a fecha
	 */
	public String ConvertirFecha(java.util.Date fecha) throws java.text.ParseException{
		String date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
    	try
        {
            date = sdf.format(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
    	return date;
	}

	/**
	 * @author Advancing
	 * Obtención de Fechas ya reservadas para la obteción de una nueva reserva
	 */
	public ArrayList<ArrayList<String>> getAgenda() throws SQLException{
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		String SQL = "SELECT DISTINCT CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) AS DIA"
				+ "  FROM  [DBO].[16_RESERVA]";
		
		ResultSet rs = Consultar(SQL);
		
		int cont = 0;
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				if(rs.getString("DIA")==null){
					System.out.println("Una vez en null");
					ArrayList<String> arr = new ArrayList<String>();
					arr.add("['nulo']");
					arr.add("['nulo']");
					array.add(cont, arr);
					cont++;
					continue;
				}
				ArrayList<String> arr = new ArrayList<String>();
				arr.add("['"+rs.getString("DIA")+"']");
				arr.add(this.getHoras(rs.getString("DIA")));
				
				array.add(cont, arr);
				cont++;
			}
		}
		System.out.println("Arreglo de horas");
		System.out.println(array);
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param Reserva Res que se ingresará a la BD
	 * Inserción de una reserva a la BD 
	 */
	public int IngresarReserva(Reserva res) throws SQLException {
		
		int bool = (res.isValidado())?1:0;
		int bool2 = (res.isPreReserva())?1:0;
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		String Insert= "";
		
		String fechafinal = "";
		if(res.getFecha()!=null){
			fechafinal = "'"+ formateador.format(res.getFecha())+"'";
		}else{
			fechafinal = "null";
		}
		
		if(res.getId_Metodo_Pago() != -1 ){
			Insert = "INSERT INTO [dbo].[16_Reserva] "
					+ "([03_Id_Agenda]"
					+ ",[15_Id_Cliente]"
					+ ",[05_Id_Tipo_Cliente]"
					+ ",[17_Id_Campania]"
					+ ",[24_Id_Ticket]"
					+ ",[04_Id_Trabajador]"
					+ ",[16_Cantidad_Personas]"
					+ ",[16_Cantidad_Adicionales]"
					+ ",[16_Cantidad_Reagendamiento]"
					+ ",[16_Monto_Pago_Estudio]"
					+ ",[16_Monto_Pago_Adelantado]"
					+ ",[11_Id_Estado]"
					+ ",[16_Fecha]"
					+ ",[16_Codigo]"
					+ ",[16_Validado]"
					+ ",[34_ID_TIPO_SESION]"
					+ ",[16_Pre_Reserva]"
					+ ",[38_Id_Vendedor]"
					+ ",[37_Id_Metodo_Pago]"
					+ ",[16_Observacion])"
					+ "VALUES"
					+ "("+res.getId_Agenda()+","
					+ ""+res.getId_Cliente() +","
					+ ""+res.getTipo_Cliente() +","
					+ ""+res.getId_Campania() +","
					+ ""+res.getId_Ticket() +","
					+ ""+res.getId_Trabajador() +","
					+ ""+res.getCantidad_Personas() +","
					+ ""+res.getCantidad_Adicionales()+","
					+ ""+res.getCantidad_Reagendamiento() +","
					+ ""+res.getMonto_Pago_Estudio() +","
					+ ""+res.getMonto_Pago_Adelantado() +","
					+ ""+res.getId_Estado()+","
					+ ""+fechafinal+","
					+ "'"+res.getCodigo() +"',"
					+ ""+bool+","
					+ ""+res.getId_Tipo_Sesion()+","
					+ ""+bool2+","
					+ "'"+res.getVendedor()+"',"
					+ ""+res.getId_Metodo_Pago()+","
					+ "'"+res.getObservacion()+"');";
		}else{
			Insert = "INSERT INTO [dbo].[16_Reserva] "
					+ "([03_Id_Agenda]"
					+ ",[15_Id_Cliente]"
					+ ",[05_Id_Tipo_Cliente]"
					+ ",[17_Id_Campania]"
					+ ",[24_Id_Ticket]"
					+ ",[04_Id_Trabajador]"
					+ ",[16_Cantidad_Personas]"
					+ ",[16_Cantidad_Adicionales]"
					+ ",[16_Cantidad_Reagendamiento]"
					+ ",[16_Monto_Pago_Estudio]"
					+ ",[16_Monto_Pago_Adelantado]"
					+ ",[11_Id_Estado]"
					+ ",[16_Fecha]"
					+ ",[16_Codigo]"
					+ ",[16_Validado]"
					+ ",[34_ID_TIPO_SESION]"
					+ ",[16_Pre_Reserva]"
					+ ",[38_Id_Vendedor]"
					+ ",[37_Id_Metodo_Pago]"
					+ ",[16_Observacion])"
					+ "VALUES"
					+ "("+res.getId_Agenda()+","
					+ ""+res.getId_Cliente() +","
					+ ""+res.getTipo_Cliente() +","
					+ ""+res.getId_Campania() +","
					+ ""+res.getId_Ticket() +","
					+ ""+res.getId_Trabajador() +","
					+ ""+res.getCantidad_Personas() +","
					+ ""+res.getCantidad_Adicionales()+","
					+ ""+res.getCantidad_Reagendamiento() +","
					+ ""+res.getMonto_Pago_Estudio() +","
					+ ""+res.getMonto_Pago_Adelantado() +","
					+ ""+res.getId_Estado()+","
					+ ""+fechafinal+","
					+ "'"+res.getCodigo() +"',"
					+ ""+bool+","
					+ ""+res.getId_Tipo_Sesion()+","
					+ ""+bool2+","
					+ "'"+res.getVendedor()+"',"
					+ "NULL);";
			
		}
		System.out.println(Insert);
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(Insert);
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	

	/**
	 * @author Advancing
	 * @param Reserva res que se actualizarán sus datos con respecto a la Id_Reserva entregada
	 * Update de una reserva, modificar
	 */
	public int ActualizarReserva(Reserva res){
		
		int bool = (res.isValidado())?1:0;
		int bool2 = (res.isPreReserva())?1:0;
		
		String Metodo = "NULL";
		if(res.getId_Metodo_Pago()!=0){
			Metodo = Integer.toString(res.getId_Metodo_Pago());
		}
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		String fecha = ",";
		if(res.getFecha()!=null){
			fecha += "[16_Fecha] = '"+formateador.format(res.getFecha())+"'";
		}else{
			fecha += "[16_Fecha] = NULL";
		}
		
		String SQL = "UPDATE [dbo].[16_Reserva] "
				+ " SET [03_Id_Agenda] = "+res.getId_Agenda()
				+ ",[15_Id_Cliente] = "+res.getId_Cliente()
				+ ",[05_Id_Tipo_Cliente] = " + res.getTipo_Cliente()
				+ ",[17_Id_Campania] = "+res.getId_Campania()
				+ ",[24_Id_Ticket] = "+res.getId_Ticket()
				+ ",[04_Id_Trabajador] = "+res.getId_Trabajador()
				+ ",[16_Cantidad_Personas] = "+res.getCantidad_Personas()
				+ ",[16_Cantidad_Adicionales] = "+res.getCantidad_Adicionales()
				+ ",[16_Cantidad_Reagendamiento] ="+res.getCantidad_Reagendamiento()
				+ ",[16_Monto_Pago_Estudio] = "+res.getMonto_Pago_Estudio()
				+ ",[16_Monto_Pago_Adelantado] = "+res.getMonto_Pago_Adelantado()
				+ ",[11_Id_Estado] = "+res.getId_Estado()
				+ ""+ fecha
				+ ",[16_Codigo] = '"+res.getCodigo()+"'"
				+ ",[16_Validado] = "+bool
				+ ",[34_ID_TIPO_SESION] = "+res.getId_Tipo_Sesion()
				+ ",[16_Pre_Reserva] = "+bool2
				+ ",[38_Id_Vendedor] = '"+res.getVendedor()+"'"
				+ ",[37_Id_Metodo_Pago] = "+Metodo
				+ ",[16_Observacion] = '"+res.getObservacion()+"'"
				+ " WHERE [16_Id_Reserva] = "+res.getId_Reserva()+";";
		
		System.out.println(SQL);
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	
	
	/**
	 * @author Advancing
	 * @param Int id de una reserva a la cual se le actualizará su estado de invalidado a validado
	 * Validar una reserva (Cupón validado)
	 */
	public int ValidarReserva(int id){
		String SQL = "UPDATE [dbo].[16_Reserva] "
				+ " SET [16_Validado] = 1"
				+ " WHERE [16_Id_Reserva] = "+id+";";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	/** 
	 * @author Advancing
	 * @param Reserva que contiene una ID donde se actualizará y un monto que asignar
	 * Update de una reserva, asignar un monto de anticipo
	 */
	public int AsignarAnticipo(Reserva res){
		
		String SQL = "UPDATE [dbo].[16_Reserva] "
				+ " SET [16_Monto_Pago_Adelantado] = "+res.getMonto_Pago_Adelantado()+","
				+ " [16_Tipo_Anticipo] = '"+res.getTipo_Anticipo()+"',"
				+ " [16_Fecha_Anticipo] = '"+res.getFecha_Anticipo()+"',"
				+ " [16_Hora_Anticipo] = '"+res.getHora_Anticipo()+"',"
				+ " [16_Nombre_Anticipo] = '"+res.getNombre_Anticipo()+"',"
				+ " [16_Pre_Reserva] = 0 " 
				+ " WHERE [16_Id_Reserva] = "+res.getId_Reserva()+";";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	

	/**
	 * @author Advancing
	 * @param int id de una reserva que será eliminada
	 * Eliminar una reserva de la BD
	 */
	public int EliminarReserva(int id){
		String SQL = "DELETE FROM [dbo].[16_Reserva] WHERE [dbo].[16_Reserva].[16_Id_Reserva] = "+id+" ;";

		this.EliminarSesionAuxiliar(id);
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
		
	}

	/**
	 * @author Advancing
	 * @param Cliente cli que será ingresado a la BD
	 * Inserción de un cliente a la BD
	 */
	public int IngresarCliente(Cliente cli){
		
		int bool = (cli.isReclamo())?1:0;
		String Comuna = (Integer.toString(cli.getId_Comuna()).equals("0"))?"null":Integer.toString(cli.getId_Comuna());
		String Ciudad = (Integer.toString(cli.getId_Ciudad()).equals("0"))?"null":Integer.toString(cli.getId_Ciudad());
		
		String SQL = "INSERT INTO [dbo].[15_Cliente]"
				+ "([15_Rut]"
				+ ",[15_Nombre]"
				+ ",[15_Apellido_Pat]"
				+ ",[15_Apellido_Mat]"
				+ ",[15_Direccion]"
				+ ",[08_Id_Comuna]"
				+ ",[06_Id_Ciudad]"
				+ ",[15_Fono]"
				+ ",[15_Celular]"
				+ ",[15_Mail]"
				+ ",[05_Id_Tipo_Cliente]"
				+ ",[15_Reclamo]"
				+ ",[15_Observacion]"
				+ ",[11_Id_Estado]"
				+ ",[15_Contrasenia])"
				+ " VALUES"
				+ " ("+cli.getRut()+""
				+ ",'"+cli.getNombre()+"'"
				+ ",'"+cli.getApellido_Pat()+"'"
				+ ",'"+cli.getApellido_Mat()+"'"
				+ ",'"+cli.getDireccion()+"'"
				+ ","+Comuna+""
				+ ","+Ciudad+""
				+ ","+cli.getFono()+""
				+ ","+cli.getCelular()+""
				+ ",'"+cli.getMail()+"'"
				+ ", null"
				+ ","+bool+""
				+ ",'"+cli.getObservacion()+"'"
				+ ","+cli.getId_Estado()+""
				+ ",'"+cli.getConstrasenia()+"')";

		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}

	/**
	 * @author Advancing
	 * @param Cliente cli al cual se le actualizarán sus datos con respecto a la Id_Cliente entregada
	 * Update de una reserva, modificar
	 */
	public int ActualizarCliente(Cliente cli){
		
		int bool = (cli.isReclamo())?1:0;
		
		String SQL = "UPDATE [dbo].[15_Cliente] "
				+ " SET [15_Rut] = "+cli.getRut()
				+ ",[15_Nombre] = '"+cli.getNombre()+"'"
				+ ",[15_Apellido_Pat] = '"+cli.getApellido_Pat()+"'"
				+ ",[15_Apellido_Mat] = '"+cli.getApellido_Mat()+"'"
				+ ",[15_Direccion] = '"+cli.getDireccion()+"'"
				+ ",[08_Id_Comuna] = "+cli.getId_Comuna()
				+ ",[06_Id_Ciudad] = "+cli.getId_Ciudad()
				+ ",[15_Fono] = "+cli.getFono()
				+ ",[15_Celular] = "+cli.getCelular()
				+ ",[15_Mail] = '"+cli.getMail()+"'"
				+ ",[05_Id_Tipo_Cliente] = "+cli.getId_Tipo_Cliente()
				+ ",[15_Reclamo] = "+bool
				+ ",[15_Observacion] = '"+cli.getObservacion()+"'"
				+ ",[11_Id_Estado] = "+cli.getId_Estado()
				+ ",[15_Contrasenia] = '"+cli.getConstrasenia()+"'"
				+ " WHERE [15_Id_Cliente] = "+cli.getId_Cliente()+";";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	
	
	/**
	 *  @author Advancing
	 *  @param int id de un cliente que será eliminado
	 * 	Eliminar un cliente de la BD
	 */
	public int EliminarCliente(int id){
		
		String SQL2 = "SELECT [16_ID_RESERVA], [15_ID_CLIENTE] FROM [16_RESERVA] WHERE [15_ID_CLIENTE] = "+ id;
		Statement ss = null;
		int columnasafectadass = 0;
		try {
			Connection conn = getConexion();
			ss = conn.createStatement();
			columnasafectadass = ss.executeUpdate(SQL2);
			if (columnasafectadass > 1) {
				return 0;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		
		
		String SQL = "DELETE FROM [dbo].[15_Cliente] WHERE [dbo].[15_Cliente].[15_Id_Cliente] = "+id+" ;";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
		
	}
	
	/**
	 * @author Advancing
	 * @param Trabajador tra que será un Trabajador a ingresar a la BD
	 * Inserción de un trabajador a la BD
	 */
	public int IngresarTrabajador(Trabajador tra){
		
		String SQL = "INSERT INTO [dbo].[04_Trabajador]"
				+ "([04_Rut]"
				+ ",[04_Nombre]"
				+ ",[04_Apellido_Pat]"
				+ ",[04_Apellido_Mat]"
				+ ",[04_Contrasenia]"
				+ ",[04_EsAdmin]"
				+ ",[11_Id_Estado]"
				+ ",[04_Email])"
				+ " VALUES"
				+ " ("+tra.getRut()+""
				+ ",'"+tra.getNombre()+"'"
				+ ",'"+tra.getApellido_Pat()+"'"
				+ ",'"+tra.getApellido_Mat()+"'"
				+ ",'"+tra.getContrasenia()+"'"
				+ ","+tra.getEsAdmin()+""
				+ ","+tra.getId_Estado()+""
				+ ",'"+tra.getEmail()+"')";
				
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	/**
	 * @author Advancing
	 * @param Trabajador tra que indica un trabajador al cual se le actualizarán los datos dada una ID
	 * Actualización de un trabajador en la BD
	 */
	public int ActualizarTrabajador(Trabajador tra){
				
		String SQL = "UPDATE [dbo].[04_Trabajador] "
				+ " SET [04_Rut] = "+tra.getRut()
				+ ",[04_Nombre] = '"+tra.getNombre()+"'"
				+ ",[04_Apellido_Pat] = '"+tra.getApellido_Pat()+"'"
				+ ",[04_Apellido_Mat] = '"+tra.getApellido_Mat()+"'"
				+ ",[04_EsAdmin] = "+tra.getEsAdmin()+""
				+ ",[04_Email] = '"+tra.getEmail()+"'"
				+ " WHERE [04_Id_Trabajador] = "+tra.getId_Trabajador()+";";

		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	/**
	 *  @author Advancing
	 * 	@param int id que indica la ID de un trabajador a eliminar
	 * 	Eliminar un trabajador de la BD
	 */
	public int EliminarTrabajador(int id){
		String SQL = "DELETE FROM [dbo].[04_Trabajador] WHERE [dbo].[04_Trabajador].[04_Id_Trabajador] = "+id+" ;";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
		
	}
	
	/**
	 * 	@author Advancing
	 *  @param Campania Camp que indica una campaña a la cual se le actualizarán los datos, dada una ID
	 *	Inserción de una campaña a la BD 
	 */
	/*public int IngresarCampania(Campania camp) throws SQLException {
		
		int bool = (camp.isCD())?1:0;
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		String Insert = "INSERT INTO [dbo].[17_Campania] "
				+ "([17_Nombre]"
				+ ",[14_Id_Canal_Venta]"
				+ ",[17_Inicio_Vigencia]"
				+ ",[17_Fin_Vigencia]"
				+ ",[17_Precio]"
				+ ",[17_Maximo_Personas]"
				+ ",[17_Descripcion]"
				+ ",[11_Id_Estado]"
				+ ",[17_Posee_CD]"
				+ ",[17_Cant_Fotos_CD]"
				+ ",[17_Cant_10x15]"
				+ ",[17_Cant_15x21]"
				+ ",[17_Cant_20x30]"
				+ ",[17_Cant_30x40]"
				+ ",[17_Precio_Adicional]"
				+ ",[17_Precio_Reagendamiento]"
				+ ",[17_Descripcion_Adicional])"
				+ "VALUES"
				+ "('"+camp.getNombre()+"',"
				+ ""+camp.getId_Canal_Venta()+","
				+ "'"+formateador.format(camp.getInicio_Vigencia())+"',"
				+ "'"+formateador.format(camp.getFin_Vigencia())+"',"
				+ ""+camp.getPrecio()+","
				+ ""+camp.getMaximo_Personas()+","
				+ "'"+camp.getDescripcion().replace("'", "''")+"',"
				+ "1,"
				+ ""+bool+","
				+ ""+camp.getCant_Fotos_CD()+","
				+ ""+camp.getCant_10x15()+","
				+ ""+camp.getCant_15x21()+","
				+ ""+camp.getCant_20x30()+","
				+ ""+camp.getCant_30x40()+","
				+ ""+camp.getPrecio_Adicional()+","
				+ ""+camp.getPrecio_Reagendamiento()+","
				+ "'"+camp.getDescripcion_Adicional().replace("'", "''")+"');";
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(Insert);
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	*/
	
public int IngresarCampania(Campania camp) throws SQLException {
		
		int bool = (camp.isCD())?1:0;
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			PreparedStatement p = conn.prepareStatement("INSERT INTO [dbo].[17_Campania] "
					+ "([17_Nombre]"
					+ ",[14_Id_Canal_Venta]"
					+ ",[17_Inicio_Vigencia]"
					+ ",[17_Fin_Vigencia]"
					+ ",[17_Precio]"
					+ ",[17_Maximo_Personas]"
					+ ",[17_Descripcion]"
					+ ",[11_Id_Estado]"
					+ ",[17_Posee_CD]"
					+ ",[17_Cant_Fotos_CD]"
					+ ",[17_Cant_10x15]"
					+ ",[17_Cant_15x21]"
					+ ",[17_Cant_20x30]"
					+ ",[17_Cant_30x40]"
					+ ",[17_Precio_Adicional]"
					+ ",[17_Precio_Reagendamiento]"
					+ ",[17_Descripcion_Adicional]"
					+ ",[17_Abono])"
					+ "VALUES"
					+ "(?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?);");
			
			int i = 1;
			p.setString(i++, camp.getNombre());
			p.setString(i++, String.valueOf(camp.getId_Canal_Venta()));
			p.setString(i++, formateador.format(camp.getInicio_Vigencia()));
			p.setString(i++, formateador.format(camp.getFin_Vigencia()));
			p.setString(i++, String.valueOf(camp.getPrecio()));
			p.setString(i++, String.valueOf(camp.getMaximo_Personas()));
			p.setString(i++, camp.getDescripcion());
			p.setString(i++, String.valueOf(1));
			p.setString(i++, String.valueOf(bool));
			p.setString(i++, String.valueOf(camp.getCant_Fotos_CD()));
			p.setString(i++, String.valueOf(camp.getCant_10x15()));
			p.setString(i++, String.valueOf(camp.getCant_15x21()));
			p.setString(i++, String.valueOf(camp.getCant_20x30()));
			p.setString(i++, String.valueOf(camp.getCant_30x40()));
			p.setString(i++, String.valueOf(camp.getPrecio_Adicional()));
			p.setString(i++, String.valueOf(camp.getPrecio_Reagendamiento()));
			p.setString(i++, String.valueOf(camp.getDescripcion_Adicional()));
			p.setString(i++, String.valueOf(camp.getAbono()));
			
			columnasafectadas = p.executeUpdate();
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	
		
	/**
	 * @author Advancing
	 * @param Campania camp que indica una campaña a la cual se le actualizarán los datos dada una ID
	 * Update de una campania, modificar
	 */
	/*public int ActualizarCampania(Campania camp){
		
		int bool = (camp.isCD())?1:0;
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		String SQL = "UPDATE [dbo].[17_Campania] "
				+ " SET [17_Nombre] = '"+camp.getNombre() +"'"
				+ ",[14_Id_Canal_Venta] = "+camp.getId_Canal_Venta()
				+ ",[17_Inicio_Vigencia] = '"+formateador.format(camp.getInicio_Vigencia())+"'"
				+ ",[17_Fin_Vigencia] = '"+formateador.format(camp.getFin_Vigencia())+"'"
				+ ",[17_Precio] = "+camp.getPrecio()
				+ ",[17_Maximo_Personas] = "+camp.getMaximo_Personas()
				+ ",[17_Descripcion] = '"+camp.getDescripcion().replace("'", "''") +"'"
				+ ",[11_Id_Estado] = 1"
				+ ",[17_Posee_CD] = "+bool
				+ ",[17_Cant_Fotos_CD] = "+camp.getCant_Fotos_CD()
				+ ",[17_Cant_10x15] = "+camp.getCant_10x15()
				+ ",[17_Cant_15x21] = "+camp.getCant_15x21()
				+ ",[17_Cant_20x30] = "+camp.getCant_20x30()
				+ ",[17_Cant_30x40] = "+camp.getCant_30x40()
				+ ",[17_Precio_Adicional] = "+camp.getPrecio_Adicional()
				+ ",[17_Precio_Reagendamiento] = "+camp.getPrecio_Reagendamiento()
				+ ",[17_Descripcion_Adicional] = '"+camp.getDescripcion_Adicional().replace("'", "''")+"'"
				+ " WHERE [17_Id_Campania] = "+camp.getId_Campania()+";";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			
			System.out.println(SQL);
			
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	*/
	
public int ActualizarCampania(Campania camp){
		
		int bool = (camp.isCD())?1:0;
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			
			PreparedStatement p = conn.prepareStatement("UPDATE [dbo].[17_Campania] "
					+ " SET [17_Nombre] = ?"
					+ ",[14_Id_Canal_Venta] = ?"
					+ ",[17_Inicio_Vigencia] = ?"
					+ ",[17_Fin_Vigencia] = ?"
					+ ",[17_Precio] = ?"
					+ ",[17_Maximo_Personas] = ?"
					+ ",[17_Descripcion] = ?"
					+ ",[11_Id_Estado] = ?"
					+ ",[17_Posee_CD] = ?"
					+ ",[17_Cant_Fotos_CD] = ?"
					+ ",[17_Cant_10x15] = ?"
					+ ",[17_Cant_15x21] = ?"
					+ ",[17_Cant_20x30] = ?"
					+ ",[17_Cant_30x40] = ?"
					+ ",[17_Precio_Adicional] = ?"
					+ ",[17_Precio_Reagendamiento] = ?"
					+ ",[17_Descripcion_Adicional] = ?"
					+ ",[17_Abono] = ?"
					+ " WHERE [17_Id_Campania] = ?");
								
			int i = 1;
			p.setString(i++, camp.getNombre());
			p.setString(i++, String.valueOf(camp.getId_Canal_Venta()));
			p.setString(i++, formateador.format(camp.getInicio_Vigencia()));
			p.setString(i++, formateador.format(camp.getFin_Vigencia()));
			p.setString(i++, String.valueOf(camp.getPrecio()));
			p.setString(i++, String.valueOf(camp.getMaximo_Personas()));
			p.setString(i++, camp.getDescripcion());
			p.setString(i++, String.valueOf(1));
			p.setString(i++, String.valueOf(bool));
			p.setString(i++, String.valueOf(camp.getCant_Fotos_CD()));
			p.setString(i++, String.valueOf(camp.getCant_10x15()));
			p.setString(i++, String.valueOf(camp.getCant_15x21()));
			p.setString(i++, String.valueOf(camp.getCant_20x30()));
			p.setString(i++, String.valueOf(camp.getCant_30x40()));
			p.setString(i++, String.valueOf(camp.getPrecio_Adicional()));
			p.setString(i++, String.valueOf(camp.getPrecio_Reagendamiento()));
			p.setString(i++, String.valueOf(camp.getDescripcion_Adicional()));
			p.setString(i++, String.valueOf(camp.getAbono()));
			p.setString(i++, String.valueOf(camp.getId_Campania()));
			
			
			columnasafectadas = p.executeUpdate();
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	
	
	/**
	 * 	@author Advancing 
	 *  @param int id que indica la ID de una campaña a eliminar
	 * 	Eliminar una campaña de la BD
	 */
	public int EliminarCampania(int id){
		String SQL = "DELETE FROM [dbo].[17_Campania] WHERE [dbo].[17_Campania].[17_Id_Campania] = "+id+" ;";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
		
	}
	
	/**
	 * @author Advancing
	 * Obtención de Comunas ordenadas alfabéticamente por Nombre
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Comuna> getComunas() throws SQLException, java.text.ParseException{
		String SQL = "SELECT * FROM [DBO].[08_COMUNA] ORDER BY [08_COMUNA];";
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Comuna> array = new ArrayList<Comuna>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Comuna cc = new Comuna(
					rs.getInt("08_Id_Comuna"),
					rs.getInt("08_Id_Ciudad_Asoc"),
					rs.getString("08_Comuna"),
					rs.getInt("11_Id_Estado")
			);
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * Obtención de Comunas
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Comuna> getComunasId() throws SQLException, java.text.ParseException{
		String SQL = "SELECT * FROM [DBO].[08_COMUNA];";
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Comuna> array = new ArrayList<Comuna>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Comuna cc = new Comuna(
					rs.getInt("08_Id_Comuna"),
					rs.getInt("08_Id_Ciudad_Asoc"),
					rs.getString("08_Comuna"),
					rs.getInt("11_Id_Estado")
			);
			array.add(cc);
		}
		return array;
	} 

	/**
	 * @author Advancing
	 * Obtención de Ciudades
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Ciudad> getCiudades() throws SQLException, java.text.ParseException{
		String SQL = "SELECT * FROM [DBO].[06_CIUDAD];";
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Ciudad> array = new ArrayList<Ciudad>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Ciudad cc = new Ciudad(
					rs.getInt("06_Id_Ciudad"),
					rs.getString("06_Ciudad"),
					rs.getInt("11_Id_Estado")
			);
			array.add(cc);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param int id que entrega la ID de una comuna para buscarla
	 * Obtencion de Ciudades con un filtro
	 */
	public ArrayList<Ciudad> getCiudadesFiltro(int IdComuna) throws SQLException{
		String SQL = "SELECT [DBO].[08_COMUNA].[08_ID_COMUNA], [DBO].[08_COMUNA].[08_COMUNA], [DBO].[08_COMUNA].[08_ID_CIUDAD_ASOC]"
				+ "		, [DBO].[06_CIUDAD].[06_ID_CIUDAD],[DBO].[06_CIUDAD].[06_CIUDAD] FROM [DBO].[06_CIUDAD], "
				+ "		[DBO].[08_COMUNA] WHERE [DBO].[08_COMUNA].[08_ID_COMUNA] = "+IdComuna+" AND [DBO].[06_CIUDAD].[06_ID_CIUDAD]"
				+ " = [DBO].[08_COMUNA].[08_Id_Ciudad_Asoc]";
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Ciudad> array = new ArrayList<Ciudad>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Ciudad cc = new Ciudad();
			cc.setId_Ciudad(rs.getInt("06_Id_Ciudad"));
			cc.setCiudad(rs.getString("06_Ciudad"));
			array.add(cc);
		}
		return array;
	}

	/** 
	 * @author Advancing
	 * @param Cliente cli que indica un cliente al cual se le cambiará la contraseña dada una Id del cliente
	 * Update de un cliente, modificar contraseña
	 */
	public int CambiarContraseniaCliente(Cliente cli){
		
		String SQL = "UPDATE [dbo].[15_Cliente] "
				+ " SET [15_Contrasenia] = '"+cli.getConstrasenia()+"'"
				+ " WHERE [15_Id_Cliente] = "+cli.getId_Cliente()+";";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	

	/**
	 * @author Advancing
	 * Reservas para exportación a excel
	 */
	public ArrayList<ArrayList<String>> ArrayExcel() throws SQLException{
		String SQL = "  SELECT CONVERT(VARCHAR,  [16_Reserva].[16_Fecha], 105) AS FECHA,"
				+ "  CONVERT(VARCHAR, DATEPART(hh,  [16_RESERVA].[16_FECHA])) + ':' + "
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [16_RESERVA].[16_FECHA])), 2) AS HORA,"
				+ " [dbo].[16_Reserva].[16_Validado],"
				+ " [dbo].[16_Reserva].[15_Id_Cliente], [dbo].[15_Cliente].[15_Id_Cliente],"
				+ " [dbo].[15_Cliente].[15_Nombre],"
				+ " [dbo].[15_Cliente].[15_Apellido_Pat], [dbo].[15_Cliente].[15_Apellido_Mat],"
				+ " [dbo].[16_Reserva].[17_Id_Campania],[dbo].[17_Campania].[17_Id_Campania],"
				+ " [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion],"
				+ " [dbo].[34_Tipo_Sesion].[34_Tipo_Sesion],"
				+ " [dbo].[17_Campania].[14_Id_Canal_Venta], [dbo].[14_Canal_Venta].[14_Id_Canal_Venta],"
				+ " [dbo].[14_Canal_Venta].[14_Canal],"
				+ " [dbo].[17_Campania].[17_Precio],"
				+ " [dbo].[17_Campania].[17_Posee_CD],"
				+ " [dbo].[17_Campania].[17_Cant_Fotos_CD],"
				+ " [dbo].[17_Campania].[17_Cant_10x15],"
				+ " [dbo].[17_Campania].[17_Cant_15x21],"
				+ " [dbo].[17_Campania].[17_Cant_20x30],"
				+ " [dbo].[17_Campania].[17_Cant_30x40],"
				+ " [dbo].[16_Reserva].[16_Cantidad_Adicionales],"
				+ " [dbo].[16_Reserva].[16_Cantidad_Reagendamiento],"
				+ " [dbo].[16_Reserva].[16_Pre_Reserva]"
				+ " FROM [dbo].[16_Reserva],[dbo].[14_Canal_Venta],[dbo].[17_Campania],[dbo].[15_Cliente],[dbo].[34_Tipo_Sesion]"
				+ " WHERE  [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente]"
				+ " AND  [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania]"
				+ " AND  [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]"
				+ " AND  [dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_Canal_Venta].[14_Id_Canal_Venta];";
		
		ResultSet rs = Consultar(SQL);
		
		System.out.println(SQL);
				
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<String> fila = new ArrayList<String>();
				fila.add(rs.getString("FECHA"));
								
				fila.add(rs.getString("HORA"));
				String Validado = "";
				if(rs.getBoolean("16_Validado")){
					Validado = "SI";
				}else{
					Validado = "NO";
				}
				fila.add(Validado);
				fila.add(rs.getString("15_Nombre"));
				fila.add(rs.getString("15_Apellido_Pat")+" "+rs.getString("15_Apellido_Mat"));
				fila.add(rs.getString("34_Tipo_Sesion"));
				fila.add(rs.getString("14_Canal"));
				fila.add(rs.getString("17_Precio"));
				fila.add("-");
				String CD = "";
				if(rs.getBoolean("17_Posee_CD")){
					CD = "SI - "+rs.getString("17_Cant_Fotos_CD")+" fotos";
				}else{
					CD = "NO";
				}
				fila.add(CD);
				fila.add(rs.getString("17_Cant_10x15"));
				fila.add(rs.getString("17_Cant_15x21"));
				fila.add(rs.getString("17_Cant_20x30"));
				fila.add(rs.getString("17_Cant_30x40"));
				fila.add(rs.getString("16_Cantidad_Adicionales"));
				if(rs.getInt("16_Cantidad_Reagendamiento")>0){
					fila.add("SI");
				}else{
					fila.add("NO");
				}
				if(rs.getBoolean("16_Pre_Reserva")){
					fila.add("SI");
				}else{
					fila.add("NO");
				}
				array.add(fila);
			}
		}
		return array;
	}

	/**
	 * @author Advancing
	 * Campañas para exportación a excel
	 */
	public ArrayList<ArrayList<String>> ArrayExcelCampanias() throws SQLException{
		String SQL = "SELECT [17_Id_Campania]"
				+ "      ,[17_Nombre]"
				+ "      ,[dbo].[17_Campania].[14_Id_Canal_Venta], [dbo].[14_Canal_Venta].[14_Id_Canal_Venta], [dbo].[14_Canal_Venta].[14_Canal]"
				+ "      ,CONVERT(VARCHAR,  [17_Inicio_Vigencia], 105) AS [17_Inicio_Vigencia] "
				+ "      ,CONVERT(VARCHAR,  [17_Fin_Vigencia], 105) AS [17_Fin_Vigencia] "
				+ "      ,[17_Precio]"
				+ "      ,[17_Maximo_Personas]"
				+ "      ,[17_Descripcion]"
				+ "      ,[dbo].[17_Campania].[11_Id_Estado], [dbo].[11_Estado].[11_Id_Estado], [dbo].[11_Estado].[11_Estado]"
				+ "      ,[17_Posee_CD]"
				+ "      ,[17_Cant_Fotos_CD]"
				+ "      ,[17_Cant_10x15]"
				+ "      ,[17_Cant_15x21]"
				+ "      ,[17_Cant_20x30]"
				+ "      ,[17_Cant_30x40]"
				+ "      ,[17_Monto_Cv]"
				+ "  FROM [dbo].[17_Campania], [dbo].[14_Canal_Venta], [dbo].[11_Estado]"
				+ "  WHERE [dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_Canal_Venta].[14_Id_Canal_Venta]"
				+ "  AND [dbo].[17_Campania].[11_Id_Estado] = [dbo].[11_Estado].[11_Id_Estado];";
		
		ResultSet rs = Consultar(SQL);
				
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<String> fila = new ArrayList<String>();
				fila.add(rs.getString("17_Id_Campania"));
				fila.add(rs.getString("17_Nombre"));
				fila.add(rs.getString("14_Canal"));
				fila.add(rs.getString("17_Inicio_Vigencia"));
				fila.add(rs.getString("17_Fin_Vigencia"));
				fila.add(rs.getString("17_Precio"));
				fila.add(rs.getString("17_Maximo_Personas"));
				fila.add(rs.getString("17_Descripcion"));
				fila.add(rs.getString("11_Estado"));
				
				String PoseeCD = "";
				if(rs.getBoolean("17_Posee_CD")){
					PoseeCD = "SI";
				}else{
					PoseeCD = "NO";
				}
				fila.add(PoseeCD);
				
				fila.add(rs.getString("17_Cant_Fotos_CD"));
				fila.add(rs.getString("17_Cant_10x15"));
				fila.add(rs.getString("17_Cant_15x21"));
				fila.add(rs.getString("17_Cant_20x30"));
				fila.add(rs.getString("17_Cant_30x40"));
				
				array.add(fila);
			}
		}
		return array;
	}
	
	/** 
	 * @author Advancing
	 * Campañas para exportación a excel
	 */
	public ArrayList<ArrayList<String>> ArrayExcelClientes() throws SQLException{
		String SQL = "SELECT [15_Id_Cliente]"
				+ "      ,[15_Rut]"
				+ "      ,[15_Nombre]"
				+ "      ,[15_Apellido_Pat]"
				+ "      ,[15_Apellido_Mat]"
				+ "      ,[15_Direccion]"
				+ "      ,[dbo].[15_Cliente].[08_Id_Comuna] ,[dbo].[08_Comuna].[08_Id_Comuna], [dbo].[08_Comuna].[08_Comuna]"
				+ "      ,[dbo].[15_Cliente].[06_Id_Ciudad],[dbo].[06_Ciudad].[06_Id_Ciudad],[dbo].[06_Ciudad].[06_Ciudad]"
				+ "      ,[15_Fono]"
				+ "      ,[15_Celular]"
				+ "      ,[15_Mail]"
				+ "      ,[05_Id_Tipo_Cliente]"
				+ "      ,[15_Reclamo]"
				+ "      ,[15_Observacion]"
				+ "      ,[dbo].[15_Cliente].[11_Id_Estado], [dbo].[11_Estado].[11_Id_Estado], [dbo].[11_Estado].[11_Estado]"
				+ "      ,[15_CV]"
				+ "  FROM [dbo].[15_Cliente], [dbo].[11_Estado], [dbo].[08_Comuna],[dbo].[06_Ciudad]"
				+ "  WHERE [dbo].[15_Cliente].[08_Id_Comuna] = [dbo].[08_Comuna].[08_Id_Comuna]"
				+ "  AND [dbo].[15_Cliente].[06_Id_Ciudad] = [dbo].[06_Ciudad].[06_Id_Ciudad]"
				+ "	 AND [dbo].[15_Cliente].[11_Id_Estado] = [dbo].[11_Estado].[11_Id_Estado]";
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<String> fila = new ArrayList<String>();
				fila.add(rs.getString("15_Id_Cliente"));
				fila.add(rs.getString("15_Rut"));
				fila.add(rs.getString("15_Nombre"));
				fila.add(rs.getString("15_Apellido_Pat"));
				fila.add(rs.getString("15_Apellido_Mat"));
				fila.add(rs.getString("06_Ciudad"));
				fila.add(rs.getString("08_Comuna"));
				fila.add(rs.getString("15_Fono"));
				fila.add(rs.getString("15_Celular"));
				fila.add(rs.getString("15_Mail"));
				
				String comentario = "NO";
				if(rs.getBoolean("15_Reclamo")){
					comentario = "SI - "+ rs.getString("15_Observacion");
				}
				
				fila.add(comentario);
				fila.add(rs.getString("11_Estado"));
				
				if(rs.getString("15_CV")!=null){
					fila.add(rs.getString("15_CV"));
				}else{
					fila.add("N/A");
				}
				
											
				array.add(fila);
			}
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * Reservas para exportación a excel con reservas y sesiones
	 */
	public ArrayList<ArrayList<String>> ArrayExcelSesiones() throws SQLException{
		String SQL = "  SELECT CONVERT(VARCHAR,  [16_Reserva].[16_Fecha], 105) AS FECHA,"
				+ "  CONVERT(VARCHAR, DATEPART(hh,  [16_RESERVA].[16_FECHA])) + ':' +"
				+ "  RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [16_RESERVA].[16_FECHA])), 2) AS HORA,"
				+ "  [dbo].[16_Reserva].[16_Validado],"
				+ "  [dbo].[15_Cliente].[15_Nombre],"
				+ "	 [dbo].[15_Cliente].[15_Apellido_Pat], [dbo].[15_Cliente].[15_Apellido_Mat],"
				+ "	 [dbo].[16_Reserva].[17_Id_Campania],[dbo].[17_Campania].[17_Id_Campania],"
				+ "	 [dbo].[16_Reserva].[34_Id_Tipo_Sesion], [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion],"
				+ "	 [dbo].[34_Tipo_Sesion].[34_Tipo_Sesion],"
				+ "	 [dbo].[17_Campania].[14_Id_Canal_Venta], [dbo].[14_Canal_Venta].[14_Id_Canal_Venta],"
				+ "	 [dbo].[14_Canal_Venta].[14_Canal],"
				+ "	 [dbo].[17_Campania].[17_Precio],"
				+ "	 [dbo].[17_Campania].[17_Posee_CD],"
				+ "	 [dbo].[17_Campania].[17_Cant_Fotos_CD],"
				+ "	 [dbo].[17_Campania].[17_Cant_10x15],"
				+ "	 [dbo].[17_Campania].[17_Cant_15x21],"
				+ "	 [dbo].[17_Campania].[17_Cant_20x30],"
				+ "	 [dbo].[17_Campania].[17_Cant_30x40],"
				+ "	 [dbo].[16_Reserva].[16_Cantidad_Adicionales],"
				+ "	 [dbo].[16_Reserva].[16_Cantidad_Reagendamiento],"
				+ "	 [dbo].[16_Reserva].[16_Id_Reserva], [dbo].[35_Auxiliar].[16_Id_Reserva],"
				+ "	 [dbo].[35_Auxiliar].[35_Asistio],"
				+ "	 [dbo].[35_Auxiliar].[35_Numero_Ticket],"
				+ "	 [dbo].[35_Auxiliar].[35_Valor_Por_Cobrar],"
				+ "	 [dbo].[35_Auxiliar].[35_CD],"
				+ "	 [dbo].[35_Auxiliar].[35_Extras],"
				+ "	 [dbo].[35_Auxiliar].[35_Persona_Adicional],"
				+ "	 [dbo].[35_Auxiliar].[35_Recargo_Por_Reagendar],"
				+ "	 [dbo].[35_Auxiliar].[35_Monto_Extras],"
				+ "	 [dbo].[35_Auxiliar].[35_Fotografo],"
				+ "	 [dbo].[35_Auxiliar].[35_Fotos_Seleccionadas],"
				+ "	 CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Entrega], 105) AS [35_Fecha_Entrega],"
				+ "	 [dbo].[35_Auxiliar].[35_Entregadas],"
				+ "	 CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Envio_Imprimir], 105) AS [35_Fecha_Envio_Imprimir],"
				+ "	 [dbo].[35_Auxiliar].[35_Monto_Impresion],"
				+ "	 [dbo].[35_Auxiliar].[35_Numero_Factura],"
				+ "	 [dbo].[35_Auxiliar].[35_Cant_10x15],"
				+ "	 [dbo].[35_Auxiliar].[35_Cant_15x21],"
				+ "	 [dbo].[35_Auxiliar].[35_Cant_20x30],"
				+ "	 [dbo].[35_Auxiliar].[35_Cant_30x40]"
				+ "	  FROM [dbo].[16_Reserva],[dbo].[14_Canal_Venta],[dbo].[17_Campania],[dbo].[15_Cliente],[dbo].[34_Tipo_Sesion],[dbo].[35_Auxiliar]"
				+ "	  WHERE  [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente]"
				+ "	   AND  [dbo].[16_Reserva].[17_Id_Campania] = [dbo].[17_Campania].[17_Id_Campania]"
				+ "	   AND  [dbo].[16_Reserva].[34_Id_Tipo_Sesion] = [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion]"
				+ "	   AND  [dbo].[17_Campania].[14_Id_Canal_Venta] = [dbo].[14_Canal_Venta].[14_Id_Canal_Venta]"
				+ "	   AND  [dbo].[16_Reserva].[16_Id_Reserva] = [dbo].[35_Auxiliar].[16_Id_Reserva]";		
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		
		System.out.println(SQL);
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<String> fila = new ArrayList<String>();
				fila.add(rs.getString("FECHA"));
				fila.add(rs.getString("HORA"));
				
				//Boleano
				String Validado = "";
				if(rs.getBoolean("16_Validado")){
					Validado = "SI";
				}else{
					Validado = "NO";
				}
				
				fila.add(Validado);
				fila.add(rs.getString("15_Nombre"));
				fila.add(rs.getString("15_Apellido_Pat")+" "+rs.getString("15_Apellido_Mat"));
				fila.add(rs.getString("34_Tipo_Sesion"));
				fila.add(rs.getString("14_Canal"));
				fila.add(rs.getString("17_Precio"));
				fila.add("-");
				
				//Boleano
				String CD = "";
				if(rs.getBoolean("17_Posee_CD")){
					CD = "SI - "+rs.getString("17_Cant_Fotos_CD")+" fotos";
				}else{
					CD = "NO";
				}
				fila.add(CD);
				
				fila.add(rs.getString("17_Cant_10x15"));
				fila.add(rs.getString("17_Cant_15x21"));
				fila.add(rs.getString("17_Cant_20x30"));
				fila.add(rs.getString("17_Cant_30x40"));
				fila.add(rs.getString("16_Cantidad_Adicionales"));
				
				//Boleano
				if(rs.getInt("16_Cantidad_Reagendamiento")>0){
					fila.add("SI");
				}else{
					fila.add("NO");
				}
				
				//Boleano
				if(rs.getBoolean("35_Asistio")){
					fila.add("SI");
				}else{
					fila.add("NO");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					fila.add("");
					
					array.add(fila);
					continue;
				}
				if(rs.getString("35_Numero_Ticket").equals("-1")){
					fila.add("-");
				}else
					fila.add(rs.getString("35_Numero_Ticket"));
				
				if(rs.getString("35_Valor_Por_Cobrar").equals("-1")){
					fila.add("-");
				}else{
					fila.add(rs.getString("35_Valor_Por_Cobrar"));
				}
				
				if(rs.getString("35_CD").equals("-1")){
					fila.add("-");
				}else{
					fila.add(rs.getString("35_CD"));
				}
		
				if(rs.getString("35_Extras").equals("-1")){
					fila.add("-");
				}else{
					fila.add(rs.getString("35_Extras"));
				}

				if(rs.getString("35_Persona_Adicional").equals("-1")){
					fila.add("-");
				}else{
					fila.add(rs.getString("35_Persona_Adicional"));
				}

				if(rs.getString("35_Recargo_Por_Reagendar").equals("-1")){
					fila.add("-");
				}else{
					fila.add(rs.getString("35_Recargo_Por_Reagendar"));
				}
				
				if(rs.getString("35_Monto_Extras").equals("-1")){
					fila.add("-");
				}else{
					fila.add(rs.getString("35_Monto_Extras"));
				}
				
				fila.add(rs.getString("35_Fotografo"));
				
				//Boleano
				if(rs.getBoolean("35_Fotos_Seleccionadas")){
					fila.add("SI");
				}else{
					fila.add("NO");
				}
				fila.add(rs.getString("35_Fecha_Entrega"));
				
				//Boleano
				if(rs.getBoolean("35_Entregadas")){
					fila.add("SI");
				}else{
					fila.add("NO");
				}
				fila.add(rs.getString("35_Fecha_Envio_Imprimir"));
				fila.add(rs.getString("35_Monto_Impresion"));
				fila.add(rs.getString("35_Numero_Factura"));
				fila.add(rs.getString("35_Cant_10x15"));
				fila.add(rs.getString("35_Cant_15x21"));
				fila.add(rs.getString("35_Cant_20x30"));
				fila.add(rs.getString("35_Cant_30x40"));
				
				array.add(fila);
			}
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * Fechas que ya están agendadas en la BD
	 */
	public String FechasDeshabilitadas() throws SQLException{
		String SQL = "SELECT FECHA FROM (SELECT Fecha1 AS FECHA, COUNT(Fecha2) AS CANTIDAD FROM (  "
				+ "	  SELECT CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) AS Fecha1 "
				+ "	  , CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha2"
				+ "	  FROM  [DBO].[16_RESERVA]) "
				+ "  AS CONTADOR"
				+ "  GROUP BY Fecha1) AS TABLA1"
				+ "  WHERE CANTIDAD>=9";
		
		ResultSet rs = Consultar(SQL);
		
		String fechas = "";
				
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			if(rs.next()){
				fechas = fechas + "['"+rs.getString("FECHA")+"'],['2016/05/09'";
			}
			while (rs.next()) {
				fechas = fechas + ",'"+rs.getString("FECHA")+"'";
			}
			fechas = fechas + "]";
			
		}
		System.out.println(fechas);
		return fechas;
	}

	/**
	 * @author Advancing
	 * Fechas que ya están agendadas en la BD (Método para usar en Javascript)
	 */
	public String[] FechasDeshabilitadasArreglo() throws SQLException{
		String SQL = "SELECT FECHA FROM (SELECT Fecha1 AS FECHA, COUNT(Fecha2) AS CANTIDAD FROM (  "
				+ "	  SELECT CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) AS Fecha1 "
				+ "	  , CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha2"
				+ "	  FROM  [DBO].[16_RESERVA]) "
				+ "  AS CONTADOR"
				+ "  GROUP BY Fecha1) AS TABLA1"
				+ "  WHERE CANTIDAD>=9";
		
		ResultSet rs = Consultar(SQL);
				
		String[] arr = null;
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			ArrayList<String> arraylist = new ArrayList<String>();
			int largo = 0;
			while (rs.next()) {
				largo++;
				arraylist.add(rs.getString("FECHA"));
				System.out.println(rs.getString("FECHA"));
			}
			
			arr = new String[largo];
			int iterador = 0;
			while(arraylist.size()>0){
				arr[iterador] = (String) arraylist.get(iterador);
				arraylist.remove(iterador);
				iterador++;
			}
			
		}
		return arr;
	}

	/**
	 * @author Advancing
	 * Fechas que ya están agendadas en la BD (Método para usar en Javascript)
	 */
	public ArrayList<String> FechasDeshabilitadasArrayList() throws SQLException{
		String SQL = "SELECT FECHA FROM (SELECT Fecha1 AS FECHA, COUNT(Fecha2) AS CANTIDAD FROM (  "
				+ "	 SELECT CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) AS Fecha1 "
				+ "	 , CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) + ':' +RIGHT('0' + CONVERT(VARCHAR, DATEPART(mi,  [DBO].[16_RESERVA].[16_FECHA])), 2) AS Fecha2"
				+ "	 FROM  [DBO].[16_RESERVA]) "
				+ " AS CONTADOR"
				+ " GROUP BY Fecha1) AS TABLA1"
				+ " WHERE CANTIDAD>=9";
		
		ResultSet rs = Consultar(SQL);
				
		ArrayList<String> arraylist = new ArrayList<String>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				arraylist.add(rs.getString("FECHA"));
				System.out.println(rs.getString("FECHA"));
			}		
		}
		return arraylist;
	}
	
	/**
	 * @author Advancing
	 * Dias que ya están agendados
	 */
	public ArrayList<ArrayList<String>> getDias() throws SQLException{
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		String SQL = "SELECT CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) AS DIA,"
				+ "		  CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) AS HORA, [DBO].[16_RESERVA].[16_FECHA]"
				+ "		  FROM  [DBO].[16_RESERVA]";
		
		ResultSet rs = Consultar(SQL);
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(rs.getString("DIA"));
				arr.add(this.getHoras(rs.getString("Dia")));
				
				array.add(arr);
			}
		}
				
		return array;
	}

	/**
	 * @author Advancing
	 * @param String Dia que indica el día al cual se le buscarán las horas ya agendadas
	 * Dia que ya están agendados
	 */
	public String getHoras(String Dia) throws SQLException{
		String SQL = "  SELECT HORA, [16_FECHA] FROM (SELECT CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 111) AS DIA,"
				+ "  CONVERT(VARCHAR, DATEPART(hh,  [DBO].[16_RESERVA].[16_FECHA])) AS HORA, [DBO].[16_RESERVA].[16_FECHA]"
				+ "  FROM  [DBO].[16_RESERVA]) AS RESULT"
				+ "   WHERE DIA = '"+Dia+"' "
				+ "   ORDER BY [16_FECHA]";
		
		ResultSet rs = Consultar(SQL);
		
		String horas = "[";
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			if(rs.next()){
				horas = horas + rs.getString("HORA");
			}
			else{
				horas = horas + "]";
			}
			while (rs.next()) {
				horas = horas + "," + rs.getString("HORA");
			}
		}
		horas = horas+"]";
				
		return horas;
	}
	
	/**
	 * @author Advancing
	 * Recoger calendario para reservas
	 */
	public ArrayList<ArrayList<String>> getCalendario() throws SQLException{
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		
		String SQL = "SELECT (CONVERT(VARCHAR,  [DBO].[16_RESERVA].[16_FECHA], 126))  AS HORA, "
				+ "((CONVERT(VARCHAR,  DATEADD(MINUTE,59,[DBO].[16_RESERVA].[16_FECHA]), 126))) AS DIASIGUIENTE,"
				+ "[DBO].[16_RESERVA].[16_Id_Reserva], [DBO].[16_RESERVA].[15_Id_Cliente], "
				+ "[DBO].[15_Cliente].[15_Id_Cliente], [DBO].[16_RESERVA].[17_Id_Campania], "
				+ "[DBO].[17_Campania].[17_Id_Campania],"
				+ "[DBO].[15_Cliente].[15_Nombre] + ' '+  [DBO].[15_Cliente].[15_Apellido_Pat] "
				//+ "[DBO].[15_Cliente].[15_Mail] + ' - ' + "
				//+ "CONVERT(VARCHAR,[DBO].[15_Cliente].[15_Rut]
				+ "  + ' - ' + [DBO].[17_Campania].[17_Nombre] AS NOMBRE, "
				+ "[DBO].[16_RESERVA].[16_Pre_Reserva] AS PRERESERVA "
				+ "FROM  [DBO].[16_RESERVA],  [DBO].[15_Cliente] , [DBO].[17_Campania] "
				+ "WHERE [DBO].[16_RESERVA].[15_Id_Cliente] = [DBO].[15_Cliente].[15_Id_Cliente] "
				+ "AND [DBO].[16_RESERVA].[17_Id_Campania] = [DBO].[17_Campania].[17_Id_Campania]";
		
		Connection CN = getConexion();
		ResultSet rs = null;
		Statement Sentencia = null;
		try {
			Sentencia = CN.createStatement();
			rs = Sentencia.executeQuery(SQL);
		} catch (SQLException e) {

		}
		
		int cont= 0;
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<String> arr = new ArrayList<String>();
				arr.add("['"+rs.getString("NOMBRE")+"']");
				arr.add("['"+rs.getString("HORA")+"']");
				arr.add("['"+rs.getString("DIASIGUIENTE")+"']");
				arr.add("['"+rs.getString("PRERESERVA")+"']");
				
				array.add(cont, arr);
				cont++;
			}
		}
		
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla trabajador
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de trabajadores con nombres
	 */
	public ArrayList<ArrayList<Object>> getTrabajadoresSinId(String Columna, String Valor, String Tipo) throws SQLException{
		String SQL = "";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT [04_Id_Trabajador]"
					+ "      ,[04_Rut]"
					+ "      ,[04_Nombre]"
					+ "      ,[04_Apellido_Pat]"
					+ "      ,[04_Apellido_Mat]"
					+ "      ,[04_Contrasenia]"
					+ "      ,[04_EsAdmin]"
					+ "      ,[19_Id_Cargo]"
					+ "      ,[dbo].[04_Trabajador].[11_Id_Estado], [dbo].[11_Estado].[11_Id_Estado], [dbo].[11_Estado].[11_Estado]"
					+ "      ,[04_Email]"
					+ "  FROM [dbo].[04_Trabajador], [dbo].[11_Estado]"
					+ "  WHERE [dbo].[04_Trabajador].[11_Id_Estado] = [dbo].[11_Estado].[11_Id_Estado] AND [dbo].["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
		}else{
			SQL = "SELECT [04_Id_Trabajador]"
					+ "      ,[04_Rut]"
					+ "      ,[04_Nombre]"
					+ "      ,[04_Apellido_Pat]"
					+ "      ,[04_Apellido_Mat]"
					+ "      ,[04_Contrasenia]"
					+ "      ,[04_EsAdmin]"
					+ "      ,[19_Id_Cargo]"
					+ "      ,[dbo].[04_Trabajador].[11_Id_Estado], [dbo].[11_Estado].[11_Id_Estado], [dbo].[11_Estado].[11_Estado]"
					+ "      ,[04_Email]"
					+ "  FROM [dbo].[04_Trabajador], [dbo].[11_Estado]"
					+ "  WHERE [dbo].[04_Trabajador].[11_Id_Estado] = [dbo].[11_Estado].[11_Id_Estado]";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			ArrayList<Object> min = new ArrayList<Object>();
			Trabajador cc = new Trabajador ();
			cc.setId_Trabajador(rs.getInt("04_Id_Trabajador"));
			cc.setRut(rs.getInt("04_Rut"));
			cc.setNombre(rs.getString("04_NOMBRE"));
			cc.setApellido_Pat(rs.getString("04_APELLIDO_PAT"));
			cc.setApellido_Mat(rs.getString("04_APELLIDO_MAT"));
			cc.setEsAdmin(rs.getInt("04_EsAdmin"));
			cc.setId_Estado(rs.getInt("11_Id_Estado"));
			cc.setEmail(rs.getString("04_Email"));
			
			//ATRIBUTOS DE TABLA RESERVA
			min.add(cc);
			min.add(rs.getString("11_Estado"));
			//ATRIBUTOS DE TABLA COMUNA Y CIUDAD
			array.add(min);
		}
		return array;		
	}

	/**
	 * @author Advancing
	 * @param int Columna que entrega el nombre de una columna de la tabla trabajador
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Obtención de trabajadores con un filtro
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Trabajador> getTrabajadoresFiltro(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT * FROM [DBO].[04_Trabajador] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT * FROM [DBO].[04_Trabajador]";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Trabajador> array = new ArrayList<Trabajador>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			//Crear ID COMUNA Y DE CIUDAD
			Trabajador cc = new Trabajador ();
			cc.setId_Trabajador(rs.getInt("04_Id_Trabajador"));
			cc.setRut(rs.getInt("04_Rut"));
			cc.setNombre(rs.getString("04_NOMBRE"));
			cc.setApellido_Pat(rs.getString("04_APELLIDO_PAT"));
			cc.setApellido_Mat(rs.getString("04_APELLIDO_MAT"));
			cc.setEsAdmin(rs.getInt("04_EsAdmin"));
			cc.setId_Estado(rs.getInt("11_Id_Estado"));
			cc.setEmail(rs.getString("04_Email"));
			
			array.add(cc);
		}
		return array;
	}

	/**
	 * @author Advancing
	 * @param SesionAuxiliar aux que se ingresará a la BD
	 * Inserción de una sesión auxiliar a la BD 
	 */
	public int IngresarSesionAuxiliar(SesionAuxiliar aux) throws SQLException {
		
		this.EliminarSesionAuxiliar(aux.getId_Reserva_Asoc());
		
		System.out.println("Entregadas: "+aux.isEntregadas()); //////// BANDERA
		
		int bool1 = (aux.isAsistio())?1:0;
		int bool2 = (aux.isEntregadas())?1:0;
		int bool3 = (aux.isFotos_Seleccionadas())?1:0;
		int bool4 = (aux.isLista_Para_Entregar())?1:0;
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
	
		String Fecha1 = "";
		if(aux.getFecha_Entrega()==null)
			Fecha1 = "null";
		else
			Fecha1 = "'"+formateador.format(aux.getFecha_Entrega())+"'";
			
		
		String Fecha2 = "";
		if(aux.getFecha_Envio_Imprimir()==null)
			Fecha2 = "null";
		else
			Fecha2 = "'"+formateador.format(aux.getFecha_Envio_Imprimir())+"'";
		
		
		String Fecha3 = "";
		if(aux.getFecha_Retiro()==null)
			Fecha3 = "null";
		else
			Fecha3 = "'"+formateador.format(aux.getFecha_Retiro())+"'";
		
		
		String Fecha4 = "";
		if(aux.getFecha_Sesion()==null)
			Fecha4 = "null";
		else
			Fecha4 = "'"+formateador.format(aux.getFecha_Sesion())+"'";
		
		String Insert = "INSERT INTO [dbo].[35_Auxiliar]"
				+ "([35_Asistio]"
				+ ",[35_Numero_Ticket]"
				+ ",[35_Valor_Por_Cobrar]"
				+ ",[35_CD]"
				+ ",[35_Extras]"
				+ ",[35_Persona_Adicional]"
				+ ",[35_Recargo_Por_Reagendar]"
				+ ",[35_Monto_Extras]"
				+ ",[35_Fotografo]"
				+ ",[35_Fotos_Seleccionadas]"
				+ ",[35_Fecha_Entrega]"
				+ ",[35_Entregadas]"
				+ ",[35_Fecha_Envio_Imprimir]"
				+ ",[35_Monto_Impresion]"
				+ ",[35_Numero_Factura]"
				+ ",[16_Id_Reserva]"
				+ ",[35_Cant_10x15]"
				+ ",[35_Cant_15x21]"
				+ ",[35_Cant_20x30]"
				+ ",[35_Cant_30x40]"
				+ ",[35_Cont_Fecha_Entrega]"
				+ ",[35_Fecha_Retiro]"
				+ ",[35_Nombre_Retira]"
				+ ",[35_Lista_Para_Entregar]"
				+ ",[35_Fecha_Sesion])"
				+ " VALUES"
				+ "	("+bool1+""
				+ ","+aux.getNumero_Ticket()+""
				+ ","+aux.getValor_Por_Cobrar()+""
				+ ","+aux.getCD()+""
				+ ","+aux.getExtras()+""
				+ ","+aux.getPersona_Adicional()+""
				+ ","+aux.getRecargo_Por_Reagendar()+""
				+ ","+aux.getMonto_Extras()+""
				+ ",'"+aux.getFotografo()+"'"
				+ ","+bool3+""
				+ ","+Fecha1+""
				+ ","+bool2+""
				+ ","+Fecha2+""
				+ ","+aux.getMonto_Impresion()+""
				+ ",'"+aux.getNumero_Factura()+"'"
				+ ","+aux.getId_Reserva_Asoc()+""
				+ ","+aux.getCant_10x15()+""
				+ ","+aux.getCant_15x21()+""
				+ ","+aux.getCant_20x30()+""
				+ ","+aux.getCant_30x40()+""
				+ ","+aux.getCont_Fecha_Entrega()+""
				+ ","+Fecha3+""
				+ ",'"+aux.getNombre_Retira()+"'"
				+ ","+bool4+""
				+ ","+Fecha4+");";
		
		System.out.println(Insert);
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(Insert);
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	
	
	/**
	 * @author Advancing
	 * @param int Id_Reserva que corresponde a un número de 
	 * 		la reserva a la que se le quiere buscar la sesión
	 * Traer una sesión de la base de datos 
	 * @throws java.text.ParseException 
	 * @throws SQLException 
	 */
	public SesionAuxiliar getSesionAuxiliar(int Id_Reserva) throws SQLException, java.text.ParseException{
		
		String SQL = "";
		
		SQL = "SELECT [35_Id_Auxiliar]"
				+ "			      ,[35_Asistio]"
				+ "			      ,[35_Numero_Ticket]"
				+ "			      ,[35_Valor_Por_Cobrar]"
				+ "			      ,[35_CD]"
				+ "			      ,[35_Extras]"
				+ "			      ,[35_Persona_Adicional]"
				+ "			      ,[35_Recargo_Por_Reagendar]"
				+ "			      ,[35_Monto_Extras]"
				+ "			      ,[35_Fotografo]"
				+ "			      ,[35_Fotos_Seleccionadas]"
				+ "			      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Entrega], 111) AS [35_Fecha_Entrega]"
				+ "			      ,[35_Entregadas]"
				+ "			      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Envio_Imprimir], 111) AS [35_Fecha_Envio_Imprimir]"
				+ "			      ,[35_Monto_Impresion]"
				+ "			      ,[35_Numero_Factura]"
				+ "			      ,[16_Id_Reserva]"
				+ "			      ,[35_Cant_10x15]"
				+ "			      ,[35_Cant_15x21]"
				+ "			      ,[35_Cant_20x30]"
				+ "			      ,[35_Cant_30x40]"
				+ "				  ,[35_Cont_Fecha_Entrega]"
				+ "			      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Retiro], 111) AS [35_Fecha_Retiro]"
				+ "			      ,[35_Nombre_Retira]"
				+ "			      ,[35_Lista_Para_Entregar]"
				+ "			      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Sesion], 111) AS [35_Fecha_Sesion]"
				+ "			  FROM [dbo].[35_Auxiliar]"
				+ "				WHERE [16_Id_Reserva] = "+Id_Reserva+";";
				
		ResultSet rs = Consultar(SQL);
		
		SesionAuxiliar sa = new SesionAuxiliar();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			sa.setId_Auxiliar(rs.getInt("35_Id_Auxiliar"));
			sa.setId_Reserva_Asoc(rs.getInt("16_Id_Reserva"));
			//Boleano
			if(rs.getBoolean("35_Asistio")){
				sa.setAsistio(true);
			}else{
				sa.setAsistio(false);
			}
			sa.setNumero_Ticket(rs.getInt("35_Numero_Ticket"));
			sa.setValor_Por_Cobrar(rs.getInt("35_Valor_Por_Cobrar"));
			sa.setCD(rs.getInt("35_CD"));
			sa.setExtras(rs.getInt("35_Extras"));
			sa.setPersona_Adicional(rs.getInt("35_Persona_Adicional"));
			sa.setRecargo_Por_Reagendar(rs.getInt("35_Recargo_Por_Reagendar"));
			sa.setMonto_Extras(rs.getInt("35_Monto_Extras"));
			sa.setFotografo(rs.getString("35_Fotografo"));
			
			//Boleano
			if(rs.getBoolean("35_Fotos_Seleccionadas")){
				sa.setFotos_Seleccionadas(true);
			}else{
				sa.setFotos_Seleccionadas(false);
			}
			
			System.out.println("Fecha entrega: " +rs.getString("35_Fecha_Entrega"));
			if(rs.getString("35_Fecha_Entrega")!=null && !((rs.getString("35_Fecha_Entrega")).equals(""))){
				sa.setFecha_Entrega(sdf.parse(rs.getString("35_Fecha_Entrega")));
			}else{
				sa.setFecha_Entrega(null);
			}
			
			//Boleano
			if(rs.getBoolean("35_Entregadas")){
				sa.setEntregadas(true);
				
			}else{
				sa.setEntregadas(false);
			}
			
			if(rs.getString("35_Fecha_Envio_Imprimir")!=null && !((rs.getString("35_Fecha_Envio_Imprimir")).equals(""))){
				sa.setFecha_Envio_Imprimir(sdf.parse(rs.getString("35_Fecha_Envio_Imprimir")));
			}else{
				sa.setFecha_Envio_Imprimir(null);
			}
			
			sa.setMonto_Impresion(rs.getInt("35_Monto_Impresion"));
			sa.setNumero_Factura(rs.getString("35_Numero_Factura"));
			sa.setCant_10x15(rs.getInt("35_Cant_10x15"));
			sa.setCant_15x21(rs.getInt("35_Cant_15x21"));
			sa.setCant_20x30(rs.getInt("35_Cant_20x30"));
			sa.setCant_30x40(rs.getInt("35_Cant_30x40"));
			sa.setCont_Fecha_Entrega(rs.getInt("35_Cont_Fecha_Entrega"));
			if(rs.getString("35_Fecha_Retiro")!=null && !(rs.getString("35_Fecha_Retiro").equals(""))){
				sa.setFecha_Retiro(sdf.parse(rs.getString("35_Fecha_Retiro")));
			}else{
				System.out.println("Cae en el null");
			}
			sa.setNombre_Retira(rs.getString("35_Nombre_Retira"));
			
			//Boleano
			if(rs.getBoolean("35_Lista_Para_Entregar")){
				sa.setLista_Para_Entregar(true);
			}else{
				sa.setLista_Para_Entregar(false);
			}
			if(rs.getString("35_Fecha_Sesion")!=null && !(rs.getString("35_Fecha_Sesion").equals(""))){
				sa.setFecha_Sesion(sdf.parse(rs.getString("35_Fecha_Sesion")));
			}else{
				System.out.println("Cae en el null");
			}
			
			return sa;
		}
		return null;		
	}
	
	/**
	 * @author Advancing
	 * Traer sesiones de la base de datos 
	 * @throws java.text.ParseException 
	 * @throws SQLException 
	 */
	public ArrayList<SesionAuxiliar> getSesionesAuxiliar() throws SQLException, java.text.ParseException{
		
		String SQL = "";
		
		SQL = "SELECT [35_Id_Auxiliar]"
				+ "			      ,[35_Asistio]"
				+ "			      ,[35_Numero_Ticket]"
				+ "			      ,[35_Valor_Por_Cobrar]"
				+ "			      ,[35_CD]"
				+ "			      ,[35_Extras]"
				+ "			      ,[35_Persona_Adicional]"
				+ "			      ,[35_Recargo_Por_Reagendar]"
				+ "			      ,[35_Monto_Extras]"
				+ "			      ,[35_Fotografo]"
				+ "			      ,[35_Fotos_Seleccionadas]"
				+ "			      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Entrega], 111) AS [35_Fecha_Entrega]"
				+ "			      ,[35_Entregadas]"
				+ "			      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Envio_Imprimir], 111) AS [35_Fecha_Envio_Imprimir]"
				+ "			      ,[35_Monto_Impresion]"
				+ "			      ,[35_Numero_Factura]"
				+ "			      ,[16_Id_Reserva]"
				+ "			      ,[35_Cant_10x15]"
				+ "			      ,[35_Cant_15x21]"
				+ "			      ,[35_Cant_20x30]"
				+ "			      ,[35_Cant_30x40]"
				+ "				  ,[35_Cont_Fecha_Entrega]"
				+ "			      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Retiro], 111) AS [35_Fecha_Retiro]"
				+ "			      ,[35_Nombre_Retira]"
				+ "			      ,[35_Lista_Para_Entregar]"
				+ "			      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Sesion], 111) AS [35_Fecha_Sesion]"
				+ "			  FROM [dbo].[35_Auxiliar];";
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<SesionAuxiliar> array = new ArrayList<SesionAuxiliar>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			SesionAuxiliar sa = new SesionAuxiliar();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			//Boleano
			if(rs.getBoolean("35_Asistio")){
				sa.setAsistio(true);
			}else{
				sa.setAsistio(false);
			}
			sa.setNumero_Ticket(rs.getInt("35_Numero_Ticket"));
			sa.setValor_Por_Cobrar(rs.getInt("35_Valor_Por_Cobrar"));
			sa.setCD(rs.getInt("35_CD"));
			sa.setExtras(rs.getInt("35_Extras"));
			sa.setPersona_Adicional(rs.getInt("35_Persona_Adicional"));
			sa.setRecargo_Por_Reagendar(rs.getInt("35_Recargo_Por_Reagendar"));
			sa.setMonto_Extras(rs.getInt("35_Monto_Extras"));
			sa.setFotografo(rs.getString("35_Fotografo"));
			
			//Boleano
			if(rs.getBoolean("35_Fotos_Seleccionadas")){
				sa.setFotos_Seleccionadas(true);
			}else{
				sa.setFotos_Seleccionadas(false);
			}
			
			System.out.println("Fecha entrega: " +rs.getString("35_Fecha_Entrega"));
			if(rs.getString("35_Fecha_Entrega")!=null && !((rs.getString("35_Fecha_Entrega")).equals(""))){
				sa.setFecha_Entrega(sdf.parse(rs.getString("35_Fecha_Entrega")));
			}else{
				sa.setFecha_Entrega(null);
			}
			
			//Boleano
			if(rs.getBoolean("35_Entregadas")){
				sa.setEntregadas(true);
				
			}else{
				sa.setEntregadas(false);
			}
			
			if(rs.getString("35_Fecha_Envio_Imprimir")!=null && !((rs.getString("35_Fecha_Envio_Imprimir")).equals(""))){
				sa.setFecha_Envio_Imprimir(sdf.parse(rs.getString("35_Fecha_Envio_Imprimir")));
			}else{
				sa.setFecha_Envio_Imprimir(null);
			}
			
			sa.setMonto_Impresion(rs.getInt("35_Monto_Impresion"));
			sa.setNumero_Factura(rs.getString("35_Numero_Factura"));
			sa.setCant_10x15(rs.getInt("35_Cant_10x15"));
			sa.setCant_15x21(rs.getInt("35_Cant_15x21"));
			sa.setCant_20x30(rs.getInt("35_Cant_20x30"));
			sa.setCant_30x40(rs.getInt("35_Cant_30x40"));
			sa.setCont_Fecha_Entrega(rs.getInt("35_Cont_Fecha_Entrega"));
			if(rs.getString("35_Fecha_Retiro")!=null && !(rs.getString("35_Fecha_Retiro").equals(""))){
				sa.setFecha_Retiro(sdf.parse(rs.getString("35_Fecha_Retiro")));
			}else{
				System.out.println("Cae en el null");
			}
			sa.setNombre_Retira(rs.getString("35_Nombre_Retira"));
			
			//Boleano
			if(rs.getBoolean("35_Lista_Para_Entregar")){
				sa.setLista_Para_Entregar(true);
			}else{
				sa.setLista_Para_Entregar(false);
			}
			if(rs.getString("35_Fecha_Sesion")!=null && !(rs.getString("35_Fecha_Sesion").equals(""))){
				sa.setFecha_Sesion(sdf.parse(rs.getString("35_Fecha_Sesion")));
			}else{
				System.out.println("Cae en el null");
			}
			
			array.add(sa);
		}
		return array;		
	}

	/**
	 * @author Advancing
	 * Traer sesiones de la base de datos sin ID
	 * @throws java.text.ParseException 
	 * @throws SQLException 
	 */
	public ArrayList<ArrayList<Object>> getSesionesSinId() throws SQLException, java.text.ParseException{
		
		String SQL = "";
		
		SQL = "SELECT DISTINCT [35_Id_Auxiliar]"
				+ "      ,[35_Asistio]"
				+ "      ,[35_Numero_Ticket]"
				+ "      ,[35_Valor_Por_Cobrar]"
				+ "      ,[35_CD]"
				+ "      ,[35_Extras]"
				+ "      ,[35_Persona_Adicional]"
				+ "      ,[35_Recargo_Por_Reagendar]"
				+ "      ,[35_Monto_Extras]"
				+ "      ,[35_Fotografo]"
				+ "      ,[35_Fotos_Seleccionadas]"
				+ "      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Entrega], 111) AS [35_Fecha_Entrega]"
				+ "      ,[35_Entregadas]"
				+ "      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Envio_Imprimir], 111) AS [35_Fecha_Envio_Imprimir]"
				+ "      ,[35_Monto_Impresion]"
				+ "      ,[35_Numero_Factura]"
				+ "      ,[dbo].[16_Reserva].[16_Id_Reserva]"
				+ "	     ,[dbo].[35_Auxiliar].[16_Id_Reserva]"
				+ "      ,[35_Cant_10x15]"
				+ "      ,[35_Cant_15x21]"
				+ "      ,[35_Cant_20x30]"
				+ "      ,[35_Cant_30x40]"
				+ "      ,[35_Cont_Fecha_Entrega]"
				+ "      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Retiro], 111) AS [35_Fecha_Retiro]"
				+ "      ,[35_Nombre_Retira]"
				+ "      ,[35_Lista_Para_Entregar]"
				+ "      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Sesion], 111) AS [35_Fecha_Sesion]"
				+ "	     ,[dbo].[16_Reserva].[15_Id_Cliente]"
				+ "	     ,[dbo].[15_Cliente].[15_Id_Cliente]"
				+ "	     ,[dbo].[15_Cliente].[15_Mail]"
				+ "	     ,[dbo].[15_Cliente].[15_Nombre]"
				+ "      ,[dbo].[15_Cliente].[15_Apellido_Pat]"
				+ "  FROM [dbo].[35_Auxiliar], [dbo].[16_Reserva], [dbo].[15_Cliente]"
				+ "  WHERE [dbo].[16_Reserva].[16_Id_Reserva] = [dbo].[35_Auxiliar].[16_Id_Reserva]"
				+ "  AND [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente];";
				
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			ArrayList<Object> min = new ArrayList<Object>();
			
			SesionAuxiliar sa = new SesionAuxiliar();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			sa.setId_Auxiliar(rs.getInt("35_Id_Auxiliar"));
			sa.setId_Reserva_Asoc(rs.getInt("16_Id_Reserva"));
			//Boleano
			if(rs.getBoolean("35_Asistio")){
				sa.setAsistio(true);
			}else{
				sa.setAsistio(false);
			}
			sa.setNumero_Ticket(rs.getInt("35_Numero_Ticket"));
			sa.setValor_Por_Cobrar(rs.getInt("35_Valor_Por_Cobrar"));
			sa.setCD(rs.getInt("35_CD"));
			sa.setExtras(rs.getInt("35_Extras"));
			sa.setPersona_Adicional(rs.getInt("35_Persona_Adicional"));
			sa.setRecargo_Por_Reagendar(rs.getInt("35_Recargo_Por_Reagendar"));
			sa.setMonto_Extras(rs.getInt("35_Monto_Extras"));
			sa.setFotografo(rs.getString("35_Fotografo"));
			
			//Boleano
			if(rs.getBoolean("35_Fotos_Seleccionadas")){
				sa.setFotos_Seleccionadas(true);
			}else{
				sa.setFotos_Seleccionadas(false);
			}
			
			System.out.println("Fecha entrega: " +rs.getString("35_Fecha_Entrega"));
			if(rs.getString("35_Fecha_Entrega")!=null && !((rs.getString("35_Fecha_Entrega")).equals(""))){
				sa.setFecha_Entrega(sdf.parse(rs.getString("35_Fecha_Entrega")));
			}else{
				sa.setFecha_Entrega(null);
			}
			
			//Boleano
			if(rs.getBoolean("35_Entregadas")){
				sa.setEntregadas(true);
				
			}else{
				sa.setEntregadas(false);
			}
			
			if(rs.getString("35_Fecha_Envio_Imprimir")!=null && !((rs.getString("35_Fecha_Envio_Imprimir")).equals(""))){
				sa.setFecha_Envio_Imprimir(sdf.parse(rs.getString("35_Fecha_Envio_Imprimir")));
			}else{
				sa.setFecha_Envio_Imprimir(null);
			}
			
			sa.setMonto_Impresion(rs.getInt("35_Monto_Impresion"));
			sa.setNumero_Factura(rs.getString("35_Numero_Factura"));
			sa.setCant_10x15(rs.getInt("35_Cant_10x15"));
			sa.setCant_15x21(rs.getInt("35_Cant_15x21"));
			sa.setCant_20x30(rs.getInt("35_Cant_20x30"));
			sa.setCant_30x40(rs.getInt("35_Cant_30x40"));
			sa.setCont_Fecha_Entrega(rs.getInt("35_Cont_Fecha_Entrega"));
			if(rs.getString("35_Fecha_Retiro")!=null && !(rs.getString("35_Fecha_Retiro").equals(""))){
				sa.setFecha_Retiro(sdf.parse(rs.getString("35_Fecha_Retiro")));
			}else{
				System.out.println("Cae en el null");
			}
			sa.setNombre_Retira(rs.getString("35_Nombre_Retira"));
			
			//Boleano
			if(rs.getBoolean("35_Lista_Para_Entregar")){
				sa.setLista_Para_Entregar(true);
			}else{
				sa.setLista_Para_Entregar(false);
			}
			if(rs.getString("35_Fecha_Sesion")!=null && !(rs.getString("35_Fecha_Sesion").equals(""))){
				sa.setFecha_Sesion(sdf.parse(rs.getString("35_Fecha_Sesion")));
			}else{
				System.out.println("Cae en el null");
			}
			
			min.add(sa);
			min.add(rs.getInt("15_Id_Cliente"));
			min.add(rs.getString("15_Mail"));
			min.add(rs.getString("15_Nombre"));
			min.add(rs.getString("15_Apellido_Pat"));
			
			array.add(min);
		}
		return array;		
	}
	
	public ArrayList<ArrayList<Object>> getSesionesSinIdLike(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		
		String SQL = "";
		
		String Excluyente ="";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			Excluyente = " AND ["+Columna+"] LIKE ";
			if(Tipo.equals("String")){
				Excluyente = Excluyente+ "'%"+Valor+"%'";
			}if(Tipo.equals("Int")){
				Excluyente = Excluyente + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
		}
		
		SQL = "SELECT DISTINCT [35_Id_Auxiliar]"
				+ "      ,[35_Asistio]"
				+ "      ,[35_Numero_Ticket]"
				+ "      ,[35_Valor_Por_Cobrar]"
				+ "      ,[35_CD]"
				+ "      ,[35_Extras]"
				+ "      ,[35_Persona_Adicional]"
				+ "      ,[35_Recargo_Por_Reagendar]"
				+ "      ,[35_Monto_Extras]"
				+ "      ,[35_Fotografo]"
				+ "      ,[35_Fotos_Seleccionadas]"
				+ "      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Entrega], 111) AS [35_Fecha_Entrega]"
				+ "      ,[35_Entregadas]"
				+ "      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Envio_Imprimir], 111) AS [35_Fecha_Envio_Imprimir]"
				+ "      ,[35_Monto_Impresion]"
				+ "      ,[35_Numero_Factura]"
				+ "      ,[dbo].[16_Reserva].[16_Id_Reserva]"
				+ "	     ,[dbo].[35_Auxiliar].[16_Id_Reserva]"
				+ "      ,[35_Cant_10x15]"
				+ "      ,[35_Cant_15x21]"
				+ "      ,[35_Cant_20x30]"
				+ "      ,[35_Cant_30x40]"
				+ "      ,[35_Cont_Fecha_Entrega]"
				+ "      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Retiro], 111) AS [35_Fecha_Retiro]"
				+ "      ,[35_Nombre_Retira]"
				+ "      ,[35_Lista_Para_Entregar]"
				+ "      ,CONVERT(VARCHAR,  [dbo].[35_Auxiliar].[35_Fecha_Sesion], 111) AS [35_Fecha_Sesion]"
				+ "	     ,[dbo].[16_Reserva].[15_Id_Cliente]"
				+ "	     ,[dbo].[15_Cliente].[15_Id_Cliente]"
				+ "	     ,[dbo].[15_Cliente].[15_Mail]"
				+ "	     ,[dbo].[15_Cliente].[15_Nombre]"
				+ "      ,[dbo].[15_Cliente].[15_Apellido_Pat]"
				+ "  FROM [dbo].[35_Auxiliar], [dbo].[16_Reserva], [dbo].[15_Cliente]"
				+ "  WHERE [dbo].[16_Reserva].[16_Id_Reserva] = [dbo].[35_Auxiliar].[16_Id_Reserva] "+Excluyente
				+ "  AND [dbo].[16_Reserva].[15_Id_Cliente] = [dbo].[15_Cliente].[15_Id_Cliente];";
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			ArrayList<Object> min = new ArrayList<Object>();
			
			SesionAuxiliar sa = new SesionAuxiliar();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			sa.setId_Auxiliar(rs.getInt("35_Id_Auxiliar"));
			sa.setId_Reserva_Asoc(rs.getInt("16_Id_Reserva"));
			//Boleano
			if(rs.getBoolean("35_Asistio")){
				sa.setAsistio(true);
			}else{
				sa.setAsistio(false);
			}
			sa.setNumero_Ticket(rs.getInt("35_Numero_Ticket"));
			sa.setValor_Por_Cobrar(rs.getInt("35_Valor_Por_Cobrar"));
			sa.setCD(rs.getInt("35_CD"));
			sa.setExtras(rs.getInt("35_Extras"));
			sa.setPersona_Adicional(rs.getInt("35_Persona_Adicional"));
			sa.setRecargo_Por_Reagendar(rs.getInt("35_Recargo_Por_Reagendar"));
			sa.setMonto_Extras(rs.getInt("35_Monto_Extras"));
			sa.setFotografo(rs.getString("35_Fotografo"));
			
			//Boleano
			if(rs.getBoolean("35_Fotos_Seleccionadas")){
				sa.setFotos_Seleccionadas(true);
			}else{
				sa.setFotos_Seleccionadas(false);
			}
			
			System.out.println("Fecha entrega: " +rs.getString("35_Fecha_Entrega"));
			if(rs.getString("35_Fecha_Entrega")!=null && !((rs.getString("35_Fecha_Entrega")).equals(""))){
				sa.setFecha_Entrega(sdf.parse(rs.getString("35_Fecha_Entrega")));
			}else{
				sa.setFecha_Entrega(null);
			}
			
			//Boleano
			if(rs.getBoolean("35_Entregadas")){
				sa.setEntregadas(true);
				
			}else{
				sa.setEntregadas(false);
			}
			
			if(rs.getString("35_Fecha_Envio_Imprimir")!=null && !((rs.getString("35_Fecha_Envio_Imprimir")).equals(""))){
				sa.setFecha_Envio_Imprimir(sdf.parse(rs.getString("35_Fecha_Envio_Imprimir")));
			}else{
				sa.setFecha_Envio_Imprimir(null);
			}
			
			sa.setMonto_Impresion(rs.getInt("35_Monto_Impresion"));
			sa.setNumero_Factura(rs.getString("35_Numero_Factura"));
			sa.setCant_10x15(rs.getInt("35_Cant_10x15"));
			sa.setCant_15x21(rs.getInt("35_Cant_15x21"));
			sa.setCant_20x30(rs.getInt("35_Cant_20x30"));
			sa.setCant_30x40(rs.getInt("35_Cant_30x40"));
			sa.setCont_Fecha_Entrega(rs.getInt("35_Cont_Fecha_Entrega"));
			if(rs.getString("35_Fecha_Retiro")!=null && !(rs.getString("35_Fecha_Retiro").equals(""))){
				sa.setFecha_Retiro(sdf.parse(rs.getString("35_Fecha_Retiro")));
			}else{
				System.out.println("Cae en el null");
			}
			sa.setNombre_Retira(rs.getString("35_Nombre_Retira"));
			
			//Boleano
			if(rs.getBoolean("35_Lista_Para_Entregar")){
				sa.setLista_Para_Entregar(true);
			}else{
				sa.setLista_Para_Entregar(false);
			}
			if(rs.getString("35_Fecha_Sesion")!=null && !(rs.getString("35_Fecha_Sesion").equals(""))){
				sa.setFecha_Sesion(sdf.parse(rs.getString("35_Fecha_Sesion")));
			}else{
				System.out.println("Cae en el null");
			}
			
			min.add(sa);
			min.add(rs.getInt("15_Id_Cliente"));
			min.add(rs.getString("15_Mail"));
			min.add(rs.getString("15_Nombre"));
			min.add(rs.getString("15_Apellido_Pat"));
			
			array.add(min);
		}
		return array;		
	}
	
	/**
	 *  @author Advancing
	 * 	@param int id que indica la ID de una reserva que posee una sesión que se eliminará
	 * 	Eliminar una Sesion Auxiliar
	 */
	public int EliminarSesionAuxiliar(int id){
		
		String SQL = "DELETE FROM [dbo].[35_Auxiliar] WHERE [dbo].[35_Auxiliar].[16_Id_Reserva] = "+id+";";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	/**
	 * @author Advancing
	 * @param Tipo_Sesion cv que se ingresará a la BD
	 * Inserción de un Tipo_Sesion a la BD 
	 */
	public int IngresarTipoSesion(Tipo_Sesion cv) throws SQLException {
				
		String Insert = "INSERT INTO [dbo].[34_Tipo_Sesion]"
				+ "	           ([34_Tipo_Sesion]"
				+ "	           ,[34_Id_Estado])"
				+ "	     VALUES"
				+ "	           ('"+cv.getTipo_Sesion()+"'"
				+ "	           ,"+cv.getId_Estado()+")";
	           
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(Insert);
			if (columnasafectadas==1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	/**
	 * @author Advancing
	 * Obtención de tipos de sesines
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Tipo_Sesion> getTiposSesionesFiltro(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";				
		
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT * FROM [DBO].[34_Tipo_Sesion] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT * FROM [DBO].[34_Tipo_Sesion];";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Tipo_Sesion> array = new ArrayList<Tipo_Sesion>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Tipo_Sesion ts = new Tipo_Sesion();
			ts.setId_Tipo_Sesion(rs.getInt("34_Id_Tipo_Sesion"));
			ts.setTipo_Sesion(rs.getString("34_Tipo_Sesion"));
			ts.setId_Estado(1);
			
			array.add(ts);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * Obtención de tipos de pago
	 * @throws java.text.ParseException 
	 */
	public ArrayList<Metodo_Pago> getMetodoPagosFiltro(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";				
		
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT * FROM [DBO].[37_Metodo_Pago] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT * FROM [DBO].[37_Metodo_Pago];";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Metodo_Pago> array = new ArrayList<Metodo_Pago>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Metodo_Pago ts = new Metodo_Pago();
			ts.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
			
			//Document doc = Jsoup.parse(rs.getString("Resenia"));
			//fila.add(doc.text());
			ts.setDescripcion(rs.getString("37_Descripcion"));
			ts.setNombre(rs.getString("37_Nombre"));
			
			array.add(ts);
		}
		return array;
	}
	
	/**
	 * @author Advancing
	 * @param String Columna que entrega el nombre de una columna de la tabla reserva
	 * @param String valor que equivale al valor de esa columna 
	 * @param String tipo que indica si se agregarán o no comillas a la entrega del valor
	 * Entrega los métodos de pagos sin el formato HTML para mostrar en la página
	 * @throws java.text.ParseException 
	 */
	
	public ArrayList<Metodo_Pago> getMetodoPagosFiltroSinHTML(String Columna, String Valor, String Tipo) throws SQLException, java.text.ParseException{
		String SQL = "";				
		
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT * FROM [DBO].[37_Metodo_Pago] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT * FROM [DBO].[37_Metodo_Pago];";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Metodo_Pago> array = new ArrayList<Metodo_Pago>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Metodo_Pago ts = new Metodo_Pago();
			ts.setId_Metodo_Pago(rs.getInt("37_Id_Metodo_Pago"));
			
			Document doc = Jsoup.parse(rs.getString("37_Descripcion"));
			ts.setDescripcion(doc.text());
			ts.setNombre(rs.getString("37_Nombre"));
			
			array.add(ts);
		}
		return array;
	}
	
	/**
	 *  @author Advancing
	 * 	@param int id que indica la ID de un canal que se eliminará
	 * 	Eliminar una Canal De Venta
	 */
	public int EliminarTipoSesion(int id){
		
		String SQL = "DELETE FROM [dbo].[34_Tipo_Sesion] WHERE [dbo].[34_Tipo_Sesion].[34_Id_Tipo_Sesion] = "+id+";";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	/**
	 *  @author Advancing
	 * 	@param int id que indica la ID de un metodo de pago que se eliminará
	 * 	Eliminar una Canal De Venta
	 */
	public int EliminarMetodoPago(int id){
		
		String SQL = "DELETE FROM [dbo].[37_Metodo_Pago] WHERE [dbo].[37_Metodo_Pago].[37_Id_Metodo_Pago] = "+id+";";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	/**
	 * @author Advancing
	 * Obtención de mensaje que va en el pie del mail
	 * @throws java.text.ParseException 
	 */
	public String getMensajeMail() throws SQLException, java.text.ParseException{
		String SQL = "";
		
		SQL = "  SELECT TOP 1 [36_Descripcion] FROM [36_Mensaje_Mail]";
		
		ResultSet rs = Consultar(SQL);
				
		if(rs==null){
			System.out.println("No hay datos");
			return "";
		}
		if(rs.next()) {
			return rs.getString("36_DESCRIPCION");
		}
		return "";
	}
	
	public String getMetodoPago(int id) throws SQLException, java.text.ParseException{
		String SQL = "";
		
		SQL = "  SELECT [37_Descripcion] FROM [37_Metodo_Pago] WHERE [37_Id_Metodo_Pago] = "+id+";";
		
		ResultSet rs = Consultar(SQL);
				
		if(rs==null){
			System.out.println("No hay datos");
			return "";
		}
		if(rs.next()) {
			return rs.getString("37_DESCRIPCION");
		}
		return "";
	}
	
	/**
	 * @author Advancing
	 * Seteo de mensaje que va en el pie del mail
	 * @throws java.text.ParseException 
	 */
	public int setMensajeMail(String mensajenuevo) throws SQLException, java.text.ParseException{
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			
			PreparedStatement p = conn.prepareStatement("UPDATE [dbo].[36_Mensaje_Mail] "
					+ " SET [36_DESCRIPCION] = ?");
					
			System.out.println(p);
			
			int i = 1;
			p.setString(i++, mensajenuevo);
			
			columnasafectadas = p.executeUpdate();
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	/**
	 * @author Advancing
	 * @param Tipo_Sesion ts que se actualizado en la BD
	 * Actualización de un tipo de sesión a la BD 
	 */
	public int ActualizarTipoSesion(Tipo_Sesion ts) throws SQLException {
				
		String SQL = "UPDATE [dbo].[34_Tipo_Sesion]"
				+ "	SET [34_Tipo_Sesion] = '"+ts.getTipo_Sesion()+"'"
				+ ",[34_Id_Estado] = "+ts.getId_Estado()+""
				+ "WHERE [34_Id_Tipo_Sesion] = "+ts.getId_Tipo_Sesion()+"";
		
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}	

	/**
	 * @author Advancing
	 * @param Tipo_Sesion Obtiene cupones deshabilitados 
	 */
	public String CuponesDeshabilitados() throws SQLException{
		String SQL = "SELECT [16_Reserva].[16_Codigo] FROM [16_Reserva] WHERE [16_Reserva].[16_Validado] = 1";
		
		ResultSet rs = Consultar(SQL);
		
		String cupones = "";
				
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			if(rs.next()){
				cupones = cupones + "['"+rs.getString("16_Codigo")+"'";
			}
			while (rs.next()) {
				cupones = cupones + ",'"+rs.getString("16_Codigo")+"'";
			}
			cupones = cupones + "]";
			
		}
		return cupones;
	}
	
	/**
	 * @author Advancing
	 * @param NumTicket que posee el numero de ticket que se quiere almacenar, int idReserva que posee la reserva
	 * a la que se le actualizará el ticket
	 */
	public int ActualizarTicket(int NumTicket, int idReserva) throws SQLException{
		String SQL = "UPDATE [16_Reserva] SET [16_Reserva].[24_Id_Ticket] = "+NumTicket+" WHERE [16_Reserva].[16_Id_Reserva] = "+idReserva+";";
		
		ResultSet rs = Consultar(SQL);	
				
		Statement s = null;
		int columnasafectadas = 0;
		try {
			Connection conn = getConexion();
			s = conn.createStatement();
			columnasafectadas = s.executeUpdate(SQL);
			if (columnasafectadas == 1) {
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR! "+ e);
		}
		return 0;
	}
	
	public ArrayList<Vendedor> getVendedoresSinId(String Columna, String Valor, String Tipo) throws SQLException{
		String SQL = "";
		if((!Columna.equals("")) && (!Valor.equals("")) && (!Tipo.equals(""))){
			SQL = "SELECT [38_Id_Vendedor]      "
					+ "		 ,[38_Vendedor]"
					+ "      ,[38_Mail]"
					+ "      ,[38_Mail_PW]"
					+ "      ,[38_Logo_Icono]"
					+ "      ,[38_Web]"
					+ "      ,[38_BD]"
					+ "      ,[38_BD_User]"
					+ "      ,[38_BD_PW]"
					+ "      ,[38_Direccion]"
					+ "  FROM [dbo].[38_Vendedor] WHERE ["+Columna+"] = ";
			if(Tipo.equals("String")){
				SQL = SQL+ "'"+Valor+"'";
			}if(Tipo.equals("Int")){
				SQL = SQL + Valor ;
			}else{
				System.out.println("ERROR DE FORMATO");
			}
			SQL = SQL + ";";
		}else{
			SQL = "SELECT [38_Id_Vendedor]  "
					+ "		 ,[38_Vendedor]"
					+ "      ,[38_Mail]"
					+ "      ,[38_Mail_PW]"
					+ "      ,[38_Logo_Icono]"
					+ "      ,[38_Web]"
					+ "      ,[38_BD]"
					+ "      ,[38_BD_User]"
					+ "      ,[38_BD_PW]"
					+ "      ,[38_Direccion]"
					+ "  FROM [dbo].[38_Vendedor]; ";
		}
		
		ResultSet rs = Consultar(SQL);
		
		ArrayList<Vendedor> array = new ArrayList<Vendedor>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}
		while (rs.next()) {
			Vendedor cc = new Vendedor ();
			cc.setId_Vendedor(rs.getInt("38_Id_Vendedor"));
			cc.setVendedor(rs.getString("38_Vendedor"));
			cc.setMail(rs.getString("38_Mail"));
			cc.setMail_PW(rs.getString("38_Mail_PW"));
			cc.setLogo_Icono(rs.getString("38_Logo_Icono"));
			cc.setWeb(rs.getString("38_Web"));
			cc.setBD(rs.getString("38_BD"));
			cc.setBD_User(rs.getString("38_BD_User"));
			cc.setBD_PW(rs.getString("38_BD_PW"));
			cc.setDireccion(rs.getString("38_Direccion"));

			//ATRIBUTOS DE TABLA RESERVA
			array.add(cc);
			//ATRIBUTOS DE TABLA COMUNA Y CIUDAD
		}
		return array;		
	}
	
	public ArrayList<ArrayList<String>> EstadisticaCampania(String Excepciones) throws SQLException{
		String SQL = " SELECT [17_Campania].[17_Id_Campania], [17_Campania].[17_Nombre],"
				+ " [17_Campania].[17_Precio],  COUNT(*) AS [Total], "
				+ " [14_Canal_Venta].[14_Id_Canal_Venta], [14_Canal_Venta].[14_Canal]"
				+ " FROM [16_Reserva]"
				+ " INNER JOIN [17_Campania]"
				+ " ON [16_Reserva].[17_Id_Campania] = [17_Campania].[17_Id_Campania]"
				+ " INNER JOIN [14_Canal_Venta]"
				+ " ON [17_Campania].[14_Id_Canal_Venta] = [14_Canal_Venta].[14_Id_Canal_Venta] "
				+ Excepciones
				+ " GROUP BY [17_Campania].[17_Id_Campania], [16_Reserva].[17_Id_Campania],"
				+ " [17_Campania].[17_Nombre], [14_Canal_Venta].[14_Id_Canal_Venta], [17_Campania].[17_Precio],"
				+ " [14_Canal_Venta].[14_Canal] "
				+ " ORDER BY [Total] DESC;";
		
		ResultSet rs = Consultar(SQL);
		
		System.out.println(SQL);
				
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		
		if(rs==null){
			System.out.println("No hay datos");
		}else{
			while (rs.next()) {
				ArrayList<String> fila = new ArrayList<String>();
				fila.add(rs.getString("17_Id_Campania"));
				fila.add(rs.getString("17_Nombre"));
				fila.add(rs.getString("Total"));
				fila.add(rs.getString("14_Id_Canal_Venta"));
				fila.add(rs.getString("14_Canal"));
				fila.add(rs.getString("17_Precio"));
				
				array.add(fila);
			}
		}
		return array;
	}
		
}