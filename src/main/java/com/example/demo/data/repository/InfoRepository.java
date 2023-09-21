
package com.example.demo.data.repository;

import com.example.demo.data.entity.Category;
import com.example.demo.data.entity.Info;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InfoRepository extends AbstractEntityRepository<Info> {
	
	public Optional<Info> findByNameIgnoreCase(String name);

	public List<Info> findAllByCategory(Category category);
	
	public Page<Info> findAllByCategory(Category category, Pageable pageable);  

	public void deleteByCategory(Category category);
}