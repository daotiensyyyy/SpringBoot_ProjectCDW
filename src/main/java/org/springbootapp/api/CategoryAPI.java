package org.springbootapp.api;

import java.util.List;

import org.springbootapp.entity.CategoryEntity;
import org.springbootapp.entity.ProductEntity;
import org.springbootapp.service.implement.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryAPI {

	@Autowired
	private CategoryService service;

	@RequestMapping(value = "/categories", method = RequestMethod.POST)
	public ResponseEntity<CategoryEntity> createProduct(@RequestBody CategoryEntity category) {
		try {
			service.create(category);
			return new ResponseEntity<>(category, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ResponseEntity<List<CategoryEntity>> getAllCategories() {
		List<CategoryEntity> category = service.getAll();
		if (category.isEmpty()) {
			return new ResponseEntity<List<CategoryEntity>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<CategoryEntity>>(category, HttpStatus.OK);
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/categories/{id}/products", method = RequestMethod.GET)
	public ResponseEntity<List<CategoryEntity>> getProductsByCategory(@PathVariable("id") Long id) {
		try {
			List<ProductEntity> products = service.getAllProductsByCategory(id);
			return new ResponseEntity(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateById(@PathVariable("id") Long id, @RequestBody CategoryEntity newCategory) {
		try {
			service.update(id, newCategory);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
