package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.OrderDetail;
import org.springbootapp.entity.ProductEntity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class OrderDetailSerialize extends StdSerializer<OrderDetail> {

	private static final long serialVersionUID = -3019191627126061759L;

	protected OrderDetailSerialize(Class<OrderDetail> t) {
		super(t);
	}

	public OrderDetailSerialize() {
		this(null);
	}

	@Override
	public void serialize(OrderDetail value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		ProductEntity product = value.getProduct();
		gen.writeObjectField("productCode", product.getId());
		gen.writeObjectField("productName", product.getName());
		gen.writeObjectField("productPrice", product.getPrice());
		gen.writeObjectField("quantity", value.getQuantity());
		gen.writeEndObject();
	}
}
