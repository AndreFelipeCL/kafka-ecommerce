package br.com.afcl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import br.com.afcl.ecommerce.dispatcher.KafkaDispatcher;
import br.com.afcl.ecommerce.model.CorrelationId;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewOrderServlet extends HttpServlet {

	private final KafkaDispatcher<Order> dispatcherOrder = new KafkaDispatcher<>();
	private final KafkaDispatcher<Email> dispatcherMail = new KafkaDispatcher<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		var orderId = UUID.randomUUID().toString();
		var totalAmount = new BigDecimal(req.getParameter("totalAmount"));
		var email = req.getParameter("email");
		var newOrder = new Order(orderId, totalAmount, email);
		var emailCode = new Email("Order Received!", "Thanks for your order! We are processing your order.");

		try {
			this.dispatcherOrder.send(new CorrelationId(NewOrderServlet.class.getSimpleName()),
									  "ECOMMERCE_NEW_ORDER",
									  email,
									  newOrder);
			this.dispatcherMail.send(new CorrelationId(NewOrderServlet.class.getSimpleName()),
									 "ECOMMERCE_SEND_EMAIL",
									 email,
									 emailCode);
			System.out.println("New Order sent successfully.");

			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().println("New Order sent successfully.");
		} catch (ExecutionException | InterruptedException | IOException e) {
			throw new ServletException(e);
		}
	}
}
