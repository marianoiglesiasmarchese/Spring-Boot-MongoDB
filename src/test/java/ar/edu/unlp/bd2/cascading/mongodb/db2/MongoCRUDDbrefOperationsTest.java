package ar.edu.unlp.bd2.cascading.mongodb.db2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.model.Dueño;
import ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.model.Negocio;
import ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.model.Producto;
import ar.edu.unlp.bd2.cascading.mongodb.db2.dbref.model.Distribuidor;
import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.RepositorioDeCliente;
import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model.Cliente;
import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model.Direccion;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoCRUDDbrefOperationsTest {

//	@Autowired
//	private RepositorioDeDistribuidor repositorioDeDistribuidor;

	@Autowired
	private MongoOperations mongoOperations;

	private static String distribuidorObjectId;
	
	@Before
	public void setUp() {

		System.out.println("#########################################################################");
		System.out.println("Limpio la base de datos (aunque en este caso es embebida y no seria necesario).");
		System.out.println("#########################################################################");

		mongoOperations.dropCollection(Distribuidor.class);
		mongoOperations.dropCollection(Producto.class);
		mongoOperations.dropCollection(Direccion.class);

		System.out.println("#########################################################################");
		System.out.println("Persisto un distribuidor para simular que ya se encontraba creado en la base y poder hacer la comparacion de comportamientos con el ejemplo que usa la simulacion de persistencia por alcance.");
		System.out.println("#########################################################################");
		
		Distribuidor distribuidor = new Distribuidor("nini");
		mongoOperations.save(distribuidor);
		this.distribuidorObjectId = distribuidor.getId();
		
	}

	@Test
	public void contextLoads() {

		System.out.println("#########################################################################");
		System.out.println("Recupero el distribuidor.");
		System.out.println("Imprimo el distribuidor recuperado de la base sin direccion y sin productos.");
		System.out.println("#########################################################################");

		Distribuidor distribuidor = mongoOperations.findById(distribuidorObjectId, Distribuidor.class);

		System.out.println("#########################################################################");
		System.out.println("Persisto la direccion y la añado al distribuidor."); 
		System.out.println("Es necesaria que sea persistida previo a la persistencia del distribuidor al que es seteada, ya que la direccion no cuenta con el OjectId y por lo tanto el distribuidor no tiene forma de referenciarlo.");
		System.out.println("#########################################################################");

		Direccion direccion = new Direccion("Calle Falsa 123");
		mongoOperations.save(direccion);
		distribuidor.setDireccion(direccion);

		System.out.println("#########################################################################");
		System.out.println("Imprimo el distribuidor con direccion (previamente persistida y asignada) y sin productos.");
		System.out.println("#########################################################################");
		System.out.println(distribuidor);
		
		System.out.println("#########################################################################");
		System.out.println("Persisto los productos y los añado al distribuidor."); 
		System.out.println("Es necesario que sean persistidos previo a la persistencia del distribuidor al que son seteados, ya que los productos no cuentan con los OjectId y por lo tanto el distribuidor no tiene forma de referenciarlos.");
		System.out.println("#########################################################################");

		Producto producto1 = new Producto("Pescado");
		Producto producto2 = new Producto("Arroz");
		Producto producto3 = new Producto("Fruta");
		mongoOperations.save(producto1);
		mongoOperations.save(producto2);
		mongoOperations.save(producto3);
		ArrayList<Producto> listaDeProductos = new ArrayList<Producto>();
		listaDeProductos.add(producto1);
		listaDeProductos.add(producto2);
		listaDeProductos.add(producto3);
		distribuidor.setProductos(listaDeProductos);

		System.out.println("#########################################################################");
		System.out.println("Imprimo el distribuidor con la direccion (previamente persistida y asignada) y de los productos (previamente persistidos y asignada).");
		System.out.println("#########################################################################");
		System.out.println(distribuidor);

		System.out.println("#########################################################################");
		System.out.println("Persisto el distribuidor, lo que genera la persistencia de las referencias de la direccion y de los productos.");
		System.out.println("#########################################################################");

		mongoOperations.save(distribuidor);

		System.out.println("#########################################################################");
		System.out.println("Vuelvo a recuperar el distribuidor.");
		System.out.println("Imprimo el distribuidor recuperado de la base para mostrar que se cuenta con las referencias de la direccion y los productos.");
		System.out.println("De esta forma se demuestra que es posible realizar la recuperacion de la totalidad de los datos desde sus documentos a partidr de sus referencias (ObjectId).");		
		System.out.println("#########################################################################");

		distribuidor = mongoOperations.findById(distribuidorObjectId, Distribuidor.class);

		System.out.println("#########################################################################");
		System.out.println("Modifico el nombre del primer producto de 'Pescado' a 'Café' y lo persisto el producto.");
		System.out.println("Como las entidades estan almacenadas en documentos propios y no se dispone de la persistencia por alcance, se debe persistir la propia entidad.");
		System.out.println("#########################################################################");
		
		distribuidor.getProductos().get(0).setNombre("Café");
		mongoOperations.save(distribuidor.getProductos().get(0));

		System.out.println("#########################################################################");
		System.out.println("Vuelvo a recuperar el distribuidor.");
		System.out.println("Imprimo el distribuidor recuperado de la base para mostrar que se realizó la modificacion del nombre del primer producto.");
		System.out.println("#########################################################################");
		
		distribuidor = mongoOperations.findById(this.distribuidorObjectId, Distribuidor.class);
		// TODO: faltan finds a partir del repositorio.

		System.out.println("#########################################################################");
		System.out.println("Borro el unico distribuidor creado en el documento dominio.");
		System.out.println("Como las entidades estan almacenadas en documentos propios y no se dipone de la persistencia por alcance, el distribuidor es la entidad que se borra mientras que la direccion y los productos no sufren cambios.");
		System.out.println("#########################################################################");
		
		mongoOperations.remove(distribuidor);
		
		System.out.println("#########################################################################");
		System.out.println("Recupero todos los distribuidores del documento dominio. Como se habia creado uno solo, el resultado es una coleccion vacia.");
		System.out.println("#########################################################################");
		
		List<Distribuidor> distribuidores = mongoOperations.findAll(Distribuidor.class);
			
		System.out.println("Cantidad de distribuidores recuperados: " + distribuidores.size());
		
	}

}
