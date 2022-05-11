package br.com.afcl.ecommerce.pubsub.services;

import br.com.afcl.ecommerce.pubsub.model.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractService<T> implements Closeable {

    private final KafkaService<T> service;

    protected AbstractService(Class<T> domainClazz, Constants topic, String groupId, Map<String, String> appendProps) {
        this.service = new KafkaService<>(topic,
                                          this::parse,
                                          domainClazz,
                                          groupId,
                                          appendProps);
    }

    protected AbstractService(Class<T> domainClazz, Constants topic, String groupId) {
        this.service = new KafkaService<>(topic,
                                          this::parse,
                                          domainClazz,
                                          groupId,
                                          Map.of());
    }

    protected abstract void parse(ConsumerRecord<String, T> rcd);

    public void run() {
        this.service.run();
    }

    @Override
    public void close() throws IOException {
        this.service.close();
    }
}
