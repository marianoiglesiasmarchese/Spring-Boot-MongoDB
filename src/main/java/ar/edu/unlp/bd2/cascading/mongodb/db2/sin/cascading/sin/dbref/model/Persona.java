package ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Persona {

	@Id
	private String id;

	@Indexed(unique = true)
	private String nombre;
	private String apellido;
	private Long dni;
	
//	@CreatedDate
//	private LocalDateTime createdDate;
//	  
//	@LastModifiedDate
//	private LocalDateTime lastModifiedDate;
	
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
