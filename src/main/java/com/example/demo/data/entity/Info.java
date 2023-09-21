
package com.example.demo.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "exercise_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Info extends AbstractEntity {
	
	@NotBlank
	private String name;
	
	@NotNull
	@ManyToOne
	private Category category;
	
	@Override
	public String toString() {
		return "Info={" + 
				"id=" + getId() + 
				", name=" + name + 
				", category=" + category + 
				"}";
	}
}
