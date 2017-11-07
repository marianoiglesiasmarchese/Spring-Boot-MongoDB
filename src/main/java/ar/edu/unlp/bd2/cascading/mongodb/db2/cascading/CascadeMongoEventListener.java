package ar.edu.unlp.bd2.cascading.mongodb.db2.cascading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.mongodb.BasicDBObject;

@Component
public class CascadeMongoEventListener extends AbstractMongoEventListener<Object> {

	@Autowired
	private MongoOperations mongoOperations;
	
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Object> event) {
		
		Object source = event.getSource();
		
		ReflectionUtils.doWithFields(source.getClass(), new ReflectionFieldPersisteCallback(source, this.getMongoOperations()));
		
	}
	
	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Object> event) {
		
		Object source = this.getMongoOperations().findById(((BasicDBObject)event.getSource()).get("_id"), event.getType());
		
		ReflectionUtils.doWithFields(source.getClass(), new ReflectionFieldRemoveCallback(source, this.getMongoOperations()));
	
	}

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	public void setMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

}
