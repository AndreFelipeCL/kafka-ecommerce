package br.com.afcl.ecommerce;

import java.util.Map;

import br.com.afcl.ecommerce.model.MessageWrapper;
import br.com.afcl.ecommerce.services.AbstractService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService extends AbstractService<String> {

	public LogService() {
		super(LogService.class.getSimpleName(),
			  "ECOMMERCE.*",
			  Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()));
	}

	@Override
	protected void parse(ConsumerRecord<String, MessageWrapper<String>> rcd) {
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
