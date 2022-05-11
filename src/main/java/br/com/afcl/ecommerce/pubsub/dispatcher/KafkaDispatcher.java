package br.com.afcl.ecommerce.pubsub.dispatcher;

import br.com.afcl.ecommerce.pubsub.model.Constants;
import br.com.afcl.ecommerce.pubsub.serializer.GsonSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 05/05/2022
 */
public class KafkaDispatcher<T> {

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return properties;
    }

    private Callback buildCallback() {
        return (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                return;
            }
            System.out.println(metadata.topic()
                               + ":::partition " + metadata.partition()
                               + " / offset " + metadata.offset()
                               + " / timestamp " + metadata.timestamp());
        };
    }

    public void send(Constants topic, String key, T value) throws ExecutionException, InterruptedException {
        try (var producer = new KafkaProducer<String, T>(properties())) {
            var callback = buildCallback();
            var nweRecord = new ProducerRecord<>(topic.name(), key, value);
            producer.send(nweRecord, callback).get();
        }
    }
}
