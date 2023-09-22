
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
		return page.getContent();
    }
	
	public Long count() {
		return infoRepository.count();
	}
	
	public Optional<Info> findByNameIgnoreCase(String name) {
		return infoRepository.findByNameIgnoreCase(name);
	}
	
	public Info save(Info info) {
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
