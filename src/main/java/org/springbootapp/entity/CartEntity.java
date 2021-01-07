//package org.springbootapp.entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "cart")
//public class CartEntity extends AbstractEntity {
//	@Column(name = "cart_quantity")
//	private double cart_quantity;
//
//	@Column(name = "cart_amount")
//	private int cart_amount;
//
////	User
//	@OneToOne
//	@JoinColumn(name = "user_id")
//	private UserEntity userEntity;
//
////	Product
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "cart_product", 
//				joinColumns = @JoinColumn(name = "cart_id"),
//				inverseJoinColumns = @JoinColumn(name = "product_id"))
//	private List<ProductEntity> products = new ArrayList<>();
//}
