
package com.example.demo.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Exercise extends AbstractEntity {
	
	@ManyToOne
	private Info info;
	
	@OneToMany(mappedBy = "exercise")
	private List<Set> sets;
	
	private Integer orderNumber;
	
	public Exercise(Info info, Integer orderNumber) {
		this.info = info;
		this.sets = new ArrayList<>();
		this.orderNumber = orderNumber;
	}
	
	public void addSet(Set set) {
		sets.add(set);
	}
	
	@Override
	public String toString() {
		final String setsString = String.join(", ", sets.stream().map(Set::toString).toList());
		
		return "Exercise={" + 
				"id=" + getId() + 
				", info=" + info +
				", orderNumber=" + orderNumber + 
				", sets=[" + setsString + 
				"]}";
	}
}