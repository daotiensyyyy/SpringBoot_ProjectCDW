package org.springbootapp.repository;

import java.util.Optional;

import org.springbootapp.entity.OrderEntity;
import org.springbootapp.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity>findByUsername(String username);
	
	UserEntity findByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	@EntityGraph("Customer.items")
	@Query("SELECT c FROM UserEntity c WHERE c.id = ?1")
	public Optional<UserEntity> findByIdWithItemsGraph(Long customerID);

	@Query("SELECT o FROM OrderEntity o WHERE o.id = ?2 AND o.customer.id = ?1")
	public OrderEntity getOrderDetails(Long customerID, Long orderID);
	
	@EntityGraph("Customer.orders")
	@Query("SELECT c FROM UserEntity c WHERE c.id = ?1")
	public Optional<UserEntity> findByIdWithOrdersGraph(Long customerID);
}
