package br.com.afcl.ecommerce.services;

import br.com.afcl.ecommerce.model.MessageWrapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@FunctionalInterface
public interface ConsumerFunction<T> {

	void consume(ConsumerRecord<String, MessageWrapper<T>> rcd) throws Exception;
}
