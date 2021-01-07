package org.springbootapp.api;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.ProductEntity;
import org.springbootapp.service.implement.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class ProductAPI {

	@Autowired
	ProductService productService;

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
		try {
			productService.save(product);
			// Trả về response với STATUS CODE = 201
			// Body sẽ chứa thông tin về đối tượng todo vừa được tạo.
			return new ResponseEntity<>(product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<List<ProductEntity>> getAllProducts() {
		List<ProductEntity> products = productService.getAll();
		if (products.isEmpty()) {
			return new ResponseEntity<List<ProductEntity>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ProductEntity>>(products, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<ProductEntity>> getProductById(@PathVariable("id") Long id) {
		Optional<ProductEntity> product = productService.getById(id);
		if (product.isPresent()) {
			return new ResponseEntity(product.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(value = "/products/{name}", method = RequestMethod.GET)
//	public ResponseEntity<Optional<ProductEntity>> getProductByName(@PathVariable("name") String name) {
//		Optional<ProductEntity> product = productService.getByName(name);
//		if (product.isPresent()) {
//			return new ResponseEntity(product.get(), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
		try {
			productService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateById(@PathVariable("id") Long id, @RequestBody ProductEntity newProduct) {
		try {
			productService.update(id, newProduct);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/products/{id}/images", method = RequestMethod.GET)
	public ResponseEntity<Optional<ProductEntity>> getProductImages(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity(productService.getImages(id), HttpStatus.OK);
		} catch(Exception e) {
//			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
