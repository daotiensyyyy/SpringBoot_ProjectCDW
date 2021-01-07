package org.springbootapp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
//	@Column(name = "createdBy")
//	private String createdBy;
//	@Column(name = "createdDate")
//	private Date createdDate;
//	@Column(name = "modifiedBy")
//	private String modifiedBy;
//	@Column(name = "modifiedDate")
//	private Date modifiedDate;
}
