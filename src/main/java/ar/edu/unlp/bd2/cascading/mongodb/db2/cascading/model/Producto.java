package ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.model;

import org.springframework.data.annotation.Id;

public class Producto {

	@Id
	private String id;

	private String nombre;
	
//	@CreatedDate
//	private Calendar createdDate;
//	  
//	@LastModifiedDate
//	private Calendar lastModifiedDate;

	public Producto() {
	}

	public Producto(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		return String.format("Producto[id=%s, nombre='%s']", this.getId(), this.getNombre());
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
