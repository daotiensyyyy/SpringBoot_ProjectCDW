//package org.springbootapp.entity;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.ForeignKey;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "Order_Details")
//public class OrderDetail extends AbstractEntity implements Serializable {
//
//	private static final long serialVersionUID = 7550745928843183535L;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "ORDER_ID", nullable = false, //
//			foreignKey = @ForeignKey(name = "ORDER_DETAIL_ORD_FK"))
//	private OrderEntity order;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "PRODUCT_ID", nullable = false, //
//			foreignKey = @ForeignKey(name = "ORDER_DETAIL_PROD_FK"))
//	private ProductEntity product;
//
//	@Column(name = "quanity", nullable = false)
//	private int quanity;
//
//	@Column(name = "price", nullable = false)
//	private double price;
//
//	@Column(name = "amount", nullable = false)
//	private double amount;
//
//}
