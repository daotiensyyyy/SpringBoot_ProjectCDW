package org.springbootapp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springbootapp.serialize.ProductSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@NamedEntityGraph(name = "ProductEntity.graph", attributeNodes = { @NamedAttributeNode("category"),
		@NamedAttributeNode("images") })
@NamedEntityGraph(name = "ProductEntity.images", attributeNodes = { @NamedAttributeNode("images") })
@JsonSerialize(using = ProductSerialize.class)
public class ProductEntity extends AbstractEntity {
	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private int price;

	@Column(name = "description")
	private String description;

	@Column(name = "evaluate")
	private double evaluate;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
	private Set<ImageEntity> images;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id", nullable=false)
	private CategoryEntity category;

	
	@JsonCreator
	public ProductEntity(Long id, String name, int price, String description, double evaluate, 
			Long category, Set<ImageEntity> images ) {
		super(id);
		this.name = name;
		this.price = price;
		this.description = description;
		this.evaluate = evaluate;
		this.category = new CategoryEntity(category);
		this.images = new HashSet<>();
		if (images != null) {
			images.forEach(image -> this.addImage(image));
		}
	}
	
	public ProductEntity(Long id) {
		super(id);
	}

	public void addImage(ImageEntity image) {
		this.images.add(image);
		image.setProduct(this);
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
		category.getProducts().add(this);
	}
////	Cart
//	@ManyToMany(mappedBy = "products")
//	private List<CartEntity> carts = new ArrayList<>();

////	Order
//	@ManyToMany(mappedBy = "products")
//	private List<OrderEntity> orders = new ArrayList<>();

}
