package org.springbootapp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category")
@NamedEntityGraph(name = "CategoryEntity.products", attributeNodes = @NamedAttributeNode(value = "products", subgraph = "images"), 
subgraphs = @NamedSubgraph(name = "images", attributeNodes = @NamedAttributeNode("images")))
public class CategoryEntity extends AbstractEntity {
	@Column(name = "name")
	private String name;
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category")
	private Set<ProductEntity> products = new HashSet<>();

	public CategoryEntity(Long id) {
		this.id = id;
	}

	@JsonCreator
	public CategoryEntity(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
}
