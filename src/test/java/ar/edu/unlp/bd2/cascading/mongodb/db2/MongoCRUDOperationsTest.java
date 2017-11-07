package ar.edu.unlp.bd2.cascading.mongodb.db2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.bd2.cascading.mongodb.db2.cascading.model.Producto;
import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model.Cliente;
import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model.Direccion;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoCRUDOperationsTest {
	
	@Autowired
	private MongoOperations mongoOperations;

//	private static final Log log = LogFactory.getLog(MongoCRUDOperationsTest.class);
	
	private static String clienteObjectId;

	@Before
	public void setUp() {

		System.out.println("#########################################################################");
		System.out.println("Limpio la base de datos (aunque en este caso es embebida y no seria necesario).");
		System.out.println("#########################################################################");

		mongoOperations.dropCollection(Cliente.class);
		mongoOperations.dropCollection(Producto.class);
		mongoOperations.dropCollection(Direccion.class);

		System.out.println("#########################################################################");
		System.out.println("Persisto un cliente para simular que ya se encontraba creado en la base y poder hacer la comparacion de comportamientos con el ejemplo que usa la simulacion de persistencia por alcance.");
		System.out.println("#########################################################################");
		
		Cliente cliente = new Cliente("Mariano", "Iglesias");
		mongoOperations.save(cliente);
		this.clienteObjectId = cliente.getId();
		
	}

	@Test
	public void contextLoads() {

		long startTime = System.nanoTime();				
		
		System.out.println("#########################################################################");
		System.out.println("Recupero el cliente.");
		System.out.println("Imprimo el cliente recuperado de la base sin direccion y sin productos.");
		System.out.println("#########################################################################");

		Cliente cliente = mongoOperations.findById(clienteObjectId, Cliente.class);

		/** añado una direccion al cliente */
		Direccion direccion = new Direccion("Calle Falsa 123");
		cliente.setDireccion(direccion);

		System.out.println("#########################################################################");
		System.out.println("Imprimo el cliente con direccion (sin persistir) y sin productos.");
		System.out.println("[Los datos impresos son datos en memoria]");
		System.out.println("#########################################################################");
		System.out.println(cliente);

		Producto producto1 = new Producto("Pescado");
		Producto producto2 = new Producto("Arroz");
		Producto producto3 = new Producto("Fruta");
		ArrayList<Producto> listaDeProductos = new ArrayList<Producto>();
		listaDeProductos.add(producto1);
		listaDeProductos.add(producto2);
		listaDeProductos.add(producto3);
		cliente.setProductos(listaDeProductos);

		System.out.println("#########################################################################");
		System.out.println("Imprimo el cliente con direccion (sin persistir) y con productos (sin persistir).");
		System.out.println("[Los datos impresos son datos en memoria]");
		System.out.println("#########################################################################");
		System.out.println(cliente);

		System.out.println("#########################################################################");
		System.out.println("Persisto el cliente, lo que genera la persistencia enbebida de la direccion y de los productos.");
		System.out.println("#########################################################################");

		mongoOperations.save(cliente);

		System.out.println("#########################################################################");
		System.out.println("Vuelvo a recuperar el cliente.");
		System.out.println("Imprimo el cliente recuperado de la base para mostrar que se realizó la enbebida de la direccion y los productos.");
		System.out.println("De esta forma se demuestra que es posible realizar la recuperacion de la totalidad de los datos.");
		System.out.println("De esta forma se puede observar que los objetos enbebidos no cuentan con ObjectId, ya que no se estan generando en un nuevo Documento de Mongo, sino que son parte del mismo documento de la entidad que los contiene que en este caso es el Cliente.");
		System.out.println("#########################################################################");

		cliente = mongoOperations.findById(clienteObjectId, Cliente.class);
		
		System.out.println("#########################################################################");
		System.out.println("Modifico el nombre del primer producto de 'Pescado' a 'Café' y lo persisto el cliente.");
		System.out.println("#########################################################################");
		
		cliente.getProductos().get(0).setNombre("Café");
		mongoOperations.save(cliente);

		System.out.println("#########################################################################");
		System.out.println("Vuelvo a recuperar el cliente.");
		System.out.println("Imprimo el cliente recuperado de la base para mostrar que se realizó la modificacion del nombre del primer producto.");
		System.out.println("Como los productos son embebidos, y son parte del documento de la misma entidad que lo contiene (el Cliente), al persistir el cliente los datos del producto son actualizados.");
		System.out.println("#########################################################################");
		
		cliente = mongoOperations.findById(clienteObjectId, Cliente.class);

		System.out.println("#########################################################################");
		System.out.println("Borro el unico cliente creado en el documento dominio.");
		System.out.println("Como la direccion y los productos estan enbebidos en el cliente, el borrar el cliente, estos tambien se eliminan.");
		System.out.println("#########################################################################");
		
		mongoOperations.remove(cliente);
		
		System.out.println("#########################################################################");
		System.out.println("Recupero todos los clientes del documento dominio. Como se habia creado uno solo, el resultado es una coleccion vacia.");
		System.out.println("#########################################################################");
		
		List<Cliente> clientes = mongoOperations.findAll(Cliente.class);
			
		System.out.println("Cantidad de clientes recuperados: " + clientes.size());
		
		long endTime = System.nanoTime() - startTime; 
		System.out.println("#########################################################################");
		System.out.println("Tiempo demorado en la ejecucion del Test: " + endTime + " nanosegundos" );
		System.out.println("#########################################################################");
		
	}

}
