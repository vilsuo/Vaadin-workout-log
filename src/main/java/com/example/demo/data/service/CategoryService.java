
package com.example.demo.data.service;

import com.example.demo.data.entity.Category;
import com.example.demo.data.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/*
TODO
- test save
*/
@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final InfoService infoService;

	public CategoryService(CategoryRepository categoryRepository,
							InfoService infoService) {
		
		this.categoryRepository = categoryRepository;
		this.infoService = infoService;
	}
	
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	public List<Category> findAll(PageRequest pageRequest) {
        Page<Category> page = categoryRepository.findAll(pageRequest);
		return page.getContent();
    }
	
	public Long count() {
		return categoryRepository.count();
	}
	
	public Optional<Category> findByNameIgnoreCase(String name) {
		return categoryRepository.findByNameIgnoreCase(name);
	}
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	@Transactional
	public void delete(Category category) {
		infoService.deleteByCategory(category);
		categoryRepository.delete(category);
	}
}
