package org.springbootapp.repository;

import java.util.Optional;

import org.springbootapp.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
	@Query("SELECT c FROM CategoryEntity c WHERE c.id = ?1")
	@EntityGraph("CategoryEntity.products")
	public Optional<CategoryEntity> findByIdWithProductsGraph(Long id);
}
