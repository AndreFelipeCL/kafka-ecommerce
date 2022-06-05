package br.com.afcl.ecommerce;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class CreateUserServiceMain {
	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, SQLException {
		try (var service = new CreateUserService()) {
			service.run();
		}
	}
}
