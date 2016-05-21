package at.tu.wmpm16.processor;

import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class MailProcessor implements Processor {

	   private static final Logger LOG = LoggerFactory.getLogger(MailProcessor.class);;  

			   public void process(Exchange exchange) throws Exception {   

				
					      Map<String, Object> headers = exchange.getIn().getHeaders();
					      headers.put("To", "e0925011@student.tuwien.ac.at");
					      headers.put("Subject", "Consumption data"); 
			     
	}
}

