package br.com.afcl.ecommerce.pubsub;

import br.com.afcl.ecommerce.pubsub.services.EmailService;

import java.io.IOException;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 10/05/2022
 */
public class EmailServiceMain {

    public static void main(String[] args) throws IOException {
        try (var service = new EmailService()) {
            service.run();
        }
    }
}
