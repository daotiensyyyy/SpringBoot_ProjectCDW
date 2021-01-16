package org.springbootapp.service;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.ImageEntity;
import org.springbootapp.entity.ProductEntity;
import org.springframework.web.multipart.MultipartFile;


public interface IProductService {
	List<ProductEntity> getAll();

	Optional<ProductEntity> getById(Long id);

	Optional<ProductEntity> getByName(String name);

	ProductEntity save(ProductEntity product);

	void delete(Long id);

	void update(Long id, ProductEntity product);

	List<ImageEntity> getImages(Long productId);
	
	void saveImage(MultipartFile imageFile, String folder);
}
