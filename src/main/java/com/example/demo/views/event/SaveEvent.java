
package com.example.demo.views.event;

import com.example.demo.data.entity.AbstractEntity;
import com.example.demo.views.form.AbstractEntityForm;

public class SaveEvent<T extends AbstractEntity> extends AbstractEntityFormEvent<T> {
	
	public SaveEvent(AbstractEntityForm source, T entity) {
		super(source, entity);
	}
}