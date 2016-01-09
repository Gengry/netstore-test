package edu.imut.dao;

import java.util.List;

import edu.imut.domain.Category;

public interface CategoryDao {

	void save(Category category);

	List<Category> findAll();

	Category findById(String categoryId);

}
