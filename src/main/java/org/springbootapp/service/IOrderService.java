package org.springbootapp.service;

import java.util.List;

import org.springbootapp.entity.OrderEntity;

public interface IOrderService {
	List<OrderEntity> getAll();

	OrderEntity getOrder(Long id);

	OrderEntity addOrder(OrderEntity order);
}
