package ar.edu.unlp.bd2.cascading.mongodb.db2;

import java.awt.Color;
import java.awt.SystemColor;
import java.io.UnsupportedEncodingException;
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
import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.RepositorioDeCliente;
import ar.edu.unlp.bd2.cascading.mongodb.db2.sin.cascading.sin.dbref.model.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoCRUDCustomCascadingOperationsTest {

//	@Autowired
//	private RepositorioDeNegocio repositorioDeNegocio;
	
	@Autowired
	private MongoOperations mongoOperations;

	private static String negocioObjectId;

	@Before
	public void setUp() {
	
		System.out.println("#########################################################################");
		System.out.println("Limpio la base de datos (aunque en este caso es embebida y no seria necesario).");
		System.out.println("#########################################################################");

		mongoOperations.dropCollection(Negocio.class);
		mongoOperations.dropCollection(Producto.class);
		mongoOperations.dropCollection(Dueño.class);

		System.out.println("#########################################################################");
		System.out.println("Persisto un negocio para simular que ya se encontraba creado en la base (necesario para la persistencia por alcance).");
		System.out.println("#########################################################################");

		Negocio negocio = new Negocio("pulpito");
		mongoOperations.save(negocio);
		this.negocioObjectId = negocio.getId();

	}

	@Test
	public void contextLoads() {

		System.out.println("#########################################################################");
		System.out.println("Recupero el negocio.");
		System.out.println("Imprimo el negocio recuperado de la base sin dueño y sin productos.");
		System.out.println("#########################################################################");

		Negocio negocio = mongoOperations.findById(negocioObjectId, Negocio.class);

		/** añado un dueño al negocio */
		Dueño dueño = new Dueño("Mariano", "Iglesias", "marianoiglesiasmarchese@gmail.com");
		negocio.setDueño(dueño);

		System.out.println("#########################################################################");
		System.out.println("Imprimo el negocio con dueño (sin persistir) y sin productos.");
		System.out.println("#########################################################################");
		System.out.println(negocio);

		Producto producto1 = new Producto("Pescado");
		Producto producto2 = new Producto("Arroz");
		Producto producto3 = new Producto("Fruta");
		ArrayList<Producto> listaDeProductos = new ArrayList<Producto>();
		listaDeProductos.add(producto1);
		listaDeProductos.add(producto2);
		listaDeProductos.add(producto3);
		negocio.setProductos(listaDeProductos);

		System.out.println("#########################################################################");
		System.out.println("Imprimo el negocio con dueño (sin persistir) y con productos (sin persistir).");
		System.out.println("#########################################################################");
		System.out.println(negocio);

		System.out.println("#########################################################################");
		System.out.println("Persisto el negocio, lo que genera la persistencia por alcance (cascading) del dueño y de los productos.");
		System.out.println("#########################################################################");

		mongoOperations.save(negocio);

		System.out.println("#########################################################################");
		System.out.println("Vuelvo a recuperar el negocio.");
		System.out.println("Imprimo el negocio recuperado de la base para mostrar que se realizó la persistencia por alcance del dueño y los productos.");
		System.out.println("Tambien se demuestra que es posible realizar la recuperacion de la totalidad de los datos.");
		System.out.println("#########################################################################");

		negocio = mongoOperations.findById(negocioObjectId, Negocio.class);

		// TODO 
		
		System.out.println("#########################################################################");
		System.out.println("Modifico el nombre del primer producto de 'Pescado' a 'Café' y lo persisto el negocio.");
		System.out.println("#########################################################################");
		
		negocio.getProductos().get(0).setNombre("Café");
		mongoOperations.save(negocio);

		System.out.println("#########################################################################");
		System.out.println("Vuelvo a recuperar el negocio.");
		System.out.println("Imprimo el negocio recuperado de la base para mostrar que se realizó la modificacion del nombre del primer producto debido a la persistencia por alcance.");
		System.out.println("#########################################################################");
		
		negocio = mongoOperations.findById(negocioObjectId, Negocio.class);
		// TODO: faltan finds a partir del repositorio.

		System.out.println("#########################################################################");
		System.out.println("Borro el unico negocio creado en el documento dominio.");
		System.out.println("Debido a la persistencia por alcance, la direccion y los productos tambien se eliminan.");
		System.out.println("#########################################################################");
		
		mongoOperations.remove(negocio);
		
		System.out.println("#########################################################################");
		System.out.println("Recupero todos los negocios del documento dominio. Como se habia creado uno solo, el resultado es una coleccion vacia.");
		System.out.println("#########################################################################");
		
		List<Negocio> negocios = mongoOperations.findAll(Negocio.class);
			
		System.out.println("Cantidad de clientes recuperados: " + negocios.size());
		
		
	}

}
