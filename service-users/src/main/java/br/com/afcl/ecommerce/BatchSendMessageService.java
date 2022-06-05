package br.com.afcl.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import br.com.afcl.ecommerce.dispatcher.KafkaDispatcher;
import br.com.afcl.ecommerce.model.MessageWrapper;
import br.com.afcl.ecommerce.services.AbstractService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class BatchSendMessageService extends AbstractService<String> {

	private final Connection connection;
	private final KafkaDispatcher<User> userKafkaDispatcher = new KafkaDispatcher<>();

	public BatchSendMessageService() throws SQLException {
		super(BatchSendMessageService.class.getSimpleName(),
			  "ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS",
			  Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()));

		var url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/service-users/target/users_database.db";
		this.connection = DriverManager.getConnection(url);
		this.connection.createStatement().execute("CREATE TABLE IF NOT EXISTS USERS(" +
												  "uuid VARCHAR(255) PRIMARY KEY," +
												  "email VARCHAR(255))");
	}

	@Override
	protected void parse(ConsumerRecord<String, MessageWrapper<String>> rcd) throws ExecutionException, InterruptedException, SQLException {
		System.out.println("______________________________________");
		System.out.println("Processing New Batch");
		System.out.println("TOPIC: " + rcd.value());

		for (var user : getAllUsers()) {
			userKafkaDispatcher.sendAsync(rcd.value().getCorrelationId().continueWith(BatchSendMessageService.class.getSimpleName()),
										  "ECOMMERCE_USER_GENERATE_READING_REPORT",
										  user.getUuid(),
										  user);
			System.out.println("Sent to: " + user);
		}
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

	private List<User> getAllUsers() throws SQLException {
		var resultSet = connection.prepareStatement("SELECT uuid, email FROM USERS").executeQuery();
		var users = new LinkedList<User>();
		while (resultSet.next()) {
			users.add(new User(resultSet.getString(1), resultSet.getString(2)));
		}
		return users;
	}
}
