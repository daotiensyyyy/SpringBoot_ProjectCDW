package org.springbootapp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springbootapp.serialize.OrderSerialize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order")
@NoArgsConstructor
@JsonSerialize(using = OrderSerialize.class)
public class OrderEntity extends AbstractEntity {
	private String code;
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity customer;
	private String name;
	private String phone;
	private String address;
	private String method;
}
