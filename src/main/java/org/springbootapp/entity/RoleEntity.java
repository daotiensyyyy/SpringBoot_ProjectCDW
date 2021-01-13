package org.springbootapp.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springbootapp.common.ERole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
public class RoleEntity extends AbstractEntity {
	@Enumerated(EnumType.STRING)
	private ERole name;

	public RoleEntity(ERole name) {
		this.name = name;
	}

}
