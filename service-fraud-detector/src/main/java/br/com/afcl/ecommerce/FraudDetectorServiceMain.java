package br.com.afcl.ecommerce;

import br.com.afcl.ecommerce.services.FraudDetectorService;

import java.io.IOException;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 10/05/2022
 */
public class FraudDetectorServiceMain {

    public static void main(String[] args) throws IOException {
        try (var service = new FraudDetectorService()) {
            service.run();
        }
    }

}
