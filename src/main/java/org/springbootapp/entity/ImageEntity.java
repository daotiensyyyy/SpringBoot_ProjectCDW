package org.springbootapp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "image")
public class ImageEntity extends AbstractEntity {

	private String link;
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductEntity product;

	public ImageEntity(Long id) {
		super(id);
	}

	@JsonCreator
	public ImageEntity(String link, String description) {
		super();
		this.link = link;
		this.description = description;
	}

	public ImageEntity(Long id, String link, String description) {
		super(id);
		this.link = link;
		this.description = description;
	}
}
