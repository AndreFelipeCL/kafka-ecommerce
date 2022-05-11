package br.com.afcl.ecommerce.pubsub.deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 09/05/2022
 */
public class GsonDeserializer<T> implements Deserializer<T> {

    public static final String CLAZZ_CONFIG = GsonDeserializer.class.getPackageName() + "clazz_config";

    private final Gson gson = new GsonBuilder().create();

    private Class<T> type;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        var typeName = String.valueOf(configs.get(CLAZZ_CONFIG));
        try {
            this.type = (Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Type deserialization does not exist in the classpath");
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        return gson.fromJson(new String(data), type);
    }
}
