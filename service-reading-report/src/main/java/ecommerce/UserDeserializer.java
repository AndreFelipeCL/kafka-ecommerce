package ecommerce;

import br.com.afcl.ecommerce.deserializer.GsonDeserializer;

public class UserDeserializer extends GsonDeserializer<User> {
	public UserDeserializer() {
		super(User.class);
	}
}
