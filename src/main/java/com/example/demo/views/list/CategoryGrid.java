
package com.example.demo.views.list;

import com.example.demo.data.entity.Category;
import com.example.demo.data.service.CategoryService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.data.renderer.ComponentRenderer;

public class CategoryGrid extends Grid<Category> {

	private final CategoryService categoryService;
	//private final InfoService infoService;

	public CategoryGrid(CategoryService categoryService) {//, InfoService infoService) {
		super(Category.class);
		
		this.categoryService = categoryService;
		//this.infoService = infoService;
		
		setColumns("name");
		
		configureContextMenu();
		//configureItemDetails();
		
		getColumns().forEach(column -> column.setAutoWidth(true));
		
		updateList();
	}
	
	private void updateList() {
		setItems(categoryService.findAll());
	}
	
	private void configureContextMenu() {
		CategoryContextMenu contextMenu = new CategoryContextMenu(this);
	}
	
	// TODO EDIT
	private static class CategoryContextMenu extends GridContextMenu<Category> {
		
		public CategoryContextMenu(Grid<Category> target) {
			super(target);
			
			GridMenuItem<Category> nameItem = addItem("Category name");
			nameItem.setEnabled(false);
			
			add(new Hr());

			addItem("Edit", e -> e.getItem().ifPresent(category -> {
				System.out.printf("Edit: %s%n", category.getName());
				
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
		/*
		addColumn(
			new ComponentRenderer<>(category -> {
				Button btnToggle = new Button("Open details");
				
				btnToggle.addClickListener(event -> {
					setDetailsVisible(
						category, !isDetailsVisible(category)
					);
					
					btnToggle.setText(
						String.format(
							"%s details",
							isDetailsVisible(category) ? "Hide" : "View"
						)
					);
					System.out.println("clicked");
				});
				
				return btnToggle;
			})
		);
		*/
		addComponentColumn(category -> {
			Button btnToggle = new Button("Open details");
				
			btnToggle.addClickListener(event -> {
				setDetailsVisible(
					category, !isDetailsVisible(category)
				);
					
				btnToggle.setText(
					String.format(
						"%s details",
						isDetailsVisible(category) ? "Hide" : "View"
					)
				);
				System.out.println("clicked");
			});
			
			return btnToggle;
		});
		
		setDetailsVisibleOnClick(false);
		setItemDetailsRenderer(createCategoryDetailsRenderer());
	}
	
	private ComponentRenderer<CategoryDetailsFormLayout, Category> 
			createCategoryDetailsRenderer() {
				
		return new ComponentRenderer<>(
			CategoryDetailsFormLayout::new,
			CategoryDetailsFormLayout::setDetails
		);
	}
	
	private static class CategoryDetailsFormLayout extends FormLayout {
		
		private final Grid<Category> grid = new Grid<>(Category.class);
		
		//@Autowired
		//private InfoService infoService;

		public CategoryDetailsFormLayout() {
			grid.setColumns("name");
		}

		public void setDetails(Category category) {
			grid.setItems(category);
		}
		
		/*
		private final InfoService infoService;
		private final Grid<Info> grid = new Grid<>(Info.class);
		
		//@Autowired
		//private InfoService infoService;

		public CategoryDetailsFormLayout(InfoService infoService) {
			this.infoService = infoService;
			grid.setColumns("name");
		}

		public void setDetails(Category category) {
			grid.setItems(infoService.findAllByCategory(category));
		}
		*/
	}
}
