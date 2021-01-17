package org.springbootapp.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="confirmation_token")
public class ConfirmationToken extends AbstractEntity {

	private String confirmationToken;
	private Date createdDate;
	@OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private UserEntity user;

	public ConfirmationToken(UserEntity user) {
		this.user = user;
		createdDate = new Date();
		confirmationToken = UUID.randomUUID().toString();
	}
}
