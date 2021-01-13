package org.springbootapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springbootapp.entity.ProductEntity;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
	ProductEntity findByName(String name);

	@EntityGraph("ProductEntity.graph")
	public List<ProductEntity> findAll();

	@Query("SELECT p FROM ProductEntity p WHERE p.id = ?1")
	@EntityGraph("ProductEntity.images")
	public Optional<ProductEntity> findByIdWithImagesGraph(Long id);
}
