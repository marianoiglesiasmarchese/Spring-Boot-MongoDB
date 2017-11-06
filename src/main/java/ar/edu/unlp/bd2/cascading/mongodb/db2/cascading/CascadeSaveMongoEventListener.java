package ar.edu.unlp.bd2.cascading.mongodb.db2.cascading;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.mongodb.BasicDBObject;

@Component
public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Object> event) {
		
		Object source = event.getSource();
		
		ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {

			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

				ReflectionUtils.makeAccessible(field);

				if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {

					final Object fieldValue = field.get(source);

					if(fieldValue != null ){
						
						boolean collectionInstace = (fieldValue instanceof Collection);
						
						if(collectionInstace){
							for (Object element : (Collection)fieldValue) {
								persistValue(element);
							}
						}else {							
							persistValue(fieldValue);
						}
						
					}					

				}

			}

			private void persistValue(final Object fieldValue) {
				FieldCallback callback = new FieldCallback();
				
				ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
				
				if (!callback.isIdFound()) {
					
					throw new MappingException("Cannot perform cascade save on child object without id set");
					
				}
				
				mongoOperations.save(fieldValue);
			}

		});

	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Object> event) {
		
		Object source = mongoOperations.findById(((BasicDBObject)event.getSource()).get("_id"), event.getType());
		
		ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {

			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

				ReflectionUtils.makeAccessible(field);

				if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {

					final Object fieldValue = field.get(source);

					if(fieldValue != null ){
						
						boolean collectionInstace = (fieldValue instanceof Collection);
						
						if(collectionInstace){
							for (Object element : (Collection)fieldValue) {
								removeValue(element);
							}
						}else {							
							removeValue(fieldValue);
						}
						
					}					

				}

			}

			private void removeValue(final Object fieldValue) {
				FieldCallback callback = new FieldCallback();
				
				ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
				
				if (!callback.isIdFound()) {
					
					throw new MappingException("Cannot perform cascade save on child object without id set");
					
				}
				
				mongoOperations.remove(fieldValue);
			}

		});

	}
	
	public class FieldCallback implements ReflectionUtils.FieldCallback {
		private boolean idFound;

		@Override
		public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
			ReflectionUtils.makeAccessible(field);

			if (field.isAnnotationPresent(Id.class)) {
				idFound = true;
			}
		}

		public boolean isIdFound() {
			return idFound;
		}
	}

}
