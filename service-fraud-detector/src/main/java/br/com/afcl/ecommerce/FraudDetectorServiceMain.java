package br.com.afcl.ecommerce;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 10/05/2022
 */
public class FraudDetectorServiceMain {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		try (var service = new FraudDetectorService()) {
			service.run();
		}
	}

}
