package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.OrderEntity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class OrderSerialize extends StdSerializer<OrderEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected OrderSerialize(Class<OrderEntity> t) {
		super(t);
	}

	public OrderSerialize() {
		this(null);
	}
	
	@Override
	public void serialize(OrderEntity value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("orderCode", value.getOrderCode());
		gen.writeObjectField("createDate", value.getCreateDate());
		gen.writeObjectField("total", value.getTotal());
		gen.writeObjectField("consigneeName", value.getConsigneeName());
		gen.writeObjectField("consigneePhone", value.getConsigneePhone());
		gen.writeObjectField("address", value.getAddress());
		gen.writeObjectField("userCode", value.getCustomer() != null ? value.getCustomer().getId() : null);
		gen.writeObjectField("items", value.getItems());
		gen.writeEndObject();
	}

}
