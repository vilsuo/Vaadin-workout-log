
package com.example.demo.data;

import com.example.demo.data.entity.Category;
import com.example.demo.data.entity.Info;
import com.example.demo.data.service.CategoryService;
import com.example.demo.data.service.InfoService;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
public class InfoServiceTest {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private InfoService infoService;
	
	Category cat1;
	Category cat2;
	
	@BeforeEach
	public void createCategories() {
		cat1 = categoryService.create("legs");
		cat2 = categoryService.create("back");
	}
	
	@Test
	public void creatingInfoWithSameNameThrows() {
		final String name = "Squat";
		final String lower = name.toLowerCase();
		final String upper = name.toUpperCase();
		
		assertDoesNotThrow(
			() -> infoService.create(name, cat1)
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> infoService.create(name, cat1)
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> infoService.create(name, cat2)
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> infoService.create(lower, cat1)
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> infoService.create(upper, cat1)
		);
	}
	
	@Test
	public void creatingInfoWithDifferentNameDoesNotThrow() {
		final String name1 = "squat";
		final String name2 = "deadlift";
		
		assertDoesNotThrow(
			() -> infoService.create(name1, cat1)
		);
		
		assertDoesNotThrow(
			() -> infoService.create(name2, cat1)
		);
	}
	
	@Test
	public void renamingToDifferenCasingDoesNotThrow() {
		final String name = "Squat";
		final String lower = name.toLowerCase();
		final String upper = name.toUpperCase();
		
		Info info = infoService.create(name, cat1);
		
		assertDoesNotThrow(
			() -> infoService.rename(info, name)
		);
		
		assertDoesNotThrow(
			() -> infoService.rename(info, lower)
		);
		
		assertDoesNotThrow(
			() -> infoService.rename(info, upper)
		);
	}
	
	@Test
	public void renamingToNonExistingNameDoesNotThrow() {
		final String name = "Squat";
		final String name2 = "Deadlift";
		final String name3 = "Leg press";
		
		Info info = infoService.create(name, cat1);
		
		assertDoesNotThrow(
			() -> infoService.rename(info, name)
		);
		
		assertDoesNotThrow(
			() -> infoService.rename(info, name2)
		);
		
		assertDoesNotThrow(
			() -> infoService.rename(info, name3)
		);
	}
	
	@Test
	public void renamingToTakenNameThrows() {
		final String name = "Squat";
		final String takenName1 = "Deadlift";
		final String takenName2 = "Leg press";
		
		infoService.create(takenName1, cat1);
		infoService.create(takenName2, cat2);
		
		Info info = infoService.create(name, cat1);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> infoService.rename(info, takenName1)
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> infoService.rename(info, takenName1.toLowerCase())
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> infoService.rename(info, takenName2)
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> infoService.rename(info, takenName2.toUpperCase())
		);
	}
	
	@Test
	public void changingCategoryTest() {
		final Category orgCat = cat1;
		final Category newCat = cat2;
		Info info = infoService.create("deadlift", orgCat);
		Info infoChanged = infoService.changeCategory(info, newCat);
		
		assertEquals(
			newCat, infoChanged.getCategory()
		);
		
		assertEquals(
			info.getName(), infoChanged.getName()
		);
	}
	
}
