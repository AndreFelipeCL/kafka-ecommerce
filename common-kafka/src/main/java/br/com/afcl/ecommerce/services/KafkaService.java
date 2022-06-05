package br.com.afcl.ecommerce.services;

import java.io.Closeable;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import br.com.afcl.ecommerce.dispatcher.KafkaDispatcher;
import br.com.afcl.ecommerce.model.MessageWrapper;
import br.com.afcl.ecommerce.serializer.GsonSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 05/05/2022
 */
final class KafkaService<T> implements Closeable {

	private final ConsumerFunction<T> parse;
	private final String groupID;
	private final KafkaConsumer<String, MessageWrapper<T>> kafkaConsumer;

	public KafkaService(String topic, ConsumerFunction<T> parse, String groupID, Map<String, String> properties) {
		this.parse = parse;
		this.groupID = groupID;

		this.kafkaConsumer = new KafkaConsumer<>(getProperties(properties));
		this.kafkaConsumer.subscribe(Pattern.compile(topic));
	}

	private Properties getProperties(Map<String, String> overrideProperties) {
		var properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9090");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, this.groupID);
		properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
		properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
		properties.putAll(overrideProperties);
		return properties;
	}

	public void run() throws ExecutionException, InterruptedException {
		try (var deadLetterDispatcher = new KafkaDispatcher<>()) {
			while (true) {
				var records = kafkaConsumer.poll(Duration.ofMillis(100));
				if (!records.isEmpty()) {
					System.out.println("Records Found: " + records.count());
					for (ConsumerRecord<String, MessageWrapper<T>> rcd : records) {
						try {
							parse.consume(rcd);
						} catch (Exception e) {
							e.printStackTrace();
							var message = rcd.value();
							deadLetterDispatcher.send(message.getCorrelationId().continueWith("DeadLetter"),
													  "ECOMMERCE_DEADLETTER",
													  message.getCorrelationId().getId(),
													  new GsonSerializer().serialize("", message));
						}
					}
				}
			}
		}
	}

	@Override
	public void close() {
		this.kafkaConsumer.close();
	}
}
