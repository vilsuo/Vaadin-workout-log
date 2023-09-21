
package com.example.demo.data.service;

import com.example.demo.data.entity.Category;
import com.example.demo.data.entity.Info;
import com.example.demo.data.repository.InfoRepository;
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
public class InfoService {
	
	private final InfoRepository infoRepository;

	public InfoService(InfoRepository infoRepository) {
		this.infoRepository = infoRepository;
	}
	
	public List<Info> findAll() {
		return infoRepository.findAll();
	}
	
	public List<Info> findAllByCategory(Category category) {
		return infoRepository.findAllByCategory(category);
	}
	
	public List<Info> findAllByCategory(Category category, PageRequest pageRequest) {
        Page<Info> page = infoRepository.findAllByCategory(category, pageRequest);
		//, Sort.by(Sort.Order.asc("id"))));
        return page.getContent();
    }
	
	public Long count() {
		return infoRepository.count();
	}
	
	public Optional<Info> findByNameIgnoreCase(String name) {
		return infoRepository.findByNameIgnoreCase(name);
	}
	
	public Info create(String name, Category category) {
		Optional<Info> opt = findByNameIgnoreCase(name);
		
		if (opt.isEmpty()) {
			return infoRepository.save(new Info(name, category));
		} else {
			throw new IllegalArgumentException(
				"Info with name '" + name + "' already exists"
			);
		}
	}
	
	public Info save(Info info) {
		return infoRepository.save(info);
	}
	
	//public Info save(Info info) {
	//	final String name = info.getName();
	//	Optional<Info> opt = findByNameIgnoreCase(name);
	//	
	//	if (opt.isEmpty() || opt.get().equals(info)) {
	//		return infoRepository.save(info);
	//	} else {
	//		throw new IllegalArgumentException(
	//			"Info with name '" + name + "' already exists"
	//		);
	//	}
	//}
	
	public Info rename(Info info, String name) {
		final boolean wasReCased = info.getName().equalsIgnoreCase(name);
		final boolean newNameIsNotTaken = findByNameIgnoreCase(name).isEmpty();
		
		if (wasReCased || newNameIsNotTaken) {
			info.setName(name);
			return infoRepository.save(info);
			
		} else {
			throw new IllegalArgumentException(
				"Info with name '" + name + "' already exists"
			);
		}
	}
	
	public Info changeCategory(Info info, Category category) {
		info.setCategory(category);
		return infoRepository.save(info);
	}
	
	public void delete(Info info) {
		infoRepository.delete(info);
	}
	
	@Transactional
	public void deleteByCategory(Category category) {
		infoRepository.deleteByCategory(category);
	}
}
