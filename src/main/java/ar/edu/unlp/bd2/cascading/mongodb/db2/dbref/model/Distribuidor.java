package ar.edu.unlp.bd2.cascading.mongodb.db2.dbref.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.model.Producto;
import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model.Direccion;

public class Distribuidor {

	@Id
	private String id;
	
	@DBRef
	private List<Producto> productos;
	
	@DBRef
	private Direccion direccion;
	
	@Indexed(unique = true)
	private String nombre;
	
//	@CreatedDate
//	private Calendar createdDate;
//	  
//	@LastModifiedDate
//	private Calendar lastModifiedDate;
	  
	public Distribuidor() {};
	
	public Distribuidor(String nombre) {
		this.setNombre(nombre);
	}

	public String toString() {
		return String.format("%n Distribuidor[id=%s, nombre='%s' %n direccion=%s %n productos=%s]", this.getId(), this.getNombre(),
				this.getDireccion(), this.getProductos());
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	};
	
	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
