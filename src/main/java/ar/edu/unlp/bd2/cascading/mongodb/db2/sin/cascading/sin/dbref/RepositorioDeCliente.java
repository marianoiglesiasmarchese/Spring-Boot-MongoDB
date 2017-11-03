package ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model.Cliente;

/**
 * Spring Data MongoDB focuses on storing data in MongoDB. It also inherits
 * functionality from the Spring Data Commons project, such as the ability to
 * derive queries. Essentially, you don’t have to learn the query language of
 * MongoDB; you can simply write a handful of methods and the queries are
 * written for you.
 */
public interface RepositorioDeCliente extends MongoRepository<Cliente, String> {
	/**
	 * 
	 * CustomerRepository extends the MongoRepository interface and plugs in the
	 * type of values and id it works with: Customer and String. Out-of-the-box,
	 * this interface comes with many operations, including standard CRUD operations
	 * (create-read-update-delete).
	 * 
	 * You can define other queries as needed by simply declaring their method
	 * signature. In this case, you add findByFirstName, which essentially seeks
	 * documents of type Customer and finds the one that matches on firstName.
	 * 
	 * You also have findByLastName to find a list of people by last name.
	 * 
	 * In a typical Java application, you write a class that implements
	 * CustomerRepository and craft the queries yourself. What makes Spring Data
	 * MongoDB so useful is the fact that you don’t have to create this
	 * implementation. Spring Data MongoDB creates it on the fly when you run the
	 * application.*
	 */

	/**
	 * Spring Data MongoDB uses the MongoTemplate to execute the queries behind your
	 * find* methods. The basic principle is that queries are constructed for you
	 * automatically based on method names. Spring data come with many magic findBy
	 * queries :
	 * http://docs.spring.io/spring-data/data-document/docs/current/reference/html/#mongodb.repositories.queries.
	 * 
	 * The first method shows a query for all people with the given lastname. The
	 * query will be derived parsing the method name for constraints which can be
	 * concatenated with And and Or. Thus the method name will result in a query
	 * expression of{"lastname" : lastname}.
	 */
	public Cliente findByNombre(String nombre);

	public List<Cliente> findByApellido(String apellido);

}
