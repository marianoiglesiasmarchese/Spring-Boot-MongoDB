package ar.edu.unlp.bd2.cascading.mongodb.db2.cascading;

import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.util.ReflectionUtils;

public class ReflectionFieldRemoveCallback extends ReflectionFieldCallback {

	public ReflectionFieldRemoveCallback(Object source, MongoOperations mongoOperations) {
		super(source, mongoOperations);
	}

	@Override
	protected void operateWithValue(final Object fieldValue) {
		FieldCallback callback = new FieldCallback();
		
		ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
		
		if (!callback.isIdFound()) {
			
			throw new MappingException("Cannot perform cascade remove on child object without id set");
			
		}
		
		this.getMongoOperations().remove(fieldValue);

	}
	
}
