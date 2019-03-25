package conexion;

import java.io.*;

/*
Author By: sergiougalde
Update By: RBJ
Update Date: Sept. 2015
Original URL: https://gist.github.com/sergiougalde/6247269
source: GitHub Gits
*/

public class Impresora {

	private FileWriter fw; // Variables de acceso al dispositivo
	private BufferedWriter bw;
	private PrintWriter pw;
	private String dispositivo = "";

	/* Esta funcion inicia el dispositivo donde se va a imprimir */
	public void setDispositivo(String texto) {
		dispositivo = texto;
		if (texto.trim().length() <= 0) {// Si el dispositivo viene en blanco el
											// sistema tratara de definirlo
			if (dispositivo.trim().length() <= 0) {
				dispositivo = "LTP1"; // para imprimir en windows
			}
		}
		try {
			String home = System.getProperty("user.home");
			String dir = System.getProperty("user.dir");
			
			System.out.println("C:/Users/" + System.getProperty("user.name") + "/Downloads/");
			
			UserHomeApplet dirUsu = new UserHomeApplet();
			System.out.println("Prop "+dirUsu.getUserHome());
							
			String fmt = "//192.168.100.4/TicketsEstudios"; 
			
			fw = new FileWriter(new File(fmt, this.dispositivo.trim()));
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);  
			
			//System.out.println("El nuevo dispositivo es: "+dispositivo); 
			//System.out.println("El nuevo dispositivo2 es: "+fw.toString());
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void escribir(String texto) {

		try {
			pw.println(texto);
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void cortar() {
		try {
			char[] ESC_CUT_PAPER = new char[] { 0x1B, 'm' };
			if (!this.dispositivo.trim().equals("pantalla.txt")) {
				pw.write(ESC_CUT_PAPER);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void avanza_pagina() {
		try {
			if (!this.dispositivo.trim().equals("pantalla.txt")) {
				pw.write(0x0C);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void setRojo() {
		try {
			char[] ESC_CUT_PAPER = new char[] { 0x1B, 'r', 1 };
			if (!this.dispositivo.trim().equals("pantalla.txt")) {
				pw.write(ESC_CUT_PAPER);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void setNegro() {
		try {
			char[] ESC_CUT_PAPER = new char[] { 0x1B, 'r', 0 };
			if (!this.dispositivo.trim().equals("pantalla.txt")) {
				pw.write(ESC_CUT_PAPER);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void setTipoCaracterLatino() {
		try {
			char[] ESC_CUT_PAPER = new char[] { 0x1B, 'R', 18 };
			if (!this.dispositivo.trim().equals("pantalla.txt")) {
				pw.write(ESC_CUT_PAPER);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void setFormato(int formato) {
		try {
			char[] ESC_CUT_PAPER = new char[] { 0x1B, '!', (char) formato };
			if (!this.dispositivo.trim().equals("pantalla.txt")) {
				pw.write(ESC_CUT_PAPER);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void correr(int fin) {
		try {
			int i = 0;
			for (i = 1; i <= fin; i++) {
				this.salto();
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void salto() {
		try {
			pw.println("");
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void dividir() {
		escribir("----------------");
	}

	public void cerrarDispositivo() {  
		try {
			pw.close();
			System.out.println("Pasa por aqui"); 
			System.out.println(this.dispositivo.trim());    
			if (this.dispositivo.trim().equals("pantalla.txt")) { 
				java.io.File archivo = new java.io.File("pantalla.txt");
				
				System.out.println(archivo.getAbsolutePath());  
				//java.awt.Desktop.getDesktop().open(archivo);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
}