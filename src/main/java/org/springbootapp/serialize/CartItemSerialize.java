package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.CartItem;
import org.springbootapp.entity.ProductEntity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CartItemSerialize extends StdSerializer<CartItem> {

	private static final long serialVersionUID = 1880223790881130861L;

	protected CartItemSerialize(Class<CartItem> t) {
		super(t);
	}

	public CartItemSerialize() {
		this(null);
	}

	@Override
	public void serialize(CartItem value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		ProductEntity product = value.getProduct();
		gen.writeObjectField("productID", product.getId());
		gen.writeObjectField("productImage",
				product.getImages() != null ? product.getImages().iterator().next().getLink() : null);
		gen.writeObjectField("productName", product.getName());
		gen.writeObjectField("quantity", value.getQuantity());
		gen.writeObjectField("productPrice", product.getPrice());
		gen.writeEndObject();
	}

}
