package org.springbootapp.service.implement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springbootapp.entity.OrderDetail;
import org.springbootapp.entity.OrderEntity;
import org.springbootapp.repository.IOrderRepository;
import org.springbootapp.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {
	@Autowired
	private IOrderRepository repo;

	@Override
	public List<OrderEntity> getAllOrders() {
		return repo.findAllWithItemsGraph();
	}

	@Override
	public OrderEntity getOrder(Long orderID) {
		return repo.findByIdWithItemsGraph(orderID).get();
	}

	@Override
	@Transactional
	public void addOrder(OrderEntity order) {
		Set<OrderDetail> temp = new HashSet<>(order.getItems());
		order.setItems(new HashSet<>());
		OrderEntity save = repo.save(order);
		temp.forEach(item -> repo.addItem(item.getQuantity(), save.getId(), item.getProduct().getId()));
	}

}
