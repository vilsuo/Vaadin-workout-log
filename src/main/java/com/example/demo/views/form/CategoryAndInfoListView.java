
package com.example.demo.views.form;

import com.example.demo.data.entity.Category;
import com.example.demo.data.entity.Info;
import com.example.demo.data.service.CategoryService;
import com.example.demo.data.service.InfoService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/*
TODO
- category grid details (InfoGrid)
	- not showing
	- set dataprovider for InfoGrid
*/
@PageTitle("View simple")
@Route(value = "simple")
public class CategoryAndInfoListView extends VerticalLayout {
	
	Grid<Category> categoryGrid = new Grid<>(Category.class);
	//Grid<Info> infoGrid = new Grid<>(Info.class);
	
	CategoryForm categoryForm;
	InfoForm infoForm;
	
	private final CategoryService categoryService;
	private final InfoService infoService;
	
	public CategoryAndInfoListView(CategoryService categoryService, InfoService infoService) {
		this.categoryService = categoryService;
		this.infoService = infoService;
		setSizeFull();
		
		configureGrids();
		configureForms();
		
		add(
			getToolBar(),
			getContent()
		);
		
		updateLists();
		closeEditors();
    }
	
	private void configureGrids() {
		categoryGrid.setSizeFull();
		
		categoryGrid.setColumns("name");
		categoryGrid.getColumns().forEach(column -> column.setAutoWidth(true));
		
		categoryGrid.asSingleSelect().addValueChangeListener(
			e -> editCategory(e.getValue())
		);
		
		// the new way
		categoryGrid.setItems(
			q -> categoryService.findAll(
				PageRequest.of(
					q.getPage(),
					q.getPageSize(),
					VaadinSpringDataHelpers.toSpringDataSort(q)
				)
			).stream()
		);

		categoryGrid.addColumn(
			new ComponentRenderer<>(category -> {
				Button btnToggle = new Button("open detail");

				btnToggle.addClickListener(event -> {
					categoryGrid.setDetailsVisible(
						category, !categoryGrid.isDetailsVisible(category)
					);
				});

				return btnToggle;
			})
		);
		
		categoryGrid.setDetailsVisibleOnClick(false);
		
		categoryGrid.setItemDetailsRenderer(
			new ComponentRenderer<>(
				InfoGrid::new,
				(InfoGrid grid, Category category) -> {
					grid.setItems(infoService.findAllByCategory(category));
					
					System.out.println("InfoGrid created: " + category.getName());
					
					grid.asSingleSelect().addValueChangeListener(
						e -> editInfo(e.getValue())
					);
				}
			)
		);
	}
	
	private void configureForms() {
		// category
		categoryForm = new CategoryForm();
		categoryForm.setWidth("15em");
		
		categoryForm.addListener(CategoryForm.SaveEvent.class, this::saveCategory);
		categoryForm.addListener(CategoryForm.DeleteEvent.class, this::deleteCategory);
		categoryForm.addListener(CategoryForm.CloseEvent.class, e -> closeCategoryEditor());
		
		// info
		infoForm = new InfoForm(categoryService.findAll());
		infoForm.setWidth("15em");
		
		infoForm.addListener(InfoForm.SaveEvent.class, this::saveInfo);
		infoForm.addListener(InfoForm.DeleteEvent.class, this::deleteInfo);
		infoForm.addListener(InfoForm.CloseEvent.class, e -> closeInfoEditor());
	}
	
	private Component getToolBar() {
		Button addCategoryButton = new Button("Add category");
		addCategoryButton.addClickListener(e -> addCategory());
		
		Button addInfoButton = new Button("Add info");
		addInfoButton.addClickListener(e -> addInfo());
		
		HorizontalLayout toolbar = new HorizontalLayout(
			addCategoryButton, addInfoButton
		);
		return toolbar;
	}
	
	private Component getContent() {
		VerticalLayout grids = new VerticalLayout(categoryGrid);//, infoGrid);
		VerticalLayout forms = new VerticalLayout(categoryForm, infoForm);
		
		HorizontalLayout content = new HorizontalLayout(
			grids, forms
		);
		
		// specify that the Grid should have twice the space of the form
		content.setFlexGrow(2, grids);
		content.setFlexGrow(1, forms);
		content.setSizeFull();
		
		return content;
	}
	
	private void editCategory(Category category) {
		if (category == null) {
			closeCategoryEditor();
		} else {
			categoryForm.setCategory(category);
			categoryForm.setVisible(true);
			
			closeInfoEditor();
		}
	}
	
	private void editInfo(Info info) {
		if (info == null) {
			closeInfoEditor();
		} else {
			infoForm.setInfo(info);
			infoForm.setVisible(true);
			
			closeCategoryEditor();
		}
	}
	
	private void saveCategory(CategoryForm.SaveEvent event) {
		categoryService.save(event.getCategory());
		updateLists();
		closeCategoryEditor();
	}
	
	private void saveInfo(InfoForm.SaveEvent event) {
		infoService.save(event.getInfo());
		updateLists();
		closeInfoEditor();
	}
	
	private void deleteCategory(CategoryForm.DeleteEvent event) {
		categoryService.delete(event.getCategory());
		updateLists();
		closeCategoryEditor();
	}
	
	private void deleteInfo(InfoForm.DeleteEvent event) {
		infoService.delete(event.getInfo());
		updateLists();
		closeInfoEditor();
	}
	
	private void addCategory() {
		categoryGrid.asSingleSelect().clear();
		editCategory(new Category());
	}
	
	private void addInfo() {
		//infoGrid.asSingleSelect().clear();
		editInfo(new Info());
	}
	
	private void updateLists() {
		List<Category> categories = categoryService.findAll();
		
		categoryGrid.getDataProvider().refreshAll();
		
		//infoGrid.setItems(infoService.findAll());
		infoForm.setCategories(categories);
	}
	
	private void closeEditors() {
		closeCategoryEditor();
		closeInfoEditor();
	}
	
	private void closeCategoryEditor() {
		categoryForm.setCategory(null);
		categoryForm.setVisible(false);
	}
	
	private void closeInfoEditor() {
		infoForm.setInfo(null);
		infoForm.setVisible(false);
	}
}
