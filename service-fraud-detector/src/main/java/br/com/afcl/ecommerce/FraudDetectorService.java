package br.com.afcl.ecommerce;

import java.math.BigDecimal;

import br.com.afcl.ecommerce.services.AbstractService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService extends AbstractService<Order> {
	
	public FraudDetectorService() {
		super(Order.class, "ECOMMERCE_NEW_ORDER", FraudDetectorService.class.getSimpleName(), Order::getEmail);
	}
	
	@Override
	protected void parse(ConsumerRecord<String, Order> rcd) throws Exception {
		System.out.println("Checking for fraud...");
		var order = rcd.value();
		printOrder(rcd);
		if (isFraud(order)) {
			System.out.println("Order is a Fraud");
			dispatchOrder("ECOMMERCE_ORDER_REJECTED", order);
		} else {
			System.out.println("Order Approved");
			dispatchOrder("ECOMMERCE_ORDER_APPROVED", order);
		}
		System.out.println(String.format("Order %s Processed.%n%n", rcd.key()));
	}
	
	private boolean isFraud(Order order) {
		return order.getTotalAmount().compareTo(new BigDecimal("4500")) >= 0;
	}
	
	private void printOrder(ConsumerRecord<String, Order> rcd) {
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
