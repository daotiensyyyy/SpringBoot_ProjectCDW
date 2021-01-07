package org.springbootapp.service.implement;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springbootapp.entity.CategoryEntity;
import org.springbootapp.entity.ProductEntity;
import org.springbootapp.repository.ICategoryRepository;
import org.springbootapp.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
	@Autowired
	private ICategoryRepository repository;
	
	@Autowired
	private CategoryEntity categoryEntity;

	@Override
	public CategoryEntity create(CategoryEntity category) {
		return repository.saveAndFlush(category);
	}

	@Override
	public List<CategoryEntity> getAll() {
		return repository.findAll();
	}

	@Override
	public List<ProductEntity> getAllProductsByCategory(Long categoryId) {
		Optional<CategoryEntity> findByIdWithProductsGraph = repository.findByIdWithProductsGraph(categoryId);
		return List.copyOf(findByIdWithProductsGraph.get().getProducts());
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public void update(Long id, CategoryEntity category) {
		if (repository.findById(id).isPresent()) {
			categoryEntity = repository.findById(id).get();
			categoryEntity.setName(category.getName());
			categoryEntity.setDescription(category.getDescription());
			repository.save(categoryEntity);

		}
	}

}
