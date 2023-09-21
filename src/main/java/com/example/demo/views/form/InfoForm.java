
package com.example.demo.views.form;

import com.example.demo.data.entity.Category;
import com.example.demo.data.entity.Info;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import java.util.List;

public class InfoForm extends FormLayout {
	// uses bean validation defined in the class also in the UI
	Binder<Info> binder = new BeanValidationBinder<>(Info.class);
	
	// when the names are the same, we Vaaden can automatically bind
	TextField name = new TextField("Name");
	
	ComboBox<Category> category = new ComboBox<>("Category");
	
	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button cancel = new Button("Cancel");
	
	private Info info;

	public InfoForm(List<Category> categories) {
		
		binder.bindInstanceFields(this);
		
		category.setItems(categories);
		category.setItemLabelGenerator(Category::getName);
		
		add(
			name,
			category,
			createButtonLayout()
		);
	}
	
	private Component createButtonLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		save.addClickListener(event -> validataAndSave());
		delete.addClickListener(event -> fireEvent(new DeleteEvent(this, info)));
		cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));
		
		save.addClickShortcut(Key.ENTER);
		cancel.addClickShortcut(Key.ESCAPE);
		
		return new HorizontalLayout(save, delete, cancel);
	}
	
	public void setInfo(Info info) {
		this.info = info;
		binder.readBean(info);
	}
	
	public void setCategories(List<Category> categories) {
		category.setItems(categories);
	}

	private void validataAndSave() {
		try {
			binder.writeBean(info);
			fireEvent(new SaveEvent(this, info));
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
	
	public static abstract class InfoFormEvent extends ComponentEvent<InfoForm> {

		private final Info info;
		
		protected InfoFormEvent(InfoForm source, Info info) {
			super(source, false);
			this.info = info;
		}

		public Info getInfo() {
			return info;
		}
	}
		
	public static class SaveEvent extends InfoFormEvent {
		SaveEvent(InfoForm source, Info info) {
			super(source, info);
		}
	}
	
	public static class DeleteEvent extends InfoFormEvent {
		DeleteEvent(InfoForm source, Info info) {
			super(source, info);
		}
	}
	
	public static class CloseEvent extends InfoFormEvent {
		CloseEvent(InfoForm source) {
			super(source, null);
		}
	}
	
	public <T  extends ComponentEvent<?>> Registration addListener(
			Class<T> eventType, ComponentEventListener<T> listener) {
		
		return getEventBus().addListener(eventType, listener);
	}	
}
