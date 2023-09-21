
package com.example.demo.views.event;

import com.example.demo.data.entity.AbstractEntity;
import com.example.demo.views.form.AbstractEntityForm;

public class DeleteEvent<T extends AbstractEntity> extends AbstractEntityFormEvent<T> {
	
	public DeleteEvent(AbstractEntityForm source, T entity) {
		super(source, entity);
	}
}