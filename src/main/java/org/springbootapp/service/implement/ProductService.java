package org.springbootapp.service.implement;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springbootapp.entity.CategoryEntity;
import org.springbootapp.entity.ImageEntity;
import org.springbootapp.entity.ProductEntity;
import org.springbootapp.repository.IProductRepository;
import org.springbootapp.service.IProductService;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductEntity productEntity;

	@Autowired
	private CategoryEntity category;

	@Autowired
	private IProductRepository productRepository;

	@Override
	public ProductEntity save(ProductEntity product) {
//		product.setName(product.getName());
//		product.setPrice(product.getPrice());
//		product.setDescription(product.getDescription());
//		product.setEvaluate(product.getEvaluate());
//		product.setImages(images);
		return productRepository.saveAndFlush(product);
	}

	@Override
	public List<ProductEntity> getAll() {
		return productRepository.findAll();
	}

	@Override
	public Optional<ProductEntity> getById(Long id) {
		Optional<ProductEntity> product = Optional.of(productRepository.findById(id).get());
		return product;
	}

	@Override
	public Optional<ProductEntity> getByName(String name) {
		Optional<ProductEntity> product = Optional.of(productRepository.findByName(name));
		return product;
	}

	@Override
	public void delete(Long id) {
		if (productRepository.findById(id).isPresent())
			productRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void update(Long id, ProductEntity product) {

		if (productRepository.findById(id).isPresent()) {
			productEntity = productRepository.findById(id).get();
			productEntity.setName(product.getName());
			productEntity.setPrice(product.getPrice());
			productEntity.setDescription(product.getDescription());
			productEntity.setEvaluate(product.getEvaluate());
			productEntity.setCategory(product.getCategory());
			productRepository.save(productEntity);

		}
	}

	@Override
	public List<ImageEntity> getImages(Long productId) {
		Optional<ProductEntity> findById = productRepository.findByIdWithImagesGraph(productId);
		return List.copyOf(findById.get().getImages());
	}

}
