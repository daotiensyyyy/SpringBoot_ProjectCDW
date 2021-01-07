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
		// TODO Auto-generated method stub
		
	}

}
