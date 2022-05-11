package br.com.afcl.ecommerce.email;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService extends AbstractService<Email> {

    public EmailService() {
        super(Email.class, "ECOMMERCE_SEND_EMAIL", EmailService.class.getSimpleName());
    }

    @Override
    protected void parse(ConsumerRecord<String, Email> rcd) {
        System.out.println("Sending new order email. OFFSET: " + rcd.offset());
        System.out.println("Email sent.");
    }
}
