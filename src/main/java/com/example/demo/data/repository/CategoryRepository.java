
package com.example.demo.data.repository;

import com.example.demo.data.entity.Category;
import java.util.Optional;

public interface CategoryRepository extends AbstractEntityRepository<Category> {
	
	public Optional<Category> findByNameIgnoreCase(String name);
}
