
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
		//, Sort.by(Sort.Order.asc("id"))));
        return page.getContent();
    }
	
	public Long count() {
		return categoryRepository.count();
	}
	
	public Optional<Category> findByNameIgnoreCase(String name) {
		return categoryRepository.findByNameIgnoreCase(name);
	}
	
	public Category create(String name) {
		Optional<Category> opt = findByNameIgnoreCase(name);
		
		if (opt.isEmpty()) {
			return categoryRepository.save(new Category(name));
		} else {
			throw new IllegalArgumentException(
				"Category with name '" + name + "' already exists"
			);
		}
	}
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	//public Category save(Category category) {
	//	final String name = category.getName();
	//	Optional<Category> opt = categoryRepository.findByNameIgnoreCase(name);
	//	
	//	if (opt.isEmpty() || opt.get().equals(category)) {
	//		return categoryRepository.save(category);
	//	} else {
	//		throw new IllegalArgumentException(
	//			"Category with name '" + name + "' already exists"
	//		);
	//	}
	//}
	
	public Category rename(Category category, String name) {
		final boolean wasReCased = category.getName().equalsIgnoreCase(name);
		final boolean newNameIsNotTaken = findByNameIgnoreCase(name).isEmpty();
		
		if (wasReCased || newNameIsNotTaken) {
			category.setName(name);
			return categoryRepository.save(category);
			
		} else {
			throw new IllegalArgumentException(
				"Category with name '" + name + "' already exists"
			);
		}
	}
	
	@Transactional
	public void delete(Category category) {
		infoService.deleteByCategory(category);
		categoryRepository.delete(category);
	}
}
