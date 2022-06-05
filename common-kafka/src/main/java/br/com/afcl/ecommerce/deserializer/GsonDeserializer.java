package br.com.afcl.ecommerce.deserializer;

import br.com.afcl.ecommerce.deserializer.adapter.MessageWrapperDeserializerAdapter;
import br.com.afcl.ecommerce.model.MessageWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

public class GsonDeserializer<T> implements Deserializer<MessageWrapper<T>> {

	private final Gson gson;

	public GsonDeserializer(Class<T> clazzType) {
		this.gson = new GsonBuilder()
				.registerTypeAdapter(MessageWrapper.class, new MessageWrapperDeserializerAdapter<>(clazzType))
				.create();
	}

	@Override
	public MessageWrapper<T> deserialize(String topic, byte[] data) {
		return gson.fromJson(new String(data), MessageWrapper.class);
	}
}
