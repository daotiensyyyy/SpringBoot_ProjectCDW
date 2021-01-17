package org.springbootapp.api;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springbootapp.entity.ImageEntity;
import org.springbootapp.entity.ProductEntity;
import org.springbootapp.service.IFileService;
import org.springbootapp.service.implement.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class ProductAPI {

	private static String UPLOAD_DIR = "/uploads";

	@Autowired
	ProductService productService;

	@Autowired
	IFileService service;

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductEntity> createProduct(@ModelAttribute ProductEntity product,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath(UPLOAD_DIR) + File.separator + fileName;
			service.save(file.getInputStream(), path);
			String link = request.getRequestURL().toString().replace(request.getRequestURI(), "") + UPLOAD_DIR
					+ File.separator + file.getOriginalFilename();
			System.out.println(link);
			ImageEntity image = new ImageEntity(link, product.getName());
			product.setImages(new HashSet<>());
			product.addImage(image);
			productService.save(product);

			return new ResponseEntity<>(null, HttpStatus.OK);
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
		try {
			productService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> updateById(@PathVariable("id") Long id, @ModelAttribute ProductEntity product,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath(UPLOAD_DIR) + File.separator + fileName;
			service.save(file.getInputStream(), path);
			String link = request.getRequestURL().toString().replace(request.getRequestURI(), "") + UPLOAD_DIR
					+ File.separator + file.getOriginalFilename();
			System.out.println(link);
			ImageEntity image = new ImageEntity(link, product.getName());
			product.setImages(new HashSet<>());
			product.addImage(image);
			productService.update(id, product);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/products/{id}/images", method = RequestMethod.GET)
	public ResponseEntity<Optional<ProductEntity>> getProductImages(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity(productService.getImages(id), HttpStatus.OK);
		} catch (Exception e) {
//			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
