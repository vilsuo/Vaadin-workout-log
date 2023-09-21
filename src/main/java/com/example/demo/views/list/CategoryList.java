
package com.example.demo.views.list;

import com.example.demo.data.entity.Category;
import com.example.demo.data.service.CategoryService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Categories")
@Route(value = "categories")
public class CategoryList extends VerticalLayout {
	
	private final CategoryService categoryService;
	
	Grid<Category> grid = new Grid<>(Category.class);

	public CategoryList(CategoryService categoryService) {
	
		this.categoryService = categoryService;
		
		configureGrid();
		
		add(
			getToolBar(),
			grid
		);
		
		updateList();
	}
	
	
	public void configureGrid() {
		grid.setColumns("name");
		
		configureContextMenu();
		configureItemDetails();
		
		grid.getColumns().forEach(column -> column.setAutoWidth(true));
	}

	private void updateList() {
		grid.setItems(categoryService.findAll());
	}
	
	private Component getToolBar() {
		Button addCategoryButton = new Button(
			"Add category", e -> addCategory()
		);
		
		HorizontalLayout toolbar = new HorizontalLayout(
			addCategoryButton
		);
		toolbar.addClassName("toolbar");
		return toolbar;
	}
	
	private void addCategory() {
		grid.asSingleSelect().clear();
		Category newCategory = new Category();
		Dialog dialog = new CategoryDialog(newCategory);
		
		dialog.open();
	}
	
	private static class CategoryDialog extends Dialog {
		
		private final TextField name = new TextField("Category name");
		
		private final BeanValidationBinder<Category> binder
			= new BeanValidationBinder<>(Category.class);

		public CategoryDialog(Category category) {
			setCloseOnOutsideClick(false);
			setHeaderTitle("New category");
			
			binder.bindInstanceFields(this);
			binder.readBean(category);
			
			add(name);
			
			Button saveButton = new Button("Save", event -> {
				System.out.println("dialog saving");
			});

			Button cancelButton = new Button("Cancel", e -> {
				System.out.println("dialog closing");
				close();
			});
			
			getFooter().add(cancelButton, saveButton);
		}
		
		public static abstract class CategoryDialogEvent extends ComponentEvent<CategoryDialog> {

			private final Category category;

			protected CategoryDialogEvent(CategoryDialog source, Category category) {
				super(source, false);
				this.category = category;
			}

			public Category getContact() {
				return category;
			}
		}
	}

	private void configureContextMenu() {
		CategoryContextMenu contextMenu = new CategoryContextMenu(grid);
	}
	
	private static class CategoryContextMenu extends GridContextMenu<Category> {
		
		public CategoryContextMenu(Grid<Category> target) {
			super(target);
			
			GridMenuItem<Category> nameItem = addItem("Category name");
			nameItem.setEnabled(false);
			
			add(new Hr());

			addItem("Rename", e -> e.getItem().ifPresent(category -> {
				System.out.printf("Rename: %s%n", category.getName());
			}));
			
			addItem("Delete", e -> e.getItem().ifPresent(category -> {
				System.out.printf("Delete: %s%n", category.getName());
			}));
			
			setDynamicContentHandler(category -> {
				// Do not show context menu when header is clicked
				if (category == null) {
					return false;
				}
				nameItem.setText("Category " + category.getName());
				
				return true;
			});
		}
	}
	
	private void configureItemDetails() {
		grid.addColumn(
			new ComponentRenderer<>(category -> {
				Button btnToggle = new Button("open detail");
				
				btnToggle.addClickListener(event -> {
					grid.setDetailsVisible(
						category, !grid.isDetailsVisible(category
					));
				});
				
				return btnToggle;
			})
		);
		
		grid.setDetailsVisibleOnClick(false);
		grid.setItemDetailsRenderer(createCategoryDetailsRenderer());
	}
	
	private static ComponentRenderer<CategoryDetailsFormLayout, Category> 
			createCategoryDetailsRenderer() {
				
		return new ComponentRenderer<>(CategoryDetailsFormLayout::new,
				CategoryDetailsFormLayout::setCategory);
	}
	
	private static class CategoryDetailsFormLayout extends FormLayout {
		private final TextField nameField = new TextField("Name");

		public CategoryDetailsFormLayout() {
			nameField.setReadOnly(true);
			add(nameField);
		}

		public void setCategory(Category category) {
			nameField.setValue(category.getName());
		}
	}
}
