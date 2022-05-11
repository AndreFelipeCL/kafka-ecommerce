package br.com.afcl.ecommerce;

import br.com.afcl.ecommerce.services.LogService;

import java.io.IOException;

public class LogServiceMain {

    public static void main(String[] args) throws IOException {
        try (var service = new LogService()) {
            service.run();
        }
    }
}
