package br.com.afcl.ecommerce.dispatcher;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import br.com.afcl.ecommerce.model.CorrelationId;
import br.com.afcl.ecommerce.model.MessageWrapper;
import br.com.afcl.ecommerce.serializer.GsonSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 05/05/2022
 */
public class KafkaDispatcher<T> implements Closeable {

	private final KafkaProducer<String, MessageWrapper<T>> producer = new KafkaProducer<>(properties());

	private static Properties properties() {
		var properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9090");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
		properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
		return properties;
	}

	private Callback buildCallback() {
		return (metadata, exception) -> {
			if (exception != null) {
				exception.printStackTrace();
				return;
			}
			System.out.println("\n" +
							   metadata.topic() +
							   ":::partition " +
							   metadata.partition() +
							   " / offset " +
							   metadata.offset() +
							   " / timestamp " +
							   metadata.timestamp());
		};
	}

	public Future<RecordMetadata> sendAsync(CorrelationId correlationId, String topic, String key, T payload) {
		var value = new MessageWrapper<>(correlationId, payload);
		var callback = buildCallback();
		var nweRecord = new ProducerRecord<>(topic, key, value);
		return producer.send(nweRecord, callback);
	}

	public void send(CorrelationId correlationId, String topic, String key, T payload) throws ExecutionException, InterruptedException {
		sendAsync(correlationId, topic, key, payload).get();
	}

	@Override
	public void close() {
		this.producer.close();
	}
}
