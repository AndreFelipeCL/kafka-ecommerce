package br.com.afcl.ecommerce.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.ExecutionException;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 05/05/2022
 */
@FunctionalInterface
public interface ConsumerFunction<T> {

    void consume(ConsumerRecord<String, T> rcd) throws Exception;
}
