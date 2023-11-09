package company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import company.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, String> {
	Category findByCategoryCode(String categoryCode);
}
