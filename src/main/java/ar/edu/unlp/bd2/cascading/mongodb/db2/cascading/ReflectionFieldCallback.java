package ar.edu.unlp.bd2.cascading.mongodb.db2.cascading;

import java.lang.reflect.Field;
import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;


public abstract class ReflectionFieldCallback implements ReflectionUtils.FieldCallback{

	private MongoOperations mongoOperations;
	
	private Object source;

	public ReflectionFieldCallback(Object source, MongoOperations mongoOperations) {
		this.setSource(source);
		this.setMongoOperations(mongoOperations);
	};
	
	@Override
	public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

		ReflectionUtils.makeAccessible(field);

		if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(Cascade.class)) {

			final Object fieldValue = field.get(this.getSource());

			if(fieldValue != null ){
				
				boolean collectionInstace = (fieldValue instanceof Collection);
				
				if(collectionInstace){
					for (Object element : (Collection)fieldValue) {
						operateWithValue(element);
					}
				}else {							
					operateWithValue(fieldValue);
				}
				
			}					

		}

	}
	
	protected abstract void operateWithValue(final Object fieldValue);

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	public void setMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}
	
}
