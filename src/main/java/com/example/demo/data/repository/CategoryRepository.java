
package com.example.demo.data.repository;

import com.example.demo.data.entity.Category;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepository extends AbstractEntityRepository<Category> {
	
	public Optional<Category> findByNameIgnoreCase(String name);
	
	public Page<Category> findAll(Pageable pageable);  
}
