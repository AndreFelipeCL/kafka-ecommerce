package br.com.afcl.ecommerce;

import br.com.afcl.ecommerce.services.AbstractService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService extends AbstractService<Order> {

    public FraudDetectorService() {
        super(Order.class, "ECOMMERCE_NEW_ORDER", FraudDetectorService.class.getSimpleName());
    }

    @Override
    protected void parse(ConsumerRecord<String, Order> rcd) {
        System.out.println("\n________ New Order Found ________ \n\t"
                           + "TOPIC: " + rcd.topic() + "\n\t"
                           + "KEY: " + rcd.key() + "\n\t"
                           + "VALUE: " + rcd.value() + "\n\t"
                           + "PARTITION: " + rcd.partition() + "\n\t"
                           + "OFFSET: " + rcd.offset()
        );
        System.out.println("Checking for fraud...");
        System.out.println(String.format("Order %s Processed.", rcd.key()));
    }
}
