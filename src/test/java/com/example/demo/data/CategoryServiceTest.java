
package com.example.demo.data;

import com.example.demo.data.entity.Category;
import com.example.demo.data.entity.Info;
import com.example.demo.data.service.CategoryService;
import com.example.demo.data.service.InfoService;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
public class CategoryServiceTest {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private InfoService infoService;

	@Test
	public void removingCategoryRemovesAllInfosWithThatCategory() {
		final Category cat1 = categoryService.create("legs");
		final Info inf11 = infoService.create("squat", cat1);
		final Info inf12 = infoService.create("deadlift", cat1);
		
		final Category cat2 = categoryService.create("back");
		final Info inf21 = infoService.create("chin up", cat2);
		final Info inf22 = infoService.create("pull up", cat2);
		
		assertEquals(2, infoService.findAllByCategory(cat1).size());
		
		categoryService.delete(cat1);
		
		// the Infoe with the deleted Category are also deleted
		assertTrue(infoService.findAllByCategory(cat1).isEmpty());
		assertTrue(infoService.findByNameIgnoreCase(inf11.getName()).isEmpty());
		assertTrue(infoService.findByNameIgnoreCase(inf12.getName()).isEmpty());
		
		// the Infos with the Category that was not deleted are not deleted
		assertEquals(2, infoService.findAllByCategory(cat2).size());
		assertTrue(infoService.findByNameIgnoreCase(inf21.getName()).isPresent());
		assertTrue(infoService.findByNameIgnoreCase(inf22.getName()).isPresent());
	}
	
	@Test
	public void renamingToDifferenCasingDoesNotThrow() {
		final String name = "Legs";
		final String lower = name.toLowerCase();
		final String upper = name.toUpperCase();
		
		Category info = categoryService.create(name);
		
		assertDoesNotThrow(
			() -> categoryService.rename(info, name)
		);
		
		assertDoesNotThrow(
			() -> categoryService.rename(info, lower)
		);
		
		assertDoesNotThrow(
			() -> categoryService.rename(info, upper)
		);
	}
	
	@Test
	public void renamingToNonExistingNameDoesNotThrow() {
		final String name = "Legs";
		final String name2 = "Legss";
		final String name3 = "Hams";
		
		Category category = categoryService.create(name);
		
		assertDoesNotThrow(
			() -> categoryService.rename(category, name)
		);
		
		assertDoesNotThrow(
			() -> categoryService.rename(category, name2)
		);
		
		assertDoesNotThrow(
			() -> categoryService.rename(category, name3)
		);
	}
	
	@Test
	public void renamingToTakenNameThrows() {
		final String name = "Legs";
		final String takenName1 = "Back";
		final String takenName2 = "Chest";
		
		categoryService.create(takenName1);
		categoryService.create(takenName2);
		
		Category category = categoryService.create(name);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> categoryService.rename(category, takenName1)
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> categoryService.rename(category, takenName1.toLowerCase())
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> categoryService.rename(category, takenName2)
		);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> categoryService.rename(category, takenName2.toUpperCase())
		);
	}
}
