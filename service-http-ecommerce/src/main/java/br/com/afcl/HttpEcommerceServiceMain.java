package br.com.afcl;

import jakarta.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpEcommerceServiceMain {

	private static final ServletContextHandler CONTEXT = new ServletContextHandler();

	public static void main(String[] args) throws Exception {
		CONTEXT.setContextPath("/");
		configureContext("/new", new NewOrderServlet());
		configureContext("/admin/generate-reports", new GenerateAllReportsServlet());

		var server = new Server(8080);
		server.setHandler(CONTEXT);
		server.start();
		server.join();
	}

	private static <T extends HttpServlet> void configureContext(final String pathSpec, final T servlet) {
		CONTEXT.addServlet(new ServletHolder(servlet), pathSpec);
	}
}
