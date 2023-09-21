
package com.example.demo.data.repository;

import com.example.demo.data.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractEntityRepository<T extends AbstractEntity> 
		extends JpaRepository<T, Long> {
	
}
