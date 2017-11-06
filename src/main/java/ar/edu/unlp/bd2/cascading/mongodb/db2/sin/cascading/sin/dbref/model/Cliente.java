package ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model;

import java.util.List;

import ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.model.Producto;

public class Cliente extends Persona{

	private Direccion direccion;

	private List<Producto> productos;
	
	public Cliente() {
	}

	public Cliente(String nombre, String apellido) {
		super(nombre,apellido);
	}

	public String toString() {
		return String.format("%n Cliente[id=%s, nombre='%s', apellido='%s' %n direccion=%s %n productos=%s ]", this.getId(), this.getNombre(), this.getApellido(),
				this.getDireccion(), this.getProductos());
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	
	
}
