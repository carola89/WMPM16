package smartHomeManagement.processor;

import org.apache.camel.Exchange;

public class FileProcessor {
	 public void process(Exchange exchange) throws Exception {
	        String body = exchange.getIn().getBody(String.class);

	        exchange.getIn().setBody(body);
	    }
}
