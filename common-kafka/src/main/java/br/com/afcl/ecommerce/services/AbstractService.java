package br.com.afcl.ecommerce.services;

import br.com.afcl.ecommerce.dispatcher.KafkaDispatcher;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public abstract class AbstractService<T> implements Closeable {
	
	private final Function<T, String> messageIdResolver;
	private final KafkaDispatcher<T> dispatcher = new KafkaDispatcher<>();
	private final KafkaService<T> service;
	
	protected AbstractService(Class<T> domainClazz,
							  String topic,
							  String groupId,
							  Function<T, String> messageIdResolver,
							  Map<String, String> appendProps) {
		this.messageIdResolver = messageIdResolver;
		this.service = new KafkaService<>(topic,
										  this::parse,
										  domainClazz,
										  groupId,
										  appendProps);
	}
	
	protected AbstractService(Class<T> domainClazz, String topic, String groupId, Function<T, String> messageIdResolver) {
		this(domainClazz, topic, groupId, messageIdResolver, Map.of());
	}
	
	protected AbstractService(Class<T> domainClazz, String topic, String groupId, Map<String, String> appendProps) {
		this(domainClazz, topic, groupId, Objects::toString, appendProps);
	}
	
	protected AbstractService(Class<T> domainClazz, String topic, String groupId) {
		this(domainClazz, topic, groupId, Objects::toString);
	}
	
	protected abstract void parse(ConsumerRecord<String, T> rcd) throws Exception;
	
	protected String messageIdResolver(T t) {
		return messageIdResolver.apply(t);
	}
	
	public void run() {
		this.service.run();
	}
	
	@Override
	public void close() throws IOException {
		this.service.close();
	}
	
	protected void dispatchOrder(String topic, T t) throws Exception {
		dispatcher.send(topic, this.messageIdResolver(t), t);
	}
}
