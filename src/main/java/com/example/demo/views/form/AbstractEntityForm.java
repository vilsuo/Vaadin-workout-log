
package com.example.demo.views.form;

import com.example.demo.data.entity.AbstractEntity;
import com.example.demo.views.event.CloseEvent;
import com.example.demo.views.event.DeleteEvent;
import com.example.demo.views.event.SaveEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public abstract class AbstractEntityForm<T extends AbstractEntity> extends FormLayout {
	
	protected final Binder<T> binder;
	
	public AbstractEntityForm(Class<T> clazz) {
		binder = new BeanValidationBinder<>(clazz);
		
		binder.bindInstanceFields(this);
	}
	
	public void setValue(T value) {
		binder.setBean(value);
	}
	
	protected Component createButtonLayout() {
		final Button save = new Button("Save");
		final Button delete = new Button("Delete");
		final Button cancel = new Button("Cancel");
	
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		save.addClickListener(event -> validateAndSave());
		
		delete.addClickListener(
			event -> fireEvent(new DeleteEvent<T>(this, binder.getBean()))
		); 
		
		cancel.addClickListener(
			event -> fireEvent(new CloseEvent<T>(this))
		);
		
		binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
		
		save.addClickShortcut(Key.ENTER);
		cancel.addClickShortcut(Key.ESCAPE);
		
		return new HorizontalLayout(save, delete, cancel);
	}
	
	private void validateAndSave() {
		if (binder.isValid()) {
			fireEvent(new SaveEvent<T>(this, binder.getBean())); 
		}
	}
}
