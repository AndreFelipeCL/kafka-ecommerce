package br.com.afcl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import br.com.afcl.ecommerce.dispatcher.KafkaDispatcher;
import br.com.afcl.ecommerce.model.CorrelationId;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GenerateAllReportsServlet extends HttpServlet {

	private final KafkaDispatcher<String> batchDispatcher = new KafkaDispatcher<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			batchDispatcher.send(new CorrelationId(GenerateAllReportsServlet.class.getSimpleName()),
								 "ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS",
								 "ECOMMERCE_USER_GENERATE_READING_REPORT",
								 "ECOMMERCE_USER_GENERATE_READING_REPORT");
			System.out.println("Sent generated reports to all users.");
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().println("Report request generated.");
		} catch (ExecutionException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}
