package br.com.afcl.ecommerce.pubsub;

import br.com.afcl.ecommerce.pubsub.dispatcher.KafkaDispatcher;
import br.com.afcl.ecommerce.pubsub.model.Constants;
import br.com.afcl.ecommerce.pubsub.model.Email;
import br.com.afcl.ecommerce.pubsub.model.Order;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * The main class of NewOrder e-Commerce.
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 27/04/2022
 */
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var dispatcherOrder = new KafkaDispatcher<Order>();
        var dispatcherMail = new KafkaDispatcher<Email>();

        for (int i = 0; i < 10; i++) {
            var userID = UUID.randomUUID().toString();
            var orderId = UUID.randomUUID().toString();
            var totalAmount = BigDecimal.valueOf(Math.random() * 5000 + 1);
            var newOrder = new Order(userID, orderId, totalAmount);
            dispatcherOrder.send(Constants.ECOMMERCE_NEW_ORDER, userID, newOrder);

            var email = new Email("Order Received!", "Thanks for your order! We are processing your order.");
            dispatcherMail.send(Constants.ECOMMERCE_SEND_EMAIL, userID, email);
        }

    }
}