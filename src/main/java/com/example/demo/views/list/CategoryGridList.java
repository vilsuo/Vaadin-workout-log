
package com.example.demo.views.list;

import com.example.demo.data.entity.Category;
import com.example.demo.data.service.CategoryService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Categories")
@Route(value = "categories")
public class CategoryGridList extends VerticalLayout {
	
	CategoryGrid categoryGrid;

	public CategoryGridList(CategoryService categoryService) {
		categoryGrid = new CategoryGrid(categoryService);
		
		add(
			getToolBar(),
			categoryGrid
		);
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
		categoryGrid.asSingleSelect().clear();
		Category newCategory = new Category();
		System.out.println("Add category");
	}
}
