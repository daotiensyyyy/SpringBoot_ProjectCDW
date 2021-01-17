package org.springbootapp.repository;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
	@Query("SELECT o FROM OrderEntity o")
	@EntityGraph("Order.items")
	public List<OrderEntity> findAllWithItemsGraph();

	@Modifying
	@Query(value = "insert into order_detail (quantity, `order_id`, product_id) values (?1, ?2, ?3)", nativeQuery = true)
	public void addItem(Long quantity, Long order_id, Long product_id);

	@Query("SELECT o FROM OrderEntity o WHERE id = :id")
	@EntityGraph("Order.items")
	public Optional<OrderEntity> findByIdWithItemsGraph(@Param("id") Long id);
}
