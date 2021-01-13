package org.springbootapp.service;

import java.util.List;

import org.springbootapp.entity.CategoryEntity;
import org.springbootapp.entity.ProductEntity;


public interface ICategoryService {
	CategoryEntity create(CategoryEntity category);

	List<CategoryEntity> getAll();

	List<ProductEntity> getAllProductsByCategory(Long categoryId);

	void delete(Long id);
	
	void update(Long id, CategoryEntity category);
}
