package ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.CascadeSave;

public class Negocio {

	@Id
	private String id;

	@Indexed(unique = true)
	private String nombre;

	@DBRef
	@CascadeSave
	public List<Producto> productos;

	@DBRef
	@CascadeSave
	public Dueño dueño;
	
//	@CreatedDate
//	private Calendar createdDate;
//	  
//	@LastModifiedDate
//	private Calendar lastModifiedDate;

	public Negocio() {
	}

	public Negocio(String nombre) {
		this.setNombre(nombre);
	}

	public String toString() {
		return String.format("%n Negocio[id=%s, nombre='%s' %n dueño=%s %n productos=%s]", this.getId(), this.getNombre(),
				this.getDueño(), this.getProductos());
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Dueño getDueño() {
		return dueño;
	}

	public void setDueño(Dueño dueño) {
		this.dueño = dueño;
	}

}
