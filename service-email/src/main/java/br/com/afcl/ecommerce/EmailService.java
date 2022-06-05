package br.com.afcl.ecommerce;

import java.util.Map;

import br.com.afcl.ecommerce.model.MessageWrapper;
import br.com.afcl.ecommerce.services.AbstractService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService extends AbstractService<Email> {

	public EmailService() {
		super(EmailService.class.getSimpleName(),
			  "ECOMMERCE_SEND_EMAIL",
			  Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EmailDeserializer.class.getName())
		);
	}

	@Override
	protected void parse(ConsumerRecord<String, MessageWrapper<Email>> rcd) {
		System.out.println("Sending new order email. OFFSET: " + rcd.offset());
		System.out.println("Email sent.");
	}
}
