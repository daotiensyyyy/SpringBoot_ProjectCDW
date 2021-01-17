package org.springbootapp.api;

import java.util.List;

import org.springbootapp.entity.OrderEntity;
import org.springbootapp.entity.ProductEntity;
import org.springbootapp.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderAPI {
	@Autowired
	private IOrderService service;

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<OrderEntity>> getAllOrders() {
		List<OrderEntity> orders = service.getAllOrders();
		if (orders.isEmpty()) {
			return new ResponseEntity<List<OrderEntity>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<OrderEntity>>(orders, HttpStatus.OK);
	}

//	@GetMapping("/{oid}")
////	@PreAuthorize("hasAuthority('order:retrieve')")
//	public OrderEntity getOrder(@PathVariable("oid") Long orderID) {
//		return service.getOrder(orderID);
//	}

	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public void addOrder(@RequestBody OrderEntity order) {
		service.addOrder(order);
	}
}