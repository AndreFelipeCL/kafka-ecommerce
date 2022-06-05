package br.com.afcl.ecommerce.deserializer.adapter;

import java.lang.reflect.Type;

import br.com.afcl.ecommerce.model.CorrelationId;
import br.com.afcl.ecommerce.model.MessageWrapper;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class MessageWrapperDeserializerAdapter<T> implements JsonDeserializer<MessageWrapper<T>> {

	private final Class<T> classType;

	public MessageWrapperDeserializerAdapter(Class<T> classType) {this.classType = classType;}

	@Override
	public MessageWrapper<T> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
		var obj = jsonElement.getAsJsonObject();
		var correlationId = (CorrelationId) context.deserialize(obj.get("correlationId"), CorrelationId.class);
		var payload = (T) context.deserialize(obj.get("payload"), classType);
		return new MessageWrapper<>(correlationId, payload);
	}
}
