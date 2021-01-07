package org.springbootapp.dto;

import java.util.Date;

import javax.persistence.Column;

import org.springbootapp.entity.AbstractEntity;
import org.springbootapp.entity.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO{
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String product_name;
	private String product_image;
	private int product_price;
	private String product_description;
	private double product_evalute;
	private CategoryEntity catalog_code;
}
