package br.com.afcl.ecommerce.services;

import br.com.afcl.ecommerce.deserializer.GsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 05/05/2022
 */
public final class KafkaService<T> implements Closeable {
	
	private final String topic;
	
	private final ConsumerFunction<T> parse;
	
	private final Class<T> clazzType;
	
	private final String groupID;
	
	private final KafkaConsumer<String, T> kafkaConsumer;
	
	public KafkaService(String topic, ConsumerFunction<T> parse, Class<T> clazzType, String groupID, Map<String, String> properties) {
		this.topic = topic;
		this.parse = parse;
		this.clazzType = clazzType;
		this.groupID = groupID;
		
		this.kafkaConsumer = new KafkaConsumer<>(getProperties(this.clazzType, properties));
		this.kafkaConsumer.subscribe(Pattern.compile(this.topic));
	}
	
	private Properties getProperties(Class<T> clazz, Map<String, String> overrideProperties) {
		var properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9090");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, this.groupID);
		properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
		properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
		properties.setProperty(GsonDeserializer.CLAZZ_CONFIG, clazz.getName());
		properties.putAll(overrideProperties);
		return properties;
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			var records = kafkaConsumer.poll(Duration.ofMillis(100));
			if (!records.isEmpty()) {
				records.forEach(rcd -> {
					try {
						parse.consume(rcd);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
		}
	}
	
	@Override
	public void close() {
		this.kafkaConsumer.close();
	}
}
