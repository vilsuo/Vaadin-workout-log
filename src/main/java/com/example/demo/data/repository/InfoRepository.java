
package com.example.demo.data.repository;

import com.example.demo.data.entity.Category;
import com.example.demo.data.entity.Info;
import java.util.List;
import java.util.Optional;

public interface InfoRepository extends AbstractEntityRepository<Info> {
	
	public Optional<Info> findByNameIgnoreCase(String name);

	public List<Info> findAllByCategory(Category category);

	public void deleteByCategory(Category category);
}