
package com.example.demo.views.form;

import com.example.demo.data.entity.Info;
import com.vaadin.flow.component.grid.Grid;

public class InfoGrid extends Grid<Info> {
	
	public InfoGrid() {
		super(Info.class);
	
		setSizeFull();
		
		setColumns("name");
		addColumn(info -> info.getCategory().getName())
			.setHeader("Category");
		
		getColumns().forEach(column -> column.setAutoWidth(true));
	}
	
	public void updateLists() {
		
	}
}
