package org.springbootapp.service;

import java.util.List;

import org.springbootapp.entity.OrderEntity;

public interface IOrderService {
	public List<OrderEntity> getAllOrders();

	public OrderEntity getOrder(Long orderID);

	public void addOrder(OrderEntity order);
}
