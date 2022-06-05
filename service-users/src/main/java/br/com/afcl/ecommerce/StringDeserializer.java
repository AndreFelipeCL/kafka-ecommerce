package br.com.afcl.ecommerce;

import br.com.afcl.ecommerce.deserializer.GsonDeserializer;

public class StringDeserializer extends GsonDeserializer<String> {
	public StringDeserializer() {
		super(String.class);
	}
}
