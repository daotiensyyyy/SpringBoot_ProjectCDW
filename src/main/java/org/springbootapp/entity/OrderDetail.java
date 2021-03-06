package org.springbootapp.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.springbootapp.serialize.OrderDetailSerialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonSerialize(using = OrderDetailSerialize.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDetail {
	@EmbeddedId
	private PkOrderDetail pk;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("orderId")
	@Include
	private OrderEntity order;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	@Include
	private ProductEntity product;
	@Column(columnDefinition = "int(5)")
	private Long quantity;

	public OrderDetail(OrderEntity order, ProductEntity product, Long quantity) {
		super();
		this.pk = new PkOrderDetail(order.getId(), product.getId());
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	@JsonCreator
	public OrderDetail(Long productID, Long quantity) {
		super();
		this.product = new ProductEntity(productID);
		this.quantity = quantity;
	}
}
