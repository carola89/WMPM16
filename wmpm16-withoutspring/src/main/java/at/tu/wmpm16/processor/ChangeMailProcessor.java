package at.tu.wmpm16.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeMailProcessor implements Processor{

	Logger logger = LoggerFactory.getLogger(ChangeMailProcessor.class);  


	@Override
	public void process(Exchange exchange) throws Exception {	
		logger.info("MailProcessor....");

		Map<String, DataHandler> attachments = exchange.getIn().getAttachments();
		
//		logger.info("After reading attachments....");
		if (attachments.size() > 0) {
			System.out.println("In if hinein" + attachments.size());
			for (String name : attachments.keySet()) {
//				System.out.println("In for hinein");
				DataHandler dh = attachments.get(name);
//				System.out.println("DataHandler geted");
				// get the file name
				String filename = dh.getName();
				System.out.println(filename);

				// get the content and convert it to byte[]
				byte[] data = exchange.getContext().getTypeConverter()
						.convertTo(byte[].class, dh.getInputStream());

				// write the data to a file
				File file = new File("c:/wmpm/file/"+filename);
				FileOutputStream out = new FileOutputStream(file);
				out.write(data);
				out.flush();
				out.close();

				logger.info("MailProcessor complete");   
			}
		}
	}
}
