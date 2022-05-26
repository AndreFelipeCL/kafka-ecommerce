package br.com.afcl;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpEcommerceServiceMain {
	
	public static void main(String[] args) throws Exception {
		var server = new Server(8080);
		server.setHandler(configureContext());
		server.start();
		server.join();
	}
	
	private static ServletContextHandler configureContext() {
		var context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(new ServletHolder(new NewOrderServlet()), "/new");
		return context;
	}
}
