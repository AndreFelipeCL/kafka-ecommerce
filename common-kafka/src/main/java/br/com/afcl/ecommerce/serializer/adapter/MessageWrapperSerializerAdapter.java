package br.com.afcl.ecommerce.serializer.adapter;

import java.lang.reflect.Type;

import br.com.afcl.ecommerce.model.MessageWrapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MessageWrapperSerializerAdapter<T> implements JsonSerializer<MessageWrapper<T>> {

	@Override
	public JsonElement serialize(MessageWrapper<T> messageWrapper, Type type, JsonSerializationContext context) {
		var obj = new JsonObject();
		obj.addProperty("type", messageWrapper.getPayload().getClass().getName());
		obj.add("correlationId", context.serialize(messageWrapper.getCorrelationId()));
		obj.add("payload", context.serialize(messageWrapper.getPayload()));
		return obj;
	}
}
