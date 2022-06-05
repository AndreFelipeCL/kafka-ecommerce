package br.com.afcl.ecommerce.services;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import br.com.afcl.ecommerce.dispatcher.KafkaDispatcher;
import br.com.afcl.ecommerce.model.CorrelationId;
import br.com.afcl.ecommerce.model.MessageWrapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public abstract class AbstractService<T> implements Closeable {

	private final KafkaDispatcher<T> dispatcher;
	private final KafkaService<T> service;

	protected AbstractService(String groupId,
							  String topic,
							  Map<String, String> appendProps) {
		this.dispatcher = new KafkaDispatcher<>();
		this.service = new KafkaService<>(topic,
										  this::parse,
										  groupId,
										  appendProps);
	}

	protected abstract void parse(ConsumerRecord<String, MessageWrapper<T>> rcd) throws Exception;

	protected String messageIdResolver(MessageWrapper<T> t) {
		return t.getCorrelationId().getId();
	}

	public void run() throws ExecutionException, InterruptedException {
		this.service.run();
	}

	@Override
	public void close() throws IOException {
		this.dispatcher.close();
		this.service.close();
	}

	protected void dispatch(String topic, CorrelationId correlationId, MessageWrapper<T> t) throws Exception {
		dispatcher.send(correlationId,
						topic,
						this.messageIdResolver(t),
						t.getPayload());
	}

	protected void dispatchAsync(String topic, CorrelationId correlationId, MessageWrapper<T> t) throws Exception {
		dispatcher.sendAsync(correlationId,
							 topic,
							 this.messageIdResolver(t),
							 t.getPayload());
	}
}
