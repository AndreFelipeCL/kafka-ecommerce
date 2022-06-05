package ecommerce;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

import br.com.afcl.ecommerce.model.MessageWrapper;
import br.com.afcl.ecommerce.services.AbstractService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ReadingReportService extends AbstractService<User> {

	private static final Path SOURCE = new File("src/main/resources/report.txt").toPath();

	public ReadingReportService() {
		super(ReadingReportService.class.getSimpleName(),
			  "ECOMMERCE_USER_GENERATE_READING_REPORT",
			  Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserDeserializer.class.getName()));
	}

	@Override
	protected void parse(ConsumerRecord<String, MessageWrapper<User>> rcd) throws IOException {
		System.out.println("______________________________________");
		System.out.println("Processing report for: " + rcd.value());
		composeReport(rcd);
	}

	private void composeReport(ConsumerRecord<String, MessageWrapper<User>> rcd) throws IOException {
		var user = rcd.value().getPayload();
		var target = new File(user.reportPath());
		IOFile.copyTo(SOURCE, target);
		IOFile.append(target, "\nCreated By: " + user.getUuid());
		IOFile.append(target, "\nEmail: " + user.getEmail());
		IOFile.append(target, "\nCreated at: " + LocalDateTime.now());
		System.out.println("File created at: " + target.getAbsolutePath());
	}

}
