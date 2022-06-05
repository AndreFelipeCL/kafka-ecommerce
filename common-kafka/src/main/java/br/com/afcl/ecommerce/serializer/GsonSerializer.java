package br.com.afcl.ecommerce.serializer;

import br.com.afcl.ecommerce.model.MessageWrapper;
import br.com.afcl.ecommerce.serializer.adapter.MessageWrapperSerializerAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

public class GsonSerializer<T> implements Serializer<MessageWrapper<T>> {

	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter(MessageWrapper.class, new MessageWrapperSerializerAdapter<MessageWrapper<T>>())
			.create();

	@Override
	public byte[] serialize(String topic, MessageWrapper<T> data) {
		return gson.toJson(data).getBytes();
	}
}
