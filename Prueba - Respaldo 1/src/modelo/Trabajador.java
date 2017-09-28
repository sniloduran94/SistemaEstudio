package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.SQLS_conexion;

public class Trabajador {
	private int Id_Trabajador;
	private int Rut;
	private String Nombre;
	private String Apellido_Pat;
	private String Apellido_Mat;
	private String Contrasenia;
	private int EsAdmin;
	private int Id_Cargo;
	private int Id_Estado;
	private String Email;
	
	public Trabajador() {
		// TODO Auto-generated constructor stub
	}
	
	public Trabajador(int id_Trabajador, int rut, String nombre, String apellido_Pat, String apellido_Mat,
			String contrasenia, int esAdmin, int id_Cargo, int id_Estado, String email) {
		super();
		Id_Trabajador = id_Trabajador;
		Rut = rut;
		Nombre = nombre;
		Apellido_Pat = apellido_Pat;
		Apellido_Mat = apellido_Mat;
		Contrasenia = contrasenia;
		EsAdmin = esAdmin;
		Id_Cargo = id_Cargo;
		Id_Estado = id_Estado;
		Email = email;
	}

	public int getId_Trabajador() {
		return Id_Trabajador;
	}
	public void setId_Trabajador(int id_Trabajador) {
		Id_Trabajador = id_Trabajador;
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
	public String getContrasenia() {
		return Contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		Contrasenia = contrasenia;
	}
	public int getEsAdmin() {
		return EsAdmin;
	}
	public void setEsAdmin(int esAdmin) {
		EsAdmin = esAdmin;
	}
	public int getId_Cargo() {
		return Id_Cargo;
	}
	public void setId_Cargo(int id_Cargo) {
		Id_Cargo = id_Cargo;
	}
	public int getId_Estado() {
		return Id_Estado;
	}
	public void setId_Estado(int id_Estado) {
		Id_Estado = id_Estado;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}

}
