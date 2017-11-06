package ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.model;

import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model.Persona;

public class Dueño extends Persona {

	private String email;
	
	public Dueño(){};
	
	public Dueño(String nombre, String apellido, String email){
		super(nombre,apellido);
		this.setEmail(email);
	};
	
	public String toString() {
		return String.format("Dueño[id=%s, email='%s']", this.getId(), this.getEmail());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
