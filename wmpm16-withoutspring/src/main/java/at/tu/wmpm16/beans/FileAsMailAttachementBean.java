package at.tu.wmpm16.beans;

import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.stereotype.Component;

@Component
public class FileAsMailAttachementBean {

	public List<Map<String, Object>> process(Exchange exchange) {
		CamelContext context = new DefaultCamelContext();
		
		Endpoint endpoint = context.getEndpoint("smtps://smtp.gmail.com:587?username=wmpm16.10@gmail.com&password=wmpm1610&to=wmpm16.10@gmail.com");
		
		Message in = exchange.getIn();
		System.out.println("in: " + in);
		byte[] file = in.getBody(byte[].class);
		System.out.println("file " + file.toString());
		String fileId = in.getHeader("CamelFileName", String.class);
		System.out.println("fileid " + fileId);
		in.addAttachment(fileId, new DataHandler(file, "plain/text"));
		System.out.println("in out " + in);
		in.setBody("");
		
		// create a producer that can produce the exchange (= send the mail)
		Producer producer;
		try {
			producer = endpoint.createProducer();
			// start the producer
			producer.start();
			// and let it go (processes the exchange by sending the email)
			producer.process(exchange);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}

}
