package org.springbootapp.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
@NamedEntityGraph(name = "Customer.orders", attributeNodes = @NamedAttributeNode(value = "orders", subgraph = "Order.graph"), subgraphs = {
		@NamedSubgraph(name = "Order.graph", attributeNodes = {
				@NamedAttributeNode(value = "items", subgraph = "OrderItem.graph") }),
		@NamedSubgraph(name = "OrderItem.graph", attributeNodes = @NamedAttributeNode("product")) })
@NamedEntityGraph(name = "Customer.items", attributeNodes = @NamedAttributeNode(value = "items", subgraph = "items.graph"), subgraphs = {
		@NamedSubgraph(name = "items.graph", attributeNodes = {
				@NamedAttributeNode(value = "product", subgraph = "product.graph") }),
		@NamedSubgraph(name = "product.graph", attributeNodes = @NamedAttributeNode("images")) })
public class UserEntity extends AbstractEntity {
	private String username;
	private String email;
	private String password;
	private String address;
	private String phone;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer")
	private Set<CartItem> items;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<OrderEntity> orders;

	public UserEntity(String username, String email, String password, String address, String phone) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.address = address;
		this.phone = phone;
	}

	public UserEntity(Long id) {
		super(id);
	}
	
	public void addCartItem(ProductEntity product, Long quantity) {
		CartItem cartItem = new CartItem(this, product, quantity);
		if (!this.items.contains(cartItem)) {
			this.items.add(cartItem);
			return;
		}
		items.forEach(item -> {
			if (item.equals(cartItem))
				item.increaseQuantity(quantity);
		});
	}
	
	public void updateCart(ProductEntity product, String action) {
		for (Iterator<CartItem> iterator = this.items.iterator(); iterator.hasNext();) {
			CartItem item = iterator.next();
			if (item.getProduct().equals(product)) {
				switch (action) {
				case "increase":
					item.increaseQuantity(1L);
					break;
				case "decrease":
					if (item.getQuantity() > 1) {
						item.decreaseQuantity(1L);
						return;
					}
					iterator.remove();
					break;
				default:
					break;
				}
			}
		}
	}

	public void removeCartItem(ProductEntity product) {
		for (Iterator<CartItem> iterator = this.items.iterator(); iterator.hasNext();) {
			CartItem item = iterator.next();
			if (item.getProduct().equals(product)) {
				iterator.remove();
			}
		}
	}
	
	public void addOrder(OrderEntity order) {
		this.orders.add(order);
		order.setCustomer(this);
	}

}
