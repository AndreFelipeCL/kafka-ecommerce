package br.com.afcl.ecommerce.pubsub.services;

import br.com.afcl.ecommerce.pubsub.model.Constants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;

public class LogService extends AbstractService<String> {

    public LogService() {
        super(String.class,
              Constants.ECOMMERCE_ALL_PATTERN,
              LogService.class.getSimpleName(),
              Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()));
    }

    @Override
    protected void parse(ConsumerRecord<String, String> rcd) {
        System.out.println("\nLOGGING FROM: " + rcd.topic());
        System.out.println("KEY: "
                           + rcd.key()
                           + "\n\t"
                           + "VALUE: "
                           + rcd.value()
                           + "\n\t"
                           + "PARTITION: "
                           + rcd.partition()
                           + "\n\t"
                           + "OFFSET: "
                           + rcd.offset());
    }
}
