package org.springbootapp.repository;

import java.util.Optional;

import org.springbootapp.entity.ProductEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
	ProductEntity findByName(String name);

	@Query("SELECT p FROM ProductEntity p WHERE p.id = ?1")
	@EntityGraph("ProductEntity.images")
	public Optional<ProductEntity> findByIdWithImagesGraph(Long id);
}
