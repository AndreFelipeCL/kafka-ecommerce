package br.com.afcl.ecommerce;

import java.io.IOException;

public class LogServiceMain {

    public static void main(String[] args) throws IOException {
        try (var service = new LogService()) {
            service.run();
        }
    }
}
