package br.com.afcl.ecommerce;

import java.io.IOException;
import java.sql.SQLException;

public class CreateUserServiceMain {
	public static void main(String[] args) {
		try (var service = new CreateUserService()) {
			service.run();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
