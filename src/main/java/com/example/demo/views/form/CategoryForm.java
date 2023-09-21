
package com.example.demo.views.form;

import com.example.demo.data.entity.Category;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class CategoryForm extends FormLayout {
	// uses bean validation defined in the class also in the UI
	Binder<Category> binder = new BeanValidationBinder<>(Category.class);
	
	// when the names are the same, we Vaaden can automatically bind
	TextField name = new TextField("Name");
	
	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button cancel = new Button("Cancel");
	
	private Category category;

	public CategoryForm() {
		binder.bindInstanceFields(this);
		
		add(
			name,
			createButtonLayout()
		);
	}
	
	private Component createButtonLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		save.addClickListener(event -> validataAndSave());
		delete.addClickListener(event -> fireEvent(new DeleteEvent(this, category)));
		cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
		
		save.addClickShortcut(Key.ENTER);
		cancel.addClickShortcut(Key.ESCAPE);
		
		return new HorizontalLayout(save, delete, cancel);
	}
	
	public void setCategory(Category categor) {
		this.category = categor;
		binder.readBean(categor);
	}

	private void validataAndSave() {
		try {
			binder.writeBean(category);
			fireEvent(new SaveEvent(this, category));
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
	
	public static abstract class CategoryFormEvent extends ComponentEvent<CategoryForm> {

		private final Category category;
		
		protected CategoryFormEvent(CategoryForm source, Category category) {
			super(source, false);
			this.category = category;
		}

		public Category getCategory() {
			return category;
		}
	}
		
	public static class SaveEvent extends CategoryFormEvent {
		SaveEvent(CategoryForm source, Category category) {
			super(source, category);
		}
	}
	
	public static class DeleteEvent extends CategoryFormEvent {
		DeleteEvent(CategoryForm source, Category category) {
			super(source, category);
		}
	}
	
	public static class CloseEvent extends CategoryFormEvent {
		CloseEvent(CategoryForm source) {
			super(source, null);
		}
	}
	
	public <T  extends ComponentEvent<?>> Registration addListener(
			Class<T> eventType, ComponentEventListener<T> listener) {
		
		return getEventBus().addListener(eventType, listener);
	}
}
