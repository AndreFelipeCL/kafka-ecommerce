package br.com.afcl.ecommerce.pubsub.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 09/05/2022
 */
public class GsonSerializer<T> implements Serializer<T> {
    private final Gson gson = new GsonBuilder().create();

    @Override
    public byte[] serialize(String topic, T data) {
        return gson.toJson(data).getBytes();
    }
}
