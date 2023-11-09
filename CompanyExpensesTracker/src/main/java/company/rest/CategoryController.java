package company.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import company.entities.Category;
import company.entities.CategoryRepo;

@RestController
public class CategoryController {
	@Autowired
	CategoryRepo categoryRepo;

	// 13
	// List All Categories
	@GetMapping("/categories")
	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	// Get CategoryName based on the CategoryCode
	@GetMapping("/categories/{categoryCode}")
	public String getOneCategory(@PathVariable("categoryCode") String categoryCode) {
		var optCategory = categoryRepo.findById(categoryCode);
		if (optCategory.isPresent())
			return optCategory.get().getCategoryName();
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Code Not Found!");
	}

	// 4
	// Add Category
	@PostMapping("/categories/add")
	public Category addNewCategory(@RequestBody Category category) {
		try {
			var optCategory = categoryRepo.findById(category.getCategoryCode());
			if (optCategory.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category Code Already Present");
			}
			categoryRepo.save(category);
			return category;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// 4
	// Delete Category
	@DeleteMapping("/categories/{categoryCode}")
	public void deleteOneCategory(@PathVariable("categoryCode") String categoryCode) {
		var optCategory = categoryRepo.findById(categoryCode);
		if (optCategory.isPresent())
			categoryRepo.deleteById(categoryCode);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Code Not Found!");
	}

	// 4
	// Update Category
	@PutMapping("/categories/{categoryCode}")
	public void updateCategory(@PathVariable("categoryCode") String categoryCode,
			@RequestParam("categoryName") String categoryName) {
		var optCategory = categoryRepo.findById(categoryCode);
		if (optCategory.isPresent()) {
			var category = optCategory.get();
			category.setCategoryName(categoryName);
			categoryRepo.save(category);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Name Not Found!");
	}
}
