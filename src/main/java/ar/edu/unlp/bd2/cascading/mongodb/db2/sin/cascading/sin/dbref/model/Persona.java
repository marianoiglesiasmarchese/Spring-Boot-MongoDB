package ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDB stores data in collections. Spring Data MongoDB will map the class
 * Customer into a collection called customer. If you want to change the name of
 * the collection, you can use Spring Data MongoDB’s @Document annotation on the
 * class.
 */
//@Document(collection = "personas")
public class Persona {

	/**
	 * id fits the standard name for a MongoDB id so it doesn’t require any special
	 * annotation to tag it for Spring Data MongoDB.
	 */
	@Id
	private String id;

	@Indexed(unique = true)
	private String nombre;
	private String apellido;
	private Long dni;
	
	public Persona(){};
	
	public Persona(String nombre, String apellido){
		this.setNombre(nombre);
		this.setApellido(apellido);
	}

	public String toString() {
		return String.format("Persona[id=%s, nombre='%s', apellid='%s']", this.getId(), this.getNombre(), this.getApellido());
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Long getDni() {
		return dni;
	}

	public void setDni(Long dni) {
		this.dni = dni;
	}
	
}
