
package com.example.demo.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import javax.annotation.Nonnegative;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "exercise_set")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Set extends AbstractEntity {
	
	@Positive
	private Integer sets;
	
	@Positive
	private Integer repetitions;
	
	@Nonnegative
	private Double weight;
	
	@NotNull
	@ManyToOne
	private Exercise exercise;

	public Set(Integer repetitions, Double weight, Exercise exercise) {
		this.sets = 1;
		this.repetitions = repetitions;
		this.weight = weight;
		this.exercise = exercise;
	}
	
	@Override
	public String toString() {
		return "Set={" + 
				"id=" + getId() +
				", sets=" + sets + 
				", repetitions=" + repetitions + 
				", weight=" + weight +
				"}";
	}
}