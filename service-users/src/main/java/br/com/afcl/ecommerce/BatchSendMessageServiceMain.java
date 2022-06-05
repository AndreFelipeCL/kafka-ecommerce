package br.com.afcl.ecommerce;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class BatchSendMessageServiceMain {
	public static void main(String[] args) throws IOException, SQLException, ExecutionException, InterruptedException {
		try (var service = new BatchSendMessageService()) {
			service.run();
		}
	}
}
