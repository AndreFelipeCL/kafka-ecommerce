package br.com.afcl.ecommerce;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LogServiceMain {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		try (var service = new LogService()) {
			service.run();
		}
	}
}
