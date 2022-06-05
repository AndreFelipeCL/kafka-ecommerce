package ecommerce;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * TODO: Do JavaDoc of class...
 *
 * @author Andre Felipe C. Leite
 * @version 1.0 10/05/2022
 */
public class ReadingReportServiceMain {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        try (var service = new ReadingReportService()) {
            service.run();
        }
    }

}
