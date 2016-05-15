package smartHomeManagement.processor;

import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class MailProcessor {

	   private static final Logger LOG = LoggerFactory.getLogger(MailProcessor.class);;  

			   public void process(Exchange exchange) throws Exception {   

			    LOG.debug("MailProcessor...");   
			    String body = exchange.getIn().getBody(String.class);   

			    Map<String, DataHandler> attachments = exchange.getIn().getAttachments();   
			    if (attachments.size() > 0) {   
			     for (String name : attachments.keySet()) {   
			      exchange.getOut().setHeader("attachmentName", name);   
			     }   
			    }   

			    // read the attachments from the in exchange putting them back on the out   
			    exchange.getOut().setAttachments(attachments);   

			    // resetting the body on out exchange   
			    exchange.getOut().setBody(body);   
			    LOG.debug("MailProcessor complete");   
			   }   
	}

