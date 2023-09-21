
package com.example.demo.views.event;

import com.example.demo.data.entity.AbstractEntity;
import com.example.demo.views.list.AbstractEntityForm;
import com.vaadin.flow.component.ComponentEvent;

public abstract class AbstractEntityFormEvent<T extends AbstractEntity>
		extends ComponentEvent<AbstractEntityForm<T>> {

	private final T entity;
	
	protected AbstractEntityFormEvent(AbstractEntityForm source, T entity) {
		super(source, false);
		this.entity = entity;
	}

	public T getInfo() {
		return entity;
	}
}