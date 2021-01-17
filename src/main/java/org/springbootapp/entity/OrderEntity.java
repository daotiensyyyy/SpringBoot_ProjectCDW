package org.springbootapp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springbootapp.serialize.OrderSerialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor
@JsonSerialize(using = OrderSerialize.class)
@NamedEntityGraph(name = "Order.items", attributeNodes = @NamedAttributeNode(value = "items", subgraph = "items.product"), subgraphs = @NamedSubgraph(name = "items.product", attributeNodes = @NamedAttributeNode("product")))
public class OrderEntity extends AbstractEntity {
	private static final long serialVersionUID = 560226111253281070L;
	@Column(unique = true)
	private String orderCode;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	private BigDecimal total;
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity customer;
	private String consigneeName;
	private String consigneePhone;
	private String address;
//	**
	private String payMethod;
	@OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<OrderDetail> items;

	@JsonCreator
	public OrderEntity(Long customerID, BigDecimal total, String consigneeName, String consigneePhone, String address,
			Set<OrderDetail> items) {
		super();
		this.orderCode = getSaltString();
		this.customer = customerID != null ? new UserEntity(customerID) : null;
		this.createDate = new Date(System.currentTimeMillis());
		this.total = total;
		this.consigneeName = consigneeName;
		this.consigneePhone = consigneePhone;
		this.address = address;
		this.payMethod = "COD";
		this.items = items;
	}

	public void addItem(ProductEntity product, Long quantity) {
		OrderDetail item = new OrderDetail(this, product, quantity);
		this.items.add(item);
	}

	private String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return "MS" + saltStr;

	}

}