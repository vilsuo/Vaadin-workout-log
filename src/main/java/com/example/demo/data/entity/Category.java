
package com.example.demo.data.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "exercise_category")
@NoArgsConstructor
@Getter @Setter
public class Category extends AbstractEntity {
	
	@NotBlank
	private String name;
	

	public Category(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Category={" +
				"id=" + getId() + 
				", name=" + name + 
				"}";
	}
}
