package ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "direcciones")
public class Direccion {

	@Id
	private String id;
	
	private String nombre;
	
	public Direccion() {}
	
	public Direccion(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return String.format("Direccion[id=%s, nombre='%s']", this.getId(), this.getNombre());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
		
}
