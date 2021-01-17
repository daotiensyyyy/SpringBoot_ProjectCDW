package org.springbootapp.serialize;

import java.io.IOException;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springbootapp.entity.ProductEntity;

public class ProductSerialize extends StdSerializer<ProductEntity>{

	protected ProductSerialize(Class<ProductEntity> t) {
		super(t);
	}
	
	public ProductSerialize() {
		this(null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(ProductEntity value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id", value.getId());
		gen.writeObjectField("name", value.getName());
		gen.writeObjectField("price", value.getPrice());
		gen.writeObjectField("description", value.getDescription());
		gen.writeObjectField("evaluate", value.getEvaluate());
		gen.writeObjectField("category", value.getCategory().getId());
//		gen.writeObjectField("images", value.getImages().size());
		gen.writeObjectField("images", value.getImages());
		gen.writeEndObject(); 
	}

}
