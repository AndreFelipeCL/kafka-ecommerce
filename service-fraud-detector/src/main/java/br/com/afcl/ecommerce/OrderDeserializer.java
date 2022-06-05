package br.com.afcl.ecommerce;

import br.com.afcl.ecommerce.deserializer.GsonDeserializer;

public class OrderDeserializer extends GsonDeserializer<Order> {
	public OrderDeserializer() {
		super(Order.class);
	}
}
