package ar.edu.unlp.bd2.cascading.mongodb.db2.cascading;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Solamente es utilizada para identificar los atributos que requieren cascading
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CascadeSave {
	
}
