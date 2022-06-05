package br.com.afcl.ecommerce;

import java.math.BigDecimal;
import java.util.Map;

import br.com.afcl.ecommerce.model.MessageWrapper;
import br.com.afcl.ecommerce.services.AbstractService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService extends AbstractService<Order> {

	public FraudDetectorService() {
		super(FraudDetectorService.class.getSimpleName(),
			  "ECOMMERCE_NEW_ORDER",
			  Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class.getName()));
	}

	@Override
	protected void parse(ConsumerRecord<String, MessageWrapper<Order>> rcd) throws Exception {
		System.out.println("Checking for fraud...");
		var messageWrapper = rcd.value();
		var order = messageWrapper.getPayload();
		printOrder(rcd);
		if (isFraud(order)) {
			System.out.println("Order is a Fraud");
			dispatch("ECOMMERCE_ORDER_REJECTED",
					 messageWrapper.getCorrelationId().continueWith(FraudDetectorService.class.getSimpleName()),
					 messageWrapper);
		} else {
			System.out.println("Order Approved");
			dispatch("ECOMMERCE_ORDER_APPROVED",
					 messageWrapper.getCorrelationId().continueWith(FraudDetectorService.class.getSimpleName()),
					 messageWrapper);
		}
		System.out.println(String.format("Order %s Processed.%n%n", rcd.key()));
	}

	private boolean isFraud(Order messageWrapper) {
		return messageWrapper.getTotalAmount().compareTo(new BigDecimal("4500")) >= 0;
	}

	private void printOrder(ConsumerRecord<String, MessageWrapper<Order>> rcd) {
		System.out.println("________ New Order Found ________ \n\t" +
						   "TOPIC: " +
						   rcd.topic() +
						   "\n\t" +
						   "KEY: " +
						   rcd.key() +
						   "\n\t" +
						   "VALUE: " +
						   rcd.value() +
						   "\n\t" +
						   "PARTITION: " +
						   rcd.partition() +
						   "\n\t" +
						   "OFFSET: " +
						   rcd.offset());
	}
}
