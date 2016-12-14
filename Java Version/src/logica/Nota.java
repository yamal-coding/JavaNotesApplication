package logica;

import java.util.Date;

public class Nota {
	private String id;
	private String nombre;
	private String contenido;
	
	public Nota(String nombre, String contenido){
		this.nombre = nombre;
		this.contenido = contenido;
		this.id = nombre + (new Date()).getTime();
	}
	
	public Nota(String nombre, String contenido, String id){
		this.nombre = nombre;
		this.contenido = contenido;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getContenido() {
		return contenido;
	}
	
	public String toString(){
		return nombre;
	}
}
