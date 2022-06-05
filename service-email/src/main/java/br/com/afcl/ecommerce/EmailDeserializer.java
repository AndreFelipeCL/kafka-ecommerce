package br.com.afcl.ecommerce;

import br.com.afcl.ecommerce.deserializer.GsonDeserializer;

public class EmailDeserializer extends GsonDeserializer<Email> {

	public EmailDeserializer() {
		super(Email.class);
	}
}
