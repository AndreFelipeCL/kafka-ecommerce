package br.com.afcl.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import br.com.afcl.ecommerce.model.MessageWrapper;
import br.com.afcl.ecommerce.services.AbstractService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class CreateUserService extends AbstractService<Order> {

	private final Connection connection;

	public CreateUserService() throws SQLException {
		super(CreateUserService.class.getSimpleName(),
			  "ECOMMERCE_NEW_ORDER",
			  Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class.getName())
		);
		var url = "jdbc:sqlite:" + "target/users_database.db";
		this.connection = DriverManager.getConnection(url);
		this.connection.createStatement().execute("CREATE TABLE IF NOT EXISTS USERS(" +
												  "uuid VARCHAR(255) PRIMARY KEY," +
												  "email VARCHAR(255))");
	}

	@Override
	protected void parse(ConsumerRecord<String, MessageWrapper<Order>> rcd) {
		System.out.println("______________________________________");
		System.out.println("Processing New Order: checking for new user");
		var order = rcd.value().getPayload();
		System.out.println(order.toString());
		try {
			if (!userExists(order)) {
				insertNewUser(order);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		System.out.println("______________________________________");
	}

	@Override
	public void close() throws IOException {
		super.close();
		try {
			this.connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean userExists(Order order) throws SQLException {
		var checkStatement = this.connection.prepareStatement("SELECT UUID FROM USERS WHERE EMAIL = ? limit 1");
		checkStatement.setString(1, order.getEmail());
		return checkStatement.executeQuery().next();
	}

	private void insertNewUser(Order order) throws SQLException {
		var insertUser = this.connection.prepareStatement("INSERT INTO USERS(uuid, email) VALUES (?,?)");
		insertUser.setString(1, UUID.randomUUID().toString());
		insertUser.setString(2, order.getEmail());
		insertUser.execute();
		System.out.println("Novo Usuario criado para o email: " + order.getEmail());
	}
}
