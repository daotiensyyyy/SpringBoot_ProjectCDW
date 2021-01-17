package org.springbootapp.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.springbootapp.serialize.CartItemSerialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonSerialize(using = CartItemSerialize.class)
@ToString
public class CartItem {
	@EmbeddedId
	private PkCartItem pk;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("customerId")
	@Include
	private UserEntity customer;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	@Include
	private ProductEntity product;
	@Column(columnDefinition = "int(5)")
	private Long quantity;

	public CartItem(UserEntity customer, ProductEntity product, Long quantity) {
		super();
		this.pk = new PkCartItem(customer.getId(), product.getId());
		this.customer = customer;
		this.product = product;
		this.quantity = quantity;
	}

	@JsonCreator
	public CartItem(Long productID, Long quantity) {
		super();
		this.product = new ProductEntity(productID);
		this.quantity = quantity;
	}

	public Long increaseQuantity(Long quantity) {
		this.quantity += quantity;
		return this.quantity;
	}

	public Long decreaseQuantity(Long quantity) {
		this.quantity -= quantity;
		return this.quantity;
	}

}