package org.springbootapp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user")

public class UserEntity extends AbstractEntity {

	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "email")
	private String user_email;
	@Column(name = "phone")
	private String user_phone;
	@Column(name = "address")
	private String user_address;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonIgnore
	private Set<RoleEntity> roles = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<OrderEntity> orders = new HashSet<>();;
	
	public void addRoles(RoleEntity role) {
		roles.add(role);
		role.getUsers().add(this);
	}

	public void addOrder(OrderEntity order) {
		this.orders.add(order);
		order.setCustomer(this);
	}

}
