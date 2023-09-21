
package com.example.demo.views.event;

import com.example.demo.data.entity.AbstractEntity;
import com.example.demo.views.form.AbstractEntityForm;

public class CloseEvent<T extends AbstractEntity> extends AbstractEntityFormEvent<T> {
	
	public CloseEvent(AbstractEntityForm source) {
		super(source, null);
	}
}