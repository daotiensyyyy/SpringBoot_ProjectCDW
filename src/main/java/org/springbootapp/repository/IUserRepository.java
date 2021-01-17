package org.springbootapp.repository;

import java.util.Optional;

import org.springbootapp.entity.OrderEntity;
import org.springbootapp.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByResetToken(String resetToken);

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

	@Modifying
	@Query("update UserEntity u set u.password = :password where u.id = :id")
	void updatePassword(@Param("password") String password, @Param("id") Long id);
}
