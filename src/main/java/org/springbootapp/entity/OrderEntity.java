//package org.springbootapp.entity;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//import org.springbootapp.serialize.OrderSerialize;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "order")
//@NoArgsConstructor
//@JsonSerialize(using = OrderSerialize.class)
//public class OrderEntity extends AbstractEntity implements Serializable {
//	private String code;
//	@JsonFormat(pattern = "dd/MM/yyyy")
//	private Date date;
//	private String name;
//	private String address;
//	private String email;
//	private String phone;
//	private double amount;
//}
